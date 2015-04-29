/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project This
 * library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLVideoElement;
import org.lobobrowser.html.w3c.MediaError;
import org.lobobrowser.html.w3c.TimeRanges;

/**
 * The Class HTMLVideoElementImpl.
 */
public class HTMLVideoElementImpl extends HTMLElementImpl implements
HTMLVideoElement {

    /**
     * Instantiates a new HTML video element impl.
     *
     * @param name
     *            the name
     */
    public HTMLVideoElementImpl(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getError()
     */
    @Override
    public MediaError getError() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getSrc()
     */
    @Override
    public String getSrc() {
        return this.getAttribute(HtmlAttributeProperties.SRC);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#setSrc(java.lang.String)
     */
    @Override
    public void setSrc(String src) {
        this.setAttribute(HtmlAttributeProperties.SRC, src);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getCurrentSrc()
     */
    @Override
    public String getCurrentSrc() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getNetworkState()
     */
    @Override
    public short getNetworkState() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getPreload()
     */
    @Override
    public String getPreload() {
        return this.getAttribute(HtmlAttributeProperties.PRELOAD);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#setPreload(java.lang.String)
     */
    @Override
    public void setPreload(String preload) {
        this.setAttribute(HtmlAttributeProperties.PRELOAD, preload);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getBuffered()
     */
    @Override
    public TimeRanges getBuffered() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#load()
     */
    @Override
    public void load() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#canPlayType(java.lang.String)
     */
    @Override
    public String canPlayType(String type) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getReadyState()
     */
    @Override
    public short getReadyState() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getSeeking()
     */
    @Override
    public boolean getSeeking() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getCurrentTime()
     */
    @Override
    public float getCurrentTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#setCurrentTime(float)
     */
    @Override
    public void setCurrentTime(float currentTime) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getStartTime()
     */
    @Override
    public float getStartTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getDuration()
     */
    @Override
    public float getDuration() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getPaused()
     */
    @Override
    public boolean getPaused() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getDefaultPlaybackRate()
     */
    @Override
    public float getDefaultPlaybackRate() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#setDefaultPlaybackRate(float)
     */
    @Override
    public void setDefaultPlaybackRate(float defaultPlaybackRate) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getPlaybackRate()
     */
    @Override
    public float getPlaybackRate() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#setPlaybackRate(float)
     */
    @Override
    public void setPlaybackRate(float playbackRate) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getPlayed()
     */
    @Override
    public TimeRanges getPlayed() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getSeekable()
     */
    @Override
    public TimeRanges getSeekable() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getEnded()
     */
    @Override
    public boolean getEnded() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getAutoplay()
     */
    @Override
    public boolean getAutoplay() {
        String autoplay = this.getAttribute(HtmlAttributeProperties.AUTOPLAY);
        return HtmlAttributeProperties.AUTOPLAY.equalsIgnoreCase(autoplay);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#setAutoplay(boolean)
     */
    @Override
    public void setAutoplay(boolean autoplay) {
        this.setAttribute(HtmlAttributeProperties.AUTOPLAY,
                autoplay ? HtmlAttributeProperties.AUTOPLAY : null);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getLoop()
     */
    @Override
    public boolean getLoop() {
        String loop = this.getAttribute(HtmlAttributeProperties.LOOP);
        return HtmlAttributeProperties.LOOP.equalsIgnoreCase(loop);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#setLoop(boolean)
     */
    @Override
    public void setLoop(boolean loop) {
        this.setAttribute(HtmlAttributeProperties.LOOP,
                loop ? HtmlAttributeProperties.LOOP : null);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#play()
     */
    @Override
    public void play() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#pause()
     */
    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getControls()
     */
    @Override
    public boolean getControls() {
        String controls = this.getAttribute(HtmlAttributeProperties.CONTROLS);
        return HtmlAttributeProperties.CONTROLS.equalsIgnoreCase(controls);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#setControls(boolean)
     */
    @Override
    public void setControls(boolean controls) {
        this.setAttribute(HtmlAttributeProperties.CONTROLS,
                controls ? HtmlAttributeProperties.CONTROLS : null);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getVolume()
     */
    @Override
    public float getVolume() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#setVolume(float)
     */
    @Override
    public void setVolume(float volume) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#getMuted()
     */
    @Override
    public boolean getMuted() {
        String muted = this.getAttribute(HtmlAttributeProperties.MUTED);
        return HtmlAttributeProperties.MUTED.equalsIgnoreCase(muted);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLMediaElement#setMuted(boolean)
     */
    @Override
    public void setMuted(boolean muted) {
        this.setAttribute(HtmlAttributeProperties.MUTED,
                muted ? HtmlAttributeProperties.MUTED : null);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLVideoElement#getWidth()
     */
    @Override
    public String getWidth() {
        return this.getAttribute(HtmlAttributeProperties.WIDTH);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLVideoElement#setWidth(java.lang.String)
     */
    @Override
    public void setWidth(String width) {
        this.setAttribute(HtmlAttributeProperties.WIDTH, width);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLVideoElement#getHeight()
     */
    @Override
    public String getHeight() {
        return this.getAttribute(HtmlAttributeProperties.HEIGHT);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLVideoElement#setHeight(java.lang.String)
     */
    @Override
    public void setHeight(String height) {
        this.setAttribute(HtmlAttributeProperties.HEIGHT, height);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLVideoElement#getVideoWidth()
     */
    @Override
    public int getVideoWidth() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLVideoElement#getVideoHeight()
     */
    @Override
    public int getVideoHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLVideoElement#getPoster()
     */
    @Override
    public String getPoster() {
        return this.getAttribute(HtmlAttributeProperties.POSTER);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLVideoElement#setPoster(java.lang.String)
     */
    @Override
    public void setPoster(String poster) {
        this.setAttribute(HtmlAttributeProperties.POSTER, poster);

    }

}
