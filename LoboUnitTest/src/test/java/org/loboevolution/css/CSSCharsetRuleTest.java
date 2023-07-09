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
 * Tests for {@link org.htmlunit.cssparser.dom.CSSCharsetRuleImpl}.
 */
public class CSSCharsetRuleTest  extends LoboUnitTest {


    @Test
    public void inStyle() {
        final String html
            = "<html><body>\n"
            + "<style>@charset \"UTF-8\";</style>\n"
            + "<script>\n"
            + "  var rules = document.styleSheets[0].cssRules;\n"
            + "  alert(rules.length);\n"
            + "</script>\n"
            + "</body></html>";

        final String[] messages = {"0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void inLink() {
        final String html
            = "<html><body>\n"
            + "<link rel='stylesheet' href='../css/imp.css'>\n"
            + "<script>\n"
            + "  var rules = document.styleSheets[0].cssRules;\n"
            + "  alert(rules.length);\n"
            + "</script>\n"
            + "</body></html>";

        final String[] messages = {"0"};
        checkHtmlAlert(html, messages);
    }
}