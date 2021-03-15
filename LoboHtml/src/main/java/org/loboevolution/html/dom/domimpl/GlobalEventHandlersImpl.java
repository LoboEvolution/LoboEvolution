/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
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
 * @author utente
 * @version $Id: $Id
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
	 * <p>
	 * Getter for the field onblur.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnblur() {
		return getEventFunction(this.onblur, "onblur");
	}

	/**
	 * <p>
	 * Getter for the field onclick.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnclick() {
		return getEventFunction(this.onclick, "onclick");
	}

	/**
	 * <p>
	 * Getter for the field oncontextmenu.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOncontextmenu() {
		return getEventFunction(this.oncontextmenu, "oncontextmenu");
	}

	/**
	 * <p>
	 * Getter for the field ondblclick.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOndblclick() {
		return getEventFunction(this.ondblclick, "ondblclick");
	}

	/**
	 * <p>
	 * Getter for the field onfocus.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnfocus() {
		return getEventFunction(this.onfocus, "onfocus");
	}

	/**
	 * <p>
	 * Getter for the field onkeydown.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnkeydown() {
		return getEventFunction(this.onkeydown, "onkeydown");
	}

	/**
	 * <p>
	 * Getter for the field onkeypress.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnkeypress() {
		return getEventFunction(this.onkeypress, "onkeypress");
	}

	/**
	 * <p>
	 * Getter for the field onkeyup.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnkeyup() {
		return getEventFunction(this.onkeyup, "onkeyup");
	}

	/**
	 * <p>
	 * Getter for the field onmousedown.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnmousedown() {
		return getEventFunction(this.onmousedown, "onmousedown");
	}

	/**
	 * <p>
	 * Getter for the field onmousemove.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnmousemove() {
		return getEventFunction(this.onmousemove, "onmousemove");
	}

	/**
	 * <p>
	 * Getter for the field onmouseout.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnmouseout() {
		return getEventFunction(this.onmouseout, "onmouseout");
	}

	/**
	 * <p>
	 * Getter for the field onmouseover.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnmouseover() {
		return getEventFunction(this.onmouseover, "onmouseover");
	}

	/**
	 * <p>
	 * Getter for the field onmouseup.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnmouseup() {
		return getEventFunction(this.onmouseup, "onmouseup");
	}

	/**
	 * <p>
	 * Getter for the field onchange.
	 * </p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	@Override
	public Function getOnchange() {
		return getEventFunction(this.onchange, "onchange");
	}

	@Override
	public Function getOnabort() {
		return getEventFunction(this.onabort, "onabort");
	}

	@Override
	public Function getOnanimationcancel() {
		return getEventFunction(this.onanimationcancel, "onanimationcancel");
	}

	@Override
	public Function getOnanimationend() {
		return getEventFunction(this.onanimationend, "onanimationend");
	}

	@Override
	public Function getOnanimationiteration() {
		return getEventFunction(this.onanimationiteration, "onanimationiteration");
	}

	@Override
	public Function getOnanimationstart() {
		return getEventFunction(this.onanimationstart, "onanimationstart");
	}

	@Override
	public Function getOnauxclick() {
		return getEventFunction(this.onauxclick, "onauxclick");
	}

	@Override
	public Function getOncancel() {
		return getEventFunction(this.oncancel, "oncancel");
	}

	@Override
	public Function getOncanplay() {

		return getEventFunction(this.oncanplay, "oncanplay");
	}

	@Override
	public Function getOncanplaythrough() {
		return getEventFunction(this.oncanplaythrough, "oncanplaythrough");
	}

	@Override
	public Function getOnclose() {
		return getEventFunction(this.onclose, "onclose");
	}

	@Override
	public Function getOncuechange() {
		return getEventFunction(this.oncuechange, "oncuechange");
	}

	@Override
	public Function getOndrag() {
		return getEventFunction(this.ondrag, "ondrag");
	}

	@Override
	public Function getOndragend() {
		return getEventFunction(this.ondragend, "ondragend");
	}

	@Override
	public Function getOndragenter() {
		return getEventFunction(this.ondragenter, "ondragenter");
	}

	@Override
	public Function getOndragexit() {
		return getEventFunction(this.ondragexit, "ondragexit");
	}

	@Override
	public Function getOndragleave() {
		return getEventFunction(this.ondragleave, "ondragleave");
	}

	@Override
	public Function getOndragover() {
		return getEventFunction(this.ondragover, "ondragover");
	}

	@Override
	public Function getOndragstart() {
		return getEventFunction(this.ondragstart, "ondragstart");
	}

	@Override
	public Function getOndrop() {
		return getEventFunction(this.ondrop, "ondrop");
	}

	@Override
	public Function getOndurationchange() {
		return getEventFunction(this.ondurationchange, "ondurationchange");
	}

	@Override
	public Function getOnemptied() {
		return getEventFunction(this.onemptied, "onemptied");
	}

	@Override
	public Function getOnended() {
		return getEventFunction(this.onended, "onended");
	}

	@Override
	public Function getOnerror() {
		return getEventFunction(this.onerror, "onerror");
	}

	@Override
	public Function getOnfocusin() {
		return getEventFunction(this.onfocusin, "onfocusin");
	}

	@Override
	public Function getOnfocusout() {
		return getEventFunction(this.onfocusout, "onfocusout");
	}

	@Override
	public Function getOngotpointercapture() {
		return getEventFunction(this.ongotpointercapture, "ongotpointercapture");
	}

	@Override
	public Function getOninput() {
		return getEventFunction(this.oninput, "oninput");
	}

	@Override
	public Function getOninvalid() {
		return getEventFunction(this.oninvalid, "oninvalid");
	}

	@Override
	public Function getOnload() {
		return getEventFunction(this.onload, "onload");
	}

	@Override
	public Function getOnloadeddata() {
		return getEventFunction(this.onloadeddata, "onloadeddata");
	}

	@Override
	public Function getOnloadedmetadata() {
		return getEventFunction(this.onloadedmetadata, "onloadedmetadata");
	}

	@Override
	public Function getOnloadend() {
		return getEventFunction(this.onloadend, "onloadend");
	}

	@Override
	public Function getOnloadstart() {
		return getEventFunction(this.onloadstart, "onloadstart");
	}

	@Override
	public Function getOnlostpointercapture() {
		return getEventFunction(this.onlostpointercapture, "onlostpointercapture");
	}

	@Override
	public Function getOnmouseenter() {
		return getEventFunction(this.onmouseenter, "onmouseenter");
	}

	@Override
	public Function getOnmouseleave() {
		return getEventFunction(this.onmouseleave, "onmouseleave");
	}

	@Override
	public Function getOnpause() {
		return getEventFunction(this.onpause, "onpause");
	}

	@Override
	public Function getOnplay() {
		return getEventFunction(this.onplay, "onplay");
	}

	@Override
	public Function getOnplaying() {
		return getEventFunction(this.onplaying, "onplaying");
	}

	@Override
	public Function getOnpointercancel() {
		return getEventFunction(this.onpointercancel, "onpointercancel");
	}

	@Override
	public Function getOnpointerdown() {
		return getEventFunction(this.onpointerdown, "onpointerdown");
	}

	@Override
	public Function getOnpointerenter() {
		return getEventFunction(this.onpointerenter, "onpointerenter");
	}

	@Override
	public Function getOnpointerleave() {
		return getEventFunction(this.onpointerleave, "onpointerleave");
	}

	@Override
	public Function getOnpointermove() {
		return getEventFunction(this.onpointermove, "onpointermove");
	}

	@Override
	public Function getOnpointerout() {
		return getEventFunction(this.onpointerout, "onpointerout");
	}

	@Override
	public Function getOnpointerover() {
		return getEventFunction(this.onpointerover, "onpointerover");
	}

	@Override
	public Function getOnpointerup() {
		return getEventFunction(this.onpointerup, "onpointerup");
	}

	@Override
	public Function getOnprogress() {
		return getEventFunction(this.onprogress, "onprogress");
	}

	@Override
	public Function getOnratechange() {
		return getEventFunction(this.onratechange, "onratechange");
	}

	@Override
	public Function getOnreset() {
		return getEventFunction(this.onreset, "onreset");
	}

	@Override
	public Function getOnresize() {
		return getEventFunction(this.onresize, "onresize");
	}

	@Override
	public Function getOnscroll() {
		return getEventFunction(this.onscroll, "onscroll");
	}

	@Override
	public Function getOnsecuritypolicyviolation() {
		return getEventFunction(this.onsecuritypolicyviolation, "onsecuritypolicyviolation");
	}

	@Override
	public Function getOnseeked() {
		return getEventFunction(this.onseeked, "onseeked");
	}

	@Override
	public Function getOnseeking() {
		return getEventFunction(this.onseeking, "onseeking");
	}

	@Override
	public Function getOnselect() {
		return getEventFunction(this.onselect, "onselect");
	}

	@Override
	public Function getOnselectionchange() {
		return getEventFunction(this.onselectionchange, "onselectionchange");
	}

	@Override
	public Function getOnselectstart() {
		return getEventFunction(this.onselectstart, "onselectstart");
	}

	@Override
	public Function getOnstalled() {
		return getEventFunction(this.onstalled, "onstalled");
	}

	@Override
	public Function getOnsubmit() {
		return getEventFunction(this.onsubmit, "onsubmit");
	}

	@Override
	public Function getOnsuspend() {
		return getEventFunction(this.onsuspend, "onsuspend");
	}

	@Override
	public Function getOntimeupdate() {
		return getEventFunction(this.ontimeupdate, "ontimeupdate");
	}

	@Override
	public Function getOntoggle() {
		return getEventFunction(this.ontoggle, "ontoggle");
	}

	@Override
	public Function getOntouchcancel() {
		return getEventFunction(this.ontouchcancel, "ontouchcancel");
	}

	@Override
	public Function getOntouchend() {
		return getEventFunction(this.ontouchend, "ontouchend");
	}

	@Override
	public Function getOntouchmove() {
		return getEventFunction(this.ontouchmove, "ontouchmove");
	}

	@Override
	public Function getOntouchstart() {
		return getEventFunction(this.ontouchstart, "ontouchstart");
	}

	@Override
	public Function getOntransitioncancel() {
		return getEventFunction(this.ontransitioncancel, "ontransitioncancel");
	}

	@Override
	public Function getOntransitionend() {
		return getEventFunction(this.ontransitionend, "ontransitionend");
	}

	@Override
	public Function getOntransitionrun() {
		return getEventFunction(this.ontransitionrun, "ontransitionrun");
	}

	@Override
	public Function getOntransitionstart() {
		return getEventFunction(this.ontransitionstart, "ontransitionstart");
	}

	@Override
	public Function getOnvolumechange() {
		return getEventFunction(this.onvolumechange, "onvolumechange");
	}

	@Override
	public Function getOnwaiting() {
		return getEventFunction(this.onwaiting, "onwaiting");
	}

	@Override
	public Function getOnwheel() {
		return getEventFunction(this.onwheel, "onwheel");
	}

	/**
	 * @param functionByAttribute the functionByAttribute to set
	 */
	public void setFunctionByAttribute(Map<String, Function> functionByAttribute) {
		this.functionByAttribute = functionByAttribute;
	}

	/**
	 * @param onfocus the onfocus to set
	 */
	@Override
	public void setOnfocus(final Function onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * @param onblur the onblur to set
	 */
	@Override
	public void setOnblur(final Function onblur) {
		this.onblur = onblur;
	}

	/**
	 * @param onclick the onclick to set
	 */
	@Override
	public void setOnclick(final Function onclick) {
		this.onclick = onclick;
	}

	/**
	 * @param ondblclick the ondblclick to set
	 */
	@Override
	public void setOndblclick(final Function ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * @param onmousedown the onmousedown to set
	 */
	@Override
	public void setOnmousedown(final Function onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * @param onmouseup the onmouseup to set
	 */
	@Override
	public void setOnmouseup(final Function onmouseup) {
		this.onmouseup = onmouseup;
	}

	/**
	 * @param onmouseover the onmouseover to set
	 */
	@Override
	public void setOnmouseover(final Function onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * @param onmousemove the onmousemove to set
	 */
	@Override
	public void setOnmousemove(final Function onmousemove) {
		this.onmousemove = onmousemove;
	}

	/**
	 * @param onmouseout the onmouseout to set
	 */
	@Override
	public void setOnmouseout(final Function onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * @param onkeypress the onkeypress to set
	 */
	@Override
	public void setOnkeypress(final Function onkeypress) {
		this.onkeypress = onkeypress;
	}

	/**
	 * @param onkeydown the onkeydown to set
	 */
	@Override
	public void setOnkeydown(final Function onkeydown) {
		this.onkeydown = onkeydown;
	}

	/**
	 * @param onkeyup the onkeyup to set
	 */
	@Override
	public void setOnkeyup(final Function onkeyup) {
		this.onkeyup = onkeyup;
	}

	/**
	 * @param oncontextmenu the oncontextmenu to set
	 */
	@Override
	public void setOncontextmenu(final Function oncontextmenu) {
		this.oncontextmenu = oncontextmenu;
	}

	/**
	 * @param onchange the onchange to set
	 */
	@Override
	public void setOnchange(final Function onchange) {
		this.onchange = onchange;
	}

	/**
	 * @param onunload the onunload to set
	 */
	public void setOnunload(final Function onunload) {
	}

	/**
	 * @param onafterprint the onafterprint to set
	 */
	public void setOnafterprint(final Function onafterprint) {
	}

	/**
	 * @param onbeforeprint the onbeforeprint to set
	 */
	public void setOnbeforeprint(final Function onbeforeprint) {
	}

	/**
	 * @param onlanguagechange the onlanguagechange to set
	 */
	public void setOnlanguagechange(final Function onlanguagechange) {
	}

	/**
	 * @param ononline the ononline to set
	 */
	public void setOnonline(final Function ononline) {
	}

	/**
	 * @param onabort the onabort to set
	 */
	@Override
	public void setOnabort(final Function onabort) {
		this.onabort = onabort;
	}

	/**
	 * @param onwaiting the onwaiting to set
	 */
	@Override
	public void setOnwaiting(final Function onwaiting) {
		this.onwaiting = onwaiting;
	}

	/**
	 * @param onvolumechange the onvolumechange to set
	 */
	@Override
	public void setOnvolumechange(final Function onvolumechange) {
		this.onvolumechange = onvolumechange;
	}

	/**
	 * @param ontransitionstart the ontransitionstart to set
	 */
	@Override
	public void setOntransitionstart(final Function ontransitionstart) {
		this.ontransitionstart = ontransitionstart;
	}

	/**
	 * @param ontransitionrun the ontransitionrun to set
	 */
	@Override
	public void setOntransitionrun(final Function ontransitionrun) {
		this.ontransitionrun = ontransitionrun;
	}

	/**
	 * @param ontransitionend the ontransitionend to set
	 */
	@Override
	public void setOntransitionend(final Function ontransitionend) {
		this.ontransitionend = ontransitionend;
	}

	/**
	 * @param ontransitioncancel the ontransitioncancel to set
	 */
	@Override
	public void setOntransitioncancel(final Function ontransitioncancel) {
		this.ontransitioncancel = ontransitioncancel;
	}

	/**
	 * @param ontouchstart the ontouchstart to set
	 */
	@Override
	public void setOntouchstart(final Function ontouchstart) {
		this.ontouchstart = ontouchstart;
	}

	/**
	 * @param ontoggle the ontoggle to set
	 */
	@Override
	public void setOntoggle(final Function ontoggle) {
		this.ontoggle = ontoggle;
	}

	/**
	 * @param ontouchmove the ontouchmove to set
	 */
	@Override
	public void setOntouchmove(final Function ontouchmove) {
		this.ontouchmove = ontouchmove;
	}

	/**
	 * @param ontouchend the ontouchend to set
	 */
	@Override
	public void setOntouchend(final Function ontouchend) {
		this.ontouchend = ontouchend;
	}

	/**
	 * @param ontouchcancel the ontouchcancel to set
	 */
	@Override
	public void setOntouchcancel(final Function ontouchcancel) {
		this.ontouchcancel = ontouchcancel;
	}

	/**
	 * @param ontimeupdate the ontimeupdate to set
	 */
	@Override
	public void setOntimeupdate(final Function ontimeupdate) {
		this.ontimeupdate = ontimeupdate;
	}

	/**
	 * @param onsubmit the onsubmit to set
	 */
	@Override
	public void setOnsubmit(final Function onsubmit) {
		this.onsubmit = onsubmit;
	}

	/**
	 * @param onstalled the onstalled to set
	 */
	@Override
	public void setOnstalled(final Function onstalled) {
		this.onstalled = onstalled;
	}

	/**
	 * @param onselectstart the onselectstart to set
	 */
	@Override
	public void setOnselectstart(final Function onselectstart) {
		this.onselectstart = onselectstart;
	}

	/**
	 * @param onselectionchange the onselectionchange to set
	 */
	@Override
	public void setOnselectionchange(final Function onselectionchange) {
		this.onselectionchange = onselectionchange;
	}

	/**
	 * @param onselect the onselect to set
	 */
	@Override
	public void setOnselect(final Function onselect) {
		this.onselect = onselect;
	}

	/**
	 * @param onseeking the onseeking to set
	 */
	@Override
	public void setOnseeking(final Function onseeking) {
		this.onseeking = onseeking;
	}

	/**
	 * @param onseeked the onseeked to set
	 */
	@Override
	public void setOnseeked(final Function onseeked) {
		this.onseeked = onseeked;
	}

	/**
	 * @param onsecuritypolicyviolation the onsecuritypolicyviolation to set
	 */
	@Override
	public void setOnsecuritypolicyviolation(final Function onsecuritypolicyviolation) {
		this.onsecuritypolicyviolation = onsecuritypolicyviolation;
	}

	/**
	 * @param onscroll the onscroll to set
	 */
	@Override
	public void setOnscroll(final Function onscroll) {
		this.onscroll = onscroll;
	}

	/**
	 * @param onresize the onresize to set
	 */
	@Override
	public void setOnresize(final Function onresize) {
		this.onresize = onresize;
	}

	/**
	 * @param onreset the onreset to set
	 */
	@Override
	public void setOnreset(final Function onreset) {
		this.onreset = onreset;
	}

	/**
	 * @param onratechange the onratechange to set
	 */
	@Override
	public void setOnratechange(final Function onratechange) {
		this.onratechange = onratechange;
	}

	/**
	 * @param onprogress the onprogress to set
	 */
	@Override
	public void setOnprogress(final Function onprogress) {
		this.onprogress = onprogress;
	}

	/**
	 * @param onpointerup the onpointerup to set
	 */
	@Override
	public void setOnpointerup(final Function onpointerup) {
		this.onpointerup = onpointerup;
	}

	/**
	 * @param onpointerover the onpointerover to set
	 */
	@Override
	public void setOnpointerover(final Function onpointerover) {
		this.onpointerover = onpointerover;
	}

	/**
	 * @param onpointerout the onpointerout to set
	 */
	@Override
	public void setOnpointerout(final Function onpointerout) {
		this.onpointerout = onpointerout;
	}

	/**
	 * @param onwheel the onwheel to set
	 */
	@Override
	public void setOnwheel(final Function onwheel) {
		this.onwheel = onwheel;
	}

	/**
	 * @param onsuspend the onsuspend to set
	 */
	@Override
	public void setOnsuspend(final Function onsuspend) {
		this.onsuspend = onsuspend;
	}

	/**
	 * @param onpointermove the onpointermove to set
	 */
	@Override
	public void setOnpointermove(final Function onpointermove) {
		this.onpointermove = onpointermove;
	}

	/**
	 * @param onpointerleave the onpointerleave to set
	 */
	@Override
	public void setOnpointerleave(final Function onpointerleave) {
		this.onpointerleave = onpointerleave;
	}

	/**
	 * @param onpointerenter the onpointerenter to set
	 */
	@Override
	public void setOnpointerenter(final Function onpointerenter) {
		this.onpointerenter = onpointerenter;
	}

	/**
	 * @param onpointerdown the onpointerdown to set
	 */
	@Override
	public void setOnpointerdown(final Function onpointerdown) {
		this.onpointerdown = onpointerdown;
	}

	/**
	 * @param onpointercancel the onpointercancel to set
	 */
	@Override
	public void setOnpointercancel(final Function onpointercancel) {
		this.onpointercancel = onpointercancel;
	}

	/**
	 * @param onplaying the onplaying to set
	 */
	@Override
	public void setOnplaying(final Function onplaying) {
		this.onplaying = onplaying;
	}

	/**
	 * @param onplay the onplay to set
	 */
	@Override
	public void setOnplay(final Function onplay) {
		this.onplay = onplay;
	}

	/**
	 * @param onpause the onpause to set
	 */
	@Override
	public void setOnpause(final Function onpause) {
		this.onpause = onpause;
	}

	/**
	 * @param onmouseleave the onmouseleave to set
	 */
	@Override
	public void setOnmouseleave(final Function onmouseleave) {
		this.onmouseleave = onmouseleave;
	}

	/**
	 * @param onmouseenter the onmouseenter to set
	 */
	@Override
	public void setOnmouseenter(final Function onmouseenter) {
		this.onmouseenter = onmouseenter;
	}

	/**
	 * @param onauxclick the onauxclick to set
	 */
	@Override
	public void setOnauxclick(final Function onauxclick) {
		this.onauxclick = onauxclick;
	}

	/**
	 * @param onlostpointercapture the onlostpointercapture to set
	 */
	@Override
	public void setOnlostpointercapture(final Function onlostpointercapture) {
		this.onlostpointercapture = onlostpointercapture;
	}

	/**
	 * @param onloadstart the onloadstart to set
	 */
	@Override
	public void setOnloadstart(final Function onloadstart) {
		this.onloadstart = onloadstart;
	}

	/**
	 * @param onloadend the onloadend to set
	 */
	@Override
	public void setOnloadend(final Function onloadend) {
		this.onloadend = onloadend;
	}

	/**
	 * @param onloadedmetadata the onloadedmetadata to set
	 */
	@Override
	public void setOnloadedmetadata(final Function onloadedmetadata) {
		this.onloadedmetadata = onloadedmetadata;
	}

	/**
	 * @param onloadeddata the onloadeddata to set
	 */
	@Override
	public void setOnloadeddata(final Function onloadeddata) {
		this.onloadeddata = onloadeddata;
	}

	/**
	 * @param onload the onload to set
	 */
	@Override
	public void setOnload(final Function onload) {
		this.onload = onload;
	}

	/**
	 * @param oninvalid the oninvalid to set
	 */
	@Override
	public void setOninvalid(final Function oninvalid) {
		this.oninvalid = oninvalid;
	}

	/**
	 * @param oninput the oninput to set
	 */
	@Override
	public void setOninput(final Function oninput) {
		this.oninput = oninput;
	}

	/**
	 * @param ongotpointercapture the ongotpointercapture to set
	 */
	@Override
	public void setOngotpointercapture(final Function ongotpointercapture) {
		this.ongotpointercapture = ongotpointercapture;
	}

	/**
	 * @param onfocusout the onfocusout to set
	 */
	@Override
	public void setOnfocusout(final Function onfocusout) {
		this.onfocusout = onfocusout;
	}

	/**
	 * @param onfocusin the onfocusin to set
	 */
	@Override
	public void setOnfocusin(final Function onfocusin) {
		this.onfocusin = onfocusin;
	}

	/**
	 * @param onerror the onerror to set
	 */
	@Override
	public void setOnerror(final Function onerror) {
		this.onerror = onerror;
	}

	/**
	 * @param onended the onended to set
	 */
	@Override
	public void setOnended(final Function onended) {
		this.onended = onended;
	}

	/**
	 * @param onemptied the onemptied to set
	 */
	@Override
	public void setOnemptied(final Function onemptied) {
		this.onemptied = onemptied;
	}

	/**
	 * @param ondurationchange the ondurationchange to set
	 */
	@Override
	public void setOndurationchange(final Function ondurationchange) {
		this.ondurationchange = ondurationchange;
	}

	/**
	 * @param ondrop the ondrop to set
	 */
	@Override
	public void setOndrop(final Function ondrop) {
		this.ondrop = ondrop;
	}

	/**
	 * @param ondragstart the ondragstart to set
	 */
	@Override
	public void setOndragstart(final Function ondragstart) {
		this.ondragstart = ondragstart;
	}

	/**
	 * @param ondragover the ondragover to set
	 */
	@Override
	public void setOndragover(final Function ondragover) {
		this.ondragover = ondragover;
	}

	/**
	 * @param ondragleave the ondragleave to set
	 */
	@Override
	public void setOndragleave(final Function ondragleave) {
		this.ondragleave = ondragleave;
	}

	/**
	 * @param ondragexit the ondragexit to set
	 */
	@Override
	public void setOndragexit(final Function ondragexit) {
		this.ondragexit = ondragexit;
	}

	/**
	 * @param ondragenter the ondragenter to set
	 */
	@Override
	public void setOndragenter(final Function ondragenter) {
		this.ondragenter = ondragenter;
	}

	/**
	 * @param ondragend the ondragend to set
	 */
	@Override
	public void setOndragend(final Function ondragend) {
		this.ondragend = ondragend;
	}

	/**
	 * @param ondrag the ondrag to set
	 */
	@Override
	public void setOndrag(final Function ondrag) {
		this.ondrag = ondrag;
	}

	/**
	 * @param oncuechange the oncuechange to set
	 */
	@Override
	public void setOncuechange(final Function oncuechange) {
		this.oncuechange = oncuechange;
	}

	/**
	 * @param onclose the onclose to set
	 */
	@Override
	public void setOnclose(final Function onclose) {
		this.onclose = onclose;
	}

	/**
	 * @param oncanplaythrough the oncanplaythrough to set
	 */
	@Override
	public void setOncanplaythrough(final Function oncanplaythrough) {
		this.oncanplaythrough = oncanplaythrough;
	}

	/**
	 * @param oncanplay the oncanplay to set
	 */
	@Override
	public void setOncanplay(final Function oncanplay) {
		this.oncanplay = oncanplay;
	}

	/**
	 * @param onanimationstart the onanimationstart to set
	 */
	@Override
	public void setOnanimationstart(final Function onanimationstart) {
		this.onanimationstart = onanimationstart;
	}

	/**
	 * @param onanimationiteration the onanimationiteration to set
	 */
	@Override
	public void setOnanimationiteration(final Function onanimationiteration) {
		this.onanimationiteration = onanimationiteration;
	}

	/**
	 * @param onanimationend the onanimationend to set
	 */
	@Override
	public void setOnanimationend(final Function onanimationend) {
		this.onanimationend = onanimationend;
	}

	/**
	 * @param onanimationcancel the onanimationcancel to set
	 */
	@Override
	public void setOnanimationcancel(final Function onanimationcancel) {
		this.onanimationcancel = onanimationcancel;
	}

	/**
	 * @param oncancel the oncancel to set
	 */
	@Override
	public void setOncancel(final Function oncancel) {
		this.oncancel = oncancel;
	}
	
	public void setOnoffline(final Function onoffline) {

	}
	
	/** {@inheritDoc} */

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