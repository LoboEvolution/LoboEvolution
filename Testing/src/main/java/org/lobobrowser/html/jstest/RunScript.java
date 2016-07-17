package org.lobobrowser.html.jstest;

import java.lang.reflect.InvocationTargetException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class RunScript {
	public static void main(String args[]) {

		Context cx = Context.enter();
		try {
			Scriptable scope = cx.initStandardObjects();
			ScriptableObject.defineClass(scope, Counter.class);

			Scriptable testCounter = cx.newObject(scope, "Counter");

			Object count = ScriptableObject.getProperty(testCounter, "count");
			System.out.println("count = " + count);

			count = ScriptableObject.getProperty(testCounter, "count");
			System.out.println("count = " + count);

			ScriptableObject.callMethod(testCounter, "resetCount", new Object[0]);
			System.out.println("resetCount");

			count = ScriptableObject.getProperty(testCounter, "count");
			System.out.println("count = " + count);
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			Context.exit();
		}
	}
}

 