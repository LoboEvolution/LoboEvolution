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

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicRootPaneUI;

/**
 * This source is a modified copy of javax.swing.plaf.metal.MetalRootPaneUI
 * Provides the base look and feel implementation of RootPaneUI.
 * <p>
 * BaseRootPaneUI provides support for the
 * windowDecorationStyle property of JRootPane.
 * BaseRootPaneUI does this by way of installing a custom
 * LayoutManager, a private Component to render the
 * appropriate widgets, and a private Border. The
 * LayoutManager is always installed, regardless of the value of
 * the windowDecorationStyle property, but the Border
 * and Component are only installed/added if the
 * windowDecorationStyle is other than JRootPane.NONE.
 * <p>
 * <strong>Warning:</strong> Serialized objects of this class will not be
 * compatible with future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Swing. As of 1.4, support for long term storage of all
 * JavaBeans TM has been added to the java.beans package. Please
 * see {@link java.beans.XMLEncoder}.
 *
 * @version 1.20 04/27/04
 * Author Terry Kellerman
 * Author Michael Hagen
 * @since 1.4
 */
public class BaseRootPaneUI extends BasicRootPaneUI {

	// Konstanten aus javax.swing.JRootPane damit Attribute aus Java 1.4 sich mit
	// Java 1.3 uebersetzen lassen

	// ------------------------------------------------------------------------------
	private static class BaseRootLayout implements LayoutManager2 {

		@Override
		public void addLayoutComponent(Component comp, Object constraints) {
		}

		@Override
		public void addLayoutComponent(String name, Component comp) {
		}

		@Override
		public float getLayoutAlignmentX(Container target) {
			return 0.0f;
		}

		@Override
		public float getLayoutAlignmentY(Container target) {
			return 0.0f;
		}

		@Override
		public void invalidateLayout(Container target) {
		}

		/**
		 * Instructs the layout manager to perform the layout for the specified
		 * container.
		 *
		 * @param parent for which this layout manager is being used
		 */
		@Override
		public void layoutContainer(Container parent) {
			JRootPane root = (JRootPane) parent;
			Rectangle b = root.getBounds();
			Insets i = root.getInsets();
			int nextY = 0;
			int w = b.width - i.right - i.left;
			int h = b.height - i.top - i.bottom;

			if (root.getLayeredPane() != null) {
				root.getLayeredPane().setBounds(i.left, i.top, w, h);
			}
			if (root.getGlassPane() != null) {
				if (root.getWindowDecorationStyle() != NONE && root.getUI() instanceof BaseRootPaneUI) {
					JComponent titlePane = ((BaseRootPaneUI) root.getUI()).internalGetTitlePane();
					int titleHeight = 0;
					if (titlePane != null) {
						titleHeight = titlePane.getSize().height;
					}
					root.getGlassPane().setBounds(i.left, i.top + titleHeight, w, h - titleHeight);
				} else {
					root.getGlassPane().setBounds(i.left, i.top, w, h);
				}
			}
			// Note: This is laying out the children in the layeredPane,
			// technically, these are not our children.
			if (root.getWindowDecorationStyle() != NONE && root.getUI() instanceof BaseRootPaneUI) {
				JComponent titlePane = ((BaseRootPaneUI) root.getUI()).internalGetTitlePane();
				if (titlePane != null) {
					Dimension tpd = titlePane.getPreferredSize();
					if (tpd != null) {
						int tpHeight = tpd.height;
						titlePane.setBounds(0, 0, w, tpHeight);
						nextY += tpHeight;
					}
				}
			}
			if (root.getJMenuBar() != null) {
				Dimension mbd = root.getJMenuBar().getPreferredSize();
				root.getJMenuBar().setBounds(0, nextY, w, mbd.height);
				nextY += mbd.height;
			}
			if (root.getContentPane() != null) {
				root.getContentPane().setBounds(0, nextY, w, h < nextY ? 0 : h - nextY);
			}
		}

		/**
		 * Returns the maximum amount of space the layout can use.
		 *
		 * @param target for which this layout manager is being used
		 * @return a Dimension object containing the layout's maximum size
		 */
		@Override
		public Dimension maximumLayoutSize(Container target) {
			return MAXIMUM_SIZE;
		}

		/**
		 * Returns the minimum amount of space the layout needs.
		 *
		 * @param parent for which this layout manager is being used
		 * @return a Dimension object containing the layout's minimum size
		 */
		@Override
		public Dimension minimumLayoutSize(Container parent) {
			return MINIMUM_SIZE;
		}

		/**
		 * Returns the amount of space the layout would like to have.
		 *
		 * @param parent for which this layout manager is being used
		 * @return a Dimension object containing the layout's preferred size
		 */
		@Override
		public Dimension preferredLayoutSize(Container parent) {
			Dimension cpd, mbd, tpd;
			int cpWidth = 0;
			int cpHeight = 0;
			int mbWidth = 0;
			int mbHeight = 0;
			int tpWidth = 0;
			Insets i = parent.getInsets();
			JRootPane root = (JRootPane) parent;

			if (root.getContentPane() != null) {
				cpd = root.getContentPane().getPreferredSize();
			} else {
				cpd = root.getSize();
			}
			if (cpd != null) {
				cpWidth = cpd.width;
				cpHeight = cpd.height;
			}

			if (root.getJMenuBar() != null) {
				mbd = root.getJMenuBar().getPreferredSize();
				if (mbd != null) {
					mbWidth = mbd.width;
					mbHeight = mbd.height;
				}
			}

			if (root.getWindowDecorationStyle() != NONE && root.getUI() instanceof BaseRootPaneUI) {
				JComponent titlePane = ((BaseRootPaneUI) root.getUI()).internalGetTitlePane();
				if (titlePane != null) {
					tpd = titlePane.getPreferredSize();
					if (tpd != null) {
						tpWidth = tpd.width;
					}
				}
			}

			return new Dimension(Math.max(Math.max(cpWidth, mbWidth), tpWidth) + i.left + i.right,
					cpHeight + mbHeight + tpWidth + i.top + i.bottom);
		}

		@Override
		public void removeLayoutComponent(Component comp) {
		}
	}

	// ------------------------------------------------------------------------------
	/**
	 * MouseInputHandler is responsible for handling resize/moving of the Window. It
	 * sets the cursor directly on the Window when then mouse moves over a hot spot.
	 */
	private class MouseInputHandler implements MouseInputListener {

		/**
		 * Set to true if the drag operation is moving the window.
		 */
		private boolean isMovingWindow;
		/**
		 * Set to true if the drag operation is resizing the window.
		 */
		private boolean isResizingWindow;
		/**
		 * Used to determine the corner the resize is occuring from.
		 */
		private int dragCursor;
		/**
		 * X location the mouse went down on for a drag operation.
		 */
		private int dragOffsetX;
		/**
		 * Y location the mouse went down on for a drag operation.
		 */
		private int dragOffsetY;
		/**
		 * Width of the window when the drag started.
		 */
		private int dragWidth;
		/**
		 * Height of the window when the drag started.
		 */
		private int dragHeight;
		private Container savedContentPane = null;
		private ResizingPanel resizingPanel = null;

		private void adjust(Rectangle bounds, Dimension min, int deltaX, int deltaY, int deltaWidth, int deltaHeight) {
			bounds.x += deltaX;
			bounds.y += deltaY;
			bounds.width += deltaWidth;
			bounds.height += deltaHeight;
			if (min != null) {
				if (bounds.width < min.width) {
					int correction = min.width - bounds.width;
					if (deltaX != 0) {
						bounds.x -= correction;
					}
					bounds.width = min.width;
				}
				if (bounds.height < min.height) {
					int correction = min.height - bounds.height;
					if (deltaY != 0) {
						bounds.y -= correction;
					}
					bounds.height = min.height;
				}
			}
		}

		/**
		 * Returns the corner that contains the point x, y, or
		 * -1 if the position doesn't match a corner.
		 */
		private int calculateCorner(Component c, int x, int y) {
			int xPosition = calculatePosition(x, c.getWidth());
			int yPosition = calculatePosition(y, c.getHeight());

			if (xPosition == -1 || yPosition == -1) {
				return -1;
			}
			return yPosition * 5 + xPosition;
		}

		/**
		 * Returns an integer indicating the position of spot in
		 * width. The return value will be: 0 if < BORDER_DRAG_THICKNESS 1
		 * if < CORNER_DRAG_WIDTH 2 if >= CORNER_DRAG_WIDTH && < width -
		 * BORDER_DRAG_THICKNESS 3 if >= width - CORNER_DRAG_WIDTH 4 if >= width -
		 * BORDER_DRAG_THICKNESS 5 otherwise
		 */
		private int calculatePosition(int spot, int width) {
			if (spot < BORDER_DRAG_THICKNESS) {
				return 0;
			}
			if (spot < CORNER_DRAG_WIDTH) {
				return 1;
			}
			if (spot >= width - BORDER_DRAG_THICKNESS) {
				return 4;
			}
			if (spot >= width - CORNER_DRAG_WIDTH) {
				return 3;
			}
			return 2;
		}

		/**
		 * Returns the Cursor to render for the specified corner. This returns 0 if the
		 * corner doesn't map to a valid Cursor
		 */
		private int getCursor(int corner) {
			if (corner == -1) {
				return 0;
			}
			return cursorMapping[corner];
		}

		private int getMinScreenY() {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] devices = ge.getScreenDevices();
			GraphicsDevice gd = devices[0];
			GraphicsConfiguration gc = gd.getDefaultConfiguration();
			int minScreenY = gc.getBounds().y + Toolkit.getDefaultToolkit().getScreenInsets(gc).top;
			if (devices.length > 1) {
				for (int i = 1; i < devices.length; i++) {
					gd = devices[i];
					gc = gd.getDefaultConfiguration();
					minScreenY = Math.min(minScreenY,
							gc.getBounds().y + Toolkit.getDefaultToolkit().getScreenInsets(gc).top);
				}
			}
			return minScreenY;
		}

		@Override
		public void mouseClicked(final MouseEvent ev) {
			if (ev.getSource() instanceof Window) {
				Window window = (Window) ev.getSource();
				if (window instanceof Frame) {
					Frame frame = (Frame) window;
					Point convertedPoint = SwingUtilities.convertPoint(window, ev.getPoint(), internalGetTitlePane());
					int state = frame.getExtendedState();
					if (titlePane != null && titlePane instanceof TitlePane && titlePane.contains(convertedPoint)
							&& frame.isResizable()) {
						if (ev.getClickCount() % 2 == 0 && (ev.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
							if ((state & BaseRootPaneUI.MAXIMIZED_BOTH) != 0) {
								((TitlePane) titlePane).restore();
							} else {
								((TitlePane) titlePane).maximize();
							}
						}
					}
				}

			}
		}

		@Override
		public void mouseDragged(final MouseEvent ev) {
			if (ev.getSource() instanceof Window) {
				Window w = (Window) ev.getSource();
				if (w.isShowing()) {
					if (w instanceof Frame) {
						Frame frame = (Frame) w;
						int frameState = frame.getExtendedState();
						if ((frameState & BaseRootPaneUI.MAXIMIZED_BOTH) != 0) {
							if (internalGetTitlePane() instanceof TitlePane) {
								Point pt = ev.getPoint();
								Point dragWindowOffset = ev.getPoint();
								Point convertedDragWindowOffset = SwingUtilities.convertPoint(w, dragWindowOffset,
										internalGetTitlePane());
								if (internalGetTitlePane().contains(convertedDragWindowOffset)) {
									int ow = w.getWidth();
									((TitlePane) internalGetTitlePane()).restore();
									int nw = w.getWidth();
									int nx = pt.x * nw / ow;
									int ny = pt.y;
									w.setLocation(nx, ny);
									dragOffsetX = nx;
									dragOffsetY = ny;
									isMovingWindow = true;
									for (PropertyChangeListener pcl : frame.getPropertyChangeListeners()) {
										pcl.propertyChange(new PropertyChangeEvent(window, "windowMoving", Boolean.FALSE,
												Boolean.FALSE));
									}
								}
							}
						}
					}

					int minScreenY = getMinScreenY();
					if (isMovingWindow) {
						Point location = ev.getLocationOnScreen();
						location.x -= dragOffsetX;
						location.y = Math.max(minScreenY, location.y - dragOffsetY);
						w.setLocation(location);
					} else if (dragCursor != 0) {
						Point pt = ev.getPoint();
						Rectangle bounds = w.getBounds();
						Rectangle startBounds = new Rectangle(bounds);
						Dimension min = MINIMUM_SIZE;
						switch (dragCursor) {
							case Cursor.E_RESIZE_CURSOR:
								adjust(bounds, min, 0, 0, pt.x + dragWidth - dragOffsetX - bounds.width, 0);
								break;
							case Cursor.S_RESIZE_CURSOR:
								adjust(bounds, min, 0, 0, 0, pt.y + dragHeight - dragOffsetY - bounds.height);
								break;
							case Cursor.N_RESIZE_CURSOR:
								adjust(bounds, min, 0, pt.y - dragOffsetY, 0, -(pt.y - dragOffsetY));
								break;
							case Cursor.W_RESIZE_CURSOR:
								adjust(bounds, min, pt.x - dragOffsetX, 0, -(pt.x - dragOffsetX), 0);
								break;
							case Cursor.NE_RESIZE_CURSOR:
								adjust(bounds, min, 0, pt.y - dragOffsetY, pt.x + dragWidth - dragOffsetX - bounds.width, -(pt.y - dragOffsetY));
								break;
							case Cursor.SE_RESIZE_CURSOR:
								adjust(bounds, min, 0, 0, pt.x + dragWidth - dragOffsetX - bounds.width, pt.y + dragHeight - dragOffsetY - bounds.height);
								break;
							case Cursor.NW_RESIZE_CURSOR:
								adjust(bounds, min, pt.x - dragOffsetX, pt.y - dragOffsetY, -(pt.x - dragOffsetX), -(pt.y - dragOffsetY));
								break;
							case Cursor.SW_RESIZE_CURSOR:
								adjust(bounds, min, pt.x - dragOffsetX, 0, -(pt.x - dragOffsetX),pt.y + dragHeight - dragOffsetY - bounds.height);
								break;
							default:
								break;
						}
						if (!bounds.equals(startBounds)) {
							if (bounds.y < minScreenY) {
								int delta = minScreenY - bounds.y;
								bounds.y = minScreenY;
								bounds.height -= delta;
							}
							w.setBounds(bounds);
							w.validate();
						}
					}
				}
			}
		}

		@Override
		public void mouseEntered(final MouseEvent ev) {
			mouseMoved(ev);
		}

		@Override
		public void mouseExited(final MouseEvent ev) {
			if (ev.getSource() instanceof Window && savedCursor != null) {
				Window w = (Window) ev.getSource();
				w.setCursor(savedCursor);
				savedCursor = null;
			}
		}

		@Override
		public void mouseMoved(final MouseEvent ev) {
			if (ev.getSource() instanceof Window) {
				JRootPane root = getRootPane();
				if (root.getWindowDecorationStyle() != NONE) {
					Window w = (Window) ev.getSource();
					Frame f = null;
					Dialog d = null;

					if (w instanceof Frame) {
						f = (Frame) w;
					} else if (w instanceof Dialog) {
						d = (Dialog) w;
					}

					// Update the cursor
					int cursor = getCursor(calculateCorner(w, ev.getX(), ev.getY()));
					if (!isMovingWindow && cursor != 0
							&& (f != null && f.isResizable() && (f.getExtendedState() & BaseRootPaneUI.MAXIMIZED_BOTH) == 0
							|| d != null && d.isResizable())) {
						if (savedCursor == null) {
							savedCursor = w.getCursor();
						}
						w.setCursor(Cursor.getPredefinedCursor(cursor));
					} else if (savedCursor != null) {
						w.setCursor(savedCursor);
						savedCursor = null;
					}
				}
			}
		}

		@Override
		public void mousePressed(final MouseEvent ev) {
			Window w = (Window) ev.getSource();
			if (w instanceof Window) {
				JRootPane root = getRootPane();
				if (root.getWindowDecorationStyle() != NONE) {
					w.toFront();

					Point dragWindowOffset = ev.getPoint();
					Point convertedDragWindowOffset = SwingUtilities.convertPoint(w, dragWindowOffset, internalGetTitlePane());

					Frame f = null;
					Dialog d = null;

					if (w instanceof Frame) {
						f = (Frame) w;
					} else if (w instanceof Dialog) {
						d = (Dialog) w;
					}

					int frameState = f != null ? f.getExtendedState() : 0;

					if (internalGetTitlePane() != null && internalGetTitlePane().contains(convertedDragWindowOffset)) {
						if ((f != null && (frameState & BaseRootPaneUI.MAXIMIZED_BOTH) == 0 || d != null)
								&& dragWindowOffset.y >= BORDER_DRAG_THICKNESS
								&& dragWindowOffset.x >= BORDER_DRAG_THICKNESS
								&& dragWindowOffset.x < w.getWidth() - BORDER_DRAG_THICKNESS) {
							isMovingWindow = true;
							dragOffsetX = dragWindowOffset.x;
							dragOffsetY = dragWindowOffset.y;
							if (window instanceof JFrame) {
								JFrame frame = (JFrame) window;
								for (PropertyChangeListener pcl : frame.getPropertyChangeListeners()) {
									pcl.propertyChange(new PropertyChangeEvent(window, "windowMoving", Boolean.FALSE, Boolean.FALSE));
								}
							}
							if (window instanceof JDialog) {
								JDialog dialog = (JDialog) window;
								for (PropertyChangeListener pcl : dialog.getPropertyChangeListeners()) {
									pcl.propertyChange(new PropertyChangeEvent(window, "windowMoving", Boolean.FALSE, Boolean.FALSE));
								}
							}
						}
					} else if (f != null && f.isResizable() && (frameState & BaseRootPaneUI.MAXIMIZED_BOTH) == 0
							|| d != null && d.isResizable()) {
						isResizingWindow = true;
						if (!isDynamicLayout()) {
							savedContentPane = getRootPane().getContentPane();
							GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
							BufferedImage bi = gc.createCompatibleImage(savedContentPane.getWidth(), savedContentPane.getHeight());
							savedContentPane.paint(bi.getGraphics());
							resizingPanel = new ResizingPanel(bi);
							getRootPane().setContentPane(resizingPanel);
						}
						dragOffsetX = dragWindowOffset.x;
						dragOffsetY = dragWindowOffset.y;
						dragWidth = w.getWidth();
						dragHeight = w.getHeight();
						dragCursor = getCursor(calculateCorner(w, dragWindowOffset.x, dragWindowOffset.y));
						if (window instanceof JFrame) {
							JFrame frame = (JFrame) window;
							for (PropertyChangeListener pcl : frame.getPropertyChangeListeners()) {
								pcl.propertyChange(new PropertyChangeEvent(window, "windowResizing", Boolean.FALSE, Boolean.FALSE));
							}
						}
						if (window instanceof JDialog) {
							JDialog dialog = (JDialog) window;
							for (PropertyChangeListener pcl : dialog.getPropertyChangeListeners()) {
								pcl.propertyChange(new PropertyChangeEvent(window, "windowResizing", Boolean.FALSE, Boolean.FALSE));
							}
						}
					}
				}
			}
		}

		@Override
		public void mouseReleased(final MouseEvent ev) {
			if (ev.getSource() instanceof Window) {
				Window w = (Window) ev.getSource();
				if (w != null) {
					if (!isDynamicLayout() && isResizingWindow) {
						getRootPane().setContentPane(savedContentPane);
						getRootPane().updateUI();
						resizingPanel = null;
					} else if (dragCursor != 0 && !window.isValid()) {
						// Some Window systems validate as you resize, others won't,
						// thus the check for validity before repainting.
						w.validate();
						getRootPane().repaint();
					}

					if (window instanceof JFrame) {
						JFrame frame = (JFrame) window;
						for (PropertyChangeListener pcl : frame.getPropertyChangeListeners()) {
							if (isMovingWindow) {
								pcl.propertyChange(
										new PropertyChangeEvent(window, "windowMoved", Boolean.FALSE, Boolean.FALSE));
							} else {
								pcl.propertyChange(
										new PropertyChangeEvent(window, "windowResized", Boolean.FALSE, Boolean.FALSE));
							}
						}
					}
					if (window instanceof JDialog) {
						JDialog dialog = (JDialog) window;
						for (PropertyChangeListener pcl : dialog.getPropertyChangeListeners()) {
							if (isMovingWindow) {
								pcl.propertyChange(
										new PropertyChangeEvent(window, "windowMoved", Boolean.FALSE, Boolean.FALSE));
							} else {
								pcl.propertyChange(
										new PropertyChangeEvent(window, "windowResized", Boolean.FALSE, Boolean.FALSE));
							}
						}
					}
				}
				isMovingWindow = false;
				isResizingWindow = false;
				dragCursor = 0;
			}
		}
	}

	// ------------------------------------------------------------------------------
	private static class ResizingPanel extends JPanel {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage bi = null;

		public ResizingPanel(BufferedImage bi) {
			super();
			this.bi = bi;
		}

		@Override
		public void paint(final Graphics g) {
			super.paint(g);
			g.drawImage(bi, 0, 0, null);
		}
	} // end of class ResizingPanel

	/** Constant NONE=0 */
	public static final int NONE = 0;
	/** Constant FRAME=1 */
	public static final int FRAME = 1;
	/** Constant PLAIN_DIALOG=2 */
	public static final int PLAIN_DIALOG = 2;
	/** Constant INFORMATION_DIALOG=3 */
	public static final int INFORMATION_DIALOG = 3;
	/** Constant ERROR_DIALOG=4 */
	public static final int ERROR_DIALOG = 4;
	/** Constant COLOR_CHOOSER_DIALOG=5 */
	public static final int COLOR_CHOOSER_DIALOG = 5;
	/** Constant FILE_CHOOSER_DIALOG=6 */
	public static final int FILE_CHOOSER_DIALOG = 6;
	/** Constant QUESTION_DIALOG=7 */
	public static final int QUESTION_DIALOG = 7;
	/** Constant WARNING_DIALOG=8 */
	public static final int WARNING_DIALOG = 8;
	// Konstanten aus java.awt.Frame damit Attribute aus Java 1.4 sich mit Java 1.3
	// uebersetzen lassen
	/** Constant MAXIMIZED_HORIZ=2 */
	public static final int MAXIMIZED_HORIZ = 2;
	/** Constant MAXIMIZED_VERT=4 */
	public static final int MAXIMIZED_VERT = 4;
	/** Constant MAXIMIZED_BOTH=MAXIMIZED_VERT | MAXIMIZED_HORIZ */
	public static final int MAXIMIZED_BOTH = MAXIMIZED_VERT | MAXIMIZED_HORIZ;
	private static final String[] borderKeys = new String[] { null, "RootPane.frameBorder",
			"RootPane.plainDialogBorder", "RootPane.informationDialogBorder", "RootPane.errorDialogBorder",
			"RootPane.colorChooserDialogBorder", "RootPane.fileChooserDialogBorder", "RootPane.questionDialogBorder",
			"RootPane.warningDialogBorder" };
	/**
	 * The minimum/maximum size of a Window
	 */
	private static final Dimension MINIMUM_SIZE = new Dimension(120, 80);
	private static final Dimension MAXIMUM_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	/**
	 * The amount of space (in pixels) that the cursor is changed on.
	 */
	private static final int CORNER_DRAG_WIDTH = 16;
	/**
	 * Region from edges that dragging is active from.
	 */
	private static final int BORDER_DRAG_THICKNESS = 5;
	/**
	 * Maps from positions to cursor type. Refer to calculateCorner and
	 * calculatePosition for details of this.
	 */
	private static final int[] cursorMapping = new int[] { Cursor.NW_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR,
			Cursor.N_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, 0, 0, 0,
			Cursor.NE_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR, 0, 0, 0, Cursor.E_RESIZE_CURSOR, Cursor.SW_RESIZE_CURSOR,
			0, 0, 0, Cursor.SE_RESIZE_CURSOR, Cursor.SW_RESIZE_CURSOR, Cursor.SW_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR,
			Cursor.SE_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR };

	/**
	 * Cursor used to track the cursor set by the user. This is
	 * initially Cursor.DEFAULT_CURSOR.
	 */
	/**
	 * {@inheritDoc}
	 *
	 * Creates a UI for a JRootPane.
	 */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseRootPaneUI();
	}

	/**
	 * Window the JRootPane is in.
	 */
	private Window window;
	/**
	 * JComponent providing window decorations. This will be null if
	 * not providing window decorations.
	 */
	private JComponent titlePane;
	/**
	 * MouseInputListener that is added to the parent
	 * Window the JRootPane is contained in.
	 */
	private MouseInputListener mouseInputListener;

	/**
	 * WindowListener that is added to the parent Window
	 * the JRootPane is contained in.
	 */
	private WindowListener windowListener;

	/**
	 * WindowListener that is added to the parent Window
	 * the JRootPane is contained in.
	 */
	private PropertyChangeListener propertyChangeListener;

	/**
	 * The LayoutManager that is set on the JRootPane.
	 */
	private LayoutManager layoutManager;

	/**
	 * LayoutManager of the JRootPane before we replaced
	 * it.
	 */
	private LayoutManager savedOldLayout;

	/**
	 * JRootPane providing the look and feel for.
	 */
	private JRootPane root;

	private Cursor savedCursor = null;

	/**
	 * Returns a LayoutManager that will be set on the
	 * JRootPane.
	 *
	 * @return The layout manager
	 */
	public LayoutManager createLayoutManager() {
		return new BaseRootLayout();
	}

	/**
	 * Returns the JComponent to render the window decoration style.
	 *
	 * @param root The root pane
	 * @return The title pane
	 */
	public JComponent createTitlePane(JRootPane root) {
		return new BaseTitlePane(root, this);
	}

	/**
	 * Returns a MouseListener that will be added to the
	 * Window containing the JRootPane.
	 *
	 * @param root The root pane
	 * @return The mouse listener
	 */
	public MouseInputListener createWindowMouseInputListener(JRootPane root) {
		return new MouseInputHandler();
	}

	/**
	 * <p>getRootPane.</p>
	 *
	 * @return a {@link javax.swing.JRootPane} object.
	 */
	public JRootPane getRootPane() {
		return root;
	}

	/**
	 * Returns the BaseTitlePane rendering the title pane. If this
	 * returns null, it implies there is no need to render window decorations.
	 *
	 * @return the current window title pane, or null
	 * @see #setTitlePane
	 */
	public BaseTitlePane getTitlePane() {
		if (titlePane instanceof BaseTitlePane) {
			return (BaseTitlePane) titlePane;
		}
		return null;
	}

	/**
	 * <p>installBorder.</p>
	 *
	 * @param root a {@link javax.swing.JRootPane} object.
	 */
	public void installBorder(JRootPane root) {
		int style = root.getWindowDecorationStyle();
		if (style == NONE) {
			LookAndFeel.uninstallBorder(root);
		} else {
			LookAndFeel.installBorder(root, borderKeys[style]);
		}
	}

	/**
	 * <p>installClientDecorations.</p>
	 *
	 * @param root a {@link javax.swing.JRootPane} object.
	 */
	public void installClientDecorations(JRootPane root) {
		installBorder(root);
		if (titlePane == null) {
			setTitlePane(root, createTitlePane(root));
		}
		installWindowListeners(root, root.getParent());
		installLayout(root);
		if (window != null) {
			// savedCursor = window.getCursor();
			root.revalidate();
			root.repaint();
		}
	}

	/**
	 * Installs the appropriate LayoutManager on the JRootPane to
	 * render the window decorations.
	 *
	 * @param root The root pane
	 */
	public void installLayout(JRootPane root) {
		if (layoutManager == null) {
			layoutManager = createLayoutManager();
		}
		savedOldLayout = root.getLayout();
		root.setLayout(layoutManager);
	}

	/** {@inheritDoc} */
	@Override
	protected void installListeners(JRootPane root) {
		super.installListeners(root);

		if (root.getWindowDecorationStyle() == NONE) {
			propertyChangeListener = evt -> {
				if ("ancestor".equals(evt.getPropertyName())) {
					if (getRootPane() != null && getRootPane().getParent() instanceof Window) {
						window = (Window) getRootPane().getParent();
						windowListener = new WindowAdapter() {

							@Override
							public void windowActivated(WindowEvent e) {
								if (getRootPane() != null) {
									getRootPane().repaint();
								}
							}

							@Override
							public void windowDeactivated(WindowEvent e) {
								if (getRootPane() != null) {
									getRootPane().repaint();
								}
							}
						};
						window.addWindowListener(windowListener);
					}
				}
			};
			root.addPropertyChangeListener(propertyChangeListener);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		root = (JRootPane) c;
		if (root.getWindowDecorationStyle() != NONE) {
			installClientDecorations(root);
		}
	}

	/**
	 * Installs the necessary Listeners on the parent Window, if there
	 * is one.
	 * <p>
	 * This takes the parent so that cleanup can be done from
	 * removeNotify, at which point the parent hasn't been reset yet.
	 *
	 * @param root   The root pane
	 * @param parent The parent of the JRootPane
	 */
	public void installWindowListeners(JRootPane root, Component parent) {
		if (parent instanceof Window) {
			window = (Window) parent;
		} else {
			window = SwingUtilities.getWindowAncestor(parent);
		}
		if (window != null) {
			if (mouseInputListener == null) {
				mouseInputListener = createWindowMouseInputListener(root);
			}
			window.addMouseListener(mouseInputListener);
			window.addMouseMotionListener(mouseInputListener);
		}
	}

	private JComponent internalGetTitlePane() {
		return titlePane;
	}

	private boolean isDynamicLayout() {
		return AbstractLookAndFeel.getTheme().isDynamicLayout();
	}

	/** {@inheritDoc} */
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		super.propertyChange(e);

		String propertyName = e.getPropertyName();
		JRootPane rp = (JRootPane) e.getSource();
		if ("windowDecorationStyle".equals(propertyName)) {
			int style = rp.getWindowDecorationStyle();

			// This is potentially more than needs to be done,
			// but it rarely happens and makes the install/uninstall process
			// simpler. BaseTitlePane also assumes it will be recreated if
			// the decoration style changes.
			uninstallClientDecorations(rp);
			if (style != NONE) {
				installClientDecorations(rp);
			}
		} else if ("ancestor".equals(propertyName)) {
			uninstallWindowListeners(rp);
			if (rp.getWindowDecorationStyle() != NONE) {
				installWindowListeners(rp, rp.getParent());
			}
		}
	}

	/**
	 * Sets the window title pane -- the JComponent used to provide a plaf a way to
	 * override the native operating system's window title pane with one whose look
	 * and feel are controlled by the plaf. The plaf creates and sets this value;
	 * the default is null, implying a native operating system window title pane.
	 *
	 * @param root      the JRootPane where to set the title pane
	 * @param titlePane the JComponent to use for the window title
	 *                  pane.
	 */
	public void setTitlePane(JRootPane root, JComponent titlePane) {
		JComponent oldTitlePane = internalGetTitlePane();
		if (oldTitlePane == null && titlePane == null || oldTitlePane != null && oldTitlePane.equals(titlePane)) {
			return;
		}
		JLayeredPane layeredPane = root.getLayeredPane();
		if (oldTitlePane != null) {
			oldTitlePane.setVisible(false);
			layeredPane.remove(oldTitlePane);
		}
		if (titlePane != null) {
			layeredPane.add(titlePane, JLayeredPane.FRAME_CONTENT_LAYER);
			titlePane.setVisible(true);
		}
		this.titlePane = titlePane;
	}

	/**
	 * Removes any border that may have been installed.
	 *
	 * @param root The root pane
	 */
	public void uninstallBorder(JRootPane root) {
		LookAndFeel.uninstallBorder(root);
	}

	/**
	 * <p>uninstallClientDecorations.</p>
	 *
	 * @param root a {@link javax.swing.JRootPane} object.
	 */
	public void uninstallClientDecorations(JRootPane root) {
		uninstallBorder(root);
		uninstallWindowListeners(root);
		setTitlePane(root, null);
		uninstallLayout(root);
		int style = root.getWindowDecorationStyle();
		if (style == NONE) {
			root.repaint();
			root.revalidate();
		}
		// Reset the cursor, as we may have changed it to a resize cursor
		if (window != null && savedCursor != null) {
			window.setCursor(savedCursor);
			savedCursor = null;
		}
		window = null;
	}

	/**
	 * <p>uninstallLayout.</p>
	 *
	 * @param root a {@link javax.swing.JRootPane} object.
	 */
	public void uninstallLayout(JRootPane root) {
		if (savedOldLayout != null) {
			root.setLayout(savedOldLayout);
			savedOldLayout = null;
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void uninstallListeners(JRootPane root) {
		super.uninstallListeners(root);
		if (root.getWindowDecorationStyle() == NONE) {
			if (root.getParent() instanceof Window && windowListener != null) {
				window = (Window) root.getParent();
				window.removeWindowListener(windowListener);
			}
			root.removePropertyChangeListener(propertyChangeListener);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		uninstallClientDecorations(root);
		layoutManager = null;
		mouseInputListener = null;
		root = null;
	}

	/**
	 * Uninstalls the necessary Listeners on the Window the Listeners
	 * were last installed on.
	 *
	 * @param root The root pane
	 */
	public void uninstallWindowListeners(JRootPane root) {
		if (window != null) {
			window.removeMouseListener(mouseInputListener);
			window.removeMouseMotionListener(mouseInputListener);
		}
	}

} // end of class BaseRootPaneUI
