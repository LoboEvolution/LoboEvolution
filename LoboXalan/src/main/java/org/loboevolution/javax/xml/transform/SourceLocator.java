// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SourceLocator.java

package org.loboevolution.javax.xml.transform;


public interface SourceLocator
{

    public abstract String getPublicId();

    public abstract String getSystemId();

    public abstract int getLineNumber();

    public abstract int getColumnNumber();
}
