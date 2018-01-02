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

import org.loboevolution.html.control.CanvasControl;
import org.loboevolution.html.control.RCanvasControl;
import org.loboevolution.html.domimpl.HTMLCanvasElementImpl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.RBlockViewport;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.UIControl;

/**
 * The Class CanvasLayout.
 */
public class CanvasLayout extends CommonWidgetLayout {

	public CanvasLayout() {
		super(ADD_AS_BLOCK, true);
	}

	@Override
	protected RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
		UIControl control = new CanvasControl((HTMLCanvasElementImpl) markupElement);
		return new RCanvasControl(markupElement, control, bodyLayout.getContainer(), bodyLayout.getFrameContext(),
				bodyLayout.getUserAgentContext());
	}
}
