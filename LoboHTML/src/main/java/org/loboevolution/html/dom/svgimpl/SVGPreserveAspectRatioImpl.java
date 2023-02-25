/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPreserveAspectRatio;

/**
 * <p>SVGPreserveAspectRatioImpl class.</p>
 */
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
    public SVGPreserveAspectRatioImpl(SVGPreserveAspectRatio preserveAspectRatio) {
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
    public void setAlign(short align) {
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
    public void setMeetOrSlice(short meetOrSlice) {
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
    public static short getAlignConst(String alignString) {

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
    public static short getMeetOrSliceConst(String meetOrSliceString) {
        if (meetOrSliceString.equalsIgnoreCase("meet")) {
            return SVG_MEETORSLICE_MEET;
        }
        if (meetOrSliceString.equalsIgnoreCase("slice")) {
            return SVG_MEETORSLICE_SLICE;
        }
        System.out.println("Cannot decode preserveAspectRatio meetOrSlice value: " + meetOrSliceString
                + ", returning default value MEET");
        return SVG_MEETORSLICE_MEET;
    }

    public static String getAlignString(short align) {
        switch (align) {
            case SVG_PRESERVEASPECTRATIO_NONE:
                return "none";
            case SVG_PRESERVEASPECTRATIO_XMINYMIN:
                return "xMinYMin";
            case SVG_PRESERVEASPECTRATIO_XMIDYMIN:
                return "xMidYMin";
            case SVG_PRESERVEASPECTRATIO_XMAXYMIN:
                return "xMaxYMin";
            case SVG_PRESERVEASPECTRATIO_XMINYMID:
                return "xMinYMid";
            case SVG_PRESERVEASPECTRATIO_XMIDYMID:
                return "xMidYMid";
            case SVG_PRESERVEASPECTRATIO_XMAXYMID:
                return "xMinYMax";
            case SVG_PRESERVEASPECTRATIO_XMINYMAX:
                return "xMidYMax";
            case SVG_PRESERVEASPECTRATIO_XMIDYMAX:
                return "xMidYMax";
            case SVG_PRESERVEASPECTRATIO_XMAXYMAX:
                return "xMaxYMax";
            default:
                return "xMidYMid";
        }
    }

    public static String getMeetOrSliceString(short meetOrSlice) {
        switch (meetOrSlice) {
            case SVG_MEETORSLICE_MEET:
                return "meet";
            case SVG_MEETORSLICE_SLICE:
                return "slice";
            default:
                return "meet";
        }
    }

    @Override
    public String toString() {
        return getAlignString(align) + " " + getMeetOrSliceString(meetOrSlice);
    }

}
