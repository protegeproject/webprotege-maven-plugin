package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
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
            for (Object compileSourceRoot : new ArrayList<>(project.getCompileSourceRoots())) {
                String sourceRoot = (String) compileSourceRoot;
                processCompileSourceRoot(sourceRoot);
            }
            var props = WebProtegeMavenPluginBuildProperties.get(project, getLog());
            props.setTimestamp(System.currentTimeMillis());
        } catch (IOException e) {
            getLog().error(e.getMessage(), e);
        }
    }

    private void processCompileSourceRoot(String sourceRoot) throws IOException {
        try {
            JavaProjectBuilder builder = getProjectBuilder(sourceRoot);

            AnnotatedPortletClassExtractor extractor = new AnnotatedPortletClassExtractor(builder);
            Set<AnnotatedPortletClass> portletClasses = extractor.findAnnotatedPortletClasses();

            AnnotatedEntityCardPresenterClassExtractor entityCardExtractor = new AnnotatedEntityCardPresenterClassExtractor(builder);
            Set<AnnotatedEntityCardPresenterClass> entityCardPresenterClasses = entityCardExtractor.findAnnotatedEntityCardPresenterClasses();

            long lastBuildTimestamp = WebProtegeMavenPluginBuildProperties.get(project, getLog()).getTimestamp();

            Set<Object> modifiedThings = new HashSet<>();
            Set<PortletTypeDescriptor> descriptors = portletClasses.stream()
                    .peek(d -> {
                        if (isModifiedSinceLastBuild(lastBuildTimestamp, d.getJavaClass())) {
                            modifiedThings.add(d);
                        }
                    })
                    .map(c -> new PortletTypeDescriptorBuilder(
                            c.getJavaClass(),
                            c.getJavaAnnotation()).build()
                    )
                    .collect(toSet());
            logPortletDescriptors(descriptors);


            Set<EntityCardPresenterClassDescriptor> entityCardDescriptors = entityCardPresenterClasses.stream()
                    .peek(c -> {
                        if (isModifiedSinceLastBuild(lastBuildTimestamp, c.javaClass())) {
                            modifiedThings.add(c);
                        }
                    })
                    .map(c -> new EntityCardPresenterClassDescriptor(
                            c.javaClass().getPackageName(),
                            c.javaClass().getCanonicalName(),
                            c.javaClass().getName(),
                            Util.annotationValueToString(c.javaAnnotation().getProperty("id")),
                            Util.annotationValueToString(c.javaAnnotation().getProperty("title"))))
                    .collect(toSet());
            logEntityCardPresenters(entityCardDescriptors);

            if(modifiedThings.isEmpty()) {
                getLog().info("[WebProtegeMojo] No modifications since last build");
                return;
            }

            AnnotatedPortletModuleClassExtractor moduleClassExtractor = new AnnotatedPortletModuleClassExtractor(builder);
            Set<PortletModuleDescriptor> moduleDescriptors = moduleClassExtractor.findAnnotatedProjectModulePlugins();
            WebProtegeCodeGeneratorVelocityImpl gen = new WebProtegeCodeGeneratorVelocityImpl(
                    descriptors,
                    moduleDescriptors,
                    new MavenSourceWriter(project, getLog()));
            gen.generate();


            EntityCardPresenterFactoryCodeGenerator gen2 = new EntityCardPresenterFactoryCodeGenerator(
                    entityCardDescriptors,
                    new HashSet<>(),
                    new MavenSourceWriter(project, getLog())
            );
            gen2.generate();

        } catch (Exception e) {
            getLog().error(e);
        }

    }

    private boolean isModifiedSinceLastBuild(long lastBuildTimestamp,
                                             JavaClass d) {
        try {
            var sourceUrl = d.getSource().getURL();
            File file = new File(sourceUrl.toURI());
            boolean modified = Files.getLastModifiedTime(file.toPath()).toInstant().getEpochSecond() > lastBuildTimestamp;
            if (!modified) {
                getLog().info("[WebProtegeMojo] " + d + " has not been modified since last build");
            }
            return modified;
        } catch (URISyntaxException | IOException e) {
            getLog().info("[WebProtegeMojo] Could not get source file: " + e.getMessage());
            return true;
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
        for (PortletTypeDescriptor d : descriptors) {
            getLog().info("[WebProtegeMojo]        " + d);
        }
    }

    private void logEntityCardPresenters(Set<EntityCardPresenterClassDescriptor> descriptors) {
        getLog().info("[WebProtegeMojo]  EntityCards:");
        for (EntityCardPresenterClassDescriptor d : descriptors) {
            getLog().info("[WebProtegeMojo]        " + d);
        }
    }
}
