/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.init;

import java.io.File;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.install.ProgressBar;
import org.loboevolution.laf.LAFSettings;
import org.loboevolution.store.SQLiteCommon;

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
 *
 * @author utente
 * @version $Id: $Id
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
		final File f = new File(SQLiteCommon.getDatabaseStore());
		if (!f.exists()) {
			new ProgressBar();
		} else {
			createAndShowGui();
		}
	}

}
