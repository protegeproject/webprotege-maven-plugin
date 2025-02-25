package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnnotatedEntityCardPresenterClassTest {

    @Test
    void shouldStoreJavaClassAndAnnotation() {
        var mockClass = mock(JavaClass.class);
        var mockAnnotation = mock(JavaAnnotation.class);

        var instance = new AnnotatedEntityCardPresenterClass(mockClass, mockAnnotation);

        assertEquals(mockClass, instance.javaClass());
        assertEquals(mockAnnotation, instance.javaAnnotation());
    }

    @Test
    void shouldThrowExceptionIfJavaClassIsNull() {
        var mockAnnotation = mock(JavaAnnotation.class);

        assertThrows(NullPointerException.class, () -> new AnnotatedEntityCardPresenterClass(null, mockAnnotation));
    }

    @Test
    void shouldThrowExceptionIfJavaAnnotationIsNull() {
        var mockClass = mock(JavaClass.class);

        assertThrows(NullPointerException.class, () -> new AnnotatedEntityCardPresenterClass(mockClass, null));
    }
}
