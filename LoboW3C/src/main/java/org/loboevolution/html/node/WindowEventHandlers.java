package org.loboevolution.html.node;

import org.loboevolution.html.node.events.EventTarget;
import org.mozilla.javascript.Function;

public interface WindowEventHandlers extends EventTarget {

	Function getOnafterprint();

	Function getOnbeforeprint();

	Function getOnlanguagechange();

	Function getOnoffline();

	Function getOnonline();

	Function getOnunload();

	void setOnunload(Function onunload);

	void setOnonline(Function ononline);

	void setOnoffline(Function onoffline);

	void setOnlanguagechange(Function onlanguagechange);

	void setOnbeforeprint(Function onbeforeprint);

	void setOnafterprint(Function onafterprint);

	default void addAfterPrintEventListener(Function listener, boolean options) {
		addEventListener("afterprint", listener, options);
	}

	default void addAfterPrintEventListener(Function listener) {
		addEventListener("afterprint", listener);
	}

	default void removeAfterPrintEventListener(Function listener, boolean options) {
		removeEventListener("afterprint", listener, options);
	}

	default void removeAfterPrintEventListener(Function listener) {
		removeEventListener("afterprint", listener);
	}

	default void addBeforePrintEventListener(Function listener, boolean options) {
		addEventListener("beforeprint", listener, options);
	}

	default void addBeforePrintEventListener(Function listener) {
		addEventListener("beforeprint", listener);
	}

	default void removeBeforePrintEventListener(Function listener, boolean options) {
		removeEventListener("beforeprint", listener, options);
	}

	default void removeBeforePrintEventListener(Function listener) {
		removeEventListener("beforeprint", listener);
	}

	default void addLanguageChangeEventListener(Function listener, boolean options) {
		addEventListener("languagechange", listener, options);
	}

	default void addLanguageChangeEventListener(Function listener) {
		addEventListener("languagechange", listener);
	}

	default void removeLanguageChangeEventListener(Function listener, boolean options) {
		removeEventListener("languagechange", listener, options);
	}

	default void removeLanguageChangeEventListener(Function listener) {
		removeEventListener("languagechange", listener);
	}

	default void addOfflineEventListener(Function listener, boolean options) {
		addEventListener("offline", listener, options);
	}

	default void addOfflineEventListener(Function listener) {
		addEventListener("offline", listener);
	}

	default void removeOfflineEventListener(Function listener, boolean options) {
		removeEventListener("offline", listener, options);
	}

	default void removeOfflineEventListener(Function listener) {
		removeEventListener("offline", listener);
	}

	default void addOnlineEventListener(Function listener, boolean options) {
		addEventListener("online", listener, options);
	}

	default void addOnlineEventListener(Function listener) {
		addEventListener("online", listener);
	}

	default void removeOnlineEventListener(Function listener, boolean options) {
		removeEventListener("online", listener, options);
	}

	default void removeOnlineEventListener(Function listener) {
		removeEventListener("online", listener);
	}

	default void addRejectionHandledEventListener(Function listener, boolean options) {
		addEventListener("rejectionhandled", listener, options);
	}

	default void addRejectionHandledEventListener(Function listener) {
		addEventListener("rejectionhandled", listener);
	}

	default void removeRejectionHandledEventListener(Function listener, boolean options) {
		removeEventListener("rejectionhandled", listener, options);
	}

	default void removeRejectionHandledEventListener(Function listener) {
		removeEventListener("rejectionhandled", listener);
	}

	default void addUnloadEventListener(Function listener, boolean options) {
		addEventListener("unload", listener, options);
	}

	default void addUnloadEventListener(Function listener) {
		addEventListener("unload", listener);
	}

	default void removeUnloadEventListener(Function listener, boolean options) {
		removeEventListener("unload", listener, options);
	}

	default void removeUnloadEventListener(Function listener) {
		removeEventListener("unload", listener);
	}
}