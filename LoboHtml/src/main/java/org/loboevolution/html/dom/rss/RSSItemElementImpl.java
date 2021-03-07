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

package org.loboevolution.html.dom.rss;

import java.awt.Graphics2D;
import java.util.Iterator;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Node;

/**
 * <p>RSSItemElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class RSSItemElementImpl extends HTMLElementImpl {
	
	/**
	 * <p>Constructor for RSSItemElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public RSSItemElementImpl(String name) {
		super(name);
	}
	
	/**
	 * <p>draw.</p>
	 *
	 * @param graphics a {@link java.awt.Graphics2D} object.
	 * @param y a int.
	 */
	public void draw(Graphics2D graphics, int y) {
		if (hasChildNodes()) {
			NodeListImpl children = (NodeListImpl)getChildNodes();
			for (Iterator<Node> i= children.iterator(); i.hasNext();) {
				Node child = i.next();
				if (child instanceof RSSDrawable) {
					RSSDrawable channel = (RSSDrawable) child;
					channel.draw(graphics, y);
					y = y + 20;
				}
			}
		}
	}
}
