package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.JavaProjectBuilder;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toSet;

public class AnnotatedEntityCardPresenterClassExtractor {

    private final JavaProjectBuilder projectBuilder;

    public AnnotatedEntityCardPresenterClassExtractor(JavaProjectBuilder projectBuilder) {
        this.projectBuilder = checkNotNull(projectBuilder);
    }

    public Set<AnnotatedEntityCardPresenterClass> findAnnotatedEntityCardPresenterClasses() {
        return projectBuilder.getClasses().stream()
                .flatMap(
                        c -> c.getAnnotations().stream()
                                .filter(Annotations::isEntityCardPresenterAnnotation)
                                .map(a -> new AnnotatedEntityCardPresenterClass(c, a))
                )
                .collect(toSet());
    }
}
