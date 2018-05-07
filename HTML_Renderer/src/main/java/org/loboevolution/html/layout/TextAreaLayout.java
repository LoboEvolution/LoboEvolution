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

import org.loboevolution.html.control.BaseInputControl;
import org.loboevolution.html.control.InputTextAreaControl;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.rendererblock.RBlockViewport;

/**
 * The Class TextAreaLayout.
 */
public class TextAreaLayout extends CommonWidgetLayout {

	/**
	 * Instantiates a new text area layout.
	 */
	public TextAreaLayout() {
		super(ADD_INLINE, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.layout.CommonWidgetLayout#createRenderable(org.
	 * loboevolution .html.renderer.RBlockViewport,
	 * org.loboevolution.html.domimpl.HTMLElementImpl)
	 */
	@Override
	protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
		HTMLBaseInputElement bie = (HTMLBaseInputElement) markupElement;
		BaseInputControl control = new InputTextAreaControl(bie);
		bie.setInputContext(control);
		return new RUIControl(markupElement, control, bodyLayout.getContainer(), bodyLayout.getFrameContext(),
				bodyLayout.getUserAgentContext());
	}
}
