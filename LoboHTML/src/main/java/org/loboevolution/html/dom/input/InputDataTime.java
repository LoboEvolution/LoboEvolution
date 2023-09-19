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
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>InputDataTime class.</p>
 */
public class InputDataTime extends BasicInput {

	private static final  Logger logger = Logger.getLogger(InputDataTime.class.getName());

	/**
	 * <p>Constructor for InputDataTime.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputDataTime(HTMLInputElementImpl modelNode, InputControl ic) {

		try {
			final String type = modelNode.getType();
			JFormattedTextField tf = null;
			MaskFormatter dateMask = null;

			switch (type.toLowerCase()) {
			case "datetime-local":
				tf = new JFormattedTextField(new SimpleDateFormat("dd/mm/yyyy hh:mm"));
				dateMask = new MaskFormatter("##/##/#### ##:##");
				break;
			case "time":
				tf = new JFormattedTextField(new SimpleDateFormat("hh:mm:ss"));
				dateMask = new MaskFormatter("##:##:##");
				break;
			case "month":
				tf = new JFormattedTextField(new SimpleDateFormat("mm/yyyy"));
				dateMask = new MaskFormatter("##/####");
				break;
			case "date":
			default:
				tf = new JFormattedTextField(new SimpleDateFormat("dd/mm/yyyy"));
				dateMask = new MaskFormatter("##/##/####");
				break;
			}

			setElement(modelNode);
			setjComponent(tf);
			final Dimension ps = tf.getPreferredSize();
			tf.setPreferredSize(new Dimension(128, ps.height));
			ic.add(tf);
			dateMask.install(tf);

			tf.addFocusListener(this);
			tf.addKeyListener(this);
			tf.addCaretListener(this);
			tf.addMouseListener(this);

		} catch (ParseException err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
	}
}
