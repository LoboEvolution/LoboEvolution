/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.nodeimpl.event.EventTargetImpl;
import org.loboevolution.events.GlobalEventHandlers;
import org.loboevolution.js.WindowEventHandlers;
import org.mozilla.javascript.Function;

/**
 * <p>WindowEventHandlersImpl class.</p>
 */
public class WindowEventHandlersImpl extends EventTargetImpl implements WindowEventHandlers, GlobalEventHandlers {


    @Override
    public void addEventListener(final String type, final Function listener) {
        addEventListener(type, listener, false);
    }

    @Override
    public Function getOnafterprint() {
       return getFunction(this, "afterprint");
    }

    @Override
    public Function getOnbeforeprint() {
       return getFunction(this, "change");
    }

    @Override
    public Function getOnlanguagechange() {
       return getFunction(this, "change");
    }

    @Override
    public Function getOnoffline() {
       return getFunction(this, "change");
    }

    @Override
    public Function getOnonline() {
       return getFunction(this, "change");
    }

    @Override
    public Function getOnunload() {
       return getFunction(this, "change");
    }

    @Override
    public Function getOnfullscreenchange() {
        return getFunction(this, "fullscreenchange");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onblur.</p>
     */
    @Override
    public Function getOnblur() {
        return getFunction(this, "blur");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onclick.</p>
     */
    @Override
    public Function getOnclick() {
        return getFunction(this, "click");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field oncontextmenu.</p>
     */
    @Override
    public Function getOncontextmenu() {
        return getFunction(this, "contextmenu");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field ondblclick.</p>
     */
    @Override
    public Function getOndblclick() {
        return getFunction(this, "dblclick");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onfocus.</p>
     */
    @Override
    public Function getOnfocus() {
        return getFunction(this, "focus");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onkeydown.</p>
     */
    @Override
    public Function getOnkeydown() {
        return getFunction(this, "keydown");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onkeypress.</p>
     */
    @Override
    public Function getOnkeypress() {
        return getFunction(this, "keypress");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onkeyup.</p>
     */
    @Override
    public Function getOnkeyup() {
        return getFunction(this, "keyup");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onmousedown.</p>
     */
    @Override
    public Function getOnmousedown() {
        return getFunction(this, "mousedown");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onmousemove.</p>
     */
    @Override
    public Function getOnmousemove() {
        return getFunction(this, "mousemove");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onmouseout.</p>
     */
    @Override
    public Function getOnmouseout() {
        return getFunction(this, "mouseout");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onmouseover.</p>
     */
    @Override
    public Function getOnmouseover() {
        return getFunction(this, "mouseover");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onmouseup.</p>
     */
    @Override
    public Function getOnmouseup() {
        return getFunction(this, "mouseup");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onchange.</p>
     */
    @Override
    public Function getOnchange() {
        return getFunction(this, "change");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnabort() {
        return getFunction(this, "abort");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnanimationcancel() {
        return getFunction(this, "animationcancel");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnanimationend() {
        return getFunction(this, "animationend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnanimationiteration() {
        return getFunction(this, "animationiteration");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnanimationstart() {
        return getFunction(this, "animationstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnauxclick() {
        return getFunction(this, "auxclick");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOncancel() {
        return getFunction(this, "cancel");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOncanplay() {

        return getFunction(this, "canplay");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOncanplaythrough() {
        return getFunction(this, "canplaythrough");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnclose() {
        return getFunction(this, "close");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOncuechange() {
        return getFunction(this, "cuechange");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndrag() {
        return getFunction(this, "drag");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragend() {
        return getFunction(this, "dragend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragenter() {
        return getFunction(this, "dragenter");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragexit() {
        return getFunction(this, "dragexit");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragleave() {
        return getFunction(this, "dragleave");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragover() {
        return getFunction(this, "dragover");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragstart() {
        return getFunction(this, "dragstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndrop() {
        return getFunction(this, "drop");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndurationchange() {
        return getFunction(this, "durationchange");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnemptied() {
        return getFunction(this, "emptied");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnended() {
        return getFunction(this, "ended");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnerror() {
        return getFunction(this, "error");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnfocusin() {
        return getFunction(this, "focusin");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnfocusout() {
        return getFunction(this, "focusout");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOngotpointercapture() {
        return getFunction(this, "gotpointercapture");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOninput() {
        return getFunction(this, "input");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOninvalid() {
        return getFunction(this, "invalid");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnload() {
        return getFunction(this, "load");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnloadeddata() {
        return getFunction(this, "loadeddata");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnloadedmetadata() {
        return getFunction(this, "loadedmetadata");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnloadend() {
        return getFunction(this, "loadend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnloadstart() {
        return getFunction(this, "loadstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnlostpointercapture() {
        return getFunction(this, "lostpointercapture");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnmouseenter() {
        return getFunction(this, "mouseenter");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnmouseleave() {
        return getFunction(this, "mouseleave");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpause() {
        return getFunction(this, "pause");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnplay() {
        return getFunction(this, "play");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnplaying() {
        return getFunction(this, "playing");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointercancel() {
        return getFunction(this, "pointercancel");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerdown() {
        return getFunction(this, "pointerdown");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerenter() {
        return getFunction(this, "pointerenter");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerleave() {
        return getFunction(this, "pointerleave");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointermove() {
        return getFunction(this, "pointermove");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerout() {
        return getFunction(this, "pointerout");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerover() {
        return getFunction(this, "pointerover");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerup() {
        return getFunction(this, "pointerup");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnprogress() {
        return getFunction(this, "progress");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnratechange() {
        return getFunction(this, "ratechange");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnreset() {
        return getFunction(this, "reset");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnresize() {
        return getFunction(this, "resize");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnscroll() {
        return getFunction(this, "scroll");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnsecuritypolicyviolation() {
        return getFunction(this, "securitypolicyviolation");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnseeked() {
        return getFunction(this, "seeked");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnseeking() {
        return getFunction(this, "seeking");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnselect() {
        return getFunction(this, "select");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnselectionchange() {
        return getFunction(this, "selectionchange");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnselectstart() {
        return getFunction(this, "selectstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnstalled() {
        return getFunction(this, "stalled");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnsubmit() {
        return getFunction(this, "submit");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnsuspend() {
        return getFunction(this, "suspend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntimeupdate() {
        return getFunction(this, "timeupdate");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntoggle() {
        return getFunction(this, "toggle");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntouchcancel() {
        return getFunction(this, "touchcancel");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntouchend() {
        return getFunction(this, "touchend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntouchmove() {
        return getFunction(this, "touchmove");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntouchstart() {
        return getFunction(this, "touchstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntransitioncancel() {
        return getFunction(this, "transitioncancel");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntransitionend() {
        return getFunction(this, "transitionend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntransitionrun() {
        return getFunction(this, "transitionrun");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntransitionstart() {
        return getFunction(this, "transitionstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnvolumechange() {
        return getFunction(this, "volumechange");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnwaiting() {
        return getFunction(this, "waiting");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnwheel() {
        return getFunction(this, "wheel");
    }

    /** {@inheritDoc} */
    @Override
    public void setOnfocus(final Function onfocus) {
        addEventListener("focus", onfocus);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnblur(final Function onblur) {
        addEventListener("blur", onblur);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnclick(final Function onclick) {
        addEventListener("click", onclick);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndblclick(final Function ondblclick) {
        addEventListener("dblclick", ondblclick);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmousedown(final Function onmousedown) {
        addEventListener("mousedown", onmousedown);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmouseup(final Function onmouseup) {
        addEventListener("mouseup", onmouseup);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmouseover(final Function onmouseover) {
        addEventListener("mouseover", onmouseover);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmousemove(final Function onmousemove) {
        addEventListener("mousemove", onmousemove);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmouseout(final Function onmouseout) {
        addEventListener("mouseout", onmouseout);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnkeypress(final Function onkeypress) {
        addEventListener("keypress", onkeypress);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnkeydown(final Function onkeydown) {
        addEventListener("keydown", onkeydown);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnkeyup(final Function onkeyup) {
        addEventListener("keyup", onkeyup);
    }

    /** {@inheritDoc} */
    @Override
    public void setOncontextmenu(final Function oncontextmenu) {
        addEventListener("contextmenu", oncontextmenu);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnchange(final Function onchange) {
        addEventListener("change", onchange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnabort(final Function onabort) {
        addEventListener("abort", onabort);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnwaiting(final Function onwaiting) {
        addEventListener("waiting", onwaiting);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnvolumechange(final Function onvolumechange) {
        addEventListener("volumechange", onvolumechange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntransitionstart(final Function ontransitionstart) {
        addEventListener("transitionstart", ontransitionstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntransitionrun(final Function ontransitionrun) {
        addEventListener("transitionrun", ontransitionrun);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntransitionend(final Function ontransitionend) {
        addEventListener("transitionend", ontransitionend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntransitioncancel(final Function ontransitioncancel) {
        addEventListener("transitioncancel", ontransitioncancel);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntouchstart(final Function ontouchstart) {
        addEventListener("touchstart", ontouchstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntoggle(final Function ontoggle) {
        addEventListener("toggle", ontoggle);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntouchmove(final Function ontouchmove) {
        addEventListener("touchmove", ontouchmove);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntouchend(final Function ontouchend) {
        addEventListener("touchend", ontouchend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntouchcancel(final Function ontouchcancel) {
        addEventListener("touchcancel", ontouchcancel);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntimeupdate(final Function ontimeupdate) {
        addEventListener("timeupdate", ontimeupdate);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnsubmit(final Function onsubmit) {
        addEventListener("submit", onsubmit);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnstalled(final Function onstalled) {
        addEventListener("stalled", onstalled);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnselectstart(final Function onselectstart) {
        addEventListener("selectstart", onselectstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnselectionchange(final Function onselectionchange) {
        addEventListener("selectionchange", onselectionchange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnselect(final Function onselect) {
        addEventListener("select", onselect);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnseeking(final Function onseeking) {
        addEventListener("seeking", onseeking);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnseeked(final Function onseeked) {
        addEventListener("seeked", onseeked);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnsecuritypolicyviolation(final Function onsecuritypolicyviolation) {
        addEventListener("securitypolicyviolation", onsecuritypolicyviolation);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnscroll(final Function onscroll) {
        addEventListener("scroll", onscroll);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnresize(final Function onresize) {
        addEventListener("resize", onresize);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnreset(final Function onreset) {
        addEventListener("reset", onreset);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnratechange(final Function onratechange) {
        addEventListener("ratechange", onratechange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnprogress(final Function onprogress) {
        addEventListener("progress", onprogress);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerup(final Function onpointerup) {
        addEventListener("pointerup", onpointerup);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerover(final Function onpointerover) {
        addEventListener("pointerover", onpointerover);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerout(final Function onpointerout) {
        addEventListener("pointerout", onpointerout);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnwheel(final Function onwheel) {
        addEventListener("wheel", onwheel);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnsuspend(final Function onsuspend) {
        addEventListener("suspend", onsuspend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointermove(final Function onpointermove) {
        addEventListener("pointermove", onpointermove);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerleave(final Function onpointerleave) {
        addEventListener("pointerleave", onpointerleave);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerenter(final Function onpointerenter) {
        addEventListener("pointerenter", onpointerenter);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerdown(final Function onpointerdown) {
        addEventListener("pointerdown", onpointerdown);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointercancel(final Function onpointercancel) {
        addEventListener("pointercancel", onpointercancel);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnplaying(final Function onplaying) {
        addEventListener("playing", onplaying);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnplay(final Function onplay) {
        addEventListener("play", onplay);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpause(final Function onpause) {
        addEventListener("pause", onpause);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmouseleave(final Function onmouseleave) {
        addEventListener("mouseleave", onmouseleave);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmouseenter(final Function onmouseenter) {
        addEventListener("mouseenter", onmouseenter);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnauxclick(final Function onauxclick) {
        addEventListener("auxclick", onauxclick);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnlostpointercapture(final Function onlostpointercapture) {
        addEventListener("lostpointercapture", onlostpointercapture);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnloadstart(final Function onloadstart) {
        addEventListener("loadstart", onloadstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnloadend(final Function onloadend) {
        addEventListener("loadend", onloadend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnloadedmetadata(final Function onloadedmetadata) {
        addEventListener("loadedmetadata", onloadedmetadata);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnloadeddata(final Function onloadeddata) {
        addEventListener("loadeddata", onloadeddata);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnload(final Function onload) {
        addEventListener("load", onload);
    }

    /** {@inheritDoc} */
    @Override
    public void setOninvalid(final Function oninvalid) {
        addEventListener("invalid", oninvalid);
    }



    /** {@inheritDoc} */
    @Override
    public void setOninput(final Function oninput) {
        addEventListener("input", oninput);
    }

    /** {@inheritDoc} */
    @Override
    public void setOngotpointercapture(final Function ongotpointercapture) {
        addEventListener("gotpointercapture", ongotpointercapture);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnfocusout(final Function onfocusout) {
        addEventListener("focusout", onfocusout);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnfocusin(final Function onfocusin) {
        addEventListener("focusin", onfocusin);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnerror(final Function onerror) {
        addEventListener("error", onerror);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnended(final Function onended) {
        addEventListener("ended", onended);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnemptied(final Function onemptied) {
        addEventListener("emptied", onemptied);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndurationchange(final Function ondurationchange) {
        addEventListener("durationchange", ondurationchange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndrop(final Function ondrop) {
        addEventListener("drop", ondrop);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragstart(final Function ondragstart) {
        addEventListener("dragstart", ondragstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragover(final Function ondragover) {
        addEventListener("dragover", ondragover);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragleave(final Function ondragleave) {
        addEventListener("dragleave", ondragleave);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragexit(final Function ondragexit) {
        addEventListener("dragexit", ondragexit);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragenter(final Function ondragenter) {
        addEventListener("dragenter", ondragenter);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragend(final Function ondragend) {
        addEventListener("dragend", ondragend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndrag(final Function ondrag) {
        addEventListener("drag", ondrag);
    }

    /** {@inheritDoc} */
    @Override
    public void setOncuechange(final Function oncuechange) {
        addEventListener("cuechange", oncuechange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnclose(final Function onclose) {
        addEventListener("close", onclose);
    }

    /** {@inheritDoc} */
    @Override
    public void setOncanplaythrough(final Function oncanplaythrough) {
        addEventListener("canplaythrough", oncanplaythrough);
    }

    /** {@inheritDoc} */
    @Override
    public void setOncanplay(final Function oncanplay) {
        addEventListener("canplay", oncanplay);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnanimationstart(final Function onanimationstart) {
        addEventListener("animationstart", onanimationstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnanimationiteration(final Function onanimationiteration) {
        addEventListener("animationiteration", onanimationiteration);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnanimationend(final Function onanimationend) {
        addEventListener("animationend", onanimationend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnanimationcancel(final Function onanimationcancel) {
        addEventListener("animationcancel", onanimationcancel);
    }

    /** {@inheritDoc} */
    @Override
    public void setOncancel(final Function oncancel) {
        addEventListener("cancel", oncancel);
    }

    @Override
    public void setOnunload(final Function onunload) {
        addEventListener("unload", onunload, false);
    }

    @Override
    public void setOnonline(final Function ononline) {
        addEventListener("online", ononline, false);
    }

    @Override
    public void setOnoffline(final Function onoffline) {
        addEventListener("offline", onoffline, false);
    }

    @Override
    public void setOnlanguagechange(final Function onlanguagechange) {
        addEventListener("languagechange", onlanguagechange, false);
    }

    @Override
    public void setOnbeforeprint(final Function onbeforeprint) {
        addEventListener("beforeprint", onbeforeprint, false);
    }

    @Override
    public void setOnafterprint(final Function onafterprint) {
        addEventListener("afterprint", onafterprint, false);
    }
}
