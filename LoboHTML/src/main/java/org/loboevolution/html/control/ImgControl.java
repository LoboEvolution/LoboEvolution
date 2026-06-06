/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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
/*
 * Created on Nov 19, 2005
 */
package org.loboevolution.html.control;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.common.Strings;
import org.loboevolution.common.WrapperLayout;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.AlignValues;
import org.loboevolution.html.dom.HTMLAreaElement;
import org.loboevolution.html.dom.HTMLMapElement;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.renderer.HtmlController;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.net.HttpNetwork;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.io.Serial;

/**
 * <p>ImgControl class.</p>
 */
public class ImgControl extends BaseControl {

	@Serial
    private static final long serialVersionUID = 1L;

	private Image image;

    private final HTMLImageElementImpl modelNode;

	private final String alt;

	private Dimension preferredSize;

	private int valign = AlignValues.BASELINE.getValue();
	
	private boolean mouseBeingPressed;

	/**
	 * <p>Constructor for ImgControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLImageElementImpl} object.
	 */
	public ImgControl(final HTMLImageElementImpl modelNode) {
		super(modelNode);
        this.modelNode = modelNode;
		setLayout(WrapperLayout.getInstance());
		final UserAgentContext bcontext = modelNode.getUserAgentContext();
		alt = modelNode.getAlt() != null ? modelNode.getAlt() : "";
		final TimingInfo info = new TimingInfo();
		if (bcontext.isImagesEnabled()) {
			image = HttpNetwork.getImage(modelNode, info, true);
			final HtmlRendererContext htmlRendererContext = modelNode.getHtmlRendererContext();
			final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
			htmlPanel.getBrowserPanel().getTimingList.add(info);
		}
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				mouseBeingPressed = true;
				repaint();
			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				mouseBeingPressed = false;
				repaint();
				HtmlController.getInstance().onPressed(modelNode, e, e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					HtmlController.getInstance().onContextMenu(modelNode, e, e.getX(), e.getY());
				}
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		final Dimension ps = this.preferredSize;
		return ps == null ? new Dimension(0, 0) : ps;
	}

	/** {@inheritDoc} */
	@Override
	public int getVAlign() {
		return this.valign;
	}

	/** {@inheritDoc} */
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
		final Dimension size = this.getSize();
		final Insets insets = this.getInsets();
		final Image image = this.image;
		if (image != null) {
            g2d.drawImage(image, insets.left, insets.top, size.width - insets.left - insets.right,
					size.height - insets.top - insets.bottom, this);

            HTMLCollectionImpl map = (HTMLCollectionImpl)modelNode.getOwnerDocument().getElementsByTagName("MAP");
            map.forEach(node -> {
                HTMLMapElement mapElement = (HTMLMapElement) node;
                if(mapElement.getName().equalsIgnoreCase(modelNode.getUseMap().substring(1))){
                    HTMLCollectionImpl areas = (HTMLCollectionImpl)mapElement.getAreas();
                    areas.forEach(area -> {
                        HTMLAreaElement areaElement = (HTMLAreaElement) area;
                        int[] coords = ArrayUtilities.parseCoords(areaElement.getCoords());
                        Shape shape = toShape(areaElement.getShape(), coords);
                        g2d.setColor(new Color(255, 0, 0, 60));
                        g2d.fill(shape);
                        g2d.setColor(new Color(255, 0, 0));
                        g2d.setStroke(new BasicStroke(1f));
                        g2d.draw(shape);
                    });
                }
            });
		} else {
			g.drawString(alt, 10, 10);
		}
		
		if (this.mouseBeingPressed) {
			final Color over = new Color(255, 100, 100, 64);
			final Color oldColor = g.getColor();
			try {
				g.setColor(over);
				g.fillRect(0, 0, size.width, size.height);
			} finally {
				g.setColor(oldColor);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void reset(final int availWidth, final int availHeight) {
		super.reset(availWidth, availHeight);
		final HTMLElementImpl element = (HTMLElementImpl) this.getControlElement();
		final CSSStyleDeclaration currentStyle = element.getCurrentStyle();
		final int dw = getValueSize(element.getAttribute("width"), currentStyle.getWidth(), availWidth);
		final int dh = getValueSize(element.getAttribute("height"), currentStyle.getHeight(), availHeight);
		this.preferredSize = createPreferredSize(dw, dh);
		this.valign = getValign(element);
	}

	private Dimension createPreferredSize(final int width, final int height) {
		final Image img = this.image;
		int dw = width;
		int dh = height;
		if (dw == -1) {
			if (dh != -1) {
				final int iw = img == null ? -1 : img.getWidth(this);
				final int ih = img == null ? -1 : img.getHeight(this);
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
				final int iw = img == null ? -1 : img.getWidth(this);
				final int ih = img == null ? -1 : img.getHeight(this);
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

	private int getValign(final HTMLElementImpl element) {
		String alignText = element.getAttribute("align");

		if (Strings.isNotBlank(alignText)) {
			alignText = alignText.toLowerCase().trim();
		} else {
			final CSSStyleDeclaration style = element.getCurrentStyle();
			alignText = Strings.isNotBlank(style.getVerticalAlign()) ? style.getVerticalAlign() : "";
		}

        return switch (alignText) {
            case "middle" -> AlignValues.MIDDLE.getValue();
            case "absmiddle" -> AlignValues.ABSMIDDLE.getValue();
            case "top" -> AlignValues.TOP.getValue();
            case "bottom" -> AlignValues.BOTTOM.getValue();
            case "absbottom" -> AlignValues.ABSBOTTOM.getValue();
            default -> AlignValues.BASELINE.getValue();
        };
	}
	
	
	private int getValueSize(final String attribute, final String styleAttribute, final int availSize) {
		final String size;
		if (Strings.isNotBlank(attribute)) {
			size = attribute.toLowerCase().trim();
		} else{
			size = Strings.isNotBlank(styleAttribute) ? styleAttribute : "";
		}
        final HTMLDocumentImpl doc = (HTMLDocumentImpl)this.getControlElement().getDocumentNode();
		return  HtmlValues.getPixelSize(size, null, doc.getDefaultView(), -1, availSize);
	}

    private Shape toShape(String shape, int[] c) {
        if (shape == null) shape = "rect";
        switch (shape.toLowerCase()) {
            case "rect":
                if (c.length != 4)
                    throw new IllegalArgumentException("RECT 4 coordinates required: left,top,right,bottom");
                int x1 = c[0], y1 = c[1], x2 = c[2], y2 = c[3];
                return new Rectangle(Math.min(x1, x2), Math.min(y1, y2),
                        Math.abs(x2 - x1), Math.abs(y2 - y1));
            case "circle":
                if (c.length != 3)
                    throw new IllegalArgumentException("CIRCLE 3 coordinates required: cx,cy,r");
                int cx = c[0], cy = c[1], r = c[2];
                return new Ellipse2D.Double(cx - r, cy - r, r * 2.0, r * 2.0);
            case "poly":
                if (c.length < 6 || c.length % 2 != 0)
                    throw new IllegalArgumentException("POLY 3 points errors");
                Path2D.Double path = new Path2D.Double();
                path.moveTo(c[0], c[1]);
                for (int i = 2; i < c.length; i += 2) {
                    path.lineTo(c[i], c[i + 1]);
                }
                path.closePath();
                return path;
            default:
                throw new UnsupportedOperationException("shape not supported: " + shape);
        }
    }
}
