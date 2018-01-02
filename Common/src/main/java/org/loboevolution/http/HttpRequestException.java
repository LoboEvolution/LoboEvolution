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
package org.loboevolution.http;

import java.io.IOException;

/**
 * HTTP request exception whose cause is always an {@link IOException}.
 */
public class HttpRequestException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1170466989781746231L;

	/**
	 * Create a new HttpRequestException with the given cause
	 *
	 * @param cause
	 */
	public HttpRequestException(final IOException cause) {
		super(cause);
	}

	/**
	 * Get {@link IOException} that triggered this request exception
	 *
	 * @return {@link IOException} cause
	 */
	@Override
	public IOException getCause() {
		return (IOException) super.getCause();
	}
}
