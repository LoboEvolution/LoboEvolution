// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XPathFunctionResolver.java

package org.loboevolution.javax.xml.xpath;

import org.loboevolution.javax.xml.namespace.QName;

// Referenced classes of package org.loboevolution.javax.xml.xpath:
//            XPathFunction

public interface XPathFunctionResolver
{

    public abstract XPathFunction resolveFunction(QName qname, int i);
}
