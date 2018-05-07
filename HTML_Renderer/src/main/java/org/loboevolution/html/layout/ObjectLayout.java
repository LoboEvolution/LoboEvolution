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

import org.loboevolution.html.HtmlObject;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.UIControl;
import org.loboevolution.html.renderer.UIControlWrapper;
import org.loboevolution.html.rendererblock.RBlockViewport;

/**
 * The Class ObjectLayout.
 */
public class ObjectLayout extends CommonWidgetLayout {

	/** The try to render content. */
	private boolean tryToRenderContent;
	
	/**
	 * Must use this ThreadLocal because an ObjectLayout instance is shared
	 * across renderers.
	 */
	private final ThreadLocal<HtmlObject> htmlObject = new ThreadLocal<HtmlObject>();

	/**
	 * Instantiates a new object layout.
	 *
	 * @param tryToRenderContent
	 *            If the object is unknown, content is rendered as HTML.
	 * @param usesAlignAttribute
	 *            the uses align attribute
	 */
	public ObjectLayout(boolean tryToRenderContent, boolean usesAlignAttribute) {
		super(ADD_INLINE, usesAlignAttribute);
		this.tryToRenderContent = tryToRenderContent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.layout.CommonWidgetLayout#layoutMarkup(org.
	 * loboevolution .html.renderer.RBlockViewport,
	 * org.loboevolution.html.domimpl.HTMLElementImpl)
	 */
	@Override
	public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
		HtmlObject ho = bodyLayout.getRendererContext().getHtmlObject(markupElement);
		if (ho == null && this.tryToRenderContent) {
			// Don't know what to do with it - render contents.
			bodyLayout.layoutMarkup(markupElement);
		} else if (ho != null) {
			this.htmlObject.set(ho);
			super.layoutMarkup(bodyLayout, markupElement);
		}
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
		HtmlObject ho = this.htmlObject.get();
		UIControl uiControl = new UIControlWrapper(ho);
		RUIControl ruiControl = new RUIControl(markupElement, uiControl, bodyLayout.getContainer(),
				bodyLayout.getFrameContext(), bodyLayout.getUserAgentContext());
		return ruiControl;
	}
}
