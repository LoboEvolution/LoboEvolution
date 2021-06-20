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

package org.loboevolution.js;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

public class ConsoleUnitTest extends LoboUnitTest {

    @Test
    public void prototype() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "<script>\n"
                + "  try {\n"
                + "    alert(window.console == undefined);\n"
                + "    alert(typeof window.console);\n"
                + "    alert('console' in window);\n"
                + "  } catch(e) { alert('exception');}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"false", "object", "true"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void prototypeUppercase() throws Exception {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "<script>\n"
                + "  try {\n"
                + "    alert(window.Console == undefined);\n"
                + "    alert(typeof window.Console);\n"
                + "    alert('Console' in window);\n"
                + "  } catch(e) { alert('exception');}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"true", "undefined", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void methods() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "<script>\n"
                + "  alert(typeof console.log);\n"
                + "  alert(typeof console.info);\n"
                + "  alert(typeof console.warn);\n"
                + "  alert(typeof console.error);\n"
                + "  alert(typeof console.debug);\n"
                + "  alert(typeof console.timeStamp);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"function", "function", "function", "function", "function", "function"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void fromWindow() throws Exception {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "<script>\n"
                + "  try {\n"
                + "    var x = console.error;\n"
                + "    x('hello');\n"
                + "    alert('success');\n"
                + "  } catch(e) {alert('exception')}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"success"};
        checkHtmlAlert(html, messages);
    }
}
