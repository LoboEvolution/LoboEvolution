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
package org.lobobrowser.html.renderstate;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.dombl.SVGRasterizer;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.info.BackgroundInfo;
import org.lobobrowser.html.renderer.BaseElementRenderable;
import org.lobobrowser.html.style.CSSValuesProperties;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.util.SSLCertificate;
import org.lobobrowser.util.gui.ColorFactory;

public class BackgroundRenderState implements CSSValuesProperties {
	
	/** The Constant logger. */
	protected static final Logger logger = LogManager.getLogger(BackgroundRenderState.class.getName());
	
	/**
	 * Apply background vertical position.
	 *
	 * @param binfo
	 *            the binfo
	 * @param yposition
	 *            the yposition
	 */
	public BackgroundInfo applyBackgroundVerticalPosition(BackgroundInfo binfo, String yposition, RenderState prevRenderState) {
		BackgroundInfo bg = binfo;
		if (yposition.endsWith("%")) {
			bg.setBackgroundYPositionAbsolute(false);
			try {
				bg.setBackgroundYPosition(
						(int) Double.parseDouble(yposition.substring(0, yposition.length() - 1).trim()));
			} catch (NumberFormatException nfe) {
				bg.setBackgroundYPosition(0);
			}
		} else {

			switch (yposition) {
			case CENTER:
				bg.setBackgroundYPositionAbsolute(false);
				bg.setBackgroundYPosition(50);
				break;
			case RIGHT:
				bg.setBackgroundYPositionAbsolute(false);
				bg.setBackgroundYPosition(100);
				break;
			case LEFT:
				bg.setBackgroundYPositionAbsolute(false);
				bg.setBackgroundYPosition(0);
				break;
			case BOTTOM:
				bg.setBackgroundYPositionAbsolute(false);
				bg.setBackgroundYPosition(100);
				break;
			case TOP:
				bg.setBackgroundYPositionAbsolute(false);
				bg.setBackgroundYPosition(0);
				break;
			case INHERIT:
				BackgroundInfo bi = prevRenderState.getPreviousRenderState().getBackgroundInfo();
				if (bi != null) {
					bg.setBackgroundYPositionAbsolute(bi.isBackgroundYPositionAbsolute());
					bg.setBackgroundYPosition(bi.getBackgroundYPosition());
				}
				break;
			case INITIAL:
				bg.setBackgroundYPositionAbsolute(true);
				bg.setBackgroundYPosition(HtmlValues.getPixelSize(yposition, prevRenderState, 0));
				break;
			default:
				bg.setBackgroundYPositionAbsolute(true);
				bg.setBackgroundYPosition(HtmlValues.getPixelSize(yposition, prevRenderState, 0));
				break;
			}
		}
		return bg;
	}
		
	/**
	 * Apply background position.
	 *
	 * @param binfo
	 *            the binfo
	 * @param position
	 *            the position
	 */
	public BackgroundInfo applyBackgroundPosition(BackgroundInfo binfo, String position, RenderState prevRenderState) {
		BackgroundInfo bg = binfo;
		if (position != null) {
			bg.setBackgroundXPositionAbsolute(false);
			bg.setBackgroundYPositionAbsolute(false);
			bg.setBackgroundXPosition(50);
			bg.setBackgroundYPosition(50);
			StringTokenizer tok = new StringTokenizer(position, " \t\r\n");
			if (tok.hasMoreTokens()) {
				String xposition = tok.nextToken();
				bg = applyBackgroundHorizontalPositon(bg, xposition, prevRenderState);
				if (tok.hasMoreTokens()) {
					String yposition = tok.nextToken();
					bg = applyBackgroundVerticalPosition(bg, yposition, prevRenderState);
				}
			}
		}
		return bg;
	}
	
	/**
	 * Apply background repeat.
	 *
	 * @param binfo
	 *            the binfo
	 * @param backgroundRepeatText
	 *            the background repeat text
	 */
	public BackgroundInfo applyBackgroundRepeat(BackgroundInfo binfo, String back) {
		BackgroundInfo bg = binfo;
		if (back!= null && bg.getBackgroundRepeat() == BackgroundInfo.BR_REPEAT) {
			switch (back.toLowerCase()) {
			case REPEAT:
				bg.backgroundRepeat = BackgroundInfo.BR_REPEAT;
				break;
			case REPEAT_X:
				bg.backgroundRepeat = BackgroundInfo.BR_REPEAT_X;
				break;
			case REPEAT_Y:
				bg.backgroundRepeat = BackgroundInfo.BR_REPEAT_Y;
				break;
			case REPEAT_NO:
				bg.backgroundRepeat = BackgroundInfo.BR_NO_REPEAT;
				break;
			default:
				break;
			}
		}
		return bg;
	}
	
	public BackgroundInfo applyBackgroundImage(BackgroundInfo binfo, String back, HTMLDocumentImpl document) {
		BackgroundInfo bg = binfo;
		if (back!= null && back.contains("url") && bg.getBackgroundImage() == null) {
			String start = "url(";
			int startIdx = start.length();
			int closingIdx = back.lastIndexOf(')');
			String quotedUri = back.substring(startIdx, closingIdx);
			URL url = document.getFullURL(quotedUri);
			if (validateURL(url)) {
				bg.setBackgroundImage(url);
			} else {
				try {
					bg.setBackgroundImage(new URL(quotedUri.replace("'", "")));
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		return bg;
	}
	
	public BackgroundInfo applyBackground(BackgroundInfo binfo, String back, RenderState prevRenderState) {
		BackgroundInfo bg = binfo;	
		if (back != null) {
			switch (back.toLowerCase()) {
			case INHERIT:
				bg.setBackgroundColor(prevRenderState.getPreviousRenderState().getBackgroundColor());
				break;
			case INITIAL:
				bg.setBackgroundColor(Color.WHITE);
				break;
			default:
				Color c = ColorFactory.getInstance().getColor(back);
				if (c != null) {
					bg.setBackgroundColor(ColorFactory.getInstance().getColor(back));
				}
				break;
			}
		}
		return bg;
	}
	
	/**
	 * Load background image.
	 *
	 * @param imageURL
	 *            the image url
	 */
	public Image loadBackgroundImage(final URL imageURL, BaseElementRenderable ber) {
		Image image = null;
		String url = imageURL.toString();
		try {
			SSLCertificate.setCertificate();

			URLConnection con = imageURL.openConnection();
			con.setRequestProperty("User-Agent", UserAgentContext.DEFAULT_USER_AGENT);

			if (url.endsWith(".svg")) {
				SVGRasterizer r = new SVGRasterizer(imageURL);
				image = r.bufferedImageToImage();
			} else if (url.startsWith("https")) {
				BufferedImage bi = ImageIO.read(con.getInputStream());
				if (bi != null) {
					image = Toolkit.getDefaultToolkit().createImage(bi.getSource());
				}
			} else if (url.endsWith(".gif")) {
				try {
					image = new ImageIcon(imageURL).getImage();
				} catch (Exception e) {
					image = ImageIO.read(con.getInputStream());
				}
			} else if (url.endsWith(".bmp")) {
				image = ImageIO.read(con.getInputStream());
			} else {
				image = ImageIO.read(con.getInputStream());
			}

			

			int w = -1;
			int h = -1;
			if (image != null) {
				w = image.getWidth(ber);
				h = image.getHeight(ber);
			}

			if (w != -1 && h != -1) {
				ber.repaint();
			}
		} catch (FileNotFoundException | IIOException ex) {
			logger.error("loadBackgroundImage(): Image not found " + url);
		} catch (IOException | TranscoderException thrown) {
			logger.error("loadBackgroundImage()", thrown);
		} catch (Exception e) {
			logger.error("loadBackgroundImage()", e);
		}
		return image;
	}
	
	
	/**
	 * Apply background horizontal positon.
	 *
	 * @param binfo
	 *            the binfo
	 * @param xposition
	 *            the xposition
	 */
	private BackgroundInfo applyBackgroundHorizontalPositon(BackgroundInfo binfo, String xposition, RenderState prevRenderState) {
		BackgroundInfo bg = binfo;
		if (xposition.endsWith("%")) {
			bg.setBackgroundXPositionAbsolute(false);
			try {
				bg.setBackgroundXPosition((int) Double.parseDouble(xposition.substring(0, xposition.length() - 1).trim()));
			} catch (NumberFormatException nfe) {
				bg.setBackgroundXPosition(0);
			}
		} else {

			switch (xposition) {
			case CENTER:
				bg.setBackgroundXPositionAbsolute(false);
				bg.setBackgroundXPosition(50);
				break;
			case RIGHT:
				bg.setBackgroundXPositionAbsolute(false);
				bg.setBackgroundXPosition(100);
				break;
			case LEFT:
				bg.setBackgroundXPositionAbsolute(false);
				bg.setBackgroundXPosition(0);
				break;
			case BOTTOM:
				bg.setBackgroundYPositionAbsolute(false);
				bg.setBackgroundYPosition(100);
				break;
			case TOP:
				bg.setBackgroundYPositionAbsolute(false);
				bg.setBackgroundYPosition(0);
				break;
			case INHERIT:
				BackgroundInfo bi = prevRenderState.getPreviousRenderState().getBackgroundInfo();
				if (bi != null) {
					bg.setBackgroundXPositionAbsolute(bi.isBackgroundXPositionAbsolute());
					bg.setBackgroundXPosition(bi.getBackgroundXPosition());
				}
				break;
			case INITIAL:
				bg.setBackgroundXPositionAbsolute(true);
				bg.setBackgroundXPosition(HtmlValues.getPixelSize(xposition, prevRenderState, 0));
				break;
			default:
				bg.setBackgroundXPositionAbsolute(true);
				bg.setBackgroundXPosition(HtmlValues.getPixelSize(xposition, prevRenderState, 0));
				break;
			}
		}
		return bg;
	}
	
	/**
	 * Checks if is background repeat.
	 *
	 * @param repeat
	 *            the repeat
	 * @return true, if is background repeat
	 */
	public static boolean isBackgroundRepeat(String repeat) {
		String repeatTL = repeat.toLowerCase();
		return repeatTL.indexOf(REPEAT) != -1;
	}

	/**
	 * Checks if is background position.
	 *
	 * @param token
	 *            the token
	 * @return true, if is background position
	 */
	public static boolean isBackgroundPosition(String token) {
		return isLength(token) || token.endsWith("%") || token.equalsIgnoreCase(TOP) || token.equalsIgnoreCase(CENTER)
				|| token.equalsIgnoreCase(BOTTOM) || token.equalsIgnoreCase(LEFT) || token.equalsIgnoreCase(RIGHT);
	}
	
	/**
	 * Checks if is length.
	 *
	 * @param token
	 *            the token
	 * @return true, if is length
	 */
	private static boolean isLength(String token) {
		if (token.endsWith("px") || token.endsWith("pt") || token.endsWith("pc") || token.endsWith("em")
				|| token.endsWith("mm") || token.endsWith("ex") || token.endsWith("em")) {
			return true;
		}
		try {
			Double.parseDouble(token);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	private boolean validateURL(URL url){
		try {
		    URLConnection conn = url.openConnection();
		    conn.connect();
		    return true;
		} catch (Exception e) {
			return false;
		}
		
	}
}
