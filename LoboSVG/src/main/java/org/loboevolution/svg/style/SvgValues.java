package org.loboevolution.svg.style;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.svg.SVGLength;

public class SvgValues {

    public static boolean isUnits(final String token) {
        final String lowerToken = token.toLowerCase();
        return lowerToken.endsWith("px") ||
                lowerToken.endsWith("pt") ||
                lowerToken.endsWith("pc") ||
                lowerToken.endsWith("cm") ||
                lowerToken.endsWith("mm") ||
                lowerToken.endsWith("ex") ||
                lowerToken.endsWith("em") ||
                (lowerToken.endsWith("in")  && !lowerToken.startsWith("zoom") && !lowerToken.equals("thin")) ||
                lowerToken.endsWith("q") ||
                lowerToken.endsWith("vh") ||
                lowerToken.endsWith("vw") ||
                lowerToken.endsWith("deg") ||
                lowerToken.endsWith("rem");
    }

    public static String getUnitTypeAsString(final short unitType) {
        return switch (unitType) {
            case SVGLength.SVG_LENGTHTYPE_CM -> "cm";
            case SVGLength.SVG_LENGTHTYPE_EMS -> "ems";
            case SVGLength.SVG_LENGTHTYPE_EXS -> "exs";
            case SVGLength.SVG_LENGTHTYPE_IN -> "in";
            case SVGLength.SVG_LENGTHTYPE_MM -> "mm";
            case SVGLength.SVG_LENGTHTYPE_PC -> "pc";
            case SVGLength.SVG_LENGTHTYPE_PERCENTAGE -> "%";
            case SVGLength.SVG_LENGTHTYPE_PT -> "pt";
            case SVGLength.SVG_LENGTHTYPE_PX -> "px";
            case SVGLength.SVG_LENGTHTYPE_NUMBER -> "";
            case SVGLength.SVG_LENGTHTYPE_UNKNOWN -> "unknown";
            default -> throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid unit type");
        };
    }
}
