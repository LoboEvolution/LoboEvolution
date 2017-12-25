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
 * Created on Nov 19, 2005
 */
package org.lobobrowser.html.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.dombl.ImageEvent;
import org.lobobrowser.html.dombl.ImageListener;
import org.lobobrowser.html.dombl.SVGRasterizer;
import org.lobobrowser.html.domimpl.DOMEventImpl;
import org.lobobrowser.html.domimpl.HTMLImageElementImpl;

import org.lobobrowser.html.renderer.RElement;
import org.lobobrowser.html.renderer.RenderableSpot;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.util.SSLCertificate;
import org.lobobrowser.util.Urls;

/**
 * The Class ImgControl.
 */
public class ImgControl extends BaseControl implements ImageListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ImgControl.class.getName());

	/** The image. */
	private volatile Image image;

	/** The valign. */
	private int valign = RElement.VALIGN_BASELINE;

	/** The preferred size. */
	private Dimension preferredSize;

	/** The declared width. */
	private int declaredWidth;

	/** The declared height. */
	private int declaredHeight;

	/** The last src. */
	private String lastSrc;

	/** The align. */
	private String align;

	/** The alt. */
	private String alt;

	/** The image height. */
	private int imageWidth, imageHeight;

	/** The mouse being pressed. */
	private boolean mouseBeingPressed;

	/** The buffer image. */
	private BufferedImage bufferImage;

	/**
	 * Instantiates a new img control.
	 *
	 * @param modelNode
	 *            the model node
	 * @throws MalformedURLException
	 * @throws TranscoderException
	 * @throws IOException
	 */
	public ImgControl(HTMLImageElementImpl modelNode) {
		super(modelNode);

		align = modelNode.getAlign();
		alt = modelNode.getAlt() != null ? modelNode.getAlt() : "";
		bufferImage = null;

		modelNode.addImageListener(this);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseBeingPressed = true;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mouseBeingPressed = false;
				repaint();
				DOMEventImpl.getInstance().onPressed(modelNode, e, e.getX(), e.getY());
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON3) {
					DOMEventImpl.getInstance().onContextMenu(modelNode, e, e.getX(), e.getY());
				}
			}
		});

		if (modelNode.getSrc() != null) {
			URL u = null;
			try {
				SSLCertificate.setCertificate();
				URL baseURL = new URL(modelNode.getOwnerDocument().getBaseURI());
				URL scriptURL = Urls.createURL(baseURL, modelNode.getSrc());
				String scriptURI = scriptURL == null ? modelNode.getSrc() : scriptURL.toExternalForm();

				u = new URL(scriptURI.replace(" ", "%20"));
				URLConnection con = u.openConnection();
				con.setRequestProperty("User-Agent", UserAgentContext.DEFAULT_USER_AGENT);

				if (scriptURI.endsWith(".svg")) {
					SVGRasterizer r = new SVGRasterizer(u);
					image = r.bufferedImageToImage();
				} else if (scriptURI.startsWith("https")) {
					image = Toolkit.getDefaultToolkit().createImage(ImageIO.read(con.getInputStream()).getSource());
				} else if (scriptURI.endsWith(".gif")) {
					try {
						image = new ImageIcon(u).getImage();
					} catch (Exception e) {
						image = ImageIO.read(con.getInputStream());
					}
				} else if (scriptURI.endsWith(".bmp")) {
					try {
						image = ImageIO.read(con.getInputStream());
					} catch (IOException e) {
						logger.error("read error: " + e.getMessage());
					}
				} else {
					image = ImageIO.read(con.getInputStream());
				}
			} catch (FileNotFoundException | IIOException ex) {
				logger.error("ImgControl(): Image not found " + modelNode.getSrc());
			} catch (TranscoderException | IOException e1) {
				logger.error(e1.getMessage());
			} catch (Exception e1) {
				logger.error("ImgControl(): Image not found " + modelNode.getSrc());
			}
		}

		if (modelNode.getHeight() > 0) {
			imageHeight = modelNode.getHeight();
		} else {
			if (image != null) {
				imageHeight = image.getHeight(this);
			}
		}

		if (modelNode.getWidth() > 0) {
			imageWidth = modelNode.getWidth();
		} else {
			if (image != null) {
				imageWidth = image.getWidth(this);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			int x = (getWidth() - imageWidth) / 2;
			int y = (getHeight() - imageHeight) / 2;
			if (bufferImage == null) {
				g.drawImage(image, x, y, imageWidth, imageHeight, this);
			} else {
				g.drawImage(bufferImage, x, y, imageWidth, imageHeight, this);
			}
		} else {
			g.drawString(alt, 10, 10);
		}

		if (this.mouseBeingPressed) {
			Color over = new Color(255, 100, 100, 64);
			if (over != null) {
				Color oldColor = g.getColor();
				try {
					g.setColor(over);
					g.fillRect(0, 0, imageWidth, imageHeight);
				} finally {
					g.setColor(oldColor);
				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		if (image == null) {
			return super.getPreferredSize();
		} else {
			return new Dimension(imageWidth, imageHeight);
		}
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		// Expected in the GUI thread.
		int dw = HtmlValues.getPixelSize(String.valueOf(imageWidth), null, -1, availWidth);
		int dh = HtmlValues.getPixelSize(String.valueOf(imageHeight), null, -1, availHeight);
		this.declaredWidth = dw;
		this.declaredHeight = dh;
		this.preferredSize = this.createPreferredSize(dw, dh);
		int valign;
		if (align == null) {
			valign = RElement.VALIGN_BASELINE;
		} else {
			align = align.toLowerCase().trim();
			switch (align) {
			case "middle":
				valign = RElement.VALIGN_MIDDLE;
				break;
			case "absmiddle":
				valign = RElement.VALIGN_ABSMIDDLE;
				break;
			case "top":
				valign = RElement.VALIGN_TOP;
				break;
			case "bottom":
				valign = RElement.VALIGN_BOTTOM;
				break;
			case "baseline":
				valign = RElement.VALIGN_BASELINE;
				break;
			case "absbottom":
				valign = RElement.VALIGN_ABSBOTTOM;
				break;
			default:
				valign = RElement.VALIGN_BASELINE;
				break;
			}
		}
		this.valign = valign;
	}

	@Override
	public int getVAlign() {
		return this.valign;
	}

	/**
	 * Creates the preferred size.
	 *
	 * @param dw
	 *            the dw
	 * @param dh
	 *            the dh
	 * @return the dimension
	 */
	public Dimension createPreferredSize(int dw, int dh) {
		Image img = this.image;
		if (dw == -1) {
			if (dh != -1) {
				int iw = img == null ? -1 : img.getWidth(this);
				int ih = img == null ? -1 : img.getHeight(this);
				if (ih == 0) {
					dw = iw == -1 ? 0 : iw;
				} else if (iw == -1 || ih == -1) {
					dw = 0;
				} else {
					dw = dh * iw / ih;
				}
			} else {
				dw = img == null ? -1 : img.getWidth(this);
				if (dw == -1) {
					dw = 0;
				}
			}
		}
		if (dh == -1) {
			if (dw != -1) {
				int iw = img == null ? -1 : img.getWidth(this);
				int ih = img == null ? -1 : img.getHeight(this);
				if (iw == 0) {
					dh = ih == -1 ? 0 : ih;
				} else if (iw == -1 || ih == -1) {
					dh = 0;
				} else {
					dh = dw * ih / iw;
				}
			} else {
				dh = img == null ? -1 : img.getHeight(this);
				if (dh == -1) {
					dh = 0;
				}
			}
		}
		return new Dimension(dw, dh);
	}

	/**
	 * Check preferred size change.
	 *
	 * @return true, if successful
	 */
	private final boolean checkPreferredSizeChange() {
		Dimension newPs = this.createPreferredSize(this.declaredWidth, this.declaredHeight);
		Dimension ps = this.preferredSize;
		if (ps == null) {
			return true;
		}
		if (ps.width != newPs.width || ps.height != newPs.height) {
			this.preferredSize = newPs;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, final int w, final int h) {
		if ((infoflags & ImageObserver.ALLBITS) != 0 || (infoflags & ImageObserver.FRAMEBITS) != 0) {
			if (SwingUtilities.isEventDispatchThread()) {
				if (!checkPreferredSizeChange()) {
					repaint();
				} else {
					if (ruicontrol != null) {
						ruicontrol.preferredSizeInvalidated();
					}
				}
			} else {
				SwingUtilities.invokeLater(() -> {
					if (!checkPreferredSizeChange()) {
						repaint();
					} else {
						if (ruicontrol != null) {
							ruicontrol.preferredSizeInvalidated();
						}
					}
				});
			}
		}
		return true;
	}

	/**
	 * Image update.
	 *
	 * @param img
	 *            the img
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public void imageUpdate(Image img, final int w, final int h) {
		if (SwingUtilities.isEventDispatchThread()) {
			if (!checkPreferredSizeChange()) {
				repaint();
			} else {
				if (ruicontrol != null) {
					ruicontrol.preferredSizeInvalidated();
				}
			}
		} else {
			SwingUtilities.invokeLater(() -> {
				if (!checkPreferredSizeChange()) {
					repaint();
				} else {
					if (ruicontrol != null) {
						ruicontrol.preferredSizeInvalidated();
					}
				}
			});
		}
	}

	/**
	 * Paint selection.
	 *
	 * @param g
	 *            the g
	 * @param inSelection
	 *            the in selection
	 * @param startPoint
	 *            the start point
	 * @param endPoint
	 *            the end point
	 * @return true, if successful
	 */
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		return inSelection;
	}

	@Override
	public void imageLoaded(ImageEvent event) {
		Image image = event.image;
		this.image = image;
		int width = image.getWidth(this);
		int height = image.getHeight(this);
		if (width != -1 && height != -1) {
			this.imageUpdate(image, width, height);
		}
	}

	@Override
	public String toString() {
		return "ImgControl[src=" + this.lastSrc + "]";
	}
}
