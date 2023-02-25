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
import org.loboevolution.component.ToolBar;
import org.loboevolution.http.CookieManager;
import org.loboevolution.info.CookieInfo;
import org.loboevolution.menu.tools.AbstractToolsUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>CookieUI class.</p>
 */
public class CookieUI extends AbstractToolsUI {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for CookieUI.</p>
     *
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    public CookieUI(BrowserFrame frame) {
        final ToolBar toolbar = frame.getToolbar();
        add(createAndShowGUI(CookieManager.getCookieList(toolbar.getAddressBar().getText())));
    }

    private JScrollPane createAndShowGUI(List<CookieInfo> cookieList) {
        final Object[] columnNames = { "Key", "Value", "Expires" };

        final List<String[]> values = new ArrayList<>();
        for (final CookieInfo info : cookieList) {
            values.add(new String[] { info.getName(), info.getValue(), info.getExpires() });
        }

        final DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
        final JTable jtable = new JTable(model);
        jtable.setPreferredSize(new Dimension(400, 380));
        jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
        jtable.setShowGrid(false);
        return new JScrollPane(jtable);
    }
}
