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
package org.loboevolution.apache.xpath.axes;

import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.compiler.Compiler;
import org.loboevolution.apache.xpath.compiler.OpMap;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.utils.PrefixResolver;

/** Location path iterator that uses Walkers. */
public class WalkingIterator extends LocPathIterator {

  /**
   * Create a WalkingIterator iterator, including creation of step walkers from the opcode list, and
   * call back into the Compiler to create predicate expressions.
   *
   * @param compiler The Compiler which is creating this expression.
   * @param opPos The position of this iterator in the opcode list from the compiler.
   * @param shouldLoadWalkers True if walkers should be loaded, or false if this is a derived
   *     iterator and it doesn't wish to load child walkers.
   * @throws org.loboevolution.javax.xml.transform.TransformerException if any
   */
  WalkingIterator(Compiler compiler, int opPos, int analysis, boolean shouldLoadWalkers)
      throws org.loboevolution.javax.xml.transform.TransformerException {
    super(analysis);

    int firstStepPos = OpMap.getFirstChildPos(opPos);

    if (shouldLoadWalkers) {
      m_firstWalker = WalkerFactory.loadWalkers(this, compiler, firstStepPos);
      m_lastUsedWalker = m_firstWalker;
    }
  }

  /**
   * Create a WalkingIterator object.
   *
   * @param nscontext The namespace context for this iterator, should be OK if null.
   */
  public WalkingIterator(PrefixResolver nscontext) {

    super(nscontext);
  }

  /** {@inheritDoc} */
  @Override
  public int getAnalysisBits() {
    int bits = 0;
    if (null != m_firstWalker) {
      AxesWalker walker = m_firstWalker;

      while (null != walker) {
        int bit = walker.getAnalysisBits();
        bits |= bit;
        walker = walker.getNextWalker();
      }
    }
    return bits;
  }

  /** {@inheritDoc} */
  @Override
  public Object clone() throws CloneNotSupportedException {

    WalkingIterator clone = (WalkingIterator) super.clone();
    if (null != m_firstWalker) {
      clone.m_firstWalker = m_firstWalker.cloneDeep(clone, null);
    }

    return clone;
  }

  /** {@inheritDoc} */
  @Override
  public void reset() {

    super.reset();

    if (null != m_firstWalker) {
      m_lastUsedWalker = m_firstWalker;

      m_firstWalker.setRoot(m_context);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void setRoot(int context, Object environment) {

    super.setRoot(context, environment);

    if (null != m_firstWalker) {
      m_firstWalker.setRoot(context);
      m_lastUsedWalker = m_firstWalker;
    }
  }

  /** {@inheritDoc} */
  @Override
  public int nextNode() {
    if (m_foundLast) return DTM.NULL;

    // If the variable stack position is not -1, we'll have to
    // set our position in the variable stack, so our variable access
    // will be correct. Iterators that are at the top level of the
    // expression need to reset the variable stack, while iterators
    // in predicates do not need to, and should not, since their execution
    // may be much later than top-level iterators.
    // m_varStackPos is set in setRoot, which is called
    // from the execute method.
    if (-1 == m_stackFrame) {
      return returnNextNode(m_firstWalker.nextNode());
    }
    return returnNextNode(m_firstWalker.nextNode());
  }

  /**
   * Set the last used walker.
   *
   * @param walker The last used walker, or null.
   */
  public final void setLastUsedWalker(AxesWalker walker) {
    m_lastUsedWalker = walker;
  }

  /**
   * Get the last used walker.
   *
   * @return The last used walker, or null.
   */
  public final AxesWalker getLastUsedWalker() {
    return m_lastUsedWalker;
  }

  /** {@inheritDoc} */
  @Override
  public void detach() {
    if (m_allowDetach) {
      AxesWalker walker = m_firstWalker;
      while (null != walker) {
        walker.detach();
        walker = walker.getNextWalker();
      }

      m_lastUsedWalker = null;

      // Always call the superclass detach last!
      super.detach();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void callVisitors(XPathVisitor visitor) {
    if (visitor.visitLocationPath()) {
      if (null != m_firstWalker) {
        m_firstWalker.callVisitors(visitor);
      }
    }
  }

  /**
   * The last used step walker in the walker list.
   *
   * @serial
   */
  protected AxesWalker m_lastUsedWalker;

  /**
   * The head of the step walker list.
   *
   * @serial
   */
  protected AxesWalker m_firstWalker;

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(Expression expr) {
    if (!super.deepEquals(expr)) return false;

    AxesWalker walker1 = m_firstWalker;
    AxesWalker walker2 = ((WalkingIterator) expr).m_firstWalker;
    while ((null != walker1) && (null != walker2)) {
      if (!walker1.deepEquals(walker2)) return false;
      walker1 = walker1.getNextWalker();
      walker2 = walker2.getNextWalker();
    }

    return (null == walker1) && (null == walker2);
  }
}
