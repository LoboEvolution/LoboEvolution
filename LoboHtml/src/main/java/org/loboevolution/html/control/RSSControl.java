package org.loboevolution.html.control;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.loboevolution.html.dom.rss.RSSElementImpl;

/**
 * <p>RSSControl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class RSSControl extends BaseControl {

	private static final long serialVersionUID = 1L;
	
	private final RSSElementImpl modelNode;

	/**
	 * <p>Constructor for RSSControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.rss.RSSElementImpl} object.
	 */
	public RSSControl(RSSElementImpl modelNode) {
		super(modelNode);
		this.modelNode = modelNode;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		modelNode.draw(g2d);
	}
}
