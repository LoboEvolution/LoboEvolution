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
import org.loboevolution.html.control.InputSelectControl;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.RBlockViewport;
import org.loboevolution.html.renderer.RElement;

/**
 * The Class SelectLayout.
 */
public class SelectLayout extends CommonWidgetLayout {

	/**
	 * Instantiates a new select layout.
	 */
	public SelectLayout() {
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
		BaseInputControl uiControl = new InputSelectControl(bie);
		bie.setInputContext(uiControl);
		return new RUIControl(markupElement, uiControl, bodyLayout.getContainer(), bodyLayout.getFrameContext(),
				bodyLayout.getUserAgentContext());
	}
}
