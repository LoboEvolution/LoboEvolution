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

import lombok.Data;
import org.loboevolution.pdfview.PDFObject;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 * <p>PDFFontDescriptor class.</p>
 * <p>
 * Author  jkaplan
 */
@Data
public class PDFFontDescriptor {

    /**
     * All glyphs have the same width.
     */
    public static final int FIXED_PITCH = 1 << (0);
    /**
     * Glyphs have serifs.
     */
    public static final int SERIF = 1 << (2 - 1);
    /**
     * Font contains glyphs outside the Adobe standard Latin.
     */
    public static final int SYMBOLIC = 1 << (3 - 1);
    /**
     * Glyphs resemble cursive handwriting.
     */
    public static final int SCRIPT = 1 << (4 - 1);
    /**
     * Font uses the Adobe standard Latic character set.
     */
    public static final int NONSYMBOLIC = 1 << (6 - 1);
    /**
     * Glyphs have dominant vertical strokes that are slanted.
     */
    public static final int ITALIC = 1 << (7 - 1);
    /**
     * Font contains no lowercase letters.
     */
    public static final int ALLCAP = 1 << (17 - 1);
    /**
     * Font contains both uppercase and lowercase letters..
     */
    public static final int SMALLCAP = 1 << (18 - 1);
    /**
     * Determines whether bold glyphs shall be painted with
     * extra pixels even at very small text sizes.
     */
    public static final int FORCEBOLD = 1 << (19 - 1);
    /**
     * Holds value of property ascent.
     */
    private int ascent;
    /**
     * Holds value of property capHeight.
     */
    private int capHeight;
    /**
     * Holds value of property descent.
     */
    private int descent;
    /**
     * Holds value of property flags.
     */
    private int flags;
    /**
     * Holds the optional FontFamily (PDF 1.5)
     */
    private String fontFamily;
    /**
     * Holds value of property fontName.
     */
    private String fontName;
    /**
     * Holds the optional FontStretch (PDF 1.5)
     */
    private String fontStretch;
    /**
     * Holds the optional FontWeight (PDF 1.5)
     */
    private int fontWeight;
    /**
     * Holds value of property italicAngle.
     */
    private int italicAngle = 0;
    /**
     * Holds value of property stemV.
     */
    private int stemV;
    /**
     * Holds value of property avgWidth.
     */
    private int avgWidth = 0;
    /**
     * Holds value of property fontFile.
     */
    private PDFObject fontFile;
    /**
     * Holds value of property fontFile2.
     */
    private PDFObject fontFile2;
    /**
     * Holds value of property fontFile3.
     */
    private PDFObject fontFile3;
    /**
     * Holds value of property leading.
     */
    private int leading = 0;
    /**
     * Holds value of property maxWidth.
     */
    private int maxWidth = 0;
    /**
     * Holds value of property misingWidth.
     */
    private int missingWidth = 0;
    /**
     * Holds value of property stemH.
     */
    private int stemH = 0;
    /**
     * Holds value of property xHeight.
     */
    private int xHeight = 0;
    /**
     * Holds value of property charSet.
     */
    private PDFObject charSet;
    /**
     * Holds value of property fontBBox.
     */
    private Rectangle2D.Float fontBBox;

    /**
     * Creates a new instance of PDFFontDescriptor
     *
     * @param basefont a {@link java.lang.String} object.
     */
    public PDFFontDescriptor(final String basefont) {
        setFontName(basefont);
        // [[MW TODO: find basefont info and fill in the rest of the
        // descriptor?]]
    }

    /**
     * Creates a new instance of PDFFontDescriptor
     *
     * @param obj         a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param fontSubType a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public PDFFontDescriptor(final PDFObject obj, final String fontSubType) throws IOException {
        // required parameters
        setFlags(obj.getDictRef("Flags").getIntValue());
        PDFObject fontNameObj = obj.getDictRef("FontName");
        if (fontNameObj == null) {
            // fallback to avoid NPE try to use the BaseFont
            fontNameObj = obj.getDictRef("BaseFont");
        }
        setFontName(fontNameObj.getStringValue());
        setItalicAngle(obj.getDictRef("ItalicAngle").getIntValue());

        // conditionally required parameters
        final boolean areConditionalParametersRequired = !"Type3".equals(fontSubType)
                && !Boolean.getBoolean("PDFRenderer.lenientFontDescriptorParsing");

        // these values are declared as Required except for Type 3 fonts
        // however a value might not be available for some fonts and
        // therefore some predefined value is set, so that we have a fallback
        if (obj.getDictionary().containsKey("Ascent")) {
            setAscent(obj.getDictRef("Ascent").getIntValue());
        } else if (areConditionalParametersRequired) {
            setAscent(728); // value of ArialMT as used with Report Label
        }
        if (obj.getDictionary().containsKey("CapHeight")) {
            setCapHeight(obj.getDictRef("CapHeight").getIntValue());
        } else if (areConditionalParametersRequired) {
            setCapHeight(716); // value of ArialMT as used with Report Label
        }
        if (obj.getDictionary().containsKey("Descent")) {
            setDescent(obj.getDictRef("Descent").getIntValue());
        } else if (areConditionalParametersRequired) {
            setDescent(-210); // value of ArialMT as used with Report Label
        }
        if (obj.getDictionary().containsKey("StemV")) {
            setStemV(obj.getDictRef("StemV").getIntValue());
        } else if (areConditionalParametersRequired) {
            setStemV(109); // "normal" value for vertical stem width (PDFlib)
        }

        // font bounding box (non-optional but a NPE won't help)
        if (obj.getDictionary().containsKey("FontBBox")) {
            final PDFObject[] bboxdef = obj.getDictRef("FontBBox").getArray();
            final float[] bboxfdef = new float[4];
            for (int i = 0; i < 4; i++) {
                bboxfdef[i] = bboxdef[i].getFloatValue();
            }
            setFontBBox(new Rectangle2D.Float(bboxfdef[0], bboxfdef[1],
                    bboxfdef[2] - bboxfdef[0],
                    bboxfdef[3] - bboxfdef[1]));
        }

        // optional parameters
        if (obj.getDictionary().containsKey("AvgWidth")) {
            setAvgWidth(obj.getDictRef("AvgWidth").getIntValue());
        }
        if (obj.getDictionary().containsKey("FontFile")) {
            setFontFile(obj.getDictRef("FontFile"));
        }
        if (obj.getDictionary().containsKey("FontFile2")) {
            setFontFile2(obj.getDictRef("FontFile2"));
        }
        if (obj.getDictionary().containsKey("FontFile3")) {
            setFontFile3(obj.getDictRef("FontFile3"));
        }
        if (obj.getDictionary().containsKey("Leading")) {
            setLeading(obj.getDictRef("Leading").getIntValue());
        }
        if (obj.getDictionary().containsKey("MaxWidth")) {
            setMaxWidth(obj.getDictRef("MaxWidth").getIntValue());
        }
        if (obj.getDictionary().containsKey("MissingWidth")) {
            setMissingWidth(obj.getDictRef("MissingWidth").getIntValue());
        }
        if (obj.getDictionary().containsKey("StemH")) {
            setStemH(obj.getDictRef("StemH").getIntValue());
        }
        if (obj.getDictionary().containsKey("XHeight")) {
            setXHeight(obj.getDictRef("XHeight").getIntValue());
        }
        if (obj.getDictionary().containsKey("CharSet")) {
            setCharSet(obj.getDictRef("CharSet"));
        }
        if (obj.getDictionary().containsKey("FontFamily")) {
            setFontFamily(obj.getDictRef("FontFamily").getStringValue());
        }
        if (obj.getDictionary().containsKey("FontWeight")) {
            setFontWeight(obj.getDictRef("FontWeight").getIntValue());
        }
        if (obj.getDictionary().containsKey("FontStretch")) {
            setFontStretch(obj.getDictRef("FontStretch").getStringValue());
        }
    }
}
