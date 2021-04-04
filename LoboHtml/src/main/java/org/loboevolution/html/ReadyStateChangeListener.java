/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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
/*
 * Created on Nov 13, 2005
 */
package org.loboevolution.html;

import java.util.EventListener;

/**
 * The ReadyStateChangeListener interface is implemented to receive
 * ReadyState change events from {@link org.loboevolution.http.HttpRequest}.
 *
 * @see org.loboevolution.http.HttpRequest#addReadyStateChangeListener(ReadyStateChangeListener)
 * Author J. H. S.
 *
 */
public interface ReadyStateChangeListener extends EventListener {
	/**
	 * This method is called when the ReadyState changes.
	 */
	void readyStateChanged();
}
