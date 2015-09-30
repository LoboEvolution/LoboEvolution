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

import org.mozilla.javascript.Function;


/**
 * The Interface MediaController.
 */
public interface MediaController {
	
	/**
	 * Gets the buffered.
	 *
	 * @return the buffered
	 */
	// MediaController
	public TimeRanges getBuffered();

	/**
	 * Gets the seekable.
	 *
	 * @return the seekable
	 */
	public TimeRanges getSeekable();

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public double getDuration();

	/**
	 * Gets the current time.
	 *
	 * @return the current time
	 */
	public double getCurrentTime();

	/**
	 * Sets the current time.
	 *
	 * @param currentTime
	 *            the new current time
	 */
	public void setCurrentTime(double currentTime);

	/**
	 * Gets the paused.
	 *
	 * @return the paused
	 */
	public boolean getPaused();

	/**
	 * Gets the played.
	 *
	 * @return the played
	 */
	public TimeRanges getPlayed();

	/**
	 * Play.
	 */
	public void play();

	/**
	 * Pause.
	 */
	public void pause();

	/**
	 * Gets the default playback rate.
	 *
	 * @return the default playback rate
	 */
	public double getDefaultPlaybackRate();

	/**
	 * Sets the default playback rate.
	 *
	 * @param defaultPlaybackRate
	 *            the new default playback rate
	 */
	public void setDefaultPlaybackRate(double defaultPlaybackRate);

	/**
	 * Gets the playback rate.
	 *
	 * @return the playback rate
	 */
	public double getPlaybackRate();

	/**
	 * Sets the playback rate.
	 *
	 * @param playbackRate
	 *            the new playback rate
	 */
	public void setPlaybackRate(double playbackRate);

	/**
	 * Gets the volume.
	 *
	 * @return the volume
	 */
	public double getVolume();

	/**
	 * Sets the volume.
	 *
	 * @param volume
	 *            the new volume
	 */
	public void setVolume(double volume);

	/**
	 * Gets the muted.
	 *
	 * @return the muted
	 */
	public boolean getMuted();

	/**
	 * Sets the muted.
	 *
	 * @param muted
	 *            the new muted
	 */
	public void setMuted(boolean muted);

	/**
	 * Gets the onemptied.
	 *
	 * @return the onemptied
	 */
	public Function getOnemptied();

	/**
	 * Sets the onemptied.
	 *
	 * @param onemptied
	 *            the new onemptied
	 */
	public void setOnemptied(Function onemptied);

	/**
	 * Gets the onloadedmetadata.
	 *
	 * @return the onloadedmetadata
	 */
	public Function getOnloadedmetadata();

	/**
	 * Sets the onloadedmetadata.
	 *
	 * @param onloadedmetadata
	 *            the new onloadedmetadata
	 */
	public void setOnloadedmetadata(Function onloadedmetadata);

	/**
	 * Gets the onloadeddata.
	 *
	 * @return the onloadeddata
	 */
	public Function getOnloadeddata();

	/**
	 * Sets the onloadeddata.
	 *
	 * @param onloadeddata
	 *            the new onloadeddata
	 */
	public void setOnloadeddata(Function onloadeddata);

	/**
	 * Gets the oncanplay.
	 *
	 * @return the oncanplay
	 */
	public Function getOncanplay();

	/**
	 * Sets the oncanplay.
	 *
	 * @param oncanplay
	 *            the new oncanplay
	 */
	public void setOncanplay(Function oncanplay);

	/**
	 * Gets the oncanplaythrough.
	 *
	 * @return the oncanplaythrough
	 */
	public Function getOncanplaythrough();

	/**
	 * Sets the oncanplaythrough.
	 *
	 * @param oncanplaythrough
	 *            the new oncanplaythrough
	 */
	public void setOncanplaythrough(Function oncanplaythrough);

	/**
	 * Gets the onplaying.
	 *
	 * @return the onplaying
	 */
	public Function getOnplaying();

	/**
	 * Sets the onplaying.
	 *
	 * @param onplaying
	 *            the new onplaying
	 */
	public void setOnplaying(Function onplaying);

	/**
	 * Gets the onended.
	 *
	 * @return the onended
	 */
	public Function getOnended();

	/**
	 * Sets the onended.
	 *
	 * @param onended
	 *            the new onended
	 */
	public void setOnended(Function onended);

	/**
	 * Gets the onwaiting.
	 *
	 * @return the onwaiting
	 */
	public Function getOnwaiting();

	/**
	 * Sets the onwaiting.
	 *
	 * @param onwaiting
	 *            the new onwaiting
	 */
	public void setOnwaiting(Function onwaiting);

	/**
	 * Gets the ondurationchange.
	 *
	 * @return the ondurationchange
	 */
	public Function getOndurationchange();

	/**
	 * Sets the ondurationchange.
	 *
	 * @param ondurationchange
	 *            the new ondurationchange
	 */
	public void setOndurationchange(Function ondurationchange);

	/**
	 * Gets the ontimeupdate.
	 *
	 * @return the ontimeupdate
	 */
	public Function getOntimeupdate();

	/**
	 * Sets the ontimeupdate.
	 *
	 * @param ontimeupdate
	 *            the new ontimeupdate
	 */
	public void setOntimeupdate(Function ontimeupdate);

	/**
	 * Gets the onplay.
	 *
	 * @return the onplay
	 */
	public Function getOnplay();

	/**
	 * Sets the onplay.
	 *
	 * @param onplay
	 *            the new onplay
	 */
	public void setOnplay(Function onplay);

	/**
	 * Gets the onpause.
	 *
	 * @return the onpause
	 */
	public Function getOnpause();

	/**
	 * Sets the onpause.
	 *
	 * @param onpause
	 *            the new onpause
	 */
	public void setOnpause(Function onpause);

	/**
	 * Gets the onratechange.
	 *
	 * @return the onratechange
	 */
	public Function getOnratechange();

	/**
	 * Sets the onratechange.
	 *
	 * @param onratechange
	 *            the new onratechange
	 */
	public void setOnratechange(Function onratechange);

	/**
	 * Gets the onvolumechange.
	 *
	 * @return the onvolumechange
	 */
	public Function getOnvolumechange();

	/**
	 * Sets the onvolumechange.
	 *
	 * @param onvolumechange
	 *            the new onvolumechange
	 */
	public void setOnvolumechange(Function onvolumechange);
}
