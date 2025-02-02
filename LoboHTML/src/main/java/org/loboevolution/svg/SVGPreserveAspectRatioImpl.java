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

package org.loboevolution.svg;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>SVGPreserveAspectRatioImpl class.</p>
 */
@Slf4j
@Getter
@Setter
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
     * Returns the appropriate SVGPreserveAspectRatio constanct for the given
     * alignment string.
     *
     * @param alignString The alignment string.
     * @return The alignment constant.
     */
    public static short getAlignConst(final String alignString) {
        return switch (alignString) {
            case "xMinYMin" -> SVG_PRESERVEASPECTRATIO_XMINYMIN;
            case "xMidYMin" -> SVG_PRESERVEASPECTRATIO_XMIDYMIN;
            case "xMaxYMin" -> SVG_PRESERVEASPECTRATIO_XMAXYMIN;
            case "xMinYMid" -> SVG_PRESERVEASPECTRATIO_XMINYMID;
            case "xMidYMid" -> SVG_PRESERVEASPECTRATIO_XMIDYMID;
            case "xMaxYMid" -> SVG_PRESERVEASPECTRATIO_XMAXYMID;
            case "xMinYMax" -> SVG_PRESERVEASPECTRATIO_XMINYMAX;
            case "xMidYMax" -> SVG_PRESERVEASPECTRATIO_XMIDYMAX;
            case "xMaxYMax" -> SVG_PRESERVEASPECTRATIO_XMAXYMAX;
            default -> SVG_PRESERVEASPECTRATIO_NONE;
        };
    }

    /**
     * Returns the appropriate SVGPreserveAspectRatio constanct for the given
     * meetOrSlice string.
     *
     * @param meetOrSliceString The meetOrSlice string.
     * @return The meetOrSlice constant.
     */
    public static short getMeetOrSliceConst(final String meetOrSliceString) {
        return "slice".equals(meetOrSliceString) ? SVG_MEETORSLICE_SLICE : SVG_MEETORSLICE_MEET;
    }

    public static String getAlignString(final short align) {
        return switch (align) {
            case SVG_PRESERVEASPECTRATIO_NONE -> "none";
            case SVG_PRESERVEASPECTRATIO_XMINYMIN -> "xMinYMin";
            case SVG_PRESERVEASPECTRATIO_XMIDYMIN -> "xMidYMin";
            case SVG_PRESERVEASPECTRATIO_XMAXYMIN -> "xMaxYMin";
            case SVG_PRESERVEASPECTRATIO_XMINYMID -> "xMinYMid";
            case SVG_PRESERVEASPECTRATIO_XMAXYMID -> "xMinYMax";
            case SVG_PRESERVEASPECTRATIO_XMINYMAX, SVG_PRESERVEASPECTRATIO_XMIDYMAX -> "xMidYMax";
            case SVG_PRESERVEASPECTRATIO_XMAXYMAX -> "xMaxYMax";
            default -> "xMidYMid";
        };
    }

    public static String getMeetOrSliceString(final short meetOrSlice) {
        return meetOrSlice == SVG_MEETORSLICE_SLICE ? "slice" : "meet";
    }

    @Override
    public String toString() {
        return getAlignString(align) + " " + getMeetOrSliceString(meetOrSlice);
    }

}
