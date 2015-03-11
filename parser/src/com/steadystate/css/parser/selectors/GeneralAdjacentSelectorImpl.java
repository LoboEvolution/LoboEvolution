/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2015 David Schweinsberg.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library:
 *
 * http://cssparser.sourceforge.net/
 * mailto:davidsch@users.sourceforge.net
 *
 */

package com.steadystate.css.parser.selectors;

import java.io.Serializable;

import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SiblingSelector;
import org.w3c.css.sac.SimpleSelector;

import com.steadystate.css.parser.Locatable;
import com.steadystate.css.parser.LocatableImpl;


/**
 * The Class GeneralAdjacentSelectorImpl.
 *
 * @author Ahmed Ashour
 * @author rbri
 */
public class GeneralAdjacentSelectorImpl extends LocatableImpl implements SiblingSelector, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1292704016876205605L;

    /** The node type_. */
    private short nodeType_;
    
    /** The selector_. */
    private Selector selector_;
    
    /** The sibling selector_. */
    private SimpleSelector siblingSelector_;

    /**
     * Sets the node type.
     *
     * @param nodeType the new node type
     */
    public void setNodeType(final short nodeType) {
        nodeType_ = nodeType;
    }

    /**
     * Sets the selector.
     *
     * @param child the new selector
     */
    public void setSelector(final Selector child) {
        selector_ = child;
        if (child instanceof Locatable) {
            setLocator(((Locatable) child).getLocator());
        }
        else if (child == null) {
            setLocator(null);
        }
    }

    /**
     * Sets the sibling selector.
     *
     * @param directAdjacent the new sibling selector
     */
    public void setSiblingSelector(final SimpleSelector directAdjacent) {
        siblingSelector_ = directAdjacent;
    }

    /**
     * Instantiates a new general adjacent selector impl.
     *
     * @param nodeType the node type
     * @param child the child
     * @param directAdjacent the direct adjacent
     */
    public GeneralAdjacentSelectorImpl(final short nodeType,
            final Selector child, final SimpleSelector directAdjacent) {
        setNodeType(nodeType);
        setSelector(child);
        setSiblingSelector(directAdjacent);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SiblingSelector#getNodeType()
     */
    public short getNodeType() {
        return nodeType_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Selector#getSelectorType()
     */
    public short getSelectorType() {
        return Selector.SAC_ANY_NODE_SELECTOR; //for now
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SiblingSelector#getSelector()
     */
    public Selector getSelector() {
        return selector_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SiblingSelector#getSiblingSelector()
     */
    public SimpleSelector getSiblingSelector() {
        return siblingSelector_;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return selector_.toString() + " ~ " + siblingSelector_.toString();
    }
}
