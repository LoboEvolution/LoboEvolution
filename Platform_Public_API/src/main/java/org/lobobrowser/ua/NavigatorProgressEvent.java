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

/**
 * An event containing response progress information.
 * 
 * @see NavigatorWindowListener#progressUpdated(NavigatorProgressEvent)
 */
public class NavigatorProgressEvent extends NavigatorEvent {
	private final ProgressType progressType;
	private final java.net.URL url;
	private final String method;
	private final int currentValue;
	private final int maxValue;

	public NavigatorProgressEvent(Object source, NavigatorFrame clientletFrame,
			ProgressType progressType, java.net.URL url, String method,
			int value, int max) {
		super(source, NavigatorEventType.PROGRESS_UPDATED, clientletFrame);
		this.progressType = progressType;
		this.url = url;
		this.method = method;
		this.currentValue = value;
		this.maxValue = max;
	}

	public String getMethod() {
		return method;
	}

	public ProgressType getProgressType() {
		return progressType;
	}

	public java.net.URL getUrl() {
		return url;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public int getMaxValue() {
		return maxValue;
	}
}
