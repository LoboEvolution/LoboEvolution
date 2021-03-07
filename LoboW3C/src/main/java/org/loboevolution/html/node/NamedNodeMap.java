package org.loboevolution.html.node;

/**
 * A collection of Attr objects. Objects inside a NamedNodeMap are not in any
 * particular order, unlike NodeList, although they may be accessed by an index
 * as in an array.
 */
public interface NamedNodeMap {

	Attr getNamedItem(String qualifiedName);

	Attr getNamedItemNS(String namespace, String localName);

	Attr removeNamedItem(String qualifiedName);

	Attr removeNamedItemNS(String namespace, String localName);

	Attr setNamedItem(Attr attr);

	Attr setNamedItemNS(Attr attr);
	
	Attr item(int index);
	
	int getLength();
	
	

}
