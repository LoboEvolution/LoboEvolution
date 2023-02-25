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

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;
import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.net.HttpNetwork;

import java.awt.*;

/**
 * <p>SourceViewerUI class.</p>
 */
public class SourceViewerUI  extends AbstractToolsUI {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for InfoPageUI.</p>
     *
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    public SourceViewerUI(BrowserFrame frame) {
        try {
            final ToolBar toolbar = frame.getToolbar();
            add(addTextArea(HttpNetwork.getSource(toolbar.getAddressBar().getText())));
        } catch (final Exception exp) {
            exp.printStackTrace();
        }
    }

    private RTextScrollPane addTextArea(String text) {
        final RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
        textArea.setText(text);

        final RTextScrollPane scrollBar = new RTextScrollPane(textArea);
        scrollBar.setBorder(null);
        scrollBar.setLineNumbersEnabled(true);
        scrollBar.setViewportView(textArea);
        scrollBar.setPreferredSize(new Dimension(420, 380));
        return scrollBar;
    }
}
