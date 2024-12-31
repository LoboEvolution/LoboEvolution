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
import org.loboevolution.html.xpath.XPathResult;

/**
 * Tests for {@link XPathResult}.
 */
@ExtendWith(AlertsExtension.class)
public class XPathResultTest extends LoboUnitTest {


    @Test
    @Alerts({"function", "error"})
    public void ctor() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      if (!('XPathResult' in window)) {\n"
                + "       alert('XPathResult not available');\n"
                + "        return;\n"
                + "      }\n"
                + "      try {\n"
                + "       alert(typeof XPathResult);\n"
                + "        new XPathResult();\n"
                + "      } catch(e) {alert('error'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "1", "3"})
    public void resultType() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    if (document.evaluate && XPathResult) {\n"
                + "      try {\n"
                + "        var text='<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n';\n"
                + "        text += '<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://myNS\">\\n';\n"
                + "        text += '  <xsl:template match=\"/\">\\n';\n"
                + "        text += '  <html>\\n';\n"
                + "        text += '    <body>\\n';\n"
                + "        text += '      <div/>\\n';\n"
                + "        text += '      <div/>\\n';\n"
                + "        text += '    </body>\\n';\n"
                + "        text += '  </html>\\n';\n"
                + "        text += '  </xsl:template>\\n';\n"
                + "        text += '</xsl:stylesheet>';\n"
                + "        var parser = new DOMParser();\n"
                + "        var doc = parser.parseFromString(text, 'text/xml');\n"
                + "        var expressions = ['//div', 'count(//div)', 'count(//div) = 2'];\n"
                + "        for (var i = 0; i < expressions.length; i++) {\n"
                + "          var expression = expressions[i];\n"
                + "          var result = doc.evaluate(expression, doc.documentElement, null,"
                + " XPathResult.ANY_TYPE, null);\n"
                + "         alert(result.resultType);\n"
                + "        }\n"
                + "      } catch (e) {alert(e); }\n"
                + "    } else {\n"
                + "     alert('evaluate not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"7", "id1", "id2"})
    public void snapshotType() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    if (document.evaluate && XPathResult) {\n"
                + "      try {\n"
                + "        var text='<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n';\n"
                + "        text += '<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://myNS\">\\n';\n"
                + "        text += '  <xsl:template match=\"/\">\\n';\n"
                + "        text += '  <html>\\n';\n"
                + "        text += '    <body>\\n';\n"
                + "        text += '      <div id=\\'id1\\'/>\\n';\n"
                + "        text += '      <div id=\\'id2\\'/>\\n';\n"
                + "        text += '    </body>\\n';\n"
                + "        text += '  </html>\\n';\n"
                + "        text += '  </xsl:template>\\n';\n"
                + "        text += '</xsl:stylesheet>';\n"
                + "        var parser=new DOMParser();\n"
                + "        var doc=parser.parseFromString(text,'text/xml');\n"
                + "        var result = doc.evaluate('//div', doc.documentElement, null,"
                + " XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);\n"
                + "       alert(result.resultType);\n"
                + "        for (var i = 0; i < result.snapshotLength; i++) {\n"
                + "         alert(result.snapshotItem(i).getAttribute('id'));\n"
                + "        }\n"
                + "      } catch (e) {alert(e); }\n"
                + "    } else {\n"
                + "     alert('evaluate not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"9", "id1"})
    public void singleNodeValue() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    if (document.evaluate && XPathResult) {\n"
                + "      try {\n"
                + "        var text='<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n';\n"
                + "        text += '<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://myNS\">\\n';\n"
                + "        text += '  <xsl:template match=\"/\">\\n';\n"
                + "        text += '  <html>\\n';\n"
                + "        text += '    <body>\\n';\n"
                + "        text += '      <div id=\\'id1\\'/>\\n';\n"
                + "        text += '      <div id=\\'id2\\'/>\\n';\n"
                + "        text += '    </body>\\n';\n"
                + "        text += '  </html>\\n';\n"
                + "        text += '  </xsl:template>\\n';\n"
                + "        text += '</xsl:stylesheet>';\n"
                + "        var parser=new DOMParser();\n"
                + "        var doc=parser.parseFromString(text,'text/xml');\n"
                + "        var result = doc.evaluate('//div', doc.documentElement, null,"
                + " XPathResult.FIRST_ORDERED_NODE_TYPE, null);\n"
                + "       alert(result.resultType);\n"
                + "       alert(result.singleNodeValue.getAttribute('id'));\n"
                + "      } catch (e) {alert(e); }\n"
                + "    } else {\n"
                + "     alert('evaluate not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"id1", "id2"})
    public void iterateNext() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    if (document.evaluate && XPathResult) {\n"
                + "      try {\n"
                + "        var text='<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n';\n"
                + "        text += '<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://myNS\">\\n';\n"
                + "        text += '  <xsl:template match=\"/\">\\n';\n"
                + "        text += '  <html>\\n';\n"
                + "        text += '    <body>\\n';\n"
                + "        text += '      <div id=\"id1\"/>\\n';\n"
                + "        text += '      <div id=\"id2\"/>\\n';\n"
                + "        text += '    </body>\\n';\n"
                + "        text += '  </html>\\n';\n"
                + "        text += '  </xsl:template>\\n';\n"
                + "        text += '</xsl:stylesheet>';\n"
                + "        var parser=new DOMParser();\n"
                + "        var doc=parser.parseFromString(text,'text/xml');\n"
                + "        var result = doc.evaluate('" + "//div" + "', doc.documentElement, "
                + "null, XPathResult.ANY_TYPE, null);\n"
                + "        var thisNode = result.iterateNext();\n"
                + "        while (thisNode) {\n"
                + "         alert(thisNode.getAttribute('id'));\n"
                + "          thisNode = result.iterateNext();\n"
                + "        }\n"
                + "      } catch (e) {alert(e); }\n"
                + "    } else {\n"
                + "     alert('evaluate not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("7")
    public void notOr() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    if (document.evaluate && XPathResult) {\n"
                + "      try {\n"
                + "        var expression = \".//*[@id='level1']/*[not(preceding-sibling::* or following-sibling::*)]\";\n"
                + "        var result = document.evaluate(expression, document, null, "
                + "XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);\n"
                + "       alert(result.resultType);\n"
                + "      } catch (e) {alert(e); }\n"
                + "    } else {\n"
                + "     alert('evaluate not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"bar", "foo", "foo"})
    public void stringType() {
        final String html = "<html><head><title attr=\"bar\">foo</title><script>\n"
                + "  function test() {\n"
                + "    if (document.evaluate && XPathResult) {\n"
                + "      try {\n"
                + "        var result = document.evaluate('//title/@attr', document, null, "
                + "XPathResult.STRING_TYPE, null);\n"
                + "       alert(result.stringValue);\n"
                + "        result = document.evaluate('//title', document, null, "
                + "XPathResult.STRING_TYPE, null);\n"
                + "       alert(result.stringValue);\n"
                + "        var result = document.evaluate('//title/text()', document, null, "
                + "XPathResult.STRING_TYPE, null);\n"
                + "       alert(result.stringValue);\n"
                + "      } catch (e) {alert(e); }\n"
                + "    } else {\n"
                + "     alert('evaluate not supported');\n"
                + "    }\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true", "true"})
    public void numberType() {
        final String html = "<html><head><title attr=\"1234\">4321.5</title><span>foo</span><script>\n"
                + "  function test() {\n"
                + "    if (document.evaluate && XPathResult) {\n"
                + "      try {\n"
                + "        var result = document.evaluate('//title/@attr', document, null, "
                + "XPathResult.NUMBER_TYPE, null);\n"
                + "       alert(result.numberValue === 1234);\n"
                + "        result = document.evaluate('//title', document, null, "
                + "XPathResult.NUMBER_TYPE, null);\n"
                + "       alert(result.numberValue === 4321.5);\n"
                + "        result = document.evaluate('//title/text()', document, null, "
                + "XPathResult.NUMBER_TYPE, null);\n"
                + "       alert(result.numberValue === 4321.5);\n"
                + "        result = document.evaluate('//span', document, null, "
                + "XPathResult.NUMBER_TYPE, null);\n"
                + "       alert(isNaN(result.numberValue));\n"
                + "      } catch (e) {alert(e); }\n"
                + "    } else {\n"
                + "     alert('evaluate not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true", "true", "true", "true"})
    public void booleanType() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    if (document.evaluate && XPathResult) {\n"
                + "      try {\n"
                + "        var result = document.evaluate('//unknown', document, null, "
                + "XPathResult.BOOLEAN_TYPE, null);\n"
                + "       alert(result.booleanValue === false);\n"
                + "        var result = document.evaluate('//title', document, null, "
                + "XPathResult.BOOLEAN_TYPE, null);\n"
                + "       alert(result.booleanValue === true);\n"
                + "        result = document.evaluate('//div', document, null, "
                + "XPathResult.BOOLEAN_TYPE, null);\n"
                + "       alert(result.booleanValue === true);\n"
                + "        result = document.evaluate('//div/@attr', document, null, "
                + "XPathResult.BOOLEAN_TYPE, null);\n"
                + "       alert(result.booleanValue === true);\n"
                + "        result = document.evaluate('//span', document, null, "
                + "XPathResult.BOOLEAN_TYPE, null);\n"
                + "       alert(result.booleanValue === true);\n"
                + "        result = document.evaluate('//span/@attr', document, null, "
                + "XPathResult.BOOLEAN_TYPE, null);\n"
                + "       alert(result.booleanValue === true);\n"
                + "      } catch (e) {alert(e); }\n"
                + "    } else {\n"
                + "     alert('evaluate not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div attr=\"false\">false</span>\n"
                + "  <span attr=\"true\">true</span>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "not boolean", "not number", "not string", "not node", "not length"})
    public void emptySetTypeAny() {
        emptySetType("XPathResult.ANY_TYPE");
    }

    @Test
    @Alerts({"1", "not boolean", "NaN", "not string", "not node", "not length"})
    public void emptySetTypeNumber() {
        emptySetType("XPathResult.NUMBER_TYPE");
    }

    @Test
    @Alerts({"2", "not boolean", "not number", "", "not node", "not length"})
    public void emptySetTypeString() {
        emptySetType("XPathResult.STRING_TYPE");
    }

    @Test
    @Alerts({"3", "false", "not number", "not string", "not node", "not length"})
    public void emptySetTypeBoolean() {
        emptySetType("XPathResult.BOOLEAN_TYPE");
    }

    @Test
    @Alerts({"4", "not boolean", "not number", "not string", "not node", "not length"})
    public void emptySetTypeUnorderedNodeIterator() {
        emptySetType("XPathResult.UNORDERED_NODE_ITERATOR_TYPE");
    }

    @Test
    @Alerts({"5", "not boolean", "not number", "not string", "not node", "not length"})
    public void emptySetTypeOrderedNodeIterator() {
        emptySetType("XPathResult.ORDERED_NODE_ITERATOR_TYPE");
    }

    @Test
    @Alerts({"6", "not boolean", "not number", "not string", "not node", "0"})
    public void emptySetTypeUnorderedNodeSnapshot() {
        emptySetType("XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE");
    }

    @Test
    @Alerts({"7", "not boolean", "not number", "not string", "not node", "0"})
    public void emptySetTypeOrderedNodeSnapshot() {
        emptySetType("XPathResult.ORDERED_NODE_SNAPSHOT_TYPE");
    }

    @Test
    @Alerts({"8", "not boolean", "not number", "not string", "null", "not length"})
    public void emptySetTypeAnyOrderedNode() {
        emptySetType("XPathResult.ANY_UNORDERED_NODE_TYPE");
    }

    @Test
    @Alerts({"9", "not boolean", "not number", "not string", "null", "not length"})
    public void emptySetTypeFirstOrderedNode() {
        emptySetType("XPathResult.FIRST_ORDERED_NODE_TYPE");
    }

    private void emptySetType(final String type) {
        type("//unknown", type);
    }

    @Test
    @Alerts({"3", "false", "not number", "not string", "not node", "not length"})
    public void zeroTypeBoolean() {
        typeBoolean("0");
    }

    @Test
    @Alerts({"3", "true", "not number", "not string", "not node", "not length"})
    public void minusOneTypeBoolean() {
        typeBoolean("-1");
    }

    @Test
    @Alerts({"3", "true", "not number", "not string", "not node", "not length"})
    public void infTypeBoolean() {
        typeBoolean("1.0 div 0.0");
    }

    @Test
    @Alerts({"3", "true", "not number", "not string", "not node", "not length"})
    public void minusInfTypeBoolean() {
        typeBoolean("-1.0 div 0.0");
    }

    @Test
    @Alerts({"3", "true", "not number", "not string", "not node", "not length"})
    public void stringTypeBoolean() {
        typeBoolean("\"abc\"");
    }

    @Test
    @Alerts({"3", "false", "not number", "not string", "not node", "not length"})
    public void emptyStringTypeBoolean() {
        typeBoolean("\"\"");
    }

    private void typeBoolean(final String xpath) {
        type(xpath, "XPathResult.BOOLEAN_TYPE");
    }

    @Test
    @Alerts({"1", "not boolean", "0", "not string", "not node", "not length"})
    public void zeroTypeNumber() {
        typeNumber("0");
    }

    @Test
    @Alerts({"1", "not boolean", "Infinity", "not string", "not node", "not length"})
    public void infTypeNumber() {
        typeNumber("1.0 div 0.0");
    }

    @Test
    @Alerts({"1", "not boolean", "-Infinity", "not string", "not node", "not length"})
    public void minusInfTypeNumber() {
        typeNumber("-1.0 div 0.0");
    }

    @Test
    @Alerts({"1", "not boolean", "NaN", "not string", "not node", "not length"})
    public void stringTypeNumber() {
        typeNumber("\"abc\"");
    }

    @Test
    @Alerts({"1", "not boolean", "123.4", "not string", "not node", "not length"})
    public void numberStringTypeNumber() {
        typeNumber("\"123.4\"");
    }

    @Test
    @Alerts({"3", "false", "not number", "not string", "not node", "not length"})
    public void emptyStringTypeNumber() {
        typeBoolean("\"\"");
    }

    @Test
    @Alerts({"1", "not boolean", "1", "not string", "not node", "not length"})
    public void trueTypeNumber() {
        typeNumber("true()");
    }

    @Test
    @Alerts({"1", "not boolean", "0", "not string", "not node", "not length"})
    public void falseTypeNumber() {
        typeNumber("false()");
    }

    private void typeNumber(final String xpath) {
        type(xpath, "XPathResult.NUMBER_TYPE");
    }

    private void type(final String xpath, final String type) {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    if (document.evaluate && XPathResult) {\n"
                + "      try {\n"
                + "        var result = document.evaluate('" + xpath + "', document, null, "
                + type + ", null);\n"
                + "       alert(result.resultType);\n"
                + "        try {\n"
                + "         alert(result.booleanValue);\n"
                + "        } catch (e) {alert('not boolean'); }\n"
                + "        try {\n"
                + "         alert(result.numberValue);\n"
                + "        } catch (e) {alert('not number'); }\n"
                + "        try {\n"
                + "         alert(result.stringValue);\n"
                + "        } catch (e) {alert('not string'); }\n"
                + "        try {\n"
                + "         alert(result.singleNodeValue);\n"
                + "        } catch (e) {alert('not node'); }\n"
                + "        try {\n"
                + "         alert(result.snapshotLength);\n"
                + "        } catch (e) {alert('not length'); }\n"
                + "      } catch (e) {alert(e); }\n"
                + "    } else {\n"
                + "     alert('evaluate not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div attr=\"false\">false</span>\n"
                + "  <span attr=\"true\">true</span>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
