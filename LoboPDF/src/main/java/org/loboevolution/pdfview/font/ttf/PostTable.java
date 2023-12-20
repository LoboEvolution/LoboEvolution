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

package org.loboevolution.pdfview.font.ttf;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.loboevolution.pdfview.PDFDebugger;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Model the TrueType Post table
 * <p>
 * Author  jkaplan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostTable extends TrueTypeTable {

    /**
     * Holds value of property format.
     */
    private int format;

    /**
     * Holds value of property italicAngle.
     */
    private int italicAngle;

    /**
     * Holds value of property underlinePosition.
     */
    private short underlinePosition;

    /**
     * Holds value of property underlineThickness.
     */
    private short underlineThickness;

    /**
     * Holds value of property isFixedPitch.
     */
    private short isFixedPitch;

    /**
     * Holds value of property minMemType42.
     */
    private int minMemType42;

    /**
     * Holds value of property maxMemType42.
     */
    private int maxMemType42;

    /**
     * Holds value of property minMemType1.
     */
    private int minMemType1;

    /**
     * Holds value of property maxMemType1.
     */
    private int maxMemType1;

    /**
     * A map which character values to names and vice versa
     */
    private PostMap nameMap;

    /**
     * Creates a new instance of PostTable
     */
    protected PostTable() {
        super(TrueTypeTable.POST_TABLE);

        this.nameMap = new PostMap();
    }

    /**
     * Map a character name to a glyphNameIndex
     *
     * @param name a {@link java.lang.String} object.
     * @return a short.
     */
    public short getGlyphNameIndex(final String name) {
        return this.nameMap.getCharIndex(name);
    }

    /**
     * Map a character code to a glyphIndex name
     *
     * @param c a char.
     * @return a {@link java.lang.String} object.
     */
    public String getGlyphName(final char c) {
        return this.nameMap.getCharName(c);
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the data in this map as a ByteBuffer
     */
    @Override
    public ByteBuffer getData() {
        final int size = getLength();

        final ByteBuffer buf = ByteBuffer.allocate(size);

        // write the header
        buf.putInt(getFormat());
        buf.putInt(getItalicAngle());
        buf.putShort(getUnderlinePosition());
        buf.putShort(getUnderlineThickness());
        buf.putShort(getIsFixedPitch());
        buf.putShort((short) 0);
        buf.putInt(getMinMemType42());
        buf.putInt(getMaxMemType42());
        buf.putInt(getMinMemType1());
        buf.putInt(getMaxMemType1());

        // now write the table
        buf.put(this.nameMap.getData());

        // reset the start pointer
        buf.flip();

        return buf;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Initialize this structure from a ByteBuffer
     */
    @Override
    public void setData(final ByteBuffer data) {
        setFormat(data.getInt());
        setItalicAngle(data.getInt());
        setUnderlinePosition(data.getShort());
        setUnderlineThickness(data.getShort());
        setIsFixedPitch(data.getShort());
        data.getShort();
        setMinMemType42(data.getInt());
        setMaxMemType42(data.getInt());
        setMinMemType1(data.getInt());
        setMaxMemType1(data.getInt());

        // create the map, based on the type
        switch (this.format) {
            case 0x10000:
                this.nameMap = new PostMapFormat0();
                break;
            case 0x20000:
                this.nameMap = new PostMapFormat2();
                break;
            case 0x30000:
                // empty post map.
                this.nameMap = new PostMap();
                break;
            default:
                this.nameMap = new PostMap();
                PDFDebugger.debug("Unknown post map type: " +
                        Integer.toHexString(this.format));
                break;
        }

        // fill in the data in the map
        this.nameMap.setData(data);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the length of this table
     */
    @Override
    public int getLength() {
        int size = 32;
        if (this.nameMap != null) {
            size += this.nameMap.getLength();
        }

        return size;
    }

    /**
     * An empty post map
     */
    static class PostMap {
        /**
         * map a name to a character index
         */
        short getCharIndex(final String charName) {
            return (short) 0;
        }

        /**
         * name a character index to a name
         */
        String getCharName(final char charIndex) {
            return null;
        }

        /**
         * get the length of the data in this map
         */
        int getLength() {
            return 0;
        }

        /**
         * get the data in this map as a ByteBuffer
         */
        ByteBuffer getData() {
            return ByteBuffer.allocate(0);
        }

        /**
         * set the data in this map from a ByteBuffer
         */
        void setData(final ByteBuffer data) {
            // do nothing
        }
    }

    /**
     * A Format 0 post map
     */
    static class PostMapFormat0 extends PostMap {
        /**
         * the glyph names in standard Macintosh ordering
         */
        protected final String[] stdNames = {
/* 0 */     ".notdef", ".null", "nonmarkingreturn", "space", "exclam", "quotedbl", "numbersign", "dollar",
/* 8 */     "percent", "ampersand", "quotesingle", "parenleft", "parenright", "asterisk", "plus", "comma",
/* 16 */    "hyphen", "period", "slash", "zero", "one", "two", "three", "four",
/* 24 */    "five", "six", "seven", "eight", "nine", "colon", "semicolon", "less",
/* 32 */    "equal", "greater", "question", "at", "A", "B", "C", "D",
/* 40 */    "E", "F", "G", "H", "I", "J", "K", "L",
/* 48 */    "M", "N", "O", "P", "Q", "R", "S", "T",
/* 56 */    "U", "V", "W", "X", "Y", "Z", "bracketleft", "ackslash",
/* 64 */    "bracketright", "asciicircum", "underscore", "grave", "a", "b", "c", "d",
/* 72 */    "e", "f", "g", "h", "i", "j", "k", "l",
/* 80 */    "m", "n", "o", "p", "q", "r", "s", "t",
/* 88 */    "u", "v", "w", "x", "y", "z", "braceleft", "bar",
/* 96 */    "braceright", "asciitilde", "Adieresis", "Aring", "Ccedilla", "Eacute", "Ntilde", "Odieresis",
/* 104 */   "Udieresis", "aacute", "agrave", "acircumflex", "adieresis", "atilde", "aring", "ccedilla",
/* 112 */   "eacute", "egrave", "ecircumflex", "edieresis", "iacute", "igrave", "icircumflex", "idieresis",
/* 120 */   "ntilde", "oacute", "ograve", "ocircumflex", "odieresis", "otilde", "uacute", "ugrave",
/* 128 */   "ucircumflex", "udieresis", "dagger", "degree", "cent", "sterling", "section", "bullet",
/* 136 */   "paragraph", "germandbls", "registered", "copyright", "trademark", "acute", "dieresis", "notequal",
/* 144 */   "AE", "Oslash", "infinity", "plusminus", "lessequal", "greaterequal", "yen", "mu",
/* 152 */   "partialdiff", "summation", "product", "pi", "integral", "ordfeminine", "ordmasculine", "Omega",
/* 160 */   "ae", "oslash", "questiondown", "exclamdown", "logicalnot", "radical", "florin", "approxequal",
/* 168 */   "Delta", "guillemotleft", "guillemotright", "ellipsis", "nonbreakingspace", "Agrave", "Atilde", "Otilde",
/* 176 */   "OE", "oe", "endash", "emdash", "quotedblleft", "quotedblright", "quoteleft", "quoteright",
/* 184 */   "divide", "lozenge", "ydieresis", "Ydieresis", "fraction", "currency", "guilsinglleft", "guilsinglright",
/* 192 */   "fi", "fl", "daggerdbl", "periodcentered", "quotesinglbase", "quotedblbase", "perthousand", "Acircumflex",
/* 200 */   "Ecircumflex", "Aacute", "Edieresis", "Egrave", "Iacute", "Icircumflex", "Idieresis", "Igrave",
/* 208 */   "Oacute", "Ocircumflex", "apple", "Ograve", "Uacute", "Ucircumflex", "Ugrave", "dotlessi",
/* 216 */   "circumflex", "tilde", "macron", "breve", "dotaccent", "ring", "cedilla", "hungarumlaut",
/* 224 */   "ogonek", "caron", "Lslash", "lslash", "Scaron", "scaron", "Zcaron", "zcaron",
/* 232 */   "brokenbar", "Eth", "eth", "Yacute", "yacute", "Thorn", "thorn", "minus",
/* 240 */   "multiply", "onesuperior", "twosuperior", "threesuperior", "onehalf", "onequarter", "threequarters", "franc",
/* 248 */   "Gbreve", "gbreve", "Idotaccent", "Scedilla", "scedilla", "Cacute", "cacute", "Ccaron",
/* 256 */   "ccaron", "dcroat"
        };

        @Override
        short getCharIndex(final String charName) {
            for (int i = 0; i < this.stdNames.length; i++) {
                if (charName.equals(this.stdNames[i])) {
                    return (short) i;
                }
            }

            return (short) 0;
        }

        @Override
        String getCharName(final char charIndex) {
            return this.stdNames[charIndex];
        }

    }

    /**
     * an extension to handle format 2 post maps
     */
    static class PostMapFormat2 extends PostMapFormat0 {
        /**
         * the glyph name index
         */
        short[] glyphNameIndex;

        /**
         * the glyph names
         */
        String[] glyphNames;

        @Override
        short getCharIndex(final String charName) {
            // find the index of this character name
            short idx = -1;

            // first try the local names map
            for (int i = 0; i < this.glyphNames.length; i++) {
                if (charName.equals(this.glyphNames[i])) {
                    // this is the value from the glyph name index
                    idx = (short) (this.stdNames.length + i);
                    break;
                }
            }

            // if that doesn't work, try the standard names
            if (idx == -1) {
                idx = super.getCharIndex(charName);
            }

            // now get the entry in the index
            for (int c = 0; c < this.glyphNameIndex.length; c++) {
                if (this.glyphNameIndex[c] == idx) {
                    return (short) c;
                }
            }

            // not found
            return (short) 0;
        }

        @Override
        String getCharName(final char charIndex) {
            if (charIndex >= this.stdNames.length) {
                return this.glyphNames[charIndex - this.stdNames.length];
            }

            return super.getCharName(charIndex);
        }

        @Override
        int getLength() {
            // the size of the header plus the table of mappings
            int size = 2 + (2 * this.glyphNameIndex.length);

            // the size of each string -- note the extra byte for a pascal
            // string
            for (final String glyphName : this.glyphNames) {
                size += glyphName.length() + 1;
            }

            return size;
        }

        @Override
        ByteBuffer getData() {
            final ByteBuffer buf = ByteBuffer.allocate(getLength());

            // write the number of glyphs
            buf.putShort((short) this.glyphNameIndex.length);

            // write the name indices
            for (final short nameIndex : this.glyphNameIndex) {
                buf.putShort(nameIndex);
            }

            // write the names as pascal strings
            for (final String glyphName : this.glyphNames) {
                buf.put((byte) glyphName.length());
                buf.put(glyphName.getBytes());
            }

            // reset the start pointer
            buf.flip();

            return buf;
        }

        @Override
        void setData(final ByteBuffer data) {
            final short numGlyphs = data.getShort();
            this.glyphNameIndex = new short[numGlyphs];

            // the highest glyph index seen so far
            int maxGlyph = 257;
            for (int i = 0; i < numGlyphs; i++) {
                this.glyphNameIndex[i] = data.getShort();

                // see if this is the highest glyph
                if (this.glyphNameIndex[i] > maxGlyph) {
                    maxGlyph = this.glyphNameIndex[i];
                }
            }

            // subtract off the default glyphs
            maxGlyph -= 257;

            // read in any additional names
            this.glyphNames = new String[maxGlyph];
            // fill with empty strings for avoiding nullpointer exception: glyph names
            // are not mandatory for true type fonts according to the PDF spec.
            Arrays.fill(this.glyphNames, "");

            // read each name from a pascal string
            // the length is stored in the first byte, followed by
            // the data
            for (int i = 0; i < maxGlyph; i++) {
                if (data.hasRemaining()) {
                    // size in the first byte
                    final byte size = data.get();

                    // then the data
                    final byte[] stringData = new byte[size];
                    data.get(stringData);

                    this.glyphNames[i] = new String(stringData);
                }
            }
        }
    }
}
