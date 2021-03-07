/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.js;

import java.awt.event.ActionEvent;
import java.lang.ref.WeakReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.mozilla.javascript.Function;

class FunctionTimerTask extends WeakWindowTask {
	
	private static final Logger logger = Logger.getLogger(FunctionTimerTask.class.getName());
	
	private final WeakReference<Function> functionRef;

	private final boolean removeTask;

	private final Integer timeIDInt;

	public FunctionTimerTask(WindowImpl window, Integer timeIDInt, Function function, boolean removeTask) {
		super(window);
		this.timeIDInt = timeIDInt;
		this.functionRef = new WeakReference<>(function);
		this.removeTask = removeTask;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// This executes in the GUI thread and that's good.
		try {
			final WindowImpl window = this.getWindow();
			if (window == null) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("actionPerformed(): WindowImpl is no longer available.");
				}
				return;
			}
			if (this.removeTask) {
				window.forgetTask(this.timeIDInt, false);
			}
			final HTMLDocumentImpl doc = (HTMLDocumentImpl) window.getDocument();
			if (doc == null) {
				throw new IllegalStateException("Cannot perform operation when document is unset.");
			}
			final Function function = this.functionRef.get();
			if (function == null) {
				throw new IllegalStateException("Cannot perform operation. Function is no longer available.");
			}
			Executor.executeFunction(window.getWindowScope(), function, doc.getDocumentURL(), window.getUserAgentContext());
		} catch (final Throwable err) {
			logger.log(Level.WARNING, "actionPerformed()", err);
		}
	}
}
