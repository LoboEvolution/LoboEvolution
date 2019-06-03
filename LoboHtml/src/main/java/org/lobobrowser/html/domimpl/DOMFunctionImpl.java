package org.lobobrowser.html.domimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lobobrowser.html.js.Event;
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
		return dispatchEventToHandlers(evt, this.onEventHandlers.get(evt.getType()));
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
	
	private boolean dispatchEventToHandlers(final Event event, final List<Function> handlers) {
		if (handlers != null) {
			final ArrayList<Function> handlersCopy = new ArrayList<Function>(handlers);
			for (final Function h : handlersCopy) {
				if (handlers.contains(h)) {
					return Executor.executeFunction(this, h, event);
				}
			}
		}
		return false;
	}
	
	private class TagNameFilter implements NodeFilter {
		private final String name;

		public TagNameFilter(String name) {
			this.name = name;
		}

		@Override
		public boolean accept(Node node) {
			if (!(node instanceof Element)) {
				return false;
			}
			final String n = this.name;
			return n.equalsIgnoreCase(((Element) node).getTagName());
		}
	}
	
	private class ClassNameFilter  implements NodeFilter {
		private final String _class;

		public ClassNameFilter(String _class) {
			this._class = _class;
		}

		public boolean accept(Node node) {
			return (node instanceof Element)
					&& this._class.equals(((Element) node).getAttribute("class"));
		}
	}
}
