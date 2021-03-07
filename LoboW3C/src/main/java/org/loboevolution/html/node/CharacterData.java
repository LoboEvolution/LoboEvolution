package org.loboevolution.html.node;

/**
 * The CharacterData abstract interface represents a Node object that contains
 * characters. This is an abstract interface, meaning there aren't any object of
 * type it is implemented by other interfaces CharacterData, like Text, Comment,
 * or ProcessingInstruction which aren't abstract.
 */
public interface CharacterData extends Node, NonDocumentTypeChildNode {

	String getData();

	void setData(String data);

	int getLength();

	void appendData(String data);

	void deleteData(int offset, int count);

	void insertData(int offset, String data);

	void replaceData(int offset, int count, String data);

	String substringData(int offset, int count);

}
