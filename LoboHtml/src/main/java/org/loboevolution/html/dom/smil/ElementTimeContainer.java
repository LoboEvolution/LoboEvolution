/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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

package org.loboevolution.html.dom.smil;

import org.w3c.dom.NodeList;

/**
 * This is a placeholder - subject to change. This represents generic timelines.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface ElementTimeContainer extends ElementTime {
	/**
	 * A NodeList that contains all timed childrens of this node. If there are
	 * no timed children, the Nodelist is empty. An iterator is
	 * more appropriate here than a node list but it requires Traversal module
	 * support.
	 *
	 * @return a {@link org.w3c.dom.NodeList} object.
	 */
	public NodeList getTimeChildren();

	/**
	 * Returns a list of child elements active at the specified invocation.
	 *
	 * @param instant
	 *            The desired position on the local timeline in milliseconds.
	 * @return List of timed child-elements active at instant.
	 */
	public NodeList getActiveChildrenAt(float instant);

}
