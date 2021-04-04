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
package org.loboevolution.html.js.events;

import org.loboevolution.html.node.events.UIEvent;
import org.loboevolution.html.node.js.Window;

/**
 * <p>UIEventImpl class.</p>
 *
 *
 *
 */
public class UIEventImpl extends EventImpl implements UIEvent {
	
	private Window abstractView;
	
	private double detail;

	/**
	 * <p>Constructor for UIEventImpl.</p>
	 *
	 * @param eventTypeArg a {@link java.lang.String} object.
	 * @param detailArg a int.
	 * @param viewArg a {@link org.w3c.dom.views.AbstractView} object.
	 */
	public UIEventImpl(String eventTypeArg, double detailArg, Window viewArg) {
		super(eventTypeArg, false, false);
		abstractView = viewArg;
		detail = detailArg;
	}
	
	/**
	 * <p>Constructor for UIEventImpl.</p>
	 */
	public UIEventImpl() {
		abstractView = null;
		detail = 0;
	}
	
	/** {@inheritDoc} */
	@Override
	public Window getView() {
		return abstractView;
	}

	/** {@inheritDoc} */
	@Override
	public double getDetail() {
		return detail;
	}

	/** {@inheritDoc} */
	@Override
	public void initUIEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, Window viewArg, double detailArg) {
		super.initEvent(typeArg, canBubbleArg, cancelableArg);
		abstractView = viewArg;
		detail = detailArg;
	}
}
