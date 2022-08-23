// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NamespaceContext.java

package org.loboevolution.javax.xml.namespace;

import java.util.Iterator;

public interface NamespaceContext
{

    public abstract String getNamespaceURI(String s);

    public abstract String getPrefix(String s);

    public abstract Iterator getPrefixes(String s);
}
