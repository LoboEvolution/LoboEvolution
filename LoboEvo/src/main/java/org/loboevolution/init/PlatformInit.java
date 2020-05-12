package org.loboevolution.init;

import javax.swing.SwingUtilities;

/**
 * <p>PlatformInit class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class PlatformInit {

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
				e.printStackTrace();
			}
		});
	}
}
