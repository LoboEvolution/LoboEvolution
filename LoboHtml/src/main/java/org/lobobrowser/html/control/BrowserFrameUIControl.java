package org.lobobrowser.html.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.dom.HTMLElement;
import org.lobobrowser.html.dom.domimpl.ModelNode;
import org.lobobrowser.html.renderer.RElement;
import org.lobobrowser.html.renderer.RenderableSpot;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;

class BrowserFrameUIControl implements UIControl {
	private int availHeight;
	private int availWidth;
	private final BrowserFrame browserFrame;
	// private final BrowserFrame browserFrame;
	private final Component component;

	private final HTMLElement element;

	private RUIControl ruiControl;

	public BrowserFrameUIControl(HTMLElement element, BrowserFrame browserFrame) {
		this.component = browserFrame.getComponent();
		this.browserFrame = browserFrame;
		this.element = element;
	}

	public float getAlignmentY() {
		return 0;
	}

	@Override
	public Color getBackgroundColor() {
		return this.component.getBackground();
	}

	@Override
	public Component getComponent() {
		return this.component;
	}

	@Override
	public Dimension getPreferredSize() {
		final int width = HtmlValues.getPixelSize(this.element.getAttribute("width"), null, 100, this.availWidth);
		final int height = HtmlValues.getPixelSize(this.element.getAttribute("height"), null, 100, this.availHeight);
		
		return new Dimension(width, height);
	}

	@Override
	public int getVAlign() {
		return RElement.VALIGN_BASELINE;
	}

	@Override
	public void invalidate() {
		this.component.invalidate();
	}

	@Override
	public void paint(Graphics g) {
		// We actually have to paint it.
		this.component.paint(g);
	}

	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		// Selection does not cross in here?
		return false;
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		this.availWidth = availWidth;
		this.availHeight = availHeight;
		final RUIControl ruiControl = this.ruiControl;
		if (ruiControl != null) {
			final ModelNode node = ruiControl.getModelNode();
			final HTMLElement element = (HTMLElement) node;
			final RenderState renderState = node.getRenderState();
			HtmlInsets insets = null;
			String marginwidth = element.getAttribute("marginwidth");
			String marginheight = element.getAttribute("marginheight");
			if (marginwidth != null && marginwidth.length() != 0) {
				if (insets == null) {
					insets = new HtmlInsets();
				}
				marginwidth = marginwidth.trim();
				if (marginwidth.endsWith("%")) {
					int value = HtmlValues.getPixelSize(marginwidth, renderState, 0);
					try {
						value = Integer.parseInt(marginwidth.substring(0, marginwidth.length() - 1));
					} catch (final NumberFormatException nfe) {
						value = 0;
					}
					insets.left = value;
					insets.right = value;
					insets.leftType = HtmlInsets.TYPE_PERCENT;
					insets.rightType = HtmlInsets.TYPE_PERCENT;
				} else {
					int value = HtmlValues.getPixelSize(marginwidth, renderState, 0);
					insets.left = value;
					insets.right = value;
					insets.leftType = HtmlInsets.TYPE_PIXELS;
					insets.rightType = HtmlInsets.TYPE_PIXELS;
				}
			}
			if (marginheight != null && marginheight.length() != 0) {
				if (insets == null) {
					insets = new HtmlInsets();
				}
				marginheight = marginheight.trim();
				if (marginheight.endsWith("%")) {
					int value;
					try {
						value = Integer.parseInt(marginheight.substring(0, marginheight.length() - 1));
					} catch (final NumberFormatException nfe) {
						value = 0;
					}
					insets.top = value;
					insets.bottom = value;
					insets.topType = HtmlInsets.TYPE_PERCENT;
					insets.bottomType = HtmlInsets.TYPE_PERCENT;
				} else {
					int value = HtmlValues.getPixelSize(marginheight, renderState, 0);
					insets.top = value;
					insets.bottom = value;
					insets.topType = HtmlInsets.TYPE_PIXELS;
					insets.bottomType = HtmlInsets.TYPE_PIXELS;
				}
			}
			final Insets awtMarginInsets = insets == null ? null : insets.getSimpleAWTInsets(availWidth, availHeight);
			final int overflowX = renderState.getOverflowX();
			final int overflowY = renderState.getOverflowY();
			if (awtMarginInsets != null) {
				this.browserFrame.setDefaultMarginInsets(awtMarginInsets);
			}
			if (overflowX != RenderState.OVERFLOW_NONE) {
				this.browserFrame.setDefaultOverflowX(overflowX);
			}
			if (overflowY != RenderState.OVERFLOW_NONE) {
				this.browserFrame.setDefaultOverflowY(overflowY);
			}
		}
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.component.setBounds(x, y, width, height);
	}

	@Override
	public void setRUIControl(RUIControl ruicontrol) {
		this.ruiControl = ruicontrol;
	}
}
