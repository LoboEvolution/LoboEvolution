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
package com.gargoylesoftware.css.parser;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

/**
 * The input supported by the parser.
 *
 * @author Ronald Brill
 */
public class InputSource implements Closeable {
    private String uri_;
    private Reader reader_;
    private String media_;
    private String title_;

    /**
     * Create a new input source backed by a reader.
     * @param reader the reader
     */
    public InputSource(final Reader reader) {
        reader_ = reader;
    }

    /**
     * @return the reader if defined
     */
    public Reader getReader() {
        return reader_;
    }

    /**
     * @return the uri if set
     */
    public String getURI() {
        return uri_;
    }

    /**
     * @param uri the uri
     */
    public void setURI(final String uri) {
        uri_ = uri;
    }

    /**
     * @return the media if set
     */
    public String getMedia() {
        if (media_ == null) {
            return "all";
        }
        return media_;
    }

    /**
     * @param media the media
     */
    public void setMedia(final String media) {
        media_ = media;
    }

    /**
     * @return the title if set
     */
    public String getTitle() {
        return title_;
    }

    /**
     * @param title the title
     */
    public void setTitle(final String title) {
        title_ = title;
    }

    @Override
    public void close() throws IOException {
        reader_.close();
    }
}
