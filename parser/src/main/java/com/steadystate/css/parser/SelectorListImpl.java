/*
 * Copyright (C) 1999-2016 David Schweinsberg.  All rights reserved.
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

package com.steadystate.css.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;

/**
 * Implementation of {@link SelectorList}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class SelectorListImpl extends LocatableImpl implements SelectorList, CSSFormatable, Serializable {

    private static final long serialVersionUID = 7313376916207026333L;

    private List<Selector> selectors_ = new ArrayList<Selector>(10);

    public List<Selector> getSelectors() {
        return selectors_;
    }

    public void setSelectors(final List<Selector> selectors) {
        selectors_ = selectors;
    }

    public int getLength() {
        return selectors_.size();
    }

    public Selector item(final int index) {
        return selectors_.get(index);
    }

    public void add(final Selector sel) {
        selectors_.add(sel);
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final int len = getLength();

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            final CSSFormatable sel = (CSSFormatable) item(i);
            sb.append(sel.getCssText(format));
            if (i < len - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getCssText(null);
    }
}
