/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLAudioElement;
import org.lobobrowser.html.w3c.MediaError;
import org.lobobrowser.html.w3c.TimeRanges;

public class HTMLAudioElementImpl extends HTMLElementImpl implements
		HTMLAudioElement {

	public HTMLAudioElementImpl(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MediaError getError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSrc() {
		return this.getAttribute(HtmlAttributeProperties.SRC);
	}

	@Override
	public void setSrc(String src) {
		this.setAttribute(HtmlAttributeProperties.SRC,src);
		
	}

	@Override
	public String getCurrentSrc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getNetworkState() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPreload() {
		return this.getAttribute(HtmlAttributeProperties.PRELOAD);
	}

	@Override
	public void setPreload(String preload) {
		this.setAttribute(HtmlAttributeProperties.PRELOAD, preload);
		
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
	public float getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentTime(float currentTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getStartTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getPaused() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getDefaultPlaybackRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDefaultPlaybackRate(float defaultPlaybackRate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getPlaybackRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPlaybackRate(float playbackRate) {
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
		String autoplay = this.getAttribute(HtmlAttributeProperties.AUTOPLAY);
		return HtmlAttributeProperties.AUTOPLAY.equalsIgnoreCase(autoplay);
	}

	@Override
	public void setAutoplay(boolean autoplay) {
		this.setAttribute(HtmlAttributeProperties.AUTOPLAY, autoplay ? HtmlAttributeProperties.AUTOPLAY : null);
		
	}

	@Override
	public boolean getLoop() {
		String loop = this.getAttribute(HtmlAttributeProperties.LOOP);
		return HtmlAttributeProperties.LOOP.equalsIgnoreCase(loop);
	}

	@Override
	public void setLoop(boolean loop) {
		this.setAttribute(HtmlAttributeProperties.LOOP, loop ? HtmlAttributeProperties.LOOP : null);
		
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
	public boolean getControls() {
		String controls = this.getAttribute(HtmlAttributeProperties.CONTROLS);
		return HtmlAttributeProperties.CONTROLS.equalsIgnoreCase(controls);
	}

	@Override
	public void setControls(boolean controls) {
		this.setAttribute(HtmlAttributeProperties.CONTROLS, controls ? HtmlAttributeProperties.CONTROLS : null);
		
	}

	@Override
	public float getVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVolume(float volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getMuted() {
		String muted = this.getAttribute(HtmlAttributeProperties.MUTED);
		return HtmlAttributeProperties.MUTED.equalsIgnoreCase(muted);
	}

	@Override
	public void setMuted(boolean muted) {
		this.setAttribute(HtmlAttributeProperties.MUTED, muted ? HtmlAttributeProperties.MUTED : null);
		
	}

}
