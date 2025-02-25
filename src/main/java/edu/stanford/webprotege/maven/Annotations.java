package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.model.JavaAnnotation;
import edu.stanford.webprotege.shared.annotations.*;

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

    /**
     * Determines if the specified {@link JavaAnnotation} is a ProjectModule annotation.
     * @param annotation The annotation.
     * @return true if the specified annotation is a {@link ProjectModule} annotation,
     * otherwise false.
     */
    public static boolean isProjectModuleAnnotation(JavaAnnotation annotation) {
        return annotation.getType().getCanonicalName().equals(PortletModule.class.getName());
    }

    /**
     * Determines if the specified {@link JavaAnnotation} is a EntityCardPresenter annotation.
     * @param javaAnnotation The annotation.
     * @return true if the specified annotation is an EntityCardPresenter annotation, otherwise false.
     */
    public static boolean isEntityCardPresenterAnnotation(JavaAnnotation javaAnnotation) {
        return javaAnnotation.getType().getCanonicalName().equals(Card.class.getName());
    }
}
