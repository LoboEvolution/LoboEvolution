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
package org.loboevolution.html.control.input;

import java.awt.ComponentOrientation;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.gui.mouse.GuiMouseImpl;
import org.loboevolution.util.gui.WrapperLayout;

/**
 * The Class InputRadioControl.
 */
public class InputRadioControl extends BaseInputControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The widget. */
	private final JRadioButton widget;
	
	/** The button group. */
	private ButtonGroup buttonGroup;

	/**
	 * Instantiates a new input radio control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public InputRadioControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setLayout(WrapperLayout.getInstance());
		JRadioButton radio = new JRadioButton();
		radio.setOpaque(false);
		if (modelNode.getTitle() != null) {
			radio.setToolTipText(modelNode.getTitle());
		}
		radio.setVisible(modelNode.getHidden());
		radio.applyComponentOrientation(direction(modelNode.getDir()));
		radio.setEnabled(!modelNode.getDisabled());
		radio.setSelected(modelNode.getChecked());
		this.widget = radio;

		// Note: Value attribute cannot be set in reset() method.
		// Otherwise, layout revalidation causes typed values to
		// be lost (including revalidation due to hover.)

		HTMLElementImpl controlElement = this.controlElement;
		String name = controlElement.getAttribute(NAME);
		ButtonGroup prevGroup = this.buttonGroup;
		if (prevGroup != null) {
			prevGroup.remove(radio);
		}
		if (name != null) {
			String key = "cobra.radio.group." + name;
			ButtonGroup group = (ButtonGroup) controlElement.getDocumentItem(key);
			if (group == null) {
				group = new ButtonGroup();
				controlElement.setDocumentItem(key, group);
			}
			group.add(radio);
			this.buttonGroup = group;
		} else {
			this.buttonGroup = null;
		}
		radio.setSelected(controlElement.getAttributeAsBoolean("checked"));
		this.add(radio);

		widget.addActionListener(
				event -> GuiMouseImpl.getInstance().onPressed(InputRadioControl.this.controlElement, null, 0, 0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.control.BaseInputControl#reset(int, int)
	 */
	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.InputContext#click()
	 */
	@Override
	public void click() {
		this.widget.doClick();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.InputContext#getChecked()
	 */
	@Override
	public boolean getChecked() {
		return this.widget.isSelected();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.InputContext#setChecked(boolean)
	 */
	@Override
	public void setChecked(boolean checked) {
		this.widget.setSelected(checked);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.InputContext#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		this.widget.setEnabled(!disabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.InputContext#resetInput()
	 */
	@Override
	public void resetInput() {
		this.widget.setSelected(this.controlElement.getAttributeAsBoolean("checked"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.control.BaseInputControl#getValue()
	 */
	@Override
	public String getValue() {
		return this.controlElement.getAttribute(VALUE);
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
