package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 26 May 16
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PortletTypeDescriptorBuilder_TestCase {

    public static final String CANONICAL_CLASS_NAME = "CANONICAL_CLASS_NAME";

    public static final String PACKAGE_NAME = "PACKAGE_NAME";

    public static final String SIMPLE_NAME = "SIMPLE_NAME";

    public static final String PORTLET_ID = "PORTLET_ID";

    public static final String PORTLET_TITLE = "PORTLET_TITLE";

    public static final String PORTLET_TOOLTIP = "PORTLET_TOOLTIP";

    @Mock
    private JavaClass cls;

    @Mock
    private JavaAnnotation annotation;

    @Mock
    private AnnotationValue annotationValue;


    private PortletTypeDescriptorBuilder builder;

    @BeforeEach
    public void setUp() throws Exception {
        when(cls.getCanonicalName()).thenReturn(CANONICAL_CLASS_NAME);
        when(cls.getPackageName()).thenReturn(PACKAGE_NAME);
        when(cls.getName()).thenReturn(SIMPLE_NAME);
        when(annotation.getNamedParameter("id")).thenReturn(PORTLET_ID);
        builder = new PortletTypeDescriptorBuilder(cls, annotation);
    }

    @Test
    public void shouldTakeCanonicalClassName() {
        PortletTypeDescriptor descriptor = builder.build();
        assertThat(descriptor.getCanonicalClassName(), is(CANONICAL_CLASS_NAME));
    }

    @Test
    public void shouldTakeSimpleClassName() {
        PortletTypeDescriptor descriptor = builder.build();
        assertThat(descriptor.getSimpleName(), is(SIMPLE_NAME));
    }

    @Test
    public void shouldTakePackageName() {
        PortletTypeDescriptor descriptor = builder.build();
        assertThat(descriptor.getPackageName(), is(PACKAGE_NAME));
    }

    @Test
    public void shouldExtractPortletId() {
        setAnnotationPropertyValue("id", PORTLET_ID);
        PortletTypeDescriptor descriptor = builder.build();
        assertThat(descriptor.getId(), is(PORTLET_ID));
    }

    @Test
    public void shouldExtractPortletTitle() {
        setAnnotationPropertyValue("title", PORTLET_TITLE);
        PortletTypeDescriptor descriptor = builder.build();
        assertThat(descriptor.getTitle(), is(PORTLET_TITLE));
    }

    @Test
    public void shouldExtractPortletTooltipIfPresent() {
        setAnnotationPropertyValue("tooltip", PORTLET_TOOLTIP);
        PortletTypeDescriptor descriptor = builder.build();
        assertThat(descriptor.getTooltip(), is(PORTLET_TOOLTIP));
    }

    @Test
    public void shouldExtractAbsentPortletTooltip() {
        PortletTypeDescriptor descriptor = builder.build();
        assertThat(descriptor.getTooltip(), is(""));
    }


    /**
     * Sets the property values for an annotation.
     * @param propertyName The name of the property.
     * @param value The unquoted string value.  This value will be wrapped in double quotes and set as the value.
     */
    private void setAnnotationPropertyValue(String propertyName, String value) {
        // When QDox parses annotations, it includes the double quotes in string values.
        String quotedValue = String.format("\"%s\"", value);
        when(annotationValue.getParameterValue()).thenReturn(quotedValue);
        when(annotation.getProperty(propertyName)).thenReturn(annotationValue);
    }
}
