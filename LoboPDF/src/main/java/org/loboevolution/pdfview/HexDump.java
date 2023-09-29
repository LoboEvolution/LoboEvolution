/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
package org.loboevolution.pdfview;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>HexDump class.</p>
 */
@Slf4j
public class HexDump {
    /**
     * <p>printData.</p>
     *
     * @param data an array of {@link byte} objects.
     */
    public static void printData(final byte[] data) {
        final char[] parts = new char[17];
        int partsloc = 0;
        for (int i = 0; i < data.length; i++) {
            final int d = (data[i]) & 0xff;
            if (d == 0) {
                parts[partsloc++] = '.';
            } else if (d < 32 || d >= 127) {
                parts[partsloc++] = '?';
            } else {
                parts[partsloc++] = (char) d;
            }
            if (i % 16 == 0) {
                final int start = Integer.toHexString(data.length).length();
                final int end = Integer.toHexString(i).length();

                for (int j = start; j > end; j--) {
                	log.info("0");
                }
                log.info("{}", Integer.toHexString(i));
            }
            if (d < 16) {
                log.info("0 {} ", Integer.toHexString(d));
            } else {
                log.info("{} ", Integer.toHexString(d));
            }
            if ((i & 15) == 15 || i == data.length - 1) {
                log.info("      {} ", String.valueOf(parts));
                partsloc = 0;
            } else if ((i & 7) == 7) {
            	log.info("  ");
                parts[partsloc++] = ' ';
            } else if ((i & 1) == 1) {
            	log.info(" ");
            }
        }
    }
}
