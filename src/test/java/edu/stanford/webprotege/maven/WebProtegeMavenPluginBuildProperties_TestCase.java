package edu.stanford.webprotege.maven;

import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2018-11-09
 */
@ExtendWith(MockitoExtension.class)
public class WebProtegeMavenPluginBuildProperties_TestCase {

    private static final long TIMESTAMP = 3000L;

    @TempDir
    public Path tempDirectory;

    @BeforeEach
    public void setUp() throws IOException {
    }

    @Test
    public void shouldProvideDefaultTimestamp() {
        WebProtegeMavenPluginBuildProperties props = WebProtegeMavenPluginBuildProperties.get(tempDirectory, mock(Log.class));
        assertThat(props.getTimestamp(), is(0L));
    }

    @Test
    public void shouldSaveAndLoadTimestamp() {
        WebProtegeMavenPluginBuildProperties props = WebProtegeMavenPluginBuildProperties.get(tempDirectory, mock(Log.class));
        props.setTimestamp(TIMESTAMP);
        WebProtegeMavenPluginBuildProperties propsB = WebProtegeMavenPluginBuildProperties.get(tempDirectory, mock(Log.class));
        long loadedTimestamp = propsB.getTimestamp();
        assertThat(loadedTimestamp, is(TIMESTAMP));
    }
}
