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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

/**
 * <p>InputDataTime class.</p>
 *
 *
 *
 */
public class InputDataTime {

	private final static Logger logger = Logger.getLogger(InputDataTime.class.getName());

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

			final Dimension ps = tf.getPreferredSize();
			tf.setPreferredSize(new Dimension(128, ps.height));
			ic.add(tf);
			dateMask.install(tf);
		} catch (ParseException err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
	}
}
