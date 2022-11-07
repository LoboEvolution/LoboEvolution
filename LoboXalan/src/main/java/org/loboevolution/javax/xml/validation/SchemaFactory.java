// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SchemaFactory.java

package org.loboevolution.javax.xml.validation;

import java.io.File;
import java.net.URL;
import org.loboevolution.javax.xml.transform.Source;
import org.xml.sax.*;
public abstract class SchemaFactory
{

    protected SchemaFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public static SchemaFactory newInstance(String schemaLanguage)
    {
        throw new RuntimeException("Stub!");
    }

    public static SchemaFactory newInstance(String schemaLanguage, String factoryClassName, ClassLoader classLoader)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract boolean isSchemaLanguageSupported(String s);

    public boolean getFeature(String name)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        throw new RuntimeException("Stub!");
    }

    public void setFeature(String name, boolean value)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        throw new RuntimeException("Stub!");
    }

    public void setProperty(String name, Object object)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        throw new RuntimeException("Stub!");
    }

    public Object getProperty(String name)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        throw new RuntimeException("Stub!");
    }

    public abstract void setErrorHandler(ErrorHandler errorhandler);

    public abstract ErrorHandler getErrorHandler();


    public Schema newSchema(Source schema)
        throws SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public Schema newSchema(File schema)
        throws SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public Schema newSchema(URL schema)
        throws SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public abstract Schema newSchema(Source asource[])
        throws SAXException;

    public abstract Schema newSchema()
        throws SAXException;
}
