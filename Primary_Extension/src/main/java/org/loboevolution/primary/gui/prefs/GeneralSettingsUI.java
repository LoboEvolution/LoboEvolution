/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.gui.prefs;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.loboevolution.primary.gui.AbstractSettingsUI;
import org.loboevolution.primary.gui.CheckBoxPanel;
import org.loboevolution.primary.gui.FieldType;
import org.loboevolution.primary.gui.FormField;
import org.loboevolution.primary.gui.FormPanel;
import org.loboevolution.primary.gui.StringListControl;
import org.loboevolution.primary.gui.SwingTasks;
import org.loboevolution.settings.GeneralSettings;

/**
 * The Class GeneralSettingsUI.
 */
public class GeneralSettingsUI extends AbstractSettingsUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant MAX_STARTUP_PAGES. */
	private static final int MAX_STARTUP_PAGES = 4;

	/** The Constant EDIT_LIST_CAPTION. */
	private static final String EDIT_LIST_CAPTION = "You may provide up to " + MAX_STARTUP_PAGES
			+ " startup URLs, one per line.";

	/** The Constant TOOLTIP_STARTUP. */
	private static final String TOOLTIP_STARTUP = "Up to " + MAX_STARTUP_PAGES
			+ " pages launched when you first run the browser.";

	/** The Constant MSIE_USER_AGENT. */
	private static final String MSIE_USER_AGENT = "Include \"MSIE\" in User-Agent header.";

	/** The ie version field. */
	private final transient FormField ieVersionField;

	/** The mozilla version field. */
	private final transient FormField mozillaVersionField;

	/** The ie spoof panel. */
	private final CheckBoxPanel ieSpoofPanel;

	/** The javscript panel. */
	private final CheckBoxPanel javscriptPanel;

	/** The css panel. */
	private final CheckBoxPanel cssPanel;
	
	/** The cachePanel panel. */
	private final CheckBoxPanel cachePanel;

	/** The cookiePanel panel. */
	private final CheckBoxPanel cookiePanel;
	
	/** The navigationPanel panel. */
	private final CheckBoxPanel navigationPanel;

	/** The moz panel. */
	private final FormPanel mozPanel;

	/** The ie panel. */
	private final FormPanel iePanel;

	/** The network panel. */
	private final FormPanel networkPanel;

	/** The startup pages string list control. */
	private final StringListControl startupPagesStringListControl;

	/**
	 * Instantiates a new general settings ui.
	 */
	public GeneralSettingsUI() {
		this.ieVersionField = new FormField(FieldType.TEXT, "MSIE Version:");
		this.mozillaVersionField = new FormField(FieldType.TEXT, "Mozilla Version:");
		this.mozillaVersionField.setToolTip("Mozilla compatibility version.");
		FormPanel iePanel = new FormPanel();
		this.iePanel = iePanel;
		iePanel.addField(this.ieVersionField);
		iePanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		FormPanel networkPanel = new FormPanel();
		this.networkPanel = networkPanel;
		networkPanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		this.ieSpoofPanel = new CheckBoxPanel(MSIE_USER_AGENT, iePanel);
		this.javscriptPanel = new CheckBoxPanel("Enable Javascript", networkPanel);
		this.cssPanel = new CheckBoxPanel("Enable Cascading Style Sheets", networkPanel);
		this.cookiePanel = new CheckBoxPanel("Enable Cookie", networkPanel);
		this.cachePanel = new CheckBoxPanel("Enable Cache", networkPanel);
		this.navigationPanel = new CheckBoxPanel("Enable Navigation", networkPanel);	
		this.mozPanel = new FormPanel();
		mozPanel.addField(this.mozillaVersionField);
		this.startupPagesStringListControl = new StringListControl();
		this.startupPagesStringListControl.setEditListCaption(EDIT_LIST_CAPTION);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.getStartupGroupBox());
		this.add(Box.createRigidArea(new Dimension(8, 8)));
		this.add(this.getUserAgentGroupBox());
		this.add(SwingTasks.createVerticalFill());
		this.add(Box.createRigidArea(new Dimension(8, 8)));
		this.add(this.getNetworkBox());
		this.add(SwingTasks.createVerticalFill());
		this.loadSettings();
		this.ieSpoofPanel.updateEnabling();
		this.javscriptPanel.updateEnabling();
		this.cssPanel.updateEnabling();
		this.cookiePanel.updateEnabling();
		this.cachePanel.updateEnabling();
		this.navigationPanel.updateEnabling();
	}

	/**
	 * Gets the startup group box.
	 *
	 * @return the startup group box
	 */
	private Component getStartupGroupBox() {
		Box startupGroupBox = new Box(BoxLayout.Y_AXIS);
		startupGroupBox.setBorder(new TitledBorder(new EtchedBorder(), "Startup"));
		Box startupPagesBox = new Box(BoxLayout.X_AXIS);
		JLabel pagesLabel = new JLabel("Pages:");
		pagesLabel.setToolTipText(TOOLTIP_STARTUP);
		startupPagesBox.add(pagesLabel);
		startupPagesBox.add(this.startupPagesStringListControl);
		startupGroupBox.add(startupPagesBox);
		return startupGroupBox;
	}

	/**
	 * Gets the user agent group box.
	 *
	 * @return the user agent group box
	 */
	private Component getUserAgentGroupBox() {
		JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "User Agent"));
		groupBox.add(this.getIECheckBoxPanel());
		groupBox.add(this.getMozVersionPanel());
		return groupBox;
	}

	/**
	 * Gets the network box.
	 *
	 * @return the network box
	 */
	private Component getNetworkBox() {
		JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "Network"));
		groupBox.add(this.getJSCheckBoxPanel());
		groupBox.add(this.getCSSCheckBoxPanel());
		groupBox.add(this.getCookiePanel());
		groupBox.add(this.getCachePanel());
		groupBox.add(this.getNavigationPanel());
		
		return groupBox;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.primary.gui.prefs.AbstractSettingsUI#restoreDefaults()
	 */
	@Override
	public void restoreDefaults() {
		GeneralSettings.restoreDefaults();
		this.loadSettings();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.primary.gui.prefs.AbstractSettingsUI#save()
	 */
	@Override
	public void save() {
		String msie = this.ieVersionField.getValue();
		String mozilla = this.mozillaVersionField.getValue();
		boolean js = this.javscriptPanel.isSelected();
		boolean css = this.cssPanel.isSelected();
		boolean cookie = this.cookiePanel.isSelected();
		boolean cache = this.cachePanel.isSelected();
		boolean navigation = this.navigationPanel.isSelected();
		boolean incluedeMsie = this.ieSpoofPanel.isSelected();
		GeneralSettings.deleteNetwork();
		GeneralSettings.deleteUserAgent();
		GeneralSettings.insertNetwork(js, css, cookie, cache, navigation);
		GeneralSettings.insertUserAgent(msie, mozilla, incluedeMsie);
		
		//TODO getStartupURLs
	}

	/**
	 * Load settings.
	 */
	private void loadSettings() {
		GeneralSettings network = GeneralSettings.getNetwork();
		this.javscriptPanel.setSelected(network.isJs());
		this.cssPanel.setSelected(network.isCss());
		this.cookiePanel.setSelected(network.isCookie());
		this.cachePanel.setSelected(network.isCache());
		this.navigationPanel.setSelected(network.isNavigation());
		
		GeneralSettings userAgent = GeneralSettings.getUserAgent();
		this.ieSpoofPanel.setSelected(userAgent.isIncludeIE());
		this.ieVersionField.setValue(userAgent.getIeVersion());
		this.mozillaVersionField.setValue(userAgent.getMozVersion());
		
		this.ieSpoofPanel.revalidate();
		this.javscriptPanel.revalidate();
		this.cssPanel.revalidate();
		this.cookiePanel.revalidate();
		this.cachePanel.revalidate();
		this.navigationPanel.revalidate();
		this.networkPanel.revalidate();
		this.startupPagesStringListControl.setStrings(GeneralSettings.getStartupURLs());
	}

	/**
	 * Gets the IE check box panel.
	 *
	 * @return the IE check box panel
	 */
	private Component getIECheckBoxPanel() {
		return this.ieSpoofPanel;
	}

	/**
	 * Gets the moz version panel.
	 *
	 * @return the moz version panel
	 */
	private Component getMozVersionPanel() {
		return this.mozPanel;
	}

	public FormPanel getIePanel() {
		return iePanel;
	}

	/**
	 * Gets the JS check box panel.
	 *
	 * @return the JS check box panel
	 */
	private Component getJSCheckBoxPanel() {
		return this.javscriptPanel;
	}

	/**
	 * Gets the CSS check box panel.
	 *
	 * @return the CSS check box panel
	 */
	private Component getCSSCheckBoxPanel() {
		return this.cssPanel;
	}

	/**
	 * @return the cachePanel
	 */
	public Component getCachePanel() {
		return cachePanel;
	}

	/**
	 * @return the cookiePanel
	 */
	public Component getCookiePanel() {
		return cookiePanel;
	}

	/**
	 * @return the navigationPanel
	 */
	public Component getNavigationPanel() {
		return navigationPanel;
	}
}
