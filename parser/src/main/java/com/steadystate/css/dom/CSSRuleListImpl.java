/*
 * Copyright (C) 1999-2017 David Schweinsberg.
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.util.LangUtils;

/**
 * Implementation of {@link CSSRuleList}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 * @author rbri
 */
public class CSSRuleListImpl implements CSSRuleList, CSSFormatable, Serializable {

	private static final long serialVersionUID = -1269068897476453290L;

	private List<CSSRule> rules_;

	public List<CSSRule> getRules() {
		if (rules_ == null) {
			rules_ = new ArrayList<CSSRule>();
		}
		return rules_;
	}

	public void setRules(final List<CSSRule> rules) {
		rules_ = rules;
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
	@Override
	public String getCssText(final CSSFormat format) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getLength(); i++) {
			if (i > 0) {
				sb.append("\r\n");
			}

			final CSSRule rule = item(i);
			sb.append(((CSSFormatable) rule).getCssText(format));
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return getCssText(null);
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
		if (crl == null || getLength() != crl.getLength()) {
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
