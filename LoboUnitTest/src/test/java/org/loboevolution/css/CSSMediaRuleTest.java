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
package org.loboevolution.css;

import org.htmlunit.cssparser.dom.CSSMediaRuleImpl;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link CSSMediaRuleImpl}.
 */
public class CSSMediaRuleTest extends LoboUnitTest {

    @Test
    public void simple() {
        final String html
            = "<html><body>\n"
            + "<style>\n"
            + "  @media screen, print { p { background-color:#FFFFFF; }};\n"
            + "</style>\n"
            + "<script>\n"
            + "  var styleSheet = document.styleSheets[0];\n"
            + "  if (styleSheet.cssRules) {\n"
            + "    var rule = styleSheet.cssRules[0];\n"
            + "    var mediaList = rule.media;\n"
            + "    alert(mediaList.length);\n"
            + "    for (var i = 0; i < mediaList.length; i++) {\n"
            + "      alert(mediaList.item(i));\n"
            + "    }\n"
            + "    alert(mediaList.mediaText);\n"
            + "  } else {// Internet Explorer\n"
            + "    alert('Your browser does not support this example!');\n"
            + "  }\n"
            + "</script>\n"
            + "</body></html>";

        final String[] messages = {"2", "screen", "print", "screen, print"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void mediaQuery() {
        final String html
            = "<html><body>\n"
            + "<style>\n"
            + "  @media only screen and (color), print { p { background-color:#FFFFFF; }};\n"
            + "</style>\n"
            + "<script>\n"
            + "  var styleSheet = document.styleSheets[0];\n"
            + "  if (styleSheet.cssRules) {\n"
            + "    var rule = styleSheet.cssRules[0];\n"
            + "    var mediaList = rule.media;\n"
            + "    alert(mediaList.length);\n"
            + "    for (var i = 0; i < mediaList.length; i++) {\n"
            + "      alert(mediaList.item(i));\n"
            + "    }\n"
            + "    alert(mediaList.mediaText);\n"
            + "  } else {// Internet Explorer\n"
            + "    alert('Your browser does not support this example!');\n"
            + "  }\n"
            + "</script>\n"
            + "</body></html>";
        final String[] messages = {"2", "only screen and (color)", "print", "only screen and (color), print"};
        checkHtmlAlert(html, messages);
    }
}
