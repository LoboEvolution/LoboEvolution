// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ErrorListener.java

package org.loboevolution.javax.xml.transform;


public interface ErrorListener {

    void warning(TransformerException transformerexception)
            throws TransformerException;

    void error(TransformerException transformerexception)
            throws TransformerException;

    void fatalError(TransformerException transformerexception)
            throws TransformerException;
}
