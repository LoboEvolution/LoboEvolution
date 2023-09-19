/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.node;

/**
 * The CharacterData abstract interface represents a Node object that contains
 * characters. This is an abstract interface, meaning there aren't any object of
 * type it is implemented by other interfaces CharacterData, like Text, Comment,
 * or ProcessingInstruction which aren't abstract.
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
