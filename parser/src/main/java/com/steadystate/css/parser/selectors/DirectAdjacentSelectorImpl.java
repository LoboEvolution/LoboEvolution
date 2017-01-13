/*
 * Copyright (C) 1999-2017 David Schweinsberg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.steadystate.css.parser.selectors;

import java.io.Serializable;

import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SiblingSelector;
import org.w3c.css.sac.SimpleSelector;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.Locatable;
import com.steadystate.css.parser.LocatableImpl;

/**
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class DirectAdjacentSelectorImpl extends LocatableImpl implements SiblingSelector, CSSFormatable, Serializable {

    private static final long serialVersionUID = -7328602345833826516L;

    private short nodeType_;
    private Selector selector_;  // child
    private SimpleSelector siblingSelector_; // direct adjacent

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

    public DirectAdjacentSelectorImpl(final short nodeType,
            final Selector child, final SimpleSelector directAdjacent) {
        setNodeType(nodeType);
        setSelector(child);
        setSiblingSelector(directAdjacent);
    }

    public short getNodeType() {
        return nodeType_;
    }

    public short getSelectorType() {
        return Selector.SAC_DIRECT_ADJACENT_SELECTOR;
    }

    public Selector getSelector() {
        return selector_;
    }

    public SimpleSelector getSiblingSelector() {
        return siblingSelector_;
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final StringBuilder sb = new StringBuilder();

        if (null != selector_) {
            sb.append(((CSSFormatable) selector_).getCssText(format));
        }

        sb.append(" + ");

        if (null != siblingSelector_) {
            sb.append(((CSSFormatable) siblingSelector_).getCssText(format));
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return getCssText(null);
    }
}
