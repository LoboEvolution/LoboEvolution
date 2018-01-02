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
import org.loboevolution.html.renderer.RBlockViewport;

/**
 * The Class NopLayout.
 */
public class NopLayout implements MarkupLayout {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.MarkupLayout#layoutMarkup(org.loboevolution.
	 * html .renderer.RBlockViewport,
	 * org.loboevolution.html.domimpl.HTMLElementImpl)
	 */
	@Override
	public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
	}
}
