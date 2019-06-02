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
package com.gargoylesoftware.css.parser;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

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
     * Create a new input source backed by byteStream.
     * @param byteStream the byte stream
     * @param encoding the encoding
     * @throws UnsupportedEncodingException if the encoding is not supported
     */
    public InputSource(final InputStream byteStream, final String encoding) throws UnsupportedEncodingException {
        if (encoding == null || encoding.length() < 1) {
            reader_ = new InputStreamReader(byteStream, Charset.defaultCharset());
        }
        else {
            reader_ = new InputStreamReader(byteStream, encoding);
        }
    }

    /**
     * Create a new input source backed by a reader.
     * @param reader the reader
     */
    public InputSource(final Reader reader) {
        reader_ = reader;
    }

    /**
     * Create a new input source backed by a reader.
     * @param uri the uri to read from
     * @throws IOException in case of error
     * @throws MalformedURLException in case of error
     */
    public InputSource(final String uri) throws MalformedURLException, IOException {
        setURI(uri);
        reader_ = new InputStreamReader(new URL(uri).openStream());
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
