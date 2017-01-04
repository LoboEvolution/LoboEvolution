/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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

import org.lobobrowser.clientlet.ClientletResponse;

/**
 * A navigator event containing an exception.
 *
 * @see NavigatorErrorListener#errorOcurred(NavigatorExceptionEvent)
 */
public class NavigatorExceptionEvent extends NavigatorResponseEvent {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** The exception. */
    private final Throwable exception;

    /**
     * Instantiates a new navigator exception event.
     *
     * @param source
     *            the source
     * @param eventType
     *            the event type
     * @param clientletFrame
     *            the clientlet frame
     * @param response
     *            the response
     * @param exception
     *            the exception
     */
    public NavigatorExceptionEvent(Object source, NavigatorEventType eventType,
            NavigatorFrame clientletFrame, ClientletResponse response,
            final Throwable exception) {
        super(source, eventType, clientletFrame, response, response
                .getRequestType());
        this.exception = exception;
    }

    /** Gets the exception.
	 *
	 * @return the exception
	 */
    public Throwable getException() {
        return exception;
    }
}
