package org.loboevolution.html.dom;



/**
 * The interface Html all collection.
 */
public interface HTMLAllCollection  {

    /**
     * Returns the number of elements in the collection.
     */
    
    int getLength();

    /**
     * Returns the item with index index from the collection (determined by tree order).
     *
     * @param nameOrIndex the name or index
     *
     * @return the unknown
     */

    HTMLCollection item(String nameOrIndex);

    /**
     * Item unknown.
     *
     * @return the unknown
     */

    HTMLCollection item();

    /**
     * Returns the item with ID or name name from the collection.
     * <p>
     * If there are multiple matching items, then an HTMLCollection object containing all those elements is returned.
     *
     * @param name the name
     *
     * @return the unknown
     */

    HTMLCollection namedItem(String name);

}
