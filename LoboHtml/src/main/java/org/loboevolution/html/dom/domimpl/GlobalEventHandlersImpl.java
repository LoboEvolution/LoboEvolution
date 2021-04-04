/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.domimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.loboevolution.common.Strings;
import org.loboevolution.html.js.Executor;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.JavaScript;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.events.GlobalEventHandlers;

/**
 * Implements common functionality of most elements.
 *
 *
 *
 */
public class GlobalEventHandlersImpl extends EventTargetImpl implements GlobalEventHandlers {
	
	private Map<String, Function> functionByAttribute = null;

	private Function onfocus, onblur, onclick, ondblclick, onmousedown, onmouseup, onmouseover, onmousemove, onmouseout,
			onkeypress, onkeydown, onkeyup, oncontextmenu, onchange, onabort, onwaiting, onvolumechange, ontransitionstart,
			ontransitionrun, ontransitionend, ontransitioncancel, ontouchstart, ontoggle, ontouchmove, ontouchend,
			ontouchcancel, ontimeupdate, onsubmit, onstalled, onselectstart, onselectionchange, onselect, onseeking,
			onseeked, onsecuritypolicyviolation, onscroll, onresize, onreset, onratechange, onprogress, onpointerup,
			onpointerover, onpointerout, onwheel, onsuspend, onpointermove, onpointerleave, onpointerenter,
			onpointerdown, onpointercancel, onplaying, onplay, onpause, onmouseleave, onmouseenter, onauxclick,
			onlostpointercapture, onloadstart, onloadend, onloadedmetadata, onloadeddata, onload, oninvalid, oninput,
			ongotpointercapture, onfocusout, onfocusin, onerror, onended, onemptied, ondurationchange, ondrop,
			ondragstart, ondragover, ondragleave, ondragexit, ondragenter, ondragend, ondrag, oncuechange, onclose,
			oncanplaythrough, oncanplay, onanimationstart, onanimationiteration, onanimationend, onanimationcancel,
			oncancel;
	

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onblur.
	 * </p>
	 */
	@Override
	public Function getOnblur() {
		return getEventFunction(this.onblur, "onblur");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onclick.
	 * </p>
	 */
	@Override
	public Function getOnclick() {
		return getEventFunction(this.onclick, "onclick");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field oncontextmenu.
	 * </p>
	 */
	@Override
	public Function getOncontextmenu() {
		return getEventFunction(this.oncontextmenu, "oncontextmenu");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field ondblclick.
	 * </p>
	 */
	@Override
	public Function getOndblclick() {
		return getEventFunction(this.ondblclick, "ondblclick");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onfocus.
	 * </p>
	 */
	@Override
	public Function getOnfocus() {
		return getEventFunction(this.onfocus, "onfocus");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onkeydown.
	 * </p>
	 */
	@Override
	public Function getOnkeydown() {
		return getEventFunction(this.onkeydown, "onkeydown");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onkeypress.
	 * </p>
	 */
	@Override
	public Function getOnkeypress() {
		return getEventFunction(this.onkeypress, "onkeypress");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onkeyup.
	 * </p>
	 */
	@Override
	public Function getOnkeyup() {
		return getEventFunction(this.onkeyup, "onkeyup");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onmousedown.
	 * </p>
	 */
	@Override
	public Function getOnmousedown() {
		return getEventFunction(this.onmousedown, "onmousedown");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onmousemove.
	 * </p>
	 */
	@Override
	public Function getOnmousemove() {
		return getEventFunction(this.onmousemove, "onmousemove");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onmouseout.
	 * </p>
	 */
	@Override
	public Function getOnmouseout() {
		return getEventFunction(this.onmouseout, "onmouseout");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onmouseover.
	 * </p>
	 */
	@Override
	public Function getOnmouseover() {
		return getEventFunction(this.onmouseover, "onmouseover");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onmouseup.
	 * </p>
	 */
	@Override
	public Function getOnmouseup() {
		return getEventFunction(this.onmouseup, "onmouseup");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Getter for the field onchange.
	 * </p>
	 */
	@Override
	public Function getOnchange() {
		return getEventFunction(this.onchange, "onchange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnabort() {
		return getEventFunction(this.onabort, "onabort");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationcancel() {
		return getEventFunction(this.onanimationcancel, "onanimationcancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationend() {
		return getEventFunction(this.onanimationend, "onanimationend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationiteration() {
		return getEventFunction(this.onanimationiteration, "onanimationiteration");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnanimationstart() {
		return getEventFunction(this.onanimationstart, "onanimationstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnauxclick() {
		return getEventFunction(this.onauxclick, "onauxclick");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncancel() {
		return getEventFunction(this.oncancel, "oncancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncanplay() {

		return getEventFunction(this.oncanplay, "oncanplay");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncanplaythrough() {
		return getEventFunction(this.oncanplaythrough, "oncanplaythrough");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnclose() {
		return getEventFunction(this.onclose, "onclose");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOncuechange() {
		return getEventFunction(this.oncuechange, "oncuechange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndrag() {
		return getEventFunction(this.ondrag, "ondrag");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragend() {
		return getEventFunction(this.ondragend, "ondragend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragenter() {
		return getEventFunction(this.ondragenter, "ondragenter");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragexit() {
		return getEventFunction(this.ondragexit, "ondragexit");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragleave() {
		return getEventFunction(this.ondragleave, "ondragleave");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragover() {
		return getEventFunction(this.ondragover, "ondragover");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndragstart() {
		return getEventFunction(this.ondragstart, "ondragstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndrop() {
		return getEventFunction(this.ondrop, "ondrop");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOndurationchange() {
		return getEventFunction(this.ondurationchange, "ondurationchange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnemptied() {
		return getEventFunction(this.onemptied, "onemptied");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnended() {
		return getEventFunction(this.onended, "onended");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnerror() {
		return getEventFunction(this.onerror, "onerror");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnfocusin() {
		return getEventFunction(this.onfocusin, "onfocusin");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnfocusout() {
		return getEventFunction(this.onfocusout, "onfocusout");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOngotpointercapture() {
		return getEventFunction(this.ongotpointercapture, "ongotpointercapture");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOninput() {
		return getEventFunction(this.oninput, "oninput");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOninvalid() {
		return getEventFunction(this.oninvalid, "oninvalid");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnload() {
		return getEventFunction(this.onload, "onload");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadeddata() {
		return getEventFunction(this.onloadeddata, "onloadeddata");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadedmetadata() {
		return getEventFunction(this.onloadedmetadata, "onloadedmetadata");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadend() {
		return getEventFunction(this.onloadend, "onloadend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnloadstart() {
		return getEventFunction(this.onloadstart, "onloadstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnlostpointercapture() {
		return getEventFunction(this.onlostpointercapture, "onlostpointercapture");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnmouseenter() {
		return getEventFunction(this.onmouseenter, "onmouseenter");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnmouseleave() {
		return getEventFunction(this.onmouseleave, "onmouseleave");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpause() {
		return getEventFunction(this.onpause, "onpause");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnplay() {
		return getEventFunction(this.onplay, "onplay");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnplaying() {
		return getEventFunction(this.onplaying, "onplaying");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointercancel() {
		return getEventFunction(this.onpointercancel, "onpointercancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerdown() {
		return getEventFunction(this.onpointerdown, "onpointerdown");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerenter() {
		return getEventFunction(this.onpointerenter, "onpointerenter");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerleave() {
		return getEventFunction(this.onpointerleave, "onpointerleave");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointermove() {
		return getEventFunction(this.onpointermove, "onpointermove");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerout() {
		return getEventFunction(this.onpointerout, "onpointerout");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerover() {
		return getEventFunction(this.onpointerover, "onpointerover");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnpointerup() {
		return getEventFunction(this.onpointerup, "onpointerup");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnprogress() {
		return getEventFunction(this.onprogress, "onprogress");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnratechange() {
		return getEventFunction(this.onratechange, "onratechange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnreset() {
		return getEventFunction(this.onreset, "onreset");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnresize() {
		return getEventFunction(this.onresize, "onresize");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnscroll() {
		return getEventFunction(this.onscroll, "onscroll");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnsecuritypolicyviolation() {
		return getEventFunction(this.onsecuritypolicyviolation, "onsecuritypolicyviolation");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnseeked() {
		return getEventFunction(this.onseeked, "onseeked");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnseeking() {
		return getEventFunction(this.onseeking, "onseeking");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnselect() {
		return getEventFunction(this.onselect, "onselect");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnselectionchange() {
		return getEventFunction(this.onselectionchange, "onselectionchange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnselectstart() {
		return getEventFunction(this.onselectstart, "onselectstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnstalled() {
		return getEventFunction(this.onstalled, "onstalled");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnsubmit() {
		return getEventFunction(this.onsubmit, "onsubmit");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnsuspend() {
		return getEventFunction(this.onsuspend, "onsuspend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntimeupdate() {
		return getEventFunction(this.ontimeupdate, "ontimeupdate");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntoggle() {
		return getEventFunction(this.ontoggle, "ontoggle");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchcancel() {
		return getEventFunction(this.ontouchcancel, "ontouchcancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchend() {
		return getEventFunction(this.ontouchend, "ontouchend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchmove() {
		return getEventFunction(this.ontouchmove, "ontouchmove");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntouchstart() {
		return getEventFunction(this.ontouchstart, "ontouchstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitioncancel() {
		return getEventFunction(this.ontransitioncancel, "ontransitioncancel");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitionend() {
		return getEventFunction(this.ontransitionend, "ontransitionend");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitionrun() {
		return getEventFunction(this.ontransitionrun, "ontransitionrun");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOntransitionstart() {
		return getEventFunction(this.ontransitionstart, "ontransitionstart");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnvolumechange() {
		return getEventFunction(this.onvolumechange, "onvolumechange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnwaiting() {
		return getEventFunction(this.onwaiting, "onwaiting");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnwheel() {
		return getEventFunction(this.onwheel, "onwheel");
	}

	/**
	 * <p>Setter for the field <code>functionByAttribute</code>.</p>
	 *
	 * @param functionByAttribute the functionByAttribute to set
	 */
	public void setFunctionByAttribute(Map<String, Function> functionByAttribute) {
		this.functionByAttribute = functionByAttribute;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnfocus(final Function onfocus) {
		this.onfocus = onfocus;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnblur(final Function onblur) {
		this.onblur = onblur;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnclick(final Function onclick) {
		this.onclick = onclick;
	}

	/** {@inheritDoc} */
	@Override
	public void setOndblclick(final Function ondblclick) {
		this.ondblclick = ondblclick;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmousedown(final Function onmousedown) {
		this.onmousedown = onmousedown;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseup(final Function onmouseup) {
		this.onmouseup = onmouseup;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseover(final Function onmouseover) {
		this.onmouseover = onmouseover;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmousemove(final Function onmousemove) {
		this.onmousemove = onmousemove;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseout(final Function onmouseout) {
		this.onmouseout = onmouseout;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnkeypress(final Function onkeypress) {
		this.onkeypress = onkeypress;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnkeydown(final Function onkeydown) {
		this.onkeydown = onkeydown;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnkeyup(final Function onkeyup) {
		this.onkeyup = onkeyup;
	}

	/** {@inheritDoc} */
	@Override
	public void setOncontextmenu(final Function oncontextmenu) {
		this.oncontextmenu = oncontextmenu;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnchange(final Function onchange) {
		this.onchange = onchange;
	}

	/**
	 * <p>setOnunload.</p>
	 *
	 * @param onunload the onunload to set
	 */
	public void setOnunload(final Function onunload) {
	}

	/**
	 * <p>setOnafterprint.</p>
	 *
	 * @param onafterprint the onafterprint to set
	 */
	public void setOnafterprint(final Function onafterprint) {
	}

	/**
	 * <p>setOnbeforeprint.</p>
	 *
	 * @param onbeforeprint the onbeforeprint to set
	 */
	public void setOnbeforeprint(final Function onbeforeprint) {
	}

	/**
	 * <p>setOnlanguagechange.</p>
	 *
	 * @param onlanguagechange the onlanguagechange to set
	 */
	public void setOnlanguagechange(final Function onlanguagechange) {
	}

	/**
	 * <p>setOnonline.</p>
	 *
	 * @param ononline the ononline to set
	 */
	public void setOnonline(final Function ononline) {
	}

	/** {@inheritDoc} */
	@Override
	public void setOnabort(final Function onabort) {
		this.onabort = onabort;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnwaiting(final Function onwaiting) {
		this.onwaiting = onwaiting;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnvolumechange(final Function onvolumechange) {
		this.onvolumechange = onvolumechange;
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitionstart(final Function ontransitionstart) {
		this.ontransitionstart = ontransitionstart;
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitionrun(final Function ontransitionrun) {
		this.ontransitionrun = ontransitionrun;
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitionend(final Function ontransitionend) {
		this.ontransitionend = ontransitionend;
	}

	/** {@inheritDoc} */
	@Override
	public void setOntransitioncancel(final Function ontransitioncancel) {
		this.ontransitioncancel = ontransitioncancel;
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchstart(final Function ontouchstart) {
		this.ontouchstart = ontouchstart;
	}

	/** {@inheritDoc} */
	@Override
	public void setOntoggle(final Function ontoggle) {
		this.ontoggle = ontoggle;
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchmove(final Function ontouchmove) {
		this.ontouchmove = ontouchmove;
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchend(final Function ontouchend) {
		this.ontouchend = ontouchend;
	}

	/** {@inheritDoc} */
	@Override
	public void setOntouchcancel(final Function ontouchcancel) {
		this.ontouchcancel = ontouchcancel;
	}

	/** {@inheritDoc} */
	@Override
	public void setOntimeupdate(final Function ontimeupdate) {
		this.ontimeupdate = ontimeupdate;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnsubmit(final Function onsubmit) {
		this.onsubmit = onsubmit;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnstalled(final Function onstalled) {
		this.onstalled = onstalled;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnselectstart(final Function onselectstart) {
		this.onselectstart = onselectstart;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnselectionchange(final Function onselectionchange) {
		this.onselectionchange = onselectionchange;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnselect(final Function onselect) {
		this.onselect = onselect;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnseeking(final Function onseeking) {
		this.onseeking = onseeking;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnseeked(final Function onseeked) {
		this.onseeked = onseeked;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnsecuritypolicyviolation(final Function onsecuritypolicyviolation) {
		this.onsecuritypolicyviolation = onsecuritypolicyviolation;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnscroll(final Function onscroll) {
		this.onscroll = onscroll;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnresize(final Function onresize) {
		this.onresize = onresize;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnreset(final Function onreset) {
		this.onreset = onreset;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnratechange(final Function onratechange) {
		this.onratechange = onratechange;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnprogress(final Function onprogress) {
		this.onprogress = onprogress;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerup(final Function onpointerup) {
		this.onpointerup = onpointerup;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerover(final Function onpointerover) {
		this.onpointerover = onpointerover;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerout(final Function onpointerout) {
		this.onpointerout = onpointerout;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnwheel(final Function onwheel) {
		this.onwheel = onwheel;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnsuspend(final Function onsuspend) {
		this.onsuspend = onsuspend;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointermove(final Function onpointermove) {
		this.onpointermove = onpointermove;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerleave(final Function onpointerleave) {
		this.onpointerleave = onpointerleave;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerenter(final Function onpointerenter) {
		this.onpointerenter = onpointerenter;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointerdown(final Function onpointerdown) {
		this.onpointerdown = onpointerdown;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpointercancel(final Function onpointercancel) {
		this.onpointercancel = onpointercancel;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnplaying(final Function onplaying) {
		this.onplaying = onplaying;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnplay(final Function onplay) {
		this.onplay = onplay;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnpause(final Function onpause) {
		this.onpause = onpause;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseleave(final Function onmouseleave) {
		this.onmouseleave = onmouseleave;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnmouseenter(final Function onmouseenter) {
		this.onmouseenter = onmouseenter;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnauxclick(final Function onauxclick) {
		this.onauxclick = onauxclick;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnlostpointercapture(final Function onlostpointercapture) {
		this.onlostpointercapture = onlostpointercapture;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadstart(final Function onloadstart) {
		this.onloadstart = onloadstart;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadend(final Function onloadend) {
		this.onloadend = onloadend;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadedmetadata(final Function onloadedmetadata) {
		this.onloadedmetadata = onloadedmetadata;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnloadeddata(final Function onloadeddata) {
		this.onloadeddata = onloadeddata;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnload(final Function onload) {
		this.onload = onload;
	}

	/** {@inheritDoc} */
	@Override
	public void setOninvalid(final Function oninvalid) {
		this.oninvalid = oninvalid;
	}

	/** {@inheritDoc} */
	@Override
	public void setOninput(final Function oninput) {
		this.oninput = oninput;
	}

	/** {@inheritDoc} */
	@Override
	public void setOngotpointercapture(final Function ongotpointercapture) {
		this.ongotpointercapture = ongotpointercapture;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnfocusout(final Function onfocusout) {
		this.onfocusout = onfocusout;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnfocusin(final Function onfocusin) {
		this.onfocusin = onfocusin;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnerror(final Function onerror) {
		this.onerror = onerror;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnended(final Function onended) {
		this.onended = onended;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnemptied(final Function onemptied) {
		this.onemptied = onemptied;
	}

	/** {@inheritDoc} */
	@Override
	public void setOndurationchange(final Function ondurationchange) {
		this.ondurationchange = ondurationchange;
	}

	/** {@inheritDoc} */
	@Override
	public void setOndrop(final Function ondrop) {
		this.ondrop = ondrop;
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragstart(final Function ondragstart) {
		this.ondragstart = ondragstart;
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragover(final Function ondragover) {
		this.ondragover = ondragover;
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragleave(final Function ondragleave) {
		this.ondragleave = ondragleave;
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragexit(final Function ondragexit) {
		this.ondragexit = ondragexit;
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragenter(final Function ondragenter) {
		this.ondragenter = ondragenter;
	}

	/** {@inheritDoc} */
	@Override
	public void setOndragend(final Function ondragend) {
		this.ondragend = ondragend;
	}

	/** {@inheritDoc} */
	@Override
	public void setOndrag(final Function ondrag) {
		this.ondrag = ondrag;
	}

	/** {@inheritDoc} */
	@Override
	public void setOncuechange(final Function oncuechange) {
		this.oncuechange = oncuechange;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnclose(final Function onclose) {
		this.onclose = onclose;
	}

	/** {@inheritDoc} */
	@Override
	public void setOncanplaythrough(final Function oncanplaythrough) {
		this.oncanplaythrough = oncanplaythrough;
	}

	/** {@inheritDoc} */
	@Override
	public void setOncanplay(final Function oncanplay) {
		this.oncanplay = oncanplay;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationstart(final Function onanimationstart) {
		this.onanimationstart = onanimationstart;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationiteration(final Function onanimationiteration) {
		this.onanimationiteration = onanimationiteration;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationend(final Function onanimationend) {
		this.onanimationend = onanimationend;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnanimationcancel(final Function onanimationcancel) {
		this.onanimationcancel = onanimationcancel;
	}

	/** {@inheritDoc} */
	@Override
	public void setOncancel(final Function oncancel) {
		this.oncancel = oncancel;
	}
	
	/**
	 * <p>setOnoffline.</p>
	 *
	 * @param onoffline a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnoffline(final Function onoffline) {

	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @param normalName a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	protected void assignAttributeField(String normalName, String value) {
		ElementImpl elem = (ElementImpl)this;
		elem.assignAttributeField(normalName, value);
		if (normalName.startsWith("on")) {
			synchronized (this) {
				final Map<String, Function> fba = this.functionByAttribute;
				if (fba != null) {
					fba.remove(normalName);
				}
			}
		}
	}
	
	/**
	 * <p>
	 * getEventFunction.
	 * </p>
	 *
	 * @param varValue      a {@link org.mozilla.javascript.Function} object.
	 * @param attributeName a {@link java.lang.String} object.
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	protected Function getEventFunction(Function varValue, String attributeName) {
		if (varValue != null) {
			return varValue;
		}
		final String normalAttributeName = Strings.normalizeAttributeName(attributeName);
		synchronized (this) {
			Map<String, Function> fba = this.functionByAttribute;
			Function f = fba == null ? null : fba.get(normalAttributeName);
			if (f != null) {
				return f;
			}
			final UserAgentContext uac = getUserAgentContext();
			if (uac == null) {
				throw new IllegalStateException("No user agent context.");
			}
			if (uac.isScriptingEnabled() && this instanceof ElementImpl) {								
				ElementImpl elem = (ElementImpl)this;
				final String attributeValue = elem.getAttribute(attributeName);
				if (attributeValue == null || attributeValue.length() == 0) {
					f = null;
				} else {
					final String functionCode = "function " + normalAttributeName + "_" + System.identityHashCode(this) + "() { " + attributeValue + " }";
					final Document doc = this.document;
					if (doc == null) {
						throw new IllegalStateException("Element does not belong to a document.");
					}
					final Context ctx = Executor.createContext(getDocumentURL(), uac);
					try {
						final Scriptable scope = (Scriptable) doc.getUserData(Executor.SCOPE_KEY);
						if (scope == null) {
							throw new IllegalStateException("Scriptable (scope) instance was expected to be keyed as UserData to document using " + Executor.SCOPE_KEY);
						}
						final Scriptable thisScope = (Scriptable) JavaScript.getInstance().getJavascriptObject(this,scope);
						try {
							ctx.setLanguageVersion(Context.VERSION_1_8);
							f = ctx.compileFunction(thisScope, functionCode, elem.getTagName() + "[" + elem.getId() + "]." + attributeName, 1, null);
						} catch (final RhinoException ecmaError) {
							logger.log(Level.WARNING, "Javascript error at " + ecmaError.sourceName() + ":" + ecmaError.lineNumber() + ": " + ecmaError.getMessage(), ecmaError.getMessage());
							f = null;
						} catch (final Throwable err) {
							logger.log(Level.WARNING, "Unable to evaluate Javascript code", err);
							f = null;
						}
					} finally {
						Context.exit();
					}
				}
				if (fba == null) {
					fba = new HashMap<>(1);
					this.functionByAttribute = fba;
				}
				fba.put(normalAttributeName, f);
			}
			return f;
		}
	}
}
