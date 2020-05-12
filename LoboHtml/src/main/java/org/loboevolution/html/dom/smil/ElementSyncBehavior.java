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

/**
 * The synchronization behavior extension.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface ElementSyncBehavior {
	/**
	 * The runtime synchronization behavior for an element.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSyncBehavior();

	/**
	 * The sync tolerance for the associated element. It has an effect only if
	 * the element has syncBehavior="locked" .
	 *
	 * @return a float.
	 */
	public float getSyncTolerance();

	/**
	 * Defines the default value for the runtime synchronization behavior for an
	 * element, and all descendents.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getDefaultSyncBehavior();

	/**
	 * Defines the default value for the sync tolerance for an element, and all
	 * descendents.
	 *
	 * @return a float.
	 */
	public float getDefaultSyncTolerance();

	/**
	 * If set to true, forces the time container playback to sync to this
	 * element.
	 *
	 * @return a boolean.
	 */
	public boolean getSyncMaster();

}
