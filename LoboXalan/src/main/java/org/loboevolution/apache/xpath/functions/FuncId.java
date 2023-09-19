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
package org.loboevolution.apache.xpath.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.loboevolution.apache.xpath.NodeSetDTM;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.objects.XNodeSet;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMIterator;

/** Execute the Id() function. */
public class FuncId extends FunctionOneArg {

  /**
   * Fill in a list with nodes that match a space delimited list if ID ID references.
   *
   * @param xctxt The runtime XPath context.
   * @param docContext The document where the nodes are being looked for.
   * @param refval A space delimited list of ID references.
   * @param usedrefs List of references for which nodes were found.
   * @param nodeSet Node set where the nodes will be added to.
   * @param mayBeMore true if there is another set of nodes to be looked for.
   * @return The usedrefs value.
   */
  private List<String> getNodesByID(
      XPathContext xctxt,
      int docContext,
      String refval,
      List<String> usedrefs,
      NodeSetDTM nodeSet,
      boolean mayBeMore) {

    if (null != refval) {
      String ref = null;
      // DOMHelper dh = xctxt.getDOMHelper();
      StringTokenizer tokenizer = new StringTokenizer(refval);
      boolean hasMore = tokenizer.hasMoreTokens();
      DTM dtm = xctxt.getDTM(docContext);

      while (hasMore) {
        ref = tokenizer.nextToken();
        hasMore = tokenizer.hasMoreTokens();

        if ((null != usedrefs) && usedrefs.contains(ref)) {
          ref = null;

          continue;
        }

        int node = dtm.getElementById(ref);

        if (DTM.NULL != node) nodeSet.addNodeInDocOrder(node, xctxt);

        if ((null != ref) && (hasMore || mayBeMore)) {
          if (null == usedrefs) usedrefs = new ArrayList<>();

          usedrefs.add(ref);
        }
      }
    }

    return usedrefs;
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    int context = xctxt.getCurrentNode();
    DTM dtm = xctxt.getDTM(context);
    int docContext = dtm.getDocument();

    if (DTM.NULL == docContext) error(xctxt, XPATHErrorResources.ER_CONTEXT_HAS_NO_OWNERDOC, null);

    XObject arg = m_arg0.execute(xctxt);
    int argType = arg.getType();
    XNodeSet nodes = new XNodeSet(xctxt.getDTMManager());
    NodeSetDTM nodeSet = nodes.mutableNodeset();

    if (XObject.CLASS_NODESET == argType) {
      DTMIterator ni = arg.iter();
      List<String> usedrefs = null;
      int pos = ni.nextNode();

      while (DTM.NULL != pos) {
        DTM ndtm = ni.getDTM(pos);
        String refval = ndtm.getStringValue(pos).toString();

        pos = ni.nextNode();
        usedrefs = getNodesByID(xctxt, docContext, refval, usedrefs, nodeSet, DTM.NULL != pos);
      }
      // ni.detach();
    } else if (XObject.CLASS_NULL == argType) {
      return nodes;
    } else {
      String refval = arg.str();

      getNodesByID(xctxt, docContext, refval, null, nodeSet, false);
    }

    return nodes;
  }
}
