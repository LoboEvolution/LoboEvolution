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
import com.jtattoo.plaf.LazyImageIcon;

import java.awt.Container;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 * <p>BaseInternalFrameUI class.</p>
 */
public class BaseInternalFrameUI extends BasicInternalFrameUI {

	// -----------------------------------------------------------------------------
// inner classes
//-----------------------------------------------------------------------------
	private final static class MyPropertyChangeHandler implements PropertyChangeListener {

		@Override
		public void propertyChange(final PropertyChangeEvent e) {
			final JInternalFrame jif = (JInternalFrame) e.getSource();
			if (!(jif.getUI() instanceof BaseInternalFrameUI)) {
				return;
			}

			final BaseInternalFrameUI ui = (BaseInternalFrameUI) jif.getUI();
			final String name = e.getPropertyName();
			if (name.equals(FRAME_TYPE)) {
				if (e.getNewValue() instanceof String) {
					if (PALETTE_FRAME.equals(e.getNewValue())) {
						LookAndFeel.installBorder(ui.frame, FRAME_PALETTE_BORDER);
						ui.setPalette(true);
					} else {
						LookAndFeel.installBorder(ui.frame, FRAME_BORDER);
						ui.setPalette(false);
					}
				}
			} else if (name.equals(IS_PALETTE)) {
				if (e.getNewValue() != null) {
					ui.setPalette((Boolean) e.getNewValue());
				} else {
					ui.setPalette(false);
				}
			} else if (name.equals(JInternalFrame.CONTENT_PANE_PROPERTY)) {
				ui.stripContentBorder();
			} else if (name.equals("ancestor") && !AbstractLookAndFeel.isWindowDecorationOn()) {
				if (e.getNewValue() instanceof JDesktopPane) {
					final JDesktopPane jp = (JDesktopPane) e.getNewValue();
					final Window window = SwingUtilities.getWindowAncestor(jp);
					if (window != null) {
						boolean doAdd = true;
						for (final WindowListener wl : window.getWindowListeners()) {
							if (wl.equals(MY_WINDOW_HANDLER)) {
								doAdd = false;
								break;
							}
						}
						if (doAdd) {
							window.addWindowListener(MY_WINDOW_HANDLER);
						}
					}
				} else if (e.getOldValue() instanceof JDesktopPane) {
					final JDesktopPane jp = (JDesktopPane) e.getOldValue();
					final Window window = SwingUtilities.getWindowAncestor(jp);
					if (window != null) {
						window.removeWindowListener(MY_WINDOW_HANDLER);
					}
				}
			}
		}
	} // end of class MyPropertyChangeHandler
		// -----------------------------------------------------------------------------

	private final static class MyWindowHandler extends WindowAdapter {

		@Override
		public void windowActivated(final WindowEvent e) {
			e.getWindow().invalidate();
			e.getWindow().repaint();
		}

		@Override
		public void windowDeactivated(final WindowEvent e) {
			e.getWindow().invalidate();
			e.getWindow().repaint();
		}
	} // end of class MyWindowHandler

	private static final PropertyChangeListener MY_PROPERTY_CHANGE_HANDLER = new MyPropertyChangeHandler();

	private static final WindowAdapter MY_WINDOW_HANDLER = new MyWindowHandler();
	private static final Border HANDY_EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
	private static final String IS_PALETTE = "JInternalFrame.isPalette";
	private static final String FRAME_TYPE = "JInternalFrame.frameType";
	private static final String FRAME_BORDER = "InternalFrame.border";

	private static final String FRAME_PALETTE_BORDER = "InternalFrame.paletteBorder";

	private static final String PALETTE_FRAME = "palette";

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseInternalFrameUI((JInternalFrame) c);
	}

	/**
	 * <p>Constructor for BaseInternalFrameUI.</p>
	 *
	 * @param b a {@link javax.swing.JInternalFrame} object.
	 */
	public BaseInternalFrameUI(final JInternalFrame b) {
		super(b);
	}

	/** {@inheritDoc} */
	@Override
	protected JComponent createNorthPane(final JInternalFrame w) {
		return new BaseInternalFrameTitlePane(w);
	}

	/**
	 * <p>getTitlePane.</p>
	 *
	 * @return a {@link BaseInternalFrameTitlePane} object.
	 */
	public BaseInternalFrameTitlePane getTitlePane() {
		return (BaseInternalFrameTitlePane) titlePane;
	}

	/** {@inheritDoc} */
	@Override
	protected void installDefaults() {
		super.installDefaults();
		final Icon frameIcon = frame.getFrameIcon();
		if (frameIcon == null || frameIcon instanceof LazyImageIcon) {
			frame.setFrameIcon(UIManager.getIcon("InternalFrame.icon"));
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void installListeners() {
		super.installListeners();
		frame.addPropertyChangeListener(MY_PROPERTY_CHANGE_HANDLER);
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(final JComponent c) {
		super.installUI(c);
		final Object paletteProp = c.getClientProperty(IS_PALETTE);
		if (paletteProp != null) {
			setPalette((Boolean) paletteProp);
		}
		stripContentBorder();
	}

	/**
	 * <p>setPalette.</p>
	 *
	 * @param isPalette a boolean.
	 */
	public void setPalette(final boolean isPalette) {
		if (isPalette) {
			frame.setBorder(UIManager.getBorder(FRAME_PALETTE_BORDER));
		} else {
			frame.setBorder(UIManager.getBorder(FRAME_BORDER));
		}
		getTitlePane().setPalette(isPalette);
	}

	/**
	 * <p>stripContentBorder.</p>
	 */
	public void stripContentBorder() {
		final Container cp = frame.getContentPane();
		if (cp instanceof JComponent) {
			final JComponent contentPane = (JComponent) cp;
			final Border contentBorder = contentPane.getBorder();
			if (contentBorder == null || contentBorder instanceof UIResource) {
				contentPane.setBorder(HANDY_EMPTY_BORDER);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void uninstallComponents() {
		titlePane = null;
		super.uninstallComponents();
	}

	/** {@inheritDoc} */
	@Override
	protected void uninstallListeners() {
		frame.removePropertyChangeListener(MY_PROPERTY_CHANGE_HANDLER);
		super.uninstallListeners();
	}

	/** {@inheritDoc} */
	@Override
	public void uninstallUI(final JComponent c) {
		final Container cp = frame.getContentPane();
		if (cp instanceof JComponent) {
			final JComponent contentPane = (JComponent) cp;
			if (contentPane.getBorder() == HANDY_EMPTY_BORDER) {
				contentPane.setBorder(null);
			}
		}
		super.uninstallUI(c);
	}

} // end of class BaseInternalFrameUI
