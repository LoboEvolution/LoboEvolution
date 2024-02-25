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

package com.jtattoo.plaf.acryl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.UIDefaults;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.IconFactory;

/**
 * <p>AcrylLookAndFeel class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AcrylLookAndFeel extends AbstractLookAndFeel {

	private static final long serialVersionUID = 1L;

	private static AcrylDefaultTheme myTheme = null;

	private static final List<String> themesList = new ArrayList<>();
	private static final Map<String, Properties> themesMap = new HashMap<>();
	private static final Properties defaultProps = new Properties();
	private static final Properties smallFontProps = new Properties();
	private static final Properties largeFontProps = new Properties();
	private static final Properties giantFontProps = new Properties();
	private static final Properties greenProps = new Properties();
	private static final Properties greenSmallFontProps = new Properties();
	private static final Properties greenLargeFontProps = new Properties();
	private static final Properties greenGiantFontProps = new Properties();
	private static final Properties lemmonProps = new Properties();
	private static final Properties lemmonSmallFontProps = new Properties();
	private static final Properties lemmonLargeFontProps = new Properties();
	private static final Properties lemmonGiantFontProps = new Properties();
	private static final Properties redProps = new Properties();
	private static final Properties redSmallFontProps = new Properties();
	private static final Properties redLargeFontProps = new Properties();
	private static final Properties redGiantFontProps = new Properties();

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

		greenProps.setProperty("backgroundColor", "232 229 222");
		greenProps.setProperty("alterBackgroundColor", "220 216 205");
		greenProps.setProperty("frameColor", "28 64 43");
		greenProps.setProperty("selectionBackgroundColor", "54 126 85");
		greenProps.setProperty("menuSelectionBackgroundColor", "54 126 85");
		greenProps.setProperty("controlColorLight", "64 149 100");
		greenProps.setProperty("controlColorDark", "48 112 75");
		greenProps.setProperty("rolloverColor", "255 213 113");
		greenProps.setProperty("rolloverColorLight", "255 213 113");
		greenProps.setProperty("rolloverColorDark", "240 168 0");
		greenProps.setProperty("windowTitleBackgroundColor", "64 149 100");
		greenProps.setProperty("windowTitleColorLight", "64 149 100");
		greenProps.setProperty("windowTitleColorDark", "48 112 75");
		greenProps.setProperty("windowBorderColor", "40 94 63");
		greenProps.setProperty("windowInactiveTitleBackgroundColor", "77 179 120");
		greenProps.setProperty("windowInactiveTitleColorLight", "77 179 120");
		greenProps.setProperty("windowInactiveTitleColorDark", "64 149 100");
		greenProps.setProperty("windowInactiveBorderColor", "64 149 100");
		greenProps.setProperty("menuBackgroundColor", "232 229 222");
		greenProps.setProperty("menuColorLight", "238 236 232");
		greenProps.setProperty("menuColorDark", "232 229 222");
		greenProps.setProperty("toolbarBackgroundColor", "232 229 222");
		greenProps.setProperty("toolbarColorLight", "238 236 232");
		greenProps.setProperty("toolbarColorDark", "232 229 222");
		greenProps.setProperty("desktopColor", "244 242 232");

		lemmonProps.setProperty("backgroundColor", "240 243 242");
		lemmonProps.setProperty("frameColor", "100 133 14");
		lemmonProps.setProperty("selectionForegroundColor", "0 0 0");
		lemmonProps.setProperty("selectionBackgroundColor", "175 232 28");
		lemmonProps.setProperty("rolloverColor", "231 253 104");
		lemmonProps.setProperty("rolloverColorLight", "243 254 180");
		lemmonProps.setProperty("rolloverColorDark", "231 253 104");
		lemmonProps.setProperty("windowTitleForegroundColor", "243 254 180");
		lemmonProps.setProperty("windowTitleBackgroundColor", "164 217 23");
		lemmonProps.setProperty("windowTitleColorLight", "164 217 23");
		lemmonProps.setProperty("windowTitleColorDark", "140 186 20");
		lemmonProps.setProperty("windowBorderColor", "106 140 15");
		lemmonProps.setProperty("windowInactiveTitleForegroundColor", "243 254 180");
		lemmonProps.setProperty("windowInactiveTitleBackgroundColor", "148 196 21");
		lemmonProps.setProperty("windowInactiveTitleColorLight", "148 196 21");
		lemmonProps.setProperty("windowInactiveTitleColorDark", "126 167 18");
		lemmonProps.setProperty("windowInactiveBorderColor", "92 123 13");
		lemmonProps.setProperty("controlColorLight", "207 245 35");
		lemmonProps.setProperty("controlColorDark", "155 211 18");
		lemmonProps.setProperty("menuBackgroundColor", "240 243 242");
		lemmonProps.setProperty("menuSelectionForegroundColor", "0 0 0");
		lemmonProps.setProperty("menuSelectionBackgroundColor", "175 232 28");
		lemmonProps.setProperty("menuColorLight", "244 247 245");
		lemmonProps.setProperty("menuColorDark", "232 236 235");
		lemmonProps.setProperty("toolbarBackgroundColor", "240 243 242");
		lemmonProps.setProperty("toolbarColorLight", "244 247 245");
		lemmonProps.setProperty("toolbarColorDark", "232 236 235");

		redProps.setProperty("backgroundColor", "244 244 244");
		redProps.setProperty("frameColor", "64 48 48");
		redProps.setProperty("selectionForegroundColor", "255 255 255");
		redProps.setProperty("selectionBackgroundColor", "220 0 0");
		redProps.setProperty("rolloverColor", "222 222 190");
		redProps.setProperty("rolloverColorLight", "248 248 180");
		redProps.setProperty("rolloverColorDark", "200 200 120");
		redProps.setProperty("windowTitleForegroundColor", "255 255 255");
		redProps.setProperty("windowTitleBackgroundColor", "160 0 0");
		redProps.setProperty("windowTitleColorLight", "230 12 12");
		redProps.setProperty("windowTitleColorDark", "190 0 0");
		redProps.setProperty("windowBorderColor", "160 0 0");
		redProps.setProperty("windowInactiveTitleForegroundColor", "255 255 255");
		redProps.setProperty("windowInactiveTitleBackgroundColor", "180 0 0");
		redProps.setProperty("windowInactiveTitleColorLight", "255 24 24");
		redProps.setProperty("windowInactiveTitleColorDark", "180 0 0");
		redProps.setProperty("windowInactiveBorderColor", "180 0 0");
		redProps.setProperty("controlColorLight", "255 24 24");
		redProps.setProperty("controlColorDark", "190 0 0");
		redProps.setProperty("menuBackgroundColor", "248 248 248");
		redProps.setProperty("menuSelectionForegroundColor", "255 255 255");
		redProps.setProperty("menuSelectionBackgroundColor", "220 0 0");
		redProps.setProperty("menuColorLight", "248 248 248");
		redProps.setProperty("menuColorDark", "236 236 236");
		redProps.setProperty("toolbarBackgroundColor", "248 248 248");
		redProps.setProperty("toolbarColorLight", "248 248 248");
		redProps.setProperty("toolbarColorDark", "236 236 236");

		String key;
		String value;
		Iterator<?> iter = smallFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = smallFontProps.getProperty(key);
			greenSmallFontProps.setProperty(key, value);
			lemmonSmallFontProps.setProperty(key, value);
			redSmallFontProps.setProperty(key, value);
		}
		iter = largeFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = largeFontProps.getProperty(key);
			greenLargeFontProps.setProperty(key, value);
			lemmonLargeFontProps.setProperty(key, value);
			redLargeFontProps.setProperty(key, value);
		}
		iter = giantFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = giantFontProps.getProperty(key);
			greenGiantFontProps.setProperty(key, value);
			lemmonGiantFontProps.setProperty(key, value);
			redGiantFontProps.setProperty(key, value);
		}

		iter = greenProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = greenProps.getProperty(key);
			greenSmallFontProps.setProperty(key, value);
			greenLargeFontProps.setProperty(key, value);
			greenGiantFontProps.setProperty(key, value);
		}
		iter = lemmonProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = lemmonProps.getProperty(key);
			lemmonSmallFontProps.setProperty(key, value);
			lemmonLargeFontProps.setProperty(key, value);
			lemmonGiantFontProps.setProperty(key, value);
		}
		iter = redProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = redProps.getProperty(key);
			redSmallFontProps.setProperty(key, value);
			redLargeFontProps.setProperty(key, value);
			redGiantFontProps.setProperty(key, value);
		}

		themesList.add("Default");
		themesList.add("Small-Font");
		themesList.add("Large-Font");
		themesList.add("Giant-Font");

		themesList.add("Green");
		themesList.add("Green-Small-Font");
		themesList.add("Green-Large-Font");
		themesList.add("Green-Giant-Font");

		themesList.add("Lemmon");
		themesList.add("Lemmon-Small-Font");
		themesList.add("Lemmon-Large-Font");
		themesList.add("Lemmon-Giant-Font");

		themesList.add("Red");
		themesList.add("Red-Small-Font");
		themesList.add("Red-Large-Font");
		themesList.add("Red-Giant-Font");

		themesMap.put("Default", defaultProps);
		themesMap.put("Small-Font", smallFontProps);
		themesMap.put("Large-Font", largeFontProps);
		themesMap.put("Giant-Font", giantFontProps);

		themesMap.put("Green", greenProps);
		themesMap.put("Green-Small-Font", greenSmallFontProps);
		themesMap.put("Green-Large-Font", greenLargeFontProps);
		themesMap.put("Green-Giant-Font", greenGiantFontProps);

		themesMap.put("Lemmon", lemmonProps);
		themesMap.put("Lemmon-Small-Font", lemmonSmallFontProps);
		themesMap.put("Lemmon-Large-Font", lemmonLargeFontProps);
		themesMap.put("Lemmon-Giant-Font", lemmonGiantFontProps);

		themesMap.put("Red", redProps);
		themesMap.put("Red-Small-Font", redSmallFontProps);
		themesMap.put("Red-Large-Font", redLargeFontProps);
		themesMap.put("Red-Giant-Font", redGiantFontProps);
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
		currentThemeName = "acrylTheme";
		if (myTheme == null) {
			myTheme = new AcrylDefaultTheme();
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
			myTheme = new AcrylDefaultTheme();
		}
		setTheme(myTheme);
	}

	/** {@inheritDoc} */
	@Override
	public BorderFactory getBorderFactory() {
		return AcrylBorderFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return "The Acryl Look and Feel";
	}

	/** {@inheritDoc} */
	@Override
	public IconFactory getIconFactory() {
		return AcrylIconFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getID() {
		return "Acryl";
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return "Acryl";
	}

	/** {@inheritDoc} */
	@Override
	protected void initClassDefaults(final UIDefaults table) {
		if (!"acrylTheme".equals(currentThemeName)) {
			setTheme("Default");
		}
		super.initClassDefaults(table);
		final Object[] uiDefaults = {
				// BaseLookAndFeel classes
				"ToggleButtonUI", BaseToggleButtonUI.class.getName(), "LabelUI", BaseLabelUI.class.getName(),
				"SeparatorUI", BaseSeparatorUI.class.getName(), "TextFieldUI", BaseTextFieldUI.class.getName(),
				"TextAreaUI", BaseTextAreaUI.class.getName(), "EditorPaneUI", BaseEditorPaneUI.class.getName(),
				"PasswordFieldUI", BasePasswordFieldUI.class.getName(), "CheckBoxUI", BaseCheckBoxUI.class.getName(),
				"RadioButtonUI", BaseRadioButtonUI.class.getName(), "SplitPaneUI", BaseSplitPaneUI.class.getName(),
				"ToolTipUI", BaseToolTipUI.class.getName(), "TreeUI", BaseTreeUI.class.getName(), "TableUI",
				BaseTableUI.class.getName(), "SliderUI", BaseSliderUI.class.getName(), "ProgressBarUI",
				BaseProgressBarUI.class.getName(), "ScrollPaneUI", BaseScrollPaneUI.class.getName(), "PanelUI",
				BasePanelUI.class.getName(), "TableHeaderUI", BaseTableHeaderUI.class.getName(), "FileChooserUI",
				BaseFileChooserUI.class.getName(), "MenuBarUI", BaseMenuBarUI.class.getName(), "MenuUI",
				BaseMenuUI.class.getName(), "PopupMenuUI", BasePopupMenuUI.class.getName(), "MenuItemUI",
				BaseMenuItemUI.class.getName(), "CheckBoxMenuItemUI", BaseCheckBoxMenuItemUI.class.getName(),
				"RadioButtonMenuItemUI", BaseRadioButtonMenuItemUI.class.getName(), "PopupMenuSeparatorUI",
				BaseSeparatorUI.class.getName(), "DesktopPaneUI", BaseDesktopPaneUI.class.getName(),

				// AcrylLookAndFeel classes
				"ButtonUI", AcrylButtonUI.class.getName(), "ComboBoxUI", AcrylComboBoxUI.class.getName(),
				"TabbedPaneUI", AcrylTabbedPaneUI.class.getName(), "ToolBarUI", AcrylToolBarUI.class.getName(),
				"InternalFrameUI", AcrylInternalFrameUI.class.getName(), "RootPaneUI", AcrylRootPaneUI.class.getName(),
				"ScrollBarUI", AcrylScrollBarUI.class.getName(), };
		table.putDefaults(uiDefaults);
		table.put("FormattedTextFieldUI", BaseFormattedTextFieldUI.class.getName());
		table.put("SpinnerUI", AcrylSpinnerUI.class.getName());
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

} // end of class AcrylLookAndFeel
