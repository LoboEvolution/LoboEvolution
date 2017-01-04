/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.layout;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.control.BrowserFrameUIControl;
import org.lobobrowser.html.control.RUIControl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.HTMLIFrameElementImpl;
import org.lobobrowser.html.renderer.RBlockViewport;
import org.lobobrowser.html.renderer.RElement;
import org.lobobrowser.html.renderer.UIControl;

/**
 * The Class IFrameLayout.
 */
public class IFrameLayout extends CommonWidgetLayout {

	/**
	 * Instantiates a new i frame layout.
	 */
	public IFrameLayout() {
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
		BrowserFrame frame = bodyLayout.getRendererContext().createBrowserFrame();
		((HTMLIFrameElementImpl) markupElement).setBrowserFrame(frame);
		UIControl control = new BrowserFrameUIControl(markupElement, frame);
		return new RUIControl(markupElement, control, bodyLayout.getContainer(), bodyLayout.getFrameContext(),
				bodyLayout.getUserAgentContext());
	}
}
