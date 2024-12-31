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

package org.loboevolution.pdfview.function.postscript.operation;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>OperationSet class.</p>
 */
public final class OperationSet {

    private static OperationSet instance;
    /**
     * the set of all Operations we support. These operations are defined
     * in Appendix B - Operators.
     */
    private Map<String, PostScriptOperation> operationSet = null;

    /*************************************************************************
     * Constructor
     ************************************************************************/

    private OperationSet() {
        super();
        initOperations();
    }

    /**
     * <p>Getter for the field <code>instance</code>.</p>
     *
     * @return a {@link org.loboevolution.pdfview.function.postscript.operation.OperationSet} object.
     */
    public static synchronized OperationSet getInstance() {
        if (instance == null) {
            instance = new OperationSet();
        }
        return instance;
    }

    /**
     * <p>getOperation.</p>
     *
     * @param token a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.pdfview.function.postscript.operation.PostScriptOperation} object.
     */
    public PostScriptOperation getOperation(final String token) {
        PostScriptOperation result = this.operationSet.get(token.trim().toLowerCase());
        if (result == null) {
            result = new PushAsNumber(token);
        }
        return result;

    }


    /**
     * Initialize the operations that we can perform.
     */
    private void initOperations() {
        /** these operators consider the left hand arguments as deeper in
         * the stack than the right hand arguments, thus the right-hand is
         * is the top of the stack and is popped first.
         *
         * PostScriptOperation details in PostScript Language Reference Manual:
         * http://www.adobe.com/products/postscript/pdfs/PLRM.pdf
         * Chapter 8 - Operator Details
         */
        if (this.operationSet == null) {
            this.operationSet = new HashMap<>();

            // Arithmetic Operators
            this.operationSet.put("abs", new Abs());
            this.operationSet.put("add", new Add());
            this.operationSet.put("atan", new Atan());
            this.operationSet.put("ceiling", new Ceiling());
            this.operationSet.put("cvi", new Cvi());
            this.operationSet.put("cvr", new Cvr());
            this.operationSet.put("div", new Div());
            this.operationSet.put("exp", new Exp());
            this.operationSet.put("floor", new Floor());
            this.operationSet.put("idiv", new Idiv());
            this.operationSet.put("ln", new Ln());
            this.operationSet.put("log", new Log());
            this.operationSet.put("mod", new Mod());
            this.operationSet.put("mul", new Mul());
            this.operationSet.put("neg", new Neg());
            this.operationSet.put("round", new Round());
            this.operationSet.put("sin", new Sin());
            this.operationSet.put("sqrt", new Sqrt());
            this.operationSet.put("sub", new Sub());
            this.operationSet.put("truncate", new Truncate());

            // Relational, boolean, and bitwise operators
            this.operationSet.put("and", new And());
            this.operationSet.put("bitshift", new Bitshift());
            this.operationSet.put("eq", new Eq());
            this.operationSet.put("false", new False());
            this.operationSet.put("ge", new Ge());
            this.operationSet.put("gt", new Gt());
            this.operationSet.put("le", new Le());
            this.operationSet.put("lt", new Lt());
            this.operationSet.put("ne", new Ne());
            this.operationSet.put("not", new Not());
            this.operationSet.put("or", new Or());
            this.operationSet.put("true", new True());
            this.operationSet.put("xor", new Xor());

            // Conditional Operators
            this.operationSet.put("if", new If());
            this.operationSet.put("ifelse", new IfElse());

            // Stack Operators
            this.operationSet.put("copy", new Copy());
            this.operationSet.put("dup", new Dup());
            this.operationSet.put("exch", new Exch());
            this.operationSet.put("index", new Index());
            this.operationSet.put("pop", new Pop());
            this.operationSet.put("roll", new Roll());
        }
    }

}
