package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.JavaProjectBuilder;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toSet;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 27 May 16
 */
public class AnnotatedPortletClassExtractor {

    private final JavaProjectBuilder projectBuilder;

    public AnnotatedPortletClassExtractor(JavaProjectBuilder projectBuilder) {
        this.projectBuilder = checkNotNull(projectBuilder);
    }

    public Set<AnnotatedPortletClass> findAnnotatedPortletClasses() {
        return projectBuilder.getClasses().stream()
                .flatMap(
                        c -> c.getAnnotations().stream()
                                .filter(Annotations::isPortletAnnotation)
                                .map(a -> new AnnotatedPortletClass(c, a))
                )
                .collect(toSet());
    }
}
