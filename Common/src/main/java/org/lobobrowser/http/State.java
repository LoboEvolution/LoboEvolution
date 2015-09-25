/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.lobobrowser.http;

/**
 * Enumeration indicating the state that a Session is in.
 */
public enum State {
    READY, CONNECTING, SENDING, SENT, RECEIVING, DONE, FAILED, ABORTED
}
