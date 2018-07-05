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
package org.loboevolution.html.js;

import java.awt.event.ActionEvent;
import java.lang.ref.WeakReference;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.js.object.Window;
import org.mozilla.javascript.Function;

/**
 * The Class FunctionTimerTask.
 */
public class FunctionTimerTask extends WeakWindowTask {
	// Implemented as a static WeakWindowTask to allow the Window
	// to get garbage collected, especially in infinite loop
	// scenarios.
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(FunctionTimerTask.class);
	
	/** The time id int. */
	private final Integer timeIDInt;

	/** The function ref. */
	private final WeakReference<Function> functionRef;

	/** The remove task. */
	private final boolean removeTask;

	/**
	 * Instantiates a new function timer task.
	 *
	 * @param window
	 *            the window
	 * @param timeIDInt
	 *            the time id int
	 * @param function
	 *            the function
	 * @param removeTask
	 *            the remove task
	 */
	public FunctionTimerTask(Window window, Integer timeIDInt, Function function, boolean removeTask) {
		super(window);
		this.timeIDInt = timeIDInt;
		this.functionRef = new WeakReference<Function>(function);
		this.removeTask = removeTask;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// This executes in the GUI thread and that's good.
		try {
			Window window = this.getWindow();
			if (window == null) {
				return;
			}
			if (this.removeTask) {
				window.forgetTask(this.timeIDInt, false);
			}
			HTMLDocumentImpl doc = (HTMLDocumentImpl) window.getDocument();
			if (doc == null) {
				throw new IllegalStateException("Cannot perform operation when document is unset.");
			}
			Function function = this.functionRef.get();
			if (function == null) {
				throw new IllegalStateException("Cannot perform operation. Function is no longer available.");
			}
			Executor.executeFunction(window.getWindowScope(), function, doc.getDocumentURL(),
					window.getUserAgentContext());
		} catch (Throwable err) {
			logger.error(err);
		}
	}
}
