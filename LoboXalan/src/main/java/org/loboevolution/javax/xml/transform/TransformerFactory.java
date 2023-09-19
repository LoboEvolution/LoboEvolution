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

package org.loboevolution.javax.xml.transform;


// Referenced classes of package org.loboevolution.javax.xml.transform:
//            TransformerFactoryConfigurationError, TransformerConfigurationException, Source, Transformer, 
//            Templates, URIResolver, ErrorListener

public abstract class TransformerFactory
{

    protected TransformerFactory()
    {
        throw new RuntimeException("Stub!");
    }

    public static TransformerFactory newInstance()
        throws TransformerFactoryConfigurationError
    {
        throw new RuntimeException("Stub!");
    }

    public static TransformerFactory newInstance(String factoryClassName, ClassLoader classLoader)
        throws TransformerFactoryConfigurationError
    {
        throw new RuntimeException("Stub!");
    }

    public abstract Transformer newTransformer(Source source)
        throws TransformerConfigurationException;

    public abstract Transformer newTransformer()
        throws TransformerConfigurationException;

    public abstract Templates newTemplates(Source source)
        throws TransformerConfigurationException;

    public abstract Source getAssociatedStylesheet(Source source, String s, String s1, String s2)
        throws TransformerConfigurationException;

    public abstract void setURIResolver(URIResolver uriresolver);

    public abstract URIResolver getURIResolver();

    public abstract void setFeature(String s, boolean flag)
        throws TransformerConfigurationException;

    public abstract boolean getFeature(String s);

    public abstract void setAttribute(String s, Object obj);

    public abstract Object getAttribute(String s);

    public abstract void setErrorListener(ErrorListener errorlistener);

    public abstract ErrorListener getErrorListener();
}
