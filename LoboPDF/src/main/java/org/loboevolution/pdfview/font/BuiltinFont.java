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

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * This class represents the 14 built-in fonts.  It reads these fonts
 * from files in the "res" directory, as specified in
 * BaseNames.properties.
 */
public class BuiltinFont extends Type1Font {

    /**
     * the names of the 14 base fonts
     */
    private static final String[] baseFonts = {
            "Courier", "Courier-Bold", "Courier-BoldOblique", "Courier-Oblique",
            "Helvetica", "Helvetica-Bold", "Helvetica-BoldOblique",
            "Helvetica-Oblique", "Times-Roman", "Times-Bold", "Times-BoldItalic",
            "Times-Italic", "Symbol", "ZapfDingbats"
    };
    /**
     * fonts others (e.g. Acrobad PDFWriter 3.02 for Windows) assume
     * are there, even though they're not in the spec.  Grrr...
     * <p>
     * the format is <Name_in_PDF> <Builtin_To_Use>
     */
    private static final String[] mappedFonts = {
            // map arial to helvetica
            "Arial", "Helvetica",
            "Arial,Bold", "Helvetica-Bold",
            "Arial,BoldItalic", "Helvetica-BoldOblique",
            "Arial,Italic", "Helvetica-Oblique",
            // map ArialMT to Helvetica
            "ArialMT", "Helvetica",
            "Arial-BoldMT", "Helvetica-Bold",
            "Arial-BoldItalicMT", "Helvetica-BoldOblique",
            "Arial-ItalicMT", "Helvetica-Oblique",
            // map TimesNewRoman to Times
            "TimesNewRoman", "Times-Roman",
            "TimesNewRoman,Bold", "Times-Bold",
            "TimesNewRoman,BoldItalic", "Times-BoldItalic",
            "TimesNewRoman,Italic", "Times-Italic",
            // map TimesNewRomanPSMT to Times
            "TimesNewRomanPSMT", "Times-Roman",
            "TimesNewRomanPS-BoldMT", "Times-Bold",
            "TimesNewRomanPS-BoldItalicMT", "Times-BoldItalic",
            "TimesNewRomanPS-ItalicMT", "Times-Italic",
            //Map some variants of Courier
            "Courier,Bold", "Courier-Bold",
            "Courier,BoldItalic", "Courier-BoldOblique",
            "Courier,Italic", "Courier-Oblique",
            // map CourierNew to Courier
            "CourierNew", "Courier",
            "CourierNew,Bold", "Courier-Bold",
            "CourierNew,BoldItalic", "Courier-BoldOblique",
            "CourierNew,Italic", "Courier-Oblique",
    };
    /**
     * the properties file
     */
    private static Properties props;

    /**
     * Create a new Builtin object based on the name of a built-in font
     * <p>
     * This must be the name of one of the 14 built-in fonts!
     *
     * @param baseFont the name of the font, from the PDF file
     * @param fontObj  the object containing font information
     * @throws java.io.IOException if any.
     */
    public BuiltinFont(final String baseFont, final PDFObject fontObj) throws IOException {
        super(baseFont, fontObj, null);

        parseFont(baseFont);
    }

    /**
     * create a new BuiltingFont object based on a description of the
     * font from the PDF file. Parse the description for key information
     * and use that to generate an appropriate font.
     *
     * @param baseFont   a {@link java.lang.String} object.
     * @param fontObj    a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param descriptor a {@link org.loboevolution.pdfview.font.PDFFontDescriptor} object.
     * @throws java.io.IOException if any.
     */
    public BuiltinFont(final String baseFont, final PDFObject fontObj,
                       final PDFFontDescriptor descriptor)
            throws IOException {
        super(baseFont, fontObj, descriptor);

        final String fontName = descriptor.getFontName();

        // check if it's one of the 14 base fonts
        for (final String bsFont : baseFonts) {
            if (fontName.equalsIgnoreCase(bsFont)) {
                parseFont(fontName);
                return;
            }
        }

        // check if it's a mapped font
        for (int i = 0; i < mappedFonts.length; i += 2) {
            if (fontName.equalsIgnoreCase(mappedFonts[i])) {
                parseFont(mappedFonts[i + 1]);
                return;
            }
        }

        final int flags = descriptor.getFlags();
        int style = ((flags & PDFFontDescriptor.FORCEBOLD) != 0) ? Font.BOLD : Font.PLAIN;

        if (fontName.indexOf("Bold") > 0) {
            style |= Font.BOLD;
        }
        if ((descriptor.getItalicAngle() != 0) ||
                ((flags & (PDFFontDescriptor.SCRIPT | PDFFontDescriptor.ITALIC)) != 0)) {
            style |= Font.ITALIC;
        }
        String name = null;

        if ((flags & PDFFontDescriptor.FIXED_PITCH) != 0) { // fixed width
            if (((style & Font.BOLD) > 0) && ((style & Font.ITALIC) > 0)) {
                name = "Courier-BoldOblique";
            } else if ((style & Font.BOLD) > 0) {
                name = "Courier-Bold";
            } else if ((style & Font.ITALIC) > 0) {
                name = "Courier-Oblique";
            } else {
                name = "Courier";
            }
        } else if ((flags & PDFFontDescriptor.SERIF) != 0) {  // serif font
            if (((style & Font.BOLD) > 0) && ((style & Font.ITALIC) > 0)) {
                name = "Times-BoldItalic";
            } else if ((style & Font.BOLD) > 0) {
                name = "Times-Bold";
            } else if ((style & Font.ITALIC) > 0) {
                name = "Times-Italic";
            } else {
                name = "Times-Roman";
            }
        } else {
            if (((style & Font.BOLD) > 0) && ((style & Font.ITALIC) > 0)) {
                name = "Helvetica-BoldOblique";
            } else if ((style & Font.BOLD) > 0) {
                name = "Helvetica-Bold";
            } else if ((style & Font.ITALIC) > 0) {
                name = "Helvetica-Oblique";
            } else {
                name = "Helvetica";
            }
        }

        parseFont(name);
    }

    /**
     * Parse a font given only the name of a builtin font
     */
    private void parseFont(final String baseFont) throws IOException {
        // load the base fonts properties files, if it isn't already loaded
        if (props == null) {
            props = new Properties();
            final URL resource = BuiltinFont.class.getResource("/org/loboevolution/pdfview/font/res/BaseFonts.properties");
            try (final InputStream stream = resource.openStream()) {
                props.load(stream);
            }
        }

        // make sure we're a known font
        if (!props.containsKey(baseFont + ".file")) {
            throw new IllegalArgumentException("Unknown Base Font: " + baseFont);
        }

        // get the font information from the properties file
        final String file = props.getProperty(baseFont + ".file");

        // the size of the file
        final int length = Integer.parseInt(props.getProperty(baseFont + ".length"));
        // the size of the unencrypted portion
        int length1 = 0;
        // the size of the encrypted portion
        int length2 = 0;

        // read the data from the file
        final byte[] data = new byte[length];
        final InputStream fontStream = NativeFont.class.getResourceAsStream("res/" + file);
        int cur = 0;
        while (cur < length) {
            cur += fontStream.read(data, cur, length - cur);
        }
        fontStream.close();

        // are we a pfb file?
        if ((data[0] & 0xff) == 0x80) {
            // read lengths from the file
            length1 = (data[2] & 0xff);
            length1 |= (data[3] & 0xff) << 8;
            length1 |= (data[4] & 0xff) << 16;
            length1 |= (data[5] & 0xff) << 24;
            length1 += 6;

            length2 = (data[length1 + 2] & 0xff);
            length2 |= (data[length1 + 3] & 0xff) << 8;
            length2 |= (data[length1 + 4] & 0xff) << 16;
            length2 |= (data[length1 + 5] & 0xff) << 24;
            length1 += 6;
        } else {
            // get the values from the properties file
            length1 = Integer.parseInt(props.getProperty(baseFont + ".length1"));

            if (props.containsKey(baseFont + ".length2")) {
                length2 = Integer.parseInt(props.getProperty(baseFont + ".lenth2"));
            } else {
                length2 = length - length1;
            }
        }

        parseFont(data, length1, length2);
    }
}
