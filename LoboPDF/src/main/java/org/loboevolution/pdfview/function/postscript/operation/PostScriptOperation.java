package org.loboevolution.pdfview.function.postscript.operation;

import java.util.Stack;

public interface PostScriptOperation {

    /**
     * evaluate the function, popping the stack as needed and pushing results.
     */
    void eval(Stack<Object> environment);

}

