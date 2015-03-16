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
 * A navigator event object.
 */
public class NavigatorEvent extends java.util.EventObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The clientlet frame. */
	private final NavigatorFrame clientletFrame;
	
	/** The event type. */
	private final NavigatorEventType eventType;

	/**
	 * Instantiates a new navigator event.
	 *
	 * @param source the source
	 * @param eventType the event type
	 * @param clientletFrame the clientlet frame
	 */
	public NavigatorEvent(Object source, final NavigatorEventType eventType,
			final NavigatorFrame clientletFrame) {
		super(source);
		this.clientletFrame = clientletFrame;
		this.eventType = eventType;
	}

	/**
	 * Gets the navigator frame.
	 *
	 * @return the navigator frame
	 */
	public NavigatorFrame getNavigatorFrame() {
		return clientletFrame;
	}

	/**
	 * Gets the event type.
	 *
	 * @return the event type
	 */
	public NavigatorEventType getEventType() {
		return eventType;
	}

	/* (non-Javadoc)
	 * @see java.util.EventObject#toString()
	 */
	public String toString() {
		return "NavigatorWindowEvent[type=" + this.getEventType() + "]";
	}
}
