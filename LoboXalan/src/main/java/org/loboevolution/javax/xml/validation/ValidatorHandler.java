// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ValidatorHandler.java

package org.loboevolution.javax.xml.validation;

import org.xml.sax.*;

// Referenced classes of package org.loboevolution.javax.xml.validation:
//            TypeInfoProvider

public abstract class ValidatorHandler
    implements ContentHandler
{

    protected ValidatorHandler()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract void setContentHandler(ContentHandler contenthandler);

    public abstract ContentHandler getContentHandler();

    public abstract void setErrorHandler(ErrorHandler errorhandler);

    public abstract ErrorHandler getErrorHandler();

    public abstract TypeInfoProvider getTypeInfoProvider();

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
