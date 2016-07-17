/*
 * Copyright (C) 1999-2016 David Schweinsberg.  All rights reserved.
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
        final StringBuilder sb = new StringBuilder();
        sb.append(name_);
        if (null != value_) {
            sb.append(": ");
            sb.append(((CSSValueImpl) value_).getCssText(format));
        }
        if (important_) {
            sb.append(" !important");
        }
        return sb.toString();
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