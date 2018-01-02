/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.w3c.html;

/**
 * The public interface ValidityState.
 */
public interface ValidityState {

	/**
	 * Gets the value missing.
	 *
	 * @return the value missing
	 */
	boolean getValueMissing();

	/**
	 * Gets the type mismatch.
	 *
	 * @return the type mismatch
	 */
	boolean getTypeMismatch();

	/**
	 * Gets the pattern mismatch.
	 *
	 * @return the pattern mismatch
	 */
	boolean getPatternMismatch();

	/**
	 * Gets the too long.
	 *
	 * @return the too long
	 */
	boolean getTooLong();

	/**
	 * Gets the range underflow.
	 *
	 * @return the range underflow
	 */
	boolean getRangeUnderflow();

	/**
	 * Gets the range overflow.
	 *
	 * @return the range overflow
	 */
	boolean getRangeOverflow();

	/**
	 * Gets the step mismatch.
	 *
	 * @return the step mismatch
	 */
	boolean getStepMismatch();

	/**
	 * Gets the custom error.
	 *
	 * @return the custom error
	 */
	boolean getCustomError();

	/**
	 * Gets the valid.
	 *
	 * @return the valid
	 */
	boolean getValid();
}
