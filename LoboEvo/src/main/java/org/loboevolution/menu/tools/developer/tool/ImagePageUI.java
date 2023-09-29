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

package org.loboevolution.menu.tools.developer.tool;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.http.HtmlContent;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.info.MetaInfo;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.net.HttpNetwork;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>ImagePageUI class.</p>
 */
@Slf4j
public class ImagePageUI extends AbstractToolsUI {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for ImagePageUI.</p>
     *
     * @param frame a {@link BrowserFrame} object.
     */
    public ImagePageUI(final BrowserFrame frame) {
        final ToolBar toolbar = frame.getToolbar();
        final Document doc = NavigationManager.getDocument(toolbar.getAddressBar().getText());
        final HtmlContent htmlcontent = new HtmlContent(doc);
        add(mediaContent(htmlcontent.getMediaList()));
    }

    private Component mediaContent(final List<MetaInfo> mediaList) {
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
                        final HTMLImageElementImpl img = new HTMLImageElementImpl();
                        img.setSrc(href);
                        jPanelImg.add(new JLabel(new ImageIcon(HttpNetwork.getImage(img, new TimingInfo(), false))));
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
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
