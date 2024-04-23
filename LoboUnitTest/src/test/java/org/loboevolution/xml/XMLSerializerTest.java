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
package org.loboevolution.xml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.js.xml.XMLSerializer;

/**
 * Tests for {@link XMLSerializer}.
 */
@ExtendWith(AlertsExtension.class)
public class XMLSerializerTest extends LoboUnitTest {

    @Test
    @Alerts({"<html xmlns=\"http://www.w3.org/1999/xhtml\"><body id=\"bodyId\"></body></html>",
            "<html xmlns=\"http://www.w3.org/1999/xhtml\"><body id=\"bodyId\"></body></html>"})
    public void xhtmlDocument() {
        final String html = "<html><head>\n"
                + "<script>\n" 
                + "function test() {\n"
                + "  var doc = document.implementation.createDocument('http://www.w3.org/1999/xhtml', 'html', null);\n"
                + "  var body = document.createElementNS('http://www.w3.org/1999/xhtml', 'body');\n"
                + "  body.setAttribute('id', 'bodyId');\n"
                + "  doc.documentElement.appendChild(body);"
                + " alert(new XMLSerializer().serializeToString(doc));\n"
                + " alert(new XMLSerializer().serializeToString(doc.documentElement));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<html xmlns=\"http://www.w3.org/1999/xhtml\"><body xmlns=\"\" id=\"bodyId\"/></html>",
            "<html xmlns=\"http://www.w3.org/1999/xhtml\"><body xmlns=\"\" id=\"bodyId\"/></html>"})
    public void xhtmlDocumentBodyEmptyNamespace() {
        final String html = "<html><head>\n"
                + "<script>\n" 
                + "function test() {\n"
                + "  var doc = document.implementation.createDocument('http://www.w3.org/1999/xhtml', 'html', null);\n"
                + "  var body = document.createElementNS('', 'body');\n"
                + "  body.setAttribute('id', 'bodyId');\n"
                + "  doc.documentElement.appendChild(body);"
                + " alert(new XMLSerializer().serializeToString(doc));\n"
                + " alert(new XMLSerializer().serializeToString(doc.documentElement));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body/></soap:Envelope>",
            "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body/></soap:Envelope>"})
    public void soapTest() {
        final String html = "<html><head>\n"
                + "<script>\n" 
                + "function test() {\n"
                + "  var dom = document.implementation.createDocument('http://schemas.xmlsoap.org/soap/envelope/', 'soap:Envelope', null);\n"
                + "  var soapEnv = dom.documentElement;\n"
                + "  soapBody = dom.createElementNS('http://schemas.xmlsoap.org/soap/envelope/', 'Body');\n"
                + "  soapEnv.appendChild(soapBody);"
                + " alert(new XMLSerializer().serializeToString(dom));\n"
                + " alert(new XMLSerializer().serializeToString(dom.documentElement));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<foo/>", "<foo/>"})
    public void document() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var doc = document.implementation.createDocument('', 'foo', null);\n"
                + " alert(new XMLSerializer().serializeToString(doc));\n"
                + " alert(new XMLSerializer().serializeToString(doc.documentElement));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("#")
    public void emptyDocumentFragment() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var fragment = document.createDocumentFragment();\n"
                + " alert('#' + new XMLSerializer().serializeToString(fragment));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<h1 xmlns=\"http://www.w3.org/1999/xhtml\">HtmlUnit</h1><h2 xmlns=\"http://www.w3.org/1999/xhtml\">is great</h2>")
    public void documentFragment() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var fragment = document.createDocumentFragment();\n"
                + "  var heading = document.createElement('h1');\n"
                + "  heading.textContent = 'HtmlUnit';\n"
                + "  fragment.appendChild(heading);\n"
                + "  heading = document.createElement('h2');\n"
                + "  heading.textContent = 'is great';\n"
                + "  fragment.appendChild(heading);\n"
                + " alert(new XMLSerializer().serializeToString(fragment));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<img/>", "<img xmlns=\"http://www.w3.org/1999/xhtml\" />", "<?myTarget myData?>"})
    public void xml() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    testFragment(doc);\n"
                + "    testFragment(document);\n"
                + "    var pi = doc.createProcessingInstruction('myTarget', 'myData');\n"
                + "   alert(new XMLSerializer().serializeToString(pi));\n"
                + "  }\n"
                + "  function testFragment(doc) {\n"
                + "    var fragment = doc.createDocumentFragment();\n"
                + "    var img = doc.createElement('img');\n"
                + "    fragment.appendChild(img);\n"
                + "   alert(new XMLSerializer().serializeToString(fragment));\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<root><my:parent xmlns:my=\"myUri\"><my:child/><another_child/></my:parent></root>")
    public void namespace() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var root = doc.createElement('root');\n"
                + "    doc.appendChild(root);\n"
                + "    var parent = createNS(doc, 'my:parent', 'myUri');\n"
                + "    root.appendChild(parent);\n"
                + "    parent.appendChild(createNS(doc, 'my:child', 'myUri'));\n"
                + "    parent.appendChild(doc.createElement('another_child'));\n"
                + "   alert(" + XMLDocumentTest.callSerializeXMLDocumentToString("doc") + ");\n"
                + "  }\n"
                + "  function createNS(doc, name, uri) {\n"
                + "    return typeof doc.createNode == 'function' || typeof doc.createNode == 'unknown' ? "
                + "doc.createNode(1, name, uri) : doc.createElementNS(uri, name);\n"
                + "  }\n"
                + XMLDocumentTest.SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<textarea xmlns=\"http://www.w3.org/1999/xhtml\"></textarea>")
    public void mixedCase() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var t = document.createElement('teXtaREa');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<area xmlns=\"http://www.w3.org/1999/xhtml\" />",
            "<base xmlns=\"http://www.w3.org/1999/xhtml\" />",
            "<basefont xmlns=\"http://www.w3.org/1999/xhtml\" />",
            "<br xmlns=\"http://www.w3.org/1999/xhtml\" />",
            "<hr xmlns=\"http://www.w3.org/1999/xhtml\" />",
            "<input xmlns=\"http://www.w3.org/1999/xhtml\" />",
            "<link xmlns=\"http://www.w3.org/1999/xhtml\" />",
            "<meta xmlns=\"http://www.w3.org/1999/xhtml\" />"})
    public void noClosingTag() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var t = document.createElement('area');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "    var t = document.createElement('base');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "    var t = document.createElement('basefont');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "    var t = document.createElement('br');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "    var t = document.createElement('hr');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "    var t = document.createElement('input');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "    var t = document.createElement('link');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "    var t = document.createElement('meta');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<input xmlns=\"http://www.w3.org/1999/xhtml\" />")
    public void inputTagWithoutType() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var t = document.createElement('input');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<div xmlns=\"http://www.w3.org/1999/xhtml\"></div>",
            "<h1 xmlns=\"http://www.w3.org/1999/xhtml\"></h1>",
            "<p xmlns=\"http://www.w3.org/1999/xhtml\"></p>",
            "<li xmlns=\"http://www.w3.org/1999/xhtml\"></li>",
            "<textarea xmlns=\"http://www.w3.org/1999/xhtml\"></textarea>"})
    public void otherTags() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var t = document.createElement('div');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "    var t = document.createElement('h1');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "    var t = document.createElement('p');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "    var t = document.createElement('li');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "    var t = document.createElement('textarea');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<img xmlns=\"http://www.w3.org/1999/xhtml\" href=\"mypage.htm\" />")
    public void noClosingTagWithAttribute() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var t = document.createElement('img');\n"
                + "    t.setAttribute('href', 'mypage.htm');\n"
                + "   alert(new XMLSerializer().serializeToString(t));\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<catalog>\n"
            + "  <cd>\n"
            + "    <title>Empire Burlesque</title>\n"
            + "    <artist>Bob Dylan \u1042</artist>\n"
            + "  </cd>\n"
            + "</catalog>")
    public void outputXmlIndent() {
        transform("<xsl:output method='xml' indent='yes' />");
    }

    @Test
    @Alerts("<catalog>\n"
            + "  <cd>\n"
            + "    <title>Empire Burlesque</title>\n"
            + "    <artist>Bob Dylan \u1042</artist>\n"
            + "  </cd>\n"
            + "</catalog>")
    public void outputIndent() {
        transform("<xsl:output indent='yes' />");
    }

    @Test
    @Alerts("<catalog><cd><title>Empire Burlesque</title><artist>Bob Dylan \u1042</artist></cd></catalog>")
    public void outputNoIndent() {
        transform("<xsl:output indent='no' />");
    }

    @Test
    @Alerts("<catalog><cd><title>Empire Burlesque</title><artist>Bob Dylan \u1042</artist></cd></catalog>")
    public void outputEncoding1252() {
        transform("<xsl:output encoding='Windows-1252' />");
    }

    @Test
    @Alerts("<catalog><cd><title>Empire Burlesque</title><artist>Bob Dylan \u1042</artist></cd></catalog>")
    public void outputEncoding1251() {
        transform("<xsl:output encoding='Windows-1251' />");
    }

    @Test
    @Alerts("<catalog><cd><title>Empire Burlesque</title><artist>Bob Dylan \u1042</artist></cd></catalog>")
    public void outputOmitXmlDeclaration() {
        transform("<xsl:output omit-xml-declaration='yes' />");
    }

    @Test
    @Alerts("<catalog><cd><title>Empire Burlesque</title><artist>Bob Dylan \u1042</artist></cd></catalog>")
    public void noOutput() {
        transform("");
    }

    public void transform(final String xslOutput) {
        final String xml
                = "<?xml version='1.0' encoding='ISO-8859-1'?>"
                + "<catalog><cd><title>Empire Burlesque</title>"
                + "<artist>Bob Dylan &#x1042;</artist>"
                + "</cd>"
                + "</catalog>";

        final String xsl
                = "<?xml version='1.0' encoding='ISO-8859-1'?>"
                + "<xsl:stylesheet version='1.0' "
                + "xmlns:xsl='http://www.w3.org/1999/XSL/Transform' >"
                + "  " + xslOutput
                + "  <xsl:template match='@*|node()'>"
                + "    <xsl:copy>"
                + "      <xsl:apply-templates select='@*|node()'/>"
                + "    </xsl:copy>"
                + "  </xsl:template>"
                + "</xsl:stylesheet>";

        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var ta = document.getElementById('myTextArea');\n"
                + "    try {\n"
                + "      var xsltProcessor = new XSLTProcessor();\n"
                + "      var xmlDoc = new DOMParser().parseFromString(\"" + xml + "\", \"application/xml\");\n"
                + "      var xsltDoc = new DOMParser().parseFromString(\"" + xsl + "\", \"application/xml\");\n"
                + "      xsltProcessor.importStylesheet(xsltDoc);\n"
                + "      var resultDocument = xsltProcessor.transformToDocument(xmlDoc);\n"
                + "      var xml = new XMLSerializer().serializeToString(resultDocument);\n"
                + "      ta.value = xml;\n"
                + "      alert(ta.value);\n"
                + "    } catch(e) { ta.value = 'exception'; }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <textarea id='myTextArea' cols='80' rows='30'></textarea>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
