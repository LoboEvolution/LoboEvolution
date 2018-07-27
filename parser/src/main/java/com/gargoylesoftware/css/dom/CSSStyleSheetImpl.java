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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;
import org.w3c.dom.stylesheets.StyleSheet;

import com.gargoylesoftware.css.parser.CSSException;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.InputSource;
import com.gargoylesoftware.css.parser.media.MediaQueryList;
import com.gargoylesoftware.css.parser.selector.ElementSelector;
import com.gargoylesoftware.css.parser.selector.Selector;
import com.gargoylesoftware.css.util.LangUtils;
import com.gargoylesoftware.css.util.ThrowCssExceptionErrorHandler;

/**
 * Implementation of {@link CSSStyleSheet}.
 *
 * @author Ronald Brill
 */
public class CSSStyleSheetImpl implements CSSStyleSheet, Serializable {

    private boolean disabled_;
    private Node ownerNode_;
    private String href_;
    private String title_;
    private MediaList media_;
    private CSSRule ownerRule_;
    private boolean readOnly_;
    private CSSRuleList cssRules_;
    private CSSStyleSheetRuleIndex index_;

    public void setMedia(final MediaList media) {
        media_ = media;
    }

    public CSSStyleSheetImpl() {
        super();
    }

    @Override
    public String getType() {
        return "text/css";
    }

    @Override
    public boolean getDisabled() {
        return disabled_;
    }

    /**
     * We will need to respond more fully if a stylesheet is disabled, probably
     * by generating an event for the main application.
     */
    @Override
    public void setDisabled(final boolean disabled) {
        disabled_ = disabled;
    }

    @Override
    public Node getOwnerNode() {
        return ownerNode_;
    }

    @Override
    public StyleSheet getParentStyleSheet() {
        return null;
    }

    @Override
    public String getHref() {
        return href_;
    }

    @Override
    public String getTitle() {
        return title_;
    }

    @Override
    public MediaList getMedia() {
        return media_;
    }

    @Override
    public CSSRule getOwnerRule() {
        return ownerRule_;
    }

    @Override
    public CSSRuleList getCssRules() {
        if (cssRules_ == null) {
            cssRules_ = new CSSRuleListImpl();
        }
        return cssRules_;
    }

    @Override
    public int insertRule(final String rule, final int index) throws DOMException {
        if (readOnly_) {
            throw new DOMExceptionImpl(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
        }

        try {
            final InputSource is = new InputSource(new StringReader(rule));
            final CSSOMParser parser = new CSSOMParser();
            parser.setParentStyleSheet(this);
            parser.setErrorHandler(ThrowCssExceptionErrorHandler.INSTANCE);
            final CSSRule r = parser.parseRule(is);

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
                if (r.getType() == CSSRule.CHARSET_RULE) {

                    // Index must be 0, and there can be only one charset rule
                    if (index != 0) {
                        msg = DOMExceptionImpl.CHARSET_NOT_FIRST;
                    }
                    else if (getCssRules().item(0).getType() == CSSRule.CHARSET_RULE) {
                        msg = DOMExceptionImpl.CHARSET_NOT_UNIQUE;
                    }
                }
                else if (r.getType() == CSSRule.IMPORT_RULE) {
                    // Import rules must preceed all other rules (except
                    // charset rules)
                    if (index <= getCssRules().getLength()) {
                        for (int i = 0; i < index; i++) {
                            final int rt = getCssRules().item(i).getType();
                            if ((rt != CSSRule.CHARSET_RULE) && (rt != CSSRule.IMPORT_RULE)) {
                                msg = DOMExceptionImpl.IMPORT_NOT_FIRST;
                                break;
                            }
                        }
                    }
                }
                else {
                    if (index <= getCssRules().getLength()) {
                        for (int i = index; i < getCssRules().getLength(); i++) {
                            final int rt = getCssRules().item(i).getType();
                            if ((rt == CSSRule.CHARSET_RULE) || (rt == CSSRule.IMPORT_RULE)) {
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
            ((CSSRuleListImpl) getCssRules()).insert(r, index);

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
        return index;
    }

    @Override
    public void deleteRule(final int index) throws DOMException {
        if (readOnly_) {
            throw new DOMExceptionImpl(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
        }

        try {
            ((CSSRuleListImpl) getCssRules()).delete(index);
        }
        catch (final IndexOutOfBoundsException e) {
            throw new DOMExceptionImpl(
                DOMException.INDEX_SIZE_ERR,
                DOMExceptionImpl.INDEX_OUT_OF_BOUNDS,
                e.getMessage());
        }
    }

    public boolean isReadOnly() {
        return readOnly_;
    }

    public void setReadOnly(final boolean b) {
        readOnly_ = b;
    }

    public void setOwnerNode(final Node ownerNode) {
        ownerNode_ = ownerNode;
    }

    public void setParentStyleSheet(final StyleSheet parentStyleSheet) {
        throw new RuntimeException("Method setParentStyleSheet not supported");
    }

    public void setHref(final String href) {
        href_ = href;
    }

    public void setTitle(final String title) {
        title_ = title;
    }

    public void setMediaText(final String mediaText) {
        final InputSource source = new InputSource(new StringReader(mediaText));
        try {
            final CSSOMParser parser = new CSSOMParser();
            final MediaQueryList sml = parser.parseMedia(source);
            media_ = new MediaListImpl(sml);
        }
        catch (final IOException e) {
            // TODO handle exception
        }
    }

    public void setOwnerRule(final CSSRule ownerRule) {
        ownerRule_ = ownerRule;
    }

    public void setCssRules(final CSSRuleList rules) {
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
        if (!(obj instanceof CSSStyleSheet)) {
            return false;
        }
        final CSSStyleSheet css = (CSSStyleSheet) obj;
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
        hash = LangUtils.hashCode(hash, readOnly_);
        hash = LangUtils.hashCode(hash, title_);
        return hash;
    }

    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.writeObject(cssRules_);
        out.writeBoolean(disabled_);
        out.writeObject(href_);
        out.writeObject(media_);
        out.writeBoolean(readOnly_);
        out.writeObject(title_);
    }

    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        cssRules_ = (CSSRuleList) in.readObject();
        if (cssRules_ != null) {
            for (int i = 0; i < cssRules_.getLength(); i++) {
                final CSSRule cssRule = cssRules_.item(i);
                if (cssRule instanceof AbstractCSSRuleImpl) {
                    ((AbstractCSSRuleImpl) cssRule).setParentStyleSheet(this);
                }
            }
        }
        disabled_ = in.readBoolean();
        href_ = (String) in.readObject();
        media_ = (MediaList) in.readObject();
        readOnly_ = in.readBoolean();
        title_ = (String) in.readObject();
    }

    public CSSStyleSheetRuleIndex getRuleIndex() {
        return index_;
    }

    public void setRuleIndex(final CSSStyleSheetRuleIndex index) {
        index_ = index;
    }

    public void resetRuleIndex() {
        index_ = null;
    }

    public static final class SelectorEntry {
        private Selector selector_;
        private CSSStyleRuleImpl rule_;

        SelectorEntry(final Selector selector, final CSSStyleRuleImpl rule) {
            selector_ = selector;
            rule_ = rule;
        }

        public Selector getSelector() {
            return selector_;
        }

        public CSSStyleRuleImpl getRule() {
            return rule_;
        }
    }

    public static class CSSStyleSheetRuleIndex {

        private static final MediaList DEFAULT_MEDIA_LIST = new MediaListImpl(null);

        private final List<CSSStyleSheetRuleIndex> children_ = new ArrayList<>();

        private MediaList mediaList_ = DEFAULT_MEDIA_LIST;
        private final Map<String, List<SelectorEntry>> elementSelectors_ = new HashMap<>();
        private final Map<String, List<SelectorEntry>> classSelectors_ = new HashMap<>();
        private final List<SelectorEntry> otherSelectors_ = new ArrayList<>();

        public void addElementSelector(final ElementSelector elementSelector,
                                        final Selector s, final CSSStyleRuleImpl styleRule) {
            final String elementName = elementSelector.getLocalNameLowerCase();
            List<SelectorEntry> entries = elementSelectors_.get(elementName);
            if (entries == null) {
                entries = new ArrayList<SelectorEntry>();
                elementSelectors_.put(elementName, entries);
            }
            final SelectorEntry selectorEntry = new SelectorEntry(s, styleRule);
            entries.add(selectorEntry);
        }

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
            List<SelectorEntry> entries = classSelectors_.get(key);
            if (entries == null) {
                entries = new ArrayList<SelectorEntry>();
                classSelectors_.put(key, entries);
            }
            final SelectorEntry selectorEntry = new SelectorEntry(s, styleRule);
            entries.add(selectorEntry);
        }

        public void addOtherSelector(final Selector s, final CSSStyleRuleImpl styleRule) {
            final SelectorEntry selectorEntry = new SelectorEntry(s, styleRule);
            otherSelectors_.add(selectorEntry);
        }

        public CSSStyleSheetRuleIndex addMedia(final MediaList mediaList) {
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

        public MediaList getMediaList() {
            return mediaList_;
        }

        public List<CSSStyleSheetRuleIndex> getChildren() {
            return children_;
        }

        public Iterator<SelectorEntry> getSelectorEntriesIteratorFor(final String elementName, final String[] classes) {
            return new SelectorEntriesIterator(this, elementName, classes);
        }
    }

    static final class SelectorEntriesIterator implements Iterator<SelectorEntry> {
        private LinkedList<Iterator<SelectorEntry>> iterators_;

        SelectorEntriesIterator(final CSSStyleSheetRuleIndex index,
                final String elementName, final String[] classes) {
            iterators_ = new LinkedList<Iterator<SelectorEntry>>();

            List<SelectorEntry> sel = index.elementSelectors_.get(null);
            if (sel != null && !sel.isEmpty()) {
                iterators_.add(sel.iterator());
            }
            sel = index.elementSelectors_.get(elementName);
            if (sel != null && !sel.isEmpty()) {
                iterators_.add(sel.iterator());
            }

            if (classes != null) {
                for (String clazz : classes) {
                    sel = index.classSelectors_.get("." + clazz);
                    if (sel != null && !sel.isEmpty()) {
                        iterators_.add(sel.iterator());
                    }

                    if (elementName != null) {
                        sel = index.classSelectors_.get(elementName + "." + clazz);
                        if (sel != null && !sel.isEmpty()) {
                            iterators_.add(sel.iterator());
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
