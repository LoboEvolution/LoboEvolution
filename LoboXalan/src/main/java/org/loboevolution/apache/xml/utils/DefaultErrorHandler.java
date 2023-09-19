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
package org.loboevolution.apache.xml.utils;

import java.io.PrintWriter;
import org.loboevolution.javax.xml.transform.ErrorListener;
import org.loboevolution.javax.xml.transform.TransformerException;

/** Implement SAX error handler for default reporting. */
public class DefaultErrorHandler implements ErrorListener {
  PrintWriter m_pw;

  /**
   * if this flag is set to true, we will rethrow the exception on the error() and fatalError()
   * methods. If it is false, the errors are reported to System.err.
   */
  final boolean m_throwExceptionOnError;

  /** Constructor DefaultErrorHandler */
  public DefaultErrorHandler() {
    this(true);
  }

  /**
   * Constructor DefaultErrorHandler
   *
   * @param throwExceptionOnError boolean for throwExceptionOnError
   */
  public DefaultErrorHandler(boolean throwExceptionOnError) {
    // Defer creation of a PrintWriter until it's actually needed
    m_throwExceptionOnError = throwExceptionOnError;
  }

  /**
   * Retrieve <code>java.io.PrintWriter</code> to which errors are being directed.
   *
   * @return The <code>PrintWriter</code> installed via the constructor or the default <code>
   *     PrintWriter</code>
   */
  public PrintWriter getErrorWriter() {
    // Defer creating the java.io.PrintWriter until an error needs to be
    // reported.
    if (m_pw == null) {
      m_pw = new PrintWriter(System.err, true);
    }
    return m_pw;
  }

  /** {@inheritDoc} */
  @Override
  public void warning(TransformerException exception) throws TransformerException {
    PrintWriter pw = getErrorWriter();
    pw.println(exception.getMessage());
  }

  /** {@inheritDoc} */
  @Override
  public void error(TransformerException exception) throws TransformerException {
    // If the m_throwExceptionOnError flag is true, rethrow the exception.
    // Otherwise report the error to System.err.
    if (m_throwExceptionOnError) throw exception;
    else {
      PrintWriter pw = getErrorWriter();
      pw.println(exception.getMessage());
    }
  }

  /** {@inheritDoc} */
  @Override
  public void fatalError(TransformerException exception) throws TransformerException {
    // If the m_throwExceptionOnError flag is true, rethrow the exception.
    // Otherwise report the error to System.err.
    if (m_throwExceptionOnError) throw exception;
    else {
      PrintWriter pw = getErrorWriter();
      pw.println(exception.getMessage());
    }
  }
}
