package edu.stanford.webprotege.maven;

import java.util.Objects;

public class EntityCardPresenterClassDescriptor implements Comparable<EntityCardPresenterClassDescriptor> {

    private final String packageName;

    private final String canonicalClassName;

    private final String simpleName;

    private final String cardContentId;

    private final String title;

    public EntityCardPresenterClassDescriptor(String packageName,
                                              String canonicalClassName,
                                              String simpleName,
                                              String cardContentId,
                                              String title) {
        this.packageName = Objects.requireNonNull(packageName);
        this.canonicalClassName = Objects.requireNonNull(canonicalClassName);
        this.simpleName = Objects.requireNonNull(simpleName);
        this.cardContentId = Objects.requireNonNull(cardContentId);
        this.title = Objects.requireNonNull(title);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getCanonicalClassName() {
        return canonicalClassName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public String getCardContentId() {
        return cardContentId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(EntityCardPresenterClassDescriptor o) {
        return cardContentId.compareTo(o.cardContentId);
    }

    @Override
    public String toString() {
        return "EntityCardPresenterClassDescriptor{" +
                "packageName='" + packageName + '\'' +
                ", canonicalClassName='" + canonicalClassName + '\'' +
                ", simpleName='" + simpleName + '\'' +
                ", cardContentId='" + cardContentId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
