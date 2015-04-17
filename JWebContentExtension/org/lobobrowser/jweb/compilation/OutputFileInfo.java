/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.jweb.compilation;

/**
 * The Class OutputFileInfo.
 */
public class OutputFileInfo implements java.io.Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 22574505000001100L;

    /** The file name. */
    public final String fileName;

    /** The bytes. */
    public final byte[] bytes;

    /**
     * Instantiates a new output file info.
     *
     * @param fileName
     *            the file name
     * @param bytes
     *            the bytes
     */
    public OutputFileInfo(final String fileName, final byte[] bytes) {
        super();
        this.fileName = fileName;
        this.bytes = bytes;
    }
}
