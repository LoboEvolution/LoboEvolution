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

package org.loboevolution.svg.dom;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.svg.SVGPreserveAspectRatio;

/**
 * <p>SVGPreserveAspectRatioImpl class.</p>
 */
@Slf4j
public class SVGPreserveAspectRatioImpl implements SVGPreserveAspectRatio {

    protected short align;
    protected short meetOrSlice;

    /**
     * Constructs a new SVGPreserveAspectRatioImpl. The align attribute is
     * initialized to SVG_PRESERVEASPECTRATIO_NONE and meetOrSlice is
     * initialized to SVG_MEETORSLICE_MEET.
     */
    public SVGPreserveAspectRatioImpl() {
        align = SVG_PRESERVEASPECTRATIO_XMIDYMID;
        meetOrSlice = SVG_MEETORSLICE_MEET;
    }

    /**
     * Copy constructor.
     */
    public SVGPreserveAspectRatioImpl(final SVGPreserveAspectRatio preserveAspectRatio) {
        this.align = preserveAspectRatio.getAlign();
        this.meetOrSlice = preserveAspectRatio.getMeetOrSlice();
    }

    /**
     * Returns the alignment value as specified by one of the
     * SVG_PRESERVEASPECTRATIO constants.
     *
     * @return The alignement value.
     */
    @Override
    public short getAlign() {
        return align;
    }

    /**
     * Sets the alignment value as specified by one of the
     * SVG_PRESERVEASPECTRATIO constants.
     *
     * @param align
     *            The value to set the alginment to.
     */
    @Override
    public void setAlign(final short align) {
        this.align = align;
    }

    /**
     * Returns the meetOrSlice value as specified by one of the SVG_MEETORSLICE
     * constants.
     *
     * @return The meetOrSlice value.
     */
    @Override
    public short getMeetOrSlice() {
        return meetOrSlice;
    }

    /**
     * Sets the meetOrSlice value as specified by one of the SVG_MEETORSLICE
     * constants.
     *
     * @param meetOrSlice
     *            The value to set the meetOrSlice to.
     */
    @Override
    public void setMeetOrSlice(final short meetOrSlice) {
        this.meetOrSlice = meetOrSlice;
    }

    /**
     * Returns the appropriate SVGPreserveAspectRatio constanct for the given
     * alignment string.
     *
     * @param alignString
     *            The alignment string.
     * @return The alignment constant.
     */
    public static short getAlignConst(final String alignString) {

        if (alignString.equalsIgnoreCase("none")) {
            return SVG_PRESERVEASPECTRATIO_NONE;
        }
        if (alignString.equalsIgnoreCase("xMinYMin")) {
            return SVG_PRESERVEASPECTRATIO_XMINYMIN;
        }
        if (alignString.equalsIgnoreCase("xMidYMin")) {
            return SVG_PRESERVEASPECTRATIO_XMIDYMIN;
        }
        if (alignString.equalsIgnoreCase("xMaxYMin")) {
            return SVG_PRESERVEASPECTRATIO_XMAXYMIN;
        }
        if (alignString.equalsIgnoreCase("xMinYMid")) {
            return SVG_PRESERVEASPECTRATIO_XMINYMID;
        }
        if (alignString.equalsIgnoreCase("xMidYMid")) {
            return SVG_PRESERVEASPECTRATIO_XMIDYMID;
        }
        if (alignString.equalsIgnoreCase("xMaxYMid")) {
            return SVG_PRESERVEASPECTRATIO_XMAXYMID;
        }
        if (alignString.equalsIgnoreCase("xMinYMax")) {
            return SVG_PRESERVEASPECTRATIO_XMINYMAX;
        }
        if (alignString.equalsIgnoreCase("xMidYMax")) {
            return SVG_PRESERVEASPECTRATIO_XMIDYMAX;
        }
        if (alignString.equalsIgnoreCase("xMaxYMax")) {
            return SVG_PRESERVEASPECTRATIO_XMAXYMAX;
        }
        return SVG_PRESERVEASPECTRATIO_NONE;
    }

    /**
     * Returns the appropriate SVGPreserveAspectRatio constanct for the given
     * meetOrSlice string.
     *
     * @param meetOrSliceString
     *            The meetOrSlice string.
     * @return The meetOrSlice constant.
     */
    public static short getMeetOrSliceConst(final String meetOrSliceString) {
        if (meetOrSliceString.equalsIgnoreCase("meet")) {
            return SVG_MEETORSLICE_MEET;
        }
        if (meetOrSliceString.equalsIgnoreCase("slice")) {
            return SVG_MEETORSLICE_SLICE;
        }
        log.info("Cannot decode preserveAspectRatio meetOrSlice value: {} , returning default value MEET",  meetOrSliceString);
        return SVG_MEETORSLICE_MEET;
    }

    public static String getAlignString(final short align) {
        return switch (align) {
            case SVG_PRESERVEASPECTRATIO_NONE -> "none";
            case SVG_PRESERVEASPECTRATIO_XMINYMIN -> "xMinYMin";
            case SVG_PRESERVEASPECTRATIO_XMIDYMIN -> "xMidYMin";
            case SVG_PRESERVEASPECTRATIO_XMAXYMIN -> "xMaxYMin";
            case SVG_PRESERVEASPECTRATIO_XMINYMID -> "xMinYMid";
            case SVG_PRESERVEASPECTRATIO_XMIDYMID -> "xMidYMid";
            case SVG_PRESERVEASPECTRATIO_XMAXYMID -> "xMinYMax";
            case SVG_PRESERVEASPECTRATIO_XMINYMAX -> "xMidYMax";
            case SVG_PRESERVEASPECTRATIO_XMIDYMAX -> "xMidYMax";
            case SVG_PRESERVEASPECTRATIO_XMAXYMAX -> "xMaxYMax";
            default -> "xMidYMid";
        };
    }

    public static String getMeetOrSliceString(final short meetOrSlice) {
        return switch (meetOrSlice) {
            case SVG_MEETORSLICE_MEET -> "meet";
            case SVG_MEETORSLICE_SLICE -> "slice";
            default -> "meet";
        };
    }

    @Override
    public String toString() {
        return getAlignString(align) + " " + getMeetOrSliceString(meetOrSlice);
    }

}
