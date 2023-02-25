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

/**
 * This class is for throwing important checked exceptions over non-checked methods. It should be
 * used with care, and in limited circumstances.
 */
public class WrappedRuntimeException extends RuntimeException {

  /**
   * Primary checked exception.
   *
   * @serial
   */
  private final Exception m_exception;

  /**
   * Construct a WrappedRuntimeException from a checked exception.
   *
   * @param e Primary checked exception
   */
  public WrappedRuntimeException(Exception e) {

    super(e.getMessage());

    m_exception = e;
  }

  /**
   * Get the checked exception that this runtime exception wraps.
   *
   * @return The primary checked exception
   */
  public Exception getException() {
    return m_exception;
  }
}
