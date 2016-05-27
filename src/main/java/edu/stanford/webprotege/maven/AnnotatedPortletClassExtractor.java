package edu.stanford.webprotege.maven;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import edu.stanford.webprotege.shared.annotations.Portlet;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

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
        Set<AnnotatedPortletClass> result = new HashSet<>();
        for(JavaClass cls : projectBuilder.getClasses()) {
            for(JavaAnnotation anno : cls.getAnnotations()) {
                if(anno.getType().getCanonicalName().equals(Portlet.class.getName())) {
                    result.add(new AnnotatedPortletClass(cls, anno));
                }
            }
        }
        return result;
    }
}
