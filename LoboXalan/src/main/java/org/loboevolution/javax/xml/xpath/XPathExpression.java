// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XPathExpression.java

package org.loboevolution.javax.xml.xpath;

import org.loboevolution.javax.xml.namespace.QName;
import org.xml.sax.InputSource;

// Referenced classes of package org.loboevolution.javax.xml.xpath:
//            XPathExpressionException

public interface XPathExpression
{

    public abstract Object evaluate(Object obj, QName qname)
        throws XPathExpressionException;

    public abstract String evaluate(Object obj)
        throws XPathExpressionException;

    public abstract Object evaluate(InputSource inputsource, QName qname)
        throws XPathExpressionException;

    public abstract String evaluate(InputSource inputsource)
        throws XPathExpressionException;
}
