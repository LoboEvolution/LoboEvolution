/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom;




/**
 * Provides special properties and methods (beyond the HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of rows in an HTML table.
 *
 *
 *
 */
public interface HTMLTableRowElement extends HTMLElement {


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
     * Retrieves a collection of all cells in the table row.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getCells();

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
     * Retrieves the position of the object in the rows collection for the table.
     *
     * @return a int.
     */
    int getRowIndex();

    /**
     * Retrieves the position of the object in the collection.
     *
     * @return a int.
     */
    int getSectionRowIndex();

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
     * Removes the specified cell from the table row, as well as from the cells collection.
     *
     * @param index Number that specifies the zero-based position of the cell to remove from the table row. If no value is provided, the last cell in the cells collection is deleted.
     */
    void deleteCell(int index);

    /**
     * Creates a new cell in the table row, and adds the cell to the cells collection.
     *
     * @param index Number that specifies where to insert the cell in the tr. The default value is -1, which appends the new cell to the end of the cells collection.
     * @return a {@link org.loboevolution.html.dom.HTMLTableCellElement} object.
     * @throws java.lang.Exception if any.
     */
    HTMLTableCellElement insertCell(Object index) throws Exception;

    /**
     * <p>insertCell.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLTableCellElement} object.
     */
    HTMLTableCellElement insertCell();

}
