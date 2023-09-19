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

import org.loboevolution.apache.xpath.Expression;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.XPathVisitor;
import org.loboevolution.apache.xpath.compiler.Compiler;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xpath.res.XPATHMessages;

/**
 * This is a superclass of all XPath functions. This allows two ways for the class to be called. One
 * method is that the super class processes the arguments and hands the results to the derived
 * class, the other method is that the derived class may process it's own arguments, which is faster
 * since the arguments don't have to be added to an array, but causes a larger code footprint.
 */
public abstract class Function extends Expression {

  /**
   * Set an argument expression for a function. This method is called by the XPath compiler.
   *
   * @param arg non-null expression that represents the argument.
   * @param argNum The argument number index.
   * @throws WrongNumberArgsException If the argNum parameter is beyond what is specified for this
   *     function.
   */
  public void setArg(Expression arg, int argNum) throws WrongNumberArgsException {
    reportWrongNumberArgs();
  }

  /**
   * Check that the number of arguments passed to this function is correct. This method is meant to
   * be overloaded by derived classes, to check for the number of arguments for a specific function
   * type. This method is called by the compiler for static number of arguments checking.
   *
   * @param argNum The number of arguments that is being passed to the function.
   * @throws WrongNumberArgsException if any
   */
  public void checkNumberArgs(int argNum) throws WrongNumberArgsException {
    if (argNum != 0) reportWrongNumberArgs();
  }

  /**
   * Constructs and throws a WrongNumberArgException with the appropriate message for this function
   * object. This method is meant to be overloaded by derived classes so that the message will be as
   * specific as possible.
   *
   * @throws WrongNumberArgsException if any
   */
  protected void reportWrongNumberArgs() throws WrongNumberArgsException {
    throw new WrongNumberArgsException(XPATHMessages.createXPATHMessage("zero", null));
  }

  /** {@inheritDoc} */
  @Override
  public XObject execute(XPathContext xctxt) throws org.loboevolution.javax.xml.transform.TransformerException {

    // Programmer's assert. (And, no, I don't want the method to be abstract).
    System.out.println("Error! Function.execute should not be called!");

    return null;
  }

  /** Call the visitors for the function arguments. */
  public void callArgVisitors(XPathVisitor visitor) {}

  /** {@inheritDoc} */
  @Override
  public void callVisitors(XPathVisitor visitor) {
    if (visitor.visitFunction(this)) {
      callArgVisitors(visitor);
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean deepEquals(Expression expr) {
    if (!isSameClass(expr)) return false;

    return true;
  }

  /**
   * This function is currently only being used by Position() and Last(). See respective functions
   * for more detail.
   */
  public void postCompileStep(Compiler compiler) {
    // no default action
  }
}
