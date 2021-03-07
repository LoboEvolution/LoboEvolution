package org.loboevolution.html.node;

/**
 * A Selection object represents the range of text selected by the user or the
 * current position of the caret. To obtain a Selection object for examination
 * or modification, call Window.getSelection().
 */
public interface Selection {

	Node getAnchorNode();

	int getAnchorOffset();

	Node getFocusNode();

	int getFocusOffset();

	boolean isIsCollapsed();

	int getRangeCount();

	String getType();

	void addRange(Range range);

	void collapse(Node node, int offset);

	void collapse(Node node);

	void collapseToEnd();

	void collapseToStart();

	boolean containsNode(Node node, boolean allowPartialContainment);

	boolean containsNode(Node node);

	void deleteFromDocument();

	void empty();

	void extend(Node node, int offset);

	void extend(Node node);

	Range getRangeAt(int index);

	void removeAllRanges();

	void removeRange(Range range);

	void selectAllChildren(Node node);

	void setBaseAndExtent(Node anchorNode, int anchorOffset, Node focusNode, int focusOffset);

	void setPosition(Node node, int offset);

	void setPosition(Node node);

}
