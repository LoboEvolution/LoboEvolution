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

import javax.swing.JButton;

import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.domimpl.HTMLInputElementImpl;
import org.lobo.common.WrapperLayout;

class InputButtonControl extends BaseInputControl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JButton widget;

	public InputButtonControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		final JButton widget = new JButton();
		widget.setContentAreaFilled(false);
		this.widget = widget;
		this.add(widget);
		widget.addActionListener(
				event -> HtmlController.getInstance().onPressed(InputButtonControl.this.controlElement, null, 0, 0));
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
	 * @see org.xamjwg.html.domimpl.InputContext#getValue()
	 */
	@Override
	public String getValue() {
		return this.widget.getText();
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		final RUIControl ruiControl = this.ruicontrol;
		final JButton button = this.widget;
		button.setContentAreaFilled(!ruiControl.hasBackground());
		final java.awt.Color foregroundColor = ruiControl.getForegroundColor();
		if (foregroundColor != null) {
			button.setForeground(foregroundColor);
		}
		final HTMLInputElementImpl element = (HTMLInputElementImpl) this.controlElement;
		String text = element.getAttribute("value");
		if (text == null || text.length() == 0) {
			final String type = element.getType();
			if ("submit".equalsIgnoreCase(type)) {
				text = "Submit Query";
			} else if ("reset".equalsIgnoreCase(type)) {
				text = "Reset";
			} else {
				text = "";
			}
		}
		button.setText(text);
	}

	@Override
	public void resetInput() {
		// nop
	}

	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		this.widget.setEnabled(!disabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		this.widget.setText(value);
	}
}
