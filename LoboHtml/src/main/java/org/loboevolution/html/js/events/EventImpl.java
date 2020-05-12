/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.html.js.events;

import org.loboevolution.js.AbstractScriptableDelegate;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;

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
	public short getEventPhase() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getBubbles() {
		return this.canBubble;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getCancelable() {
		return this.cancelable;
	}

	/** {@inheritDoc} */
	@Override
	public long getTimeStamp() {
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
	public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg) {
		eventType = eventTypeArg;
		canBubble = canBubbleArg;
		cancelable = cancelableArg;
	}
}
