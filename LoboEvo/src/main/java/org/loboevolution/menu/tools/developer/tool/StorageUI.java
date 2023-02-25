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

package org.loboevolution.menu.tools.developer.tool;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.store.TabStore;
import org.loboevolution.store.WebStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>StorageUI class.</p>
 */
public class StorageUI  extends AbstractToolsUI {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for StorageUI.</p>
     *
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    public StorageUI(BrowserFrame frame, int session) {
        int index = TabStore.getTabs().size();
        if(index > 0) index = index -1;
        add(createAndShowGUI(index, session));
    }

    private Component createAndShowGUI(int index, int session) {
        final Map<String, String> map = WebStore.getMapStorage(index, session);
        final Object[] columnNames = { "Key", "Value"};
        final List<String[]> values = new ArrayList<>();
        final Set<Map.Entry<String, String>> set = map.entrySet();
        final List<Map.Entry<String, String>> list = new ArrayList<>(set);
        for(Map.Entry<String, String> entry : list) {
            values.add(new String[] { entry.getKey(), entry.getValue() });
        }

        final DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
        final JTable jtable = new JTable(model);
        jtable.setPreferredSize(new Dimension(400, 380));
        jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
        jtable.setShowGrid(false);
        return new JScrollPane(jtable);

    }
}
