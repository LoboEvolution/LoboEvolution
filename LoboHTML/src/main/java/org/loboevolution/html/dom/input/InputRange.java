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

package org.loboevolution.html.dom.input;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.style.HtmlValues;

import javax.swing.*;
import java.awt.*;

/**
 * <p>InputRange class.</p>
 */
public class InputRange {

	/**
	 * <p>Constructor for InputRange.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputRange(HTMLInputElementImpl modelNode, InputControl ic) {
		HTMLDocumentImpl doc =  (HTMLDocumentImpl)modelNode.getDocumentNode();
		final int min = HtmlValues.getPixelSize(modelNode.getAttribute("min"), null, doc.getDefaultView(), 0);
		final int max = HtmlValues.getPixelSize(modelNode.getAttribute("max"), null, doc.getDefaultView(), 0);
		final int value = HtmlValues.getPixelSize(modelNode.getAttribute("value"), null, doc.getDefaultView(), 0);

		JSlider rangeSlider = new JSlider();
		rangeSlider.setPreferredSize(new Dimension(240, rangeSlider.getPreferredSize().height));
		rangeSlider.setMinimum(min);
		rangeSlider.setMaximum(max);
		rangeSlider.setValue(value);
		ic.add(rangeSlider);
	}
}
