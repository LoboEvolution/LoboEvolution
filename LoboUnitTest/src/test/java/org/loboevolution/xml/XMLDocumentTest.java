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
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.js.xml.XMLDocument;

/**
 * Tests for {@link XMLDocument}.
 */
@ExtendWith(AlertsExtension.class)
public class XMLDocumentTest extends LoboUnitTest {

    private static final String LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION_NAME = "loadXMLDocumentFromFile";

    /**
     * Helper.
     */
    public static final String LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION =
            "  function " + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION_NAME + "(file) {\n"
            + "    xhttp = new XMLHttpRequest();\n"
            + "    xhttp.open(\"GET\", file, false);\n"
            + "    xhttp.send();\n"
            + "    return xhttp.responseXML;\n"
            + "  }\n";

    /**
     * Helper.
     */
    public static final String LOAD_NATIVE_XML_DOCUMENT_FROM_FILE_FUNCTION = "  function " + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION_NAME + "(file) {\n"
            + "    xhttp = new XMLHttpRequest();\n"
            + "    xhttp.open(\"GET\", file, false);\n"
            + "    xhttp.send();\n"
            + "    return xhttp.responseXML;\n"
            + "  }\n";

    /**
     * Helper.
     */
    public static final String LOAD_ACTIVEX_XML_DOCUMENT_FROM_FILE_FUNCTION = "  function " + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION_NAME + "(file) {\n"
            + "    xhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n"
            + "    xhttp.open(\"GET\", file, false);\n"
            + "    xhttp.send();\n"
            + "    return xhttp.responseXML;\n"
            + "  }\n";
    private static final String LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION_NAME = "loadXMLDocumentFromString";
    /**
     * Helper.
     */
    public static final String LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION = "  function " + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION_NAME + "(xml) {\n"
            + "    var parser = new DOMParser();\n"
            + "    return parser.parseFromString(xml,\"text/xml\");\n"
            + "  }\n";
    private static final String SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION_NAME = "serializeXMLDocumentToString";
    /**
     * Helper.
     */
    public static final String SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION = "  function " + SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION_NAME + "(doc) {\n"
            + "    return new XMLSerializer().serializeToString(doc);\n"
            + "  }\n";
    /**
     * Helper.
     */
    public static final String SERIALIZE_NATIVE_XML_DOCUMENT_TO_STRING_FUNCTION = "  function " + SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION_NAME + "(doc) {\n"
            + "    serializer = new XMLSerializer();\n"
            + "    return serializer.serializeToString(doc);\n"
            + "  }\n";
    /**
     * Helper.
     */
    public static final String SERIALIZE_ACTIVEX_XML_DOCUMENT_TO_STRING_FUNCTION = "  function " + SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION_NAME + "(doc) {\n"
            + "    return doc.xml;\n"
            + "  }\n";

    /**
     * Helper.
     *
     * @param file the file parameter
     * @return xml helper
     */
    public static String callLoadXMLDocumentFromFile(final String file) {
        return LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION_NAME + "(" + file + ")";
    }

    /**
     * Helper.
     *
     * @param string the parameter
     * @return xml helper
     */
    public static String callLoadXMLDocumentFromString(final String string) {
        return LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION_NAME + "(" + string + ")";
    }

    /**
     * Helper.
     *
     * @param doc the doc parameter
     * @return xml helper
     */
    public static String callSerializeXMLDocumentToString(final String doc) {
        return SERIALIZE_XML_DOCUMENT_TO_STRING_FUNCTION_NAME + "(" + doc + ")";
    }

    @Test
    @Alerts({"undefined", "undefined"})
    public void async() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "   alert(document.async);\n"
                + "   alert(doc.async);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void load() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    doc.async = false;\n"
                + "    try {\n"
                + "     alert(doc.load('" + URL_SECOND + "'));\n"
                + "     alert(doc.documentElement.nodeName);\n"
                + "     alert(doc.childNodes[0].nodeName);\n"
                + "     alert(doc.childNodes[0].childNodes.length);\n"
                + "     alert(doc.childNodes[0].childNodes[0].nodeName);\n"
                + "     alert(doc.getElementsByTagName('books').item(0).attributes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void loadRelativeURL() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    doc.async = false;\n"
                + "    try {\n"
                + "     alert(doc.load('" + URL_SECOND + "'));\n"
                + "     alert(doc.documentElement.nodeName);\n"
                + "     alert(doc.childNodes[0].nodeName);\n"
                + "     alert(doc.childNodes[0].childNodes.length);\n"
                + "     alert(doc.childNodes[0].childNodes[0].nodeName);\n"
                + "     alert(doc.getElementsByTagName('books').item(0).attributes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("undefined")
    public void preserveWhiteSpace() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "   alert(doc.preserveWhiteSpace);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void setProperty() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    try {\n"
                + "      doc.setProperty('SelectionNamespaces', \"xmlns:xsl='http://www.w3.org/1999/XSL/Transform'\");\n"
                + "      doc.setProperty('SelectionLanguage', 'XPath');\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void selectNodes() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "    try {\n"
                + "      var nodes = doc.selectNodes('/books');\n"
                + "     alert(nodes.length);\n"
                + "     alert(nodes[0].tagName);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void selectNodesCaseSensitive() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "    try {\n"
                + "     alert(doc.selectNodes('/bOoKs').length);\n"
                + "     alert(doc.selectNodes('/books').length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void selectNodesNamespace() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "    try {\n"
                + "     alert(doc.selectNodes('//ns1:title').length);\n"
                + "     alert(doc.selectNodes('//ns2:title').length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void selectNodesNextNodeAndReset() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = " + callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "      var nodes = doc.selectNodes('//book');\n"
                + "     alert(nodes.nextNode().nodeName);\n"
                + "     alert(nodes.nextNode());\n"
                + "      nodes.reset();\n"
                + "     alert(nodes.nextNode().nodeName);\n"
                + "     alert(nodes.nextNode());\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>foo</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"book", "exception /title", "exception title"})
    public void selectNodes_fromRoot() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = " + callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "      var child = doc.documentElement.firstChild;\n"
                + "     alert(child.tagName);\n"
                + "      try {\n"
                + "       alert(child.selectNodes('/title').length);\n"
                + "      } catch(e) {alert('exception /title'); }\n"
                + "      try {\n"
                + "       alert(child.selectNodes('title').length);\n"
                + "      } catch(e) {alert('exception title'); }\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>foo</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void selectSingleNode() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<book/>';\n"
                + "    try {\n"
                + "      var doc = " + callLoadXMLDocumentFromString("text") + ";\n"
                + "     alert(doc.selectNodes('*')[0].nodeName);\n"
                + "     alert(doc.selectNodes('/')[0].nodeName);\n"
                + "     alert(doc.selectSingleNode('*').nodeName);\n"
                + "     alert(doc.selectNodes('*')[0].selectSingleNode('/').nodeName);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("someprefix:test")
    public void loadXMLNamespace() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<someprefix:test xmlns:someprefix=\"http://myNS\"/>';\n"
                + "    var doc = " + callLoadXMLDocumentFromString("text") + ";\n"
                + "   alert(doc.documentElement.tagName);\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("7")
    public void loadXMLXMLSpaceAttribute() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<root xml:space=\\'preserve\\'>This t"
                + "<elem>ext has</elem> <![CDATA[ CDATA ]]>in<elem /> it</root>';\n"
                + "    var doc = " + callLoadXMLDocumentFromString("text") + ";\n"
                + "   alert(doc.documentElement.childNodes.length);\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void parseError() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = new ActiveXObject('Microsoft.XMLDOM');\n"
                + "     alert(doc.documentElement == null);\n"
                + "     alert(doc.parseError.errorCode === 0);\n"
                + "     alert(doc.parseError.filepos === 0);\n"
                + "     alert(doc.parseError.line === 0);\n"
                + "     alert(doc.parseError.linepos === 0);\n"
                + "     alert(doc.parseError.reason === '');\n"
                + "     alert(doc.parseError.srcText === '');\n"
                + "     alert(doc.parseError.url === '');\n"
                + "      doc.async = false;\n"
                + "     alert(doc.load('" + URL_SECOND + "'));\n"
                + "     alert(doc.documentElement == null);\n"
                + "     alert(doc.parseError.errorCode !== 0);\n"
                + "     alert(doc.parseError.filepos !== 0);\n"
                + "     alert(doc.parseError.line !== 0);\n"
                + "     alert(doc.parseError.linepos !== 0);\n"
                + "     alert(doc.parseError.reason !== '');\n"
                + "     alert(doc.parseError.srcText !== '');\n"
                + "     alert(doc.parseError.url !== '');\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("http://myNS")
    public void createNSResolver() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n';\n"
                + "    text += '<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://myNS\">\\n';\n"
                + "    text += '  <xsl:template match=\"/\">\\n';\n"
                + "    text += '  <html>\\n';\n"
                + "    text += '    <body>\\n';\n"
                + "    text += '    </body>\\n';\n"
                + "    text += '  </html>\\n';\n"
                + "    text += '  </xsl:template>\\n';\n"
                + "    text += '</xsl:stylesheet>';\n"
                + "    var doc = " + callLoadXMLDocumentFromString("text") + ";\n"
                + "    if (doc.createNSResolver) {\n"
                + "     alert(doc.createNSResolver(doc.documentElement).lookupNamespaceURI('xsl'));\n"
                + "    }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void xmlInsideHtml() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "     alert(messageTableHeaders.documentElement.nodeName);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <xml id='messageTableHeaders'>\n"
                + "    <columns>\n"
                + "      <column name='_checkbox'/>\n"
                + "      <column name='itemStatus'/>\n"
                + "    </columns>\n"
                + "  </xml>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void instanceOf() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var x = " + callLoadXMLDocumentFromString("'<x/>'") + ";\n"
                + "    try {\n"
                + "     alert(x instanceof XMLDocument);\n"
                + "    }catch(e) {alert('exception')}\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <xml id='messageTableHeaders'>\n"
                + "    <columns>\n"
                + "      <column name='_checkbox'/>\n"
                + "      <column name='itemStatus'/>\n"
                + "    </columns>\n"
                + "  </xml>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("button")
    public void evaluate() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var s = '<toolbar><button id=\"compose_button\"/></toolbar>';\n"
                + "    var xDoc = " + callLoadXMLDocumentFromString("s") + ";\n"
                + "    if (xDoc.evaluate) {\n"
                + "      var r = xDoc.evaluate(\"button[@id='compose_button']\", xDoc.firstChild, null, 9, null);\n"
                + "     alert(r.singleNodeValue.tagName);\n"
                + "    }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"same doc: false", "in first: 3", "book", "ownerDocument: doc1", "getRootNode(): doc1",
            "in 2nd: 3", "ownerDocument: doc2", "getRootNode(): doc2",
            "first child ownerDocument: doc2", "first child getRootNode(): doc2", "in first: 2", "in 2nd: 4",
            "ownerDocument: doc1", "getRootNode(): doc1", "in first: 2", "in 2nd: 3",
            "ownerDocument: doc2", "getRootNode(): doc2", "in first: 1", "in 2nd: 4"})
    public void moveChildBetweenDocuments() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var doc1 = " + callLoadXMLDocumentFromFile("'foo.xml'") + ";\n"
                + "  var doc2 = " + callLoadXMLDocumentFromFile("'foo.xml'") + ";\n"
                + " alert('same doc: ' + (doc1 == doc2));\n"
                + "  var doc1Root = doc1.firstChild;\n"
                + " alert('in first: ' + doc1Root.childNodes.length);\n"
                + "  var doc1RootOriginalFirstChild = doc1Root.firstChild;\n"
                + " alert(doc1RootOriginalFirstChild.tagName);\n"
                + "  var hasRootNode = doc1RootOriginalFirstChild.getRootNode !== undefined;\n"
                + " alert('ownerDocument: ' + (doc1RootOriginalFirstChild.ownerDocument === doc1 ? 'doc1' : 'doc2'));\n"
                + "  hasRootNode ?alert('getRootNode(): ' "
                + "+ (doc1RootOriginalFirstChild.getRootNode() === doc1 ? 'doc1' : 'doc2'))"
                + " :alert('-');\n"
                + "\n"
                + "  var doc2Root = doc2.firstChild;\n"
                + " alert('in 2nd: ' + doc2Root.childNodes.length);\n"
                + "  doc2Root.appendChild(doc1RootOriginalFirstChild);\n"
                + " alert('ownerDocument: ' + (doc1RootOriginalFirstChild.ownerDocument === doc1 ? 'doc1' : 'doc2'));\n"
                + "  hasRootNode ?alert('getRootNode(): ' "
                + "+ (doc1RootOriginalFirstChild.getRootNode() === doc1 ? 'doc1' : 'doc2'))"
                + " :alert('-');\n"
                + " alert('first child ownerDocument: ' + "
                + "(doc1RootOriginalFirstChild.firstChild.ownerDocument === doc1 ? 'doc1' : 'doc2'));\n"
                + "  hasRootNode ?alert('first child getRootNode(): ' + "
                + "(doc1RootOriginalFirstChild.firstChild.getRootNode() === doc1 ? 'doc1' : 'doc2')) :alert('-');\n"
                + " alert('in first: ' + doc1Root.childNodes.length);\n"
                + " alert('in 2nd: ' + doc2Root.childNodes.length);\n"
                + "\n"
                + "  doc1Root.replaceChild(doc1RootOriginalFirstChild, doc1Root.firstChild);\n"
                + " alert('ownerDocument: ' + (doc1RootOriginalFirstChild.ownerDocument === doc1 ? 'doc1' : 'doc2'));\n"
                + "  hasRootNode ?alert('getRootNode(): ' "
                + "+ (doc1RootOriginalFirstChild.getRootNode() === doc1 ? 'doc1' : 'doc2'))"
                + " :alert('-');\n"
                + " alert('in first: ' + doc1Root.childNodes.length);\n"
                + " alert('in 2nd: ' + doc2Root.childNodes.length);\n"
                + "\n"
                + "  doc2Root.insertBefore(doc1RootOriginalFirstChild, doc2Root.firstChild);\n"
                + " alert('ownerDocument: ' + (doc1RootOriginalFirstChild.ownerDocument === doc1 ? 'doc1' : 'doc2'));\n"
                + "  hasRootNode ?alert('getRootNode(): ' "
                + "+ (doc1RootOriginalFirstChild.getRootNode() === doc1 ? 'doc1' : 'doc2'))"
                + " :alert('-');\n"
                + " alert('in first: ' + doc1Root.childNodes.length);\n"
                + " alert('in 2nd: ' + doc2Root.childNodes.length);\n"
                + "\n"
                + "}\n"
                + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "0", "1", "0"})
    public void getElementsByTagName() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.getElementsByTagName('book').length);\n"
                + "   alert(doc.getElementsByTagName('soap:book').length);\n"
                + "    var elem = doc.getElementsByTagName('book')[0];\n"
                + "   alert(elem.getElementsByTagName('title').length);\n"
                + "   alert(elem.getElementsByTagName('soap:title').length);\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1", "0", "1"})
    public void getElementsByTagNameWithNamespace() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "   alert(doc.getElementsByTagName('book').length);\n"
                + "   alert(doc.getElementsByTagName('soap:book').length);\n"
                + "    if (doc.getElementsByTagName('soap:book').length != 0) {\n"
                + "      var elem = doc.getElementsByTagName('soap:book')[0];\n"
                + "     alert(elem.getElementsByTagName('title').length);\n"
                + "     alert(elem.getElementsByTagName('soap:title').length);\n"
                + "    }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "false", "true", "false"})
    // XML ID handling not yet correctly implemented
    public void getElementByIdXml() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<?xml version=\"1.0\" encoding=\"utf-8\"?>\\n'\n"
                + "      + '<!DOCTYPE idTest [\\n'\n"
                + "      + '    <!ATTLIST item xId ID #IMPLIED>\\n'\n"
                + "      + ']>\\n'\n"
                + "      + '<idTest>\\n'\n"
                + "      + '    <item xId=\"item1\" />\\n'\n"
                + "      + '    <item xml:id=\"item2\" />\\n'\n"
                + "      + '    <item id=\"item3\" />\\n'\n"
                + "      + '    <item ID=\"item4\" />\\n'\n"
                + "      + '</idTest>';\n"
                + "    try {\n"
                + "      var doc = " + callLoadXMLDocumentFromString("text") + ";\n"
                + "     alert(doc.getElementById('item1') != null);\n"
                + "     alert(doc.getElementById('item2') != null);\n"
                + "     alert(doc.getElementById('item3') != null);\n"
                + "     alert(doc.getElementById('item4') != null);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    // XML ID handling not yet correctly implemented
    public void getElementByIdHtml() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<form id=\"form1\">\\n'\n"
                + "      + '    <div id=\"div1\"></div>\\n'\n"
                + "      + '</form>';\n"
                + "    try {\n"
                + "      var doc = " + callLoadXMLDocumentFromString("text") + ";\n"
                + "     alert(doc.getElementById('form1') != null);\n"
                + "     alert(doc.getElementById('div1') != null);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void getElementByIdXhtml() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var text='<form xmlns=\"http://www.w3.org/1999/xhtml\" id=\"form1\">\\n'\n"
                + "      + '    <div id=\"div1\"></div>\\n'\n"
                + "      + '</form>';\n"
                + "    try {\n"
                + "      var doc = " + callLoadXMLDocumentFromString("text") + ";\n"
                + "     alert(doc.getElementById('form1') != null);\n"
                + "     alert(doc.getElementById('div1') != null);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void xpathWithNamespaces() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "    try {\n"
                + "     alert(doc.selectNodes('//soap:book').length);\n"
                + "    } catch (e) {\n"
                + "      try {\n"
                + "     alert(doc.evaluate('count(//book)', doc.documentElement, "
                + "null, XPathResult.NUMBER_TYPE, null).numberValue);\n"
                + "      } catch (e) {\n"
                + "       alert('exception');\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({})
    public void selectionNamespaces() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  var selectionNamespaces = 'xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                + "xmlns:ns1=\"http://www.example.com/ns1\"';\n"
                + "  function test() {\n"
                + "  if ('ActiveXObject' in window) {\n"
                + "    var doc = new ActiveXObject('Microsoft.XMLDOM');\n"
                + "    doc.setProperty('SelectionNamespaces', selectionNamespaces);\n"
                + "    doc.async = false;\n"
                + "    doc.load('" + URL_SECOND + "');\n"
                + "    try {\n"
                + "     alert(doc.selectNodes('/s:Envelope/ns1:books/s:book').length);\n"
                + "    } catch (e) {\n"
                + "     alert(doc.evaluate('count(//book)', doc.documentElement, "
                + "null, XPathResult.NUMBER_TYPE, null).numberValue);\n"
                + "    }}\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("nodeFromID not available")
    public void nodeFromID() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "    try {\n"
                + "     alert('nodeFromID ' + doc.nodeFromID('target'));\n"
                + "    } catch (e) {\n"
                + "     alert('nodeFromID not available');\n"
                + "    }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object XMLDocument]", "OK"})
    public void test() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var ifr = document.getElementById('ifr');\n"
                + "    ifr.onload = function() {\n"
                + "      var xml = ifr.contentWindow.document;\n"
                + "     alert(xml);\n"
                + "     alert(xml.getElementsByTagName('status')[0].textContent);\n"
                + "    };\n"
                + "    ifr.src = '" + URL_SECOND + "';\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <iframe id='ifr'></iframe>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLDocument]")
    public void html() {
        final String svg
                = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<svg xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect id=\"rect\" width=\"50\" height=\"50\" fill=\"green\" onclick=\"alert(document)\"/>\n"
                + "</svg>";
        final HTMLDocument document = loadHtml(svg);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("rect");
        elem.getOnclick();
    }

    @Test
    @Alerts("[object XMLDocument]")
    public void svg() {
        final String svg
                = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<svg xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect id=\"rect\" width=\"50\" height=\"50\" fill=\"green\" onclick=\"alert(document)\"/>\n"
                + "</svg>";
        final HTMLDocument document = loadHtml(svg);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("rect");
        elem.getOnclick();
    }

    @Test
    @Alerts({"myAttr", ""})
    public void createAttributeNameValue() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var node = doc.createAttribute('myAttr');\n"
                + "   alert(node.name);\n"
                + "   alert(node.value);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='tester'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("about:blank")
    public void url() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "   alert(doc.URL);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='tester'></div>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object XMLDocument]")
    public void string() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "   alert(doc);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='tester'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
