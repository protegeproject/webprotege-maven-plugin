package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import edu.stanford.webprotege.shared.annotations.Portlet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 27 May 16
 */
@RunWith(MockitoJUnitRunner.class)
public class Annotations_TestCase {

    @Mock
    private JavaAnnotation annotation;

    @Mock
    private JavaClass portletAnnotationClass;

    @Before
    public void setUp() throws Exception {
        when(annotation.getType()).thenReturn(portletAnnotationClass);
    }

    @Test
    public void shouldFindPortletAnnotation() {
        when(portletAnnotationClass.getCanonicalName()).thenReturn(Portlet.class.getName());
        assertThat(Annotations.isPortletAnnotation(annotation), is(true));
    }

    @Test
    public void shouldNotFindPortletAnnotation() {
        when(portletAnnotationClass.getCanonicalName()).thenReturn("SomethingElse");
        assertThat(Annotations.isPortletAnnotation(annotation), is(false));
    }
}
