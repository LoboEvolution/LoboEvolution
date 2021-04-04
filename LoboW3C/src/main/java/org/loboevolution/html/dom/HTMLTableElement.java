/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom;


/**
 * Provides special properties and methods (beyond the regular HTMLElement object interface it also has available to it by inheritance) for manipulating the layout and presentation of tables in an HTML document.
 *
 *
 *
 */
public interface HTMLTableElement extends HTMLElement {
    
    /**
     * Sets or retrieves a value that indicates the table alignment.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getAlign();

    
    /**
     * <p>setAlign.</p>
     *
     * @param align a {@link java.lang.String} object.
     */
    void setAlign(String align);

    /**
     * <p>getBgColor.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getBgColor();

    
    /**
     * <p>setBgColor.</p>
     *
     * @param bgColor a {@link java.lang.String} object.
     */
    void setBgColor(String bgColor);

    /**
     * Sets or retrieves the width of the border to draw around the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getBorder();

    
    /**
     * <p>setBorder.</p>
     *
     * @param border a {@link java.lang.String} object.
     */
    void setBorder(String border);

    /**
     * Retrieves the caption object of a table.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLTableCaptionElement} object.
     */
    HTMLTableCaptionElement getCaption();

    
    /**
     * <p>setCaption.</p>
     *
     * @param caption a {@link org.loboevolution.html.dom.HTMLTableCaptionElement} object.
     */
    void setCaption(HTMLTableCaptionElement caption);

    /**
     * Sets or retrieves the amount of space between the border of the cell and the content of the cell.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getCellPadding();

    
    /**
     * <p>setCellPadding.</p>
     *
     * @param cellPadding a {@link java.lang.String} object.
     */
    void setCellPadding(String cellPadding);

    /**
     * Sets or retrieves the amount of space between cells in a table.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getCellSpacing();

    
    /**
     * <p>setCellSpacing.</p>
     *
     * @param cellSpacing a {@link java.lang.String} object.
     */
    void setCellSpacing(String cellSpacing);

    /**
     * Sets or retrieves the way the border frame around the table is displayed.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getFrame();

    
    /**
     * <p>setFrame.</p>
     *
     * @param frame a {@link java.lang.String} object.
     */
    void setFrame(String frame);

    /**
     * Sets or retrieves the number of horizontal rows contained in the object.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getRows();

    /**
     * Sets or retrieves which dividing lines (inner borders) are displayed.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getRules();

    
    /**
     * <p>setRules.</p>
     *
     * @param rules a {@link java.lang.String} object.
     */
    void setRules(String rules);

    /**
     * Sets or retrieves a description and/or structure of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getSummary();

    
    /**
     * <p>setSummary.</p>
     *
     * @param summary a {@link java.lang.String} object.
     */
    void setSummary(String summary);

    /**
     * Retrieves a collection of all tBody objects in the table. Objects in this collection are in source order.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection gettBodies();

    /**
     * Retrieves the tFoot object of the table.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLTableSectionElement} object.
     */
    HTMLTableSectionElement gettFoot();

    
    /**
     * <p>settFoot.</p>
     *
     * @param tFoot a {@link org.loboevolution.html.dom.HTMLTableSectionElement} object.
     */
    void settFoot(HTMLTableSectionElement tFoot);

    /**
     * Retrieves the tHead object of the table.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLTableSectionElement} object.
     */
    HTMLTableSectionElement gettHead();

    
    /**
     * <p>settHead.</p>
     *
     * @param tHead a {@link org.loboevolution.html.dom.HTMLTableSectionElement} object.
     */
    void settHead(HTMLTableSectionElement tHead);

    /**
     * Sets or retrieves the width of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getWidth();

    
    /**
     * <p>setWidth.</p>
     *
     * @param width a {@link java.lang.String} object.
     */
    void setWidth(String width);

    /**
     * Creates an empty caption element in the table.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
     */
    HTMLElement createCaption();

    /**
     * Creates an empty tBody element in the table.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
     */
    HTMLElement createTBody();

    /**
     * Creates an empty tFoot element in the table.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
     */
    HTMLElement createTFoot();

    /**
     * Returns the tHead element object if successful, or null otherwise.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
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
     * @param index Number that specifies where to insert the row in the rows collection.
     *              The default value is -1, which appends the new row to the end of the rows collection.
     * @return a {@link org.loboevolution.html.dom.HTMLTableRowElement} object.
     * @throws java.lang.Exception if any.
     */
    HTMLTableRowElement insertRow(int index) throws Exception;

    /**
     * <p>insertRow.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLTableRowElement} object.
     */
    HTMLTableRowElement insertRow();

}
