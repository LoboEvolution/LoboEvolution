package org.lobobrowser.html.w3c;


/**
 * The Interface HTMLMediaElement.
 */
public interface HTMLMediaElement extends HTMLElement {
	// HTMLMediaElement
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
	 * @param src the new src
	 */
	public void setSrc(String src);

	/**
	 * Gets the current src.
	 *
	 * @return the current src
	 */
	public String getCurrentSrc();

	/** The Constant NETWORK_EMPTY. */
	public static final short NETWORK_EMPTY = 0;
	
	/** The Constant NETWORK_IDLE. */
	public static final short NETWORK_IDLE = 1;
	
	/** The Constant NETWORK_LOADING. */
	public static final short NETWORK_LOADING = 2;
	
	/** The Constant NETWORK_NO_SOURCE. */
	public static final short NETWORK_NO_SOURCE = 3;

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
	 * @param preload the new preload
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
	 * @param type the type
	 * @return the string
	 */
	public String canPlayType(String type);

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
	 * Gets the ready state.
	 *
	 * @return the ready state
	 */
	public short getReadyState();

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
	public float getCurrentTime();

	/**
	 * Sets the current time.
	 *
	 * @param currentTime the new current time
	 */
	public void setCurrentTime(float currentTime);

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public float getStartTime();

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public float getDuration();

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
	public float getDefaultPlaybackRate();

	/**
	 * Sets the default playback rate.
	 *
	 * @param defaultPlaybackRate the new default playback rate
	 */
	public void setDefaultPlaybackRate(float defaultPlaybackRate);

	/**
	 * Gets the playback rate.
	 *
	 * @return the playback rate
	 */
	public float getPlaybackRate();

	/**
	 * Sets the playback rate.
	 *
	 * @param playbackRate the new playback rate
	 */
	public void setPlaybackRate(float playbackRate);

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
	 * @param autoplay the new autoplay
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
	 * @param loop the new loop
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
	 * Gets the controls.
	 *
	 * @return the controls
	 */
	public boolean getControls();

	/**
	 * Sets the controls.
	 *
	 * @param controls the new controls
	 */
	public void setControls(boolean controls);

	/**
	 * Gets the volume.
	 *
	 * @return the volume
	 */
	public float getVolume();

	/**
	 * Sets the volume.
	 *
	 * @param volume the new volume
	 */
	public void setVolume(float volume);

	/**
	 * Gets the muted.
	 *
	 * @return the muted
	 */
	public boolean getMuted();

	/**
	 * Sets the muted.
	 *
	 * @param muted the new muted
	 */
	public void setMuted(boolean muted);
}
