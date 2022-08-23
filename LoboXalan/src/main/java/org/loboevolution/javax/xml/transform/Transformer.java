// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Transformer.java

package org.loboevolution.javax.xml.transform;

import java.util.Properties;

// Referenced classes of package org.loboevolution.javax.xml.transform:
//            TransformerException, Source, Result, URIResolver, 
//            ErrorListener

public abstract class Transformer
{

    protected Transformer()
    {
        throw new RuntimeException("Stub!");
    }

    public void reset()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract void transform(Source source, Result result)
        throws TransformerException;

    public abstract void setParameter(String s, Object obj);

    public abstract Object getParameter(String s);

    public abstract void clearParameters();

    public abstract void setURIResolver(URIResolver uriresolver);

    public abstract URIResolver getURIResolver();

    public abstract void setOutputProperties(Properties properties);

    public abstract Properties getOutputProperties();

    public abstract void setOutputProperty(String s, String s1)
        throws IllegalArgumentException;

    public abstract String getOutputProperty(String s)
        throws IllegalArgumentException;

    public abstract void setErrorListener(ErrorListener errorlistener)
        throws IllegalArgumentException;

    public abstract ErrorListener getErrorListener();
}
