/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

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
