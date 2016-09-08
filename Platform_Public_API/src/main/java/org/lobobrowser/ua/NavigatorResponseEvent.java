/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.ua;

import java.net.URL;

import org.lobobrowser.clientlet.ClientletResponse;

/**
 * An event containing response information.
 */
public class NavigatorResponseEvent extends NavigatorEvent {

    /** The Constant serialVersionUID. */
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

    /** Gets the response.
	 *
	 * @return the response
	 */
    public ClientletResponse getResponse() {
        return response;
    }

    /** Gets the url.
	 *
	 * @return the url
	 */
    public URL getUrl() {
        return this.response == null ? null : this.response.getResponseURL();
    }

    /** Gets the method.
	 *
	 * @return the method
	 */
    public String getMethod() {
        return this.response == null ? null : this.response
                .getLastRequestMethod();
    }

    /** Gets the request type.
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
