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
package org.loboevolution.html;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLSelectElement;
import org.loboevolution.html.dom.domimpl.HTMLSelectElementImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link HTMLSelectElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLSelectElement2Test extends LoboUnitTest {

    @Test
    public void noOnchangeFromJS() {
        final String html = "<html><head><title>Test infinite loop on js onchange</title></head>\n"
                + "<body><form name='myForm'>\n"
                + "<select name='a' onchange='this.form.b.selectedIndex=0'>\n"
                + "<option value='1'>one</option>\n"
                + "<option value='2'>two</option>\n"
                + "</select>\n"
                + "<select name='b' onchange='alert(\"b changed\")'>\n"
                + "<option value='G'>green</option>\n"
                + "<option value='R' selected>red</option>\n"
                + "</select>\n"
                + "</form>\n"
                + "</body>\n"
                + "</html>";

        final HTMLDocument document = loadHtml(html);
        HTMLSelectElementImpl elem = (HTMLSelectElementImpl) document.getElementById("a");
        assertEquals("two", elem.getOptions().item(1).getNodeValue());

        elem = (HTMLSelectElementImpl) document.getElementById("b");
        assertEquals(1, elem.getSelectedOptions().getLength());
        assertEquals("red", elem.getOptions().item(0).getNodeValue());
    }
}
