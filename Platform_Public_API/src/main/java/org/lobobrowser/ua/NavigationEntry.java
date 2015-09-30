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

import org.lobobrowser.clientlet.ClientletResponse;

/**
 * Represents one item in the navigation history.
 */
public class NavigationEntry {
    // Note: Do not retain request context here.

    /** The url. */
    private final URL url;

    /** The method. */
    private final String method;

    /** The title. */
    private final String title;

    /** The description. */
    private final String description;

    /** The frame. */
    private final NavigatorFrame frame;

    /**
     * Instantiates a new navigation entry.
     *
     * @param frame
     *            the frame
     * @param url
     *            the url
     * @param method
     *            the method
     * @param title
     *            the title
     * @param description
     *            the description
     */
    public NavigationEntry(NavigatorFrame frame, final URL url,
            final String method, String title, String description) {
        super();
        this.frame = frame;
        this.url = url;
        this.method = method;
        this.title = title;
        this.description = description;
    }

    /** Gets the method.
	 *
	 * @return the method
	 */
    public String getMethod() {
        return method;
    }

    /** Gets the title.
	 *
	 * @return the title
	 */
    public String getTitle() {
        return title;
    }

    /** Gets the url.
	 *
	 * @return the url
	 */
    public URL getUrl() {
        return url;
    }

    /** Gets the navigator frame.
	 *
	 * @return the navigator frame
	 */
    public NavigatorFrame getNavigatorFrame() {
        return frame;
    }

    /**
     * From response.
     *
     * @param frame
     *            the frame
     * @param response
     *            the response
     * @param title
     *            the title
     * @param description
     *            the description
     * @return the navigation entry
     */
    public static NavigationEntry fromResponse(NavigatorFrame frame,
            ClientletResponse response, String title, String description) {
        return new NavigationEntry(frame, response.getResponseURL(),
                response.getLastRequestMethod(), title, description);
    }

    /** Gets the description.
	 *
	 * @return the description
	 */
    public String getDescription() {
        return description;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "NavigationEntry[url=" + this.url + ",method=" + this.method
                + ",title=" + title + "]";
    }
}
