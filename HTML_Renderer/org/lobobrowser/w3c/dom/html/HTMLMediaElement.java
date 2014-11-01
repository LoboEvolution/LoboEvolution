package org.lobobrowser.w3c.dom.html;

public interface HTMLMediaElement extends HTMLElement
{
    // HTMLMediaElement
    public MediaError getError();
    public String getSrc();
    public void setSrc(String src);
    public String getCurrentSrc();
    public static final short NETWORK_EMPTY = 0;
    public static final short NETWORK_IDLE = 1;
    public static final short NETWORK_LOADING = 2;
    public static final short NETWORK_NO_SOURCE = 3;
    public short getNetworkState();
    public String getPreload();
    public void setPreload(String preload);
    public TimeRanges getBuffered();
    public void load();
    public String canPlayType(String type);
    public static final short HAVE_NOTHING = 0;
    public static final short HAVE_METADATA = 1;
    public static final short HAVE_CURRENT_DATA = 2;
    public static final short HAVE_FUTURE_DATA = 3;
    public static final short HAVE_ENOUGH_DATA = 4;
    public short getReadyState();
    public boolean getSeeking();
    public float getCurrentTime();
    public void setCurrentTime(float currentTime);
    public float getStartTime();
    public float getDuration();
    public boolean getPaused();
    public float getDefaultPlaybackRate();
    public void setDefaultPlaybackRate(float defaultPlaybackRate);
    public float getPlaybackRate();
    public void setPlaybackRate(float playbackRate);
    public TimeRanges getPlayed();
    public TimeRanges getSeekable();
    public boolean getEnded();
    public boolean getAutoplay();
    public void setAutoplay(boolean autoplay);
    public boolean getLoop();
    public void setLoop(boolean loop);
    public void play();
    public void pause();
    public boolean getControls();
    public void setControls(boolean controls);
    public float getVolume();
    public void setVolume(float volume);
    public boolean getMuted();
    public void setMuted(boolean muted);
}
