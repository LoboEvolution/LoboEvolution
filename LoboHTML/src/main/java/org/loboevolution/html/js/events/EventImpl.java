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

import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventTarget;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.type.EventPhase;

import java.util.List;

/**
 * <p>EventImpl class.</p>
 */
public class EventImpl extends AbstractScriptableDelegate implements Event {
	
	private String eventType;
	
	private boolean cancelable;
	
	private boolean canBubble;
	
	/**
	 * <p>Constructor for EventImpl.</p>
	 *
	 * @param eventTypeArg a {@link java.lang.String} object.
	 * @param canBubbleArg a boolean.
	 * @param cancelableArg a boolean.
	 */
	public EventImpl(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg) {
		eventType = eventTypeArg;
		canBubble = canBubbleArg;
		cancelable = cancelableArg;
	}
	
	/**
	 * <p>Constructor for EventImpl.</p>
	 */
	public EventImpl() {
		eventType = null;
		canBubble = false;
		cancelable = false;
	}
	
	/** {@inheritDoc} */
	@Override
	public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg) {
		eventType = eventTypeArg;
		canBubble = canBubbleArg;
		cancelable = cancelableArg;
	}

	/** {@inheritDoc} */
	@Override
	public String getType() {
		return this.eventType;
	}

	/** {@inheritDoc} */
	@Override
	public EventTarget getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public EventTarget getCurrentTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isBubbles() {
		return this.canBubble;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isCancelable() {
		return this.cancelable;
	}

	/** {@inheritDoc} */
	@Override
	public double getTimeStamp() {
		return System.currentTimeMillis();
	}

	/** {@inheritDoc} */
	@Override
	public void stopPropagation() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void preventDefault() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setCancelBubble(boolean cancelBubble) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean isComposed() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDefaultPrevented() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public EventPhase getEventPhase() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isIsTrusted() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isReturnValue() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setReturnValue(boolean returnValue) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public EventTarget getSrcElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public List<EventTarget> composedPath() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void initEvent(String type, boolean bubbles) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void initEvent(String type) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void stopImmediatePropagation() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean isCancelBubble() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return "[object Event]";
	}
}
