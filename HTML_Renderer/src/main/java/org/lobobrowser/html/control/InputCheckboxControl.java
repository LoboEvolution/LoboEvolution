/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
/*
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.control;

import java.awt.ComponentOrientation;

import javax.swing.JCheckBox;

import org.lobobrowser.html.domimpl.DOMEventImpl;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;

import org.lobobrowser.util.gui.WrapperLayout;

/**
 * The Class InputCheckboxControl.
 */
public class InputCheckboxControl extends BaseInputControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The widget. */
	private final JCheckBox widget;

	/**
	 * Instantiates a new input checkbox control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public InputCheckboxControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setLayout(WrapperLayout.getInstance());
		JCheckBox checkBox = new JCheckBox();
		checkBox.setOpaque(false);
		this.widget = checkBox;

		if (modelNode.getTitle() != null) {
			checkBox.setToolTipText(modelNode.getTitle());
		}
		checkBox.setVisible(modelNode.getHidden());
		checkBox.applyComponentOrientation(direction(modelNode.getDir()));
		checkBox.setSelected(this.controlElement.getAttributeAsBoolean("checked"));
		checkBox.setEnabled(!modelNode.getDisabled());
		checkBox.setSelected(modelNode.getChecked());
		widget.addActionListener(
				event -> DOMEventImpl.getInstance().onPressed(InputCheckboxControl.this.controlElement, null, 0, 0));

		this.add(checkBox);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.control.BaseInputControl#reset(int, int)
	 */
	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#click()
	 */
	@Override
	public void click() {
		this.widget.doClick();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#getChecked()
	 */
	@Override
	public boolean getChecked() {
		return this.widget.isSelected();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setChecked(boolean)
	 */
	@Override
	public void setChecked(boolean checked) {
		this.widget.setSelected(checked);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		this.widget.setEnabled(!disabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#resetInput()
	 */
	@Override
	public void resetInput() {
		this.widget.setSelected(this.controlElement.getAttributeAsBoolean("checked"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.control.BaseInputControl#getValue()
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
