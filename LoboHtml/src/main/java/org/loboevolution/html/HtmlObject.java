package org.loboevolution.html;

import java.awt.Component;

/**
 * This interface should be implemented to provide OBJECT, EMBED or APPLET
 * functionality.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HtmlObject {
	/**
	 * <p>destroy.</p>
	 */
	void destroy();

	/**
	 * <p>getComponent.</p>
	 *
	 * @return a {@link java.awt.Component} object.
	 */
	Component getComponent();

	/**
	 * Called as the object is layed out, either the first time it's layed out or
	 * whenever the DOM changes. This is where the object should reset its state
	 * based on element children or attributes and possibly change its preferred
	 * size if appropriate.
	 *
	 * @param availableWidth a int.
	 * @param availableHeight a int.
	 */
	void reset(int availableWidth, int availableHeight);

	/**
	 * <p>resume.</p>
	 */
	void resume();

	/**
	 * <p>suspend.</p>
	 */
	void suspend();
}
