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
 * A Selection object represents the range of text selected by the user or the
 * current position of the caret. To obtain a Selection object for examination
 * or modification, call Window.getSelection().
 *
 *
 *
 */
public interface Selection {

	/**
	 * <p>getAnchorNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getAnchorNode();

	/**
	 * <p>getAnchorOffset.</p>
	 *
	 * @return a int.
	 */
	int getAnchorOffset();

	/**
	 * <p>getFocusNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getFocusNode();

	/**
	 * <p>getFocusOffset.</p>
	 *
	 * @return a int.
	 */
	int getFocusOffset();

	/**
	 * <p>isIsCollapsed.</p>
	 *
	 * @return a boolean.
	 */
	boolean isIsCollapsed();

	/**
	 * <p>getRangeCount.</p>
	 *
	 * @return a int.
	 */
	int getRangeCount();

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * <p>addRange.</p>
	 *
	 * @param range a {@link org.loboevolution.html.node.Range} object.
	 */
	void addRange(Range range);

	/**
	 * <p>collapse.</p>
	 *
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 * @param offset a int.
	 */
	void collapse(Node node, int offset);

	/**
	 * <p>collapse.</p>
	 *
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 */
	void collapse(Node node);

	/**
	 * <p>collapseToEnd.</p>
	 */
	void collapseToEnd();

	/**
	 * <p>collapseToStart.</p>
	 */
	void collapseToStart();

	/**
	 * <p>containsNode.</p>
	 *
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 * @param allowPartialContainment a boolean.
	 * @return a boolean.
	 */
	boolean containsNode(Node node, boolean allowPartialContainment);

	/**
	 * <p>containsNode.</p>
	 *
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 * @return a boolean.
	 */
	boolean containsNode(Node node);

	/**
	 * <p>deleteFromDocument.</p>
	 */
	void deleteFromDocument();

	/**
	 * <p>empty.</p>
	 */
	void empty();

	/**
	 * <p>extend.</p>
	 *
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 * @param offset a int.
	 */
	void extend(Node node, int offset);

	/**
	 * <p>extend.</p>
	 *
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 */
	void extend(Node node);

	/**
	 * <p>getRangeAt.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.node.Range} object.
	 */
	Range getRangeAt(int index);

	/**
	 * <p>removeAllRanges.</p>
	 */
	void removeAllRanges();

	/**
	 * <p>removeRange.</p>
	 *
	 * @param range a {@link org.loboevolution.html.node.Range} object.
	 */
	void removeRange(Range range);

	/**
	 * <p>selectAllChildren.</p>
	 *
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 */
	void selectAllChildren(Node node);

	/**
	 * <p>setBaseAndExtent.</p>
	 *
	 * @param anchorNode a {@link org.loboevolution.html.node.Node} object.
	 * @param anchorOffset a int.
	 * @param focusNode a {@link org.loboevolution.html.node.Node} object.
	 * @param focusOffset a int.
	 */
	void setBaseAndExtent(Node anchorNode, int anchorOffset, Node focusNode, int focusOffset);

	/**
	 * <p>setPosition.</p>
	 *
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 * @param offset a int.
	 */
	void setPosition(Node node, int offset);

	/**
	 * <p>setPosition.</p>
	 *
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 */
	void setPosition(Node node);

}
