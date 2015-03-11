package org.lobobrowser.html.w3c;


/**
 * The Interface HTMLCanvasElement.
 */
public interface HTMLCanvasElement extends HTMLElement {
	
	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth();

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width);

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight();

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(int height);

	/**
	 * To data url.
	 *
	 * @return the string
	 */
	public String toDataURL();

	/**
	 * To data url.
	 *
	 * @param type the type
	 * @param args the args
	 * @return the string
	 */
	public String toDataURL(String type, Object... args);

	/**
	 * Gets the context.
	 *
	 * @param contextId the context id
	 * @return the context
	 */
	public Object getContext(String contextId);
}
