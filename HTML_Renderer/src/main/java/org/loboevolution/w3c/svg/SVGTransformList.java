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
package org.loboevolution.w3c.svg;

import org.w3c.dom.DOMException;

public interface SVGTransformList {
	public int getNumberOfItems();

	public void clear() throws DOMException;

	public SVGTransform initialize(SVGTransform newItem) throws DOMException, SVGException;

	public SVGTransform getItem(int index) throws DOMException;

	public SVGTransform insertItemBefore(SVGTransform newItem, int index) throws DOMException, SVGException;

	public SVGTransform replaceItem(SVGTransform newItem, int index) throws DOMException, SVGException;

	public SVGTransform removeItem(int index) throws DOMException;

	public SVGTransform appendItem(SVGTransform newItem) throws DOMException, SVGException;

	public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix);

	public SVGTransform consolidate();
}
