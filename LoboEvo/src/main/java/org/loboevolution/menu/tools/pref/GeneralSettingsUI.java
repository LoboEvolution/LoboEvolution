package org.loboevolution.menu.tools.pref;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.loboevolution.common.Strings;
import org.loboevolution.gui.CheckBoxPanel;
import org.loboevolution.gui.FieldType;
import org.loboevolution.gui.FormField;
import org.loboevolution.gui.FormPanel;
import org.loboevolution.gui.SwingTasks;
import org.loboevolution.menu.tools.pref.startup.StartupListControl;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.store.GeneralStore;

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
	private final CheckBoxPanel cachePanel;

	/** The cookiePanel panel. */
	private final CheckBoxPanel cookiePanel;

	/** The css panel. */
	private final CheckBoxPanel cssPanel;

	/** The dimension panel. */
	private final FormPanel dimensionPanel;

	/** The height field. */
	private final transient FormField height;

	/** The javscript panel. */
	private final CheckBoxPanel javscriptPanel;

	/** The moz panel. */
	private final FormPanel mozPanel;

	/** The navigationPanel panel. */
	private final CheckBoxPanel navigationPanel;

	/** The network panel. */
	private final FormPanel networkPanel;

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

		final FormPanel networkPanel = new FormPanel();
		this.networkPanel = networkPanel;
		networkPanel.setBorder(new EmptyBorder(1, 8, 8, 0));

		final FormPanel dimensionPanel = new FormPanel();
		this.dimensionPanel = dimensionPanel;
		dimensionPanel.setBorder(new EmptyBorder(1, 8, 8, 0));

		this.width = new FormField(FieldType.TEXT, "Width:");
		this.height = new FormField(FieldType.TEXT, "Height:");

		dimensionPanel.addField(this.width);
		dimensionPanel.addField(this.height);

		this.javscriptPanel = new CheckBoxPanel("Enable Javascript", networkPanel);
		this.cssPanel = new CheckBoxPanel("Enable Cascading Style Sheets", networkPanel);
		this.cookiePanel = new CheckBoxPanel("Enable Cookie", networkPanel);
		this.cachePanel = new CheckBoxPanel("Enable Cache", networkPanel);
		this.navigationPanel = new CheckBoxPanel("Enable Navigation", networkPanel);

		this.mozPanel = new FormPanel();
		this.mozPanel.addField(this.userAgentField);
		this.startupPages = new StartupListControl();
		this.startupPages.setEditListCaption(EDIT_LIST_CAPTION);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.add(getStartupGroupBox());
		this.add(Box.createRigidArea(new Dimension(8, 8)));
		this.add(getUserAgentGroupBox());
		this.add(SwingTasks.createVerticalFill());
		this.add(getDimensionGroupBox());
		this.add(SwingTasks.createVerticalFill());
		this.add(Box.createRigidArea(new Dimension(8, 8)));
		this.add(getNetworkBox());
		this.add(SwingTasks.createVerticalFill());

		loadSettings();
		this.javscriptPanel.updateEnabling();
		this.cssPanel.updateEnabling();
		this.cookiePanel.updateEnabling();
		this.cachePanel.updateEnabling();
		this.navigationPanel.updateEnabling();
	}

	/**
	 * <p>Getter for the field cachePanel.</p>
	 *
	 * @return the cachePanel
	 */
	public Component getCachePanel() {
		return this.cachePanel;
	}

	/**
	 * <p>Getter for the field cookiePanel.</p>
	 *
	 * @return the cookiePanel
	 */
	public Component getCookiePanel() {
		return this.cookiePanel;
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
	 * Gets the dimension group box.
	 *
	 * @return the dimension agent group box
	 */
	private Component getDimensionGroupBox() {
		final JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "Dimension"));
		groupBox.add(getDimensionPanel());
		return groupBox;
	}

	/**
	 * <p>Getter for the field dimensionPanel.</p>
	 *
	 * @return the dimensionPanel
	 */
	public FormPanel getDimensionPanel() {
		return this.dimensionPanel;
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
	 * Gets the moz version panel.
	 *
	 * @return the moz version panel
	 */
	private Component getMozVersionPanel() {
		return this.mozPanel;
	}

	/**
	 * <p>Getter for the field navigationPanel.</p>
	 *
	 * @return the navigationPanel
	 */
	public Component getNavigationPanel() {
		return this.navigationPanel;
	}

	/**
	 * Gets the network box.
	 *
	 * @return the network box
	 */
	private Component getNetworkBox() {
		final JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "Network"));
		groupBox.add(getJSCheckBoxPanel());
		groupBox.add(getCSSCheckBoxPanel());
		groupBox.add(getCookiePanel());
		groupBox.add(getCachePanel());
		groupBox.add(getNavigationPanel());
		return groupBox;
	}

	/**
	 * Gets the startup group box.
	 *
	 * @return the startup group box
	 */
	private Component getStartupGroupBox() {
		final Box startupGroupBox = new Box(BoxLayout.Y_AXIS);
		startupGroupBox.setBorder(new TitledBorder(new EtchedBorder(), "Startup"));
		final Box startupPagesBox = new Box(BoxLayout.X_AXIS);
		final JLabel pagesLabel = new JLabel("Pages:");
		pagesLabel.setToolTipText(TOOLTIP_STARTUP);
		startupPagesBox.add(pagesLabel);
		startupPagesBox.add(this.startupPages);
		startupGroupBox.add(startupPagesBox);
		return startupGroupBox;
	}

	/**
	 * Gets the user agent group box.
	 *
	 * @return the user agent group box
	 */
	private Component getUserAgentGroupBox() {
		final JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "User Agent"));
		groupBox.add(getMozVersionPanel());
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
		this.networkPanel.revalidate();
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
