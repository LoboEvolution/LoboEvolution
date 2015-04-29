/*
 * Copyright 1994-2006 The Lobo Project. Copyright 2014 Lobo Evolution. All
 * rights reserved. Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met: Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer. Redistributions
 * in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE
 * LOBO PROJECT ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE FREEBSD PROJECT OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.lobobrowser.clientlet;

import java.awt.Component;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.lobobrowser.io.ManagedStore;
import org.lobobrowser.ua.NavigatorFrame;
import org.lobobrowser.ua.NavigatorProgressEvent;
import org.lobobrowser.ua.NetworkRequest;
import org.lobobrowser.ua.ProgressType;
import org.lobobrowser.ua.UserAgent;

/**
 * The context in which a clientlet processes a web or file response.
 *
 * @see ClientletAccess#getCurrentClientletContext()
 */
public interface ClientletContext {
    /**
     * Sets a data item for later retrieval.
     *
     * @param name
     *            The item name.
     * @param value
     *            The item value.
     */
    void setItem(String name, Object value);

    /**
     * Gets a data item.
     *
     * @param name
     *            The item name.
     * @return The item value.
     */
    Object getItem(String name);

    /**
     * Gets the clientlet request.
     *
     * @return the request
     */
    ClientletRequest getRequest();

    /**
     * Gets the clientlet response.
     *
     * @return the response
     */
    ClientletResponse getResponse();

    /**
     * Gets a UserAgent instance with information about the current user agent.
     *
     * @return the user agent
     */
    UserAgent getUserAgent();

    /**
     * Undocumented.
     *
     * @param contentType
     *            the content type
     * @param content
     *            the content
     * @return the org.lobobrowser.clientlet. content buffer
     */
    org.lobobrowser.clientlet.ContentBuffer createContentBuffer(
            String contentType, byte[] content);

    /**
     * Undocumented.
     *
     * @param contentType
     *            the content type
     * @param content
     *            the content
     * @param encoding
     *            the encoding
     * @return the org.lobobrowser.clientlet. content buffer
     * @throws UnsupportedEncodingException
     *             the unsupported encoding exception
     */
    org.lobobrowser.clientlet.ContentBuffer createContentBuffer(
            String contentType, String content, String encoding)
                    throws UnsupportedEncodingException;

    /**
     * Gets a managed store instance (a small file system restricted by a quota)
     * corresponding to the host of the clientlet response of this context.
     *
     * @return the managed store
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @see #getManagedStore(String)
     */
    ManagedStore getManagedStore() throws IOException;

    /**
     * Gets a managed store instance (a small file system restricted by a quota)
     * for the host name provided.
     *
     * @param hostName
     *            A host whose cookies the caller is allowed to access. For
     *            example, if the response host name is
     *            <code>test.acme.com</code>, then the <code>hostName</code>
     *            parameter can be <code>acme.com</code> but not
     *            <code>com</code>.
     * @return the managed store
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    ManagedStore getManagedStore(String hostName) throws IOException;

    /**
     * Gets the frame interface associated with this context.
     *
     * @return the navigator frame
     */
    NavigatorFrame getNavigatorFrame();

    /**
     * After processing a response a clientlet should invoke this method to set
     * displayable frame content.
     *
     * @param content
     *            the new resulting content
     */
    void setResultingContent(ComponentContent content);

    /**
     * A simple alternative to {@link #setResultingContent(ComponentContent)}
     * provided for convenience. It does not set any properties such as title or
     * source code.
     *
     * @param content
     *            the new resulting content
     */
    void setResultingContent(Component content);

    /**
     * Gets content previously set with {@link #setResultingContent(Component)}.
     * The return value may be <code>null</code>.
     *
     * @return the resulting content
     */
    ComponentContent getResultingContent();

    /**
     * Navigates to the URI provided, which may be absolute or relative to the
     * response URL.
     *
     * @param uri
     *            The target URI.
     * @throws MalformedURLException
     *             the malformed url exception
     * @throws IOException 
     * @see NavigatorFrame#navigate(String)
     */
    void navigate(String uri) throws MalformedURLException, IOException;

    /**
     * For documents requested in order to open a new window, this method may be
     * invoked to override window properties. To take effect, this method should
     * be invoked before content is set.
     *
     * @param properties
     *            A properties object following JavaScript Window.open()
     *            conventions.
     */
    void overrideWindowProperties(java.util.Properties properties);

    /**
     * Gets window properties previously set with
     * {@link #overrideWindowProperties(java.util.Properties)}.
     *
     * @return the overridding window properties
     */
    java.util.Properties getOverriddingWindowProperties();

    /**
     * Returns <code>true</code> if resulting content has already been set with
     * {@link #setResultingContent(ComponentContent)}.
     *
     * @return true, if is resulting content set
     */
    boolean isResultingContentSet();

    /**
     * Requests the frame to update its progress bar if any.
     *
     * @param progressType
     *            The type of progress action.
     * @param value
     *            The current progress value.
     * @param max
     *            The maximum progress value, which may be <code>-1</code> to
     *            indicate it is unknown.
     * @see NavigatorFrame#setProgressEvent(NavigatorProgressEvent)
     */
    void setProgressEvent(org.lobobrowser.ua.ProgressType progressType,
            int value, int max);

    /**
     * Requests the frame to update its progress bar if any.
     *
     * @param progressType
     *            The type of progress action.
     * @param value
     *            The current progress value.
     * @param max
     *            The maximum progress value, which may be <code>-1</code> to
     *            indicate it is unknown.
     * @param url
     *            The URL to be shown in progress messages.
     * @see NavigatorFrame#setProgressEvent(NavigatorProgressEvent)
     */
    void setProgressEvent(org.lobobrowser.ua.ProgressType progressType,
            int value, int max, URL url);

    /**
     * Sets the current progress state.
     *
     * @param event
     *            The progress event.
     * @see NavigatorFrame#setProgressEvent(NavigatorProgressEvent)
     * @see #getProgressEvent()
     */
    void setProgressEvent(NavigatorProgressEvent event);

    /**
     * Gets the progress event most recently set.
     *
     * @return the progress event
     * @see #setProgressEvent(ProgressType, int, int)
     * @see NavigatorFrame#setProgressEvent(NavigatorProgressEvent)
     */
    NavigatorProgressEvent getProgressEvent();

    /**
     * Creates a {@link NetworkRequest} object that can be used to load data
     * over HTTP and other network protocols.
     *
     * @return the network request
     */
    NetworkRequest createNetworkRequest();

    /**
     * Opens an alert message dialog.
     *
     * @param message
     *            An alert message.
     */
    void alert(String message);

    /**
     * Creates a lose navigator frame that may be added to GUI components.
     *
     * @return the navigator frame
     * @see NavigatorFrame#getComponent()
     * @see NavigatorFrame#navigate(String)
     */
    NavigatorFrame createNavigatorFrame();
}
