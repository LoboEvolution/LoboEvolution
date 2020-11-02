package org.loboevolution.component;

/**
 * <p>IBrowserFrame interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface IBrowserFrame {
	
	/**
	 * <p>getToolbar.</p>
	 *
	 * @return a {@link org.loboevolution.component.IToolBar} object.
	 */
	IToolBar getToolbar();
	
	/**
	 * <p>getPanel.</p>
	 *
	 * @return a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	IBrowserPanel getPanel();


	/**
	 * <p>getDownload.</p>
	 *
	 * @return a {@link org.loboevolution.component.IDownload} object.
	 */
	IDownload getDownload();
}
