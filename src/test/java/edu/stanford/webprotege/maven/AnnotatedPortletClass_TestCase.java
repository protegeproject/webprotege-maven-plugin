
package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.NullPointerException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class AnnotatedPortletClass_TestCase {

    private AnnotatedPortletClass annotatedPortletClass;

    @Mock
    private JavaClass javaClass;

    @Mock
    private JavaAnnotation javaAnnotation;

    @Before
    public void setUp() {
        annotatedPortletClass = new AnnotatedPortletClass(javaClass, javaAnnotation);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_javaClass_IsNull() {
        new AnnotatedPortletClass(null, javaAnnotation);
    }

    @Test
    public void shouldReturnSupplied_javaClass() {
        assertThat(annotatedPortletClass.getJavaClass(), is(this.javaClass));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_javaAnnotation_IsNull() {
        new AnnotatedPortletClass(javaClass, null);
    }

    @Test
    public void shouldReturnSupplied_javaAnnotation() {
        assertThat(annotatedPortletClass.getJavaAnnotation(), is(this.javaAnnotation));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(annotatedPortletClass, is(annotatedPortletClass));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(annotatedPortletClass.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(annotatedPortletClass, is(new AnnotatedPortletClass(javaClass, javaAnnotation)));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_javaClass() {
        assertThat(annotatedPortletClass, is(not(new AnnotatedPortletClass(mock(JavaClass.class), javaAnnotation))));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_javaAnnotation() {
        assertThat(annotatedPortletClass, is(not(new AnnotatedPortletClass(javaClass, mock(JavaAnnotation.class)))));
    }

    @Test
    public void shouldBeEqualToOtherHashCode() {
        assertThat(annotatedPortletClass.hashCode(), is(new AnnotatedPortletClass(javaClass, javaAnnotation).hashCode()));
    }

    @Test
    public void shouldImplementToString() {
        assertThat(annotatedPortletClass.toString(), Matchers.startsWith("AnnotatedPortletClass"));
    }

}
