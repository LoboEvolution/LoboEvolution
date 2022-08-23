// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SAXSource.java

package org.loboevolution.javax.xml.transform.sax;

import org.loboevolution.javax.xml.transform.Source;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class SAXSource
    implements Source
{

    public SAXSource()
    {
        throw new RuntimeException("Stub!");
    }

    public SAXSource(XMLReader reader, InputSource inputSource)
    {
        throw new RuntimeException("Stub!");
    }

    public SAXSource(InputSource inputSource)
    {
        throw new RuntimeException("Stub!");
    }

    public void setXMLReader(XMLReader reader)
    {
        throw new RuntimeException("Stub!");
    }

    public XMLReader getXMLReader()
    {
        throw new RuntimeException("Stub!");
    }

    public void setInputSource(InputSource inputSource)
    {
        throw new RuntimeException("Stub!");
    }

    public InputSource getInputSource()
    {
        throw new RuntimeException("Stub!");
    }

    public void setSystemId(String systemId)
    {
        throw new RuntimeException("Stub!");
    }

    public String getSystemId()
    {
        throw new RuntimeException("Stub!");
    }

    public static InputSource sourceToInputSource(Source source)
    {
        throw new RuntimeException("Stub!");
    }

    public static final String FEATURE = "http://org.loboevolution.javax.xml.transform.sax.SAXSource/feature";
}
