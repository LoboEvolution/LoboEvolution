
/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.dom.svg;

/**
 * <p>SVGAElement interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGAElement extends SVGElement, SVGURIReference, SVGTests, SVGLangSpace, SVGExternalResourcesRequired,
		SVGStylable, SVGTransformable, Drawable {
	/**
	 * <p>getTarget.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedString} object.
	 */
	SVGAnimatedString getTarget();
}
