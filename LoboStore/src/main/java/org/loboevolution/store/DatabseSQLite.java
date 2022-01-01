/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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

package org.loboevolution.store;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>DatabseSQLite class.</p>
 */
public class DatabseSQLite {

    /** The Constant SETTINGS_DIR. */
    public static final String JDBC_SQLITE = "jdbc:sqlite:";

    /** The Constant LOBO_DB. */
    private static final String LOBO_DB = "LOBOEVOLUTION_STORAGE.sqlite";

    /**
     * <p>createDatabaseDirectory.</p>
     */
    public static void createDatabaseDirectory() throws Exception {
        Path path = Paths.get(System.getProperty("user.home"), "lobo", "store");
        if(!Files.exists(path)) Files.createDirectories(path);
    }

    /**
     * <p>getCacheStore.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getCacheStore() {
        final Path path = Paths.get(System.getProperty("user.home"), ".lobo", "cache");
        return path.toString();
    }

    /**
     * <p>getDatabaseDirectory.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getDatabaseDirectory() {
        try {
            Path path = Paths.get(System.getProperty("user.home"), "lobo", "store", LOBO_DB);
            if (!storeExist()) Files.createFile(path);
            return JDBC_SQLITE + path;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * <p>storeExist.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public static boolean storeExist(){
        return Files.exists(Paths.get(System.getProperty("user.home"), "lobo", "store", LOBO_DB));
    }
}
