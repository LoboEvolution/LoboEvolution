/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui.prefs;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.lobobrowser.primary.gui.CheckBoxPanel;
import org.lobobrowser.primary.gui.FieldType;
import org.lobobrowser.primary.gui.FormField;
import org.lobobrowser.primary.gui.FormPanel;
import org.lobobrowser.primary.gui.StringListControl;
import org.lobobrowser.primary.gui.SwingTasks;
import org.lobobrowser.settings.GeneralSettings;

/**
 * The Class GeneralSettingsUI.
 */
public class GeneralSettingsUI extends AbstractSettingsUI {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The settings. */
    private final GeneralSettings settings = GeneralSettings.getInstance();

    /** The ie version field. */
    private final FormField ieVersionField;

    /** The mozilla version field. */
    private final FormField mozillaVersionField;

    /** The ie spoof panel. */
    private final CheckBoxPanel ieSpoofPanel;

    /** The moz panel. */
    private final FormPanel mozPanel;

    /** The ie panel. */
    private final FormPanel iePanel;

    /** The startup pages string list control. */
    private final StringListControl startupPagesStringListControl;

    /**
     * Instantiates a new general settings ui.
     */
    public GeneralSettingsUI() {
        this.ieVersionField = new FormField(FieldType.TEXT, "MSIE Version:");
        this.mozillaVersionField = new FormField(FieldType.TEXT,
                "Mozilla Version:");
        this.mozillaVersionField.setToolTip("Mozilla compatibility version.");
        FormPanel iePanel = new FormPanel();
        this.iePanel = iePanel;
        iePanel.addField(this.ieVersionField);
        iePanel.setBorder(new EmptyBorder(1, 8, 8, 0));
        this.ieSpoofPanel = new CheckBoxPanel(
                "Include \"MSIE\" in User-Agent header.", iePanel);
        this.mozPanel = new FormPanel();
        mozPanel.addField(this.mozillaVersionField);
        this.startupPagesStringListControl = new StringListControl();
        this.startupPagesStringListControl
        .setEditListCaption("You may provide up to "
                + MAX_STARTUP_PAGES + " startup URLs, one per line.");
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(this.getStartupGroupBox());
        this.add(Box.createRigidArea(new Dimension(8, 8)));
        this.add(this.getUserAgentGroupBox());
        this.add(SwingTasks.createVerticalFill());
        this.loadSettings();
        this.ieSpoofPanel.updateEnabling();
    }

    /** The Constant MAX_STARTUP_PAGES. */
    private static final int MAX_STARTUP_PAGES = 4;

    /**
     * Gets the startup group box.
     *
     * @return the startup group box
     */
    private Component getStartupGroupBox() {
        Box startupGroupBox = new Box(BoxLayout.Y_AXIS);
        startupGroupBox.setBorder(new TitledBorder(new EtchedBorder(),
                "Startup"));
        Box startupPagesBox = new Box(BoxLayout.X_AXIS);
        JLabel pagesLabel = new JLabel("Pages:");
        pagesLabel.setToolTipText("Up to " + MAX_STARTUP_PAGES
                + " pages launched when you first run the browser.");
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

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.primary.gui.prefs.AbstractSettingsUI#restoreDefaults()
     */
    @Override
    public void restoreDefaults() {
        this.settings.restoreDefaults();
        this.loadSettings();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.primary.gui.prefs.AbstractSettingsUI#save()
     */
    @Override
    public void save() {
        GeneralSettings settings = this.settings;
        settings.setSpoofIE(this.ieSpoofPanel.isSelected());
        settings.setIeVersion(this.ieVersionField.getValue());
        settings.setMozVersion(this.mozillaVersionField.getValue());
        settings.setStartupURLs(this.startupPagesStringListControl.getStrings());
        settings.save();
    }

    /**
     * Load settings.
     */
    private void loadSettings() {
        GeneralSettings settings = this.settings;
        this.ieSpoofPanel.setSelected(settings.isSpoofIE());
        this.ieVersionField.setValue(settings.getIeVersion());
        this.mozillaVersionField.setValue(settings.getMozVersion());
        this.mozPanel.revalidate();
        this.iePanel.revalidate();
        this.startupPagesStringListControl
        .setStrings(settings.getStartupURLs());
    }
}
