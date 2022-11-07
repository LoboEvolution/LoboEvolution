// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SAXParser.java

package org.loboevolution.javax.xml.parsers;

import java.io.*;
import org.loboevolution.javax.xml.validation.Schema;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public abstract class SAXParser
{

    protected SAXParser()
    {
        throw new RuntimeException("Stub!");
    }

    public void reset()
    {
        throw new RuntimeException("Stub!");
    }

    public void parse(InputStream is, HandlerBase hb)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public void parse(InputStream is, HandlerBase hb, String systemId)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public void parse(InputStream is, DefaultHandler dh)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public void parse(InputStream is, DefaultHandler dh, String systemId)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public void parse(String uri, HandlerBase hb)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public void parse(String uri, DefaultHandler dh)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public void parse(File f, HandlerBase hb)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public void parse(File f, DefaultHandler dh)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public void parse(InputSource is, HandlerBase hb)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public void parse(InputSource is, DefaultHandler dh)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public abstract Parser getParser()
        throws SAXException;

    public abstract XMLReader getXMLReader()
        throws SAXException;

    public abstract boolean isNamespaceAware();

    public abstract boolean isValidating();

    public abstract void setProperty(String s, Object obj)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    public abstract Object getProperty(String s)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    public Schema getSchema()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isXIncludeAware()
    {
        throw new RuntimeException("Stub!");
    }
}
