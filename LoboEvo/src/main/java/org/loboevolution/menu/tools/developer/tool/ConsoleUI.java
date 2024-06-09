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

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.loboevolution.config.HtmlRendererConfigImpl;
import org.loboevolution.html.js.storage.SessionStorage;
import org.loboevolution.menu.tools.AbstractToolsUI;

import java.awt.*;
import java.io.Serial;

/**
 * <p>ConsoleUI class.</p>
 */
public class ConsoleUI extends AbstractToolsUI {

    /** The Constant serialVersionUID. */
    @Serial
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
        final SessionStorage storage = new SessionStorage(new HtmlRendererConfigImpl());
        textArea.setText(String.valueOf(storage.getItem("log")));

        final RTextScrollPane scrollBar = new RTextScrollPane(textArea);
        scrollBar.setBorder(null);
        scrollBar.setLineNumbersEnabled(true);
        scrollBar.setViewportView(textArea);
        scrollBar.setPreferredSize(new Dimension(420, 380));
        return scrollBar;
    }
}
