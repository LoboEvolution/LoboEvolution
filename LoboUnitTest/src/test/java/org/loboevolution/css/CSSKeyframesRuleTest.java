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

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for CSSKeyframesRule.
 */
public class CSSKeyframesRuleTest extends LoboUnitTest {

    @Test
    public void simple() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @keyframes identifier { 0% { top: 0; left: 0; } 100% { top: 100px; left: 100%; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  if (styleSheet.cssRules) {\n"
                + "    var rule = styleSheet.cssRules[0];\n"
                + "    alert(rule);\n"
                + "    alert(rule.type);\n"
                + "  } else {\n"
                + "    alert('Your browser does not support this example');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object CSSKeyframesRule]", "7"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void name() throws Exception {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @keyframes identifier { 0% { top: 0; left: 0; } 100% { top: 100px; left: 100%; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  if (styleSheet.cssRules) {\n"
                + "    var rule = styleSheet.cssRules[0];\n"
                + "    alert(rule);\n"
                + "    alert(rule.name);\n"
                + "  } else {\n"
                + "    alert('Your browser does not support this example');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object CSSKeyframesRule]", "identifier"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void cssRules() throws Exception {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @keyframes identifier { 0% { top: 0; left: 0; } 100% { top: 100px; left: 100%; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  if (styleSheet.cssRules) {\n"
                + "    var rule = styleSheet.cssRules[0];\n"
                + "    alert(rule.cssRules);\n"
                + "  } else {\n"
                + "    alert('Your browser does not support this example');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object CSSRuleList]"};
        checkHtmlAlert(html, messages);
    }
}