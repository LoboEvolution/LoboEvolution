package org.loboevolution.html.node;

import org.loboevolution.html.dom.HTMLSlotElement;

/**
 * The textual content of Element or Attr. If an element has no markup within its content, it has a single child implementing Text that contains the element's text. However, if the element contains markup, it is parsed into information items and Text nodes that form its children.
 */
public interface Text extends CharacterData {
   
    HTMLSlotElement getAssignedSlot();

    /**
     * Returns the combined data of all direct Text node siblings.
     */
    String getWholeText();

    /**
     * Splits data at the given offset and returns the remainder as Text node.
     */
    Text splitText(int offset);
    
    boolean isElementContentWhitespace();
    
    Text replaceWholeText(String content);

}
