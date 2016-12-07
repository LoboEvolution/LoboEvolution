/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.util.io;

import java.io.File;

/**
 * The Class Files.
 */
public class Files {
    /**
     * Instantiates a new files.
     */
    private Files() {
    }
    
    /**
     * Guesses the right content-type for a local file, and includes a charset
     * if appropriate.
     *
     * @param file
     *            the file
     * @return the content type
     */
    public static String getContentType(final File file) {
        // Not very complete at the moment :)
        String name = file.getName();
        int dotIdx = name.lastIndexOf('.');
        String extension = dotIdx == -1 ? null : name.substring(dotIdx + 1);
        if ("txt".equalsIgnoreCase(extension)) {
            return "text/plain; charset=\""
                    + System.getProperty("file.encoding") + "\"";
        }
        if ("html".equalsIgnoreCase(extension)
                || "htm".equalsIgnoreCase(extension)) {
            return "text/html; charset=\"" + System.getProperty("file.encoding")
                    + "\"";
        } else {
            return "application/octet-stream";
        }
    }
}
