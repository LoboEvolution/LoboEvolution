/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui.prefs;

import java.awt.Component;
import java.awt.Dimension;
import java.net.InetSocketAddress;
import java.net.Proxy;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lobobrowser.primary.gui.AbstractSettingsUI;
import org.lobobrowser.primary.gui.FieldType;
import org.lobobrowser.primary.gui.FormField;
import org.lobobrowser.primary.gui.FormPanel;
import org.lobobrowser.primary.gui.SwingTasks;
import org.lobobrowser.primary.gui.ValidationException;
import org.lobobrowser.settings.ConnectionSettings;

/**
 * The Class ConnectionSettingsUI.
 */
public class ConnectionSettingsUI extends AbstractSettingsUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The settings. */
	private final ConnectionSettings settings = ConnectionSettings.getInstance();

	/** The no proxy radio button. */
	private final JRadioButton noProxyRadioButton = new JRadioButton();

	/** The http proxy radio button. */
	private final JRadioButton httpProxyRadioButton = new JRadioButton();

	/** The socks proxy radio button. */
	private final JRadioButton socksProxyRadioButton = new JRadioButton();

	/** The authenticated check box. */
	private final JCheckBox authenticatedCheckBox = new JCheckBox();

	/** The bypass local check box. */
	private final JCheckBox bypassLocalCheckBox = new JCheckBox();

	/** The proxy host area. */
	private final Box proxyHostArea = new Box(BoxLayout.Y_AXIS);

	/** The authentication panel. */
	private final FormPanel authenticationPanel = new FormPanel();

	/** The user name field. */
	private final FormField userNameField = new FormField(FieldType.TEXT);

	/** The password field. */
	private final FormField passwordField = new FormField(FieldType.PASSWORD);

	/** The host port panel. */
	private final FormPanel hostPortPanel = new FormPanel();

	/** The host field. */
	private final FormField hostField = new FormField(FieldType.TEXT);

	/** The port field. */
	private final FormField portField = new FormField(FieldType.TEXT);

	/**
	 * Instantiates a new connection settings ui.
	 */
	public ConnectionSettingsUI() {
		this.noProxyRadioButton.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateEnabling();
			}
		});
		this.authenticatedCheckBox.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateEnabling();
			}
		});
		this.noProxyRadioButton.setText("Direct connection (no proxy)");
		this.httpProxyRadioButton.setText("HTTP proxy");
		this.socksProxyRadioButton.setText("SOCKS proxy");
		this.authenticatedCheckBox.setText("Authenticate with proxy server.");
		this.bypassLocalCheckBox.setText("Bypass proxy for local addresses.");
		this.userNameField.setCaption("User name:");
		this.passwordField.setCaption("Password:");
		this.authenticationPanel.addField(this.userNameField);
		this.authenticationPanel.addField(this.passwordField);
		this.hostField.setCaption("Host:");
		this.portField.setCaption("Port:");
		this.hostPortPanel.addField(this.hostField);
		this.hostPortPanel.addField(this.portField);

		ButtonGroup group = new ButtonGroup();
		group.add(this.noProxyRadioButton);
		group.add(this.httpProxyRadioButton);
		group.add(this.socksProxyRadioButton);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createRigidArea(new Dimension(8, 8)));
		this.add(this.getProxyBox());
		this.add(SwingTasks.createVerticalFill());
		this.loadSettings();
		this.updateEnabling();
	}

	/**
	 * Update enabling.
	 */
	private void updateEnabling() {
		SwingTasks.setNestedEnabled(this.proxyHostArea, !this.noProxyRadioButton.isSelected());
		SwingTasks.setNestedEnabled(this.authenticationPanel, this.authenticatedCheckBox.isSelected());
	}

	/**
	 * Gets the proxy box.
	 *
	 * @return the proxy box
	 */
	private Component getProxyBox() {
		Box radioBox = new Box(BoxLayout.Y_AXIS);
		radioBox.setPreferredSize(new Dimension(600, 200));
		radioBox.add(this.noProxyRadioButton);
		radioBox.add(this.httpProxyRadioButton);
		radioBox.add(this.socksProxyRadioButton);

		Box radioBoxExpander = new Box(BoxLayout.X_AXIS);
		radioBoxExpander.add(radioBox);
		radioBoxExpander.add(Box.createGlue());

		Box box = SwingTasks.createGroupBox(BoxLayout.Y_AXIS, "Proxy");
		box.add(radioBoxExpander);
		box.add(this.getProxyHostArea());
		return box;
	}

	/**
	 * Gets the proxy host area.
	 *
	 * @return the proxy host area
	 */
	private Component getProxyHostArea() {
		Box checkBoxBox = new Box(BoxLayout.Y_AXIS);
		checkBoxBox.setPreferredSize(new Dimension(600, 200));
		checkBoxBox.add(this.bypassLocalCheckBox);
		checkBoxBox.add(this.authenticatedCheckBox);

		Box checkBoxBoxExpander = new Box(BoxLayout.X_AXIS);
		checkBoxBoxExpander.add(checkBoxBox);
		checkBoxBoxExpander.add(Box.createHorizontalGlue());

		Box box = this.proxyHostArea;
		box.setBorder(new EmptyBorder(8, 16, 8, 8));
		box.add(this.hostPortPanel);
		box.add(checkBoxBoxExpander);
		box.add(this.authenticationPanel);
		return box;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.primary.gui.prefs.AbstractSettingsUI#restoreDefaults()
	 */
	@Override
	public void restoreDefaults() {
		this.settings.restoreDefaults();
		this.loadSettings();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.primary.gui.prefs.AbstractSettingsUI#save()
	 */
	@Override
	public void save() throws ValidationException {
		ConnectionSettings settings = this.settings;
		Proxy.Type proxyType;
		if (this.noProxyRadioButton.isSelected()) {
			proxyType = Proxy.Type.DIRECT;
		} else if (this.httpProxyRadioButton.isSelected()) {
			proxyType = Proxy.Type.HTTP;
		} else if (this.socksProxyRadioButton.isSelected()) {
			proxyType = Proxy.Type.SOCKS;
		} else {
			throw new IllegalStateException("not expected");
		}
		settings.setProxyType(proxyType);
		settings.setAuthenticated(this.authenticatedCheckBox.isSelected());
		settings.setUserName(this.userNameField.getValue());
		settings.setPassword(this.passwordField.getValue());
		settings.setDisableProxyForLocalAddresses(this.bypassLocalCheckBox.isSelected());
		String host = this.hostField.getValue();
		if ("".equals(host) && (proxyType != Proxy.Type.DIRECT)) {
			throw new ValidationException("To set up a proxy, a host name must be provided.");
		}
		int port;
		try {
			port = Integer.parseInt(this.portField.getValue());
		} catch (NumberFormatException nfe) {
			if (proxyType != Proxy.Type.DIRECT) {
				throw new ValidationException("The port must be a number.");
			} else {
				port = 0;
			}
		}
		InetSocketAddress socketAddress = new InetSocketAddress(host, port);
		settings.setInetSocketAddress(socketAddress);
		settings.save();
	}

	/**
	 * Load settings.
	 */
	private void loadSettings() {
		ConnectionSettings settings = this.settings;
		switch (settings.getProxyType()) {
		case DIRECT:
			this.noProxyRadioButton.setSelected(true);
			break;
		case HTTP:
			this.httpProxyRadioButton.setSelected(true);
			break;
		case SOCKS:
			this.socksProxyRadioButton.setSelected(true);
			break;
		}
		this.authenticatedCheckBox.setSelected(settings.isAuthenticated());
		this.userNameField.setValue(settings.getUserName());
		this.passwordField.setValue(settings.getPassword());
		this.bypassLocalCheckBox.setSelected(settings.isDisableProxyForLocalAddresses());
		InetSocketAddress socketAddress = settings.getInetSocketAddress();
		if (socketAddress == null) {
			this.hostField.setValue("");
			this.portField.setValue("");
		} else {
			this.hostField.setValue(socketAddress.getHostName());
			this.portField.setValue(String.valueOf(socketAddress.getPort()));
		}
		this.authenticationPanel.revalidate();
		this.hostPortPanel.revalidate();
	}
}
