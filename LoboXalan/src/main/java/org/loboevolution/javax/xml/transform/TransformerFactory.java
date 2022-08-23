// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransformerFactory.java

package org.loboevolution.javax.xml.transform;


// Referenced classes of package org.loboevolution.javax.xml.transform:
//            TransformerFactoryConfigurationError, TransformerConfigurationException, Source, Transformer, 
//            Templates, URIResolver, ErrorListener

public abstract class TransformerFactory
{

    protected TransformerFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public static TransformerFactory newInstance()
        throws TransformerFactoryConfigurationError
    {
        throw new RuntimeException("Stub!");
    }

    public static TransformerFactory newInstance(String factoryClassName, ClassLoader classLoader)
        throws TransformerFactoryConfigurationError
    {
        throw new RuntimeException("Stub!");
    }

    public abstract Transformer newTransformer(Source source)
        throws TransformerConfigurationException;

    public abstract Transformer newTransformer()
        throws TransformerConfigurationException;

    public abstract Templates newTemplates(Source source)
        throws TransformerConfigurationException;

    public abstract Source getAssociatedStylesheet(Source source, String s, String s1, String s2)
        throws TransformerConfigurationException;

    public abstract void setURIResolver(URIResolver uriresolver);

    public abstract URIResolver getURIResolver();

    public abstract void setFeature(String s, boolean flag)
        throws TransformerConfigurationException;

    public abstract boolean getFeature(String s);

    public abstract void setAttribute(String s, Object obj);

    public abstract Object getAttribute(String s);

    public abstract void setErrorListener(ErrorListener errorlistener);

    public abstract ErrorListener getErrorListener();
}
