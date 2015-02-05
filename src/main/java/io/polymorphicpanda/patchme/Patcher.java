package io.polymorphicpanda.patchme;

import java.nio.file.FileSystem;

/**
 * @author Ranie Jade Ramiso
 */
public abstract class Patcher<T extends Patch> {
    private final FileSystem fileSystem;

    protected Patcher(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public abstract void apply(String workingDir, T patch) throws PatchException;

    public final FileSystem getFileSystem() {
        return fileSystem;
    }

    public class PatchException extends Exception {
        public PatchException(String message) {
            super(message);
        }

        public PatchException(String message, Throwable cause) {
            super(message, cause);
        }

        public PatchException(Throwable cause) {
            super(cause);
        }
    }
}
