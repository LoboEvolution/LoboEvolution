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
	public static ComponentUI createUI(JComponent c) {
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
