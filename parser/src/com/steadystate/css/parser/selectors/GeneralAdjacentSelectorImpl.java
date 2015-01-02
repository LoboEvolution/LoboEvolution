/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2014 David Schweinsberg.  All rights reserved.
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
 * @author Ahmed Ashour
 * @author rbri
 */
public class GeneralAdjacentSelectorImpl extends LocatableImpl implements SiblingSelector, Serializable {

    private static final long serialVersionUID = 1292704016876205605L;

    private short nodeType_;
    private Selector selector_;
    private SimpleSelector siblingSelector_;

    public void setNodeType(final short nodeType) {
        nodeType_ = nodeType;
    }

    public void setSelector(final Selector child) {
        selector_ = child;
        if (child instanceof Locatable) {
            setLocator(((Locatable) child).getLocator());
        }
        else if (child == null) {
            setLocator(null);
        }
    }

    public void setSiblingSelector(final SimpleSelector directAdjacent) {
        siblingSelector_ = directAdjacent;
    }

    public GeneralAdjacentSelectorImpl(final short nodeType,
            final Selector child, final SimpleSelector directAdjacent) {
        setNodeType(nodeType);
        setSelector(child);
        setSiblingSelector(directAdjacent);
    }

    public short getNodeType() {
        return nodeType_;
    }

    public short getSelectorType() {
        return Selector.SAC_ANY_NODE_SELECTOR; //for now
    }

    public Selector getSelector() {
        return selector_;
    }

    public SimpleSelector getSiblingSelector() {
        return siblingSelector_;
    }

    @Override
    public String toString() {
        return selector_.toString() + " ~ " + siblingSelector_.toString();
    }
}
