/*
 * Copyright (c) 2019-2020 Ronald Brill.
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
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.DOMException;

import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.util.LangUtils;

/**
 * Implementation of CSSStyleDeclaration.
 *
 * Author Ronald Brill
 *
 */
public class CSSStyleDeclarationImpl implements Serializable {

    private static final String PRIORITY_IMPORTANT = "important";

    private AbstractCSSRuleImpl parentRule_;
    private List<Property> properties_ = new ArrayList<>();

    /**
     * Ctor.
     *
     * @param parentRule the parent role
     */
    public CSSStyleDeclarationImpl(final AbstractCSSRuleImpl parentRule) {
        parentRule_ = parentRule;
    }

    /**
     * <p>getProperties.</p>
     *
     * @return the properties
     */
    public List<Property> getProperties() {
        return properties_;
    }

    /**
     * <p>getCssText.</p>
     *
     * @return the current css text
     */
    public String getCssText() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < properties_.size(); ++i) {
            final Property p = properties_.get(i);
            if (p != null) {
                sb.append(p.toString());
            }
            if (i < properties_.size() - 1) {
                sb.append(";");
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    /**
     * Sets the css text.
     *
     * @param cssText the new css text
     * @throws org.w3c.dom.DOMException in case of error
     */
    public void setCssText(final String cssText) throws DOMException {
        try {
            final CSSOMParser parser = new CSSOMParser();
            properties_.clear();
            parser.parseStyleDeclaration(this, cssText);
        }
        catch (final Exception e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
    }

    /**
     * <p>getPropertyValue.</p>
     *
     * @param propertyName the property name
     * @return the property value
     */
    public String getPropertyValue(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        if (p == null || p.getValue() == null) {
            return "";
        }
        return p.getValue().toString();
    }

    /**
     * <p>getPropertyCSSValue.</p>
     *
     * @param propertyName the property name
     * @return the property value
     */
    public CSSValueImpl getPropertyCSSValue(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        return (p == null) ? null : p.getValue();
    }

    /**
     * Remove a property.
     *
     * @param propertyName the property name
     * @return the removed property
     * @throws org.w3c.dom.DOMException in case of error
     */
    public String removeProperty(final String propertyName) throws DOMException {
        if (null == propertyName) {
            return "";
        }
        for (int i = 0; i < properties_.size(); i++) {
            final Property p = properties_.get(i);
            if (p != null && propertyName.equalsIgnoreCase(p.getName())) {
                properties_.remove(i);
                if (p.getValue() == null) {
                    return "";
                }
                return p.getValue().toString();
            }
        }
        return "";
    }

    /**
     * <p>getPropertyPriority.</p>
     *
     * @param propertyName the name of the property
     * @return the priority
     */
    public String getPropertyPriority(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        if (p == null) {
            return "";
        }
        return p.isImportant() ? PRIORITY_IMPORTANT : "";
    }

    /**
     * Set a property.
     *
     * @param propertyName the name of the property
     * @param value the new value
     * @param priority the priority
     * @throws org.w3c.dom.DOMException in case of error
     */
    public void setProperty(
            final String propertyName,
            final String value,
            final String priority) throws DOMException {
        try {
            CSSValueImpl expr = null;
            if (!value.isEmpty()) {
                final CSSOMParser parser = new CSSOMParser();
                expr = parser.parsePropertyValue(value);
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

    /**
     * <p>getLength.</p>
     *
     * @return the properties count
     */
    public int getLength() {
        return properties_.size();
    }

    /**
     * <p>getParentRule.</p>
     *
     * @return the parent rule
     */
    public AbstractCSSRuleImpl getParentRule() {
        return parentRule_;
    }

    /**
     * Add a property.
     *
     * @param p the property to add
     */
    public void addProperty(final Property p) {
        if (null == p) {
            return;
        }
        properties_.add(p);
    }

    /**
     * <p>getPropertyDeclaration.</p>
     *
     * @param propertyName the name of the propery
     * @return the property
     */
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

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getCssText();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSStyleDeclarationImpl)) {
            return false;
        }
        final CSSStyleDeclarationImpl csd = (CSSStyleDeclarationImpl) obj;

        // don't use parentRule in equals()
        // recursive loop -> stack overflow!
        return equalsProperties(csd);
    }

    private boolean equalsProperties(final CSSStyleDeclarationImpl csd) {
        if ((csd == null) || (getLength() != csd.getLength())) {
            return false;
        }
        for (Property property : properties_) {
            final String propertyName = property.getName();
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

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        // don't use parentRule in hashCode()
        // recursive loop -> stack overflow!
        hash = LangUtils.hashCode(hash, properties_);
        return hash;
    }
}
