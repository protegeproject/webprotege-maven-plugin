package edu.stanford.webprotege.maven;

import com.google.common.base.Charsets;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 27 May 16
 */
public class MavenSourceWriter implements SourceWriter {

    private static final String TARGET_DIR_NAME = "target";

    private static final String GENERATED_SOURCES_DIR_NAME = "generated-sources";

    private final MavenProject mavenProject;

    private final Log log;

    public MavenSourceWriter(MavenProject mavenProject, Log log) {
        this.mavenProject = mavenProject;
        this.log = log;
    }

    /**
     * Write the specified source to the specified class in the specified package.
     *
     * @param packageName     The package.  Not {@code null}.
     * @param simpleClassName The simple class name. Not {@code null}.
     * @param source          The source to write. Not {@code null}.
     * @throws java.io.IOException
     */
    @Override
    public void writeSource(String packageName, String simpleClassName, String source) throws IOException {
        String packagePathName = packageName.replace(".", "/");
        Path outputDirectory = getOutputDirectory();
        Path packagePath = outputDirectory.resolve(packagePathName);
        Files.createDirectories(packagePath);
        Path classFilePath = packagePath.resolve(simpleClassName + ".java");
        try(OutputStream os = Files.newOutputStream(classFilePath)) {
            os.write(source.getBytes(Charsets.UTF_8));
        }
        String extraSourceRoot = outputDirectory.toAbsolutePath().toString();
        if(!mavenProject.getCompileSourceRoots().contains(extraSourceRoot)) {
            log.info("[WebProtegeMojo] Adding source root for generated sources: " + extraSourceRoot);
            mavenProject.addCompileSourceRoot(extraSourceRoot);
        }
    }

    private Path getOutputDirectory() {
        File file = mavenProject.getBasedir();
        return Paths.get(
                file.getAbsolutePath(),
                TARGET_DIR_NAME,
                GENERATED_SOURCES_DIR_NAME,
                "webprotege");
    }
}
