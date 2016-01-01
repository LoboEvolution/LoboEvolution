/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.layout;

import org.lobobrowser.html.control.BaseInputControl;
import org.lobobrowser.html.control.InputButtonControl;
import org.lobobrowser.html.control.RUIControl;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.renderer.RBlockViewport;
import org.lobobrowser.html.renderer.RElement;

/**
 * The Class ButtonLayout.
 */
public class ButtonLayout extends CommonWidgetLayout {

	/**
	 * Instantiates a new button layout.
	 */
	public ButtonLayout() {
		super(ADD_INLINE, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.layout.CommonWidgetLayout#createRenderable(org.
	 * lobobrowser .html.renderer.RBlockViewport,
	 * org.lobobrowser.html.domimpl.HTMLElementImpl)
	 */
	@Override
	protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
		HTMLBaseInputElement bie = (HTMLBaseInputElement) markupElement;
		BaseInputControl uiControl = new InputButtonControl(bie);
		bie.setInputContext(uiControl);
		return new RUIControl(markupElement, uiControl, bodyLayout.getContainer(), bodyLayout.getFrameContext(),
				bodyLayout.getUserAgentContext());
	}
}
