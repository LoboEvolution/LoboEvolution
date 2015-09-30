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

package org.lobobrowser.w3c.html;


/**
 * The Interface TextTrackCue_Constructor.
 */
public interface TextTrackCue_Constructor {
	
	/**
	 * Creates the instance.
	 *
	 * @param id the id
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param text the text
	 * @return the text track cue
	 */
	// Constructor
	public TextTrackCue createInstance(String id, double startTime, double endTime, String text);

	/**
	 * Creates the instance.
	 *
	 * @param id the id
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param text the text
	 * @param settings the settings
	 * @return the text track cue
	 */
	public TextTrackCue createInstance(String id, double startTime, double endTime, String text, String settings);

	/**
	 * Creates the instance.
	 *
	 * @param id the id
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param text the text
	 * @param settings the settings
	 * @param pauseOnExit the pause on exit
	 * @return the text track cue
	 */
	public TextTrackCue createInstance(String id, double startTime, double endTime, String text, String settings,
			boolean pauseOnExit);
}
