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
package org.loboevolution.css;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link CSSPageRule}.
 */
@ExtendWith(AlertsExtension.class)
public class CSSTest extends LoboUnitTest {


    @Test
    @Alerts({"[object CSS]", "undefined"})
    public void global() {
        final String html
                = "<html><body>\n"
                + "<style>@charset 'UTF-8';</style>\n"
                + "<script>\n"                + "  try {\n"
                + "   alert(CSS);"
                + "   alert(CSS.prototype);"
                + "  } catch (e) {alert('Exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("ctor Exception")
    public void constructor() {
        final String html
                = "<html><body>\n"
                + "<style>@charset 'UTF-8';</style>\n"
                + "<script>\n"                + "  try {\n"
                + "    var o = Object.create(CSS.prototype);\n"
                + "   alert(o);"
                + "  } catch (e) {alert('ctor Exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"true", "true", "true"})
    public void supports() {
        final String html
                = "<html><body>\n"
                + "<script>\n"                + "  try {\n"
                + "   alert(CSS.supports('display', 'flex'));"
                + "   alert(CSS.supports('display', 'grid'));"
                + "   alert(CSS.supports('color', 'red'));"
                + "  } catch (e) {alert('Exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"true", "true"})
    public void supportsCondition() {
        final String html
                = "<html><body>\n"
                + "<script>\n"                + "  try {\n"
                + "   alert(CSS.supports('display: flex'));"
                + "   alert(CSS.supports('color: red'));"
                + "  } catch (e) {alert('Exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"true", "false"})
    public void supportsSelector() {
        final String html
                = "<html><body>\n"
                + "<script>\n"                + "  try {\n"
                + "   alert(CSS.supports('selector(div)'));"
                + "   alert(CSS.supports('selector(div, span)'));"
                + "  } catch (e) {alert('Exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
