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

import org.loboevolution.javax.xml.validation.Schema;

// Referenced classes of package org.loboevolution.javax.xml.parsers:
//            ParserConfigurationException, DocumentBuilder

public abstract class DocumentBuilderFactory
{

    protected DocumentBuilderFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public static DocumentBuilderFactory newInstance()
    {
        throw new RuntimeException("Stub!");
    }

    public static DocumentBuilderFactory newInstance(String factoryClassName, ClassLoader classLoader)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract DocumentBuilder newDocumentBuilder()
        throws ParserConfigurationException;

    public void setNamespaceAware(boolean awareness)
    {
        throw new RuntimeException("Stub!");
    }

    public void setValidating(boolean validating)
    {
        throw new RuntimeException("Stub!");
    }

    public void setIgnoringElementContentWhitespace(boolean whitespace)
    {
        throw new RuntimeException("Stub!");
    }

    public void setExpandEntityReferences(boolean expandEntityRef)
    {
        throw new RuntimeException("Stub!");
    }

    public void setIgnoringComments(boolean ignoreComments)
    {
        throw new RuntimeException("Stub!");
    }

    public void setCoalescing(boolean coalescing)
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isNamespaceAware()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isValidating()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isIgnoringElementContentWhitespace()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isExpandEntityReferences()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isIgnoringComments()
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isCoalescing()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract void setAttribute(String s, Object obj)
        throws IllegalArgumentException;

    public abstract Object getAttribute(String s)
        throws IllegalArgumentException;

    public abstract void setFeature(String s, boolean flag)
        throws ParserConfigurationException;

    public abstract boolean getFeature(String s)
        throws ParserConfigurationException;

    public Schema getSchema()
    {
        throw new RuntimeException("Stub!");
    }

    public void setSchema(Schema schema)
    {
        throw new RuntimeException("Stub!");
    }

    public void setXIncludeAware(boolean state)
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isXIncludeAware()
    {
        throw new RuntimeException("Stub!");
    }
}
