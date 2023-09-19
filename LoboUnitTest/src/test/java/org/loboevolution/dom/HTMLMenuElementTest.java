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
 * Tests for {@link org.loboevolution.html.dom.HTMLMenuElement}.
 */
public class HTMLMenuElementTest extends LoboUnitTest {

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
                        + "        alert(document.getElementById('menu1').compact);\n"
                        + "        alert(document.getElementById('menu2').compact);\n"
                        + "        alert(document.getElementById('menu3').compact);\n"
                        + "        alert(document.getElementById('menu4').compact);\n"
                        + "        alert(document.getElementById('menu1').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('menu2').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('menu3').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('menu4').getAttribute('compact'));\n"

                        + "        document.getElementById('menu1').compact = true;\n"
                        + "        document.getElementById('menu2').compact = false;\n"
                        + "        document.getElementById('menu3').compact = 'xyz';\n"
                        + "        document.getElementById('menu4').compact = null;\n"
                        + "        alert(document.getElementById('menu1').compact);\n"
                        + "        alert(document.getElementById('menu2').compact);\n"
                        + "        alert(document.getElementById('menu3').compact);\n"
                        + "        alert(document.getElementById('menu4').compact);\n"
                        + "        alert(document.getElementById('menu1').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('menu2').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('menu3').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('menu4').getAttribute('compact'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <menu id='menu1'><li>a</li><li>b</li></menu>\n"
                        + "    <menu compact='' id='menu2'><li>a</li><li>b</li></menu>\n"
                        + "    <menu compact='blah' id='menu3'><li>a</li><li>b</li></menu>\n"
                        + "    <menu compact='2' id='menu4'><li>a</li><li>b</li></menu>\n"
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
                        + "        alert(document.getElementById('menu1').type);\n"
                        + "        alert(document.getElementById('menu2').type);\n"
                        + "        alert(document.getElementById('menu3').type);\n"
                        + "        alert(document.getElementById('menu4').type);\n"
                        + "        alert(document.getElementById('menu5').type);\n"
                        + "        alert(document.getElementById('menu1').getAttribute('type'));\n"
                        + "        alert(document.getElementById('menu2').getAttribute('type'));\n"
                        + "        alert(document.getElementById('menu3').getAttribute('type'));\n"
                        + "        alert(document.getElementById('menu4').getAttribute('type'));\n"
                        + "        alert(document.getElementById('menu5').getAttribute('type'));\n"

                        + "        try { document.getElementById('menu1').type = 'list' } catch(e) {alert('ex');}\n"
                        + "        alert(document.getElementById('menu1').type);\n"

                        + "        try { document.getElementById('menu1').type = 'context' } catch(e) {alert('ex');}\n"
                        + "        alert(document.getElementById('menu1').type);\n"

                        + "        try { document.getElementById('menu1').type = 'toolbar' } catch(e) {alert('ex');}\n"
                        + "        alert(document.getElementById('menu1').type);\n"

                        + "        try { document.getElementById('menu1').type = 'ConText' } catch(e) {alert('ex');}\n"
                        + "        alert(document.getElementById('menu1').type);\n"

                        + "        try { document.getElementById('menu1').type = '' } catch(e) {alert('ex');}\n"
                        + "        alert(document.getElementById('menu1').type);\n"

                        + "        try { document.getElementById('menu1').type = 'unknown' } catch(e) {alert('ex');}\n"
                        + "        alert(document.getElementById('menu1').type);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <menu id='menu1'><li>a</li><li>b</li></menu>\n"
                        + "    <menu type='' id='menu2'><li>a</li><li>b</li></menu>\n"
                        + "    <menu type='blah' id='menu3'><li>a</li><li>b</li></menu>\n"
                        + "    <menu type='context' id='menu4'><li>a</li><li>b</li></menu>\n"
                        + "    <menu type='ToolBar' id='menu5'><li>a</li><li>b</li></menu>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages =  {"undefined", "undefined", "undefined", "undefined", "undefined",
                null, "", "blah", "context", "ToolBar", "list",
                "context", "toolbar", "ConText", "", "unknown"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>label.</p>
     */
    @Test
    public void label() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var menu1 = document.getElementById('menu1');\n"
                        + "        alert(menu1.label);\n"

                        + "        var menu2 = document.getElementById('menu1');\n"
                        + "        alert(menu2.label);\n"

                        + "        menu1.label = 'new';\n"
                        + "        alert(menu1.label);\n"

                        + "        menu1.label = '';\n"
                        + "        alert(menu1.label);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <menu id='menu1' ><li>a</li><li>b</li></menu>\n"
                        + "    <menu id='menu2' label='Menu1'><li>a</li><li>b</li></menu>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"undefined", "undefined", "new", ""};
        checkHtmlAlert(html, messages);
    }
}
