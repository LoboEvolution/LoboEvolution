/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.CharacterData;

/**
 * Tests for {@link CharacterData}.
 */
@ExtendWith(AlertsExtension.class)
public class CharacterDataTest extends LoboUnitTest {


    @Test
    @Alerts({"Some Text", "9", "3", "Some Text", "#text"})
    public void textNode() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + " alert(text1.data);\n"
                + " alert(text1.length);\n"
                + " alert(text1.nodeType);\n"
                + " alert(text1.nodeValue);\n"
                + " alert(text1.nodeName);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>Some Text</div></body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"Some New Text", "Some New Text"})
    public void setData() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + "  text1.data = 'Some New Text';\n"
                + " alert(text1.data);\n"
                + " alert(text1.nodeValue);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>Some Text</div></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"Some New Text", "Some New Text"})
    public void setNodeValue() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + "  text1.nodeValue = 'Some New Text';\n"
                + " alert(text1.data);\n"
                + " alert(text1.nodeValue);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>Some Text</div></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Some Text Appended")
    public void appendData() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + "  text1.appendData(' Appended');\n"
                + " alert(text1.data);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>Some Text</div></body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"Some Text", "Some", "Some", "me", ""})
    public void deleteData() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + "  try {\n"
                + "    text1.deleteData(5, 11);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(4, 5);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(1, 0);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(0, 2);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(0, 2);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>Some Not So New Text</div></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "", "", ""})
    public void deleteDataEmptyImput() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + "  try {\n"
                + "    text1.deleteData(0, 1);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(0, 0);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(0, 1);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(0, -1);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>-</div></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"exception", "exception", "exception", "exception"})
    public void deleteDataInvalidStart() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + "  try {\n"
                + "    text1.deleteData(-1, 4);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(20, 4);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(20, 0);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(20, -18);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>abcde</div></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"Some Not So New Te", "Some ", "So"})
    public void deleteDataNegativeCount() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + "  try {\n"
                + "    text1.deleteData(18, -15);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(5, -4);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "  try {\n"
                + "    text1.deleteData(2, -4);\n"
                + "   alert(text1.data);\n"
                + "  } catch (e) {alert('exception') }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>Some Not So New Text</div></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Some New Text")
    public void insertData() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + "  text1.insertData(5, 'New ');\n"
                + " alert(text1.data);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>Some Text</div></body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("Some New Text")
    public void replaceData() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + "  text1.replaceData(5, 3, 'New');\n"
                + " alert(text1.data);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>Some Old Text</div></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"New", "Some New Text"})
    public void substringData() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + " alert(text1.substringData(5, 3));\n"
                + " alert(text1.data);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>Some New Text</div></body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"Some ", "Text", "true"})
    public void textImpl_splitText() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var div1=document.getElementById('div1');\n"
                + "  var text1=div1.firstChild;\n"
                + "  var text2=text1.splitText(5);\n"
                + " alert(text1.data);\n"
                + " alert(text2.data);\n"
                + " alert(text1.nextSibling == text2);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='div1'>Some Text</div></body></html>";

        checkHtmlAlert(html);
    }
}
