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

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;
import org.loboevolution.html.node.Document;
import org.loboevolution.http.HtmlContent;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.info.MetaInfo;
import org.loboevolution.menu.tools.AbstractToolsUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>InfoPageUI class.</p>
 */
@Slf4j
public class InfoPageUI extends AbstractToolsUI {

    /** The Constant serialVersionUID. */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for InfoPageUI.</p>
     *
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    public InfoPageUI(final BrowserFrame frame){
        final ToolBar toolbar = frame.getToolbar();
        final Document doc = NavigationManager.getDocument(toolbar.getAddressBar().getText());
        final HtmlContent htmlcontent = new HtmlContent(doc);
        add(infoContent(htmlcontent.getMetaList()));
    }

    private JScrollPane infoContent(final List<MetaInfo> infoList) {
        try {
            final Object[] columnNames = { "Key", "Value" };
            final List<String[]> values = new ArrayList<>();
            for (final MetaInfo info : infoList) {
                if (Strings.isNotBlank(info.getName())) {
                    values.add(new String[] { info.getName(), info.getContent() });
                } else if (Strings.isNotBlank(info.getItemprop())) {
                    values.add(new String[] { info.getItemprop(), info.getContent() });
                } else if (Strings.isNotBlank(info.getProperty())) {
                    values.add(new String[] { info.getProperty(), info.getContent() });
                } else if (Strings.isNotBlank(info.getHttpEqui())) {
                    values.add(new String[] { info.getHttpEqui(), info.getContent() });
                }
            }
            final DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
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
