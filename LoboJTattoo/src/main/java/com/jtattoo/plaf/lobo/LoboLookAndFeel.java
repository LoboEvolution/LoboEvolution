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

		if (settings.isBlackWhite() || settings.isNoire() || settings.isHiFi() || settings.isTexture()
				|| settings.isGraphite() || settings.isAcryl()) {
			color = instance.getColor(LAFColorType.BACKGROUND_BLACK_WHITE);
		} else if (settings.isWhiteBlack() || settings.isAluminium() || settings.isBernstein() || settings.isFast()
				|| settings.isMcWin() || settings.isMint() || settings.isSmart()) {
			color = instance.getColor(LAFColorType.BACKGROUND_WHITE_BLACK);
		} else {
			color = instance.getColor(LAFColorType.BACKGROUND_MODERN);
		}
		return color;
	}

	default Color foreground() {
		ColorFactory instance = ColorFactory.getInstance();

		final LAFSettings settings = new LAFSettings().getInstance();
		Color color = null;

		if (settings.isBlackWhite() || settings.isNoire() || settings.isHiFi() || settings.isTexture()
				|| settings.isGraphite() || settings.isAcryl()) {
			color = instance.getColor(LAFColorType.FOREGROUND_BLACK_WHITE);
		} else if (settings.isWhiteBlack() || settings.isAluminium() || settings.isBernstein() || settings.isFast()
				|| settings.isMcWin() || settings.isMint() || settings.isSmart()) {
			color = instance.getColor(LAFColorType.FOREGROUND_WHITE_BLACK);
		} else {
			color = instance.getColor(LAFColorType.FOREGROUND_MODERN);
		}
		
		return color;
	}
	
	
	default Color interactive() {
		final LAFSettings settings = new LAFSettings().getInstance();
		Color color = null;

		if (settings.isBlackWhite() || settings.isNoire() || settings.isHiFi() || settings.isTexture()
				|| settings.isGraphite() || settings.isAcryl()) {
			color = new Color(32, 32, 32);
		} else if (settings.isWhiteBlack() || settings.isAluminium() || settings.isBernstein() || settings.isFast()
				|| settings.isMcWin() || settings.isMint() || settings.isSmart()) {
			color = new Color(255,255,240).darker();
		} else {
			color = new Color(37, 51, 61).darker();
		}

		return color;
	}
}