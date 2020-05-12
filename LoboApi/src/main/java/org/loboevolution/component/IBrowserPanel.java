package org.loboevolution.component;

import javax.swing.JScrollPane;

import org.loboevolution.tab.DnDTabbedPane;

/**
 * <p>IBrowserPanel interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface IBrowserPanel {
	
	/**
	 * <p>getTabbedPane.</p>
	 *
	 * @return a {@link org.loboevolution.tab.DnDTabbedPane} object.
	 */
	DnDTabbedPane getTabbedPane();
	
	/**
	 * <p>getScroll.</p>
	 *
	 * @return a {@link javax.swing.JScrollPane} object.
	 */
	JScrollPane getScroll();
	
	/**
	 * <p>getBrowserFrame.</p>
	 *
	 * @return a {@link org.loboevolution.component.IBrowserFrame} object.
	 */
	IBrowserFrame getBrowserFrame();
	
	/**
	 * <p>getWelcome.</p>
	 *
	 * @return a {@link org.loboevolution.component.IWelcomePanel} object.
	 */
	IWelcomePanel getWelcome();

}
