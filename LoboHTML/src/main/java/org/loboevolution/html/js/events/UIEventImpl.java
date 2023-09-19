/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
	 * @param viewArg a {@link org.loboevolution.html.node.views.AbstractView} object.
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
