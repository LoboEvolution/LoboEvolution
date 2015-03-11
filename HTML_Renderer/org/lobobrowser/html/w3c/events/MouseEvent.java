package org.lobobrowser.html.w3c.events;

import org.lobobrowser.html.w3c.HTMLElement;
import org.w3c.dom.views.AbstractView;


/**
 * The Interface MouseEvent.
 */
public interface MouseEvent extends UIEvent {
	// MouseEvent
	/**
	 * Gets the screen x.
	 *
	 * @return the screen x
	 */
	public int getScreenX();

	/**
	 * Gets the screen y.
	 *
	 * @return the screen y
	 */
	public int getScreenY();

	/**
	 * Gets the client x.
	 *
	 * @return the client x
	 */
	public int getClientX();

	/**
	 * Gets the client y.
	 *
	 * @return the client y
	 */
	public int getClientY();

	/**
	 * Gets the ctrl key.
	 *
	 * @return the ctrl key
	 */
	public boolean getCtrlKey();

	/**
	 * Gets the shift key.
	 *
	 * @return the shift key
	 */
	public boolean getShiftKey();

	/**
	 * Gets the alt key.
	 *
	 * @return the alt key
	 */
	public boolean getAltKey();

	/**
	 * Gets the meta key.
	 *
	 * @return the meta key
	 */
	public boolean getMetaKey();

	/**
	 * Gets the related target.
	 *
	 * @return the related target
	 */
	public HTMLElement getRelatedTarget();

	/**
	 * Inits the mouse event.
	 *
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param viewArg the view arg
	 * @param detailArg the detail arg
	 * @param screenXArg the screen x arg
	 * @param screenYArg the screen y arg
	 * @param clientXArg the client x arg
	 * @param clientYArg the client y arg
	 * @param ctrlKeyArg the ctrl key arg
	 * @param altKeyArg the alt key arg
	 * @param shiftKeyArg the shift key arg
	 * @param metaKeyArg the meta key arg
	 * @param buttonArg the button arg
	 * @param relatedTargetArg the related target arg
	 */
	public void initMouseEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, int detailArg,
			int screenXArg, int screenYArg, int clientXArg, int clientYArg,
			boolean ctrlKeyArg, boolean altKeyArg, boolean shiftKeyArg,
			boolean metaKeyArg, int buttonArg, EventTarget relatedTargetArg);

	/**
	 * Inits the mouse event ns.
	 *
	 * @param namespaceURIArg the namespace uri arg
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param viewArg the view arg
	 * @param detailArg the detail arg
	 * @param screenXArg the screen x arg
	 * @param screenYArg the screen y arg
	 * @param clientXArg the client x arg
	 * @param clientYArg the client y arg
	 * @param ctrlKeyArg the ctrl key arg
	 * @param altKeyArg the alt key arg
	 * @param shiftKeyArg the shift key arg
	 * @param metaKeyArg the meta key arg
	 * @param buttonArg the button arg
	 * @param relatedTargetArg the related target arg
	 */
	public void initMouseEventNS(String namespaceURIArg, String typeArg,
			boolean canBubbleArg, boolean cancelableArg, AbstractView viewArg,
			int detailArg, int screenXArg, int screenYArg, int clientXArg,
			int clientYArg, boolean ctrlKeyArg, boolean altKeyArg,
			boolean shiftKeyArg, boolean metaKeyArg, int buttonArg,
			EventTarget relatedTargetArg);

	/**
	 * Gets the modifier state.
	 *
	 * @param keyArg the key arg
	 * @return the modifier state
	 */
	public boolean getModifierState(String keyArg);

	// MouseEvent-43
	/**
	 * Gets the page x.
	 *
	 * @return the page x
	 */
	public int getPageX();

	/**
	 * Gets the page y.
	 *
	 * @return the page y
	 */
	public int getPageY();

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX();

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY();

	/**
	 * Gets the offset x.
	 *
	 * @return the offset x
	 */
	public int getOffsetX();

	/**
	 * Gets the offset y.
	 *
	 * @return the offset y
	 */
	public int getOffsetY();
}
