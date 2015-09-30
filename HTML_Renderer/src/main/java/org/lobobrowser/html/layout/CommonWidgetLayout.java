/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.dombl.UINode;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.renderer.MarkupLayout;
import org.lobobrowser.html.renderer.RBlockViewport;
import org.lobobrowser.html.renderer.RElement;
import org.lobobrowser.html.style.AbstractCSS2Properties;

/**
 * The Class CommonWidgetLayout.
 */
public abstract class CommonWidgetLayout implements MarkupLayout {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(CommonWidgetLayout.class.getName());

	/** The Constant ADD_INLINE. */
	protected static final int ADD_INLINE = 0;

	/** The Constant ADD_AS_BLOCK. */
	protected static final int ADD_AS_BLOCK = 1;

	/** The method. */
	private final int method;

	/** The use align attribute. */
	private final boolean useAlignAttribute;

	/**
	 * Instantiates a new common widget layout.
	 *
	 * @param method
	 *            the method
	 * @param usesAlignAttribute
	 *            the uses align attribute
	 */
	public CommonWidgetLayout(int method, boolean usesAlignAttribute) {
		this.method = method;
		this.useAlignAttribute = usesAlignAttribute;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.MarkupLayout#layoutMarkup(org.lobobrowser.
	 * html .renderer.RBlockViewport,
	 * org.lobobrowser.html.domimpl.HTMLElementImpl)
	 */
	@Override
	public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
		AbstractCSS2Properties style = markupElement.getCurrentStyle();
		if (style != null) {
			String display = style.getDisplay();
			if ((display != null) && "none".equalsIgnoreCase(display)) {
				return;
			}
		}
		UINode node = markupElement.getUINode();
		RElement renderable = null;
		if (node == null) {
			renderable = this.createRenderable(bodyLayout, markupElement);
			if (renderable == null) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("layoutMarkup(): Don't know how to render " + markupElement + ".");
				}
				return;
			}
			markupElement.setUINode(renderable);
		} else {
			renderable = (RElement) node;
		}
		renderable.setOriginalParent(bodyLayout);
		switch (this.method) {
		case ADD_INLINE:
			bodyLayout.addRenderableToLineCheckStyle(renderable, markupElement, this.useAlignAttribute);
			break;
		case ADD_AS_BLOCK:
			bodyLayout.positionRElement(markupElement, renderable, this.useAlignAttribute, true, false);
			break;
		}
	}

	/**
	 * Creates the renderable.
	 *
	 * @param bodyLayout
	 *            the body layout
	 * @param markupElement
	 *            the markup element
	 * @return the r element
	 */
	protected abstract RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement);
}
