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

package com.jtattoo.plaf.fast;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseSliderUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>FastSliderUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class FastSliderUI extends BaseSliderUI {

	// ------------------------------------------------------------------------------
// inner classes
//------------------------------------------------------------------------------    
	private static class ThumbHorIcon implements Icon {

		private static final int WIDTH = 11;
		private static final int HEIGHT = 18;

		@Override
		public int getIconHeight() {
			return HEIGHT;
		}

		@Override
		public int getIconWidth() {
			return WIDTH;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			int w = WIDTH - 1;
			int h = HEIGHT - 1;
			int dw = WIDTH / 2;
			Color backColor = AbstractLookAndFeel.getControlBackgroundColor();
			Color loColor = AbstractLookAndFeel.getFrameColor();
			Color hiColor = ColorHelper.brighter(backColor, 40);
			Polygon poly = new Polygon();
			poly.addPoint(x, y);
			poly.addPoint(x + w, y);
			poly.addPoint(x + w, y + h - dw);
			poly.addPoint(x + dw, y + h);
			poly.addPoint(x, y + h - dw);
			g.setColor(backColor);
			g.fillPolygon(poly);
			g.setColor(loColor);
			g.drawPolygon(poly);
			g.setColor(hiColor);
			g.drawLine(x + 1, y + 1, x + w - 1, y + 1);
			g.drawLine(x + 1, y + 1, x + 1, y + h - dw);
		}

	} // end of class ThumbHorIcon

	private static class ThumbVerIcon implements Icon {

		private static final int WIDTH = 18;
		private static final int HEIGHT = 11;

		@Override
		public int getIconHeight() {
			return HEIGHT;
		}

		@Override
		public int getIconWidth() {
			return WIDTH;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			int w = WIDTH - 1;
			int h = HEIGHT - 1;
			int dh = HEIGHT / 2;
			Color backColor = AbstractLookAndFeel.getControlBackgroundColor();
			Color loColor = AbstractLookAndFeel.getFrameColor();
			Color hiColor = ColorHelper.brighter(backColor, 40);
			Polygon poly = new Polygon();
			poly.addPoint(x, y);
			poly.addPoint(x + w - dh, y);
			poly.addPoint(x + w, y + dh);
			poly.addPoint(x + w - dh, y + h);
			poly.addPoint(x, y + h);
			g.setColor(backColor);
			g.fillPolygon(poly);
			g.setColor(loColor);
			g.drawPolygon(poly);
			g.setColor(hiColor);
			g.drawLine(x + 1, y + 1, x + w - dh, y + 1);
			g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
		}
	} // end of class ThumbVerIcon

	private static final ThumbHorIcon THUMB_HOR_ICON = new ThumbHorIcon();

	private static final ThumbVerIcon THUMB_VER_ICON = new ThumbVerIcon();

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new FastSliderUI((JSlider) c);
	}

	/**
	 * <p>Constructor for FastSliderUI.</p>
	 *
	 * @param slider a {@link javax.swing.JSlider} object.
	 */
	public FastSliderUI(JSlider slider) {
		super(slider);
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbHorIcon() {
		return THUMB_HOR_ICON;
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbHorIconRollover() {
		return THUMB_HOR_ICON;
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIcon() {
		return THUMB_VER_ICON;
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIconRollover() {
		return THUMB_VER_ICON;
	}

	/** {@inheritDoc} */
	@Override
	public void paintTrack(Graphics g) {
		boolean leftToRight = JTattooUtilities.isLeftToRight(slider);

		g.translate(trackRect.x, trackRect.y);
		int overhang = 5;
		int trackLeft = 0;
		int trackTop = 0;
		int trackRight = 0;
		int trackBottom = 0;

		// Draw the track
		if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
			trackBottom = trackRect.height - 1 - overhang;
			trackTop = trackBottom - (getTrackWidth() - 1);
			trackRight = trackRect.width - 1;
		} else {
			if (leftToRight) {
				trackLeft = trackRect.width - overhang - getTrackWidth();
				trackRight = trackRect.width - overhang - 1;
			} else {
				trackLeft = overhang;
				trackRight = overhang + getTrackWidth() - 1;
			}
			trackBottom = trackRect.height - 1;
		}

		g.setColor(Color.gray);
		g.drawRect(trackLeft, trackTop, trackRight - trackLeft - 1, trackBottom - trackTop - 1);

		int middleOfThumb = 0;
		int fillTop = 0;
		int fillLeft = 0;
		int fillBottom = 0;
		int fillRight = 0;

		if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
			middleOfThumb = thumbRect.x + thumbRect.width / 2;
			middleOfThumb -= trackRect.x; // To compensate for the g.translate()
			fillTop = trackTop + 1;
			fillBottom = trackBottom - 2;

			if (!drawInverted()) {
				fillLeft = trackLeft + 1;
				fillRight = middleOfThumb;
			} else {
				fillLeft = middleOfThumb;
				fillRight = trackRight - 2;
			}
//            if (slider.isEnabled()) {
			g.setColor(AbstractLookAndFeel.getControlBackgroundColor());
			g.fillRect(fillLeft, fillTop, fillRight - fillLeft, fillBottom - fillTop + 1);
//            } else {
//                g.setColor(slider.getBackground());
//                g.fillRect(fillLeft, fillTop, fillRight - fillLeft, fillBottom - fillTop);
//            }
		} else {
			middleOfThumb = thumbRect.y + thumbRect.height / 2;
			middleOfThumb -= trackRect.y; // To compensate for the g.translate()
			fillLeft = trackLeft + 1;
			fillRight = trackRight - 2;

			if (!drawInverted()) {
				fillTop = middleOfThumb;
				fillBottom = trackBottom - 2;
			} else {
				fillTop = trackTop + 1;
				fillBottom = middleOfThumb;
			}
//            if (slider.isEnabled()) {
			g.setColor(AbstractLookAndFeel.getControlBackgroundColor());
			g.fillRect(fillLeft, fillTop, fillRight - fillLeft + 1, fillBottom - fillTop + 1);
//            } else {
//                g.setColor(slider.getBackground());
//                g.fillRect(fillLeft, fillTop, fillRight - fillLeft + 1, fillBottom - fillTop + 1);
//            }
		}

		g.translate(-trackRect.x, -trackRect.y);
	}

} // end of class FastSliderUI
