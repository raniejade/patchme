package io.polymorphicpanda.patchme.basic;

import java.nio.file.Path;

import io.polymorphicpanda.patchme.Patch;

/**
 * Essentially an archive, that will be extracted on install.
 *
 * @author Ranie Jade Ramiso
 */
public class BasicPatch implements Patch {
    private final Path root;

    public BasicPatch(Path root) {
        this.root = root;
    }

    public Path getRoot() {
        return root;
    }
}
