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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.w3c.dom.DOMException;

import com.gargoylesoftware.css.parser.CSSException;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.util.LangUtils;
import com.gargoylesoftware.css.util.ThrowCssExceptionErrorHandler;

/**
 * Implementation of CSSMediaRule.
 *
 * @author Ronald Brill
 * @version $Id: $Id
 */
public class CSSMediaRuleImpl extends AbstractCSSRuleImpl {

	private static final long serialVersionUID = 1L;
	private MediaListImpl mediaList_;
    private CSSRuleListImpl cssRules_;

    /**
     * Ctor.
     *
     * @param parentStyleSheet the parent style sheet
     * @param parentRule the parent rule
     * @param media the media
     */
    public CSSMediaRuleImpl(
            final CSSStyleSheetImpl parentStyleSheet,
            final AbstractCSSRuleImpl parentRule,
            final MediaListImpl media) {
        super(parentStyleSheet, parentRule);
        mediaList_ = media;
    }

    /** {@inheritDoc} */
    @Override
    public String getCssText() {
        final StringBuilder sb = new StringBuilder("@media ");

        sb.append(getMediaList().getMediaText());
        sb.append(" {");
        for (int i = 0; i < getCssRules().getLength(); i++) {
            final AbstractCSSRuleImpl rule = getCssRules().getRules().get(i);
            sb.append(rule.getCssText()).append(" ");
        }
        sb.append("}");
        return sb.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void setCssText(final String cssText) throws DOMException {
        try {
            final CSSOMParser parser = new CSSOMParser();
            final AbstractCSSRuleImpl r = parser.parseRule(cssText);

            // The rule must be a media rule
            if (r instanceof CSSMediaRuleImpl) {
                mediaList_ = ((CSSMediaRuleImpl) r).mediaList_;
                cssRules_ = ((CSSMediaRuleImpl) r).cssRules_;
            }
            else {
                throw new DOMExceptionImpl(
                    DOMException.INVALID_MODIFICATION_ERR,
                    DOMExceptionImpl.EXPECTING_MEDIA_RULE);
            }
        }
        catch (final CSSException e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
        catch (final IOException e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
    }

    /**
     * <p>getMediaList.</p>
     *
     * @return the media list
     */
    public MediaListImpl getMediaList() {
        return mediaList_;
    }

    /**
     * <p>getCssRules.</p>
     *
     * @return the css rules
     */
    public CSSRuleListImpl getCssRules() {
        if (cssRules_ == null) {
            cssRules_ = new CSSRuleListImpl();
        }
        return cssRules_;
    }

    /**
     * Insert a new rule at the given index.
     *
     * @param rule the rule to be inserted
     * @param index the insert pos
     * @throws org.w3c.dom.DOMException in case of error
     */
    public void insertRule(final String rule, final int index) throws DOMException {
        final CSSStyleSheetImpl parentStyleSheet = getParentStyleSheet();

        try {
            final CSSOMParser parser = new CSSOMParser();
            parser.setParentStyleSheet(parentStyleSheet);
            parser.setErrorHandler(ThrowCssExceptionErrorHandler.INSTANCE);
            final AbstractCSSRuleImpl r = parser.parseRule(rule);

            // Insert the rule into the list of rules
            getCssRules().insert(r, index);

        }
        catch (final IndexOutOfBoundsException e) {
            throw new DOMExceptionImpl(
                DOMException.INDEX_SIZE_ERR,
                DOMExceptionImpl.INDEX_OUT_OF_BOUNDS,
                e.getMessage());
        }
        catch (final CSSException e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
        catch (final IOException e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
    }

    /**
     * Removes a rule at the given index.
     *
     * @param index the insert pos
     * @throws org.w3c.dom.DOMException in case of error
     */
    public void deleteRule(final int index) throws DOMException {
        try {
            getCssRules().delete(index);
        }
        catch (final IndexOutOfBoundsException e) {
            throw new DOMExceptionImpl(
                DOMException.INDEX_SIZE_ERR,
                DOMExceptionImpl.INDEX_OUT_OF_BOUNDS,
                e.getMessage());
        }
    }

    /**
     * Replaces the rule list.
     *
     * @param rules the new rule list
     */
    public void setRuleList(final CSSRuleListImpl rules) {
        cssRules_ = rules;
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
        if (!(obj instanceof CSSMediaRuleImpl)) {
            return false;
        }
        final CSSMediaRuleImpl cmr = (CSSMediaRuleImpl) obj;
        return super.equals(obj)
            && LangUtils.equals(getMediaList(), cmr.getMediaList())
            && LangUtils.equals(getCssRules(), cmr.getCssRules());
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, mediaList_);
        hash = LangUtils.hashCode(hash, cssRules_);
        return hash;
    }

    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.writeObject(cssRules_);
        out.writeObject(mediaList_);
    }

    private void readObject(final ObjectInputStream in)
        throws IOException, ClassNotFoundException {

        cssRules_ = (CSSRuleListImpl) in.readObject();
        if (cssRules_ != null) {
            for (int i = 0; i < cssRules_.getLength(); i++) {
                final AbstractCSSRuleImpl cssRule = cssRules_.getRules().get(i);
                cssRule.setParentRule(this);
                cssRule.setParentStyleSheet(getParentStyleSheet());
            }
        }
        mediaList_ = (MediaListImpl) in.readObject();
    }
}
