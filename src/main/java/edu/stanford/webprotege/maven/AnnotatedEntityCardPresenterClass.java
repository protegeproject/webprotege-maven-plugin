package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import edu.stanford.webprotege.shared.annotations.Card;

import java.util.Objects;

/**
 * Represents a {@link Card} annotated Java
 * EntityCardPresenter class.
 */
public final class AnnotatedEntityCardPresenterClass {

    private final JavaClass javaClass;

    private final JavaAnnotation javaAnnotation;


    public AnnotatedEntityCardPresenterClass(JavaClass javaClass, JavaAnnotation javaAnnotation) {
        this.javaClass = Objects.requireNonNull(javaClass);
        this.javaAnnotation = Objects.requireNonNull(javaAnnotation);
    }

    public JavaClass javaClass() {
        return javaClass;
    }

    public JavaAnnotation javaAnnotation() {
        return javaAnnotation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AnnotatedEntityCardPresenterClass) obj;
        return Objects.equals(this.javaClass, that.javaClass) &&
                Objects.equals(this.javaAnnotation, that.javaAnnotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(javaClass, javaAnnotation);
    }

    @Override
    public String toString() {
        return "AnnotatedEntityCardPresenterClass[" +
                "javaClass=" + javaClass + ", " +
                "javaAnnotation=" + javaAnnotation + ']';
    }

}
