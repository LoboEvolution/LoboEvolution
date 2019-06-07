/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
 * Created on Jan 29, 2006
 */
package org.lobobrowser.html.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.domimpl.FrameNode;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.NodeImpl;
import org.lobobrowser.html.renderer.NodeRenderer;
import org.lobobrowser.html.style.HtmlLength;
import org.lobobrowser.http.HtmlRendererContext;
import org.lobo.common.WrapperLayout;

/**
 * A Swing panel used to render FRAMESETs only. It is used by {@link HtmlPanel}
 * when a document is determined to be a FRAMESET.
 * 
 * @see HtmlPanel
 * @see HtmlBlockPanel
 */
public class FrameSetPanel extends JComponent implements NodeRenderer {
	private static final Logger logger = Logger.getLogger(FrameSetPanel.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean domInvalid = true;

	private Component[] frameComponents;

	private HtmlRendererContext htmlContext;

	private HTMLElementImpl rootNode;

	public FrameSetPanel() {
		super();
		setLayout(WrapperLayout.getInstance());
		// TODO: This should be a temporary preferred size
		setPreferredSize(new Dimension(600, 400));
	}

	/**
	 * This method is invoked by AWT in the GUI thread to lay out the component.
	 * This implementation is an override.
	 */
	@Override
	public void doLayout() {
		if (this.domInvalid) {
			this.domInvalid = false;
			removeAll();
			final HtmlRendererContext context = this.htmlContext;
			if (context != null) {
				final HTMLElementImpl element = this.rootNode;
				final String rows = element.getAttribute("rows");
				final String cols = element.getAttribute("cols");
				final HtmlLength[] rowLengths = getLengths(rows);
				final HtmlLength[] colLengths = getLengths(cols);
				final HTMLElementImpl[] subframes = getSubFrames(element);
				final Component[] frameComponents = new Component[subframes.length];
				this.frameComponents = frameComponents;
				for (int i = 0; i < subframes.length; i++) {
					final HTMLElementImpl frameElement = subframes[i];
					if (frameElement != null && "FRAMESET".equalsIgnoreCase(frameElement.getTagName())) {
						final FrameSetPanel fsp = new FrameSetPanel();
						fsp.setRootNode(frameElement);
						frameComponents[i] = fsp;
					} else {
						if (frameElement instanceof FrameNode) {
							final BrowserFrame frame = context.createBrowserFrame();
							((FrameNode) frameElement).setBrowserFrame(frame);
							final String src = frameElement.getAttribute("src");
							if (src != null) {
								java.net.URL url;
								try {
									url = frameElement.getFullURL(src);
									if (url != null) {
										frame.loadURL(url);
									}
								} catch (final MalformedURLException mfu) {
									logger.warning("Frame URI=[" + src + "] is malformed.");
								}
							}
							frameComponents[i] = frame.getComponent();
						} else {
							frameComponents[i] = new JPanel();
						}
					}

				}
				final HtmlLength[] rhl = rowLengths;
				final HtmlLength[] chl = colLengths;
				final Component[] fc = this.frameComponents;
				if (rhl != null && chl != null && fc != null) {
					final Dimension size = this.getSize();
					final Insets insets = this.getInsets();
					final int width = size.width - insets.left - insets.right;
					final int height = size.height - insets.left - insets.right;
					final int[] absColLengths = getAbsoluteLengths(chl, width);
					final int[] absRowLengths = getAbsoluteLengths(rhl, height);
					this.add(getSplitPane(this.htmlContext, absColLengths, 0, absColLengths.length, absRowLengths, 0,
							absRowLengths.length, fc));
				}
			}
		}
		super.doLayout();
	}

	private int[] getAbsoluteLengths(HtmlLength[] htmlLengths, int totalSize) {
		final int[] absLengths = new int[htmlLengths.length];
		int totalSizeNonMulti = 0;
		int sumMulti = 0;
		for (int i = 0; i < htmlLengths.length; i++) {
			final HtmlLength htmlLength = htmlLengths[i];
			final int lengthType = htmlLength.getLengthType();
			if (lengthType == HtmlLength.PIXELS) {
				final int absLength = htmlLength.getRawValue();
				totalSizeNonMulti += absLength;
				absLengths[i] = absLength;
			} else if (lengthType == HtmlLength.LENGTH) {
				final int absLength = htmlLength.getLength(totalSize);
				totalSizeNonMulti += absLength;
				absLengths[i] = absLength;
			} else {
				sumMulti += htmlLength.getRawValue();
			}
		}
		final int remaining = totalSize - totalSizeNonMulti;
		if (remaining > 0 && sumMulti > 0) {
			for (int i = 0; i < htmlLengths.length; i++) {
				final HtmlLength htmlLength = htmlLengths[i];
				if (htmlLength.getLengthType() == HtmlLength.MULTI_LENGTH) {
					final int absLength = remaining * htmlLength.getRawValue() / sumMulti;
					absLengths[i] = absLength;
				}
			}
		}
		return absLengths;
	}

	private HtmlLength[] getLengths(String spec) {
		if (spec == null) {
			return new HtmlLength[] { new HtmlLength("1*") };
		}
		final StringTokenizer tok = new StringTokenizer(spec, ",");
		final ArrayList<HtmlLength> lengths = new ArrayList<HtmlLength>();
		while (tok.hasMoreTokens()) {
			final String token = tok.nextToken().trim();
			try {
				lengths.add(new HtmlLength(token));
			} catch (final Exception err) {
				logger.warning("Frame rows or cols value [" + spec + "] is invalid.");
			}
		}
		return (HtmlLength[]) lengths.toArray(HtmlLength.EMPTY_ARRAY);
	}

	private Component getSplitPane(HtmlRendererContext context, int[] colLengths, int firstCol, int numCols,
			int[] rowLengths, int firstRow, int numRows, Component[] frameComponents) {
		if (numCols == 1) {
			final int frameindex = colLengths.length * firstRow + firstCol;
			final Component topComponent = frameindex < frameComponents.length ? frameComponents[frameindex] : null;
			if (numRows == 1) {
				return topComponent;
			} else {
				final Component bottomComponent = getSplitPane(context, colLengths, firstCol, numCols, rowLengths,
						firstRow + 1, numRows - 1, frameComponents);
				final JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topComponent, bottomComponent);
				sp.setDividerLocation(rowLengths[firstRow]);
				return sp;
			}
		} else {
			final Component rightComponent = getSplitPane(context, colLengths, firstCol + 1, numCols - 1, rowLengths,
					firstRow, numRows, frameComponents);
			final Component leftComponent = getSplitPane(context, colLengths, firstCol, 1, rowLengths, firstRow,
					numRows, frameComponents);
			final JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftComponent, rightComponent);
			sp.setDividerLocation(colLengths[firstCol]);
			return sp;
		}
	}

	private HTMLElementImpl[] getSubFrames(HTMLElementImpl parent) {
		final NodeImpl[] children = parent.getChildrenArray();
		final ArrayList<NodeImpl> subFrames = new ArrayList<NodeImpl>();
		for (final NodeImpl child : children) {
			if (child instanceof HTMLElementImpl) {
				final String nodeName = child.getNodeName();
				if ("FRAME".equalsIgnoreCase(nodeName) || "FRAMESET".equalsIgnoreCase(nodeName)) {
					subFrames.add(child);
				}
			}
		}
		return (HTMLElementImpl[]) subFrames.toArray(new HTMLElementImpl[0]);
	}

	public final void processDocumentNotifications(DocumentNotification[] notifications) {
		// Called in the GUI thread.
		if (notifications.length > 0) {
			// Not very efficient, but it will do.
			this.domInvalid = true;
			invalidate();
			if (isVisible()) {
				validate();
				this.repaint();
			}
		}
	}

	@Override
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
	}

	/**
	 * Sets the FRAMESET node and invalidates the component so it can be rendered
	 * immediately in the GUI thread.
	 */
	@Override
	public void setRootNode(NodeImpl node) {
		// Method expected to be called in the GUI thread.
		if (!(node instanceof HTMLElementImpl)) {
			throw new IllegalArgumentException("node=" + node);
		}
		final HTMLElementImpl element = (HTMLElementImpl) node;
		this.rootNode = element;
		final HtmlRendererContext context = element.getHtmlRendererContext();
		this.htmlContext = context;
		this.domInvalid = true;
		invalidate();
		validateAll();
		this.repaint();
	}

	protected void validateAll() {
		Component toValidate = this;
		for (;;) {
			final Container parent = toValidate.getParent();
			if (parent == null || parent.isValid()) {
				break;
			}
			toValidate = parent;
		}
		toValidate.validate();
	}
}
