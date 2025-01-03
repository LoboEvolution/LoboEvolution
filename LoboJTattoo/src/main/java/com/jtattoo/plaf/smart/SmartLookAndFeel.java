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
package com.jtattoo.plaf.smart;

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
 * <p>SmartLookAndFeel class.</p>
 *
 * Author Michael Hagen
 *
 */
public class SmartLookAndFeel extends AbstractLookAndFeel {

	private static final long serialVersionUID = 1L;

	private static SmartDefaultTheme myTheme = null;

	private static final List<String> themesList = new ArrayList<>();
	private static final Map<String, Properties> themesMap = new HashMap<>();
	private static final Properties defaultProps = new Properties();
	private static final Properties smallFontProps = new Properties();
	private static final Properties largeFontProps = new Properties();
	private static final Properties giantFontProps = new Properties();
	private static final Properties goldProps = new Properties();
	private static final Properties goldSmallFontProps = new Properties();
	private static final Properties goldLargeFontProps = new Properties();
	private static final Properties goldGiantFontProps = new Properties();
	private static final Properties greenProps = new Properties();
	private static final Properties greenSmallFontProps = new Properties();
	private static final Properties greenLargeFontProps = new Properties();
	private static final Properties greenGiantFontProps = new Properties();
	private static final Properties brownProps = new Properties();
	private static final Properties brownSmallFontProps = new Properties();
	private static final Properties brownLargeFontProps = new Properties();
	private static final Properties brownGiantFontProps = new Properties();
	private static final Properties lemmonProps = new Properties();
	private static final Properties lemmonSmallFontProps = new Properties();
	private static final Properties lemmonLargeFontProps = new Properties();
	private static final Properties lemmonGiantFontProps = new Properties();
	private static final Properties grayProps = new Properties();
	private static final Properties graySmallFontProps = new Properties();
	private static final Properties grayLargeFontProps = new Properties();
	private static final Properties grayGiantFontProps = new Properties();

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

		goldProps.setProperty("focusCellColor", "160 160 120");
		goldProps.setProperty("backgroundColor", "240 240 240");
		goldProps.setProperty("backgroundColorLight", "255 255 255");
		goldProps.setProperty("backgroundColorDark", "230 230 230");
		goldProps.setProperty("alterBackgroundColor", "230 230 230");
		goldProps.setProperty("selectionBackgroundColor", "232 232 180");
		goldProps.setProperty("controlBackgroundColor", "240 240 240");
		goldProps.setProperty("controlColorLight", "248 248 180");
		goldProps.setProperty("controlColorDark", "200 200 120");
		goldProps.setProperty("pressedBackgroundColorLight", "249 249 195");
		goldProps.setProperty("pressedBackgroundColorDark", "232 232 180");
		goldProps.setProperty("buttonColorLight", "255 255 255");
		goldProps.setProperty("buttonColorDark", "230 230 230");
		goldProps.setProperty("rolloverColor", "220 220 169");
		goldProps.setProperty("rolloverColorLight", "250 250 240");
		goldProps.setProperty("rolloverColorDark", "220 220 169");
		goldProps.setProperty("windowTitleBackgroundColor", "248 248 180");
		goldProps.setProperty("windowTitleColorLight", "248 248 180");
		goldProps.setProperty("windowTitleColorDark", "200 200 120");
		goldProps.setProperty("windowInactiveBackgroundColor", "240 240 240");
		goldProps.setProperty("windowInactiveTitleColorLight", "248 248 248");
		goldProps.setProperty("windowInactiveTitleColorDark", "220 220 220");
		goldProps.setProperty("menuBackgroundColor", "248 248 248");
		goldProps.setProperty("menuColorLight", "248 248 248");
		goldProps.setProperty("menuColorDark", "230 230 230");
		goldProps.setProperty("menuSelectionBackgroundColor", "232 232 180");
		goldProps.setProperty("toolbarBackgroundColor", "248 248 248");
		goldProps.setProperty("toolbarColorLight", "248 248 248");
		goldProps.setProperty("toolbarColorDark", "230 230 230");
		goldProps.setProperty("desktopColor", "240 240 240");

		greenProps.setProperty("frameColor", "140 140 140");
		greenProps.setProperty("focusCellColor", "40 100 60");
		greenProps.setProperty("backgroundColor", "240 240 240");
		greenProps.setProperty("backgroundColorLight", "255 255 255");
		greenProps.setProperty("backgroundColorDark", "230 230 230");
		greenProps.setProperty("alterBackgroundColor", "230 230 230");
		greenProps.setProperty("selectionBackgroundColor", "160 210 170");
		greenProps.setProperty("controlBackgroundColor", "240 240 240");
		greenProps.setProperty("controlColorLight", "120 199 153");
		greenProps.setProperty("controlColorDark", "60 142 95");
		greenProps.setProperty("pressedBackgroundColorLight", "171 219 191");
		greenProps.setProperty("pressedBackgroundColorDark", "150 211 176");
		greenProps.setProperty("buttonColorLight", "255 255 255");
		greenProps.setProperty("buttonColorDark", "230 230 230");
		greenProps.setProperty("rolloverColor", "150 211 176");
		greenProps.setProperty("rolloverColorLight", "200 232 214");
		greenProps.setProperty("rolloverColorDark", "131 203 162");
		greenProps.setProperty("windowTitleForegroundColor", "255 255 255");
		greenProps.setProperty("windowTitleBackgroundColor", "100 160 140");
		greenProps.setProperty("windowTitleColorLight", "120 199 153");
		greenProps.setProperty("windowTitleColorDark", "60 142 95");
		greenProps.setProperty("windowInactiveForegroundColor", "0 0 0");
		greenProps.setProperty("windowInactiveBackgroundColor", "240 240 240");
		greenProps.setProperty("windowInactiveTitleColorLight", "248 248 248");
		greenProps.setProperty("windowInactiveTitleColorDark", "220 220 220");
		greenProps.setProperty("menuBackgroundColor", "248 248 248");
		greenProps.setProperty("menuColorLight", "248 248 248");
		greenProps.setProperty("menuColorDark", "230 230 230");
		greenProps.setProperty("menuSelectionBackgroundColor", "160 210 170");
		greenProps.setProperty("toolbarBackgroundColor", "248 248 248");
		greenProps.setProperty("toolbarColorLight", "248 248 248");
		greenProps.setProperty("toolbarColorDark", "230 230 230");
		greenProps.setProperty("desktopColor", "240 240 240");

		brownProps.setProperty("frameColor", "128 124 112");
		brownProps.setProperty("focusCellColor", "255 230 120");
		brownProps.setProperty("backgroundColor", "236 233 216");
		brownProps.setProperty("backgroundColorLight", "255 255 255");
		brownProps.setProperty("backgroundColorDark", "226 222 194");
		brownProps.setProperty("alterBackgroundColor", "226 222 194");
		brownProps.setProperty("selectionBackgroundColor", "224 220 192");
		brownProps.setProperty("controlBackgroundColor", "236 233 216");
		brownProps.setProperty("controlColorLight", "242 240 230");
		brownProps.setProperty("controlColorDark", "215 208 172");
		brownProps.setProperty("pressedBackgroundColorLight", "236 233 216");
		brownProps.setProperty("pressedBackgroundColorDark", "224 220 192");
		brownProps.setProperty("buttonColorLight", "255 255 255");
		brownProps.setProperty("buttonColorDark", "244 242 232");
		brownProps.setProperty("rolloverColor", "210 203 164");
		brownProps.setProperty("rolloverColorLight", "236 233 216");
		brownProps.setProperty("rolloverColorDark", "215 208 172");
		brownProps.setProperty("windowTitleForegroundColor", "0 0 0");
		brownProps.setProperty("windowTitleBackgroundColor", "236 233 216");
		brownProps.setProperty("windowTitleColorLight", "236 233 216");
		brownProps.setProperty("windowTitleColorDark", "215 208 172");
		brownProps.setProperty("windowInactiveBackgroundColor", "240 240 230");
		brownProps.setProperty("windowInactiveTitleColorLight", "242 240 230");
		brownProps.setProperty("windowInactiveTitleColorDark", "236 233 216");
		brownProps.setProperty("menuBackgroundColor", "236 233 216");
		brownProps.setProperty("menuColorLight", "248 245 235");
		brownProps.setProperty("menuColorDark", "236 233 216");
		brownProps.setProperty("menuSelectionBackgroundColor", "215 208 172");
		brownProps.setProperty("toolbarBackgroundColor", "248 248 248");
		brownProps.setProperty("toolbarColorLight", "248 245 235");
		brownProps.setProperty("toolbarColorDark", "236 233 216");
		brownProps.setProperty("desktopColor", "236 233 216");

		lemmonProps.setProperty("frameColor", "91 121 13");
		lemmonProps.setProperty("backgroundColor", "227 231 230");
		lemmonProps.setProperty("backgroundColorLight", "255 255 255");
		lemmonProps.setProperty("backgroundColorDark", "218 222 221");
		lemmonProps.setProperty("alterBackgroundColor", "218 222 221");
		lemmonProps.setProperty("selectionBackgroundColor", "175 232 28");
		lemmonProps.setProperty("rolloverColor", "243 254 180");
		lemmonProps.setProperty("rolloverColorLight", "243 254 180");
		lemmonProps.setProperty("rolloverColorDark", "231 253 104");
		lemmonProps.setProperty("windowTitleBackgroundColor", "236 253 138");
		lemmonProps.setProperty("windowTitleColorLight", "236 253 138");
		lemmonProps.setProperty("windowTitleColorDark", "170 229 23");
		lemmonProps.setProperty("windowInactiveTitleBackgroundColor", "240 240 240");
		lemmonProps.setProperty("windowInactiveTitleColorLight", "240 240 240");
		lemmonProps.setProperty("windowInactiveTitleColorDark", "226 226 226");
		lemmonProps.setProperty("controlBackgroundColor", "227 231 230");
		lemmonProps.setProperty("controlColorLight", "236 253 138");
		lemmonProps.setProperty("controlColorDark", "170 229 23");
		lemmonProps.setProperty("pressedBackgroundColorLight", "232 232 232");
		lemmonProps.setProperty("pressedBackgroundColorDark", "220 220 220");
		lemmonProps.setProperty("buttonColorLight", "236 253 138");
		lemmonProps.setProperty("buttonColorDark", "170 229 23");
		lemmonProps.setProperty("menuBackgroundColor", "227 231 230");
		lemmonProps.setProperty("menuSelectionBackgroundColor", "175 232 28");
		lemmonProps.setProperty("menuBackgroundColorLight", "238 240 240");
		lemmonProps.setProperty("menuColorLight", "238 240 240");
		lemmonProps.setProperty("menuColorDark", "224 228 227");
		lemmonProps.setProperty("toolbarBackgroundColor", "238 240 240");
		lemmonProps.setProperty("toolbarColorLight", "238 240 240");
		lemmonProps.setProperty("toolbarColorDark", "224 228 227");
		lemmonProps.setProperty("desktopColor", "227 231 230");

		grayProps.setProperty("backgroundColor", "244 244 244");
		grayProps.setProperty("backgroundColorLight", "255 255 255");
		grayProps.setProperty("backgroundColorDark", "230 230 230");
		grayProps.setProperty("alterBackgroundColor", "230 230 230");
		grayProps.setProperty("selectionForegroundColor", "255 255 255");
		grayProps.setProperty("selectionBackgroundColor", "128 128 128");
		grayProps.setProperty("controlBackgroundColor", "244 244 244");
		grayProps.setProperty("controlColorLight", "192 192 192");
		grayProps.setProperty("controlColorDark", "128 128 128");
		grayProps.setProperty("pressedBackgroundColorLight", "236 236 236");
		grayProps.setProperty("pressedBackgroundColorDark", "224 224 224");
		grayProps.setProperty("buttonColorLight", "255 255 255");
		grayProps.setProperty("buttonColorDark", "232 232 232");
		grayProps.setProperty("rolloverColor", "212 212 212");
		grayProps.setProperty("rolloverColorLight", "232 232 232");
		grayProps.setProperty("rolloverColorDark", "200 200 200");
		grayProps.setProperty("windowTitleForegroundColor", "255 255 255");
		grayProps.setProperty("windowTitleBackgroundColor", "64 64 64");
		grayProps.setProperty("windowTitleColorLight", "164 164 164");
		grayProps.setProperty("windowTitleColorDark", "96 96 96");
		grayProps.setProperty("windowInactiveForegroundColor", "0 0 0");
		grayProps.setProperty("windowInactiveBackgroundColor", "240 240 240");
		grayProps.setProperty("windowInactiveTitleColorLight", "244 244 244");
		grayProps.setProperty("windowInactiveTitleColorDark", "212 212 212");
		grayProps.setProperty("menuBackgroundColor", "255 255 255");
		grayProps.setProperty("menuColorLight", "252 252 252");
		grayProps.setProperty("menuColorDark", "232 232 232");
		grayProps.setProperty("menuSelectionForegroundColor", "255 255 255");
		grayProps.setProperty("menuSelectionBackgroundColor", "128 128 128");
		grayProps.setProperty("toolbarBackgroundColor", "248 248 248");
		grayProps.setProperty("toolbarColorLight", "252 252 252");
		grayProps.setProperty("toolbarColorDark", "232 232 232");
		grayProps.setProperty("desktopColor", "240 240 240");

		String key;
		String value;
		Iterator<Object> iter = smallFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = smallFontProps.getProperty(key);
			goldSmallFontProps.setProperty(key, value);
			greenSmallFontProps.setProperty(key, value);
			brownSmallFontProps.setProperty(key, value);
			lemmonSmallFontProps.setProperty(key, value);
			graySmallFontProps.setProperty(key, value);
		}
		iter = largeFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = largeFontProps.getProperty(key);
			goldLargeFontProps.setProperty(key, value);
			greenLargeFontProps.setProperty(key, value);
			brownLargeFontProps.setProperty(key, value);
			lemmonLargeFontProps.setProperty(key, value);
			grayLargeFontProps.setProperty(key, value);
		}
		iter = giantFontProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = giantFontProps.getProperty(key);
			goldGiantFontProps.setProperty(key, value);
			greenGiantFontProps.setProperty(key, value);
			brownGiantFontProps.setProperty(key, value);
			lemmonGiantFontProps.setProperty(key, value);
			grayGiantFontProps.setProperty(key, value);
		}

		iter = goldProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = goldProps.getProperty(key);
			goldSmallFontProps.setProperty(key, value);
			goldLargeFontProps.setProperty(key, value);
			goldGiantFontProps.setProperty(key, value);
		}
		iter = greenProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = greenProps.getProperty(key);
			greenSmallFontProps.setProperty(key, value);
			greenLargeFontProps.setProperty(key, value);
			greenGiantFontProps.setProperty(key, value);
		}
		iter = brownProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = brownProps.getProperty(key);
			brownSmallFontProps.setProperty(key, value);
			brownLargeFontProps.setProperty(key, value);
			brownGiantFontProps.setProperty(key, value);
		}
		iter = lemmonProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = lemmonProps.getProperty(key);
			lemmonSmallFontProps.setProperty(key, value);
			lemmonLargeFontProps.setProperty(key, value);
			lemmonGiantFontProps.setProperty(key, value);
		}
		iter = grayProps.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			value = grayProps.getProperty(key);
			graySmallFontProps.setProperty(key, value);
			grayLargeFontProps.setProperty(key, value);
			grayGiantFontProps.setProperty(key, value);
		}

		themesList.add("Default");
		themesList.add("Small-Font");
		themesList.add("Large-Font");
		themesList.add("Giant-Font");

		themesList.add("Gold");
		themesList.add("Gold-Small-Font");
		themesList.add("Gold-Large-Font");
		themesList.add("Gold-Giant-Font");

		themesList.add("Green");
		themesList.add("Green-Small-Font");
		themesList.add("Green-Large-Font");
		themesList.add("Green-Giant-Font");

		themesList.add("Brown");
		themesList.add("Brown-Small-Font");
		themesList.add("Brown-Large-Font");
		themesList.add("Brown-Giant-Font");

		themesList.add("Lemmon");
		themesList.add("Lemmon-Small-Font");
		themesList.add("Lemmon-Large-Font");
		themesList.add("Lemmon-Giant-Font");

		themesList.add("Gray");
		themesList.add("Gray-Small-Font");
		themesList.add("Gray-Large-Font");
		themesList.add("Gray-Giant-Font");

		themesMap.put("Default", defaultProps);
		themesMap.put("Small-Font", smallFontProps);
		themesMap.put("Large-Font", largeFontProps);
		themesMap.put("Giant-Font", giantFontProps);

		themesMap.put("Gold", goldProps);
		themesMap.put("Gold-Small-Font", goldSmallFontProps);
		themesMap.put("Gold-Large-Font", goldLargeFontProps);
		themesMap.put("Gold-Giant-Font", goldGiantFontProps);

		themesMap.put("Green", greenProps);
		themesMap.put("Green-Small-Font", greenSmallFontProps);
		themesMap.put("Green-Large-Font", greenLargeFontProps);
		themesMap.put("Green-Giant-Font", greenGiantFontProps);

		themesMap.put("Brown", brownProps);
		themesMap.put("Brown-Small-Font", brownSmallFontProps);
		themesMap.put("Brown-Large-Font", brownLargeFontProps);
		themesMap.put("Brown-Giant-Font", brownGiantFontProps);

		themesMap.put("Lemmon", lemmonProps);
		themesMap.put("Lemmon-Small-Font", lemmonSmallFontProps);
		themesMap.put("Lemmon-Large-Font", lemmonLargeFontProps);
		themesMap.put("Lemmon-Giant-Font", lemmonGiantFontProps);

		themesMap.put("Gray", grayProps);
		themesMap.put("Gray-Small-Font", graySmallFontProps);
		themesMap.put("Gray-Large-Font", grayLargeFontProps);
		themesMap.put("Gray-Giant-Font", grayGiantFontProps);
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
		currentThemeName = "smartTheme";
		if (myTheme == null) {
			myTheme = new SmartDefaultTheme();
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
			myTheme = new SmartDefaultTheme();
		}
		setTheme(myTheme);
	}

	/** {@inheritDoc} */
	@Override
	public BorderFactory getBorderFactory() {
		return SmartBorderFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return "The Smart Look and Feel";
	}

	/** {@inheritDoc} */
	@Override
	public IconFactory getIconFactory() {
		return SmartIconFactory.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public String getID() {
		return "Smart";
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return "Smart";
	}

	/** {@inheritDoc} */
	@Override
	protected void initClassDefaults(final UIDefaults table) {
		if (!"smartTheme".equals(currentThemeName)) {
			setTheme("Default");
		}
		super.initClassDefaults(table);
		final Object[] uiDefaults = {
				// BaseLookAndFeel classes
				"LabelUI", BaseLabelUI.class.getName(), "SeparatorUI", BaseSeparatorUI.class.getName(), "TextFieldUI",
				BaseTextFieldUI.class.getName(), "TextAreaUI", BaseTextAreaUI.class.getName(), "EditorPaneUI",
				BaseEditorPaneUI.class.getName(), "PasswordFieldUI", BasePasswordFieldUI.class.getName(), "CheckBoxUI",
				BaseCheckBoxUI.class.getName(), "RadioButtonUI", BaseRadioButtonUI.class.getName(), "SplitPaneUI",
				BaseSplitPaneUI.class.getName(), "ToolTipUI", BaseToolTipUI.class.getName(), "TreeUI",
				BaseTreeUI.class.getName(), "TableUI", BaseTableUI.class.getName(), "SliderUI",
				BaseSliderUI.class.getName(), "ProgressBarUI", BaseProgressBarUI.class.getName(), "ScrollPaneUI",
				BaseScrollPaneUI.class.getName(), "PanelUI", BasePanelUI.class.getName(), "FileChooserUI",
				BaseFileChooserUI.class.getName(), "MenuBarUI", BaseMenuBarUI.class.getName(), "MenuUI",
				BaseMenuUI.class.getName(), "PopupMenuUI", BasePopupMenuUI.class.getName(), "MenuItemUI",
				BaseMenuItemUI.class.getName(), "CheckBoxMenuItemUI", BaseCheckBoxMenuItemUI.class.getName(),
				"RadioButtonMenuItemUI", BaseRadioButtonMenuItemUI.class.getName(), "PopupMenuSeparatorUI",
				BaseSeparatorUI.class.getName(), "DesktopPaneUI", BaseDesktopPaneUI.class.getName(),
				// SmartLookAndFeel classes
				"ButtonUI", SmartButtonUI.class.getName(), "ToggleButtonUI", SmartToggleButtonUI.class.getName(),
				"ComboBoxUI", SmartComboBoxUI.class.getName(), "ScrollBarUI", SmartScrollBarUI.class.getName(),
				"TabbedPaneUI", SmartTabbedPaneUI.class.getName(), "TableHeaderUI", SmartTableHeaderUI.class.getName(),
				"ToolBarUI", SmartToolBarUI.class.getName(), "InternalFrameUI", SmartInternalFrameUI.class.getName(),
				"RootPaneUI", SmartRootPaneUI.class.getName(), };
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

} // end of class SmartLookAndFeel
