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
/*
 * Created on Nov 5, 2005
 */
package org.loboevolution.html.control;

import org.loboevolution.html.AlignValues;

import java.awt.*;

/**
 * <p>Abstract UIControl interface.</p>
 *
 *
 *
 */
public interface UIControl {
	/**
	 * <p>getBackgroundColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getBackgroundColor();

	/**
	 * <p>getComponent.</p>
	 *
	 * @return a {@link java.awt.Component} object.
	 */
	Component getComponent();

	/**
	 * <p>getPreferredSize.</p>
	 *
	 * @return a {@link java.awt.Dimension} object.
	 */
	Dimension getPreferredSize();

	/**
	 * <p>getVAlign.</p>
	 *
	 * @return a int.
	 */
	default int getVAlign() {
		return AlignValues.BASELINE.getValue();
	}

	/**
	 * <p>invalidate.</p>
	 */
	void invalidate();

	/**
	 * <p>paint.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 */
	void paint(Graphics g);

	/**
	 * <p>reset.</p>
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 */
	void reset(int availWidth, int availHeight);

	/**
	 * <p>setBounds.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @param width a int.
	 * @param height a int.
	 */
	void setBounds(int x, int y, int width, int height);

	/**
	 * <p>setRUIControl.</p>
	 *
	 * @param ruicontrol a {@link org.loboevolution.html.control.RUIControl} object.
	 */
	void setRUIControl(RUIControl ruicontrol);
}
