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

import org.loboevolution.apache.xpath.functions.Function;

/**
 * A derivation from this class can be passed to a class that implements the XPathVisitable
 * interface, to have the appropriate method called for each component of the XPath. Aside from
 * possible other uses, the main intention is to provide a reasonable means to perform expression
 * rewriting.
 *
 * <p>Each method has the form <code>
 * boolean visitComponentType(ExpressionOwner owner, ComponentType compType)</code>. The
 * ExpressionOwner argument is the owner of the component, and can be used to reset the expression
 * for rewriting. If a method returns false, the sub hierarchy will not be traversed.
 *
 * <p>This class is meant to be a base class that will be derived by concrete classes, and doesn't
 * much except return true for each method.
 */
public class XPathVisitor {
  /**
   * Visit a LocationPath.
   *
   * @return true if the sub expressions should be traversed.
   */
  public boolean visitLocationPath() {
    return true;
  }

  /**
   * Visit a UnionPath.
   *
   * @return true if the sub expressions should be traversed.
   */
  public boolean visitUnionPath() {
    return true;
  }

  /**
   * Visit a step within a location path.
   *
   * @return true if the sub expressions should be traversed.
   */
  public boolean visitStep() {
    return true;
  }

  /**
   * Visit a predicate within a location path. Note that there isn't a proper unique component for
   * predicates, and that the expression will be called also for whatever type Expression is.
   *
   * @param pred The predicate object.
   * @return true if the sub expressions should be traversed.
   */
  public boolean visitPredicate(Expression pred) {
    return true;
  }

  /**
   * Visit a binary operation.
   *
   * @return true if the sub expressions should be traversed.
   */
  public boolean visitBinaryOperation() {
    return true;
  }

  /**
   * Visit a unary operation.
   *
   * @return true if the sub expressions should be traversed.
   */
  public boolean visitUnaryOperation() {
    return true;
  }

  /**
   * Visit a function.
   *
   * @param func The function reference object.
   * @return true if the sub expressions should be traversed.
   */
  public boolean visitFunction(Function func) {
    return true;
  }

  /**
   * Visit a match pattern.
   *
   * @return true if the sub expressions should be traversed.
   */
  public boolean visitMatchPattern() {
    return true;
  }

  /** Visit a union pattern. */
  public void visitUnionPattern() {}

  /** Visit a string literal. */
  public void visitStringLiteral() {}

  /** Visit a number literal. */
  public void visitNumberLiteral() {}
}
