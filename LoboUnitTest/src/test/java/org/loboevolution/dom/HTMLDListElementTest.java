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
