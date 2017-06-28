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

/**
 * Enumeration with different the different types of request/response progress
 * qualifiers.
 *
 * @see NavigatorProgressEvent#getProgressType()
 */
public enum ProgressType {
	/** This is the initial state. */
	NONE,

	/** The client is attempting to connect to the server. */
	CONNECTING,

	/** The client is sending POST data. */
	SENDING,
	/**
	 * The client is waiting for a response from the server. POST data, if any,
	 * has been sent.
	 */
	WAITING_FOR_RESPONSE,
	/**
	 * Content is starting to load.
	 */
	CONTENT_LOADING,
	/**
	 * Additional processing of the document is being performed.
	 */
	BUILDING,
	/**
	 * All operations done.
	 */
	DONE
}
