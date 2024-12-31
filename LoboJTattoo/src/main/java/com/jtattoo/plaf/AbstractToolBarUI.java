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

package com.jtattoo.plaf;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicToolBarUI;

/**
 * <p>Abstract AbstractToolBarUI class.</p>
 *
 *
 *
 */
public abstract class AbstractToolBarUI extends BasicToolBarUI {

	protected class MyContainerListener implements ContainerListener {

		@Override
		public void componentAdded(final ContainerEvent e) {
			final Component c = e.getChild();
			if (c instanceof AbstractButton) {
				changeButtonBorder((AbstractButton) c);
			}
		}

		@Override
		public void componentRemoved(final ContainerEvent e) {
			final Component c = e.getChild();
			if (c instanceof AbstractButton) {
				restoreButtonBorder((AbstractButton) c);
			}
		}
	}

	protected class MyPropertyChangeListener implements PropertyChangeListener {

		@Override
		public void propertyChange(final PropertyChangeEvent e) {
			if (e.getPropertyName().equals(IS_ROLLOVER)) {
				if (e.getNewValue() != null) {
					rolloverEnabled = (Boolean) e.getNewValue();
					changeBorders();
				}
			} else if ("componentOrientation".equals(e.getPropertyName())) {
				updateToolbarBorder();
			}
		}
	}

	private final static class NullBorder implements Border, UIResource {

		private static final Insets INSETS = new Insets(0, 0, 0, 0);

		@Override
		public Insets getBorderInsets(final Component c) {
			return INSETS;
		}

		@Override
		public boolean isBorderOpaque() {
			return true;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
		}

	} // end of class NullBorder

	private static final  String IS_ROLLOVER = "JToolBar.isRollover";
	private static final  Insets BUTTON_MARGIN = new Insets(1, 1, 1, 1);
	private static final  Border INNER_BORDER = BorderFactory.createEmptyBorder(2, 2, 2, 2);
	private boolean rolloverEnabled = true;
	private MyPropertyChangeListener propertyChangeListener = null;

	private MyContainerListener containerListener = null;

	private final Map<AbstractButton, Border> orgBorders = new HashMap<>();

	private final Map<AbstractButton, Insets> orgMargins = new HashMap<>();

	/**
	 * <p>changeBorders.</p>
	 */
	protected void changeBorders() {
		final Component[] components = toolBar.getComponents();
		for (final Component comp : components) {
			if (comp instanceof AbstractButton) {
				changeButtonBorder((AbstractButton) comp);
			}
		}
	}

	/**
	 * <p>changeButtonBorder.</p>
	 *
	 * @param b a {@link javax.swing.AbstractButton} object.
	 */
	protected void changeButtonBorder(final AbstractButton b) {
		final Object cp = b.getClientProperty("paintToolBarBorder");
		if (cp instanceof Boolean) {
			final Boolean changeBorder = (Boolean) cp;
			if (!changeBorder) {
				return;
			}
		}
		if (!orgBorders.containsKey(b)) {
			if (b.getBorder() != null) {
				orgBorders.put(b, b.getBorder());
			} else {
				orgBorders.put(b, new NullBorder());
			}
		}

		if (!orgMargins.containsKey(b)) {
			orgMargins.put(b, b.getMargin());
		}

		if (b.getBorder() != null) {
			if (isRolloverEnabled()) {
				b.setBorderPainted(true);
				b.setBorder(BorderFactory.createCompoundBorder(getRolloverBorder(), INNER_BORDER));
				b.setMargin(BUTTON_MARGIN);
				b.setRolloverEnabled(true);
			} else {
				b.setBorder(BorderFactory.createCompoundBorder(getNonRolloverBorder(), INNER_BORDER));
				b.setMargin(BUTTON_MARGIN);
				b.setRolloverEnabled(false);
			}
			b.setOpaque(isButtonOpaque());
			b.setContentAreaFilled(isButtonOpaque());
		}
	}

	/**
	 * <p>getNonRolloverBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public abstract Border getNonRolloverBorder();

	/**
	 * <p>getRolloverBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public abstract Border getRolloverBorder();

	/** {@inheritDoc} */
	@Override
	protected void installListeners() {
		super.installListeners();
		propertyChangeListener = new MyPropertyChangeListener();
		if (propertyChangeListener != null) {
			toolBar.addPropertyChangeListener(propertyChangeListener);
		}
		containerListener = new MyContainerListener();
		if (containerListener != null) {
			toolBar.addContainerListener(containerListener);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(final JComponent c) {
		super.installUI(c);
		final Boolean isRollover = (Boolean) UIManager.get(IS_ROLLOVER);
		if (isRollover != null) {
			rolloverEnabled = isRollover;
		}
		SwingUtilities.invokeLater(this::changeBorders);
	}

	/**
	 * <p>isButtonOpaque.</p>
	 *
	 * @return a boolean.
	 */
	public abstract boolean isButtonOpaque();

	/**
	 * <p>isRolloverEnabled.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean isRolloverEnabled() {
		return rolloverEnabled;
	}

	/**
	 * <p>isToolbarDecorated.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean isToolbarDecorated() {
		return AbstractLookAndFeel.getTheme().isToolbarDecorated();
	}

	/**
	 * <p>isToolBarUnderMenubar.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean isToolBarUnderMenubar() {
		if (toolBar != null && toolBar.getOrientation() == SwingConstants.HORIZONTAL) {
			final JRootPane rp = SwingUtilities.getRootPane(toolBar);
			final JMenuBar mb = rp.getJMenuBar();
			if (mb != null) {
				Point mbPoint = new Point(0, 0);
				mbPoint = SwingUtilities.convertPoint(mb, mbPoint, rp);
				Point tbPoint = new Point(0, 0);
				tbPoint = SwingUtilities.convertPoint(toolBar, tbPoint, rp);
				tbPoint.y -= mb.getHeight() - 1;
				final Rectangle rect = new Rectangle(mbPoint, mb.getSize());
				return rect.contains(tbPoint);
			}
		}
		return false;
	}

	/**
	 * <p>restoreBorders.</p>
	 */
	protected void restoreBorders() {
		final Component[] components = toolBar.getComponents();
		for (final Component comp : components) {
			if (comp instanceof AbstractButton) {
				restoreButtonBorder((AbstractButton) comp);
			}
		}
	}

	/**
	 * <p>restoreButtonBorder.</p>
	 *
	 * @param b a {@link javax.swing.AbstractButton} object.
	 */
	protected void restoreButtonBorder(final AbstractButton b) {
		final Object cp = b.getClientProperty("paintToolBarBorder");
		if (cp instanceof Boolean) {
			final Boolean changeBorder = (Boolean) cp;
			if (!changeBorder) {
				return;
			}
		}
		final Border border = orgBorders.get(b);
		if (border != null) {
			if (border instanceof NullBorder) {
				b.setBorder(null);
			} else {
				b.setBorder(border);
			}
		}
		b.setMargin(orgMargins.get(b));
	}

	/** {@inheritDoc} */
	@Override
	protected void setBorderToNonRollover(final Component c) {
	}

	/** {@inheritDoc} */
	@Override
	protected void setBorderToNormal(final Component c) {
	}

	/** {@inheritDoc} */
	@Override
	protected void setBorderToRollover(final Component c) {
	}

	/** {@inheritDoc} */
	@Override
	protected void uninstallListeners() {
		if (propertyChangeListener != null) {
			toolBar.removePropertyChangeListener(propertyChangeListener);
		}
		propertyChangeListener = null;
		if (containerListener != null) {
			toolBar.removeContainerListener(containerListener);
		}
		containerListener = null;
		super.uninstallListeners();
	}

	/** {@inheritDoc} */
	@Override
	public void uninstallUI(final JComponent c) {
		restoreBorders();
		super.uninstallUI(c);
	}

	/**
	 * <p>updateToolbarBorder.</p>
	 */
	protected void updateToolbarBorder() {
		toolBar.revalidate();
		toolBar.repaint();
	}

} // end of class AbstractToolBarUI
