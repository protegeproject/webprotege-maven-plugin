package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.model.expression.AnnotationValue;

public class Util {

    /**
     * Converts a possibly null annotation value to a non-null (possibly empty) string.
     * @param annotationValue The annotation value to be converted.
     * @return The String value of the annotation.
     */
    public static String annotationValueToString(AnnotationValue annotationValue) {
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
