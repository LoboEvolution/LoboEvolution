/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.init;

import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.loboevolution.store.laf.LAFSettings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.install.ProgressBar;
import org.loboevolution.store.DatabseSQLite;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.luna.LunaLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.jtattoo.plaf.texture.TextureLookAndFeel;

/**
 * <p>GuiInit class.</p>
 */
public class GuiInit {

	/**
	 * <p>initLookAndFeel.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	public static void initLookAndFeel() throws Exception {
		final LAFSettings settings = new LAFSettings().getInstance();
		final Properties props = new Properties();
		props.put("logoString", "Lobo Evolution");

		if (settings.isAcryl()) {
			AcrylLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		}

		if (settings.isAero()) {
			AeroLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
		}

		if (settings.isAluminium()) {
			AluminiumLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		}

		if (settings.isBernstein()) {
			BernsteinLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		}

		if (settings.isFast()) {
			FastLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
		}

		if (settings.isGraphite()) {
			GraphiteLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		}

		if (settings.isHiFi()) {
			HiFiLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		}

		if (settings.isLuna()) {
			LunaLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
		}

		if (settings.isMcWin()) {
			McWinLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		}

		if (settings.isNoire()) {
			NoireLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
		}

		if (settings.isSmart()) {
			SmartLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		}

		if (settings.isTexture()) {
			TextureLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		}
	}

	/**
	 * <p>createAndShowGui.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	public static void createAndShowGui() throws Exception {
		initLookAndFeel();
		final BrowserFrame frame = new BrowserFrame("LoboEvo");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * <p>install.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	public void install() throws Exception {
		if (!DatabseSQLite.storeExist()) {
			new ProgressBar();
		} else {
			createAndShowGui();
		}
	}

}
