package org.lobo.init;

import javax.swing.SwingUtilities;

public class PlatformInit {

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