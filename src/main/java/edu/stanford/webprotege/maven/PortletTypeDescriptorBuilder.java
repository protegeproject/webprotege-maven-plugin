package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.expression.AnnotationValue;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 26 May 16
 */
public class PortletTypeDescriptorBuilder {

    public static final String ID_PROPERTY_NAME = "id";

    public static final String TITLE_PROPERTY_NAME = "title";

    public static final String TOOLTIP_PROPERTY_NAME = "tooltip";

    private final JavaClass cls;

    private final JavaAnnotation anno;

    /**
     * Constructs a PortletTypeDescriptorBuilder that will build a PortletTypeDescriptor from the specified annotation
     * on the specified class.
     * @param cls The class that has the portlet annotation.  Not {@code null}.
     * @param anno The actual portlet annotation.  Not {@code null}.
     */
    public PortletTypeDescriptorBuilder(JavaClass cls, JavaAnnotation anno) {
        this.cls = checkNotNull(cls);
        this.anno = checkNotNull(anno);
    }

    /**
     * Builds the descriptor.
     * @return The built portlet descriptor.
     */
    public PortletTypeDescriptor build() {
        AnnotationValue id = anno.getProperty(ID_PROPERTY_NAME);
        AnnotationValue title = anno.getProperty(TITLE_PROPERTY_NAME);
        AnnotationValue tooltip = anno.getProperty(TOOLTIP_PROPERTY_NAME);
        return new PortletTypeDescriptor(
                cls.getCanonicalName(),
                cls.getName(),
                cls.getPackageName(),
                annotationValueToString(id),
                annotationValueToString(title),
                annotationValueToString(tooltip));
    }


    /**
     * Converts a possibly null annotation value to a non-null (possibly empty) string.
     * @param annotationValue The annotation value to be converted.
     * @return The String value of the annotation.
     */
    private static String annotationValueToString(AnnotationValue annotationValue) {
        if(annotationValue == null) {
            return "";
        }
        else if(annotationValue.getParameterValue() instanceof String) {
            String parameterValue = (String) annotationValue.getParameterValue();
            return parameterValue.substring(1, parameterValue.length() - 1);
        }
        else {
            return annotationValue.getParameterValue().toString();
        }
    }

}
