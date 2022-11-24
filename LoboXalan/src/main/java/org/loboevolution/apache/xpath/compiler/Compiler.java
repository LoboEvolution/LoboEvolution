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
package org.loboevolution.apache.xpath.compiler;

import org.loboevolution.javax.xml.transform.ErrorListener;
import org.loboevolution.javax.xml.transform.TransformerException;
import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.axes.UnionPathIterator;
import org.loboevolution.apache.xpath.axes.WalkerFactory;
import org.loboevolution.apache.xpath.functions.Function;
import org.loboevolution.apache.xpath.functions.WrongNumberArgsException;
import org.loboevolution.apache.xpath.objects.XNumber;
import org.loboevolution.apache.xpath.objects.XString;
import org.loboevolution.apache.xpath.operations.And;
import org.loboevolution.apache.xpath.operations.Div;
import org.loboevolution.apache.xpath.operations.Equals;
import org.loboevolution.apache.xpath.operations.Gt;
import org.loboevolution.apache.xpath.operations.Gte;
import org.loboevolution.apache.xpath.operations.Lt;
import org.loboevolution.apache.xpath.operations.Lte;
import org.loboevolution.apache.xpath.operations.Minus;
import org.loboevolution.apache.xpath.operations.Mod;
import org.loboevolution.apache.xpath.operations.Mult;
import org.loboevolution.apache.xpath.operations.Neg;
import org.loboevolution.apache.xpath.operations.NotEquals;
import org.loboevolution.apache.xpath.operations.Operation;
import org.loboevolution.apache.xpath.operations.Or;
import org.loboevolution.apache.xpath.operations.Plus;
import org.loboevolution.apache.xpath.operations.UnaryOperation;
import org.loboevolution.apache.xpath.patterns.FunctionPattern;
import org.loboevolution.apache.xpath.patterns.NodeTest;
import org.loboevolution.apache.xpath.patterns.StepPattern;
import org.loboevolution.apache.xpath.patterns.UnionPattern;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.apache.xml.dtm.Axis;
import org.loboevolution.apache.xml.dtm.DTMFilter;
import org.loboevolution.apache.xml.dtm.DTMIterator;
import org.loboevolution.apache.xml.utils.PrefixResolver;

/**
 * An instance of this class compiles an XPath string expression into a Expression object. This
 * class compiles the string into a sequence of operation codes (op map) and then builds from that
 * into an Expression tree.
 */
public class Compiler extends OpMap {

  /**
   * Construct a Compiler object with a specific ErrorListener.
   *
   * @param errorHandler Error listener where messages will be sent, or null if messages should be
   *     sent to System err.
   * @param fTable The FunctionTable object where the xpath build-in functions are stored.
   */
  public Compiler(ErrorListener errorHandler, FunctionTable fTable) {
    m_errorHandler = errorHandler;
    m_functionTable = fTable;
  }

  /**
   * Execute the XPath object from a given opcode position.
   *
   * @param opPos The current position in the xpath.m_opMap array.
   * @return The result of the XPath.
   * @throws TransformerException if there is a syntax or other error.
   */
  public Expression compile(int opPos) throws TransformerException {

    int op = getOp(opPos);

    Expression expr = null;
    // System.out.println(getPatternString()+"op: "+op);
    switch (op) {
      case OpCodes.OP_XPATH:
        expr = compile(opPos + 2);
        break;
      case OpCodes.OP_OR:
        expr = or(opPos);
        break;
      case OpCodes.OP_AND:
        expr = and(opPos);
        break;
      case OpCodes.OP_NOTEQUALS:
        expr = notequals(opPos);
        break;
      case OpCodes.OP_EQUALS:
        expr = equals(opPos);
        break;
      case OpCodes.OP_LTE:
        expr = lte(opPos);
        break;
      case OpCodes.OP_LT:
        expr = lt(opPos);
        break;
      case OpCodes.OP_GTE:
        expr = gte(opPos);
        break;
      case OpCodes.OP_GT:
        expr = gt(opPos);
        break;
      case OpCodes.OP_PLUS:
        expr = plus(opPos);
        break;
      case OpCodes.OP_MINUS:
        expr = minus(opPos);
        break;
      case OpCodes.OP_MULT:
        expr = mult(opPos);
        break;
      case OpCodes.OP_DIV:
        expr = div(opPos);
        break;
      case OpCodes.OP_MOD:
        expr = mod(opPos);
        break;
        // case OpCodes.OP_QUO :
        // expr = quo(opPos); break;
      case OpCodes.OP_NEG:
        expr = neg(opPos);
        break;
      case OpCodes.OP_STRING:
        expr = string(opPos);
        break;
      case OpCodes.OP_BOOL:
        expr = bool(opPos);
        break;
      case OpCodes.OP_NUMBER:
        expr = number(opPos);
        break;
      case OpCodes.OP_UNION:
        expr = union(opPos);
        break;
      case OpCodes.OP_LITERAL:
        expr = literal(opPos);
        break;
        // case OpCodes.OP_VARIABLE :
        // expr = variable(opPos); break;
      case OpCodes.OP_GROUP:
        expr = group(opPos);
        break;
      case OpCodes.OP_NUMBERLIT:
        expr = numberlit(opPos);
        break;
      case OpCodes.OP_ARGUMENT:
        expr = arg(opPos);
        break;
        // case OpCodes.OP_EXTFUNCTION :
        // expr = compileExtension(opPos); break;
      case OpCodes.OP_FUNCTION:
        expr = compileFunction(opPos);
        break;
      case OpCodes.OP_LOCATIONPATH:
        expr = locationPath(opPos);
        break;
      case OpCodes.OP_PREDICATE:
        expr = null;
        break; // should never hit this here.
      case OpCodes.OP_MATCHPATTERN:
        expr = matchPattern(opPos + 2);
        break;
      case OpCodes.OP_LOCATIONPATHPATTERN:
        expr = locationPathPattern(opPos);
        break;
      case OpCodes.OP_QUO:
        error(XPATHErrorResources.ER_UNKNOWN_OPCODE, new Object[] {"quo"});
        break;
      default:
        error(XPATHErrorResources.ER_UNKNOWN_OPCODE, new Object[] {Integer.toString(getOp(opPos))});
    }

    return expr;
  }

  /**
   * Bottle-neck compilation of an operation with left and right operands.
   *
   * @param operation non-null reference to parent operation.
   * @param opPos The op map position of the parent operation.
   * @return reference to {@link Operation} instance.
   * @throws TransformerException if there is a syntax or other error.
   */
  private Expression compileOperation(Operation operation, int opPos) throws TransformerException {

    int leftPos = getFirstChildPos(opPos);
    int rightPos = getNextOpPos(leftPos);

    operation.setLeftRight(compile(leftPos), compile(rightPos));

    return operation;
  }

  /**
   * Bottle-neck compilation of a unary operation.
   *
   * @param unary The parent unary operation.
   * @param opPos The position in the op map of the parent operation.
   * @return The unary argument.
   * @throws TransformerException if syntax or other error occurs.
   */
  private Expression compileUnary(UnaryOperation unary, int opPos) throws TransformerException {

    int rightPos = getFirstChildPos(opPos);

    unary.setRight(compile(rightPos));

    return unary;
  }

  /**
   * Compile an 'or' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Or} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression or(int opPos) throws TransformerException {
    return compileOperation(new Or(), opPos);
  }

  /**
   * Compile an 'and' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link And} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression and(int opPos) throws TransformerException {
    return compileOperation(new And(), opPos);
  }

  /**
   * Compile a '!=' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link NotEquals} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression notequals(int opPos) throws TransformerException {
    return compileOperation(new NotEquals(), opPos);
  }

  /**
   * Compile a '=' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Equals} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression equals(int opPos) throws TransformerException {
    return compileOperation(new Equals(), opPos);
  }

  /**
   * Compile a '&#60;=' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Lte} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression lte(int opPos) throws TransformerException {
    return compileOperation(new Lte(), opPos);
  }

  /**
   * Compile a '&#60;' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Lt} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression lt(int opPos) throws TransformerException {
    return compileOperation(new Lt(), opPos);
  }

  /**
   * Compile a '&#62;' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Gte} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression gte(int opPos) throws TransformerException {
    return compileOperation(new Gte(), opPos);
  }

  /**
   * Compile a '&#62;' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Gt} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression gt(int opPos) throws TransformerException {
    return compileOperation(new Gt(), opPos);
  }

  /**
   * Compile a '+' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Plus} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression plus(int opPos) throws TransformerException {
    return compileOperation(new Plus(), opPos);
  }

  /**
   * Compile a '-' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Minus} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression minus(int opPos) throws TransformerException {
    return compileOperation(new Minus(), opPos);
  }

  /**
   * Compile a '*' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Mult} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression mult(int opPos) throws TransformerException {
    return compileOperation(new Mult(), opPos);
  }

  /**
   * Compile a 'div' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Div} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression div(int opPos) throws TransformerException {
    return compileOperation(new Div(), opPos);
  }

  /**
   * Compile a 'mod' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Mod} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression mod(int opPos) throws TransformerException {
    return compileOperation(new Mod(), opPos);
  }

  /**
   * Compile a unary '-' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Neg} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression neg(int opPos) throws TransformerException {
    return compileUnary(new Neg(), opPos);
  }

  /**
   * Compile a 'string(...)' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link org.loboevolution.apache.xpath.operations.String} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression string(int opPos) throws TransformerException {
    return compileUnary(new org.loboevolution.apache.xpath.operations.String(), opPos);
  }

  /**
   * Compile a 'boolean(...)' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link org.loboevolution.apache.xpath.operations.Bool} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression bool(int opPos) throws TransformerException {
    return compileUnary(new org.loboevolution.apache.xpath.operations.Bool(), opPos);
  }

  /**
   * Compile a 'number(...)' operation.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link org.loboevolution.apache.xpath.operations.Number} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression number(int opPos) throws TransformerException {
    return compileUnary(new org.loboevolution.apache.xpath.operations.Number(), opPos);
  }

  /**
   * Compile a literal string value.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link org.loboevolution.apache.xpath.objects.XString} instance.
   */
  protected Expression literal(int opPos) {

    opPos = getFirstChildPos(opPos);

    return (XString) getTokenQueue().elementAt(getOp(opPos));
  }

  /**
   * Compile a literal number value.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link org.loboevolution.apache.xpath.objects.XNumber} instance.
   */
  protected Expression numberlit(int opPos) {

    opPos = getFirstChildPos(opPos);

    return (XNumber) getTokenQueue().elementAt(getOp(opPos));
  }

  /**
   * Compile an expression group.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to the contained expression.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression group(int opPos) throws TransformerException {

    // no-op
    return compile(opPos + 2);
  }

  /**
   * Compile a function argument.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to the argument expression.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression arg(int opPos) throws TransformerException {

    // no-op
    return compile(opPos + 2);
  }

  /**
   * Compile a location path union. The UnionPathIterator itself may create {@link
   * org.loboevolution.apache.xpath.axes.LocPathIterator} children.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link org.loboevolution.apache.xpath.axes.LocPathIterator} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression union(int opPos) throws TransformerException {
    locPathDepth++;
    try {
      return UnionPathIterator.createUnionIterator(this, opPos);
    } finally {
      locPathDepth--;
    }
  }

  private int locPathDepth = -1;

  /**
   * Get the level of the location path or union being constructed.
   *
   * @return 0 if it is a top-level path.
   */
  public int getLocationPathDepth() {
    return locPathDepth;
  }

  /** Get the function table */
  FunctionTable getFunctionTable() {
    return m_functionTable;
  }

  /**
   * Compile a location path. The LocPathIterator itself may create {@link
   * org.loboevolution.apache.xpath.axes.AxesWalker} children.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link org.loboevolution.apache.xpath.axes.LocPathIterator} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  public Expression locationPath(int opPos) throws TransformerException {
    locPathDepth++;
    try {
      DTMIterator iter = WalkerFactory.newDTMIterator(this, opPos, locPathDepth == 0);
      return (Expression) iter; // cast OK, I guess.
    } finally {
      locPathDepth--;
    }
  }

  /**
   * Compile a location step predicate expression.
   *
   * @param opPos The current position in the m_opMap array.
   * @return the contained predicate expression.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  public Expression predicate(int opPos) throws TransformerException {
    return compile(opPos + 2);
  }

  /**
   * Compile an entire match pattern expression.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link UnionPattern} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected Expression matchPattern(int opPos) throws TransformerException {
    locPathDepth++;
    try {
      // First, count...
      int nextOpPos = opPos;
      int i;

      for (i = 0; getOp(nextOpPos) == OpCodes.OP_LOCATIONPATHPATTERN; i++) {
        nextOpPos = getNextOpPos(nextOpPos);
      }

      if (i == 1) return compile(opPos);

      UnionPattern up = new UnionPattern();
      StepPattern[] patterns = new StepPattern[i];

      for (i = 0; getOp(opPos) == OpCodes.OP_LOCATIONPATHPATTERN; i++) {
        nextOpPos = getNextOpPos(opPos);
        patterns[i] = (StepPattern) compile(opPos);
        opPos = nextOpPos;
      }

      up.setPatterns(patterns);

      return up;
    } finally {
      locPathDepth--;
    }
  }

  /**
   * Compile a location match pattern unit expression.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link StepPattern} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  public Expression locationPathPattern(int opPos) throws TransformerException {

    opPos = getFirstChildPos(opPos);

    return stepPattern(opPos, 0, null);
  }

  /**
   * Get a {@link org.loboevolution.html.node.traversal.NodeFilter} bit set that tells what to show for a given node
   * test.
   *
   * @param opPos the op map position for the location step.
   * @return {@link org.loboevolution.html.node.traversal.NodeFilter} bit set that tells what to show for a given
   *     node test.
   */
  public int getWhatToShow(int opPos) {

    int axesType = getOp(opPos);
    int testType = getOp(opPos + 3);

    // System.out.println("testType: "+testType);
    switch (testType) {
      case OpCodes.NODETYPE_COMMENT:
        return DTMFilter.SHOW_COMMENT;
      case OpCodes.NODETYPE_TEXT:
        // return DTMFilter.SHOW_TEXT | DTMFilter.SHOW_COMMENT;
        return DTMFilter.SHOW_TEXT | DTMFilter.SHOW_CDATA_SECTION;
      case OpCodes.NODETYPE_PI:
        return DTMFilter.SHOW_PROCESSING_INSTRUCTION;
      case OpCodes.NODETYPE_NODE:
        // return DTMFilter.SHOW_ALL;
        switch (axesType) {
          case OpCodes.FROM_NAMESPACE:
            return DTMFilter.SHOW_NAMESPACE;
          case OpCodes.FROM_ATTRIBUTES:
          case OpCodes.MATCH_ATTRIBUTE:
            return DTMFilter.SHOW_ATTRIBUTE;
          case OpCodes.FROM_SELF:
          case OpCodes.FROM_ANCESTORS_OR_SELF:
          case OpCodes.FROM_DESCENDANTS_OR_SELF:
            return DTMFilter.SHOW_ALL;
          default:
            if (getOp(0) == OpCodes.OP_MATCHPATTERN)
              return ~DTMFilter.SHOW_ATTRIBUTE
                  & ~DTMFilter.SHOW_DOCUMENT
                  & ~DTMFilter.SHOW_DOCUMENT_FRAGMENT;
            else return ~DTMFilter.SHOW_ATTRIBUTE;
        }
      case OpCodes.NODETYPE_ROOT:
        return DTMFilter.SHOW_DOCUMENT | DTMFilter.SHOW_DOCUMENT_FRAGMENT;
      case OpCodes.NODETYPE_FUNCTEST:
        return NodeTest.SHOW_BYFUNCTION;
      case OpCodes.NODENAME:
        switch (axesType) {
          case OpCodes.FROM_NAMESPACE:
            return DTMFilter.SHOW_NAMESPACE;
          case OpCodes.FROM_ATTRIBUTES:
          case OpCodes.MATCH_ATTRIBUTE:
            return DTMFilter.SHOW_ATTRIBUTE;

            // break;
          case OpCodes.MATCH_ANY_ANCESTOR:
          case OpCodes.MATCH_IMMEDIATE_ANCESTOR:
            return DTMFilter.SHOW_ELEMENT;

            // break;
          default:
            return DTMFilter.SHOW_ELEMENT;
        }
      default:
        // System.err.println("We should never reach here.");
        return DTMFilter.SHOW_ALL;
    }
  }

  private static final boolean DEBUG = false;

  /**
   * Compile a step pattern unit expression, used for both location paths and match patterns.
   *
   * @param opPos The current position in the m_opMap array.
   * @param stepCount The number of steps to expect.
   * @param ancestorPattern The owning StepPattern, which may be null.
   * @return reference to {@link StepPattern} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  protected StepPattern stepPattern(int opPos, int stepCount, StepPattern ancestorPattern)
      throws TransformerException {

    int startOpPos = opPos;
    int stepType = getOp(opPos);

    if (OpCodes.ENDOP == stepType) {
      return null;
    }

    int endStep = getNextOpPos(opPos);

    // int nextStepType = getOpMap()[endStep];
    StepPattern pattern;

    // boolean isSimple = ((OpCodes.ENDOP == nextStepType) && (stepCount == 0));
    int argLen;

    switch (stepType) {
      case OpCodes.OP_FUNCTION:
        if (DEBUG) System.out.println("MATCH_FUNCTION: " + m_currentPattern);
        argLen = getOp(opPos + OpMap.MAPINDEX_LENGTH);
        pattern = new FunctionPattern(compileFunction(opPos), Axis.PARENT);
        break;
      case OpCodes.FROM_ROOT:
        if (DEBUG) System.out.println("FROM_ROOT, " + m_currentPattern);
        argLen = getArgLengthOfStep(opPos);
        opPos = getFirstChildPosOfStep(opPos);
        pattern =
            new StepPattern(
                DTMFilter.SHOW_DOCUMENT | DTMFilter.SHOW_DOCUMENT_FRAGMENT, Axis.PARENT);
        break;
      case OpCodes.MATCH_ATTRIBUTE:
        if (DEBUG)
          System.out.println(
              "MATCH_ATTRIBUTE: " + getStepLocalName(startOpPos) + ", " + m_currentPattern);
        argLen = getArgLengthOfStep(opPos);
        opPos = getFirstChildPosOfStep(opPos);
        pattern =
            new StepPattern(
                DTMFilter.SHOW_ATTRIBUTE,
                getStepNS(startOpPos),
                getStepLocalName(startOpPos),
                Axis.PARENT);
        break;
      case OpCodes.MATCH_ANY_ANCESTOR:
        if (DEBUG)
          System.out.println(
              "MATCH_ANY_ANCESTOR: " + getStepLocalName(startOpPos) + ", " + m_currentPattern);
        argLen = getArgLengthOfStep(opPos);
        opPos = getFirstChildPosOfStep(opPos);
        // bit-o-hackery, but this code is due for the morgue anyway...
        pattern =
            new StepPattern(
                getWhatToShow(startOpPos),
                getStepNS(startOpPos),
                getStepLocalName(startOpPos),
                Axis.ANCESTOR);
        break;
      case OpCodes.MATCH_IMMEDIATE_ANCESTOR:
        if (DEBUG)
          System.out.println(
              "MATCH_IMMEDIATE_ANCESTOR: "
                  + getStepLocalName(startOpPos)
                  + ", "
                  + m_currentPattern);
        argLen = getArgLengthOfStep(opPos);
        opPos = getFirstChildPosOfStep(opPos);
        pattern =
            new StepPattern(
                getWhatToShow(startOpPos),
                getStepNS(startOpPos),
                getStepLocalName(startOpPos),
                Axis.PARENT);
        break;
      default:
        error(XPATHErrorResources.ER_UNKNOWN_MATCH_OPERATION, null);

        return null;
    }

    pattern.setPredicates(getCompiledPredicates(opPos + argLen));
    if (null == ancestorPattern) {
      // This is the magic and invisible "." at the head of every
      // match pattern, and corresponds to the current node in the context
      // list, from where predicates are counted.
      // So, in order to calculate "foo[3]", it has to count from the
      // current node in the context list, so, from that current node,
      // the full pattern is really "self::node()/child::foo[3]". If you
      // translate this to a select pattern from the node being tested,
      // which is really how we're treating match patterns, it works out to
      // self::foo/parent::node[child::foo[3]]", or close enough.
      /*
       * if(addMagicSelf && pattern.getPredicateCount() > 0) { StepPattern selfPattern
       * = new StepPattern(DTMFilter.SHOW_ALL, Axis.PARENT, Axis.CHILD); // We need to
       * keep the new nodetest from affecting the score... XNumber score =
       * pattern.getStaticScore(); pattern.setRelativePathPattern(selfPattern);
       * pattern.setStaticScore(score); selfPattern.setStaticScore(score); }
       */
    } else {
      // System.out.println("Setting "+ancestorPattern+" as relative to "+pattern);
      pattern.setRelativePathPattern(ancestorPattern);
    }

    StepPattern relativePathPattern = stepPattern(endStep, stepCount + 1, pattern);

    return (null != relativePathPattern) ? relativePathPattern : pattern;
  }

  /**
   * Compile a zero or more predicates for a given match pattern.
   *
   * @param opPos The position of the first predicate the m_opMap array.
   * @return reference to array of {@link Expression} instances.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  public Expression[] getCompiledPredicates(int opPos) throws TransformerException {

    int count = countPredicates(opPos);

    if (count > 0) {
      Expression[] predicates = new Expression[count];

      compilePredicates(opPos, predicates);

      return predicates;
    }

    return null;
  }

  /**
   * Count the number of predicates in the step.
   *
   * @param opPos The position of the first predicate the m_opMap array.
   * @return The number of predicates for this step.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  public int countPredicates(int opPos) throws TransformerException {

    int count = 0;

    while (OpCodes.OP_PREDICATE == getOp(opPos)) {
      count++;

      opPos = getNextOpPos(opPos);
    }

    return count;
  }

  /**
   * Compiles predicates in the step.
   *
   * @param opPos The position of the first predicate the m_opMap array.
   * @param predicates An empty pre-determined array of {@link
   *     Expression}s, that will be filled in.
   * @throws TransformerException if any
   */
  private void compilePredicates(int opPos, Expression[] predicates) throws TransformerException {

    for (int i = 0; OpCodes.OP_PREDICATE == getOp(opPos); i++) {
      predicates[i] = predicate(opPos);
      opPos = getNextOpPos(opPos);
    }
  }

  /**
   * Compile a built-in XPath function.
   *
   * @param opPos The current position in the m_opMap array.
   * @return reference to {@link Function} instance.
   * @throws TransformerException if a error occurs creating the Expression.
   */
  Expression compileFunction(int opPos) throws TransformerException {

    int endFunc = opPos + getOp(opPos + 1) - 1;

    opPos = getFirstChildPos(opPos);

    int funcID = getOp(opPos);

    opPos++;

    if (-1 != funcID) {
      Function func = m_functionTable.getFunction(funcID);

      /*
       It is a trick for function-available. Since the function table is an instance field, insert
       this table at compilation time for later usage
      */

      // if (func instanceof FuncExtFunctionAvailable)
      // ((FuncExtFunctionAvailable) func).setFunctionTable(m_functionTable);

      func.postCompileStep(this);

      try {
        int i = 0;

        for (int p = opPos; p < endFunc; p = getNextOpPos(p), i++) {

          // System.out.println("argPos: "+ p);
          // System.out.println("argCode: "+ m_opMap[p]);
          func.setArg(compile(p), i);
        }

        func.checkNumberArgs(i);
      } catch (WrongNumberArgsException wnae) {
        String name = m_functionTable.getFunctionName(funcID);

        error(XPATHErrorResources.ER_ONLY_ALLOWS, new Object[] {name, wnae.getMessage()});
      }

      return func;
    }
    error(XPATHErrorResources.ER_FUNCTION_TOKEN_NOT_FOUND, null);
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public void error(String msg, Object[] args) throws TransformerException {

    String fmsg = XPATHMessages.createXPATHMessage(msg, args);

    if (null != m_errorHandler) {
      m_errorHandler.fatalError(new TransformerException(fmsg));
    } else {
      throw new TransformerException(fmsg);
    }
  }

  /** The current prefixResolver for the execution context. */
  private PrefixResolver m_currentPrefixResolver = null;

  /**
   * Get the current namespace context for the xpath.
   *
   * @return The current prefix resolver, *may* be null, though hopefully not.
   */
  public PrefixResolver getNamespaceContext() {
    return m_currentPrefixResolver;
  }

  /**
   * Set the current namespace context for the xpath.
   *
   * @param pr The resolver for prefixes in the XPath expression.
   */
  public void setNamespaceContext(PrefixResolver pr) {
    m_currentPrefixResolver = pr;
  }

  /**
   * The error listener where errors will be sent. If this is null, errors and warnings will be sent
   * to System.err. May be null.
   */
  final ErrorListener m_errorHandler;

  /** The FunctionTable for all xpath build-in functions */
  private final FunctionTable m_functionTable;
}
