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