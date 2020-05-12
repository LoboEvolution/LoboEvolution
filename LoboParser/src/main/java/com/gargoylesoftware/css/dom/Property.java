/*
 * Copyright (c) 2019 Ronald Brill.
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
package com.gargoylesoftware.css.dom;

import java.io.Serializable;

import com.gargoylesoftware.css.parser.AbstractLocatable;
import com.gargoylesoftware.css.util.LangUtils;

/**
 * <p>Property class.</p>
 *
 * @author Ronald Brill
 * @version $Id: $Id
 */
public class Property extends AbstractLocatable implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name_;
    private CSSValueImpl value_;
    private boolean important_;

    /**
     * Creates new Property.
     *
     * @param name the name
     * @param value the value
     * @param important true if the important flag set
     */
    public Property(final String name, final CSSValueImpl value, final boolean important) {
        name_ = name;
        value_ = value;
        important_ = important;
    }

    /**
     * Returns the name.
     *
     * @return the name
     */
    public String getName() {
        return name_;
    }

    /**
     * Sets the name to a new value.
     *
     * @param name the new name
     */
    public void setName(final String name) {
        name_ = name;
    }

    /**
     * Returns the value.
     *
     * @return the value
     */
    public CSSValueImpl getValue() {
        return value_;
    }

    /**
     * Returns true if the important flag is set.
     *
     * @return true or false
     */
    public boolean isImportant() {
        return important_;
    }

    /**
     * Sets the value to a new value.
     *
     * @param value the new CSSValue
     */
    public void setValue(final CSSValueImpl value) {
        value_ = value;
    }

    /**
     * Sets the important flag to a new value.
     *
     * @param important the new flag value
     */
    public void setImportant(final boolean important) {
        important_ = important;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name_);
        if (null != value_) {
            sb.append(": ");
            sb.append(value_.toString());
        }
        if (important_) {
            sb.append(" !important");
        }
        return sb.toString();
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, important_);
        hash = LangUtils.hashCode(hash, name_);
        hash = LangUtils.hashCode(hash, value_);
        return hash;
    }
}
