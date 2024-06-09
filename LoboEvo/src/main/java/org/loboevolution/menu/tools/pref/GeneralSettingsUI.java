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

package org.loboevolution.menu.tools.pref;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.Serial;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import org.loboevolution.common.Strings;
import org.loboevolution.gui.FieldType;
import org.loboevolution.gui.FormField;
import org.loboevolution.gui.FormPanel;
import org.loboevolution.gui.SwingTasks;
import org.loboevolution.info.GeneralInfo;
import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.menu.tools.pref.startup.StartupListControl;
import org.loboevolution.store.GeneralStore;

import com.jtattoo.plaf.lobo.LoboCheckBox;
import com.jtattoo.plaf.lobo.LoboLabel;
import com.jtattoo.plaf.lobo.LoboPanel;

/**
 * <p>GeneralSettingsUI class.</p>
 */
public class GeneralSettingsUI extends AbstractToolsUI {

	/** The Constant EDIT_LIST_CAPTION. */
	private static final String EDIT_LIST_CAPTION = "Startup URLs, one per line.";

	/** The Constant serialVersionUID. */
	@Serial
    private static final long serialVersionUID = 1L;

	/** The Constant TOOLTIP_STARTUP. */
	private static final String TOOLTIP_STARTUP = "Pages launched when you first run the browser.";

	/** The cachePanel panel. */
	private final LoboCheckBox cachePanel;

	/** The cookiePanel panel. */
	private final LoboCheckBox cookiePanel;

	/** The css panel. */
	private final LoboCheckBox cssPanel;

	/** The image panel. */
	private final LoboCheckBox imagePanel;

	/** The dimension panel. */
	private final FormPanel dimensionPanel;

	/** The height field. */
	private final transient FormField height;

	/** The javscript panel. */
	private final LoboCheckBox javscriptPanel;

	/** The navigationPanel panel. */
	private final LoboCheckBox navigationPanel;

	/** The startup pages string list control. */
	private final StartupListControl startupPages;

	/** The width field. */
	private final transient FormField width;

	/**
	 * Instantiates a new general settings ui.
	 */
	public GeneralSettingsUI() {

		final FormPanel dimensionPanel = new FormPanel();
		this.dimensionPanel = dimensionPanel;
		dimensionPanel.setBorder(new EmptyBorder(1, 8, 8, 0));

		this.width = new FormField(FieldType.TEXT, "Width:");
		this.height = new FormField(FieldType.TEXT, "Height:");

		dimensionPanel.addField(this.width);
		dimensionPanel.addField(this.height);

		this.javscriptPanel = new LoboCheckBox("Enable Javascript");
		this.cssPanel = new LoboCheckBox("Enable Cascading Style Sheets");
		this.imagePanel = new LoboCheckBox("Enable Image");
		this.cookiePanel = new LoboCheckBox("Enable Cookie");
		this.cachePanel = new LoboCheckBox("Enable Cache");
		this.navigationPanel = new LoboCheckBox("Enable Navigation");
		this.startupPages = new StartupListControl();
		this.startupPages.setEditListCaption(EDIT_LIST_CAPTION);

		this.add(getStartupGroupBox());
		this.add(getDimensionGroupBox());
		this.add(getNetworkBox());
		this.add(SwingTasks.createVerticalFill());
		loadSettings();
	}

	/**
	 * Gets the dimension group box.
	 *
	 * @return the dimension agent group box
	 */
	private Component getDimensionGroupBox() {
		final LoboPanel groupBox = new LoboPanel("Dimension");
		groupBox.setPreferredSize(new Dimension(420, 80));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.add(dimensionPanel);
		return groupBox;
	}

	/**
	 * Gets the network box.
	 *
	 * @return the network box
	 */
	private Component getNetworkBox() {
		final LoboPanel groupBox = new LoboPanel("Network");
		groupBox.setPreferredSize(new Dimension(420, 150));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.add(javscriptPanel);
		groupBox.add(cssPanel);
		groupBox.add(imagePanel);
		groupBox.add(cookiePanel);
		groupBox.add(navigationPanel);
		return groupBox;
	}

	/**
	 * Gets the startup group box.
	 *
	 * @return the startup group box
	 */
	private Component getStartupGroupBox() {
		final LoboPanel groupBox = new LoboPanel("Startup");
		groupBox.setPreferredSize(new Dimension(420, 50));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.X_AXIS));
		final LoboLabel pagesLabel = new LoboLabel("Pages:");
		pagesLabel.setToolTipText(TOOLTIP_STARTUP);
		groupBox.add(pagesLabel);
		groupBox.add(this.startupPages);
		return groupBox;
	}


	/**
	 * Load settings.
	 */
	private void loadSettings() {
		final GeneralInfo network = GeneralStore.getGeneralInfo();
		this.javscriptPanel.setSelected(network.isJs());
		this.cssPanel.setSelected(network.isCss());
		this.imagePanel.setSelected(network.isImage());
		this.cookiePanel.setSelected(network.isCookie());
		this.cachePanel.setSelected(network.isCache());
		this.navigationPanel.setSelected(network.isNavigation());

		final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
		this.width.setValue(String.valueOf(initialWindowBounds.getWidth()));
		this.height.setValue(String.valueOf(initialWindowBounds.getHeight()));

		this.startupPages.setStrings(GeneralStore.getStartupURLs());

		this.javscriptPanel.revalidate();
		this.cssPanel.revalidate();
		this.imagePanel.revalidate();
		this.cookiePanel.revalidate();
		this.cachePanel.revalidate();
		this.navigationPanel.revalidate();
		this.dimensionPanel.revalidate();
	}

	/** {@inheritDoc} */
	@Override
	public void restoreDefaults() {
		GeneralStore.restoreDefaults();
		loadSettings();
	}

	/** {@inheritDoc} */
	@Override
	public void save() {
		final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
		final boolean js = this.javscriptPanel.isSelected();
		final boolean css = this.cssPanel.isSelected();
		final boolean image = this.imagePanel.isSelected();
		final boolean cookie = this.cookiePanel.isSelected();
		final boolean cache = this.cachePanel.isSelected();
		final boolean navigation = this.navigationPanel.isSelected();
		final int width = Double.valueOf(this.width.getValue()).intValue();
		final int height = Double.valueOf(this.height.getValue()).intValue();

		GeneralStore.deleteNetwork();
		GeneralStore.deleteStartUpUrl();
		GeneralStore.insertNetwork(js, css, image, cookie, cache, navigation);
		GeneralStore.insertBounds(new Rectangle(width, height));

		final List<String> strings = this.startupPages.getStrings();
		for (final String url : strings) {
			if (Strings.isNotBlank(url)) {
				GeneralStore.insertStartupUrl(url);
			}
		}

		if (initialWindowBounds.getWidth() != width || initialWindowBounds.getHeight() != height) {
			JOptionPane.showMessageDialog(this, "Please restart Lobo Evolution");
		}
	}
}
