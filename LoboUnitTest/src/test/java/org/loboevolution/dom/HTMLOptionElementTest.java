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

package org.loboevolution.dom;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.dom.HTMLOptionElement}.
 */
public class HTMLOptionElementTest extends LoboUnitTest {

    /**
     * <p>label.</p>
     */
    @Test
    public void label() {
        final String html
                = "<html><head><title>foo</title><script>\n"
                + "function test() {\n"
                + "  var s = document.getElementById('testSelect');\n"
                + "  var lastIndex = s.length;\n"
                + "  s.length += 1;\n"
                + "  s[lastIndex].value = 'value2';\n"
                + "  s[lastIndex].text  = 'text2';\n"
                + "  alert(s[0].value);\n"
                + "  alert(s[0].text);\n"
                + "  alert(s[0].label);\n"
                + "  alert(s[1].value);\n"
                + "  alert(s[1].text);\n"
                + "  alert(s[1].label);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <select id='testSelect'>\n"
                + "    <option value='value1' label='label1'>text1</option>\n"
                + "  </select>\n"
                + "</form>\n"
                + "</body></html>";

        final String[] messages = {"value1", "text1", "label1", "value2", "text2", "text2"};
        checkHtmlAlert(html, messages);
    }
}
