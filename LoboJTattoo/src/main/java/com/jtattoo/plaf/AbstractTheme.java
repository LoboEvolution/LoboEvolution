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

package com.jtattoo.plaf;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalTheme;

/**
 * <p>Abstract AbstractTheme class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract class AbstractTheme extends MetalTheme {

	private static final Logger logger = Logger.getLogger(AbstractTheme.class.getName());
	
	/** Constant TEXT_ANTIALIAS_DEFAULT=0 */
	public static final int TEXT_ANTIALIAS_DEFAULT = 0;
	/** Constant TEXT_ANTIALIAS_GRAY=1 */
	public static final int TEXT_ANTIALIAS_GRAY = 1;
	/** Constant TEXT_ANTIALIAS_HRGB=2 */
	public static final int TEXT_ANTIALIAS_HRGB = 2;
	/** Constant TEXT_ANTIALIAS_HBGR=3 */
	public static final int TEXT_ANTIALIAS_HBGR = 3;
	/** Constant TEXT_ANTIALIAS_VRGB=4 */
	public static final int TEXT_ANTIALIAS_VRGB = 4;
	/** Constant TEXT_ANTIALIAS_VBGR=5 */
	public static final int TEXT_ANTIALIAS_VBGR = 5;
	/** Constant DIALOG="Dialog" */
	public static final String DIALOG = "Dialog";

	/** Constant RED */
	public static final ColorUIResource RED = new ColorUIResource(255, 0, 0);
	/** Constant GREEN */
	public static final ColorUIResource GREEN = new ColorUIResource(0, 255, 255);
	/** Constant CYAN */
	public static final ColorUIResource CYAN = new ColorUIResource(0, 255, 255);
	/** Constant WHITE */
	public static final ColorUIResource WHITE = new ColorUIResource(255, 255, 255);
	/** Constant SUPER_LIGHT_GRAY */
	public static final ColorUIResource SUPER_LIGHT_GRAY = new ColorUIResource(248, 248, 248);
	/** Constant EXTRA_LIGHT_GRAY */
	public static final ColorUIResource EXTRA_LIGHT_GRAY = new ColorUIResource(232, 232, 232);
	/** Constant LIGHT_GRAY */
	public static final ColorUIResource LIGHT_GRAY = new ColorUIResource(196, 196, 196);
	/** Constant GRAY */
	public static final ColorUIResource GRAY = new ColorUIResource(164, 164, 164);
	/** Constant DARK_GRAY */
	public static final ColorUIResource DARK_GRAY = new ColorUIResource(148, 148, 148);
	/** Constant EXTRA_DARK_GRAY */
	public static final ColorUIResource EXTRA_DARK_GRAY = new ColorUIResource(96, 96, 96);
	/** Constant BLACK */
	public static final ColorUIResource BLACK = new ColorUIResource(0, 0, 0);
	/** Constant ORANGE */
	public static final ColorUIResource ORANGE = new ColorUIResource(255, 200, 0);
	/** Constant LIGHT_ORANGE */
	public static final ColorUIResource LIGHT_ORANGE = new ColorUIResource(255, 220, 96);
	/** Constant YELLOW */
	public static final ColorUIResource YELLOW = new ColorUIResource(255, 255, 196);
	/** Constant BLUE */
	public static final ColorUIResource BLUE = new ColorUIResource(0, 128, 255);
	/** Constant DARK_BLUE */
	public static final ColorUIResource DARK_BLUE = new ColorUIResource(0, 64, 128);

	/** Constant internalName="Default" */
	protected static String internalName = "Default";
	/** Constant windowDecoration=false */
	protected static boolean windowDecoration = false;
	/** Constant macStyleWindowDecoration=false */
	protected static boolean macStyleWindowDecoration = false;
	/** Constant centerWindowTitle=false */
	protected static boolean centerWindowTitle = false;
	/** Constant linuxStyleScrollBar=false */
	protected static boolean linuxStyleScrollBar = false;
	/** Constant macStyleScrollBar=false */
	protected static boolean macStyleScrollBar = false;
	/** Constant dynamicLayout=false */
	protected static boolean dynamicLayout = false;
	/** Constant textShadow=false */
	protected static boolean textShadow = false;
	/** Constant textAntiAliasing=true */
	protected static boolean textAntiAliasing = true;
	/** Constant textAntiAliasingMode=TEXT_ANTIALIAS_HRGB */
	protected static int textAntiAliasingMode = TEXT_ANTIALIAS_HRGB;
	/** Constant backgroundPattern=true */
	protected static boolean backgroundPattern = true;
	/** Constant brightMode=false */
	protected static boolean brightMode = false;
	/** Constant showFocusFrame=false */
	protected static boolean showFocusFrame = false;
	/** Constant drawSquareButtons=false */
	protected static boolean drawSquareButtons = false;
	/** Constant toolbarDecorated=true */
	protected static boolean toolbarDecorated = true;

	/** Constant menuOpaque=true */
	protected static boolean menuOpaque = true;
	/** Constant menuAlpha=0.9f */
	protected static float menuAlpha = 0.9f;
	/** Constant logoString="JTattoo" */
	protected static String logoString = "JTattoo";
	/** Constant controlFont */
	protected static FontUIResource controlFont = null;
	/** Constant systemFont */
	protected static FontUIResource systemFont = null;
	/** Constant userFont */
	protected static FontUIResource userFont = null;
	/** Constant smallFont */
	protected static FontUIResource smallFont = null;
	/** Constant menuFont */
	protected static FontUIResource menuFont = null;
	/** Constant windowTitleFont */
	protected static FontUIResource windowTitleFont = null;
	/** Constant foregroundColor */
	protected static ColorUIResource foregroundColor = null;
	/** Constant backgroundColor */
	protected static ColorUIResource backgroundColor = null;
	/** Constant backgroundColorLight */
	protected static ColorUIResource backgroundColorLight = null;
	/** Constant backgroundColorDark */
	protected static ColorUIResource backgroundColorDark = null;
	/** Constant alterBackgroundColor */
	protected static ColorUIResource alterBackgroundColor = null;
	/** Constant disabledForegroundColor */
	protected static ColorUIResource disabledForegroundColor = null;
	/** Constant disabledBackgroundColor */
	protected static ColorUIResource disabledBackgroundColor = null;
	/** Constant inputBackgroundColor */
	protected static ColorUIResource inputBackgroundColor = null;
	/** Constant inputForegroundColor */
	protected static ColorUIResource inputForegroundColor = null;
	/** Constant selectionForegroundColor */
	protected static ColorUIResource selectionForegroundColor = null;
	/** Constant selectionBackgroundColorLight */
	protected static ColorUIResource selectionBackgroundColorLight = null;
	/** Constant selectionBackgroundColorDark */
	protected static ColorUIResource selectionBackgroundColorDark = null;
	/** Constant selectionBackgroundColor */
	protected static ColorUIResource selectionBackgroundColor = null;
	/** Constant rolloverForegroundColor */
	protected static ColorUIResource rolloverForegroundColor = null;
	/** Constant rolloverColor */
	protected static ColorUIResource rolloverColor = null;
	/** Constant rolloverColorLight */
	protected static ColorUIResource rolloverColorLight = null;
	/** Constant rolloverColorDark */
	protected static ColorUIResource rolloverColorDark = null;
	/** Constant pressedForegroundColor */
	protected static ColorUIResource pressedForegroundColor = null;
	/** Constant pressedBackgroundColor */
	protected static ColorUIResource pressedBackgroundColor = null;
	/** Constant pressedBackgroundColorLight */
	protected static ColorUIResource pressedBackgroundColorLight = null;
	/** Constant pressedBackgroundColorDark */
	protected static ColorUIResource pressedBackgroundColorDark = null;
	/** Constant focusColor */
	protected static ColorUIResource focusColor = null;
	/** Constant focusCellColor */
	protected static ColorUIResource focusCellColor = null;
	/** Constant focusFrameColor */
	protected static ColorUIResource focusFrameColor = null;
	/** Constant focusBackgroundColor */
	protected static ColorUIResource focusBackgroundColor = null;
	/** Constant focusForegroundColor */
	protected static ColorUIResource focusForegroundColor = null;
	/** Constant frameColor */
	protected static ColorUIResource frameColor = null;
	/** Constant gridColor */
	protected static ColorUIResource gridColor = null;
	/** Constant shadowColor */
	protected static ColorUIResource shadowColor = null;
	/** Constant buttonForegroundColor */
	protected static ColorUIResource buttonForegroundColor = null;
	/** Constant buttonBackgroundColor */
	protected static ColorUIResource buttonBackgroundColor = null;
	/** Constant buttonColorLight */
	protected static ColorUIResource buttonColorLight = null;
	/** Constant buttonColorDark */
	protected static ColorUIResource buttonColorDark = null;
	/** Constant controlForegroundColor */
	protected static ColorUIResource controlForegroundColor = null;
	/** Constant controlBackgroundColor */
	protected static ColorUIResource controlBackgroundColor = null;
	/** Constant controlHighlightColor */
	protected static ColorUIResource controlHighlightColor = null;
	/** Constant controlShadowColor */
	protected static ColorUIResource controlShadowColor = null;
	/** Constant controlDarkShadowColor */
	protected static ColorUIResource controlDarkShadowColor = null;
	/** Constant controlColorLight */
	protected static ColorUIResource controlColorLight = null;
	/** Constant controlColorDark */
	protected static ColorUIResource controlColorDark = null;
	/** Constant windowTitleForegroundColor */
	protected static ColorUIResource windowTitleForegroundColor = null;
	/** Constant windowTitleBackgroundColor */
	protected static ColorUIResource windowTitleBackgroundColor = null;
	/** Constant windowTitleColorLight */
	protected static ColorUIResource windowTitleColorLight = null;
	/** Constant windowTitleColorDark */
	protected static ColorUIResource windowTitleColorDark = null;
	/** Constant windowBorderColor */
	protected static ColorUIResource windowBorderColor = null;
	/** Constant windowIconColor */
	protected static ColorUIResource windowIconColor = null;
	/** Constant windowIconShadowColor */
	protected static ColorUIResource windowIconShadowColor = null;
	/** Constant windowIconRolloverColor */
	protected static ColorUIResource windowIconRolloverColor = null;

	/** Constant windowInactiveTitleForegroundColor */
	protected static ColorUIResource windowInactiveTitleForegroundColor = null;
	/** Constant windowInactiveTitleBackgroundColor */
	protected static ColorUIResource windowInactiveTitleBackgroundColor = null;
	/** Constant windowInactiveTitleColorLight */
	protected static ColorUIResource windowInactiveTitleColorLight = null;
	/** Constant windowInactiveTitleColorDark */
	protected static ColorUIResource windowInactiveTitleColorDark = null;
	/** Constant windowInactiveBorderColor */
	protected static ColorUIResource windowInactiveBorderColor = null;
	/** Constant menuForegroundColor */
	protected static ColorUIResource menuForegroundColor = null;
	/** Constant menuBackgroundColor */
	protected static ColorUIResource menuBackgroundColor = null;
	/** Constant menuSelectionForegroundColor */
	protected static ColorUIResource menuSelectionForegroundColor = null;
	/** Constant menuSelectionBackgroundColor */
	protected static ColorUIResource menuSelectionBackgroundColor = null;
	/** Constant menuSelectionBackgroundColorLight */
	protected static ColorUIResource menuSelectionBackgroundColorLight = null;
	/** Constant menuSelectionBackgroundColorDark */
	protected static ColorUIResource menuSelectionBackgroundColorDark = null;
	/** Constant menuColorLight */
	protected static ColorUIResource menuColorLight = null;
	/** Constant menuColorDark */
	protected static ColorUIResource menuColorDark = null;
	/** Constant toolbarForegroundColor */
	protected static ColorUIResource toolbarForegroundColor = null;
	/** Constant toolbarBackgroundColor */
	protected static ColorUIResource toolbarBackgroundColor = null;
	/** Constant toolbarColorLight */
	protected static ColorUIResource toolbarColorLight = null;
	/** Constant toolbarColorDark */
	protected static ColorUIResource toolbarColorDark = null;
	/** Constant tabAreaBackgroundColor */
	protected static ColorUIResource tabAreaBackgroundColor = null;
	/** Constant tabSelectionForegroundColor */
	protected static ColorUIResource tabSelectionForegroundColor = null;
	/** Constant desktopColor */
	protected static ColorUIResource desktopColor = null;
	/** Constant tooltipForegroundColor */
	protected static ColorUIResource tooltipForegroundColor = null;
	/** Constant tooltipBackgroundColor */
	protected static ColorUIResource tooltipBackgroundColor = null;
	/** Constant tooltipBorderSize=6 */
	protected static int tooltipBorderSize = 6;
	/** Constant tooltipShadowSize=6 */
	protected static int tooltipShadowSize = 6;
	/** Constant tooltipCastShadow=false */
	protected static boolean tooltipCastShadow = false;

	/** Constant DEFAULT_COLORS */
	protected static Color[] DEFAULT_COLORS = null;
	/** Constant HIDEFAULT_COLORS */
	protected static Color[] HIDEFAULT_COLORS = null;
	/** Constant ACTIVE_COLORS */
	protected static Color[] ACTIVE_COLORS = null;
	/** Constant INACTIVE_COLORS */
	protected static Color[] INACTIVE_COLORS = null;
	/** Constant ROLLOVER_COLORS */
	protected static Color[] ROLLOVER_COLORS = null;
	/** Constant SELECTED_COLORS */
	protected static Color[] SELECTED_COLORS = null;
	/** Constant SELECTION_COLORS */
	protected static Color[] SELECTION_COLORS = null;
	/** Constant FOCUS_COLORS */
	protected static Color[] FOCUS_COLORS = null;
	/** Constant MENU_SELECTION_COLORS */
	protected static Color[] MENU_SELECTION_COLORS = null;
	/** Constant PRESSED_COLORS */
	protected static Color[] PRESSED_COLORS = null;
	/** Constant DISABLED_COLORS */
	protected static Color[] DISABLED_COLORS = null;
	/** Constant WINDOW_TITLE_COLORS */
	protected static Color[] WINDOW_TITLE_COLORS = null;
	/** Constant WINDOW_INACTIVE_TITLE_COLORS */
	protected static Color[] WINDOW_INACTIVE_TITLE_COLORS = null;
	/** Constant TOOLBAR_COLORS */
	protected static Color[] TOOLBAR_COLORS = null;
	/** Constant MENUBAR_COLORS */
	protected static Color[] MENUBAR_COLORS = null;
	/** Constant BUTTON_COLORS */
	protected static Color[] BUTTON_COLORS = null;
	/** Constant CHECKBOX_COLORS */
	protected static Color[] CHECKBOX_COLORS = null;
	/** Constant TAB_COLORS */
	protected static Color[] TAB_COLORS = null;
	/** Constant COL_HEADER_COLORS */
	protected static Color[] COL_HEADER_COLORS = null;
	/** Constant TRACK_COLORS */
	protected static Color[] TRACK_COLORS = null;
	/** Constant THUMB_COLORS */
	protected static Color[] THUMB_COLORS = null;
	/** Constant SLIDER_COLORS */
	protected static Color[] SLIDER_COLORS = null;
	/** Constant PROGRESSBAR_COLORS */
	protected static Color[] PROGRESSBAR_COLORS = null;

	/** Constant textureSet="Default" */
	protected static String textureSet = "Default";
	/** Constant darkTexture=true */
	protected static boolean darkTexture = true;
	/** Constant windowTexture */
	protected static Icon windowTexture = null;
	/** Constant backgroundTexture */
	protected static Icon backgroundTexture = null;
	/** Constant alterBackgroundTexture */
	protected static Icon alterBackgroundTexture = null;
	/** Constant selectedTexture */
	protected static Icon selectedTexture = null;
	/** Constant rolloverTexture */
	protected static Icon rolloverTexture = null;
	/** Constant pressedTexture */
	protected static Icon pressedTexture = null;
	/** Constant disabledTexture */
	protected static Icon disabledTexture = null;
	/** Constant menubarTexture */
	protected static Icon menubarTexture = null;

	/**
	 * <p>createColor.</p>
	 *
	 * @param colorProp a {@link java.lang.String} object.
	 * @param color a {@link javax.swing.plaf.ColorUIResource} object.
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	protected static ColorUIResource createColor(String colorProp, ColorUIResource color) {
		if (colorProp != null && colorProp.trim().length() >= 5) {
			colorProp = colorProp.trim();
			int r = color.getRed();
			int g = color.getGreen();
			int b = color.getBlue();
			try {
				int p1 = 0;
				int p2 = colorProp.indexOf(' ');
				if (p2 > 0) {
					r = Integer.parseInt(colorProp.substring(p1, p2));
					p1 = p2 + 1;
					p2 = colorProp.indexOf(' ', p1);
					if (p2 > 0) {
						g = Integer.parseInt(colorProp.substring(p1, p2));
						b = Integer.parseInt(colorProp.substring(p2 + 1));
					}
				}
				return new ColorUIResource(r, g, b);
			} catch (NumberFormatException ex) {
				logger.severe("Exception while parsing color property: " + colorProp);
			}
		}
		return color;
	}

	/**
	 * <p>createFont.</p>
	 *
	 * @param fontProp a {@link java.lang.String} object.
	 * @return a {@link javax.swing.plaf.FontUIResource} object.
	 */
	protected static FontUIResource createFont(String fontProp) {
		if (fontProp != null && fontProp.trim().length() > 5) {
			return new FontUIResource(Font.decode(fontProp));
		}
		return null;
	}

	/**
	 * <p>createInt.</p>
	 *
	 * @param intProp a {@link java.lang.String} object.
	 * @param defaultValue a int.
	 * @return a int.
	 */
	protected static int createInt(String intProp, int defaultValue) {
		int val = defaultValue;
		try {
			val = Integer.parseInt(intProp);
		} catch (NumberFormatException ex) {
			logger.severe("Exception while parsing int property: " + intProp);
		}
		return val;
	}

	/**
	 * <p>Getter for the field internalName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String getInternalName() {
		return internalName;
	}

	/**
	 * <p>Setter for the field internalName.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public static void setInternalName(String name) {
		internalName = name;
	}

	/**
	 * <p>Constructor for AbstractTheme.</p>
	 */
	public AbstractTheme() {
		super();
	}

	/**
	 * <p>doDrawSquareButtons.</p>
	 *
	 * @return a boolean.
	 */
	public boolean doDrawSquareButtons() {
		return drawSquareButtons;
	}

	/**
	 * <p>doShowFocusFrame.</p>
	 *
	 * @return a boolean.
	 */
	public boolean doShowFocusFrame() {
		return showFocusFrame;
	}

	/**
	 * <p>getActiveColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getActiveColors() {
		return ACTIVE_COLORS;
	}

	/**
	 * <p>Getter for the field alterBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getAlterBackgroundColor() {
		return alterBackgroundColor;
	}

	/**
	 * <p>Getter for the field alterBackgroundTexture.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public Icon getAlterBackgroundTexture() {
		return alterBackgroundTexture;
	}

	/**
	 * <p>Getter for the field backgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * <p>Getter for the field backgroundColorDark.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getBackgroundColorDark() {
		return backgroundColorDark;
	}

	/**
	 * <p>Getter for the field backgroundColorLight.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getBackgroundColorLight() {
		return backgroundColorLight;
	}

	/**
	 * <p>Getter for the field backgroundTexture.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public Icon getBackgroundTexture() {
		return backgroundTexture;
	}

	/**
	 * <p>Getter for the field buttonBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getButtonBackgroundColor() {
		return buttonBackgroundColor;
	}

	/**
	 * <p>Getter for the field buttonColorDark.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getButtonColorDark() {
		return buttonColorDark;
	}

	/**
	 * <p>Getter for the field buttonColorLight.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getButtonColorLight() {
		return buttonColorLight;
	}

	/**
	 * <p>getButtonColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getButtonColors() {
		return BUTTON_COLORS;
	}

	/**
	 * <p>Getter for the field buttonForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getButtonForegroundColor() {
		return buttonForegroundColor;
	}

	/**
	 * <p>getCheckBoxColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getCheckBoxColors() {
		return CHECKBOX_COLORS;
	}

	/**
	 * <p>getColHeaderColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getColHeaderColors() {
		return COL_HEADER_COLORS;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getControl() {
		return controlBackgroundColor;
	}

	/**
	 * <p>Getter for the field controlBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getControlBackgroundColor() {
		return controlBackgroundColor;
	}

	/**
	 * <p>Getter for the field controlColorDark.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getControlColorDark() {
		return controlColorDark;
	}

	/**
	 * <p>Getter for the field controlColorLight.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getControlColorLight() {
		return controlColorLight;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getControlDarkShadow() {
		return controlDarkShadowColor;
	}

	/**
	 * <p>Getter for the field controlDarkShadowColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getControlDarkShadowColor() {
		return controlDarkShadowColor;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getControlDisabled() {
		return controlShadowColor;
	}

	/**
	 * <p>Getter for the field controlForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getControlForegroundColor() {
		return controlForegroundColor;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getControlHighlight() {
		return controlHighlightColor;
	}

	/**
	 * <p>Getter for the field controlHighlightColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getControlHighlightColor() {
		return controlHighlightColor;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getControlInfo() {
		return controlForegroundColor;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getControlShadow() {
		return controlShadowColor;
	}

	/**
	 * <p>Getter for the field controlShadowColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getControlShadowColor() {
		return controlShadowColor;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getControlTextColor() {
		return controlForegroundColor;
	}

	/** {@inheritDoc} */
	@Override
	public FontUIResource getControlTextFont() {
		if (controlFont == null) {
			if (JTattooUtilities.isLinux() && JTattooUtilities.isHiresScreen()) {
				controlFont = new FontUIResource(DIALOG, Font.BOLD, 14);
			} else {
				controlFont = new FontUIResource(DIALOG, Font.PLAIN, 12);
			}
		}
		return controlFont;
	}

	/**
	 * <p>getDefaultColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getDefaultColors() {
		return DEFAULT_COLORS;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getDesktopColor() {
		return desktopColor;
	}

	/**
	 * <p>Getter for the field disabledBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getDisabledBackgroundColor() {
		return disabledBackgroundColor;
	}

	/**
	 * <p>getDisabledColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getDisabledColors() {
		return DISABLED_COLORS;
	}

	/**
	 * <p>Getter for the field disabledForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getDisabledForegroundColor() {
		return disabledForegroundColor;
	}

	/**
	 * <p>Getter for the field disabledTexture.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public Icon getDisabledTexture() {
		return disabledTexture;
	}

	/**
	 * <p>Getter for the field focusBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getFocusBackgroundColor() {
		return focusBackgroundColor;
	}

	/**
	 * <p>Getter for the field focusCellColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getFocusCellColor() {
		return focusCellColor;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getFocusColor() {
		return focusColor;
	}

	/**
	 * <p>getFocusColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getFocusColors() {
		return FOCUS_COLORS;
	}

	/**
	 * <p>Getter for the field focusForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getFocusForegroundColor() {
		return focusForegroundColor;
	}

	/**
	 * <p>Getter for the field focusFrameColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getFocusFrameColor() {
		return focusFrameColor;
	}

	/**
	 * <p>Getter for the field foregroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * <p>Getter for the field frameColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getFrameColor() {
		return frameColor;
	}

	/**
	 * <p>Getter for the field gridColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getGridColor() {
		return gridColor;
	}

	/**
	 * <p>getHiDefaultColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getHiDefaultColors() {
		return HIDEFAULT_COLORS;
	}

	/**
	 * <p>getInActiveColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getInActiveColors() {
		return INACTIVE_COLORS;
	}

	/**
	 * <p>Getter for the field inputBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getInputBackgroundColor() {
		return inputBackgroundColor;
	}

	/**
	 * <p>Getter for the field inputForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getInputForegroundColor() {
		return inputForegroundColor;
	}

	/**
	 * <p>Getter for the field logoString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLogoString() {
		if (logoString != null) {
			if (logoString.trim().length() == 0) {
				return null;
			}
		}
		return logoString;
	}

	/**
	 * <p>Getter for the field menuAlpha.</p>
	 *
	 * @return a float.
	 */
	public float getMenuAlpha() {
		return menuAlpha;
	}

	/**
	 * <p>Getter for the field menuBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getMenuBackgroundColor() {
		return menuBackgroundColor;
	}

	/**
	 * <p>getMenuBarColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getMenuBarColors() {
		return MENUBAR_COLORS;
	}

	/**
	 * <p>Getter for the field menubarTexture.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public Icon getMenubarTexture() {
		return menubarTexture;
	}

	/**
	 * <p>Getter for the field menuColorDark.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getMenuColorDark() {
		return menuColorDark;
	}

	/**
	 * <p>Getter for the field menuColorLight.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getMenuColorLight() {
		return menuColorLight;
	}

	/**
	 * <p>Getter for the field menuForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getMenuForegroundColor() {
		return menuForegroundColor;
	}

	/**
	 * <p>Getter for the field menuSelectionBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getMenuSelectionBackgroundColor() {
		return menuSelectionBackgroundColor;
	}

	/**
	 * <p>Getter for the field menuSelectionBackgroundColorDark.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getMenuSelectionBackgroundColorDark() {
		return menuSelectionBackgroundColorDark;
	}

	/**
	 * <p>Getter for the field menuSelectionBackgroundColorLight.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getMenuSelectionBackgroundColorLight() {
		return menuSelectionBackgroundColorLight;
	}

	/**
	 * <p>getMenuSelectionColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getMenuSelectionColors() {
		return MENU_SELECTION_COLORS;
	}

	/**
	 * <p>Getter for the field menuSelectionForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getMenuSelectionForegroundColor() {
		return menuSelectionForegroundColor;
	}

	/** {@inheritDoc} */
	@Override
	public FontUIResource getMenuTextFont() {
		if (menuFont == null) {
			if (JTattooUtilities.isLinux() && JTattooUtilities.isHiresScreen()) {
				menuFont = new FontUIResource(DIALOG, Font.BOLD, 14);
			} else {
				menuFont = new FontUIResource(DIALOG, Font.PLAIN, 12);
			}
		}
		return menuFont;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return getInternalName();
	}

	/**
	 * <p>Getter for the field pressedBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getPressedBackgroundColor() {
		return pressedBackgroundColor;
	}

	/**
	 * <p>getPressedColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getPressedColors() {
		return PRESSED_COLORS;
	}

	/**
	 * <p>Getter for the field pressedForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getPressedForegroundColor() {
		return pressedForegroundColor;
	}

	/**
	 * <p>Getter for the field pressedTexture.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public Icon getPressedTexture() {
		return pressedTexture;
	}

	// -----------------------------------------------------------------------------------
	/** {@inheritDoc} */
	@Override
	protected ColorUIResource getPrimary1() {
		return foregroundColor;
	}

	/** {@inheritDoc} */
	@Override
	protected ColorUIResource getPrimary2() {
		return desktopColor;
	}

	/** {@inheritDoc} */
	@Override
	protected ColorUIResource getPrimary3() {
		return selectionBackgroundColor;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getPrimaryControl() {
		return EXTRA_LIGHT_GRAY;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getPrimaryControlDarkShadow() {
		return GRAY;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getPrimaryControlHighlight() {
		return WHITE;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getPrimaryControlInfo() {
		return DARK_GRAY;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getPrimaryControlShadow() {
		return LIGHT_GRAY;
	}

	/**
	 * <p>getProgressBarColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getProgressBarColors() {
		return PROGRESSBAR_COLORS;
	}

	/**
	 * <p>getPropertyFileName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPropertyFileName() {
		return "JTattooTheme.properties";
	}

	/**
	 * <p>Getter for the field rolloverColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getRolloverColor() {
		return rolloverColor;
	}

	/**
	 * <p>Getter for the field rolloverColorDark.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getRolloverColorDark() {
		return rolloverColorDark;
	}

	/**
	 * <p>Getter for the field rolloverColorLight.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getRolloverColorLight() {
		return rolloverColorLight;
	}

	/**
	 * <p>getRolloverColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getRolloverColors() {
		return ROLLOVER_COLORS;
	}

	/**
	 * <p>Getter for the field rolloverForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getRolloverForegroundColor() {
		return rolloverForegroundColor;
	}

	/**
	 * <p>Getter for the field rolloverTexture.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public Icon getRolloverTexture() {
		return rolloverTexture;
	}

	/** {@inheritDoc} */
	@Override
	protected ColorUIResource getSecondary1() {
		return frameColor;
	}

	/** {@inheritDoc} */
	@Override
	protected ColorUIResource getSecondary2() {
		return controlBackgroundColor;
	}

	/** {@inheritDoc} */
	@Override
	protected ColorUIResource getSecondary3() {
		return backgroundColor;
	}

	/**
	 * <p>getSelectedColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getSelectedColors() {
		return SELECTED_COLORS;
	}

	/**
	 * <p>Getter for the field selectedTexture.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public Icon getSelectedTexture() {
		return selectedTexture;
	}

	/**
	 * <p>Getter for the field selectionBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getSelectionBackgroundColor() {
		return selectionBackgroundColor;
	}

	/**
	 * <p>Getter for the field selectionBackgroundColorDark.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getSelectionBackgroundColorDark() {
		return selectionBackgroundColorDark;
	}

	/**
	 * <p>Getter for the field selectionBackgroundColorLight.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getSelectionBackgroundColorLight() {
		return selectionBackgroundColorLight;
	}

	/**
	 * <p>getSelectionColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getSelectionColors() {
		return SELECTION_COLORS;
	}

	/**
	 * <p>Getter for the field selectionForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getSelectionForegroundColor() {
		return selectionForegroundColor;
	}

	/**
	 * <p>Getter for the field shadowColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getShadowColor() {
		return shadowColor;
	}

	/**
	 * <p>getSliderColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getSliderColors() {
		return SLIDER_COLORS;
	}

	/** {@inheritDoc} */
	@Override
	public FontUIResource getSubTextFont() {
		if (smallFont == null) {
			if (JTattooUtilities.isLinux() && JTattooUtilities.isHiresScreen()) {
				smallFont = new FontUIResource(DIALOG, Font.BOLD, 12);
			} else {
				smallFont = new FontUIResource(DIALOG, Font.PLAIN, 10);
			}
		}
		return smallFont;
	}

	/** {@inheritDoc} */
	@Override
	public ColorUIResource getSystemTextColor() {
		return foregroundColor;
	}

	/** {@inheritDoc} */
	@Override
	public FontUIResource getSystemTextFont() {
		if (systemFont == null) {
			if (JTattooUtilities.isLinux() && JTattooUtilities.isHiresScreen()) {
				systemFont = new FontUIResource(DIALOG, Font.BOLD, 14);
			} else {
				systemFont = new FontUIResource(DIALOG, Font.PLAIN, 12);
			}
		}
		return systemFont;
	}

	/**
	 * <p>Getter for the field tabAreaBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getTabAreaBackgroundColor() {
		return tabAreaBackgroundColor;
	}

	/**
	 * <p>getTabColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getTabColors() {
		return TAB_COLORS;
	}

	/**
	 * <p>Getter for the field tabSelectionForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getTabSelectionForegroundColor() {
		return tabSelectionForegroundColor;
	}

	/**
	 * <p>getTextAntiAliasingHint.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getTextAntiAliasingHint() {
		if (isTextAntiAliasingOn()) {
			switch (textAntiAliasingMode) {
			case TEXT_ANTIALIAS_DEFAULT:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT;
			case TEXT_ANTIALIAS_HRGB:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB;
			case TEXT_ANTIALIAS_HBGR:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR;
			case TEXT_ANTIALIAS_VRGB:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB;
			case TEXT_ANTIALIAS_VBGR:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR;
			default:
				return RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
			}
		}
		return RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
	}

	/**
	 * <p>Getter for the field textAntiAliasingMode.</p>
	 *
	 * @return a int.
	 */
	public int getTextAntiAliasingMode() {
		return textAntiAliasingMode;
	}

	/**
	 * <p>Getter for the field textureSet.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTextureSet() {
		return textureSet;
	}

	/**
	 * <p>getThumbColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getThumbColors() {
		return THUMB_COLORS;
	}

	/**
	 * <p>Getter for the field toolbarBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getToolbarBackgroundColor() {
		return toolbarBackgroundColor;
	}

	/**
	 * <p>Getter for the field toolbarColorDark.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getToolbarColorDark() {
		return toolbarColorDark;
	}

	/**
	 * <p>Getter for the field toolbarColorLight.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getToolbarColorLight() {
		return toolbarColorLight;
	}

	/**
	 * <p>getToolBarColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getToolBarColors() {
		return TOOLBAR_COLORS;
	}

	/**
	 * <p>Getter for the field toolbarForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getToolbarForegroundColor() {
		return toolbarForegroundColor;
	}

	/**
	 * <p>Getter for the field tooltipBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getTooltipBackgroundColor() {
		return tooltipBackgroundColor;
	}

	/**
	 * <p>Getter for the field tooltipBorderSize.</p>
	 *
	 * @return a int.
	 */
	public int getTooltipBorderSize() {
		return Math.max(0, Math.min(8, tooltipBorderSize));
	}

	/**
	 * <p>Getter for the field tooltipForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getTooltipForegroundColor() {
		return tooltipForegroundColor;
	}

	/**
	 * <p>Getter for the field tooltipShadowSize.</p>
	 *
	 * @return a int.
	 */
	public int getTooltipShadowSize() {
		return Math.max(0, Math.min(8, tooltipShadowSize));
	}

	/**
	 * <p>getTrackColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getTrackColors() {
		return TRACK_COLORS;
	}

	/** {@inheritDoc} */
	@Override
	public FontUIResource getUserTextFont() {
		if (userFont == null) {
			if (JTattooUtilities.isLinux() && JTattooUtilities.isHiresScreen()) {
				userFont = new FontUIResource(DIALOG, Font.BOLD, 14);
			} else {
				userFont = new FontUIResource(DIALOG, Font.PLAIN, 12);
			}
		}
		return userFont;
	}

	/**
	 * <p>Getter for the field windowBorderColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowBorderColor() {
		return windowBorderColor;
	}

	/**
	 * <p>Getter for the field windowIconColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowIconColor() {
		return windowIconColor;
	}

	/**
	 * <p>Getter for the field windowIconRolloverColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowIconRolloverColor() {
		return windowIconRolloverColor;
	}

	/**
	 * <p>Getter for the field windowIconShadowColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowIconShadowColor() {
		return windowIconShadowColor;
	}

	/**
	 * <p>Getter for the field windowInactiveBorderColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowInactiveBorderColor() {
		return windowInactiveBorderColor;
	}

	/**
	 * <p>Getter for the field windowInactiveTitleBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowInactiveTitleBackgroundColor() {
		return windowInactiveTitleBackgroundColor;
	}

	/**
	 * <p>Getter for the field windowInactiveTitleColorDark.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowInactiveTitleColorDark() {
		return windowInactiveTitleColorDark;
	}

	/**
	 * <p>Getter for the field windowInactiveTitleColorLight.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowInactiveTitleColorLight() {
		return windowInactiveTitleColorLight;
	}

	/**
	 * <p>getWindowInactiveTitleColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getWindowInactiveTitleColors() {
		return WINDOW_INACTIVE_TITLE_COLORS;
	}

	/**
	 * <p>Getter for the field windowInactiveTitleForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowInactiveTitleForegroundColor() {
		return windowInactiveTitleForegroundColor;
	}

	/**
	 * <p>Getter for the field windowTexture.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public Icon getWindowTexture() {
		return windowTexture;
	}

	/**
	 * <p>Getter for the field windowTitleBackgroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowTitleBackgroundColor() {
		return windowTitleBackgroundColor;
	}

	/**
	 * <p>Getter for the field windowTitleColorDark.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowTitleColorDark() {
		return windowTitleColorDark;
	}

	/**
	 * <p>Getter for the field windowTitleColorLight.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowTitleColorLight() {
		return windowTitleColorLight;
	}

	/**
	 * <p>getWindowTitleColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	public Color[] getWindowTitleColors() {
		return WINDOW_TITLE_COLORS;
	}

	/** {@inheritDoc} */
	@Override
	public FontUIResource getWindowTitleFont() {
		if (windowTitleFont == null) {
			if (JTattooUtilities.isLinux() && JTattooUtilities.isHiresScreen()) {
				windowTitleFont = new FontUIResource(DIALOG, Font.BOLD, 14);
			} else {
				windowTitleFont = new FontUIResource(DIALOG, Font.BOLD, 12);
			}
		}
		return windowTitleFont;
	}

	/**
	 * <p>Getter for the field windowTitleForegroundColor.</p>
	 *
	 * @return a {@link javax.swing.plaf.ColorUIResource} object.
	 */
	public ColorUIResource getWindowTitleForegroundColor() {
		return windowTitleForegroundColor;
	}

	/**
	 * <p>isBackgroundPatternOn.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isBackgroundPatternOn() {
		return backgroundPattern;
	}

	/**
	 * <p>isBrightMode.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isBrightMode() {
		return brightMode;
	}

	/**
	 * <p>isCenterWindowTitleOn.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isCenterWindowTitleOn() {
		return centerWindowTitle;
	}

	/**
	 * <p>isDarkTexture.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isDarkTexture() {
		return darkTexture;
	}

	/**
	 * <p>isDynamicLayout.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isDynamicLayout() {
		return dynamicLayout;
	}

	/**
	 * <p>isLargeFontSize.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isLargeFontSize() {
		return userFont.getSize() >= 16;
	}

	/**
	 * <p>isLinuxStyleScrollBarOn.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isLinuxStyleScrollBarOn() {
		return linuxStyleScrollBar;
	}

	/**
	 * <p>isMacStyleScrollBarOn.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isMacStyleScrollBarOn() {
		return macStyleScrollBar;
	}

	/**
	 * <p>isMacStyleWindowDecorationOn.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isMacStyleWindowDecorationOn() {
		return macStyleWindowDecoration;
	}

	/**
	 * <p>isMediumFontSize.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isMediumFontSize() {
		return userFont.getSize() >= 14 && userFont.getSize() < 16;
	}

	/**
	 * <p>isMenuOpaque.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isMenuOpaque() {
		return menuOpaque;
	}

	/**
	 * <p>isSmallFontSize.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isSmallFontSize() {
		return userFont.getSize() < 14;
	}

	/**
	 * <p>isTextAntiAliasingOn.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isTextAntiAliasingOn() {
		return textAntiAliasing;
	}

	/**
	 * <p>isTextShadowOn.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isTextShadowOn() {
		return textShadow;
	}

	/**
	 * <p>isTinyFontSize.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isTinyFontSize() {
		return userFont.getSize() < 12;
	}

	/**
	 * <p>isToolbarDecorated.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isToolbarDecorated() {
		return toolbarDecorated;
	}

	/**
	 * <p>isTooltipCastShadow.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isTooltipCastShadow() {
		return tooltipCastShadow;
	}

	/**
	 * <p>isWindowDecorationOn.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isWindowDecorationOn() {
		return windowDecoration;
	}

	/**
	 * <p>loadProperties.</p>
	 */
	public void loadProperties() {
		FileInputStream in = null;
		try {
			String fileName = System.getProperty("user.home") + "/.jtattoo/" + getPropertyFileName();
			Properties props = new Properties();
			in = new FileInputStream(fileName);
			props.load(in);
			setProperties(props);
		} catch (IOException ex) {
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * <p>setProperties.</p>
	 *
	 * @param props a {@link java.util.Properties} object.
	 */
	public void setProperties(Properties props) {
		if (props != null) {
			if (props.getProperty("windowDecoration") != null) {
				windowDecoration = props.getProperty("windowDecoration").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("macStyleWindowDecoration") != null) {
				macStyleWindowDecoration = props.getProperty("macStyleWindowDecoration").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("centerWindowTitle") != null) {
				centerWindowTitle = props.getProperty("centerWindowTitle").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("linuxStyleScrollBar") != null) {
				linuxStyleScrollBar = props.getProperty("linuxStyleScrollBar").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("macStyleScrollBar") != null) {
				macStyleScrollBar = props.getProperty("macStyleScrollBar").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("dynamicLayout") != null) {
				dynamicLayout = props.getProperty("dynamicLayout").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("textShadow") != null) {
				textShadow = props.getProperty("textShadow").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("textAntiAliasing") != null) {
				textAntiAliasing = props.getProperty("textAntiAliasing").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("textAntiAliasingMode") != null) {
				String mode = props.getProperty("textAntiAliasingMode");
				if (mode.equalsIgnoreCase("default")) {
					textAntiAliasingMode = TEXT_ANTIALIAS_DEFAULT;
				}
				if (mode.equalsIgnoreCase("gray")) {
					textAntiAliasingMode = TEXT_ANTIALIAS_GRAY;
				}
				if (mode.equalsIgnoreCase("hrgb")) {
					textAntiAliasingMode = TEXT_ANTIALIAS_HRGB;
				}
				if (mode.equalsIgnoreCase("hbgr")) {
					textAntiAliasingMode = TEXT_ANTIALIAS_HBGR;
				}
				if (mode.equalsIgnoreCase("vrgb")) {
					textAntiAliasingMode = TEXT_ANTIALIAS_VRGB;
				}
				if (mode.equalsIgnoreCase("vbgr")) {
					textAntiAliasingMode = TEXT_ANTIALIAS_VBGR;
				}
			}
			if (props.getProperty("backgroundPattern") != null) {
				backgroundPattern = props.getProperty("backgroundPattern").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("brightMode") != null) {
				brightMode = props.getProperty("brightMode").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("showFocusFrame") != null) {
				showFocusFrame = props.getProperty("showFocusFrame").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("drawSquareButtons") != null) {
				drawSquareButtons = props.getProperty("drawSquareButtons").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("toolbarDecorated") != null) {
				toolbarDecorated = props.getProperty("toolbarDecorated").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("menuOpaque") != null) {
				menuOpaque = props.getProperty("menuOpaque").trim().equalsIgnoreCase("on");
			}
			if (props.getProperty("logoString") != null) {
				logoString = props.getProperty("logoString").trim();
			}
			if (props.getProperty("controlTextFont") != null) {
				controlFont = createFont(props.getProperty("controlTextFont"));
			}
			if (props.getProperty("systemTextFont") != null) {
				systemFont = createFont(props.getProperty("systemTextFont"));
			}
			if (props.getProperty("userTextFont") != null) {
				userFont = createFont(props.getProperty("userTextFont"));
			}
			if (props.getProperty("menuTextFont") != null) {
				menuFont = createFont(props.getProperty("menuTextFont"));
			}
			if (props.getProperty("windowTitleFont") != null) {
				windowTitleFont = createFont(props.getProperty("windowTitleFont"));
			}
			if (props.getProperty("subTextFont") != null) {
				smallFont = createFont(props.getProperty("subTextFont"));
			}

			if (props.getProperty("foregroundColor") != null) {
				foregroundColor = createColor(props.getProperty("foregroundColor"), foregroundColor);
			}
			if (props.getProperty("backgroundColor") != null) {
				backgroundColor = createColor(props.getProperty("backgroundColor"), backgroundColor);
			}
			if (props.getProperty("backgroundColorLight") != null) {
				backgroundColorLight = createColor(props.getProperty("backgroundColorLight"), backgroundColorLight);
			}
			if (props.getProperty("backgroundColorDark") != null) {
				backgroundColorDark = createColor(props.getProperty("backgroundColorDark"), backgroundColorDark);
			}
			if (props.getProperty("alterBackgroundColor") != null) {
				alterBackgroundColor = createColor(props.getProperty("alterBackgroundColor"), alterBackgroundColor);
			}
			if (props.getProperty("disabledForegroundColor") != null) {
				disabledForegroundColor = createColor(props.getProperty("disabledForegroundColor"),
						disabledForegroundColor);
			}
			if (props.getProperty("disabledBackgroundColor") != null) {
				disabledBackgroundColor = createColor(props.getProperty("disabledBackgroundColor"),
						disabledBackgroundColor);
			}
			if (props.getProperty("inputForegroundColor") != null) {
				inputForegroundColor = createColor(props.getProperty("inputForegroundColor"), inputForegroundColor);
			}
			if (props.getProperty("inputBackgroundColor") != null) {
				inputBackgroundColor = createColor(props.getProperty("inputBackgroundColor"), inputBackgroundColor);
			}
			if (props.getProperty("selectionForegroundColor") != null) {
				selectionForegroundColor = createColor(props.getProperty("selectionForegroundColor"),
						selectionForegroundColor);
			}
			if (props.getProperty("selectionBackgroundColor") != null) {
				selectionBackgroundColor = createColor(props.getProperty("selectionBackgroundColor"),
						selectionBackgroundColor);
			}
			if (props.getProperty("selectionBackgroundColorLight") != null) {
				selectionBackgroundColorLight = createColor(props.getProperty("selectionBackgroundColorLight"),
						selectionBackgroundColorLight);
			}
			if (props.getProperty("selectionBackgroundColorDark") != null) {
				selectionBackgroundColorDark = createColor(props.getProperty("selectionBackgroundColorDark"),
						selectionBackgroundColorDark);
			}
			if (props.getProperty("frameColor") != null) {
				frameColor = createColor(props.getProperty("frameColor"), frameColor);
			}
			if (props.getProperty("gridColor") != null) {
				gridColor = createColor(props.getProperty("gridColor"), gridColor);
			}
			if (props.getProperty("shadowColor") != null) {
				shadowColor = createColor(props.getProperty("shadowColor"), shadowColor);
			}
			if (props.getProperty("focusColor") != null) {
				focusColor = createColor(props.getProperty("focusColor"), focusColor);
			}
			if (props.getProperty("focusCellColor") != null) {
				focusCellColor = createColor(props.getProperty("focusCellColor"), focusCellColor);
			}
			if (props.getProperty("focusFrameColor") != null) {
				focusFrameColor = createColor(props.getProperty("focusFrameColor"), focusFrameColor);
			}
			if (props.getProperty("focusBackgroundColor") != null) {
				focusBackgroundColor = createColor(props.getProperty("focusBackgroundColor"), focusBackgroundColor);
			}
			if (props.getProperty("focusForegroundColor") != null) {
				focusForegroundColor = createColor(props.getProperty("focusForegroundColor"), focusForegroundColor);
			}

			if (props.getProperty("rolloverForegroundColor") != null) {
				rolloverForegroundColor = createColor(props.getProperty("rolloverForegroundColor"),
						rolloverForegroundColor);
			}
			if (props.getProperty("rolloverColor") != null) {
				rolloverColor = createColor(props.getProperty("rolloverColor"), rolloverColor);
			}
			if (props.getProperty("rolloverColorLight") != null) {
				rolloverColorLight = createColor(props.getProperty("rolloverColorLight"), rolloverColorLight);
			}
			if (props.getProperty("rolloverColorDark") != null) {
				rolloverColorDark = createColor(props.getProperty("rolloverColorDark"), rolloverColorDark);
			}
			if (props.getProperty("pressedForegroundColor") != null) {
				pressedForegroundColor = createColor(props.getProperty("pressedForegroundColor"),
						pressedForegroundColor);
			}
			if (props.getProperty("pressedBackgroundColor") != null) {
				pressedBackgroundColor = createColor(props.getProperty("pressedBackgroundColor"),
						pressedBackgroundColor);
			}
			if (props.getProperty("pressedBackgroundColorLight") != null) {
				pressedBackgroundColorLight = createColor(props.getProperty("pressedBackgroundColorLight"),
						pressedBackgroundColorDark);
			}
			if (props.getProperty("pressedBackgroundColorDark") != null) {
				pressedBackgroundColorDark = createColor(props.getProperty("pressedBackgroundColorDark"),
						pressedBackgroundColorDark);
			}

			if (props.getProperty("buttonForegroundColor") != null) {
				buttonForegroundColor = createColor(props.getProperty("buttonForegroundColor"), buttonForegroundColor);
			}
			if (props.getProperty("buttonBackgroundColor") != null) {
				buttonBackgroundColor = createColor(props.getProperty("buttonBackgroundColor"), buttonBackgroundColor);
			}
			if (props.getProperty("buttonColorLight") != null) {
				buttonColorLight = createColor(props.getProperty("buttonColorLight"), buttonColorLight);
			}
			if (props.getProperty("buttonColorDark") != null) {
				buttonColorDark = createColor(props.getProperty("buttonColorDark"), buttonColorDark);
			}

			if (props.getProperty("controlForegroundColor") != null) {
				controlForegroundColor = createColor(props.getProperty("controlForegroundColor"),
						controlForegroundColor);
			}
			if (props.getProperty("controlBackgroundColor") != null) {
				controlBackgroundColor = createColor(props.getProperty("controlBackgroundColor"),
						controlBackgroundColor);
			}
			if (props.getProperty("controlColorLight") != null) {
				controlColorLight = createColor(props.getProperty("controlColorLight"), controlColorLight);
			}
			if (props.getProperty("controlColorDark") != null) {
				controlColorDark = createColor(props.getProperty("controlColorDark"), controlColorDark);
			}
			if (props.getProperty("controlHighlightColor") != null) {
				controlHighlightColor = createColor(props.getProperty("controlHighlightColor"), controlHighlightColor);
			}
			if (props.getProperty("controlShadowColor") != null) {
				controlShadowColor = createColor(props.getProperty("controlShadowColor"), controlShadowColor);
			}
			if (props.getProperty("controlDarkShadowColor") != null) {
				controlDarkShadowColor = createColor(props.getProperty("controlDarkShadowColor"),
						controlDarkShadowColor);
			}

			if (props.getProperty("windowTitleForegroundColor") != null) {
				windowTitleForegroundColor = createColor(props.getProperty("windowTitleForegroundColor"),
						windowTitleForegroundColor);
			}
			if (props.getProperty("windowTitleBackgroundColor") != null) {
				windowTitleBackgroundColor = createColor(props.getProperty("windowTitleBackgroundColor"),
						windowTitleBackgroundColor);
			}
			if (props.getProperty("windowTitleColorLight") != null) {
				windowTitleColorLight = createColor(props.getProperty("windowTitleColorLight"), windowTitleColorLight);
			}
			if (props.getProperty("windowTitleColorDark") != null) {
				windowTitleColorDark = createColor(props.getProperty("windowTitleColorDark"), windowTitleColorDark);
			}
			if (props.getProperty("windowBorderColor") != null) {
				windowBorderColor = createColor(props.getProperty("windowBorderColor"), windowBorderColor);
			}
			if (props.getProperty("windowIconColor") != null) {
				windowIconColor = createColor(props.getProperty("windowIconColor"), windowIconColor);
			}
			if (props.getProperty("windowIconShadowColor") != null) {
				windowIconShadowColor = createColor(props.getProperty("windowIconShadowColor"), windowIconShadowColor);
			}
			if (props.getProperty("windowIconRolloverColor") != null) {
				windowIconRolloverColor = createColor(props.getProperty("windowIconRolloverColor"),
						windowIconRolloverColor);
			}

			if (props.getProperty("windowInactiveTitleForegroundColor") != null) {
				windowInactiveTitleForegroundColor = createColor(
						props.getProperty("windowInactiveTitleForegroundColor"), windowInactiveTitleForegroundColor);
			}
			if (props.getProperty("windowTitleBackgroundColor") != null) {
				windowInactiveTitleBackgroundColor = createColor(
						props.getProperty("windowInactiveTitleBackgroundColor"), windowInactiveTitleBackgroundColor);
			}
			if (props.getProperty("windowInactiveTitleColorLight") != null) {
				windowInactiveTitleColorLight = createColor(props.getProperty("windowInactiveTitleColorLight"),
						windowInactiveTitleColorLight);
			}
			if (props.getProperty("windowInactiveTitleColorDark") != null) {
				windowInactiveTitleColorDark = createColor(props.getProperty("windowInactiveTitleColorDark"),
						windowInactiveTitleColorDark);
			}
			if (props.getProperty("windowInactiveBorderColor") != null) {
				windowInactiveBorderColor = createColor(props.getProperty("windowInactiveBorderColor"),
						windowInactiveBorderColor);
			}

			if (props.getProperty("menuForegroundColor") != null) {
				menuForegroundColor = createColor(props.getProperty("menuForegroundColor"), menuForegroundColor);
			}
			if (props.getProperty("menuBackgroundColor") != null) {
				menuBackgroundColor = createColor(props.getProperty("menuBackgroundColor"), menuBackgroundColor);
			}
			if (props.getProperty("menuSelectionForegroundColor") != null) {
				menuSelectionForegroundColor = createColor(props.getProperty("menuSelectionForegroundColor"),
						menuSelectionForegroundColor);
			}
			if (props.getProperty("menuSelectionBackgroundColor") != null) {
				menuSelectionBackgroundColor = createColor(props.getProperty("menuSelectionBackgroundColor"),
						menuSelectionBackgroundColor);
			}
			if (props.getProperty("menuSelectionBackgroundColorLight") != null) {
				menuSelectionBackgroundColorLight = createColor(props.getProperty("menuSelectionBackgroundColorLight"),
						menuSelectionBackgroundColorLight);
			}
			if (props.getProperty("menuSelectionBackgroundColorDark") != null) {
				menuSelectionBackgroundColorDark = createColor(props.getProperty("menuSelectionBackgroundColorDark"),
						menuSelectionBackgroundColorDark);
			}
			if (props.getProperty("menuColorLight") != null) {
				menuColorLight = createColor(props.getProperty("menuColorLight"), menuColorLight);
			}
			if (props.getProperty("menuColorDark") != null) {
				menuColorDark = createColor(props.getProperty("menuColorDark"), menuColorDark);
			}

			if (props.getProperty("toolbarForegroundColor") != null) {
				toolbarForegroundColor = createColor(props.getProperty("toolbarForegroundColor"),
						toolbarForegroundColor);
			}
			if (props.getProperty("toolbarBackgroundColor") != null) {
				toolbarBackgroundColor = createColor(props.getProperty("toolbarBackgroundColor"),
						toolbarBackgroundColor);
			}
			if (props.getProperty("toolbarColorLight") != null) {
				toolbarColorLight = createColor(props.getProperty("toolbarColorLight"), toolbarColorLight);
			}
			if (props.getProperty("toolbarColorDark") != null) {
				toolbarColorDark = createColor(props.getProperty("toolbarColorDark"), toolbarColorDark);
			}

			if (props.getProperty("tabAreaBackgroundColor") != null) {
				tabAreaBackgroundColor = createColor(props.getProperty("tabAreaBackgroundColor"),
						tabAreaBackgroundColor);
			} else {
				tabAreaBackgroundColor = backgroundColor;
			}
			if (props.getProperty("tabSelectionForegroundColor") != null) {
				tabSelectionForegroundColor = createColor(props.getProperty("tabSelectionForegroundColor"),
						tabSelectionForegroundColor);
			}

			if (props.getProperty("desktopColor") != null) {
				desktopColor = createColor(props.getProperty("desktopColor"), desktopColor);
			}
			if (props.getProperty("tooltipForegroundColor") != null) {
				tooltipForegroundColor = createColor(props.getProperty("tooltipForegroundColor"),
						tooltipForegroundColor);
			}
			if (props.getProperty("tooltipBackgroundColor") != null) {
				tooltipBackgroundColor = createColor(props.getProperty("tooltipBackgroundColor"),
						tooltipBackgroundColor);
			}
			if (props.getProperty("tooltipBorderSize") != null) {
				tooltipBorderSize = createInt(props.getProperty("tooltipBorderSize"), tooltipBorderSize);
			}
			if (props.getProperty("tooltipShadowSize") != null) {
				tooltipShadowSize = createInt(props.getProperty("tooltipShadowSize"), tooltipShadowSize);
			}
			if (props.getProperty("tooltipCastShadow") != null) {
				tooltipCastShadow = props.getProperty("tooltipCastShadow").trim().equalsIgnoreCase("on");
			}

			if (props.getProperty("textureSet") != null) {
				textureSet = props.getProperty("textureSet");
			}
			if (props.getProperty("darkTexture") != null) {
				darkTexture = props.getProperty("darkTexture").trim().equalsIgnoreCase("on");
			}
			if (props.get("windowTexture") != null) {
				Object texture = props.get("windowTexture");
				if (texture instanceof Icon) {
					windowTexture = (Icon) texture;
				}
			}
			if (props.get("backgroundTexture") != null) {
				Object texture = props.get("backgroundTexture");
				if (texture instanceof Icon) {
					backgroundTexture = (Icon) texture;
				}
			}
			if (props.get("alterBackgroundTexture") != null) {
				Object texture = props.get("alterBackgroundTexture");
				if (texture instanceof Icon) {
					alterBackgroundTexture = (Icon) texture;
				}
			}
			if (props.get("selectedTexture") != null) {
				Object texture = props.get("selectedTexture");
				if (texture instanceof Icon) {
					selectedTexture = (Icon) texture;
				}
			}
			if (props.get("rolloverTexture") != null) {
				Object texture = props.get("rolloverTexture");
				if (texture instanceof Icon) {
					rolloverTexture = (Icon) texture;
				}
			}
			if (props.get("pressedTexture") != null) {
				Object texture = props.get("pressedTexture");
				if (texture instanceof Icon) {
					pressedTexture = (Icon) texture;
				}
			}
			if (props.get("disabledTexture") != null) {
				Object texture = props.get("disabledTexture");
				if (texture instanceof Icon) {
					disabledTexture = (Icon) texture;
				}
			}
			if (props.get("menubarTexture") != null) {
				Object texture = props.get("menubarTexture");
				if (texture instanceof Icon) {
					menubarTexture = (Icon) texture;
				}
			}
		}
	}

	/**
	 * <p>setUpColor.</p>
	 */
	public void setUpColor() {
		windowDecoration = true;
		macStyleWindowDecoration = JTattooUtilities.isMac();
		centerWindowTitle = JTattooUtilities.isWindows() && JTattooUtilities.getOSVersion() >= 6.2
				&& JTattooUtilities.getOSVersion() < 10.0;
		linuxStyleScrollBar = !JTattooUtilities.isWindows();
		macStyleScrollBar = JTattooUtilities.isMac();
		dynamicLayout = true;
		textShadow = false;
		textAntiAliasing = true;
		textAntiAliasingMode = TEXT_ANTIALIAS_HRGB;
		backgroundPattern = true;
		brightMode = false;
		showFocusFrame = false;
		drawSquareButtons = false;
		toolbarDecorated = true;
		menuOpaque = true;
		menuAlpha = 0.9f;
		logoString = "JTattoo";

		controlFont = null;
		systemFont = null;
		userFont = null;
		smallFont = null;
		menuFont = null;
		windowTitleFont = null;

		foregroundColor = BLACK;
		backgroundColor = EXTRA_LIGHT_GRAY;
		backgroundColorLight = WHITE;
		backgroundColorDark = EXTRA_LIGHT_GRAY;
		alterBackgroundColor = LIGHT_GRAY;
		disabledForegroundColor = GRAY;
		disabledBackgroundColor = SUPER_LIGHT_GRAY;
		inputBackgroundColor = WHITE;
		inputForegroundColor = BLACK;
		selectionForegroundColor = BLACK;
		selectionBackgroundColor = LIGHT_GRAY;
		selectionBackgroundColorLight = EXTRA_LIGHT_GRAY;
		selectionBackgroundColorDark = LIGHT_GRAY;
		focusColor = ORANGE;
		focusCellColor = ORANGE;
		focusFrameColor = new ColorUIResource(230, 191, 116);
		focusBackgroundColor = new ColorUIResource(255, 250, 212);
		focusForegroundColor = BLACK;
		frameColor = DARK_GRAY;
		gridColor = GRAY;
		shadowColor = new ColorUIResource(0, 24, 0);

		rolloverForegroundColor = BLACK;
		rolloverColor = EXTRA_LIGHT_GRAY;
		rolloverColorLight = WHITE;
		rolloverColorDark = EXTRA_LIGHT_GRAY;

		pressedForegroundColor = BLACK;
		pressedBackgroundColor = LIGHT_GRAY;
		pressedBackgroundColorLight = new ColorUIResource(ColorHelper.brighter(pressedBackgroundColor, 20));
		pressedBackgroundColorDark = new ColorUIResource(ColorHelper.darker(pressedBackgroundColor, 4));

		buttonForegroundColor = BLACK;
		buttonBackgroundColor = LIGHT_GRAY;
		buttonColorLight = WHITE;
		buttonColorDark = LIGHT_GRAY;

		controlForegroundColor = BLACK;
		controlBackgroundColor = LIGHT_GRAY;
		controlHighlightColor = WHITE;
		controlShadowColor = LIGHT_GRAY;
		controlDarkShadowColor = DARK_GRAY;
		controlColorLight = WHITE;
		controlColorDark = LIGHT_GRAY;

		windowTitleForegroundColor = BLACK;
		windowTitleBackgroundColor = BLUE;
		windowTitleColorLight = EXTRA_LIGHT_GRAY;
		windowTitleColorDark = LIGHT_GRAY;
		windowBorderColor = LIGHT_GRAY;
		windowIconColor = BLACK;
		windowIconShadowColor = WHITE;
		windowIconRolloverColor = RED;

		windowInactiveTitleForegroundColor = BLACK;
		windowInactiveTitleBackgroundColor = EXTRA_LIGHT_GRAY;
		windowInactiveTitleColorLight = WHITE;
		windowInactiveTitleColorDark = EXTRA_LIGHT_GRAY;
		windowInactiveBorderColor = EXTRA_LIGHT_GRAY;

		menuForegroundColor = BLACK;
		menuBackgroundColor = EXTRA_LIGHT_GRAY;
		menuSelectionForegroundColor = BLACK;
		menuSelectionBackgroundColor = LIGHT_GRAY;
		menuSelectionBackgroundColorLight = EXTRA_LIGHT_GRAY;
		menuSelectionBackgroundColorDark = LIGHT_GRAY;
		menuColorLight = EXTRA_LIGHT_GRAY;
		menuColorDark = LIGHT_GRAY;

		toolbarForegroundColor = BLACK;
		toolbarBackgroundColor = LIGHT_GRAY;
		toolbarColorLight = WHITE;
		toolbarColorDark = LIGHT_GRAY;

		tabAreaBackgroundColor = backgroundColor;
		tabSelectionForegroundColor = selectionForegroundColor;
		desktopColor = DARK_BLUE;
		tooltipForegroundColor = BLACK;
		tooltipBackgroundColor = YELLOW;
		tooltipBorderSize = 6;
		tooltipShadowSize = 6;
		tooltipCastShadow = false;

		textureSet = "Default";
		darkTexture = true;
	}

	/**
	 * <p>setUpColorArrs.</p>
	 */
	public void setUpColorArrs() {
		DEFAULT_COLORS = ColorHelper.createColorArr(controlColorLight, controlColorDark, 20);
		HIDEFAULT_COLORS = ColorHelper.createColorArr(ColorHelper.brighter(controlColorLight, 40),
				ColorHelper.brighter(controlColorDark, 40), 20);
		ACTIVE_COLORS = DEFAULT_COLORS;
		INACTIVE_COLORS = HIDEFAULT_COLORS;
		ROLLOVER_COLORS = ColorHelper.createColorArr(rolloverColorLight, rolloverColorDark, 20);
		SELECTED_COLORS = DEFAULT_COLORS;
		SELECTION_COLORS = ColorHelper.createColorArr(selectionBackgroundColorLight, selectionBackgroundColorDark, 20);
		FOCUS_COLORS = ColorHelper.createColorArr(ColorHelper.brighter(focusBackgroundColor, 20),
				ColorHelper.darker(focusBackgroundColor, 10), 20);
		MENU_SELECTION_COLORS = ColorHelper.createColorArr(menuSelectionBackgroundColorLight,
				menuSelectionBackgroundColorDark, 20);
		// PRESSED_COLORS = DEFAULT_COLORS;
		PRESSED_COLORS = ColorHelper.createColorArr(pressedBackgroundColorDark, pressedBackgroundColorLight, 20);
		DISABLED_COLORS = HIDEFAULT_COLORS;
		WINDOW_TITLE_COLORS = ColorHelper.createColorArr(windowTitleColorLight, windowTitleColorDark, 20);
		WINDOW_INACTIVE_TITLE_COLORS = ColorHelper.createColorArr(windowInactiveTitleColorLight,
				windowInactiveTitleColorDark, 20);
		TOOLBAR_COLORS = ColorHelper.createColorArr(toolbarColorLight, toolbarColorDark, 20);
		MENUBAR_COLORS = ColorHelper.createColorArr(menuColorLight, menuColorDark, 20);
		BUTTON_COLORS = ColorHelper.createColorArr(buttonColorLight, buttonColorDark, 20);
		CHECKBOX_COLORS = DEFAULT_COLORS;
		TAB_COLORS = DEFAULT_COLORS;
		COL_HEADER_COLORS = DEFAULT_COLORS;
		TRACK_COLORS = ColorHelper.createColorArr(new Color(220, 220, 220), Color.white, 20);
		THUMB_COLORS = DEFAULT_COLORS;
		SLIDER_COLORS = DEFAULT_COLORS;
		PROGRESSBAR_COLORS = DEFAULT_COLORS;
	}

} // end of class AbstractTheme
