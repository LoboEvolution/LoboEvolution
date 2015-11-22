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
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

/**
 * The Class AbstractLookAndFeel.
 *
 * @author Michael Hagen
 */
abstract public class AbstractLookAndFeel extends MetalLookAndFeel {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	// Workaround to avoid a bug in the java 1.3 VM
    static {
        try {
            if (JTattooUtilities.getJavaVersion() < 1.4) {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            }
        } catch (Exception ex) {
        }
    }

    /** The current theme name. */
    protected static String currentThemeName = "abstractTheme";

    /** The my theme. */
    private static AbstractTheme myTheme = null;

    /** Gets the border factory.
	 *
	 * @return the border factory
	 */
    abstract public AbstractBorderFactory getBorderFactory();

    /** Gets the icon factory.
	 *
	 * @return the icon factory
	 */
    abstract public AbstractIconFactory getIconFactory();

    protected void initSystemColorDefaults(UIDefaults table) {
        Object[] systemColors = {
            "desktop", getDesktopColor(), // Color of the desktop background

            "activeCaption", getWindowTitleBackgroundColor(), // Color for captions (title bars) when they are active.
            "activeCaptionLight", getWindowTitleColorLight(),
            "activeCaptionDark", getWindowTitleColorDark(),
            "activeCaptionText", getWindowTitleForegroundColor(), // Text color for text in captions (title bars).
            "activeCaptionBorder", getWindowBorderColor(), // Border color for caption (title bar) window borders.

            "inactiveCaption", getWindowInactiveTitleBackgroundColor(), // Color for captions (title bars) when not active.
            "inactiveCaptionLight", getWindowInactiveTitleColorLight(), //
            "inactiveCaptionDark", getWindowInactiveTitleColorDark(), //
            "inactiveCaptionText", getWindowInactiveTitleForegroundColor(), // Text color for text in inactive captions (title bars).
            "inactiveCaptionBorder", getWindowInactiveBorderColor(), // Border color for inactive caption (title bar) window borders.

            "window", getInputBackgroundColor(), // Default color for the interior of windows, list, tree etc
            "windowBorder", getBackgroundColor(), // ???
            "windowText", getControlForegroundColor(), // ???

            "menu", getMenuBackgroundColor(), // Background color for menus
            "menuText", getMenuForegroundColor(), // Text color for menus
            "MenuBar.rolloverEnabled", Boolean.TRUE,
            "text", getBackgroundColor(), // Text background color
            "textText", getControlForegroundColor(), // Text foreground color
            "textHighlight", getSelectionBackgroundColor(), // Text background color when selected
            "textHighlightText", getSelectionForegroundColor(), // Text color when selected
            "textInactiveText", getDisabledForegroundColor(), // Text color when disabled

            "control", getControlBackgroundColor(), // Default color for controls (buttons, sliders, etc)
            "controlText", getControlForegroundColor(), // Default color for text in controls
            "controlHighlight", getControlHighlightColor(), // Specular highlight (opposite of the shadow)
            "controlLtHighlight", getControlHighlightColor(), // Highlight color for controls
            "controlShadow", getControlShadowColor(), // Shadow color for controls
            "controlDkShadow", getControlDarkShadowColor(), // Dark shadow color for controls

            "scrollbar", getControlBackgroundColor(), // Scrollbar background (usually the "track")
            "info", getTooltipBackgroundColor(), // ToolTip Background
            "infoText", getTooltipForegroundColor() // ToolTip Text
        };

        for (int i = 0; i < systemColors.length; i += 2) {
            table.put((String) systemColors[i], systemColors[i + 1]);
        }
    }

    protected void initComponentDefaults(UIDefaults table) {
        super.initComponentDefaults(table);

        BaseBorders.initDefaults();
        BaseIcons.initDefaults();

        Object textFieldBorder = getBorderFactory().getTextFieldBorder();
        Object comboBoxBorder = getBorderFactory().getComboBoxBorder();
        Object scrollPaneBorder = getBorderFactory().getScrollPaneBorder();
        Object tableScrollPaneBorder = getBorderFactory().getTableScrollPaneBorder();
        Object tabbedPaneBorder = getBorderFactory().getTabbedPaneBorder();
        Object buttonBorder = getBorderFactory().getButtonBorder();
        Object toggleButtonBorder = getBorderFactory().getToggleButtonBorder();
        Object titledBorderBorder = new UIDefaults.ProxyLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[]{getFrameColor()});
        Object menuBarBorder = getBorderFactory().getMenuBarBorder();
        Object popupMenuBorder = getBorderFactory().getPopupMenuBorder();
        Object menuItemBorder = getBorderFactory().getMenuItemBorder();
        Object toolBarBorder = getBorderFactory().getToolBarBorder();
        Object progressBarBorder = getBorderFactory().getProgressBarBorder();
        Object toolTipBorder = new UIDefaults.ProxyLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[]{getFrameColor()});
        Object focusCellHighlightBorder = new UIDefaults.ProxyLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[]{getFocusCellColor()});
        Object optionPaneBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        Object optionPaneMessageAreaBorder = BorderFactory.createEmptyBorder(8, 8, 8, 8);
        Object optionPaneButtonAreaBorder = BorderFactory.createEmptyBorder(0, 8, 8, 8);
        Object windowBorder = getBorderFactory().getInternalFrameBorder();

        Color c = getBackgroundColor();
        ColorUIResource progressBarBackground = new ColorUIResource(ColorHelper.brighter(c, 20));

        // DEFAULTS TABLE
        Object[] defaults = {
            "controlTextFont", getControlTextFont(),
            "systemTextFont ", getSystemTextFont(),
            "userTextFont", getUserTextFont(),
            "menuTextFont", getMenuTextFont(),
            "windowTitleFont", getWindowTitleFont(),
            "subTextFont", getSubTextFont(),
            "Label.font", getUserTextFont(),
            "Label.background", getBackgroundColor(),
            "Label.foreground", getForegroundColor(),
            "Label.disabledText", getDisabledForegroundColor(),
            "Label.disabledShadow", getWhite(),
            // Text (Note: many are inherited)
            "TextField.border", textFieldBorder,
            "TextField.foreground", getInputForegroundColor(),
            "TextField.background", getInputBackgroundColor(),
            "TextField.disabledForeground", getDisabledForegroundColor(),
            "TextField.disabledBackground", getDisabledBackgroundColor(),
            "TextField.inactiveForeground", getDisabledForegroundColor(),
            "TextField.inactiveBackground", getDisabledBackgroundColor(),
            "TextArea.foreground", getInputForegroundColor(),
            "TextArea.background", getInputBackgroundColor(),
            "TextArea.disabledForeground", getDisabledForegroundColor(),
            "TextArea.disabledBackground", getDisabledBackgroundColor(),
            "TextArea.inactiveForeground", getDisabledForegroundColor(),
            "TextArea.inactiveBackground", getDisabledBackgroundColor(),
            "EditorPane.foreground", getInputForegroundColor(),
            "EditorPane.background", getInputBackgroundColor(),
            "EditorPane.disabledForeground", getDisabledForegroundColor(),
            "EditorPane.disabledBackground", getDisabledBackgroundColor(),
            "EditorPane.inactiveForeground", getDisabledForegroundColor(),
            "EditorPane.inactiveBackground", getDisabledBackgroundColor(),
            "FormattedTextField.border", textFieldBorder,
            "FormattedTextField.foreground", getInputForegroundColor(),
            "FormattedTextField.background", getInputBackgroundColor(),
            "FormattedTextField.disabledForeground", getDisabledForegroundColor(),
            "FormattedTextField.disabledBackground", getDisabledBackgroundColor(),
            "FormattedTextField.inactiveForeground", getDisabledForegroundColor(),
            "FormattedTextField.inactiveBackground", getDisabledBackgroundColor(),
            "PasswordField.border", textFieldBorder,
            "PasswordField.foreground", getInputForegroundColor(),
            "PasswordField.background", getInputBackgroundColor(),
            "PasswordField.disabledForeground", getDisabledForegroundColor(),
            "PasswordField.disabledBackground", getDisabledBackgroundColor(),
            "PasswordField.inactiveForeground", getDisabledForegroundColor(),
            "PasswordField.inactiveBackground", getDisabledBackgroundColor(),
            // Buttons
            "Button.background", getButtonBackgroundColor(),
            "Button.foreground", getButtonForegroundColor(),
            "Button.disabledText", getDisabledForegroundColor(),
            "Button.disabledShadow", getWhite(),
            "Button.select", getSelectionBackgroundColor(),
            "Button.border", buttonBorder,
            "Button.frame", getFrameColor(),
            "Button.focus", getFocusColor(),
            "Button.rolloverColor", getTheme().getRolloverColor(),
            "Button.rolloverForeground", getTheme().getRolloverForegroundColor(),
            "CheckBox.font", getUserTextFont(),
            "CheckBox.background", getBackgroundColor(),
            "CheckBox.foreground", getForegroundColor(),
            "CheckBox.disabledText", getDisabledForegroundColor(),
            "CheckBox.disabledShadow", getWhite(),
            "Checkbox.select", getSelectionBackgroundColor(),
            "CheckBox.focus", getFocusColor(),
            "CheckBox.icon", getIconFactory().getCheckBoxIcon(),
            "RadioButton.font", getUserTextFont(),
            "RadioButton.background", getBackgroundColor(),
            "RadioButton.foreground", getForegroundColor(),
            "RadioButton.disabledText", getDisabledForegroundColor(),
            "RadioButton.disabledShadow", getWhite(),
            "RadioButton.select", getSelectionBackgroundColor(),
            "RadioButton.icon", getIconFactory().getRadioButtonIcon(),
            "RadioButton.focus", getFocusColor(),
            "ToggleButton.background", getButtonBackgroundColor(),
            "ToggleButton.foreground", getButtonForegroundColor(),
            "ToggleButton.select", getSelectionBackgroundColor(),
            "ToggleButton.text", getButtonForegroundColor(),
            "ToggleButton.disabledText", getDisabledForegroundColor(),
            "ToggleButton.disabledShadow", getWhite(),
            "ToggleButton.disabledSelectedText", getDisabledForegroundColor(),
            "ToggleButton.disabledBackground", getButtonBackgroundColor(),
            "ToggleButton.disabledSelectedBackground", getSelectionBackgroundColor(),
            "ToggleButton.focus", getFocusColor(),
            "ToggleButton.border", toggleButtonBorder,
            // ToolTip
            "ToolTip.border", toolTipBorder,
            "ToolTip.foreground", getTooltipForegroundColor(),
            "ToolTip.background", getTooltipBackgroundColor(),
            // Slider
            "Slider.border", null,
            "Slider.foreground", getFrameColor(),
            "Slider.background", getBackgroundColor(),
            "Slider.focus", getFocusColor(),
            "Slider.focusInsets", new InsetsUIResource(0, 0, 0, 0),
            "Slider.trackWidth", new Integer(7),
            "Slider.majorTickLength", new Integer(6),
            // Progress Bar
            "ProgressBar.border", progressBarBorder,
            "ProgressBar.background", progressBarBackground,
            "ProgressBar.selectionForeground", getSelectionForegroundColor(),
            "ProgressBar.selectionBackground", getForegroundColor(),
            // Combo Box
            "ComboBox.border", comboBoxBorder,
            "ComboBox.background", getInputBackgroundColor(),
            "ComboBox.foreground", getInputForegroundColor(),
            "ComboBox.selectionBackground", getSelectionBackgroundColor(),
            "ComboBox.selectionForeground", getSelectionForegroundColor(),
            "ComboBox.selectionBorderColor", getFocusColor(),
            "ComboBox.disabledBackground", getDisabledBackgroundColor(),
            "ComboBox.disabledForeground", getDisabledForegroundColor(),
            "ComboBox.listBackground", getInputBackgroundColor(),
            "ComboBox.listForeground", getInputForegroundColor(),
            "ComboBox.font", getUserTextFont(),
            // Panel
            "Panel.foreground", getForegroundColor(),
            "Panel.background", getBackgroundColor(),
            "Panel.darkBackground", getTheme().getBackgroundColorDark(),
            "Panel.lightBackground", getTheme().getBackgroundColorLight(),
            "Panel.alterBackground", getTheme().getAlterBackgroundColor(),
            "Panel.font", getUserTextFont(),
            // RootPane
            "RootPane.frameBorder", windowBorder,
            "RootPane.plainDialogBorder", windowBorder,
            "RootPane.informationDialogBorder", windowBorder,
            "RootPane.errorDialogBorder", windowBorder,
            "RootPane.colorChooserDialogBorder", windowBorder,
            "RootPane.fileChooserDialogBorder", windowBorder,
            "RootPane.questionDialogBorder", windowBorder,
            "RootPane.warningDialogBorder", windowBorder,
            // InternalFrame
            "InternalFrame.border", getBorderFactory().getInternalFrameBorder(),
            "InternalFrame.font", getWindowTitleFont(),
            "InternalFrame.paletteBorder", getBorderFactory().getPaletteBorder(),
            "InternalFrame.paletteTitleHeight", new Integer(11),
            "InternalFrame.paletteCloseIcon", getIconFactory().getPaletteCloseIcon(),
            "InternalFrame.icon", getIconFactory().getMenuIcon(),
            "InternalFrame.iconifyIcon", getIconFactory().getIconIcon(),
            "InternalFrame.maximizeIcon", getIconFactory().getMaxIcon(),
            "InternalFrame.altMaximizeIcon", getIconFactory().getMinIcon(),
            "InternalFrame.minimizeIcon", getIconFactory().getMinIcon(),
            "InternalFrame.closeIcon", getIconFactory().getCloseIcon(),
            // Titled Border
            "TitledBorder.titleColor", getForegroundColor(),
            "TitledBorder.border", titledBorderBorder,
            // List
            "List.focusCellHighlightBorder", focusCellHighlightBorder,
            "List.font", getUserTextFont(),
            "List.foreground", getInputForegroundColor(),
            "List.background", getInputBackgroundColor(),
            "List.selectionForeground", getSelectionForegroundColor(),
            "List.selectionBackground", getSelectionBackgroundColor(),
            "List.disabledForeground", getDisabledForegroundColor(),
            "List.disabledBackground", getDisabledBackgroundColor(),
            // ScrollBar
            "ScrollBar.background", getControlBackgroundColor(),
            "ScrollBar.highlight", getControlHighlightColor(),
            "ScrollBar.shadow", getControlShadowColor(),
            "ScrollBar.darkShadow", getControlDarkShadowColor(),
            "ScrollBar.thumb", getControlBackgroundColor(),
            "ScrollBar.thumbShadow", getControlShadowColor(),
            "ScrollBar.thumbHighlight", getControlHighlightColor(),
            "ScrollBar.width", new Integer(17),
            "ScrollBar.allowsAbsolutePositioning", Boolean.TRUE,
            // ScrollPane
            "ScrollPane.border", scrollPaneBorder,
            "ScrollPane.foreground", getForegroundColor(),
            "ScrollPane.background", getBackgroundColor(),
            // Viewport
            "Viewport.foreground", getForegroundColor(),
            "Viewport.background", getBackgroundColor(),
            "Viewport.font", getUserTextFont(),
            // Tabbed Pane
            "TabbedPane.boder", tabbedPaneBorder,
            "TabbedPane.background", getBackgroundColor(),
            "TabbedPane.tabAreaBackground", getTabAreaBackgroundColor(),
            "TabbedPane.unselectedBackground", getControlColorDark(),
            "TabbedPane.foreground", getControlForegroundColor(),
            "TabbedPane.selected", getBackgroundColor(),
            "TabbedPane.selectedForeground", getTabSelectionForegroundColor(),
            "TabbedPane.tabAreaInsets", new InsetsUIResource(5, 5, 5, 5),
            "TabbedPane.contentBorderInsets", new InsetsUIResource(0, 0, 0, 0),
            "TabbedPane.tabInsets", new InsetsUIResource(1, 6, 1, 6),
            "TabbedPane.focus", getFocusColor(),
            // TabbedPane ScrollButton
            "TabbedPane.selected", getButtonBackgroundColor(),
            "TabbedPane.shadow", new ColorUIResource(180, 180, 180),
            "TabbedPane.darkShadow", new ColorUIResource(120, 120, 120),
            "TabbedPane.highlight", new ColorUIResource(Color.white),
            // Tab Colors in Netbeans
            "tab_unsel_fill", getControlBackgroundColor(),
            "tab_sel_fill", getControlBackgroundColor(),
            // Table
            "Table.focusCellHighlightBorder", focusCellHighlightBorder,
            "Table.scrollPaneBorder", tableScrollPaneBorder,
            "Table.foreground", getInputForegroundColor(),
            "Table.background", getInputBackgroundColor(),
            "Table.gridColor", getGridColor(),
            "TableHeader.foreground", getControlForegroundColor(),
            "TableHeader.background", getBackgroundColor(),
            "TableHeader.cellBorder", getBorderFactory().getTableHeaderBorder(),
            // MenuBar
            "MenuBar.border", menuBarBorder,
            "MenuBar.foreground", getMenuForegroundColor(),
            "MenuBar.background", getMenuBackgroundColor(),
            // Menu
            "Menu.border", menuItemBorder,
            "Menu.borderPainted", Boolean.TRUE,
            "Menu.foreground", getMenuForegroundColor(),
            "Menu.background", getMenuBackgroundColor(),
            "Menu.selectionForeground", getMenuSelectionForegroundColor(),
            "Menu.selectionBackground", getMenuSelectionBackgroundColor(),
            "Menu.disabledForeground", getDisabledForegroundColor(),
            "Menu.acceleratorForeground", getMenuForegroundColor(),
            "Menu.acceleratorSelectionForeground", getMenuSelectionForegroundColor(),
            "Menu.arrowIcon", getIconFactory().getMenuArrowIcon(),
            // Popup Menu
            "PopupMenu.background", getMenuBackgroundColor(),
            "PopupMenu.border", popupMenuBorder,
            // Menu Item
            "MenuItem.border", menuItemBorder,
            "MenuItem.borderPainted", Boolean.TRUE,
            "MenuItem.foreground", getMenuForegroundColor(),
            "MenuItem.background", getMenuBackgroundColor(),
            "MenuItem.selectionForeground", getMenuSelectionForegroundColor(),
            "MenuItem.selectionBackground", getMenuSelectionBackgroundColor(),
            "MenuItem.disabledForeground", getDisabledForegroundColor(),
            "MenuItem.disabledShadow", getWhite(),
            "MenuItem.acceleratorForeground", getMenuForegroundColor(),
            "MenuItem.acceleratorSelectionForeground", getMenuSelectionForegroundColor(),
            "CheckBoxMenuItem.border", menuItemBorder,
            "CheckBoxMenuItem.borderPainted", Boolean.TRUE,
            "CheckBoxMenuItem.foreground", getMenuForegroundColor(),
            "CheckBoxMenuItem.background", getMenuBackgroundColor(),
            "CheckBoxMenuItem.selectionForeground", getMenuSelectionForegroundColor(),
            "CheckBoxMenuItem.selectionBackground", getMenuSelectionBackgroundColor(),
            "CheckBoxMenuItem.disabledForeground", getDisabledForegroundColor(),
            "CheckBoxMenuItem.disabledShadow", getWhite(),
            "CheckBoxMenuItem.acceleratorForeground", getMenuForegroundColor(),
            "CheckBoxMenuItem.acceleratorSelectionForeground", getMenuSelectionForegroundColor(),
            "CheckBoxMenuItem.checkIcon", getIconFactory().getMenuCheckBoxIcon(),
            "RadioButtonMenuItem.border", menuItemBorder,
            "RadioButtonMenuItem.borderPainted", Boolean.TRUE,
            "RadioButtonMenuItem.foreground", getMenuForegroundColor(),
            "RadioButtonMenuItem.background", getMenuBackgroundColor(),
            "RadioButtonMenuItem.selectionForeground", getMenuSelectionForegroundColor(),
            "RadioButtonMenuItem.selectionBackground", getMenuSelectionBackgroundColor(),
            "RadioButtonMenuItem.disabledForeground", getDisabledForegroundColor(),
            "RadioButtonMenuItem.disabledShadow", getWhite(),
            "RadioButtonMenuItem.acceleratorForeground", getMenuForegroundColor(),
            "RadioButtonMenuItem.acceleratorSelectionForeground", getMenuSelectionForegroundColor(),
            "RadioButtonMenuItem.checkIcon", getIconFactory().getMenuRadioButtonIcon(),
            // OptionPane.
            "OptionPane.errorIcon", getIconFactory().getOptionPaneErrorIcon(),
            "OptionPane.informationIcon", getIconFactory().getOptionPaneInformationIcon(),
            "OptionPane.warningIcon", getIconFactory().getOptionPaneWarningIcon(),
            "OptionPane.questionIcon", getIconFactory().getOptionPaneQuestionIcon(),
            "OptionPane.border", optionPaneBorder,
            "OptionPane.messageAreaBorder", optionPaneMessageAreaBorder,
            "OptionPane.buttonAreaBorder", optionPaneButtonAreaBorder,
            // File View
            "FileView.directoryIcon", getIconFactory().getTreeOpenIcon(),
            "FileView.fileIcon", getIconFactory().getTreeLeafIcon(),
            "FileView.computerIcon", getIconFactory().getFileViewComputerIcon(),
            "FileView.hardDriveIcon", getIconFactory().getFileViewHardDriveIcon(),
            "FileView.floppyDriveIcon", getIconFactory().getFileViewFloppyDriveIcon(),
            // File Chooser
            "FileChooser.upFolderIcon", getIconFactory().getFileChooserUpFolderIcon(),
            "FileChooser.homeFolderIcon", getIconFactory().getFileChooserHomeFolderIcon(),
            "FileChooser.newFolderIcon", getIconFactory().getFileChooserNewFolderIcon(),
            "FileChooser.listViewIcon", getIconFactory().getFileChooserListViewIcon(),
            "FileChooser.detailsViewIcon", getIconFactory().getFileChooserDetailViewIcon(),
            "FileChooser.viewMenuIcon", getIconFactory().getFileChooserDetailViewIcon(),
            // Separator
            "Separator.background", getBackgroundColor(),
            "Separator.foreground", getControlForegroundColor(),
            // SplitPane
            "SplitPane.centerOneTouchButtons", Boolean.TRUE,
            "SplitPane.dividerSize", new Integer(7),
            "SplitPane.border", BorderFactory.createEmptyBorder(),
            // Tree
            "Tree.background", getInputBackgroundColor(),
            "Tree.foreground", getInputForegroundColor(),
            "Tree.textForeground", getInputForegroundColor(),
            "Tree.textBackground", getInputBackgroundColor(),
            "Tree.selectionForeground", getSelectionForegroundColor(),
            "Tree.selectionBackground", getSelectionBackgroundColor(),
            "Tree.disabledForeground", getDisabledForegroundColor(),
            "Tree.disabledBackground", getDisabledBackgroundColor(),
            "Tree.openIcon", getIconFactory().getTreeOpenIcon(),
            "Tree.closedIcon", getIconFactory().getTreeCloseIcon(),
            "Tree.leafIcon", getIconFactory().getTreeLeafIcon(),
            "Tree.expandedIcon", getIconFactory().getTreeExpandedIcon(),
            "Tree.collapsedIcon", getIconFactory().getTreeCollapsedIcon(),
            "Tree.selectionBorderColor", getFocusCellColor(),
            "Tree.line", getFrameColor(), // horiz lines
            "Tree.hash", getFrameColor(), // legs

            // ToolBar
            "JToolBar.isRollover", Boolean.TRUE,
            "ToolBar.border", toolBarBorder,
            "ToolBar.background", getToolbarBackgroundColor(),
            "ToolBar.foreground", getToolbarForegroundColor(),
            "ToolBar.dockingBackground", getToolbarBackgroundColor(),
            "ToolBar.dockingForeground", getToolbarDockingColor(),
            "ToolBar.floatingBackground", getToolbarBackgroundColor(),
            "ToolBar.floatingForeground", getToolbarForegroundColor(),};
        table.putDefaults(defaults);

        if (JTattooUtilities.getJavaVersion() >= 1.5) {
            table.put("Spinner.font", getControlTextFont());
            table.put("Spinner.background", getButtonBackgroundColor());
            table.put("Spinner.foreground", getButtonForegroundColor());
            table.put("Spinner.border", getBorderFactory().getSpinnerBorder());
            table.put("Spinner.arrowButtonInsets", null);
            table.put("Spinner.arrowButtonBorder", BorderFactory.createEmptyBorder());
            table.put("Spinner.editorBorderPainted", Boolean.FALSE);
        }
        if (getTheme().isMacStyleScrollBarOn()) {
            if (getTheme().isSmallFontSize()) {
                table.put("ScrollBar.width", new Integer(8));
                table.put("SplitPane.dividerSize", new Integer(7));
            } else if (getTheme().isMediumFontSize()) {
                table.put("ScrollBar.width", new Integer(10));
                table.put("SplitPane.dividerSize", new Integer(9));
            } else {
                table.put("ScrollBar.width", new Integer(12));
                table.put("SplitPane.dividerSize", new Integer(11));
            }
        } else {
            if (getTheme().isSmallFontSize()) {
                table.put("ScrollBar.width", new Integer(17));
                table.put("SplitPane.dividerSize", new Integer(7));
            } else if (getTheme().isMediumFontSize()) {
                table.put("ScrollBar.width", new Integer(19));
                table.put("SplitPane.dividerSize", new Integer(9));
            } else {
                table.put("ScrollBar.width", new Integer(21));
                table.put("SplitPane.dividerSize", new Integer(11));
            }
        }
    }

    /** Sets the theme.
	 *
	 * @param theme
	 *            the new theme
	 */
    public static void setTheme(AbstractTheme theme) {
        if (theme == null) {
            return;
        }

        MetalLookAndFeel.setCurrentTheme(theme);
        myTheme = theme;
        if (isWindowDecorationOn()) {
            DecorationHelper.decorateWindows(Boolean.TRUE);
        } else {
            DecorationHelper.decorateWindows(Boolean.FALSE);
        }
    }

    /** Sets the theme.
	 *
	 * @param name
	 *            the new theme
	 */
    public static void setTheme(String name) {
        // Overwrite this in derived classes
    }

    /** Gets the theme.
	 *
	 * @return the theme
	 */
    public static AbstractTheme getTheme() {
        return myTheme;
    }

    /** Gets the current theme.
	 *
	 * @return the current theme
	 */
    public static MetalTheme getCurrentTheme() {
        return myTheme;
    }

    /** Gets the themes.
	 *
	 * @return the themes
	 */
    public static List<String> getThemes() {
        ArrayList<String> themes = new ArrayList<String>();
        themes.add(getTheme().getName());
        return themes;
    }

    /** Checks if is window decoration on.
	 *
	 * @return true, if is window decoration on
	 */
    public static boolean isWindowDecorationOn() {
        return getTheme().isWindowDecorationOn();
    }

    /** Gets the foreground color.
	 *
	 * @return the foreground color
	 */
    public static ColorUIResource getForegroundColor() {
        return getTheme().getForegroundColor();
    }

    /** Gets the disabled foreground color.
	 *
	 * @return the disabled foreground color
	 */
    public static ColorUIResource getDisabledForegroundColor() {
        return getTheme().getDisabledForegroundColor();
    }

    /** Gets the background color.
	 *
	 * @return the background color
	 */
    public static ColorUIResource getBackgroundColor() {
        return getTheme().getBackgroundColor();
    }

    /** Gets the alter background color.
	 *
	 * @return the alter background color
	 */
    public static ColorUIResource getAlterBackgroundColor() {
        return getTheme().getAlterBackgroundColor();
    }

    /** Gets the disabled background color.
	 *
	 * @return the disabled background color
	 */
    public static ColorUIResource getDisabledBackgroundColor() {
        return getTheme().getDisabledBackgroundColor();
    }

    /** Gets the input foreground color.
	 *
	 * @return the input foreground color
	 */
    public static ColorUIResource getInputForegroundColor() {
        return getTheme().getInputForegroundColor();
    }

    /** Gets the input background color.
	 *
	 * @return the input background color
	 */
    public static ColorUIResource getInputBackgroundColor() {
        return getTheme().getInputBackgroundColor();
    }

    /** Gets the focus color.
	 *
	 * @return the focus color
	 */
    public static ColorUIResource getFocusColor() {
        return getTheme().getFocusColor();
    }

    /** Gets the focus cell color.
	 *
	 * @return the focus cell color
	 */
    public static ColorUIResource getFocusCellColor() {
        return getTheme().getFocusCellColor();
    }

    /** Gets the frame color.
	 *
	 * @return the frame color
	 */
    public static ColorUIResource getFrameColor() {
        return getTheme().getFrameColor();
    }

    /** Gets the grid color.
	 *
	 * @return the grid color
	 */
    public static ColorUIResource getGridColor() {
        return getTheme().getGridColor();
    }

    /** Gets the selection foreground color.
	 *
	 * @return the selection foreground color
	 */
    public static ColorUIResource getSelectionForegroundColor() {
        return getTheme().getSelectionForegroundColor();
    }

    /** Gets the selection background color.
	 *
	 * @return the selection background color
	 */
    public static ColorUIResource getSelectionBackgroundColor() {
        return getTheme().getSelectionBackgroundColor();
    }

    /** Gets the button foreground color.
	 *
	 * @return the button foreground color
	 */
    public static ColorUIResource getButtonForegroundColor() {
        return getTheme().getButtonForegroundColor();
    }

    /** Gets the button background color.
	 *
	 * @return the button background color
	 */
    public static ColorUIResource getButtonBackgroundColor() {
        return getTheme().getButtonBackgroundColor();
    }

    /** Gets the button color light.
	 *
	 * @return the button color light
	 */
    public static ColorUIResource getButtonColorLight() {
        return getTheme().getButtonColorLight();
    }

    /** Gets the button color dark.
	 *
	 * @return the button color dark
	 */
    public static ColorUIResource getButtonColorDark() {
        return getTheme().getButtonColorDark();
    }

    /** Gets the control foreground color.
	 *
	 * @return the control foreground color
	 */
    public static ColorUIResource getControlForegroundColor() {
        return getTheme().getControlForegroundColor();
    }

    /** Gets the control background color.
	 *
	 * @return the control background color
	 */
    public static ColorUIResource getControlBackgroundColor() {
        return getTheme().getControlBackgroundColor();
    }

    /** Gets the control highlight color.
	 *
	 * @return the control highlight color
	 */
    public ColorUIResource getControlHighlightColor() {
        return getTheme().getControlHighlightColor();
    }

    /** Gets the control shadow color.
	 *
	 * @return the control shadow color
	 */
    public ColorUIResource getControlShadowColor() {
        return getTheme().getControlShadowColor();
    }

    /** Gets the control dark shadow color.
	 *
	 * @return the control dark shadow color
	 */
    public ColorUIResource getControlDarkShadowColor() {
        return getTheme().getControlDarkShadowColor();
    }

    /** Gets the control color light.
	 *
	 * @return the control color light
	 */
    public static ColorUIResource getControlColorLight() {
        return getTheme().getControlColorLight();
    }

    /** Gets the control color dark.
	 *
	 * @return the control color dark
	 */
    public static ColorUIResource getControlColorDark() {
        return getTheme().getControlColorDark();
    }

    /** Gets the window title foreground color.
	 *
	 * @return the window title foreground color
	 */
    public static ColorUIResource getWindowTitleForegroundColor() {
        return getTheme().getWindowTitleForegroundColor();
    }

    /** Gets the window title background color.
	 *
	 * @return the window title background color
	 */
    public static ColorUIResource getWindowTitleBackgroundColor() {
        return getTheme().getWindowTitleBackgroundColor();
    }

    /** Gets the window title color light.
	 *
	 * @return the window title color light
	 */
    public static ColorUIResource getWindowTitleColorLight() {
        return getTheme().getWindowTitleColorLight();
    }

    /** Gets the window title color dark.
	 *
	 * @return the window title color dark
	 */
    public static ColorUIResource getWindowTitleColorDark() {
        return getTheme().getWindowTitleColorDark();
    }

    /** Gets the window border color.
	 *
	 * @return the window border color
	 */
    public static ColorUIResource getWindowBorderColor() {
        return getTheme().getWindowBorderColor();
    }

    /** Gets the window inactive title foreground color.
	 *
	 * @return the window inactive title foreground color
	 */
    public static ColorUIResource getWindowInactiveTitleForegroundColor() {
        return getTheme().getWindowInactiveTitleForegroundColor();
    }

    /** Gets the window inactive title background color.
	 *
	 * @return the window inactive title background color
	 */
    public static ColorUIResource getWindowInactiveTitleBackgroundColor() {
        return getTheme().getWindowInactiveTitleBackgroundColor();
    }

    /** Gets the window inactive title color light.
	 *
	 * @return the window inactive title color light
	 */
    public static ColorUIResource getWindowInactiveTitleColorLight() {
        return getTheme().getWindowInactiveTitleColorLight();
    }

    /** Gets the window inactive title color dark.
	 *
	 * @return the window inactive title color dark
	 */
    public static ColorUIResource getWindowInactiveTitleColorDark() {
        return getTheme().getWindowInactiveTitleColorDark();
    }

    /** Gets the window inactive border color.
	 *
	 * @return the window inactive border color
	 */
    public static ColorUIResource getWindowInactiveBorderColor() {
        return getTheme().getWindowInactiveBorderColor();
    }

    /** Gets the menu foreground color.
	 *
	 * @return the menu foreground color
	 */
    public static ColorUIResource getMenuForegroundColor() {
        return getTheme().getMenuForegroundColor();
    }

    /** Gets the menu background color.
	 *
	 * @return the menu background color
	 */
    public static ColorUIResource getMenuBackgroundColor() {
        return getTheme().getMenuBackgroundColor();
    }

    /** Gets the menu selection foreground color.
	 *
	 * @return the menu selection foreground color
	 */
    public static ColorUIResource getMenuSelectionForegroundColor() {
        return getTheme().getMenuSelectionForegroundColor();
    }

    /** Gets the menu selection background color.
	 *
	 * @return the menu selection background color
	 */
    public static ColorUIResource getMenuSelectionBackgroundColor() {
        return getTheme().getMenuSelectionBackgroundColor();
    }

    /** Gets the menu color light.
	 *
	 * @return the menu color light
	 */
    public static ColorUIResource getMenuColorLight() {
        return getTheme().getMenuColorLight();
    }

    /** Gets the menu color dark.
	 *
	 * @return the menu color dark
	 */
    public static ColorUIResource getMenuColorDark() {
        return getTheme().getMenuColorDark();
    }

    /** Gets the toolbar foreground color.
	 *
	 * @return the toolbar foreground color
	 */
    public static ColorUIResource getToolbarForegroundColor() {
        return getTheme().getToolbarForegroundColor();
    }

    /** Gets the toolbar background color.
	 *
	 * @return the toolbar background color
	 */
    public static ColorUIResource getToolbarBackgroundColor() {
        return getTheme().getToolbarBackgroundColor();
    }

    /** Gets the toolbar color light.
	 *
	 * @return the toolbar color light
	 */
    public static ColorUIResource getToolbarColorLight() {
        return getTheme().getToolbarColorLight();
    }

    /** Gets the toolbar color dark.
	 *
	 * @return the toolbar color dark
	 */
    public static ColorUIResource getToolbarColorDark() {
        return getTheme().getToolbarColorDark();
    }

    /** Gets the toolbar docking color.
	 *
	 * @return the toolbar docking color
	 */
    public static ColorUIResource getToolbarDockingColor() {
        return getTheme().getFocusColor();
    }

    /** Gets the tab area background color.
	 *
	 * @return the tab area background color
	 */
    public static ColorUIResource getTabAreaBackgroundColor() {
        return getTheme().getTabAreaBackgroundColor();
    }

    /** Gets the tab selection foreground color.
	 *
	 * @return the tab selection foreground color
	 */
    public static ColorUIResource getTabSelectionForegroundColor() {
        return getTheme().getTabSelectionForegroundColor();
    }

    /** Gets the desktop color.
	 *
	 * @return the desktop color
	 */
    public static ColorUIResource getDesktopColor() {
        return getTheme().getDesktopColor();
    }

    /** Gets the tooltip foreground color.
	 *
	 * @return the tooltip foreground color
	 */
    public static ColorUIResource getTooltipForegroundColor() {
        return getTheme().getTooltipForegroundColor();
    }

    /** Gets the tooltip background color.
	 *
	 * @return the tooltip background color
	 */
    public static ColorUIResource getTooltipBackgroundColor() {
        return getTheme().getTooltipBackgroundColor();
    }

}
