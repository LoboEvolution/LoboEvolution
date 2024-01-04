/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.menu.tools.AbstractToolsUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TimingPageUI class.</p>
 */
@Slf4j
public class TimingPageUI extends AbstractToolsUI {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for TimingPageUI.</p>
     *
     * @param frame a {@link BrowserFrame} object.
     */
    public TimingPageUI(final BrowserFrame frame) {
        add(infoContent(frame));
    }

    private JScrollPane infoContent(final BrowserFrame frame) {
        try {
            final Object[] columnNames = {"Name", "Type", "State", "Time(ms)"};
            final List<String[]> values = new ArrayList<>();

            for (final TimingInfo info : frame.getPanel().getTimingList) {
                values.add(new String[]{info.getName(),
                        info.getType(),
                        String.valueOf(info.getHttpResponse()),
                        String.valueOf(info.getTimeElapsed())});
            }
            final DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][]{}), columnNames);
            final JTable jtable = new JTable(model);
            jtable.setPreferredSize(new Dimension(400, 380));
            jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
            jtable.setShowGrid(false);
            return new JScrollPane(jtable);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
