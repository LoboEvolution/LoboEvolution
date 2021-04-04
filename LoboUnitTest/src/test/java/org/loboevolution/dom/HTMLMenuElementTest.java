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
