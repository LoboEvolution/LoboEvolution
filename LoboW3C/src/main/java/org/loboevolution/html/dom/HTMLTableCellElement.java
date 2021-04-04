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
 * Provides special properties and methods (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of table cells, either header or data cells, in an HTML document.
 *
 *
 *
 */
public interface HTMLTableCellElement extends HTMLElement {
    
    /**
     * Sets or retrieves abbreviated text for the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getAbbr();

    
    /**
     * <p>setAbbr.</p>
     *
     * @param abbr a {@link java.lang.String} object.
     */
    void setAbbr(String abbr);

    /**
     * Sets or retrieves how the object is aligned with adjacent text.
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
     * Sets or retrieves a comma-delimited list of conceptual categories associated with the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getAxis();

    
    /**
     * <p>setAxis.</p>
     *
     * @param axis a {@link java.lang.String} object.
     */
    void setAxis(String axis);

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
     * Retrieves the position of the object in the cells collection of a row.
     *
     * @return a int.
     */
    int getCellIndex();

    /**
     * <p>getCh.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getCh();

    
    /**
     * <p>setCh.</p>
     *
     * @param ch a {@link java.lang.String} object.
     */
    void setCh(String ch);

    /**
     * <p>getChOff.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getChOff();

    
    /**
     * <p>setChOff.</p>
     *
     * @param chOff a {@link java.lang.String} object.
     */
    void setChOff(String chOff);

    /**
     * Sets or retrieves the number columns in the table that the object should span.
     *
     * @return a int.
     */
    int getColSpan();

    
    /**
     * <p>setColSpan.</p>
     *
     * @param colSpan a {@link java.lang.Object} object.
     */
    void setColSpan(Object colSpan);

    /**
     * Sets or retrieves a list of header cells that provide information for the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getHeaders();

    
    /**
     * <p>setHeaders.</p>
     *
     * @param headers a {@link java.lang.String} object.
     */
    void setHeaders(String headers);

    /**
     * Sets or retrieves the height of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getHeight();

    
    /**
     * <p>setHeight.</p>
     *
     * @param height a {@link java.lang.String} object.
     */
    void setHeight(String height);

    /**
     * Sets or retrieves whether the browser automatically performs wordwrap.
     *
     * @return a boolean.
     */
    @Deprecated
    boolean isNoWrap();

    
    /**
     * <p>setNoWrap.</p>
     *
     * @param noWrap a boolean.
     */
    void setNoWrap(boolean noWrap);

    /**
     * Sets or retrieves how many rows in a table the cell should span.
     *
     * @return a int.
     */
    int getRowSpan();

    
    /**
     * <p>setRowSpan.</p>
     *
     * @param rowSpan a {@link java.lang.Object} object.
     */
    void setRowSpan(Object rowSpan);

    /**
     * Sets or retrieves the group of cells in a table to which the object's information applies.
     *
     * @return a {@link java.lang.String} object.
     */
    String getScope();

    
    /**
     * <p>setScope.</p>
     *
     * @param scope a {@link java.lang.String} object.
     */
    void setScope(String scope);

    /**
     * <p>getvAlign.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getvAlign();

    
    /**
     * <p>setvAlign.</p>
     *
     * @param vAlign a {@link java.lang.String} object.
     */
    void setvAlign(String vAlign);

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

}
