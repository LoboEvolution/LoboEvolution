package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import org.lobobrowser.html.HtmlObject;
import org.lobobrowser.html.control.RUIControl;


/**
 * The Class UIControlWrapper.
 */
public class UIControlWrapper implements UIControl {
	
	/** The component. */
	private final Component component;
	
	/** The html object. */
	private final HtmlObject htmlObject;

	/**
	 * Instantiates a new UI control wrapper.
	 *
	 * @param ho the ho
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

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.UIControl#reset(int, int)
	 */
	public void reset(int availWidth, int availHeight) {
		this.htmlObject.reset(availWidth, availHeight);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.UIControl#getComponent()
	 */
	public Component getComponent() {
		return this.component;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.UIControl#getVAlign()
	 */
	public int getVAlign() {
		return RElement.VALIGN_BASELINE;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.UIControl#getBackgroundColor()
	 */
	public Color getBackgroundColor() {
		return this.component.getBackground();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.UIControl#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		return this.component.getPreferredSize();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.UIControl#invalidate()
	 */
	public void invalidate() {
		// Calls its AWT parent's invalidate, but I guess that's OK.
		this.component.invalidate();
	}

	/**
	 * Paint selection.
	 *
	 * @param g the g
	 * @param inSelection the in selection
	 * @param startPoint the start point
	 * @param endPoint the end point
	 * @return true, if successful
	 */
	public boolean paintSelection(Graphics g, boolean inSelection,
			RenderableSpot startPoint, RenderableSpot endPoint) {
		// Does not paint selection
		return inSelection;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.UIControl#setBounds(int, int, int, int)
	 */
	public void setBounds(int x, int y, int width, int height) {
		this.component.setBounds(x, y, width, height);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.UIControl#setRUIControl(org.lobobrowser.html.control.RUIControl)
	 */
	public void setRUIControl(RUIControl ruicontrol) {
		// Not doing anything with this.
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderer.UIControl#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		this.component.paint(g);
	}
}
