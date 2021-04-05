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
package org.loboevolution.common;

/**
 * <p>BufferExceededException class.</p>
 *
 *
 *
 */
public class BufferExceededException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for BufferExceededException.</p>
	 */
	public BufferExceededException() {
		super();
	}

	/**
	 * <p>Constructor for BufferExceededException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public BufferExceededException(String message) {
		super(message);
	}

	/**
	 * <p>Constructor for BufferExceededException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public BufferExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Constructor for BufferExceededException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public BufferExceededException(Throwable cause) {
		super(cause);
	}
}
