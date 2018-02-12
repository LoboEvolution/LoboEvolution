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
import java.awt.ComponentOrientation;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.util.gui.WrapperLayout;

/**
 * The Class InputColorPickerControl.
 */
public class InputColorPickerControl extends BaseInputControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The widget. */
	private JButton widget = new JButton();

	/**
	 * Instantiates a new input button control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public InputColorPickerControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setLayout(WrapperLayout.getInstance());
		widget.setContentAreaFilled(false);

		if (modelNode.getTitle() != null) {
			widget.setToolTipText(modelNode.getTitle());
		}
		widget.setVisible(modelNode.getHidden());
		widget.applyComponentOrientation(direction(modelNode.getDir()));
		widget.setEnabled(!modelNode.getDisabled());
		widget.setPreferredSize(new Dimension(90, 20));
		this.add(widget);
		widget.addActionListener(event -> {
			Color c = JColorChooser.showDialog(null, "Choose a Color", null);
			String value = "#" + Integer.toHexString(c.getRGB()).substring(2);
			modelNode.setValue(value);
			widget.setToolTipText(value);
			widget.setBackground(c);

		});
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		RUIControl ruiControl = this.ruicontrol;
		JButton button = this.widget;
		button.setContentAreaFilled(!ruiControl.hasBackground());
		Color foregroundColor = ruiControl.getForegroundColor();
		if (foregroundColor != null) {
			button.setForeground(foregroundColor);
		}
		button.setText("");
	}

	@Override
	public void click() {
		this.widget.doClick();
	}

	@Override
	public String getValue() {
		return this.widget.getText();
	}

	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		this.widget.setEnabled(!disabled);
	}

	@Override
	public void setValue(String value) {
		this.widget.setText(value);
	}

	@Override
	public void resetInput() {
		// Method not implemented
	}

	/**
	 * Direction.
	 *
	 * @param dir
	 *            the dir
	 * @return the component orientation
	 */
	private ComponentOrientation direction(String dir) {

		if ("ltr".equalsIgnoreCase(dir)) {
			return ComponentOrientation.LEFT_TO_RIGHT;
		} else if ("rtl".equalsIgnoreCase(dir)) {
			return ComponentOrientation.RIGHT_TO_LEFT;
		} else {
			return ComponentOrientation.UNKNOWN;
		}
	}

}
