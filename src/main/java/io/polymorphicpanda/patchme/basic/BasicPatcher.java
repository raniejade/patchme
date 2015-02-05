package io.polymorphicpanda.patchme.basic;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import io.polymorphicpanda.patchme.Patcher;

/**
 * @author Ranie Jade Ramiso
 */
public class BasicPatcher extends Patcher<BasicPatch> {
    public BasicPatcher(FileSystem fileSystem) {
        super(fileSystem);
    }

    @Override
    public void apply(String workingDir, BasicPatch patch) throws PatchException {
        apply(getFileSystem().getPath(workingDir), patch);
    }

    protected void apply(Path workingDir, BasicPatch patch) throws PatchException {
        try {
            // delete everything from working dir
            Files.walkFileTree(workingDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return super.postVisitDirectory(dir, exc);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return super.visitFile(file, attrs);
                }
            });

            // copy the contents of archive
            Files.walkFileTree(patch.getRoot(), new SimpleFileVisitor<Path>() {
                private Path currentDirectory = workingDir;
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    currentDirectory = Files.createDirectory(currentDirectory.relativize(dir.getFileName()));
                    return super.preVisitDirectory(dir, attrs);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, currentDirectory.relativize(file.getFileName()));
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            throw new PatchException(e);
        }
    }
}
