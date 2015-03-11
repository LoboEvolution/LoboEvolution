package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;


/**
 * The Class BrokenComponent.
 */
class BrokenComponent extends Component {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see java.awt.Component#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		return new Dimension(10, 10);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#update(java.awt.Graphics)
	 */
	public void update(Graphics g) {
		this.paint(g);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		Dimension size = this.getSize();
		g.drawRect(0, 0, size.width, size.height);
		g.drawLine(0, 0, size.width - 1, size.height - 1);
		g.drawLine(size.width - 1, 0, 0, size.height - 1);
	}
}
