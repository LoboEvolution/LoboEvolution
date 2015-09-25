package org.lobobrowser.http;

/**
 * Indicates the state of this HttpRequestImpl.
 */
public enum ReadyState {
    UNINITIALIZED, OPEN, SENT, RECEIVING, LOADED, LOADING, INTERACTIVE, COMPLETE
}
