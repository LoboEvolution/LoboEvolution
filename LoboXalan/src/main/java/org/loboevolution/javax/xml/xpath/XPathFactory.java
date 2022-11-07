// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XPathFactory.java

package org.loboevolution.javax.xml.xpath;


// Referenced classes of package org.loboevolution.javax.xml.xpath:
//            XPathFactoryConfigurationException, XPathVariableResolver, XPathFunctionResolver, XPath

public abstract class XPathFactory
{

    protected XPathFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public static final XPathFactory newInstance()
    {
        throw new RuntimeException("Stub!");
    }

    public static final XPathFactory newInstance(String uri)
        throws XPathFactoryConfigurationException
    {
        throw new RuntimeException("Stub!");
    }

    public static XPathFactory newInstance(String uri, String factoryClassName, ClassLoader classLoader)
        throws XPathFactoryConfigurationException
    {
        throw new RuntimeException("Stub!");
    }

    public abstract boolean isObjectModelSupported(String s);

    public abstract void setFeature(String s, boolean flag)
        throws XPathFactoryConfigurationException;

    public abstract boolean getFeature(String s)
        throws XPathFactoryConfigurationException;

    public abstract void setXPathVariableResolver(XPathVariableResolver xpathvariableresolver);

    public abstract void setXPathFunctionResolver(XPathFunctionResolver xpathfunctionresolver);

    public abstract XPath newXPath();

    public static final String DEFAULT_OBJECT_MODEL_URI = "http://java.sun.com/jaxp/xpath/dom";
    public static final String DEFAULT_PROPERTY_NAME = "org.loboevolution.javax.xml.xpath.XPathFactory";
}
