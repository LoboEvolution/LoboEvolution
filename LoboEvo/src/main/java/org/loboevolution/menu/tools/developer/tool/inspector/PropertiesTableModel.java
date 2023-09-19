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

package org.loboevolution.menu.tools.developer.tool.inspector;

import javax.swing.table.AbstractTableModel;
import java.util.Map;

/**
 * <p>PropertiesTableModel class.</p>
 */
public class PropertiesTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private String[] colNames = {"Property", "Text", "Value"};

    Map<String, String> properties;

    /**
     * Constructor for the PropertiesTableModel object
     *
     * @param cssProperties a {@link java.util.Map} object
     */
    PropertiesTableModel(Map<String, String> cssProperties) {
        this.properties = cssProperties;
    }

    /**
     * Gets the columnName attribute of the PropertiesTableModel object
     *
     * @param col a {@link java.lang.Integer} object
     * @return The columnName value
     */
    public String getColumnName(int col) {
        return colNames[col];
    }

    /**
     * Gets the columnCount attribute of the PropertiesTableModel object
     *
     * @return The columnCount value
     */
    public int getColumnCount() {
        return colNames.length;
    }

    /**
     * Gets the rowCount attribute of the PropertiesTableModel object
     *
     * @return The rowCount value
     */
    public int getRowCount() {
        return this.properties.size();
    }

    /**
     * Gets the valueAt attribute of the PropertiesTableModel object
     *
     * @param row a {@link java.lang.Integer} object
     * @param col a {@link java.lang.Integer} object
     * @return The valueAt value
     */
    public Object getValueAt(int row, int col) {
        Map.Entry me = (Map.Entry) this.properties.entrySet().toArray()[row];

        Object val = null;
        switch (col) {

            case 0:
                val = me.getKey();
                break;
            case 1:
                val = me.getValue();
                break;
            default:
                break;
        }
        return val;
    }

    /**
     * Gets the cellEditable attribute of the PropertiesTableModel object
     *
     * @param row a {@link java.lang.Integer} object
     * @param col a {@link java.lang.Integer} object
     * @return The cellEditable value
     */
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}