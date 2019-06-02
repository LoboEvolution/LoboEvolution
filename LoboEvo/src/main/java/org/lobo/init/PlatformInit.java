package org.lobo.init;

import java.awt.EventQueue;

public class PlatformInit {

	public static void main(String... args) {
		EventQueue.invokeLater(() -> {
			try {
				new GuiInit().install();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
	}
}