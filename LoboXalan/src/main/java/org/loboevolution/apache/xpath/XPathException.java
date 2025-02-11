/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import lombok.extern.slf4j.Slf4j;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

import java.io.PrintWriter;

/**
 * This class implements an exception object that all XPath classes will throw in case of an error.
 * This class extends TransformerException, and may hold other exceptions. In the case of nested
 * exceptions, printStackTrace will dump all the traces of the nested exceptions, not just the trace
 * of this object.
 */
@Slf4j
public class XPathException extends TransformerException {

  /**
   * Create an XPathException object that holds an error message.
   *
   * @param message The error message.
   */
  public XPathException(final String message, final SourceLocator ex) {
    super(message);
    this.setLocator(ex);
  }

  /**
   * Create an XPathException object that holds an error message.
   *
   * @param message The error message.
   */
  public XPathException(final String message) {
    super(message);
  }

  /** {@inheritDoc} */
  @Override
  public void printStackTrace(java.io.PrintStream s) {

    if (s == null) s = System.err;

    try {
      super.printStackTrace(s);
    } catch (final Exception e) {
      log.info(e.getMessage());
    }
  }

  /** {@inheritDoc} */
  @Override
  public String getMessage() {
    final String lastMessage = super.getMessage();
    return (null != lastMessage) ? lastMessage : "";
  }

  /** {@inheritDoc} */
  @Override
  public void printStackTrace(PrintWriter s) {

    if (s == null) s = new PrintWriter(System.err);

    try {
      super.printStackTrace(s);
    } catch (final Exception e) {
      log.info(e.getMessage());
    }
  }

  /** {@inheritDoc} */
  @Override
  public Throwable getException() {
    return null;
  }
}
