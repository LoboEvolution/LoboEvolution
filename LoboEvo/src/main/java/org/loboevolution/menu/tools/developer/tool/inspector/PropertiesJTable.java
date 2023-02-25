/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

import org.loboevolution.laf.ColorFactory;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Map;

/**
 * <p>PropertiesJTable class.</p>
 */
public class PropertiesJTable extends JTable {

    private static final long serialVersionUID = 1L;

    private final Font propLabelFont;

    private final Font defaultFont;

    /**
     * <p>Constructor for PropertiesJTable.</p>
     */
    PropertiesJTable() {
        this.setColumnSelectionAllowed(false);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        propLabelFont = new Font("Courier New", Font.BOLD, 12);
        defaultFont = new Font("Default", Font.PLAIN, 12);
    }

    /**
     * Gets the cellRenderer attribute of the PropertiesJTable object
     *
     * @param row a {@link java.lang.Integer} object
     * @param col a {@link java.lang.Integer} object
     * @return The cellRenderer value
     */
    public TableCellRenderer getCellRenderer(int row, int col) {
        JLabel label = (JLabel) super.getCellRenderer(row, col);
        label.setBackground(Color.white);
        label.setFont(defaultFont);
        if (col == 0) {
            label.setFont(propLabelFont);
        } else if (col == 2) {
            PropertiesTableModel pmodel = (PropertiesTableModel) this.getModel();
            Map.Entry me = (Map.Entry) pmodel.properties.entrySet().toArray()[row];
            Color color = ColorFactory.getInstance().getColor((String) me.getValue());
            if (color != null)
                label.setBackground(color);
        }

        return (TableCellRenderer) label;
    }
}