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

package org.loboevolution.html.node.js;

import org.loboevolution.html.node.events.EventTarget;
import org.mozilla.javascript.Function;

/**
 * <p>WindowEventHandlers interface.</p>
 *
 *
 *
 */
public interface WindowEventHandlers extends EventTarget {

	/**
	 * <p>getOnafterprint.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	Function getOnafterprint();

	/**
	 * <p>getOnbeforeprint.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	Function getOnbeforeprint();

	/**
	 * <p>getOnlanguagechange.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	Function getOnlanguagechange();

	/**
	 * <p>getOnoffline.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	Function getOnoffline();

	/**
	 * <p>getOnonline.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	Function getOnonline();

	/**
	 * <p>getOnunload.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	Function getOnunload();

	/**
	 * <p>setOnunload.</p>
	 *
	 * @param onunload a {@link org.mozilla.javascript.Function} object.
	 */
	void setOnunload(Function onunload);

	/**
	 * <p>setOnonline.</p>
	 *
	 * @param ononline a {@link org.mozilla.javascript.Function} object.
	 */
	void setOnonline(Function ononline);

	/**
	 * <p>setOnoffline.</p>
	 *
	 * @param onoffline a {@link org.mozilla.javascript.Function} object.
	 */
	void setOnoffline(Function onoffline);

	/**
	 * <p>setOnlanguagechange.</p>
	 *
	 * @param onlanguagechange a {@link org.mozilla.javascript.Function} object.
	 */
	void setOnlanguagechange(Function onlanguagechange);

	/**
	 * <p>setOnbeforeprint.</p>
	 *
	 * @param onbeforeprint a {@link org.mozilla.javascript.Function} object.
	 */
	void setOnbeforeprint(Function onbeforeprint);

	/**
	 * <p>setOnafterprint.</p>
	 *
	 * @param onafterprint a {@link org.mozilla.javascript.Function} object.
	 */
	void setOnafterprint(Function onafterprint);

	/**
	 * <p>addAfterPrintEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void addAfterPrintEventListener(Function listener, boolean options) {
		addEventListener("afterprint", listener, options);
	}

	/**
	 * <p>addAfterPrintEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void addAfterPrintEventListener(Function listener) {
		addEventListener("afterprint", listener);
	}

	/**
	 * <p>removeAfterPrintEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void removeAfterPrintEventListener(Function listener, boolean options) {
		removeEventListener("afterprint", listener, options);
	}

	/**
	 * <p>removeAfterPrintEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void removeAfterPrintEventListener(Function listener) {
		removeEventListener("afterprint", listener);
	}

	/**
	 * <p>addBeforePrintEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void addBeforePrintEventListener(Function listener, boolean options) {
		addEventListener("beforeprint", listener, options);
	}

	/**
	 * <p>addBeforePrintEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void addBeforePrintEventListener(Function listener) {
		addEventListener("beforeprint", listener);
	}

	/**
	 * <p>removeBeforePrintEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void removeBeforePrintEventListener(Function listener, boolean options) {
		removeEventListener("beforeprint", listener, options);
	}

	/**
	 * <p>removeBeforePrintEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void removeBeforePrintEventListener(Function listener) {
		removeEventListener("beforeprint", listener);
	}

	/**
	 * <p>addLanguageChangeEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void addLanguageChangeEventListener(Function listener, boolean options) {
		addEventListener("languagechange", listener, options);
	}

	/**
	 * <p>addLanguageChangeEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void addLanguageChangeEventListener(Function listener) {
		addEventListener("languagechange", listener);
	}

	/**
	 * <p>removeLanguageChangeEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void removeLanguageChangeEventListener(Function listener, boolean options) {
		removeEventListener("languagechange", listener, options);
	}

	/**
	 * <p>removeLanguageChangeEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void removeLanguageChangeEventListener(Function listener) {
		removeEventListener("languagechange", listener);
	}

	/**
	 * <p>addOfflineEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void addOfflineEventListener(Function listener, boolean options) {
		addEventListener("offline", listener, options);
	}

	/**
	 * <p>addOfflineEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void addOfflineEventListener(Function listener) {
		addEventListener("offline", listener);
	}

	/**
	 * <p>removeOfflineEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void removeOfflineEventListener(Function listener, boolean options) {
		removeEventListener("offline", listener, options);
	}

	/**
	 * <p>removeOfflineEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void removeOfflineEventListener(Function listener) {
		removeEventListener("offline", listener);
	}

	/**
	 * <p>addOnlineEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void addOnlineEventListener(Function listener, boolean options) {
		addEventListener("online", listener, options);
	}

	/**
	 * <p>addOnlineEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void addOnlineEventListener(Function listener) {
		addEventListener("online", listener);
	}

	/**
	 * <p>removeOnlineEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void removeOnlineEventListener(Function listener, boolean options) {
		removeEventListener("online", listener, options);
	}

	/**
	 * <p>removeOnlineEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void removeOnlineEventListener(Function listener) {
		removeEventListener("online", listener);
	}

	/**
	 * <p>addRejectionHandledEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void addRejectionHandledEventListener(Function listener, boolean options) {
		addEventListener("rejectionhandled", listener, options);
	}

	/**
	 * <p>addRejectionHandledEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void addRejectionHandledEventListener(Function listener) {
		addEventListener("rejectionhandled", listener);
	}

	/**
	 * <p>removeRejectionHandledEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void removeRejectionHandledEventListener(Function listener, boolean options) {
		removeEventListener("rejectionhandled", listener, options);
	}

	/**
	 * <p>removeRejectionHandledEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void removeRejectionHandledEventListener(Function listener) {
		removeEventListener("rejectionhandled", listener);
	}

	/**
	 * <p>addUnloadEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void addUnloadEventListener(Function listener, boolean options) {
		addEventListener("unload", listener, options);
	}

	/**
	 * <p>addUnloadEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void addUnloadEventListener(Function listener) {
		addEventListener("unload", listener);
	}

	/**
	 * <p>removeUnloadEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param options a boolean.
	 */
	default void removeUnloadEventListener(Function listener, boolean options) {
		removeEventListener("unload", listener, options);
	}

	/**
	 * <p>removeUnloadEventListener.</p>
	 *
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	default void removeUnloadEventListener(Function listener) {
		removeEventListener("unload", listener);
	}
}
