/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.w3c.html;

import org.mozilla.javascript.Function;

/**
 * The Interface TextTrack.
 */
public interface TextTrack {
	
	/** The Constant NONE. */
	public static final short NONE = 0;

	/** The Constant LOADING. */
	public static final short LOADING = 1;

	/** The Constant LOADED. */
	public static final short LOADED = 2;

	/** The Constant ERROR. */
	public static final short ERROR = 3;
	
	/** The Constant OFF. */
	public static final short OFF = 0;

	/** The Constant HIDDEN. */
	public static final short HIDDEN = 1;

	/** The Constant SHOWING. */
	public static final short SHOWING = 2;

	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public String getKind();

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel();

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage();

	/**
	 * Gets the ready state.
	 *
	 * @return the ready state
	 */
	public short getReadyState();

	/**
	 * Gets the onload.
	 *
	 * @return the onload
	 */
	public Function getOnload();

	/**
	 * Sets the onload.
	 *
	 * @param onload
	 *            the new onload
	 */
	public void setOnload(Function onload);

	/**
	 * Gets the onerror.
	 *
	 * @return the onerror
	 */
	public Function getOnerror();

	/**
	 * Sets the onerror.
	 *
	 * @param onerror
	 *            the new onerror
	 */
	public void setOnerror(Function onerror);


	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public short getMode();

	/**
	 * Sets the mode.
	 *
	 * @param mode
	 *            the new mode
	 */
	public void setMode(short mode);

	/**
	 * Gets the cues.
	 *
	 * @return the cues
	 */
	public TextTrackCueList getCues();

	/**
	 * Gets the active cues.
	 *
	 * @return the active cues
	 */
	public TextTrackCueList getActiveCues();

	/**
	 * Gets the oncuechange.
	 *
	 * @return the oncuechange
	 */
	public Function getOncuechange();

	/**
	 * Sets the oncuechange.
	 *
	 * @param oncuechange
	 *            the new oncuechange
	 */
	public void setOncuechange(Function oncuechange);
}
