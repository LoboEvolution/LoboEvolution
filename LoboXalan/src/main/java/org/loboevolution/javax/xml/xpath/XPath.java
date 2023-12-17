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

package org.loboevolution.javax.xml.xpath;

import org.loboevolution.javax.xml.namespace.NamespaceContext;
import org.loboevolution.javax.xml.namespace.QName;
import org.xml.sax.InputSource;

public interface XPath {

    void reset();

    XPathVariableResolver getXPathVariableResolver();

    void setXPathVariableResolver(XPathVariableResolver xpathvariableresolver);

    XPathFunctionResolver getXPathFunctionResolver();

    void setXPathFunctionResolver(XPathFunctionResolver xpathfunctionresolver);

    NamespaceContext getNamespaceContext();

    void setNamespaceContext(NamespaceContext namespacecontext);

    XPathExpression compile(final String s)
            throws XPathExpressionException;

    Object evaluate(final String s, Object obj, QName qname)
            throws XPathExpressionException;

    String evaluate(final String s, Object obj)
            throws XPathExpressionException;

    Object evaluate(final String s, InputSource inputsource, QName qname)
            throws XPathExpressionException;

    String evaluate(final String s, InputSource inputsource)
            throws XPathExpressionException;
}
