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

package org.loboevolution.menu.tools.pref;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import org.loboevolution.common.Strings;
import org.loboevolution.gui.FieldType;
import org.loboevolution.gui.FormField;
import org.loboevolution.gui.FormPanel;
import org.loboevolution.gui.SwingTasks;
import org.loboevolution.menu.tools.pref.startup.StartupListControl;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.store.GeneralStore;

import com.jtattoo.plaf.lobo.LoboCheckBox;
import com.jtattoo.plaf.lobo.LoboLabel;
import com.jtattoo.plaf.lobo.LoboPanel;

/**
 * <p>GeneralSettingsUI class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class GeneralSettingsUI extends AbstractSettingsUI {

	/** The Constant EDIT_LIST_CAPTION. */
	private static final String EDIT_LIST_CAPTION = "Startup URLs, one per line.";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TOOLTIP_STARTUP. */
	private static final String TOOLTIP_STARTUP = "Pages launched when you first run the browser.";

	/** The cachePanel panel. */
	private final LoboCheckBox cachePanel;

	/** The cookiePanel panel. */
	private final LoboCheckBox cookiePanel;

	/** The css panel. */
	private final LoboCheckBox cssPanel;

	/** The dimension panel. */
	private final FormPanel dimensionPanel;

	/** The height field. */
	private final transient FormField height;

	/** The javscript panel. */
	private final LoboCheckBox javscriptPanel;

	/** The moz panel. */
	private final FormPanel mozPanel;

	/** The navigationPanel panel. */
	private final LoboCheckBox navigationPanel;

	/** The startup pages string list control. */
	private final StartupListControl startupPages;

	/** The user agent field. */
	private final transient FormField userAgentField;

	/** The width field. */
	private final transient FormField width;

	/**
	 * Instantiates a new general settings ui.
	 */
	public GeneralSettingsUI() {

		this.userAgentField = new FormField(FieldType.TEXT, "User Agent:");

		final FormPanel dimensionPanel = new FormPanel();
		this.dimensionPanel = dimensionPanel;
		dimensionPanel.setBorder(new EmptyBorder(1, 8, 8, 0));

		this.width = new FormField(FieldType.TEXT, "Width:");
		this.height = new FormField(FieldType.TEXT, "Height:");

		dimensionPanel.addField(this.width);
		dimensionPanel.addField(this.height);

		this.javscriptPanel = new LoboCheckBox("Enable Javascript");
		this.cssPanel = new LoboCheckBox("Enable Cascading Style Sheets");
		this.cookiePanel = new LoboCheckBox("Enable Cookie");
		this.cachePanel = new LoboCheckBox("Enable Cache");
		this.navigationPanel = new LoboCheckBox("Enable Navigation");

		this.mozPanel = new FormPanel();
		this.mozPanel.addField(this.userAgentField);
		this.startupPages = new StartupListControl();
		this.startupPages.setEditListCaption(EDIT_LIST_CAPTION);

		this.add(getStartupGroupBox());
		this.add(getUserAgentGroupBox());
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
	 * Gets the user agent group box.
	 *
	 * @return the user agent group box
	 */
	private Component getUserAgentGroupBox() {
		final LoboPanel groupBox = new LoboPanel("User Agent");
		groupBox.setPreferredSize(new Dimension(420, 50));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.add(mozPanel);
		return groupBox;
	}

	/**
	 * Load settings.
	 */
	private void loadSettings() {
		final GeneralStore network = GeneralStore.getNetwork();
		this.javscriptPanel.setSelected(network.isJs());
		this.cssPanel.setSelected(network.isCss());
		this.cookiePanel.setSelected(network.isCookie());
		this.cachePanel.setSelected(network.isCache());
		this.navigationPanel.setSelected(network.isNavigation());
		this.userAgentField.setValue(HttpNetwork.getUserAgentValue());

		final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
		this.width.setValue(String.valueOf(initialWindowBounds.getWidth()));
		this.height.setValue(String.valueOf(initialWindowBounds.getHeight()));

		this.startupPages.setStrings(GeneralStore.getStartupURLs());

		this.javscriptPanel.revalidate();
		this.cssPanel.revalidate();
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
		final String userAgent = this.userAgentField.getValue();
		final boolean js = this.javscriptPanel.isSelected();
		final boolean css = this.cssPanel.isSelected();
		final boolean cookie = this.cookiePanel.isSelected();
		final boolean cache = this.cachePanel.isSelected();
		final boolean navigation = this.navigationPanel.isSelected();
		final int width = Double.valueOf(this.width.getValue()).intValue();
		final int height = Double.valueOf(this.height.getValue()).intValue();

		GeneralStore.deleteNetwork();
		GeneralStore.deleteUserAgent();
		GeneralStore.deleteStartUpUrl();
		GeneralStore.insertNetwork(js, css, cookie, cache, navigation);
		GeneralStore.insertUserAgent(userAgent);
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
