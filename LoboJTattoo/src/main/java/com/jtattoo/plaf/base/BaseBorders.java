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

package com.jtattoo.plaf.base;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.base.border.*;
import com.jtattoo.plaf.acryl.border.SpinnerBorder;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.BorderUIResource;


/**
 * <p>BaseBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseBorders {

	/** Constant buttonBorder */
	protected static Border buttonBorder = null;
    
	/** Constant focusFrameBorder */
	protected static Border focusFrameBorder = null;
    
	/** Constant textFieldBorder */
	protected static Border textFieldBorder = null;
    
	/** Constant spinnerBorder */
	protected static Border spinnerBorder = null;
    
	/** Constant comboBoxBorder */
	protected static Border comboBoxBorder = null;
    
	/** Constant progressBarBorder */
	protected static Border progressBarBorder = null;
    
	/** Constant tableHeaderBorder */
	protected static Border tableHeaderBorder = null;
    
	/** Constant popupMenuBorder */
	protected static Border popupMenuBorder = null;
	
    	/** Constant menuItemBorder */
	protected static Border menuItemBorder = null;
    
	/** Constant toolBarBorder */
	protected static Border toolBarBorder = null;
    
	/** Constant toolButtonBorder */
	protected static Border toolButtonBorder = null;
    
	/** Constant rolloverToolButtonBorder */
	protected static Border rolloverToolButtonBorder = null;

	/** Constant internalFrameBorder */
	protected static Border internalFrameBorder = null;

	/** Constant paletteBorder */
	protected static Border paletteBorder = null;
    
	/** Constant scrollPaneBorder */
	protected static Border scrollPaneBorder = null;
    
	/** Constant tableScrollPaneBorder */
	protected static Border tableScrollPaneBorder = null;
    
	/** Constant tabbedPaneBorder */
	protected static Border tabbedPaneBorder = null;

	/** Constant desktopIconBorder */
	protected static Border desktopIconBorder = null;
    
	/**
	 * <p>Getter for the field comboBoxBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getComboBoxBorder() {
		if (comboBoxBorder == null) {
			comboBoxBorder = new ComboBoxBorder();
		}
		return comboBoxBorder;
	}
    
	/**
	 * <p>Getter for the field desktopIconBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getDesktopIconBorder() {
		if (desktopIconBorder == null) {
			desktopIconBorder = new BorderUIResource.CompoundBorderUIResource(
					new LineBorder(AbstractLookAndFeel.getWindowBorderColor(), 1),
					new MatteBorder(2, 2, 1, 2, AbstractLookAndFeel.getWindowBorderColor()));
		}
		return desktopIconBorder;
	}

	// ------------------------------------------------------------------------------------
	// Lazy access methods
	// ------------------------------------------------------------------------------------
	
    
	/**
	 * <p>Getter for the field focusFrameBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getFocusFrameBorder() {
		if (focusFrameBorder == null) {
			focusFrameBorder = new FocusFrameBorder();
		}
		return focusFrameBorder;
	}
    
	/**
	 * <p>getMenuBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getMenuBarBorder() {
		return BorderFactory.createEmptyBorder(1, 1, 1, 1);
	}
    
	/**
	 * <p>Getter for the field menuItemBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getMenuItemBorder() {
		if (menuItemBorder == null) {
			menuItemBorder = new MenuItemBorder();
		}
		return menuItemBorder;
	}
    
	/**
	 * <p>Getter for the field paletteBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getPaletteBorder() {
		if (paletteBorder == null) {
			paletteBorder = new PaletteBorder();
		}
		return paletteBorder;
	}
    
	/**
	 * <p>Getter for the field popupMenuBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getPopupMenuBorder() {
		if (popupMenuBorder == null) {
			if (AbstractLookAndFeel.getTheme().isMenuOpaque()) {
				popupMenuBorder = new BasePopupMenuBorder();
			} else {
				popupMenuBorder = new BasePopupMenuShadowBorder();
			}
		}
		return popupMenuBorder;
	}
    
	/**
	 * <p>Getter for the field progressBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getProgressBarBorder() {
		if (progressBarBorder == null) {
			progressBarBorder = BorderFactory
					.createLineBorder(ColorHelper.darker(AbstractLookAndFeel.getBackgroundColor(), 30));
		}
		return progressBarBorder;
	}
    
	/**
	 * <p>Getter for the field scrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getScrollPaneBorder() {
		if (scrollPaneBorder == null) {
			scrollPaneBorder = new ScrollPaneBorder(false);
		}
		return scrollPaneBorder;
	}
    
	/**
	 * <p>Getter for the field spinnerBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getSpinnerBorder() {
		if (spinnerBorder == null) {
			spinnerBorder = new SpinnerBorder();
		}
		return spinnerBorder;
	}
    
	/**
	 * <p>Getter for the field tabbedPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTabbedPaneBorder() {
		if (tabbedPaneBorder == null) {
			tabbedPaneBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		}
		return tabbedPaneBorder;
	}
    
	/**
	 * <p>Getter for the field tableHeaderBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTableHeaderBorder() {
		if (tableHeaderBorder == null) {
			tableHeaderBorder = new TableHeaderBorder();
		}
		return tableHeaderBorder;
	}
    
	/**
	 * <p>Getter for the field tableScrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTableScrollPaneBorder() {
		if (tableScrollPaneBorder == null) {
			tableScrollPaneBorder = new ScrollPaneBorder(true);
		}
		return tableScrollPaneBorder;
	}

	// ------------------------------------------------------------------------------------
	// Lazy access methods
	// ------------------------------------------------------------------------------------
	
    
	/**
	 * <p>getTextBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTextBorder() {
		if (textFieldBorder == null) {
			textFieldBorder = new TextFieldBorder();
		}
		return textFieldBorder;
	}
    
	/**
	 * <p>Getter for the field textFieldBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTextFieldBorder() {
		return getTextBorder();
	}
    
	/**
	 * <p>Getter for the field toolBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getToolBarBorder() {
		if (toolBarBorder == null) {
			toolBarBorder = new ToolBarBorder();
		}
		return toolBarBorder;
	}
    
	/**
	 * <p>Getter for the field toolButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getToolButtonBorder() {
		if (toolButtonBorder == null) {
			toolButtonBorder = new ToolButtonBorder();
		}
		return toolButtonBorder;
	}
    
	/**
	 * <p>initDefaults.</p>
	 */
	public static void initDefaults() {
		buttonBorder = null;
		textFieldBorder = null;
		spinnerBorder = null;
		comboBoxBorder = null;
		progressBarBorder = null;
		tableHeaderBorder = null;
		popupMenuBorder = null;
		menuItemBorder = null;
		toolBarBorder = null;
		toolButtonBorder = null;
		rolloverToolButtonBorder = null;
		paletteBorder = null;
		internalFrameBorder = null;
		scrollPaneBorder = null;
		tableScrollPaneBorder = null;
		tabbedPaneBorder = null;
		desktopIconBorder = null;
	}

} // end of class BaseBorders
