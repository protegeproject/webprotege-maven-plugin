package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.JavaProjectBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 26 Apr 16
 */
@Mojo(name = "generatePortletFactory", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class WebProtegeMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @SuppressWarnings("unchecked")
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            for(Object compileSourceRoot : new ArrayList<>(project.getCompileSourceRoots())) {
                String sourceRoot = (String) compileSourceRoot;
                processCompileSourceRoot(sourceRoot);
            }
        } catch (IOException e) {
            getLog().error(e.getMessage(), e);
        }
    }

    private void processCompileSourceRoot(String sourceRoot) throws IOException {
        try {
            JavaProjectBuilder builder = getProjectBuilder(sourceRoot);
            AnnotatedPortletClassExtractor extractor = new AnnotatedPortletClassExtractor(builder);
            Set<AnnotatedPortletClass> portletClasses = extractor.findAnnotatedPortletClasses();
            Set<PortletTypeDescriptor> descriptors = portletClasses.stream()
                    .map(c -> new PortletTypeDescriptorBuilder(
                            c.getJavaClass(),
                            c.getJavaAnnotation()).build()
                    )
                    .collect(toSet());
            logPortletDescriptors(descriptors);
            WebProtegeCodeGeneratorVelocityImpl gen = new WebProtegeCodeGeneratorVelocityImpl(descriptors, new MavenSourceWriter(project, getLog()));
            gen.generate();
        } catch (Exception e) {
            getLog().error(e);
        }

    }

    private JavaProjectBuilder getProjectBuilder(String sourceRoot) {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.setErrorHandler(e -> getLog().info("[WebProtegeMojo] Couldn't parse file: " + e));
        builder.addSourceTree(new File(sourceRoot));
        return builder;
    }

    private void logPortletDescriptors(Set<PortletTypeDescriptor> descriptors) {
        getLog().info("[WebProtegeMojo]  Portlets:");
        for(PortletTypeDescriptor d : descriptors) {
            getLog().info("[WebProtegeMojo]        " + d);
        }
    }
}
