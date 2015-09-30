/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.w3c.html;


/**
 * The public interface MediaError.
 */
public interface MediaError {
	// MediaError
	/** The Constant MEDIA_ERR_ABORTED. */
	short MEDIA_ERR_ABORTED = 1;

	/** The Constant MEDIA_ERR_NETWORK. */
	short MEDIA_ERR_NETWORK = 2;

	/** The Constant MEDIA_ERR_DECODE. */
	short MEDIA_ERR_DECODE = 3;

	/** The Constant MEDIA_ERR_SRC_NOT_SUPPORTED. */
	short MEDIA_ERR_SRC_NOT_SUPPORTED = 4;

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	short getCode();
}
