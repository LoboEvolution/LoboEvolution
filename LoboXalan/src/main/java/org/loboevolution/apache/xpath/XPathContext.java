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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Deque;
import org.loboevolution.javax.xml.transform.ErrorListener;
import org.loboevolution.javax.xml.transform.URIResolver;

import org.loboevolution.apache.xml.utils.DefaultErrorHandler;
import org.loboevolution.apache.xpath.axes.SubContextList;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMFilter;
import org.loboevolution.apache.xml.dtm.DTMIterator;
import org.loboevolution.apache.xml.dtm.DTMManager;
import org.loboevolution.apache.xml.utils.NodeVector;
import org.loboevolution.apache.xml.utils.PrefixResolver;
import org.loboevolution.html.node.Node;

/**
 * Default class for the runtime execution context for XPath.
 *
 * <p>This class extends DTMManager but does not directly implement it.
 */
public class XPathContext extends DTMManager {
  /**
   * Though XPathContext context extends the DTMManager, it really is a proxy for this object, which
   * is the real DTMManager.
   */
  protected DTMManager m_dtmManager = DTMManager.newInstance();

  /**
   * Return the DTMManager object. Though XPathContext context extends the DTMManager, it really is
   * a proxy for the real DTMManager. If a caller needs to make a lot of calls to the DTMManager, it
   * is faster if it gets the real one from this function.
   */
  public DTMManager getDTMManager() {
    return m_dtmManager;
  }

  /** {@inheritDoc} */
  @Override
  public DTM getDTM(
          final org.loboevolution.javax.xml.transform.Source source, final boolean unique, final boolean incremental, final boolean doIndexing) {
    return m_dtmManager.getDTM(source, unique, incremental, doIndexing);
  }

  /** {@inheritDoc} */
  @Override
  public DTM getDTM(final int nodeHandle) {
    return m_dtmManager.getDTM(nodeHandle);
  }

  /** {@inheritDoc} */
  @Override
  public int getDTMHandleFromNode(final Node node) {
    return m_dtmManager.getDTMHandleFromNode(node);
  }

  /** {@inheritDoc} */
  @Override
  public DTMIterator createDTMIterator(final Object xpathCompiler, final int pos) {
    return m_dtmManager.createDTMIterator(xpathCompiler, pos);
  }

  /** {@inheritDoc} */
  @Override
  public DTMIterator createDTMIterator(final String xpathString, final PrefixResolver presolver) {
    return m_dtmManager.createDTMIterator(xpathString, presolver);
  }

  /** {@inheritDoc} */
  @Override
  public DTMIterator createDTMIterator(
          final int whatToShow, final DTMFilter filter, final boolean entityReferenceExpansion) {
    return m_dtmManager.createDTMIterator(whatToShow, filter, entityReferenceExpansion);
  }

  /**
   * Create an XPathContext instance. This is equivalent to calling the {@link
   * #XPathContext(boolean)} constructor with the value <code>true</code>.
   */
  public XPathContext() {
    this(true);
  }

  /**
   * Create an XPathContext instance.
   *
   * @param recursiveVarContext A <code>boolean</code> value indicating whether the XPath context
   *     needs to support pushing of scopes for variable resolution
   */
  public XPathContext(final boolean recursiveVarContext) {
    m_prefixResolvers.push(null);
    m_currentNodes.push(DTM.NULL);
    m_currentExpressionNodes.push(DTM.NULL);
  }

  /** Reset for new run. */
  public void reset() {
    m_dtmManager = DTMManager.newInstance();

    m_axesIteratorStack.clear();
    m_currentExpressionNodes.clear();
    m_currentNodes.clear();
    m_iteratorRoots.RemoveAllNoClear();
    m_predicatePos.clear();
    m_predicateRoots.RemoveAllNoClear();
    m_prefixResolvers.clear();

    m_prefixResolvers.push(null);
    m_currentNodes.push(DTM.NULL);
    m_currentExpressionNodes.push(DTM.NULL);
  }

  // =================================================

  /** The ErrorListener where errors and warnings are to be reported. */
  private ErrorListener m_errorListener;

  /**
   * A default ErrorListener in case our m_errorListener was not specified and our owner either does
   * not have an ErrorListener or has a null one.
   */
  private ErrorListener m_defaultErrorListener;

  /**
   * Get the ErrorListener where errors and warnings are to be reported.
   *
   * @return A non-null ErrorListener reference.
   */
  public final ErrorListener getErrorListener() {
    if (null != m_errorListener) return m_errorListener;

    if (null == m_defaultErrorListener) {
      m_defaultErrorListener = new DefaultErrorHandler();
    }
    return m_defaultErrorListener;
  }

  /**
   * Set the ErrorListener where errors and warnings are to be reported.
   *
   * @param listener A non-null ErrorListener reference.
   */
  public void setErrorListener(final ErrorListener listener) throws IllegalArgumentException {
    if (listener == null)
      throw new IllegalArgumentException(
          XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_NULL_ERROR_HANDLER, null));
    m_errorListener = listener;
  }

  // =================================================

  /**
   * The TrAX URI Resolver for resolving URIs from the document(...) function to source tree nodes.
   */
  private URIResolver m_uriResolver;

  /**
   * Get the URIResolver associated with this execution context.
   *
   * @return a URI resolver, which may be null.
   */
  public final URIResolver getURIResolver() {
    return m_uriResolver;
  }

  /**
   * Set the URIResolver associated with this execution context.
   *
   * @param resolver the URIResolver to be associated with this execution context, may be null to
   *     clear an already set resolver.
   */
  public void setURIResolver(final URIResolver resolver) {
    m_uriResolver = resolver;
  }

  // ==========================================================
  // SECTION: Execution context state tracking
  // ==========================================================

  /** The ammount to use for stacks that record information during the recursive execution. */
  public static final int RECURSIONLIMIT = 1024 * 4;

  /**
   * The stack of <a href="http://www.w3.org/TR/xslt#dt-current-node">current node</a> objects. Not
   * to be confused with the current node list. %REVIEW% Note that there are no bounds check and
   * resize for this stack, so if it is blown, it's all over.
   */
  private final Deque<Integer> m_currentNodes = new ArrayDeque<>();

  /**
   * Get the current context node.
   *
   * @return the <a href="http://www.w3.org/TR/xslt#dt-current-node">current node</a>.
   */
  public final int getCurrentNode() {
    return m_currentNodes.peek();
  }

  /**
   * Set the current context node and expression node.
   *
   * @param cn the <a href="http://www.w3.org/TR/xslt#dt-current-node">current node</a>.
   */
  public final void pushCurrentNodeAndExpression(final int cn) {
    m_currentNodes.push(cn);
    m_currentExpressionNodes.push(cn);
  }

  /** Set the current context node. */
  public final void popCurrentNodeAndExpression() {
    m_currentNodes.pop();
    m_currentExpressionNodes.pop();
  }

  /**
   * Set the current context node.
   *
   * @param n the <a href="http://www.w3.org/TR/xslt#dt-current-node">current node</a>.
   */
  public final void pushCurrentNode(final int n) {
    m_currentNodes.push(n);
  }

  /** Pop the current context node. */
  public final void popCurrentNode() {
    m_currentNodes.pop();
  }

  /** Get the current location path iterator root. */
  public final int getIteratorRoot() {
    return m_iteratorRoots.peepOrNull();
  }

  /** A stack of the current sub-expression nodes. */
  private final NodeVector m_iteratorRoots = new NodeVector();

  /** A stack of the current sub-expression nodes. */
  private final NodeVector m_predicateRoots = new NodeVector();

  /** A stack of the current sub-expression nodes. */
  private final Deque<Integer> m_currentExpressionNodes = new ArrayDeque<>();

  private final Deque<Integer> m_predicatePos = new ArrayDeque<>();

  public final int getPredicatePos() {
    return m_predicatePos.peek();
  }

  public final void pushPredicatePos(final int n) {
    m_predicatePos.push(n);
  }

  public final void popPredicatePos() {
    m_predicatePos.pop();
  }

  private final Deque<PrefixResolver> m_prefixResolvers = new ArrayDeque<>();

  /**
   * Get the current namespace context for the xpath.
   *
   * @return the current prefix resolver for resolving prefixes to namespace URLs.
   */
  public final PrefixResolver getNamespaceContext() {
    return m_prefixResolvers.peek();
  }

  /**
   * Get the current namespace context for the xpath.
   *
   * @param pr the prefix resolver to be used for resolving prefixes to namespace URLs.
   */
  public final void setNamespaceContext(final PrefixResolver pr) {
    m_prefixResolvers.pop();
    m_prefixResolvers.push(pr);
  }

  /**
   * Push a current namespace context for the xpath.
   *
   * @param pr the prefix resolver to be used for resolving prefixes to namespace URLs.
   */
  public final void pushNamespaceContext(final PrefixResolver pr) {
    m_prefixResolvers.push(pr);
  }

  /** Pop the current namespace context for the xpath. */
  public final void popNamespaceContext() {
    m_prefixResolvers.pop();
  }

  // ==========================================================
  // SECTION: Current TreeWalker contexts (for internal use)
  // ==========================================================

  /** Stack of AxesIterators. */
  private final Deque<SubContextList> m_axesIteratorStack = new ArrayDeque<>();

  /**
   * Push a TreeWalker on the stack.
   *
   * @param iter A sub-context AxesWalker.
   */
  public final void pushSubContextList(final SubContextList iter) {
    m_axesIteratorStack.push(iter);
  }

  /** Pop the last pushed axes iterator. */
  public final void popSubContextList() {
    m_axesIteratorStack.pop();
  }

  /**
   * Get the current axes iterator, or return null if none.
   *
   * @return the sub-context node list.
   */
  public SubContextList getSubContextList() {
    return m_axesIteratorStack.isEmpty() ? null : m_axesIteratorStack.peek();
  }

  // ==========================================================
  // SECTION: Implementation of ExpressionContext interface
  // ==========================================================

}
