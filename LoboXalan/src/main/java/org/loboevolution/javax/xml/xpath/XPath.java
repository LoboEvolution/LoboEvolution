// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XPath.java

package org.loboevolution.javax.xml.xpath;

import org.loboevolution.javax.xml.namespace.NamespaceContext;
import org.loboevolution.javax.xml.namespace.QName;
import org.xml.sax.InputSource;

// Referenced classes of package org.loboevolution.javax.xml.xpath:
//            XPathExpressionException, XPathVariableResolver, XPathFunctionResolver, XPathExpression

public interface XPath
{

    public abstract void reset();

    public abstract void setXPathVariableResolver(XPathVariableResolver xpathvariableresolver);

    public abstract XPathVariableResolver getXPathVariableResolver();

    public abstract void setXPathFunctionResolver(XPathFunctionResolver xpathfunctionresolver);

    public abstract XPathFunctionResolver getXPathFunctionResolver();

    public abstract void setNamespaceContext(NamespaceContext namespacecontext);

    public abstract NamespaceContext getNamespaceContext();

    public abstract XPathExpression compile(String s)
        throws XPathExpressionException;

    public abstract Object evaluate(String s, Object obj, QName qname)
        throws XPathExpressionException;

    public abstract String evaluate(String s, Object obj)
        throws XPathExpressionException;

    public abstract Object evaluate(String s, InputSource inputsource, QName qname)
        throws XPathExpressionException;

    public abstract String evaluate(String s, InputSource inputsource)
        throws XPathExpressionException;
}
