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

import org.w3c.css.sac.CharacterDataSelector;
import org.w3c.css.sac.Selector;

import com.steadystate.css.parser.LocatableImpl;


/**
 * The Class CharacterDataSelectorImpl.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class CharacterDataSelectorImpl extends LocatableImpl implements CharacterDataSelector, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4635511567927852889L;

    /** The data_. */
    private String data_;

    /**
     * Sets the data.
     *
     * @param data the new data
     */
    public void setData(final String data) {
        data_ = data;
    }

    /**
     * Instantiates a new character data selector impl.
     *
     * @param data the data
     */
    public CharacterDataSelectorImpl(final String data) {
        setData(data);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Selector#getSelectorType()
     */
    public short getSelectorType() {
        return Selector.SAC_CDATA_SECTION_NODE_SELECTOR;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.CharacterDataSelector#getData()
     */
    public String getData() {
        return data_;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getData();
    }
}
