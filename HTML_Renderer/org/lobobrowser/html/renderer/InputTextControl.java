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

import javax.swing.text.JTextComponent;

import org.lobobrowser.html.dombl.JTextFieldImpl;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;

public class InputTextControl extends BaseInputTextControl {

	private static final long serialVersionUID = 1L;

	public InputTextControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		JTextFieldImpl text =  (JTextFieldImpl) this.widget;
		
		if(modelNode.getTitle() != null)
			text.setToolTipText(modelNode.getTitle());
		
		text.setVisible(modelNode.getHidden());
		text.applyComponentOrientation(direction(modelNode.getDir()));
		text.setEditable(new Boolean(modelNode.getContentEditable() == null ? "true" : modelNode.getContentEditable()));
		text.setEnabled(!modelNode.getDisabled());
		text.setPlaceholder(modelNode.getPlaceholder());
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				HtmlController.getInstance().onEnterPressed(modelNode, null);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.render.BaseInputTextControl#createTextField(java.lang
	 * .String)
	 */
	protected JTextComponent createTextField() {
		return new JTextFieldImpl();
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
