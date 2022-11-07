/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the  "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.loboevolution.apache.xpath.patterns;

import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.axes.SubContextList;
import org.loboevolution.apache.xpath.compiler.PseudoNames;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xml.dtm.Axis;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMAxisTraverser;
import org.loboevolution.apache.xml.dtm.DTMFilter;
import org.loboevolution.html.node.traversal.NodeFilter;

/** This class represents a single pattern match step. */
public class StepPattern extends NodeTest implements SubContextList {

  /** The axis for this test. */
  protected int m_axis;

  /**
   * Construct a StepPattern that tests for namespaces and node names.
   *
   * @param whatToShow Bit set defined mainly by {@link org.loboevolution.html.node.traversal.NodeFilter}.
   * @param namespace The namespace to be tested.
   * @param name The local name to be tested.
   * @param axis The Axis for this test, one of of Axes.ANCESTORORSELF, etc.
   */
  public StepPattern(int whatToShow, String namespace, String name, int axis) {

    super(whatToShow, namespace, name);

    m_axis = axis;
  }

  /**
   * Construct a StepPattern that doesn't test for node names.
   *
   * @param whatToShow Bit set defined mainly by {@link NodeFilter}.
   * @param axis The Axis for this test, one of of Axes.ANCESTORORSELF, etc.
   */
  public StepPattern(int whatToShow, int axis) {

    super(whatToShow);

    m_axis = axis;
  }

  /**
   * The target local name or psuedo name, for hash table lookup optimization.
   *
   * @serial
   */
  String m_targetString; // only calculate on head

  /**
   * Calculate the local name or psuedo name of the node that this pattern will test, for hash table
   * lookup optimization.
   *
   * @see org.loboevolution.apache.xpath.compiler.PseudoNames
   */
  public void calcTargetString() {

    int whatToShow = getWhatToShow();

    switch (whatToShow) {
      case DTMFilter.SHOW_COMMENT:
        m_targetString = PseudoNames.PSEUDONAME_COMMENT;
        break;
      case DTMFilter.SHOW_TEXT:
      case DTMFilter.SHOW_CDATA_SECTION:
      case DTMFilter.SHOW_TEXT | DTMFilter.SHOW_CDATA_SECTION:
        m_targetString = PseudoNames.PSEUDONAME_TEXT;
        break;
      case DTMFilter.SHOW_ALL:
        m_targetString = PseudoNames.PSEUDONAME_ANY;
        break;
      case DTMFilter.SHOW_DOCUMENT:
      case DTMFilter.SHOW_DOCUMENT | DTMFilter.SHOW_DOCUMENT_FRAGMENT:
        m_targetString = PseudoNames.PSEUDONAME_ROOT;
        break;
      case DTMFilter.SHOW_ELEMENT:
        if (WILD == m_name) m_targetString = PseudoNames.PSEUDONAME_ANY;
        else m_targetString = m_name;
        break;
      default:
        m_targetString = PseudoNames.PSEUDONAME_ANY;
        break;
    }
  }

  /**
   * Reference to nodetest and predicate for parent or ancestor.
   *
   * @serial
   */
  StepPattern m_relativePathPattern;

  /**
   * Set the reference to nodetest and predicate for parent or ancestor.
   *
   * @param expr The relative pattern expression.
   */
  public void setRelativePathPattern(StepPattern expr) {

    m_relativePathPattern = expr;
    expr.exprSetParent(this);

    calcScore();
  }

  /**
   * Get the reference to nodetest and predicate for parent or ancestor.
   *
   * @return The relative pattern expression.
   */
  public StepPattern getRelativePathPattern() {
    return m_relativePathPattern;
  }

  /**
   * Set the list of predicate expressions for this pattern step.
   *
   * @return List of expression objects.
   */
  public Expression[] getPredicates() {
    return m_predicates;
  }

  /**
   * The list of predicate expressions for this pattern step.
   *
   * @serial
   */
  Expression[] m_predicates;

  /** {@inheritDoc} */
  @Override
  public boolean canTraverseOutsideSubtree() {

    int n = getPredicateCount();

    for (int i = 0; i < n; i++) {
      if (getPredicate(i).canTraverseOutsideSubtree()) return true;
    }

    return false;
  }

  /**
   * Get a predicate expression.
   *
   * @param i The index of the predicate.
   * @return A predicate expression.
   */
  public Expression getPredicate(int i) {
    return m_predicates[i];
  }

  /**
   * Get the number of predicates for this match pattern step.
   *
   * @return the number of predicates for this match pattern step.
   */
  public final int getPredicateCount() {
    return (null == m_predicates) ? 0 : m_predicates.length;
  }

  /**
   * Set the predicates for this match pattern step.
   *
   * @param predicates An array of expressions that define predicates for this step.
   */
  public void setPredicates(Expression[] predicates) {

    m_predicates = predicates;
    if (null != predicates) {
      for (Expression predicate : predicates) {
        predicate.exprSetParent(this);
      }
    }

    calcScore();
  }

  /** {@inheritDoc} */
  @Override
  public void calcScore() {

    if ((getPredicateCount() > 0) || (null != m_relativePathPattern)) {
      m_score = SCORE_OTHER;
    } else super.calcScore();

    if (null == m_targetString) calcTargetString();
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(XPathContext xctxt, int currentNode)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    DTM dtm = xctxt.getDTM(currentNode);

    if (dtm != null) {
      int expType = dtm.getExpandedTypeID(currentNode);

      return execute(xctxt, currentNode, dtm, expType);
    }

    return NodeTest.SCORE_NONE;
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {
    return execute(xctxt, xctxt.getCurrentNode());
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(XPathContext xctxt, int currentNode, DTM dtm, int expType)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    if (m_whatToShow == NodeTest.SHOW_BYFUNCTION) {
      if (null != m_relativePathPattern) {
        return m_relativePathPattern.execute(xctxt);
      }
      return NodeTest.SCORE_NONE;
    }

    XObject score;

    score = super.execute(xctxt, currentNode, dtm, expType);

    if (score == NodeTest.SCORE_NONE) return NodeTest.SCORE_NONE;

    if (getPredicateCount() != 0) {
      if (!executePredicates(xctxt, dtm, currentNode)) return NodeTest.SCORE_NONE;
    }

    if (null != m_relativePathPattern)
      return m_relativePathPattern.executeRelativePathPattern(xctxt, dtm, currentNode);

    return score;
  }

  /**
   * New Method to check whether the current node satisfies a position predicate
   *
   * @param xctxt The XPath runtime context.
   * @param predPos Which predicate we're evaluating of foo[1][2][3].
   * @param dtm The DTM of the current node.
   * @param context The currentNode.
   * @param pos The position being requested, i.e. the value returned by
   *     m_predicates[predPos].execute(xctxt).
   * @return true of the position of the context matches pos, false otherwise.
   */
  private boolean checkProximityPosition(
      XPathContext xctxt, int predPos, DTM dtm, int context, int pos) {

    try {
      DTMAxisTraverser traverser = dtm.getAxisTraverser(Axis.PRECEDINGSIBLING);

      for (int child = traverser.first(context);
          DTM.NULL != child;
          child = traverser.next(context, child)) {
        try {
          xctxt.pushCurrentNode(child);

          if (NodeTest.SCORE_NONE != super.execute(xctxt, child)) {
            boolean pass = true;

            try {
              xctxt.pushSubContextList(this);

              for (int i = 0; i < predPos; i++) {
                xctxt.pushPredicatePos(i);
                try {
                  XObject pred = m_predicates[i].execute(xctxt);

                  try {
                    if (XObject.CLASS_NUMBER == pred.getType()) {
                      throw new Error("Why: Should never have been called");
                    } else if (!pred.boolWithSideEffects()) {
                      pass = false;

                      break;
                    }
                  } finally {
                    pred.detach();
                  }
                } finally {
                  xctxt.popPredicatePos();
                }
              }
            } finally {
              xctxt.popSubContextList();
            }

            if (pass) pos--;

            if (pos < 1) return false;
          }
        } finally {
          xctxt.popCurrentNode();
        }
      }
    } catch (org.loboevolution.javax.xml.transform.TransformerException se) {

      // TODO: should keep throw sax exception...
      throw new RuntimeException(se.getMessage());
    }

    return pos == 1;
  }

  /**
   * Get the proximity position index of the current node based on this node test.
   *
   * @param xctxt XPath runtime context.
   * @param predPos Which predicate we're evaluating of foo[1][2][3].
   * @param findLast If true, don't terminate when the context node is found.
   * @return the proximity position index of the current node based on the node test.
   */
  private int getProximityPosition(XPathContext xctxt, int predPos, boolean findLast) {

    int pos = 0;
    int context = xctxt.getCurrentNode();
    DTM dtm = xctxt.getDTM(context);
    int parent = dtm.getParent(context);

    try {
      DTMAxisTraverser traverser = dtm.getAxisTraverser(Axis.CHILD);

      for (int child = traverser.first(parent);
          DTM.NULL != child;
          child = traverser.next(parent, child)) {
        try {
          xctxt.pushCurrentNode(child);

          if (NodeTest.SCORE_NONE != super.execute(xctxt, child)) {
            boolean pass = true;

            try {
              xctxt.pushSubContextList(this);

              for (int i = 0; i < predPos; i++) {
                xctxt.pushPredicatePos(i);
                try {
                  XObject pred = m_predicates[i].execute(xctxt);

                  try {
                    if (XObject.CLASS_NUMBER == pred.getType()) {
                      if ((pos + 1) != (int) pred.numWithSideEffects()) {
                        pass = false;

                        break;
                      }
                    } else if (!pred.boolWithSideEffects()) {
                      pass = false;

                      break;
                    }
                  } finally {
                    pred.detach();
                  }
                } finally {
                  xctxt.popPredicatePos();
                }
              }
            } finally {
              xctxt.popSubContextList();
            }

            if (pass) pos++;

            if (!findLast && child == context) {
              return pos;
            }
          }
        } finally {
          xctxt.popCurrentNode();
        }
      }
    } catch (org.loboevolution.javax.xml.transform.TransformerException se) {

      // TODO: should keep throw sax exception...
      throw new RuntimeException(se.getMessage());
    }

    return pos;
  }

  /** {@inheritDoc} */
  @Override
  public int getProximityPosition(XPathContext xctxt) {
    return getProximityPosition(xctxt, xctxt.getPredicatePos(), false);
  }

  /** {@inheritDoc} */
  @Override
  public int getLastPos(XPathContext xctxt) {
    return getProximityPosition(xctxt, xctxt.getPredicatePos(), true);
  }

  /**
   * Execute the match pattern step relative to another step.
   *
   * @param xctxt The XPath runtime context.
   * @param dtm The DTM of the current node.
   * @param currentNode The current node context.
   * @return {@link org.loboevolution.apache.xpath.patterns.NodeTest#SCORE_NODETEST}, {@link
   *     org.loboevolution.apache.xpath.patterns.NodeTest#SCORE_NONE}, {@link
   *     org.loboevolution.apache.xpath.patterns.NodeTest#SCORE_NSWILD}, {@link
   *     org.loboevolution.apache.xpath.patterns.NodeTest#SCORE_QNAME}, or {@link
   *     org.loboevolution.apache.xpath.patterns.NodeTest#SCORE_OTHER}.
   * @throws org.loboevolution.javax.xml.transform.TransformerException in case of error
   */
  protected final XObject executeRelativePathPattern(XPathContext xctxt, DTM dtm, int currentNode)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    XObject score = NodeTest.SCORE_NONE;
    DTMAxisTraverser traverser;

    traverser = dtm.getAxisTraverser(m_axis);

    for (int relative = traverser.first(currentNode);
        DTM.NULL != relative;
        relative = traverser.next(currentNode, relative)) {
      try {
        xctxt.pushCurrentNode(relative);

        score = execute(xctxt);

        if (score != NodeTest.SCORE_NONE) break;
      } finally {
        xctxt.popCurrentNode();
      }
    }

    return score;
  }

  /**
   * Execute the predicates on this step to determine if the current node should be filtered or
   * accepted.
   *
   * @param xctxt The XPath runtime context.
   * @param dtm The DTM of the current node.
   * @param currentNode The current node context.
   * @return true if the node should be accepted, false otherwise.
   * @throws org.loboevolution.javax.xml.transform.TransformerException in case of error
   */
  protected final boolean executePredicates(XPathContext xctxt, DTM dtm, int currentNode)
      throws org.loboevolution.javax.xml.transform.TransformerException {

    boolean result = true;
    boolean positionAlreadySeen = false;
    int n = getPredicateCount();

    try {
      xctxt.pushSubContextList(this);

      for (int i = 0; i < n; i++) {
        xctxt.pushPredicatePos(i);

        try {
          XObject pred = m_predicates[i].execute(xctxt);

          try {
            if (XObject.CLASS_NUMBER == pred.getType()) {
              int pos = (int) pred.num();

              if (positionAlreadySeen) {
                result = pos == 1;

                break;
              } else {
                positionAlreadySeen = true;

                if (!checkProximityPosition(xctxt, i, dtm, currentNode, pos)) {
                  result = false;

                  break;
                }
              }

            } else if (!pred.boolWithSideEffects()) {
              result = false;

              break;
            }
          } finally {
            pred.detach();
          }
        } finally {
          xctxt.popPredicatePos();
        }
      }
    } finally {
      xctxt.popSubContextList();
    }

    return result;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {

    StringBuilder buf = new StringBuilder();

    for (StepPattern pat = this; pat != null; pat = pat.m_relativePathPattern) {
      if (pat != this) buf.append("/");

      buf.append(Axis.getNames(pat.m_axis));
      buf.append("::");

      if (0x000005000 == pat.m_whatToShow) {
        buf.append("doc()");
      } else if (DTMFilter.SHOW_BYFUNCTION == pat.m_whatToShow) {
        buf.append("function()");
      } else if (DTMFilter.SHOW_ALL == pat.m_whatToShow) {
        buf.append("node()");
      } else if (DTMFilter.SHOW_TEXT == pat.m_whatToShow) {
        buf.append("text()");
      } else if (DTMFilter.SHOW_PROCESSING_INSTRUCTION == pat.m_whatToShow) {
        buf.append("processing-instruction(");

        if (null != pat.m_name) {
          buf.append(pat.m_name);
        }

        buf.append(")");
      } else if (DTMFilter.SHOW_COMMENT == pat.m_whatToShow) {
        buf.append("comment()");
      } else if (null != pat.m_name) {
        if (DTMFilter.SHOW_ATTRIBUTE == pat.m_whatToShow) {
          buf.append("@");
        }

        if (null != pat.m_namespace) {
          buf.append("{");
          buf.append(pat.m_namespace);
          buf.append("}");
        }

        buf.append(pat.m_name);
      } else if (DTMFilter.SHOW_ATTRIBUTE == pat.m_whatToShow) {
        buf.append("@");
      } else if ((DTMFilter.SHOW_DOCUMENT | DTMFilter.SHOW_DOCUMENT_FRAGMENT) == pat.m_whatToShow) {
        buf.append("doc-root()");
      } else {
        buf.append("?").append(Integer.toHexString(pat.m_whatToShow));
      }

      if (null != pat.m_predicates) {
        for (int i = 0; i < pat.m_predicates.length; i++) {
          buf.append("[");
          buf.append(pat.m_predicates[i]);
          buf.append("]");
        }
      }
    }

    return buf.toString();
  }

  /**
   * Set the axis that this step should follow.
   *
   * @param axis The Axis for this test, one of of Axes.ANCESTORORSELF, etc.
   */
  public void setAxis(int axis) {
    m_axis = axis;
  }

  /**
   * Get the axis that this step follows.
   *
   * @return The Axis for this test, one of of Axes.ANCESTORORSELF, etc.
   */
  public int getAxis() {
    return m_axis;
  }

  /** {@inheritDoc} */
  @Override
  public void callVisitors(XPathVisitor visitor) {
    if (visitor.visitMatchPattern()) {
      callSubtreeVisitors(visitor);
    }
  }

  /**
   * Call the visitors on the subtree. Factored out from callVisitors so it may be called by derived
   * classes.
   */
  protected void callSubtreeVisitors(XPathVisitor visitor) {
    if (null != m_predicates) {
      for (Expression m_predicate : m_predicates) {
        if (visitor.visitPredicate(m_predicate)) {
          m_predicate.callVisitors(visitor);
        }
      }
    }
    if (null != m_relativePathPattern) {
      m_relativePathPattern.callVisitors(visitor);
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(Expression expr) {
    if (!super.deepEquals(expr)) return false;

    StepPattern sp = (StepPattern) expr;

    if (null != m_predicates) {
      int n = m_predicates.length;
      if ((null == sp.m_predicates) || (sp.m_predicates.length != n)) return false;
      for (int i = 0; i < n; i++) {
        if (!m_predicates[i].deepEquals(sp.m_predicates[i])) return false;
      }
    } else if (null != sp.m_predicates) return false;

    if (null != m_relativePathPattern) {
      if (!m_relativePathPattern.deepEquals(sp.m_relativePathPattern)) return false;
    } else if (sp.m_relativePathPattern != null) return false;

    return true;
  }
}
