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

import java.awt.Adjustable;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * <p>BaseScrollBarUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseScrollBarUI extends BasicScrollBarUI {

	// -----------------------------------------------------------------------------
	private static class InvisibleScrollButton extends JButton {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public InvisibleScrollButton() {
			super();
			setVisible(false);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(0, 0);
		}

	} // end of class InvisibleScrollButton
		// -----------------------------------------------------------------------------
// inner classes
//-----------------------------------------------------------------------------

	protected class MyTrackListener extends TrackListener {

		@Override
		public void mouseEntered(final MouseEvent e) {
			super.mouseEntered(e);
			isRollover = true;
			final Rectangle r = getTrackBounds();
			scrollbar.repaint(r.x, r.y, r.width, r.height);
		}

		@Override
		public void mouseExited(final MouseEvent e) {
			super.mouseExited(e);
			isRollover = false;
			final Rectangle r = getTrackBounds();
			scrollbar.repaint(r.x, r.y, r.width, r.height);
		}

		@Override
		public void mousePressed(final MouseEvent e) {
			super.mousePressed(e);
			final Rectangle r = getTrackBounds();
			scrollbar.repaint(r.x, r.y, r.width, r.height);
		}

		@Override
		public void mouseReleased(final MouseEvent e) {
			super.mouseReleased(e);
			final Rectangle r = getTrackBounds();
			scrollbar.repaint(r.x, r.y, r.width, r.height);
		}

	} // end of class MyTrackListener

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseScrollBarUI();
	}

	protected int scrollBarWidth = 17;

	protected int incrGap = 0;

	protected int decrGap = 0;

	protected boolean isRollover = false;

	/** {@inheritDoc} */
	@Override
	protected JButton createDecreaseButton(final int orientation) {
		if (AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
			return new InvisibleScrollButton();
		} else {
			return new BaseScrollButton(orientation, scrollBarWidth);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected JButton createIncreaseButton(final int orientation) {
		if (AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
			return new InvisibleScrollButton();
		} else {
			return new BaseScrollButton(orientation, scrollBarWidth);
		}
	}

	/** {@inheritDoc} */
	@Override
	public TrackListener createTrackListener() {
		return new MyTrackListener();
	}

	/** {@inheritDoc} */
	@Override
	protected Dimension getMinimumThumbSize() {
		return new Dimension(scrollBarWidth, scrollBarWidth);
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize(final JComponent c) {
		if (AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
			if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
				return new Dimension(scrollBarWidth + 1, scrollBarWidth * 3);
			} else {
				return new Dimension(scrollBarWidth * 3, scrollBarWidth);
			}
		} else {
			if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
				return new Dimension(scrollBarWidth, scrollBarWidth * 3 + 16);
			} else {
				return new Dimension(scrollBarWidth * 3 + 16, scrollBarWidth);
			}
		}
	}

	/**
	 * <p>getThumbColors.</p>
	 *
	 * @return an array of {@link java.awt.Color} objects.
	 */
	protected Color[] getThumbColors() {
		if (isRollover || isDragging) {
			return AbstractLookAndFeel.getTheme().getRolloverColors();
		} else if (!JTattooUtilities.isActive(scrollbar)) {
			return AbstractLookAndFeel.getTheme().getInActiveColors();
		} else {
			return AbstractLookAndFeel.getTheme().getThumbColors();
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void installDefaults() {
		super.installDefaults();

		scrollBarWidth = UIManager.getInt("ScrollBar.width");
		incrGap = UIManager.getInt("ScrollBar.incrementButtonGap");
		decrGap = UIManager.getInt("ScrollBar.decrementButtonGap");

		// TODO this can be removed when incrGap/decrGap become protected
		// handle scaling for sizeVarients for special case components. The
		// key "JComponent.sizeVariant" scales for large/small/mini
		// components are based on Apples LAF
		final String scaleKey = (String) scrollbar.getClientProperty("JComponent.sizeVariant");
		switch (scaleKey != null ? scaleKey : "") {
			case "large":
				scrollBarWidth = (int) (scrollBarWidth * 1.15);
				incrGap = (int) (incrGap * 1.15);
				decrGap = (int) (decrGap * 1.15);
				break;
			case "small":
				scrollBarWidth = (int) (scrollBarWidth * 0.857);
				incrGap = (int) (incrGap * 0.857);
				decrGap = (int) (decrGap * 0.857);
				break;
			case "mini":
				scrollBarWidth = (int) (scrollBarWidth * 0.714);
				incrGap = (int) (incrGap * 0.714);
				decrGap = (int) (decrGap * 0.714);
				break;
			default:
				break;
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void layoutHScrollbar(final JScrollBar sb) {
		if (AbstractLookAndFeel.getTheme().isLinuxStyleScrollBarOn() && incrButton.isVisible()
				&& decrButton.isVisible()) {
			final Dimension sbSize = sb.getSize();
			final Insets sbInsets = sb.getInsets();
			final int sizeW = sbSize.width - sbInsets.left - sbInsets.right;

			/*
			 * Height and top edge of the buttons and thumb.
			 */
			final int itemY = sbInsets.top;
			final int itemH = sbSize.height - (sbInsets.top + sbInsets.bottom);// Math.min(itemW, sizeH / 2);
			final int itemW = Math.min(itemH, sizeW / 2);// sbSize.width - (sbInsets.left + sbInsets.right);

			/*
			 * Nominal locations of the buttons, assuming their preferred size will fit.
			 */
			final int decrButtonX = sbSize.width - sbInsets.right - itemW - itemW + 1;
			final int incrButtonX = sbSize.width - sbInsets.right - itemW;

			/*
			 * Compute the width and origin of the thumb. The case where the thumb is at the
			 * right edge is handled specially to avoid numerical problems in computing
			 * thumbX. Enforce the thumbs min/max dimensions. If the thumb doesn't fit in
			 * the track (trackW) we'll hide it later.
			 */
			final float trackW = sbSize.width - sbInsets.left - sbInsets.right - itemH - itemH + 1;
			final float min = sb.getMinimum();
			final float max = sb.getMaximum();
			final float extent = sb.getVisibleAmount();
			final float range = max - min;
			final float value = sb.getValue();

			final int maxThumbW = getMaximumThumbSize().width;
			final int minThumbW = getMinimumThumbSize().width;
			int thumbW = range <= 0 ? maxThumbW : (int) (trackW * (extent / range));
			thumbW = Math.max(thumbW, minThumbW);
			thumbW = Math.min(thumbW, maxThumbW);

			int thumbX = decrButtonX - thumbW;
			if (value < max - extent) {
				final float thumbRange = trackW - thumbW;
				thumbX = (int) (0.5f + thumbRange * ((value - min) / (range - extent)));
			}

			/*
			 * If the thumb isn't going to fit, zero it's bounds. Otherwise make sure it
			 * fits between the buttons. Note that setting the thumbs bounds will cause a
			 * repaint.
			 */
			if (thumbW > trackW) {
				setThumbBounds(0, 0, 0, 0);
			} else {
				setThumbBounds(thumbX, itemY, thumbW, itemH);
			}
			decrButton.setBounds(decrButtonX, itemY, itemW, itemH);
			incrButton.setBounds(incrButtonX, itemY, itemW, itemH);

			/*
			 * Update the trackRect field.
			 */
			trackRect.setBounds(0, itemY, (int) trackW, itemH);

		} else {
			super.layoutHScrollbar(sb);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void layoutVScrollbar(final JScrollBar sb) {
		if (AbstractLookAndFeel.getTheme().isLinuxStyleScrollBarOn() && incrButton.isVisible()
				&& decrButton.isVisible()) {
			final Dimension sbSize = sb.getSize();
			final Insets sbInsets = sb.getInsets();
			final int sizeH = sbSize.height - sbInsets.top - sbInsets.bottom;

			/*
			 * Width and left edge of the buttons and thumb.
			 */
			final int itemX = sbInsets.left;
			final int itemW = sbSize.width - (sbInsets.left + sbInsets.right);
			final int itemH = Math.min(itemW, sizeH / 2);

			/*
			 * Nominal locations of the buttons, assuming their preferred size will fit.
			 */
			final int decrButtonY = sbSize.height - sbInsets.bottom - itemH - itemH + 1;
			final int incrButtonY = sbSize.height - sbInsets.bottom - itemH;

			/*
			 * Compute the height and origin of the thumb. The case where the thumb is at
			 * the bottom edge is handled specially to avoid numerical problems in computing
			 * thumbY. Enforce the thumbs min/max dimensions. If the thumb doesn't fit in
			 * the track (trackH) we'll hide it later.
			 */
			final float trackH = sbSize.height - sbInsets.top - sbInsets.bottom - itemW - itemW + 1;
			final float min = sb.getMinimum();
			final float max = sb.getMaximum();
			final float extent = sb.getVisibleAmount();
			final float range = max - min;
			final float value = sb.getValue();

			final int maxThumbH = getMaximumThumbSize().height;
			final int minThumbH = getMinimumThumbSize().height;
			int thumbH = range <= 0 ? maxThumbH : (int) (trackH * (extent / range));
			thumbH = Math.max(thumbH, minThumbH);
			thumbH = Math.min(thumbH, maxThumbH);

			int thumbY = decrButtonY - thumbH;
			if (value < max - extent) {
				final float thumbRange = trackH - thumbH;
				thumbY = (int) (0.5f + thumbRange * ((value - min) / (range - extent)));
			}

			/*
			 * If the thumb isn't going to fit, zero it's bounds. Otherwise make sure it
			 * fits between the buttons. Note that setting the thumbs bounds will cause a
			 * repaint.
			 */
			if (thumbH > trackH) {
				setThumbBounds(0, 0, 0, 0);
			} else {
				setThumbBounds(itemX, thumbY, itemW, thumbH);
			}
			decrButton.setBounds(itemX, decrButtonY, itemW, itemH);
			incrButton.setBounds(itemX, incrButtonY, itemW, itemH);

			/*
			 * Update the trackRect field.
			 */
			trackRect.setBounds(itemX, 0, itemW, (int) trackH);

		} else {
			super.layoutVScrollbar(sb);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintThumb(final Graphics g, final JComponent c, final Rectangle thumbBounds) {
		if (c.isEnabled()) {
			g.translate(thumbBounds.x, thumbBounds.y);
			final Color[] colors = getThumbColors();
			final Color frameColorHi = ColorHelper.brighter(colors[1], 20);
			final Color frameColorLo = ColorHelper.darker(colors[colors.length - 1], 10);
			final Graphics2D g2D = (Graphics2D) g;
			final Composite savedComposite = g2D.getComposite();
			if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
				JTattooUtilities.fillVerGradient(g, colors, 1, 1, thumbBounds.width - 1, thumbBounds.height - 1);
				JTattooUtilities.draw3DBorder(g, frameColorLo, ColorHelper.darker(frameColorLo, 15), 0, 0, thumbBounds.width, thumbBounds.height);
				g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				g.setColor(frameColorHi);
				g.drawLine(1, 1, thumbBounds.width - 2, 1);
				g.drawLine(1, 1, 1, thumbBounds.height - 2);

				if (!AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
					final int dx = 5;
					int dy = thumbBounds.height / 2 - 3;
					final int dw = thumbBounds.width - 11;

					final Color c1 = Color.white;
					final Color c2 = Color.darkGray;

					for (int i = 0; i < 4; i++) {
						g.setColor(c1);
						g.drawLine(dx, dy, dx + dw, dy);
						dy++;
						g.setColor(c2);
						g.drawLine(dx, dy, dx + dw, dy);
						dy++;
					}
				}
				g2D.setComposite(savedComposite);
			} else { // HORIZONTAL
				JTattooUtilities.fillHorGradient(g, colors, 1, 1, thumbBounds.width - 1, thumbBounds.height - 1);
				JTattooUtilities.draw3DBorder(g, frameColorLo, ColorHelper.darker(frameColorLo, 10), 0, 0, thumbBounds.width, thumbBounds.height);
				g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				g.setColor(frameColorHi);
				g.drawLine(1, 1, thumbBounds.width - 2, 1);
				g.drawLine(1, 1, 1, thumbBounds.height - 2);

				if (!AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
					int dx = thumbBounds.width / 2 - 3;
					final int dy = 5;
					final int dh = thumbBounds.height - 11;

					final Color c1 = Color.white;
					final Color c2 = Color.darkGray;

					for (int i = 0; i < 4; i++) {
						g.setColor(c1);
						g.drawLine(dx, dy, dx, dy + dh);
						dx++;
						g.setColor(c2);
						g.drawLine(dx, dy, dx, dy + dh);
						dx++;
					}
				}
			}
			g2D.setComposite(savedComposite);
			g.translate(-thumbBounds.x, -thumbBounds.y);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintTrack(final Graphics g, final JComponent c, final Rectangle trackBounds) {
		final int w = c.getWidth();
		final int h = c.getHeight();
		if (AbstractLookAndFeel.getTheme().isMacStyleScrollBarOn()) {
			final Color bc = ColorHelper.darker(AbstractLookAndFeel.getTheme().getBackgroundColor(), 4);
			g.setColor(bc);
			g.fillRect(0, 0, w, h);
		} else {
			if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
				JTattooUtilities.fillVerGradient(g, AbstractLookAndFeel.getTheme().getTrackColors(), 0, 0, w, h);
			} else {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getTrackColors(), 0, 0, w, h);
			}
		}
	}

} // end of class BaseScrollBarUI
