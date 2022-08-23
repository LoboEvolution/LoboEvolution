// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocumentBuilder.java

package org.loboevolution.javax.xml.parsers;

import java.io.*;

import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.javax.xml.validation.Schema;
import org.xml.sax.*;

public abstract class DocumentBuilder
{

    protected DocumentBuilder()
    {
        throw new RuntimeException("Stub!");
    }

    public void reset()
    {
        throw new RuntimeException("Stub!");
    }

    public Document parse(InputStream is)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public Document parse(InputStream is, String systemId)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public Document parse(String uri)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public Document parse(File f)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public abstract Document parse(InputSource inputsource)
        throws IOException, SAXException;

    public abstract boolean isNamespaceAware();

    public abstract boolean isValidating();

    public abstract void setEntityResolver(EntityResolver entityresolver);

    public abstract void setErrorHandler(ErrorHandler errorhandler);

    public abstract Document newDocument();

    public abstract DOMImplementation getDOMImplementation();

    public Schema getSchema()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isXIncludeAware()
    {
        throw new RuntimeException("Stub!");
    }
}
