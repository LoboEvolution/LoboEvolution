// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransformerHandler.java

package org.loboevolution.javax.xml.transform.sax;

import org.loboevolution.javax.xml.transform.Result;
import org.loboevolution.javax.xml.transform.Transformer;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ext.LexicalHandler;

public interface TransformerHandler
    extends ContentHandler, LexicalHandler, DTDHandler
{

    public abstract void setResult(Result result)
        throws IllegalArgumentException;

    public abstract void setSystemId(String s);

    public abstract String getSystemId();

    public abstract Transformer getTransformer();
}
