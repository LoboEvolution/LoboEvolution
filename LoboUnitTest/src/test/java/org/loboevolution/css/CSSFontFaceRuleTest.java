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
