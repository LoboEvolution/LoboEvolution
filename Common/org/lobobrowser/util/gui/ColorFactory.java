/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.util.ColorCommon;

/**
 * @author J. H. S.
 */
public class ColorFactory {
	private static final Logger logger = Logger.getLogger(ColorFactory.class.getName());
	private static ColorFactory instance;
	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
	private Map<String, Color> colorMap = new HashMap<String, Color>(510);
	private static final String RGB_START = "rgb(";
	private static final String RGBA_START = "rgba(";

	private ColorFactory() {
		synchronized (this) {
			this.colorMap = ColorCommon.mapColor();
		}
	}

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

	public Color getColor(String colorSpec) {
		String normalSpec = colorSpec.toLowerCase();

		synchronized (this) {
			Color color = (Color) colorMap.get(normalSpec);
			if (color == null) {
				int red = 0, green = 0, blue = 0, alpha = 0;
				
				if (normalSpec.startsWith(RGBA_START)) {
					int endIdx = normalSpec.lastIndexOf(')');
					String commaValues = endIdx == -1 ? normalSpec
							.substring(RGBA_START.length()) : normalSpec
							.substring(RGBA_START.length(), endIdx);
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
										alpha = new Float(255 * Float.parseFloat(astr)).intValue();
									} catch (NumberFormatException nfe) {
										// ignore
									}
								}
							}
						}
					}								
					color = new Color(normalize(red), normalize(green), normalize(blue),alpha);
				} else if (normalSpec.startsWith(RGB_START)) {
					// CssParser produces this format.
					int endIdx = normalSpec.lastIndexOf(')');
					String commaValues = endIdx == -1 ? normalSpec
							.substring(RGB_START.length()) : normalSpec
							.substring(RGB_START.length(), endIdx);
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
					int len = normalSpec.length();
					int[] rgba = new int[4];
					rgba[3] = 255;
					for (int i = 0; i < rgba.length; i++) {
						int idx = 2 * i + 1;
						if (idx < len) {
							String hexText = normalSpec.substring(idx, idx
									+ Math.min(2, len - idx));
							try {
								rgba[i] = Integer.parseInt(hexText, 16);
							} catch (NumberFormatException nfe) {
								// Ignore
							}
						}
					}
					color = new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
				} else {
					if (logger.isLoggable(Level.INFO)) {
						logger.warning("getColor(): Color spec [" + normalSpec
								+ "] unknown.");
					}
					return Color.RED;
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
