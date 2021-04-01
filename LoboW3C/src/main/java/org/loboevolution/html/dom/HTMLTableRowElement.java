package org.loboevolution.html.dom;




/**
 * Provides special properties and methods (beyond the HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of rows in an HTML table.
 */
public interface HTMLTableRowElement extends HTMLElement {


    /**
     * Sets or retrieves how the object is aligned with adjacent text.
     */
    @Deprecated
    
    String getAlign();

    
    void setAlign(String align);

    @Deprecated
    
    String getBgColor();

    
    void setBgColor(String bgColor);

    /**
     * Retrieves a collection of all cells in the table row.
     */
    
    HTMLCollection getCells();

    @Deprecated
    
    String getCh();

    
    void setCh(String ch);

    @Deprecated
    
    String getChOff();

    
    void setChOff(String chOff);

    /**
     * Retrieves the position of the object in the rows collection for the table.
     */
    
    int getRowIndex();

    /**
     * Retrieves the position of the object in the collection.
     */
    
    int getSectionRowIndex();

    @Deprecated
    
    String getvAlign();

    
    void setvAlign(String vAlign);

    /**
     * Removes the specified cell from the table row, as well as from the cells collection.
     *
     * @param index Number that specifies the zero-based position of the cell to remove from the table row. If no value is provided, the last cell in the cells collection is deleted.
     */
    void deleteCell(int index);

    /**
     * Creates a new cell in the table row, and adds the cell to the cells collection.
     *
     * @param index Number that specifies where to insert the cell in the tr. The default value is -1, which appends the new cell to the end of the cells collection.
     */
    HTMLTableCellElement insertCell(Object index) throws Exception;

    HTMLTableCellElement insertCell();

}
