/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.menu.tools.developer.tool;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.store.TabStore;
import org.loboevolution.store.WebStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>StorageUI class.</p>
 */
public class StorageUI  extends AbstractToolsUI {

    /** The Constant serialVersionUID. */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for StorageUI.</p>
     *
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    public StorageUI(final BrowserFrame frame, final int session) {
        int index = TabStore.getTabs().size();
        if(index > 0) index = index -1;
        add(createAndShowGUI(index, session));
    }

    private Component createAndShowGUI(final int index, final int session) {
        final Map<String, String> map = WebStore.getMapStorage(index, session);
        final Object[] columnNames = { "Key", "Value"};
        final List<String[]> values = new ArrayList<>();
        final Set<Map.Entry<String, String>> set = map.entrySet();
        final List<Map.Entry<String, String>> list = new ArrayList<>(set);
        for(final Map.Entry<String, String> entry : list) {
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
