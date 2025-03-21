/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package org.loboevolution.apache.xpath.axes;

import org.loboevolution.apache.xml.utils.WrappedRuntimeException;
import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.compiler.Compiler;
import org.loboevolution.apache.xpath.compiler.OpCodes;
import org.loboevolution.apache.xpath.objects.XNodeSet;
import org.loboevolution.apache.xml.dtm.Axis;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMIterator;
import org.loboevolution.apache.xml.utils.PrefixResolver;

/**
 * Walker for the OP_VARIABLE, or OP_EXTFUNCTION, or OP_FUNCTION, or OP_GROUP, op codes.
 *
 * @see <a href="http://www.w3.org/TR/xpath#NT-FilterExpr">XPath FilterExpr descriptions</a>
 */
public class FilterExprWalker extends AxesWalker {

  /**
   * Construct a FilterExprWalker using a LocPathIterator.
   *
   * @param locPathIterator non-null reference to the parent iterator.
   */
  public FilterExprWalker(final WalkingIterator locPathIterator) {
    super(locPathIterator, Axis.FILTEREDLIST);
  }

  /** {@inheritDoc} */
  @Override
  public void init(final Compiler compiler, final int opPos, final int stepType)
      throws javax.xml.transform.TransformerException {

    super.init(compiler, opPos, stepType);

    // Smooth over an anomily in the opcode map...
    switch (stepType) {
      case OpCodes.OP_FUNCTION:
      case OpCodes.OP_EXTFUNCTION:
      case OpCodes.OP_GROUP:
      case OpCodes.OP_VARIABLE:
        m_expr = compiler.compile(opPos);
        m_expr.exprSetParent(this);
        break;
      default:
        m_expr = compiler.compile(opPos + 2);
        m_expr.exprSetParent(this);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void detach() {
    super.detach();
    final boolean m_canDetachNodeset = true;
    if (m_canDetachNodeset) {
      m_exprObj.detach();
    }
    m_exprObj = null;
  }

  /** {@inheritDoc} */
  @Override
  public void setRoot(final int root) {

    super.setRoot(root);

    m_exprObj =
        executeFilterExpr(
            root,
            m_lpi.getXPathContext(),
            m_lpi.getPrefixResolver(),
            m_lpi.getIsTopLevel(),
            m_expr);
  }

  /**
   * Execute the expression. Meant for reuse by other FilterExpr iterators that are not derived from
   * this object.
   */
  public static XNodeSet executeFilterExpr(
          final int context,
          final XPathContext xctxt,
          final PrefixResolver prefixResolver,
          final boolean isTopLevel,
          final Expression expr)
      throws WrappedRuntimeException {
    final PrefixResolver savedResolver = xctxt.getNamespaceContext();
    XNodeSet result;

    try {
      xctxt.pushCurrentNode(context);
      xctxt.setNamespaceContext(prefixResolver);

      // The setRoot operation can take place with a reset operation,
      // and so we may not be in the context of LocPathIterator#nextNode,
      // so we have to set up the variable context, execute the expression,
      // and then restore the variable context.

      if (isTopLevel) {
        result = (XNodeSet) expr.execute(xctxt);
        result.setShouldCacheNodes(true);
      } else result = (XNodeSet) expr.execute(xctxt);

    } catch (final javax.xml.transform.TransformerException se) {

      // TODO: Fix...
      throw new WrappedRuntimeException(se);
    } finally {
      xctxt.popCurrentNode();
      xctxt.setNamespaceContext(savedResolver);
    }
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public Object clone() throws CloneNotSupportedException {

    final FilterExprWalker clone = (FilterExprWalker) super.clone();

    if (null != m_exprObj) clone.m_exprObj = (XNodeSet) m_exprObj.clone();

    return clone;
  }

  /** {@inheritDoc} */
  @Override
  public short acceptNode(final int n) {

    try {
      if (getPredicateCount() > 0) {
        countProximityPosition(0);

        if (!executePredicates(n, m_lpi.getXPathContext())) return DTMIterator.FILTER_SKIP;
      }

      return DTMIterator.FILTER_ACCEPT;
    } catch (final javax.xml.transform.TransformerException se) {
      throw new RuntimeException(se.getMessage());
    }
  }

  /** {@inheritDoc} */
  @Override
  public int getNextNode() {

    if (null != m_exprObj) {
      return m_exprObj.nextNode();
    }
    return DTM.NULL;
  }

  /** {@inheritDoc} */
  @Override
  public int getLastPos(final XPathContext xctxt) {
    return m_exprObj.getLength();
  }

  /**
   * The contained expression. Should be non-null.
   *
   * @serial
   */
  private Expression m_expr;

  /** The result of executing m_expr. Needs to be deep cloned on clone op. */
  private transient XNodeSet m_exprObj;

  /** {@inheritDoc} */
  @Override
  public int getAnalysisBits() {
    if (null != m_expr && m_expr instanceof PathComponent) {
      return ((PathComponent) m_expr).getAnalysisBits();
    }
    return WalkerFactory.BIT_FILTER;
  }

  /** {@inheritDoc} */
  @Override
  public int getAxis() {
    return m_exprObj.getAxis();
  }

  /** {@inheritDoc} */
  @Override
  public void callPredicateVisitors(final XPathVisitor visitor) {
    m_expr.callVisitors(visitor);

    super.callPredicateVisitors(visitor);
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(final Expression expr) {
    if (!super.deepEquals(expr)) return false;

    final FilterExprWalker walker = (FilterExprWalker) expr;
    return m_expr.deepEquals(walker.m_expr);
  }
}
