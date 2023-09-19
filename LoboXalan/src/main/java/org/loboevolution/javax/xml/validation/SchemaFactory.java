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

package org.loboevolution.javax.xml.validation;

import java.io.File;
import java.net.URL;
import org.loboevolution.javax.xml.transform.Source;
import org.xml.sax.*;
public abstract class SchemaFactory
{

    protected SchemaFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public static SchemaFactory newInstance(String schemaLanguage)
    {
        throw new RuntimeException("Stub!");
    }

    public static SchemaFactory newInstance(String schemaLanguage, String factoryClassName, ClassLoader classLoader)
    {
        throw new RuntimeException("Stub!");
    }

    public abstract boolean isSchemaLanguageSupported(String s);

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

    public abstract void setErrorHandler(ErrorHandler errorhandler);

    public abstract ErrorHandler getErrorHandler();


    public Schema newSchema(Source schema)
        throws SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public Schema newSchema(File schema)
        throws SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public Schema newSchema(URL schema)
        throws SAXException
    {
        throw new RuntimeException("Stub!");
    }

    public abstract Schema newSchema(Source asource[])
        throws SAXException;

    public abstract Schema newSchema()
        throws SAXException;
}
