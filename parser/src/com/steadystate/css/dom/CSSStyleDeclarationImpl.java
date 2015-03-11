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

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.w3c.css.sac.InputSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.util.LangUtils;


/**
 * Implementation of {@link CSSStyleDeclaration}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 * @author Ahmed Ashour
 */
public class CSSStyleDeclarationImpl implements CSSStyleDeclaration, Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2373755821317100189L;

    /** The Constant PRIORITY_IMPORTANT. */
    private static final String PRIORITY_IMPORTANT = "important";

    /** The parent rule_. */
    private CSSRule parentRule_;
    
    /** The properties_. */
    private List<Property> properties_ = new ArrayList<Property>();

    /**
     * Sets the parent rule.
     *
     * @param parentRule the new parent rule
     */
    public void setParentRule(final CSSRule parentRule) {
        parentRule_ = parentRule;
    }

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public List<Property> getProperties() {
        return properties_;
    }

    /**
     * Sets the properties.
     *
     * @param properties the new properties
     */
    public void setProperties(final List<Property> properties) {
        properties_ = properties;
    }

    /**
     * Instantiates a new CSS style declaration impl.
     *
     * @param parentRule the parent rule
     */
    public CSSStyleDeclarationImpl(final CSSRule parentRule) {
        parentRule_ = parentRule;
    }

    /**
     * Instantiates a new CSS style declaration impl.
     */
    public CSSStyleDeclarationImpl() {
        // Empty.
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSStyleDeclaration#getCssText()
     */
    public String getCssText() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < properties_.size(); ++i) {
            final Property p = properties_.get(i);
            if (p != null) {
                sb.append(p.toString());
            }
            if (i < properties_.size() - 1) {
                sb.append("; ");
            }
        }
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSStyleDeclaration#setCssText(java.lang.String)
     */
    public void setCssText(final String cssText) throws DOMException {
        try {
            final InputSource is = new InputSource(new StringReader(cssText));
            final CSSOMParser parser = new CSSOMParser();
            properties_.clear();
            parser.parseStyleDeclaration(this, is);
        }
        catch (final Exception e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSStyleDeclaration#getPropertyValue(java.lang.String)
     */
    public String getPropertyValue(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        return (p == null || p.getValue() == null) ? "" : p.getValue().toString();
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSStyleDeclaration#getPropertyCSSValue(java.lang.String)
     */
    public CSSValue getPropertyCSSValue(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        return (p == null) ? null : p.getValue();
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSStyleDeclaration#removeProperty(java.lang.String)
     */
    public String removeProperty(final String propertyName) throws DOMException {
        if (null == propertyName) {
            return "";
        }
        for (int i = 0; i < properties_.size(); i++) {
            final Property p = properties_.get(i);
            if (p != null && propertyName.equalsIgnoreCase(p.getName())) {
                properties_.remove(i);
                return (p.getValue() == null) ? "" : p.getValue().toString();
            }
        }
        return "";
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSStyleDeclaration#getPropertyPriority(java.lang.String)
     */
    public String getPropertyPriority(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        if (p == null) {
            return "";
        }
        return p.isImportant() ? PRIORITY_IMPORTANT : "";
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSStyleDeclaration#setProperty(java.lang.String, java.lang.String, java.lang.String)
     */
    public void setProperty(
            final String propertyName,
            final String value,
            final String priority) throws DOMException {
        try {
            CSSValue expr = null;
            if (!value.isEmpty()) {
                final CSSOMParser parser = new CSSOMParser();
                final InputSource is = new InputSource(new StringReader(value));
                expr = parser.parsePropertyValue(is);
            }
            Property p = getPropertyDeclaration(propertyName);
            final boolean important = PRIORITY_IMPORTANT.equalsIgnoreCase(priority);
            if (p == null) {
                p = new Property(propertyName, expr, important);
                addProperty(p);
            }
            else {
                p.setValue(expr);
                p.setImportant(important);
            }
        }
        catch (final Exception e) {
            throw new DOMExceptionImpl(
                    DOMException.SYNTAX_ERR,
                    DOMExceptionImpl.SYNTAX_ERROR,
                    e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSStyleDeclaration#getLength()
     */
    public int getLength() {
        return properties_.size();
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSStyleDeclaration#item(int)
     */
    public String item(final int index) {
        final Property p = properties_.get(index);
        return (p == null) ? "" : p.getName();
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSStyleDeclaration#getParentRule()
     */
    public CSSRule getParentRule() {
        return parentRule_;
    }

    /**
     * Adds the property.
     *
     * @param p the p
     */
    public void addProperty(final Property p) {
        properties_.add(p);
    }

    /**
     * Gets the property declaration.
     *
     * @param propertyName the property name
     * @return the property declaration
     */
    public Property getPropertyDeclaration(final String propertyName) {
        if (null == propertyName) {
            return null;
        }
        for (int i = 0; i < properties_.size(); i++) {
            final Property p = properties_.get(i);
            if (p != null && propertyName.equalsIgnoreCase(p.getName())) {
                return p;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getCssText();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSStyleDeclaration)) {
            return false;
        }
        final CSSStyleDeclaration csd = (CSSStyleDeclaration) obj;

        // don't use parentRule in equals()
        // recursive loop -> stack overflow!
        return equalsProperties(csd);
    }

    /**
     * Equals properties.
     *
     * @param csd the csd
     * @return true, if successful
     */
    private boolean equalsProperties(final CSSStyleDeclaration csd) {
        if ((csd == null) || (getLength() != csd.getLength())) {
            return false;
        }
        for (int i = 0; i < getLength(); i++) {
            final String propertyName = item(i);
            // CSSValue propertyCSSValue1 = getPropertyCSSValue(propertyName);
            // CSSValue propertyCSSValue2 = csd.getPropertyCSSValue(propertyName);
            final String propertyValue1 = getPropertyValue(propertyName);
            final String propertyValue2 = csd.getPropertyValue(propertyName);
            if (!LangUtils.equals(propertyValue1, propertyValue2)) {
                return false;
            }
            final String propertyPriority1 = getPropertyPriority(propertyName);
            final String propertyPriority2 = csd.getPropertyPriority(propertyName);
            if (!LangUtils.equals(propertyPriority1, propertyPriority2)) {
                return false;
            }
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        // don't use parentRule in hashCode()
        // recursive loop -> stack overflow!
        hash = LangUtils.hashCode(hash, properties_);
        return hash;
    }
}
