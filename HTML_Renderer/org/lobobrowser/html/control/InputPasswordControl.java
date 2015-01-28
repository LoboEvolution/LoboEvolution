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
package org.lobobrowser.html.control;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.text.JTextComponent;

import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.renderer.HtmlController;

public class InputPasswordControl extends BaseInputTextControl {

	private static final long serialVersionUID = 1L;

	public InputPasswordControl(final HTMLBaseInputElement modelNode) {super(modelNode);
	JPasswordField pwd =  (JPasswordField) this.widget;
	
	if(modelNode.getTitle() != null)
		pwd.setToolTipText(modelNode.getTitle());
	
	pwd.setVisible(modelNode.getHidden());
	pwd.applyComponentOrientation(direction(modelNode.getDir()));
	pwd.setEditable(new Boolean(modelNode.getContentEditable() == null ? "true" : modelNode.getContentEditable()));
	pwd.setEnabled(!modelNode.getDisabled());
	pwd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			HtmlController.getInstance().onEnterPressed(modelNode, null);
		}
	});
}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.render.InputTextControl#createTextField(String
	 * )
	 */
	protected JTextComponent createTextField() {
		return new JPasswordField();
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
