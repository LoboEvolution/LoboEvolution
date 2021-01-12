/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.common;

import java.util.EventListener;
import java.util.EventObject;

/**
 * <p>GenericEventListener interface.</p>
 *
 * @author J. H. S.
 * @version $Id: $Id
 */
public interface GenericEventListener extends EventListener {
	/** Constant EMPTY_ARRAY */
	GenericEventListener[] EMPTY_ARRAY = new GenericEventListener[0];

	/**
	 * <p>processEvent.</p>
	 *
	 * @param event a {@link java.util.EventObject} object.
	 */
	void processEvent(EventObject event);
}
