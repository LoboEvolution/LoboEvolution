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
 * Author Michael Hagen
 *
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 */
	public static void draw3DBorder(final Graphics g, final Color c1, final Color c2, final int x, final int y, final int w, final int h) {
		final int x2 = x + w - 1;
		final int y2 = y + h - 1;
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 */
	public static void drawBorder(final Graphics g, final Color c, final int x, final int y, final int w, final int h) {
		g.setColor(c);
		g.drawRect(x, y, w - 1, h - 1);
	}

	/**
	 * <p>drawRound3DBorder.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c1 a {@link java.awt.Color} object.
	 * @param c2 a {@link java.awt.Color} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 */
	public static void drawRound3DBorder(final Graphics g, final Color c1, final Color c2, final int x, final int y, final int w, final int h) {
		final Graphics2D g2D = (Graphics2D) g;
		final int x2 = x + w;
		final int y2 = y + h;
		final int d = h;
		final int r = h / 2;
		final Color cm = ColorHelper.median(c1, c2);
		final Color c1m = ColorHelper.median(c1, cm);
		final Color c2m = ColorHelper.median(c2, cm);

		final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 * @param r a {@link java.lang.Integer} object.
	 */
	public static void drawRoundBorder(final Graphics g, final Color c, final int x, final int y, final int w, final int h, final int r) {
		final Graphics2D g2D = (Graphics2D) g;
		final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 */
	public static void drawString(final JComponent c, final Graphics g, final String text, final int x, final int y) {
		final Graphics2D g2D = (Graphics2D) g;
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
	 * @param underlinedIndex a {@link java.lang.Integer} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 */
	public static void drawStringUnderlineCharAt(final JComponent c, final Graphics g, final String text, final int underlinedIndex, final int x,
                                                 final int y) {
		final Graphics2D g2D = (Graphics2D) g;
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
	public static void fillComponent(final Graphics g, final Component c, final Icon texture) {
		int x = 0;
		int y = 0;
		final int w = c.getWidth();
		final int h = c.getHeight();
		if (texture != null) {
			final int tw = texture.getIconWidth();
			final int th = texture.getIconHeight();
			final Point p = JTattooUtilities.getRelLocation(c);
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 */
	public static void fillHorGradient(final Graphics g, final Color[] colors, final int x, final int y, final int w, final int h) {
		if (colors != null) {
			final int steps = colors.length;
			final double dy = (double) h / (double) steps;
			if (dy <= 2.001) {
				int y1 = y;
				for (int i = 0; i < steps; i++) {
					final int y2 = y + (int) Math.round(i * dy);
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 */
	public static void fillInverseHorGradient(final Graphics g, final Color[] colors, final int x, final int y, final int w, final int h) {
		if (colors != null) {
			final int steps = colors.length;
			final double dy = (double) h / (double) steps;
			if (dy <= 2.001) {
				int y1 = y;
				for (int i = 0; i < steps; i++) {
					final int y2 = y + (int) Math.round(i * dy);
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 */
	public static void fillInverseVerGradient(final Graphics g, final Color[] colors, final int x, final int y, final int w, final int h) {
		if (colors != null) {
			final int steps = colors.length;
			final double dx = (double) w / (double) steps;
			int x1 = x;
			for (int i = 0; i < steps; i++) {
				final int x2 = x + (int) Math.round(i * dx);
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 */
	public static void fillVerGradient(final Graphics g, final Color[] colors, final int x, final int y, final int w, final int h) {
		if (colors != null) {
			final int steps = colors.length;
			final double dx = (double) w / (double) steps;
			int x1 = x;
			for (int i = 0; i < steps; i++) {
				final int x2 = x + (int) Math.round(i * dx);
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
	 * @param maxWidth a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getClippedText(final String text, final FontMetrics fm, final int maxWidth) {
		if (text == null || text.length() == 0) {
			return "";
		}
		final int width = SwingUtilities.computeStringWidth(fm, text);
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
	public static FontMetrics getFontMetrics(final JComponent c, Graphics g, final Font f) {
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
	public static Dimension getFrameSize(final Component c) {
		final Container parent = getRootContainer(c);
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
				final String ver = System.getProperty("java.version");
				final StringBuilder version = new StringBuilder();
				boolean firstPoint = true;
				for (int i = 0; i < ver.length(); i++) {
					if (ver.charAt(i) == '.') {
						if (firstPoint) {
							version.append(ver.charAt(i));
						}
						firstPoint = false;
					} else if (Character.isDigit(ver.charAt(i))) {
						version.append(ver.charAt(i));
					}
				}
				javaVersion = Double.valueOf(version.toString());
			} catch (final NumberFormatException ex) {
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
				final String ver = System.getProperties().getProperty("os.version");
				final StringBuilder version = new StringBuilder();
				boolean firstPoint = true;
				for (int i = 0; i < ver.length(); i++) {
					if (ver.charAt(i) == '.') {
						if (firstPoint) {
							version.append(ver.charAt(i));
						}
						firstPoint = false;
					} else if (Character.isDigit(ver.charAt(i))) {
						version.append(ver.charAt(i));
					}
				}
				osVersion = Double.valueOf(version.toString());
			} catch (final NumberFormatException ex) {
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
	public static Point getRelLocation(final Component c) {
		if (c == null || !c.isShowing()) {
			return new Point(0, 0);
		}

		final Container parent = getRootContainer(c);
		if (parent != null && parent.isShowing() && c.isShowing()) {
			final Point p1 = c.getLocationOnScreen();
			final Point p2 = parent.getLocationOnScreen();
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
	public static Container getRootContainer(final Component c) {
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
	public static boolean isActive(final JComponent c) {
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
	public static boolean isFrameActive(final Component c) {
		if (c == null) {
			return false;
		}
		final Window w = SwingUtilities.getWindowAncestor(c);
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
	public static boolean isLeftToRight(final Component c) {
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 */
	public static void smoothFillHorGradient(final Graphics g, final Color[] colors, final int x, final int y, final int w, final int h) {
		if (colors != null) {
			final Graphics2D g2D = (Graphics2D) g;
			final Paint savedPaint = g2D.getPaint();
			final int steps = colors.length;
			final double dy = (double) h / (double) (steps - 1);
			int y1 = y;
			for (int i = 0; i < steps; i++) {
				final int y2 = y + (int) Math.round(i * dy);
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
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 */
	public static void smoothFillInverseHorGradient(final Graphics g, final Color[] colors, final int x, final int y, final int w, final int h) {
		if (colors != null) {
			final Graphics2D g2D = (Graphics2D) g;
			final Paint savedPaint = g2D.getPaint();
			final int steps = colors.length;
			final double dy = (double) h / (double) steps;
			int y1 = y;
			for (int i = 0; i < steps; i++) {
				final int y2 = y + (int) Math.round(i * dy);
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
