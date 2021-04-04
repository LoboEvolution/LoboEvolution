/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.node;

/**
 * A collection of Attr objects. Objects inside a NamedNodeMap are not in any
 * particular order, unlike NodeList, although they may be accessed by an index
 * as in an array.
 *
 *
 *
 */
public interface NamedNodeMap {

	/**
	 * <p>getNamedItem.</p>
	 *
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 */
	Attr getNamedItem(String qualifiedName);

	/**
	 * <p>getNamedItemNS.</p>
	 *
	 * @param namespace a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 */
	Attr getNamedItemNS(String namespace, String localName);

	/**
	 * <p>removeNamedItem.</p>
	 *
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 */
	Attr removeNamedItem(String qualifiedName);

	/**
	 * <p>removeNamedItemNS.</p>
	 *
	 * @param namespace a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 */
	Attr removeNamedItemNS(String namespace, String localName);

	/**
	 * <p>setNamedItem.</p>
	 *
	 * @param attr a {@link org.loboevolution.html.node.Attr} object.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 */
	Attr setNamedItem(Attr attr);

	/**
	 * <p>setNamedItemNS.</p>
	 *
	 * @param attr a {@link org.loboevolution.html.node.Attr} object.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 */
	Attr setNamedItemNS(Attr attr);
	
	/**
	 * <p>item.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 */
	Attr item(int index);
	
	/**
	 * <p>getLength.</p>
	 *
	 * @return a int.
	 */
	int getLength();
	
	

}
