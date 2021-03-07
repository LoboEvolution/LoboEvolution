package org.loboevolution.html.dom;


/**
 * HTMLOptionsCollection is an interface representing a collection of HTML option elements (in document order) and offers methods and properties for traversing the list as well as optionally altering its items. This type is returned solely by the "options" property of select.
 */
public interface HTMLOptionsCollection extends HTMLCollection {

    /**
     * Returns the number of elements in the collection.
     * <p>
     * When set to a smaller number, truncates the number of option elements in the corresponding container.
     * <p>
     * When set to a greater number, adds new blank option elements to that container.
     */
    
    int getLength();

    
    void setLength(int length);

    /**
     * Returns the index of the first selected item, if any, or −1 if there is no selected item.
     * <p>
     * Can be set, to change the selection.
     */
    
    int getSelectedIndex();

    
    void setSelectedIndex(int selectedIndex);

    /**
     * Inserts element before the node given by before.
     * <p>
     * The before argument can be a number, in which case element is inserted before the item with that number, or an element from the collection, in which case element is inserted before that element.
     * <p>
     * If before is omitted, null, or a number out of range, then element will be added at the end of the list.
     * <p>
     * This method will throw a "HierarchyRequestError" DOMException if element is an ancestor of the element into which it is to be inserted.
     */
    void add(HTMLOptGroupElement element, HTMLElement before);

    void add(HTMLOptGroupElement element, int before);

    void add(HTMLOptGroupElement element);

    void add(HTMLOptionElement element, HTMLElement before);

    void add(HTMLOptionElement element, int before);

    void add(HTMLOptionElement element);

    /**
     * Removes the item with index index from the collection.
     */
    void remove(int index);

}
