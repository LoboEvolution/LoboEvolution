package org.loboevolution.html.js;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.loboevolution.js.AbstractScriptableDelegate;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.html.HTMLDocument;

/**
 * <p>XMLSerializer class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class XMLSerializer extends AbstractScriptableDelegate {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(XMLSerializer.class.getName());

	/**
	 * The subtree rooted by the specified element is serialized to a string.
	 *
	 * @param root
	 *            the root of the subtree to be serialized (this may be any
	 *            node, even a document)
	 * @return the serialized string
	 */
	public String serializeToString(Node root) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			if (root instanceof Document) {
				return documentToString((Document) root);
			} else if (root instanceof DocumentFragment) {
				if (root.getOwnerDocument() instanceof HTMLDocument) {
					return "";
				}
				root = root.getFirstChild();
			}

			transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(root), new StreamResult(writer));
			return writer.getBuffer().toString().replaceAll("\n|\r", "");
		} catch (TransformerException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return "";
	}

	private String documentToString(Document doc) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		elementToStream(doc.getDocumentElement(), baos);
		return String.valueOf(baos.toByteArray());
	}

	private void elementToStream(Element element, OutputStream out) {
		try {
			DOMSource source = new DOMSource(element);
			StreamResult result = new StreamResult(out);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
		} catch (Exception ex) {
		}
	}

}

