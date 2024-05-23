/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.js.xml;

import org.loboevolution.html.node.Document;
import org.mozilla.javascript.Function;

import java.util.List;

public interface XMLHttpRequest extends XMLHttpRequestEventTarget {

    Function getOnreadystatechange();

    void setOnreadystatechange(Function onreadystatechange);

    int getReadyState();

    /**
     * <p>open.</p>
     *
     * @param method a {@link java.lang.String} object.
     * @param url a {@link java.lang.String} object.
     * @throws java.lang.Exception if any.
     */
    void open(String method, String url) throws Exception;

    /**
     * <p>open.</p>
     *
     * @param method a {@link java.lang.String} object.
     * @param url a {@link java.lang.String} object.
     * @param async a boolean.
     * @throws java.lang.Exception if any.
     */
    void open(String method, String url, boolean async) throws Exception;

    /**
     * <p>open.</p>
     *
     * @param method a {@link java.lang.String} object.
     * @param url a {@link java.lang.String} object.
     * @param async a boolean.
     * @param username a {@link java.lang.String} object.
     * @throws java.lang.Exception if any.
     */
    void open(String method, String url, boolean async, String username) throws Exception;

    /**
     * <p>open.</p>
     *
     * @param method a {@link java.lang.String} object.
     * @param url a {@link java.lang.String} object.
     * @param async a boolean.
     * @param username a {@link java.lang.String} object.
     * @param password a {@link java.lang.String} object.
     * @throws java.lang.Exception if any.
     */
    void open(String method, String url, boolean async, String username, String password) throws Exception;

    /**
     * <p>send.</p>
     *
     * @throws java.lang.Exception if any.
     */
    void send() throws Exception;

    /**
     * <p>abort.</p>
     */
    void abort();

    /**
     * <p>send.</p>
     *
     * @param data a {@link java.lang.Object} object.
     * @throws java.lang.Exception if any.
     */
    void send(Object data) throws Exception;

    void setRequestHeader(String header, String value);

    int getTimeout();

    void setTimeout(int timeout);

    boolean getWithCredentials();

    void setWithCredentials(boolean withCredentials);

    XMLHttpRequestUpload getUpload();

    int getStatus();

    String getStatusText();

    List<String> getResponseHeader(String header);

    String getAllResponseHeaders();

    void overrideMimeType(String mime);

    String getResponseType();

    void setResponseType(String responseType);

    Object getResponse();

    String getResponseText();

    Document getResponseXML();
}
