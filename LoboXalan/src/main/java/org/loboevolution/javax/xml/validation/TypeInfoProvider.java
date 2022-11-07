// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TypeInfoProvider.java

package org.loboevolution.javax.xml.validation;

import org.loboevolution.html.node.TypeInfo;

public abstract class TypeInfoProvider
{

    protected TypeInfoProvider()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract TypeInfo getElementTypeInfo();

    public abstract TypeInfo getAttributeTypeInfo(int i);

    public abstract boolean isIdAttribute(int i);

    public abstract boolean isSpecified(int i);
}
