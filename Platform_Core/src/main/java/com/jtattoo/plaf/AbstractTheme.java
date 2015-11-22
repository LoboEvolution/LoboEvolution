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
import java.util.Properties;
import javax.swing.Icon;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalTheme;

/**
 * The Class AbstractTheme.
 */
public abstract class AbstractTheme extends MetalTheme {

    /** The Constant TEXT_ANTIALIAS_DEFAULT. */
    public static final int TEXT_ANTIALIAS_DEFAULT = 0;
    
    /** The Constant TEXT_ANTIALIAS_GRAY. */
    public static final int TEXT_ANTIALIAS_GRAY = 1;
    
    /** The Constant TEXT_ANTIALIAS_HRGB. */
    public static final int TEXT_ANTIALIAS_HRGB = 2;
    
    /** The Constant TEXT_ANTIALIAS_HBGR. */
    public static final int TEXT_ANTIALIAS_HBGR = 3;
    
    /** The Constant TEXT_ANTIALIAS_VRGB. */
    public static final int TEXT_ANTIALIAS_VRGB = 4;
    
    /** The Constant TEXT_ANTIALIAS_VBGR. */
    public static final int TEXT_ANTIALIAS_VBGR = 5;
    
    /** The Constant DIALOG. */
    public static final String DIALOG = "Dialog";

    /** The Constant red. */
    public static final ColorUIResource red = new ColorUIResource(255, 0, 0);
    
    /** The Constant green. */
    public static final ColorUIResource green = new ColorUIResource(0, 255, 255);
    
    /** The Constant cyan. */
    public static final ColorUIResource cyan = new ColorUIResource(0, 255, 255);
    
    /** The Constant white. */
    public static final ColorUIResource white = new ColorUIResource(255, 255, 255);
    
    /** The Constant superLightGray. */
    public static final ColorUIResource superLightGray = new ColorUIResource(248, 248, 248);
    
    /** The Constant extraLightGray. */
    public static final ColorUIResource extraLightGray = new ColorUIResource(232, 232, 232);
    
    /** The Constant lightGray. */
    public static final ColorUIResource lightGray = new ColorUIResource(196, 196, 196);
    
    /** The Constant gray. */
    public static final ColorUIResource gray = new ColorUIResource(164, 164, 164);
    
    /** The Constant darkGray. */
    public static final ColorUIResource darkGray = new ColorUIResource(148, 148, 148);
    
    /** The Constant extraDarkGray. */
    public static final ColorUIResource extraDarkGray = new ColorUIResource(96, 96, 96);
    
    /** The Constant black. */
    public static final ColorUIResource black = new ColorUIResource(0, 0, 0);
    
    /** The Constant orange. */
    public static final ColorUIResource orange = new ColorUIResource(255, 200, 0);
    
    /** The Constant lightOrange. */
    public static final ColorUIResource lightOrange = new ColorUIResource(255, 220, 96);
    
    /** The Constant yellow. */
    public static final ColorUIResource yellow = new ColorUIResource(255, 255, 196);
    
    /** The Constant blue. */
    public static final ColorUIResource blue = new ColorUIResource(0, 128, 255);
    
    /** The Constant darkBlue. */
    public static final ColorUIResource darkBlue = new ColorUIResource(0, 64, 128);

    /** The internal name. */
    protected static String internalName = "Default";
    
    /** The window decoration. */
    protected static boolean windowDecoration = false;
    
    /** The mac style window decoration. */
    protected static boolean macStyleWindowDecoration = false;
    
    /** The center window title. */
    protected static boolean centerWindowTitle = false;
    
    /** The linux style scroll bar. */
    protected static boolean linuxStyleScrollBar = false;
    
    /** The mac style scroll bar. */
    protected static boolean macStyleScrollBar = false;
    
    /** The dynamic layout. */
    protected static boolean dynamicLayout = false;
    
    /** The text shadow. */
    protected static boolean textShadow = false;
    
    /** The text anti aliasing. */
    protected static boolean textAntiAliasing = false;
    
    /** The text anti aliasing mode. */
    protected static int textAntiAliasingMode = TEXT_ANTIALIAS_HRGB;
    
    /** The background pattern. */
    protected static boolean backgroundPattern = true;
    
    /** The bright mode. */
    protected static boolean brightMode = false;
    
    /** The show focus frame. */
    protected static boolean showFocusFrame = false;
    
    /** The draw square buttons. */
    protected static boolean drawSquareButtons = false;
    
    /** The toolbar decorated. */
    protected static boolean toolbarDecorated = true;

    /** The menu opaque. */
    protected static boolean menuOpaque = true;
    
    /** The menu alpha. */
    protected static float menuAlpha = 0.9f;
    
    /** The logo string. */
    protected static String logoString = "JTattoo";
    
    /** The control font. */
    protected static FontUIResource controlFont = null;
    
    /** The system font. */
    protected static FontUIResource systemFont = null;
    
    /** The user font. */
    protected static FontUIResource userFont = null;
    
    /** The small font. */
    protected static FontUIResource smallFont = null;
    
    /** The menu font. */
    protected static FontUIResource menuFont = null;
    
    /** The window title font. */
    protected static FontUIResource windowTitleFont = null;
    
    /** The foreground color. */
    protected static ColorUIResource foregroundColor = null;
    
    /** The background color. */
    protected static ColorUIResource backgroundColor = null;
    
    /** The background color light. */
    protected static ColorUIResource backgroundColorLight = null;
    
    /** The background color dark. */
    protected static ColorUIResource backgroundColorDark = null;
    
    /** The alter background color. */
    protected static ColorUIResource alterBackgroundColor = null;
    
    /** The disabled foreground color. */
    protected static ColorUIResource disabledForegroundColor = null;
    
    /** The disabled background color. */
    protected static ColorUIResource disabledBackgroundColor = null;
    
    /** The input background color. */
    protected static ColorUIResource inputBackgroundColor = null;
    
    /** The input foreground color. */
    protected static ColorUIResource inputForegroundColor = null;
    
    /** The selection foreground color. */
    protected static ColorUIResource selectionForegroundColor = null;
    
    /** The selection background color light. */
    protected static ColorUIResource selectionBackgroundColorLight = null;
    
    /** The selection background color dark. */
    protected static ColorUIResource selectionBackgroundColorDark = null;
    
    /** The selection background color. */
    protected static ColorUIResource selectionBackgroundColor = null;
    
    /** The rollover foreground color. */
    protected static ColorUIResource rolloverForegroundColor = null;
    
    /** The rollover color. */
    protected static ColorUIResource rolloverColor = null;
    
    /** The rollover color light. */
    protected static ColorUIResource rolloverColorLight = null;
    
    /** The rollover color dark. */
    protected static ColorUIResource rolloverColorDark = null;
    
    /** The pressed foreground color. */
    protected static ColorUIResource pressedForegroundColor = null;
    
    /** The focus color. */
    protected static ColorUIResource focusColor = null;
    
    /** The focus cell color. */
    protected static ColorUIResource focusCellColor = null;
    
    /** The focus frame color. */
    protected static ColorUIResource focusFrameColor = null;
    
    /** The focus background color. */
    protected static ColorUIResource focusBackgroundColor = null;
    
    /** The focus foreground color. */
    protected static ColorUIResource focusForegroundColor = null;
    
    /** The frame color. */
    protected static ColorUIResource frameColor = null;
    
    /** The grid color. */
    protected static ColorUIResource gridColor = null;
    
    /** The shadow color. */
    protected static ColorUIResource shadowColor = null;
    
    /** The button foreground color. */
    protected static ColorUIResource buttonForegroundColor = null;
    
    /** The button background color. */
    protected static ColorUIResource buttonBackgroundColor = null;
    
    /** The button color light. */
    protected static ColorUIResource buttonColorLight = null;
    
    /** The button color dark. */
    protected static ColorUIResource buttonColorDark = null;
    
    /** The control foreground color. */
    protected static ColorUIResource controlForegroundColor = null;
    
    /** The control background color. */
    protected static ColorUIResource controlBackgroundColor = null;
    
    /** The control highlight color. */
    protected static ColorUIResource controlHighlightColor = null;
    
    /** The control shadow color. */
    protected static ColorUIResource controlShadowColor = null;
    
    /** The control dark shadow color. */
    protected static ColorUIResource controlDarkShadowColor = null;
    
    /** The control color light. */
    protected static ColorUIResource controlColorLight = null;
    
    /** The control color dark. */
    protected static ColorUIResource controlColorDark = null;
    
    /** The window title foreground color. */
    protected static ColorUIResource windowTitleForegroundColor = null;
    
    /** The window title background color. */
    protected static ColorUIResource windowTitleBackgroundColor = null;
    
    /** The window title color light. */
    protected static ColorUIResource windowTitleColorLight = null;
    
    /** The window title color dark. */
    protected static ColorUIResource windowTitleColorDark = null;
    
    /** The window border color. */
    protected static ColorUIResource windowBorderColor = null;
    
    /** The window icon color. */
    protected static ColorUIResource windowIconColor = null;
    
    /** The window icon shadow color. */
    protected static ColorUIResource windowIconShadowColor = null;
    
    /** The window icon rollover color. */
    protected static ColorUIResource windowIconRolloverColor = null;

    /** The window inactive title foreground color. */
    protected static ColorUIResource windowInactiveTitleForegroundColor = null;
    
    /** The window inactive title background color. */
    protected static ColorUIResource windowInactiveTitleBackgroundColor = null;
    
    /** The window inactive title color light. */
    protected static ColorUIResource windowInactiveTitleColorLight = null;
    
    /** The window inactive title color dark. */
    protected static ColorUIResource windowInactiveTitleColorDark = null;
    
    /** The window inactive border color. */
    protected static ColorUIResource windowInactiveBorderColor = null;
    
    /** The menu foreground color. */
    protected static ColorUIResource menuForegroundColor = null;
    
    /** The menu background color. */
    protected static ColorUIResource menuBackgroundColor = null;
    
    /** The menu selection foreground color. */
    protected static ColorUIResource menuSelectionForegroundColor = null;
    
    /** The menu selection background color. */
    protected static ColorUIResource menuSelectionBackgroundColor = null;
    
    /** The menu selection background color light. */
    protected static ColorUIResource menuSelectionBackgroundColorLight = null;
    
    /** The menu selection background color dark. */
    protected static ColorUIResource menuSelectionBackgroundColorDark = null;
    
    /** The menu color light. */
    protected static ColorUIResource menuColorLight = null;
    
    /** The menu color dark. */
    protected static ColorUIResource menuColorDark = null;
    
    /** The toolbar foreground color. */
    protected static ColorUIResource toolbarForegroundColor = null;
    
    /** The toolbar background color. */
    protected static ColorUIResource toolbarBackgroundColor = null;
    
    /** The toolbar color light. */
    protected static ColorUIResource toolbarColorLight = null;
    
    /** The toolbar color dark. */
    protected static ColorUIResource toolbarColorDark = null;
    
    /** The tab area background color. */
    protected static ColorUIResource tabAreaBackgroundColor = null;
    
    /** The tab selection foreground color. */
    protected static ColorUIResource tabSelectionForegroundColor = null;
    
    /** The desktop color. */
    protected static ColorUIResource desktopColor = null;
    
    /** The tooltip foreground color. */
    protected static ColorUIResource tooltipForegroundColor = null;
    
    /** The tooltip background color. */
    protected static ColorUIResource tooltipBackgroundColor = null;
    
    /** The tooltip border size. */
    protected static int tooltipBorderSize = 6;
    
    /** The tooltip shadow size. */
    protected static int tooltipShadowSize = 6;
    
    /** The tooltip cast shadow. */
    protected static boolean tooltipCastShadow = false;

    /** The default colors. */
    protected static Color DEFAULT_COLORS[] = null;
    
    /** The hidefault colors. */
    protected static Color HIDEFAULT_COLORS[] = null;
    
    /** The active colors. */
    protected static Color ACTIVE_COLORS[] = null;
    
    /** The inactive colors. */
    protected static Color INACTIVE_COLORS[] = null;
    
    /** The rollover colors. */
    protected static Color ROLLOVER_COLORS[] = null;
    
    /** The selected colors. */
    protected static Color SELECTED_COLORS[] = null;
    
    /** The selection colors. */
    protected static Color SELECTION_COLORS[] = null;
    
    /** The focus colors. */
    protected static Color FOCUS_COLORS[] = null;
    
    /** The menu selection colors. */
    protected static Color MENU_SELECTION_COLORS[] = null;
    
    /** The pressed colors. */
    protected static Color PRESSED_COLORS[] = null;
    
    /** The disabled colors. */
    protected static Color DISABLED_COLORS[] = null;
    
    /** The window title colors. */
    protected static Color WINDOW_TITLE_COLORS[] = null;
    
    /** The window inactive title colors. */
    protected static Color WINDOW_INACTIVE_TITLE_COLORS[] = null;
    
    /** The toolbar colors. */
    protected static Color TOOLBAR_COLORS[] = null;
    
    /** The menubar colors. */
    protected static Color MENUBAR_COLORS[] = null;
    
    /** The button colors. */
    protected static Color BUTTON_COLORS[] = null;
    
    /** The checkbox colors. */
    protected static Color CHECKBOX_COLORS[] = null;
    
    /** The tab colors. */
    protected static Color TAB_COLORS[] = null;
    
    /** The col header colors. */
    protected static Color COL_HEADER_COLORS[] = null;
    
    /** The track colors. */
    protected static Color TRACK_COLORS[] = null;
    
    /** The thumb colors. */
    protected static Color THUMB_COLORS[] = null;
    
    /** The slider colors. */
    protected static Color SLIDER_COLORS[] = null;
    
    /** The progressbar colors. */
    protected static Color PROGRESSBAR_COLORS[] = null;

    /** The texture set. */
    protected static String textureSet = "Default";
    
    /** The dark texture. */
    protected static boolean darkTexture = true;
    
    /** The window texture. */
    protected static Icon windowTexture = null;
    
    /** The background texture. */
    protected static Icon backgroundTexture = null;
    
    /** The alter background texture. */
    protected static Icon alterBackgroundTexture = null;
    
    /** The selected texture. */
    protected static Icon selectedTexture = null;
    
    /** The rollover texture. */
    protected static Icon rolloverTexture = null;
    
    /** The pressed texture. */
    protected static Icon pressedTexture = null;
    
    /** The disabled texture. */
    protected static Icon disabledTexture = null;
    
    /** The menubar texture. */
    protected static Icon menubarTexture = null;

    public AbstractTheme() {
        super();
    }

    public String getName() {
        return getInternalName();
    }

    /** Gets the internal name.
	 *
	 * @return the internal name
	 */
    public static String getInternalName() {
        return internalName;
    }

    /** Sets the internal name.
	 *
	 * @param name
	 *            the new internal name
	 */
    public static void setInternalName(String name) {
        internalName = name;
    }

    /** Gets the property file name.
	 *
	 * @return the property file name
	 */
    public String getPropertyFileName() {
        return "JTattooTheme.properties";
    }

    public void setUpColor() {
        windowDecoration = true;
        macStyleWindowDecoration = JTattooUtilities.isMac();
        centerWindowTitle = JTattooUtilities.isWindows() && JTattooUtilities.getOSVersion() >= 6.2;
        linuxStyleScrollBar = !JTattooUtilities.isWindows();
        macStyleScrollBar = JTattooUtilities.isMac();
        dynamicLayout = true;
        textShadow = false;
        textAntiAliasing = false;
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

        foregroundColor = black;
        backgroundColor = extraLightGray;
        backgroundColorLight = white;
        backgroundColorDark = extraLightGray;
        alterBackgroundColor = lightGray;
        disabledForegroundColor = gray;
        disabledBackgroundColor = superLightGray;
        inputBackgroundColor = white;
        inputForegroundColor = black;
        selectionForegroundColor = black;
        selectionBackgroundColor = lightGray;
        selectionBackgroundColorLight = extraLightGray;
        selectionBackgroundColorDark = lightGray;
        focusColor = orange;
        focusCellColor = orange;
        focusFrameColor = new ColorUIResource(230, 191, 116);
        focusBackgroundColor = new ColorUIResource(255, 250, 212);
        focusForegroundColor = black;
        frameColor = darkGray;
        gridColor = gray;
        shadowColor = new ColorUIResource(0, 24, 0);

        rolloverForegroundColor = black;
        rolloverColor = extraLightGray;
        rolloverColorLight = white;
        rolloverColorDark = extraLightGray;

        pressedForegroundColor = black;

        buttonForegroundColor = black;
        buttonBackgroundColor = lightGray;
        buttonColorLight = white;
        buttonColorDark = lightGray;

        controlForegroundColor = black;
        controlBackgroundColor = lightGray;
        controlHighlightColor = white;
        controlShadowColor = lightGray;
        controlDarkShadowColor = darkGray;
        controlColorLight = white;
        controlColorDark = lightGray;

        windowTitleForegroundColor = black;
        windowTitleBackgroundColor = blue;
        windowTitleColorLight = extraLightGray;
        windowTitleColorDark = lightGray;
        windowBorderColor = lightGray;
        windowIconColor = black;
        windowIconShadowColor = white;
        windowIconRolloverColor = red;

        windowInactiveTitleForegroundColor = black;
        windowInactiveTitleBackgroundColor = extraLightGray;
        windowInactiveTitleColorLight = white;
        windowInactiveTitleColorDark = extraLightGray;
        windowInactiveBorderColor = extraLightGray;

        menuForegroundColor = black;
        menuBackgroundColor = extraLightGray;
        menuSelectionForegroundColor = black;
        menuSelectionBackgroundColor = lightGray;
        menuSelectionBackgroundColorLight = extraLightGray;
        menuSelectionBackgroundColorDark = lightGray;
        menuColorLight = extraLightGray;
        menuColorDark = lightGray;

        toolbarForegroundColor = black;
        toolbarBackgroundColor = lightGray;
        toolbarColorLight = white;
        toolbarColorDark = lightGray;

        tabAreaBackgroundColor = backgroundColor;
        tabSelectionForegroundColor = selectionForegroundColor;
        desktopColor = darkBlue;
        tooltipForegroundColor = black;
        tooltipBackgroundColor = yellow;
        tooltipBorderSize = 6;
        tooltipShadowSize = 6;
        tooltipCastShadow = false;

        textureSet = "Default";
        darkTexture = true;
    }

    public void setUpColorArrs() {
        DEFAULT_COLORS = ColorHelper.createColorArr(controlColorLight, controlColorDark, 20);
        HIDEFAULT_COLORS = ColorHelper.createColorArr(ColorHelper.brighter(controlColorLight, 40), ColorHelper.brighter(controlColorDark, 40), 20);
        ACTIVE_COLORS = DEFAULT_COLORS;
        INACTIVE_COLORS = HIDEFAULT_COLORS;
        ROLLOVER_COLORS = ColorHelper.createColorArr(rolloverColorLight, rolloverColorDark, 20);
        SELECTED_COLORS = DEFAULT_COLORS;
        SELECTION_COLORS = ColorHelper.createColorArr(selectionBackgroundColorLight, selectionBackgroundColorDark, 20);
        FOCUS_COLORS = ColorHelper.createColorArr(ColorHelper.brighter(focusBackgroundColor, 20), ColorHelper.darker(focusBackgroundColor, 10), 20);
        MENU_SELECTION_COLORS = ColorHelper.createColorArr(menuSelectionBackgroundColorLight, menuSelectionBackgroundColorDark, 20);
        PRESSED_COLORS = DEFAULT_COLORS;
        DISABLED_COLORS = HIDEFAULT_COLORS;
        WINDOW_TITLE_COLORS = ColorHelper.createColorArr(windowTitleColorLight, windowTitleColorDark, 20);
        WINDOW_INACTIVE_TITLE_COLORS = ColorHelper.createColorArr(windowInactiveTitleColorLight, windowInactiveTitleColorDark, 20);
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

    /** Sets the properties.
	 *
	 * @param props
	 *            the new properties
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
                disabledForegroundColor = createColor(props.getProperty("disabledForegroundColor"), disabledForegroundColor);
            }
            if (props.getProperty("disabledBackgroundColor") != null) {
                disabledBackgroundColor = createColor(props.getProperty("disabledBackgroundColor"), disabledBackgroundColor);
            }
            if (props.getProperty("inputForegroundColor") != null) {
                inputForegroundColor = createColor(props.getProperty("inputForegroundColor"), inputForegroundColor);
            }
            if (props.getProperty("inputBackgroundColor") != null) {
                inputBackgroundColor = createColor(props.getProperty("inputBackgroundColor"), inputBackgroundColor);
            }
            if (props.getProperty("selectionForegroundColor") != null) {
                selectionForegroundColor = createColor(props.getProperty("selectionForegroundColor"), selectionForegroundColor);
            }
            if (props.getProperty("selectionBackgroundColor") != null) {
                selectionBackgroundColor = createColor(props.getProperty("selectionBackgroundColor"), selectionBackgroundColor);
            }
            if (props.getProperty("selectionBackgroundColorLight") != null) {
                selectionBackgroundColorLight = createColor(props.getProperty("selectionBackgroundColorLight"), selectionBackgroundColorLight);
            }
            if (props.getProperty("selectionBackgroundColorDark") != null) {
                selectionBackgroundColorDark = createColor(props.getProperty("selectionBackgroundColorDark"), selectionBackgroundColorDark);
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
                rolloverForegroundColor = createColor(props.getProperty("rolloverForegroundColor"), rolloverForegroundColor);
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
                pressedForegroundColor = createColor(props.getProperty("pressedForegroundColor"), pressedForegroundColor);
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
                controlForegroundColor = createColor(props.getProperty("controlForegroundColor"), controlForegroundColor);
            }
            if (props.getProperty("controlBackgroundColor") != null) {
                controlBackgroundColor = createColor(props.getProperty("controlBackgroundColor"), controlBackgroundColor);
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
                controlDarkShadowColor = createColor(props.getProperty("controlDarkShadowColor"), controlDarkShadowColor);
            }

            if (props.getProperty("windowTitleForegroundColor") != null) {
                windowTitleForegroundColor = createColor(props.getProperty("windowTitleForegroundColor"), windowTitleForegroundColor);
            }
            if (props.getProperty("windowTitleBackgroundColor") != null) {
                windowTitleBackgroundColor = createColor(props.getProperty("windowTitleBackgroundColor"), windowTitleBackgroundColor);
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
                windowIconRolloverColor = createColor(props.getProperty("windowIconRolloverColor"), windowIconRolloverColor);
            }

            if (props.getProperty("windowInactiveTitleForegroundColor") != null) {
                windowInactiveTitleForegroundColor = createColor(props.getProperty("windowInactiveTitleForegroundColor"), windowInactiveTitleForegroundColor);
            }
            if (props.getProperty("windowTitleBackgroundColor") != null) {
                windowInactiveTitleBackgroundColor = createColor(props.getProperty("windowInactiveTitleBackgroundColor"), windowInactiveTitleBackgroundColor);
            }
            if (props.getProperty("windowInactiveTitleColorLight") != null) {
                windowInactiveTitleColorLight = createColor(props.getProperty("windowInactiveTitleColorLight"), windowInactiveTitleColorLight);
            }
            if (props.getProperty("windowInactiveTitleColorDark") != null) {
                windowInactiveTitleColorDark = createColor(props.getProperty("windowInactiveTitleColorDark"), windowInactiveTitleColorDark);
            }
            if (props.getProperty("windowInactiveBorderColor") != null) {
                windowInactiveBorderColor = createColor(props.getProperty("windowInactiveBorderColor"), windowInactiveBorderColor);
            }

            if (props.getProperty("menuForegroundColor") != null) {
                menuForegroundColor = createColor(props.getProperty("menuForegroundColor"), menuForegroundColor);
            }
            if (props.getProperty("menuBackgroundColor") != null) {
                menuBackgroundColor = createColor(props.getProperty("menuBackgroundColor"), menuBackgroundColor);
            }
            if (props.getProperty("menuSelectionForegroundColor") != null) {
                menuSelectionForegroundColor = createColor(props.getProperty("menuSelectionForegroundColor"), menuSelectionForegroundColor);
            }
            if (props.getProperty("menuSelectionBackgroundColor") != null) {
                menuSelectionBackgroundColor = createColor(props.getProperty("menuSelectionBackgroundColor"), menuSelectionBackgroundColor);
            }
            if (props.getProperty("menuSelectionBackgroundColorLight") != null) {
                menuSelectionBackgroundColorLight = createColor(props.getProperty("menuSelectionBackgroundColorLight"), menuSelectionBackgroundColorLight);
            }
            if (props.getProperty("menuSelectionBackgroundColorDark") != null) {
                menuSelectionBackgroundColorDark = createColor(props.getProperty("menuSelectionBackgroundColorDark"), menuSelectionBackgroundColorDark);
            }
            if (props.getProperty("menuColorLight") != null) {
                menuColorLight = createColor(props.getProperty("menuColorLight"), menuColorLight);
            }
            if (props.getProperty("menuColorDark") != null) {
                menuColorDark = createColor(props.getProperty("menuColorDark"), menuColorDark);
            }

            if (props.getProperty("toolbarForegroundColor") != null) {
                toolbarForegroundColor = createColor(props.getProperty("toolbarForegroundColor"), toolbarForegroundColor);
            }
            if (props.getProperty("toolbarBackgroundColor") != null) {
                toolbarBackgroundColor = createColor(props.getProperty("toolbarBackgroundColor"), toolbarBackgroundColor);
            }
            if (props.getProperty("toolbarColorLight") != null) {
                toolbarColorLight = createColor(props.getProperty("toolbarColorLight"), toolbarColorLight);
            }
            if (props.getProperty("toolbarColorDark") != null) {
                toolbarColorDark = createColor(props.getProperty("toolbarColorDark"), toolbarColorDark);
            }

            if (props.getProperty("tabAreaBackgroundColor") != null) {
                tabAreaBackgroundColor = createColor(props.getProperty("tabAreaBackgroundColor"), tabAreaBackgroundColor);
            } else {
                tabAreaBackgroundColor = backgroundColor;
            }
            if (props.getProperty("tabSelectionForegroundColor") != null) {
                tabSelectionForegroundColor = createColor(props.getProperty("tabSelectionForegroundColor"), tabSelectionForegroundColor);
            }

            if (props.getProperty("desktopColor") != null) {
                desktopColor = createColor(props.getProperty("desktopColor"), desktopColor);
            }
            if (props.getProperty("tooltipForegroundColor") != null) {
                tooltipForegroundColor = createColor(props.getProperty("tooltipForegroundColor"), tooltipForegroundColor);
            }
            if (props.getProperty("tooltipBackgroundColor") != null) {
                tooltipBackgroundColor = createColor(props.getProperty("tooltipBackgroundColor"), tooltipBackgroundColor);
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
                    windowTexture = (Icon)texture;
                }
            }
            if (props.get("backgroundTexture") != null) {
                Object texture = props.get("backgroundTexture");
                if (texture instanceof Icon) {
                    backgroundTexture = (Icon)texture;
                }
            }
            if (props.get("alterBackgroundTexture") != null) {
                Object texture = props.get("alterBackgroundTexture");
                if (texture instanceof Icon) {
                    alterBackgroundTexture = (Icon)texture;
                }
            }
            if (props.get("selectedTexture") != null) {
                Object texture = props.get("selectedTexture");
                if (texture instanceof Icon) {
                    selectedTexture = (Icon)texture;
                }
            }
            if (props.get("rolloverTexture") != null) {
                Object texture = props.get("rolloverTexture");
                if (texture instanceof Icon) {
                    rolloverTexture = (Icon)texture;
                }
            }
            if (props.get("pressedTexture") != null) {
                Object texture = props.get("pressedTexture");
                if (texture instanceof Icon) {
                    pressedTexture = (Icon)texture;
                }
            }
            if (props.get("disabledTexture") != null) {
                Object texture = props.get("disabledTexture");
                if (texture instanceof Icon) {
                    disabledTexture = (Icon)texture;
                }
            }
            if (props.get("menubarTexture") != null) {
                Object texture = props.get("menubarTexture");
                if (texture instanceof Icon) {
                    menubarTexture = (Icon)texture;
                }
            }
        }
    }

    public void loadProperties() {
        FileInputStream in = null;
        try {
            String fileName = System.getProperty("user.home") + "/.jtattoo/" + getPropertyFileName();
            Properties props = new Properties();
            in = new FileInputStream(fileName);
            props.load(in);
            setProperties(props);
        } catch (Exception ex) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    protected static FontUIResource createFont(String fontProp) {
        if ((fontProp != null) && (fontProp.trim().length() > 5)) {
            return new FontUIResource(Font.decode(fontProp));
        }
        return null;
    }

    protected static ColorUIResource createColor(String colorProp, ColorUIResource color) {
        if ((colorProp != null) && (colorProp.trim().length() >= 5)) {
            colorProp = colorProp.trim();
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();
            try {
                int p1 = 0;
                int p2 = colorProp.indexOf(' ');
                if (p2 > 0) {
                    r = Integer.parseInt(colorProp.substring(p1, p2));
                }
                p1 = p2 + 1;
                p2 = colorProp.indexOf(' ', p1);
                if (p2 > 0) {
                    g = Integer.parseInt(colorProp.substring(p1, p2));
                }
                b = Integer.parseInt(colorProp.substring(p2 + 1));
                return new ColorUIResource(r, g, b);
            } catch (Exception ex) {
                System.out.println("Exception while parsing color: " + colorProp);
            }
        }
        return color;
    }

    protected static int createInt(String intProp, int defaultValue) {
        int val = defaultValue;
        try {
            val = Integer.parseInt(intProp);
        } catch (Exception ex) {
            System.out.println("Exception while parsing color: " + intProp);
        }
        return val;
    }
    
    /** Checks if is tiny font size.
	 *
	 * @return true, if is tiny font size
	 */
    public boolean isTinyFontSize() {
        return userFont.getSize() < 12;
    }
    
    /** Checks if is small font size.
	 *
	 * @return true, if is small font size
	 */
    public boolean isSmallFontSize() {
        return userFont.getSize() < 14;
    }
    
    /** Checks if is medium font size.
	 *
	 * @return true, if is medium font size
	 */
    public boolean isMediumFontSize() {
        return userFont.getSize() >= 14 && userFont.getSize() < 16;
    }
    
    /** Checks if is large font size.
	 *
	 * @return true, if is large font size
	 */
    public boolean isLargeFontSize() {
        return userFont.getSize() >= 16;
    }
    
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

    //-----------------------------------------------------------------------------------
    protected ColorUIResource getPrimary1() {
        return foregroundColor;
    }

    protected ColorUIResource getPrimary2() {
        return desktopColor;
    }

    protected ColorUIResource getPrimary3() {
        return selectionBackgroundColor;
    }

    protected ColorUIResource getSecondary1() {
        return frameColor;
    }

    protected ColorUIResource getSecondary2() {
        return controlBackgroundColor;
    }

    protected ColorUIResource getSecondary3() {
        return backgroundColor;
    }

    public ColorUIResource getControl() {
        return controlBackgroundColor;
    }

    public ColorUIResource getControlShadow() {
        return controlShadowColor;
    }

    public ColorUIResource getControlDarkShadow() {
        return controlDarkShadowColor;
    }

    public ColorUIResource getControlInfo() {
        return controlForegroundColor;
    }

    public ColorUIResource getControlHighlight() {
        return controlHighlightColor;
    }

    public ColorUIResource getControlDisabled() {
        return controlShadowColor;
    }

    public ColorUIResource getPrimaryControl() {
        return extraLightGray;
    }

    public ColorUIResource getPrimaryControlShadow() {
        return lightGray;
    }

    public ColorUIResource getPrimaryControlDarkShadow() {
        return gray;
    }

    public ColorUIResource getPrimaryControlInfo() {
        return darkGray;
    }

    public ColorUIResource getPrimaryControlHighlight() {
        return white;
    }

    public ColorUIResource getControlTextColor() {
        return controlForegroundColor;
    }

    public ColorUIResource getSystemTextColor() {
        return foregroundColor;
    }

    /** Gets the logo string.
	 *
	 * @return the logo string
	 */
    public String getLogoString() {
        if (logoString != null) {
            if (logoString.trim().length() == 0) {
                return null;
            }
        }
        return logoString;
    }

    /** Checks if is window decoration on.
	 *
	 * @return true, if is window decoration on
	 */
    public boolean isWindowDecorationOn() {
        return windowDecoration;
    }

    /** Checks if is center window title on.
	 *
	 * @return true, if is center window title on
	 */
    public boolean isCenterWindowTitleOn() {
        return centerWindowTitle;
    }
    
    /** Checks if is mac style window decoration on.
	 *
	 * @return true, if is mac style window decoration on
	 */
    public boolean isMacStyleWindowDecorationOn() {
        return macStyleWindowDecoration;
    }

    /** Checks if is linux style scroll bar on.
	 *
	 * @return true, if is linux style scroll bar on
	 */
    public boolean isLinuxStyleScrollBarOn() {
        return linuxStyleScrollBar;
    }
    
    /** Checks if is mac style scroll bar on.
	 *
	 * @return true, if is mac style scroll bar on
	 */
    public boolean isMacStyleScrollBarOn() {
        return macStyleScrollBar;
    }
    
    /** Checks if is dynamic layout.
	 *
	 * @return true, if is dynamic layout
	 */
    public boolean isDynamicLayout() {
        return dynamicLayout;
    }

    /** Checks if is text shadow on.
	 *
	 * @return true, if is text shadow on
	 */
    public boolean isTextShadowOn() {
        return textShadow;
    }

    /** Checks if is text anti aliasing on.
	 *
	 * @return true, if is text anti aliasing on
	 */
    public boolean isTextAntiAliasingOn() {
        if (JTattooUtilities.getJavaVersion() < 1.4) {
            return false;
        }
        return textAntiAliasing;
    }

    /** Gets the text anti aliasing mode.
	 *
	 * @return the text anti aliasing mode
	 */
    public int getTextAntiAliasingMode() {
        return textAntiAliasingMode;
    }

    /** Gets the text anti aliasing hint.
	 *
	 * @return the text anti aliasing hint
	 */
    public Object getTextAntiAliasingHint() {
        if (isTextAntiAliasingOn()) {
            if (JTattooUtilities.getJavaVersion() >= 1.6) {
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
            return RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
        }
        return RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
    }

    /** Checks if is background pattern on.
	 *
	 * @return true, if is background pattern on
	 */
    public boolean isBackgroundPatternOn() {
        return backgroundPattern;
    }

    /** Checks if is bright mode.
	 *
	 * @return true, if is bright mode
	 */
    public boolean isBrightMode() {
        return brightMode;
    }

    public boolean doShowFocusFrame() {
        return showFocusFrame;
    }

    public boolean doDrawSquareButtons() {
        return drawSquareButtons;
    }

    /** Checks if is toolbar decorated.
	 *
	 * @return true, if is toolbar decorated
	 */
    public boolean isToolbarDecorated() {
        return toolbarDecorated;
    }

    /** Checks if is menu opaque.
	 *
	 * @return true, if is menu opaque
	 */
    public boolean isMenuOpaque() {
        return menuOpaque;
    }

    /** Gets the menu alpha.
	 *
	 * @return the menu alpha
	 */
    public float getMenuAlpha() {
        return menuAlpha;
    }

    /** Gets the foreground color.
	 *
	 * @return the foreground color
	 */
    public ColorUIResource getForegroundColor() {
        return foregroundColor;
    }

    /** Gets the disabled foreground color.
	 *
	 * @return the disabled foreground color
	 */
    public ColorUIResource getDisabledForegroundColor() {
        return disabledForegroundColor;
    }

    /** Gets the background color.
	 *
	 * @return the background color
	 */
    public ColorUIResource getBackgroundColor() {
        return backgroundColor;
    }

    /** Gets the disabled background color.
	 *
	 * @return the disabled background color
	 */
    public ColorUIResource getDisabledBackgroundColor() {
        return disabledBackgroundColor;
    }

    /** Gets the background color light.
	 *
	 * @return the background color light
	 */
    public ColorUIResource getBackgroundColorLight() {
        return backgroundColorLight;
    }

    /** Gets the background color dark.
	 *
	 * @return the background color dark
	 */
    public ColorUIResource getBackgroundColorDark() {
        return backgroundColorDark;
    }

    /** Gets the alter background color.
	 *
	 * @return the alter background color
	 */
    public ColorUIResource getAlterBackgroundColor() {
        return alterBackgroundColor;
    }

    /** Gets the input foreground color.
	 *
	 * @return the input foreground color
	 */
    public ColorUIResource getInputForegroundColor() {
        return inputForegroundColor;
    }

    /** Gets the input background color.
	 *
	 * @return the input background color
	 */
    public ColorUIResource getInputBackgroundColor() {
        return inputBackgroundColor;
    }

    /** Gets the selection foreground color.
	 *
	 * @return the selection foreground color
	 */
    public ColorUIResource getSelectionForegroundColor() {
        return selectionForegroundColor;
    }

    /** Gets the selection background color light.
	 *
	 * @return the selection background color light
	 */
    public ColorUIResource getSelectionBackgroundColorLight() {
        return selectionBackgroundColorLight;
    }

    /** Gets the selection background color dark.
	 *
	 * @return the selection background color dark
	 */
    public ColorUIResource getSelectionBackgroundColorDark() {
        return selectionBackgroundColorDark;
    }

    /** Gets the selection background color.
	 *
	 * @return the selection background color
	 */
    public ColorUIResource getSelectionBackgroundColor() {
        return selectionBackgroundColor;
    }

    /** Gets the frame color.
	 *
	 * @return the frame color
	 */
    public ColorUIResource getFrameColor() {
        return frameColor;
    }

    /** Gets the grid color.
	 *
	 * @return the grid color
	 */
    public ColorUIResource getGridColor() {
        return gridColor;
    }

    /** Gets the shadow color.
	 *
	 * @return the shadow color
	 */
    public ColorUIResource getShadowColor() {
        return shadowColor;
    }

    public ColorUIResource getFocusColor() {
        return focusColor;
    }

    /** Gets the focus cell color.
	 *
	 * @return the focus cell color
	 */
    public ColorUIResource getFocusCellColor() {
        return focusCellColor;
    }

    /** Gets the focus frame color.
	 *
	 * @return the focus frame color
	 */
    public ColorUIResource getFocusFrameColor() {
        return focusFrameColor;
    }

    /** Gets the focus background color.
	 *
	 * @return the focus background color
	 */
    public ColorUIResource getFocusBackgroundColor() {
        return focusBackgroundColor;
    }

    /** Gets the focus foreground color.
	 *
	 * @return the focus foreground color
	 */
    public ColorUIResource getFocusForegroundColor() {
        return focusForegroundColor;
    }

    /** Gets the rollover foreground color.
	 *
	 * @return the rollover foreground color
	 */
    public ColorUIResource getRolloverForegroundColor() {
        return rolloverForegroundColor;
    }

    /** Gets the rollover color.
	 *
	 * @return the rollover color
	 */
    public ColorUIResource getRolloverColor() {
        return rolloverColor;
    }

    /** Gets the rollover color light.
	 *
	 * @return the rollover color light
	 */
    public ColorUIResource getRolloverColorLight() {
        return rolloverColorLight;
    }

    /** Gets the rollover color dark.
	 *
	 * @return the rollover color dark
	 */
    public ColorUIResource getRolloverColorDark() {
        return rolloverColorDark;
    }

    /** Gets the pressed foreground color.
	 *
	 * @return the pressed foreground color
	 */
    public ColorUIResource getPressedForegroundColor() {
        return pressedForegroundColor;
    }

    /** Gets the button foreground color.
	 *
	 * @return the button foreground color
	 */
    public ColorUIResource getButtonForegroundColor() {
        return buttonForegroundColor;
    }

    /** Gets the button background color.
	 *
	 * @return the button background color
	 */
    public ColorUIResource getButtonBackgroundColor() {
        return buttonBackgroundColor;
    }

    /** Gets the button color light.
	 *
	 * @return the button color light
	 */
    public ColorUIResource getButtonColorLight() {
        return buttonColorLight;
    }

    /** Gets the button color dark.
	 *
	 * @return the button color dark
	 */
    public ColorUIResource getButtonColorDark() {
        return buttonColorDark;
    }

    /** Gets the control foreground color.
	 *
	 * @return the control foreground color
	 */
    public ColorUIResource getControlForegroundColor() {
        return controlForegroundColor;
    }

    /** Gets the control background color.
	 *
	 * @return the control background color
	 */
    public ColorUIResource getControlBackgroundColor() {
        return controlBackgroundColor;
    }

    /** Gets the control highlight color.
	 *
	 * @return the control highlight color
	 */
    public ColorUIResource getControlHighlightColor() {
        return controlHighlightColor;
    }

    /** Gets the control shadow color.
	 *
	 * @return the control shadow color
	 */
    public ColorUIResource getControlShadowColor() {
        return controlShadowColor;
    }

    /** Gets the control dark shadow color.
	 *
	 * @return the control dark shadow color
	 */
    public ColorUIResource getControlDarkShadowColor() {
        return controlDarkShadowColor;
    }

    /** Gets the control color light.
	 *
	 * @return the control color light
	 */
    public ColorUIResource getControlColorLight() {
        return controlColorLight;
    }

    /** Gets the control color dark.
	 *
	 * @return the control color dark
	 */
    public ColorUIResource getControlColorDark() {
        return controlColorDark;
    }

    /** Gets the window title foreground color.
	 *
	 * @return the window title foreground color
	 */
    public ColorUIResource getWindowTitleForegroundColor() {
        return windowTitleForegroundColor;
    }

    /** Gets the window title background color.
	 *
	 * @return the window title background color
	 */
    public ColorUIResource getWindowTitleBackgroundColor() {
        return windowTitleBackgroundColor;
    }

    /** Gets the window title color light.
	 *
	 * @return the window title color light
	 */
    public ColorUIResource getWindowTitleColorLight() {
        return windowTitleColorLight;
    }

    /** Gets the window title color dark.
	 *
	 * @return the window title color dark
	 */
    public ColorUIResource getWindowTitleColorDark() {
        return windowTitleColorDark;
    }

    /** Gets the window border color.
	 *
	 * @return the window border color
	 */
    public ColorUIResource getWindowBorderColor() {
        return windowBorderColor;
    }

    /** Gets the window icon color.
	 *
	 * @return the window icon color
	 */
    public ColorUIResource getWindowIconColor() {
        return windowIconColor;
    }

    /** Gets the window icon shadow color.
	 *
	 * @return the window icon shadow color
	 */
    public ColorUIResource getWindowIconShadowColor() {
        return windowIconShadowColor;
    }

    /** Gets the window icon rollover color.
	 *
	 * @return the window icon rollover color
	 */
    public ColorUIResource getWindowIconRolloverColor() {
        return windowIconRolloverColor;
    }

    /** Gets the window inactive title foreground color.
	 *
	 * @return the window inactive title foreground color
	 */
    public ColorUIResource getWindowInactiveTitleForegroundColor() {
        return windowInactiveTitleForegroundColor;
    }

    /** Gets the window inactive title background color.
	 *
	 * @return the window inactive title background color
	 */
    public ColorUIResource getWindowInactiveTitleBackgroundColor() {
        return windowInactiveTitleBackgroundColor;
    }

    /** Gets the window inactive title color light.
	 *
	 * @return the window inactive title color light
	 */
    public ColorUIResource getWindowInactiveTitleColorLight() {
        return windowInactiveTitleColorLight;
    }

    /** Gets the window inactive title color dark.
	 *
	 * @return the window inactive title color dark
	 */
    public ColorUIResource getWindowInactiveTitleColorDark() {
        return windowInactiveTitleColorDark;
    }

    /** Gets the window inactive border color.
	 *
	 * @return the window inactive border color
	 */
    public ColorUIResource getWindowInactiveBorderColor() {
        return windowInactiveBorderColor;
    }

    /** Gets the menu foreground color.
	 *
	 * @return the menu foreground color
	 */
    public ColorUIResource getMenuForegroundColor() {
        return menuForegroundColor;
    }

    /** Gets the menu background color.
	 *
	 * @return the menu background color
	 */
    public ColorUIResource getMenuBackgroundColor() {
        return menuBackgroundColor;
    }

    /** Gets the menu selection foreground color.
	 *
	 * @return the menu selection foreground color
	 */
    public ColorUIResource getMenuSelectionForegroundColor() {
        return menuSelectionForegroundColor;
    }

    /** Gets the menu selection background color.
	 *
	 * @return the menu selection background color
	 */
    public ColorUIResource getMenuSelectionBackgroundColor() {
        return menuSelectionBackgroundColor;
    }

    /** Gets the menu selection background color light.
	 *
	 * @return the menu selection background color light
	 */
    public ColorUIResource getMenuSelectionBackgroundColorLight() {
        return menuSelectionBackgroundColorLight;
    }

    /** Gets the menu selection background color dark.
	 *
	 * @return the menu selection background color dark
	 */
    public ColorUIResource getMenuSelectionBackgroundColorDark() {
        return menuSelectionBackgroundColorDark;
    }

    /** Gets the menu color light.
	 *
	 * @return the menu color light
	 */
    public ColorUIResource getMenuColorLight() {
        return menuColorLight;
    }

    /** Gets the menu color dark.
	 *
	 * @return the menu color dark
	 */
    public ColorUIResource getMenuColorDark() {
        return menuColorDark;
    }

    /** Gets the toolbar foreground color.
	 *
	 * @return the toolbar foreground color
	 */
    public ColorUIResource getToolbarForegroundColor() {
        return toolbarForegroundColor;
    }

    /** Gets the toolbar background color.
	 *
	 * @return the toolbar background color
	 */
    public ColorUIResource getToolbarBackgroundColor() {
        return toolbarBackgroundColor;
    }

    /** Gets the toolbar color light.
	 *
	 * @return the toolbar color light
	 */
    public ColorUIResource getToolbarColorLight() {
        return toolbarColorLight;
    }

    /** Gets the toolbar color dark.
	 *
	 * @return the toolbar color dark
	 */
    public ColorUIResource getToolbarColorDark() {
        return toolbarColorDark;
    }

    /** Gets the tab area background color.
	 *
	 * @return the tab area background color
	 */
    public ColorUIResource getTabAreaBackgroundColor() {
        return tabAreaBackgroundColor;
    }

    /** Gets the tab selection foreground color.
	 *
	 * @return the tab selection foreground color
	 */
    public ColorUIResource getTabSelectionForegroundColor() {
        return tabSelectionForegroundColor;
    }

    public ColorUIResource getDesktopColor() {
        return desktopColor;
    }

    /** Gets the tooltip foreground color.
	 *
	 * @return the tooltip foreground color
	 */
    public ColorUIResource getTooltipForegroundColor() {
        return tooltipForegroundColor;
    }

    /** Gets the tooltip background color.
	 *
	 * @return the tooltip background color
	 */
    public ColorUIResource getTooltipBackgroundColor() {
        return tooltipBackgroundColor;
    }

    /** Gets the tooltip border size.
	 *
	 * @return the tooltip border size
	 */
    public int getTooltipBorderSize() {
        return Math.max(0, Math.min(8, tooltipBorderSize));
    }
    
    /** Gets the tooltip shadow size.
	 *
	 * @return the tooltip shadow size
	 */
    public int getTooltipShadowSize() {
        return Math.max(0, Math.min(8, tooltipShadowSize));
    }

    /** Checks if is tooltip cast shadow.
	 *
	 * @return true, if is tooltip cast shadow
	 */
    public boolean isTooltipCastShadow() {
        return tooltipCastShadow;
    }
    
    /** Gets the default colors.
	 *
	 * @return the default colors
	 */
    public Color[] getDefaultColors() {
        return DEFAULT_COLORS;
    }

    /** Gets the hi default colors.
	 *
	 * @return the hi default colors
	 */
    public Color[] getHiDefaultColors() {
        return HIDEFAULT_COLORS;
    }

    /** Gets the active colors.
	 *
	 * @return the active colors
	 */
    public Color[] getActiveColors() {
        return ACTIVE_COLORS;
    }

    /** Gets the in active colors.
	 *
	 * @return the in active colors
	 */
    public Color[] getInActiveColors() {
        return INACTIVE_COLORS;
    }

    /** Gets the rollover colors.
	 *
	 * @return the rollover colors
	 */
    public Color[] getRolloverColors() {
        return ROLLOVER_COLORS;
    }

    /** Gets the selected colors.
	 *
	 * @return the selected colors
	 */
    public Color[] getSelectedColors() {
        return SELECTED_COLORS;
    }

    /** Gets the selection colors.
	 *
	 * @return the selection colors
	 */
    public Color[] getSelectionColors() {
        return SELECTION_COLORS;
    }

    /** Gets the focus colors.
	 *
	 * @return the focus colors
	 */
    public Color[] getFocusColors() {
        return FOCUS_COLORS;
    }

    /** Gets the menu selection colors.
	 *
	 * @return the menu selection colors
	 */
    public Color[] getMenuSelectionColors() {
        return MENU_SELECTION_COLORS;
    }

    /** Gets the pressed colors.
	 *
	 * @return the pressed colors
	 */
    public Color[] getPressedColors() {
        return PRESSED_COLORS;
    }

    /** Gets the disabled colors.
	 *
	 * @return the disabled colors
	 */
    public Color[] getDisabledColors() {
        return DISABLED_COLORS;
    }

    /** Gets the window title colors.
	 *
	 * @return the window title colors
	 */
    public Color[] getWindowTitleColors() {
        return WINDOW_TITLE_COLORS;
    }

    /** Gets the window inactive title colors.
	 *
	 * @return the window inactive title colors
	 */
    public Color[] getWindowInactiveTitleColors() {
        return WINDOW_INACTIVE_TITLE_COLORS;
    }

    /** Gets the tool bar colors.
	 *
	 * @return the tool bar colors
	 */
    public Color[] getToolBarColors() {
        return TOOLBAR_COLORS;
    }

    /** Gets the menu bar colors.
	 *
	 * @return the menu bar colors
	 */
    public Color[] getMenuBarColors() {
        return MENUBAR_COLORS;
    }

    /** Gets the button colors.
	 *
	 * @return the button colors
	 */
    public Color[] getButtonColors() {
        return BUTTON_COLORS;
    }

    /** Gets the check box colors.
	 *
	 * @return the check box colors
	 */
    public Color[] getCheckBoxColors() {
        return CHECKBOX_COLORS;
    }

    /** Gets the tab colors.
	 *
	 * @return the tab colors
	 */
    public Color[] getTabColors() {
        return TAB_COLORS;
    }

    /** Gets the col header colors.
	 *
	 * @return the col header colors
	 */
    public Color[] getColHeaderColors() {
        return COL_HEADER_COLORS;
    }

    /** Gets the track colors.
	 *
	 * @return the track colors
	 */
    public Color[] getTrackColors() {
        return TRACK_COLORS;
    }

    /** Gets the thumb colors.
	 *
	 * @return the thumb colors
	 */
    public Color[] getThumbColors() {
        return THUMB_COLORS;
    }

    /** Gets the slider colors.
	 *
	 * @return the slider colors
	 */
    public Color[] getSliderColors() {
        return SLIDER_COLORS;
    }

    /** Gets the progress bar colors.
	 *
	 * @return the progress bar colors
	 */
    public Color[] getProgressBarColors() {
        return PROGRESSBAR_COLORS;
    }

    /** Gets the texture set.
	 *
	 * @return the texture set
	 */
    public String getTextureSet() {
        return textureSet;
    }

    /** Checks if is dark texture.
	 *
	 * @return true, if is dark texture
	 */
    public boolean isDarkTexture() {
        return darkTexture;
    }

    /** Gets the window texture.
	 *
	 * @return the window texture
	 */
    public Icon getWindowTexture() {
        return windowTexture;
    }

    /** Gets the background texture.
	 *
	 * @return the background texture
	 */
    public Icon getBackgroundTexture() {
        return backgroundTexture;
    }

    /** Gets the alter background texture.
	 *
	 * @return the alter background texture
	 */
    public Icon getAlterBackgroundTexture() {
        return alterBackgroundTexture;
    }

    /** Gets the selected texture.
	 *
	 * @return the selected texture
	 */
    public Icon getSelectedTexture() {
        return selectedTexture;
    }

    /** Gets the rollover texture.
	 *
	 * @return the rollover texture
	 */
    public Icon getRolloverTexture() {
        return rolloverTexture;
    }

    /** Gets the pressed texture.
	 *
	 * @return the pressed texture
	 */
    public Icon getPressedTexture() {
        return pressedTexture;
    }

    /** Gets the disabled texture.
	 *
	 * @return the disabled texture
	 */
    public Icon getDisabledTexture() {
        return disabledTexture;
    }

    /** Gets the menubar texture.
	 *
	 * @return the menubar texture
	 */
    public Icon getMenubarTexture() {
        return menubarTexture;
    }
}
