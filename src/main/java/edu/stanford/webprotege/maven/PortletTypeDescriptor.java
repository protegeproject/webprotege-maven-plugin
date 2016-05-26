package edu.stanford.webprotege.maven;

import com.google.common.base.Objects;

import java.util.Comparator;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 26 Apr 16
 */
public class PortletTypeDescriptor implements Comparable<PortletTypeDescriptor> {

    private final String packageName;

    private final String canonicalClassName;

    private final String simpleName;

    private final String id;

    private final String title;

    private final String tooltip;

    /**
     * Constructs a PortletTypeDescriptor that describes a type of portlet
     * @param canonicalClassName The portlet class name.  Not {@code null}.
     * @param simpleName The simple class name of the portlet.  Not {@code null}.
     * @param packageName The package name of the portlet.  Not {@code null}.
     * @param id The porlet id. Not {@code null}.
     * @param title The portlet title.  Not {@code null}.
     * @param tooltip The tooltip for the portlet.  Not {@code null}.
     */
    public PortletTypeDescriptor(String canonicalClassName,
                                 String simpleName,
                                 String packageName,
                                 String id,
                                 String title,
                                 String tooltip) {
        this.canonicalClassName = checkNotNull(canonicalClassName);
        this.simpleName = checkNotNull(simpleName);
        this.id = checkNotNull(id);
        checkArgument(!id.startsWith("\"") && !id.endsWith("\""));
        this.title = checkNotNull(title);
        checkArgument(!title.startsWith("\"") && !title.endsWith("\""));
        this.packageName = checkNotNull(packageName);
        this.tooltip = checkNotNull(tooltip);
        checkArgument(!tooltip.startsWith("\"") && !tooltip.endsWith("\""));
    }

    /**
     * Gets the simple name of the portlet class.
     * @return A string representing the simple name.  Not {@code null}.
     */
    public String getSimpleName() {
        return simpleName;
    }

    /**
     * Gets the package name of the portlet class.
     * @return A string representing the portlet class package name.  Not {@code null}.
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Gets the canonical class name of the portlet.
     * @return The canonical class name of the portlet.  Not {@code null}.
     */
    public String getCanonicalClassName() {
        return canonicalClassName;
    }

    /**
     * Gets the id of the portlet.
     * @return The portlet id.  Not {@code null}.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the title of the portlet.
     * @return The portlet title.  Not {@code null}.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the tooltip of the portlet.
     * @return The portlet tooltip.  Not {@code null}.
     */
    public String getTooltip() {
        return tooltip;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(canonicalClassName, id, title);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PortletTypeDescriptor)) {
            return false;
        }
        PortletTypeDescriptor other = (PortletTypeDescriptor) obj;
        return this.canonicalClassName.equals(other.canonicalClassName)
                && this.simpleName.equals(other.simpleName)
                && this.packageName.equals(other.packageName)
                && this.id.equals(other.id)
                && this.title.equals(other.title)
                && this.tooltip.equals(other.tooltip);
    }


    @Override
    public String toString() {
        return toStringHelper("PortletTypeDescriptor")
                .addValue(id)
                .addValue(title)
                .addValue(canonicalClassName)
                .addValue(packageName)
                .addValue(simpleName)
                .toString();
    }

    @Override
    public int compareTo(PortletTypeDescriptor o) {
        return this.canonicalClassName.compareTo(o.canonicalClassName);
    }
}
