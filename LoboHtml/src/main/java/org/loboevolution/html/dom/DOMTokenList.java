package org.loboevolution.html.dom;

/**
 * <p>DOMTokenList interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface DOMTokenList {

    /**
     * Gets the length.
     *
     * @return the length
     */
    int getLength();

    /**
     * Item.
     *
     * @param index
     *            the index
     * @return the string
     */
    String item(int index);

    /**
     * Contains.
     *
     * @param token
     *            the token
     * @return true, if successful
     */
    boolean contains(String token);

    /**
     * Adds the.
     *
     * @param token
     *            the token
     */
    void add(String token);

    /**
     * Removes the.
     *
     * @param token
     *            the token
     */
    void remove(String token);

    /**
     * Toggle.
     *
     * @param token
     *            the token
     * @return true, if successful
     */
    boolean toggle(String token);

    /**
     * Toggle.
     *
     * @param token
     *            the token
     * @param force
     *            the force
     * @return true, if successful
     */
    boolean toggle(String token, boolean force);

    /**
     * To string.
     *
     * @return the string
     */
    String toString();
}
