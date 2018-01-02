/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.domimpl;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.loboevolution.html.HtmlEventProperties;
import org.loboevolution.html.HtmlJsAttributeProperties;
import org.loboevolution.html.HtmlProperties;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.dombl.QuerySelectorImpl;
import org.loboevolution.html.domfilter.ClassNameFilter;
import org.loboevolution.html.domfilter.ElementAttributeFilter;
import org.loboevolution.html.domfilter.ElementFilter;
import org.loboevolution.html.domfilter.TagNameFilter;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.jsimpl.CustomEventImpl;
import org.loboevolution.html.jsimpl.EventException;
import org.loboevolution.html.jsimpl.EventImpl;
import org.loboevolution.html.jsimpl.FunctionImpl;
import org.loboevolution.html.jsimpl.KeyboardEventImpl;
import org.loboevolution.html.jsimpl.MouseEventImpl;
import org.loboevolution.html.jsimpl.MutationEventImpl;
import org.loboevolution.html.jsimpl.MutationNameEventImpl;
import org.loboevolution.html.jsimpl.TextEventImpl;
import org.loboevolution.html.jsimpl.UIEventImpl;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.html.xpath.XPathEvaluatorImpl;
import org.loboevolution.html.xpath.XPathNSResolverImpl;
import org.loboevolution.html.xpath.XPathResultImpl;
import org.loboevolution.http.HttpRequest;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.w3c.events.Event;
import org.loboevolution.w3c.html.DOMElementMap;
import org.loboevolution.w3c.html.HTMLDocument;
import org.loboevolution.w3c.html.HTMLElement;
import org.loboevolution.w3c.xpath.XPathExpression;
import org.loboevolution.w3c.xpath.XPathNSResolver;
import org.loboevolution.w3c.xpath.XPathResult;
import org.mozilla.javascript.Function;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

public abstract class DOMFunctionImpl extends FunctionImpl implements HtmlJsAttributeProperties, HtmlEventProperties, HtmlProperties {
	
	private List<String> EXECUTE_CMDS = Arrays.asList(new String[] { "backColor", "bold", "contentReadOnly",
			"copy", "createLink", "cut", "decreaseFontSize", "delete", "fontName", "fontSize", "foreColor",
			"formatBlock", "heading", "hiliteColor", "increaseFontSize", "indent", "insertHorizontalRule", "insertHTML",
			"insertImage", "insertOrderedList", "insertUnorderedList", "insertParagraph", "italic", "justifyCenter",
			"justifyLeft", "justifyRight", "outdent", "paste", "redo", "removeFormat", "selectAll", "strikeThrough",
			"subscript", "superscript", "underline", "undo", "unlink", "useCSS", "styleWithCSS" });

	public NodeList getElementsByClassName(String classNames) {
		return this.getNodeList(new ClassNameFilter(classNames));
	}

	public NodeList getElementsByTagName(String tagname) {
		if ("*".equals(tagname)) {
			return this.getNodeList(new ElementFilter());
		} else {
			return this.getNodeList(new TagNameFilter(tagname));
		}
	}

	public String getFgColor() {
		NodeList nodeList = getElementsByTagName(BODY);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, TEXT_HTML);
		return attr.getAttribute();
	}

	public void setFgColor(String fgColor) {
		ElementAttributeFilter attr = new ElementAttributeFilter(TEXT_HTML);
		attr.setAttribute((HTMLDocumentImpl) this.document, fgColor);
	}

	public String getBgColor() {
		NodeList nodeList = getElementsByTagName(BODY);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, BGCOLOR);
		return attr.getAttribute();
	}

	public void setBgColor(String bgColor) {
		ElementAttributeFilter attr = new ElementAttributeFilter(BGCOLOR);
		attr.setAttribute((HTMLDocumentImpl) this.document, bgColor);
	}

	public String getLinkColor() {
		NodeList nodeList = getElementsByTagName(BODY);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, LINK_HTML);
		return attr.getAttribute();
	}

	public void setLinkColor(String linkColor) {
		ElementAttributeFilter attr = new ElementAttributeFilter(LINK_HTML);
		attr.setAttribute((HTMLDocumentImpl) this.document, linkColor);
	}

	public String getVlinkColor() {
		NodeList nodeList = getElementsByTagName(BODY);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, VLINK);
		return attr.getAttribute();
	}

	public void setVlinkColor(String vlinkColor) {
		ElementAttributeFilter attr = new ElementAttributeFilter(VLINK);
		attr.setAttribute((HTMLDocumentImpl) this.document, vlinkColor);

	}

	public String getAlinkColor() {
		NodeList nodeList = getElementsByTagName(BODY);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, ALINK);
		return attr.getAttribute();
	}

	public void setAlinkColor(String alinkColor) {
		ElementAttributeFilter attr = new ElementAttributeFilter(ALINK);
		attr.setAttribute((HTMLDocumentImpl) this.document, alinkColor);
	}

	public void addEventListener(String script, Function function) {

		String key = script.toLowerCase();

		switch (key) {
		case CLICK:
			setOnclick(function);
			break;
		case DBLCLICK:
			setOndblclick(function);
			break;
		case MOUSEUP:
			setOnmouseup(function);
			break;
		case MOUSEDOWN:
			setOnmousedown(function);
			break;
		case MOUSEOVER:
			setOnmouseover(function);
			break;
		case MOUSEOUT:
			setOnmouseout(function);
			break;
		case KEYPRESS:
			setOnkeypress(function);
			break;
		case KEYUP:
			setOnkeyup(function);
			break;
		case KEYDOWN:
			setOnkeydown(function);
			break;
		case LOAD:
			setOnloadHandler(function);
			break;

		default:
			break;
		}

	}

	public void removeEventListener(String script, Function function) {
		String key = script.toLowerCase();

		switch (key) {
		case CLICK:
			setOnclick(null);
			break;
		case DBLCLICK:
			setOndblclick(null);
			break;
		case MOUSEUP:
			setOnmouseup(null);
			break;
		case MOUSEDOWN:
			setOnmousedown(null);
			break;
		case MOUSEOVER:
			setOnmouseover(null);
			break;
		case MOUSEOUT:
			setOnmouseout(null);
			break;
		case KEYPRESS:
			setOnkeypress(null);
			break;
		case KEYUP:
			setOnkeyup(null);
			break;
		case KEYDOWN:
			setOnkeydown(null);
			break;
		case LOAD:
			setOnloadHandler(null);
			break;

		default:
			break;
		}
	}

	public void addEventListener(String script, Function function, boolean bool) {
		addEventListener(script, function);
	}

	public void removeEventListener(String script, Function function, boolean bool) {
		removeEventListener(script, function);
	}

	public void evaluate(String expression, HTMLDocumentImpl contextNode, XPathNSResolver resolver, short type,
			Object result) {
		evaluate(expression, contextNode, resolver, type, result);
	}

	public XPathExpression createExpression(String expression, XPathNSResolver resolver) {
		XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return evaluator.createExpression(expression, resolver);
	}

	public XPathNSResolver createNSResolver(Node nodeResolver) {
		XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return evaluator.createNSResolver(nodeResolver);
	}

	public XPathResult evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type,
			Object result) {
		return eval(expression, contextNode, resolver, type, result);
	}

	public XPathResultImpl evaluate(String expression, HTMLElement contextNode, XPathNSResolverImpl resolver,
			Short type, Object result) {
		return eval(expression, contextNode, resolver, type, result);
	}
	
	public boolean queryCommandEnabled(String commandId) {
		Iterator<String> it = EXECUTE_CMDS.iterator();
		while (it.hasNext()) {
			if (commandId.equalsIgnoreCase(it.next())) {
				return true;
			}
		}
		return false;
	}

	public boolean queryCommandSupported(String commandId) {
		Iterator<String> it = EXECUTE_CMDS.iterator();
		while (it.hasNext()) {
			if (commandId.equalsIgnoreCase(it.next())) {
				return true;
			}
		}
		return false;
	}

	public Element querySelector(String selectors) {
		QuerySelectorImpl qsel = new QuerySelectorImpl();
		return qsel.documentQuerySelector((HTMLDocumentImpl) this.document, selectors);
	}

	public NodeList querySelectorAll(String selectors) {
		QuerySelectorImpl qsel = new QuerySelectorImpl();
		return qsel.documentQuerySelectorAll((HTMLDocumentImpl) this.document, selectors);
	}
	
	public String getCharacterSet() {
		NodeList nodeList = getElementsByTagName(META);
		ElementAttributeFilter attr = new ElementAttributeFilter(nodeList, CHARSET);
		return attr.getAttribute();
	}

	public String getDefaultCharset() {
		return Charset.defaultCharset().displayName();
	}

	public String getReadyState() {
		HtmlRendererContext rcontext = this.getHtmlRendererContext();
		UserAgentContext uac = rcontext.getUserAgentContext();
		HttpRequest httpRequest = uac.createHttpRequest();
		switch (httpRequest.getReadyState()) {
		case UNINITIALIZED:
			return "uninitialized";
		case LOADING:
			return "loading";
		case LOADED:
			return "loaded";
		case INTERACTIVE:
			return "interactive";
		case COMPLETE:
			return "complete";
		default:
			return "";
		}
	}
	
	public DocumentFragment createDocumentFragment() {
		DOMFragmentImpl node = new DOMFragmentImpl();
		node.setOwnerDocument(this.document);
		return node;
	}

	public Text createTextNode(String data) {
		DOMTextImpl node = new DOMTextImpl(data);
		node.setOwnerDocument(this.document);
		return node;
	}

	public Comment createComment(String data) {
		DOMCommentImpl node = new DOMCommentImpl(data);
		node.setOwnerDocument(this.document);
		return node;
	}

	public CDATASection createCDATASection(String data) throws DOMException {
		DOMCDataSectionImpl node = new DOMCDataSectionImpl(data);
		node.setOwnerDocument(this.document);
		return node;
	}

	public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
		HTMLProcessingInstruction node = new HTMLProcessingInstruction(target, data);
		node.setOwnerDocument(this.document);
		return node;
	}

	public Attr createAttribute(String name) throws DOMException {
		return new DOMAttrImpl(name);
	}

	public EntityReference createEntityReference(String name) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	public Node importNode(Node importedNode, boolean deep) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Not implemented");
	}

	public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}

	public Event createEvent(String eventType) throws DOMException {

		switch (eventType) {
		case HTML_EVENT:
			return new EventImpl();
		case UIEVENT:
			return new UIEventImpl();
		case MOUSEEVENT:
			return new MouseEventImpl();
		case MUTATIONEVENT:
			return new MutationEventImpl();
		case MUTATIONNAMEEVENT:
			return new MutationNameEventImpl();
		case TEXTEVENT:
			return new TextEventImpl();
		case KEYBOARDEVENT:
			return new KeyboardEventImpl();
		case CUSTOMEVENT:
			return new CustomEventImpl();
		default:
			return new EventImpl();
		}
	}

	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}
	
	public String getLocalName() {
		// Always null for document
		return null;
	}
	
	public Node adoptNode(Node source) throws DOMException {
		if (source instanceof DOMNodeImpl) {
			DOMNodeImpl node = (DOMNodeImpl) source;
			node.setOwnerDocument(this.document, true);
			return node;
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid Node implementation");
		}
	}

	public void normalizeDocument() {
		synchronized (this.getTreeLock()) {
			this.visitImpl(node -> node.normalize());
		}
	}
	
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No renaming");
	}

	public String getNodeName() {
		return "#document";
	}

	public short getNodeType() {
		return Node.DOCUMENT_NODE;
	}

	public String getNodeValue() throws DOMException {
		// Always null for document
		return null;
	}

	public void setNodeValue(String nodeValue) throws DOMException {
		throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "Cannot set node value of document");
	}
	

	protected RenderState createRenderState(RenderState prevRenderState) {
		return new StyleSheetRenderState((HTMLDocumentImpl)this.document);
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		Function onloadHandler = this.getOnloadHandler();
		if (onloadHandler != null && HtmlParser.MODIFYING_KEY.equals(key) && data == Boolean.FALSE) {
			Executor.executeFunction(this, onloadHandler, null);
		}
		return super.setUserData(key, data, handler);
	}

	
	public Object getElement(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDir() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDir(String dir) {
		// TODO Auto-generated method stub

	}

	public DOMElementMap getCssElementMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInnerHTML(String innerHTML) {
		// TODO Auto-generated method stub

	}

	public HTMLDocument open(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public HTMLDocument open(String type, String replace) {
		// TODO Auto-generated method stub
		return null;
	}

	public Element getActiveElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public void clear() {
		// TODO Auto-generated method stub

	}

	public boolean dispatchEvent(Event evt) throws EventException, DOMException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasFocus() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDesignMode() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDesignMode(String designMode) {
		// TODO Auto-generated method stub

	}

	public boolean execCommand(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean execCommand(String commandId, boolean showUI) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean execCommand(String commandId, boolean showUI, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean queryCommandIndeterm(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean queryCommandState(String commandId) {
		// TODO Auto-generated method stub
		return false;
	}

	public String queryCommandValue(String commandId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private XPathResultImpl eval(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result) {
		XPathEvaluatorImpl evaluator = new XPathEvaluatorImpl(document);
		return (XPathResultImpl) evaluator.evaluate(expression, contextNode, resolver, type, result);
	}
}