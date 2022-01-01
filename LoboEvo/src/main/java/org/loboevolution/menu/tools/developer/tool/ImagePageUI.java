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

import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;
import org.loboevolution.html.node.Document;
import org.loboevolution.http.HtmlContent;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.info.MetaInfo;
import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.net.HttpNetwork;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>ImagePageUI class.</p>
 */
public class ImagePageUI extends AbstractToolsUI {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(ImagePageUI.class.getName());

    /**
     * <p>Constructor for ImagePageUI.</p>
     *
     * @param frame a {@link BrowserFrame} object.
     */
    public ImagePageUI(BrowserFrame frame) {
        final ToolBar toolbar = frame.getToolbar();
        final Document doc = NavigationManager.getDocument(toolbar.getAddressBar().getText());
        final HtmlContent htmlcontent = new HtmlContent(doc);
        add(mediaContent(htmlcontent.getMediaList()));
    }

    private Component mediaContent(List<MetaInfo> mediaList) {
        try {
            final Object[] columnNames = { "" };
            final List<String[]> values = new ArrayList<>();
            for (final MetaInfo info : mediaList) {
                if (Strings.isNotBlank(info.getName())) {
                    values.add(new String[] { info.getName() });
                }
            }
            final DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
            final JTable jtable = new JTable(model);
            jtable.setPreferredSize(new Dimension(400, 380));
            jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
            jtable.setTableHeader(null);
            jtable.setShowGrid(false);
            jtable.setColumnSelectionAllowed(true);
            jtable.setCellSelectionEnabled(true);

            final JPanel jPanelImg = new JPanel();

            final ListSelectionModel cellSelectionModel = jtable.getSelectionModel();
            cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            cellSelectionModel.addListSelectionListener(e -> {
                jPanelImg.removeAll();
                final int[] selectedRow = jtable.getSelectedRows();
                final int[] selectedColumns = jtable.getSelectedColumns();

                for (final int element : selectedRow) {
                    for (final int selectedColumn : selectedColumns) {
                        final String href = (String) jtable.getValueAt(element, selectedColumn);
                        jPanelImg.add(new JLabel(new ImageIcon(HttpNetwork.getImage(href, null))));
                        jPanelImg.repaint();
                    }
                }
            });

            final JPanel tablePanel = new JPanel();
            tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
            tablePanel.add(jtable);
            tablePanel.add(jPanelImg);
            return new JScrollPane(tablePanel);
        } catch (final Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
}