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
package com.gargoylesoftware.css.parser.media;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.css.parser.AbstractLocatable;

/**
 * Implementation of MediaQueryList.
 *
 * @author Ronald Brill
 */
public class MediaQueryList extends AbstractLocatable {

    private final List<MediaQuery> mediaQueries_;

    /**
     * Ctor.
     */
    public MediaQueryList() {
        mediaQueries_ = new ArrayList<>();
    }

    /**
     * @return the number of mediaQueries
     */
    public int getLength() {
        return mediaQueries_.size();
    }

    /**
     * @return the list of media queries
     */
    public List<MediaQuery> getMediaQueries() {
        return mediaQueries_;
    }

    /**
     * Adds a new media query to the list.
     * @param mediaQuery the media query to add
     */
    public void add(final MediaQuery mediaQuery) {
        mediaQueries_.add(mediaQuery);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (MediaQuery mediaQuery : mediaQueries_) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(mediaQuery.getMedia());
        }
        return sb.toString();
    }
}
