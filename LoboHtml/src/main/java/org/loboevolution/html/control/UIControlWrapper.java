package org.loboevolution.html.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import org.loboevolution.html.HtmlObject;
import org.loboevolution.html.renderer.BrokenComponent;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.RenderableSpot;

public class UIControlWrapper implements UIControl {
	private final Component component;
	private final HtmlObject htmlObject;

	public UIControlWrapper(HtmlObject ho) {
		this.htmlObject = ho;
		Component c;
		if (ho == null) {
			c = new BrokenComponent();
		} else {
			c = ho.getComponent();
		}
		this.component = c;
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
		return this.component.getPreferredSize();
	}

	@Override
	public int getVAlign() {
		return RElement.VALIGN_BASELINE;
	}

	@Override
	public void invalidate() {
		// Calls its AWT parent's invalidate, but I guess that's OK.
		this.component.invalidate();
	}

	@Override
	public void paint(Graphics g) {
		this.component.paint(g);
	}

	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		// Does not paint selection
		return inSelection;
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		this.htmlObject.reset(availWidth, availHeight);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.component.setBounds(x, y, width, height);
	}

	@Override
	public void setRUIControl(RUIControl ruicontrol) {
		// Not doing anything with this.
	}
}
