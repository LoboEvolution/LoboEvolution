/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.loboevolution.html.node.events.EventTarget;
import org.loboevolution.html.node.events.MouseEvent;
import org.loboevolution.html.node.js.Window;

import java.awt.event.InputEvent;

/**
 * MouseEventImpl class.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MouseEventImpl extends UIEventImpl implements MouseEvent {

	private double screenX;

	private double screenY;

	private double clientX;

	private double clientY;

	private boolean ctrlKey;

	private boolean shiftKey;

	private boolean altKey;

	private boolean metaKey;

	private int button;

	private EventTarget relatedTarget;

	private InputEvent ie;

	/**
	 * Constructor for MouseEventImpl.
	 *
	 * @param type     a {@link java.lang.String} object.
	 * @param shiftKey a boolean.
	 * @param ctrlKey  a boolean.
	 * @param altKey   a boolean.
	 * @param button   a {@link java.lang.Integer} object.
	 */
	public MouseEventImpl(final String type, final boolean shiftKey, final boolean ctrlKey, final boolean altKey,
			final int button) {
		super(type, ("dblclick".equals(type) ? 2 : 1), null);
		this.shiftKey = shiftKey;
		this.ctrlKey = ctrlKey;
		this.altKey = altKey;
		this.button = (short) button;
	}

	/**
	 * Constructor for MouseEventImpl.
	 */
	public MouseEventImpl() {
		this.shiftKey = false;
		this.ctrlKey = false;
		this.altKey = false;
		this.button = 0;
	}

	/** {@inheritDoc} */
	@Override
	public void initMouseEvent(final String typeArg, final boolean canBubbleArg, final boolean cancelableArg, final Window viewArg,
                               final double detailArg, final double screenXArg, final double screenYArg, final double clientXArg, final double clientYArg,
                               final boolean ctrlKeyArg, final boolean altKeyArg, final boolean shiftKeyArg, final boolean metaKeyArg, final int buttonArg,
                               final EventTarget relatedTargetArg) {
		super.initUIEvent(typeArg, canBubbleArg, cancelableArg, viewArg, detailArg);
		screenX = screenXArg;
		screenY = screenYArg;
		clientX = clientXArg;
		clientY = clientYArg;
		ctrlKey = ctrlKeyArg;
		altKey = altKeyArg;
		shiftKey = shiftKeyArg;
		metaKey = metaKeyArg;
		button = buttonArg;
		relatedTarget = relatedTargetArg;

	}

	/** {@inheritDoc} */
	@Override
	public boolean isCtrlKey() {
		return ctrlKey && ie.isControlDown();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isShiftKey() {
		return shiftKey && ie.isShiftDown();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAltKey() {
		return altKey && ie.isAltDown();
	}

	/** {@inheritDoc} */
	@Override
	public int getButton() {
		if (ie instanceof MouseEvent) {
			return (short) (((MouseEvent) ie).getButton() - 1);
		} else {
			return button;
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getButtons() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getMovementX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getMovementY() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getOffsetX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getOffsetY() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getPageX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getPageY() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getModifierState(final String keyArg) {
		// TODO Auto-generated method stub
		return false;
	}
}
