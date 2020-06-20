package org.loboevolution.init;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

/**
 * <p>PlatformInit class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class PlatformInit {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(PlatformInit.class.getName());

	/**
	 * <p>main.</p>
	 *
	 * @param args a {@link java.lang.String} object.
	 */
	public static void main(String... args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new GuiInit().install();
			} catch (final Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		});
	}
}
