/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.w3c.ObjectArray;
import org.lobobrowser.w3c.html.AudioTrackList;
import org.lobobrowser.w3c.html.HTMLVideoElement;
import org.lobobrowser.w3c.html.MediaController;
import org.lobobrowser.w3c.html.MediaError;
import org.lobobrowser.w3c.html.MutableTextTrack;
import org.lobobrowser.w3c.html.TimeRanges;
import org.lobobrowser.w3c.html.VideoTrackList;

/**
 * The Class HTMLVideoElementImpl.
 */
public class HTMLVideoElementImpl extends HTMLElementImpl implements HTMLVideoElement {

	/**
	 * Instantiates a new HTML video element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLVideoElementImpl(String name) {
		super(name);
	}

	@Override
	public MediaError getError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSrc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSrc(String src) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCurrentSrc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCrossOrigin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCrossOrigin(String crossOrigin) {
		// TODO Auto-generated method stub

	}

	@Override
	public short getNetworkState() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPreload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPreload(String preload) {
		// TODO Auto-generated method stub

	}

	@Override
	public TimeRanges getBuffered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub

	}

	@Override
	public String canPlayType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getReadyState() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getSeeking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentTime(double currentTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getInitialTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getStartOffsetTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getPaused() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getDefaultPlaybackRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDefaultPlaybackRate(double defaultPlaybackRate) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getPlaybackRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPlaybackRate(double playbackRate) {
		// TODO Auto-generated method stub

	}

	@Override
	public TimeRanges getPlayed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeRanges getSeekable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getEnded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getAutoplay() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAutoplay(boolean autoplay) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getLoop() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setLoop(boolean loop) {
		// TODO Auto-generated method stub

	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMediaGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMediaGroup(String mediaGroup) {
		// TODO Auto-generated method stub

	}

	@Override
	public MediaController getController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setController(MediaController controller) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getControls() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setControls(boolean controls) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVolume(double volume) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getMuted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMuted(boolean muted) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getDefaultMuted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDefaultMuted(boolean defaultMuted) {
		// TODO Auto-generated method stub

	}

	@Override
	public AudioTrackList getAudioTracks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VideoTrackList getVideoTracks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MutableTextTrack addTextTrack(String kind) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MutableTextTrack addTextTrack(String kind, String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MutableTextTrack addTextTrack(String kind, String label, String language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHeight(int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getVideoWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getVideoHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPoster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPoster(String poster) {
		// TODO Auto-generated method stub

	}

	@Override
	public ObjectArray getTextTracks() {
		// TODO Auto-generated method stub
		return null;
	}
}
