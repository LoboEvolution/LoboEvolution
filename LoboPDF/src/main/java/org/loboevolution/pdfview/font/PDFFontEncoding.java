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
package org.loboevolution.pdfview.font;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFRenderer;
import org.loboevolution.pdfview.font.cid.PDFCMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The PDFFont encoding encapsulates the mapping from character codes
 * in the PDF document to glyphs of the font.
 * <p>
 * Encodings take two basic forms.  For Type1, TrueType, and Type3 fonts,
 * the encoding maps from character codes to Strings, which represent the
 * glyphs of the font.  For Type0 fonts, the mapping is a CMap which maps
 * character codes to characters in one of many descendant fonts.
 * <p>
 * Note that the data in the PDF might be ASCII characters (bytes) or it might
 * be a multi-byte format such as unicode.  For now we will assume all
 * glyph ids fit into at most the two bytes of a character.
 */
public class PDFFontEncoding {

    /**
     * Encoding types
     */
    private static final int TYPE_ENCODING = 0;
    private static final int TYPE_CMAP = 1;
    /**
     * the base encoding (an array of integers which can be mapped to names
     * using the methods on FontSupport
     */
    private int[] baseEncoding;
    /**
     * any differences from the base encoding
     */
    private Map<Character, String> differences;
    /**
     * a CMap for fonts encoded by CMap
     */
    private PDFCMap cmap;
    /**
     * the type of this encoding (encoding or CMap)
     */
    private int type;
    private PDFObject mapName;

    /**
     * <p>Constructor for PDFFontEncoding.</p>
     *
     * @param cmap a {@link org.loboevolution.pdfview.font.cid.PDFCMap} object.
     */
    public PDFFontEncoding(final PDFCMap cmap) {
        super();
        this.type = TYPE_CMAP;
        this.cmap = cmap;
    }

    /**
     * Creates a new instance of PDFFontEncoding
     *
     * @param fontType a {@link java.lang.String} object.
     * @param encoding a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public PDFFontEncoding(final String fontType, final PDFObject encoding)
            throws IOException {
        if (encoding.getType() == PDFObject.NAME) {
            // if the encoding is a String, it is the name of an encoding
            // or the name of a CMap, depending on the type of the font
            if (fontType.equals("Type0")) {
                this.type = TYPE_CMAP;
                this.cmap = PDFCMap.getCMap(encoding.getStringValue());
            } else {
                this.type = TYPE_ENCODING;

                this.differences = new HashMap<>();
                this.baseEncoding = this.getBaseEncoding(encoding.getStringValue());
            }
        } else {
            // loook at the "Type" entry of the encoding to determine the type
            final String typeStr = encoding.getDictRef("Type").getStringValue();
            switch (typeStr) {
                case "Encoding":
                    // it is an encoding
                    this.type = TYPE_ENCODING;
                    parseEncoding(encoding);
                    break;
                case "CMap":
                    // it is a CMap
                    this.type = TYPE_CMAP;
                    this.cmap = PDFCMap.getCMap(encoding);
                    this.mapName = encoding.getDictRef("CMapName");
                    break;
                default:
                    throw new IllegalArgumentException("Uknown encoding type: " + this.type);
            }
        }
    }

    /**
     * Get the glyphs associated with a given String
     *
     * @param font a {@link org.loboevolution.pdfview.font.PDFFont} object.
     * @param text a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    public List<PDFGlyph> getGlyphs(final PDFFont font, final String text) {
        final List<PDFGlyph> outList = new ArrayList<>(text.length());

        // go character by character through the text
        final char[] arry = text.toCharArray();
        for (int i = 0; i < arry.length; i++) {
            switch (this.type) {
                case TYPE_ENCODING:
                    outList.add(getGlyphFromEncoding(font, arry[i]));
                    break;
                case TYPE_CMAP:
                    // 2 bytes -> 1 character in a CMap
                    char c = (char) ((arry[i] & 0xff) << 8);
                    if (i < arry.length - 1) {
                        c |= (char) (arry[++i] & 0xff);
                    }
                    outList.add(getGlyphFromCMap(font, c));
                    break;
                default:
                    break;
            }
        }
        return outList;
    }

    /**
     * Get a glyph from an encoding, given a font and character
     */
    private PDFGlyph getGlyphFromEncoding(final PDFFont font, char src) {
        String charName = null;

        // only deal with one byte of source
        src &= 0xff;

        // see if this character is in the differences list
        if (this.differences.containsKey(src)) {
            charName = this.differences.get(src);
        } else if (this.baseEncoding != null) {
            // get the character name from the base encoding
            final int charID = this.baseEncoding[src];
            charName = FontSupport.getName(charID);
        }

        return font.getCachedGlyph(src, charName);
    }

    /**
     * Get a glyph from a CMap, given a Type0 font and a character
     */
    private PDFGlyph getGlyphFromCMap(PDFFont font, final char src) {
        final int fontID = this.cmap.getFontID(src);
        final char charID = this.cmap.map(src);

        if (font instanceof Type0Font) {
            font = ((Type0Font) font).getDescendantFont(fontID);
        }

        return font.getCachedGlyph(charID, null);
    }

    /**
     * Parse a PDF encoding object for the actual encoding
     *
     * @param encoding a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public void parseEncoding(final PDFObject encoding) throws IOException {
        this.differences = new HashMap<>();

        // figure out the base encoding, if one exists
        final PDFObject baseEncObj = encoding.getDictRef("BaseEncoding");
        if (baseEncObj != null) {
            this.baseEncoding = getBaseEncoding(baseEncObj.getStringValue());
        }

        // parse the differences array
        final PDFObject diffArrayObj = encoding.getDictRef("Differences");
        if (diffArrayObj != null) {
            final PDFObject[] diffArray = diffArrayObj.getArray();
            int curPosition = -1;
            for (final PDFObject pdfObject : diffArray) {
                if (pdfObject.getType() == PDFObject.NUMBER) {
                    curPosition = pdfObject.getIntValue();
                } else if (pdfObject.getType() == PDFObject.NAME) {
                    final Character key = (char) curPosition;
                    this.differences.put(key, pdfObject.getStringValue());
                    curPosition++;
                } else {
                    throw new IllegalArgumentException("Unexpected type in diff array: " + pdfObject);
                }
            }
        }
    }

    /**
     * Get the base encoding for a given name
     */
    private int[] getBaseEncoding(final String encodingName) {
        return switch (encodingName) {
            case "MacRomanEncoding" -> FontSupport.macRomanEncoding;
            case "MacExpertEncoding" -> FontSupport.type1CExpertCharset;
            case "WinAnsiEncoding" -> FontSupport.winAnsiEncoding;
            case "StandardEncoding" -> FontSupport.standardEncoding;
            case "SymbolSetEncoding" -> FontSupport.symbolSetEncoding;
            default -> throw new IllegalArgumentException("Unknown encoding: " + encodingName);
        };
    }

    /**
     * <p>isOneByteIdentity.</p>
     *
     * @return a boolean.
     */
    public boolean isOneByteIdentity() {
        if (this.mapName != null) {
            try {
                return "OneByteIdentityH".equals(this.mapName.getStringValue());
            } catch (final IOException e) {
                PDFRenderer.getErrorHandler().publishException(e);
            }
        }

        return false;
    }
}
