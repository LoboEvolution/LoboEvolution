/*
Copyright 1994-2006 The Lobo Project. Copyright 2014 Lobo Evolution. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list 
of conditions and the following disclaimer. Redistributions in binary form must 
reproduce the above copyright notice, this list of conditions and the following 
disclaimer in the documentation and/or other materials provided with the distribution.
 
THIS SOFTWARE IS PROVIDED BY THE LOBO PROJECT ``AS IS'' AND ANY EXPRESS OR IMPLIED 
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO 
EVENT SHALL THE FREEBSD PROJECT OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
OF THE POSSIBILITY OF SUCH DAMAGE.
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
	 * @param frame the frame
	 * @param url the url
	 * @param method the method
	 * @param title the title
	 * @param description the description
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

	/**
	 * Gets the uppercase request method that resulted in this navigation entry.
	 *
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * Gets the navigator frame.
	 *
	 * @return the navigator frame
	 */
	public NavigatorFrame getNavigatorFrame() {
		return frame;
	}

	/**
	 * From response.
	 *
	 * @param frame the frame
	 * @param response the response
	 * @param title the title
	 * @param description the description
	 * @return the navigation entry
	 */
	public static NavigationEntry fromResponse(NavigatorFrame frame,
			ClientletResponse response, String title, String description) {
		return new NavigationEntry(frame, response.getResponseURL(),
				response.getLastRequestMethod(), title, description);
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "NavigationEntry[url=" + this.url + ",method=" + this.method
				+ ",title=" + title + "]";
	}
}
