package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import edu.stanford.webprotege.shared.annotations.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnnotatedEntityCardPresenterClassExtractorTest {

    @Mock
    private JavaProjectBuilder mockProjectBuilder;

    @Mock
    private JavaClass mockClassWithAnnotation;

    @Mock
    private JavaClass mockClassWithoutAnnotation;

    @Mock
    private JavaAnnotation mockAnnotation;

    private AnnotatedEntityCardPresenterClassExtractor extractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        extractor = new AnnotatedEntityCardPresenterClassExtractor(mockProjectBuilder);
        var type = mock(JavaClass.class);
        when(type.getCanonicalName()).thenReturn(Card.class.getCanonicalName());
        when(mockAnnotation.getType()).thenReturn(type);
    }

    @Test
    void shouldExtractAnnotatedClassesCorrectly() {
        when(mockClassWithAnnotation.getAnnotations()).thenReturn(List.of(mockAnnotation));
        when(mockClassWithoutAnnotation.getAnnotations()).thenReturn(Collections.emptyList());
        when(mockProjectBuilder.getClasses()).thenReturn(List.of(mockClassWithAnnotation, mockClassWithoutAnnotation));

        var result = extractor.findAnnotatedEntityCardPresenterClasses();

        assertEquals(1, result.size());
    }

    @Test
    void shouldIgnoreClassesWithoutAnnotation() {
        when(mockClassWithoutAnnotation.getAnnotations()).thenReturn(Collections.emptyList());
        when(mockProjectBuilder.getClasses()).thenReturn(List.of(mockClassWithoutAnnotation));

        var result = extractor.findAnnotatedEntityCardPresenterClasses();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldHandleEmptyJavaProject() {
        when(mockProjectBuilder.getClasses()).thenReturn(Collections.emptyList());

        var result = extractor.findAnnotatedEntityCardPresenterClasses();

        assertTrue(result.isEmpty());
    }

    @Test
    void constructorShouldEnforceNonNullProjectBuilder() {
        assertThrows(NullPointerException.class, () -> new AnnotatedEntityCardPresenterClassExtractor(null));
    }
}
