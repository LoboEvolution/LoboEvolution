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

/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.loboevolution.javax.xml.stream;

public class FactoryConfigurationError extends Error {
    private static final long serialVersionUID = -2994412584589975744L;

  Exception nested;

  /**
   * Default constructor
   */
  public FactoryConfigurationError(){}

  /**
   * Construct an exception with a nested inner exception
   *
   * @param e the exception to nest
   */
  public FactoryConfigurationError(java.lang.Exception e){
    nested = e;
  }

  /**
   * Construct an exception with a nested inner exception
   * and a message
   *
   * @param e the exception to nest
   * @param msg the message to report
   */
  public FactoryConfigurationError(java.lang.Exception e, java.lang.String msg){
    super(msg);
    nested = e;
  }

  /**
   * Construct an exception with a nested inner exception
   * and a message
   *
   * @param msg the message to report
   * @param e the exception to nest
   */
  public FactoryConfigurationError(java.lang.String msg, java.lang.Exception e){
    super(msg);
    nested = e;
  }

  /**
   * Construct an exception with associated message
   *
   * @param msg the message to report
   */
  public FactoryConfigurationError(java.lang.String msg) {
    super(msg);
  }

  /**
   * Return the nested exception (if any)
   *
   * @return the nested exception or null
   */
  public Exception getException() {
    return nested;
  }
    /**
     * use the exception chaining mechanism of JDK1.4
    */
    @Override
    public Throwable getCause() {
        return nested;
    }

  /**
   * Report the message associated with this error
   *
   * @return the string value of the message
   */
  public String getMessage() {
    String msg = super.getMessage();
    if(msg != null)
      return msg;
    if(nested != null){
      msg = nested.getMessage();
      if(msg == null)
        msg = nested.getClass().toString();
    }
    return msg;
  }



}
