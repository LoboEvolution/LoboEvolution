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

import org.lobobrowser.html.HtmlObject;
import org.lobobrowser.html.control.RUIControl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.renderer.RBlockViewport;
import org.lobobrowser.html.renderer.RElement;
import org.lobobrowser.html.renderer.UIControl;
import org.lobobrowser.html.renderer.UIControlWrapper;

/**
 * The Class ObjectLayout.
 */
public class ObjectLayout extends CommonWidgetLayout {

	/** The try to render content. */
	private boolean tryToRenderContent;

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

	/**
	 * Must use this ThreadLocal because an ObjectLayout instance is shared
	 * across renderers.
	 */
	private final ThreadLocal<HtmlObject> htmlObject = new ThreadLocal<HtmlObject>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.layout.CommonWidgetLayout#layoutMarkup(org.
	 * lobobrowser .html.renderer.RBlockViewport,
	 * org.lobobrowser.html.domimpl.HTMLElementImpl)
	 */
	@Override
	public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
		HtmlObject ho = bodyLayout.getRendererContext().getHtmlObject(markupElement);
		if ((ho == null) && this.tryToRenderContent) {
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
	 * @see org.lobobrowser.html.layout.CommonWidgetLayout#createRenderable(org.
	 * lobobrowser .html.renderer.RBlockViewport,
	 * org.lobobrowser.html.domimpl.HTMLElementImpl)
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
