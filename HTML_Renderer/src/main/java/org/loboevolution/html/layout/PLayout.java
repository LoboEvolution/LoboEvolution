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

import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.rendererblock.RBlockViewport;

/**
 * The Class PLayout.
 */
public class PLayout extends CommonLayout {

	/**
	 * Instantiates a new p layout.
	 */
	public PLayout() {
		super(DISPLAY_BLOCK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.render.MarkupLayout#layoutMarkup(java.awt.Container
	 * , java.awt.Insets, org.loboevolution.html.dombl.HTMLElementImpl)
	 */
	@Override
	public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
		super.layoutMarkup(bodyLayout, markupElement);
	}
}
