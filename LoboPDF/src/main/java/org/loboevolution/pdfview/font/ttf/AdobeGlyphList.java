/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.pdfview.font.ttf;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@Slf4j
public final class AdobeGlyphList {

    /**
     * the loader thread we are reading through.
     */
    static Thread glyphLoaderThread = null;
    /**
     * provide a translation from a glyph name to the possible unicode values.
     */
    static private Map<String, int[]> glyphToUnicodes;
    /**
     * provide a translation from a unicode value to a glyph name.
     */
    static private Map<Integer, String> unicodeToGlyph;

    static {
        new AdobeGlyphList();
    }

    /**
     * <p>private constructor to restrict creation to a singleton.</p>
     *
     * <p>We initialize by creating the storage and parsing the glyphlist
     * into the tables.</p>
     */
    private AdobeGlyphList() {
        glyphToUnicodes = new HashMap<>(4500);
        unicodeToGlyph = new HashMap<>(4500);
        glyphLoaderThread = new Thread(new Runnable() {

            @Override
            public void run() {
                int[] codes;
                StringTokenizer codeTokens;
                String glyphName;
                StringTokenizer tokens;
                final ArrayList<String> unicodes = new ArrayList<>();
                final URL resource = getClass().getResource("/org/loboevolution/pdfview/font/ttf/resource/glyphlist.txt");
                try (final InputStream istr = resource.openStream()) {
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(istr));
                    String line = "";
                    while (line != null) {
                        try {
                            unicodes.clear();
                            line = reader.readLine();
                            if (line == null) {
                                break;
                            }
                            line = line.trim();
                            if (!line.isEmpty() && !line.startsWith("#")) {
                                // ignore comment lines
                                tokens = new StringTokenizer(line, ";");
                                glyphName = tokens.nextToken();
                                codeTokens = new StringTokenizer(tokens.nextToken(), " ");
                                while (codeTokens.hasMoreTokens()) {
                                    unicodes.add(codeTokens.nextToken());
                                }
                                codes = new int[unicodes.size()];
                                for (int i = 0; i < unicodes.size(); i++) {
                                    codes[i] = Integer.parseInt(unicodes.get(i), 16);
                                    unicodeToGlyph.put(codes[i], glyphName);
                                }
                                glyphToUnicodes.put(glyphName, codes);
                            }

                        } catch (final IOException ex) {
                            break;
                        }
                    }
                } catch (final IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }, "Adobe Glyph Loader Thread");
        glyphLoaderThread.setDaemon(true);
        glyphLoaderThread.setPriority(Thread.MIN_PRIORITY);
        glyphLoaderThread.start();
    }

    /**
     * translate a glyph name into the possible unicode values that it
     * might represent. It is possible to have more than one unicode
     * value for a single glyph name.
     *
     * @param glyphName a {@link java.lang.String} object.
     * @return int[]
     */
    public static int[] getUnicodeValues(final String glyphName) {
        while (glyphLoaderThread != null && glyphLoaderThread.isAlive()) {
            synchronized (glyphToUnicodes) {
                try {
                    glyphToUnicodes.wait(250);
                } catch (final InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return glyphToUnicodes.get(glyphName);
    }

    /**
     * return a single index for a glyph, though there may be multiples.
     *
     * @param glyphName a {@link java.lang.String} object.
     * @return Integer
     */
    public static Integer getGlyphNameIndex(final String glyphName) {
        final int[] unicodes = getUnicodeValues(glyphName);
        if (unicodes == null) {
            return null;
        } else {
            return unicodes[0];
        }
    }

    /**
     * translate a unicode value into a glyph name. It is possible for
     * different unicode values to translate into the same glyph name.
     *
     * @param unicode a {@link java.lang.Integer} object.
     * @return String
     */
    public static String getGlyphName(final int unicode) {
        while (glyphLoaderThread != null && glyphLoaderThread.isAlive()) {
            synchronized (glyphToUnicodes) {
                try {
                    glyphToUnicodes.wait(250);
                } catch (final InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return unicodeToGlyph.get(unicode);
    }
}
