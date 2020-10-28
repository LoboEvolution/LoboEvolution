/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.html.js;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.html.dom.domimpl.NodeImpl;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.JavaScript;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.w3c.dom.Document;
import org.w3c.dom.events.Event;

/**
 * <p>Executor class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class Executor {
	private static final Logger logger = Logger.getLogger(Executor.class.getName());

	/**
	 * A document UserData key used to map Javascript scope in the HTML
	 * document.
	 */
	public static final String SCOPE_KEY = "cobra.js.scope";

	/**
	 * This method should be invoked instead of Context.enter.
	 *
	 * @param codeSource a {@link java.net.URL} object.
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @return a {@link org.mozilla.javascript.Context} object.
	 */
	public static Context createContext(URL codeSource, UserAgentContext ucontext) {
		final Context ctx = Context.enter();
		ctx.setLanguageVersion(Context.VERSION_1_8);
		ctx.setOptimizationLevel(-1);
		return ctx;
	}

	/**
	 * <p>executeFunction.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 * @param f a {@link org.mozilla.javascript.Function} object.
	 * @param event a {@link org.w3c.dom.events.Event} object.
	 * @param obj an array of {@link java.lang.Object} objects.
	 * @return a boolean.
	 */
	public static boolean executeFunction(NodeImpl element, Function f, Event event, Object[] obj) {
		return Executor.executeFunction(element, element, f, event, obj);
	}

	/**
	 * <p>executeFunction.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 * @param thisObject a {@link java.lang.Object} object.
	 * @param f a {@link org.mozilla.javascript.Function} object.
	 * @param event a {@link org.w3c.dom.events.Event} object.
	 * @param obj an array of {@link java.lang.Object} objects.
	 * @return a boolean.
	 */
	public static boolean executeFunction(NodeImpl element, Object thisObject, Function f, Event event, Object[] obj) {
		final Document doc = element.getOwnerDocument();
		if (doc == null) {
			throw new IllegalStateException("Element does not belong to a document.");
		}
		final Context ctx = createContext(element.getDocumentURL(), element.getUserAgentContext());
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
				logger.log(Level.WARNING, "executeFunction(): There was an error in Javascript code.", thrown);
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
	 * @param codeSource a {@link java.net.URL} object.
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @return a boolean.
	 */
	public static boolean executeFunction(Scriptable thisScope, Function f, URL codeSource,
			UserAgentContext ucontext) {
		final Context ctx = createContext(codeSource, ucontext);
		try {
			try {
				final Object result = f.call(ctx, thisScope, thisScope, new Object[0]);
				if (!(result instanceof Boolean)) {
					return true;
				}
				return (Boolean) result;
			} catch (final Throwable err) {
				logger.log(Level.WARNING,
						"executeFunction(): Unable to execute Javascript function " + f.getClassName() + ".", err);
				return true;
			}
		} finally {
			Context.exit();
		}
	}
}
