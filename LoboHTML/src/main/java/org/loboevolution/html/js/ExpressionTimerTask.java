/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.js.LoboContextFactory;
import org.mozilla.javascript.Context;

import java.awt.event.ActionEvent;

/**
 * The class ExpressionTimerTask.
 */
@Slf4j
class ExpressionTimerTask extends WeakWindowTask {
	
	private final String expression;
	
	private final boolean removeTask;
	
	private final Integer timeIDInt;

	/**
	 * <p>Constructor for ExpressionTimerTask.</p>
	 *
	 * @param window a {@link org.loboevolution.html.js.WindowImpl} object.
	 * @param timeIDInt a {@link java.lang.Integer} object.
	 * @param expression a {@link java.lang.String} object.
	 * @param removeTask a boolean.
	 */
	public ExpressionTimerTask(final WindowImpl window, final Integer timeIDInt, final String expression, final boolean removeTask) {
		super(window);
		this.timeIDInt = timeIDInt;
		this.expression = expression;
		this.removeTask = removeTask;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// This executes in the GUI thread and that's good.
		try {
			final WindowImpl window = this.getWindow();
			if (window == null) {
				log.info("actionPerformed(): WindowImpl is no longer available.");
				return;
			}
			if (this.removeTask) {
				window.forgetTask(this.timeIDInt, false);
			}
			final HTMLDocumentImpl doc = (HTMLDocumentImpl) window.getDocument();
			if (doc == null) {
				throw new IllegalStateException("Cannot perform operation when document is unset.");
			}
			evalInScope(window, this.expression);
		} catch (final Throwable err) {
			log.error("actionPerformed()", err);
		}
	}

	private void evalInScope(final WindowImpl window, final String javascript) {
		LoboContextFactory contextFactory =  window.getContextFactory();
		try (Context ctx = contextFactory.enterContext()) {
			final String scriptURI = "window.eval";
			ctx.evaluateString(window.getWindowScope(ctx), javascript, scriptURI, 1, null);
		}
	}
}
