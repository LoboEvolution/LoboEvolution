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
package com.jtattoo.plaf.graphite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.UIDefaults;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.BorderFactory;

/**
 * <p>GraphiteLookAndFeel class.</p>
 *
 * Author Michael Hagen
 *
 */
public class GraphiteLookAndFeel extends AbstractLookAndFeel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static GraphiteDefaultTheme myTheme = null;
	private static final List<String> themesList = new ArrayList<>();
	private static final Map<String, Properties> themesMap = new HashMap<>();
	private static final Properties defaultProps = new Properties();
	private static final Properties smallFontProps = new Properties();
	private static final Properties mediumFontProps = new Properties();
	private static final Properties largeFontProps = new Properties();
	private static final Properties greenProps = new Properties();
	private static final Properties greenSmallFontProps = new Properties();
	private static final Properties greenMediumFontProps = new Properties();
	private static final Properties greenLargeFontProps = new Properties();
	private static final Properties blueProps = new Properties();
	private static final Properties blueSmallFontProps = new Properties();
	private static final Properties blueMediumFontProps = new Properties();
	private static final Properties blueLargeFontProps = new Properties();

	static {
		smallFontProps.setProperty("controlTextFont", "Dialog 12");
		smallFontProps.setProperty("systemTextFont", "Dialog 12");
		smallFontProps.setProperty("userTextFont", "Dialog 12");
		smallFontProps.setProperty("menuTextFont", "Dialog 12");
		smallFontProps.setProperty("windowTitleFont", "Dialog bold 12");
		smallFontProps.setProperty("subTextFont", "Dialog 11");

		mediumFontProps.setProperty("controlTextFont", "Dialog 15");
		mediumFontProps.setProperty("systemTextFont", "Dialog 15");
		mediumFontProps.setProperty("userTextFont", "Dialog 15");
		mediumFontProps.setProperty("menuTextFont", "Dialog 15");
		mediumFontProps.setProperty("windowTitleFont", "Dialog bold 15");
		mediumFontProps.setProperty("subTextFont", "Dialog 13");

		largeFontProps.setProperty("controlTextFont", "Dialog 16");
		largeFontProps.setProperty("systemTextFont", "Dialog 16");
		largeFontProps.setProperty("userTextFont", "Dialog 16");
		largeFontProps.setProperty("menuTextFont", "Dialog 16");
		largeFontProps.setProperty("windowTitleFont", "Dialog bold 16");
		largeFontProps.setProperty("subTextFont", "Dialog 14");

		greenProps.setProperty("windowTitleForegroundColor", "255 255 255");
		greenProps.setProperty("windowTitleBackgroundColor", "0 96 52");
		greenProps.setProperty("windowTitleColorLight", "0 136 57");
		greenProps.setProperty("windowTitleColorDark", "0 88 47");
		greenProps.setProperty("windowBorderColor", "0 88 47");
		greenProps.setProperty("windowInactiveTitleForegroundColor", "255 255 255");
		greenProps.setProperty("windowInactiveTitleBackgroundColor", "0 96 50");
		greenProps.setProperty("windowInactiveTitleColorLight", "0 119 51");
		greenProps.setProperty("windowInactiveTitleColorDark", "0 88 47");
		greenProps.setProperty("windowInactiveBorderColor", "0 88 47");
		greenProps.setProperty("backgroundColor", "231 244 219");
		greenProps.setProperty("backgroundColorLight", "255 255 255");
		greenProps.setProperty("backgroundColorDark", "208 234 185");
		greenProps.setProperty("alterBackgroundColor", "208 234 185");
		greenProps.setProperty("frameColor", "114 180 54");
		greenProps.setProperty("disabledForegroundColor", "96 96 96");
		greenProps.setProperty("disabledBackgroundColor", "228 240 216");
		greenProps.setProperty("selectionForegroundColor", "255 255 255");
		greenProps.setProperty("selectionBackgroundColor", "48 136 53");
		greenProps.setProperty("controlBackgroundColor", "231 244 219");
		greenProps.setProperty("controlColorLight", "40 170 60");
		greenProps.setProperty("controlColorDark", "48 136 53");
		greenProps.setProperty("controlDarkShadowColor", "112 176 53");
		greenProps.setProperty("buttonColorLight", "255 255 255");
		greenProps.setProperty("buttonColorDark", "231 244 219");
		greenProps.setProperty("menuBackgroundColor", "255 255 255");
		greenProps.setProperty("menuColorDark", "218 238 200");
		greenProps.setProperty("menuColorLight", "231 244 219");
		greenProps.setProperty("menuSelectionBackgroundColor", "72 51 0");
		greenProps.setProperty("toolbarBackgroundColor", "231 244 219");
		greenProps.setProperty("toolbarColorLight", "231 244 219");
		greenProps.setProperty("toolbarColorDark", "208 234 185");

		blueProps.setProperty("windowTitleForegroundColor", "255 255 255");
		blueProps.setProperty("windowTitleBackgroundColor", "1 49 157");
		blueProps.setProperty("windowTitleColorLight", "1 40 131");
		blueProps.setProperty("windowTitleColorDark", "1 30 97");
		blueProps.setProperty("windowBorderColor", "0 24 83");
		blueProps.setProperty("windowInactiveTitleForegroundColor", "255 255 255");
		blueProps.setProperty("windowInactiveTitleBackgroundColor", "55 87 129");
		blueProps.setProperty("windowInactiveTitleColorLight", "1 35 116");
		blueProps.setProperty("windowInactiveTitleColorDark", "1 30 97");
		blueProps.setProperty("windowInactiveBorderColor", "0 24 83");
		blueProps.setProperty("backgroundColor", "228 235 243");
		blueProps.setProperty("backgroundColorLight", "255 255 255");
		blueProps.setProperty("backgroundColorDark", "188 204 226");
		blueProps.setProperty("alterBackgroundColor", "208 220 234");
		blueProps.setProperty("frameColor", "120 153 197");
		blueProps.setProperty("disabledForegroundColor", "96 96 96");
		blueProps.setProperty("disabledBackgroundColor", "225 232 240");
		blueProps.setProperty("selectionForegroundColor", "255 255 255");
		blueProps.setProperty("selectionBackgroundColor", "1 38 124");
		blueProps.setProperty("controlBackgroundColor", "228 235 243");
		blueProps.setProperty("controlColorLight", "1 40 131");
		blueProps.setProperty("controlColorDark", "1 30 98");
		blueProps.setProperty("controlDarkShadowColor", "64 100 149");
		blueProps.setProperty("buttonColorLight", "255 255 255");
		blueProps.setProperty("buttonColorDark", "228 235 243");
		blueProps.setProperty("menuBackgroundColor", "255 255 255");
		blueProps.setProperty("menuColorDark", "208 220 234");
		blueProps.setProperty("menuColorLight", "228 235 243");
		blueProps.setProperty("menuSelectionBackgroundColor", "72 51 0");
		blueProps.setProperty("toolbarBackgroundColor", "228 235 243");
		blueProps.setProperty("toolbarColorLight", "228 235 243");
		blueProps.setProperty("toolbarColorDark", "188 204 226");

		String key;
		String value;
		Iterator<Object> iter = smallFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = smallFontProps.getProperty(key);
			greenSmallFontProps.setProperty(key, value);
			blueSmallFontProps.setProperty(key, value);
		}

		iter = mediumFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = mediumFontProps.getProperty(key);
			greenMediumFontProps.setProperty(key, value);
			blueMediumFontProps.setProperty(key, value);
		}

		iter = largeFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = largeFontProps.getProperty(key);
			greenLargeFontProps.setProperty(key, value);
			blueLargeFontProps.setProperty(key, value);
		}

		iter = greenProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = greenProps.getProperty(key);
			greenSmallFontProps.setProperty(key, value);
			greenMediumFontProps.setProperty(key, value);
			greenLargeFontProps.setProperty(key, value);
		}

		iter = blueProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = blueProps.getProperty(key);
			blueSmallFontProps.setProperty(key, value);
			blueMediumFontProps.setProperty(key, value);
			blueLargeFontProps.setProperty(key, value);
		}

		themesList.add("Default");
		themesList.add("Small-Font");
		themesList.add("Medium-Font");
		themesList.add("Large-Font");

		themesList.add("Green");
		themesList.add("Green-Small-Font");
		themesList.add("Green-Medium-Font");
		themesList.add("Green-Large-Font");

		themesList.add("Blue");
		themesList.add("Blue-Small-Font");
		themesList.add("Blue-Medium-Font");
		themesList.add("Blue-Large-Font");

		themesMap.put("Default", defaultProps);
		themesMap.put("Small-Font", smallFontProps);
		themesMap.put("Medium-Font", mediumFontProps);
		themesMap.put("Large-Font", largeFontProps);

		themesMap.put("Green", greenProps);
		themesMap.put("Green-Small-Font", greenSmallFontProps);
		themesMap.put("Green-Medium-Font", greenMediumFontProps);
		themesMap.put("Green-Large-Font", greenLargeFontProps);

		themesMap.put("Blue", blueProps);
		themesMap.put("Blue-Small-Font", blueSmallFontProps);
		themesMap.put("Blue-Medium-Font", blueMediumFontProps);
		themesMap.put("Blue-Large-Font", blueLargeFontProps);
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
		currentThemeName = "graphiteTheme";
		if (myTheme == null) {
			myTheme = new GraphiteDefaultTheme();
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
			myTheme = new GraphiteDefaultTheme();
		}
		setTheme(myTheme);
	}

	/** {@inheritDoc} */
	@Override
	public BorderFactory getBorderFactory() {
		return GraphiteBorderFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return "The Graphite Look and Feel";
	}

	/** {@inheritDoc} */
	@Override
	public IconFactory getIconFactory() {
		return GraphiteIconFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getID() {
		return "Graphite";
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return "Graphite";
	}

	/** {@inheritDoc} */
	@Override
	protected void initClassDefaults(final UIDefaults table) {
		if (!"graphiteTheme".equals(currentThemeName)) {
			setTheme("Default");
		}
		super.initClassDefaults(table);
		final Object[] uiDefaults = {
				// BaseLookAndFeel classes
				"LabelUI", BaseLabelUI.class.getName(), "ToggleButtonUI", BaseToggleButtonUI.class.getName(),
				"SeparatorUI", BaseSeparatorUI.class.getName(), "TextFieldUI", BaseTextFieldUI.class.getName(),
				"TextAreaUI", BaseTextAreaUI.class.getName(), "EditorPaneUI", BaseEditorPaneUI.class.getName(),
				"PasswordFieldUI", BasePasswordFieldUI.class.getName(), "ComboBoxUI", BaseComboBoxUI.class.getName(),
				"ToolTipUI", BaseToolTipUI.class.getName(), "TreeUI", BaseTreeUI.class.getName(), "TableUI",
				BaseTableUI.class.getName(), "TableHeaderUI", BaseTableHeaderUI.class.getName(), "ScrollBarUI",
				BaseScrollBarUI.class.getName(), "ScrollPaneUI", BaseScrollPaneUI.class.getName(), "ProgressBarUI",
				BaseProgressBarUI.class.getName(), "PanelUI", BasePanelUI.class.getName(), "SplitPaneUI",
				BaseSplitPaneUI.class.getName(), "SliderUI", BaseSliderUI.class.getName(), "FileChooserUI",
				BaseFileChooserUI.class.getName(), "MenuBarUI", BaseMenuBarUI.class.getName(), "PopupMenuUI",
				BasePopupMenuUI.class.getName(), "PopupMenuSeparatorUI", BaseSeparatorUI.class.getName(),
				"DesktopPaneUI", BaseDesktopPaneUI.class.getName(),
				// GraphiteLookAndFeel classes
				"RadioButtonUI", GraphiteRadioButtonUI.class.getName(), "CheckBoxUI",
				GraphiteCheckBoxUI.class.getName(), "ButtonUI", GraphiteButtonUI.class.getName(), "MenuUI",
				GraphiteMenuUI.class.getName(), "MenuItemUI", GraphiteMenuItemUI.class.getName(), "CheckBoxMenuItemUI",
				GraphiteCheckBoxMenuItemUI.class.getName(), "RadioButtonMenuItemUI",
				GraphiteRadioButtonMenuItemUI.class.getName(), "TabbedPaneUI", GraphiteTabbedPaneUI.class.getName(),
				"ToolBarUI", GraphiteToolBarUI.class.getName(), "InternalFrameUI",
				GraphiteInternalFrameUI.class.getName(), "RootPaneUI", GraphiteRootPaneUI.class.getName(), };
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

} // end of class GraphiteLookAndFeel
