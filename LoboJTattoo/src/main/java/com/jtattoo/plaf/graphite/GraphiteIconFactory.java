/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package com.jtattoo.plaf.graphite;

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractIconFactory;

/**
 * <p>GraphiteIconFactory class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class GraphiteIconFactory extends AbstractIconFactory {

	private static GraphiteIconFactory instance = null;

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link com.jtattoo.plaf.graphite.GraphiteIconFactory} object.
	 */
	public static synchronized GraphiteIconFactory getInstance() {
		if (instance == null) {
			instance = new GraphiteIconFactory();
		}
		return instance;
	}

	private GraphiteIconFactory() {
	}

	/** {@inheritDoc} */
	@Override
	public Icon getCloseIcon() {
		return GraphiteIcons.getCloseIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getIconIcon() {
		return GraphiteIcons.getIconIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMinIcon() {
		return GraphiteIcons.getMinIcon();
	}

} // end of class GraphiteIconFactory
