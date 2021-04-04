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
 * The CharacterData abstract interface represents a Node object that contains
 * characters. This is an abstract interface, meaning there aren't any object of
 * type it is implemented by other interfaces CharacterData, like Text, Comment,
 * or ProcessingInstruction which aren't abstract.
 *
 *
 *
 */
public interface CharacterData extends Node, NonDocumentTypeChildNode {

	/**
	 * <p>getData.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getData();

	/**
	 * <p>setData.</p>
	 *
	 * @param data a {@link java.lang.String} object.
	 */
	void setData(String data);

	/**
	 * <p>getLength.</p>
	 *
	 * @return a int.
	 */
	int getLength();

	/**
	 * <p>appendData.</p>
	 *
	 * @param data a {@link java.lang.String} object.
	 */
	void appendData(String data);

	/**
	 * <p>deleteData.</p>
	 *
	 * @param offset a int.
	 * @param count a int.
	 */
	void deleteData(int offset, int count);

	/**
	 * <p>insertData.</p>
	 *
	 * @param offset a int.
	 * @param data a {@link java.lang.String} object.
	 */
	void insertData(int offset, String data);

	/**
	 * <p>replaceData.</p>
	 *
	 * @param offset a int.
	 * @param count a int.
	 * @param data a {@link java.lang.String} object.
	 */
	void replaceData(int offset, int count, String data);

	/**
	 * <p>substringData.</p>
	 *
	 * @param offset a int.
	 * @param count a int.
	 * @return a {@link java.lang.String} object.
	 */
	String substringData(int offset, int count);

}
