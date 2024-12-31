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

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractIconFactory;

/**
 * <p>TextureIconFactory class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class TextureIconFactory extends AbstractIconFactory {

	private static TextureIconFactory instance = null;

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link com.jtattoo.plaf.texture.TextureIconFactory} object.
	 */
	public static synchronized TextureIconFactory getInstance() {
		if (instance == null) {
			instance = new TextureIconFactory();
		}
		return instance;
	}

	private TextureIconFactory() {
	}

	/** {@inheritDoc} */
	@Override
	public Icon getCloseIcon() {
		return TextureIcons.getCloseIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getDownArrowIcon() {
		return TextureIcons.getDownArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getIconIcon() {
		return TextureIcons.getIconIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getLeftArrowIcon() {
		return TextureIcons.getLeftArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMaxIcon() {
		return TextureIcons.getMaxIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMinIcon() {
		return TextureIcons.getMinIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getRightArrowIcon() {
		return TextureIcons.getRightArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterHorBumpIcon() {
		return TextureIcons.getSplitterHorBumpIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterVerBumpIcon() {
		return TextureIcons.getSplitterVerBumpIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbHorIcon() {
		return TextureIcons.getThumbHorIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbHorIconRollover() {
		return TextureIcons.getThumbHorIconRollover();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIcon() {
		return TextureIcons.getThumbVerIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIconRollover() {
		return TextureIcons.getThumbVerIconRollover();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getUpArrowIcon() {
		return TextureIcons.getUpArrowIcon();
	}

} // end of class TextureIconFactory
