/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
import org.xml.sax.*;

// Referenced classes of package org.loboevolution.javax.xml.parsers:
//            ParserConfigurationException, SAXParser

public abstract class SAXParserFactory
{

    protected SAXParserFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public static SAXParserFactory newInstance()
    {
        throw new RuntimeException("Stub!");
    }

    public static SAXParserFactory newInstance(final String factoryClassName, final ClassLoader classLoader)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract SAXParser newSAXParser()
        throws ParserConfigurationException, SAXException;

    public void setNamespaceAware(final boolean awareness)
    {
        throw new RuntimeException("Stub!");
    }

    public void setValidating(final boolean validating)
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

    public abstract void setFeature(final String s, boolean flag)
        throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException;

    public abstract boolean getFeature(final String s)
        throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException;

    public Schema getSchema()
    {
        throw new RuntimeException("Stub!");
    }

    public void setSchema(final Schema schema)
    {
        throw new RuntimeException("Stub!");
    }

    public void setXIncludeAware(final boolean state)
    {
        throw new RuntimeException("Stub!");
    }

    public boolean isXIncludeAware()
    {
        throw new RuntimeException("Stub!");
    }
}
