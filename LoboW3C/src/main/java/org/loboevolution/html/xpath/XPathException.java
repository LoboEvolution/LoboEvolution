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

package org.loboevolution.html.xpath;

/**
 * A new exception has been created for exceptions specific to these XPath
 * interfaces.
 * <p>See also the <a href=http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820>Document Object Model (DOM) Level 3 XPath Specification</a>.
 */
public class XPathException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;
	
    /**
     * <p>Constructor for XPathException.</p>
     *
     * @param code a short.
     * @param message a {@link java.lang.String} object.
     */
    public XPathException(short code, String message) {
       super(message);
       this.code = code;
    }
    public final short   code;
    /**
     * If the expression has a syntax error or otherwise is not a legal
     * expression according to the rules of the specific
     * XPathEvaluator or contains specialized extension
     * functions or variables not supported by this implementation.
     */
    public static final short INVALID_EXPRESSION_ERR    = 1;
    /**
     * If the expression cannot be converted to return the specified type.
     */
    public static final short TYPE_ERR                  = 2;

}
