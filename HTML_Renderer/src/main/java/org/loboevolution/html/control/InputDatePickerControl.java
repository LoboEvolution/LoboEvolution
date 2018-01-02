/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.control;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;

import org.jdatepicker.JDatePicker;
import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.util.Strings;
import org.loboevolution.util.gui.WrapperLayout;

/**
 * The Class InputDatePickerControl.
 */
public class InputDatePickerControl extends BaseInputControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The date pattern. */
	private static String datePattern = "dd/MM/yyyy";

	/** The min. */
	private String min = "";

	/** The max. */
	private String max = "";

	public InputDatePickerControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setLayout(WrapperLayout.getInstance());
		JDatePicker picker = new JDatePicker();
		picker.setTextEditable(true);
		picker.setShowYearButtons(true);
		min = modelNode.getAttribute(MIN);
		max = modelNode.getAttribute(MAX);

		if (!Strings.isBlank(modelNode.getPattern())) {
			datePattern = modelNode.getPattern();
		}

		picker.addActionListener(e -> {
			Calendar selectedValue = (Calendar) picker.getModel().getValue();
			Date selectedDate = selectedValue.getTime();
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
			modelNode.setValue(dateFormatter.format(selectedDate));
			if (isMin(selectedDate) || isMax(selectedDate)) {
				picker.setBorder(BorderFactory.createLineBorder(Color.RED));
			} else {
				picker.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		});

		this.add(picker);
	}

	@Override
	public void resetInput() {
		// TODO Auto-generated method stub

	}

	private boolean isMin(Date keyCode) {
		try {
			if (keyCode == null) {
				return true;
			}
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
			return keyCode.before(dateFormatter.parse(min));
		} catch (Exception ex) {
			return false;
		}
	}

	private boolean isMax(Date keyCode) {
		try {
			if (keyCode == null) {
				return true;
			}
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
			return keyCode.after(dateFormatter.parse(max));
		} catch (Exception ex) {
			return false;
		}
	}

}
