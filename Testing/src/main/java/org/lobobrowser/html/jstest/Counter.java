package org.lobobrowser.html.jstest;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSGetter;

public class Counter extends ScriptableObject {
    private static final long serialVersionUID = 438270592527335642L;

    // The zero-argument constructor used by Rhino runtime to create instances
    public Counter() { }

    // @JSConstructor annotation defines the JavaScript constructor
    @JSConstructor
    public Counter(int a) { count = a; }

    // The class name is defined by the getClassName method
    @Override
    public String getClassName() { return "Counter"; }

    // The method getCount defines the count property.
    @JSGetter
    public int getCount() { return count++; }

    // Methods can be defined the @JSFunction annotation.
    // Here we define resetCount for JavaScript.
    @JSFunction
    public void resetCount() { count = 0; }

    private int count;
}