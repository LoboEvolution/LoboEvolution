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
package org.loboevolution.apache.xml.dtm;

import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;

/** This class specifies an exceptional condition that occured in the DTM module. */
public class DTMException extends RuntimeException {

  /**
   * Field containedException specifies a wrapped exception. May be null.
   *
   * @serial
   */
  Throwable containedException;

  /** {@inheritDoc} */
  @Override
  public Throwable getCause() {

    return (containedException == this) ? null : containedException;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized Throwable initCause(Throwable cause) {

    if ((this.containedException == null) && (cause != null)) {
      throw new IllegalStateException(
          XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_CANNOT_OVERWRITE_CAUSE, null));
    }

    if (cause == this) {
      throw new IllegalArgumentException(
          XPATHMessages.createXPATHMessage(
              XPATHErrorResources.ER_SELF_CAUSATION_NOT_PERMITTED, null));
    }

    this.containedException = cause;

    return this;
  }

  /**
   * Create a new DTMException.
   *
   * @param message The error or warning message.
   */
  public DTMException(String message) {

    super(message);

    this.containedException = null;
  }

  /** {@inheritDoc} */
  @Override
  public void printStackTrace() {
    printStackTrace(new java.io.PrintWriter(System.err, true));
  }

  /** {@inheritDoc} */
  @Override
  public void printStackTrace(java.io.PrintStream s) {
    printStackTrace(new java.io.PrintWriter(s));
  }
}
