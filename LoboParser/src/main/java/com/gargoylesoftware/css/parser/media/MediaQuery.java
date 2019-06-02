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
package com.gargoylesoftware.css.parser.media;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.css.dom.Property;
import com.gargoylesoftware.css.parser.AbstractLocatable;

/**
 * @author Ronald Brill
 */
public class MediaQuery extends AbstractLocatable implements Serializable {

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

    @Override
    public String toString() {
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
                .append(prop.toString())
                .append(')');
        }
        return sb.toString();
    }
}
