package org.loboevolution.html.dom;



/**
 * Provides special properties and methods (beyond the HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of sections, that is headers, footers and bodies, in an HTML table.
 */
public interface HTMLTableSectionElement extends HTMLElement {
    
	/**
     * Sets or retrieves a value that indicates the table alignment.
     */
    @Deprecated
    
    String getAlign();

    
    void setAlign(String align);

    @Deprecated
    
    String getCh();

    
    void setCh(String ch);

    @Deprecated
    
    String getChOff();

    
    void setChOff(String chOff);

    /**
     * Sets or retrieves the number of horizontal rows contained in the object.
     */
    
    HTMLCollection getRows();

    @Deprecated
    
    String getvAlign();

    
    void setvAlign(String vAlign);

    /**
     * Removes the specified row (tr) from the element and from the rows collection.
     *
     * @param index Number that specifies the zero-based position in the rows collection of the row to remove.
     */
    void deleteRow(int index);

    /**
     * Creates a new row (tr) in the table, and adds the row to the rows collection.
     *
     * @param index Number that specifies where to insert the row in the rows collection. The default value is -1, which appends the new row to the end of the rows collection.
     */
    HTMLTableRowElement insertRow(int index);

    HTMLTableRowElement insertRow();

}
