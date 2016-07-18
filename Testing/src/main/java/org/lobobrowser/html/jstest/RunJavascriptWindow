package org.lobobrowser.html.jstest;

import org.lobobrowser.js.JavaScript;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class RunJavascriptWindow {
	public static void main(String[] args) {

		String evaluationScript = "window.console, n = window.navigator, o = window.document;";

		// Create and enter a Context. A Context stores information about the
		// execution environment of a script.
		Context cx = Context.enter();
		try {
			// Initialize the standard objects (Object, Function, etc.). This
			// must be done before scripts can be
			// executed. The null parameter tells initStandardObjects
			// to create and return a scope object that we use
			// in later calls.
			Scriptable scope = cx.initStandardObjects();
			
			// Create a Window object to evaluate.
			Window win = new Window();
			
			// Pass the Window Java object to the JavaScript
			Object winJSObj = JavaScript.getInstance().getJavascriptObject(win, scope);
			ScriptableObject.putProperty(scope, "window", winJSObj);
			
			// Execute the script
			cx.evaluateString(scope, evaluationScript, "RunJavascriptWindow", 1, null);
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Exit the Context. This removes the association between the Context and the current thread and is an
			// essential cleanup action. There should be a call to exit for every call to enter.
			Context.exit();
		}
	}
}
