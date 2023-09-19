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

import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.javax.xml.validation.Schema;
import org.xml.sax.*;

public abstract class DocumentBuilder
{

    protected DocumentBuilder()
    {
        throw new RuntimeException("Stub!");
    }

    public void reset()
    {
        throw new RuntimeException("Stub!");
    }

    public Document parse(InputStream is)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public Document parse(InputStream is, String systemId)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public Document parse(String uri)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public Document parse(File f)
        throws IOException, SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public abstract Document parse(InputSource inputsource)
        throws IOException, SAXException;

    public abstract boolean isNamespaceAware();

    public abstract boolean isValidating();

    public abstract void setEntityResolver(EntityResolver entityresolver);

    public abstract void setErrorHandler(ErrorHandler errorhandler);

    public abstract Document newDocument();

    public abstract DOMImplementation getDOMImplementation();

    public Schema getSchema()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isXIncludeAware()
    {
        throw new RuntimeException("Stub!");
    }
}
