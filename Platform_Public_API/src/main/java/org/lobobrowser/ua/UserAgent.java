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

/**
 * Represents the user agent, browser or clientlet engine.
 *
 * @author J. H. S.
 */
public interface UserAgent {

    /** Gets the name.
	 *
	 * @return the name
	 */
    String getName();

    /** Gets the version.
	 *
	 * @return the version
	 */
    String getVersion();

    // /**
    // * Gets the earliest XAMJ version this user agent
    // * implements as a subset. (Supersets and other
    // * non-subsets of XAMJ versions are not allowed.)
    // */
    // String getXamjVersion();

    /** Gets the java version.
	 *
	 * @return the java version
	 */
    String getJavaVersion();

    /** Gets the name and version.
	 *
	 * @return the name and version
	 */
    String getNameAndVersion();

    /** Gets the info url.
	 *
	 * @return the info url
	 */
    String getInfoUrl();

    /** Gets the user agent string.
	 *
	 * @return the user agent string
	 */
    String getUserAgentString();

    // (unavalilable for now)
    // /**
    // * Gets an ID that is nearly guaranteed to be globally unique
    // * for a browser session and a protocol-host pair. The session ID
    // * should be practically impossible to guess, even if the algorithm
    // * to produce it is well known. The session ID does not persist
    // * if the browser exits.
    // */
    // String getSessionID(URL url);
}
