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

package org.loboevolution.html.dom.nodeimpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.loboevolution.html.dom.DOMError;
import org.loboevolution.html.dom.DOMLocator;

/**
 * This is a utility implementation of EventListener
 * that captures all events and provides access
 * to lists of all events by mode
 */
@Data
@AllArgsConstructor
public class DOMErrorImpl implements DOMError {
  private final short severity;
  private final String message;
  private final String type;
  private final Object relatedException;
  private final Object relatedData;

  private final DOMLocator location;

  /**
   * Public constructor
   *
   */
  public DOMErrorImpl(DOMError src) {
    this.severity = src.getSeverity();
    this.message = src.getMessage();
    this.type = src.getType();
    this.relatedException = src.getRelatedException();
    this.relatedData = src.getRelatedData();
    this.location = new DOMLocatorImpl(src.getLocation());
  }
}
