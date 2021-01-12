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

package org.loboevolution.html.gui;

import org.loboevolution.html.dom.domimpl.NodeImpl;

class DocumentNotification {
	/** Constant GENERIC=3 */
	public static final int GENERIC = 3;
	/** Constant LOOK=0 */
	public static final int LOOK = 0;
	/** Constant POSITION=1 */
	public static final int POSITION = 1;
	/** Constant SIZE=2 */
	public static final int SIZE = 2;

	public final NodeImpl node;
	public final int type;

	/**
	 * <p>Constructor for DocumentNotification.</p>
	 *
	 * @param type a int.
	 * @param node a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 */
	public DocumentNotification(int type, NodeImpl node) {
		this.type = type;
		this.node = node;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "DocumentNotification[type=" + this.type + ",node=" + this.node + "]";
	}
}
