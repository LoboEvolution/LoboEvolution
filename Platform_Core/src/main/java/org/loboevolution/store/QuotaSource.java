/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jun 1, 2005
 */
package org.loboevolution.store;

import java.io.IOException;

/**
 * The Interface QuotaSource.
 *
 * @author J. H. S.
 */
public interface QuotaSource {

	/**
	 * Gets the space left.
	 *
	 * @return the space left
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	long getSpaceLeft() throws IOException;

	/**
	 * Adds the used bytes.
	 *
	 * @param total
	 *            the total
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void addUsedBytes(long total) throws IOException;
}
