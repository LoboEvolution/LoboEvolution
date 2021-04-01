package org.loboevolution.html.dom;


/**
 * Provides special properties and methods (beyond the regular HTMLElement object interface it also has available to it by inheritance) for manipulating the layout and presentation of tables in an HTML document.
 */
public interface HTMLTableElement extends HTMLElement {
    
    /**
     * Sets or retrieves a value that indicates the table alignment.
     */
    @Deprecated
    
    String getAlign();

    
    void setAlign(String align);

    @Deprecated
    
    String getBgColor();

    
    void setBgColor(String bgColor);

    /**
     * Sets or retrieves the width of the border to draw around the object.
     */
    @Deprecated
    
    String getBorder();

    
    void setBorder(String border);

    /**
     * Retrieves the caption object of a table.
     */
    
    
    HTMLTableCaptionElement getCaption();

    
    void setCaption(HTMLTableCaptionElement caption);

    /**
     * Sets or retrieves the amount of space between the border of the cell and the content of the cell.
     */
    @Deprecated
    
    String getCellPadding();

    
    void setCellPadding(String cellPadding);

    /**
     * Sets or retrieves the amount of space between cells in a table.
     */
    @Deprecated
    
    String getCellSpacing();

    
    void setCellSpacing(String cellSpacing);

    /**
     * Sets or retrieves the way the border frame around the table is displayed.
     */
    @Deprecated
    
    String getFrame();

    
    void setFrame(String frame);

    /**
     * Sets or retrieves the number of horizontal rows contained in the object.
     */
    
    HTMLCollection getRows();

    /**
     * Sets or retrieves which dividing lines (inner borders) are displayed.
     */
    @Deprecated
    
    String getRules();

    
    void setRules(String rules);

    /**
     * Sets or retrieves a description and/or structure of the object.
     */
    @Deprecated
    
    String getSummary();

    
    void setSummary(String summary);

    /**
     * Retrieves a collection of all tBody objects in the table. Objects in this collection are in source order.
     */
    
    HTMLCollection gettBodies();

    /**
     * Retrieves the tFoot object of the table.
     */
    
    
    HTMLTableSectionElement gettFoot();

    
    void settFoot(HTMLTableSectionElement tFoot);

    /**
     * Retrieves the tHead object of the table.
     */
    
    
    HTMLTableSectionElement gettHead();

    
    void settHead(HTMLTableSectionElement tHead);

    /**
     * Sets or retrieves the width of the object.
     */
    @Deprecated
    
    String getWidth();

    
    void setWidth(String width);

    /**
     * Creates an empty caption element in the table.
     */
    HTMLElement createCaption();

    /**
     * Creates an empty tBody element in the table.
     */
    HTMLElement createTBody();

    /**
     * Creates an empty tFoot element in the table.
     */
    HTMLElement createTFoot();

    /**
     * Returns the tHead element object if successful, or null otherwise.
     */
    HTMLElement createTHead();

    /**
     * Deletes the caption element and its contents from the table.
     */
    void deleteCaption();

    /**
     * Removes the specified row (tr) from the element and from the rows collection.
     *
     * @param index Number that specifies the zero-based position in the rows collection of the row to remove.
     */
    void deleteRow(int index);

    /**
     * Deletes the tFoot element and its contents from the table.
     */
    void deleteTFoot();

    /**
     * Deletes the tHead element and its contents from the table.
     */
    void deleteTHead();
    
    /**
     * Deletes the tBody element and its contents from the table.
     */
    void deleteTBody();

    /**
     * Creates a new row (tr) in the table, and adds the row to the rows collection.
     *
     * @param index Number that specifies where to insert the row in the rows collection. The default value is -1, which appends the new row to the end of the rows collection.
     * @throws Exception 
     */
    HTMLTableRowElement insertRow(int index) throws Exception;

    HTMLTableRowElement insertRow();

}
