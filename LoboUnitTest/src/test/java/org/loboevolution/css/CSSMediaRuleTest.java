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
