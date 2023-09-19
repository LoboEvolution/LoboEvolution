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

import java.util.Properties;

// Referenced classes of package org.loboevolution.javax.xml.transform:
//            TransformerException, Source, Result, URIResolver, 
//            ErrorListener

public abstract class Transformer
{

    protected Transformer()
    {
        throw new RuntimeException("Stub!");
    }

    public void reset()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract void transform(Source source, Result result)
        throws TransformerException;

    public abstract void setParameter(String s, Object obj);

    public abstract Object getParameter(String s);

    public abstract void clearParameters();

    public abstract void setURIResolver(URIResolver uriresolver);

    public abstract URIResolver getURIResolver();

    public abstract void setOutputProperties(Properties properties);

    public abstract Properties getOutputProperties();

    public abstract void setOutputProperty(String s, String s1)
        throws IllegalArgumentException;

    public abstract String getOutputProperty(String s)
        throws IllegalArgumentException;

    public abstract void setErrorListener(ErrorListener errorlistener)
        throws IllegalArgumentException;

    public abstract ErrorListener getErrorListener();
}
