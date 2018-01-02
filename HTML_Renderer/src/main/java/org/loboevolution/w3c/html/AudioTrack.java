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

package org.loboevolution.w3c.html;

/**
 * The Interface AudioTrack.
 */
public interface AudioTrack {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	// AudioTrack
	public String getId();

	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public String getKind();

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel();

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage();

	/**
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	public boolean getEnabled();

	/**
	 * Sets the enabled.
	 *
	 * @param enabled
	 *            the new enabled
	 */
	public void setEnabled(boolean enabled);
}
