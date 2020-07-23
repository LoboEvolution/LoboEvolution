package com.jtattoo.plaf.lobo;

import java.awt.Color;

import org.loboevolution.laf.ColorFactory;
import org.loboevolution.laf.LAFColorType;
import org.loboevolution.laf.LAFSettings;

public interface LoboLookAndFeel {

	default Color background() {
		ColorFactory instance = ColorFactory.getInstance();

		final LAFSettings settings = new LAFSettings().getInstance();
		Color color = null;

		if (settings.isModern()) {
			color = instance.getColor(LAFColorType.BACKGROUND_MODERN);
		}

		if (settings.isBlackWhite()) {
			color = instance.getColor(LAFColorType.BACKGROUND_BLACK_WHITE);
		}

		if (settings.isWhiteBlack()) {
			color = instance.getColor(LAFColorType.BACKGROUND_WHITE_BLACK);
		}
		return color;
	}

	default Color foreground() {
		ColorFactory instance = ColorFactory.getInstance();

		final LAFSettings settings = new LAFSettings().getInstance();
		Color color = null;

		if (settings.isModern()) {
			color = instance.getColor(LAFColorType.FOREGROUND_MODERN);
		}

		if (settings.isBlackWhite()) {
			color = instance.getColor(LAFColorType.FOREGROUND_BLACK_WHITE);
		}

		if (settings.isWhiteBlack()) {
			color = instance.getColor(LAFColorType.FOREGROUND_WHITE_BLACK);
		}
		return color;
	}
	
	
	default Color interactive() {
		final LAFSettings settings = new LAFSettings().getInstance();
		Color color = null;

		if (settings.isModern()) {
			color = new Color(32, 32, 32);
		}

		if (settings.isBlackWhite()) {
			color = new Color(32, 32, 32);
		}

		if (settings.isWhiteBlack()) {
			color = new Color(32, 32, 32);
		}
		return color;
	}
}