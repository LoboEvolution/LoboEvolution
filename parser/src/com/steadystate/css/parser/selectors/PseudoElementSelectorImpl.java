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

import org.w3c.css.sac.ElementSelector;
import org.w3c.css.sac.Selector;

import com.steadystate.css.parser.LocatableImpl;


/**
 * The Class PseudoElementSelectorImpl.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class PseudoElementSelectorImpl extends LocatableImpl implements ElementSelector, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2913936296006875268L;

    /** The local name_. */
    private String localName_;

    /**
     * Sets the locale name.
     *
     * @param localName the new locale name
     */
    public void setLocaleName(final String localName) {
        localName_ = localName;
    }

    /**
     * Instantiates a new pseudo element selector impl.
     *
     * @param localName the local name
     */
    public PseudoElementSelectorImpl(final String localName) {
        setLocaleName(localName);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Selector#getSelectorType()
     */
    public short getSelectorType() {
        return Selector.SAC_PSEUDO_ELEMENT_SELECTOR;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ElementSelector#getNamespaceURI()
     */
    public String getNamespaceURI() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ElementSelector#getLocalName()
     */
    public String getLocalName() {
        return localName_;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return localName_;
    }
}
