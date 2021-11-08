package com.jtattoo.plaf.lobo;

import java.awt.Color;

import com.jtattoo.plaf.LAFColorType;
import com.loboevolution.store.laf.LAFSettings;
import org.loboevolution.laf.ColorFactory;

/**
 * <p>LoboLookAndFeel interface.</p>
 *
 *
 *
 */
public interface LoboLookAndFeel {

	/**
	 * <p>background.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	default Color background() {
		ColorFactory instance = ColorFactory.getInstance();

		final LAFSettings settings = new LAFSettings().getInstance();
		Color color = null;

		if (settings.isBlackWhite() || settings.isNoire() || settings.isHiFi() || settings.isTexture()
				|| settings.isGraphite() || settings.isAcryl()) {
			color = LAFColorType.BACKGROUND_BLACK_WHITE;
		} else if (settings.isWhiteBlack() || settings.isAluminium() || settings.isBernstein() || settings.isFast()
				|| settings.isMcWin() || settings.isMint() || settings.isSmart()) {
			color = LAFColorType.BACKGROUND_WHITE_BLACK;
		} else {
			color = LAFColorType.BACKGROUND_MODERN;
		}
		return color;
	}

	/**
	 * <p>foreground.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	default Color foreground() {
		ColorFactory instance = ColorFactory.getInstance();

		final LAFSettings settings = new LAFSettings().getInstance();
		Color color = null;

		if (settings.isBlackWhite() || settings.isNoire() || settings.isHiFi() || settings.isTexture()
				|| settings.isGraphite() || settings.isAcryl()) {
			color = LAFColorType.FOREGROUND_BLACK_WHITE;
		} else if (settings.isWhiteBlack() || settings.isAluminium() || settings.isBernstein() || settings.isFast()
				|| settings.isMcWin() || settings.isMint() || settings.isSmart()) {
			color = LAFColorType.FOREGROUND_WHITE_BLACK;
		} else {
			color = LAFColorType.FOREGROUND_MODERN;
		}
		
		return color;
	}
	
	
	/**
	 * <p>interactive.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
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
