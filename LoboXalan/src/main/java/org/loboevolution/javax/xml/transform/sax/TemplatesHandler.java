// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TemplatesHandler.java

package org.loboevolution.javax.xml.transform.sax;

import org.loboevolution.javax.xml.transform.Templates;
import org.xml.sax.ContentHandler;

public interface TemplatesHandler
    extends ContentHandler
{

    public abstract Templates getTemplates();

    public abstract void setSystemId(String s);

    public abstract String getSystemId();
}
