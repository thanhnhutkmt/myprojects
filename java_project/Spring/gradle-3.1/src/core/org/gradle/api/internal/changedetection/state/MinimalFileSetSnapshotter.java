/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.changedetection.state;

import org.gradle.api.UncheckedIOException;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.file.FileVisitDetails;
import org.gradle.api.file.RelativePath;
import org.gradle.api.internal.cache.StringInterner;
import org.gradle.api.internal.file.DefaultFileVisitDetails;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.cache.CacheAccess;
import org.gradle.internal.nativeplatform.filesystem.FileSystem;
import org.gradle.internal.serialize.SerializerRegistry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * A minimal file set snapshotter is different from the default file collection snapshotter in that it creates a snapshot for every file
 * in the input FileCollection without visiting the files on disk.  This allows files that do not exist yet to be considered part of the
 * FileCollectionSnapshot without that information being lost.
 */
public class MinimalFileSetSnapshotter extends AbstractFileCollectionSnapshotter {
    private final FileSystem fileSystem;

    public MinimalFileSetSnapshotter(FileSnapshotter snapshotter, CacheAccess cacheAccess, StringInterner stringInterner, FileResolver fileResolver, FileSystem fileSystem) {
        super(snapshotter, cacheAccess, stringInterner, fileResolver);
        this.fileSystem = fileSystem;
    }

    @Override
    protected void visitFiles(FileCollection input, List<FileTreeElement> fileTreeElements, List<FileTreeElement> missingFiles) {
        for (File file : input.getFiles()) {
            if (file.exists()) {
                fileTreeElements.add(new DefaultFileVisitDetails(file, fileSystem, fileSystem));
            } else {
                missingFiles.add(new MissingFileVisitDetails(file));
            }
        }
    }

    @Override
    public void registerSerializers(SerializerRegistry registry) {
    }

    private static class MissingFileVisitDetails implements FileVisitDetails {
        private final File file;
        private final RelativePath relativePath;

        public MissingFileVisitDetails(File file) {
            this.file = file;
            this.relativePath = new RelativePath(true, file.getName());
        }

        @Override
        public File getFile() {
            return file;
        }

        @Override
        public boolean isDirectory() {
            return false;
        }

        @Override
        public String getName() {
            return file.getName();
        }

        @Override
        public String getPath() {
            return file.getName();
        }

        @Override
        public RelativePath getRelativePath() {
            return relativePath;
        }

        @Override
        public int getMode() {
            return 0;
        }

        @Override
        public long getLastModified() {
            return 0L;
        }

        @Override
        public long getSize() {
            return 0L;
        }

        @Override
        public InputStream open() {
            throw new UncheckedIOException(new FileNotFoundException(file.getAbsolutePath()));
        }

        @Override
        public void copyTo(OutputStream output) {
            throw new UncheckedIOException(new FileNotFoundException(file.getAbsolutePath()));
        }

        @Override
        public boolean copyTo(File target) {
            throw new UncheckedIOException(new FileNotFoundException(file.getAbsolutePath()));
        }

        @Override
        public void stopVisiting() {
            // Ignore
        }
    }
}
