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
package org.loboevolution.html.layout;


import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.control.input.BaseInputControl;
import org.loboevolution.html.control.input.InputButtonControl;
import org.loboevolution.html.control.input.InputCheckboxControl;
import org.loboevolution.html.control.input.InputColorPickerControl;
import org.loboevolution.html.control.input.InputDatePickerControl;
import org.loboevolution.html.control.input.InputEmailControl;
import org.loboevolution.html.control.input.InputFileControl;
import org.loboevolution.html.control.input.InputHiddenControl;
import org.loboevolution.html.control.input.InputImageControl;
import org.loboevolution.html.control.input.InputNumberControl;
import org.loboevolution.html.control.input.InputPasswordControl;
import org.loboevolution.html.control.input.InputPhoneControl;
import org.loboevolution.html.control.input.InputRadioControl;
import org.loboevolution.html.control.input.InputTextControl;
import org.loboevolution.html.control.input.InputUrlControl;
import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.rendererblock.RBlockViewport;

/**
 * The Class InputLayout.
 */
public class InputLayout extends CommonWidgetLayout {

	/**
	 * Instantiates a new input layout.
	 */
	public InputLayout() {
		super(ADD_INLINE, true);
	}

	@Override
	protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
		HTMLBaseInputElement bie = (HTMLBaseInputElement) markupElement;
		BaseInputControl uiControl = createInputControl(bie);
		if (uiControl == null) {
			return null;
		}
		bie.setInputContext(uiControl);
		return new RUIControl(markupElement, uiControl, bodyLayout.getContainer(), bodyLayout.getFrameContext(),
				bodyLayout.getUserAgentContext());
	}

	/**
	 * Creates the input control.
	 *
	 * @param markupElement
	 *            the markup element
	 * @return the base input control
	 */
	private final BaseInputControl createInputControl(HTMLBaseInputElement markupElement) {
		String type = markupElement.getAttribute(TYPE) == null ? ""
				: markupElement.getAttribute(TYPE);

		switch (type) {
		case "text":
			return new InputTextControl(markupElement);
		case "hidden":
			return new InputHiddenControl(markupElement);
		case "submit":
			return new InputButtonControl(markupElement);
		case "password":
			return new InputPasswordControl(markupElement);
		case "file":
			return new InputFileControl(markupElement);
		case "number":
			return new InputNumberControl(markupElement);
		case "email":
			return new InputEmailControl(markupElement);
		case "color":
			return new InputColorPickerControl(markupElement);
		case "url":
			return new InputUrlControl(markupElement);
		case "tel":
			return new InputPhoneControl(markupElement);
		case "date":
		case "datetime":
		case "datetime-local":
		case "month":
			return new InputDatePickerControl(markupElement);
		case "radio":
			return new InputRadioControl(markupElement);
		case "checkbox":
			return new InputCheckboxControl(markupElement);
		case "button":
			return new InputButtonControl(markupElement);
		case "image":
			return new InputImageControl(markupElement);
		case "reset":
			return new InputButtonControl(markupElement);
		default:
			return new InputTextControl(markupElement);
		}
	}
}
