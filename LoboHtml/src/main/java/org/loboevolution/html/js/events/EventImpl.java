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
package org.loboevolution.html.js.events;

import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.jsenum.EventPhase;

import java.util.List;

import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventTarget;

/**
 * <p>EventImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
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

	@Override
	public void setCancelBubble(boolean cancelBubble) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isComposed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDefaultPrevented() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EventPhase getEventPhase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isIsTrusted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReturnValue() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setReturnValue(boolean returnValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EventTarget getSrcElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventTarget> composedPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initEvent(String type, boolean bubbles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initEvent(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopImmediatePropagation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCancelBubble() {
		// TODO Auto-generated method stub
		return false;
	}
}
