/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package com.jtattoo.plaf;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.Popup;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

/**
 * <p>BasePopupMenuUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BasePopupMenuUI extends BasicPopupMenuUI {

	// ----------------------------------------------------------------------------------------
// inner classes
//----------------------------------------------------------------------------------------    
	public static class MyPopupMenuListener implements PopupMenuListener {

		private BasePopupMenuUI popupMenuUI = null;

		public MyPopupMenuListener(BasePopupMenuUI aPopupMenuUI) {
			popupMenuUI = aPopupMenuUI;
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			if (popupMenuUI.screenImage != null) {
				JPopupMenu popup = (JPopupMenu) e.getSource();
				JRootPane root = popup.getRootPane();
				if (popup.isShowing() && root.isShowing()) {
					Point ptPopup = popup.getLocationOnScreen();
					Point ptRoot = root.getLocationOnScreen();
					Graphics g = popup.getRootPane().getGraphics();
					g.drawImage(popupMenuUI.screenImage, ptPopup.x - ptRoot.x, ptPopup.y - ptRoot.y, null);
					popupMenuUI.resetScreenImage();
				}
			}
		}

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		}

	} // end of class MyPopupMenuListener

	/** Constant robot */
	protected static Robot robot = null;

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BasePopupMenuUI();
	}

	protected BufferedImage screenImage = null;

	protected MyPopupMenuListener myPopupListener = null;

	/** {@inheritDoc} */
	@Override
	public Popup getPopup(JPopupMenu popupMenu, int x, int y) {
		Popup popup = super.getPopup(popupMenu, x, y);
		if (!isMenuOpaque()) {
			try {
				Dimension size = popupMenu.getPreferredSize();
				if (size.width > 0 && size.height > 0) {
					Rectangle screenRect = new Rectangle(x, y, size.width, size.height);
					screenImage = getRobot().createScreenCapture(screenRect);
				}
				for (int i = 0; i < popupMenu.getComponentCount(); i++) {
					if (popupMenu.getComponent(i) instanceof JPanel) {
						JPanel panel = (JPanel) popupMenu.getComponent(i);
						panel.setOpaque(true);
					}
				}
			} catch (Exception ex) {
				screenImage = null;
			}
		}
		return popup;
	}

	private Robot getRobot() {
		if (robot == null) {
			try {
				robot = new Robot();
			} catch (AWTException ex) {
			}
		}
		return robot;
	}

	/** {@inheritDoc} */
	@Override
	public void installListeners() {
		super.installListeners();
		if (!isMenuOpaque()) {
			myPopupListener = new MyPopupMenuListener(this);
			popupMenu.addPopupMenuListener(myPopupListener);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		popupMenu.setOpaque(false);
		popupMenu.setLightWeightPopupEnabled(false);
	}

	private boolean isMenuOpaque() {
		return AbstractLookAndFeel.getTheme().isMenuOpaque() || getRobot() == null;
	}

	private void resetScreenImage() {
		screenImage = null;
	}

	/** {@inheritDoc} */
	@Override
	public void uninstallListeners() {
		if (!isMenuOpaque()) {
			popupMenu.removePopupMenuListener(myPopupListener);
		}
		super.uninstallListeners();
	}

	/** {@inheritDoc} */
	@Override
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		c.setOpaque(true);
	}

	/** {@inheritDoc} */
	@Override
	public void update(Graphics g, JComponent c) {
		if (screenImage != null) {
			g.drawImage(screenImage, 0, 0, null);
		} else {
			g.setColor(AbstractLookAndFeel.getMenuBackgroundColor());
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
		}
	}

} // end of class BasePopupMenuUI
