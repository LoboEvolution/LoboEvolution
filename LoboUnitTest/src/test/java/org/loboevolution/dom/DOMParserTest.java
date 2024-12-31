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
import org.loboevolution.js.DOMParser;

/**
 * Tests for {@link DOMParser}.
 */
@ExtendWith(AlertsExtension.class)
public class DOMParserTest extends LoboUnitTest {


    @Test
    @Alerts("[object DOMParser]")
    public void scriptableToString() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "     alert(new DOMParser());\n"
                + "    } catch (e) {alert('exception');}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLDocument]", "", ""})
    public void parseFromStringTextHtml() {
        final String content = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var text='<html></html>';\n"
                + "    try {\n"
                + "      var parser = new DOMParser();\n"
                + "      var doc = parser.parseFromString(text, 'text/html');\n"
                + "     alert(doc);\n"
                + "     alert(doc.body.innerHTML);\n"
                + "     alert(doc.URL);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(content);
    }

    @Test
    @Alerts({"[object HTMLDocument]", "<div></div>", ""})
    public void parseFromStringTextHtmlDiv() {
        final String content = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<div></div>';\n"
                + "    try {\n"
                + "      var parser = new DOMParser();\n"
                + "      var doc = parser.parseFromString(text, 'text/html');\n"
                + "     alert(doc);\n"
                + "     alert(doc.body.innerHTML);\n"
                + "     alert(doc.URL);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(content);
    }

    @Test
    @Alerts("[object XMLDocument]")
    public void parseFromStringTextXml() {
        final String content = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var text='<note/>';\n"
                + "    try {\n"
                + "      var parser = new DOMParser();\n"
                + "      var doc = parser.parseFromString(text, 'text/xml');\n"
                + "     alert(doc);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(content);
    }

    @Test
    @Alerts("[object XMLDocument]")
    public void parseFromStringApplicationXml() {
        final String content = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<note/>';\n"
                + "    try {\n"
                + "      var parser = new DOMParser();\n"
                + "      var doc = parser.parseFromString(text, 'application/xml');\n"
                + "     alert(doc);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(content);
    }

    @Test
    @Alerts("[object XMLDocument]")
    public void parseFromStringApplicationXhtmlXml() {
        final String content = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<html/>';\n"
                + "    try {\n"
                + "      var parser = new DOMParser();\n"
                + "      var doc = parser.parseFromString(text, 'application/xhtml+xml');\n"
                + "     alert(doc);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(content);
    }

    @Test
    @Alerts("[object XMLDocument]")
    public void parseFromStringApplication_svgXml() {
        final String content = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<svg xmlns=\"http://www.w3.org/2000/svg\"/>';\n"
                + "    try {\n"
                + "      var parser = new DOMParser();\n"
                + "      var doc = parser.parseFromString(text, 'image/svg+xml');\n"
                + "     alert(doc);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(content);
    }

    @Test
    @Alerts("exception")
    public void parseFromString_unknownType() {
        final String content = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<test/>';\n"
                + "    try {\n"
                + "      var parser = new DOMParser();\n"
                + "      var doc = parser.parseFromString(text, 'unknown/type');\n"
                + "     alert(doc);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(content);
    }

    @Test
    @Alerts("9")
    public void parseFromString() {
        final String content = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<note> ';\n"
                + "    text += '<to>Tove</to> ';\n"
                + "    text += '<from>Jani</from> ';\n"
                + "    text += '<heading>Reminder</heading> ';\n"
                + "    text += '<body>Do not forget me this weekend!</body> ';\n"
                + "    text += '</note>';\n"
                + "    try {\n"
                + "      var parser = new DOMParser();\n"
                + "      var doc = parser.parseFromString(text, 'text/xml');\n"
                + "      if (doc.getElementsByTagName('parsererror').length > 0) {alert('parsererror'); return; }\n"
                + "      var x = doc.documentElement;\n"
                + "     alert(x.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(content);
    }


    @Test
    @Alerts("parsererror")
    public void parseFromString_invalidXml() {
        final String content = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text = '</notvalid> ';\n"
                + "    try {\n"
                + "      var parser = new DOMParser();\n"
                + "      var doc = parser.parseFromString(text, 'text/xml');\n"
                + "      if (doc.getElementsByTagName('parsererror').length > 0) {\n"
                + "       alert('parsererror');\n"
                + "        return;\n"
                + "      }\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(content);
    }

    @Test
    @Alerts("parsererror")
    public void parseFromStringEmptyString() {
        final String content = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='';\n"
                + "    try {\n"
                + "      var parser = new DOMParser();\n"
                + "      var doc = parser.parseFromString(text, 'text/xml');\n"
                + "      if (doc.getElementsByTagName('parsererror').length > 0) {\n"
                + "       alert('parsererror');\n"
                + "        return;\n"
                + "      }\n"
                + "     alert(doc.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(content);
    }

    @Test
    @Alerts("exception")
    public void parseFromStringMissingMimeType() {
        final String content = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<root/>';\n"
                + "    try {\n"
                + "      var parser=new DOMParser();\n"
                + "      parser.parseFromString(text);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(content);
    }

    @Test
    @Alerts({"5", "[object CDATASection]", "[object Comment]", "[object Element]",
            "[object ProcessingInstruction]", "[object Text]"})
    public void parseFromStringProcessingInstructionKept() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text = '<elementWithChildren>' + '<![CDATA[sampl<<< >>e data]]>' + '<!--a sample comment-->'\n"
                + "      + '<elementWithChildren/>' + '<?target processing instruction data?>' + 'sample text node'\n"
                + "      + '</elementWithChildren>';\n"
                + "    try {\n"
                + "      var parser = new DOMParser();\n"
                + "      var doc = parser.parseFromString(text, 'text/xml');\n"
                + "      if (doc.getElementsByTagName('parsererror').length > 0) {\n"
                + "       alert('parsererror');\n"
                + "        return;\n"
                + "      }\n"
                + "     alert(doc.documentElement.childNodes.length);\n"
                + "      for(var i = 0; i < doc.documentElement.childNodes.length; i++) {\n"
                + "       alert(doc.documentElement.childNodes[i]);\n"
                + "      }\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLDocument]")
    public void parseFromStringDoNotExecuteScripts() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var html = '<script>document.title = \"parsed script executed\";</' + 'script>';\n"
                + "      var parser = new DOMParser();\n"
                + "     alert(parser.parseFromString(html, 'text/html'));\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLDocument]")
    public void parseFromStringDoNotExecuteSvgScripts() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var html = '<svg viewBox=\"0 0 10 10\" xmlns=\"http://www.w3.org/2000/svg\">'\n"
                + " '<script>document.title = \"parsed script executed\";</' + 'script>'\n"
                + " '</svg>';\n"
                + "      var parser = new DOMParser();\n"
                + "     alert(parser.parseFromString(html, 'text/html'));\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"parsed", "inserted"})
    public void dontExecScriptsFromDOMParser() {
        final String html =
                "<html>\n"
                        + "<head></head>\n"
                        + "<body>\n"
                        + "<div id='tester'><div>"
                        + "<script>\n"
                        + "  var script = \"<div><script>alert('from script');</\" + \"script></div>\"\n"
                        + "  var parser = new DOMParser();\n"
                        + "  var parsedDoc = parser.parseFromString(script, 'text/html');\n"
                        + "  var parsedNode = parsedDoc.body.firstChild.firstChild;\n"
                        + " alert('parsed');\n"

                        + "  document.getElementById('tester').insertBefore(parsedNode, null);\n"
                        + " alert('inserted');\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }
}
