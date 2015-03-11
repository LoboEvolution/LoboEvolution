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
package org.lobobrowser.clientlet;


/**
 * Thrown when a clientlet requires a newer navigator version than the one
 * running.
 */
public class NavigatorVersionException extends ClientletException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The expecting version. */
	private final String expectingVersion;

	/**
	 * Instantiates a new navigator version exception.
	 *
	 * @param message the message
	 * @param expectingVersion the expecting version
	 */
	public NavigatorVersionException(String message, String expectingVersion) {
		super(message);
		this.expectingVersion = expectingVersion;
	}

	/**
	 * Gets the expecting version.
	 *
	 * @return the expecting version
	 */
	public String getExpectingVersion() {
		return expectingVersion;
	}

	/**
	 * Instantiates a new navigator version exception.
	 *
	 * @param message the message
	 * @param expectingVersion the expecting version
	 * @param rootCause the root cause
	 */
	public NavigatorVersionException(String message, String expectingVersion,
			Throwable rootCause) {
		super(message, rootCause);
		this.expectingVersion = expectingVersion;
	}

	/**
	 * Instantiates a new navigator version exception.
	 *
	 * @param expectingVersion the expecting version
	 * @param rootCause the root cause
	 */
	public NavigatorVersionException(String expectingVersion,
			Throwable rootCause) {
		super(rootCause);
		this.expectingVersion = expectingVersion;
	}
}
