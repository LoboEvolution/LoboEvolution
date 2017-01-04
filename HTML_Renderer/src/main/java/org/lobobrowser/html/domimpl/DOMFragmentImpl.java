/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 9, 2005
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dombl.QuerySelectorImpl;
import org.lobobrowser.w3c.html.HTMLDocumentFragment;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class DOMFragmentImpl.
 */
public class DOMFragmentImpl extends DOMNodeImpl implements DocumentFragment,
HTMLDocumentFragment {

    /**
     * Instantiates a new DOM fragment impl.
     */
    public DOMFragmentImpl() {
        super();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getLocalName()
     */
    @Override
    public String getLocalName() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getNodeName()
     */
    @Override
    public String getNodeName() {
        return "#document-fragment";
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getNodeValue()
     */
    @Override
    public String getNodeValue() throws DOMException {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.DOMNodeImpl#setNodeValue(java.lang.String)
     */
    @Override
    public void setNodeValue(String nodeValue) throws DOMException {
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getNodeType()
     */
    @Override
    public short getNodeType() {
        return Node.DOCUMENT_FRAGMENT_NODE;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.DOMNodeImpl#createSimilarNode()
     */
    @Override
    protected Node createSimilarNode() {
        return new DOMFragmentImpl();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.w3c.html.HTMLDocumentFragment#querySelector(java.lang.String)
     */
    @Override
    public Element querySelector(String selectors) {
        QuerySelectorImpl qsel = new QuerySelectorImpl();
        return qsel.documentQuerySelector(this.document, selectors);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.w3c.html.HTMLDocumentFragment#querySelectorAll(java.lang.
     * String)
     */
    @Override
    public NodeList querySelectorAll(String selectors) {
        QuerySelectorImpl qsel = new QuerySelectorImpl();
        return qsel.documentQuerySelectorAll(this.document, selectors);
    }
}
