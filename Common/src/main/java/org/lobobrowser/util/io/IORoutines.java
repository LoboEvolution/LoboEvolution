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
/*
 * Created on Mar 19, 2005
 */
package org.lobobrowser.util.io;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class IORoutines.
 *
 * @author J. H. S.
 */
public class IORoutines {
    /** The Constant LINE_BREAK_BYTES. */
    public static final byte[] LINE_BREAK_BYTES = { (byte) 13, (byte) 10 };
    
    /**
     * Load as text.
     *
     * @param in
     *            the in
     * @param encoding
     *            the encoding
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String loadAsText(final InputStream in, final String encoding)
            throws IOException {
        return loadAsText(in, encoding, 4096);
    }
    
    /**
     * Load as text.
     *
     * @param in
     *            the in
     * @param encoding
     *            the encoding
     * @param bufferSize
     *            the buffer size
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String loadAsText(final InputStream in, final String encoding,
            final int bufferSize) throws IOException {
        InputStreamReader reader = new InputStreamReader(in, encoding);
        char[] buffer = new char[bufferSize];
        int offset = 0;
        for (;;) {
            int remain = buffer.length - offset;
            if (remain <= 0) {
                char[] newBuffer = new char[buffer.length * 2];
                System.arraycopy(buffer, 0, newBuffer, 0, offset);
                buffer = newBuffer;
                remain = buffer.length - offset;
            }
            int numRead = reader.read(buffer, offset, remain);
            if (numRead == -1) {
                break;
            }
            offset += numRead;
        }
        return new String(buffer, 0, offset);
    }
    
    /**
     * Load.
     *
     * @param file
     *            the file
     * @return the byte[]
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static byte[] load(final File file) throws IOException {
        long fileLength = file.length();
        if (fileLength > Integer.MAX_VALUE) {
            throw new IOException("File '" + file.getName() + "' too big");
        }
        InputStream in = new FileInputStream(file);
        try {
            return loadExact(in, (int) fileLength);
        } finally {
            in.close();
        }
    }
    
    /**
     * Load.
     *
     * @param in
     *            the in
     * @return the byte[]
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static byte[] load(final InputStream in) throws IOException {
        return load(in, 4096);
    }
    
    /**
     * Load.
     *
     * @param in
     *            the in
     * @param initialBufferSize
     *            the initial buffer size
     * @return the byte[]
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static byte[] load(final InputStream in, int initialBufferSize)
            throws IOException {
        if (initialBufferSize == 0) {
            initialBufferSize = 1;
        }
        byte[] buffer = new byte[initialBufferSize];
        int offset = 0;
        for (;;) {
            int remain = buffer.length - offset;
            if (remain <= 0) {
                int newSize = buffer.length * 2;
                byte[] newBuffer = new byte[newSize];
                System.arraycopy(buffer, 0, newBuffer, 0, offset);
                buffer = newBuffer;
                remain = buffer.length - offset;
            }
            int numRead = in.read(buffer, offset, remain);
            if (numRead == -1) {
                break;
            }
            offset += numRead;
        }
        if (offset < buffer.length) {
            byte[] newBuffer = new byte[offset];
            System.arraycopy(buffer, 0, newBuffer, 0, offset);
            buffer = newBuffer;
        }
        return buffer;
    }
    
    /**
     * Load exact.
     *
     * @param in
     *            the in
     * @param length
     *            the length
     * @return the byte[]
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static byte[] loadExact(final InputStream in, final int length)
            throws IOException {
        byte[] buffer = new byte[length];
        int offset = 0;
        for (;;) {
            int remain = length - offset;
            if (remain <= 0) {
                break;
            }
            int numRead = in.read(buffer, offset, remain);
            if (numRead == -1) {
                throw new IOException(
                        "Reached EOF, read " + offset + " expecting " + length);
            }
            offset += numRead;
        }
        return buffer;
    }
    
    /**
     * Equal content.
     *
     * @param file
     *            the file
     * @param content
     *            the content
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static boolean equalContent(final File file, final byte[] content)
            throws IOException {
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            throw new IOException("File '" + file + "' too big");
        }
        InputStream in = new FileInputStream(file);
        try {
            byte[] fileContent = loadExact(in, (int) length);
            return java.util.Arrays.equals(content, fileContent);
        } finally {
            in.close();
        }
    }
    
    /**
     * Save.
     *
     * @param file
     *            the file
     * @param content
     *            the content
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void save(final File file, final byte[] content) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        try {
            out.write(content);
        } finally {
            out.close();
        }
    }
    
    /**
     * Reads line without buffering.
     *
     * @param in
     *            the in
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String readLine(final InputStream in) throws IOException {
        int b;
        StringBuffer sb = null;
        OUTER: while ((b = in.read()) != -1) {
            if (sb == null) {
                sb = new StringBuffer();
            }
            switch (b) {
                case (byte) '\n':
                    break OUTER;
                case (byte) '\r':
                    break;
                default:
                    sb.append((char) b);
                    break;
            }
        }
        return sb == null ? null : sb.toString();
    }
    
    /**
     * Touch.
     *
     * @param file
     *            the file
     */
    public static void touch(final File file) {
        file.setLastModified(System.currentTimeMillis());
    }
    
    /**
     * Save strings.
     *
     * @param file
     *            the file
     * @param list
     *            the list
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void saveStrings(final File file, final Collection list)
            throws IOException {
        BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(file));
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(bout,StandardCharsets.UTF_8));
            Iterator i = list.iterator();
            while (i.hasNext()) {
                String text = (String) i.next();
                writer.println(text);
            }
            writer.flush();
            writer.close();
        } finally {
            bout.close();
        }
    }
    
    /**
     * Load strings.
     *
     * @param file
     *            the file
     * @return the list
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static List<String> loadStrings(final File file) throws IOException {
        List<String> list = new LinkedList<String>();
        InputStream in = new FileInputStream(file);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            return list;
        } finally {
            in.close();
        }
    }
}
