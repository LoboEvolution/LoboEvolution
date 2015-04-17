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
package org.lobobrowser.html.w3c;

/**
 * The public interface HTMLMediaElement.
 */
public interface HTMLMediaElement extends HTMLElement {
    // HTMLMediaElement
    /**
     * Gets the error.
     *
     * @return the error
     */
    MediaError getError();

    /**
     * Gets the src.
     *
     * @return the src
     */
    String getSrc();

    /**
     * Sets the src.
     *
     * @param src
     *            the new src
     */
    void setSrc(String src);

    /**
     * Gets the current src.
     *
     * @return the current src
     */
    String getCurrentSrc();

    /** The Constant NETWORK_EMPTY. */
    short NETWORK_EMPTY = 0;

    /** The Constant NETWORK_IDLE. */
    short NETWORK_IDLE = 1;

    /** The Constant NETWORK_LOADING. */
    short NETWORK_LOADING = 2;

    /** The Constant NETWORK_NO_SOURCE. */
    short NETWORK_NO_SOURCE = 3;

    /**
     * Gets the network state.
     *
     * @return the network state
     */
    short getNetworkState();

    /**
     * Gets the preload.
     *
     * @return the preload
     */
    String getPreload();

    /**
     * Sets the preload.
     *
     * @param preload
     *            the new preload
     */
    void setPreload(String preload);

    /**
     * Gets the buffered.
     *
     * @return the buffered
     */
    TimeRanges getBuffered();

    /**
     * Load.
     */
    void load();

    /**
     * Can play type.
     *
     * @param type
     *            the type
     * @return the string
     */
    String canPlayType(String type);

    /** The Constant HAVE_NOTHING. */
    short HAVE_NOTHING = 0;

    /** The Constant HAVE_METADATA. */
    short HAVE_METADATA = 1;

    /** The Constant HAVE_CURRENT_DATA. */
    short HAVE_CURRENT_DATA = 2;

    /** The Constant HAVE_FUTURE_DATA. */
    short HAVE_FUTURE_DATA = 3;

    /** The Constant HAVE_ENOUGH_DATA. */
    short HAVE_ENOUGH_DATA = 4;

    /**
     * Gets the ready state.
     *
     * @return the ready state
     */
    short getReadyState();

    /**
     * Gets the seeking.
     *
     * @return the seeking
     */
    boolean getSeeking();

    /**
     * Gets the current time.
     *
     * @return the current time
     */
    float getCurrentTime();

    /**
     * Sets the current time.
     *
     * @param currentTime
     *            the new current time
     */
    void setCurrentTime(float currentTime);

    /**
     * Gets the start time.
     *
     * @return the start time
     */
    float getStartTime();

    /**
     * Gets the duration.
     *
     * @return the duration
     */
    float getDuration();

    /**
     * Gets the paused.
     *
     * @return the paused
     */
    boolean getPaused();

    /**
     * Gets the default playback rate.
     *
     * @return the default playback rate
     */
    float getDefaultPlaybackRate();

    /**
     * Sets the default playback rate.
     *
     * @param defaultPlaybackRate
     *            the new default playback rate
     */
    void setDefaultPlaybackRate(float defaultPlaybackRate);

    /**
     * Gets the playback rate.
     *
     * @return the playback rate
     */
    float getPlaybackRate();

    /**
     * Sets the playback rate.
     *
     * @param playbackRate
     *            the new playback rate
     */
    void setPlaybackRate(float playbackRate);

    /**
     * Gets the played.
     *
     * @return the played
     */
    TimeRanges getPlayed();

    /**
     * Gets the seekable.
     *
     * @return the seekable
     */
    TimeRanges getSeekable();

    /**
     * Gets the ended.
     *
     * @return the ended
     */
    boolean getEnded();

    /**
     * Gets the autoplay.
     *
     * @return the autoplay
     */
    boolean getAutoplay();

    /**
     * Sets the autoplay.
     *
     * @param autoplay
     *            the new autoplay
     */
    void setAutoplay(boolean autoplay);

    /**
     * Gets the loop.
     *
     * @return the loop
     */
    boolean getLoop();

    /**
     * Sets the loop.
     *
     * @param loop
     *            the new loop
     */
    void setLoop(boolean loop);

    /**
     * Play.
     */
    void play();

    /**
     * Pause.
     */
    void pause();

    /**
     * Gets the controls.
     *
     * @return the controls
     */
    boolean getControls();

    /**
     * Sets the controls.
     *
     * @param controls
     *            the new controls
     */
    void setControls(boolean controls);

    /**
     * Gets the volume.
     *
     * @return the volume
     */
    float getVolume();

    /**
     * Sets the volume.
     *
     * @param volume
     *            the new volume
     */
    void setVolume(float volume);

    /**
     * Gets the muted.
     *
     * @return the muted
     */
    boolean getMuted();

    /**
     * Sets the muted.
     *
     * @param muted
     *            the new muted
     */
    void setMuted(boolean muted);
}
