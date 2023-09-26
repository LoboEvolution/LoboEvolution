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
    public TableCellRenderer getCellRenderer(final int row, final int col) {
        final JLabel label = (JLabel) super.getCellRenderer(row, col);
        label.setBackground(Color.white);
        label.setFont(defaultFont);
        if (col == 0) {
            label.setFont(propLabelFont);
        } else if (col == 2) {
            final PropertiesTableModel pmodel = (PropertiesTableModel) this.getModel();
            final Map.Entry me = (Map.Entry) pmodel.properties.entrySet().toArray()[row];
            final Color color = ColorFactory.getInstance().getColor((String) me.getValue());
            if (color != null)
                label.setBackground(color);
        }

        return (TableCellRenderer) label;
    }
}