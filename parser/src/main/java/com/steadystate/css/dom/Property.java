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

package com.steadystate.css.dom;

import org.w3c.dom.css.CSSValue;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.util.LangUtils;

/**
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class Property extends CSSOMObjectImpl implements CSSFormatable {
    private static final long serialVersionUID = 8720637891949104989L;

    private String name_;
    private CSSValue value_;
    private boolean important_;

    /**
     * Creates new Property.
     * @param name the name
     * @param value the value
     * @param important true if the important flag set
     */
    public Property(final String name, final CSSValue value, final boolean important) {
        name_ = name;
        value_ = value;
        important_ = important;
    }

    /**
     * Constructor.
     * The attributes are null.
     */
    public Property() {
        super();
    }

    /**
     * Returns the name.
     * @return the name
     */
    public String getName() {
        return name_;
    }

    /**
     * Sets the name to a new value.
     * @param name the new name
     */
    public void setName(final String name) {
        name_ = name;
    }

    /**
     * Returns the value.
     * @return the value
     */
    public CSSValue getValue() {
        return value_;
    }

    /**
     * Returns true if the important flag is set.
     * @return true or false
     */
    public boolean isImportant() {
        return important_;
    }

    /**
     * Sets the value to a new value.
     * @param value the new CSSValue
     */
    public void setValue(final CSSValue value) {
        value_ = value;
    }

    /**
     * Sets the important flag to a new value.
     * @param important the new flag value
     */
    public void setImportant(final boolean important) {
        important_ = important;
    }

    /**
     * Same as {@link #getCssText(CSSFormat)} but using the default format.
     *
     * @return the formated string
     */
    public String getCssText() {
        return getCssText(null);
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final StringBuilder result = new StringBuilder();
        result.append(name_);
        if (null != value_) {
            result.append(": ");
            if (value_ instanceof CSSFormatable) {
                result.append(((CSSFormatable) value_).getCssText(format));
            }
            else {
                result.append(value_.toString());
            }
        }
        if (important_) {
            result.append(" !important");
        }
        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getCssText(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Property)) {
            return false;
        }
        final Property p = (Property) obj;
        return super.equals(obj)
            && (important_ == p.important_)
            && LangUtils.equals(name_, p.name_)
            && LangUtils.equals(value_, p.value_);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, important_);
        hash = LangUtils.hashCode(hash, name_);
        hash = LangUtils.hashCode(hash, value_);
        return hash;
    }
}