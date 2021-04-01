package org.loboevolution.html.dom;

/**
 * Provides special properties and methods (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of table cells, either header or data cells, in an HTML document.
 */
public interface HTMLTableCellElement extends HTMLElement {
    
    /**
     * Sets or retrieves abbreviated text for the object.
     */
    
    String getAbbr();

    
    void setAbbr(String abbr);

    /**
     * Sets or retrieves how the object is aligned with adjacent text.
     */
    @Deprecated
    
    String getAlign();

    
    void setAlign(String align);

    /**
     * Sets or retrieves a comma-delimited list of conceptual categories associated with the object.
     */
    @Deprecated
    
    String getAxis();

    
    void setAxis(String axis);

    @Deprecated
    
    String getBgColor();

    
    void setBgColor(String bgColor);

    /**
     * Retrieves the position of the object in the cells collection of a row.
     */
    
    int getCellIndex();

    @Deprecated
    
    String getCh();

    
    void setCh(String ch);

    @Deprecated
    
    String getChOff();

    
    void setChOff(String chOff);

    /**
     * Sets or retrieves the number columns in the table that the object should span.
     */
    
    int getColSpan();

    
    void setColSpan(Object colSpan);

    /**
     * Sets or retrieves a list of header cells that provide information for the object.
     */
    
    String getHeaders();

    
    void setHeaders(String headers);

    /**
     * Sets or retrieves the height of the object.
     */
    @Deprecated
    
    String getHeight();

    
    void setHeight(String height);

    /**
     * Sets or retrieves whether the browser automatically performs wordwrap.
     */
    @Deprecated
    
    boolean isNoWrap();

    
    void setNoWrap(boolean noWrap);

    /**
     * Sets or retrieves how many rows in a table the cell should span.
     */
    
    int getRowSpan();

    
    void setRowSpan(Object rowSpan);

    /**
     * Sets or retrieves the group of cells in a table to which the object's information applies.
     */
    
    String getScope();

    
    void setScope(String scope);

    @Deprecated
    
    String getvAlign();

    
    void setvAlign(String vAlign);

    /**
     * Sets or retrieves the width of the object.
     */
    @Deprecated
    
    String getWidth();

    
    void setWidth(String width);

}
