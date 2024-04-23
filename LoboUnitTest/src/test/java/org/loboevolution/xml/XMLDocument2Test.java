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
import org.loboevolution.html.js.xml.XMLDocument;

/**
 * Tests for {@link XMLDocument}.
 */
@ExtendWith(AlertsExtension.class)
public class XMLDocument2Test extends LoboUnitTest {


    @Test
    @Alerts({"myTarget,myData,7", "myTarget,myData", "<?myTarget myData?>"})
    public void createProcessingInstruction() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var d = doc.createElement('doc');\n"
                + "    d.setAttribute('fluffy', 'true');\n"
                + "    d.setAttribute('numAttributes', '2');\n"
                + "    doc.appendChild(d);\n"
                + "    var pi = doc.createProcessingInstruction('myTarget', 'myData');\n"
                + "    doc.insertBefore(pi, d);\n"
                + "   alert(pi.nodeName + ',' + pi.nodeValue + ',' + pi.nodeType);\n"
                + "   alert(pi.target + ',' + pi.data);\n"
                + "   alert(" + XMLDocumentTest.callSerializeXMLDocumentToString("pi") + ");\n"
                + "  }\n"
                + XMLDocumentTest.SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"#cdata-section,abcdefghij,4", "abcdefghij", "<![CDATA[abcdefghij]]>"})
    public void createCDATASection() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var d = doc.createElement('doc');\n"
                + "    doc.appendChild(d);\n"
                + "    var cdata = doc.createCDATASection('abcdefghij');\n"
                + "    d.appendChild(cdata);\n"
                + "   alert(cdata.nodeName + ',' + cdata.nodeValue + ',' + cdata.nodeType);\n"
                + "   alert(cdata.data);\n"
                + "   alert(" + XMLDocumentTest.callSerializeXMLDocumentToString("cdata") + ");\n"
                + "  }\n"
                + XMLDocumentTest.SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"#cdata-section,<>&?,4", "<>&?", "<![CDATA[<>&?]]>"})
    public void createCDATASection_specialChars() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var d = doc.createElement('doc');\n"
                + "    doc.appendChild(d);\n"
                + "    var cdata = doc.createCDATASection('<>&?');\n"
                + "    d.appendChild(cdata);\n"
                + "   alert(cdata.nodeName + ',' + cdata.nodeValue + ',' + cdata.nodeType);\n"
                + "   alert(cdata.data);\n"
                + "   alert(" + XMLDocumentTest.callSerializeXMLDocumentToString("cdata") + ");\n"
                + "  }\n"
                + XMLDocumentTest.SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("createNode not available")
    public void createNode() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromString("'<root><child/></root>'") + ";\n"
                + "    if (document.createNode) {\n"
                + "      var node = doc.createNode(2, 'Sci-Fi', '');\n"
                + "      doc.documentElement.childNodes.item(0).attributes.setNamedItem(node);\n"
                + "     alert(" + XMLDocumentTest.callSerializeXMLDocumentToString("doc.documentElement") + ");\n"
                + "    } else {alert('createNode not available'); }\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + XMLDocumentTest.SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("createNode not available")
    public void createNodeElement() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    if (document.createNode) {\n"
                + "      var node = doc.createNode(1, 'test:element', 'uri:test');\n"
                + "     alert(node.localName);\n"
                + "     alert(node.prefix);\n"
                + "     alert(node.namespaceURI);\n"
                + "     alert(node.nodeName);\n"
                + "    } else {alert('createNode not available'); }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"a", "null", "b"})
    public void documentElementCaching() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var a = doc.createElement('a');\n"
                + "    var b = doc.createElement('b');\n"
                + "    doc.appendChild(a);\n"
                + "   alert(doc.documentElement.tagName);\n"
                + "    doc.removeChild(a);\n"
                + "   alert(doc.documentElement);\n"
                + "    doc.appendChild(b);\n"
                + "   alert(doc.documentElement.tagName);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("a:b")
    public void createElementNamespace() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var a = doc.createElement('a:b');\n"
                + "   alert(a.tagName);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void text() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      new ActiveXObject('Microsoft.XMLDOM');\n"
                + "    } catch (e) {\n"
                + "     alert('exception');\n"
                + "      return;\n"
                + "    }\n"
                + "    var xmldoc = new ActiveXObject('Microsoft.XMLDOM');\n"
                + "    var xml = '<Envelope><Body>content</Body></Envelope>';\n"
                + "    xmldoc.loadXML(xml);\n"
                + "    var expression = '/Envelope/Body';\n"
                + "    var body = xmldoc.documentElement.selectSingleNode(expression);\n"
                + "   alert(body.text);\n"
                + "   alert(body.firstChild.text);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"foo", "foo"})
    public void firstChildElement() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.firstChild.nodeName);\n"
                + "   alert(doc.documentElement.nodeName);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_NATIVE_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void firstChildElementActiveX() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      new ActiveXObject('Microsoft.XMLDOM');\n"
                + "    } catch (e) {\n"
                + "     alert('exception');\n"
                + "      return;\n"
                + "    }\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.firstChild.nodeName);\n"
                + "   alert(doc.documentElement.nodeName);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_ACTIVEX_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"foo", "foo"})
    public void firstChildXmlDeclaration() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.firstChild.nodeName);\n"
                + "   alert(doc.documentElement.nodeName);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_NATIVE_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    // Xerces does not offer any way to access the XML declaration
    public void firstChildXmlDeclarationActiveX() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      new ActiveXObject('Microsoft.XMLDOM');\n"
                + "    } catch (e) {\n"
                + "     alert('exception');\n"
                + "      return;\n"
                + "    }\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.firstChild.nodeName);\n"
                + "   alert(doc.documentElement.nodeName);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_ACTIVEX_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"apache", "foo"})
    public void firstChildProcessingInstruction() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.firstChild.nodeName);\n"
                + "   alert(doc.documentElement.nodeName);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_NATIVE_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void firstChildProcessingInstructionActiveX() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      new ActiveXObject('Microsoft.XMLDOM');\n"
                + "    } catch (e) {\n"
                + "     alert('exception');\n"
                + "      return;\n"
                + "    }\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.firstChild.nodeName);\n"
                + "   alert(doc.documentElement.nodeName);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_ACTIVEX_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"dtd", "a"})
    public void firstChildDocumentType() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.firstChild.nodeName);\n"
                + "   alert(doc.documentElement.nodeName);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_NATIVE_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void firstChildDocumentTypeActiveX() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      new ActiveXObject('Microsoft.XMLDOM');\n"
                + "    } catch (e) {\n"
                + "     alert('exception');\n"
                + "      return;\n"
                + "    }\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.firstChild.nodeName);\n"
                + "   alert(doc.documentElement.nodeName);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_ACTIVEX_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"#comment", "foo"})
    public void firstChildComment() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.firstChild.nodeName);\n"
                + "   alert(doc.documentElement.nodeName);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_NATIVE_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void firstChildCommentActiveX() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      new ActiveXObject('Microsoft.XMLDOM');\n"
                + "    } catch (e) {\n"
                + "     alert('exception');\n"
                + "      return;\n"
                + "    }\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.firstChild.nodeName);\n"
                + "   alert(doc.documentElement.nodeName);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_ACTIVEX_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"foo", "fooc1", "null"})
    public void firstElementChild() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "    if (doc.firstElementChild == null) {alert('not available'); return };\n"
                + "   alert(doc.firstElementChild.nodeName);\n"
                + "   alert(doc.firstElementChild.firstElementChild.nodeName);\n"
                + "   alert(doc.firstElementChild.firstElementChild.firstElementChild);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_NATIVE_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"foo", "fooc2", "null"})
    public void lastElementChild() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "    if (doc.firstElementChild == null) {alert('not available'); return };\n"
                + "   alert(doc.lastElementChild.nodeName);\n"
                + "   alert(doc.firstElementChild.lastElementChild.nodeName);\n"
                + "   alert(doc.firstElementChild.firstElementChild.lastElementChild);\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_NATIVE_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"name: item1", "id: 1", "id: 2", "name: item2", "name: item3", "id: 3"})
    public void attributeOrder() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + XMLDocumentTest.callLoadXMLDocumentFromString(
                "'<items>"
                        + "<item name=\"item1\" id=\"1\">value1</item>"
                        + "<item id=\"2\" name=\"item2\">value2</item>"
                        + "<item name=\"item3\" id=\"3\">value3</item>"
                        + "</items>'") + ";\n"
                + "    var items = doc.getElementsByTagName('item');\n"
                + "    for(var i = 0; i < items.length; i++) {\n"
                + "      var attribs = items[i].attributes;\n"
                + "      for(var j = 0; j < attribs.length; j++) {\n"
                + "       alert(attribs[j].name + ': ' + attribs[j].value);\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + XMLDocumentTest.LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
