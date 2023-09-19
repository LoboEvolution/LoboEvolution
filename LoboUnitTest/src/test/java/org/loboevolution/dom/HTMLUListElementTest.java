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

package org.loboevolution.dom;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.dom.HTMLUListElement}.
 */
public class HTMLUListElementTest extends LoboUnitTest {
	
    /**
     * <p>compact.</p>
     */
    @Test
    public void compact() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        alert(document.getElementById('u1').compact);\n"
                        + "        alert(document.getElementById('u2').compact);\n"
                        + "        alert(document.getElementById('u3').compact);\n"
                        + "        alert(document.getElementById('u4').compact);\n"
                        + "        alert(document.getElementById('u1').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('u2').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('u3').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('u4').getAttribute('compact'));\n"

                        + "        document.getElementById('u1').compact = true;\n"
                        + "        document.getElementById('u2').compact = false;\n"
                        + "        document.getElementById('u3').compact = 'xyz';\n"
                        + "        document.getElementById('u4').compact = null;\n"
                        + "        alert(document.getElementById('u1').compact);\n"
                        + "        alert(document.getElementById('u2').compact);\n"
                        + "        alert(document.getElementById('u3').compact);\n"
                        + "        alert(document.getElementById('u4').compact);\n"
                        + "        alert(document.getElementById('u1').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('u2').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('u3').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('u4').getAttribute('compact'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <ul id='u1'><li>a</li><li>b</li></ul>\n"
                        + "    <ul compact='' id='u2'><li>a</li><li>b</li></ul>\n"
                        + "    <ul compact='blah' id='u3'><li>a</li><li>b</li></ul>\n"
                        + "    <ul compact='2' id='u4'><li>a</li><li>b</li></ul>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages =  {"false", "true", "true", "true", null, "", "blah", "2",
                    "true", "false", "true", "false", "", null, "", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>type.</p>
     */
    @Test
    public void type() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        alert(document.getElementById('u1').type);\n"
                        + "        alert(document.getElementById('u2').type);\n"
                        + "        alert(document.getElementById('u3').type);\n"
                        + "        alert(document.getElementById('u4').type);\n"
                        + "        alert(document.getElementById('u1').getAttribute('type'));\n"
                        + "        alert(document.getElementById('u2').getAttribute('type'));\n"
                        + "        alert(document.getElementById('u3').getAttribute('type'));\n"
                        + "        alert(document.getElementById('u4').getAttribute('type'));\n"

                        + "        document.getElementById('u1').type = '1';\n"
                        + "        alert(document.getElementById('u1').type);\n"

                        + "        document.getElementById('u1').type = 'a';\n"
                        + "        alert(document.getElementById('u1').type);\n"

                        + "        document.getElementById('u1').type = 'A';\n"
                        + "        alert(document.getElementById('u1').type);\n"

                        + "        document.getElementById('u1').type = 'i';\n"
                        + "        alert(document.getElementById('u1').type);\n"

                        + "        document.getElementById('u1').type = 'I';\n"
                        + "        alert(document.getElementById('u1').type);\n"

                        + "        try { document.getElementById('u1').type = 'u' } catch(e) {alert('exception');}\n"
                        + "        alert(document.getElementById('u1').type);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <ul id='u1'><li>a</li><li>b</li></ul>\n"
                        + "    <ul type='' id='u2'><li>a</li><li>b</li></ul>\n"
                        + "    <ul type='blah' id='u3'><li>a</li><li>b</li></ul>\n"
                        + "    <ul type='A' id='u4'><li>a</li><li>b</li></ul>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"", "", "blah", "A", null, "", "blah", "A", "1", "a", "A", "i", "I", "u"};
        checkHtmlAlert(html, messages);
    }
}
