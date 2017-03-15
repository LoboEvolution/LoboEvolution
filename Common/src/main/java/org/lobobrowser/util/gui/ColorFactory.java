/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 17, 2005
 */
package org.lobobrowser.util.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.util.ColorCommon;

/**
 * A factory for creating Color objects.
 *
 * @author J. H. S.
 */
public class ColorFactory {
    /** The Constant logger. */
    private static final Logger logger = LogManager
            .getLogger(ColorFactory.class);
    /** The instance. */
    private static ColorFactory instance;
    /** The Constant TRANSPARENT. */
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    /** The color map. */
    private Map<String, Color> colorMap = new HashMap<String, Color>(510);
    /** The Constant RGB_START. */
    public static final String RGB_START = "rgb(";
    /** The Constant RGBA_START. */
    public static final String RGBA_START = "rgba(";
    
    /**
     * Instantiates a new color factory.
     */
    private ColorFactory() {
        synchronized (this) {
            this.colorMap = ColorCommon.mapColor();
        }
    }
    
    /** Gets the instance.
	 *
	 * @return the instance
	 */
    public static final ColorFactory getInstance() {
        if (instance == null) {
            synchronized (ColorFactory.class) {
                if (instance == null) {
                    instance = new ColorFactory();
                }
            }
        }
        return instance;
    }
    
    /**
     * Checks if is color.
     *
     * @param colorSpec
     *            the color spec
     * @return true, if is color
     */
    public boolean isColor(String colorSpec) {
        if (colorSpec.startsWith("#")) {
            return true;
        }
        String normalSpec = colorSpec.toLowerCase();
        if (normalSpec.startsWith(RGBA_START)) {
            return true;
        } else if (normalSpec.startsWith(RGB_START)) {
            return true;
        }
        synchronized (this) {
            return colorMap.containsKey(normalSpec);
        }
    }
    
    /**
     * Gets the color.
     *
     * @param colorSpec
     *            the color spec
     * @return the color
     */
    public Color getColor(String colorSpec) {
        String normalSpec = colorSpec.toLowerCase();
        synchronized (this) {
            Color color = colorMap.get(normalSpec);
            if (color == null) {
                int red = 0, green = 0, blue = 0, alpha = 0;
                if (normalSpec.startsWith(RGBA_START)) {
                    int endIdx = normalSpec.lastIndexOf(')');
                    String commaValues = endIdx == -1
                            ? normalSpec.substring(RGBA_START.length())
                            : normalSpec.substring(RGBA_START.length(), endIdx);
                    StringTokenizer tok = new StringTokenizer(commaValues, ",");
                    if (tok.hasMoreTokens()) {
                        String rstr = tok.nextToken().trim();
                        try {
                            red = Integer.parseInt(rstr);
                        } catch (NumberFormatException nfe) {
                            // ignore
                        }
                        if (tok.hasMoreTokens()) {
                            String gstr = tok.nextToken().trim();
                            try {
                                green = Integer.parseInt(gstr);
                            } catch (NumberFormatException nfe) {
                                // ignore
                            }
                            if (tok.hasMoreTokens()) {
                                String bstr = tok.nextToken().trim();
                                try {
                                    blue = Integer.parseInt(bstr);
                                } catch (NumberFormatException nfe) {
                                    // ignore
                                }
                                if (tok.hasMoreTokens()) {
                                    String astr = tok.nextToken().trim();
                                    try {
                                        alpha = new Float(
                                                255 * Float.parseFloat(astr))
                                                        .intValue();
                                    } catch (NumberFormatException nfe) {
                                        // ignore
                                    }
                                }
                            }
                        }
                    }
                    color = new Color(normalize(red), normalize(green),
                            normalize(blue), alpha);
                } else if (normalSpec.startsWith(RGB_START)) {
                    // CssParser produces this format.
                    int endIdx = normalSpec.lastIndexOf(')');
                    String commaValues = endIdx == -1
                            ? normalSpec.substring(RGB_START.length())
                            : normalSpec.substring(RGB_START.length(), endIdx);
                    StringTokenizer tok = new StringTokenizer(commaValues, ",");
                    if (tok.hasMoreTokens()) {
                        String rstr = tok.nextToken().trim();
                        try {
                            red = Integer.parseInt(rstr);
                        } catch (NumberFormatException nfe) {
                            // ignore
                        }
                        if (tok.hasMoreTokens()) {
                            String gstr = tok.nextToken().trim();
                            try {
                                green = Integer.parseInt(gstr);
                            } catch (NumberFormatException nfe) {
                                // ignore
                            }
                            if (tok.hasMoreTokens()) {
                                String bstr = tok.nextToken().trim();
                                try {
                                    blue = Integer.parseInt(bstr);
                                } catch (NumberFormatException nfe) {
                                    // ignore
                                }
                            }
                        }
                    }
                    color = new Color(normalize(red), normalize(green), normalize(blue));
                } else if (normalSpec.startsWith("#")) {
                	String hexText = normalSpec.split("#")[1];
                    color = new Color(Integer.parseInt(hexText,16));
                } else {
                    if (logger.isInfoEnabled()) {
                        logger.warn("getColor(): Color spec [" + normalSpec
                                + "] unknown.");
                    }
                    return null;
                }
                colorMap.put(normalSpec, color);
            }
            return color;
        }
    }
    
    /**
     * Normalize color component within allow range 0..255
     *
     * @param colorComponent
     *            any value
     * @return 0 <= value <= 255
     */
    private static int normalize(int colorComponent) {
        if (colorComponent > 255) {
            colorComponent = 255;
        } else if (colorComponent < 0) {
            colorComponent = 0;
        }
        return colorComponent;
    }
}
