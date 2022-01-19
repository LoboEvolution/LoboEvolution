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

package org.loboevolution.menu.tools.developer.tool;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.menu.tools.AbstractToolsUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimingPageUI extends AbstractToolsUI {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Constant logger.
     */
    private static final Logger logger = Logger.getLogger(TimingPageUI.class.getName());

    /**
     * <p>Constructor for TimingPageUI.</p>
     *
     * @param frame a {@link BrowserFrame} object.
     */
    public TimingPageUI(BrowserFrame frame) {
        add(infoContent(frame));
    }

    private JScrollPane infoContent(BrowserFrame frame) {
        try {
            final Object[] columnNames = {"Name", "Type", "State", "Time(ms)"};
            final List<String[]> values = new ArrayList<>();

            for (TimingInfo info : frame.getPanel().getTimingList) {
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
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
}
