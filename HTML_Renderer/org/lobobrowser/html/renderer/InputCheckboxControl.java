/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.util.gui.WrapperLayout;

public class InputCheckboxControl extends BaseInputControl {

	private static final long serialVersionUID = 1L;
	private final JCheckBox widget;

	public InputCheckboxControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setLayout(WrapperLayout.getInstance());
		JCheckBox checkBox = new JCheckBox();
		checkBox.setOpaque(false);
		this.widget = checkBox;
		
		if(modelNode.getTitle() != null)
			checkBox.setToolTipText(modelNode.getTitle());
		checkBox.setVisible(modelNode.getHidden());
		checkBox.applyComponentOrientation(direction(modelNode.getDir()));
		checkBox.setSelected(this.controlElement.getAttributeAsBoolean("checked"));
		checkBox.setEnabled(!modelNode.getDisabled());
		checkBox.setSelected(modelNode.getChecked());
		widget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				HtmlController.getInstance().onPressed(
						InputCheckboxControl.this.controlElement, null, 0, 0);
			}
		});

		this.add(checkBox);
	}

	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#click()
	 */
	public void click() {
		this.widget.doClick();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#getChecked()
	 */
	public boolean getChecked() {
		return this.widget.isSelected();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setChecked(boolean)
	 */
	public void setChecked(boolean checked) {
		this.widget.setSelected(checked);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setDisabled(boolean)
	 */
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		this.widget.setEnabled(!disabled);
	}

	public void resetInput() {
		this.widget.setSelected(this.controlElement
				.getAttributeAsBoolean("checked"));
	}

	public String getValue() {
		return this.controlElement.getAttribute(HtmlAttributeProperties.VALUE);
	}
	
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
