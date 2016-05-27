package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import edu.stanford.webprotege.shared.annotations.Portlet;
import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 27 May 16
 */
@RunWith(MockitoJUnitRunner.class)
public class AnnotatedPortletClassExtractor_TestCase {


    private AnnotatedPortletClassExtractor extractor;

    @Mock
    private JavaProjectBuilder projectBuilder;

    @Mock
    private JavaClass portletClass;

    @Mock
    private JavaAnnotation portletAnnotation;

    @Mock
    private JavaClass portletAnnotationClass;

    @Before
    public void setUp() throws Exception {
        when(projectBuilder.getClasses()).thenReturn(Collections.singleton(portletClass));
        when(portletClass.getAnnotations()).thenReturn(Arrays.asList(portletAnnotation));
        when(portletAnnotation.getType()).thenReturn(portletAnnotationClass);
        when(portletAnnotationClass.getCanonicalName()).thenReturn(Portlet.class.getName());
        extractor = new AnnotatedPortletClassExtractor(projectBuilder);
    }

    @Test
    public void shouldFindPortletClass() {
        Set<AnnotatedPortletClass> clses = extractor.findAnnotatedPortletClasses();
        assertThat(clses.size(), is(1));
        AnnotatedPortletClass result = clses.iterator().next();
        assertThat(result.getJavaClass(), is(portletClass));
        assertThat(result.getJavaAnnotation(), is(portletAnnotation));
    }
}
