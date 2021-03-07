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

import org.loboevolution.html.node.events.EventTarget;

/**
 * <p>SVGElementInstance interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGElementInstance extends EventTarget {
	/**
	 * <p>getCorrespondingElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 */
	SVGElement getCorrespondingElement();

	/**
	 * <p>getCorrespondingUseElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGUseElement} object.
	 */
	SVGUseElement getCorrespondingUseElement();

	/**
	 * <p>getParentNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElementInstance} object.
	 */
	SVGElementInstance getParentNode();

	/**
	 * <p>getChildNodes.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElementInstanceList} object.
	 */
	SVGElementInstanceList getChildNodes();

	/**
	 * <p>getFirstChild.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElementInstance} object.
	 */
	SVGElementInstance getFirstChild();

	/**
	 * <p>getLastChild.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElementInstance} object.
	 */
	SVGElementInstance getLastChild();

	/**
	 * <p>getPreviousSibling.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElementInstance} object.
	 */
	SVGElementInstance getPreviousSibling();

	/**
	 * <p>getNextSibling.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElementInstance} object.
	 */
	SVGElementInstance getNextSibling();
}
