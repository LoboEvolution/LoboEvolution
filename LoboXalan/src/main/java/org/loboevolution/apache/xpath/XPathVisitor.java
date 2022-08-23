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
