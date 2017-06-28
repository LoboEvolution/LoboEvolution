/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
/*
 * Created on Apr 23, 2005
 */
package org.lobobrowser.html.renderer;

import java.util.Collection;

/**
 * The Class OverflowException.
 *
 * @author J. H. S.
 */
public class OverflowException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The renderables. */
	private final Collection renderables;

	/**
	 * Instantiates a new overflow exception.
	 *
	 * @param renderables
	 *            the renderables
	 */
	public OverflowException(Collection renderables) {
		super();
		this.renderables = renderables;
	}

	/**
	 * Gets the renderables.
	 *
	 * @return the renderables
	 */
	public Collection getRenderables() {
		return this.renderables;
	}
}
