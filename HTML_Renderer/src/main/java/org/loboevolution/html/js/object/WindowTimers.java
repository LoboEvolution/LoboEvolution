/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.js.object;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.loboevolution.html.js.ExpressionTimerTask;
import org.loboevolution.html.js.FunctionTimerTask;
import org.loboevolution.html.js.object.WindowTimers;
import org.loboevolution.http.UserAgentContext;
import org.mozilla.javascript.Function;

public class WindowTimers extends JSGlobalFunction {

	/** The timer id counter. */
	private static int timerIdCounter = 0;

	public WindowTimers(UserAgentContext uaContext) {
		super(uaContext);
	}

	public int setTimeout(Function aFunction, int timeout) {
		return timeout(aFunction, timeout, false);
	}

	public int setTimeout(Object code, int timeout) {
		return timeout(code, timeout, false);
	}

	public int setTimeout(Object code, int timeout, Object[] args) {
		return timeout(code, timeout, false);
	}

	public void clearTimeout(int handle) {
		Integer key = Integer.valueOf(handle);
		this.forgetTask(key, true);
	}

	public int setInterval(Function aFunction, int timeout) {
		return timeout(aFunction, timeout, true);
	}

	public int setInterval(Object code, int timeout, Object[] args) {
		return setInterval(code, timeout);
	}

	public int setInterval(Object code, int timeout) {
		return timeout(code, timeout, true);
	}

	private int timeout(Object code, int timeout, boolean repeats) {
		if (timeout > Integer.MAX_VALUE || timeout < 0) {
			throw new IllegalArgumentException("Timeout value " + timeout + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = Integer.valueOf(timeID);
		ActionListener task = new ExpressionTimerTask(getWindowImpl(), timeIDInt, code, false);
		if (timeout < 1) {
			timeout = 1;
		}
		Timer timer = new Timer(timeout, task);
		timer.setRepeats(repeats);
		this.putAndStartTask(timeIDInt, timer);
		return timeID;
	}

	private int timeout(Function aFunction, int timeout, boolean repeats) {
		if (timeout > Integer.MAX_VALUE || timeout < 0) {
			throw new IllegalArgumentException("Timeout value " + timeout + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = Integer.valueOf(timeID);
		ActionListener task = new FunctionTimerTask(getWindowImpl(), timeIDInt, aFunction, false);
		if (timeout < 1) {
			timeout = 1;
		}
		Timer timer = new Timer(timeout, task);
		timer.setRepeats(repeats);
		this.putAndStartTask(timeIDInt, timer);
		return timeID;
	}

	public void clearInterval(int handle) {
		Integer key = Integer.valueOf(handle);
		this.forgetTask(key, true);
	}

	private static int generateTimerID() {
		return timerIdCounter++;
	}
}
