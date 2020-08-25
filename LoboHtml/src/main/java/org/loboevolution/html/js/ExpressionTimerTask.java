package org.loboevolution.html.js;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.mozilla.javascript.Context;

class ExpressionTimerTask extends WeakWindowTask {
	
	private static final Logger logger = Logger.getLogger(ExpressionTimerTask.class.getName());
	
	private final String expression;
	
	private final boolean removeTask;
	
	private final Integer timeIDInt;

	public ExpressionTimerTask(Window window, Integer timeIDInt, String expression, boolean removeTask) {
		super(window);
		this.timeIDInt = timeIDInt;
		this.expression = expression;
		this.removeTask = removeTask;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// This executes in the GUI thread and that's good.
		try {
			final Window window = this.getWindow();
			if (window == null) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("actionPerformed(): Window is no longer available.");
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
			evalInScope(window, this.expression);
		} catch (final Throwable err) {
			logger.log(Level.WARNING, "actionPerformed()", err);
		}
	}
	
	private Object evalInScope(Window window, final String javascript) {
		HTMLDocumentImpl document = (HTMLDocumentImpl)window.getDocumentNode();
		final Context ctx = Executor.createContext(document.getDocumentURL(), window.getUaContext());
		try {
			final String scriptURI = "window.eval";
			return ctx.evaluateString(window.getWindowScope(), javascript, scriptURI, 1, null);
		} finally {
			Context.exit();
		}
	}
}