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

package com.steadystate.css.parser.media;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.steadystate.css.dom.Property;
import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.LocatableImpl;

/**
 * @author rbri
 */
public class MediaQuery extends LocatableImpl implements CSSFormatable, Serializable {

    private static final long serialVersionUID = 456776383828897471L;

    private String media_;
    private List<Property> properties_;
    private boolean isOnly_;
    private boolean isNot_;

    public MediaQuery(final String media) {
        this(media, false, false);
    }

    public MediaQuery(final String media, final boolean isOnly, final boolean isNot) {
        setMedia(media);
        properties_ = new ArrayList<Property>(10);
        isOnly_ = isOnly;
        isNot_ = isNot;
    }

    public String getMedia() {
        return media_;
    }

    public void setMedia(final String media) {
        media_ = media;
    }

    public List<Property> getProperties() {
        return properties_;
    }

    public void addMediaProperty(final Property mp) {
        properties_.add(mp);
    }

    public boolean isOnly() {
        return isOnly_;
    }

    public boolean isNot() {
        return isNot_;
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final StringBuilder sb = new StringBuilder();

        if (isOnly_) {
            sb.append("only ");
        }
        if (isNot_) {
            sb.append("not ");
        }

        sb.append(getMedia());

        for (Property prop : properties_) {
            sb.append(" and (")
                .append(prop.getCssText(format))
                .append(')');
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getCssText(null);
    }
}
