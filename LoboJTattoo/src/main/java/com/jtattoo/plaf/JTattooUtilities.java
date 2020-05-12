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
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
 * <p>JTattooUtilities class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class JTattooUtilities {

	private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("windows");
	private static final boolean IS_OS2 = System.getProperty("os.name").toLowerCase().contains("os/2");
	private static final boolean IS_MAC = System.getProperty("os.name").toLowerCase().contains("mac");
	private static final boolean IS_LINUX = System.getProperty("os.name").toLowerCase().contains("linux");
	private static final boolean IS_SUNOS = System.getProperty("os.name").toLowerCase().contains("sunos");
	private static final boolean IS_AIX = System.getProperty("os.name").toLowerCase().contains("aix");
	private static final boolean IS_HPUX = System.getProperty("os.name").toLowerCase().contains("hpux");
	private static final boolean IS_FREEBSD = System.getProperty("os.name").toLowerCase().contains("freebsd");
	private static final boolean IS_HIRES_SCREEN = Toolkit.getDefaultToolkit().getScreenSize().width > 1280;
	private static Double javaVersion = null;
	private static Double osVersion = null;
	private static final String ELLIPSIS = "...";

	/**
	 * <p>draw3DBorder.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c1 a {@link java.awt.Color} object.
	 * @param c2 a {@link java.awt.Color} object.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 */
	public static void draw3DBorder(Graphics g, Color c1, Color c2, int x, int y, int w, int h) {
		int x2 = x + w - 1;
		int y2 = y + h - 1;
		g.setColor(c1);
		g.drawLine(x, y, x2 - 1, y);
		g.drawLine(x, y + 1, x, y2);
		g.setColor(c2);
		g.drawLine(x, y2, x2 - 1, y2);
		g.drawLine(x2, y, x2, y2);
	}

	/**
	 * <p>drawBorder.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Color} object.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 */
	public static void drawBorder(Graphics g, Color c, int x, int y, int w, int h) {
		g.setColor(c);
		g.drawRect(x, y, w - 1, h - 1);
	}

	/**
	 * <p>drawRound3DBorder.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c1 a {@link java.awt.Color} object.
	 * @param c2 a {@link java.awt.Color} object.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 */
	public static void drawRound3DBorder(Graphics g, Color c1, Color c2, int x, int y, int w, int h) {
		Graphics2D g2D = (Graphics2D) g;
		int x2 = x + w;
		int y2 = y + h;
		int d = h;
		int r = h / 2;
		Color cm = ColorHelper.median(c1, c2);
		Color c1m = ColorHelper.median(c1, cm);
		Color c2m = ColorHelper.median(c2, cm);

		Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// oben
		g2D.setColor(c1);
		g2D.drawLine(x + r, y, x2 - r, y);
		// rechts
		g2D.drawLine(x, y + r, x, y2 - r);
		// unten
		g2D.setColor(c2);
		g2D.drawLine(x + r, y2, x2 - r, y2);
		// links
		g2D.drawLine(x2, y + r, x2, y2 - r);

		// links
		g2D.setColor(c1);
		g2D.drawArc(x, y, d, d, 90, 45);
		g2D.setColor(c1m);
		g2D.drawArc(x, y, d, d, 135, 45);
		g2D.setColor(cm);
		g2D.drawArc(x, y, d, d, 180, 45);
		g2D.setColor(c2m);
		g2D.drawArc(x, y, d, d, 225, 45);
		// rechts
		g2D.setColor(c1m);
		g2D.drawArc(x2 - d, y, d, d, 45, 45);
		g2D.setColor(cm);
		g2D.drawArc(x2 - d, y, d, d, 0, 45);
		g2D.setColor(c2m);
		g2D.drawArc(x2 - d, y, d, d, -45, 45);
		g2D.setColor(c2);
		g2D.drawArc(x2 - d, y, d, d, -90, 45);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
	}

	/**
	 * <p>drawRoundBorder.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Color} object.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 * @param r a int.
	 */
	public static void drawRoundBorder(Graphics g, Color c, int x, int y, int w, int h, int r) {
		Graphics2D g2D = (Graphics2D) g;
		Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setColor(c);
		g2D.drawRoundRect(x, y, w - 1, h - 1, r, r);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
	}

	/**
	 * <p>drawString.</p>
	 *
	 * @param c a {@link javax.swing.JComponent} object.
	 * @param g a {@link java.awt.Graphics} object.
	 * @param text a {@link java.lang.String} object.
	 * @param x a int.
	 * @param y a int.
	 */
	public static void drawString(JComponent c, Graphics g, String text, int x, int y) {
		Graphics2D g2D = (Graphics2D) g;
		Object savedRenderingHint = null;
		if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
			savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
		}
		g2D.drawString(text, x, y);
		if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
		}
	}

	/**
	 * <p>drawStringUnderlineCharAt.</p>
	 *
	 * @param c a {@link javax.swing.JComponent} object.
	 * @param g a {@link java.awt.Graphics} object.
	 * @param text a {@link java.lang.String} object.
	 * @param underlinedIndex a int.
	 * @param x a int.
	 * @param y a int.
	 */
	public static void drawStringUnderlineCharAt(JComponent c, Graphics g, String text, int underlinedIndex, int x,
			int y) {
		Graphics2D g2D = (Graphics2D) g;
		Object savedRenderingHint = null;
		if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
			savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
		}
		BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, underlinedIndex, x, y);
		if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
		}
	}

	/**
	 * <p>fillComponent.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Component} object.
	 * @param texture a {@link javax.swing.Icon} object.
	 */
	public static void fillComponent(Graphics g, Component c, Icon texture) {
		int x = 0;
		int y = 0;
		int w = c.getWidth();
		int h = c.getHeight();
		if (texture != null) {
			int tw = texture.getIconWidth();
			int th = texture.getIconHeight();
			Point p = JTattooUtilities.getRelLocation(c);
			y = -p.y;
			while (y < h) {
				x = -p.x;
				while (x < w) {
					texture.paintIcon(c, g, x, y);
					x += tw;
				}
				y += th;
			}
		} else {
			g.setColor(c.getBackground());
			g.fillRect(x, y, w, h);
		}
	}

	/**
	 * <p>fillHorGradient.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param colors an array of {@link java.awt.Color} objects.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 */
	public static void fillHorGradient(Graphics g, Color[] colors, int x, int y, int w, int h) {
		if (colors != null) {
			int steps = colors.length;
			double dy = (double) h / (double) steps;
			if (dy <= 2.001) {
				int y1 = y;
				for (int i = 0; i < steps; i++) {
					int y2 = y + (int) Math.round(i * dy);
					g.setColor(colors[i]);
					if (i == steps - 1) {
						g.fillRect(x, y1, w, y + h - y1);
					} else {
						g.fillRect(x, y1, w, y2 - y1);
					}
					y1 = y2;
				}
			} else {
				smoothFillHorGradient(g, colors, x, y, w, h);
			}
		}
	}

	/**
	 * <p>fillInverseHorGradient.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param colors an array of {@link java.awt.Color} objects.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 */
	public static void fillInverseHorGradient(Graphics g, Color[] colors, int x, int y, int w, int h) {
		if (colors != null) {
			int steps = colors.length;
			double dy = (double) h / (double) steps;
			if (dy <= 2.001) {
				int y1 = y;
				for (int i = 0; i < steps; i++) {
					int y2 = y + (int) Math.round(i * dy);
					g.setColor(colors[colors.length - i - 1]);
					if (i == steps - 1) {
						g.fillRect(x, y1, w, y + h - y1);
					} else {
						g.fillRect(x, y1, w, y2 - y1);
					}
					y1 = y2;
				}
			} else {
				smoothFillInverseHorGradient(g, colors, x, y, w, h);
			}
		}
	}

	/**
	 * <p>fillInverseVerGradient.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param colors an array of {@link java.awt.Color} objects.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 */
	public static void fillInverseVerGradient(Graphics g, Color[] colors, int x, int y, int w, int h) {
		if (colors != null) {
			int steps = colors.length;
			double dx = (double) w / (double) steps;
			int x1 = x;
			for (int i = 0; i < steps; i++) {
				int x2 = x + (int) Math.round(i * dx);
				g.setColor(colors[colors.length - i - 1]);
				if (i == steps - 1) {
					g.fillRect(x1, y, x + w - x1, h);
				} else {
					g.fillRect(x1, y, x2 - x1, h);
				}
				x1 = x2;
			}
		}
	}

	/**
	 * <p>fillVerGradient.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param colors an array of {@link java.awt.Color} objects.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 */
	public static void fillVerGradient(Graphics g, Color[] colors, int x, int y, int w, int h) {
		if (colors != null) {
			int steps = colors.length;
			double dx = (double) w / (double) steps;
			int x1 = x;
			for (int i = 0; i < steps; i++) {
				int x2 = x + (int) Math.round(i * dx);
				g.setColor(colors[i]);
				if (i == steps - 1) {
					g.fillRect(x1, y, x + w - x1, h);
				} else {
					g.fillRect(x1, y, x2 - x1, h);
				}
				x1 = x2;
			}
		}
	}

	/**
	 * <p>getClippedText.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @param fm a {@link java.awt.FontMetrics} object.
	 * @param maxWidth a int.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getClippedText(String text, FontMetrics fm, int maxWidth) {
		if (text == null || text.length() == 0) {
			return "";
		}
		int width = SwingUtilities.computeStringWidth(fm, text);
		if (width > maxWidth) {
			int totalWidth = SwingUtilities.computeStringWidth(fm, ELLIPSIS);
			for (int i = 0; i < text.length(); i++) {
				totalWidth += fm.charWidth(text.charAt(i));
				if (totalWidth > maxWidth) {
					return text.substring(0, i) + ELLIPSIS;
				}
			}
		}
		return text;
	}

	/**
	 * <p>getFontMetrics.</p>
	 *
	 * @param c a {@link javax.swing.JComponent} object.
	 * @param g a {@link java.awt.Graphics} object.
	 * @param f a {@link java.awt.Font} object.
	 * @return a {@link java.awt.FontMetrics} object.
	 */
	public static FontMetrics getFontMetrics(JComponent c, Graphics g, Font f) {
		FontMetrics fm = null;
		if (fm == null) {
			if (g == null) {
				if (c != null) {
					g = c.getGraphics();
				}
			}
			if (g != null) {
				if (f != null) {
					fm = g.getFontMetrics(f);
				} else {
					fm = g.getFontMetrics();
				}
			} else if (c != null) {
				if (f != null) {
					fm = c.getFontMetrics(f);
				} else {
					fm = c.getFontMetrics(c.getFont());
				}
			}
		}
		return fm;
	}

	/**
	 * <p>getFrameSize.</p>
	 *
	 * @param c a {@link java.awt.Component} object.
	 * @return a {@link java.awt.Dimension} object.
	 */
	public static Dimension getFrameSize(Component c) {
		Container parent = getRootContainer(c);
		if (parent != null) {
			return parent.getSize();
		}
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	/**
	 * <p>Getter for the field javaVersion.</p>
	 *
	 * @return a double.
	 */
	public static double getJavaVersion() {
		if (javaVersion == null) {
			try {
				String ver = System.getProperty("java.version");
				String version = "";
				boolean firstPoint = true;
				for (int i = 0; i < ver.length(); i++) {
					if (ver.charAt(i) == '.') {
						if (firstPoint) {
							version += ver.charAt(i);
						}
						firstPoint = false;
					} else if (Character.isDigit(ver.charAt(i))) {
						version += ver.charAt(i);
					}
				}
				javaVersion = Double.valueOf(version);
			} catch (NumberFormatException ex) {
				javaVersion = 1.3;
			}
		}
		return javaVersion;
	}

	/**
	 * <p>getOSVersion.</p>
	 *
	 * @return a double.
	 */
	public static double getOSVersion() {
		if (osVersion == null) {
			try {
				String ver = System.getProperties().getProperty("os.version");
				String version = "";
				boolean firstPoint = true;
				for (int i = 0; i < ver.length(); i++) {
					if (ver.charAt(i) == '.') {
						if (firstPoint) {
							version += ver.charAt(i);
						}
						firstPoint = false;
					} else if (Character.isDigit(ver.charAt(i))) {
						version += ver.charAt(i);
					}
				}
				osVersion = Double.valueOf(version);
			} catch (NumberFormatException ex) {
				osVersion = 1.0;
			}
		}
		return osVersion;
	}

	/**
	 * <p>getRelLocation.</p>
	 *
	 * @param c a {@link java.awt.Component} object.
	 * @return a {@link java.awt.Point} object.
	 */
	public static Point getRelLocation(Component c) {
		if (c == null || !c.isShowing()) {
			return new Point(0, 0);
		}

		Container parent = getRootContainer(c);
		if (parent != null && parent.isShowing() && c.isShowing()) {
			Point p1 = c.getLocationOnScreen();
			Point p2 = parent.getLocationOnScreen();
			return new Point(p1.x - p2.x, p1.y - p2.y);
		}

		return new Point(0, 0);
	}

	/**
	 * <p>getRootContainer.</p>
	 *
	 * @param c a {@link java.awt.Component} object.
	 * @return a {@link java.awt.Container} object.
	 */
	public static Container getRootContainer(Component c) {
		if (c == null) {
			return null;
		}
		Container parent = c.getParent();
		while (parent != null && !(parent instanceof JPopupMenu) && !(parent instanceof JInternalFrame)
				&& !(parent instanceof Window) && parent.getParent() != null) {
			parent = parent.getParent();
		}
		return parent;
	}

	/**
	 * <p>isActive.</p>
	 *
	 * @param c a {@link javax.swing.JComponent} object.
	 * @return a boolean.
	 */
	public static boolean isActive(JComponent c) {
		if (c == null) {
			return false;
		}
		boolean active = true;
		if (c instanceof JInternalFrame) {
			active = ((JInternalFrame) c).isSelected();
		}
		if (active) {
			Container parent = c.getParent();
			while (parent != null) {
				if (parent instanceof JInternalFrame) {
					active = ((JInternalFrame) parent).isSelected();
					break;
				}
				parent = parent.getParent();
			}
		}
		if (active) {
			active = isFrameActive(c);
		}
		return active;
	}

	/**
	 * <p>isAIX.</p>
	 *
	 * @return a boolean.
	 */
	public static boolean isAIX() {
		return IS_AIX;
	}

	/**
	 * <p>isFrameActive.</p>
	 *
	 * @param c a {@link java.awt.Component} object.
	 * @return a boolean.
	 */
	public static boolean isFrameActive(Component c) {
		if (c == null) {
			return false;
		}
		Window w = SwingUtilities.getWindowAncestor(c);
		if (w != null) {
			if (w.getClass().getName().contains("Popup")) {
				return true;
			} else {
				return w.isActive();
			}
		}

		return true;
	}

	/**
	 * <p>isFreeBSD.</p>
	 *
	 * @return a boolean.
	 */
	public static boolean isFreeBSD() {
		return IS_FREEBSD;
	}

	/**
	 * <p>isHiresScreen.</p>
	 *
	 * @return a boolean.
	 */
	public static boolean isHiresScreen() {
		return IS_HIRES_SCREEN;
	}

	/**
	 * <p>isHPUX.</p>
	 *
	 * @return a boolean.
	 */
	public static boolean isHPUX() {
		return IS_HPUX;
	}

	/**
	 * <p>isLeftToRight.</p>
	 *
	 * @param c a {@link java.awt.Component} object.
	 * @return a boolean.
	 */
	public static boolean isLeftToRight(Component c) {
		if (c == null) {
			return true;
		}
		return c.getComponentOrientation().isLeftToRight();
	}

	/**
	 * <p>isLinux.</p>
	 *
	 * @return a boolean.
	 */
	public static boolean isLinux() {
		return IS_LINUX;
	}

	/**
	 * <p>isMac.</p>
	 *
	 * @return a boolean.
	 */
	public static boolean isMac() {
		return IS_MAC;
	}

	/**
	 * <p>isOS2.</p>
	 *
	 * @return a boolean.
	 */
	public static boolean isOS2() {
		return IS_OS2;
	}

	// -------------------------------------------------------------------------------------------

	/**
	 * <p>isSunOS.</p>
	 *
	 * @return a boolean.
	 */
	public static boolean isSunOS() {
		return IS_SUNOS;
	}

	/**
	 * <p>isWindows.</p>
	 *
	 * @return a boolean.
	 */
	public static boolean isWindows() {
		return IS_WINDOWS;
	}

	/**
	 * <p>smoothFillHorGradient.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param colors an array of {@link java.awt.Color} objects.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 */
	public static void smoothFillHorGradient(Graphics g, Color[] colors, int x, int y, int w, int h) {
		if (colors != null) {
			Graphics2D g2D = (Graphics2D) g;
			Paint savedPaint = g2D.getPaint();
			int steps = colors.length;
			double dy = (double) h / (double) (steps - 1);
			int y1 = y;
			for (int i = 0; i < steps; i++) {
				int y2 = y + (int) Math.round(i * dy);
				if (i == steps - 1) {
					g2D.setPaint(null);
					g2D.setColor(colors[i]);
					g2D.fillRect(x, y1, w, y + h - y1);
				} else {
					g2D.setPaint(new GradientPaint(0, y1, colors[i], 0, y2, colors[i + 1]));
					g2D.fillRect(x, y1, w, y2 - y1);
				}
				y1 = y2;
			}
			g2D.setPaint(savedPaint);
		}
	}

	/**
	 * <p>smoothFillInverseHorGradient.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param colors an array of {@link java.awt.Color} objects.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 */
	public static void smoothFillInverseHorGradient(Graphics g, Color[] colors, int x, int y, int w, int h) {
		if (colors != null) {
			Graphics2D g2D = (Graphics2D) g;
			Paint savedPaint = g2D.getPaint();
			int steps = colors.length;
			double dy = (double) h / (double) steps;
			int y1 = y;
			for (int i = 0; i < steps; i++) {
				int y2 = y + (int) Math.round(i * dy);
				g.setColor(colors[colors.length - i - 1]);
				if (i == steps - 1) {
					g2D.setPaint(null);
					g2D.setColor(colors[colors.length - i - 1]);
					g.fillRect(x, y1, w, y + h - y1);
				} else {
					g2D.setPaint(new GradientPaint(0, y1, colors[colors.length - i - 1], 0, y2,
							colors[colors.length - i - 2]));
					g.fillRect(x, y1, w, y2 - y1);
				}
				y1 = y2;
			}
			g2D.setPaint(savedPaint);
		}
	}

} // end of class JTattooUtilities
