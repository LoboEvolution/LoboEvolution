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

package org.lobobrowser.w3c.html;

import org.lobobrowser.w3c.ObjectArray;

/**
 * The Interface HTMLMediaElement.
 */
public interface HTMLMediaElement extends HTMLElement {
	
	/** The Constant NETWORK_EMPTY. */
	public static final short NETWORK_EMPTY = 0;

	/** The Constant NETWORK_IDLE. */
	public static final short NETWORK_IDLE = 1;

	/** The Constant NETWORK_LOADING. */
	public static final short NETWORK_LOADING = 2;

	/** The Constant NETWORK_NO_SOURCE. */
	public static final short NETWORK_NO_SOURCE = 3;

	/** The Constant HAVE_NOTHING. */
	public static final short HAVE_NOTHING = 0;

	/** The Constant HAVE_METADATA. */
	public static final short HAVE_METADATA = 1;

	/** The Constant HAVE_CURRENT_DATA. */
	public static final short HAVE_CURRENT_DATA = 2;

	/** The Constant HAVE_FUTURE_DATA. */
	public static final short HAVE_FUTURE_DATA = 3;

	/** The Constant HAVE_ENOUGH_DATA. */
	public static final short HAVE_ENOUGH_DATA = 4;

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */

	public MediaError getError();

	/**
	 * Gets the src.
	 *
	 * @return the src
	 */
	public String getSrc();

	/**
	 * Sets the src.
	 *
	 * @param src
	 *            the new src
	 */
	public void setSrc(String src);

	/**
	 * Gets the current src.
	 *
	 * @return the current src
	 */
	public String getCurrentSrc();

	/**
	 * Gets the cross origin.
	 *
	 * @return the cross origin
	 */
	public String getCrossOrigin();

	/**
	 * Sets the cross origin.
	 *
	 * @param crossOrigin
	 *            the new cross origin
	 */
	public void setCrossOrigin(String crossOrigin);

	/**
	 * Gets the network state.
	 *
	 * @return the network state
	 */
	public short getNetworkState();

	/**
	 * Gets the preload.
	 *
	 * @return the preload
	 */
	public String getPreload();

	/**
	 * Sets the preload.
	 *
	 * @param preload
	 *            the new preload
	 */
	public void setPreload(String preload);

	/**
	 * Gets the buffered.
	 *
	 * @return the buffered
	 */
	public TimeRanges getBuffered();

	/**
	 * Load.
	 */
	public void load();

	/**
	 * Can play type.
	 *
	 * @param type
	 *            the type
	 * @return the string
	 */
	public String canPlayType(String type);

	/**
	 * Gets the ready state.
	 *
	 * @return the ready state
	 */
	public String getReadyState();

	/**
	 * Gets the seeking.
	 *
	 * @return the seeking
	 */
	public boolean getSeeking();

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
	 * Gets the initial time.
	 *
	 * @return the initial time
	 */
	public double getInitialTime();

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public double getDuration();

	/**
	 * Gets the start offset time.
	 *
	 * @return the start offset time
	 */
	public long getStartOffsetTime();

	/**
	 * Gets the paused.
	 *
	 * @return the paused
	 */
	public boolean getPaused();

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
	 * Gets the played.
	 *
	 * @return the played
	 */
	public TimeRanges getPlayed();

	/**
	 * Gets the seekable.
	 *
	 * @return the seekable
	 */
	public TimeRanges getSeekable();

	/**
	 * Gets the ended.
	 *
	 * @return the ended
	 */
	public boolean getEnded();

	/**
	 * Gets the autoplay.
	 *
	 * @return the autoplay
	 */
	public boolean getAutoplay();

	/**
	 * Sets the autoplay.
	 *
	 * @param autoplay
	 *            the new autoplay
	 */
	public void setAutoplay(boolean autoplay);

	/**
	 * Gets the loop.
	 *
	 * @return the loop
	 */
	public boolean getLoop();

	/**
	 * Sets the loop.
	 *
	 * @param loop
	 *            the new loop
	 */
	public void setLoop(boolean loop);

	/**
	 * Play.
	 */
	public void play();

	/**
	 * Pause.
	 */
	public void pause();

	/**
	 * Gets the media group.
	 *
	 * @return the media group
	 */
	public String getMediaGroup();

	/**
	 * Sets the media group.
	 *
	 * @param mediaGroup
	 *            the new media group
	 */
	public void setMediaGroup(String mediaGroup);

	/**
	 * Gets the controller.
	 *
	 * @return the controller
	 */
	public MediaController getController();

	/**
	 * Sets the controller.
	 *
	 * @param controller
	 *            the new controller
	 */
	public void setController(MediaController controller);

	/**
	 * Gets the controls.
	 *
	 * @return the controls
	 */
	public boolean getControls();

	/**
	 * Sets the controls.
	 *
	 * @param controls
	 *            the new controls
	 */
	public void setControls(boolean controls);

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
	 * Gets the default muted.
	 *
	 * @return the default muted
	 */
	public boolean getDefaultMuted();

	/**
	 * Sets the default muted.
	 *
	 * @param defaultMuted
	 *            the new default muted
	 */
	public void setDefaultMuted(boolean defaultMuted);

	/**
	 * Gets the audio tracks.
	 *
	 * @return the audio tracks
	 */
	public AudioTrackList getAudioTracks();

	/**
	 * Gets the video tracks.
	 *
	 * @return the video tracks
	 */
	public VideoTrackList getVideoTracks();

	/**
	 * Gets the text tracks.
	 *
	 * @return the text tracks
	 */
	public ObjectArray getTextTracks();

	/**
	 * Adds the text track.
	 *
	 * @param kind
	 *            the kind
	 * @return the mutable text track
	 */
	public MutableTextTrack addTextTrack(String kind);

	/**
	 * Adds the text track.
	 *
	 * @param kind
	 *            the kind
	 * @param label
	 *            the label
	 * @return the mutable text track
	 */
	public MutableTextTrack addTextTrack(String kind, String label);

	/**
	 * Adds the text track.
	 *
	 * @param kind
	 *            the kind
	 * @param label
	 *            the label
	 * @param language
	 *            the language
	 * @return the mutable text track
	 */
	public MutableTextTrack addTextTrack(String kind, String label, String language);
}
