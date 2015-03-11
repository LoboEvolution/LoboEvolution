package org.lobobrowser.html.w3c;


/**
 * The Interface HTMLMarqueeElement.
 */
public interface HTMLMarqueeElement extends HTMLElement {
	// HTMLMarqueeElement
	/**
	 * Gets the behavior.
	 *
	 * @return the behavior
	 */
	public String getBehavior();

	/**
	 * Sets the behavior.
	 *
	 * @param behavior the new behavior
	 */
	public void setBehavior(String behavior);

	/**
	 * Gets the bg color.
	 *
	 * @return the bg color
	 */
	public String getBgColor();

	/**
	 * Sets the bg color.
	 *
	 * @param bgColor the new bg color
	 */
	public void setBgColor(String bgColor);

	/**
	 * Gets the direction.
	 *
	 * @return the direction
	 */
	public String getDirection();

	/**
	 * Sets the direction.
	 *
	 * @param direction the new direction
	 */
	public void setDirection(String direction);

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public String getHeight();

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(String height);

	/**
	 * Gets the hspace.
	 *
	 * @return the hspace
	 */
	public int getHspace();

	/**
	 * Sets the hspace.
	 *
	 * @param hspace the new hspace
	 */
	public void setHspace(int hspace);

	/**
	 * Gets the loop.
	 *
	 * @return the loop
	 */
	public int getLoop();

	/**
	 * Sets the loop.
	 *
	 * @param loop the new loop
	 */
	public void setLoop(int loop);

	/**
	 * Gets the scroll amount.
	 *
	 * @return the scroll amount
	 */
	public int getScrollAmount();

	/**
	 * Sets the scroll amount.
	 *
	 * @param scrollAmount the new scroll amount
	 */
	public void setScrollAmount(int scrollAmount);

	/**
	 * Gets the scroll delay.
	 *
	 * @return the scroll delay
	 */
	public int getScrollDelay();

	/**
	 * Sets the scroll delay.
	 *
	 * @param scrollDelay the new scroll delay
	 */
	public void setScrollDelay(int scrollDelay);

	/**
	 * Gets the true speed.
	 *
	 * @return the true speed
	 */
	public String getTrueSpeed();

	/**
	 * Sets the true speed.
	 *
	 * @param trueSpeed the new true speed
	 */
	public void setTrueSpeed(String trueSpeed);

	/**
	 * Gets the vspace.
	 *
	 * @return the vspace
	 */
	public int getVspace();

	/**
	 * Sets the vspace.
	 *
	 * @param vspace the new vspace
	 */
	public void setVspace(int vspace);

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public String getWidth();

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(String width);

	/**
	 * Start.
	 */
	public void start();

	/**
	 * Stop.
	 */
	public void stop();
}
