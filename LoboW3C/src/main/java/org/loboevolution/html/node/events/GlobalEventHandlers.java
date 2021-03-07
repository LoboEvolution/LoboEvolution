package org.loboevolution.html.node.events;

import org.mozilla.javascript.Function;

/**
 * The interface Global event handlers.
 */
public interface GlobalEventHandlers extends EventTarget {
	/**
	 * Fires when the user aborts the download.
	 *
	 * @return the onabort
	 */

	Function getOnabort();

	/**
	 * Sets onabort.
	 *
	 * @param onabort the onabort
	 */

	void setOnabort(Function onabort);

	/**
	 * Add abort event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addAbortEventListener(Function listener, boolean options) {
		addEventListener("abort", listener, options);
	}

	/**
	 * Add abort event listener.
	 *
	 * @param listener the listener
	 */
	default void addAbortEventListener(Function listener) {
		addEventListener("abort", listener);
	}

	/**
	 * Remove abort event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeAbortEventListener(Function listener, boolean options) {
		removeEventListener("abort", listener, options);
	}

	/**
	 * Remove abort event listener.
	 *
	 * @param listener the listener
	 */
	default void removeAbortEventListener(Function listener) {
		removeEventListener("abort", listener);
	}

	/**
	 * Gets onanimationcancel.
	 *
	 * @return the onanimationcancel
	 */

	Function getOnanimationcancel();

	/**
	 * Sets onanimationcancel.
	 *
	 * @param onanimationcancel the onanimationcancel
	 */

	void setOnanimationcancel(Function onanimationcancel);

	/**
	 * Add animation cancel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addAnimationCancelEventListener(Function listener, boolean options) {
		addEventListener("animationcancel", listener, options);
	}

	/**
	 * Add animation cancel event listener.
	 *
	 * @param listener the listener
	 */
	default void addAnimationCancelEventListener(Function listener) {
		addEventListener("animationcancel", listener);
	}

	/**
	 * Remove animation cancel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeAnimationCancelEventListener(Function listener, boolean options) {
		removeEventListener("animationcancel", listener, options);
	}

	/**
	 * Remove animation cancel event listener.
	 *
	 * @param listener the listener
	 */
	default void removeAnimationCancelEventListener(Function listener) {
		removeEventListener("animationcancel", listener);
	}

	/**
	 * Gets onanimationend.
	 *
	 * @return the onanimationend
	 */

	Function getOnanimationend();

	/**
	 * Sets onanimationend.
	 *
	 * @param onanimationend the onanimationend
	 */

	void setOnanimationend(Function onanimationend);

	/**
	 * Add animation end event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addAnimationEndEventListener(Function listener, boolean options) {
		addEventListener("animationend", listener, options);
	}

	/**
	 * Add animation end event listener.
	 *
	 * @param listener the listener
	 */
	default void addAnimationEndEventListener(Function listener) {
		addEventListener("animationend", listener);
	}

	/**
	 * Remove animation end event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeAnimationEndEventListener(Function listener, boolean options) {
		removeEventListener("animationend", listener, options);
	}

	/**
	 * Remove animation end event listener.
	 *
	 * @param listener the listener
	 */
	default void removeAnimationEndEventListener(Function listener) {
		removeEventListener("animationend", listener);
	}

	/**
	 * Gets onanimationiteration.
	 *
	 * @return the onanimationiteration
	 */

	Function getOnanimationiteration();

	/**
	 * Sets onanimationiteration.
	 *
	 * @param onanimationiteration the onanimationiteration
	 */

	void setOnanimationiteration(Function onanimationiteration);

	/**
	 * Add animation iteration event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addAnimationIterationEventListener(Function listener, boolean options) {
		addEventListener("animationiteration", listener, options);
	}

	/**
	 * Add animation iteration event listener.
	 *
	 * @param listener the listener
	 */
	default void addAnimationIterationEventListener(Function listener) {
		addEventListener("animationiteration", listener);
	}

	/**
	 * Remove animation iteration event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeAnimationIterationEventListener(Function listener, boolean options) {
		removeEventListener("animationiteration", listener, options);
	}

	/**
	 * Remove animation iteration event listener.
	 *
	 * @param listener the listener
	 */
	default void removeAnimationIterationEventListener(Function listener) {
		removeEventListener("animationiteration", listener);
	}

	/**
	 * Gets onanimationstart.
	 *
	 * @return the onanimationstart
	 */

	Function getOnanimationstart();

	/**
	 * Sets onanimationstart.
	 *
	 * @param onanimationstart the onanimationstart
	 */

	void setOnanimationstart(Function onanimationstart);

	/**
	 * Add animation start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addAnimationStartEventListener(Function listener, boolean options) {
		addEventListener("animationstart", listener, options);
	}

	/**
	 * Add animation start event listener.
	 *
	 * @param listener the listener
	 */
	default void addAnimationStartEventListener(Function listener) {
		addEventListener("animationstart", listener);
	}

	/**
	 * Remove animation start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeAnimationStartEventListener(Function listener, boolean options) {
		removeEventListener("animationstart", listener, options);
	}

	/**
	 * Remove animation start event listener.
	 *
	 * @param listener the listener
	 */
	default void removeAnimationStartEventListener(Function listener) {
		removeEventListener("animationstart", listener);
	}

	/**
	 * Gets onauxclick.
	 *
	 * @return the onauxclick
	 */

	Function getOnauxclick();

	/**
	 * Sets onauxclick.
	 *
	 * @param onauxclick the onauxclick
	 */

	void setOnauxclick(Function onauxclick);

	/**
	 * Add aux click event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addAuxClickEventListener(Function listener, boolean options) {
		addEventListener("auxclick", listener, options);
	}

	/**
	 * Add aux click event listener.
	 *
	 * @param listener the listener
	 */
	default void addAuxClickEventListener(Function listener) {
		addEventListener("auxclick", listener);
	}

	/**
	 * Remove aux click event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeAuxClickEventListener(Function listener, boolean options) {
		removeEventListener("auxclick", listener, options);
	}

	/**
	 * Remove aux click event listener.
	 *
	 * @param listener the listener
	 */
	default void removeAuxClickEventListener(Function listener) {
		removeEventListener("auxclick", listener);
	}

	/**
	 * Fires when the object loses the input focus.
	 *
	 *
	 *
	 * @return the onblur
	 */

	Function getOnblur();

	/**
	 * Sets onblur.
	 *
	 * @param onblur the onblur
	 */

	void setOnblur(Function onblur);

	/**
	 * Add blur event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addBlurEventListener(Function listener, boolean options) {
		addEventListener("blur", listener, options);
	}

	/**
	 * Add blur event listener.
	 *
	 * @param listener the listener
	 */
	default void addBlurEventListener(Function listener) {
		addEventListener("blur", listener);
	}

	/**
	 * Remove blur event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeBlurEventListener(Function listener, boolean options) {
		removeEventListener("blur", listener, options);
	}

	/**
	 * Remove blur event listener.
	 *
	 * @param listener the listener
	 */
	default void removeBlurEventListener(Function listener) {
		removeEventListener("blur", listener);
	}

	/**
	 * Gets oncancel.
	 *
	 * @return the oncancel
	 */

	Function getOncancel();

	/**
	 * Sets oncancel.
	 *
	 * @param oncancel the oncancel
	 */

	void setOncancel(Function oncancel);

	/**
	 * Add cancel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addCancelEventListener(Function listener, boolean options) {
		addEventListener("cancel", listener, options);
	}

	/**
	 * Add cancel event listener.
	 *
	 * @param listener the listener
	 */
	default void addCancelEventListener(Function listener) {
		addEventListener("cancel", listener);
	}

	/**
	 * Remove cancel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeCancelEventListener(Function listener, boolean options) {
		removeEventListener("cancel", listener, options);
	}

	/**
	 * Remove cancel event listener.
	 *
	 * @param listener the listener
	 */
	default void removeCancelEventListener(Function listener) {
		removeEventListener("cancel", listener);
	}

	/**
	 * Occurs when playback is possible, but would require further buffering.
	 *
	 * @return the oncanplay
	 */

	Function getOncanplay();

	/**
	 * Sets oncanplay.
	 *
	 * @param oncanplay the oncanplay
	 */

	void setOncanplay(Function oncanplay);

	/**
	 * Add can play event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addCanPlayEventListener(Function listener, boolean options) {
		addEventListener("canplay", listener, options);
	}

	/**
	 * Add can play event listener.
	 *
	 * @param listener the listener
	 */
	default void addCanPlayEventListener(Function listener) {
		addEventListener("canplay", listener);
	}

	/**
	 * Remove can play event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeCanPlayEventListener(Function listener, boolean options) {
		removeEventListener("canplay", listener, options);
	}

	/**
	 * Remove can play event listener.
	 *
	 * @param listener the listener
	 */
	default void removeCanPlayEventListener(Function listener) {
		removeEventListener("canplay", listener);
	}

	/**
	 * Gets oncanplaythrough.
	 *
	 * @return the oncanplaythrough
	 */

	Function getOncanplaythrough();

	/**
	 * Sets oncanplaythrough.
	 *
	 * @param oncanplaythrough the oncanplaythrough
	 */

	void setOncanplaythrough(Function oncanplaythrough);

	/**
	 * Add can play through event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addCanPlayThroughEventListener(Function listener, boolean options) {
		addEventListener("canplaythrough", listener, options);
	}

	/**
	 * Add can play through event listener.
	 *
	 * @param listener the listener
	 */
	default void addCanPlayThroughEventListener(Function listener) {
		addEventListener("canplaythrough", listener);
	}

	/**
	 * Remove can play through event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeCanPlayThroughEventListener(Function listener, boolean options) {
		removeEventListener("canplaythrough", listener, options);
	}

	/**
	 * Remove can play through event listener.
	 *
	 * @param listener the listener
	 */
	default void removeCanPlayThroughEventListener(Function listener) {
		removeEventListener("canplaythrough", listener);
	}

	/**
	 * Fires when the contents of the object or selection have changed.
	 *
	 * @return the onchange
	 */

	Function getOnchange();

	/**
	 * Sets onchange.
	 *
	 * @param onchange the onchange
	 */

	void setOnchange(Function onchange);

	/**
	 * Add change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addChangeEventListener(Function listener, boolean options) {
		addEventListener("change", listener, options);
	}

	/**
	 * Add change event listener.
	 *
	 * @param listener the listener
	 */
	default void addChangeEventListener(Function listener) {
		addEventListener("change", listener);
	}

	/**
	 * Remove change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeChangeEventListener(Function listener, boolean options) {
		removeEventListener("change", listener, options);
	}

	/**
	 * Remove change event listener.
	 *
	 * @param listener the listener
	 */
	default void removeChangeEventListener(Function listener) {
		removeEventListener("change", listener);
	}

	/**
	 * Fires when the user clicks the left mouse button on the object
	 *
	 *
	 *
	 * @return the onclick
	 */

	Function getOnclick();

	/**
	 * Sets onclick.
	 *
	 * @param onclick the onclick
	 */

	void setOnclick(Function onclick);

	/**
	 * Add click event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addClickEventListener(Function listener, boolean options) {
		addEventListener("click", listener, options);
	}

	/**
	 * Add click event listener.
	 *
	 * @param listener the listener
	 */
	default void addClickEventListener(Function listener) {
		addEventListener("click", listener);
	}

	/**
	 * Remove click event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeClickEventListener(Function listener, boolean options) {
		removeEventListener("click", listener, options);
	}

	/**
	 * Remove click event listener.
	 *
	 * @param listener the listener
	 */
	default void removeClickEventListener(Function listener) {
		removeEventListener("click", listener);
	}

	/**
	 * Gets onclose.
	 *
	 * @return the onclose
	 */

	Function getOnclose();

	/**
	 * Sets onclose.
	 *
	 * @param onclose the onclose
	 */

	void setOnclose(Function onclose);

	/**
	 * Add close event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addCloseEventListener(Function listener, boolean options) {
		addEventListener("close", listener, options);
	}

	/**
	 * Add close event listener.
	 *
	 * @param listener the listener
	 */
	default void addCloseEventListener(Function listener) {
		addEventListener("close", listener);
	}

	/**
	 * Remove close event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeCloseEventListener(Function listener, boolean options) {
		removeEventListener("close", listener, options);
	}

	/**
	 * Remove close event listener.
	 *
	 * @param listener the listener
	 */
	default void removeCloseEventListener(Function listener) {
		removeEventListener("close", listener);
	}

	/**
	 * Fires when the user clicks the right mouse button in the client area, opening
	 * the context menu.
	 *
	 *
	 *
	 * @return the oncontextmenu
	 */

	Function getOncontextmenu();

	/**
	 * Sets oncontextmenu.
	 *
	 * @param oncontextmenu the oncontextmenu
	 */

	void setOncontextmenu(Function oncontextmenu);

	/**
	 * Add context menu event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addContextMenuEventListener(Function listener, boolean options) {
		addEventListener("contextmenu", listener, options);
	}

	/**
	 * Add context menu event listener.
	 *
	 * @param listener the listener
	 */
	default void addContextMenuEventListener(Function listener) {
		addEventListener("contextmenu", listener);
	}

	/**
	 * Remove context menu event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeContextMenuEventListener(Function listener, boolean options) {
		removeEventListener("contextmenu", listener, options);
	}

	/**
	 * Remove context menu event listener.
	 *
	 * @param listener the listener
	 */
	default void removeContextMenuEventListener(Function listener) {
		removeEventListener("contextmenu", listener);
	}

	/**
	 * Gets oncuechange.
	 *
	 * @return the oncuechange
	 */

	Function getOncuechange();

	/**
	 * Sets oncuechange.
	 *
	 * @param oncuechange the oncuechange
	 */

	void setOncuechange(Function oncuechange);

	/**
	 * Add cue change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addCueChangeEventListener(Function listener, boolean options) {
		addEventListener("cuechange", listener, options);
	}

	/**
	 * Add cue change event listener.
	 *
	 * @param listener the listener
	 */
	default void addCueChangeEventListener(Function listener) {
		addEventListener("cuechange", listener);
	}

	/**
	 * Remove cue change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeCueChangeEventListener(Function listener, boolean options) {
		removeEventListener("cuechange", listener, options);
	}

	/**
	 * Remove cue change event listener.
	 *
	 * @param listener the listener
	 */
	default void removeCueChangeEventListener(Function listener) {
		removeEventListener("cuechange", listener);
	}

	/**
	 * Fires when the user double-clicks the object.
	 *
	 *
	 *
	 * @return the ondblclick
	 */

	Function getOndblclick();

	/**
	 * Sets ondblclick.
	 *
	 * @param ondblclick the ondblclick
	 */

	void setOndblclick(Function ondblclick);

	/**
	 * Add dbl click event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addDblClickEventListener(Function listener, boolean options) {
		addEventListener("dblclick", listener, options);
	}

	/**
	 * Add dbl click event listener.
	 *
	 * @param listener the listener
	 */
	default void addDblClickEventListener(Function listener) {
		addEventListener("dblclick", listener);
	}

	/**
	 * Remove dbl click event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeDblClickEventListener(Function listener, boolean options) {
		removeEventListener("dblclick", listener, options);
	}

	/**
	 * Remove dbl click event listener.
	 *
	 * @param listener the listener
	 */
	default void removeDblClickEventListener(Function listener) {
		removeEventListener("dblclick", listener);
	}

	/**
	 * Fires on the source object continuously during a drag operation.
	 *
	 * @return the ondrag
	 */

	Function getOndrag();

	/**
	 * Sets ondrag.
	 *
	 * @param ondrag the ondrag
	 */

	void setOndrag(Function ondrag);

	/**
	 * Add drag event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addDragEventListener(Function listener, boolean options) {
		addEventListener("drag", listener, options);
	}

	/**
	 * Add drag event listener.
	 *
	 * @param listener the listener
	 */
	default void addDragEventListener(Function listener) {
		addEventListener("drag", listener);
	}

	/**
	 * Remove drag event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeDragEventListener(Function listener, boolean options) {
		removeEventListener("drag", listener, options);
	}

	/**
	 * Remove drag event listener.
	 *
	 * @param listener the listener
	 */
	default void removeDragEventListener(Function listener) {
		removeEventListener("drag", listener);
	}

	/**
	 * Fires on the source object when the user releases the mouse at the close of a
	 * drag operation.
	 *
	 * @return the ondragend
	 */

	Function getOndragend();

	/**
	 * Sets ondragend.
	 *
	 * @param ondragend the ondragend
	 */

	void setOndragend(Function ondragend);

	/**
	 * Add drag end event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addDragEndEventListener(Function listener, boolean options) {
		addEventListener("dragend", listener, options);
	}

	/**
	 * Add drag end event listener.
	 *
	 * @param listener the listener
	 */
	default void addDragEndEventListener(Function listener) {
		addEventListener("dragend", listener);
	}

	/**
	 * Remove drag end event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeDragEndEventListener(Function listener, boolean options) {
		removeEventListener("dragend", listener, options);
	}

	/**
	 * Remove drag end event listener.
	 *
	 * @param listener the listener
	 */
	default void removeDragEndEventListener(Function listener) {
		removeEventListener("dragend", listener);
	}

	/**
	 * Fires on the target element when the user drags the object to a valid drop
	 * target.
	 *
	 *
	 *
	 * @return the ondragenter
	 */

	Function getOndragenter();

	/**
	 * Sets ondragenter.
	 *
	 * @param ondragenter the ondragenter
	 */

	void setOndragenter(Function ondragenter);

	/**
	 * Add drag enter event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addDragEnterEventListener(Function listener, boolean options) {
		addEventListener("dragenter", listener, options);
	}

	/**
	 * Add drag enter event listener.
	 *
	 * @param listener the listener
	 */
	default void addDragEnterEventListener(Function listener) {
		addEventListener("dragenter", listener);
	}

	/**
	 * Remove drag enter event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeDragEnterEventListener(Function listener, boolean options) {
		removeEventListener("dragenter", listener, options);
	}

	/**
	 * Remove drag enter event listener.
	 *
	 * @param listener the listener
	 */
	default void removeDragEnterEventListener(Function listener) {
		removeEventListener("dragenter", listener);
	}

	/**
	 * Gets ondragexit.
	 *
	 * @return the ondragexit
	 */

	Function getOndragexit();

	/**
	 * Sets ondragexit.
	 *
	 * @param ondragexit the ondragexit
	 */

	void setOndragexit(Function ondragexit);

	/**
	 * Add drag exit event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addDragExitEventListener(Function listener, boolean options) {
		addEventListener("dragexit", listener, options);
	}

	/**
	 * Add drag exit event listener.
	 *
	 * @param listener the listener
	 */
	default void addDragExitEventListener(Function listener) {
		addEventListener("dragexit", listener);
	}

	/**
	 * Remove drag exit event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeDragExitEventListener(Function listener, boolean options) {
		removeEventListener("dragexit", listener, options);
	}

	/**
	 * Remove drag exit event listener.
	 *
	 * @param listener the listener
	 */
	default void removeDragExitEventListener(Function listener) {
		removeEventListener("dragexit", listener);
	}

	/**
	 * Fires on the target object when the user moves the mouse out of a valid drop
	 * target during a drag operation.
	 *
	 *
	 *
	 * @return the ondragleave
	 */

	Function getOndragleave();

	/**
	 * Sets ondragleave.
	 *
	 * @param ondragleave the ondragleave
	 */

	void setOndragleave(Function ondragleave);

	/**
	 * Add drag leave event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addDragLeaveEventListener(Function listener, boolean options) {
		addEventListener("dragleave", listener, options);
	}

	/**
	 * Add drag leave event listener.
	 *
	 * @param listener the listener
	 */
	default void addDragLeaveEventListener(Function listener) {
		addEventListener("dragleave", listener);
	}

	/**
	 * Remove drag leave event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeDragLeaveEventListener(Function listener, boolean options) {
		removeEventListener("dragleave", listener, options);
	}

	/**
	 * Remove drag leave event listener.
	 *
	 * @param listener the listener
	 */
	default void removeDragLeaveEventListener(Function listener) {
		removeEventListener("dragleave", listener);
	}

	/**
	 * Fires on the target element continuously while the user drags the object over
	 * a valid drop target.
	 *
	 * @return the ondragover
	 */

	Function getOndragover();

	/**
	 * Sets ondragover.
	 *
	 * @param ondragover the ondragover
	 */

	void setOndragover(Function ondragover);

	/**
	 * Add drag over event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addDragOverEventListener(Function listener, boolean options) {
		addEventListener("dragover", listener, options);
	}

	/**
	 * Add drag over event listener.
	 *
	 * @param listener the listener
	 */
	default void addDragOverEventListener(Function listener) {
		addEventListener("dragover", listener);
	}

	/**
	 * Remove drag over event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeDragOverEventListener(Function listener, boolean options) {
		removeEventListener("dragover", listener, options);
	}

	/**
	 * Remove drag over event listener.
	 *
	 * @param listener the listener
	 */
	default void removeDragOverEventListener(Function listener) {
		removeEventListener("dragover", listener);
	}

	/**
	 * Fires on the source object when the user starts to drag a text selection or
	 * selected object.
	 *
	 * @return the ondragstart
	 */

	Function getOndragstart();

	/**
	 * Sets ondragstart.
	 *
	 * @param ondragstart the ondragstart
	 */

	void setOndragstart(Function ondragstart);

	/**
	 * Add drag start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addDragStartEventListener(Function listener, boolean options) {
		addEventListener("dragstart", listener, options);
	}

	/**
	 * Add drag start event listener.
	 *
	 * @param listener the listener
	 */
	default void addDragStartEventListener(Function listener) {
		addEventListener("dragstart", listener);
	}

	/**
	 * Remove drag start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeDragStartEventListener(Function listener, boolean options) {
		removeEventListener("dragstart", listener, options);
	}

	/**
	 * Remove drag start event listener.
	 *
	 * @param listener the listener
	 */
	default void removeDragStartEventListener(Function listener) {
		removeEventListener("dragstart", listener);
	}

	/**
	 * Gets ondrop.
	 *
	 * @return the ondrop
	 */

	Function getOndrop();

	/**
	 * Sets ondrop.
	 *
	 * @param ondrop the ondrop
	 */

	void setOndrop(Function ondrop);

	/**
	 * Add drop event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addDropEventListener(Function listener, boolean options) {
		addEventListener("drop", listener, options);
	}

	/**
	 * Add drop event listener.
	 *
	 * @param listener the listener
	 */
	default void addDropEventListener(Function listener) {
		addEventListener("drop", listener);
	}

	/**
	 * Remove drop event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeDropEventListener(Function listener, boolean options) {
		removeEventListener("drop", listener, options);
	}

	/**
	 * Remove drop event listener.
	 *
	 * @param listener the listener
	 */
	default void removeDropEventListener(Function listener) {
		removeEventListener("drop", listener);
	}

	/**
	 * Occurs when the duration attribute is updated.
	 *
	 * @return the ondurationchange
	 */

	Function getOndurationchange();

	/**
	 * Sets ondurationchange.
	 *
	 * @param ondurationchange the ondurationchange
	 */

	void setOndurationchange(Function ondurationchange);

	/**
	 * Add duration change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addDurationChangeEventListener(Function listener, boolean options) {
		addEventListener("durationchange", listener, options);
	}

	/**
	 * Add duration change event listener.
	 *
	 * @param listener the listener
	 */
	default void addDurationChangeEventListener(Function listener) {
		addEventListener("durationchange", listener);
	}

	/**
	 * Remove duration change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeDurationChangeEventListener(Function listener, boolean options) {
		removeEventListener("durationchange", listener, options);
	}

	/**
	 * Remove duration change event listener.
	 *
	 * @param listener the listener
	 */
	default void removeDurationChangeEventListener(Function listener) {
		removeEventListener("durationchange", listener);
	}

	/**
	 * Occurs when the media element is reset to its initial state.
	 *
	 * @return the onemptied
	 */

	Function getOnemptied();

	/**
	 * Sets onemptied.
	 *
	 * @param onemptied the onemptied
	 */

	void setOnemptied(Function onemptied);

	/**
	 * Add emptied event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addEmptiedEventListener(Function listener, boolean options) {
		addEventListener("emptied", listener, options);
	}

	/**
	 * Add emptied event listener.
	 *
	 * @param listener the listener
	 */
	default void addEmptiedEventListener(Function listener) {
		addEventListener("emptied", listener);
	}

	/**
	 * Remove emptied event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeEmptiedEventListener(Function listener, boolean options) {
		removeEventListener("emptied", listener, options);
	}

	/**
	 * Remove emptied event listener.
	 *
	 * @param listener the listener
	 */
	default void removeEmptiedEventListener(Function listener) {
		removeEventListener("emptied", listener);
	}

	/**
	 * Occurs when the end of playback is reached.
	 *
	 *
	 *
	 * @return the onended
	 */

	Function getOnended();

	/**
	 * Sets onended.
	 *
	 * @param onended the onended
	 */

	void setOnended(Function onended);

	/**
	 * Add ended event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addEndedEventListener(Function listener, boolean options) {
		addEventListener("ended", listener, options);
	}

	/**
	 * Add ended event listener.
	 *
	 * @param listener the listener
	 */
	default void addEndedEventListener(Function listener) {
		addEventListener("ended", listener);
	}

	/**
	 * Remove ended event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeEndedEventListener(Function listener, boolean options) {
		removeEventListener("ended", listener, options);
	}

	/**
	 * Remove ended event listener.
	 *
	 * @param listener the listener
	 */
	default void removeEndedEventListener(Function listener) {
		removeEventListener("ended", listener);
	}

	/**
	 * Fires when an error occurs during object loading.
	 *
	 * @return the onerror
	 */

	Function getOnerror();

	/**
	 * Sets onerror.
	 *
	 * @param onerror the onerror
	 */

	void setOnerror(Function onerror);

	/**
	 * Add error event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addErrorEventListener(Function listener, boolean options) {
		addEventListener("error", listener, options);
	}

	/**
	 * Add error event listener.
	 *
	 * @param listener the listener
	 */
	default void addErrorEventListener(Function listener) {
		addEventListener("error", listener);
	}

	/**
	 * Remove error event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeErrorEventListener(Function listener, boolean options) {
		removeEventListener("error", listener, options);
	}

	/**
	 * Remove error event listener.
	 *
	 * @param listener the listener
	 */
	default void removeErrorEventListener(Function listener) {
		removeEventListener("error", listener);
	}

	/**
	 * Fires when the object receives focus.
	 *
	 * @return the onfocus
	 */

	Function getOnfocus();

	/**
	 * Sets onfocus.
	 *
	 * @param onfocus the onfocus
	 */

	void setOnfocus(Function onfocus);

	/**
	 * Add focus event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addFocusEventListener(Function listener, boolean options) {
		addEventListener("focus", listener, options);
	}

	/**
	 * Add focus event listener.
	 *
	 * @param listener the listener
	 */
	default void addFocusEventListener(Function listener) {
		addEventListener("focus", listener);
	}

	/**
	 * Remove focus event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeFocusEventListener(Function listener, boolean options) {
		removeEventListener("focus", listener, options);
	}

	/**
	 * Remove focus event listener.
	 *
	 * @param listener the listener
	 */
	default void removeFocusEventListener(Function listener) {
		removeEventListener("focus", listener);
	}

	/**
	 * Gets onfocusin.
	 *
	 * @return the onfocusin
	 */

	Function getOnfocusin();

	/**
	 * Sets onfocusin.
	 *
	 * @param onfocusin the onfocusin
	 */

	void setOnfocusin(Function onfocusin);

	/**
	 * Add focus in event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addFocusInEventListener(Function listener, boolean options) {
		addEventListener("focusin", listener, options);
	}

	/**
	 * Add focus in event listener.
	 *
	 * @param listener the listener
	 */
	default void addFocusInEventListener(Function listener) {
		addEventListener("focusin", listener);
	}

	/**
	 * Remove focus in event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeFocusInEventListener(Function listener, boolean options) {
		removeEventListener("focusin", listener, options);
	}

	/**
	 * Remove focus in event listener.
	 *
	 * @param listener the listener
	 */
	default void removeFocusInEventListener(Function listener) {
		removeEventListener("focusin", listener);
	}

	/**
	 * Gets onfocusout.
	 *
	 * @return the onfocusout
	 */

	Function getOnfocusout();

	/**
	 * Sets onfocusout.
	 *
	 * @param onfocusout the onfocusout
	 */

	void setOnfocusout(Function onfocusout);

	/**
	 * Add focus out event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addFocusOutEventListener(Function listener, boolean options) {
		addEventListener("focusout", listener, options);
	}

	/**
	 * Add focus out event listener.
	 *
	 * @param listener the listener
	 */
	default void addFocusOutEventListener(Function listener) {
		addEventListener("focusout", listener);
	}

	/**
	 * Remove focus out event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeFocusOutEventListener(Function listener, boolean options) {
		removeEventListener("focusout", listener, options);
	}

	/**
	 * Remove focus out event listener.
	 *
	 * @param listener the listener
	 */
	default void removeFocusOutEventListener(Function listener) {
		removeEventListener("focusout", listener);
	}

	/**
	 * Gets ongotpointercapture.
	 *
	 * @return the ongotpointercapture
	 */

	Function getOngotpointercapture();

	/**
	 * Sets ongotpointercapture.
	 *
	 * @param ongotpointercapture the ongotpointercapture
	 */

	void setOngotpointercapture(Function ongotpointercapture);

	/**
	 * Add got pointer capture event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addGotPointerCaptureEventListener(Function listener, boolean options) {
		addEventListener("gotpointercapture", listener, options);
	}

	/**
	 * Add got pointer capture event listener.
	 *
	 * @param listener the listener
	 */
	default void addGotPointerCaptureEventListener(Function listener) {
		addEventListener("gotpointercapture", listener);
	}

	/**
	 * Remove got pointer capture event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeGotPointerCaptureEventListener(Function listener, boolean options) {
		removeEventListener("gotpointercapture", listener, options);
	}

	/**
	 * Remove got pointer capture event listener.
	 *
	 * @param listener the listener
	 */
	default void removeGotPointerCaptureEventListener(Function listener) {
		removeEventListener("gotpointercapture", listener);
	}

	/**
	 * Gets oninput.
	 *
	 * @return the oninput
	 */

	Function getOninput();

	/**
	 * Sets oninput.
	 *
	 * @param oninput the oninput
	 */

	void setOninput(Function oninput);

	/**
	 * Add input event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addInputEventListener(Function listener, boolean options) {
		addEventListener("input", listener, options);
	}

	/**
	 * Add input event listener.
	 *
	 * @param listener the listener
	 */
	default void addInputEventListener(Function listener) {
		addEventListener("input", listener);
	}

	/**
	 * Remove input event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeInputEventListener(Function listener, boolean options) {
		removeEventListener("input", listener, options);
	}

	/**
	 * Remove input event listener.
	 *
	 * @param listener the listener
	 */
	default void removeInputEventListener(Function listener) {
		removeEventListener("input", listener);
	}

	/**
	 * Gets oninvalid.
	 *
	 * @return the oninvalid
	 */

	Function getOninvalid();

	/**
	 * Sets oninvalid.
	 *
	 * @param oninvalid the oninvalid
	 */

	void setOninvalid(Function oninvalid);

	/**
	 * Add invalid event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addInvalidEventListener(Function listener, boolean options) {
		addEventListener("invalid", listener, options);
	}

	/**
	 * Add invalid event listener.
	 *
	 * @param listener the listener
	 */
	default void addInvalidEventListener(Function listener) {
		addEventListener("invalid", listener);
	}

	/**
	 * Remove invalid event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeInvalidEventListener(Function listener, boolean options) {
		removeEventListener("invalid", listener, options);
	}

	/**
	 * Remove invalid event listener.
	 *
	 * @param listener the listener
	 */
	default void removeInvalidEventListener(Function listener) {
		removeEventListener("invalid", listener);
	}

	/**
	 * Fires when the user presses a key.
	 *
	 *
	 *
	 * @return the onkeydown
	 */

	Function getOnkeydown();

	/**
	 * Sets onkeydown.
	 *
	 * @param onkeydown the onkeydown
	 */

	void setOnkeydown(Function onkeydown);

	/**
	 * Add key down event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addKeyDownEventListener(Function listener, boolean options) {
		addEventListener("keydown", listener, options);
	}

	/**
	 * Add key down event listener.
	 *
	 * @param listener the listener
	 */
	default void addKeyDownEventListener(Function listener) {
		addEventListener("keydown", listener);
	}

	/**
	 * Remove key down event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeKeyDownEventListener(Function listener, boolean options) {
		removeEventListener("keydown", listener, options);
	}

	/**
	 * Remove key down event listener.
	 *
	 * @param listener the listener
	 */
	default void removeKeyDownEventListener(Function listener) {
		removeEventListener("keydown", listener);
	}

	/**
	 * Fires when the user presses an alphanumeric key.
	 *
	 * @return the onkeypress
	 */

	Function getOnkeypress();

	/**
	 * Sets onkeypress.
	 *
	 * @param onkeypress the onkeypress
	 */

	void setOnkeypress(Function onkeypress);

	/**
	 * Add key press event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addKeyPressEventListener(Function listener, boolean options) {
		addEventListener("keypress", listener, options);
	}

	/**
	 * Add key press event listener.
	 *
	 * @param listener the listener
	 */
	default void addKeyPressEventListener(Function listener) {
		addEventListener("keypress", listener);
	}

	/**
	 * Remove key press event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeKeyPressEventListener(Function listener, boolean options) {
		removeEventListener("keypress", listener, options);
	}

	/**
	 * Remove key press event listener.
	 *
	 * @param listener the listener
	 */
	default void removeKeyPressEventListener(Function listener) {
		removeEventListener("keypress", listener);
	}

	/**
	 * Fires when the user releases a key.
	 *
	 *
	 *
	 * @return the onkeyup
	 */

	Function getOnkeyup();

	/**
	 * Sets onkeyup.
	 *
	 * @param onkeyup the onkeyup
	 */

	void setOnkeyup(Function onkeyup);

	/**
	 * Add key up event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addKeyUpEventListener(Function listener, boolean options) {
		addEventListener("keyup", listener, options);
	}

	/**
	 * Add key up event listener.
	 *
	 * @param listener the listener
	 */
	default void addKeyUpEventListener(Function listener) {
		addEventListener("keyup", listener);
	}

	/**
	 * Remove key up event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeKeyUpEventListener(Function listener, boolean options) {
		removeEventListener("keyup", listener, options);
	}

	/**
	 * Remove key up event listener.
	 *
	 * @param listener the listener
	 */
	default void removeKeyUpEventListener(Function listener) {
		removeEventListener("keyup", listener);
	}

	/**
	 * Fires immediately after the browser loads the object.
	 *
	 * @return the onload
	 */

	Function getOnload();

	/**
	 * Sets onload.
	 *
	 * @param onload the onload
	 */

	void setOnload(Function onload);

	/**
	 * Add load event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addLoadEventListener(Function listener, boolean options) {
		addEventListener("load", listener, options);
	}

	/**
	 * Add load event listener.
	 *
	 * @param listener the listener
	 */
	default void addLoadEventListener(Function listener) {
		addEventListener("load", listener);
	}

	/**
	 * Remove load event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeLoadEventListener(Function listener, boolean options) {
		removeEventListener("load", listener, options);
	}

	/**
	 * Remove load event listener.
	 *
	 * @param listener the listener
	 */
	default void removeLoadEventListener(Function listener) {
		removeEventListener("load", listener);
	}

	/**
	 * Occurs when media data is loaded at the current playback position.
	 *
	 * @return the onloadeddata
	 */

	Function getOnloadeddata();

	/**
	 * Sets onloadeddata.
	 *
	 * @param onloadeddata the onloadeddata
	 */

	void setOnloadeddata(Function onloadeddata);

	/**
	 * Add loaded data event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addLoadedDataEventListener(Function listener, boolean options) {
		addEventListener("loadeddata", listener, options);
	}

	/**
	 * Add loaded data event listener.
	 *
	 * @param listener the listener
	 */
	default void addLoadedDataEventListener(Function listener) {
		addEventListener("loadeddata", listener);
	}

	/**
	 * Remove loaded data event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeLoadedDataEventListener(Function listener, boolean options) {
		removeEventListener("loadeddata", listener, options);
	}

	/**
	 * Remove loaded data event listener.
	 *
	 * @param listener the listener
	 */
	default void removeLoadedDataEventListener(Function listener) {
		removeEventListener("loadeddata", listener);
	}

	/**
	 * Occurs when the duration and dimensions of the media have been determined.
	 *
	 * @return the onloadedmetadata
	 */

	Function getOnloadedmetadata();

	/**
	 * Sets onloadedmetadata.
	 *
	 * @param onloadedmetadata the onloadedmetadata
	 */

	void setOnloadedmetadata(Function onloadedmetadata);

	/**
	 * Add loaded meta data event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addLoadedMetaDataEventListener(Function listener, boolean options) {
		addEventListener("loadedmetadata", listener, options);
	}

	/**
	 * Add loaded meta data event listener.
	 *
	 * @param listener the listener
	 */
	default void addLoadedMetaDataEventListener(Function listener) {
		addEventListener("loadedmetadata", listener);
	}

	/**
	 * Remove loaded meta data event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeLoadedMetaDataEventListener(Function listener, boolean options) {
		removeEventListener("loadedmetadata", listener, options);
	}

	/**
	 * Remove loaded meta data event listener.
	 *
	 * @param listener the listener
	 */
	default void removeLoadedMetaDataEventListener(Function listener) {
		removeEventListener("loadedmetadata", listener);
	}

	/**
	 * Gets onloadend.
	 *
	 * @return the onloadend
	 */

	Function getOnloadend();

	/**
	 * Sets onloadend.
	 *
	 * @param onloadend the onloadend
	 */

	void setOnloadend(Function onloadend);

	/**
	 * Add load end event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addLoadEndEventListener(Function listener, boolean options) {
		addEventListener("loadend", listener, options);
	}

	/**
	 * Add load end event listener.
	 *
	 * @param listener the listener
	 */
	default void addLoadEndEventListener(Function listener) {
		addEventListener("loadend", listener);
	}

	/**
	 * Remove load end event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeLoadEndEventListener(Function listener, boolean options) {
		removeEventListener("loadend", listener, options);
	}

	/**
	 * Remove load end event listener.
	 *
	 * @param listener the listener
	 */
	default void removeLoadEndEventListener(Function listener) {
		removeEventListener("loadend", listener);
	}

	/**
	 * Occurs when Internet Explorer begins looking for media data.
	 *
	 * @return the onloadstart
	 */

	Function getOnloadstart();

	/**
	 * Sets onloadstart.
	 *
	 * @param onloadstart the onloadstart
	 */

	void setOnloadstart(Function onloadstart);

	/**
	 * Add load start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addLoadStartEventListener(Function listener, boolean options) {
		addEventListener("loadstart", listener, options);
	}

	/**
	 * Add load start event listener.
	 *
	 * @param listener the listener
	 */
	default void addLoadStartEventListener(Function listener) {
		addEventListener("loadstart", listener);
	}

	/**
	 * Remove load start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeLoadStartEventListener(Function listener, boolean options) {
		removeEventListener("loadstart", listener, options);
	}

	/**
	 * Remove load start event listener.
	 *
	 * @param listener the listener
	 */
	default void removeLoadStartEventListener(Function listener) {
		removeEventListener("loadstart", listener);
	}

	/**
	 * Gets onlostpointercapture.
	 *
	 * @return the onlostpointercapture
	 */

	Function getOnlostpointercapture();

	/**
	 * Sets onlostpointercapture.
	 *
	 * @param onlostpointercapture the onlostpointercapture
	 */

	void setOnlostpointercapture(Function onlostpointercapture);

	/**
	 * Add lost pointer capture event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addLostPointerCaptureEventListener(Function listener, boolean options) {
		addEventListener("lostpointercapture", listener, options);
	}

	/**
	 * Add lost pointer capture event listener.
	 *
	 * @param listener the listener
	 */
	default void addLostPointerCaptureEventListener(Function listener) {
		addEventListener("lostpointercapture", listener);
	}

	/**
	 * Remove lost pointer capture event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeLostPointerCaptureEventListener(Function listener, boolean options) {
		removeEventListener("lostpointercapture", listener, options);
	}

	/**
	 * Remove lost pointer capture event listener.
	 *
	 * @param listener the listener
	 */
	default void removeLostPointerCaptureEventListener(Function listener) {
		removeEventListener("lostpointercapture", listener);
	}

	/**
	 * Fires when the user clicks the object with either mouse button.
	 *
	 *
	 *
	 * @return the onmousedown
	 */

	Function getOnmousedown();

	/**
	 * Sets onmousedown.
	 *
	 * @param onmousedown the onmousedown
	 */

	void setOnmousedown(Function onmousedown);

	/**
	 * Add mouse down event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addMouseDownEventListener(Function listener, boolean options) {
		addEventListener("mousedown", listener, options);
	}

	/**
	 * Add mouse down event listener.
	 *
	 * @param listener the listener
	 */
	default void addMouseDownEventListener(Function listener) {
		addEventListener("mousedown", listener);
	}

	/**
	 * Remove mouse down event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeMouseDownEventListener(Function listener, boolean options) {
		removeEventListener("mousedown", listener, options);
	}

	/**
	 * Remove mouse down event listener.
	 *
	 * @param listener the listener
	 */
	default void removeMouseDownEventListener(Function listener) {
		removeEventListener("mousedown", listener);
	}

	/**
	 * Gets onmouseenter.
	 *
	 * @return the onmouseenter
	 */

	Function getOnmouseenter();

	/**
	 * Sets onmouseenter.
	 *
	 * @param onmouseenter the onmouseenter
	 */

	void setOnmouseenter(Function onmouseenter);

	/**
	 * Add mouse enter event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addMouseEnterEventListener(Function listener, boolean options) {
		addEventListener("mouseenter", listener, options);
	}

	/**
	 * Add mouse enter event listener.
	 *
	 * @param listener the listener
	 */
	default void addMouseEnterEventListener(Function listener) {
		addEventListener("mouseenter", listener);
	}

	/**
	 * Remove mouse enter event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeMouseEnterEventListener(Function listener, boolean options) {
		removeEventListener("mouseenter", listener, options);
	}

	/**
	 * Remove mouse enter event listener.
	 *
	 * @param listener the listener
	 */
	default void removeMouseEnterEventListener(Function listener) {
		removeEventListener("mouseenter", listener);
	}

	/**
	 * Gets onmouseleave.
	 *
	 * @return the onmouseleave
	 */

	Function getOnmouseleave();

	/**
	 * Sets onmouseleave.
	 *
	 * @param onmouseleave the onmouseleave
	 */

	void setOnmouseleave(Function onmouseleave);

	/**
	 * Add mouse leave event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addMouseLeaveEventListener(Function listener, boolean options) {
		addEventListener("mouseleave", listener, options);
	}

	/**
	 * Add mouse leave event listener.
	 *
	 * @param listener the listener
	 */
	default void addMouseLeaveEventListener(Function listener) {
		addEventListener("mouseleave", listener);
	}

	/**
	 * Remove mouse leave event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeMouseLeaveEventListener(Function listener, boolean options) {
		removeEventListener("mouseleave", listener, options);
	}

	/**
	 * Remove mouse leave event listener.
	 *
	 * @param listener the listener
	 */
	default void removeMouseLeaveEventListener(Function listener) {
		removeEventListener("mouseleave", listener);
	}

	/**
	 * Fires when the user moves the mouse over the object.
	 *
	 *
	 *
	 * @return the onmousemove
	 */

	Function getOnmousemove();

	/**
	 * Sets onmousemove.
	 *
	 * @param onmousemove the onmousemove
	 */

	void setOnmousemove(Function onmousemove);

	/**
	 * Add mouse move event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addMouseMoveEventListener(Function listener, boolean options) {
		addEventListener("mousemove", listener, options);
	}

	/**
	 * Add mouse move event listener.
	 *
	 * @param listener the listener
	 */
	default void addMouseMoveEventListener(Function listener) {
		addEventListener("mousemove", listener);
	}

	/**
	 * Remove mouse move event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeMouseMoveEventListener(Function listener, boolean options) {
		removeEventListener("mousemove", listener, options);
	}

	/**
	 * Remove mouse move event listener.
	 *
	 * @param listener the listener
	 */
	default void removeMouseMoveEventListener(Function listener) {
		removeEventListener("mousemove", listener);
	}

	/**
	 * Fires when the user moves the mouse pointer outside the boundaries of the
	 * object.
	 *
	 *
	 *
	 * @return the onmouseout
	 */

	Function getOnmouseout();

	/**
	 * Sets onmouseout.
	 *
	 * @param onmouseout the onmouseout
	 */

	void setOnmouseout(Function onmouseout);

	/**
	 * Add mouse out event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addMouseOutEventListener(Function listener, boolean options) {
		addEventListener("mouseout", listener, options);
	}

	/**
	 * Add mouse out event listener.
	 *
	 * @param listener the listener
	 */
	default void addMouseOutEventListener(Function listener) {
		addEventListener("mouseout", listener);
	}

	/**
	 * Remove mouse out event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeMouseOutEventListener(Function listener, boolean options) {
		removeEventListener("mouseout", listener, options);
	}

	/**
	 * Remove mouse out event listener.
	 *
	 * @param listener the listener
	 */
	default void removeMouseOutEventListener(Function listener) {
		removeEventListener("mouseout", listener);
	}

	/**
	 * Fires when the user moves the mouse pointer into the object.
	 *
	 *
	 *
	 * @return the onmouseover
	 */

	Function getOnmouseover();

	/**
	 * Sets onmouseover.
	 *
	 * @param onmouseover the onmouseover
	 */

	void setOnmouseover(Function onmouseover);

	/**
	 * Add mouse over event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addMouseOverEventListener(Function listener, boolean options) {
		addEventListener("mouseover", listener, options);
	}

	/**
	 * Add mouse over event listener.
	 *
	 * @param listener the listener
	 */
	default void addMouseOverEventListener(Function listener) {
		addEventListener("mouseover", listener);
	}

	/**
	 * Remove mouse over event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeMouseOverEventListener(Function listener, boolean options) {
		removeEventListener("mouseover", listener, options);
	}

	/**
	 * Remove mouse over event listener.
	 *
	 * @param listener the listener
	 */
	default void removeMouseOverEventListener(Function listener) {
		removeEventListener("mouseover", listener);
	}

	/**
	 * Fires when the user releases a mouse button while the mouse is over the
	 * object.
	 *
	 *
	 *
	 * @return the onmouseup
	 */

	Function getOnmouseup();

	/**
	 * Sets onmouseup.
	 *
	 * @param onmouseup the onmouseup
	 */

	void setOnmouseup(Function onmouseup);

	/**
	 * Add mouse up event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addMouseUpEventListener(Function listener, boolean options) {
		addEventListener("mouseup", listener, options);
	}

	/**
	 * Add mouse up event listener.
	 *
	 * @param listener the listener
	 */
	default void addMouseUpEventListener(Function listener) {
		addEventListener("mouseup", listener);
	}

	/**
	 * Remove mouse up event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeMouseUpEventListener(Function listener, boolean options) {
		removeEventListener("mouseup", listener, options);
	}

	/**
	 * Remove mouse up event listener.
	 *
	 * @param listener the listener
	 */
	default void removeMouseUpEventListener(Function listener) {
		removeEventListener("mouseup", listener);
	}

	/**
	 * Occurs when playback is paused.
	 *
	 * @return the onpause
	 */

	Function getOnpause();

	/**
	 * Sets onpause.
	 *
	 * @param onpause the onpause
	 */

	void setOnpause(Function onpause);

	/**
	 * Add pause event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addPauseEventListener(Function listener, boolean options) {
		addEventListener("pause", listener, options);
	}

	/**
	 * Add pause event listener.
	 *
	 * @param listener the listener
	 */
	default void addPauseEventListener(Function listener) {
		addEventListener("pause", listener);
	}

	/**
	 * Remove pause event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removePauseEventListener(Function listener, boolean options) {
		removeEventListener("pause", listener, options);
	}

	/**
	 * Remove pause event listener.
	 *
	 * @param listener the listener
	 */
	default void removePauseEventListener(Function listener) {
		removeEventListener("pause", listener);
	}

	/**
	 * Occurs when the play method is requested.
	 *
	 * @return the onplay
	 */

	Function getOnplay();

	/**
	 * Sets onplay.
	 *
	 * @param onplay the onplay
	 */

	void setOnplay(Function onplay);

	/**
	 * Add play event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addPlayEventListener(Function listener, boolean options) {
		addEventListener("play", listener, options);
	}

	/**
	 * Add play event listener.
	 *
	 * @param listener the listener
	 */
	default void addPlayEventListener(Function listener) {
		addEventListener("play", listener);
	}

	/**
	 * Remove play event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removePlayEventListener(Function listener, boolean options) {
		removeEventListener("play", listener, options);
	}

	/**
	 * Remove play event listener.
	 *
	 * @param listener the listener
	 */
	default void removePlayEventListener(Function listener) {
		removeEventListener("play", listener);
	}

	/**
	 * Occurs when the audio or video has started playing.
	 *
	 * @return the onplaying
	 */

	Function getOnplaying();

	/**
	 * Sets onplaying.
	 *
	 * @param onplaying the onplaying
	 */

	void setOnplaying(Function onplaying);

	/**
	 * Add playing event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addPlayingEventListener(Function listener, boolean options) {
		addEventListener("playing", listener, options);
	}

	/**
	 * Add playing event listener.
	 *
	 * @param listener the listener
	 */
	default void addPlayingEventListener(Function listener) {
		addEventListener("playing", listener);
	}

	/**
	 * Remove playing event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removePlayingEventListener(Function listener, boolean options) {
		removeEventListener("playing", listener, options);
	}

	/**
	 * Remove playing event listener.
	 *
	 * @param listener the listener
	 */
	default void removePlayingEventListener(Function listener) {
		removeEventListener("playing", listener);
	}

	/**
	 * Gets onpointercancel.
	 *
	 * @return the onpointercancel
	 */

	Function getOnpointercancel();

	/**
	 * Sets onpointercancel.
	 *
	 * @param onpointercancel the onpointercancel
	 */

	void setOnpointercancel(Function onpointercancel);

	/**
	 * Add pointer cancel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addPointerCancelEventListener(Function listener, boolean options) {
		addEventListener("pointercancel", listener, options);
	}

	/**
	 * Add pointer cancel event listener.
	 *
	 * @param listener the listener
	 */
	default void addPointerCancelEventListener(Function listener) {
		addEventListener("pointercancel", listener);
	}

	/**
	 * Remove pointer cancel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removePointerCancelEventListener(Function listener, boolean options) {
		removeEventListener("pointercancel", listener, options);
	}

	/**
	 * Remove pointer cancel event listener.
	 *
	 * @param listener the listener
	 */
	default void removePointerCancelEventListener(Function listener) {
		removeEventListener("pointercancel", listener);
	}

	/**
	 * Gets onpointerdown.
	 *
	 * @return the onpointerdown
	 */

	Function getOnpointerdown();

	/**
	 * Sets onpointerdown.
	 *
	 * @param onpointerdown the onpointerdown
	 */

	void setOnpointerdown(Function onpointerdown);

	/**
	 * Add pointer down event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addPointerDownEventListener(Function listener, boolean options) {
		addEventListener("pointerdown", listener, options);
	}

	/**
	 * Add pointer down event listener.
	 *
	 * @param listener the listener
	 */
	default void addPointerDownEventListener(Function listener) {
		addEventListener("pointerdown", listener);
	}

	/**
	 * Remove pointer down event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removePointerDownEventListener(Function listener, boolean options) {
		removeEventListener("pointerdown", listener, options);
	}

	/**
	 * Remove pointer down event listener.
	 *
	 * @param listener the listener
	 */
	default void removePointerDownEventListener(Function listener) {
		removeEventListener("pointerdown", listener);
	}

	/**
	 * Gets onpointerenter.
	 *
	 * @return the onpointerenter
	 */

	Function getOnpointerenter();

	/**
	 * Sets onpointerenter.
	 *
	 * @param onpointerenter the onpointerenter
	 */

	void setOnpointerenter(Function onpointerenter);

	/**
	 * Add pointer enter event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addPointerEnterEventListener(Function listener, boolean options) {
		addEventListener("pointerenter", listener, options);
	}

	/**
	 * Add pointer enter event listener.
	 *
	 * @param listener the listener
	 */
	default void addPointerEnterEventListener(Function listener) {
		addEventListener("pointerenter", listener);

	}

	/**
	 * Remove pointer enter event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removePointerEnterEventListener(Function listener, boolean options) {
		removeEventListener("pointerenter", listener, options);
	}

	/**
	 * Remove pointer enter event listener.
	 *
	 * @param listener the listener
	 */
	default void removePointerEnterEventListener(Function listener) {
		removeEventListener("pointerenter", listener);
	}

	/**
	 * Gets onpointerleave.
	 *
	 * @return the onpointerleave
	 */

	Function getOnpointerleave();

	/**
	 * Sets onpointerleave.
	 *
	 * @param onpointerleave the onpointerleave
	 */

	void setOnpointerleave(Function onpointerleave);

	/**
	 * Add pointer leave event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addPointerLeaveEventListener(Function listener, boolean options) {
		addEventListener("pointerleave", listener, options);
	}

	/**
	 * Add pointer leave event listener.
	 *
	 * @param listener the listener
	 */
	default void addPointerLeaveEventListener(Function listener) {
		addEventListener("pointerleave", listener);
	}

	/**
	 * Remove pointer leave event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removePointerLeaveEventListener(Function listener, boolean options) {
		removeEventListener("pointerleave", listener, options);
	}

	/**
	 * Remove pointer leave event listener.
	 *
	 * @param listener the listener
	 */
	default void removePointerLeaveEventListener(Function listener) {
		removeEventListener("pointerleave", listener);
	}

	/**
	 * Gets onpointermove.
	 *
	 * @return the onpointermove
	 */

	Function getOnpointermove();

	/**
	 * Sets onpointermove.
	 *
	 * @param onpointermove the onpointermove
	 */

	void setOnpointermove(Function onpointermove);

	/**
	 * Add pointer move event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addPointerMoveEventListener(Function listener, boolean options) {
		addEventListener("pointermove", listener, options);
	}

	/**
	 * Add pointer move event listener.
	 *
	 * @param listener the listener
	 */
	default void addPointerMoveEventListener(Function listener) {
		addEventListener("pointermove", listener);
	}

	/**
	 * Remove pointer move event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removePointerMoveEventListener(Function listener, boolean options) {
		removeEventListener("pointermove", listener, options);
	}

	/**
	 * Remove pointer move event listener.
	 *
	 * @param listener the listener
	 */
	default void removePointerMoveEventListener(Function listener) {
		removeEventListener("pointermove", listener);
	}

	/**
	 * Gets onpointerout.
	 *
	 * @return the onpointerout
	 */

	Function getOnpointerout();

	/**
	 * Sets onpointerout.
	 *
	 * @param onpointerout the onpointerout
	 */

	void setOnpointerout(Function onpointerout);

	/**
	 * Add pointer out event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addPointerOutEventListener(Function listener, boolean options) {
		addEventListener("pointerout", listener, options);
	}

	/**
	 * Add pointer out event listener.
	 *
	 * @param listener the listener
	 */
	default void addPointerOutEventListener(Function listener) {
		addEventListener("pointerout", listener);
	}

	/**
	 * Remove pointer out event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removePointerOutEventListener(Function listener, boolean options) {
		removeEventListener("pointerout", listener, options);
	}

	/**
	 * Remove pointer out event listener.
	 *
	 * @param listener the listener
	 */
	default void removePointerOutEventListener(Function listener) {
		removeEventListener("pointerout", listener);
	}

	/**
	 * Gets onpointerover.
	 *
	 * @return the onpointerover
	 */

	Function getOnpointerover();

	/**
	 * Sets onpointerover.
	 *
	 * @param onpointerover the onpointerover
	 */

	void setOnpointerover(Function onpointerover);

	/**
	 * Add pointer over event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addPointerOverEventListener(Function listener, boolean options) {
		addEventListener("pointerover", listener, options);
	}

	/**
	 * Add pointer over event listener.
	 *
	 * @param listener the listener
	 */
	default void addPointerOverEventListener(Function listener) {
		addEventListener("pointerover", listener);
	}

	/**
	 * Remove pointer over event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removePointerOverEventListener(Function listener, boolean options) {
		removeEventListener("pointerover", listener, options);
	}

	/**
	 * Remove pointer over event listener.
	 *
	 * @param listener the listener
	 */
	default void removePointerOverEventListener(Function listener) {
		removeEventListener("pointerover", listener);
	}

	/**
	 * Gets onpointerup.
	 *
	 * @return the onpointerup
	 */

	Function getOnpointerup();

	/**
	 * Sets onpointerup.
	 *
	 * @param onpointerup the onpointerup
	 */

	void setOnpointerup(Function onpointerup);

	/**
	 * Add pointer up event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addPointerUpEventListener(Function listener, boolean options) {
		addEventListener("pointerup", listener, options);
	}

	/**
	 * Add pointer up event listener.
	 *
	 * @param listener the listener
	 */
	default void addPointerUpEventListener(Function listener) {
		addEventListener("pointerup", listener);
	}

	/**
	 * Remove pointer up event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removePointerUpEventListener(Function listener, boolean options) {
		removeEventListener("pointerup", listener, options);
	}

	/**
	 * Remove pointer up event listener.
	 *
	 * @param listener the listener
	 */
	default void removePointerUpEventListener(Function listener) {
		removeEventListener("pointerup", listener);
	}

	/**
	 * Occurs to indicate progress while downloading media data.
	 *
	 * @return the onprogress
	 */

	Function getOnprogress();

	/**
	 * Sets onprogress.
	 *
	 * @param onprogress the onprogress
	 */

	void setOnprogress(Function onprogress);

	/**
	 * Add progress event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addProgressEventListener(Function listener, boolean options) {
		addEventListener("progress", listener, options);
	}

	/**
	 * Add progress event listener.
	 *
	 * @param listener the listener
	 */
	default void addProgressEventListener(Function listener) {
		addEventListener("progress", listener);
	}

	/**
	 * Remove progress event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeProgressEventListener(Function listener, boolean options) {
		removeEventListener("progress", listener, options);
	}

	/**
	 * Remove progress event listener.
	 *
	 * @param listener the listener
	 */
	default void removeProgressEventListener(Function listener) {
		removeEventListener("progress", listener);
	}

	/**
	 * Occurs when the playback rate is increased or decreased.
	 *
	 * @return the onratechange
	 */

	Function getOnratechange();

	/**
	 * Sets onratechange.
	 *
	 * @param onratechange the onratechange
	 */

	void setOnratechange(Function onratechange);

	/**
	 * Add rate change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addRateChangeEventListener(Function listener, boolean options) {
		addEventListener("ratechange", listener, options);
	}

	/**
	 * Add rate change event listener.
	 *
	 * @param listener the listener
	 */
	default void addRateChangeEventListener(Function listener) {
		addEventListener("ratechange", listener);
	}

	/**
	 * Remove rate change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeRateChangeEventListener(Function listener, boolean options) {
		removeEventListener("ratechange", listener, options);
	}

	/**
	 * Remove rate change event listener.
	 *
	 * @param listener the listener
	 */
	default void removeRateChangeEventListener(Function listener) {
		removeEventListener("ratechange", listener);
	}

	/**
	 * Fires when the user resets a form.
	 *
	 * @return the onreset
	 */

	Function getOnreset();

	/**
	 * Sets onreset.
	 *
	 * @param onreset the onreset
	 */

	void setOnreset(Function onreset);

	/**
	 * Add reset event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addResetEventListener(Function listener, boolean options) {
		addEventListener("reset", listener, options);
	}

	/**
	 * Add reset event listener.
	 *
	 * @param listener the listener
	 */
	default void addResetEventListener(Function listener) {
		addEventListener("reset", listener);
	}

	/**
	 * Remove reset event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeResetEventListener(Function listener, boolean options) {
		removeEventListener("reset", listener, options);
	}

	/**
	 * Remove reset event listener.
	 *
	 * @param listener the listener
	 */
	default void removeResetEventListener(Function listener) {
		removeEventListener("reset", listener);
	}

	/**
	 * Gets onresize.
	 *
	 * @return the onresize
	 */

	Function getOnresize();

	/**
	 * Sets onresize.
	 *
	 * @param onresize the onresize
	 */

	void setOnresize(Function onresize);

	/**
	 * Add resize event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addResizeEventListener(Function listener, boolean options) {
		addEventListener("resize", listener, options);
	}

	/**
	 * Add resize event listener.
	 *
	 * @param listener the listener
	 */
	default void addResizeEventListener(Function listener) {
		addEventListener("resize", listener);
	}

	/**
	 * Remove resize event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeResizeEventListener(Function listener, boolean options) {
		removeEventListener("resize", listener, options);
	}

	/**
	 * Remove resize event listener.
	 *
	 * @param listener the listener
	 */
	default void removeResizeEventListener(Function listener) {
		removeEventListener("resize", listener);
	}

	/**
	 * Fires when the user repositions the scroll box in the scroll bar on the
	 * object.
	 *
	 * @return the onscroll
	 */

	Function getOnscroll();

	/**
	 * Sets onscroll.
	 *
	 * @param onscroll the onscroll
	 */

	void setOnscroll(Function onscroll);

	/**
	 * Add scroll event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addScrollEventListener(Function listener, boolean options) {
		addEventListener("scroll", listener, options);
	}

	/**
	 * Add scroll event listener.
	 *
	 * @param listener the listener
	 */
	default void addScrollEventListener(Function listener) {
		addEventListener("scroll", listener);
	}

	/**
	 * Remove scroll event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeScrollEventListener(Function listener, boolean options) {
		removeEventListener("scroll", listener, options);
	}

	/**
	 * Remove scroll event listener.
	 *
	 * @param listener the listener
	 */
	default void removeScrollEventListener(Function listener) {
		removeEventListener("scroll", listener);
	}

	/**
	 * Gets onsecuritypolicyviolation.
	 *
	 * @return the onsecuritypolicyviolation
	 */

	Function getOnsecuritypolicyviolation();

	/**
	 * Sets onsecuritypolicyviolation.
	 *
	 * @param onsecuritypolicyviolation the onsecuritypolicyviolation
	 */

	void setOnsecuritypolicyviolation(Function onsecuritypolicyviolation);

	/**
	 * Add security policy violation event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addSecurityPolicyViolationEventListener(Function listener, boolean options) {
		addEventListener("securitypolicyviolation", listener, options);
	}

	/**
	 * Add security policy violation event listener.
	 *
	 * @param listener the listener
	 */
	default void addSecurityPolicyViolationEventListener(Function listener) {
		addEventListener("securitypolicyviolation", listener);
	}

	/**
	 * Remove security policy violation event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeSecurityPolicyViolationEventListener(Function listener, boolean options) {
		removeEventListener("securitypolicyviolation", listener, options);
	}

	/**
	 * Remove security policy violation event listener.
	 *
	 * @param listener the listener
	 */
	default void removeSecurityPolicyViolationEventListener(Function listener) {
		removeEventListener("securitypolicyviolation", listener);
	}

	/**
	 * Occurs when the seek operation ends.
	 *
	 * @return the onseeked
	 */

	Function getOnseeked();

	/**
	 * Sets onseeked.
	 *
	 * @param onseeked the onseeked
	 */

	void setOnseeked(Function onseeked);

	/**
	 * Add seeked event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addSeekedEventListener(Function listener, boolean options) {
		addEventListener("seeked", listener, options);
	}

	/**
	 * Add seeked event listener.
	 *
	 * @param listener the listener
	 */
	default void addSeekedEventListener(Function listener) {
		addEventListener("seeked", listener);
	}

	/**
	 * Remove seeked event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeSeekedEventListener(Function listener, boolean options) {
		removeEventListener("seeked", listener, options);
	}

	/**
	 * Remove seeked event listener.
	 *
	 * @param listener the listener
	 */
	default void removeSeekedEventListener(Function listener) {
		removeEventListener("seeked", listener);
	}

	/**
	 * Occurs when the current playback position is moved.
	 *
	 * @return the onseeking
	 */

	Function getOnseeking();

	/**
	 * Sets onseeking.
	 *
	 * @param onseeking the onseeking
	 */

	void setOnseeking(Function onseeking);

	/**
	 * Add seeking event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addSeekingEventListener(Function listener, boolean options) {
		addEventListener("seeking", listener, options);
	}

	/**
	 * Add seeking event listener.
	 *
	 * @param listener the listener
	 */
	default void addSeekingEventListener(Function listener) {
		addEventListener("seeking", listener);
	}

	/**
	 * Remove seeking event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeSeekingEventListener(Function listener, boolean options) {
		removeEventListener("seeking", listener, options);
	}

	/**
	 * Remove seeking event listener.
	 *
	 * @param listener the listener
	 */
	default void removeSeekingEventListener(Function listener) {
		removeEventListener("seeking", listener);
	}

	/**
	 * Fires when the current selection changes.
	 *
	 * @return the onselect
	 */

	Function getOnselect();

	/**
	 * Sets onselect.
	 *
	 * @param onselect the onselect
	 */

	void setOnselect(Function onselect);

	/**
	 * Add select event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addSelectEventListener(Function listener, boolean options) {
		addEventListener("select", listener, options);
	}

	/**
	 * Add select event listener.
	 *
	 * @param listener the listener
	 */
	default void addSelectEventListener(Function listener) {
		addEventListener("select", listener);
	}

	/**
	 * Remove select event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeSelectEventListener(Function listener, boolean options) {
		removeEventListener("select", listener, options);
	}

	/**
	 * Remove select event listener.
	 *
	 * @param listener the listener
	 */
	default void removeSelectEventListener(Function listener) {
		removeEventListener("select", listener);
	}

	/**
	 * Gets onselectionchange.
	 *
	 * @return the onselectionchange
	 */

	Function getOnselectionchange();

	/**
	 * Sets onselectionchange.
	 *
	 * @param onselectionchange the onselectionchange
	 */

	void setOnselectionchange(Function onselectionchange);

	/**
	 * Add selection change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addSelectionChangeEventListener(Function listener, boolean options) {
		addEventListener("selectionchange", listener, options);
	}

	/**
	 * Add selection change event listener.
	 *
	 * @param listener the listener
	 */
	default void addSelectionChangeEventListener(Function listener) {
		addEventListener("selectionchange", listener);
	}

	/**
	 * Remove selection change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeSelectionChangeEventListener(Function listener, boolean options) {
		removeEventListener("selectionchange", listener, options);
	}

	/**
	 * Remove selection change event listener.
	 *
	 * @param listener the listener
	 */
	default void removeSelectionChangeEventListener(Function listener) {
		removeEventListener("selectionchange", listener);
	}

	/**
	 * Gets onselectstart.
	 *
	 * @return the onselectstart
	 */

	Function getOnselectstart();

	/**
	 * Sets onselectstart.
	 *
	 * @param onselectstart the onselectstart
	 */

	void setOnselectstart(Function onselectstart);

	/**
	 * Add select start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addSelectStartEventListener(Function listener, boolean options) {
		addEventListener("selectstart", listener, options);
	}

	/**
	 * Add select start event listener.
	 *
	 * @param listener the listener
	 */
	default void addSelectStartEventListener(Function listener) {
		addEventListener("selectstart", listener);
	}

	/**
	 * Remove select start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeSelectStartEventListener(Function listener, boolean options) {
		removeEventListener("selectstart", listener, options);
	}

	/**
	 * Remove select start event listener.
	 *
	 * @param listener the listener
	 */
	default void removeSelectStartEventListener(Function listener) {
		removeEventListener("selectstart", listener);
	}

	/**
	 * Occurs when the download has stopped.
	 *
	 * @return the onstalled
	 */

	Function getOnstalled();

	/**
	 * Sets onstalled.
	 *
	 * @param onstalled the onstalled
	 */

	void setOnstalled(Function onstalled);

	/**
	 * Add stalled event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addStalledEventListener(Function listener, boolean options) {
		addEventListener("stalled", listener, options);
	}

	/**
	 * Add stalled event listener.
	 *
	 * @param listener the listener
	 */
	default void addStalledEventListener(Function listener) {
		addEventListener("stalled", listener);
	}

	/**
	 * Remove stalled event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeStalledEventListener(Function listener, boolean options) {
		removeEventListener("stalled", listener, options);
	}

	/**
	 * Remove stalled event listener.
	 *
	 * @param listener the listener
	 */
	default void removeStalledEventListener(Function listener) {
		removeEventListener("stalled", listener);
	}

	/**
	 * Gets onsubmit.
	 *
	 * @return the onsubmit
	 */

	Function getOnsubmit();

	/**
	 * Sets onsubmit.
	 *
	 * @param onsubmit the onsubmit
	 */

	void setOnsubmit(Function onsubmit);

	/**
	 * Add submit event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addSubmitEventListener(Function listener, boolean options) {
		addEventListener("submit", listener, options);
	}

	/**
	 * Add submit event listener.
	 *
	 * @param listener the listener
	 */
	default void addSubmitEventListener(Function listener) {
		addEventListener("submit", listener);
	}

	/**
	 * Remove submit event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeSubmitEventListener(Function listener, boolean options) {
		removeEventListener("submit", listener, options);
	}

	/**
	 * Remove submit event listener.
	 *
	 * @param listener the listener
	 */
	default void removeSubmitEventListener(Function listener) {
		removeEventListener("submit", listener);
	}

	/**
	 * Occurs if the load operation has been intentionally halted.
	 *
	 * @return the onsuspend
	 */

	Function getOnsuspend();

	/**
	 * Sets onsuspend.
	 *
	 * @param onsuspend the onsuspend
	 */

	void setOnsuspend(Function onsuspend);

	/**
	 * Add suspend event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addSuspendEventListener(Function listener, boolean options) {
		addEventListener("suspend", listener, options);
	}

	/**
	 * Add suspend event listener.
	 *
	 * @param listener the listener
	 */
	default void addSuspendEventListener(Function listener) {
		addEventListener("suspend", listener);
	}

	/**
	 * Remove suspend event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeSuspendEventListener(Function listener, boolean options) {
		removeEventListener("suspend", listener, options);
	}

	/**
	 * Remove suspend event listener.
	 *
	 * @param listener the listener
	 */
	default void removeSuspendEventListener(Function listener) {
		removeEventListener("suspend", listener);
	}

	/**
	 * Occurs to indicate the current playback position.
	 *
	 * @return the ontimeupdate
	 */

	Function getOntimeupdate();

	/**
	 * Sets ontimeupdate.
	 *
	 * @param ontimeupdate the ontimeupdate
	 */

	void setOntimeupdate(Function ontimeupdate);

	/**
	 * Add time update event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addTimeUpdateEventListener(Function listener, boolean options) {
		addEventListener("timeupdate", listener, options);
	}

	/**
	 * Add time update event listener.
	 *
	 * @param listener the listener
	 */
	default void addTimeUpdateEventListener(Function listener) {
		addEventListener("timeupdate", listener);
	}

	/**
	 * Remove time update event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeTimeUpdateEventListener(Function listener, boolean options) {
		removeEventListener("timeupdate", listener, options);
	}

	/**
	 * Remove time update event listener.
	 *
	 * @param listener the listener
	 */
	default void removeTimeUpdateEventListener(Function listener) {
		removeEventListener("timeupdate", listener);
	}

	/**
	 * Gets ontoggle.
	 *
	 * @return the ontoggle
	 */

	Function getOntoggle();

	/**
	 * Sets ontoggle.
	 *
	 * @param ontoggle the ontoggle
	 */

	void setOntoggle(Function ontoggle);

	/**
	 * Add toggle event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addToggleEventListener(Function listener, boolean options) {
		addEventListener("toggle", listener, options);
	}

	/**
	 * Add toggle event listener.
	 *
	 * @param listener the listener
	 */
	default void addToggleEventListener(Function listener) {
		addEventListener("toggle", listener);
	}

	/**
	 * Remove toggle event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeToggleEventListener(Function listener, boolean options) {
		removeEventListener("toggle", listener, options);
	}

	/**
	 * Remove toggle event listener.
	 *
	 * @param listener the listener
	 */
	default void removeToggleEventListener(Function listener) {
		removeEventListener("toggle", listener);
	}

	/**
	 * Gets ontouchcancel.
	 *
	 * @return the ontouchcancel
	 */

	Function getOntouchcancel();

	/**
	 * Sets ontouchcancel.
	 *
	 * @param ontouchcancel the ontouchcancel
	 */

	void setOntouchcancel(Function ontouchcancel);

	/**
	 * Add touch cancel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addTouchCancelEventListener(Function listener, boolean options) {
		addEventListener("touchcancel", listener, options);
	}

	/**
	 * Add touch cancel event listener.
	 *
	 * @param listener the listener
	 */
	default void addTouchCancelEventListener(Function listener) {
		addEventListener("touchcancel", listener);
	}

	/**
	 * Remove touch cancel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeTouchCancelEventListener(Function listener, boolean options) {
		removeEventListener("touchcancel", listener, options);
	}

	/**
	 * Remove touch cancel event listener.
	 *
	 * @param listener the listener
	 */
	default void removeTouchCancelEventListener(Function listener) {
		removeEventListener("touchcancel", listener);
	}

	/**
	 * Gets ontouchend.
	 *
	 * @return the ontouchend
	 */

	Function getOntouchend();

	/**
	 * Sets ontouchend.
	 *
	 * @param ontouchend the ontouchend
	 */

	void setOntouchend(Function ontouchend);

	/**
	 * Add touch end event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addTouchEndEventListener(Function listener, boolean options) {
		addEventListener("touchend", listener, options);
	}

	/**
	 * Add touch end event listener.
	 *
	 * @param listener the listener
	 */
	default void addTouchEndEventListener(Function listener) {
		addEventListener("touchend", listener);
	}

	/**
	 * Remove touch end event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeTouchEndEventListener(Function listener, boolean options) {
		removeEventListener("touchend", listener, options);
	}

	/**
	 * Remove touch end event listener.
	 *
	 * @param listener the listener
	 */
	default void removeTouchEndEventListener(Function listener) {
		removeEventListener("touchend", listener);
	}

	/**
	 * Gets ontouchmove.
	 *
	 * @return the ontouchmove
	 */

	Function getOntouchmove();

	/**
	 * Sets ontouchmove.
	 *
	 * @param ontouchmove the ontouchmove
	 */

	void setOntouchmove(Function ontouchmove);

	/**
	 * Add touch move event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addTouchMoveEventListener(Function listener, boolean options) {
		addEventListener("touchmove", listener, options);
	}

	/**
	 * Add touch move event listener.
	 *
	 * @param listener the listener
	 */
	default void addTouchMoveEventListener(Function listener) {
		addEventListener("touchmove", listener);
	}

	/**
	 * Remove touch move event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeTouchMoveEventListener(Function listener, boolean options) {
		removeEventListener("touchmove", listener, options);
	}

	/**
	 * Remove touch move event listener.
	 *
	 * @param listener the listener
	 */
	default void removeTouchMoveEventListener(Function listener) {
		removeEventListener("touchmove", listener);
	}

	/**
	 * Gets ontouchstart.
	 *
	 * @return the ontouchstart
	 */

	Function getOntouchstart();

	/**
	 * Sets ontouchstart.
	 *
	 * @param ontouchstart the ontouchstart
	 */

	void setOntouchstart(Function ontouchstart);

	/**
	 * Add touch start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addTouchStartEventListener(Function listener, boolean options) {
		addEventListener("touchstart", listener, options);
	}

	/**
	 * Add touch start event listener.
	 *
	 * @param listener the listener
	 */
	default void addTouchStartEventListener(Function listener) {
		addEventListener("touchstart", listener);
	}

	/**
	 * Remove touch start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeTouchStartEventListener(Function listener, boolean options) {
		removeEventListener("touchstart", listener, options);
	}

	/**
	 * Remove touch start event listener.
	 *
	 * @param listener the listener
	 */
	default void removeTouchStartEventListener(Function listener) {
		removeEventListener("touchstart", listener);
	}

	/**
	 * Gets ontransitioncancel.
	 *
	 * @return the ontransitioncancel
	 */

	Function getOntransitioncancel();

	/**
	 * Sets ontransitioncancel.
	 *
	 * @param ontransitioncancel the ontransitioncancel
	 */

	void setOntransitioncancel(Function ontransitioncancel);

	/**
	 * Add transition cancel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addTransitionCancelEventListener(Function listener, boolean options) {
		addEventListener("transitioncancel", listener, options);
	}

	/**
	 * Add transition cancel event listener.
	 *
	 * @param listener the listener
	 */
	default void addTransitionCancelEventListener(Function listener) {
		addEventListener("transitioncancel", listener);
	}

	/**
	 * Remove transition cancel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeTransitionCancelEventListener(Function listener, boolean options) {
		removeEventListener("transitioncancel", listener, options);
	}

	/**
	 * Remove transition cancel event listener.
	 *
	 * @param listener the listener
	 */
	default void removeTransitionCancelEventListener(Function listener) {
		removeEventListener("transitioncancel", listener);
	}

	/**
	 * Gets ontransitionend.
	 *
	 * @return the ontransitionend
	 */

	Function getOntransitionend();

	/**
	 * Sets ontransitionend.
	 *
	 * @param ontransitionend the ontransitionend
	 */

	void setOntransitionend(Function ontransitionend);

	/**
	 * Add transition end event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addTransitionEndEventListener(Function listener, boolean options) {
		addEventListener("transitionend", listener, options);
	}

	/**
	 * Add transition end event listener.
	 *
	 * @param listener the listener
	 */
	default void addTransitionEndEventListener(Function listener) {
		addEventListener("transitionend", listener);
	}

	/**
	 * Remove transition end event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeTransitionEndEventListener(Function listener, boolean options) {
		removeEventListener("transitionend", listener, options);
	}

	/**
	 * Remove transition end event listener.
	 *
	 * @param listener the listener
	 */
	default void removeTransitionEndEventListener(Function listener) {
		removeEventListener("transitionend", listener);
	}

	/**
	 * Gets ontransitionrun.
	 *
	 * @return the ontransitionrun
	 */

	Function getOntransitionrun();

	/**
	 * Sets ontransitionrun.
	 *
	 * @param ontransitionrun the ontransitionrun
	 */

	void setOntransitionrun(Function ontransitionrun);

	/**
	 * Add transition run event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addTransitionRunEventListener(Function listener, boolean options) {
		addEventListener("transitionrun", listener, options);
	}

	/**
	 * Add transition run event listener.
	 *
	 * @param listener the listener
	 */
	default void addTransitionRunEventListener(Function listener) {
		addEventListener("transitionrun", listener);
	}

	/**
	 * Remove transition run event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeTransitionRunEventListener(Function listener, boolean options) {
		removeEventListener("transitionrun", listener, options);
	}

	/**
	 * Remove transition run event listener.
	 *
	 * @param listener the listener
	 */
	default void removeTransitionRunEventListener(Function listener) {
		removeEventListener("transitionrun", listener);
	}

	/**
	 * Gets ontransitionstart.
	 *
	 * @return the ontransitionstart
	 */

	Function getOntransitionstart();

	/**
	 * Sets ontransitionstart.
	 *
	 * @param ontransitionstart the ontransitionstart
	 */

	void setOntransitionstart(Function ontransitionstart);

	/**
	 * Add transition start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addTransitionStartEventListener(Function listener, boolean options) {
		addEventListener("transitionstart", listener, options);
	}

	/**
	 * Add transition start event listener.
	 *
	 * @param listener the listener
	 */
	default void addTransitionStartEventListener(Function listener) {
		addEventListener("transitionstart", listener);
	}

	/**
	 * Remove transition start event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeTransitionStartEventListener(Function listener, boolean options) {
		removeEventListener("transitionstart", listener, options);
	}

	/**
	 * Remove transition start event listener.
	 *
	 * @param listener the listener
	 */
	default void removeTransitionStartEventListener(Function listener) {
		removeEventListener("transitionstart", listener);
	}

	/**
	 * Occurs when the volume is changed, or playback is muted or unmuted.
	 *
	 * @return the onvolumechange
	 */

	Function getOnvolumechange();

	/**
	 * Sets onvolumechange.
	 *
	 * @param onvolumechange the onvolumechange
	 */

	void setOnvolumechange(Function onvolumechange);

	/**
	 * Add volume change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addVolumeChangeEventListener(Function listener, boolean options) {
		addEventListener("volumechange", listener, options);
	}

	/**
	 * Add volume change event listener.
	 *
	 * @param listener the listener
	 */
	default void addVolumeChangeEventListener(Function listener) {
		addEventListener("volumechange", listener);
	}

	/**
	 * Remove volume change event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeVolumeChangeEventListener(Function listener, boolean options) {
		removeEventListener("volumechange", listener, options);
	}

	/**
	 * Remove volume change event listener.
	 *
	 * @param listener the listener
	 */
	default void removeVolumeChangeEventListener(Function listener) {
		removeEventListener("volumechange", listener);
	}

	/**
	 * Occurs when playback stops because the next frame of a video resource is not
	 * available.
	 *
	 * @return the onwaiting
	 */

	Function getOnwaiting();

	/**
	 * Sets onwaiting.
	 *
	 * @param onwaiting the onwaiting
	 */

	void setOnwaiting(Function onwaiting);

	/**
	 * Add waiting event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addWaitingEventListener(Function listener, boolean options) {
		addEventListener("waiting", listener, options);
	}

	/**
	 * Add waiting event listener.
	 *
	 * @param listener the listener
	 */
	default void addWaitingEventListener(Function listener) {
		addEventListener("waiting", listener);
	}

	/**
	 * Remove waiting event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeWaitingEventListener(Function listener, boolean options) {
		removeEventListener("waiting", listener, options);
	}

	/**
	 * Remove waiting event listener.
	 *
	 * @param listener the listener
	 */
	default void removeWaitingEventListener(Function listener) {
		removeEventListener("waiting", listener);
	}

	/**
	 * Gets onwheel.
	 *
	 * @return the onwheel
	 */

	Function getOnwheel();

	/**
	 * Sets onwheel.
	 *
	 * @param onwheel the onwheel
	 */

	void setOnwheel(Function onwheel);

	/**
	 * Add wheel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void addWheelEventListener(Function listener, boolean options) {
		addEventListener("wheel", listener, options);
	}

	/**
	 * Add wheel event listener.
	 *
	 * @param listener the listener
	 */
	default void addWheelEventListener(Function listener) {
		addEventListener("wheel", listener);
	}

	/**
	 * Remove wheel event listener.
	 *
	 * @param listener the listener
	 * @param options  the options
	 */
	default void removeWheelEventListener(Function listener, boolean options) {
		removeEventListener("wheel", listener, options);
	}

	/**
	 * Remove wheel event listener.
	 *
	 * @param listener the listener
	 */
	default void removeWheelEventListener(Function listener) {
		removeEventListener("wheel", listener);
	}
}
