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
package org.lobobrowser.ua;

import java.net.URL;

import org.lobobrowser.clientlet.ClientletResponse;

/**
 * An event containing response information.
 */
public class NavigatorResponseEvent extends NavigatorEvent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** The response. */
    private final ClientletResponse response;

    /** The request type. */
    private final RequestType requestType;

    /**
     * Instantiates a new navigator response event.
     *
     * @param source
     *            the source
     * @param eventType
     *            the event type
     * @param clientletFrame
     *            the clientlet frame
     * @param response
     *            the response
     * @param requestType
     *            the request type
     */
    public NavigatorResponseEvent(Object source, NavigatorEventType eventType,
            NavigatorFrame clientletFrame, ClientletResponse response,
            RequestType requestType) {
        super(source, eventType, clientletFrame);
        this.response = response;
        this.requestType = requestType;
    }

    /**
     * Gets the response.
     *
     * @return the response
     */
    public ClientletResponse getResponse() {
        return response;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public URL getUrl() {
        return this.response == null ? null : this.response.getResponseURL();
    }

    /**
     * Gets the method.
     *
     * @return the method
     */
    public String getMethod() {
        return this.response == null ? null : this.response
                .getLastRequestMethod();
    }

    /**
     * Gets the request type.
     *
     * @return the request type
     */
    public RequestType getRequestType() {
        return requestType;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.ua.NavigatorEvent#toString()
     */
    @Override
    public String toString() {
        return "NavigatorWindowEvent[type=" + this.getEventType() + ",url="
                + this.getUrl() + "]";
    }
}
