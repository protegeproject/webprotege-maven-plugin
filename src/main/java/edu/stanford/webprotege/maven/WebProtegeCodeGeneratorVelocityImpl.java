package edu.stanford.webprotege.maven;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toSet;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 26 Apr 16
 */
public class WebProtegeCodeGeneratorVelocityImpl {

    private static final String PACKAGE_NAME = "edu.stanford.bmir.protege.web.client.portlet";

    private static final String DESCRIPTORS = "descriptors";

    private static final String MODULE_DESCRIPTORS = "moduleDescriptors";

    private static final String IMPORTED_PACKAGES = "importedPackages";

    private static final String CLASSPATH_RESOURCE_LOADER_CLASS = "classpath.resource.loader.class";


    private Set<PortletTypeDescriptor> descriptors;

    private Set<PortletModuleDescriptor> moduleDescriptors;

    private SourceWriter sourceWriter;

    public WebProtegeCodeGeneratorVelocityImpl(Set<PortletTypeDescriptor> descriptors,
                                               Set<PortletModuleDescriptor> moduleDescriptors,
                                               SourceWriter sourceWriter) {
        this.descriptors = new TreeSet<>(checkNotNull(descriptors));
        this.moduleDescriptors = new HashSet<>(checkNotNull(moduleDescriptors));
        this.sourceWriter = checkNotNull(sourceWriter);
    }

    private Set<String> getImportedPackages() {
        return descriptors.stream()
                .map(PortletTypeDescriptor::getPackageName)
                .collect(toSet());
    }

    public void generate() throws IOException {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty(CLASSPATH_RESOURCE_LOADER_CLASS, ClasspathResourceLoader.class.getName());
        engine.init();

        VelocityContext context = new VelocityContext();
        context.put(DESCRIPTORS, descriptors);
        context.put(MODULE_DESCRIPTORS, moduleDescriptors);
        context.put(IMPORTED_PACKAGES, getImportedPackages());
        context.put("date", new Date().toString());

        generateSource("PortletFactoryGenerated", PACKAGE_NAME, engine, context);
        generateSource("PortletModulesGenerated", PACKAGE_NAME, engine, context);
    }


    private void generateSource(String className, String packageName, VelocityEngine engine, VelocityContext context) throws IOException {
        Template template = engine.getTemplate(className + ".java.vm");
        writeSource(packageName, className, context, template);
    }


    private void writeSource(String packageName, String className, VelocityContext context, Template template) throws IOException {
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        String source = writer.toString();
        sourceWriter.writeSource(packageName, className, source);
    }
}
