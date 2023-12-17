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

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.js.JavaScript;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * <p>Executor class.</p>
 */
@Slf4j
public class Executor {

	/**
	 * A document UserData key used to map Javascript scope in the HTML
	 * document.
	 */
	public static final String SCOPE_KEY = "cobra.js.scope";

	/**
	 * This method should be invoked instead of Context.enter.
	 * @return a {@link org.mozilla.javascript.Context} object.
	 */
	public static Context createContext() {
		final Context ctx = Context.enter();
		ctx.setLanguageVersion(Context.VERSION_1_8);
		ctx.setOptimizationLevel(-1);
		return ctx;
	}

	/**
	 * <p>executeFunction.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 * @param f a {@link org.mozilla.javascript.Function} object.
	 * @param event a {@link org.loboevolution.html.node.events.Event} object.
	 * @param obj an array of {@link java.lang.Object} objects.
	 * @return a boolean.
	 */
	public static boolean executeFunction(final NodeImpl element, final Function f, final Event event, final Object[] obj) {
		return Executor.executeFunction(element, element, f, event, obj);
	}

	/**
	 * <p>executeFunction.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 * @param thisObject a {@link java.lang.Object} object.
	 * @param f a {@link org.mozilla.javascript.Function} object.
	 * @param event a {@link org.loboevolution.html.node.events.Event} object.
	 * @param obj an array of {@link java.lang.Object} objects.
	 * @return a boolean.
	 */
	public static boolean executeFunction(final NodeImpl element, final Object thisObject, final Function f, final Event event, final Object[] obj) {
		final Document doc = element.getOwnerDocument();
		if (doc == null) {
			throw new IllegalStateException("Element does not belong to a document.");
		}
		final Context ctx = createContext();
		try {
			final Scriptable scope = (Scriptable) doc.getUserData(Executor.SCOPE_KEY);
			if (scope == null) {
				throw new IllegalStateException(
						"Scriptable (scope) instance was expected to be keyed as UserData to document using "
								+ Executor.SCOPE_KEY);
			}
			final JavaScript js = JavaScript.getInstance();
			final Scriptable thisScope = (Scriptable) js.getJavascriptObject(thisObject, scope);
			try {
				final Scriptable eventScriptable = (Scriptable) js.getJavascriptObject(event, thisScope);
				ScriptableObject.defineProperty(thisScope, "event", eventScriptable, ScriptableObject.READONLY);
				final Object result = f.call(ctx, thisScope, thisScope, obj);
				if (!(result instanceof Boolean)) {
					return true;
				}
				return (Boolean) result;
			} catch (final Throwable thrown) {
				log.error("executeFunction(): There was an error in Javascript code.", thrown);
				return true;
			}
		} finally {
			Context.exit();
		}
	}

	/**
	 * <p>executeFunction.</p>
	 *
	 * @param thisScope a {@link org.mozilla.javascript.Scriptable} object.
	 * @param f a {@link org.mozilla.javascript.Function} object.
	 * @return a boolean.
	 */
	public static boolean executeFunction(final Scriptable thisScope, final Function f) {
		final Context ctx = createContext();
		try {
			try {
				final Object result = f.call(ctx, thisScope, thisScope, new Object[0]);
				if (!(result instanceof Boolean)) {
					return true;
				}
				return (Boolean) result;
			} catch (final Throwable err) {
				log.error("executeFunction(): Unable to execute Javascript function {} ", f.getClassName(), err);
				return true;
			}
		} finally {
			Context.exit();
		}
	}
}
