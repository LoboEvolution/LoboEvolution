/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DesktopConfig {

    public static String PATH_IMAGE = "image";

    public static String PATH_PDF_IMAGE = "pdfview";

    public static String PATH_WELCOME = "welcome";

    /**
     * <p>createDirectory.</p>
     * @param path a {@link java.lang.String} object.
     */
    public void createDirectory(final String path) throws Exception {
        final Path pathDir = Paths.get(System.getProperty("user.home"), "lobo", path);
        if(!Files.exists(pathDir)) Files.createDirectories(pathDir);
    }

    /**
     * <p>createDatabaseDirectory.</p>
     */
    public void createDatabaseDirectory() throws Exception {
        final Path path = Paths.get(System.getProperty("user.home"), "lobo", "store");
        if(!Files.exists(path)) Files.createDirectories(path);
    }

    /**
     * <p>createFile.</p>
     * @param inputStream a {@link java.io.InputStream} object.
     * @param path a {@link java.lang.String} object.
     * @param name a {@link java.lang.String} object.
     */
    public void createFile(final InputStream inputStream, final String path, final String name) throws Exception {
        final String filename = name.substring(name.lastIndexOf("/") +1, name.length());
        final Path pathFile = Files.createFile(Paths.get(System.getProperty("user.home"), "lobo", path, filename));
        org.loboevolution.common.Files.copyInputStreamToFile(inputStream, pathFile.toFile());
    }

    /**
     * <p>getResourceFolderFiles.</p>
     */
    public static File[] getResourceFolderFiles() {
        final Path dir = Paths.get(System.getProperty("user.home"), "lobo", PATH_WELCOME);
        final List<File> list = new ArrayList<>();
        try (final Stream<Path> paths = Files.list(dir)) {
            paths.forEach(path -> list.add(path.toFile()));
            return list.toArray(new File[0]);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>getResourceFile.</p>
     * @param fileName a {@link java.lang.String} object.
     * @param path a {@link java.lang.String} object.
     */
    public static URL getResourceFile(final String fileName, final String path)  {
        try {
            final Path dir = Paths.get(System.getProperty("user.home"), "lobo", path, fileName);
            return dir.toUri().toURL();
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
