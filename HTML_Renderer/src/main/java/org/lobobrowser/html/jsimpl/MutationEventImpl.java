/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.w3c.events.MutationEvent;
import org.lobobrowser.w3c.html.HTMLElement;
import org.w3c.dom.Node;

/**
 * The Class MutationEventImpl.
 */
public class MutationEventImpl extends EventImpl implements MutationEvent {

    /** The related. */
    private Node related;

    /** The pvalue. */
    private String pvalue;

    /** The nvalue. */
    private String nvalue;

    /** The attname. */
    private String attname;

    /** The attchid. */
    private short attchid;

    /**
     * Instantiates a new mutation event impl.
     */
    public MutationEventImpl() {
    }

    /**
     * Instantiates a new mutation event impl.
     *
     * @param type
     *            the type
     * @param srcElement
     *            the src element
     */
    public MutationEventImpl(String type, HTMLElement srcElement) {
        super(type, srcElement);
    }

    /**
     * Instantiates a new mutation event impl.
     *
     * @param type
     *            the type
     * @param srcElement
     *            the src element
     * @param mouseEvent
     *            the mouse event
     * @param leafX
     *            the leaf x
     * @param leafY
     *            the leaf y
     */
    public MutationEventImpl(String type, HTMLElement srcElement,
            InputEvent mouseEvent, int leafX, int leafY) {
        super(type, srcElement, mouseEvent, leafX, leafY);
    }

    /**
     * Instantiates a new mutation event impl.
     *
     * @param type
     *            the type
     * @param srcElement
     *            the src element
     * @param keyEvent
     *            the key event
     */
    public MutationEventImpl(String type, HTMLElement srcElement,
            KeyEvent keyEvent) {
        super(type, srcElement, keyEvent);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.w3c.events.MutationEvent#initMutationEvent(java.lang
     * .String, boolean, boolean, org.w3c.dom.Node, java.lang.String,
     * java.lang.String, java.lang.String, short)
     */
    @Override
    public void initMutationEvent(String type, boolean canBubble,
            boolean cancelable, Node relatedNode, String prevValue,
            String newValue, String attrName, short attrChange) {

        setType(type);
        setCanBubble(canBubble);
        setCancelable(cancelable);
        related = relatedNode;
        pvalue = prevValue;
        nvalue = newValue;
        attname = attrName;
        attchid = attrChange;

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.events.MutationEvent#getRelatedNode()
     */
    @Override
    public Node getRelatedNode() {
        return related;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.events.MutationEvent#getPrevValue()
     */
    @Override
    public String getPrevValue() {
        return pvalue;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.events.MutationEvent#getNewValue()
     */
    @Override
    public String getNewValue() {
        return nvalue;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.events.MutationEvent#getAttrName()
     */
    @Override
    public String getAttrName() {
        return attname;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.events.MutationEvent#getAttrChange()
     */
    @Override
    public short getAttrChange() {
        return attchid;
    }
}
