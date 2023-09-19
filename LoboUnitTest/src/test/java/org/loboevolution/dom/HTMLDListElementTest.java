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
 * Tests for {@link org.loboevolution.html.dom.HTMLDListElement}.
 */
public class HTMLDListElementTest extends LoboUnitTest {

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
                        + "        alert(document.getElementById('dl1').compact);\n"
                        + "        alert(document.getElementById('dl2').compact);\n"
                        + "        alert(document.getElementById('dl3').compact);\n"
                        + "        alert(document.getElementById('dl4').compact);\n"
                        + "        alert(document.getElementById('dl1').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dl2').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dl3').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dl4').getAttribute('compact'));\n"

                        + "        document.getElementById('dl1').compact = true;\n"
                        + "        document.getElementById('dl2').compact = false;\n"
                        + "        document.getElementById('dl3').compact = 'xyz';\n"
                        + "        document.getElementById('dl4').compact = null;\n"
                        + "        alert(document.getElementById('dl1').compact);\n"
                        + "        alert(document.getElementById('dl2').compact);\n"
                        + "        alert(document.getElementById('dl3').compact);\n"
                        + "        alert(document.getElementById('dl4').compact);\n"
                        + "        alert(document.getElementById('dl1').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dl2').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dl3').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dl4').getAttribute('compact'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dl id='dl1'><dt>a</dt><dd>b</dd></dl>\n"
                        + "    <dl compact='' id='dl2'><dt>a</dt><dd>b</dd></dl>\n"
                        + "    <dl compact='blah' id='dl3'><dt>a</dt><dd>b</dd></dl>\n"
                        + "    <dl compact='2' id='dl4'><dt>a</dt><dd>b</dd></dl>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"false", "true", "true", "true", null, "", "blah", "2",
                "true", "false", "true", "false", "", null, "", null};;
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
                        + "        alert(document.getElementById('dl1').type);\n"
                        + "        alert(document.getElementById('dl2').type);\n"
                        + "        alert(document.getElementById('dl3').type);\n"
                        + "        alert(document.getElementById('dl4').type);\n"
                        + "        alert(document.getElementById('dl1').getAttribute('type'));\n"
                        + "        alert(document.getElementById('dl2').getAttribute('type'));\n"
                        + "        alert(document.getElementById('dl3').getAttribute('type'));\n"
                        + "        alert(document.getElementById('dl4').getAttribute('type'));\n"

                        + "        document.getElementById('dl1').type = '1';\n"
                        + "        alert(document.getElementById('dl1').type);\n"

                        + "        document.getElementById('dl1').type = 'a';\n"
                        + "        alert(document.getElementById('dl1').type);\n"

                        + "        document.getElementById('dl1').type = 'A';\n"
                        + "        alert(document.getElementById('dl1').type);\n"

                        + "        document.getElementById('dl1').type = 'i';\n"
                        + "        alert(document.getElementById('dl1').type);\n"

                        + "        document.getElementById('dl1').type = 'I';\n"
                        + "        alert(document.getElementById('dl1').type);\n"

                        + "        try { document.getElementById('dl1').type = 'u' } catch(e) {alert('exception');}\n"
                        + "        alert(document.getElementById('dl1').type);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dl id='dl1'><dt>a</dt><dd>b</dd></dl>\n"
                        + "    <dl type='' id='dl2'><dt>a</dt><dd>b</dd></dl>\n"
                        + "    <dl type='blah' id='dl3'><dt>a</dt><dd>b</dd></dl>\n"
                        + "    <dl type='A' id='dl4'><dt>a</dt><dd>b</dd></dl>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"undefined", "undefined", "undefined", "undefined",
                null, "", "blah", "A", "1", "a", "A", "i", "I", "u"};
        checkHtmlAlert(html, messages);
    }
}
