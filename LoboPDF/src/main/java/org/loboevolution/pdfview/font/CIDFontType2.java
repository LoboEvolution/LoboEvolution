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
package org.loboevolution.pdfview.font;

import lombok.Getter;
import org.loboevolution.pdfview.PDFObject;

import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * a font object derived from a CID font.
 * <p>
 * Author Jonathan Kaplan
 */
public class CIDFontType2 extends TTFFont {

    /*
     * the default width
     */
    private final int defaultWidth = 1000;
    /**
     * The width of each glyph from the DW and W arrays
     */
    private Map<Character, Float> widths = null;
    /**
     * The vertical width of each glyph from the DW2 and W2 arrays
     */
    private Map<Character, Float> widthsVertical = null;
    /*
     * the default vertical width
     */
    @Getter
    private int defaultWidthVertical = 1000;
    /**
     * the CIDtoGID map, if any
     */
    private ByteBuffer cidToGidMap;

    /**
     * create a new CIDFontType2 object based on the name of a built-in font
     * and the font descriptor
     *
     * @param baseName   the name of the font, from the PDF file
     * @param fontObj    a dictionary that contains the DW (defaultWidth) and
     *                   W (width) parameters
     * @param descriptor a descriptor for the font
     * @throws java.io.IOException if any.
     */
    public CIDFontType2(final String baseName, final PDFObject fontObj,
                        final PDFFontDescriptor descriptor) throws IOException {
        super(baseName, fontObj, descriptor);

        parseWidths(fontObj);

        // read the CIDSystemInfo dictionary (required)
        final PDFObject systemInfoObj = fontObj.getDictRef("CIDSystemInfo");
        // read the cid to gid map (optional)
        final PDFObject mapObj = fontObj.getDictRef("CIDToGIDMap");


        // only read the map if it is a stream (if it is a name, it
        // is "Identity" and can be ignored
        if (mapObj != null && (mapObj.getType() == PDFObject.STREAM)) {
            this.cidToGidMap = mapObj.getStreamBuffer();
        }
    }

    /**
     * Parse the Widths array and DW object
     */
    private void parseWidths(final PDFObject fontObj)
            throws IOException {
        // read the default width (otpional)
        PDFObject defaultWidthObj = fontObj.getDictRef("DW");
        if (defaultWidthObj != null && defaultWidthObj.getIntValue() != 0) {
            // XOND: commented out the setting of new default width, as several
            //		PDFs are displayed in a wrong format due to this:
//            this.defaultWidth = defaultWidthObj.getIntValue();
        }

        int entryIdx = 0;
        int first = 0;
        int last = 0;
        PDFObject[] widthArray;

        // read the widths table 
        PDFObject widthObj = fontObj.getDictRef("W");
        if (widthObj != null) {

            // initialize the widths array
            this.widths = new HashMap<>();

            // parse the width array
            widthArray = widthObj.getArray();

            /* an entry can be in one of two forms:
             *   <startIndex> <endIndex> <value> or
             *   <startIndex> [ array of values ]
             * we use the entryIdx to differentitate between them
             */
            for (final PDFObject pdfObject : widthArray) {
                if (entryIdx == 0) {
                    // first value in an entry.  Just store it
                    first = pdfObject.getIntValue();
                } else if (entryIdx == 1) {
                    // second value -- is it an int or array?
                    if (pdfObject.getType() == PDFObject.ARRAY) {
                        // add all the entries in the array to the width array
                        final PDFObject[] entries = pdfObject.getArray();
                        for (int c = 0; c < entries.length; c++) {
                            final Character key = (char) (c + first);

                            // value is width / default width
                            final float value = entries[c].getIntValue();
                            this.widths.put(key, value);
                        }
                        // all done
                        entryIdx = -1;
                    } else {
                        last = pdfObject.getIntValue();
                    }
                } else {
                    // third value.  Set a range
                    final int value = pdfObject.getIntValue();

                    // set the range
                    for (int c = first; c <= last; c++) {
                        this.widths.put((char) c, (float) value);
                    }

                    // all done
                    entryIdx = -1;
                }

                entryIdx++;
            }
        }

        // read the optional vertical default width
        defaultWidthObj = fontObj.getDictRef("DW2");
        if (defaultWidthObj != null) {
            this.defaultWidthVertical = defaultWidthObj.getIntValue();
        }

        // read the vertical widths table
        widthObj = fontObj.getDictRef("W2");
        if (widthObj != null) {

            // initialize the widths array
            this.widthsVertical = new HashMap<>();

            // parse the width2 array
            widthArray = widthObj.getArray();

            /* an entry can be in one of two forms:
             *   <startIndex> <endIndex> <value> or
             *   <startIndex> [ array of values ]
             * we use the entryIdx to differentitate between them
             */
            entryIdx = 0;
            first = 0;
            last = 0;

            for (final PDFObject pdfObject : widthArray) {
                if (entryIdx == 0) {
                    // first value in an entry.  Just store it
                    first = pdfObject.getIntValue();
                } else if (entryIdx == 1) {
                    // second value -- is it an int or array?
                    if (pdfObject.getType() == PDFObject.ARRAY) {
                        // add all the entries in the array to the width array
                        final PDFObject[] entries = pdfObject.getArray();
                        for (int c = 0; c < entries.length; c++) {
                            final Character key = (char) (c + first);

                            // value is width / default width
                            final float value = entries[c].getIntValue();
                            this.widthsVertical.put(key, value);
                        }
                        // all done
                        entryIdx = -1;
                    } else {
                        last = pdfObject.getIntValue();
                    }
                } else {
                    // third value.  Set a range
                    final int value = pdfObject.getIntValue();

                    // set the range
                    for (int c = first; c <= last; c++) {
                        this.widthsVertical.put((char) c, (float) value);
                    }

                    // all done
                    entryIdx = -1;
                }

                entryIdx++;
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the default width in text space
     */
    @Override
    public int getDefaultWidth() {
        return this.defaultWidth;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the width of a given character
     */
    @Override
    public float getWidth(final char code, final String name) {
        if (this.widths == null) {
            return 1f;
        }
        final Float w = this.widths.get(code);
        if (w == null) {
            return 1f;
        }

        return w / getDefaultWidth();
    }

    /**
     * Get the vertical width of a given character
     *
     * @param code a char.
     * @param name a {@link java.lang.String} object.
     * @return a float.
     */
    public float getWidthVertical(final char code, final String name) {
        if (this.widthsVertical == null) {
            return 1f;
        }
        final Float w = this.widthsVertical.get(code);
        if (w == null) {
            return 1f;
        }

        return w / getDefaultWidthVertical();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the outline of a character given the character code.  We
     * interpose here in order to avoid using the CMap of the font in
     * a CID mapped font.
     */
    @Override
    protected synchronized GeneralPath getOutline(final char src, final float width) {
        int glyphId = (src & 0xffff);

        // check if there is a cidToGidMap
        if (this.cidToGidMap != null) {
            // read the map
            glyphId = this.cidToGidMap.getChar(glyphId * 2);
        }

        // call getOutline on the glyphId
        return getOutline(glyphId, width);
    }
}
