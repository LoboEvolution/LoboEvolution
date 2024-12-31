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
package org.loboevolution.dom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Comment;

/**
 * Tests for {@link Comment}.
 */
@ExtendWith(AlertsExtension.class)
public class CommentTest extends LoboUnitTest {


    @Test
    @Alerts("[object Comment]")
    public void simpleScriptable() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.body.firstChild);\n"
                + "}\n"
                + "</script></head><body onload='test()'><!-- comment --></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"after", "comment"})
    public void textContent() {
        final String html
                = "<html><body>\n"
                + "<div id='it'><!--comment-->after</div>\n"
                + "<script>\n"
                + "var node = document.getElementById('it');\n"
                + "alert(node.textContent);\n"
                + "alert(node.firstChild.textContent);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"after", "undefined"})
    public void innerText() {
        final String html
                = "<html><body>\n"
                + "<div id='it'><!--comment-->after</div>\n"
                + "<script>\n"
                + "var node = document.getElementById('it');\n"
                + "alert(node.innerText);\n"
                + "alert(node.firstChild.innerText);\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    private void property(final String property) {
        final String html
                = "<html><body>\n"
                + "<div id='it'><!--abcdefg-->after</div>\n"
                + "<script>\n"
                + "var node = document.getElementById('it');\n"
                + "alert(node.firstChild." + property + ");\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("undefined")
    public void id() {
        property("id");
    }

    @Test
    @Alerts("undefined")
    public void className() {
        property("className");
    }

    @Test
    @Alerts("undefined")
    public void tagName() {
        property("tagName");
    }

    @Test
    @Alerts("undefined")
    public void text() {
        property("text");
    }

    @Test
    @Alerts("undefined")
    public void document() {
        property("document");
    }

}
