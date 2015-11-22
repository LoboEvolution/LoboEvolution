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
 
package com.jtattoo.plaf.fast;

import com.jtattoo.plaf.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

/**
 * The Class FastBorders.
 *
 * @author Michael Hagen
 */
public class FastBorders extends BaseBorders {

//------------------------------------------------------------------------------------
// Lazy access methods
/**
 * Gets the button border.
 *
 * @return the button border
 */
//------------------------------------------------------------------------------------
    public static Border getButtonBorder() {
        if (buttonBorder == null) {
            buttonBorder = new ButtonBorder();
        }
        return buttonBorder;
    }

    /** Gets the toggle button border.
	 *
	 * @return the toggle button border
	 */
    public static Border getToggleButtonBorder() {
        return getButtonBorder();
    }

    /** Gets the tool button border.
	 *
	 * @return the tool button border
	 */
    public static Border getToolButtonBorder() {
        if (toolButtonBorder == null) {
            toolButtonBorder = new ToolButtonBorder();
        }
        return toolButtonBorder;
    }

    /** Gets the rollover tool button border.
	 *
	 * @return the rollover tool button border
	 */
    public static Border getRolloverToolButtonBorder() {
        if (rolloverToolButtonBorder == null) {
            rolloverToolButtonBorder = new RolloverToolButtonBorder();
        }
        return rolloverToolButtonBorder;
    }

    /** Gets the internal frame border.
	 *
	 * @return the internal frame border
	 */
    public static Border getInternalFrameBorder() {
        if (internalFrameBorder == null) {
            internalFrameBorder = new InternalFrameBorder();
        }
        return internalFrameBorder;
    }

//------------------------------------------------------------------------------------
// Implementation of border classes
/**
 * The Class ButtonBorder.
 */
//------------------------------------------------------------------------------------
    public static class ButtonBorder implements Border, UIResource {

        /** The Constant defaultFrameColor. */
        private static final Color defaultFrameColor = new Color(0, 64, 255);
        
        /** The Constant insets. */
        private static final Insets insets = new Insets(4, 8, 4, 8);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            Color frameColor = ColorHelper.darker(button.getBackground(), 30);
            if (model.isEnabled()) {
                if ((model.isPressed() && model.isArmed()) || model.isSelected()) {
                    g.setColor(frameColor);
                    g.drawRect(x, y, w - 1, h - 1);
                } else {
                    g.setColor(frameColor);
                    g.drawRect(x, y, w - 1, h - 1);
                    g.setColor(ColorHelper.brighter(button.getBackground(), 40));
                    g.drawLine(x + 1, y + 1, x + w - 2, y + 1);
                    g.drawLine(x + 1, y + 1, x + 1, y + h - 2);
                }
                if (c instanceof JButton) {
                    JButton b = (JButton) c;
                    if (b.getRootPane() != null && b.equals(b.getRootPane().getDefaultButton())) {
                        g.setColor(defaultFrameColor);
                        g.drawRect(x, y, w - 1, h - 1);
                    }
                }
            } else {
                g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
                g.drawRect(x, y, w - 1, h - 1);
            }
        }

        public Insets getBorderInsets(Component c) {
            return insets;
        }

        public Insets getBorderInsets(Component c, Insets borderInsets) {
            borderInsets.left = insets.left;
            borderInsets.top = insets.top;
            borderInsets.right = insets.right;
            borderInsets.bottom = insets.bottom;
            return borderInsets;
        }

        public boolean isBorderOpaque() {
            return true;
        }
    } // class ButtonBorder

/**
 * The Class ToolButtonBorder.
 */
//------------------------------------------------------------------------------
    public static class ToolButtonBorder implements Border, UIResource {

        /** The Constant insets. */
        private static final Insets insets = new Insets(1, 1, 1, 1);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            Color hiColor = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 90);
            Color loColor = AbstractLookAndFeel.getFrameColor();
            if (model.isEnabled()) {
                if ((model.isPressed() && model.isArmed()) || model.isSelected()) {
                    Graphics2D g2D = (Graphics2D) g;
                    Composite composite = g2D.getComposite();
                    g.setColor(loColor);
                    g.drawRect(x, y, w - 1, h - 1);
                    AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f);
                    g2D.setComposite(alpha);
                    g.setColor(Color.black);
                    g.fillRect(x + 1, y + 1, w - 2, h - 2);
                    g2D.setComposite(composite);
                } else {
                    JTattooUtilities.draw3DBorder(g, hiColor, loColor, 0, 0, w, h);
                }
            }
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(insets.top, insets.left, insets.bottom, insets.right);
        }

        public Insets getBorderInsets(Component c, Insets borderInsets) {
            borderInsets.left = insets.left;
            borderInsets.top = insets.top;
            borderInsets.right = insets.right;
            borderInsets.bottom = insets.bottom;
            return borderInsets;
        }

        public boolean isBorderOpaque() {
            return true;
        }
    } // class ToolButtonBorder

/**
 * The Class RolloverToolButtonBorder.
 */
//------------------------------------------------------------------------------
    public static class RolloverToolButtonBorder implements Border, UIResource {

        /** The Constant insets. */
        private static final Insets insets = new Insets(1, 1, 1, 1);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            Color loColor = AbstractLookAndFeel.getFrameColor();
            if (model.isEnabled()) {
                if ((model.isPressed() && model.isArmed()) || model.isSelected()) {
                    Graphics2D g2D = (Graphics2D) g;
                    Composite composite = g2D.getComposite();
                    g.setColor(loColor);
                    g.drawRect(x, y, w - 1, h - 1);
                    AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f);
                    g2D.setComposite(alpha);
                    g.setColor(Color.black);
                    g.fillRect(x + 1, y + 1, w - 2, h - 2);
                    g2D.setComposite(composite);
                } else if (model.isRollover()) {
                    Graphics2D g2D = (Graphics2D) g;
                    Composite composite = g2D.getComposite();
                    g.setColor(loColor);
                    g.drawRect(x, y, w - 1, h - 1);
                    AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
                    g2D.setComposite(alpha);
                    g.setColor(Color.white);
                    g.fillRect(x + 1, y + 1, w - 2, h - 2);
                    g2D.setComposite(composite);
                }
            }
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(insets.top, insets.left, insets.bottom, insets.right);
        }

        public Insets getBorderInsets(Component c, Insets borderInsets) {
            borderInsets.left = insets.left;
            borderInsets.top = insets.top;
            borderInsets.right = insets.right;
            borderInsets.bottom = insets.bottom;
            return borderInsets;
        }

        public boolean isBorderOpaque() {
            return true;
        }
    } // class RolloverToolButtonBorder

/**
 * The Class InternalFrameBorder.
 */
//------------------------------------------------------------------------------
    public static class InternalFrameBorder extends BaseInternalFrameBorder {

        /** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            boolean active = isActive(c);
            boolean resizable = isResizable(c);
            Color frameColor = AbstractLookAndFeel.getFrameColor();
            Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
            if (active) {
                borderColor = AbstractLookAndFeel.getWindowBorderColor();
            }
            Color cHi = ColorHelper.brighter(frameColor, 40);
            Color cLo = frameColor;
            if (!resizable) {
                JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
                g.setColor(borderColor);
                for (int i = 1; i < dw; i++) {
                    g.drawRect(i, i, w - (2 * i) - 1, h - (2 * i) - 1);
                }
                return;
            }
            JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
            cHi = ColorHelper.brighter(borderColor, 40);
            cLo = ColorHelper.darker(borderColor, 20);
            JTattooUtilities.draw3DBorder(g, cHi, cLo, x + 1, y + 1, w - 2, h - 2);

            g.setColor(borderColor);
            g.drawRect(x + 2, y + 2, w - 5, h - 5);
            g.drawRect(x + 3, y + 3, w - 7, h - 7);
            JTattooUtilities.draw3DBorder(g, ColorHelper.darker(borderColor, 5), ColorHelper.brighter(borderColor, 30), x + 4, y + 4, w - 8, h - 8);
        }
    } // class InternalFrameBorder

} // class FastBorders

