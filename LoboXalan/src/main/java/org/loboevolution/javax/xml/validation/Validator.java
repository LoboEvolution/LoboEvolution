// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Validator.java

package org.loboevolution.javax.xml.validation;

import java.io.IOException;
import org.loboevolution.javax.xml.transform.Result;
import org.loboevolution.javax.xml.transform.Source;
import org.xml.sax.*;

public abstract class Validator
{

    protected Validator()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract void reset();

    public void validate(Source source)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public abstract void validate(Source source, Result result)
        throws IOException, SAXException;

    public abstract void setErrorHandler(ErrorHandler errorhandler);

    public abstract ErrorHandler getErrorHandler();

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
}
