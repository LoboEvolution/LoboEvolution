/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package com.jtattoo.plaf.luna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.UIDefaults;

import com.jtattoo.plaf.BorderFactory;
import com.jtattoo.plaf.IconFactory;
import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.base.BaseCheckBoxMenuItemUI;
import com.jtattoo.plaf.base.BaseCheckBoxUI;
import com.jtattoo.plaf.base.BaseDesktopPaneUI;
import com.jtattoo.plaf.base.BaseEditorPaneUI;
import com.jtattoo.plaf.base.BaseFileChooserUI;
import com.jtattoo.plaf.base.BaseFormattedTextFieldUI;
import com.jtattoo.plaf.base.BaseLabelUI;
import com.jtattoo.plaf.base.BaseMenuBarUI;
import com.jtattoo.plaf.base.BaseMenuItemUI;
import com.jtattoo.plaf.base.BaseMenuUI;
import com.jtattoo.plaf.base.BasePanelUI;
import com.jtattoo.plaf.base.BasePasswordFieldUI;
import com.jtattoo.plaf.base.BasePopupMenuUI;
import com.jtattoo.plaf.base.BaseProgressBarUI;
import com.jtattoo.plaf.base.BaseRadioButtonMenuItemUI;
import com.jtattoo.plaf.base.BaseRadioButtonUI;
import com.jtattoo.plaf.base.BaseScrollPaneUI;
import com.jtattoo.plaf.base.BaseSeparatorUI;
import com.jtattoo.plaf.base.BaseSliderUI;
import com.jtattoo.plaf.base.BaseSpinnerUI;
import com.jtattoo.plaf.base.BaseSplitPaneUI;
import com.jtattoo.plaf.base.BaseTableUI;
import com.jtattoo.plaf.base.BaseTextAreaUI;
import com.jtattoo.plaf.base.BaseTextFieldUI;
import com.jtattoo.plaf.base.BaseToggleButtonUI;
import com.jtattoo.plaf.base.BaseToolTipUI;
import com.jtattoo.plaf.base.BaseTreeUI;

/**
 * <p>LunaLookAndFeel class.</p>
 *
 * Author Michael Hagen
 *
 */
public class LunaLookAndFeel extends AbstractLookAndFeel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static LunaDefaultTheme myTheme = null;

	private static final List<String> themesList = new ArrayList<>();
	private static final Map<String, Properties> themesMap = new HashMap<>();
	private static final Properties defaultProps = new Properties();
	private static final Properties smallFontProps = new Properties();
	private static final Properties largeFontProps = new Properties();
	private static final Properties giantFontProps = new Properties();

	static {
		smallFontProps.setProperty("controlTextFont", "Dialog 10");
		smallFontProps.setProperty("systemTextFont", "Dialog 10");
		smallFontProps.setProperty("userTextFont", "Dialog 10");
		smallFontProps.setProperty("menuTextFont", "Dialog 10");
		smallFontProps.setProperty("windowTitleFont", "Dialog bold 10");
		smallFontProps.setProperty("subTextFont", "Dialog 8");

		largeFontProps.setProperty("controlTextFont", "Dialog 14");
		largeFontProps.setProperty("systemTextFont", "Dialog 14");
		largeFontProps.setProperty("userTextFont", "Dialog 14");
		largeFontProps.setProperty("menuTextFont", "Dialog 14");
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
	public static Properties getThemeProperties(final String name) {
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
	public static void setCurrentTheme(final Properties themesProps) {
		setTheme(themesProps);
	}

	/**
	 * <p>setTheme.</p>
	 *
	 * @param themesProps a {@link java.util.Properties} object.
	 */
	public static void setTheme(final Properties themesProps) {
		currentThemeName = "lunaTheme";
		if (myTheme == null) {
			myTheme = new LunaDefaultTheme();
		}
		if (myTheme != null && themesProps != null) {
			myTheme.setUpColor();
			myTheme.setProperties(themesProps);
			myTheme.setUpColorArrs();
			AbstractLookAndFeel.setTheme(myTheme);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>setTheme.</p>
	 */
	public static void setTheme(final String name) {
		setTheme(themesMap.get(name));
		if (myTheme != null) {
			AbstractTheme.setInternalName(name);
		}
	}

	/**
	 * <p>setTheme.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param licenseKey a {@link java.lang.String} object.
	 * @param logoString a {@link java.lang.String} object.
	 */
	public static void setTheme(final String name, final String licenseKey, final String logoString) {
		final Properties props = themesMap.get(name);
		if (props != null) {
			props.put("licenseKey", licenseKey);
			props.put("logoString", logoString);
			setTheme(props);
			if (myTheme != null) {
				AbstractTheme.setInternalName(name);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void createDefaultTheme() {
		if (myTheme == null) {
			myTheme = new LunaDefaultTheme();
		}
		setTheme(myTheme);
	}

	/** {@inheritDoc} */
	@Override
	public BorderFactory getBorderFactory() {
		return LunaBorderFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return "The XP Look and Feel";
	}

	/** {@inheritDoc} */
	@Override
	public IconFactory getIconFactory() {
		return LunaIconFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getID() {
		return "XP";
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return "XP";
	}

	/** {@inheritDoc} */
	@Override
	protected void initClassDefaults(final UIDefaults table) {
		if (!"lunaTheme".equals(currentThemeName)) {
			setTheme("Default");
		}
		super.initClassDefaults(table);
		final Object[] uiDefaults = {
				// BaseLookAndFeel classes
				"LabelUI", BaseLabelUI.class.getName(), "ToggleButtonUI", BaseToggleButtonUI.class.getName(),
				"SeparatorUI", BaseSeparatorUI.class.getName(), "TextFieldUI", BaseTextFieldUI.class.getName(),
				"TextAreaUI", BaseTextAreaUI.class.getName(), "EditorPaneUI", BaseEditorPaneUI.class.getName(),
				"PasswordFieldUI", BasePasswordFieldUI.class.getName(), "CheckBoxUI", BaseCheckBoxUI.class.getName(),
				"RadioButtonUI", BaseRadioButtonUI.class.getName(), "ToolTipUI", BaseToolTipUI.class.getName(),
				"TreeUI", BaseTreeUI.class.getName(), "TableUI", BaseTableUI.class.getName(), "SliderUI",
				BaseSliderUI.class.getName(), "ProgressBarUI", BaseProgressBarUI.class.getName(), "ScrollPaneUI",
				BaseScrollPaneUI.class.getName(), "PanelUI", BasePanelUI.class.getName(), "SplitPaneUI",
				BaseSplitPaneUI.class.getName(), "FileChooserUI", BaseFileChooserUI.class.getName(), "MenuBarUI",
				BaseMenuBarUI.class.getName(), "MenuUI", BaseMenuUI.class.getName(), "PopupMenuUI",
				BasePopupMenuUI.class.getName(), "MenuItemUI", BaseMenuItemUI.class.getName(), "CheckBoxMenuItemUI",
				BaseCheckBoxMenuItemUI.class.getName(), "RadioButtonMenuItemUI",
				BaseRadioButtonMenuItemUI.class.getName(), "PopupMenuSeparatorUI", BaseSeparatorUI.class.getName(),
				"DesktopPaneUI", BaseDesktopPaneUI.class.getName(),
				// LunaLookAndFeel classes
				"ButtonUI", LunaButtonUI.class.getName(), "ComboBoxUI", LunaComboBoxUI.class.getName(), "ScrollBarUI",
				LunaScrollBarUI.class.getName(), "TabbedPaneUI", LunaTabbedPaneUI.class.getName(), "TableHeaderUI",
				LunaTableHeaderUI.class.getName(), "ToolBarUI", LunaToolBarUI.class.getName(), "InternalFrameUI",
				LunaInternalFrameUI.class.getName(), "RootPaneUI", LunaRootPaneUI.class.getName(), };
		table.putDefaults(uiDefaults);
		table.put("FormattedTextFieldUI", BaseFormattedTextFieldUI.class.getName());
		table.put("SpinnerUI", BaseSpinnerUI.class.getName());
	}

	/** {@inheritDoc} */
	@Override
	protected void initComponentDefaults(final UIDefaults table) {
		super.initComponentDefaults(table);
		table.put("ScrollBar.incrementButtonGap", -1);
		table.put("ScrollBar.decrementButtonGap", -1);
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

} // end of class LunaLookAndFeel
