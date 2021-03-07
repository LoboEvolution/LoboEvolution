package org.loboevolution.html.node;

/**
 * An object providing methods which are not dependent on any particular
 * document. Such an object is returned by the Document.implementation property.
 */
public interface DOMImplementation {

	Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype);

	DocumentType createDocumentType(String qualifiedName, String publicId, String systemId);

	Document createHTMLDocument(String title);

	Document createHTMLDocument();
}
