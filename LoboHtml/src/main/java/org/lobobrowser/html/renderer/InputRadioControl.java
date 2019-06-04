/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.renderer;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.util.gui.WrapperLayout;

class InputRadioControl extends BaseInputControl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ButtonGroup buttonGroup;

	private final JRadioButton widget;

	public InputRadioControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		final JRadioButton radio = new JRadioButton();
		radio.setOpaque(false);
		this.widget = radio;

		// Note: Value attribute cannot be set in reset() method.
		// Otherwise, layout revalidation causes typed values to
		// be lost (including revalidation due to hover.)

		final HTMLElementImpl controlElement = this.controlElement;
		final String name = controlElement.getAttribute("name");
		final ButtonGroup prevGroup = this.buttonGroup;
		if (prevGroup != null) {
			prevGroup.remove(radio);
		}
		if (name != null) {
			final String key = "cobra.radio.group." + name;
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#click()
	 */
	@Override
	public void click() {
		this.widget.doClick();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getChecked()
	 */
	@Override
	public boolean getChecked() {
		return this.widget.isSelected();
	}

	@Override
	public String getValue() {
		return this.controlElement.getAttribute("value");
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
	}

	@Override
	public void resetInput() {
		this.widget.setSelected(this.controlElement.getAttributeAsBoolean("checked"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setChecked(boolean)
	 */
	@Override
	public void setChecked(boolean checked) {
		this.widget.setSelected(checked);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		this.widget.setEnabled(!disabled);
	}
}