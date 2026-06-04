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

package org.loboevolution.html.node;

public interface TypeInfo {

    String ATTVAL_ANYTYPE = "anyType";
    int DERIVATION_ANY = 0;
    int DERIVATION_RESTRICTION = 1;
    int DERIVATION_EXTENSION = 2;
    int DERIVATION_UNION = 4;
    int DERIVATION_LIST = 8;

    String URI_SCHEMAFORSCHEMA = "http://www.w3.org/2001/XMLSchema";
    String URI_XHTML = "http://www.w3.org/1999/xhtml";

    /**
     * <p>getTypeName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getTypeName();

    /**
     * <p>getTypeNamespace.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getTypeNamespace();

    /**
     * <p>isDerivedFrom.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    boolean isDerivedFrom(String typeNamespaceArg,
                                 String typeNameArg,
                                 int derivationMethod);
}
