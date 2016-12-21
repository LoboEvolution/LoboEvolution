/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;

import org.jdatepicker.JDatePicker;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.util.gui.WrapperLayout;

/**
 * The Class InputDatePickerControl.
 */
public class InputDatePickerControl extends BaseInputControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The date pattern. */
	private String datePattern = "dd/MM/yyyy";
	
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
		min = modelNode.getAttribute(HtmlAttributeProperties.MIN);
		max = modelNode.getAttribute(HtmlAttributeProperties.MAX);

		if (modelNode.getPattern() != null && modelNode.getPattern().length() > 0) {
			datePattern = modelNode.getPattern();
		}

		picker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar selectedValue = (Calendar) picker.getModel().getValue();
				Date selectedDate = selectedValue.getTime();
				SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
				modelNode.setValue(dateFormatter.format(selectedDate));
				if (isMin(selectedDate) || isMax(selectedDate)) {
					picker.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					picker.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
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
