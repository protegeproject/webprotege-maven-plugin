package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.model.JavaAnnotation;
import edu.stanford.webprotege.shared.annotations.Portlet;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 27 May 16
 */
public class Annotations {

    /**
     * Determines if the specified JavaAnnotation is a Portlet annotation
     * @param annotation The annotation.
     * @return true if the specified annotation is a Portlet annotation otherwise false.
     */
    public static boolean isPortletAnnotation(JavaAnnotation annotation) {
        return annotation.getType().getCanonicalName().equals(Portlet.class.getName());
    }
}
