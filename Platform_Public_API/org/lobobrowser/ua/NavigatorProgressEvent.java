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


/**
 * An event containing response progress information.
 * 
 * @see NavigatorWindowListener#progressUpdated(NavigatorProgressEvent)
 */
public class NavigatorProgressEvent extends NavigatorEvent {
	
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
	 * @param source the source
	 * @param clientletFrame the clientlet frame
	 * @param progressType the progress type
	 * @param url the url
	 * @param method the method
	 * @param value the value
	 * @param max the max
	 */
	public NavigatorProgressEvent(Object source, NavigatorFrame clientletFrame,
			ProgressType progressType, URL url, String method,
			int value, int max) {
		super(source, NavigatorEventType.PROGRESS_UPDATED, clientletFrame);
		this.progressType = progressType;
		this.url = url;
		this.method = method;
		this.currentValue = value;
		this.maxValue = max;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Gets the progress type.
	 *
	 * @return the progress type
	 */
	public ProgressType getProgressType() {
		return progressType;
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
	 * Gets the current value.
	 *
	 * @return the current value
	 */
	public int getCurrentValue() {
		return currentValue;
	}

	/**
	 * Gets the max value.
	 *
	 * @return the max value
	 */
	public int getMaxValue() {
		return maxValue;
	}
}
