/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

    public static String PATH_WELCOME = "welcome";

    /**
     * <p>createWallpapersDirectorys.</p>
     * @param path a {@link java.lang.String} object.
     */
    public static void createWallpapersDirectory(String path) throws Exception {
        Path pathDir = Paths.get(System.getProperty("user.home"), "lobo", path);
        if(!Files.exists(pathDir)) Files.createDirectories(pathDir);
    }

    /**
     * <p>createWallpapersFile.</p>
     * @param inputStream a {@link java.io.InputStream} object.
     * @param path a {@link java.lang.String} object.
     * @param name a {@link java.lang.String} object.
     */
    public static void createWallpapersFile(InputStream inputStream, String path, String name) throws Exception {
        String filename = name.substring(name.lastIndexOf("/") +1, name.length());
        Path pathFile = Files.createFile(Paths.get(System.getProperty("user.home"), "lobo", path, filename));
        org.loboevolution.common.Files.copyInputStreamToFile(inputStream, pathFile.toFile());
    }

    /**
     * <p>getResourceFolderFiles.</p>
     */
    public static File[] getResourceFolderFiles() {
        final Path dir = Paths.get(System.getProperty("user.home"), "lobo", PATH_WELCOME);
        List<File> list = new ArrayList<>();
        try (Stream<Path> paths = Files.list(dir)) {
            paths.forEach(path -> list.add(path.toFile()));
            return list.toArray(new File[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>getResourceFile.</p>
     * @param fileName a {@link java.lang.String} object.
     */
    public static URL getResourceFile(String fileName)  {
        try {
            final Path dir = Paths.get(System.getProperty("user.home"), "lobo", PATH_IMAGE, fileName);
            return dir.toUri().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
