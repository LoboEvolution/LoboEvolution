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

import java.io.IOException;
import java.io.StringReader;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSUnknownRule;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.util.LangUtils;

/**
 * Implementation of {@link CSSUnknownRule}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 * @author rbri
 */
public class CSSUnknownRuleImpl extends AbstractCSSRuleImpl implements CSSUnknownRule {

	private static final long serialVersionUID = -268104019127675990L;

	private String text_;

	public String getText() {
		return text_;
	}

	public void setText(final String text) {
		text_ = text;
	}

	public CSSUnknownRuleImpl(final CSSStyleSheetImpl parentStyleSheet, final CSSRule parentRule, final String text) {
		super(parentStyleSheet, parentRule);
		text_ = text;
	}

	public CSSUnknownRuleImpl() {
		super();
	}

	@Override
	public short getType() {
		return UNKNOWN_RULE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCssText(final CSSFormat format) {
		if (null == text_) {
			return "";
		}
		return text_;
	}

	@Override
	public void setCssText(final String cssText) throws DOMException {
		final CSSStyleSheetImpl parentStyleSheet = getParentStyleSheetImpl();
		if (parentStyleSheet != null && parentStyleSheet.isReadOnly()) {
			throw new DOMExceptionImpl(DOMException.NO_MODIFICATION_ALLOWED_ERR,
					DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
		}

		try {
			final InputSource is = new InputSource(new StringReader(cssText));
			final CSSOMParser parser = new CSSOMParser();
			final CSSRule r = parser.parseRule(is);

			// The rule must be an unknown rule
			if (r.getType() == CSSRule.UNKNOWN_RULE) {
				text_ = ((CSSUnknownRuleImpl) r).text_;
			} else {
				throw new DOMExceptionImpl(DOMException.INVALID_MODIFICATION_ERR,
						DOMExceptionImpl.EXPECTING_FONT_FACE_RULE);
			}
		} catch (final CSSException e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR, DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		} catch (final IOException e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR, DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		}
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
		if (!(obj instanceof CSSUnknownRule)) {
			return false;
		}
		final CSSUnknownRule cur = (CSSUnknownRule) obj;
		return super.equals(obj) && LangUtils.equals(getCssText(), cur.getCssText());
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = LangUtils.hashCode(hash, text_);
		return hash;
	}

}
