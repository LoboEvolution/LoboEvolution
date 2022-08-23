// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XPathVariableResolver.java

package org.loboevolution.javax.xml.xpath;

import org.loboevolution.javax.xml.namespace.QName;

public interface XPathVariableResolver
{

    public abstract Object resolveVariable(QName qname);
}
