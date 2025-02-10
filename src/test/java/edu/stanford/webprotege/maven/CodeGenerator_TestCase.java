package edu.stanford.webprotege.maven;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 27 May 16
 *
 * An integration test for the code generator, although
 * this is fast enough to run as a unit test.
 */
@ExtendWith(MockitoExtension.class)
public class CodeGenerator_TestCase {

    private WebProtegeCodeGeneratorVelocityImpl codeGenerator;

    @BeforeEach
    public void setUp() throws Exception {
        SourceWriter sourceWriter = (packageName, simpleClassName, source) -> {
            try {
                JavaParser.parse(new StringReader(source), true);
            } catch (ParseException e) {
                // Source code failed to parse.  Throw an exception that will cause the test to fail.
                throw new RuntimeException("Failed to parse generated source code", e);
            }
        };
        PortletTypeDescriptor descriptor = new PortletTypeDescriptor(
                "edu.stanford.protege.MyPortlet",
                "MyPortlet",
                "edu.stanford.protege",
                "my.portlet.id",
                "My Portlet\"\"!",
                "My amazing portlet.\n(Does all \"sorts\" of things)");
        PortletModuleDescriptor moduleDescriptorA = new PortletModuleDescriptor("edu.stanford.protege.MyPortletModuleA");
        PortletModuleDescriptor moduleDescriptorB = new PortletModuleDescriptor("edu.stanford.protege.MyPortletModuleB");
        codeGenerator = new WebProtegeCodeGeneratorVelocityImpl(
                Collections.singleton(descriptor),
                Sets.newHashSet(moduleDescriptorA, moduleDescriptorB),
                sourceWriter
        );
    }

    @Test
    public void shouldWriteValidJavaSource() throws IOException, ParseException {
        codeGenerator.generate();
    }
}
