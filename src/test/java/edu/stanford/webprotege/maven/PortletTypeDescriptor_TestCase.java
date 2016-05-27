
package edu.stanford.webprotege.maven;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.NullPointerException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(MockitoJUnitRunner.class)
public class PortletTypeDescriptor_TestCase {

    private PortletTypeDescriptor portletTypeDescriptor;
    private String canonicalClassName = "The canonicalClassName";
    private String simpleName = "The simpleName";
    private String packageName = "The packageName";
    private String id = "The id";
    private String title = "The \\u000Atitle";
    private String tooltip = "The \\u000tooltip";

    @Before
    public void setUp()
    {
        portletTypeDescriptor = new PortletTypeDescriptor(canonicalClassName, simpleName, packageName, id, title, tooltip);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_canonicalClassName_IsNull() {
        new PortletTypeDescriptor(null, simpleName, packageName, id, title, tooltip);
    }

    @Test
    public void shouldReturnSupplied_canonicalClassName() {
        assertThat(portletTypeDescriptor.getCanonicalClassName(), is(this.canonicalClassName));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_simpleName_IsNull() {
        new PortletTypeDescriptor(canonicalClassName, null, packageName, id, title, tooltip);
    }

    @Test
    public void shouldReturnSupplied_simpleName() {
        assertThat(portletTypeDescriptor.getSimpleName(), is(this.simpleName));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_packageName_IsNull() {
        new PortletTypeDescriptor(canonicalClassName, simpleName, null, id, title, tooltip);
    }

    @Test
    public void shouldReturnSupplied_packageName() {
        assertThat(portletTypeDescriptor.getPackageName(), is(this.packageName));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_id_IsNull() {
        new PortletTypeDescriptor(canonicalClassName, simpleName, packageName, null, title, tooltip);
    }

    @Test
    public void shouldReturnSupplied_id() {
        assertThat(portletTypeDescriptor.getId(), is(this.id));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_title_IsNull() {
        new PortletTypeDescriptor(canonicalClassName, simpleName, packageName, id, null, tooltip);
    }

    @Test
    public void shouldReturnSupplied_title() {
        assertThat(portletTypeDescriptor.getTitle(), is(this.title));
    }

    public void shouldReturnEscaped_title() {
        assertThat(portletTypeDescriptor.getEscapedTitle(), is("The \ntitle"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_tooltip_IsNull() {
        new PortletTypeDescriptor(canonicalClassName, simpleName, packageName, id, title, null);
    }

    @Test
    public void shouldReturnSupplied_tooltip() {
        assertThat(portletTypeDescriptor.getTooltip(), is(this.tooltip));
    }

    public void shouldReturnEscapted_tooltip() {
        assertThat(portletTypeDescriptor.getTooltip(), is("The \ntooltip"));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(portletTypeDescriptor, is(portletTypeDescriptor));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(portletTypeDescriptor.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToOther() {
        assertThat(portletTypeDescriptor, is(new PortletTypeDescriptor(canonicalClassName, simpleName, packageName, id, title, tooltip)));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_canonicalClassName() {
        assertThat(portletTypeDescriptor, is(not(new PortletTypeDescriptor("String-f4b97d14-3b29-4a11-b9a5-7f9a093d65d6", simpleName, packageName, id, title, tooltip))));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_simpleName() {
        assertThat(portletTypeDescriptor, is(not(new PortletTypeDescriptor(canonicalClassName, "String-0b0493f7-ab6f-4481-b2e1-6f4864f12a57", packageName, id, title, tooltip))));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_packageName() {
        assertThat(portletTypeDescriptor, is(not(new PortletTypeDescriptor(canonicalClassName, simpleName, "String-736e9d0b-87d5-4285-b16a-a8b95222cf4f", id, title, tooltip))));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_id() {
        assertThat(portletTypeDescriptor, is(not(new PortletTypeDescriptor(canonicalClassName, simpleName, packageName, "String-c94e7943-c907-475d-b335-9be5a4b95c37", title, tooltip))));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_title() {
        assertThat(portletTypeDescriptor, is(not(new PortletTypeDescriptor(canonicalClassName, simpleName, packageName, id, "String-83ec3fa8-d2e7-4f46-b315-8def68d74c5e", tooltip))));
    }

    @Test
    public void shouldNotBeEqualToOtherThatHasDifferent_tooltip() {
        assertThat(portletTypeDescriptor, is(not(new PortletTypeDescriptor(canonicalClassName, simpleName, packageName, id, title, "String-c5e6f44a-8655-48d0-9e05-3de2515f87aa"))));
    }

    @Test
    public void shouldBeEqualToOtherHashCode() {
        assertThat(portletTypeDescriptor.hashCode(), is(new PortletTypeDescriptor(canonicalClassName, simpleName, packageName, id, title, tooltip).hashCode()));
    }

    @Test
    public void shouldImplementToString() {
        assertThat(portletTypeDescriptor.toString(), Matchers.startsWith("PortletTypeDescriptor"));
    }


}
