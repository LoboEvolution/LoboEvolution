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
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLLabelElement;
import org.loboevolution.html.dom.domimpl.HTMLButtonElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link HTMLLabelElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLLabelElementTest extends LoboUnitTest {

    @Test
    @Alerts("")
    public void htmlForNone() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "<script>\n"                + "      function doTest() {\n"
                + "        alert(document.getElementById('label1').htmlFor);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1'>Item</label>\n"
                + "    <input type='text' id='text1'>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("")
    public void htmlForEmpty() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "<script>\n"                + "      function doTest() {\n"
                + "        alert(document.getElementById('label1').htmlFor);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1' for=''>Item</label>\n"
                + "    <input type='text' id='text1'>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("unknown")
    public void htmlForUnknown() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "<script>\n"                + "      function doTest() {\n"
                + "        alert(document.getElementById('label1').htmlFor);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1' for='unknown'>Item</label>\n"
                + "    <input type='text' id='text1'>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("text1")
    public void htmlForKnown() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "<script>\n"                + "      function doTest() {\n"
                + "        alert(document.getElementById('label1').htmlFor);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1' for='text1'>Item</label>\n"
                + "    <input type='text' id='text1'>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"unknown", "null", "null"})
    public void htmlForSetToUnknown() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "<script>\n"                + "      function doTest() {\n"
                + "        try {\n"
                + "          document.getElementById('label1').htmlFor = 'unknown';\n"
                + "        } catch (e) {"
                + "          alert('exception');\n"
                + "        }\n"
                + "        alert(document.getElementById('label1').htmlFor);\n"
                + "        alert(document.getElementById('label1').control);\n"
                + "        alert(document.getElementById('label1').form);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1'>Item</label>\n"
                + "    <form id='form1'>\n"
                + "      <input type='text' id='text1'>\n"
                + "    </form>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"div1", "null", "null"})
    public void htmlForSetToNotLabelable() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "<script>\n"                + "      function doTest() {\n"
                + "        try {\n"
                + "          document.getElementById('label1').htmlFor = 'div1';\n"
                + "        } catch (e) {"
                + "          alert('exception');\n"
                + "        }\n"
                + "        alert(document.getElementById('label1').htmlFor);\n"
                + "        alert(document.getElementById('label1').control);\n"
                + "        alert(document.getElementById('label1').form);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1'>Item</label>\n"
                + "    <form id='form1'>\n"
                + "      <div id='div1'></div>\n"
                + "    </form>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"text1", "[object HTMLInputElement]", "[object HTMLFormElement]"})
    public void htmlForSetToLabelable() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "<script>\n"                + "      function doTest() {\n"
                + "        try {\n"
                + "          document.getElementById('label1').htmlFor = 'text1';\n"
                + "        } catch (e) {"
                + "          alert('exception');\n"
                + "        }\n"
                + "        alert(document.getElementById('label1').htmlFor);\n"
                + "        alert(document.getElementById('label1').control);\n"
                + "        alert(document.getElementById('label1').form);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1'>Item</label>\n"
                + "    <form id='form1'>\n"
                + "      <input type='text' id='text1'>\n"
                + "    </form>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void controlSet() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "<script>\n"                + "      function doTest() {\n"
                + "        try {\n"
                + "          document.getElementById('label1').control = document.getElementById('text1');\n"
                + "        } catch (e) {"
                + "          alert('exception');\n"
                + "        }\n"
                + "        alert(document.getElementById('label1').control);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1'>Item</label>\n"
                + "    <input type='text' id='text1'>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void formSet() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "<script>\n"                + "      function doTest() {\n"
                + "        try {\n"
                + "          document.getElementById('label1').form = document.getElementById('form1');\n"
                + "        } catch (e) {"
                + "          alert('exception');\n"
                + "        }\n"
                + "        alert(document.getElementById('label1').form);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1'>Item</label>\n"
                + "    <form id='form1'>\n"
                + "      <input type='text' id='text1'>\n"
                + "    </form>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    public void clickAfterHtmlForSetByJS() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "      function doTest() {\n"
                + "        document.getElementById('label1').htmlFor = 'checkbox1';\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1'>Item</label>\n"
                + "    <input type='checkbox' id='checkbox1'>\n"
                + "  </body>\n"
                + "</html>";

        final HTMLDocument document = loadHtml(html);
        HTMLInputElementImpl checkbox = (HTMLInputElementImpl) document.getElementById("checkbox1");
        assertFalse(checkbox.isChecked());
        checkbox.getOnclick();
        assertTrue(checkbox.isChecked());
    }

    @Test
    public void clickAfterNestedByJS() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "      function doTest() {\n"
                + "        document.getElementById('label1').appendChild(document.getElementById('checkbox1'));\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1'>Item</label>\n"
                + "    <input type='checkbox' id='checkbox1'>\n"
                + "  </body>\n"
                + "</html>";

        final HTMLDocument document = loadHtml(html);
        HTMLInputElementImpl checkbox = (HTMLInputElementImpl) document.getElementById("checkbox1");
        assertFalse(checkbox.isChecked());
        checkbox.getOnclick();
        HTMLLabelElement label = (HTMLLabelElement) document.getElementById("label1");
        label.click();
        assertTrue(checkbox.isChecked());
    }

    @Test
    public void clickByJSAfterHtmlForSetByJS() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "      function doTest() {\n"
                + "        document.getElementById('label1').htmlFor = 'checkbox1';\n"
                + "      }\n"
                + "      function delegateClick() {\n"
                + "        try {\n"
                + "          document.getElementById('label1').click();\n"
                + "        } catch (e) {}\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1'>My Label</label>\n"
                + "    <input type='checkbox' id='checkbox1'><br>\n"
                + "    <input type=button id='button1' value='Test' onclick='delegateClick()'>\n"
                + "  </body>\n"
                + "</html>";

        final HTMLDocument document = loadHtml(html);
        HTMLInputElementImpl checkbox = (HTMLInputElementImpl) document.getElementById("checkbox1");
        assertFalse(checkbox.isChecked());

        HTMLButtonElementImpl button = (HTMLButtonElementImpl) document.getElementById("label1");
        button.click();
        assertTrue(checkbox.isChecked());
    }

    @Test
    public void clickByJSAfterNestedByJS() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "      function doTest() {\n"
                + "        document.getElementById('label1').appendChild(document.getElementById('checkbox1'));\n"
                + "      }\n"
                + "      function delegateClick() {\n"
                + "        try {\n"
                + "          document.getElementById('label1').click();\n"
                + "        } catch (e) {}\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <label id='label1'>My Label</label>\n"
                + "    <input type='checkbox' id='checkbox1'><br>\n"
                + "    <input type=button id='button1' value='Test' onclick='delegateClick()'>\n"
                + "  </body>\n"
                + "</html>";

        final HTMLDocument document = loadHtml(html);
        HTMLInputElementImpl checkbox = (HTMLInputElementImpl) document.getElementById("checkbox1");
        assertFalse(checkbox.isChecked());

        HTMLButtonElementImpl button = (HTMLButtonElementImpl) document.getElementById("label1");
        button.click();
        assertTrue(checkbox.isChecked());
    }

    @Test
    @Alerts({"", "A", "a", "A", "a8", "8Afoo", "8", "@"})
    public void accessKey() {
        final String html
                = "<html>\n"
                + "  <body>\n"
                + "    <label id='a1'>a1</label>\n"
                + "    <label id='a2' accesskey='A'>a2</label>\n"
                + "<script>\n"                + "      var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "      alert(a1.accessKey);\n"
                + "      alert(a2.accessKey);\n"
                + "      a1.accessKey = 'a';\n"
                + "      a2.accessKey = 'A';\n"
                + "      alert(a1.accessKey);\n"
                + "      alert(a2.accessKey);\n"
                + "      a1.accessKey = 'a8';\n"
                + "      a2.accessKey = '8Afoo';\n"
                + "      alert(a1.accessKey);\n"
                + "      alert(a2.accessKey);\n"
                + "      a1.accessKey = '8';\n"
                + "      a2.accessKey = '@';\n"
                + "      alert(a1.accessKey);\n"
                + "      alert(a2.accessKey);\n"
                + "    </script>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }
}
