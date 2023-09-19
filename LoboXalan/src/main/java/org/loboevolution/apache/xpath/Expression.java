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

import org.loboevolution.javax.xml.transform.ErrorListener;
import org.loboevolution.javax.xml.transform.TransformerException;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMIterator;

/**
 * This abstract class serves as the base for all expression objects. An Expression can be executed
 * to return a {@link org.loboevolution.apache.xpath.objects.XObject}, normally has a location
 * within a document or DOM, can send error and warning events, and normally do not hold state and
 * are meant to be immutable once construction has completed. An exception to the immutibility rule
 * is iterators and walkers, which must be cloned in order to be used -- the original must still be
 * immutable.
 */
public abstract class Expression implements ExpressionNode, XPathVisitable {

  /**
   * The location where this expression was built from. Need for diagnostic messages. May be null.
   *
   * @serial
   */
  private ExpressionNode m_parent;

  /**
   * Tell if this expression or it's subexpressions can traverse outside the current subtree.
   *
   * @return true if traversal outside the context node's subtree can occur.
   */
  public boolean canTraverseOutsideSubtree() {
    return false;
  }

  /**
   * Execute an expression in the XPath runtime context, and return the result of the expression.
   *
   * @param xctxt The XPath runtime context.
   * @param currentNode The currentNode.
   * @return The result of the expression in the form of a <code>XObject</code>.
   * @throws TransformerException if a runtime exception occurs.
   */
  public XObject execute(XPathContext xctxt, int currentNode)
      throws TransformerException {

    // For now, the current node is already pushed.
    return execute(xctxt);
  }

  /**
   * Execute an expression in the XPath runtime context, and return the result of the expression.
   *
   * @param xctxt The XPath runtime context.
   * @param currentNode The currentNode.
   * @param dtm The DTM of the current node.
   * @param expType The expanded type ID of the current node.
   * @return The result of the expression in the form of a <code>XObject</code>.
   * @throws TransformerException if a runtime exception occurs.
   */
  public XObject execute(XPathContext xctxt, int currentNode, DTM dtm, int expType)
      throws TransformerException {

    // For now, the current node is already pushed.
    return execute(xctxt);
  }

  /**
   * Execute an expression in the XPath runtime context, and return the result of the expression.
   *
   * @param xctxt The XPath runtime context.
   * @return The result of the expression in the form of a <code>XObject</code>.
   * @throws TransformerException if a runtime exception occurs.
   */
  public abstract XObject execute(XPathContext xctxt)
      throws TransformerException;

  /**
   * Execute an expression in the XPath runtime context, and return the result of the expression,
   * but tell that a "safe" object doesn't have to be returned. The default implementation just
   * calls execute(xctxt).
   *
   * @param xctxt The XPath runtime context.
   * @param destructiveOK true if a "safe" object doesn't need to be returned.
   * @return The result of the expression in the form of a <code>XObject</code>.
   * @throws TransformerException if a runtime exception occurs.
   */
  public XObject execute(XPathContext xctxt, boolean destructiveOK)
      throws TransformerException {
    return execute(xctxt);
  }

  /**
   * Evaluate expression to a number.
   *
   * @param xctxt The XPath runtime context.
   * @return The expression evaluated as a double.
   * @throws TransformerException if any
   */
  public double num(XPathContext xctxt) throws TransformerException {
    return execute(xctxt).num();
  }

  /**
   * Evaluate expression to a boolean.
   *
   * @param xctxt The XPath runtime context.
   * @return false
   * @throws TransformerException if any
   */
  public boolean bool(XPathContext xctxt) throws TransformerException {
    return execute(xctxt).bool();
  }

  /**
   * Return the first node out of the nodeset, if this expression is a nodeset expression.
   *
   * @param xctxt The XPath runtime context.
   * @return the first node out of the nodeset, or DTM.NULL.
   * @throws TransformerException if any
   */
  public int asNode(XPathContext xctxt) throws TransformerException {
    DTMIterator iter = execute(xctxt).iter();
    return iter.nextNode();
  }

  /**
   * Given an select expression and a context, evaluate the XPath and return the resulting iterator.
   *
   * @param xctxt The execution context.
   * @param contextNode The node that "." expresses.
   * @return A valid DTMIterator.
   * @throws TransformerException thrown if the active ProblemListener decides the error condition
   *     is severe enough to halt processing.
   * @throws TransformerException if any
   */
  public DTMIterator asIterator(XPathContext xctxt, int contextNode)
      throws TransformerException {

    try {
      xctxt.pushCurrentNodeAndExpression(contextNode);

      return execute(xctxt).iter();
    } finally {
      xctxt.popCurrentNodeAndExpression();
    }
  }

  /**
   * Tell if this expression returns a stable number that will not change during iterations within
   * the expression. This is used to determine if a proximity position predicate can indicate that
   * no more searching has to occur.
   *
   * @return true if the expression represents a stable number.
   */
  public boolean isStableNumber() {
    return false;
  }

  /**
   * Compare this object with another object and see if they are equal, include the sub heararchy.
   *
   * @param expr Another expression object.
   * @return true if this objects class and the expr object's class are the same, and the data
   *     contained within both objects are considered equal.
   */
  public abstract boolean deepEquals(Expression expr);

  /**
   * This is a utility method to tell if the passed in class is the same class as this. It is to be
   * used by the deepEquals method. I'm bottlenecking it here because I'm not totally confident that
   * comparing the class objects is the best way to do this.
   *
   * @return true of the passed in class is the exact same class as this class.
   */
  protected final boolean isSameClass(Expression expr) {
    if (null == expr) return false;

    return getClass() == expr.getClass();
  }

  /**
   * Tell the user of an assertion error, and probably throw an exception.
   *
   * @param b If false, a runtime exception will be thrown.
   * @param msg The assertion message, which should be informative.
   * @throws RuntimeException if the b argument is false.
   */
  public void assertion(boolean b, String msg) {

    if (!b) {
      String fMsg =
          XPATHMessages.createXPATHMessage(
              XPATHErrorResources.ER_INCORRECT_PROGRAMMER_ASSERTION, new Object[] {msg});

      throw new RuntimeException(fMsg);
    }
  }

  /**
   * Tell the user of an error, and probably throw an exception.
   *
   * @param xctxt The XPath runtime context.
   * @param msg An error msgkey that corresponds to one of the constants found in {@link
   *     org.loboevolution.apache.xpath.res.XPATHErrorResources}, which is a key for a format
   *     string.
   * @param args An array of arguments represented in the format string, which may be null.
   * @throws TransformerException if the current ErrorListoner determines to throw an exception.
   * @throws TransformerException if any
   */
  public void error(XPathContext xctxt, String msg, Object[] args)
      throws TransformerException {

    String fmsg = XPATHMessages.createXPATHMessage(msg, args);

    if (null != xctxt) {
      ErrorListener eh = xctxt.getErrorListener();
      TransformerException te = new TransformerException(fmsg, this);

      eh.fatalError(te);
    }
  }

  /**
   * Get the first non-Expression parent of this node.
   *
   * @return null or first ancestor that is not an Expression.
   */
  public ExpressionNode getExpressionOwner() {
    ExpressionNode parent = exprGetParent();
    while ((null != parent) && (parent instanceof Expression)) parent = parent.exprGetParent();
    return parent;
  }

  // =============== ExpressionNode methods ================

  /** {@inheritDoc} */
  @Override
  public void exprSetParent(ExpressionNode n) {
    assertion(n != this, "Can not parent an expression to itself!");
    m_parent = n;
  }

  /** {@inheritDoc} */
  @Override
  public ExpressionNode exprGetParent() {
    return m_parent;
  }

  /** {@inheritDoc} */
  @Override
  public String getPublicId() {
    if (null == m_parent) return null;
    return m_parent.getPublicId();
  }

  /** {@inheritDoc} */
  @Override
  public String getSystemId() {
    if (null == m_parent) return null;
    return m_parent.getSystemId();
  }

  /** {@inheritDoc} */
  @Override
  public int getLineNumber() {
    if (null == m_parent) return 0;
    return m_parent.getLineNumber();
  }

  /** {@inheritDoc} */
  @Override
  public int getColumnNumber() {
    if (null == m_parent) return 0;
    return m_parent.getColumnNumber();
  }
}
