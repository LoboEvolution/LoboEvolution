package com.jtattoo.plaf.lobo;

import java.awt.Color;

import com.loboevolution.store.laf.LAFSettings;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.laf.LAFColorType;

/**
 * <p>LoboLookAndFeel interface.</p>
 */
public interface LoboLookAndFeel {

	ColorFactory instance = ColorFactory.getInstance();
	LAFSettings laf = new LAFSettings().getInstance();

	/**
	 * <p>background.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	default Color background() {
		Color color;
		if (laf.isBlackWhite() || laf.isNoire() || laf.isHiFi() || laf.isTexture()
				|| laf.isGraphite() || laf.isAcryl()) {
			color = instance.getColor(LAFColorType.BACKGROUND_BLACK_WHITE);
		} else if (laf.isWhiteBlack() || laf.isAluminium() || laf.isBernstein() || laf.isFast()
				|| laf.isMcWin() || laf.isMint() || laf.isSmart()) {
			color = instance.getColor(LAFColorType.BACKGROUND_WHITE_BLACK);
		} else {
			color = instance.getColor(LAFColorType.BACKGROUND_MODERN);
		}
		return color;
	}

	/**
	 * <p>foreground.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	default Color foreground() {
		Color color;

		if (laf.isBlackWhite() || laf.isNoire() || laf.isHiFi() || laf.isTexture()
				|| laf.isGraphite() || laf.isAcryl()) {
			color = instance.getColor(LAFColorType.FOREGROUND_BLACK_WHITE);
		} else if (laf.isWhiteBlack() || laf.isAluminium() || laf.isBernstein() || laf.isFast()
				|| laf.isMcWin() || laf.isMint() || laf.isSmart()) {
			color = instance.getColor(LAFColorType.FOREGROUND_WHITE_BLACK);
		} else {
			color = instance.getColor(LAFColorType.FOREGROUND_MODERN);
		}
		
		return color;
	}
	
	
	/**
	 * <p>interactive.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	default Color interactive() {
		Color color;

		if (laf.isBlackWhite() || laf.isNoire() || laf.isHiFi() || laf.isTexture()
				|| laf.isGraphite() || laf.isAcryl()) {
			color = new Color(32, 32, 32);
		} else if (laf.isWhiteBlack() || laf.isAluminium() || laf.isBernstein() || laf.isFast()
				|| laf.isMcWin() || laf.isMint() || laf.isSmart()) {
			color = new Color(255,255,240).darker();
		} else {
			color = new Color(37, 51, 61).darker();
		}

		return color;
	}
}
