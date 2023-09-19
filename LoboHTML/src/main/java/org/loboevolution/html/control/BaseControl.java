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
 * Created on Oct 23, 2005
 */
package org.loboevolution.html.control;

import org.loboevolution.html.AlignValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Abstract BaseControl class.</p>
 */
public abstract class BaseControl extends JComponent implements UIControl {
	private static final long serialVersionUID = 1L;
	protected final HTMLElementImpl controlElement;
	protected RUIControl ruicontrol;

	/**
	 * <p>Constructor for BaseControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public BaseControl(HTMLElementImpl modelNode) {
		this.controlElement = modelNode;
	}


	/** {@inheritDoc} */
	@Override
	public Color getBackgroundColor() {
		return getBackground();
	}

	/** {@inheritDoc} */
	@Override
	public Component getComponent() {
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int getVAlign() {
		return AlignValues.BASELINE.getValue();
	}

	/** {@inheritDoc} */
	@Override
	public void reset(final int availWidth, final int availHeight) {
	}

	/** {@inheritDoc} */
	@Override
	public void setRUIControl(RUIControl ruicontrol) {
		this.ruicontrol = ruicontrol;
	}
	
	/**
	 * <p>getRUIControl.</p>
	 *
	 * @return a {@link org.loboevolution.html.control.RUIControl} object.
	 */
	public RUIControl getRUIControl() {
		return ruicontrol;
	}
}
