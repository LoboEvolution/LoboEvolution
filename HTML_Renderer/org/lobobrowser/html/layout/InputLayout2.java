/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

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

    Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.layout;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.renderer.BaseInputControl;
import org.lobobrowser.html.renderer.InputButtonControl;
import org.lobobrowser.html.renderer.InputCheckboxControl;
import org.lobobrowser.html.renderer.InputFileControl;
import org.lobobrowser.html.renderer.InputImageControl;
import org.lobobrowser.html.renderer.InputPasswordControl;
import org.lobobrowser.html.renderer.InputRadioControl;
import org.lobobrowser.html.renderer.InputTextControl;
import org.lobobrowser.html.renderer.RBlockViewport;
import org.lobobrowser.html.renderer.RElement;
import org.lobobrowser.html.renderer.RUIControl;

public class InputLayout2 extends CommonWidgetLayout {
	public InputLayout2() {
		super(ADD_INLINE, true);
	}

	protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
		HTMLBaseInputElement bie = (HTMLBaseInputElement) markupElement;
		BaseInputControl uiControl = createInputControl(bie);
		if (uiControl == null) {
			return null;
		}
		bie.setInputContext(uiControl);
		return new RUIControl(markupElement, uiControl,
				bodyLayout.getContainer(), bodyLayout.getFrameContext(),
				bodyLayout.getUserAgentContext());
	}

	private final BaseInputControl createInputControl(HTMLBaseInputElement markupElement) {
		String type = markupElement.getAttribute(HtmlAttributeProperties.TYPE);
		if (type == null) {
			return new InputTextControl(markupElement);
		}
		type = type.toLowerCase();
		if ("text".equals(type) || type.length() == 0) {
			return new InputTextControl(markupElement);
		} else if ("hidden".equals(type)) {
			return null;
		} else if ("submit".equals(type)) {
			return new InputButtonControl(markupElement);
		} else if ("password".equals(type)) {
			return new InputPasswordControl(markupElement);
		} else if ("radio".equals(type)) {
			return new InputRadioControl(markupElement);
		} else if ("checkbox".equals(type)) {
			return new InputCheckboxControl(markupElement);
		} else if ("image".equals(type)) {
			return new InputImageControl(markupElement);
		} else if ("reset".equals(type)) {
			return new InputButtonControl(markupElement);
		} else if ("button".equals(type)) {
			return new InputButtonControl(markupElement);
		} else if ("file".equals(type)) {
			return new InputFileControl(markupElement);
		} else {
			return null;
		}
	}
}