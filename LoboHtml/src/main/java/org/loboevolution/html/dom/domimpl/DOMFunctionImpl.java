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
import org.mozilla.javascript.Function;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.events.Event;

import com.gargoylesoftware.css.parser.selector.Selector;
import com.gargoylesoftware.css.parser.selector.SelectorList;

/**
 * <p>DOMFunctionImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DOMFunctionImpl extends NodeImpl {
	
	private final Map<NodeImpl, Map<String, List<Function>>> onEventHandlers = new HashMap<>();
	
	private final List<NodeImpl> clicked = new ArrayList<>();
	
	/**
	 * <p>getElementsByTagName.</p>
	 *
	 * @param tagname a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.NodeList} object.
	 */
	public NodeList getElementsByTagName(String tagname) {
		if ("*".equals(tagname)) {
			return getNodeList(new ElementFilter(null));
		} else {
			return getNodeList(new TagNameFilter(tagname));
		}
	}
	
	/**
	 * <p>getElementsByClassName.</p>
	 *
	 * @param classNames a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.NodeList} object.
	 */
	public NodeList getElementsByClassName(String classNames) {
		return getNodeList(new ClassNameFilter(classNames));
	}
	
    /**
     * <p>querySelector.</p>
     *
     * @param selectors a {@link java.lang.String} object.
     * @return a {@link org.w3c.dom.Element} object.
     */
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
    
    /**
     * <p>querySelectorAll.</p>
     *
     * @param selectors a {@link java.lang.String} object.
     * @return a {@link org.w3c.dom.NodeList} object.
     */
    public NodeList querySelectorAll(String selectors) {
    	final ArrayList<Node> al = new ArrayList<>();
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
		
	/**
	 * <p>addEventListener.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	public void addEventListener(final String type, final Function listener) {
	    addEventListener(type, listener, false);
	}

	/**
	 * <p>addEventListener.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param useCapture a boolean.
	 */
	public void addEventListener(String type, Function listener, boolean useCapture) {
		if ("load".equals(type) || "DOMContentLoaded".equals(type)) {
			onloadEvent(listener);
		} else {
			List<Function> handlerList = new ArrayList<>();
			handlerList.add(listener);
			final Map<String, List<Function>> onEventListeners = new HashMap<>();
			onEventListeners.put(type, handlerList);
			this.onEventHandlers.put(this, onEventListeners);
		}
	}
	
	private void onloadEvent(Function onloadHandler) {
		Executor.executeFunction(this, onloadHandler, null, new Object[0]);
	}
		
	/**
	 * <p>removeEventListener.</p>
	 *
	 * @param script a {@link java.lang.String} object.
	 * @param function a {@link org.mozilla.javascript.Function} object.
	 */
	public void removeEventListener(String script, Function function) {
		removeEventListener(script, function, true);
	}
	
	/**
	 * <p>removeEventListener.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param useCapture a boolean.
	 */
	public void removeEventListener(String type, Function listener, boolean useCapture) {
		Set<NodeImpl> keySet = onEventHandlers.keySet();
		for (NodeImpl htmlElementImpl : keySet) {
			Map<String, List<Function>> map = this.onEventHandlers.get(htmlElementImpl);
			if (map != null) {
				map.get(type).remove(listener);
			}
		}
	}
	
	/**
	 * <p>dispatchEvent.</p>
	 *
	 * @param htmlElementImpl a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 * @param evt a {@link org.w3c.dom.events.Event} object.
	 * @return a boolean.
	 */
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

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "#document";
	}

	/** {@inheritDoc} */
	@Override
	public short getNodeType() {
		return Node.DOCUMENT_NODE;
	}

	/** {@inheritDoc} */
	@Override
	protected Node createSimilarNode() {
		return new HTMLDocumentImpl(new UserAgentContext());
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "Cannot set node value of document");
	}
	
	/**
	 * <p>getElementsByTagNameNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.NodeList} object.
	 */
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>getAttributeNodeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Attr} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/**
	 * <p>getAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>renameNode.</p>
	 *
	 * @param n a {@link org.w3c.dom.Node} object.
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Node} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No renaming");
	}
	
	/**
	 * <p>createEntityReference.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.EntityReference} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public EntityReference createEntityReference(String name) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	/**
	 * <p>createAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Attr} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}
	
	/**
	 * <p>importNode.</p>
	 *
	 * @param importedNode a {@link org.w3c.dom.Node} object.
	 * @param deep a boolean.
	 * @return a {@link org.w3c.dom.Node} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public Node importNode(Node importedNode, boolean deep) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Not implemented");
	}
	
	/**
	 * <p>removeAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>hasAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @return a boolean.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>getSchemaTypeInfo.</p>
	 *
	 * @return a {@link org.w3c.dom.TypeInfo} object.
	 */
	public TypeInfo getSchemaTypeInfo() {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>setIdAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @param isId a boolean.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>setAttributeNodeNS.</p>
	 *
	 * @param newAttr a {@link org.w3c.dom.Attr} object.
	 * @return a {@link org.w3c.dom.Attr} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/**
	 * <p>setAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>createElementNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Element} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
}
