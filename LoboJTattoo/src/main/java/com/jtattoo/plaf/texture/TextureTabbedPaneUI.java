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
package com.jtattoo.plaf.texture;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.text.View;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseTabbedPaneUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>TextureTabbedPaneUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureTabbedPaneUI extends BaseTabbedPaneUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new TextureTabbedPaneUI();
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getContentBorderColors(final int tabPlacement) {
		final Color c = AbstractLookAndFeel.getTheme().getSelectionBackgroundColorDark();
		return new Color[] { getLoBorderColor(0), c, c, c, ColorHelper.darker(c, 10) };
	}

	/** {@inheritDoc} */
	@Override
	protected Color getLoBorderColor(final int tabIndex) {
		if (tabIndex == tabPane.getSelectedIndex() && tabPane.getBackgroundAt(tabIndex) instanceof ColorUIResource
				&& AbstractLookAndFeel.getTheme().isDarkTexture()) {
			return ColorHelper.darker(super.getLoBorderColor(tabIndex), 20);
		}
		return AbstractLookAndFeel.getFrameColor();
	}

	/**
	 * <p>getSelectedTexture.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getSelectedTexture() {
		return TextureUtils.SELECTED_TEXTURE_TYPE;
	}

	/** {@inheritDoc} */
	@Override
	protected Font getTabFont(final boolean isSelected) {
		if (isSelected) {
			return super.getTabFont(isSelected).deriveFont(Font.BOLD);
		} else {
			return super.getTabFont(isSelected);
		}
	}

	/**
	 * <p>getTexture.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getTexture() {
		return TextureUtils.BACKGROUND_TEXTURE_TYPE;
	}

	/**
	 * <p>getUnSelectedTexture.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getUnSelectedTexture(final int tabIndex) {
		if (tabIndex == rolloverIndex && tabPane.isEnabledAt(tabIndex)) {
			return TextureUtils.ROLLOVER_TEXTURE_TYPE;
		}
		return TextureUtils.ALTER_BACKGROUND_TEXTURE_TYPE;
	}

	/** {@inheritDoc} */
	@Override
	protected void installComponents() {
		simpleButtonBorder = true;
		super.installComponents();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintContentBorder(final Graphics g, final int tabPlacement, final int selectedIndex, final int x, final int y, final int w, final int h) {
		final int textureType = TextureUtils.getTextureType(tabPane);
		final int tabAreaHeight = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
		final int tabAreaWidth = calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
		// paint the background
		if (tabPane.isOpaque()) {
			final int xt = tabPlacement == RIGHT ? w - tabAreaWidth : 0;
			final int yt = tabPlacement == BOTTOM ? h - tabAreaHeight : 0;
			final int wt = tabPlacement == TOP || tabPlacement == BOTTOM ? w : tabAreaWidth;
			final int ht = tabPlacement == LEFT || tabPlacement == RIGHT ? h : tabAreaHeight;
			g.setColor(tabAreaBackground);
			g.fillRect(xt, yt, wt, ht);
		}
		if (isContentOpaque()) {
			final int xt = tabPlacement == LEFT ? tabAreaWidth : 0;
			final int yt = tabPlacement == TOP ? tabAreaHeight : 0;
			final int wt = tabPlacement == LEFT || tabPlacement == RIGHT ? w - tabAreaWidth : w;
			final int ht = tabPlacement == TOP || tabPlacement == BOTTOM ? h - tabAreaHeight : h;
			TextureUtils.fillComponent(g, tabPane, xt, yt, wt, ht, textureType);
		}

		final int sepHeight = tabAreaInsets.bottom;
		if (sepHeight > 0) {
			Insets bi = new Insets(0, 0, 0, 0);
			if (tabPane.getBorder() != null) {
				bi = tabPane.getBorder().getBorderInsets(tabPane);
			}
			switch (tabPlacement) {
			case TOP: {
				TextureUtils.fillComponent(g, tabPane, x, y + tabAreaHeight - sepHeight + bi.top, w, sepHeight,
						getSelectedTexture());
				if (textureType == TextureUtils.MENUBAR_TEXTURE_TYPE) {
					final Graphics2D g2D = (Graphics2D) g;
					final Composite saveComposite = g2D.getComposite();
					AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
					g2D.setComposite(alpha);
					g2D.setColor(Color.black);
					g2D.drawLine(x, y, w, y);
					g2D.drawLine(w, y, w, y + tabAreaHeight - sepHeight);
					alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
					g2D.setComposite(alpha);
					g2D.setColor(Color.white);
					g2D.drawLine(x, y + tabAreaHeight - sepHeight, w, y + tabAreaHeight - sepHeight);
					alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
					g2D.setComposite(alpha);
					g2D.drawLine(x, y + 1, x, y + tabAreaHeight - sepHeight - 1);
					g2D.setComposite(saveComposite);
				}
				break;
			}
			case LEFT: {
				TextureUtils.fillComponent(g, tabPane, x + tabAreaWidth - sepHeight + bi.left, y, sepHeight, h,
						getSelectedTexture());
				break;
			}
			case BOTTOM: {
				TextureUtils.fillComponent(g, tabPane, x, y + h - tabAreaHeight - bi.bottom, w, sepHeight,
						getSelectedTexture());
				break;
			}
			case RIGHT: {
				TextureUtils.fillComponent(g, tabPane, x + w - tabAreaWidth - bi.right, y, sepHeight, h,
						getSelectedTexture());
				break;
			}
			default:
				break;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintTabBackground(final Graphics g, final int tabPlacement, final int tabIndex, final int x, final int y, final int w, final int h,
			final boolean isSelected) {
		final Color backColor = tabPane.getBackgroundAt(tabIndex);
		if (!(backColor instanceof UIResource) || !AbstractLookAndFeel.getTheme().isDarkTexture()) {
			super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
			return;
		}
		if (isTabOpaque() || isSelected) {
			final Graphics2D g2D = (Graphics2D) g;
			final Composite savedComposite = g2D.getComposite();
			final Shape savedClip = g.getClip();
			Area orgClipArea = new Area(new Rectangle2D.Double(x, y, w, h));
			if (savedClip != null) {
				orgClipArea = new Area(savedClip);
			}
			final int d = 2 * GAP;
			switch (tabPlacement) {
			case TOP:
			default:
				if (isSelected) {
					final Area clipArea = new Area(new RoundRectangle2D.Double(x, y, w, h + 4, d, d));
					final Area rectArea = new Area(new Rectangle2D.Double(x, y, w, h + 1));
					clipArea.intersect(rectArea);
					clipArea.intersect(orgClipArea);
					g2D.setClip(clipArea);
					TextureUtils.fillRect(g, tabPane, x, y, w, h + 4, getSelectedTexture());
					g2D.setClip(savedClip);
				} else {
					final Area clipArea = new Area(new RoundRectangle2D.Double(x, y, w, h + 4, d, d));
					final Area rectArea = new Area(new Rectangle2D.Double(x, y, w, h));
					clipArea.intersect(rectArea);
					clipArea.intersect(orgClipArea);
					g2D.setClip(clipArea);
					TextureUtils.fillRect(g, tabPane, x, y, w, h + 4, getUnSelectedTexture(tabIndex));
					final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
					g2D.setComposite(alpha);
					final Color[] colors = AbstractLookAndFeel.getTheme().getButtonColors();
					JTattooUtilities.fillHorGradient(g, colors, x, y, w, h + 4);
					g2D.setComposite(savedComposite);
					g2D.setClip(savedClip);
				}
				break;
			case LEFT:
				if (isSelected) {
					TextureUtils.fillComponent(g, tabPane, x + 1, y + 1, w, h - 1, getSelectedTexture());
				} else {
					TextureUtils.fillComponent(g, tabPane, x + 1, y + 1, w - 1, h - 1, getUnSelectedTexture(tabIndex));
				}
				break;
			case BOTTOM:
				if (isSelected) {
					final Area clipArea = new Area(new RoundRectangle2D.Double(x, y - 4, w, h + 4, d, d));
					final Area rectArea = new Area(new Rectangle2D.Double(x, y - 1, w, h + 1));
					clipArea.intersect(rectArea);
					clipArea.intersect(orgClipArea);
					g2D.setClip(clipArea);
					TextureUtils.fillRect(g, tabPane, x, y - 4, w, h + 4, getSelectedTexture());
					g2D.setClip(savedClip);
				} else {
					final Area clipArea = new Area(new RoundRectangle2D.Double(x, y - 4, w, h + 4, d, d));
					final Area rectArea = new Area(new Rectangle2D.Double(x, y, w, h));
					clipArea.intersect(rectArea);
					clipArea.intersect(orgClipArea);
					g2D.setClip(clipArea);
					TextureUtils.fillRect(g, tabPane, x, y - 4, w, h + 4, getUnSelectedTexture(tabIndex));
					final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
					g2D.setComposite(alpha);
					final Color[] colors = AbstractLookAndFeel.getTheme().getButtonColors();
					JTattooUtilities.fillHorGradient(g, colors, x, y - 4, w, h + 4);
					g2D.setComposite(savedComposite);
					g2D.setClip(savedClip);
				}
				break;
			case RIGHT:
				if (isSelected) {
					TextureUtils.fillComponent(g, tabPane, x, y + 1, w, h - 1, getSelectedTexture());
				} else {
					TextureUtils.fillComponent(g, tabPane, x, y + 1, w, h - 1, getUnSelectedTexture(tabIndex));
				}
				break;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(final Graphics g, final int tabPlacement, final Font font, final FontMetrics metrics, final int tabIndex, final String title,
			final Rectangle textRect, final boolean isSelected) {
		final Color backColor = tabPane.getBackgroundAt(tabIndex);
		if (!(backColor instanceof UIResource) || !AbstractLookAndFeel.getTheme().isDarkTexture()) {
			super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
			return;
		}
		g.setFont(font);
		final View v = getTextViewForTab(tabIndex);
		if (v != null) {
			// html
			final Graphics2D g2D = (Graphics2D) g;
			Object savedRenderingHint = null;
			if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
				savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
				g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
			}
			v.paint(g, textRect);
			if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
				g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
			}
		} else {
			// plain text
			final int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

			if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
				if (isSelected) {
					final Color titleColor = AbstractLookAndFeel.getTabSelectionForegroundColor();
					if (ColorHelper.getGrayValue(titleColor) > 164) {
						g.setColor(Color.black);
					} else {
						g.setColor(Color.white);
					}
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x + 1,
							textRect.y + 1 + metrics.getAscent());
					g.setColor(titleColor);
					final Graphics2D g2D = (Graphics2D) g;
					final Shape savedClip = g2D.getClip();

					Area clipArea = new Area(
							new Rectangle2D.Double(textRect.x, textRect.y, textRect.width, textRect.height / 2 + 1));
					if (savedClip != null) {
						clipArea.intersect(new Area(savedClip));
					}
					g2D.setClip(clipArea);
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
							textRect.y + metrics.getAscent());

					clipArea = new Area(new Rectangle2D.Double(textRect.x, textRect.y + textRect.height / 2 + 1,
							textRect.width, textRect.height));
					if (savedClip != null) {
						clipArea.intersect(new Area(savedClip));
					}
					g2D.setClip(clipArea);
					g2D.setColor(ColorHelper.darker(titleColor, 20));
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
							textRect.y + metrics.getAscent());
					g2D.setClip(savedClip);
				} else {
					g.setColor(tabPane.getForegroundAt(tabIndex));
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
							textRect.y + metrics.getAscent());
				}

			} else { // tab disabled
				final Graphics2D g2D = (Graphics2D) g;
				final Composite savedComposite = g2D.getComposite();
				final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
				g2D.setComposite(alpha);
				g.setColor(Color.white);
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
						textRect.y + metrics.getAscent() + 1);
				g2D.setComposite(savedComposite);
				g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
						textRect.y + metrics.getAscent());
			}
		}
	}

} // end of class TextureTabbedPaneUI
