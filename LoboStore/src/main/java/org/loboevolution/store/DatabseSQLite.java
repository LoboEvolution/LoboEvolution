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
     * <p>getDatabaseDirectory.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getDatabaseDirectory() {
        try {
            final Path path = Paths.get(System.getProperty("user.home"), "lobo", "store", LOBO_DB);
            if (!storeExist()) Files.createFile(path);
            return JDBC_SQLITE + path;
        } catch (final Exception e) {
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
