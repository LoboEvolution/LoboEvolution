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
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLParamElement;

/**
 * Unit tests for {@link HTMLParamElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLParamElementTest extends LoboUnitTest {

    @Test
    @Alerts({"myValue", "", "", ""})
    public void value() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"                        + "  function test() {\n"
                        + "    for(var i = 1; i < 5; i++) {\n"
                        + "      var param = document.getElementById('tester' + i);\n"
                        + "      alert(param.value);\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <object>\n"
                        + "    <param id='tester1' name='testParam' value='myValue'/>\n"
                        + "    <param id='tester2' name='testParam' value=''/>\n"
                        + "    <param id='tester3' name='testParam' />\n"
                        + "    <param id='tester4' />\n"
                        + "  </object>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"testParam", "", "", ""})
    public void name() {
        final String html =
                "<html><head>\n"
                        + "    <script>\n"
                        + "  function test() {\n"
                        + "    for(var i = 1; i < 5; i++) {\n"
                        + "      var param = document.getElementById('tester' + i);\n"
                        + "      alert(param.name);\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <object>\n"
                        + "    <param id='tester1' name='testParam' value='myValue'/>\n"
                        + "    <param id='tester2' name='' value='myValue'/>\n"
                        + "    <param id='tester3' value='myValue'/>\n"
                        + "    <param id='tester4' />\n"
                        + "  </object>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"ttt", "", "", "", "ttt", "", "", "", "ttt", "ttt", "ttt"})
    public void type() {
        final String html =
                "<html><head>\n"
                        + "    <script>\n"
                        + "  function test() {\n"
                        + "    for(var i = 1; i < 12; i++) {\n"
                        + "      var param = document.getElementById('tester' + i);\n"
                        + "      alert(param.type);\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <object>\n"
                        + "    <param id='tester1' type='ttt' value='myValue'/>\n"
                        + "    <param id='tester2' type='' value='myValue'/>\n"
                        + "    <param id='tester3' value='myValue'/>\n"
                        + "    <param id='tester4' />\n"

                        + "    <param id='tester5' type='ttt' value='myValue' valueType='ref'/>\n"
                        + "    <param id='tester6' type='' value='myValue' valueType='ref'/>\n"
                        + "    <param id='tester7' value='myValue' valueType='ref'/>\n"
                        + "    <param id='tester8' valueType='ref'/>\n"

                        + "    <param id='tester9' type='ttt' value='myValue' valueType='data'/>\n"
                        + "    <param id='tester10' type='ttt' value='myValue' valueType='object'/>\n"
                        + "    <param id='tester11' type='ttt' value='myValue' valueType='foo'/>\n"
                        + "  </object>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"ref", "object", "data", "foo"})
    public void valueType() {
        final String html =
                "<html><head>\n"
                        + "    <script>\n"
                        + "  function test() {\n"
                        + "    for(var i = 1; i < 5; i++) {\n"
                        + "      var param = document.getElementById('tester' + i);\n"
                        + "      alert(param.valueType);\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <object>\n"
                        + "    <param id='tester1' valuetype='ref' value='myValue'/>\n"
                        + "    <param id='tester2' valuetype='object' value='myValue'/>\n"
                        + "    <param id='tester3' valuetype='data' value='myValue'/>\n"
                        + "    <param id='tester4' valuetype='foo' value='myValue'/>\n"
                        + "  </object>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }
}
