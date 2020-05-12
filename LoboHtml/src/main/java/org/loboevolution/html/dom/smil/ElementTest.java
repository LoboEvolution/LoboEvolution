/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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

package org.loboevolution.html.dom.smil;

import org.w3c.dom.DOMException;

/**
 * Defines the test attributes interface. See the Test attributes definition .
 *
 * @author utente
 * @version $Id: $Id
 */
public interface ElementTest {
	/**
	 * The systemBitrate value.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a int.
	 */
	public int getSystemBitrate();

	/**
	 * <p>setSystemBitrate.</p>
	 *
	 * @param systemBitrate a int.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void setSystemBitrate(int systemBitrate) throws DOMException;

	/**
	 * The systemCaptions value.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a boolean.
	 */
	public boolean getSystemCaptions();

	/**
	 * <p>setSystemCaptions.</p>
	 *
	 * @param systemCaptions a boolean.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void setSystemCaptions(boolean systemCaptions) throws DOMException;

	/**
	 * The systemLanguage value.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
	public String getSystemLanguage();

	/**
	 * <p>setSystemLanguage.</p>
	 *
	 * @param systemLanguage a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void setSystemLanguage(String systemLanguage) throws DOMException;

	/**
	 * The result of the evaluation of the systemRequired attribute.
	 *
	 * @return a boolean.
	 */
	public boolean getSystemRequired();

	/**
	 * The result of the evaluation of the systemScreenSize attribute.
	 *
	 * @return a boolean.
	 */
	public boolean getSystemScreenSize();

	/**
	 * The result of the evaluation of the systemScreenDepth attribute.
	 *
	 * @return a boolean.
	 */
	public boolean getSystemScreenDepth();

	/**
	 * The value of the systemOverdubOrSubtitle attribute.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
	public String getSystemOverdubOrSubtitle();

	/**
	 * <p>setSystemOverdubOrSubtitle.</p>
	 *
	 * @param systemOverdubOrSubtitle a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void setSystemOverdubOrSubtitle(String systemOverdubOrSubtitle) throws DOMException;

	/**
	 * The value of the systemAudioDesc attribute.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a boolean.
	 */
	public boolean getSystemAudioDesc();

	/**
	 * <p>setSystemAudioDesc.</p>
	 *
	 * @param systemAudioDesc a boolean.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void setSystemAudioDesc(boolean systemAudioDesc) throws DOMException;

}
