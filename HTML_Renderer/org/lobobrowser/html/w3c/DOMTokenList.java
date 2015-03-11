package org.lobobrowser.html.w3c;


/**
 * The Interface DOMTokenList.
 */
public interface DOMTokenList {
	// DOMTokenList
	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength();

	/**
	 * Item.
	 *
	 * @param index the index
	 * @return the string
	 */
	public String item(int index);

	/**
	 * Contains.
	 *
	 * @param token the token
	 * @return true, if successful
	 */
	public boolean contains(String token);

	/**
	 * Adds the.
	 *
	 * @param token the token
	 */
	public void add(String token);

	/**
	 * Removes the.
	 *
	 * @param token the token
	 */
	public void remove(String token);

	/**
	 * Toggle.
	 *
	 * @param token the token
	 * @return true, if successful
	 */
	public boolean toggle(String token);
	
	/**
	 * Toggle.
	 *
	 * @param token the token
	 * @param force the force
	 * @return true, if successful
	 */
	public boolean toggle(String token,boolean force);

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString();
}
