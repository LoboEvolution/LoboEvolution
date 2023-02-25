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

package org.loboevolution.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * <p>BytesUtilities class.</p>
 */
public class BytesUtilities {

    private static final int DEFAULT_BLOCK_SIZE = 8192;

    /**
     * <p>readStream.</p>
     *
     * @param inputStream a {@link java.io.InputStream} object.
     */
    public static ByteBuffer readStream(InputStream inputStream) {
        ByteBuffer buffer = null;
        if (inputStream == null) {
            return buffer;
        }

        try {
            byte[] bytes = inputStreamToByteArray(inputStream);
            buffer = ByteBuffer.wrap(bytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return buffer;
    }

    private static byte[] inputStreamToByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[DEFAULT_BLOCK_SIZE];
        int n;
        while ((n = in.read(buffer)) > 0) {
            out.write(buffer, 0, n);
        }
        out.flush();
    }
}
