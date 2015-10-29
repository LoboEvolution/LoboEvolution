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

import com.steadystate.css.format.CSSFormat;

/**
 * Special ElementSelectorImpl used by the parser at all the places where
 * the parser inserts a '*' selector. The selector will be ignored when
 * generating output.
 * This is done to be backward compatible.
 *
 * @author rbri
 */
public class SyntheticElementSelectorImpl extends ElementSelectorImpl {

    private static final long serialVersionUID = 3426191759125755798L;

    public SyntheticElementSelectorImpl() {
        super(null);
    }

    @Override
    public void setLocalName(final String localName) {
        throw new RuntimeException("Method setLocalName is not supported for SyntheticElementSelectorImpl.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCssText(final CSSFormat format) {
        return "";
    }
}
