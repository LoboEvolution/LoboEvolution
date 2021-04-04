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
 * Created on Oct 16, 2005
 */
package org.loboevolution.html.parser;

import org.xml.sax.Locator;

class LocatorImpl implements Locator {
	private final int columnNumber;
	private final int lineNumber;
	private final String publicId;
	private final String systemId;

	/**
	 * <p>Constructor for LocatorImpl.</p>
	 *
	 * @param pid a {@link java.lang.String} object.
	 * @param sid a {@link java.lang.String} object.
	 * @param lnumber a int.
	 * @param cnumber a int.
	 */
	public LocatorImpl(String pid, String sid, int lnumber, int cnumber) {
		super();
		this.publicId = pid;
		this.systemId = sid;
		this.lineNumber = lnumber;
		this.columnNumber = cnumber;
	}

	/** {@inheritDoc} */
	@Override
	public int getColumnNumber() {
		return this.columnNumber;
	}

	/** {@inheritDoc} */
	@Override
	public int getLineNumber() {
		return this.lineNumber;
	}

	/** {@inheritDoc} */
	@Override
	public String getPublicId() {
		return this.publicId;
	}

	/** {@inheritDoc} */
	@Override
	public String getSystemId() {
		return this.systemId;
	}
}
