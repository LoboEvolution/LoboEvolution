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

package com.jtattoo.plaf.acryl;

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractIconFactory;

/**
 * <p>AcrylIconFactory class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class AcrylIconFactory extends AbstractIconFactory {

	private static AcrylIconFactory instance = null;

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link com.jtattoo.plaf.acryl.AcrylIconFactory} object.
	 */
	public static synchronized AcrylIconFactory getInstance() {
		if (instance == null) {
			instance = new AcrylIconFactory();
		}
		return instance;
	}

	private AcrylIconFactory() {
	}

	/** {@inheritDoc} */
	@Override
	public Icon getCheckBoxIcon() {
		return AcrylIcons.getCheckBoxIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getCloseIcon() {
		return AcrylIcons.getCloseIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getIconIcon() {
		return AcrylIcons.getIconIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMaxIcon() {
		return AcrylIcons.getMaxIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMinIcon() {
		return AcrylIcons.getMinIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getRadioButtonIcon() {
		return AcrylIcons.getRadioButtonIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbHorIcon() {
		return AcrylIcons.getThumbHorIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbHorIconRollover() {
		return AcrylIcons.getThumbHorIconRollover();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIcon() {
		return AcrylIcons.getThumbVerIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIconRollover() {
		return AcrylIcons.getThumbVerIconRollover();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeCollapsedIcon() {
		return AcrylIcons.getTreeCollapsedIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeExpandedIcon() {
		return AcrylIcons.getTreeExpandedIcon();
	}

} // end of class AcrylIconFactory
