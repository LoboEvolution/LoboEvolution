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
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;

import com.gargoylesoftware.css.util.LangUtils;

/**
 * Implementation of {@link CSSRuleList}.
 *
 * @author Ronald Brill
 */
public class CSSRuleListImpl implements CSSRuleList, Serializable {

    private List<CSSRule> rules_ = new ArrayList<CSSRule>();

    public List<CSSRule> getRules() {
        return rules_;
    }

    public CSSRuleListImpl() {
        super();
    }

    @Override
    public int getLength() {
        return getRules().size();
    }

    @Override
    public CSSRule item(final int index) {
        if (index < 0 || null == rules_ || index >= rules_.size()) {
            return null;
        }
        return rules_.get(index);
    }

    public void add(final CSSRule rule) {
        getRules().add(rule);
    }

    public void insert(final CSSRule rule, final int index) {
        getRules().add(index, rule);
    }

    public void delete(final int index) {
        getRules().remove(index);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getLength(); i++) {
            if (i > 0) {
                sb.append("\r\n");
            }

            final CSSRule rule = item(i);
            sb.append(rule.toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSRuleList)) {
            return false;
        }
        final CSSRuleList crl = (CSSRuleList) obj;
        return equalsRules(crl);
    }

    private boolean equalsRules(final CSSRuleList crl) {
        if ((crl == null) || (getLength() != crl.getLength())) {
            return false;
        }
        for (int i = 0; i < getLength(); i++) {
            final CSSRule cssRule1 = item(i);
            final CSSRule cssRule2 = crl.item(i);
            if (!LangUtils.equals(cssRule1, cssRule2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        hash = LangUtils.hashCode(hash, rules_);
        return hash;
    }
}
