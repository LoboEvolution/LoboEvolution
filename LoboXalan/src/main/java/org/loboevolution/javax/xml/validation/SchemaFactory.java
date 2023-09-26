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

public abstract class SchemaFactory {

    protected SchemaFactory() {
        throw new RuntimeException("Stub!");
    }

    public static SchemaFactory newInstance(final String schemaLanguage) {
        throw new RuntimeException("Stub!");
    }

    public static SchemaFactory newInstance(final String schemaLanguage, final String factoryClassName, final ClassLoader classLoader) {
        throw new RuntimeException("Stub!");
    }

    public abstract boolean isSchemaLanguageSupported(final String s);

    public boolean getFeature(final String name)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        throw new RuntimeException("Stub!");
    }

    public void setFeature(final String name, final boolean value)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        throw new RuntimeException("Stub!");
    }

    public void setProperty(final String name, final Object object)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        throw new RuntimeException("Stub!");
    }

    public Object getProperty(final String name)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        throw new RuntimeException("Stub!");
    }

    public abstract ErrorHandler getErrorHandler();

    public abstract void setErrorHandler(ErrorHandler errorhandler);

    public Schema newSchema(final Source schema)
            throws SAXException {
        throw new RuntimeException("Stub!");
    }

    public Schema newSchema(final File schema)
            throws SAXException {
        throw new RuntimeException("Stub!");
    }

    public Schema newSchema(final URL schema)
            throws SAXException {
        throw new RuntimeException("Stub!");
    }

    public abstract Schema newSchema(Source asource[])
            throws SAXException;

    public abstract Schema newSchema()
            throws SAXException;
}
