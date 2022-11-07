// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocumentBuilderFactory.java

package org.loboevolution.javax.xml.parsers;

import org.loboevolution.javax.xml.validation.Schema;

// Referenced classes of package org.loboevolution.javax.xml.parsers:
//            ParserConfigurationException, DocumentBuilder

public abstract class DocumentBuilderFactory
{

    protected DocumentBuilderFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public static DocumentBuilderFactory newInstance()
    {
        throw new RuntimeException("Stub!");
    }

    public static DocumentBuilderFactory newInstance(String factoryClassName, ClassLoader classLoader)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract DocumentBuilder newDocumentBuilder()
        throws ParserConfigurationException;

    public void setNamespaceAware(boolean awareness)
    {
        throw new RuntimeException("Stub!");
    }

    public void setValidating(boolean validating)
    {
        throw new RuntimeException("Stub!");
    }

    public void setIgnoringElementContentWhitespace(boolean whitespace)
    {
        throw new RuntimeException("Stub!");
    }

    public void setExpandEntityReferences(boolean expandEntityRef)
    {
        throw new RuntimeException("Stub!");
    }

    public void setIgnoringComments(boolean ignoreComments)
    {
        throw new RuntimeException("Stub!");
    }

    public void setCoalescing(boolean coalescing)
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isNamespaceAware()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isValidating()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isIgnoringElementContentWhitespace()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isExpandEntityReferences()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isIgnoringComments()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isCoalescing()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract void setAttribute(String s, Object obj)
        throws IllegalArgumentException;

    public abstract Object getAttribute(String s)
        throws IllegalArgumentException;

    public abstract void setFeature(String s, boolean flag)
        throws ParserConfigurationException;

    public abstract boolean getFeature(String s)
        throws ParserConfigurationException;

    public Schema getSchema()
    {
        throw new RuntimeException("Stub!");
    }

    public void setSchema(Schema schema)
    {
        throw new RuntimeException("Stub!");
    }

    public void setXIncludeAware(boolean state)
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isXIncludeAware()
    {
        throw new RuntimeException("Stub!");
    }
}
