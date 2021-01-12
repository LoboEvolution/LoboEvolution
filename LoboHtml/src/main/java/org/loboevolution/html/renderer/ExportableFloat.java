/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.renderer;

/**
 * <p>ExportableFloat class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ExportableFloat {
	/** Constant EMPTY_ARRAY */
	public static final ExportableFloat[] EMPTY_ARRAY = new ExportableFloat[0];
	public final BoundableRenderable element;
	public final boolean leftFloat;
	public final int origX;
	public final int origY;

	/**
	 * <p>Constructor for ExportableFloat.</p>
	 *
	 * @param element a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 * @param leftFloat a boolean.
	 * @param origX a int.
	 * @param origY a int.
	 */
	public ExportableFloat(BoundableRenderable element, boolean leftFloat, int origX, int origY) {
		super();
		this.element = element;
		this.leftFloat = leftFloat;
		this.origX = origX;
		this.origY = origY;
	}
}
