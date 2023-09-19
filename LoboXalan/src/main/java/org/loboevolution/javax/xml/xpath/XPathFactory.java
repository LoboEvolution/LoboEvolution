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

package org.loboevolution.javax.xml.xpath;


// Referenced classes of package org.loboevolution.javax.xml.xpath:
//            XPathFactoryConfigurationException, XPathVariableResolver, XPathFunctionResolver, XPath

public abstract class XPathFactory
{

    protected XPathFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public static final XPathFactory newInstance()
    {
        throw new RuntimeException("Stub!");
    }

    public static final XPathFactory newInstance(String uri)
        throws XPathFactoryConfigurationException
    {
        throw new RuntimeException("Stub!");
    }

    public static XPathFactory newInstance(String uri, String factoryClassName, ClassLoader classLoader)
        throws XPathFactoryConfigurationException
    {
        throw new RuntimeException("Stub!");
    }

    public abstract boolean isObjectModelSupported(String s);

    public abstract void setFeature(String s, boolean flag)
        throws XPathFactoryConfigurationException;

    public abstract boolean getFeature(String s)
        throws XPathFactoryConfigurationException;

    public abstract void setXPathVariableResolver(XPathVariableResolver xpathvariableresolver);

    public abstract void setXPathFunctionResolver(XPathFunctionResolver xpathfunctionresolver);

    public abstract XPath newXPath();

    public static final String DEFAULT_OBJECT_MODEL_URI = "http://java.sun.com/jaxp/xpath/dom";
    public static final String DEFAULT_PROPERTY_NAME = "org.loboevolution.javax.xml.xpath.XPathFactory";
}
