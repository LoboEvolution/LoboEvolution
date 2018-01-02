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

import org.loboevolution.html.control.ImgControl;
import org.loboevolution.html.control.RImgControl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.renderer.RBlockViewport;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.UIControl;

/**
 * The Class ImgLayout.
 */
public class ImgLayout extends CommonWidgetLayout {

	/**
	 * Instantiates a new img layout.
	 */
	public ImgLayout() {
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
		UIControl control = new ImgControl((HTMLImageElementImpl) markupElement);
		return new RImgControl(markupElement, control, bodyLayout.getContainer(), bodyLayout.getFrameContext(),
				bodyLayout.getUserAgentContext());
	}
}
