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

import org.loboevolution.pdfview.BaseWatchable;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A ColorSpace for the CMYK color space.
 * <p>
 * Take from net.sf.jcgm.core.CMYKColorSpace.java
 * <p>
 * Author XOND
 *
 * @version $Id: CMYKColorSpace.java,v 1.2 2011-01-06 12:12:21 xond Exp $
 */
public class CMYKColorSpace extends ColorSpace {

    private ICC_Profile icc;
    private ICC_ColorSpace icc_cs;

    /**
     * Create a new CMYKColorSpace Instance.
     */
    public CMYKColorSpace() {
        super(ColorSpace.TYPE_CMYK, 4);
        try {
            final URL resource = CMYKColorSpace.class.getResource("/org/monte/media/jpeg/Generic_CMYK_Profile.icc");
            try (final InputStream stream = resource.openStream()) {
                icc = ICC_Profile.getInstance(stream);
                icc_cs = new ICC_ColorSpace(icc);
            }
        } catch (final IOException e) {
            BaseWatchable.getErrorHandler().publishException(e);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Converts to CMYK from CIEXYZ. We cheat here, using the RGB colorspace
     * to do the math for us. The toCIEXYZ function has a description of how
     * this is supposed to work, which may be implemented in the future.
     *
     * @see java.awt.color.ColorSpace#fromCIEXYZ(float[])
     */
    @Override
    public float[] fromCIEXYZ(final float[] p_colorvalue) {
        if (icc_cs != null) {
            return icc_cs.fromCIEXYZ(p_colorvalue);
        }
        final ColorSpace l_cs = ColorSpace.getInstance(ColorSpace.TYPE_RGB);
        final float[] l_rgb = l_cs.toCIEXYZ(p_colorvalue);
        return fromRGB(l_rgb);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Converts a given RGB to CMYK. RGB doesn't really use black, so K will
     * always be 0. On printers, the black should actually look dark brown.
     * RGB (an additive space) is simply the backwards from CMY (a subtractive
     * space), so all we do is:
     * <p>
     * C = 1-R
     * M = 1-G
     * Y = 1-B
     *
     * @see java.awt.color.ColorSpace#fromRGB(float[])
     */
    @Override
    public float[] fromRGB(final float[] p_rgbvalue) {
        if (icc_cs != null) {
            return icc_cs.fromRGB(p_rgbvalue);
        }

        /* TODO: Maybe we should do a better job to determine when black should
         * be used and pulled out? -- At this time, it's not necessary for our
         * (Scantegrity's) uses.
         */
        final float[] l_res = {0, 0, 0, 0};
        if (p_rgbvalue.length >= 3) {
            l_res[0] = (float) 1.0 - p_rgbvalue[0];
            l_res[1] = (float) 1.0 - p_rgbvalue[1];
            l_res[2] = (float) 1.0 - p_rgbvalue[2];
        }
        return normalize(l_res);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Converts the CMYK color to CIEXYZ. Because CIEXYZ is 3-component, we
     * cheat, converting to RGB and then using the RGB colorspace function
     * to do the conversion. Details on this colorspace are available on
     * wikipedia:
     * <p>
     * http://en.wikipedia.org/wiki/CIE_XYZ_color_space
     * <p>
     * There is also an "ideal relationship" to CMYK, which might be implemented
     * in the future (don't recall the reference we got this from, probably
     * color.org):
     * <p>
     * C = (C' - K)/(1 - K)
     * M = (M' - K)/(1 - K)
     * Y = (Y' - K)/(1 - K)
     * K = Min(C', M', Y')
     * <p>
     * X   41.2453 35.7580 18.0423 | 1-C'
     * Y = 21.2671 71.5160 07.2169 | 1-M'
     * Z   01.9334 11.9193 95.0227 | 1-Y'
     *
     * @see java.awt.color.ColorSpace#toCIEXYZ(float[])
     */
    @Override
    public float[] toCIEXYZ(final float[] p_colorvalue) {
        if (icc_cs != null) {
            return icc_cs.toCIEXYZ(p_colorvalue);
        }

        final float[] l_rgb = toRGB(p_colorvalue);
        final ColorSpace l_cs = ColorSpace.getInstance(ColorSpace.TYPE_RGB);
        return l_cs.toCIEXYZ(l_rgb);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Converts CMYK colors to RGB. Note that converting back will be lossy. The
     * formula for this is:
     * <p>
     * K = 1 - K (go to additive)
     * R = K * (1 - C)
     * G = K * (1 - M)
     * B = K * (1 - Y)
     *
     * @see java.awt.color.ColorSpace#toRGB(float[])
     */
    @Override
    public float[] toRGB(final float[] p_colorvalue) {
        if (icc_cs != null) {
            return icc_cs.toRGB(p_colorvalue);
        }
        final float[] l_res = {0, 0, 0};
        if (p_colorvalue.length >= 4) {
            final float l_black = (float) 1.0 - p_colorvalue[3];
            l_res[0] = l_black * ((float) 1.0 - p_colorvalue[0]);
            l_res[1] = l_black * ((float) 1.0 - p_colorvalue[1]);
            l_res[2] = l_black * ((float) 1.0 - p_colorvalue[2]);
        }
        return normalize(l_res);
    }

    /**
     * Normalize ensures all color values returned are between 0 and 1.
     *
     * @param p_colors an array of {@link float} objects.
     * @return p_colors, with any values greater than 1 set to 1, and less than
     * 0 set to 0.
     */
    public float[] normalize(final float[] p_colors) {
        for (int l_i = 0; l_i < p_colors.length; l_i++) {
            if (p_colors[l_i] > (float) 1.0) p_colors[l_i] = (float) 1.0;
            else if (p_colors[l_i] < (float) 0.0) p_colors[l_i] = (float) 0.0;
        }
        return p_colors;
    }
}
