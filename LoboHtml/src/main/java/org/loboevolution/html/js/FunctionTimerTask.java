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

	public FunctionTimerTask(Window window, Integer timeIDInt, Function function, boolean removeTask) {
		super(window);
		this.timeIDInt = timeIDInt;
		this.functionRef = new WeakReference<Function>(function);
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
