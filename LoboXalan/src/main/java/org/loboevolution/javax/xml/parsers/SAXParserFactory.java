// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SAXParserFactory.java

package org.loboevolution.javax.xml.parsers;

import org.loboevolution.javax.xml.validation.Schema;
import org.xml.sax.*;

// Referenced classes of package org.loboevolution.javax.xml.parsers:
//            ParserConfigurationException, SAXParser

public abstract class SAXParserFactory
{

    protected SAXParserFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public static SAXParserFactory newInstance()
    {
        throw new RuntimeException("Stub!");
    }

    public static SAXParserFactory newInstance(String factoryClassName, ClassLoader classLoader)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract SAXParser newSAXParser()
        throws ParserConfigurationException, SAXException;

    public void setNamespaceAware(boolean awareness)
    {
        throw new RuntimeException("Stub!");
    }

    public void setValidating(boolean validating)
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

    public abstract void setFeature(String s, boolean flag)
        throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException;

    public abstract boolean getFeature(String s)
        throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException;

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
