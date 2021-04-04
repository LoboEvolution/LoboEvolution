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

import com.gargoylesoftware.css.parser.AbstractLocatable;

/**
 * <p>Abstract AbstractCSSRuleImpl class.</p>
 *
 * Author Ronald Brill
 *
 */
public abstract class AbstractCSSRuleImpl extends AbstractLocatable implements Serializable {

    private CSSStyleSheetImpl parentStyleSheet_;
    private AbstractCSSRuleImpl parentRule_;

    /**
     * Ctor.
     *
     * @param parentStyleSheet the parent style sheet
     * @param parentRule the parent rule
     */
    public AbstractCSSRuleImpl(final CSSStyleSheetImpl parentStyleSheet, final AbstractCSSRuleImpl parentRule) {
        super();
        setParentStyleSheet(parentStyleSheet);
        setParentRule(parentRule);
    }

    /**
     * <p>getCssText.</p>
     *
     * @return the current css text
     */
    public abstract String getCssText();

    /**
     * Sets the css text.
     *
     * @param text the new css text
     */
    public abstract void setCssText(String text);

    /**
     * Sets the parent style sheet.
     *
     * @param parentStyleSheet the new parent style sheet
     */
    public void setParentStyleSheet(final CSSStyleSheetImpl parentStyleSheet) {
        parentStyleSheet_ = parentStyleSheet;
    }

    /**
     * Sets the parent rule.
     *
     * @param parentRule the new parent rule
     */
    public void setParentRule(final AbstractCSSRuleImpl parentRule) {
        parentRule_ = parentRule;
    }

    /**
     * <p>getParentStyleSheet.</p>
     *
     * @return the parent style sheet
     */
    public CSSStyleSheetImpl getParentStyleSheet() {
        return parentStyleSheet_;
    }

    /**
     * <p>getParentRule.</p>
     *
     * @return the parent rule
     */
    public AbstractCSSRuleImpl getParentRule() {
        return parentRule_;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractCSSRuleImpl)) {
            return false;
        }
        return super.equals(obj);
        // don't use parentRule and parentStyleSheet in equals()
        // recursive loop -> stack overflow!
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int hash = super.hashCode();
        // don't use parentRule and parentStyleSheet in hashCode()
        // recursive loop -> stack overflow!
        return hash;
    }
}
