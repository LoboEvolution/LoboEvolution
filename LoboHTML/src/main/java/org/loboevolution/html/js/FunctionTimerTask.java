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

package org.loboevolution.html.js;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.mozilla.javascript.Function;

import java.awt.event.ActionEvent;
import java.lang.ref.WeakReference;
import java.util.logging.Level;
import java.util.logging.Logger;

class FunctionTimerTask extends WeakWindowTask {
	
	private static final Logger logger = Logger.getLogger(FunctionTimerTask.class.getName());
	
	private final WeakReference<Function> functionRef;

	private final boolean removeTask;

	private final Integer timeIDInt;

	/**
	 * <p>Constructor for FunctionTimerTask.</p>
	 *
	 * @param window a {@link org.loboevolution.html.js.WindowImpl} object.
	 * @param timeIDInt a {@link java.lang.Integer} object.
	 * @param function a {@link org.mozilla.javascript.Function} object.
	 * @param removeTask a boolean.
	 */
	public FunctionTimerTask(WindowImpl window, Integer timeIDInt, Function function, boolean removeTask) {
		super(window);
		this.timeIDInt = timeIDInt;
		this.functionRef = new WeakReference<>(function);
		this.removeTask = removeTask;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {

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
			if (function != null) {
				Executor.executeFunction(window.getWindowScope(), function, doc.getDocumentURL(), window.getUserAgentContext());
			}

		} catch (final Throwable err) {
			logger.log(Level.WARNING, "actionPerformed()", err);
		}
	}
}
