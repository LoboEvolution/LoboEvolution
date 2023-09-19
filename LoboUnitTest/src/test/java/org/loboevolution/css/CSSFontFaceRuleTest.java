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

import org.htmlunit.cssparser.dom.CSSFontFaceRuleImpl;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link CSSFontFaceRuleImpl}.
 */

public class CSSFontFaceRuleTest extends LoboUnitTest {

    @Test
    public void simple() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @font-face { font-family: Delicious; src: url('Delicious-Bold.otf'); }\n"
                + "  h3 { font-family: Delicious;  }\n"
                + "</style>\n"
                + "<script>\n"
                + "try {\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule);\n"
                + "  alert(rule.type);\n"
                + "  alert(rule.cssText);\n"
                + "}\n"
                + "catch (e) { alert('exception'); }\n"
                + "</script></body></html>";
        final String[] messages = {"[object CSSFontFaceRule]", "5",
                "@font-face { font-family: Delicious; src: url(\"Delicious-Bold.otf\"); }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void urlSlashSlashColon() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @font-face { font-family: Delicious; src: url(//:); }\n"
                + "  h3 { font-family: Delicious;  }\n"
                + "</style>\n"
                + "<script>\n"
                + "try {\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule.cssText);\n"
                + "}\n"
                + "catch (e) { alert('exception'); }\n"
                + "</script></body></html>";
        final String[] messages = {"@font-face { font-family: Delicious; src: url(\"//:\"); }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void urlSlashColon() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @font-face { font-family: Delicious; src: url(/:); }\n"
                + "  h3 { font-family: Delicious;  }\n"
                + "</style>\n"
                + "<script>\n"
                + "try {\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule.cssText);\n"
                + "}\n"
                + "catch (e) { alert('exception'); }\n"
                + "</script></body></html>";
        final String[] messages = {"@font-face { font-family: Delicious; src: url(\"/:\"); }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void urlSlashSlash() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @font-face { font-family: Delicious; src: url(//); }\n"
                + "  h3 { font-family: Delicious;  }\n"
                + "</style>\n"
                + "<script>\n"
                + "try {\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule.cssText);\n"
                + "}\n"
                + "catch (e) { alert('exception'); }\n"
                + "</script></body></html>";
        final String[] messages = {"@font-face { font-family: Delicious; src: url(\"//\"); }"};
        checkHtmlAlert(html, messages);
    }
}
