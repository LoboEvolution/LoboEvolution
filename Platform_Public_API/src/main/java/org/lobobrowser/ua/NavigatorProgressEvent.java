/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.ua;

import java.net.URL;

/**
 * An event containing response progress information.
 *
 * @see NavigatorWindowListener#progressUpdated(NavigatorProgressEvent)
 */
public class NavigatorProgressEvent extends NavigatorEvent {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The progress type. */
    private final ProgressType progressType;

    /** The url. */
    private final URL url;

    /** The method. */
    private final String method;

    /** The current value. */
    private final int currentValue;

    /** The max value. */
    private final int maxValue;

    /**
     * Instantiates a new navigator progress event.
     *
     * @param source
     *            the source
     * @param clientletFrame
     *            the clientlet frame
     * @param progressType
     *            the progress type
     * @param url
     *            the url
     * @param method
     *            the method
     * @param value
     *            the value
     * @param max
     *            the max
     */
    public NavigatorProgressEvent(Object source, NavigatorFrame clientletFrame,
            ProgressType progressType, URL url, String method, int value,
            int max) {
        super(source, NavigatorEventType.PROGRESS_UPDATED, clientletFrame);
        this.progressType = progressType;
        this.url = url;
        this.method = method;
        this.currentValue = value;
        this.maxValue = max;
    }

    /** Gets the method.
	 *
	 * @return the method
	 */
    public String getMethod() {
        return method;
    }

    /** Gets the progress type.
	 *
	 * @return the progress type
	 */
    public ProgressType getProgressType() {
        return progressType;
    }

    /** Gets the url.
	 *
	 * @return the url
	 */
    public URL getUrl() {
        return url;
    }

    /** Gets the current value.
	 *
	 * @return the current value
	 */
    public int getCurrentValue() {
        return currentValue;
    }

    /** Gets the max value.
	 *
	 * @return the max value
	 */
    public int getMaxValue() {
        return maxValue;
    }
}
