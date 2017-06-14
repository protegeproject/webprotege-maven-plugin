package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.JavaProjectBuilder;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toSet;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 12 Jun 2017
 */
public class AnnotatedPortletModuleClassExtractor {

    private final JavaProjectBuilder projectBuilder;

    public AnnotatedPortletModuleClassExtractor(JavaProjectBuilder projectBuilder) {
        this.projectBuilder = checkNotNull(projectBuilder);
    }

    public Set<PortletModuleDescriptor> findAnnotatedProjectModulePlugins() {
        return projectBuilder.getClasses().stream()
                             .flatMap(
                                     c -> c.getAnnotations().stream()
                                           .filter(Annotations::isProjectModuleAnnotation)
                                           .map(a -> new PortletModuleDescriptor(c.getFullyQualifiedName()))
                             )
                             .collect(toSet());
    }
}
