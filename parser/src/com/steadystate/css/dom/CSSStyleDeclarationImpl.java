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
    private static final long serialVersionUID = -2373755821317100189L;

    private static final String PRIORITY_IMPORTANT = "important";

    private CSSRule parentRule_;
    private List<Property> properties_ = new ArrayList<Property>();

    public void setParentRule(final CSSRule parentRule) {
        parentRule_ = parentRule;
    }

    public List<Property> getProperties() {
        return properties_;
    }

    public void setProperties(final List<Property> properties) {
        properties_ = properties;
    }

    public CSSStyleDeclarationImpl(final CSSRule parentRule) {
        parentRule_ = parentRule;
    }

    public CSSStyleDeclarationImpl() {
        // Empty.
    }

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

    public String getPropertyValue(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        return (p == null || p.getValue() == null) ? "" : p.getValue().toString();
    }

    public CSSValue getPropertyCSSValue(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        return (p == null) ? null : p.getValue();
    }

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

    public String getPropertyPriority(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        if (p == null) {
            return "";
        }
        return p.isImportant() ? PRIORITY_IMPORTANT : "";
    }

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

    public int getLength() {
        return properties_.size();
    }

    public String item(final int index) {
        final Property p = properties_.get(index);
        return (p == null) ? "" : p.getName();
    }

    public CSSRule getParentRule() {
        return parentRule_;
    }

    public void addProperty(final Property p) {
        if (null == p) {
            return;
        }
        properties_.add(p);
    }

    public Property getPropertyDeclaration(final String propertyName) {
        if (null == propertyName) {
            return null;
        }
        for (int i = properties_.size() - 1; i > -1; i--) {
            final Property p = properties_.get(i);
            if (p != null && propertyName.equalsIgnoreCase(p.getName())) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getCssText();
    }

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

    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        // don't use parentRule in hashCode()
        // recursive loop -> stack overflow!
        hash = LangUtils.hashCode(hash, properties_);
        return hash;
    }
}