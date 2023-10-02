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
package org.loboevolution.pdfview.colorspace;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFPaint;
import org.loboevolution.pdfview.PDFParseException;
import org.loboevolution.pdfview.function.PDFFunction;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * A color space that can convert a set of color components into
 * PDFPaint.
 * Author Mike Wessler
 */
public class PDFColorSpace {

    /**
     * the name of the device-dependent gray color space
     */
    public static final int COLORSPACE_GRAY = 0;

    /**
     * the name of the device-dependent RGB color space
     */
    public static final int COLORSPACE_RGB = 1;

    /**
     * the name of the device-dependent CMYK color space
     */
    public static final int COLORSPACE_CMYK = 2;

    /**
     * the name of the pattern color space
     */
    public static final int COLORSPACE_PATTERN = 3;

    private static final PDFColorSpace rgbSpace = new PDFColorSpace(ColorSpace.getInstance(ColorSpace.CS_sRGB));
    private static final PDFColorSpace cmykSpace = new PDFColorSpace(new CMYKColorSpace());

    /**
     * the pattern space
     */
    private static final PDFColorSpace patternSpace = new PatternSpace();

    /**
     * graySpace and the gamma correction for it.
     */
    private static final PDFColorSpace graySpace;

    static {
        final boolean useSGray = true;

        try {
            final URL resource = PDFColorSpace.class.getResource("/org/loboevolution/pdfview/colorspace/sGray.icc");
            try (final InputStream stream = resource.openStream()) {
                graySpace = new PDFColorSpace((!useSGray) ? ColorSpace.getInstance(ColorSpace.CS_GRAY)
                        : new ICC_ColorSpace(ICC_Profile.getInstance(stream)));
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * the color space
     */
    ColorSpace cs;

    /**
     * create a PDFColorSpace based on a Java ColorSpace
     *
     * @param cs the Java ColorSpace
     */
    public PDFColorSpace(final ColorSpace cs) {
        this.cs = cs;
    }

    /**
     * Get a color space by name
     *
     * @param name the name of one of the device-dependent color spaces
     * @return a {@link org.loboevolution.pdfview.colorspace.PDFColorSpace} object.
     */
    public static PDFColorSpace getColorSpace(final int name) {
        switch (name) {
            case COLORSPACE_GRAY:
            case ColorSpace.CS_GRAY:
            case ColorSpace.TYPE_GRAY:
                return graySpace;

            case COLORSPACE_RGB:
                return rgbSpace;

            case COLORSPACE_CMYK:
                return cmykSpace;

            case COLORSPACE_PATTERN:
                return patternSpace;

            default:
                throw new IllegalArgumentException("Unknown Color Space name: " +
                        name);
        }
    }

    /**
     * Get a color space specified in a PDFObject
     *
     * @param csobj     the PDFObject with the colorspace information
     * @param resources a {@link java.util.Map} object.
     * @return a {@link org.loboevolution.pdfview.colorspace.PDFColorSpace} object.
     * @throws java.io.IOException if any.
     */
    public static PDFColorSpace getColorSpace(PDFObject csobj, final Map resources)
            throws IOException {
        String name;

        PDFObject colorSpaces = null;

        if (resources != null) {
            colorSpaces = (PDFObject) resources.get("ColorSpace");
        }

        if (csobj.getType() == PDFObject.NAME) {
            name = csobj.getStringValue();

            if (name.equals("DeviceGray") || name.equals("G")) {
                return getColorSpace(COLORSPACE_GRAY);
            } else if (name.equals("DeviceRGB") || name.equals("RGB")) {
                return getColorSpace(COLORSPACE_RGB);
            } else if (name.equals("DeviceCMYK") || name.equals("CMYK")) {
                return getColorSpace(COLORSPACE_CMYK);
            } else if (name.equals("Pattern")) {
                return getColorSpace(COLORSPACE_PATTERN);
            } else if (colorSpaces != null) {
                csobj = colorSpaces.getDictRef(name);
            }
        }

        if (csobj == null) {
            return null;
        } else if (csobj.getCache() != null) {
            return (PDFColorSpace) csobj.getCache();
        }

        PDFColorSpace value = null;

        // csobj is [/name <<dict>>]
        final PDFObject[] ary = csobj.getArray();
        name = ary[0].getStringValue();

        switch (name) {
            case "DeviceGray":
            case "G":
                return getColorSpace(COLORSPACE_GRAY);
            case "DeviceRGB":
            case "RGB":
                return getColorSpace(COLORSPACE_RGB);
            case "DeviceCMYK":
            case "CMYK":
                return getColorSpace(COLORSPACE_CMYK);
            case "CalGray":
                value = new PDFColorSpace(new CalGrayColor(ary[1]));
                break;
            case "CalRGB":
                value = new PDFColorSpace(new CalRGBColor(ary[1]));
                break;
            case "Lab":
                value = new PDFColorSpace(new LabColor(ary[1]));
                break;
            case "ICCBased":
                try {
                    final ByteArrayInputStream bais = new ByteArrayInputStream(ary[1].getStream());
                    final ICC_Profile profile = ICC_Profile.getInstance(bais);
                    if (profile.getColorSpaceType() == ColorSpace.CS_GRAY || profile.getColorSpaceType() == ColorSpace.TYPE_GRAY) {
                        return graySpace;
                    }
                    value = new PDFColorSpace(new ICC_ColorSpace(profile));
                } catch (final IllegalArgumentException e) {
                    return getColorSpace(COLORSPACE_RGB);
                }
                break;
            case "Separation":
            case "DeviceN":
                final PDFColorSpace alternate = getColorSpace(ary[2], resources);
                final PDFFunction function = PDFFunction.getFunction(ary[3]);
                value = new AlternateColorSpace(alternate, function);
                break;
            case "Indexed":
            case "I":
                /**
                 * 4.5.5 [/Indexed baseColor hival lookup]
                 */
                final PDFColorSpace refspace = getColorSpace(ary[1], resources);

                // number of indices= ary[2], data is in ary[3];
                final int count = ary[2].getIntValue();
                try {
                    value = new IndexedColor(refspace, count, ary[3]);
                } catch (final Exception e) {
                    // there might be problems in reading the colorspace from stream,
                    // in that case use the reference colorspace
                    value = refspace;
                }

                break;
            case "Pattern":
                if (ary.length == 1) {
                    return getColorSpace(COLORSPACE_PATTERN);
                }

                final PDFColorSpace base = getColorSpace(ary[1], resources);

                return new PatternSpace(base);
            default:
                // removed access to ary[1] dur to index out of bounds exceptions
                throw new PDFParseException("Unknown color space: " + name);
        }

        csobj.setCache(value);

        return value;
    }

    /**
     * get the number of components expected in the getPaint command
     *
     * @return a int.
     */
    public int getNumComponents() {
        return this.cs.getNumComponents();
    }

    /**
     * get the PDFPaint representing the color described by the
     * given color components
     *
     * @param components the color components corresponding to the given
     *                   colorspace
     * @return a PDFPaint object representing the closest Color to the
     * given components.
     */
    public PDFPaint getPaint(final float[] components) {
        final float[] rgb = this.cs.toRGB(components);

        return PDFPaint.getColorPaint(new Color(rgb[0], rgb[1], rgb[2]));
    }

    /**
     * get the original Java ColorSpace.
     *
     * @return a {@link java.awt.color.ColorSpace} object.
     */
    public ColorSpace getColorSpace() {
        return this.cs;
    }
}
