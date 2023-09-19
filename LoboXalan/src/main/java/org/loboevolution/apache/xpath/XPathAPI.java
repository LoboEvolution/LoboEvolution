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
package org.loboevolution.apache.xpath;

import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.traversal.NodeIterator;
import org.loboevolution.javax.xml.transform.TransformerException;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xml.utils.PrefixResolver;
import org.loboevolution.apache.xml.utils.PrefixResolverDefault;
import org.loboevolution.html.node.Node;

/**
 * The methods in this class are convenience methods into the low-level XPath API. These functions
 * tend to be a little slow, since a number of objects must be created for each evaluation. A faster
 * way is to precompile the XPaths using the low-level API, and then just use the XPaths over and
 * over.
 *
 * <p>NOTE: In particular, each call to this method will create a new XPathContext, a new
 * DTMManager... and thus a new DTM. That's very safe, since it guarantees that you're always
 * processing against a fully up-to-date view of your document. But it's also portentially very
 * expensive, since you're rebuilding the DTM every time. You should consider using an instance of
 * CachedXPathAPI rather than these static methods.
 *
 * @see <a href="http://www.w3.org/TR/xpath">XPath Specification</a>
 */
public class XPathAPI {

  /**
   * Use an XPath string to select a single node. XPath namespace prefixes are resolved from the
   * context node, which may not be what you want (see the next method).
   *
   * @param contextNode The node to start searching from.
   * @param str A valid XPath string.
   * @return The first node found that matches the XPath, or null.
   * @throws TransformerException in case of error
   */
  public static Node selectSingleNode(Node contextNode, String str) throws TransformerException {
    return selectSingleNode(contextNode, str, contextNode);
  }

  /**
   * Use an XPath string to select a single node. XPath namespace prefixes are resolved from the
   * namespaceNode.
   *
   * @param contextNode The node to start searching from.
   * @param str A valid XPath string.
   * @param namespaceNode The node from which prefixes in the XPath will be resolved to namespaces.
   * @return The first node found that matches the XPath, or null.
   * @throws TransformerException in case of error
   */
  public static Node selectSingleNode(Node contextNode, String str, Node namespaceNode)
      throws TransformerException {

    // Have the XObject return its result as a NodeSetDTM.
    NodeIterator nl = selectNodeIterator(contextNode, str, namespaceNode);

    // Return the first node, or null
    return nl.nextNode();
  }

  /**
   * Use an XPath string to select a nodelist. XPath namespace prefixes are resolved from the
   * contextNode.
   *
   * @param contextNode The node to start searching from.
   * @param str A valid XPath string.
   * @return A NodeIterator, should never be null.
   * @throws TransformerException in case of error
   */
  public static NodeIterator selectNodeIterator(Node contextNode, String str)
      throws TransformerException {
    return selectNodeIterator(contextNode, str, contextNode);
  }

  /**
   * Use an XPath string to select a nodelist. XPath namespace prefixes are resolved from the
   * namespaceNode.
   *
   * @param contextNode The node to start searching from.
   * @param str A valid XPath string.
   * @param namespaceNode The node from which prefixes in the XPath will be resolved to namespaces.
   * @return A NodeIterator, should never be null.
   * @throws TransformerException in case of error
   */
  public static NodeIterator selectNodeIterator(Node contextNode, String str, Node namespaceNode)
      throws TransformerException {

    // Execute the XPath, and have it return the result
    XObject list = eval(contextNode, str, namespaceNode);

    // Have the XObject return its result as a NodeSetDTM.
    return list.nodeset();
  }

  /**
   * Use an XPath string to select a nodelist. XPath namespace prefixes are resolved from the
   * contextNode.
   *
   * @param contextNode The node to start searching from.
   * @param str A valid XPath string.
   * @return A NodeIterator, should never be null.
   * @throws TransformerException in case of error
   */
  public static NodeList selectNodeList(Node contextNode, String str) throws TransformerException {
    return selectNodeList(contextNode, str, contextNode);
  }

  /**
   * Use an XPath string to select a nodelist. XPath namespace prefixes are resolved from the
   * namespaceNode.
   *
   * @param contextNode The node to start searching from.
   * @param str A valid XPath string.
   * @param namespaceNode The node from which prefixes in the XPath will be resolved to namespaces.
   * @return A NodeIterator, should never be null.
   * @throws TransformerException in case of error
   */
  public static NodeList selectNodeList(Node contextNode, String str, Node namespaceNode)
      throws TransformerException {

    // Execute the XPath, and have it return the result
    XObject list = eval(contextNode, str, namespaceNode);

    // Return a NodeList.
    return list.nodelist();
  }

  /**
   * Evaluate XPath string to an XObject. Using this method, XPath namespace prefixes will be
   * resolved from the namespaceNode.
   *
   * @param contextNode The node to start searching from.
   * @param str A valid XPath string.
   * @return An XObject, which can be used to obtain a string, number, nodelist, etc, should never
   *     be null.
   * @see org.loboevolution.apache.xpath.objects.XObject
   * @see org.loboevolution.apache.xpath.objects.XBoolean
   * @see org.loboevolution.apache.xpath.objects.XNumber
   * @see org.loboevolution.apache.xpath.objects.XString
   * @throws TransformerException in case of error
   */
  public static XObject eval(Node contextNode, String str) throws TransformerException {
    return eval(contextNode, str, contextNode);
  }

  /**
   * Evaluate XPath string to an XObject. XPath namespace prefixes are resolved from the
   * namespaceNode. The implementation of this is a little slow, since it creates a number of
   * objects each time it is called. This could be optimized to keep the same objects around, but
   * then thread-safety issues would arise.
   *
   * @param contextNode The node to start searching from.
   * @param str A valid XPath string.
   * @param namespaceNode The node from which prefixes in the XPath will be resolved to namespaces.
   * @return An XObject, which can be used to obtain a string, number, nodelist, etc, should never
   *     be null.
   * @see org.loboevolution.apache.xpath.objects.XObject
   * @see org.loboevolution.apache.xpath.objects.XBoolean
   * @see org.loboevolution.apache.xpath.objects.XNumber
   * @see org.loboevolution.apache.xpath.objects.XString
   * @throws TransformerException in case of error
   */
  public static XObject eval(Node contextNode, String str, Node namespaceNode)
      throws TransformerException {

    // Since we don't have a XML Parser involved here, install some default support
    // for things like namespaces, etc.
    // (Changed from: XPathContext xpathSupport = new XPathContext();
    // because XPathContext is weak in a number of areas... perhaps
    // XPathContext should be done away with.)
    // Create an XPathContext that doesn't support pushing and popping of
    // variable resolution scopes. Sufficient for simple XPath 1.0 expressions.
    XPathContext xpathSupport = new XPathContext(false);

    // Create an object to resolve namespace prefixes.
    // XPath namespaces are resolved from the input context node's document element
    // if it is a root node, or else the current context node (for lack of a better
    // resolution space, given the simplicity of this sample code).
    PrefixResolverDefault prefixResolver =
        new PrefixResolverDefault(
            (namespaceNode.getNodeType() == Node.DOCUMENT_NODE)
                ? ((Document) namespaceNode).getDocumentElement()
                : namespaceNode);

    // Create the XPath object.
    XPath xpath = new XPath(str, prefixResolver, XPath.SELECT, null);

    // Execute the XPath, and have it return the result
    // return xpath.execute(xpathSupport, contextNode, prefixResolver);
    int ctxtNode = xpathSupport.getDTMHandleFromNode(contextNode);

    return xpath.execute(xpathSupport, ctxtNode, prefixResolver);
  }

  /**
   * Evaluate XPath string to an XObject. XPath namespace prefixes are resolved from the
   * namespaceNode. The implementation of this is a little slow, since it creates a number of
   * objects each time it is called. This could be optimized to keep the same objects around, but
   * then thread-safety issues would arise.
   *
   * @param contextNode The node to start searching from.
   * @param str A valid XPath string.
   * @param prefixResolver Will be called if the parser encounters namespace prefixes, to resolve
   *     the prefixes to URLs.
   * @return An XObject, which can be used to obtain a string, number, nodelist, etc, should never
   *     be null.
   * @see org.loboevolution.apache.xpath.objects.XObject
   * @see org.loboevolution.apache.xpath.objects.XBoolean
   * @see org.loboevolution.apache.xpath.objects.XNumber
   * @see org.loboevolution.apache.xpath.objects.XString
   * @throws TransformerException in case of error
   */
  public static XObject eval(Node contextNode, String str, PrefixResolver prefixResolver)
      throws TransformerException {

    // Since we don't have a XML Parser involved here, install some default support
    // for things like namespaces, etc.
    // (Changed from: XPathContext xpathSupport = new XPathContext();
    // because XPathContext is weak in a number of areas... perhaps
    // XPathContext should be done away with.)
    // Create the XPath object.
    XPath xpath = new XPath(str, prefixResolver, XPath.SELECT, null);

    // Create an XPathContext that doesn't support pushing and popping of
    // variable resolution scopes. Sufficient for simple XPath 1.0 expressions.
    XPathContext xpathSupport = new XPathContext(false);

    // Execute the XPath, and have it return the result
    int ctxtNode = xpathSupport.getDTMHandleFromNode(contextNode);

    return xpath.execute(xpathSupport, ctxtNode, prefixResolver);
  }
}
