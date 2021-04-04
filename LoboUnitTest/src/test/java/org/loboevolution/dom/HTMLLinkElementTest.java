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
 * Tests for {@link org.loboevolution.html.dom.HTMLLinkElement}.
 */
public class HTMLLinkElementTest extends LoboUnitTest {

    /**
     * <p>basicLinkAttributes.</p>
     */
    @Test
    public void basicLinkAttributes() {
        final String html =
                "<html>\n"
                        + "  <body onload='test()'>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var s = document.createElement('link');\n"
                        + "        alert(s.href);\n"
                        + "        alert(s.type);\n"
                        + "        alert(s.rel);\n"
                        + "        alert(s.rev);\n"
                        + "        s.href = 'test.css';\n"
                        + "        s.type = 'text/css';\n"
                        + "        s.rel  = 'stylesheet';\n"
                        + "        s.rev  = 'stylesheet1';\n"
                        + "        alert(s.href);\n"
                        + "        alert(s.type);\n"
                        + "        alert(s.rel);\n"
                        + "        alert(s.rev);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {"", "", "", "", "§§URL§§test.css", "text/css", "stylesheet", "stylesheet1"};;
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>readWriteRel.</p>
     */
    @Test
    public void readWriteRel() {
        final String html
                = "<html><body><link id='l1'><link id='l2' rel='alternate help'><script>\n"
                + "var l1 = document.getElementById('l1'), l2 = document.getElementById('l2');\n"

                + "alert(l1.rel);\n"
                + "alert(l2.rel);\n"

                + "l1.rel = 'prefetch';\n"
                + "l2.rel = 'prefetch';\n"
                + "alert(l1.rel);\n"
                + "alert(l2.rel);\n"

                + "l1.rel = 'not supported';\n"
                + "l2.rel = 'notsupported';\n"
                + "alert(l1.rel);\n"
                + "alert(l2.rel);\n"

                + "</script></body></html>";
        final String[] messages = {"", "alternate help", "prefetch", "prefetch", "not supported", "notsupported"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>relList.</p>
     */
    @Test
    public void relList() {
        final String html
                = "<html><body><link id='l1'><link id='l2' rel='alternate help'><script>\n"
                + "var l1 = document.getElementById('l1'), l2 = document.getElementById('l2');\n"

                + "try {\n"
                + "  alert(l1.relList.length);\n"
                + "  alert(l2.relList.length);\n"

                + "  for (var i = 0; i < l2.relList.length; i++) {\n"
                + "    alert(l2.relList[i]);\n"
                + "  }\n"
                + "} catch(e) { alert('exception'); }\n"

                + "</script></body></html>";
        final String[] messages = {"0", "2", "alternate", "help"};
        checkHtmlAlert(html, messages);
    }
}
