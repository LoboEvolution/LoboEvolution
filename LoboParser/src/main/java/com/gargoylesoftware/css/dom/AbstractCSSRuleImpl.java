/*
 * Copyright (c) 2018 Ronald Brill.
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

import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleSheet;

import com.gargoylesoftware.css.parser.AbstractLocatable;

/**
 * @author Ronald Brill
 */
public abstract class AbstractCSSRuleImpl extends AbstractLocatable implements Serializable {

    private CSSStyleSheetImpl parentStyleSheet_;
    private CSSRule parentRule_;

    protected CSSStyleSheetImpl getParentStyleSheetImpl() {
        return parentStyleSheet_;
    }

    public void setParentStyleSheet(final CSSStyleSheetImpl parentStyleSheet) {
        parentStyleSheet_ = parentStyleSheet;
    }

    public void setParentRule(final CSSRule parentRule) {
        parentRule_ = parentRule;
    }

    public AbstractCSSRuleImpl(final CSSStyleSheetImpl parentStyleSheet, final CSSRule parentRule) {
        super();
        parentStyleSheet_ = parentStyleSheet;
        parentRule_ = parentRule;
    }

    public AbstractCSSRuleImpl() {
        super();
    }

    public CSSStyleSheet getParentStyleSheet() {
        return parentStyleSheet_;
    }

    public CSSRule getParentRule() {
        return parentRule_;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSRule)) {
            return false;
        }
        return super.equals(obj);
        // don't use parentRule and parentStyleSheet in equals()
        // recursive loop -> stack overflow!
    }

    @Override
    public int hashCode() {
        final int hash = super.hashCode();
        // don't use parentRule and parentStyleSheet in hashCode()
        // recursive loop -> stack overflow!
        return hash;
    }
}
