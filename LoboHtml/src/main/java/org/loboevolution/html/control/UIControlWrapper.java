package org.loboevolution.html.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import org.loboevolution.html.HtmlObject;
import org.loboevolution.html.renderer.BrokenComponent;
import org.loboevolution.html.renderer.RenderableSpot;

/**
 * <p>UIControlWrapper class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class UIControlWrapper implements UIControl {
	private final Component component;
	private final HtmlObject htmlObject;

	/**
	 * <p>Constructor for UIControlWrapper.</p>
	 *
	 * @param ho a {@link org.loboevolution.html.HtmlObject} object.
	 */
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

	/** {@inheritDoc} */
	@Override
	public Color getBackgroundColor() {
		return this.component.getBackground();
	}

	/** {@inheritDoc} */
	@Override
	public Component getComponent() {
		return this.component;
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		return this.component.getPreferredSize();
	}

	/** {@inheritDoc} */
	@Override
	public void invalidate() {
		// Calls its AWT parent's invalidate, but I guess that's OK.
		this.component.invalidate();
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		this.component.paint(g);
	}

	/**
	 * <p>paintSelection.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param inSelection a boolean.
	 * @param startPoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 * @param endPoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 * @return a boolean.
	 */
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		// Does not paint selection
		return inSelection;
	}

	/** {@inheritDoc} */
	@Override
	public void reset(int availWidth, int availHeight) {
		this.htmlObject.reset(availWidth, availHeight);
	}

	/** {@inheritDoc} */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.component.setBounds(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public void setRUIControl(RUIControl ruicontrol) {
		// Not doing anything with this.
	}
}
