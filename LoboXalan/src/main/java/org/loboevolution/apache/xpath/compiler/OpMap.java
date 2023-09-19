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
package org.loboevolution.apache.xpath.compiler;

import org.loboevolution.javax.xml.transform.TransformerException;
import org.loboevolution.apache.xpath.patterns.NodeTest;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.apache.xml.utils.ObjectVector;

/** This class represents the data structure basics of the XPath object. */
public class OpMap {

  /** The current pattern string, for diagnostics purposes */
  protected String m_currentPattern;

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return m_currentPattern;
  }

  /**
   * Return the expression as a string for diagnostics.
   *
   * @return The expression string.
   */
  public String getPatternString() {
    return m_currentPattern;
  }

  /** The starting size of the token queue. */
  static final int MAXTOKENQUEUESIZE = 500;

  /*
   * Amount to grow token queue when it becomes full
   */
  static final int BLOCKTOKENQUEUESIZE = 500;

  /**
   * TokenStack is the queue of used tokens. The current token is the token at the end of the
   * m_tokenQueue. The idea is that the queue can be marked and a sequence of tokens can be reused.
   */
  final ObjectVector m_tokenQueue = new ObjectVector(MAXTOKENQUEUESIZE, BLOCKTOKENQUEUESIZE);

  /**
   * Get the XPath as a list of tokens.
   *
   * @return ObjectVector of tokens.
   */
  public ObjectVector getTokenQueue() {
    return m_tokenQueue;
  }

  /**
   * Get size of the token queue.
   *
   * @return The size of the token queue.
   */
  public int getTokenQueueSize() {
    return m_tokenQueue.size();
  }

  /**
   * An operations map is used instead of a proper parse tree. It contains operations codes and
   * indexes into the m_tokenQueue. I use an array instead of a full parse tree in order to cut down
   * on the number of objects created.
   */
  OpMapVector m_opMap = null;

  // Position indexes

  /**
   * The length is always the opcode position + 1. Length is always expressed as the opcode+length
   * bytes, so it is always 2 or greater.
   */
  public static final int MAPINDEX_LENGTH = 1;

  /** Replace the large arrays with a small array. */
  void shrink() {

    int n = m_opMap.elementAt(MAPINDEX_LENGTH);
    m_opMap.setToSize(n + 4);

    m_opMap.setElementAt(0, n);
    m_opMap.setElementAt(0, n + 1);
    m_opMap.setElementAt(0, n + 2);

    n = m_tokenQueue.size();
    m_tokenQueue.setToSize(n + 4);

    m_tokenQueue.setElementAt(null, n);
    m_tokenQueue.setElementAt(null, n + 1);
    m_tokenQueue.setElementAt(null, n + 2);
  }

  /**
   * Given an operation position, return the current op.
   *
   * @param opPos index into op map.
   * @return the op that corresponds to the opPos argument.
   */
  public int getOp(int opPos) {
    return m_opMap.elementAt(opPos);
  }

  /**
   * Set the op at index to the given int.
   *
   * @param opPos index into op map.
   * @param value Value to set
   */
  public void setOp(int opPos, int value) {
    m_opMap.setElementAt(value, opPos);
  }

  /**
   * Given an operation position, return the end position, i.e. the beginning of the next operation.
   *
   * @param opPos An op position of an operation for which there is a size entry following.
   * @return position of next operation in m_opMap.
   */
  public int getNextOpPos(int opPos) {
    return opPos + m_opMap.elementAt(opPos + 1);
  }

  /**
   * Given a location step position, return the end position, i.e. the beginning of the next step.
   *
   * @param opPos the position of a location step.
   * @return the position of the next location step.
   */
  public int getNextStepPos(int opPos) {

    int stepType = getOp(opPos);

    if ((stepType >= OpCodes.AXES_START_TYPES) && (stepType <= OpCodes.AXES_END_TYPES)) {
      return getNextOpPos(opPos);
    } else if ((stepType >= OpCodes.FIRST_NODESET_OP) && (stepType <= OpCodes.LAST_NODESET_OP)) {
      int newOpPos = getNextOpPos(opPos);

      while (OpCodes.OP_PREDICATE == getOp(newOpPos)) {
        newOpPos = getNextOpPos(newOpPos);
      }

      stepType = getOp(newOpPos);

      if (!((stepType >= OpCodes.AXES_START_TYPES) && (stepType <= OpCodes.AXES_END_TYPES))) {
        return OpCodes.ENDOP;
      }

      return newOpPos;
    } else {
      throw new RuntimeException(
          XPATHMessages.createXPATHMessage(
              XPATHErrorResources.ER_UNKNOWN_STEP, new Object[] {String.valueOf(stepType)}));
    }
  }

  /**
   * Given an FROM_stepType position, return the position of the first predicate, if there is one,
   * or else this will point to the end of the FROM_stepType. Example: int posOfPredicate =
   * xpath.getNextOpPos(stepPos); boolean hasPredicates = OpCodes.OP_PREDICATE ==
   * xpath.getOp(posOfPredicate);
   *
   * @param opPos position of FROM_stepType op.
   * @return position of predicate in FROM_stepType structure.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  public int getFirstPredicateOpPos(int opPos) throws TransformerException {

    int stepType = m_opMap.elementAt(opPos);

    if ((stepType >= OpCodes.AXES_START_TYPES) && (stepType <= OpCodes.AXES_END_TYPES)) {
      return opPos + m_opMap.elementAt(opPos + 2);
    } else if ((stepType >= OpCodes.FIRST_NODESET_OP) && (stepType <= OpCodes.LAST_NODESET_OP)) {
      return opPos + m_opMap.elementAt(opPos + 1);
    } else if (-2 == stepType) {
      return -2;
    } else {
      error(
          org.loboevolution.apache.xpath.res.XPATHErrorResources.ER_UNKNOWN_OPCODE,
          new Object[] {String.valueOf(stepType)});
      return -1;
    }
  }

  /**
   * Tell the user of an error, and probably throw an exception.
   *
   * @param msg An error msgkey that corresponds to one of the constants found in {@link
   *     org.loboevolution.apache.xpath.res.XPATHErrorResources}, which is a key for a format
   *     string.
   * @param args An array of arguments represented in the format string, which may be null.
   * @throws TransformerException if the current ErrorListoner determines to throw an exception.
   */
  public void error(String msg, Object[] args) throws TransformerException {

    String fmsg = XPATHMessages.createXPATHMessage(msg, args);

    throw new TransformerException(fmsg);
  }

  /**
   * Go to the first child of a given operation.
   *
   * @param opPos position of operation.
   * @return The position of the first child of the operation.
   */
  public static int getFirstChildPos(int opPos) {
    return opPos + 2;
  }

  /**
   * Given a location step, get the length of that step.
   *
   * @param opPos Position of location step in op map.
   * @return The length of the step.
   */
  public int getArgLengthOfStep(int opPos) {
    return m_opMap.elementAt(opPos + MAPINDEX_LENGTH + 1) - 3;
  }

  /**
   * Get the first child position of a given location step.
   *
   * @param opPos Position of location step in the location map.
   * @return The first child position of the step.
   */
  public static int getFirstChildPosOfStep(int opPos) {
    return opPos + 3;
  }

  /**
   * Get the test type of the step, i.e. NODETYPE_XXX value.
   *
   * @param opPosOfStep The position of the FROM_XXX step.
   * @return NODETYPE_XXX value.
   */
  public int getStepTestType(int opPosOfStep) {
    return m_opMap.elementAt(opPosOfStep + 3); // skip past op, len, len without predicates
  }

  /**
   * Get the namespace of the step.
   *
   * @param opPosOfStep The position of the FROM_XXX step.
   * @return The step's namespace, NodeTest.WILD, or null for null namespace.
   */
  public String getStepNS(int opPosOfStep) {

    int argLenOfStep = getArgLengthOfStep(opPosOfStep);

    // System.out.println("getStepNS.argLenOfStep: "+argLenOfStep);
    if (argLenOfStep == 3) {
      int index = m_opMap.elementAt(opPosOfStep + 4);

      if (index >= 0) return (String) m_tokenQueue.elementAt(index);
      else if (OpCodes.ELEMWILDCARD == index) return NodeTest.WILD;
      else return null;
    } else return null;
  }

  /**
   * Get the local name of the step.
   *
   * @param opPosOfStep The position of the FROM_XXX step.
   * @return OpCodes.EMPTY, OpCodes.ELEMWILDCARD, or the local name.
   */
  public String getStepLocalName(int opPosOfStep) {

    int argLenOfStep = getArgLengthOfStep(opPosOfStep);

    // System.out.println("getStepLocalName.argLenOfStep: "+argLenOfStep);
    int index;

    switch (argLenOfStep) {
      case 0:
        index = OpCodes.EMPTY;
        break;
      case 1:
        index = OpCodes.ELEMWILDCARD;
        break;
      case 2:
        index = m_opMap.elementAt(opPosOfStep + 4);
        break;
      case 3:
        index = m_opMap.elementAt(opPosOfStep + 5);
        break;
      default:
        index = OpCodes.EMPTY;
        break; // Should assert error
    }

    // int index = (argLenOfStep == 3) ? m_opMap[opPosOfStep+5]
    // : ((argLenOfStep == 1) ? -3 : -2);
    if (index >= 0) return m_tokenQueue.elementAt(index).toString();
    else if (OpCodes.ELEMWILDCARD == index) return NodeTest.WILD;
    else return null;
  }
}
