package edu.stanford.webprotege.maven;

import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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
@RunWith(MockitoJUnitRunner.class)
public class WebProtegeMavenPluginBuildProperties_TestCase {

    private static final long TIMESTAMP = 3000L;

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
    }

    @Test
    public void shouldProvideDefaultTimestamp() {
        Path path = tempDirectory.getRoot().toPath();
        WebProtegeMavenPluginBuildProperties props = WebProtegeMavenPluginBuildProperties.get(path, mock(Log.class));
        assertThat(props.getTimestamp(), is(0L));
    }

    @Test
    public void shouldSaveAndLoadTimestamp() {
        Path path = tempDirectory.getRoot().toPath();
        WebProtegeMavenPluginBuildProperties props = WebProtegeMavenPluginBuildProperties.get(path, mock(Log.class));
        props.setTimestamp(TIMESTAMP);
        WebProtegeMavenPluginBuildProperties propsB = WebProtegeMavenPluginBuildProperties.get(path, mock(Log.class));
        long loadedTimestamp = propsB.getTimestamp();
        assertThat(loadedTimestamp, is(TIMESTAMP));
    }
}
