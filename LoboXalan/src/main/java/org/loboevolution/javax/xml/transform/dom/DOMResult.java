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

package org.loboevolution.javax.xml.transform.dom;

import org.loboevolution.javax.xml.transform.Result;
import org.loboevolution.html.node.Node;

public class DOMResult
    implements Result
{

    public DOMResult()
    {
        throw new RuntimeException("Stub!");
    }

    public DOMResult(Node node)
    {
        throw new RuntimeException("Stub!");
    }

    public DOMResult(Node node, String systemId)
    {
        throw new RuntimeException("Stub!");
    }

    public DOMResult(Node node, Node nextSibling)
    {
        throw new RuntimeException("Stub!");
    }

    public DOMResult(Node node, Node nextSibling, String systemId)
    {
        throw new RuntimeException("Stub!");
    }

    public void setNode(Node node)
    {
        throw new RuntimeException("Stub!");
    }

    public Node getNode()
    {
        throw new RuntimeException("Stub!");
    }

    public void setNextSibling(Node nextSibling)
    {
        throw new RuntimeException("Stub!");
    }

    public Node getNextSibling()
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

    public static final String FEATURE = "http://org.loboevolution.javax.xml.transform.dom.DOMResult/feature";
}
