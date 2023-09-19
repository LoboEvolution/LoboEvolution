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
