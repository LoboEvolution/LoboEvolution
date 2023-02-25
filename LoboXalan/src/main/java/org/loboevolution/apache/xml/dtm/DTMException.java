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
