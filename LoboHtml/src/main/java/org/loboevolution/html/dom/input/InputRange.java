/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.input;

import java.awt.Dimension;

import javax.swing.JSlider;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>InputRange class.</p>
 *
 *
 *
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
		final int min = HtmlValues.getPixelSize(modelNode.getAttribute("min"), null, doc.getWindow(), 0);
		final int max = HtmlValues.getPixelSize(modelNode.getAttribute("max"), null, doc.getWindow(), 0);
		final int value = HtmlValues.getPixelSize(modelNode.getAttribute("value"), null, doc.getWindow(), 0);

		JSlider rangeSlider = new JSlider();
		rangeSlider.setPreferredSize(new Dimension(240, rangeSlider.getPreferredSize().height));
		rangeSlider.setMinimum(min);
		rangeSlider.setMaximum(max);
		rangeSlider.setValue(value);
		ic.add(rangeSlider);
	}
}
