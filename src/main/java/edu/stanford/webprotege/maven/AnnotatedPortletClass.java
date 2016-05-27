package edu.stanford.webprotege.maven;

import com.google.common.base.Objects;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 27 May 16
 */
public class AnnotatedPortletClass {

    private final JavaClass javaClass;

    private final JavaAnnotation javaAnnotation;

    /**
     * Constructs a description of a Java class annotated with a portlet class.
     * @param javaClass The annotated Java class.
     * @param javaAnnotation The portlet annotation.
     */
    public AnnotatedPortletClass(JavaClass javaClass, JavaAnnotation javaAnnotation) {
        this.javaClass = checkNotNull(javaClass);
        this.javaAnnotation = checkNotNull(javaAnnotation);
    }

    /**
     * Gets the annotated Java class.
     * @return The class.
     */
    public JavaClass getJavaClass() {
        return javaClass;
    }

    /**
     * Gets the portle annotation.
     * @return The annotation.
     */
    public JavaAnnotation getJavaAnnotation() {
        return javaAnnotation;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(javaClass, javaAnnotation);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AnnotatedPortletClass)) {
            return false;
        }
        AnnotatedPortletClass other = (AnnotatedPortletClass) obj;
        return this.javaClass.equals(other.javaClass) && this.javaAnnotation.equals(other.javaAnnotation);
    }


    @Override
    public String toString() {
        return toStringHelper("AnnotatedPortletClass")
                .addValue(javaClass)
                .addValue(javaAnnotation)
                .toString();
    }
}
