/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.renderstate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.dombl.SVGRasterizer;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.info.BackgroundInfo;
import org.loboevolution.html.renderer.BaseElementRenderable;
import org.loboevolution.html.style.CSSValuesProperties;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.SSLCertificate;
import org.loboevolution.util.Strings;
import org.loboevolution.util.Urls;
import org.loboevolution.util.gui.ColorFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		if (bg.getBackgroundRepeat() == BackgroundInfo.BR_REPEAT) {
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
		if (back.contains("url") && bg.getBackgroundImage() == null) {
			String start = "url(";
			int startIdx = start.length();
			int closingIdx = back.lastIndexOf(')');
			String quotedUri = back.substring(startIdx, closingIdx);
			URL url = null;
			bg = applyBackgroundImageFromExternalLink(document, bg, quotedUri);
			
			if (bg.getBackgroundImage() == null && bg.getBackgroundImageBase64() == null) {
				try {
					url = document.getFullURL(quotedUri);
					logger.error(url);
					bg.setBackgroundImage(url);
				} catch (Exception ex) {
					logger.error(ex.getMessage());
				}
			}
		}
		return bg;
	}
	
	public BackgroundInfo applyBackgroundImageFromExternalLink(HTMLDocumentImpl document, BackgroundInfo binfo, String quotedUri) {
		URL url = null;
		BackgroundInfo bg = binfo;
		NodeList nodeList = document.getElementsByTagName("LINK");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = (Node) nodeList.item(i);
			NamedNodeMap attribs = node.getAttributes();
			String href = "";
			String rel = "";

			for (int s = 0; s < attribs.getLength(); s++) {
				Attr attr = (Attr) attribs.item(s);

				if ("rel".equalsIgnoreCase(attr.getNodeName())) {
					rel = attr.getNodeValue();
				}

				if ("href".equalsIgnoreCase(attr.getNodeName())) {
					href = attr.getNodeValue();
				}

				if ("stylesheet".equalsIgnoreCase(rel) && !Strings.isBlank(href)) {
					
					if (quotedUri.contains(";base64,")) {
						String base64 = quotedUri.split(";base64,")[1];
						String type = quotedUri.split(";base64,")[0];
						if(type.contains("svg")) {
							byte[] decodedBytes = Base64.getDecoder().decode(base64);
							bg.setBackgroundImageBase64(new String(decodedBytes));
						} else {
							try {
								bg.setBackgroundImage(new URL(base64));
							} catch (Exception ex) {
								logger.error("Unable to create URL ", ex);
							}
						}
					} else {
						
						try {
							
							if(!Urls.isAbsolute(href)) {
								href = document.getFullURL(href).toString();
							}
							
							if (href.startsWith("//")) {
								href = "http:" + href;
							}							
							
							url = Urls.createURL(new URL(href), quotedUri);
							bg.setBackgroundImage(url);
							
						} catch (Exception e) {
							try {
								url = document.getFullURL(quotedUri);
								bg.setBackgroundImage(url);
							} catch (Exception ex) {
								logger.error(ex.getMessage());
							}

						}
					}
				}
			}
		}
		return bg;
	}
	
	
	
	public BackgroundInfo applyBackground(BackgroundInfo binfo, String back, RenderState prevRenderState) {
		BackgroundInfo bg = binfo;	
		Color c = ColorFactory.getInstance().getColor(back);
		if (c != null) {
			bg.setBackgroundColor(c);
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
		} catch (Exception e) {
			logger.error("loadBackgroundImage()", e);
		}
		return image;
	}
	
	public Image loadBackgroundImageBase64(final String img, BaseElementRenderable ber) {
		Image image = null;
		try {
			InputStream stream = new ByteArrayInputStream(img.getBytes());
			SVGRasterizer r = new SVGRasterizer(stream);
			image = r.bufferedImageToImage();

			int w = -1;
			int h = -1;
			if (image != null) {
				w = image.getWidth(ber);
				h = image.getHeight(ber);
			}

			if (w != -1 && h != -1) {
				ber.repaint();
			}
		} catch (Exception e) {
			logger.error("loadBackgroundImage()", e);
		}
		return image;
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
		return HtmlInsets.isLength(token) || token.endsWith("%") || token.equalsIgnoreCase(TOP) || token.equalsIgnoreCase(CENTER)
				|| token.equalsIgnoreCase(BOTTOM) || token.equalsIgnoreCase(LEFT) || token.equalsIgnoreCase(RIGHT);
	}
	
	public void backgroundNoRepeat(Graphics g, Image image, BackgroundInfo binfo, int w, int h, int totalWidth, int totalHeight, BaseElementRenderable elem) {
		int imageX;
		if (binfo.isBackgroundXPositionAbsolute()) {
			imageX = binfo.getBackgroundXPosition();
		} else {
			imageX = binfo.getBackgroundXPosition() * (totalWidth - w) / 100;
		}
		int imageY;
		if (binfo.isBackgroundYPositionAbsolute()) {
			imageY = binfo.getBackgroundYPosition();
		} else {
			imageY = binfo.getBackgroundYPosition() * (totalHeight - h) / 100;
		}
		g.drawImage(image, imageX, imageY, w, h, elem);
	}
	
	public void backgroundRepeatX(Graphics clientG, Image image, BackgroundInfo binfo, int w, int h, int totalWidth, int totalHeight, Rectangle bkgBounds, BaseElementRenderable elem) {
		int imageY;
		if (binfo.isBackgroundYPositionAbsolute()) {
			imageY = binfo.getBackgroundYPosition();
		} else {
			imageY = binfo.getBackgroundYPosition() * (totalHeight - h) / 100;
		}
		int x = bkgBounds.x / w * w;
		int topX = bkgBounds.x + bkgBounds.width;
		for (; x < topX; x += w) {
			clientG.drawImage(image, x, imageY, w, h, elem);
		}
		
	}

	public void backgroundRepeatY(Graphics clientG, Image image, BackgroundInfo binfo, int w, int h, int totalWidth, int totalHeight, Rectangle bkgBounds, BaseElementRenderable elem) {
		int imageX;
		if (binfo.isBackgroundXPositionAbsolute()) {
			imageX = binfo.getBackgroundXPosition();
		} else {
			imageX = binfo.getBackgroundXPosition() * (totalWidth - w) / 100;
		}
		// Modulate starting y.
		int y = bkgBounds.y / h * h;
		int topY = bkgBounds.y + bkgBounds.height;
		for (; y < topY; y += h) {
			clientG.drawImage(image, imageX, y, w, h, elem);
		}
	}

	public void backgroundRepeat(Graphics clientG, Image image, int w, int h, Rectangle bkgBounds, BaseElementRenderable elem) {
		// Modulate starting x and y.
		int baseX = bkgBounds.x / w * w;
		int baseY = bkgBounds.y / h * h;
		int topX = bkgBounds.x + bkgBounds.width;
		int topY = bkgBounds.y + bkgBounds.height;
		// Replacing this:
		for (int x = baseX; x < topX; x += w) {
			for (int y = baseY; y < topY; y += h) {
				clientG.drawImage(image, x, y, w, h, elem);
			}
		}
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
	
	public ArrayList<String> spliBackground(String backgroundText) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> backList = new ArrayList<String>(Arrays.asList(backgroundText.split("[\\)]")));
		for (String back : backList) {
			if (back.contains("(")) {
				back = back + ")";
				if (back.contains("url") && !back.startsWith("url")) {
					ArrayList<String> backList2 = new ArrayList<String>(Arrays.asList(back.split("\\s+")));
					for (String back2 : backList2) {
						list.add(back2.trim());
					}

				} else {
					list.add(back.trim());
				}
			} else if (!Strings.isBlank(back)) {
				ArrayList<String> backList2 = new ArrayList<String>(Arrays.asList(back.split("[\\s+]")));
				for (String back2 : backList2) {
					if (!Strings.isBlank(back2)) {
						list.add(back2.trim());
					}
				}
			}
		}
		return list;
	}
}
