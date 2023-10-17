/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import lombok.Data;
import org.loboevolution.html.dom.nodeimpl.event.EventTargetImpl;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.GlobalEventHandlers;
import org.loboevolution.html.node.js.WindowEventHandlers;
import org.mozilla.javascript.Function;
import org.w3c.dom.events.EventException;

/**
 * <p>WindowEventHandlersImpl class.</p>
 */
public class WindowEventHandlersImpl implements WindowEventHandlers, GlobalEventHandlers {

    private final EventTargetImpl eventTarget = new EventTargetImpl(null);


    @Override
    public void addEventListener(final String type, final Function listener) {
        addEventListener(type, listener, false);
    }

    @Override
    public void addEventListener(final String type, final Function listener, final boolean useCapture) {
        eventTarget.addEventListener(type, listener, useCapture);
    }

    @Override
    public void removeEventListener(final String type, final Function function) {
        addEventListener(type, function, false);
    }

    @Override
    public void removeEventListener(final String type, final Function listener, final boolean useCapture) {
        eventTarget.removeEventListener(type, listener, useCapture);
    }

    @Override
    public boolean dispatchEvent(final Node element, final Event evt) {
        return eventTarget.dispatchEvent(element, evt);
    }

    @Override
    public boolean dispatchEvent(final Event evt) throws EventException {
        return eventTarget.dispatchEvent(evt);
    }

    @Override
    public Function getOnafterprint() {
       return eventTarget.getFunction(this, "afterprint");
    }

    @Override
    public Function getOnbeforeprint() {
       return eventTarget.getFunction(this, "change");
    }

    @Override
    public Function getOnlanguagechange() {
       return eventTarget.getFunction(this, "change");
    }

    @Override
    public Function getOnoffline() {
       return eventTarget.getFunction(this, "change");
    }

    @Override
    public Function getOnonline() {
       return eventTarget.getFunction(this, "change");
    }

    @Override
    public Function getOnunload() {
       return eventTarget.getFunction(this, "change");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onblur.</p>
     */
    @Override
    public Function getOnblur() {
        return eventTarget.getFunction(this, "blur");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onclick.</p>
     */
    @Override
    public Function getOnclick() {
        return eventTarget.getFunction(this, "click");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field oncontextmenu.</p>
     */
    @Override
    public Function getOncontextmenu() {
        return eventTarget.getFunction(this, "contextmenu");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field ondblclick.</p>
     */
    @Override
    public Function getOndblclick() {
        return eventTarget.getFunction(this, "dblclick");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onfocus.</p>
     */
    @Override
    public Function getOnfocus() {
        return eventTarget.getFunction(this, "focus");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onkeydown.</p>
     */
    @Override
    public Function getOnkeydown() {
        return eventTarget.getFunction(this, "keydown");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onkeypress.</p>
     */
    @Override
    public Function getOnkeypress() {
        return eventTarget.getFunction(this, "keypress");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onkeyup.</p>
     */
    @Override
    public Function getOnkeyup() {
        return eventTarget.getFunction(this, "keyup");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onmousedown.</p>
     */
    @Override
    public Function getOnmousedown() {
        return eventTarget.getFunction(this, "mousedown");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onmousemove.</p>
     */
    @Override
    public Function getOnmousemove() {
        return eventTarget.getFunction(this, "mousemove");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onmouseout.</p>
     */
    @Override
    public Function getOnmouseout() {
        return eventTarget.getFunction(this, "mouseout");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onmouseover.</p>
     */
    @Override
    public Function getOnmouseover() {
        return eventTarget.getFunction(this, "mouseover");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onmouseup.</p>
     */
    @Override
    public Function getOnmouseup() {
        return eventTarget.getFunction(this, "mouseup");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Getter for the field onchange.</p>
     */
    @Override
    public Function getOnchange() {
        return eventTarget.getFunction(this, "change");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnabort() {
        return eventTarget.getFunction(this, "abort");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnanimationcancel() {
        return eventTarget.getFunction(this, "animationcancel");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnanimationend() {
        return eventTarget.getFunction(this, "animationend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnanimationiteration() {
        return eventTarget.getFunction(this, "animationiteration");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnanimationstart() {
        return eventTarget.getFunction(this, "animationstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnauxclick() {
        return eventTarget.getFunction(this, "auxclick");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOncancel() {
        return eventTarget.getFunction(this, "cancel");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOncanplay() {

        return eventTarget.getFunction(this, "canplay");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOncanplaythrough() {
        return eventTarget.getFunction(this, "canplaythrough");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnclose() {
        return eventTarget.getFunction(this, "close");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOncuechange() {
        return eventTarget.getFunction(this, "cuechange");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndrag() {
        return eventTarget.getFunction(this, "drag");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragend() {
        return eventTarget.getFunction(this, "dragend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragenter() {
        return eventTarget.getFunction(this, "dragenter");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragexit() {
        return eventTarget.getFunction(this, "dragexit");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragleave() {
        return eventTarget.getFunction(this, "dragleave");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragover() {
        return eventTarget.getFunction(this, "dragover");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndragstart() {
        return eventTarget.getFunction(this, "dragstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndrop() {
        return eventTarget.getFunction(this, "drop");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOndurationchange() {
        return eventTarget.getFunction(this, "durationchange");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnemptied() {
        return eventTarget.getFunction(this, "emptied");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnended() {
        return eventTarget.getFunction(this, "ended");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnerror() {
        return eventTarget.getFunction(this, "error");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnfocusin() {
        return eventTarget.getFunction(this, "focusin");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnfocusout() {
        return eventTarget.getFunction(this, "focusout");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOngotpointercapture() {
        return eventTarget.getFunction(this, "gotpointercapture");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOninput() {
        return eventTarget.getFunction(this, "input");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOninvalid() {
        return eventTarget.getFunction(this, "invalid");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnload() {
        return eventTarget.getFunction(this, "load");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnloadeddata() {
        return eventTarget.getFunction(this, "loadeddata");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnloadedmetadata() {
        return eventTarget.getFunction(this, "loadedmetadata");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnloadend() {
        return eventTarget.getFunction(this, "loadend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnloadstart() {
        return eventTarget.getFunction(this, "loadstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnlostpointercapture() {
        return eventTarget.getFunction(this, "lostpointercapture");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnmouseenter() {
        return eventTarget.getFunction(this, "mouseenter");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnmouseleave() {
        return eventTarget.getFunction(this, "mouseleave");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpause() {
        return eventTarget.getFunction(this, "pause");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnplay() {
        return eventTarget.getFunction(this, "play");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnplaying() {
        return eventTarget.getFunction(this, "playing");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointercancel() {
        return eventTarget.getFunction(this, "pointercancel");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerdown() {
        return eventTarget.getFunction(this, "pointerdown");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerenter() {
        return eventTarget.getFunction(this, "pointerenter");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerleave() {
        return eventTarget.getFunction(this, "pointerleave");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointermove() {
        return eventTarget.getFunction(this, "pointermove");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerout() {
        return eventTarget.getFunction(this, "pointerout");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerover() {
        return eventTarget.getFunction(this, "pointerover");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnpointerup() {
        return eventTarget.getFunction(this, "pointerup");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnprogress() {
        return eventTarget.getFunction(this, "progress");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnratechange() {
        return eventTarget.getFunction(this, "ratechange");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnreset() {
        return eventTarget.getFunction(this, "reset");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnresize() {
        return eventTarget.getFunction(this, "resize");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnscroll() {
        return eventTarget.getFunction(this, "scroll");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnsecuritypolicyviolation() {
        return eventTarget.getFunction(this, "securitypolicyviolation");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnseeked() {
        return eventTarget.getFunction(this, "seeked");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnseeking() {
        return eventTarget.getFunction(this, "seeking");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnselect() {
        return eventTarget.getFunction(this, "select");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnselectionchange() {
        return eventTarget.getFunction(this, "selectionchange");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnselectstart() {
        return eventTarget.getFunction(this, "selectstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnstalled() {
        return eventTarget.getFunction(this, "stalled");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnsubmit() {
        return eventTarget.getFunction(this, "submit");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnsuspend() {
        return eventTarget.getFunction(this, "suspend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntimeupdate() {
        return eventTarget.getFunction(this, "timeupdate");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntoggle() {
        return eventTarget.getFunction(this, "toggle");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntouchcancel() {
        return eventTarget.getFunction(this, "touchcancel");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntouchend() {
        return eventTarget.getFunction(this, "touchend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntouchmove() {
        return eventTarget.getFunction(this, "touchmove");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntouchstart() {
        return eventTarget.getFunction(this, "touchstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntransitioncancel() {
        return eventTarget.getFunction(this, "transitioncancel");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntransitionend() {
        return eventTarget.getFunction(this, "transitionend");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntransitionrun() {
        return eventTarget.getFunction(this, "transitionrun");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOntransitionstart() {
        return eventTarget.getFunction(this, "transitionstart");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnvolumechange() {
        return eventTarget.getFunction(this, "volumechange");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnwaiting() {
        return eventTarget.getFunction(this, "waiting");
    }

    /** {@inheritDoc} */
    @Override
    public Function getOnwheel() {
        return eventTarget.getFunction(this, "wheel");
    }

    /** {@inheritDoc} */
    @Override
    public void setOnfocus(final Function onfocus) {
        eventTarget.addEventListener("focus", onfocus);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnblur(final Function onblur) {
        eventTarget.addEventListener("blur", onblur);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnclick(final Function onclick) {
        eventTarget.addEventListener("click", onclick);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndblclick(final Function ondblclick) {
        eventTarget.addEventListener("dblclick", ondblclick);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmousedown(final Function onmousedown) {
        eventTarget.addEventListener("mousedown", onmousedown);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmouseup(final Function onmouseup) {
        eventTarget.addEventListener("mouseup", onmouseup);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmouseover(final Function onmouseover) {
        eventTarget.addEventListener("mouseover", onmouseover);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmousemove(final Function onmousemove) {
        eventTarget.addEventListener("mousemove", onmousemove);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmouseout(final Function onmouseout) {
        eventTarget.addEventListener("mouseout", onmouseout);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnkeypress(final Function onkeypress) {
        eventTarget.addEventListener("keypress", onkeypress);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnkeydown(final Function onkeydown) {
        eventTarget.addEventListener("keydown", onkeydown);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnkeyup(final Function onkeyup) {
        eventTarget.addEventListener("keyup", onkeyup);
    }

    /** {@inheritDoc} */
    @Override
    public void setOncontextmenu(final Function oncontextmenu) {
        eventTarget.addEventListener("contextmenu", oncontextmenu);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnchange(final Function onchange) {
        eventTarget.addEventListener("change", onchange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnabort(final Function onabort) {
        eventTarget.addEventListener("abort", onabort);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnwaiting(final Function onwaiting) {
        eventTarget.addEventListener("waiting", onwaiting);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnvolumechange(final Function onvolumechange) {
        eventTarget.addEventListener("volumechange", onvolumechange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntransitionstart(final Function ontransitionstart) {
        eventTarget.addEventListener("transitionstart", ontransitionstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntransitionrun(final Function ontransitionrun) {
        eventTarget.addEventListener("transitionrun", ontransitionrun);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntransitionend(final Function ontransitionend) {
        eventTarget.addEventListener("transitionend", ontransitionend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntransitioncancel(final Function ontransitioncancel) {
        eventTarget.addEventListener("transitioncancel", ontransitioncancel);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntouchstart(final Function ontouchstart) {
        eventTarget.addEventListener("touchstart", ontouchstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntoggle(final Function ontoggle) {
        eventTarget.addEventListener("toggle", ontoggle);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntouchmove(final Function ontouchmove) {
        eventTarget.addEventListener("touchmove", ontouchmove);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntouchend(final Function ontouchend) {
        eventTarget.addEventListener("touchend", ontouchend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntouchcancel(final Function ontouchcancel) {
        eventTarget.addEventListener("touchcancel", ontouchcancel);
    }

    /** {@inheritDoc} */
    @Override
    public void setOntimeupdate(final Function ontimeupdate) {
        eventTarget.addEventListener("timeupdate", ontimeupdate);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnsubmit(final Function onsubmit) {
        eventTarget.addEventListener("submit", onsubmit);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnstalled(final Function onstalled) {
        eventTarget.addEventListener("stalled", onstalled);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnselectstart(final Function onselectstart) {
        eventTarget.addEventListener("selectstart", onselectstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnselectionchange(final Function onselectionchange) {
        eventTarget.addEventListener("selectionchange", onselectionchange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnselect(final Function onselect) {
        eventTarget.addEventListener("select", onselect);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnseeking(final Function onseeking) {
        eventTarget.addEventListener("seeking", onseeking);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnseeked(final Function onseeked) {
        eventTarget.addEventListener("seeked", onseeked);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnsecuritypolicyviolation(final Function onsecuritypolicyviolation) {
        eventTarget.addEventListener("securitypolicyviolation", onsecuritypolicyviolation);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnscroll(final Function onscroll) {
        eventTarget.addEventListener("scroll", onscroll);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnresize(final Function onresize) {
        eventTarget.addEventListener("resize", onresize);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnreset(final Function onreset) {
        eventTarget.addEventListener("reset", onreset);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnratechange(final Function onratechange) {
        eventTarget.addEventListener("ratechange", onratechange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnprogress(final Function onprogress) {
        eventTarget.addEventListener("progress", onprogress);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerup(final Function onpointerup) {
        eventTarget.addEventListener("pointerup", onpointerup);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerover(final Function onpointerover) {
        eventTarget.addEventListener("pointerover", onpointerover);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerout(final Function onpointerout) {
        eventTarget.addEventListener("pointerout", onpointerout);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnwheel(final Function onwheel) {
        eventTarget.addEventListener("wheel", onwheel);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnsuspend(final Function onsuspend) {
        eventTarget.addEventListener("suspend", onsuspend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointermove(final Function onpointermove) {
        eventTarget.addEventListener("pointermove", onpointermove);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerleave(final Function onpointerleave) {
        eventTarget.addEventListener("pointerleave", onpointerleave);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerenter(final Function onpointerenter) {
        eventTarget.addEventListener("pointerenter", onpointerenter);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointerdown(final Function onpointerdown) {
        eventTarget.addEventListener("pointerdown", onpointerdown);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpointercancel(final Function onpointercancel) {
        eventTarget.addEventListener("pointercancel", onpointercancel);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnplaying(final Function onplaying) {
        eventTarget.addEventListener("playing", onplaying);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnplay(final Function onplay) {
        eventTarget.addEventListener("play", onplay);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnpause(final Function onpause) {
        eventTarget.addEventListener("pause", onpause);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmouseleave(final Function onmouseleave) {
        eventTarget.addEventListener("mouseleave", onmouseleave);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnmouseenter(final Function onmouseenter) {
        eventTarget.addEventListener("mouseenter", onmouseenter);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnauxclick(final Function onauxclick) {
        eventTarget.addEventListener("auxclick", onauxclick);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnlostpointercapture(final Function onlostpointercapture) {
        eventTarget.addEventListener("lostpointercapture", onlostpointercapture);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnloadstart(final Function onloadstart) {
        eventTarget.addEventListener("loadstart", onloadstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnloadend(final Function onloadend) {
        eventTarget.addEventListener("loadend", onloadend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnloadedmetadata(final Function onloadedmetadata) {
        eventTarget.addEventListener("loadedmetadata", onloadedmetadata);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnloadeddata(final Function onloadeddata) {
        eventTarget.addEventListener("loadeddata", onloadeddata);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnload(final Function onload) {
        eventTarget.addEventListener("load", onload);
    }

    /** {@inheritDoc} */
    @Override
    public void setOninvalid(final Function oninvalid) {
        eventTarget.addEventListener("invalid", oninvalid);
    }

    /** {@inheritDoc} */
    @Override
    public void setOninput(final Function oninput) {
        eventTarget.addEventListener("input", oninput);
    }

    /** {@inheritDoc} */
    @Override
    public void setOngotpointercapture(final Function ongotpointercapture) {
        eventTarget.addEventListener("gotpointercapture", ongotpointercapture);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnfocusout(final Function onfocusout) {
        eventTarget.addEventListener("focusout", onfocusout);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnfocusin(final Function onfocusin) {
        eventTarget.addEventListener("focusin", onfocusin);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnerror(final Function onerror) {
        eventTarget.addEventListener("error", onerror);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnended(final Function onended) {
        eventTarget.addEventListener("ended", onended);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnemptied(final Function onemptied) {
        eventTarget.addEventListener("emptied", onemptied);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndurationchange(final Function ondurationchange) {
        eventTarget.addEventListener("durationchange", ondurationchange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndrop(final Function ondrop) {
        eventTarget.addEventListener("drop", ondrop);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragstart(final Function ondragstart) {
        eventTarget.addEventListener("dragstart", ondragstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragover(final Function ondragover) {
        eventTarget.addEventListener("dragover", ondragover);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragleave(final Function ondragleave) {
        eventTarget.addEventListener("dragleave", ondragleave);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragexit(final Function ondragexit) {
        eventTarget.addEventListener("dragexit", ondragexit);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragenter(final Function ondragenter) {
        eventTarget.addEventListener("dragenter", ondragenter);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndragend(final Function ondragend) {
        eventTarget.addEventListener("dragend", ondragend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOndrag(final Function ondrag) {
        eventTarget.addEventListener("drag", ondrag);
    }

    /** {@inheritDoc} */
    @Override
    public void setOncuechange(final Function oncuechange) {
        eventTarget.addEventListener("cuechange", oncuechange);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnclose(final Function onclose) {
        eventTarget.addEventListener("close", onclose);
    }

    /** {@inheritDoc} */
    @Override
    public void setOncanplaythrough(final Function oncanplaythrough) {
        eventTarget.addEventListener("canplaythrough", oncanplaythrough);
    }

    /** {@inheritDoc} */
    @Override
    public void setOncanplay(final Function oncanplay) {
        eventTarget.addEventListener("canplay", oncanplay);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnanimationstart(final Function onanimationstart) {
        eventTarget.addEventListener("animationstart", onanimationstart);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnanimationiteration(final Function onanimationiteration) {
        eventTarget.addEventListener("animationiteration", onanimationiteration);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnanimationend(final Function onanimationend) {
        eventTarget.addEventListener("animationend", onanimationend);
    }

    /** {@inheritDoc} */
    @Override
    public void setOnanimationcancel(final Function onanimationcancel) {
        eventTarget.addEventListener("animationcancel", onanimationcancel);
    }

    /** {@inheritDoc} */
    @Override
    public void setOncancel(final Function oncancel) {
        eventTarget.addEventListener("cancel", oncancel);
    }

    @Override
    public void setOnunload(final Function onunload) {
        eventTarget.addEventListener("unload", onunload, false);
    }

    @Override
    public void setOnonline(final Function ononline) {
        eventTarget.addEventListener("online", ononline, false);
    }

    @Override
    public void setOnoffline(final Function onoffline) {
        eventTarget.addEventListener("offline", onoffline, false);
    }

    @Override
    public void setOnlanguagechange(final Function onlanguagechange) {
        eventTarget.addEventListener("languagechange", onlanguagechange, false);
    }

    @Override
    public void setOnbeforeprint(final Function onbeforeprint) {
        eventTarget.addEventListener("beforeprint", onbeforeprint, false);
    }

    @Override
    public void setOnafterprint(final Function onafterprint) {
        eventTarget.addEventListener("afterprint", onafterprint, false);
    }
}
