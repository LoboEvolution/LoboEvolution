/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
