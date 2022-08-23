// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SAXTransformerFactory.java

package org.loboevolution.javax.xml.transform.sax;

import org.loboevolution.javax.xml.transform.*;
import org.xml.sax.XMLFilter;

// Referenced classes of package org.loboevolution.javax.xml.transform.sax:
//            TransformerHandler, TemplatesHandler

public abstract class SAXTransformerFactory extends TransformerFactory
{

    protected SAXTransformerFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract TransformerHandler newTransformerHandler(Source source)
        throws TransformerConfigurationException;

    public abstract TransformerHandler newTransformerHandler(Templates templates)
        throws TransformerConfigurationException;

    public abstract TransformerHandler newTransformerHandler()
        throws TransformerConfigurationException;

    public abstract TemplatesHandler newTemplatesHandler()
        throws TransformerConfigurationException;

    public abstract XMLFilter newXMLFilter(Source source)
        throws TransformerConfigurationException;

    public abstract XMLFilter newXMLFilter(Templates templates)
        throws TransformerConfigurationException;

    public static final String FEATURE = "http://org.loboevolution.javax.xml.transform.sax.SAXTransformerFactory/feature";
    public static final String FEATURE_XMLFILTER = "http://org.loboevolution.javax.xml.transform.sax.SAXTransformerFactory/feature/xmlfilter";
}
