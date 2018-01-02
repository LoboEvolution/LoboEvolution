package org.loboevolution.html.domimpl;

import java.util.ArrayList;

import org.loboevolution.html.dombl.DescendentHTMLCollection;
import org.loboevolution.html.domfilter.ElementTableAttributeFilter;
import org.loboevolution.w3c.html.HTMLCollection;
import org.loboevolution.w3c.html.HTMLElement;
import org.loboevolution.w3c.html.HTMLTableSectionElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class HTMLTableSectionElementImpl.
 */
public class HTMLTableSectionElementImpl extends HTMLAbstractUIElement implements HTMLTableSectionElement {

	public HTMLTableSectionElementImpl(String name) {
		super(name);
	}

	@Override
	public HTMLCollection getRows() {
		return new DescendentHTMLCollection(this, new ElementTableAttributeFilter(TR),
				this.getTreeLock(), false);
	}

	@Override
	public HTMLElement insertRow() {
		return insertRow(-1);
	}

	@Override
	public HTMLElement insertRow(int index) throws DOMException {
		Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		HTMLElement rowElement = (HTMLElement) doc.createElement(TR);
		synchronized (this.getTreeLock()) {
			if (index == -1) {
				this.appendChild(rowElement);
				return rowElement;
			}
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				int size = nl.size();
				int trcount = 0;
				for (int i = 0; i < size; i++) {
					Node node = nl.get(i);
					if (TR.equalsIgnoreCase(node.getNodeName())) {
						if (trcount == index) {
							this.insertAt(rowElement, i);
							return rowElement;
						}
						trcount++;
					}
				}
			} else {
				this.appendChild(rowElement);
				return rowElement;
			}
		}
		throw new DOMException(DOMException.INDEX_SIZE_ERR, "Index out of range");
	}

	@Override
	public void deleteRow(int index) throws DOMException {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				int size = nl.size();
				int trcount = 0;
				for (int i = 0; i < size; i++) {
					Node node = nl.get(i);
					if (TR.equalsIgnoreCase(node.getNodeName())) {
						if (trcount == index) {
							this.removeChildAt(i);
							return;
						}
						trcount++;
					}
				}
			}
		}
		throw new DOMException(DOMException.INDEX_SIZE_ERR, "Index out of range");
	}

	@Override
	public String getAlign() {
		return this.getAttribute(ALIGN);
	}

	@Override
	public void setAlign(String align) {
		this.setAttribute(ALIGN, align);

	}

	@Override
	public String getCh() {
		return this.getAttribute(CH);
	}

	@Override
	public void setCh(String ch) {
		this.setAttribute(CH, ch);

	}

	@Override
	public String getChOff() {
		return this.getAttribute(CHOFF);
	}

	@Override
	public void setChOff(String chOff) {
		this.setAttribute(CHOFF, chOff);

	}

	@Override
	public String getVAlign() {
		return this.getAttribute(VALIGN);
	}

	@Override
	public void setVAlign(String vAlign) {
		this.setAttribute(VALIGN, vAlign);

	}

}
