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
package org.loboevolution.css;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for CSS selectors.

 */
@ExtendWith(AlertsExtension.class)
public class CSSSelectorTest extends LoboUnitTest {


    @Test
    @Alerts({"0", "0"})
    public void querySelectorAllNullUndefined() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll(null).length);\n"
                + " alert(document.querySelectorAll(undefined).length);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "  <li id='li3'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"exception", "exception"})
    public void querySelectorAllEmptyString() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "   alert(document.querySelectorAll(''));\n"
                + "  } catch(e) {alert('exception')}\n"
                + "  try {\n"
                + "   alert(document.querySelectorAll('  '));\n"
                + "  } catch(e) {alert('exception')}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "  <li id='li3'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"li2", "li1", "li2", "li1", "li3", "li1", "2", "li1", "li2"})
    public void nthChild() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('li:nth-child(2)')[0].id);\n"
                + " alert(document.querySelectorAll('li:nth-child(n)')[0].id);\n"
                + " alert(document.querySelectorAll('li:nth-child(2n)')[0].id);\n"
                + " alert(document.querySelectorAll('li:nth-child(2n+1)')[0].id);\n"
                + " alert(document.querySelectorAll('li:nth-child(2n+1)')[1].id);\n"
                + " alert(document.querySelectorAll('li:nth-child(2n-1)')[0].id);\n"
                + " alert(document.querySelectorAll('li:nth-child(-n+2)').length);\n"
                + " alert(document.querySelectorAll('li:nth-child(-n+2)')[0].id);\n"
                + " alert(document.querySelectorAll('li:nth-child(-n+2)')[1].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "  <li id='li3'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "li2", "2", "li1", "li3"})
    public void nthChildEvenOdd() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('li:nth-child(even)').length);\n"
                + " alert(document.querySelectorAll('li:nth-child(eVen)')[0].id);\n"
                + " alert(document.querySelectorAll('li:nth-child(odd)').length);\n"
                + " alert(document.querySelectorAll('li:nth-child(OdD)')[0].id);\n"
                + " alert(document.querySelectorAll('li:nth-child(ODD)')[1].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "  <li id='li3'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "[object HTMLBodyElement]", "1", "0"})
    public void childSelectorHtml_body() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('html > body').length);\n"
                + " alert(document.querySelectorAll('html > body')[0]);\n"
                + " alert(document.querySelectorAll('  \\t\\r\\n  html > body  \\t\\r\\n  ').length);\n"
                + "  elem = document.getElementById('root');\n"
                + " alert(elem.querySelectorAll('html > body').length);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<div id='root'>\n"
                + "</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void nthChildNoArgument() {
        final String html
                =  "<html><head><script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "   alert(document.querySelectorAll('li:nth-child()'));\n"
                + "  } catch (e) {alert('exception')}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "  <li id='li3'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"li1", "li4", "li7", "li10"})
    public void nthChildEquation() {
        final String html
                =  "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('li:nth-child(3n+1)');\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].id);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "  <li id='li3'></li>\n"
                + "  <li id='li4'></li>\n"
                + "  <li id='li5'></li>\n"
                + "  <li id='li6'></li>\n"
                + "  <li id='li7'></li>\n"
                + "  <li id='li8'></li>\n"
                + "  <li id='li9'></li>\n"
                + "  <li id='li10'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void invalid() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "   alert(document.querySelectorAll('td:gt(4)').length);\n"
                + "  } catch(e) {alert('exception')}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "ul2"})
    public void directAdjacentSelector() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('p+ul');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='ul1'></ul>\n"
                + "  <p></p>\n"
                + "  <ul id='ul2'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "thing1"})
    public void prefixAttribute() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[id^=\"thing\"]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='something'></ul>\n"
                + "  <p></p>\n"
                + "  <ul id='thing1'></ul>\n"
                + "  <ul id='tHIng2'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "thing1", "tHIng2"})
    public void prefixAttributeCaseInSensitive() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[id^=\"thing\" i]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + " alert(list[1].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='something'></ul>\n"
                + "  <p></p>\n"
                + "  <ul id='thing1'></ul>\n"
                + "  <ul id='tHIng2'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void prefixAttributeEmpty() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[id^=\"\"]');\n"
                + " alert(list.length);\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].outerHTML.replace(/^\\s+|\\s+$/g, ''));\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='something'></ul>\n"
                + "  <p></p>\n"
                + "  <ul id='thing1'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "something"})
    public void suffixAttribute() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[id$=\"thing\"]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='something'></ul>\n"
                + "  <ul id='AnyThinG'></ul>\n"
                + "  <p></p>\n"
                + "  <ul id='thing2'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "something", "AnyThinG"})
    public void suffixAttributeCaseInSensitive() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[id$=\"thing\" I]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + " alert(list[1].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='something'></ul>\n"
                + "  <ul id='AnyThinG'></ul>\n"
                + "  <p></p>\n"
                + "  <ul id='thing2'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void suffixAttributeEmpty() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[id$=\"\"]');\n"
                + " alert(list.length);\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].outerHTML.replace(/^\\s+|\\s+$/g, ''));\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='something'></ul>\n"
                + "  <p></p>\n"
                + "  <ul id='thing2'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "something", "thing2"})
    public void substringAttribute() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[id*=\"thing\"]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + " alert(list[1].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='something'></ul>\n"
                + "  <p></p>\n"
                + "  <ul id='thing2'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "sometHIng", "thinG2"})
    public void substringAttributeInSensitive() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[id*=\"thin\" i ]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + " alert(list[1].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='sometHIng'></ul>\n"
                + "  <p></p>\n"
                + "  <ul id='thinG2'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void substringAttributeEmpty() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[id*=\"\"]');\n"
                + " alert(list.length);\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].outerHTML.replace(/^\\s+|\\s+$/g, ''));\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='something'></ul>\n"
                + "  <p></p>\n"
                + "  <ul id='thing2'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "id1", "id2"})
    public void oneOfAttribute() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[title~=\"w2\"]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + " alert(list[1].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='id1' title='w1 w2 w3'></ul>\n"
                + "  <p id='id2' title='w2'></p>\n"
                + "  <ul id='id3' title='w1w2 w3'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "id1", "id2"})
    public void oneOfAttributeInSensitive() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[title~=\"W2\"i]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + " alert(list[1].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='id1' title='w1 w2 w3'></ul>\n"
                + "  <p id='id2' title='W2'></p>\n"
                + "  <ul id='id3' title='w1w2 w3'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void oneOfAttributeEmpty() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[title~=\"\"]');\n"
                + " alert(list.length);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='id1' title='w1 w2 w3'></ul>\n"
                + "  <p id='id2' title='w2'></p>\n"
                + "  <ul id='id3' title='w1w2 w3'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "id2", "id3"})
    public void hasAttribute() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[title]');\n"
                + " alert(list.length);\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].id);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='id1'></ul>\n"
                + "  <p id='id2' title='w2'></p>\n"
                + "  <ul id='id3' title=''></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"5", "id1", "id2", "id5", "id6", "id7"})
    public void hyphenSeparatedAttributeValue() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[title|=\"abc\"]');\n"
                + " alert(list.length);\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].id);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='id1' title='abc'></ul>\n"
                + "  <p id='id2' title='abc-def'></p>\n"
                + "  <p id='id3' title='x-abc-def'></p>\n"
                + "  <p id='id4' title='abc -def'></p>\n"
                + "  <p id='id5' title='abc- def'></p>\n"
                + "  <p id='id6' title='abc-def gh'></p>\n"
                + "  <p id='id7' title='abc-def-gh'></p>\n"
                + "  <p id='id8' title='xabc'></p>\n"
                + "  <ul id='id9' title='abcd'></ul>\n"
                + "  <p id='id10' title='abc def'></p>\n"
                + "  <p id='id11' title=' abc-def gh'></p>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"5", "id1", "id2", "id5", "id6", "id7"})
    public void hyphenSeparatedAttributeValueInSensitive() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[title|=\"Abc\" i]');\n"
                + " alert(list.length);\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].id);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='id1' title='abc'></ul>\n"
                + "  <p id='id2' title='abc-def'></p>\n"
                + "  <p id='id3' title='x-aBc-def'></p>\n"
                + "  <p id='id4' title='abc -def'></p>\n"
                + "  <p id='id5' title='aBc- def'></p>\n"
                + "  <p id='id6' title='abc-def gh'></p>\n"
                + "  <p id='id7' title='abC-def-gh'></p>\n"
                + "  <p id='id8' title='xabc'></p>\n"
                + "  <ul id='id9' title='abcd'></ul>\n"
                + "  <p id='id10' title='abc def'></p>\n"
                + "  <p id='id11' title=' abc-def gh'></p>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "id1", "id4"})
    public void hyphenSeparatedAttributeValueHyphenInSelector() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[title|=\"ab-c\"]');\n"
                + " alert(list.length);\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].id);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='id1' title='ab-c'></ul>\n"
                + "  <p id='id2' title='ab-cd'></p>\n"
                + "  <ul id='id3' title='ab-c d'></ul>\n"
                + "  <p id='id4' title='ab-c-d'></p>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "id2", "id6"})
    public void hyphenSeparatedAttributeValueEmpty() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[title|=\"\"]');\n"
                + " alert(list.length);\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].id);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='id1' title='abc'></ul>\n"
                + "  <p id='id2' title=''></p>\n"
                + "  <ul id='id3' title=' '></ul>\n"
                + "  <p id='id4' title=' -abc'></p>\n"
                + "  <p id='id5' title=' -abc'></p>\n"
                + "  <p id='id6' title='-abc'></p>\n"
                + "  <p id='id7' title='\\t'></p>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "id3"})
    public void emptyAttributeValue() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('[title=\"\"]');\n"
                + " alert(list.length);\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].id);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <ul id='id1' title='w1'></ul>\n"
                + "  <p id='id2' title=' '></p>\n"
                + "  <ul id='id3' title=''></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "ul2", "ul3"})
    public void generalAdjacentSelector() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('div~ul');\n"
                + " alert(list.length);\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].id);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div></div>\n"
                + "  <p></p>\n"
                + "  <ul id='ul2'></ul>\n"
                + "  <ul id='ul3'></ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"li3", "2", "li1", "li3"})
    public void nthLastChild() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('li:nth-last-child(1)')[0].id);\n"
                + " alert(document.querySelectorAll('li:nth-last-child(odd)').length);\n"
                + " alert(document.querySelectorAll('li:nth-last-child(odd)')[0].id);\n"
                + " alert(document.querySelectorAll('li:nth-last-child(odd)')[1].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "  <li id='li3'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "div1", "div3", "2", "div1", "div3", "2", "div1", "div3", "0"})
    public void nthLastChild2() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('.nthchild1 > :nth-last-child(odd)').length);\n"
                + " alert(document.querySelectorAll('.nthchild1 > :nth-last-child(odd)')[0].id);\n"
                + " alert(document.querySelectorAll('.nthchild1 > :nth-last-child(odd)')[1].id);\n"
                + "  elem = document.getElementById('root');\n"
                + " alert(elem.querySelectorAll('.nthchild1 > :nth-last-child(odd)').length);\n"
                + " alert(elem.querySelectorAll('.nthchild1 > :nth-last-child(odd)')[0].id);\n"
                + " alert(elem.querySelectorAll('.nthchild1 > :nth-last-child(odd)')[1].id);\n"
                + "  elem = document.getElementById('parent');\n"
                + " alert(elem.querySelectorAll('.nthchild1 > :nth-last-child(odd)').length);\n"
                + " alert(elem.querySelectorAll('.nthchild1 > :nth-last-child(odd)')[0].id);\n"
                + " alert(elem.querySelectorAll('.nthchild1 > :nth-last-child(odd)')[1].id);\n"
                + "  elem = document.getElementById('div1');\n"
                + " alert(elem.querySelectorAll('.nthchild1 > :nth-last-child(odd)').length);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<div id='root'>\n"
                + "  <div id='parent' class='nthchild1'>\n"
                + "    <div id='div1'>1</div>\n"
                + "    <div id='div2'>2</div>\n"
                + "    <div id='div3'>3</div>\n"
                + "  </div>\n"
                + "</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("id3")
    public void nthOfType() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('p:nth-of-type(2)')[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<section>\n"
                + "  <h1 id='id11'></h1>\n"
                + "  <p id='id2'></p>\n"
                + "  <p id='id3'></p>\n"
                + "</section>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("id3")
    public void nthLastOfType() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('p:nth-last-of-type(1)')[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<section>\n"
                + "  <h1 id='id1'></h1>\n"
                + "  <p  id='id2'></p>\n"
                + "  <p  id='id3'></p>\n"
                + "</section>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void pseudoAfter() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('#li1:after');\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].id);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "checkbox2", "1", "checkbox2"})
    public void pseudoCheckboxChecked() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('input[type=checkbox]:checked');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + "  var list = document.querySelectorAll('#t2 > input[type=checkbox]:checked');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='t2'>\n"
                + "    <input type='checkbox' name='checkbox1' id='checkbox1' value='foo'>\n"
                + "    <input type='checkbox' name='checkbox2' id='checkbox2' value='bar' checked>\n"
                + "  </div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "radio2", "1", "radio2"})
    public void pseudoRadioChecked() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('input[type=radio]:checked');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + "  var list = document.querySelectorAll('#t2 > input[type=radio]:checked');\n"
                + " alert(list.length);\n"
                + " alert(list[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='t2'>\n"
                + "    <input type='radio' name='radio1' id='radio1' value='foo'>\n"
                + "    <input type='radio' name='radio2' id='radio2' value='bar' checked>\n"
                + "  </div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"theform", "id3"})
    public void pseudoInvalid() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll(':invalid');\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].id);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<form id='theform'>\n"
                + "  <input id='id1' type='text' value='foo' required>\n"
                + "  <input id='id2' type='text' value=''>\n"
                + "  <input id='id3' type='text' value='' required>\n"
                + "  <input id='id4' type='text' minLength='2' maxLength='5' value='foo'>\n"
                + "  <input id='id5' type='text' maxLength='2' value='foo'>\n"
                + "  <input id='id6' type='text' minLength='5' value='foo'>\n"
                + "  <p id='id7'>foo</p>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"id1", "id2", "id4", "id5", "id6"})
    public void pseudoValid() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll(':valid');\n"
                + "  for (var i = 0 ; i < list.length; i++) {\n"
                + "   alert(list[i].id);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<form id='theform'>\n"
                + "  <input id='id1' type='text' value='foo' required>\n"
                + "  <input id='id2' type='text' value=''>\n"
                + "  <input id='id3' type='text' value='' required>\n"
                + "  <input id='id4' type='text' minLength='2' maxLength='5' value='foo'>\n"
                + "  <input id='id5' type='text' maxLength='2' value='foo'>\n"
                + "  <input id='id6' type='text' minLength='5' value='foo'>\n"
                + "  <p id='id7'>foo</p>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("li1")
    public void firstChild() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('li:first-child')[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "  <li id='li3'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("li3")
    public void lastChild() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('li:last-child')[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "  <li id='li3'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("id2")
    public void firstOfType() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('p:first-of-type')[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<section>\n"
                + "  <h1 id='id1'></h1>\n"
                + "  <p  id='id2'></p>\n"
                + "  <h1 id='id3'></h1>\n"
                + "  <p  id='id4'></p>\n"
                + "  <h1 id='id5'></h1>\n"
                + "</section>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "link_2", "link_3"})
    public void invalidNot() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var found = document.querySelectorAll('p a:not(a:first-of-type)');\n"
                + "   alert(found.length);\n"
                + "   alert(found[0].id);\n"
                + "   alert(found[1].id);\n"
                + "  } catch(e) {alert('exception')}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<p>\n"
                + "  <strong id='strong'>This</strong> is a short blurb\n"
                + "  <a id='link_1' href='#'>with a link</a> or\n"
                + "  <a id='link_2' href='#'>two</a>.\n"
                + "  <a id='link_3' href='#'>three</a>.\n"
                + "  Or <cite id='withTitle' title='hello world!'>a citation</cite>.\n"
                + "</p>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("id4")
    public void lastOfType() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('p:last-of-type')[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<section>\n"
                + "  <h1 id='id1'></h1>\n"
                + "  <p  id='id2'></p>\n"
                + "  <h1 id='id3'></h1>\n"
                + "  <p  id='id4'></p>\n"
                + "  <h1 id='id5'></h1>\n"
                + "</section>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("id3")
    public void onlyChild() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('h1:only-child')[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<section>\n"
                + "  <h1 id='id1'></h1>\n"
                + "  <p  id='id2'></p>\n"
                + "</section>\n"
                + "<section>\n"
                + "  <h1 id='id3'></h1>\n"
                + "</section>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("id3")
    public void onlyOfType() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('p:only-of-type')[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<section>\n"
                + "  <p  id='id1'></p>\n"
                + "  <p  id='id2'></p>\n"
                + "</section>\n"
                + "<section>\n"
                + "  <p  id='id3'></p>\n"
                + "</section>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"id2", "span1"})
    public void empty() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('p:empty')[0].id);\n"
                + " alert(document.querySelectorAll('span:empty')[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <p id='id1'>Hello, World!</p>\n"
                + "  <p id='id2'></p>\n"
                + "  <span id='span1'><!-- a comment --></span>\n"
                + "  <span id='span2'>a text</span>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("id2")
    public void not() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.querySelectorAll('input:not([type=\"file\"])')[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='id1' type='file'>\n"
                + "  <input id='id2'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("id2")
    public void notWithFirstOfType() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "   alert(document.querySelectorAll('div:not(div:first-of-type)')[0].id);\n"
                + "  } catch(e) {alert('exception')}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='id1'>1</div>\n"
                + "  <div id='id2'>2</div>\n"
                + "  <div id='id3'>3</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "id2", "id3", "2", "id1", "id3", "2", "id1", "id2",
            "3", "id1", "id2", "id3"})
    public void notWithNthOfType() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var res = document.querySelectorAll('div:not(div:nth-of-type(1))');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].id);\n"
                + "   alert(res[1].id);\n"
                + "    res = document.querySelectorAll('div:not(div:nth-of-type(2))');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].id);\n"
                + "   alert(res[1].id);\n"
                + "    res = document.querySelectorAll('div:not(div:nth-of-type(3))');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].id);\n"
                + "   alert(res[1].id);\n"
                + "    res = document.querySelectorAll('div:not(div:nth-of-type(4))');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].id);\n"
                + "   alert(res[1].id);\n"
                + "   alert(res[2].id);\n"
                + "  } catch(e) {alert('exception')}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='id1'>1</div>\n"
                + "  <div id='id2'>2</div>\n"
                + "  <div id='id3'>3</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("id2")
    public void notWithLastOfType() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "   alert(document.querySelectorAll('div:not(div:last-of-type)')[1].id);\n"
                + "  } catch(e) {alert('exception')}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='id1'>1</div>\n"
                + "  <div id='id2'>2</div>\n"
                + "  <div id='id3'>3</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "id1", "id2", "2", "id1", "id3", "2", "id2", "id3",
            "3", "id1", "id2", "id3"})
    public void notWithNthLastOfType() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var res = document.querySelectorAll('div:not(div:nth-last-of-type(1))');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].id);\n"
                + "   alert(res[1].id);\n"
                + "    res = document.querySelectorAll('div:not(div:nth-last-of-type(2))');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].id);\n"
                + "   alert(res[1].id);\n"
                + "    res = document.querySelectorAll('div:not(div:nth-last-of-type(3))');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].id);\n"
                + "   alert(res[1].id);\n"
                + "    res = document.querySelectorAll('div:not(div:nth-last-of-type(4))');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].id);\n"
                + "   alert(res[1].id);\n"
                + "   alert(res[2].id);\n"
                + "  } catch(e) {alert('exception')}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='id1'>1</div>\n"
                + "  <div id='id2'>2</div>\n"
                + "  <div id='id3'>3</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "item_2", "item_3"})
    public void childNot() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=9'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var res = document.querySelectorAll('#list li:not(#item_1)');\n"
                + " alert(res.length);\n"
                + " alert(res[0].id);\n"
                + " alert(res[1].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <ul id='list'>\n"
                + "    <li id='item_1'>1</li>\n"
                + "    <li id='item_2'>2</li>\n"
                + "    <li id='item_3'>3</li>\n"
                + "  </ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "item_2"})
    public void childNotNot() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=9'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var res = document.querySelectorAll('#list li:not(#item_1):not(#item_3)');\n"
                + " alert(res.length);\n"
                + " alert(res[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <ul id='list'>\n"
                + "    <li id='item_1'>1</li>\n"
                + "    <li id='item_2'>2</li>\n"
                + "    <li id='item_3'>3</li>\n"
                + "  </ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "undefined", "1", "[object HTMLInputElement]", "id2"})
    public void focus() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  found = document.querySelectorAll(':focus');\n"
                + " alert(found.length);\n"
                + " alert(found[0]);\n"
                + "\n"
                + "  document.getElementById('id2').focus();\n"
                + "\n"
                + "  found = document.querySelectorAll(':focus');\n"
                + " alert(found.length);\n"
                + " alert(found[0]);\n"
                + " alert(found[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='setTimeout(test, 10);'>\n"
                + "  <form id='id0'>\n"
                + "    <input id='id1'>\n"
                + "    <input id='id2'>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "undefined",
            "4", "[object HTMLHtmlElement]", "[object HTMLBodyElement]",
            "[object HTMLFormElement]", "id0",
            "[object HTMLInputElement]", "id2"})
    public void focusWithin() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  found = document.querySelectorAll(':focus-within');\n"
                + " alert(found.length);\n"
                + " alert(found[0]);\n"
                + "\n"
                + "  document.getElementById('id2').focus();\n"
                + "\n"
                + "  found = document.querySelectorAll(':focus-within');\n"
                + " alert(found.length);\n"
                + " alert(found[0]);\n"
                + " alert(found[1]);\n"
                + " alert(found[2]);\n"
                + " alert(found[2].id);\n"
                + " alert(found[3]);\n"
                + " alert(found[3].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='setTimeout(test, 10);'>\n"
                + "  <form id='id0'>\n"
                + "    <input id='id1'>\n"
                + "    <input id='id2'>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "undefined", "1", "[object HTMLInputElement]", "id2"})
    public void focusVisible() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  found = document.querySelectorAll(':focus-visible');\n"
                + " alert(found.length);\n"
                + " alert(found[0]);\n"
                + "\n"
                + "  document.getElementById('id2').focus();\n"
                + "\n"
                + "  found = document.querySelectorAll(':focus-visible');\n"
                + " alert(found.length);\n"
                + " alert(found[0]);\n"
                + " alert(found[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='setTimeout(test, 10);'>\n"
                + "  <form id='id0'>\n"
                + "    <input id='id1'>\n"
                + "    <input id='id2'>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"5", "cb1", "rd1", "sl1", "ml1", "ml3"})
    public void checked() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  found = document.querySelectorAll(':checked');\n"
                + " alert(found.length);\n"
                + "  for (var i = 0; i < found.length; i++) {alert(found[i].id); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='id1'>\n"
                + "  <input id='id2' disabled='disabled'>\n"
                + "  <input id='id3' type'hidden'>\n"
                + "  <input type='checkbox' name='checkboxes' id='cb1' checked='checked' value='On' />\n"
                + "  <input type='checkbox' name='checkboxes' id='cb2' value='Off' />\n"
                + "  <input type='radio' name='radiobuttons' id='rd1' checked='checked' value='On' />\n"
                + "  <input type='radio' name='radiobuttons' id='rd2' value='Off' />\n"
                + "  <select name='sl'>\n"
                + "    <option value='sl1' id='sl1' selected='selected'>SL One</option>\n"
                + "    <option value='sl2' id='sl2' >SL Two</option>\n"
                + "  </select>\n"
                + "  <select name='ml'  multiple=multiple'>\n"
                + "    <option value='ml1' id='ml1' selected='selected'>ML One</option>\n"
                + "    <option value='ml2' id='ml2' >ML Two</option>\n"
                + "    <option value='ml3' id='ml3' selected='selected'>ML Three</option>\n"
                + "  </select>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "cb1", "rd1", "2", "cb2", "rd2"})
    public void checkedChanged() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  found = document.querySelectorAll(':checked');\n"
                + " alert(found.length);\n"
                + "  for (var i = 0; i < found.length; i++) {alert(found[i].id); }\n"
                + "  document.getElementById('cb1').checked = false;\n"
                + "  document.getElementById('cb2').checked = true;\n"
                + "  document.getElementById('rd1').checked = false;\n"
                + "  document.getElementById('rd2').checked = true;\n"
                + "  found = document.querySelectorAll(':checked');\n"
                + " alert(found.length);\n"
                + "  for (var i = 0; i < found.length; i++) {alert(found[i].id); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input type='checkbox' name='checkboxes' id='cb1' checked='checked' value='On' />\n"
                + "  <input type='checkbox' name='checkboxes' id='cb2' value='Off' />\n"
                + "  <input type='radio' name='radiobuttons' id='rd1' checked='checked' value='On' />\n"
                + "  <input type='radio' name='radiobuttons' id='rd2' value='Off' />\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "cb1", "rd1", "2", "cb1", "rd1"})
    public void checkedAttribute() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  found = document.querySelectorAll('[checked]');\n"
                + " alert(found.length);\n"
                + "  for (var i = 0; i < found.length; i++) {alert(found[i].id); }\n"
                + "  document.getElementById('cb1').checked = false;\n"
                + "  document.getElementById('cb2').checked = true;\n"
                + "  document.getElementById('rd1').checked = false;\n"
                + "  document.getElementById('rd2').checked = true;\n"
                + "  found = document.querySelectorAll('[checked]');\n"
                + " alert(found.length);\n"
                + "  for (var i = 0; i < found.length; i++) {alert(found[i].id); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input type='checkbox' name='checkboxes' id='cb1' checked='checked' value='On' />\n"
                + "  <input type='checkbox' name='checkboxes' id='cb2' value='Off' />\n"
                + "  <input type='radio' name='radiobuttons' id='rd1' checked='checked' value='On' />\n"
                + "  <input type='radio' name='radiobuttons' id='rd2' value='Off' />\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "1-iy", "1-iy", "2", "1-iy", "1-iz"})
    public void selectedChecked() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + " alert(document.getElementById('s1').selectedIndex);\n"
                + "  var sel = document.querySelectorAll('[selected]');\n"
                + " alert(sel.length + '-' + sel[0].id);\n"
                + "  sel = document.querySelectorAll(':checked');\n"
                + " alert(sel.length + '-' + sel[0].id);\n"
                + "  document.getElementById('iz').selected = 'selected';\n"
                + " alert(document.getElementById('s1').selectedIndex);\n"
                + "  var sel = document.querySelectorAll('[selected]');\n"
                + " alert(sel.length + '-' + sel[0].id);\n"
                + "  sel = document.querySelectorAll(':checked');\n"
                + " alert(sel.length + '-' + sel[0].id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <select id='s1'>\n"
                + "    <option id='ix' value='x'>x</option>\n"
                + "    <option id='iy' value='y' selected>y</option> \n"
                + "    <option id='iz' value='z'>z</option> \n"
                + "  </select>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "id1", "id3"})
    public void enabled() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  found = document.querySelectorAll('input:enabled');\n"
                + " alert(found.length);\n"
                + "  for (var i = 0; i < found.length; i++) {alert(found[i].id); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='id1'>\n"
                + "  <input id='id2' disabled='disabled'>\n"
                + "  <input id='id3' type'hidden'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "id2"})
    public void disabled() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  found = document.querySelectorAll('input:disabled');\n"
                + " alert(found.length);\n"
                + "  for (var i = 0; i < found.length; i++) {alert(found[i].id); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='id1' >\n"
                + "  <input id='id2' disabled='disabled'>\n"
                + "  <input id='id3' type'hidden'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "id2"})
    public void target() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + "  found = document.querySelectorAll(':target');\n"
                        + " alert(found.length);\n"
                        + "  if (found.length > 0) {alert(found[0].id); }\n"
                        + "}\n"
                        + "</script></head>\n"
                        + "<body onload='test()'>\n"
                        + "  <input id='id1' >\n"
                        + "  <input id='id2'>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void targetNoHash() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  found = document.querySelectorAll(':target');\n"
                + " alert(found.length);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='id1' >\n"
                + "  <input id='id2'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void targetUnknown() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  found = document.querySelectorAll(':target');\n"
                + " alert(found.length);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='id1' >\n"
                + "  <input id='id2'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "[object HTMLHtmlElement]"})
    public void root() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll(':root');\n"
                + " alert(list.length);\n"
                + " alert(list[0]);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "[object HTMLHeadingElement]"})
    public void has() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var list = document.querySelectorAll('h1:has(p)');\n"
                + "   alert(list.length);\n"
                + "   alert(list[0]);\n"
                + "  } catch(e) {alert('exception')}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<h1>abc</h1>\n"
                + "<h1><p>de</p></h1>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"first", "second"})
    public void escapedAttributeValue() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "</head><body>\n"
                + "  <input id='first' name='foo[bar]'>\n"
                + "  <input id='second' name='foo.bar'>\n"
                + "<script>\n"
                + "try {\n"
                + " alert(document.querySelectorAll('input[name=foo\\\\[bar\\\\]]')[0].id);\n"
                + "} catch(e) {alert(e)}\n"
                + "try {\n"
                + " alert(document.querySelectorAll('input[name=foo\\\\.bar]')[0].id);\n"
                + "} catch(e) {alert(e)}\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"6", "3"})
    public void differentWhitespaceClassName() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "</head><body>\n"
                + "  <input id='first' class='foo'>\n"
                + "  <input id='first' class='\tfoo\n'>\n"
                + "  <input id='first' class='foo bar'>\n"
                + "  <input id='second' class='foo\tbar'>\n"
                + "  <input id='third' class='foo\r\nbar'>\n"
                + "  <input id='third' class='foobar foo'>\n"
                + "  <input id='third' class='foobar'>\n"
                + "  <input id='third' class='abcfoobar'>\n"
                + "<script>\n"
                + "try {\n"
                + " alert(document.querySelectorAll('.foo').length);\n"
                + " alert(document.querySelectorAll('.bar').length);\n"
                + "} catch(e) {alert('exception')}\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"first", "second", "third"})
    public void escapedClassName() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "</head><body>\n"
                + "  <input id='first' class='foo[bar]'>\n"
                + "  <input id='second' class='foo.bar'>\n"
                + "  <input id='third' class='foo:bar'>\n"
                + "<script>\n"
                + "try {\n"
                + " alert(document.querySelectorAll('.foo\\\\[bar\\\\]')[0].id);\n"
                + " alert(document.querySelectorAll('.foo\\\\.bar')[0].id);\n"
                + " alert(document.querySelectorAll('.foo\\\\:bar')[0].id);\n"
                + "} catch(e) {alert('exception')}\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"silly:id::with:colons", "silly:id::with:colons", "silly~id", "silly~id"})
    public void escapedId() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "</head><body>\n"
                + "  <input id='silly:id::with:colons'>\n"
                + "  <input id='silly~id'>\n"
                + "<script>\n"
                + "try {\n"
                + " alert(document.querySelectorAll('#silly\\\\:id\\\\:\\\\:with\\\\:colons')[0].id);\n"
                + " alert(document.querySelectorAll(\"#silly\\\\:id\\\\:\\\\:with\\\\:colons\")[0].id);\n"
                + " alert(document.querySelectorAll('#silly\\\\~id')[0].id);\n"
                + " alert(document.querySelectorAll(\"#silly\\\\~id\")[0].id);\n"
                + "} catch(e) {alert('exception ' + e)}\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void invalidSelectors() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var list = document.querySelectorAll('li:foo() ~ li');\n"
                + "   alert(list.length);\n"
                + "  } catch(e) {alert('exception')}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul id='ul'>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void activeEmptyDetached() {
        emptyAndDetached("*:active");
        emptyAndDetached(":active");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void checkedEmptyDetached() {
        emptyAndDetached("*:checked");
        emptyAndDetached(":checked");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void disabledEmptyDetached() {
        emptyAndDetached("*:disabled");
        emptyAndDetached(":disabled");
    }

    @Test
    @Alerts({"null", "null", "[object HTMLSpanElement]"})
    public void emptyEmptyDetached() {
        emptyAndDetached("*:empty");
        emptyAndDetached(":empty");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void enabledEmptyDetached() {
        emptyAndDetached("*:enabled");
        emptyAndDetached(":enabled");
    }

    @Test
    @Alerts({"null", "null", "[object HTMLSpanElement]"})
    public void firstchildEmptyDetached() {
        emptyAndDetached("*:first-child");
        emptyAndDetached(":first-child");
    }

    @Test
    @Alerts({"null", "null", "[object HTMLSpanElement]"})
    public void firstoftypeEmptyDetached() {
        emptyAndDetached("*:first-of-type");
        emptyAndDetached(":first-of-type");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void focusEmptyDetached() {
        emptyAndDetached("*:focus");
        emptyAndDetached(":focus");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void focusWithinEmptyDetached() {
        emptyAndDetached("*:focus-within");
        emptyAndDetached(":focus-within");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void focusVisibleEmptyDetached() {
        emptyAndDetached("*:focus-visible");
        emptyAndDetached(":focus-visible");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void hoverEmptyDetached() {
        emptyAndDetached("*:hover");
        emptyAndDetached(":hover");
    }

    @Test
    @Alerts({"null", "null", "[object HTMLSpanElement]"})
    public void lastchildEmptyDetached() {
        emptyAndDetached("*:last-child");
        emptyAndDetached(":last-child");
    }

    @Test
    @Alerts({"null", "null", "[object HTMLSpanElement]"})
    public void lastoftypeEmptyDetached() {
        emptyAndDetached("*:last-of-type");
        emptyAndDetached(":last-of-type");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void linkEmptyDetached() {
        emptyAndDetached("*:link");
        emptyAndDetached(":link");
    }

    @Test
    @Alerts({"null", "null", "[object HTMLSpanElement]"})
    public void notEmptyDetached() {
        emptyAndDetached("*:not(p)");
        emptyAndDetached(":not(p)");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void nthchildEmptyDetached() {
        emptyAndDetached("*:nth-child(2n)");
        emptyAndDetached(":nth-child(2n)");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void nthlastchildEmptyDetached() {
        emptyAndDetached("*:nth-last-child(2n)");
        emptyAndDetached(":nth-last-child(2n)");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void nthoftypeEmptyDetached() {
        emptyAndDetached("*:nth-of-type(2n)");
        emptyAndDetached(":nth-of-type(2n)");
    }

    @Test
    @Alerts({"null", "null", "[object HTMLSpanElement]"})
    public void onlychildEmptyDetached() {
        emptyAndDetached("*:only-child");
        emptyAndDetached(":only-child");
    }

    @Test
    @Alerts({"null", "null", "[object HTMLSpanElement]"})
    public void onlyoftypeEmptyDetached() {
        emptyAndDetached("*:only-of-type");
        emptyAndDetached(":only-of-type");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void rootEmptyDetached() {
        emptyAndDetached("*:root");
        emptyAndDetached(":root");
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void visitedEmptyDetached() {
        emptyAndDetached("*:visited");
        emptyAndDetached(":visited");
    }

    private void emptyAndDetached(final String selector) {
        final String html =
                "<html><head>\n"
                        + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + "  var div = document.getElementById('myDiv');\n"
                        + "  try {\n"
                        + "    found = div.querySelector('" + selector + "');\n"
                        + "   alert(found);\n"
                        + "  } catch(e) {alert('exception')}\n"

                        + "  div = document.createElement('div');\n"
                        + "  try {\n"
                        + "    found = div.querySelector('" + selector + "');\n"
                        + "   alert(found);\n"
                        + "  } catch(e) {alert('exception')}\n"

                        + "  var input = document.createElement('span');\n"
                        + "  div.appendChild(input);\n"
                        + "  try {\n"
                        + "    found = div.querySelector('" + selector + "');\n"
                        + "   alert(found);\n"
                        + "  } catch(e) {alert('exception')}\n"
                        + "}\n"
                        + "</script></head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='myDiv'></myDiv>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "<nested>Three</nested>", "Four",
            "1", "Two", "0", "0"})
    public void xmlTagName() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "</head><body>\n"
                + "<script>\n"
                + "  var xmlString = [\n"
                + "                 '<ResultSet>',\n"
                + "                 '<Result>One</Result>',\n"
                + "                 '<RESULT>Two</RESULT>',\n"
                + "                 '<result><nested>Three</nested></result>',\n"
                + "                 '<result>Four</result>',\n"
                + "                 '</ResultSet>'\n"
                + "                ].join('');\n"
                + "  if (window.DOMParser) {\n"
                + "    var parser = new DOMParser();\n"
                + "    xml = parser.parseFromString(xmlString, 'text/xml');\n"
                + "  } else { // IE\n"
                + "    var parser = new ActiveXObject('Microsoft.XMLDOM');\n"
                + "    parser.async = 'false';\n"
                + "    parser.loadXML(xmlString);\n"
                + "  }\n"
                + "  var xmlDoc = parser.parseFromString(xmlString, 'text/xml');\n"
                + "  var de = xmlDoc.documentElement;\n"
                + "  try {\n"
                + "    var res = de.querySelectorAll('result');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].innerHTML);\n"
                + "   alert(res[1].innerHTML);\n"
                + "    res = de.querySelectorAll('RESULT');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].innerHTML);\n"
                + "    res = de.querySelectorAll('resulT');\n"
                + "   alert(res.length);\n"
                + "    res = de.querySelectorAll('rEsulT');\n"
                + "   alert(res.length);\n"
                + "  } catch(e) {alert('exception ' + e)}\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "ONE", "<CHILD>Two</CHILD>",
            "0",
            "2", "ONE", "<CHILD>Two</CHILD>",
            "1", "ONE",
            "1", "Two"})
    public void xmlAttribute() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "</head><body>\n"
                + "<script>\n"
                + "  var xmlString = [\n"
                + "                 '<ResultSet>',\n"
                + "                 '<RESULT thinger=\"blah\">ONE</RESULT>',\n"
                + "                 '<RESULT thinger=\"gadzooks\"><CHILD>Two</CHILD></RESULT>',\n"
                + "                 '</ResultSet>'\n"
                + "                ].join('');\n"
                + "  if (window.DOMParser) {\n"
                + "    var parser = new DOMParser();\n"
                + "    xml = parser.parseFromString(xmlString, 'text/xml');\n"
                + "  } else { // IE\n"
                + "    var parser = new ActiveXObject('Microsoft.XMLDOM');\n"
                + "    parser.async = 'false';\n"
                + "    parser.loadXML(xmlString);\n"
                + "  }\n"
                + "  var xmlDoc = parser.parseFromString(xmlString, 'text/xml');\n"
                + "  var de = xmlDoc.documentElement;\n"
                + "  try {\n"
                + "    var res = de.querySelectorAll('RESULT');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].innerHTML);\n"
                + "   alert(res[1].innerHTML);\n"
                + "    res = de.querySelectorAll('RESULT[THINGER]');\n"
                + "   alert(res.length);\n"
                + "    res = de.querySelectorAll('RESULT[thinger]');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].innerHTML);\n"
                + "   alert(res[1].innerHTML);\n"
                + "    res = de.querySelectorAll('RESULT[thinger=blah]');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].innerHTML);\n"
                + "    res = de.querySelectorAll('RESULT > CHILD');\n"
                + "   alert(res.length);\n"
                + "   alert(res[0].innerHTML);\n"
                + "  } catch(e) {alert('exception ' + e)}\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"exception", "exception"})
    public void querySelectorInvalid() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "   alert(document.querySelectorAll('#foo > :not(:first)'));\n"
                + "  } catch(e) {alert('exception')}\n"
                + "  try {\n"
                + "   alert(document.querySelector('#foo > :not(:first)'));\n"
                + "  } catch(e) {alert('exception')}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<ul id='foo'>\n"
                + "  <li id='li1'></li>\n"
                + "  <li id='li2'></li>\n"
                + "  <li id='li3'></li>\n"
                + "</ul>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "S1", "S2",
            "2", "S1", "S2",
            "2", "S1", "S2",
            "2", "S1", "S2"})
    public void typeSubmit() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('button[type=\"submit\"]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].innerHTML);\n"
                + " alert(list[1].innerHTML );\n"
                + "  var list = document.querySelectorAll('button[type=\"SubMit\"]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].innerHTML);\n"
                + " alert(list[1].innerHTML );\n"
                + "  var list = document.querySelectorAll('button[type=\"SUBmit\"]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].innerHTML);\n"
                + " alert(list[1].innerHTML );\n"
                + "  var list = document.querySelectorAll('button[type=\"SUBmit\" i]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].innerHTML);\n"
                + " alert(list[1].innerHTML );\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <button>None</button>\n"
                + "  <button type=''>Empty</button>\n"
                + "  <button type='submit'>S1</button>\n"
                + "  <button type='SubMit'>S2</button>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "I1"})
    public void buttonTypeInvalid() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var list = document.querySelectorAll('button[type=\"invalid\"]');\n"
                + " alert(list.length);\n"
                + " alert(list[0].innerHTML);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <button>None</button>\n"
                + "  <button type=''>Empty</button>\n"
                + "  <button type='submit'>S1</button>\n"
                + "  <button type='invalid'>I1</button>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
