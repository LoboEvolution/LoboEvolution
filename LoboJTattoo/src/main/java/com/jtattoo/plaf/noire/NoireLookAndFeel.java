/*
* Copyright (c) 2002 and later by MH Software-Entwicklung. All Rights Reserved.
*
* JTattoo is multiple licensed. If your are an open source developer you can use
* it under the terms and conditions of the GNU General Public License version 2.0
* or later as published by the Free Software Foundation.
*
* see: gpl-2.0.txt
*
* If you pay for a license you will become a registered user who could use the
* software under the terms and conditions of the GNU Lesser General Public License
* version 2.0 or later with classpath exception as published by the Free Software
* Foundation.
*
* see: lgpl-2.0.txt
* see: classpath-exception.txt
*
* Registered users could also use JTattoo under the terms and conditions of the
* Apache License, Version 2.0 as published by the Apache Software Foundation.
*
* see: APACHE-LICENSE-2.0.txt
 */
package com.jtattoo.plaf.noire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.UIDefaults;

import com.jtattoo.plaf.AbstractBorderFactory;
import com.jtattoo.plaf.AbstractIconFactory;
import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.BaseCheckBoxMenuItemUI;
import com.jtattoo.plaf.BaseComboBoxUI;
import com.jtattoo.plaf.BaseDesktopPaneUI;
import com.jtattoo.plaf.BaseEditorPaneUI;
import com.jtattoo.plaf.BaseFileChooserUI;
import com.jtattoo.plaf.BaseFormattedTextFieldUI;
import com.jtattoo.plaf.BaseMenuItemUI;
import com.jtattoo.plaf.BaseMenuUI;
import com.jtattoo.plaf.BasePasswordFieldUI;
import com.jtattoo.plaf.BasePopupMenuUI;
import com.jtattoo.plaf.BaseProgressBarUI;
import com.jtattoo.plaf.BaseRadioButtonMenuItemUI;
import com.jtattoo.plaf.BaseScrollBarUI;
import com.jtattoo.plaf.BaseSeparatorUI;
import com.jtattoo.plaf.BaseSpinnerUI;
import com.jtattoo.plaf.BaseSplitPaneUI;
import com.jtattoo.plaf.BaseTableHeaderUI;
import com.jtattoo.plaf.BaseTableUI;
import com.jtattoo.plaf.BaseTextAreaUI;
import com.jtattoo.plaf.BaseTextFieldUI;
import com.jtattoo.plaf.BaseToolTipUI;
import com.jtattoo.plaf.BaseTreeUI;
import com.jtattoo.plaf.hifi.HiFiBorderFactory;
import com.jtattoo.plaf.hifi.HiFiButtonUI;
import com.jtattoo.plaf.hifi.HiFiCheckBoxUI;
import com.jtattoo.plaf.hifi.HiFiInternalFrameUI;
import com.jtattoo.plaf.hifi.HiFiLabelUI;
import com.jtattoo.plaf.hifi.HiFiMenuBarUI;
import com.jtattoo.plaf.hifi.HiFiPanelUI;
import com.jtattoo.plaf.hifi.HiFiRadioButtonUI;
import com.jtattoo.plaf.hifi.HiFiRootPaneUI;
import com.jtattoo.plaf.hifi.HiFiScrollPaneUI;
import com.jtattoo.plaf.hifi.HiFiSliderUI;
import com.jtattoo.plaf.hifi.HiFiTabbedPaneUI;
import com.jtattoo.plaf.hifi.HiFiToggleButtonUI;
import com.jtattoo.plaf.hifi.HiFiToolBarUI;

/**
 * <p>NoireLookAndFeel class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class NoireLookAndFeel extends AbstractLookAndFeel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static NoireDefaultTheme myTheme = null;

	private static final List<String> themesList = new ArrayList<String>();
	private static final Map<String, Properties> themesMap = new HashMap<String, Properties>();
	private static final Properties defaultProps = new Properties();
	private static final Properties smallFontProps = new Properties();
	private static final Properties largeFontProps = new Properties();
	private static final Properties giantFontProps = new Properties();

	static {
		smallFontProps.setProperty("controlTextFont", "Dialog bold 10");
		smallFontProps.setProperty("systemTextFont", "Dialog bold 10");
		smallFontProps.setProperty("userTextFont", "Dialog 10");
		smallFontProps.setProperty("menuTextFont", "Dialog bold 10");
		smallFontProps.setProperty("windowTitleFont", "Dialog bold 10");
		smallFontProps.setProperty("subTextFont", "Dialog 8");

		largeFontProps.setProperty("controlTextFont", "Dialog bold 14");
		largeFontProps.setProperty("systemTextFont", "Dialog bold 14");
		largeFontProps.setProperty("userTextFont", "Dialog bold 14");
		largeFontProps.setProperty("menuTextFont", "Dialog bold 14");
		largeFontProps.setProperty("windowTitleFont", "Dialog bold 14");
		largeFontProps.setProperty("subTextFont", "Dialog 12");

		giantFontProps.setProperty("controlTextFont", "Dialog 18");
		giantFontProps.setProperty("systemTextFont", "Dialog 18");
		giantFontProps.setProperty("userTextFont", "Dialog 18");
		giantFontProps.setProperty("menuTextFont", "Dialog 18");
		giantFontProps.setProperty("windowTitleFont", "Dialog 18");
		giantFontProps.setProperty("subTextFont", "Dialog 16");

		themesList.add("Default");
		themesList.add("Small-Font");
		themesList.add("Large-Font");
		themesList.add("Giant-Font");

		themesMap.put("Default", defaultProps);
		themesMap.put("Small-Font", smallFontProps);
		themesMap.put("Large-Font", largeFontProps);
		themesMap.put("Giant-Font", giantFontProps);

	}

	/**
	 * <p>getThemeProperties.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link java.util.Properties} object.
	 */
	public static Properties getThemeProperties(String name) {
		return themesMap.get(name);
	}

	/**
	 * <p>getThemes.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> getThemes() {
		return themesList;
	}

	/**
	 * <p>setCurrentTheme.</p>
	 *
	 * @param themesProps a {@link java.util.Properties} object.
	 */
	public static void setCurrentTheme(Properties themesProps) {
		setTheme(themesProps);
	}

	/**
	 * <p>setTheme.</p>
	 *
	 * @param themesProps a {@link java.util.Properties} object.
	 */
	public static void setTheme(Properties themesProps) {
		currentThemeName = "noireTheme";
		if (myTheme == null) {
			myTheme = new NoireDefaultTheme();
		}
		if (myTheme != null && themesProps != null) {
			myTheme.setUpColor();
			myTheme.setProperties(themesProps);
			myTheme.setUpColorArrs();
			AbstractLookAndFeel.setTheme(myTheme);
		}
	}

	/**
	 * <p>setTheme.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public static void setTheme(String name) {
		AbstractTheme.setInternalName(name);
		setTheme(themesMap.get(name));
	}

	/**
	 * <p>setTheme.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param licenseKey a {@link java.lang.String} object.
	 * @param logoString a {@link java.lang.String} object.
	 */
	public static void setTheme(String name, String licenseKey, String logoString) {
		Properties props = themesMap.get(name);
		if (props != null) {
			props.put("licenseKey", licenseKey);
			props.put("logoString", logoString);
			AbstractTheme.setInternalName(name);
			setTheme(props);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void createDefaultTheme() {
		if (myTheme == null) {
			myTheme = new NoireDefaultTheme();
		}
		setTheme(myTheme);
	}

	/** {@inheritDoc} */
	@Override
	public AbstractBorderFactory getBorderFactory() {
		return HiFiBorderFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return "The Noire Look and Feel";
	}

	/** {@inheritDoc} */
	@Override
	public AbstractIconFactory getIconFactory() {
		return NoireIconFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getID() {
		return "Noire";
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return "Noire";
	}

	/** {@inheritDoc} */
	@Override
	protected void initClassDefaults(UIDefaults table) {
		if (!"noireTheme".equals(currentThemeName)) {
			setTheme("Default");
		}
		super.initClassDefaults(table);
		Object[] uiDefaults = {
				// BaseLookAndFeel classes
				"SeparatorUI", BaseSeparatorUI.class.getName(), "TextFieldUI", BaseTextFieldUI.class.getName(),
				"TextAreaUI", BaseTextAreaUI.class.getName(), "EditorPaneUI", BaseEditorPaneUI.class.getName(),
				"PasswordFieldUI", BasePasswordFieldUI.class.getName(), "ComboBoxUI", BaseComboBoxUI.class.getName(),
				"ToolTipUI", BaseToolTipUI.class.getName(), "TreeUI", BaseTreeUI.class.getName(), "TableUI",
				BaseTableUI.class.getName(), "TableHeaderUI", BaseTableHeaderUI.class.getName(), "SplitPaneUI",
				BaseSplitPaneUI.class.getName(), "ProgressBarUI", BaseProgressBarUI.class.getName(), "FileChooserUI",
				BaseFileChooserUI.class.getName(), "ScrollBarUI", BaseScrollBarUI.class.getName(), "MenuUI",
				BaseMenuUI.class.getName(), "PopupMenuUI", BasePopupMenuUI.class.getName(), "MenuItemUI",
				BaseMenuItemUI.class.getName(), "CheckBoxMenuItemUI", BaseCheckBoxMenuItemUI.class.getName(),
				"RadioButtonMenuItemUI", BaseRadioButtonMenuItemUI.class.getName(), "PopupMenuSeparatorUI",
				BaseSeparatorUI.class.getName(), "DesktopPaneUI", BaseDesktopPaneUI.class.getName(),
				// HiFiLookAndFeel classes
				"LabelUI", HiFiLabelUI.class.getName(), "CheckBoxUI", HiFiCheckBoxUI.class.getName(), "RadioButtonUI",
				HiFiRadioButtonUI.class.getName(), "ButtonUI", HiFiButtonUI.class.getName(), "ToggleButtonUI",
				HiFiToggleButtonUI.class.getName(), "SliderUI", HiFiSliderUI.class.getName(), "PanelUI",
				HiFiPanelUI.class.getName(), "ScrollPaneUI", HiFiScrollPaneUI.class.getName(), "TabbedPaneUI",
				HiFiTabbedPaneUI.class.getName(), "ToolBarUI", HiFiToolBarUI.class.getName(), "MenuBarUI",
				HiFiMenuBarUI.class.getName(), "InternalFrameUI", HiFiInternalFrameUI.class.getName(), "RootPaneUI",
				HiFiRootPaneUI.class.getName(), };
		table.putDefaults(uiDefaults);
		table.put("FormattedTextFieldUI", BaseFormattedTextFieldUI.class.getName());
		table.put("SpinnerUI", BaseSpinnerUI.class.getName());
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNativeLookAndFeel() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSupportedLookAndFeel() {
		return true;
	}

} // end of class NoireLookAndFeel
