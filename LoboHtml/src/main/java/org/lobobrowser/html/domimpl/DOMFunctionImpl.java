package org.lobobrowser.html.domimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lobobrowser.html.dom.filter.ClassNameFilter;
import org.lobobrowser.html.dom.filter.ElementFilter;
import org.lobobrowser.html.dom.filter.TagNameFilter;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.http.UserAgentContext;
import org.mozilla.javascript.Function;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.events.Event;

public class DOMFunctionImpl extends NodeImpl {
	
	private final Map<String, List<Function>> onEventHandlers = new HashMap<String, List<Function>>();

	public NodeList getElementsByTagName(String tagname) {
		if ("*".equals(tagname)) {
			return getNodeList(new ElementFilter(null));
		} else {
			return getNodeList(new TagNameFilter(tagname));
		}
	}
	
	public NodeList getElementsByClassName(String classNames) {
		return getNodeList(new ClassNameFilter(classNames));
	}
		
	public void addEventListener(final String type, final Function listener) {
	    addEventListener(type, listener, false);
	}

	public void addEventListener(String type, Function listener, boolean useCapture) {
		List<Function> handlerList = null;
		if (this.onEventHandlers.containsKey(type)) {
			handlerList = this.onEventHandlers.get(type);
		} else {
			handlerList = new ArrayList<Function>();
			this.onEventHandlers.put(type, handlerList);
		}
		handlerList.add(listener);
	}
	
	public void removeEventListener(String script, Function function) {
		removeEventListener(script, function, true);
	}
	
	public void removeEventListener(String type, Function listener, boolean useCapture) {
		if (this.onEventHandlers.containsKey(type)) {
			this.onEventHandlers.get(type).remove(listener);
		}
	}
	
	public boolean dispatchEvent(Event evt) {
	    final List<Function> handlers = this.onEventHandlers.get(evt.getType());
		if (handlers != null) {
			for (final Function h : handlers) {
				Executor.executeFunction(this, h, evt);
			}
			return true;
		}
		return false;
	}

	@Override
	public String getNodeName() {
		return "#document";
	}

	@Override
	public short getNodeType() {
		return Node.DOCUMENT_NODE;
	}

	@Override
	protected Node createSimilarNode() {
		return new HTMLDocumentImpl(new UserAgentContext());
	}

	@Override
	public String getLocalName() {
		return null;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}
	
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "Cannot set node value of document");
	}
	
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No renaming");
	}
	
	public EntityReference createEntityReference(String name) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}
	
	public Node importNode(Node importedNode, boolean deep) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Not implemented");
	}
	
	public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	public TypeInfo getSchemaTypeInfo() {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
}