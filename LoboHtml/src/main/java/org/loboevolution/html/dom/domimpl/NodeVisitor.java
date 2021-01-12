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
/*
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.domimpl;

/**
 * <p>NodeVisitor interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface NodeVisitor {
	/**
	 * Visits a node. The call should be expected to occur in a synchronized block.
	 * The lock will be node-dependent.
	 *
	 * @param node a {@link org.w3c.dom.Node} object.
	 */
	void visit(org.w3c.dom.Node node);
}
