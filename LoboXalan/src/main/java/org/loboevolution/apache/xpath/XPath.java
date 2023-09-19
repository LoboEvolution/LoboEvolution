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
package org.loboevolution.apache.xpath;

import org.loboevolution.html.node.Node;
import org.loboevolution.javax.xml.transform.ErrorListener;
import org.loboevolution.javax.xml.transform.SourceLocator;
import org.loboevolution.javax.xml.transform.TransformerException;

import org.loboevolution.apache.xml.utils.DefaultErrorHandler;
import org.loboevolution.apache.xml.utils.WrappedRuntimeException;
import org.loboevolution.apache.xpath.compiler.Compiler;
import org.loboevolution.apache.xpath.compiler.FunctionTable;
import org.loboevolution.apache.xpath.compiler.XPathParser;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.apache.xml.utils.PrefixResolver;

/**
 * The XPath class wraps an expression object and provides general services for execution of that
 * expression.
 */
public class XPath {

    /**
     * The top of the expression tree.
     *
     * @serial
     */
    private final Expression m_mainExp;

    /**
     * The function table for xpath build-in functions
     */
    private transient FunctionTable m_funcTable = null;

    /**
     * initial the function table
     */
    private void initFunctionTable() {
        m_funcTable = new FunctionTable();
    }

    /**
     * Get the SourceLocator on the expression object.
     *
     * @return the SourceLocator on the expression object, which may be null.
     */
    public SourceLocator getLocator() {
        return m_mainExp;
    }

    /**
     * Represents a select type expression.
     */
    public static final int SELECT = 0;

    /**
     * Represents a match type expression.
     */
    public static final int MATCH = 1;

    /**
     * The pattern string, mainly kept around for diagnostic purposes.
     *
     * @serial
     */
    String m_patternString;


    /**
     * Construct an XPath object.
     *
     * <p>(Needs review -sc) This method initializes an XPathParser/ Compiler and compiles the
     * expression.
     *
     * @param exprString     The XPath expression.
     * @param prefixResolver A prefix resolver to use to resolve prefixes to namespace URIs.
     * @param type           one of {@link #SELECT} or {@link #MATCH}.
     * @param errorListener  The error listener, or null if default should be used.
     * @throws TransformerException if syntax or other error.
     */
    public XPath(
            String exprString, PrefixResolver prefixResolver, int type, ErrorListener errorListener)
            throws TransformerException {
        initFunctionTable();
        if (null == errorListener)
            errorListener = new DefaultErrorHandler();

        m_patternString = exprString;

        XPathParser parser = new XPathParser(errorListener);
        Compiler compiler = new Compiler(errorListener, m_funcTable);

        if (SELECT == type) parser.initXPath(compiler, exprString, prefixResolver);
        else if (MATCH == type) parser.initMatchPattern(compiler, exprString, prefixResolver);
        else
            throw new RuntimeException(
                    XPATHMessages.createXPATHMessage(
                            XPATHErrorResources.ER_CANNOT_DEAL_XPATH_TYPE,
                            new Object[]{Integer.toString(type)}));

        m_mainExp = compiler.compile(0);
    }

    /**
     * Construct an XPath object.
     *
     * <p>(Needs review -sc) This method initializes an XPathParser/ Compiler and compiles the
     * expression.
     *
     * @param exprString     The XPath expression.
     * @param prefixResolver A prefix resolver to use to resolve prefixes to namespace URIs.
     * @param type           one of {@link #SELECT} or {@link #MATCH}.
     * @param errorListener  The error listener, or null if default should be used.
     * @param aTable         the function table to be used
     * @throws TransformerException if syntax or other error.
     */
    public XPath(
            String exprString,
            PrefixResolver prefixResolver,
            int type,
            ErrorListener errorListener,
            FunctionTable aTable)
            throws TransformerException {
        m_funcTable = aTable;
        if (null == errorListener)
            errorListener = new DefaultErrorHandler();

        XPathParser parser = new XPathParser(errorListener);
        Compiler compiler = new Compiler(errorListener, m_funcTable);

        if (SELECT == type) parser.initXPath(compiler, exprString, prefixResolver);
        else if (MATCH == type) parser.initMatchPattern(compiler, exprString, prefixResolver);
        else
            throw new RuntimeException(
                    XPATHMessages.createXPATHMessage(
                            XPATHErrorResources.ER_CANNOT_DEAL_XPATH_TYPE,
                            new Object[]{Integer.toString(type)}));

        m_mainExp = compiler.compile(0);
    }

    /**
     * Construct an XPath object.
     *
     * <p>(Needs review -sc) This method initializes an XPathParser/ Compiler and compiles the
     * expression.
     *
     * @param exprString     The XPath expression.
     * @param prefixResolver A prefix resolver to use to resolve prefixes to namespace URIs.
     * @param type           one of {@link #SELECT} or {@link #MATCH}.
     * @throws TransformerException if syntax or other error.
     */
    public XPath(String exprString, PrefixResolver prefixResolver, int type)
            throws TransformerException {
        this(exprString, prefixResolver, type, null);
    }

    /**
     * Construct an XPath object.
     *
     * @param expr The Expression object.
     */
    public XPath(Expression expr) {
        m_mainExp = expr;
        initFunctionTable();
    }

    /**
     * Given an expression and a context, evaluate the XPath and return the result.
     *
     * @param xctxt            The execution context.
     * @param contextNode      The node that "." expresses.
     * @param namespaceContext The context in which namespaces in the XPath are supposed to be
     *                         expanded.
     * @return The result of the XPath or null if callbacks are used.
     * @throws TransformerException thrown if the error condition is severe enough to halt processing.
     * @throws TransformerException in case of error
     */
    public XObject execute(
            XPathContext xctxt, Node contextNode, PrefixResolver namespaceContext)
            throws TransformerException {
        return execute(xctxt, xctxt.getDTMHandleFromNode(contextNode), namespaceContext);
    }

    /**
     * Given an expression and a context, evaluate the XPath and return the result.
     *
     * @param xctxt            The execution context.
     * @param contextNode      The node that "." expresses.
     * @param namespaceContext The context in which namespaces in the XPath are supposed to be
     *                         expanded.
     * @throws TransformerException thrown if the active ProblemListener decides the error condition
     *                              is severe enough to halt processing.
     * @throws TransformerException in case of error
     */
    public XObject execute(XPathContext xctxt, int contextNode, PrefixResolver namespaceContext)
            throws TransformerException {

        xctxt.pushNamespaceContext(namespaceContext);

        xctxt.pushCurrentNodeAndExpression(contextNode);

        XObject xobj = null;

        try {
            xobj = m_mainExp.execute(xctxt);
        } catch (TransformerException te) {
            te.setLocator(this.getLocator());
            ErrorListener el = xctxt.getErrorListener();
            if (null != el) // defensive, should never happen.
            {
                el.error(te);
            } else throw te;
        } catch (Exception e) {
            while (e instanceof WrappedRuntimeException) {
                e = ((WrappedRuntimeException) e).getException();
            }
            // e.printStackTrace();

            String msg = e.getMessage();

            if (msg == null || msg.length() == 0) {
                msg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_XPATH_ERROR, null);
            }
            TransformerException te = new TransformerException(msg, getLocator(), e);
            ErrorListener el = xctxt.getErrorListener();
            // te.printStackTrace();
            if (null != el) // defensive, should never happen.
            {
                el.fatalError(te);
            } else throw te;
        } finally {
            xctxt.popNamespaceContext();

            xctxt.popCurrentNodeAndExpression();
        }

        return xobj;
    }

    /**
     * Given an expression and a context, evaluate the XPath and return the result.
     *
     * @param xctxt            The execution context.
     * @param contextNode      The node that "." expresses.
     * @param namespaceContext The context in which namespaces in the XPath are supposed to be
     *                         expanded.
     * @return the result
     * @throws TransformerException thrown if the active ProblemListener decides the error condition
     *                              is severe enough to halt processing.
     * @throws TransformerException in case of error
     */
    public boolean bool(XPathContext xctxt, int contextNode, PrefixResolver namespaceContext)
            throws TransformerException {

        xctxt.pushNamespaceContext(namespaceContext);

        xctxt.pushCurrentNodeAndExpression(contextNode);

        try {
            return m_mainExp.bool(xctxt);
        } catch (TransformerException te) {
            te.setLocator(this.getLocator());
            ErrorListener el = xctxt.getErrorListener();
            if (null != el) // defensive, should never happen.
            {
                el.error(te);
            } else throw te;
        } catch (Exception e) {
            while (e instanceof WrappedRuntimeException) {
                e = ((WrappedRuntimeException) e).getException();
            }
            // e.printStackTrace();

            String msg = e.getMessage();

            if (msg == null || msg.length() == 0) {
                msg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_XPATH_ERROR, null);
            }

            TransformerException te = new TransformerException(msg, getLocator(), e);
            ErrorListener el = xctxt.getErrorListener();
            // te.printStackTrace();
            if (null != el) // defensive, should never happen.
            {
                el.fatalError(te);
            } else throw te;
        } finally {
            xctxt.popNamespaceContext();

            xctxt.popCurrentNodeAndExpression();
        }

        return false;
    }

    /**
     * Tell the user of an error, and probably throw an exception.
     *
     * @param xctxt The XPath runtime context.
     * @param msg   An error msgkey that corresponds to one of the constants found in {@link
     *              org.loboevolution.apache.xpath.res.XPATHErrorResources}, which is a key for a format
     *              string.
     * @param args  An array of arguments represented in the format string, which may be null.
     * @throws TransformerException if the current ErrorListoner determines to throw an exception.
     */
    public void error(XPathContext xctxt, String msg, Object[] args)
            throws TransformerException {

        String fmsg = XPATHMessages.createXPATHMessage(msg, args);
        ErrorListener ehandler = xctxt.getErrorListener();

        if (null != ehandler) {
            ehandler.fatalError(new TransformerException(fmsg));
        }
    }

    /**
     * Return the XPath string associated with this object.
     *
     * @return the XPath string associated with this object.
     */
    public String getPatternString() {
        return m_patternString;
    }

    /**
     * This will traverse the heararchy, calling the visitor for each member. If the called visitor
     * method returns false, the subtree should not be called.
     *
     * @param visitor The visitor whose appropriate method will be called.
     */
    public void callVisitors(XPathVisitor visitor) {
        m_mainExp.callVisitors(visitor);
    }

    /**
     * The match score if no match is made.
     */
    public static final double MATCH_SCORE_NONE = Double.NEGATIVE_INFINITY;

    /**
     * The match score if the pattern has the form of a QName optionally preceded by an @ character.
     */
    public static final double MATCH_SCORE_QNAME = 0.0;

    /**
     * The match score if the pattern pattern has the form NCName:*.
     */
    public static final double MATCH_SCORE_NSWILD = -0.25;

    /**
     * The match score if the pattern consists of just a NodeTest.
     */
    public static final double MATCH_SCORE_NODETEST = -0.5;

    /**
     * The match score if the pattern consists of something other than just a NodeTest or just a
     * qname.
     */
    public static final double MATCH_SCORE_OTHER = 0.5;
}
