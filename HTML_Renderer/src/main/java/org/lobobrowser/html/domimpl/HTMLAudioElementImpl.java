/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.w3c.ObjectArray;
import org.lobobrowser.w3c.html.AudioTrackList;
import org.lobobrowser.w3c.html.DOMSettableTokenList;
import org.lobobrowser.w3c.html.HTMLAudioElement;
import org.lobobrowser.w3c.html.MediaController;
import org.lobobrowser.w3c.html.MediaError;
import org.lobobrowser.w3c.html.MutableTextTrack;
import org.lobobrowser.w3c.html.TimeRanges;
import org.lobobrowser.w3c.html.VideoTrackList;
import org.mozilla.javascript.Function;
import org.w3c.dom.NodeList;

/**
 * The Class HTMLAudioElementImpl.
 */
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
	public NodeList getElementsByClassName(String classNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DOMSettableTokenList getDropzone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDropzone(String dropzone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSpellcheck() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void setSpellcheck(boolean spellcheck) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Function getOnabort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnabort(Function onabort) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnblur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnblur(Function onblur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOncanplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOncanplay(Function oncanplay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOncanplaythrough() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOncanplaythrough(Function oncanplaythrough) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnchange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnchange(Function onchange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnclick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnclick(Function onclick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOncontextmenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOncontextmenu(Function oncontextmenu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOncuechange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOncuechange(Function oncuechange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndblclick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndblclick(Function ondblclick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndrag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndrag(Function ondrag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndragend() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndragend(Function ondragend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndragenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndragenter(Function ondragenter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndragleave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndragleave(Function ondragleave) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndragover() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndragover(Function ondragover) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndragstart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndragstart(Function ondragstart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndrop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndrop(Function ondrop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndurationchange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndurationchange(Function ondurationchange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnemptied() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnemptied(Function onemptied) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnended() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnended(Function onended) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnerror() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnerror(Function onerror) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnfocus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnfocus(Function onfocus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOninput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOninput(Function oninput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOninvalid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOninvalid(Function oninvalid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnkeydown() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnkeydown(Function onkeydown) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnkeypress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnkeypress(Function onkeypress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnkeyup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnkeyup(Function onkeyup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnload(Function onload) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnloadeddata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnloadeddata(Function onloadeddata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnloadedmetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnloadedmetadata(Function onloadedmetadata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnloadstart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnloadstart(Function onloadstart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmousedown() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmousedown(Function onmousedown) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmousemove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmousemove(Function onmousemove) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmouseout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmouseout(Function onmouseout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmouseover() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmouseover(Function onmouseover) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmouseup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmouseup(Function onmouseup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmousewheel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmousewheel(Function onmousewheel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnpause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpause(Function onpause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnplay(Function onplay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnplaying() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnplaying(Function onplaying) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnprogress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnprogress(Function onprogress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnratechange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnratechange(Function onratechange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnreadystatechange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnreadystatechange(Function onreadystatechange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnreset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnreset(Function onreset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnscroll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnscroll(Function onscroll) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnseeked() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnseeked(Function onseeked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnseeking() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnseeking(Function onseeking) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnselect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnselect(Function onselect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnshow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnshow(Function onshow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnstalled() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnstalled(Function onstalled) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnsubmit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnsubmit(Function onsubmit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnsuspend() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnsuspend(Function onsuspend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOntimeupdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOntimeupdate(Function ontimeupdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnvolumechange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnvolumechange(Function onvolumechange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnwaiting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnwaiting(Function onwaiting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ObjectArray<?> getTextTracks() {
		// TODO Auto-generated method stub
		return null;
	}

    
}
