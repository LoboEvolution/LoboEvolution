package org.loboevolution.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * <p>BrokenComponent class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class BrokenComponent extends Component {

	private static final long serialVersionUID = 1L;

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(10, 10);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		final Dimension size = this.getSize();
		g.drawRect(0, 0, size.width, size.height);
		g.drawLine(0, 0, size.width - 1, size.height - 1);
		g.drawLine(size.width - 1, 0, 0, size.height - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void update(Graphics g) {
		paint(g);
	}
}
