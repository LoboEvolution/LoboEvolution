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
import org.loboevolution.html.dom.HTMLScriptElement;

/**
 * Unit tests for {@link HTMLScriptElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLScriptElementTest extends LoboUnitTest {

    @Test
    @Alerts({"1", "2", "3", "4", "onload"})
    public void onReadyStateChangeHandler() {
        final String html = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "      function test() {\n"
                + "        var script = document.createElement('script');\n"
                + "        script.id = 'b';\n"
                + "        script.type = 'text/javascript';\n"
                + "        script.onreadystatechange = null;\n"
                + "        script.onreadystatechange = function() {\n"
                + "          alert('onreadystatechange ' + script.readyState);\n"
                + "        }\n"
                + "        script.onload = function() {\n"
                + "          alert('onload');\n"
                + "        }\n"
                + "        alert('1');\n"
                + "        script.src = 'script.js';\n"
                + "        alert('2');\n"
                + "        document.getElementsByTagName('head')[0].appendChild(script);\n"
                + "        alert('3');\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='test()'>\n"
                + "  </body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"foo.js", "foo.js", "", ""})
    public void srcPropertyShouldBeAFullUrl() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      var script = document.getElementById('my');\n"
                        + "      alert(script.src);\n"
                        + "      alert(script.getAttribute('src'));\n"

                        + "      var script2 = document.getElementById('my2');\n"
                        + "      alert(script2.src);\n"
                        + "      alert(script2.getAttribute('src'));\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <script id='my' src='foo.js'></script>\n"
                        + "  <script id='my2' src=''></script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "null", "", "null"})
    public void srcPropertyNoSource() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      var script = document.getElementById('my');\n"
                        + "      alert(script.src);\n"
                        + "      alert(script.getAttribute('src'));\n"

                        + "      var script2 = document.createElement('script');\n"
                        + "      alert(script2.src);\n"
                        + "      alert(script2.getAttribute('src'));\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <script id='my'></script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void srcWithJavaScriptProtocol_Dynamic() {
        final String html =
                "<html><head><script>\n"

                        + "  function test() {\n"
                        + "    var script=document.createElement('script');\n"
                        + "    script.src = \"javascript: 'alert(1)'\";\n"
                        + "    document.getElementsByTagName('head')[0].appendChild(script);\n"
                        + "  }\n"
                        + "</script></head><body onload='test()'>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"start", "end"})
    public void reexecuteModifiedScript() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.getElementsByTagName('script')[0];\n"
                        + "  script.text = \"alert('executed');\";\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"hello", "hello", "world", "-"})
    public void scriptInCdataXHtml() {
        final String html =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<!DOCTYPE html PUBLIC \n"
                        + "  \"-//W3C//DTD XHTML 1.0 Strict//EN\" \n"
                        + "  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                        + "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:xhtml='http://www.w3.org/1999/xhtml'>\n"
                        + "<head></head>\n"
                        + "<body>\n"
                        + "    <script>\n"
                        + "</script>\n"
                        + "  <script>\n"
                        + "    //<![CDATA[\n"
                        + "    alert('hello');\n"
                        + "    //]]>\n"
                        + "  </script>\n"
                        + "  <script>\n"
                        + "    /*<![CDATA[*/alert('hello');/*]]>*/\n"
                        + "  </script>\n"
                        + "  <script>\n"
                        + "    <![CDATA[\n"
                        + "    alert('world');\n"
                        + "    ]]>\n"
                        + "  </script>\n"
                        + "  <script>alert('-');</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"hello", "hello", "world", "-"})
    public void scriptInCdataXml() {
        final String html =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<!DOCTYPE html PUBLIC \n"
                        + "  \"-//W3C//DTD XHTML 1.0 Strict//EN\" \n"
                        + "  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                        + "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:xhtml='http://www.w3.org/1999/xhtml'>\n"
                        + "<head></head>\n"
                        + "<body>\n"
                        + "    <script>\n"
                        + "</script>\n"
                        + "  <script>\n"
                        + "    //<![CDATA[\n"
                        + "    alert('hello');\n"
                        + "    //]]>\n"
                        + "  </script>\n"
                        + "  <script>\n"
                        + "    /*<![CDATA[*/alert('hello');/*]]>*/\n"
                        + "  </script>\n"
                        + "  <script>\n"
                        + "    <![CDATA[\n"
                        + "    alert('world');\n"
                        + "    ]]>\n"
                        + "  </script>\n"
                        + "  <script>alert('-');</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"hello", "hello", "-"})
    public void scriptInCdataHtml() {
        final String html =
                "<html>\n"
                        + "<head></head>\n"
                        + "<body>\n"
                        + "    <script>\n"
                        + "</script>\n"
                        + "  <script>\n"
                        + "    //<![CDATA[\n"
                        + "    alert('hello');\n"
                        + "    //]]>\n"
                        + "  </script>\n"
                        + "  <script>\n"
                        + "    /*<![CDATA[*/alert('hello');/*]]>*/\n"
                        + "  </script>\n"
                        + "  <script>\n"
                        + "    <![CDATA[\n"
                        + "    alert('world');\n"
                        + "    ]]>\n"
                        + "  </script>\n"
                        + "  <script>alert('-');</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"start", "end"})
    public void createElementWithCreateTextNode() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.createElement('script');\n"
                        + "  var source = document.createTextNode(\"alert('executed');\");\n"
                        + "  try {\n"
                        + "    script.appendChild(source);\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"start", "middle", "executed", "end"})
    public void createElementWithCreateTextNodeAndAppend() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.createElement('script');\n"
                        + "  var source = document.createTextNode(\"alert('executed');\");\n"
                        + "  try {\n"
                        + "    script.appendChild(source);\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "  alert('middle');\n"
                        + "  document.body.appendChild(script);\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"start", "end"})
    public void createElementWithSetText() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.createElement('script');\n"
                        + "  script.text = \"alert('executed');\";\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"start", "middle", "executed", "end"})
    public void createElementWithSetTextAndAppend() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.createElement('script');\n"
                        + "  script.text = \"alert('executed');\";\n"
                        + "  alert('middle');\n"
                        + "  document.body.appendChild(script);\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"start", "end"})
    public void createElementWithSetSrc() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.createElement('script');\n"
                        + "  script.src = \"" + URL_SECOND + "\";\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"start", "middle", "end", "executed"})
    public void createElementWithSetSrcAndAppend() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.createElement('script');\n"
                        + "  script.src = \"" + URL_SECOND + "\";\n"
                        + "  alert('middle');\n"
                        + "  document.body.appendChild(script);\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Replaces the source of the current script element using <code>createTextNode</code> and <code>appendChild</code>.
     */
    @Test
    @Alerts({"start", "end"})
    public void replaceSelfWithCreateTextNode() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.getElementsByTagName('script')[0];\n"
                        + "  var source = document.createTextNode(\"alert('executed');\");\n"
                        + "  try {\n"
                        + "    script.appendChild(source);\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Replaces the source of the current script element using <code>.text</code>.
     */
    @Test
    @Alerts({"start", "end"})
    public void replaceSelfWithSetText() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.getElementsByTagName('script')[0];\n"
                        + "  var source = document.createTextNode(\"alert('executed');\");\n"
                        + "  script.text = \"alert('executed');\";\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Replaces the source of the current script element using <code>.src</code>.
     */
    @Test
    @Alerts({"start", "end"})
    public void replaceSelfWithSetSrc() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.getElementsByTagName('script')[0];\n"
                        + "  var source = document.createTextNode(\"alert('executed');\");\n"
                        + "  script.src = \"" + URL_SECOND + "\";\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Replaces the empty source of another script element using <code>createTextNode</code> and <code>appendChild</code>.
     */
    @Test
    @Alerts({"start", "executed", "end"})
    public void replaceWithCreateTextNodeEmpty() {
        final String html =
                "<html><head></head><body>\n"
                        + "<script id='js1'></script>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.getElementById('js1');\n"
                        + "  var source = document.createTextNode(\"alert('executed');\");\n"
                        + "  try {\n"
                        + "    script.appendChild(source);\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Replaces the source containing just a blank of another script element using <code>createTextNode</code> and <code>appendChild</code>.
     */
    @Test
    @Alerts({"start", "end"})
    public void replaceWithCreateTextNodeBlank() {
        final String html =
                "<html><head></head><body>\n"
                        + "<script id='js1'> </script>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.getElementById('js1');\n"
                        + "  var source = document.createTextNode(\"alert('executed');\");\n"
                        + "  try {\n"
                        + "    script.appendChild(source);\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Replaces the source containing a script of another script element using <code>createTextNode</code> and <code>appendChild</code>.
     */
    @Test
    @Alerts({"script", "start", "end"})
    public void replaceWithCreateTextNodeScript() {
        final String html =
                "<html><head></head><body>\n"
                        + "<script id='js1'>\n"

                        + "  alert('script');\n"
                        + "</script>\n"
                        + "    <script>\n" 
                + "  alert('start');\n"
                        + "  var script = document.getElementById('js1');\n"
                        + "  var source = document.createTextNode(\"alert('executed');\");\n"
                        + "  try {\n"
                        + "    script.appendChild(source);\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Replaces the empty source of another script element using <code>.text</code>.
     */
    @Test
    @Alerts({"start", "executed", "end"})
    public void replaceWithSetTextEmpty() {
        final String html =
                "<html><head></head><body>\n"
                        + "<script id='js1'></script>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.getElementById('js1');\n"
                        + "  script.text = \"alert('executed');\";\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Replaces the source containing just a blank of another script element using <code>.text</code>.
     */
    @Test
    @Alerts({"start", "end"})
    public void replaceWithSetTextBlank() {
        final String html =
                "<html><head></head><body>\n"
                        + "<script id='js1'> </script>\n"
                        + "    <script>\n"
                        + "  alert('start');\n"
                        + "  var script = document.getElementById('js1');\n"
                        + "  script.text = \"alert('executed');\";\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Replaces the source containing a script of another script element using <code>.text</code>.
     */
    @Test
    @Alerts({"script", "start", "end"})
    public void replaceWithSetTextScript() {
        final String html =
                "<html><head></head><body>\n"
                        + "<script id='js1'>\n"

                        + "  alert('script');\n"
                        + "</script>\n"
                        + "    <script>\n" 
                + "  alert('start');\n"
                        + "  var script = document.getElementById('js1');\n"
                        + "  script.text = \"alert('executed');\";\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Replaces the empty source of another script element using <code>.src</code>.
     */
    @Test
    @Alerts({"start", "end", "executed"})
    public void replaceWithSetSrcEmpty() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "</script>\n"
                        + "<script id='js1'></script>\n"
                        + "    <script>\n" 
                + "  alert('start');\n"
                        + "  var script = document.getElementById('js1');\n"
                        + "  script.src = \"" + URL_SECOND + "\";\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    /**
     * Replaces the source containing just a blank of another script element using <code>.src</code>.
     */
    @Test
    @Alerts({"start", "end"})
    public void replaceWithSetSrcBlank() {
        final String html =
                "<html><head></head><body>\n"
                        + "<script id='js1'> </script>\n"
                        + "    <script>\n" 
                + "  alert('start');\n"
                        + "  var script = document.getElementById('js1');\n"
                        + "  script.src = \"" + URL_SECOND + "\";\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Replaces the source containing a script of another script element using <code>.text</code>.
     */
    @Test
    @Alerts({"script", "start", "end"})
    public void replaceWithSetSrcScript() {
        final String html =
                "<html><head></head><body>\n"
                        + "<script id='js1'>\n"
                        + "  alert('script');\n"
                        + "</script>\n"
                        + "    <script>\n" 
                + "  alert('start');\n"
                        + "  var script = document.getElementById('js1');\n"
                        + "  script.src = \"" + URL_SECOND + "\";\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    /**
     * Moves a script element from a div element to the body element using <code>appendChild</code>.
     */
    @Test
    @Alerts({"executed", "start", "end"})
    public void moveWithAppend() {
        final String html =
                "<html><head></head><body>\n"
                        + "<div>\n"
                        + "    <script>\n"
                        + "</script>\n"
                        + "<script id='js1'>alert('executed');</script>\n"
                        + "    <script>\n" 
                + "  alert('start');\n"
                        + "  var script = document.getElementById('js1');\n"
                        + "  document.body.appendChild(script);\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</div>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Moves a script element from a div element to the body element using <code>insertBefore</code>.
     */
    @Test
    @Alerts({"executed", "start", "end"})
    public void moveWithInsert() {
        final String html =
                "<html><head></head><body>\n"
                        + "<div>\n"
                        + "<script id='js1'>\n"

                        + "  alert('executed');\n"
                        + "</script>\n"
                        + "    <script>\n" 
                + "  alert('start');\n"
                        + "  var script = document.getElementById('js1');\n"
                        + "  document.body.insertBefore(script, null);\n"
                        + "  alert('end');\n"
                        + "</script>\n"
                        + "</div>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"script-for", "exception", "script-body"})
    public void scriptForEvent() {
        // IE accepts it with () or without
        scriptForEvent("onload");
        scriptForEvent("onload()");
    }

    private void scriptForEvent(final String eventName) {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "</script>\n"
                + "<script FOR='window' EVENT='" + eventName + "' LANGUAGE='javascript'>\n"
                + "  alert('script-for');\n"
                + "  try {\n"
                + "    document.form1.txt.value = 'hello';\n"
                + "    alert(document.form1.txt.value);\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "</script></head>\n"
                + "<body>\n"
                + "  <form name='form1'><input type='text' name='txt'></form>\n"
                + "  <script>\n"
                + "    alert('script-body');\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Verifies the correct the ordering of script element execution, deferred script element
     * execution, script ready state changes, deferred script ready state changes, and onload
     * handlers.
     */
    @Test
    @Alerts({"3", "4", "2", "5"})
    public void onReadyStateChange_Order() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "    </script>\n"
                        + "    <script defer=''>alert('3');</script>\n"
                        + "    <script defer onreadystatechange='if(this.readyState==\"complete\") alert(\"6\");'>alert('4');</script>\n"
                        + "    <script src='//:' onreadystatechange='if(this.readyState==\"complete\") alert(\"1\");'></script>\n"
                        + "    <script defer='' src='//:' onreadystatechange='if(this.readyState==\"complete\") alert(\"7\");'></script>\n"
                        + "    <script>alert('2')</script>\n"
                        + "  </head>\n"
                        + "  <body onload='alert(5)'></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    public void onReadyStateChange_EventAvailable() {
        final String html =
                "<html><body><script>\n"
                        + "var s = document.createElement('script');\n"
                        + "s.src = '//:';\n"
                        + "s.onreadystatechange = function() {alert(window.event);};\n"
                        + "document.body.appendChild(s);\n"
                        + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "4", "2"})
    public void onReadyStateChangeOrderNoBody() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "    </script>\n"
                        + "    <script defer=''>alert('3');</script>\n"
                        + "    <script defer='' onreadystatechange='if(this.readyState==\"complete\") alert(\"5\");'>alert('4');</script>\n"
                        + "    <script src='//:' onreadystatechange='if(this.readyState==\"complete\") alert(\"1\");'></script>\n"
                        + "    <script defer='' src='//:' onreadystatechange='if(this.readyState==\"complete\") alert(\"6\");'></script>\n"
                        + "    <script>alert('2')</script>\n"
                        + "  </head>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void text() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        execMe('alert(1)');\n"
                        + "      }\n"
                        + "      function execMe(text) {\n"
                        + "        document.head = document.getElementsByTagName('head')[0];\n"
                        + "        var script = document.createElement('script');\n"
                        + "        script.text = text;\n"
                        + "        document.head.appendChild(script);\n"
                        + "        document.head.removeChild(script);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("onload")
    public void onloadAfterDeferReadStateComplete() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "    </script>\n"
                        + "    <script onreadystatechange='if(this.readyState==\"complete\") alert(\"defer\");' defer></script>\n"
                        + "  </head>\n"
                        + "  <body onload='alert(\"onload\")'>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    /**
     * Regression test for bug 47038.
     * <a href="http://sourceforge.net/tracker/?func=detail&atid=448266&aid=3403860&group_id=47038">issue</a>
     */
    @Test
    @Alerts({"1", "2", "3"})
    public void scriptType() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "    <script>\n"
                + "</script>\n"
                + "<script type='text/javascript'>alert(1)</script>\n"
                + "<script type=' text/javascript'>alert(2)</script>\n"
                + "<script type=' text/javascript '>alert(3)</script>\n"
                + "<script type=' text / javascript '>alert(4)</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<ul>{{for people}}<li>Name: {{:name}}</li>{{/for}}</ul>\\n")
    public void specialScriptType() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "</script>\n"
                + "<script id='template' type='text/x-jsrender'>\n"
                + "  <ul>{{for people}}\n"
                + "    <li>Name: {{:name}}</li>\n"
                + "  {{/for}}</ul>\n"
                + "</script>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  script = document.getElementById('template');\n"
                + "  alert(script.innerHTML);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Test exception throw by IE when calling <code>appendChild</code>.
     */
    @Test
    public void appendChild_UnexpectedCall() {
        final String html =
                "<html><head></head><body>\n"
                        + "    <script>\n"
                        + "  var script = document.createElement('script');\n"
                        + "  var source = document.createTextNode(\"alert('executed');\");\n"
                        + "  try {\n"
                        + "    script.appendChild(source);\n"
                        + "  } catch(e) {\n"
                        + "    alert(e.message.slice(0,44));\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }


    /**
     * Firefox should not run scripts with "event" and "for" attributes.
     */
    @Test
    @Alerts("onload for window")
    public void scriptEventFor() {
        final String html = "<html>\n"
                + "<head>\n"
                + "    <script>\n"
                + "</script>\n"
                + "</head><body>\n"
                + "  <script event='onload' for='window'>\n"
                + "    alert('onload for window');\n"
                + "  </script>\n"
                + "  <div id='div1'>the div 1</div>\n"
                + "  <div id='div2'>the div 2</div>\n"
                + "  <script event='onclick' for='div1'>\n"
                + "    alert('onclick for div1');\n"
                + "  </script>\n"
                + "  <script event='onclick' for='document.all.div2'>\n"
                + "    alert('onclick for div2');\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"function foo() { return a > b}", "function mce() { return a &gt; b}"})
    public void innerHtml() {
        final String html
                = "<html><head>\n"
                + "<script id='script1'>function foo() { return a > b}</script>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  script = document.getElementById('script1');\n"
                + "  alert(script.innerHTML);\n"
                + "  script = document.getElementById('mce');\n"
                + "  alert(script.innerHTML);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                // this is done by TinyMce
                + "<script>document.write('<mce:script id=\"mce\">function mce() { return a > b}</mce:script>');</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<SCRIPT id=\"testScript\">function foo() { return a > b}</SCRIPT>")
    public void innerHTMLGetSet() {
        final String html
                = "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "  <div id='tester'>\n"
                + "    <script id='testScript'>function foo() { return a > b}</script>\n"
                + "  </div>\n"
                + "  <script type='text/javascript'>\n"
                + "    var div = document.getElementById('tester');\n"
                + "    try {\n"
                + "      div.innerHTML = div.innerHTML;\n"
                + "    } catch (e) { alert('exception'); }\n"
                + "    alert(div.innerHTML);\n"
                + "  </script>\n"
                + "</body>\n"
                + "</html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "append", "append done", "from script", "undefined"})
    public void asyncOnLoad() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "</script>\n"
                + "<script>\n"
                + "  var script = document.createElement('script');\n"
                + "  alert(script.readyState);\n"
                + "  script.src = 'js.js';\n"
                + "  script.async = true;\n"
                + "  script.onload = function () {\n"
                + "    alert(this.readyState);\n"
                + "  };\n"
                + "  alert('append');\n"
                + "  document.body.appendChild(script);\n"
                + "  alert('append done');\n"
                + "</script>\n"
                + "</body></html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "true", "", "true", "", "false", "null"})
    public void asyncProperty() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script id='script1' src='js1.js'></script>\n"
                + "<script id='script2' src='js2.js' async></script>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  var script = document.getElementById('script1');\n"
                + "  alert(script.async);\n"
                + "  alert(script.getAttribute('async'));\n"
                + "  script.async = true;\n"
                + "  alert(script.async);\n"
                + "  alert(script.getAttribute('async'));\n"
                + "  script = document.getElementById('script2');\n"
                + "  alert(script.async);\n"
                + "  alert(script.getAttribute('async'));\n"
                + "  script.async = false;\n"
                + "  alert(script.async);\n"
                + "  alert(script.getAttribute('async'));\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "true", "true", "true", "", "true", "true", "false", "null"})
    public void asyncAttribute() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script id='script1' src='js1.js'></script>\n"
                + "<script id='script2' src='js2.js' async></script>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  var script = document.getElementById('script1');\n"
                + "  alert(script.async);\n"
                + "  alert(script.getAttribute('async'));\n"
                + "  script.setAttribute('async', true);\n"
                + "  alert(script.async);\n"
                + "  alert(script.getAttribute('async'));\n"
                + "  script = document.getElementById('script2');\n"
                + "  alert(script.async);\n"
                + "  alert(script.getAttribute('async'));\n"
                + "  script.setAttribute('async', true);\n"
                + "  alert(script.async);\n"
                + "  alert(script.getAttribute('async'));\n"
                + "  script.removeAttribute('async');\n"
                + "  alert(script.async);\n"
                + "  alert(script.getAttribute('async'));\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("inside script.js")
    public void loadScriptDynamicallyAdded() {
        final String html = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "      function test() {\n"
                + "        var script = document.createElement('script');\n"
                + "        script.type = 'text/javascript';\n"
                + "        script.async = true;\n"
                + "        script.src = 'script.js';\n"
                + "        var s = document.getElementsByTagName('script')[0];\n"
                + "        s.parentNode.insertBefore(script, s);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='test()'>\n"
                + "  </body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"change type", "type changed"})
    public void loadScriptDynamicallyAddedUnsupportedType() {
        final String html = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "      function test() {\n"
                + "        var script = document.createElement('script');\n"
                + "        script.type = 'true/text/javascript';\n"
                + "        script.src = 'script.js';\n"
                + "        var s = document.getElementsByTagName('script')[0];\n"
                + "        s.parentNode.insertBefore(script, s);\n"
                + "        alert('change type');\n"
                + "        s.type = 'text/javascript';\n"
                + "        alert('type changed');\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='test()'>\n"
                + "  </body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"out", "<!-- yy --!>alert('out');\\n  "})
    public void incorrectlyClosedComment() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "  </script>\n"
                        + "  <script id='testScript'>\n"
                        + "    <!-- yy --!>\n"
                        + "    alert('out');\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "  <!-- xx -->\n"

                        + "  <script >\n"
                        + "    alert(document.getElementById('testScript').innerHTML);\n"
                        + "  </script>\n"

                        + "</body>\n"
                        + "</html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null-", "testType-testType", "-"})
    public void modifyType() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script id='testScript'></script>\n"
                        + "</head>\n"
                        + "<body>\n"

                        + "  <script >\n"

                        + "    var script = document.getElementById('testScript');\n"
                        + "    alert(script.getAttribute('type') + '-' + script.type);\n"

                        + "    script.type = 'testType';\n"
                        + "    alert(script.getAttribute('type') + '-' + script.type);\n"

                        + "    script.type = '';\n"
                        + "    alert(script.getAttribute('type') + '-' + script.type);\n"

                        + "  </script>\n"

                        + "</body>\n"
                        + "</html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"typeAttr-typeAttr", "null-", "newType-newType", "null-null"})
    public void modifyTypeAttribute() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script id='testScript' type='typeAttr'></script>\n"
                        + "</head>\n"
                        + "<body>\n"

                        + "  <script >\n"

                        + "    var script = document.getElementById('testScript');\n"
                        + "    alert(script.getAttribute('type') + '-' + script.type);\n"

                        + "    script.removeAttribute('type');\n"
                        + "    alert(script.getAttribute('type') + '-' + script.type);\n"

                        + "    script.setAttribute('type', 'newType');\n"
                        + "    alert(script.getAttribute('type') + '-' + script.type);\n"

                        + "    script.setAttribute('type', null);\n"
                        + "    alert(script.getAttribute('type') + '-' + script.type);\n"

                        + "  </script>\n"

                        + "</body>\n"
                        + "</html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"typeAttr", "text/javascript"})
    public void modifyTypeToJs() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "    <script>\n"
                        + "</script>\n"
                        + "  <script id='testScript' type='typeAttr'>alert('exec');</script>\n"
                        + "</head>\n"
                        + "<body>\n"

                        + "  <script >\n"
                        + "    var script = document.getElementById('testScript');\n"
                        + "    alert(script.getAttribute('type'));\n"

                        + "    script.type = 'text/javascript';\n"
                        + "    alert(script.getAttribute('type'));\n"
                        + "  </script>\n"

                        + "</body>\n"
                        + "</html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("onerror")
    public void onErrorHandler() {
        final String html = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "    </script>\n"
                + "    <script src='http://www.unknown-host.xyz' onload='alert(\"onload\")' onerror='alert(\"onerror\")'></script>\n"
                + "  </head>\n"
                + "  <body>\n"
                + "  </body></html>";

        checkHtmlAlert(html);
    }
}
