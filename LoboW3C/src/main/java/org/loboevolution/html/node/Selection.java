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
