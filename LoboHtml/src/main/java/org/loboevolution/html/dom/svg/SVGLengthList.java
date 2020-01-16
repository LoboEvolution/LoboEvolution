
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
package org.loboevolution.html.dom.svg;

import org.w3c.dom.DOMException;

public interface SVGLengthList {
	int getNumberOfItems();

	void clear() throws DOMException;

	SVGLength initialize(SVGLength newItem) throws DOMException, SVGException;

	SVGLength getItem(int index) throws DOMException;

	SVGLength insertItemBefore(SVGLength newItem, int index) throws DOMException, SVGException;

	SVGLength replaceItem(SVGLength newItem, int index) throws DOMException, SVGException;

	SVGLength removeItem(int index) throws DOMException;

	SVGLength appendItem(SVGLength newItem) throws DOMException, SVGException;
}
