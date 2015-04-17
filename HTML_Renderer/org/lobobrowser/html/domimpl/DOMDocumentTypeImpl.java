/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 15, 2005
 */
package org.lobobrowser.html.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * The Class DOMDocumentTypeImpl.
 */
public class DOMDocumentTypeImpl extends DOMNodeImpl implements DocumentType {

    /** The qualified name. */
    private final String qualifiedName;

    /** The public id. */
    private final String publicId;

    /** The system id. */
    private final String systemId;

    /**
     * Instantiates a new DOM document type impl.
     *
     * @param qname
     *            the qname
     * @param publicId
     *            the public id
     * @param systemId
     *            the system id
     */
    public DOMDocumentTypeImpl(String qname, String publicId, String systemId) {
        super();
        this.qualifiedName = qname;
        this.publicId = publicId;
        this.systemId = systemId;
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
        return this.getName();
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
        // nop
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getNodeType()
     */
    @Override
    public short getNodeType() {
        return org.w3c.dom.Node.DOCUMENT_TYPE_NODE;
    }

    /*
     * (non-Javadoc)
     * @see org.w3c.dom.DocumentType#getName()
     */
    @Override
    public String getName() {
        return this.qualifiedName;
    }

    /*
     * (non-Javadoc)
     * @see org.w3c.dom.DocumentType#getEntities()
     */
    @Override
    public NamedNodeMap getEntities() {
        // TODO: DOCTYPE declared entities
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.w3c.dom.DocumentType#getNotations()
     */
    @Override
    public NamedNodeMap getNotations() {
        // TODO: DOCTYPE notations
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.w3c.dom.DocumentType#getPublicId()
     */
    @Override
    public String getPublicId() {
        return this.publicId;
    }

    /*
     * (non-Javadoc)
     * @see org.w3c.dom.DocumentType#getSystemId()
     */
    @Override
    public String getSystemId() {
        return this.systemId;
    }

    /*
     * (non-Javadoc)
     * @see org.w3c.dom.DocumentType#getInternalSubset()
     */
    @Override
    public String getInternalSubset() {
        // TODO: DOCTYPE internal subset
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.DOMNodeImpl#createSimilarNode()
     */
    @Override
    protected Node createSimilarNode() {
        return new DOMDocumentTypeImpl(this.qualifiedName, this.publicId,
                this.systemId);
    }
}
