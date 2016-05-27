package edu.stanford.webprotege.maven;

import java.io.IOException;

/**
* Matthew Horridge
* Stanford Center for Biomedical Informatics Research
* 27 May 16
*/
public interface SourceWriter {

    /**
     * Write the specified source to the specified class in the specified package.
     * @param packageName The package.  Not {@code null}.
     * @param simpleClassName The simple class name. Not {@code null}.
     * @param source The source to write. Not {@code null}.
     * @throws IOException
     */
    void writeSource(String packageName, String simpleClassName, String source) throws IOException;
}
