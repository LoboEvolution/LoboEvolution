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
import org.loboevolution.html.dom.HTMLFormElement;

/**
 * Tests for {@link HTMLFormElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLFormElementTest extends LoboUnitTest {

    @Test
    @Alerts({"16", "button1", "button2", "checkbox1", "fileupload1", "hidden1",
            "radio1", "radio1",
            "select1", "select2", "password1", "reset1",
            "reset2", "submit1", "submit2", "textInput1", "textarea1"})
    public void elementsAccessor() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  alert(document.form1.length);\n"
                + "  for (var i = 0; i < document.form1.length; i++) {\n"
                + "    var element = document.form1.elements[i];\n"
                + "    if (element.type != 'radio' && element != document.form1[element.name]) {\n"
                + "      alert('name index not working for '+element.name);\n"
                + "    }\n"
                + "    alert(element.name);\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='button' name='button1' />\n"
                + "  <button type='button' name='button2'>button2</button>\n"
                + "  <input type='checkbox' name='checkbox1' />\n"
                + "  <input type='file' name='fileupload1' />\n"
                + "  <input type='hidden' name='hidden1' />\n"
                + "  <input type='radio' name='radio1' value='1' />\n"
                + "  <input type='radio' name='radio1' value='2' />\n"
                + "  <select name='select1'>\n"
                + "    <option>foo</option>\n"
                + "  </select>\n"
                + "  <select multiple='multiple' name='select2'>\n"
                + "    <option>foo</option>\n"
                + "  </select>\n"
                + "  <input type='password' name='password1' />\n"
                + "  <input type='reset' name='reset1' />\n"
                + "  <button type='reset' name='reset2'>reset2</button>\n"
                + "  <input type='submit' name='submit1' />\n"
                + "  <button type='submit' name='submit2'>submit2</button>\n"
                + "  <input type='text' name='textInput1' />\n"
                + "  <textarea name='textarea1'>foo</textarea>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined"})
    public void elementsAccessorOutOfBound() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  alert(document.form1[-1]);\n"
                + "  alert(document.form1[2]);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form name='form1'>\n"
                + "  <input type='button' name='button1'/>\n"
                + "  <input type='submit' name='submit1'/>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "textInput1", "button1", "textInput3"})
    public void elementsAccessorFormAttribute() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  alert(document.form1.length);\n"
                + "  for (var i = 0; i < document.form1.length; i++) {\n"
                + "    var element = document.form1.elements[i];\n"
                + "    alert(element.name);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"

                + "<input type='text' name='textInput1' form='myForm'/>\n"
                + "<input type='text' name='textInput2' form='form1'/>\n"

                + "<form id='myForm' name='form1'>\n"
                + "  <input type='button' name='button1' />\n"
                + "</form>\n"

                + "<input type='text' name='textInput3' form='myForm'/>\n"
                + "<input type='text' name='textInput4' form='form1'/>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "1", "2", "3"})
    public void radioButtonArray() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  var radioArray = document.form1['radio1'];\n"
                + "  alert(radioArray.length);\n"
                + "  for (var i = 0; i < radioArray.length; i++) {\n"
                + "    var element = radioArray[i];\n"
                + "    alert(element.value);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='radio' name='radio1' value='1'/>\n"
                + "  <input type='radio' name='radio1' value='2'/>\n"
                + "  <input type='radio' name='radio1' value='3'/>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * If there is only one radio button with a specified name then that radio
     * button will be returned for the name, not an array of radio buttons. Test
     * this.
     */
    @Test
    @Alerts("1")
    public void radioButton_OnlyOne() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  alert(document.form1['radio1'].value);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='radio' name='radio1' value='1'/>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"http://foo.com/", "mailto:me@bar.com", "mailto:me@bar.com"})
    public void actionProperty() {
        doTestProperty("action", "action", "http://foo.com/", "mailto:me@bar.com");
    }

    @Test
    @Alerts({"myForm", "testForm", "testForm"})
    public void nameProperty() {
        doTestProperty("name", "name", "myForm", "testForm");
    }

    @Test
    @Alerts("application/x-www-form-urlencoded")
    public void defaultEnctype() {
        enctype(null);
    }

    @Test
    @Alerts("application/x-www-form-urlencoded")
    public void emptyEnctype() {
        enctype("");
    }

    @Test
    @Alerts("application/x-www-form-urlencoded")
    public void blankEnctype() {
        enctype(" ");
    }

    @Test
    @Alerts("application/x-www-form-urlencoded")
    public void unknownEnctype() {
        enctype("unknown");
    }

    @Test
    @Alerts("application/x-www-form-urlencoded")
    public void urlencodedEnctype() {
        enctype("application/x-www-form-urlencoded");
    }

    @Test
    @Alerts("multipart/form-data")
    public void multipartEnctype() {
        enctype("multipart/form-data");
    }

    @Test
    @Alerts("text/plain")
    public void plainEnctype() {
        enctype("text/plain");
    }

    @Test
    @Alerts("application/x-www-form-urlencoded")
    public void xmlEnctype() {
        enctype("text/xml");
    }

    @Test
    @Alerts("application/x-www-form-urlencoded")
    public void jsonEnctype() {
        enctype("application/json");
    }

    private void enctype(final String encoding) {
        String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  alert(document.forms[0].encoding);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form name='testForm'";
        if (null != encoding) {
            html = html + " enctype='" + encoding + "'";
        }
        html = html + ">\n"
                + "    <input type='submit' name='submit1'/>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded"})
    public void jsDefaultEnctype() {
        jsEnctype(null);
        jsEncoding(null);
    }

    @Test
    @Alerts({"application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded"})
    public void jsEmptyEnctype() {
        jsEnctype("");
        jsEncoding("");
    }

    @Test
    @Alerts({"application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded"})
    public void jsBlankEnctype() {
        jsEnctype(" ");
        jsEncoding(" ");
    }

    @Test
    @Alerts({"application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded"})
    public void jsUnknownEnctype() {
        jsEnctype("unknown");
        jsEncoding("unknown");
    }

    @Test
    @Alerts({"application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded"})
    public void jsUrlencodedEnctype() {
        jsEnctype("application/x-www-form-urlencoded");
        jsEncoding("application/x-www-form-urlencoded");
    }

    @Test
    @Alerts({"multipart/form-data", "multipart/form-data", "multipart/form-data"})
    public void jsMultipartEnctype() {
        jsEnctype("multipart/form-data");
        jsEncoding("multipart/form-data");
    }

    @Test
    @Alerts({"text/plain", "text/plain", "text/plain"})
    public void jsPlainEnctype() {
        jsEnctype("text/plain");
        jsEncoding("text/plain");
    }

    @Test
    @Alerts({"application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded"})
    public void jsXmlEnctype() {
        jsEnctype("text/xml");
        jsEncoding("text/xml");
    }

    @Test
    @Alerts({"application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded",
            "application/x-www-form-urlencoded"})
    public void jsJsonEnctype() {
        jsEnctype("application/json");
        jsEncoding("application/json");
    }

    private void jsEnctype(final String enctype) {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"

                + "  function doTest() {\n"
                + "    try {\n"
                + "      document.forms[0].enctype = '" + enctype + "';\n"
                + "      alert(document.forms[0].enctype);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "    alert(document.forms[0].encoding);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form id='testForm' name='testForm' method='post' action = 'page2.html'>\n"
                + "    <input type='submit' name='submit1' />\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    private void jsEncoding(final String encoding) {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"

                + "  function doTest() {\n"
                + "    try {\n"
                + "      document.forms[0].encoding = '" + encoding + "';\n"
                + "      alert(document.forms[0].encoding);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "    alert(document.forms[0].enctype);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form id='testForm' name='testForm' method='post' action = 'page2.html'>\n"
                + "    <input type='submit' name='submit1' />\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"multipart/form-data", "application/x-www-form-urlencoded", "application/x-www-form-urlencoded"})
    public void encodingProperty() {
        doTestProperty("encoding", "enctype", "multipart/form-data", "application/x-www-form-urlencoded");
    }

    @Test
    @Alerts({"text/plain", "application/x-www-form-urlencoded", "newEncoding"})
    public void encodingPropertyTextPlain() {
        doTestProperty("encoding", "enctype", "text/plain", "newEncoding");
    }

    @Test
    @Alerts({"application/x-www-form-urlencoded", "application/x-www-form-urlencoded", "newEncoding"})
    public void encodingPropertyDummyValues() {
        doTestProperty("encoding", "enctype", "myEncoding", "newEncoding");
    }

    @Test
    @Alerts({"get", "post", "post"})
    public void methodProperty() {
        doTestProperty("method", "method", "get", "post");
    }

    @Test
    @Alerts({"_top", "_parent", "_parent"})
    public void targetProperty() {
        doTestProperty("target", "target", "_top", "_parent");
    }

    private void doTestProperty(final String jsProperty, final String htmlProperty,
                                final String oldValue, final String newValue) {

        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  alert(document.forms[0]." + jsProperty + ");\n"
                + "  try {\n"
                + "    document.forms[0]." + jsProperty + "='" + newValue + "';\n"
                + "    alert(document.forms[0]." + jsProperty + ");\n"
                + "    alert(document.forms[0].getAttribute('" + htmlProperty + "'));\n"
                + "  } catch(e) { alert('exception'); }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form " + htmlProperty + "='" + oldValue + "'>\n"
                + "  <input type='button' name='button1' />\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"id2", "foo"})
    public void inputNamedId() {
        doTestInputWithName("id");
    }

    @Test
    @Alerts({"action2", "foo"})
    public void inputNamedAction() {
        doTestInputWithName("action");
    }

    private void doTestInputWithName(final String name) {
        final String html
                = "<html><head><script>\n"

                + "function go() {\n"
                + "  alert(document.simple_form." + name + ".value);\n"
                + "  document.simple_form." + name + ".value = 'foo';\n"
                + "  alert(document.simple_form." + name + ".value);\n"
                + "}</script></head>\n"
                + "<body onload='go()'>\n"
                + "<p>hello world</p>\n"
                + "<form action='login.jsp' name='simple_form'>\n"
                + "  <input name='" + name + "' type='hidden' value='" + name + "2'>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("value = 2")
    public void accessingRadioButtonArrayByNameRegression() {
        final String html
                = "<html><head></head>\n"
                + "<body><form name='whatsnew'>\n"
                + "<input type='radio' name='second' value='1'>\n"
                + "<input type='radio' name='second' value='2' checked>\n"
                + "</form><script>\n"

                + "clickAction();\n"
                + "function clickAction() {\n"
                + "  var value = -1;\n"
                + "  radios = document.forms['whatsnew'].elements['second'];\n"
                + "  for (var i = 0; i < radios.length; i++){\n"
                + "    if (radios[i].checked == true) {\n"
                + "      value = radios[i].value;\n"
                + "      break;\n"
                + "    }\n"
                + "  }\n"
                + "  alert('value = ' + value);\n"
                + "}\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("foo")
    public void findInputWithoutTypeDefined() {
        final String html
                = "<html><head>\n"
                + "<script>" + "</script>\n"
                + "</head>\n"
                + "<body onload='alert(document.simple_form.login.value);'>\n"
                + "<p>hello world</p><table><tr><td>\n"
                + "<form action='login.jsp' name='simple_form'>\n"
                + "  <input name='msg' type='hidden' value='0'>\n"
                + "  <script>document.simple_form.msg.value = 1</script>\n"
                + "  <input name='login' size='17' value='foo'>\n"
                + "</form></td></tr></table>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("2")
    public void length() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  alert(document.form1.length);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form name='form1'>\n"
                + "  <input type='radio' name='radio1' value='1'/>\n"
                + "  <input type='image' src='foo' value='1'/>\n"
                + "  <input type='submit' name='submit1' value='1'/>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("button1")
    public void get() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  alert(document.form1[0].name);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='button' name='button1' />\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1", "1", "true"})
    public void elementsLive() {
        final String html = "<html>\n"
                + "<body>\n"
                + "<form name='myForm'>\n"
                + "<script>\n"
                + "var oElements = document.myForm.elements;\n"
                + "alert(oElements.length);\n"
                + "</script>\n"
                + "<input type='text' name='foo'/>\n"
                + "<script>\n"                + "alert(oElements.length);\n"
                + "alert(document.myForm.elements.length);\n"
                + "alert(oElements == document.myForm.elements);\n"
                + "</script>\n"
                + "</form>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("foo.html")
    public void getFormFromFormsById() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>" + "</script>\n"
                        + "</head>\n"
                        + "<body onload=\"alert(document.forms['myForm'].action)\">\n"
                        + "<form id='myForm' action='foo.html'>\n"
                        + "</form>\n"
                        + "</body>\n"
                        + "</html>";


        checkHtmlAlert(html);
    }

    @Test
    @Alerts("")
    public void action() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>" + "</script>\n"
                        + "</head>\n"
                        + "<body onload=\"alert(document.forms['myForm'].action)\">\n"
                        + "<form id='myForm'>\n"
                        + "</form>\n"
                        + "</body>\n"
                        + "</html>";


        checkHtmlAlert(html);
    }

    @Test
    @Alerts("")
    public void actionEmpty() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>" + "</script>\n"
                        + "</head>\n"
                        + "<body onload=\"alert(document.forms['myForm'].action)\">\n"
                        + "<form id='myForm' action=''>\n"
                        + "</form>\n"
                        + "</body>\n"
                        + "</html>";


        checkHtmlAlert(html);
    }

    @Test
    @Alerts("")
    public void actionBlank() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>" + "</script>\n"
                        + "</head>\n"
                        + "<body onload=\"alert(document.forms['myForm'].action)\">\n"
                        + "<form id='myForm' action='  '>\n"
                        + "</form>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("text")
    public void getFieldNamedLikeForm() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>" + "</script>\n"
                        + "</head>\n"
                        + "<body onload='alert(document.login.login.type)'>\n"
                        + "<form name='login' action='foo.html'>\n"
                        + "<input name='login' type='text'>\n"
                        + "</form>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    public void fieldNamedSubmit() {
        fieldNamedSubmit("<input type='text' name='submit'>\n", "INPUT");
        fieldNamedSubmit("<input type='password' name='submit'>\n", "INPUT");
        fieldNamedSubmit("<input type='submit' name='submit'>\n", "INPUT");
        fieldNamedSubmit("<input type='radio' name='submit'>\n", "INPUT");
        fieldNamedSubmit("<input type='checkbox' name='submit'>\n", "INPUT");
        fieldNamedSubmit("<input type='button' name='submit'>\n", "INPUT");
        fieldNamedSubmit("<button type='submit' name='submit'>\n", "BUTTON");
        fieldNamedSubmit("<textarea name='submit'></textarea>\n", "TEXTAREA");
        fieldNamedSubmit("<select name='submit'></select>\n", "SELECT");
        fieldNamedSubmit("<input type='image' name='submit'>\n", "function");
        fieldNamedSubmit("<input type='IMAGE' name='submit'>\n", "function");
    }

    private void fieldNamedSubmit(final String htmlSnippet, final String expected) {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "function test() {\n"
                        + "  if (document.login.submit.tagName)\n"
                        + "    alert(document.login.submit.tagName);\n"
                        + "  else"
                        + "    alert('function');\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<form name='login' action='foo.html'>\n"
                        + htmlSnippet
                        + "</form>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"before", "2", "undefined"})
    public void fieldFoundWithId() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  alert(IRForm.IRText.value);\n"
                + "  alert(IRForm.myField.length);\n"
                + "  alert(IRForm.myDiv);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form name='IRForm' action='#'>\n"
                + "    <input type='text' id='IRText' value='before'/>\n"
                + "    <input type='image' name='myField' src='foo.gif'/>\n"
                + "    <input type='image' id='myField' src='foo.gif'/>\n"
                + "    <input type='text' name='myField'/>\n"
                + "    <input type='text' id='myField'/>\n"
                + "    <div id='myDiv'>oooo</div>\n"
                + "  </form>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLInputElement]", "[object HTMLInputElement]"})
    public void fieldFoundWithIdByReference() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  alert(IRForm.IRText);\n"
                + "  alert(IRForm.myField);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form name='IRForm' id='testForm' action='#'>\n"
                + "  </form>\n"
                + "  <input type='text' id='IRText' value='abc' form='testForm' />\n"
                + "  <input type='text' id='myField' value='xy' form='testForm'/>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"INPUT", "idImg1", "img2", "true"})
    public void nonFieldChildFound() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var oForm = document.testForm;\n"
                + "  alert(oForm.img.tagName);\n"
                + "  alert(oForm.img1.id);\n"
                + "  alert(oForm.img2.id);\n"
                + "  alert(oForm.testSpan == undefined);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form name='testForm' action='foo'>\n"
                + "    <input type='text' id='img' value='before'/>\n"
                + "    <img name='img' id='idImg' src='foo.png'/>\n"
                + "    <img name='img1' id='idImg1' src='foo.png'/>\n"
                + "    <img id='img2' src='foo.png'/>\n"
                + "    <span id='testSpan'>foo</span>\n"
                + "  </form>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLImageElement]", "[object HTMLImageElement]"})
    public void findImageWhenNotDirectChild() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  alert(document.fmLogin.myImgName);\n"
                + "  alert(document.fmLogin.myImgId);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form name='fmLogin' action='doLogin' method='POST'>\n"
                + "    <div>\n"
                + "      <img name='myImgName' src='somewhere'>\n"
                + "      <img id='myImgId' src='somewhere'>\n"
                + "    <div/>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"page 1: formPage1", "page 2: formPage2"})
    public void formAccessAfterBrowsing() {
        final String html = "<html><head>\n"
                + "<script>\n"                + "function test() {\n"
                + "  window.name = 'page 1: ' + document.forms[0].name;\n"
                + "  document.location = 'page2.html';\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "<form name='formPage1' action='foo'>\n"
                + "</form>\n"
                + "</body></html>";
        final String secondContent = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  alert(window.name);\n"
                + "  alert('page 2: ' + document.forms[0].name);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "<form name='formPage2' action='foo'>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"function handler() {}", "null", "null"})
    public void onsubmitNull() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "  function handler() {}\n"
                        + "  function test() {\n"
                        + "    var form = document.getElementById('myForm');\n"
                        + "    form.onsubmit = handler;\n"
                        + "    alert(String(form.onsubmit).replace(/\\n/g, ''));\n"
                        + "    form.onsubmit = null;\n"
                        + "    alert(form.onsubmit);\n"
                        + "    try {\n"
                        + "      form.onsubmit = undefined;\n"
                        + "      alert(form.onsubmit);\n"
                        + "    } catch(e) { alert('exception'); }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "<body onload=test()>\n"
                        + "  <form id='myForm'></form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void item() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      try {\n"
                + "        alert(document.forms['myForm'].item('myRadio').type);\n"
                + "      } catch(e) { alert('exception') }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form action='page1.html' name='myForm'>\n"
                + "    <input type='radio' name='myRadio'>\n"
                + "  </form>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void itemMany() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      try {\n"
                + "        alert(document.forms['myForm'].item('myRadio').length);\n"
                + "      } catch(e) { alert('exception') }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form action='page1.html' name='myForm'>\n"
                + "    <input type='radio' name='myRadio'>\n"
                + "    <input type='radio' name='myRadio'>\n"
                + "  </form>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void itemManysubindex() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      try {\n"
                + "        alert(document.forms['myForm'].item('myRadio', 1).id);\n"
                + "      } catch(e) { alert('exception') }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form action='page1.html' name='myForm'>\n"
                + "    <input type='radio' name='myRadio' id='radio1'>\n"
                + "    <input type='radio' name='myRadio' id='radio2'>\n"
                + "  </form>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void item_integer() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      try {\n"
                + "        alert(document.forms['myForm'].item(1).id);\n"
                + "      } catch(e) { alert('exception') }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form action='page1.html' name='myForm'>\n"
                + "    <input type='radio' name='myRadio' id='radio1'>\n"
                + "    <input type='radio' name='myRadio' id='radio2'>\n"
                + "  </form>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("application/x-www-form-urlencoded")
    public void enctypeDefaultValueHtml5() {
        final String html = "<html>\n"
                + "<html><body><script>\n"

                + "alert(document.createElement('form').enctype);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("application/x-www-form-urlencoded")
    public void enctypeDefaultValue() {
        final String html = "<html><body><script>\n"
                + "alert(document.createElement('form').enctype);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLInputElement]", "undefined",
            "[object HTMLInputElement]", "[object HTMLInputElement]",
            "[object HTMLInputElement]", "[object HTMLInputElement]", "[object HTMLInputElement]"})
    public void accessByNameAfterNameChange() {
        final String html
                = "<html><head><script>\n"

                + "function go() {\n"
                + "  var field = document.simple_form.originalName;\n"
                + "  alert(document.simple_form.originalName);\n"
                + "  alert(document.simple_form.newName);\n"

                + "  field.name = 'newName';\n"
                + "  alert(document.simple_form.originalName);\n"
                + "  alert(document.simple_form.newName);\n"

                + "  field.name = 'brandNewName';\n"
                + "  alert(document.simple_form.originalName);\n"
                + "  alert(document.simple_form.newName);\n"
                + "  alert(document.simple_form.brandNewName);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='go()'>\n"
                + "<form name='simple_form'>\n"
                + "  <input name='originalName'>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"[object HTMLInputElement]", "[object HTMLInputElement]"})
    public void lostChildrenFromElements() {
        final String html
                = "<html><body>\n"
                + "<div><form name='form1' >\n"
                + "</div>\n"
                + "<input name='b'/>\n"
                + "</form><script>\n"

                + "  alert(document.form1['b']);\n"
                + "  alert(document.form1.elements['b']);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("function")
    public void onchangeHandler() {
        final String html
                = "<html><head><script>\n"

                + "function test() {\n"
                + "  var form = document.getElementsByTagName('form')[0];\n"
                + "  alert(typeof form.onchange);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "<form onchange='cat=true'></form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"type: submit", "submitter: [object HTMLInputElement]",
            "srcElement null: false", "srcElement==form: true",
            "target null: false", "target==form: true"})
    public void onSubmitEvent() {
        final String html = "<html><head>\n"
                + "<script>\n"                + "function test(_event) {\n"
                + "  var oEvent = _event ? _event : window.event;\n"
                + "  alert('type: ' + oEvent.type);\n"
                + "  alert('submitter: ' + oEvent.submitter);\n"
                + "  alert('srcElement null: ' + (oEvent.srcElement == null));\n"
                + "  alert('srcElement==form: ' + (oEvent.srcElement == document.forms[0]));\n"
                + "  alert('target null: ' + (oEvent.target == null));\n"
                + "  alert('target==form: ' + (oEvent.target == document.forms[0]));\n"

                + "  if (_event.preventDefault) { _event.preventDefault(); }\n"
                + "  return false;\n"
                + "}\n"
                + "</script>\n"
                + "</head><body>\n"
                + "<form name='formPage1' action='about:blank' onsubmit='return test(event);'>\n"
                + "<input type='submit' id='theButton'>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"prepare frame", "submit form", "submitted ok"})
    public void submitWithTargetOnIFrameAndOnloadscript() {
        final String html
                = "<html><head></head><body>\n"
                + "<p>hello world</p>\n"
                + "<form id='form1' name='form1' method='get' action='" + URL_SECOND + "'>\n"
                + "  <input type='button' name='button1' />\n"
                + "</form>\n"
                + "<script>\n"                + "  // Prepare the iframe for the target\n"
                + "  alert('prepare frame');\n"
                + "  var div = document.createElement('div');\n"
                + "  div.style.display = 'none';\n"
                + "  div.innerHTML = \"<iframe name='frame' id='frame'></iframe>\";\n"
                + "  document.body.appendChild(div);\n"
                + "  // Get the form and set the target\n"
                + "  var form = document.getElementById('form1');\n"
                + "  form.target = 'frame';\n"
                + "  // Finally submit the form with a delay to make sure that the onload of the iframe\n"
                + "  // is called for the submit and not for the page creation\n"
                + "  var t = setTimeout(function() {\n"
                + "    clearTimeout(t);\n"
                + "    var iframe = document.getElementById('frame');\n"
                + "    iframe.onload = function() {\n"
                + "      alert('submitted ' + iframe.contentWindow.document.body.getAttribute('id'));\n"
                + "    };\n"
                + "    alert('submit form');\n"
                + "    form.submit();\n"
                + "  }, 1000);\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"submit form", "listener: submitted ok"})
    public void submitWithTargetOnIFrameAndOnloadBubbling() {
        final String html
                = "<html><head>/head><body>\n"
                + "<p>hello world</p>\n"
                + "<form id='form1' name='form1' method='get' action='" + URL_SECOND + "' target='frame'>\n"
                + "  <input type='button' name='button1' />\n"
                + "</form>\n"
                + "<div style='display:none;'><iframe name='frame' id='frame'></iframe></div>\n"
                + "<script>\n"
                + "  // Get the form and set the target\n"
                + "  var form = document.getElementById('form1');\n"
                + "  var iframe = document.getElementById('frame');\n"
                + "  // Finally submit the form with a delay to make sure that the onload of the iframe\n"
                + "  // is called for the submit and not for the page creation\n"
                + "  var t = setTimeout(function() {\n"
                + "    clearTimeout(t);\n"
                + "    iframe.addEventListener('load', function() {\n"
                + "      alert('listener: submitted ' + iframe.contentWindow.document.body.getAttribute('id'));\n"
                + "    }, true);\n"
                + "    alert('submit form');\n"
                + "    form.submit();\n"
                + "  }, 1000);\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"first", "requestSubmit failed"})
    public void requestSubmitNotMember() {
        final String html
                = "<html>\n"
                + "<head><title>first</title>\n"
                + "<script>\n"                + "function doTest() {\n"
                + "  var myForm = document.getElementById('form1');\n"
                + "  if (myForm.requestSubmit) {\n"
                + "    var sub = document.getElementById('submit2');\n"
                + "    try {\n"
                + "      myForm.requestSubmit(sub);\n"
                + "    } catch (e) { alert('requestSubmit failed'); }\n"
                + "    return;\n"
                + "  }\n"
                + "  alert('requestSubmit() not available');\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"

                + "<body onload='doTest()'>\n"
                + "  <form id='form1' name='form1' method='get' action='" + URL_SECOND + "' encoding='text/plain'>\n"
                + "    <input name='param1' type='hidden' value='value1'>\n"
                + "  </form>\n"

                + "  <form id='form2' name='form2' method='get' action='" + URL_SECOND + "' encoding='text/plain'>\n"
                + "    <input type='submit' id='submit2' />\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"first", "requestSubmit failed"})
    public void requestSubmitNotSubmit() {
        final String html
                = "<html>\n"
                + "<head><title>first</title>\n"
                + "<script>\n"                + "function doTest() {\n"
                + "  var myForm = document.getElementById('form1');\n"
                + "  if (myForm.requestSubmit) {\n"
                + "    var sub = document.getElementById('param1');\n"
                + "    try {\n"
                + "      myForm.requestSubmit(sub);\n"
                + "    } catch (e) { alert('requestSubmit failed'); }\n"
                + "    return;\n"
                + "  }\n"
                + "  alert('requestSubmit() not available');\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"

                + "<body onload='doTest()'>\n"
                + "  <form id='form1' name='form1' method='get' action='" + URL_SECOND + "' encoding='text/plain'>\n"
                + "    <input id='param1' name='param1' type='hidden' value='value1'>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "false", "true", "false", "false"})
    public void in() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var f = document.testForm;\n"
                + "  alert(f.length);\n"
                + "  alert(-1 in f);\n"
                + "  alert(0 in f);\n"
                + "  alert(1 in f);\n"
                + "  alert(42 in f);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form name='testForm' action='about:blank'>\n"
                + "    <input type='submit' id='theButton'>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "alternate help", "prefetch", "prefetch", "not supported", "notsupported"})
    public void readWriteRel() {
        final String html
                = "<html><body><form id='f1'>a1</form><form id='f2' rel='alternate help'>a2</form><script>\n"

                + "var a1 = document.getElementById('f1'), a2 = document.getElementById('f2');\n"

                + "alert(a1.rel);\n"
                + "alert(a2.rel);\n"

                + "a1.rel = 'prefetch';\n"
                + "a2.rel = 'prefetch';\n"
                + "alert(a1.rel);\n"
                + "alert(a2.rel);\n"

                + "a1.rel = 'not supported';\n"
                + "a2.rel = 'notsupported';\n"
                + "alert(a1.rel);\n"
                + "alert(a2.rel);\n"

                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "alternate", "help"})
    public void relList() {
        final String html
                = "<html><body><form id='f1'>a1</form><form id='f2' rel='alternate help'>a2</form><script>\n"

                + "var a1 = document.getElementById('f1'), a2 = document.getElementById('f2');\n"

                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"

                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"
                + "} catch(e) { alert('exception'); }\n"

                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "2", "1", "alternate", "help", "abc", "alternate help", "abc"})
    public void setRelListString() {
        final String html
                = "<html><body><form id='f1'>a1</form><form id='f2' rel='alternate help'>a2</form><script>\n"

                + "var a1 = document.getElementById('f1'), a2 = document.getElementById('f2');\n"

                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"

                + "  a1.relList = 'alternate help';\n"
                + "  a2.relList = 'abc';\n"

                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"

                + "  for (var i = 0; i < a1.relList.length; i++) {\n"
                + "    alert(a1.relList[i]);\n"
                + "  }\n"

                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"

                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"

                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "0", "0", "", "\\s\\s\\t"})
    public void setRelListStringBlank() {
        final String html
                = "<html><body><form id='f1'>a1</form><form id='f2' rel='alternate help'>a2</form><script>\n"

                + "var a1 = document.getElementById('f1'), a2 = document.getElementById('f2');\n"

                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"

                + "  a1.relList = '';\n"
                + "  a2.relList = '  \t';\n"

                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"

                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"

                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "1", "1", "null", "null", "null", "null"})
    public void setRelListNull() {
        final String html
                = "<html><body><form id='f1'>a1</form><form id='f2' rel='alternate help'>a2</form><script>\n"

                + "var a1 = document.getElementById('f1'), a2 = document.getElementById('f2');\n"

                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"

                + "  a1.relList = null;\n"
                + "  a2.relList = null;\n"

                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"

                + "  for (var i = 0; i < a1.relList.length; i++) {\n"
                + "    alert(a1.relList[i]);\n"
                + "  }\n"

                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"

                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"

                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "1", "1", "undefined", "undefined", "undefined", "undefined"})
    public void setRelListUndefined() {
        final String html
                = "<html><body><form id='f1'>a1</form><form id='f2' rel='alternate help'>a2</form><script>\n"

                + "var a1 = document.getElementById('f1'), a2 = document.getElementById('f2');\n"

                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"

                + "  a1.relList = undefined;\n"
                + "  a2.relList = undefined;\n"

                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"

                + "  for (var i = 0; i < a1.relList.length; i++) {\n"
                + "    alert(a1.relList[i]);\n"
                + "  }\n"

                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"

                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"

                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLInputElement]", "[object HTMLInputElement]"})
    public void elementsForOf() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var f = document.getElementById('testForm');\n"
                        + "    for (var attr of f) {\n"
                        + "      alert(attr);\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <form name='testForm' id='testForm'>\n"
                        + "    <input type='submit' id='submit'>\n"
                        + "    <input type='text' name='test' value=''>"
                        + "  </form>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
