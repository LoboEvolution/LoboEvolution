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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import com.gargoylesoftware.css.parser.CSSException;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.media.MediaQueryList;
import com.gargoylesoftware.css.parser.selector.ElementSelector;
import com.gargoylesoftware.css.parser.selector.Selector;
import com.gargoylesoftware.css.util.LangUtils;
import com.gargoylesoftware.css.util.ThrowCssExceptionErrorHandler;

/**
 * Implementation of CSSStyleSheet.
 *
 * @author Ronald Brill
 */
public class CSSStyleSheetImpl implements Serializable {

    private boolean disabled_;
    private Node ownerNode_;
    private String href_;
    private String title_;
    private MediaListImpl media_;
    private AbstractCSSRuleImpl ownerRule_;
    private CSSRuleListImpl cssRules_;
    private CSSStyleSheetRuleIndex index_;

    /**
     * Ctor.
     */
    public CSSStyleSheetImpl() {
        super();
    }

    /**
     * @return the disable state
     */
    public boolean getDisabled() {
        return disabled_;
    }

    /**
     * We will need to respond more fully if a stylesheet is disabled, probably
     * by generating an event for the main application.
     * @param disabled the new disabled
     */
    public void setDisabled(final boolean disabled) {
        disabled_ = disabled;
    }

    /**
     * @return the owner node
     */
    public Node getOwnerNode() {
        return ownerNode_;
    }

    /**
     * @return the href
     */
    public String getHref() {
        return href_;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title_;
    }

    /**
     * @return the media list
     */
    public MediaListImpl getMedia() {
        return media_;
    }

    /**
     * @return the owner rule
     */
    public AbstractCSSRuleImpl getOwnerRule() {
        return ownerRule_;
    }

    /**
     * @return the css rules
     */
    public CSSRuleListImpl getCssRules() {
        if (cssRules_ == null) {
            cssRules_ = new CSSRuleListImpl();
        }
        return cssRules_;
    }

    /**
     * inserts a new rule.
     *
     * @param rule the rule to insert
     * @param index the insert pos
     * @throws DOMException in case of error
     */
    public void insertRule(final String rule, final int index) throws DOMException {
        try {
            final CSSOMParser parser = new CSSOMParser();
            parser.setParentStyleSheet(this);
            parser.setErrorHandler(ThrowCssExceptionErrorHandler.INSTANCE);
            final AbstractCSSRuleImpl r = parser.parseRule(rule);

            if (r == null) {
                // this should neven happen because of the ThrowCssExceptionErrorHandler
                throw new DOMExceptionImpl(
                        DOMException.SYNTAX_ERR,
                        DOMExceptionImpl.SYNTAX_ERROR,
                        "Parsing rule '" + rule + "' failed.");
            }

            if (getCssRules().getLength() > 0) {
                // We need to check that this type of rule can legally go into
                // the requested position.
                int msg = -1;
                if (r instanceof CSSCharsetRuleImpl) {

                    // Index must be 0, and there can be only one charset rule
                    if (index != 0) {
                        msg = DOMExceptionImpl.CHARSET_NOT_FIRST;
                    }
                    else if (getCssRules().getRules().get(0) instanceof CSSCharsetRuleImpl) {
                        msg = DOMExceptionImpl.CHARSET_NOT_UNIQUE;
                    }
                }
                else if (r instanceof CSSImportRuleImpl) {
                    // Import rules must preceed all other rules (except
                    // charset rules)
                    if (index <= getCssRules().getLength()) {
                        for (int i = 0; i < index; i++) {
                            final AbstractCSSRuleImpl ri = getCssRules().getRules().get(i);
                            if (!(ri instanceof CSSCharsetRuleImpl) && !(ri instanceof CSSImportRuleImpl)) {
                                msg = DOMExceptionImpl.IMPORT_NOT_FIRST;
                                break;
                            }
                        }
                    }
                }
                else {
                    if (index <= getCssRules().getLength()) {
                        for (int i = index; i < getCssRules().getLength(); i++) {
                            final AbstractCSSRuleImpl ri = getCssRules().getRules().get(i);
                            if ((ri instanceof CSSCharsetRuleImpl) || (ri instanceof CSSImportRuleImpl)) {
                                msg = DOMExceptionImpl.INSERT_BEFORE_IMPORT;
                                break;
                            }
                        }
                    }
                }
                if (msg > -1) {
                    throw new DOMExceptionImpl(DOMException.HIERARCHY_REQUEST_ERR, msg);
                }
            }

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
     * delete the rule at the given pos.
     *
     * @param index the pos
     * @throws DOMException in case of error
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
     * Set the owner node.
     * @param ownerNode the new node
     */
    public void setOwnerNode(final Node ownerNode) {
        ownerNode_ = ownerNode;
    }

    /**
     * Set the href.
     * @param href the new href
     */
    public void setHref(final String href) {
        href_ = href;
    }

    /**
     * Set the title.
     * @param title the new title
     */
    public void setTitle(final String title) {
        title_ = title;
    }

    /**
     * Set the media text.
     * @param mediaText the new media text
     */
    public void setMediaText(final String mediaText) {
        try {
            final CSSOMParser parser = new CSSOMParser();
            final MediaQueryList sml = parser.parseMedia(mediaText);
            media_ = new MediaListImpl(sml);
        }
        catch (final IOException e) {
            // TODO handle exception
        }
    }

    /**
     * @param ownerRule the new ownerRule
     */
    public void setOwnerRule(final AbstractCSSRuleImpl ownerRule) {
        ownerRule_ = ownerRule;
    }

    /**
     * @param rules the new rules
     */
    public void setCssRules(final CSSRuleListImpl rules) {
        cssRules_ = rules;
    }

    @Override
    public String toString() {
        return getCssRules().toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSStyleSheetImpl)) {
            return false;
        }
        final CSSStyleSheetImpl css = (CSSStyleSheetImpl) obj;
        boolean eq = LangUtils.equals(getCssRules(), css.getCssRules());
        eq = eq && (getDisabled() == css.getDisabled());
        eq = eq && LangUtils.equals(getHref(), css.getHref());
        eq = eq && LangUtils.equals(getMedia(), css.getMedia());
        eq = eq && LangUtils.equals(getTitle(), css.getTitle());
        return eq;
    }

    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        hash = LangUtils.hashCode(hash, cssRules_);
        hash = LangUtils.hashCode(hash, disabled_);
        hash = LangUtils.hashCode(hash, href_);
        hash = LangUtils.hashCode(hash, media_);
        hash = LangUtils.hashCode(hash, ownerNode_);
        hash = LangUtils.hashCode(hash, title_);
        return hash;
    }

    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.writeObject(cssRules_);
        out.writeBoolean(disabled_);
        out.writeObject(href_);
        out.writeObject(media_);
        out.writeObject(title_);
    }

    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        cssRules_ = (CSSRuleListImpl) in.readObject();
        if (cssRules_ != null) {
            for (int i = 0; i < cssRules_.getLength(); i++) {
                final AbstractCSSRuleImpl cssRule = cssRules_.getRules().get(i);
                cssRule.setParentStyleSheet(this);
            }
        }
        disabled_ = in.readBoolean();
        href_ = (String) in.readObject();
        media_ = (MediaListImpl) in.readObject();
        title_ = (String) in.readObject();
    }

    /**
     * @return the CSSStyleSheetRuleIndex
     */
    public CSSStyleSheetRuleIndex getRuleIndex() {
        return index_;
    }

    /**
     * Set the CSSStyleSheetRuleIndex.
     * @param index the new index
     */
    public void setRuleIndex(final CSSStyleSheetRuleIndex index) {
        index_ = index;
    }

    /**
     * Clean the index.
     */
    public void resetRuleIndex() {
        index_ = null;
    }

    /**
     * SelectorEntry.
     */
    public static final class SelectorEntry {
        private Selector selector_;
        private CSSStyleRuleImpl rule_;

        SelectorEntry(final Selector selector, final CSSStyleRuleImpl rule) {
            selector_ = selector;
            rule_ = rule;
        }

        /**
         * @return the selector
         */
        public Selector getSelector() {
            return selector_;
        }

        /**
         * @return the rule
         */
        public CSSStyleRuleImpl getRule() {
            return rule_;
        }
    }

    /**
     * CSSStyleSheetRuleIndex.
     */
    public static class CSSStyleSheetRuleIndex {

        private static final class SelectorIndex {

            private final Map<String, List<SelectorEntry>> keyToSelectors_ = new HashMap<>();

            void add(final String key, final SelectorEntry selector) {
                List<SelectorEntry> entry = keyToSelectors_.get(key);
                if (entry == null) {
                    entry = new ArrayList<>();
                    keyToSelectors_.put(key, entry);
                }
                entry.add(selector);
            }

            List<SelectorEntry> get(final String key) {
                final List<SelectorEntry> entry = keyToSelectors_.get(key);
                if (entry == null) {
                    return Collections.emptyList();
                }
                return entry;
            }
        }

        private static final MediaListImpl DEFAULT_MEDIA_LIST = new MediaListImpl(null);

        private final List<CSSStyleSheetRuleIndex> children_ = new ArrayList<>();

        private MediaListImpl mediaList_ = DEFAULT_MEDIA_LIST;
        private final SelectorIndex elementSelectors_ = new SelectorIndex();
        private final SelectorIndex classSelectors_ = new SelectorIndex();
        private final List<SelectorEntry> otherSelectors_ = new ArrayList<>();

        /**
         * Add an ElementSelector.
         *
         * @param elementSelector the selector to be added
         * @param s the selector
         * @param styleRule the rule
         */
        public void addElementSelector(final ElementSelector elementSelector,
                                        final Selector s, final CSSStyleRuleImpl styleRule) {
            final String elementName = elementSelector.getLocalNameLowerCase();
            elementSelectors_.add(elementName, new SelectorEntry(s, styleRule));
        }

        /**
         * Add a ClassSelector.
         *
         * @param elementSelector the selector to be added
         * @param className the class name
         * @param s the selector
         * @param styleRule the rule
         */
        public void addClassSelector(final ElementSelector elementSelector, final String className,
                final Selector s, final CSSStyleRuleImpl styleRule) {
            final String elementName = elementSelector.getLocalNameLowerCase();
            final String key;
            if (elementName == null) {
                key = "." + className;
            }
            else {
                key = elementName + "." + className;
            }
            classSelectors_.add(key, new SelectorEntry(s, styleRule));
        }

        /**
         * Add a OtherSelector.
         *
         * @param s the selector
         * @param styleRule the rule
         */
        public void addOtherSelector(final Selector s, final CSSStyleRuleImpl styleRule) {
            final SelectorEntry selectorEntry = new SelectorEntry(s, styleRule);
            otherSelectors_.add(selectorEntry);
        }

        /**
         * Add a media list.
         * @param mediaList the list to add
         * @return the CSSStyleSheetRuleIndex
         */
        public CSSStyleSheetRuleIndex addMedia(final MediaListImpl mediaList) {
            final String media = mediaList.getMediaText();
            for (CSSStyleSheetRuleIndex cssStyleSheetRuleIndex : children_) {
                if (media.equals(cssStyleSheetRuleIndex.getMediaList().getMediaText())) {
                    return cssStyleSheetRuleIndex;
                }
            }

            final CSSStyleSheetRuleIndex index = new CSSStyleSheetRuleIndex();
            index.mediaList_ = mediaList;

            children_.add(index);
            return index;
        }

        /**
         * @return return the medial list
         */
        public MediaListImpl getMediaList() {
            return mediaList_;
        }

        /**
         * @return the children
         */
        public List<CSSStyleSheetRuleIndex> getChildren() {
            return children_;
        }

        /**
         * @param elementName the element
         * @param classes the classes
         * @return Iterator of SelectorEntry
         */
        public Iterator<SelectorEntry> getSelectorEntriesIteratorFor(final String elementName, final String[] classes) {
            return new SelectorEntriesIterator(this, elementName, classes);
        }
    }

    static final class SelectorEntriesIterator implements Iterator<SelectorEntry> {
        private LinkedList<Iterator<SelectorEntry>> iterators_;

        SelectorEntriesIterator(final CSSStyleSheetRuleIndex index,
                final String elementName,
                final String[] classes) {

            iterators_ = new LinkedList<>();

            List<SelectorEntry> selectors = index.elementSelectors_.get(null);
            if (!selectors.isEmpty()) {
                iterators_.add(selectors.iterator());
            }

            selectors = index.elementSelectors_.get(elementName);
            if (!selectors.isEmpty()) {
                iterators_.add(selectors.iterator());
            }

            if (classes != null) {
                for (String clazz : classes) {
                    selectors = index.classSelectors_.get("." + clazz);
                    if (selectors != null && !selectors.isEmpty()) {
                        iterators_.add(selectors.iterator());
                    }

                    if (elementName != null) {
                        selectors = index.classSelectors_.get(elementName + "." + clazz);
                        if (selectors != null && !selectors.isEmpty()) {
                            iterators_.add(selectors.iterator());
                        }
                    }
                }
            }

            if (index.otherSelectors_ != null && !index.otherSelectors_.isEmpty()) {
                iterators_.add(index.otherSelectors_.iterator());
            }
        }

        @Override
        public SelectorEntry next() {
            if (iterators_.isEmpty()) {
                return null;
            }

            final Iterator<SelectorEntry> iter = iterators_.peek();
            if (iter.hasNext()) {
                return iter.next();
            }

            iterators_.removeFirst();
            return next();
        }

        @Override
        public boolean hasNext() {
            if (iterators_.isEmpty()) {
                return false;
            }

            final Iterator<SelectorEntry> iter = iterators_.peek();
            if (iter.hasNext()) {
                return true;
            }

            iterators_.pop();
            return hasNext();
        }
    }
}
