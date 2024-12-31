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
import org.loboevolution.component.ToolBar;
import org.loboevolution.http.CookieManager;
import org.loboevolution.info.CookieInfo;
import org.loboevolution.menu.tools.AbstractToolsUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>CookieUI class.</p>
 */
public class CookieUI extends AbstractToolsUI {

    /** The Constant serialVersionUID. */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for CookieUI.</p>
     *
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    public CookieUI(final BrowserFrame frame) {
        final ToolBar toolbar = frame.getToolbar();
        add(createAndShowGUI(CookieManager.getCookieList(toolbar.getAddressBar().getText())));
    }

    private JScrollPane createAndShowGUI(final List<CookieInfo> cookieList) {
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
