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

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.loboevolution.html.js.storage.SessionStorage;
import org.loboevolution.menu.tools.AbstractToolsUI;

import java.awt.*;

/**
 * <p>ConsoleUI class.</p>
 */
public class ConsoleUI extends AbstractToolsUI {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for ConsoleUI.</p>
     */
    public ConsoleUI() {
        add(addTextArea());
    }

    private RTextScrollPane addTextArea() {
        final RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL);
        SessionStorage storage = new SessionStorage();
        textArea.setText(String.valueOf(storage.getItem("log")));

        final RTextScrollPane scrollBar = new RTextScrollPane(textArea);
        scrollBar.setBorder(null);
        scrollBar.setLineNumbersEnabled(true);
        scrollBar.setViewportView(textArea);
        scrollBar.setPreferredSize(new Dimension(420, 380));
        return scrollBar;
    }
}
