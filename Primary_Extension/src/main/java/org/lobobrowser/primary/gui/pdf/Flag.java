/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui.pdf;

/**
 * A generic synchronized flag, because Java doesn't have one.
 */
public class Flag {
	
	/** The is set. */
	private boolean isSet;

	/**
	 * Sets the flag. Any pending waitForFlag calls will now return.
	 */
	public synchronized void set() {
		isSet = true;
		notifyAll();
	}

	/**
	 * Clears the flag. Do this before calling waitForFlag.
	 */
	public synchronized void clear() {
		isSet = false;
	}

	/**
	 * Waits for the flag to be set, if it is not set already. This method
	 * catches InterruptedExceptions, so if you want notification of
	 * interruptions, use interruptibleWaitForFlag instead.
	 */
	public synchronized void waitForFlag() {
		if (!isSet) {
			try {
				wait();
			} catch (InterruptedException ie) {
			}
		}
	}

	/**
	 * Waits for the flag to be set, if it is not set already.
	 */
	public synchronized void interruptibleWaitForFlag() throws InterruptedException {
		if (!isSet) {
			wait();
		}
	}
}
