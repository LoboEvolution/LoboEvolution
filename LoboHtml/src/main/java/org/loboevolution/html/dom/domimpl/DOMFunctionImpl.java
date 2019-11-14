package org.loboevolution.html.dom.domimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.filter.ClassNameFilter;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.dom.filter.TagNameFilter;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.loboevolution.http.UserAgentContext;

import com.gargoylesoftware.css.parser.selector.Selector;
import com.gargoylesoftware.css.parser.selector.SelectorList;
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
	
	private Map<NodeImpl, Map<String, List<Function>>> onEventHandlers = new HashMap<NodeImpl, Map<String, List<Function>>>();
	
	private List<NodeImpl> clicked = new ArrayList<NodeImpl>();
	
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
	
    public Element querySelector(String selectors) {
    	SelectorList selectorList = CSSUtilities.getSelectorList(selectors);
    	if (selectorList != null) {
    		NodeList childNodes = getDescendents(new ElementFilter(null), true);
    		for (Node child : Nodes.iterable(childNodes)) {
                for (Selector selector : selectorList) {                	
                	if (child instanceof Element && StyleSheetAggregator.selects(selector, child, null, true)) {
                        return (Element)child;
                    }
                }
            }
        }
        return null;
    }
    
    public NodeList querySelectorAll(String selectors) {
    	final ArrayList<Node> al = new ArrayList<Node>();
    	SelectorList selectorList = CSSUtilities.getSelectorList(selectors);
    	if (selectorList != null) {
    		NodeList childNodes = getDescendents(new ElementFilter(null), true);
    		for (Node child : Nodes.iterable(childNodes)) {
                for (Selector selector : selectorList) {                	
                	if (child instanceof Element && StyleSheetAggregator.selects(selector, child, null, true)) {
                        al.add(child);
                    }
                }
            }
        }
        return new NodeListImpl(al);
    }
		
	public void addEventListener(final String type, final Function listener) {
	    addEventListener(type, listener, false);
	}

	public void addEventListener(String type, Function listener, boolean useCapture) {
		if ("load".equals(type) || "DOMContentLoaded".equals(type)) {
			onloadEvent(listener);
		} else {
			List<Function> handlerList = new ArrayList<Function>();
			handlerList.add(listener);
			final Map<String, List<Function>> onEventListeners = new HashMap<String, List<Function>>();
			onEventListeners.put(type, handlerList);
			this.onEventHandlers.put((NodeImpl) this, onEventListeners);
		}
	}
	
	private void onloadEvent(Function onloadHandler) {
		Executor.executeFunction(this, onloadHandler, null, new Object[0]);
	}
		
	public void removeEventListener(String script, Function function) {
		removeEventListener(script, function, true);
	}
	
	public void removeEventListener(String type, Function listener, boolean useCapture) {
		Set<NodeImpl> keySet = onEventHandlers.keySet();
		for (NodeImpl htmlElementImpl : keySet) {
			Map<String, List<Function>> map = this.onEventHandlers.get(htmlElementImpl);
			if (map != null) {
				map.get(type).remove(listener);
			}
		}
	}
	
	public boolean dispatchEvent(NodeImpl htmlElementImpl, Event evt) {
		Map<String, List<Function>> map = this.onEventHandlers.get(htmlElementImpl);
		if (map != null) {
			List<Function> handlers = map.get(evt.getType());
			if (handlers != null) {
				for (final Function h : handlers) {
					if(!clicked.contains(htmlElementImpl)){
					Executor.executeFunction(this, h, evt, new Object[0]);
					clicked.add(htmlElementImpl);
				}else {
					clicked.clear();
				}
					}
			}
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