package org.lobobrowser.html.w3c.events;


/**
 * The Interface Event.
 */
public interface Event {
	// Event
	/** The Constant CAPTURING_PHASE. */
	public static final short CAPTURING_PHASE = 1;
	
	/** The Constant AT_TARGET. */
	public static final short AT_TARGET = 2;
	
	/** The Constant BUBBLING_PHASE. */
	public static final short BUBBLING_PHASE = 3;

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public EventTarget getTarget();

	/**
	 * Gets the current target.
	 *
	 * @return the current target
	 */
	public EventTarget getCurrentTarget();

	/**
	 * Gets the event phase.
	 *
	 * @return the event phase
	 */
	public short getEventPhase();

	/**
	 * Gets the bubbles.
	 *
	 * @return the bubbles
	 */
	public boolean getBubbles();

	/**
	 * Gets the cancelable.
	 *
	 * @return the cancelable
	 */
	public boolean getCancelable();

	/**
	 * Gets the time stamp.
	 *
	 * @return the time stamp
	 */
	public long getTimeStamp();

	/**
	 * Stop propagation.
	 */
	public void stopPropagation();

	/**
	 * Prevent default.
	 */
	public void preventDefault();

	/**
	 * Inits the event.
	 *
	 * @param eventTypeArg the event type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 */
	public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg);
	
	/**
	 * Inits the event ns.
	 *
	 * @param namespaceURIArg the namespace uri arg
	 * @param eventTypeArg the event type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 */
	public void initEventNS(String namespaceURIArg, String eventTypeArg,boolean canBubbleArg, boolean cancelableArg);

	/**
	 * Stop immediate propagation.
	 */
	public void stopImmediatePropagation();

	/**
	 * Gets the default prevented.
	 *
	 * @return the default prevented
	 */
	public boolean getDefaultPrevented();

	/**
	 * Gets the trusted.
	 *
	 * @return the trusted
	 */
	public boolean getTrusted();
}
