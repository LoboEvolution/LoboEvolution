/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.htmlunit.cssparser.dom.CSSStyleDeclarationImpl;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link CSSStyleDeclarationImpl}.
 */

public class CSSStyleDeclarationTest extends LoboUnitTest {

    @Test
    public void styleOneCssAttribute() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var node = document.getElementById('div1');\n"
                + "  var style = node.style;\n"
                + "  alert(style.color);\n"
                + "  style.color = 'pink';\n"
                + "  alert(style.color);\n"
                + "  alert(node.getAttribute('style'));\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'><div id='div1' style='color: black'>foo</div></body></html>";
        final String[] messages = {"black", "pink", "color: pink;"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void styleMultipleCssAttributes() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var style = document.getElementById('div1').style;\n"
                + "  alert(style.color);\n"
                + "  style.color = 'pink';\n"
                + "  alert(style.color);\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='div1' style='color: black;background:blue;foo:bar'>foo</div></body></html>";

        final String[] messages = {"black", "pink"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void styleOneUndefinedCssAttribute() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var style = document.getElementById('div1').style;\n"
                + "  alert(document.getElementById('nonexistingid'));\n"
                + "  alert(style.color);\n"
                + "  style.color = 'pink';\n"
                + "  alert(style.color);\n"
                + "  alert(document.getElementById('div1').getAttribute('style'));\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'><div id='div1'>foo</div></body></html>";

        final String[] messages = {null, null, "pink", "color: pink;"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void accessProperties() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var oDiv = document.getElementById('div1');\n"
                + "  alert(typeof oDiv.style.visibility);\n"
                + "  alert(typeof oDiv.style.color);\n"
                + "  alert(typeof oDiv.style.backgroundImage);\n"
                + "  alert(typeof oDiv.style.foo);\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='div1'>foo</div></body></html>";
        final String[] messages = {"string", "string", "string", "undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getPropertyValue() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  try {\n"
                + "    var oDiv1 = document.getElementById('div1');\n"
                + "    alert(oDiv1.style.getPropertyValue('background'));\n"
                + "  } catch(e) { alert('exception'); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='div1' style='background: blue'>foo</div></body></html>";
        final String[] messages = {"blue"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void removeProperty() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var oDiv1 = document.getElementById('div1');\n"
                + "  if (oDiv1.style.removeProperty) {\n"
                + "    var value = oDiv1.style.removeProperty('color');\n"
                + "    alert('*' + value + '* ' + typeof(value));\n"
                + "    alert(oDiv1.style.cssText);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='div1' style='color: blue'>foo</div></body></html>";
        final String[] messages = {"*blue* string", ""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void removePropertyUnknown() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var oDiv1 = document.getElementById('div1');\n"
                + "  if (oDiv1.style.removeProperty) {\n"
                + "    var value = oDiv1.style.removeProperty('font-size');\n"
                + "    alert('*' + value + '* ' + typeof(value));\n"
                + "    alert(oDiv1.style.getPropertyValue('color'));\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='div1' style='color: blue'>foo</div></body></html>";
        final String[] messages = {"** string", "blue"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void removePropertyUndefined() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var oDiv1 = document.getElementById('div1');\n"
                + "  if (!oDiv1.style.removeProperty) {\n"
                + "    alert('removeProperty not available');\n"
                + "    return;\n"
                + "  }\n"
                + "  var value = oDiv1.style.removeProperty(undefined);\n"
                + "  alert('*' + value + '* ' + typeof(value));\n"
                + "  alert(oDiv1.style.getPropertyValue('color'));\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='div1' style='color: blue'>foo</div></body></html>";
        final String[] messages = {"** string", "blue"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getPropertyValue_WithDash() {
        final String html =
                "<html><body onload='test()'><script>\n"
                        + "    function prop(elem, prop) {\n"
                        + "      try{\n"
                        + "        var p = span.style.getPropertyValue(prop);\n"
                        + "        alert(p);\n"
                        + "      } catch (e) { alert('exception'); }\n"
                        + "    }\n"
                        + "    function test() {\n"
                        + "      var span = document.getElementById('span');\n"
                        + "      span.style['fontSize'] = '30px';\n"
                        + "      alert(span.style.fontSize);\n"
                        + "      prop(span, 'fontSize');\n"
                        + "      prop(span, 'font-size');\n"
                        + "      span.style['fontFamily'] = 'arial';\n"
                        + "      alert(span.style.fontFamily);\n"
                        + "      prop(span, 'fontFamily');\n"
                        + "      prop(span, 'font-family');\n"
                        + "    }\n"
                        + "</script>\n"
                        + "<span id='span'>x</span>\n"
                        + "</body></html>";
        final String[] messages = {"30px", null, "30px", "arial", null, "arial"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void styleFilter() {
        final String html = "<html><body onload='test()'><script>\n"
                + "   function test() {\n"
                + "     var div1 = document.getElementById('div1');\n"
                + "     alert(div1.style.filter);\n"
                + "     var div2 = document.getElementById('div2');\n"
                + "     alert(div2.style.filter);\n"
                + "   }\n"
                + "</script>\n"
                + "<div id='div1'>foo</div>\n"
                + "<div id='div2' style='filter:alpha(opacity=50)'>bar</div>\n"
                + "</body></html>";
        final String[] messages = {"", ""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void initOpacity() {
        final String html = "<html><body>\n"
                + "<div id='o1' style='opacity: '>d</div>\n"
                + "<div id='o2' style='opacity:0.5'>d</div>\n"
                + "<div id='o3' style='opacity:.4'>d</div>\n"
                + "<div id='o4' style='opacity: 0.33333'>d</div>\n"
                + "<div id='o5' style='opacity: -3'>d</div>\n"
                + "<div id='o6' style='opacity: 3'>d</div>\n"
                + "<div id='o7' style='opacity: 10px'>d</div>\n"
                + "<div id='o8' style='opacity: foo'>d</div>\n"
                + "<div id='o9' style='opacity: auto'>d</div>\n"
                + "<script>\n"
                + "for (var i = 1; i < 10; i++) {\n"
                + "  d = document.getElementById('o' + i);\n"
                + "  alert(d.style.opacity);\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"", "0.5", "0.4", "0.33333", "-3", "3", "", "", ""};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void setOpacity() {
        final String html = "<html><body>\n"
                + "<div id='d'>d</div>\n"
                + "<script>\n"
                + "var d = document.getElementById('d');\n"
                + "var s = '-';\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = 0.5;\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = .4;\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = 0.33333;\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = -3;\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = 3;\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = '8';\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = ' 7 ';\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = NaN;\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = '10px';\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = 'foo';\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = 'auto';\n"
                + "s += d.style.opacity + ' ';\n"
                + "d.style.opacity = '';\n"
                + "s += d.style.opacity;\n"
                + "alert(s);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"- 0.5 0.4 0.33333 -3 3 8 7 7 7 7 7 "};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setExpression() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var div1 = document.getElementById('div1');\n"
                + "    alert(typeof div1.style.setExpression);\n"
                + "    div1.style.setExpression('title','id');\n"
                + "  } catch(e) { alert('exception'); }\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1'/>\n"
                + "</body></html>";
        final String[] messages = {"undefined", "exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void removeExpression() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var div1 = document.getElementById('div1');\n"
                + "    alert(typeof div1.style.removeExpression);\n"
                + "    div1.style.setExpression('title','id');\n"
                + "    div1.style.removeExpression('title');\n"
                + "  } catch(e) { alert('exception'); }\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1'/>\n"
                + "</body></html>";
        final String[] messages = {"undefined", "exception"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void borderStyles_noStyle() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var oDiv = document.getElementById('div1');\n"
                + "  alert(oDiv.style.borderBottom);\n"
                + "  alert(oDiv.style.borderBottomColor);\n"
                + "  alert(oDiv.style.borderBottomStyle);\n"
                + "  alert(oDiv.style.borderBottomWidth);\n"
                + "  alert(oDiv.style.borderLeft);\n"
                + "  alert(oDiv.style.borderLeftColor);\n"
                + "  alert(oDiv.style.borderLeftStyle);\n"
                + "  alert(oDiv.style.borderLeftWidth);\n"
                + "  alert(oDiv.style.borderRight);\n"
                + "  alert(oDiv.style.borderRightColor);\n"
                + "  alert(oDiv.style.borderRightStyle);\n"
                + "  alert(oDiv.style.borderRightWidth);\n"
                + "  alert(oDiv.style.borderTop);\n"
                + "  alert(oDiv.style.borderTopColor);\n"
                + "  alert(oDiv.style.borderTopStyle);\n"
                + "  alert(oDiv.style.borderTopWidth);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='div1'>foo</div></body></html>";
        final String[] messages = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void borderXxxWidth() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var oDiv = document.getElementById('div1');\n"
                + "  alert(oDiv.style.borderBottomWidth);\n"
                + "  alert(oDiv.style.borderLeftWidth);\n"
                + "  alert(oDiv.style.borderRightWidth);\n"
                + "  alert(oDiv.style.borderTopWidth);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='div1' style='border-width: 1px 2px 3px 4px'>foo</div></body></html>";
        final String[] messages = {"3px", "4px", "2px", "1px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void borderXxxWidthConstants() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var oDiv = document.getElementById('div1');\n"
                + "  alert(oDiv.style.borderRightWidth);\n"
                + "  oDiv = document.getElementById('div2');\n"
                + "  alert(oDiv.style.borderLeftWidth);\n"
                + "  oDiv = document.getElementById('div3');\n"
                + "  alert(oDiv.style.borderBottomWidth);\n"
                + "  alert(oDiv.style.borderTopWidth);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='div1' style='border: thin'>foo</div>\n"
                + "<div id='div2' style='border: medium'>foo</div>\n"
                + "<div id='div3' style='border: thick'>foo</div>\n"
                + "</body></html>";
        final String[] messages = {"thin", "medium", "thick", "thick"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void initUnsupportdProperty() {
        final String html = "<html><body>\n"
                + "<div id='my' style='htmlunit: foo'>d</div>\n"
                + "<script>\n"
                + "  d = document.getElementById('my');\n"
                + "  alert(d.style.htmlunit);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setUnsupportdProperty() {
        final String html = "<html><body>\n"
                + "<div id='my' style=''>d</div>\n"
                + "<script>\n"
                + "  d = document.getElementById('my');\n"
                + "  alert(d.style.htmlunit);\n"
                + "  d.style.htmlunit = 'foo';\n"
                + "  alert(d.style.htmlunit);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"undefined", "foo"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void zIndex() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var style = document.getElementById('myDiv').style;\n"
                + "  alert(typeof style.zIndex);\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = 1;\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = 2.0;\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = 3.1;\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = 4.5;\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = 4.6;\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = '5';\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = '7.1';\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = '8.6';\n"
                + "  alert(style.zIndex);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'/>\n"
                + "</body></html>";
        final String[] messages = {"string", "", "1", "2", "2", "2", "2", "5", "5", "5"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void zIndexDefault() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var style = document.getElementById('divUndefined').style;\n"
                + "  alert(typeof style.zIndex);\n"
                + "  alert(style.zIndex);\n"
                + "  style = document.getElementById('divBlank').style;\n"
                + "  alert(typeof style.zIndex);\n"
                + "  alert(style.zIndex);\n"
                + "  style = document.getElementById('divInteger').style;\n"
                + "  alert(typeof style.zIndex);\n"
                + "  alert(style.zIndex);\n"
                + "  style = document.getElementById('divFloat').style;\n"
                + "  alert(typeof style.zIndex);\n"
                + "  alert(style.zIndex);\n"
                + "  style = document.getElementById('divFloat2').style;\n"
                + "  alert(typeof style.zIndex);\n"
                + "  alert(style.zIndex);\n"
                + "  style = document.getElementById('invalidDiv').style;\n"
                + "  alert(typeof style.zIndex);\n"
                + "  alert(style.zIndex);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='divUndefined'/>\n"
                + "  <div id='divBlank' style='z-index: '/>\n"
                + "  <div id='divInteger' style='z-index: 4'/>\n"
                + "  <div id='divFloat' style='z-index: 4.2'/>\n"
                + "  <div id='divFloat2' style='z-index: 4.7'/>\n"
                + "  <div id='invalidDiv' style='z-index: unfug'/>\n"
                + "</body></html>";
        final String[] messages = {"string", "", "string", "", "string", "4", "string", "", "string", "", "string", ""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void zIndexSetUndefined() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var style = document.getElementById('myDiv').style;\n"
                + "  var un_defined;\n"
                + "  alert(style.zIndex);\n"
                + "  try {\n"
                + "    style.zIndex = un_defined;\n"
                + "  } catch (e) { alert('error'); }\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = 1;\n"
                + "  alert(style.zIndex);\n"
                + "  try {\n"
                + "    style.zIndex = un_defined;\n"
                + "  } catch (e) { alert('error'); }\n"
                + "  alert(style.zIndex);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'/>\n"
                + "</body></html>";
        final String[] messages = {"", "", "1", "1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void zIndexSetNull() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var style = document.getElementById('myDiv').style;\n"
                + "  alert(style.zIndex);\n"
                + "  try {\n"
                + "    style.zIndex = null;\n"
                + "  } catch (e) { alert('error'); }\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = 1;\n"
                + "  alert(style.zIndex);\n"
                + "  try {\n"
                + "    style.zIndex = null;\n"
                + "  } catch (e) { alert('error'); }\n"
                + "  alert(style.zIndex);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'/>\n"
                + "</body></html>";
        final String[] messages = {"", "", "1", ""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void zIndexSetString() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var style = document.getElementById('myDiv').style;\n"
                + "  var unknown;\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = '7';\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = '7.6';\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = '';\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = '4';\n"
                + "  try {\n"
                + "    style.zIndex = '   ';\n"
                + "  } catch (e) { alert('error'); }\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = '1';\n"
                + "  try {\n"
                + "    style.zIndex = 'NAN';\n"
                + "  } catch (e) { alert('error'); }\n"
                + "  alert(style.zIndex);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'/>\n"
                + "</body></html>";
        final String[] messages = {"", "7", "7", "", "4", "1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void zIndexSetInvalid() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var style = document.getElementById('myDiv').style;\n"
                + "  alert(style.zIndex);\n"
                + "  try {\n"
                + "    style.zIndex = 'hallo';\n"
                + "  } catch (e) { alert('error'); }\n"
                + "  alert(style.zIndex);\n"
                + "  style.zIndex = 1;\n"
                + "  alert(style.zIndex);\n"
                + "  try {\n"
                + "    style.zIndex = 'hallo';\n"
                + "  } catch (e) { alert('error'); }\n"
                + "  alert(style.zIndex);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'/>\n"
                + "</body></html>";
        final String[] messages = {"", "", "1", "1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void cssText() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var style = document.getElementById('myDiv').style;\n"
                + "    alert(style.fontSize);\n"
                + "    alert(style.fontStyle);\n"
                + "    style.cssText = 'font-size: 15px; font-style: italic';\n"
                + "    alert(style.fontSize);\n"
                + "    alert(style.fontStyle);\n"
                + "    style.cssText = 'font-style: italic';\n"
                + "    alert(style.fontSize);\n"
                + "    alert(style.fontStyle);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv'/>\n"
                + "</body></html>";
        final String[] messages = {null, null, "15px", "italic", null, "italic"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void border() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var style = document.getElementById('myDiv').style;\n"
                + "    if (style.getPropertyValue) {\n"
                + "      alert(style.getPropertyValue('border-top-width'));\n"
                + "      alert(style.getPropertyValue('border-top-style'));\n"
                + "      alert(style.getPropertyValue('border-top-color'));\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv' style='border: red 1px solid'/>\n"
                + "</body></html>";
        final String[] messages = {"1px", "solid", "red"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void display() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var myDiv = document.getElementById('myDiv');\n"
                + "    myDiv.style.display = 'none';\n"
                + "    alert(myDiv.style.display == 'none');\n"
                + "    myDiv.style.display = '';\n"
                + "    alert(myDiv.style.display == 'none');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv'/>\n"
                + "</body></html>";
        final String[] messages = {"true", "false"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void displayDefaultOverwritesNone() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <style>\n"
                + "    tt { display: none; color: green; }\n"
                + "  </style>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var e = document.createElement('tt');\n"
                + "      var style = window.getComputedStyle(e, null);\n"
                + "      alert(style['display']);\n"
                + "      alert(style['color']);\n"
                + "      document.body.appendChild(e);\n"
                + "      alert(style['display']);\n"
                + "      alert(style['color']);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {null, null, "none", "rgb(0, 128, 0)"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void displayDefault() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var e = document.createElement('tt');\n"
                + "      var style = window.getComputedStyle(e, null);\n"
                + "      alert(style['display']);\n"
                + "      alert(style['color']);\n"
                + "      document.body.appendChild(e);\n"
                + "      alert(style['display']);\n"
                + "      alert(style['color']);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {null, "", "inline", "rgb(0, 0, 0)"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void resettingValue() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var myDiv = document.getElementById('myDiv');\n"
                + "    myDiv.style.marginTop = '1px';\n"
                + "    alert(myDiv.style.marginTop);\n"
                + "    myDiv.style.marginTop = '2px';\n"
                + "    alert(myDiv.style.marginTop);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv'/>\n"
                + "</body></html>";
        final String[] messages = {"1px", "2px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void resettingValue2() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var myDiv = document.getElementById('myDiv');\n"
                + "    myDiv.style.marginTop = '2px';\n"
                + "    alert(myDiv.style.marginTop);\n"
                + "    myDiv.style.left = '-1px';\n"
                + "    myDiv.style.marginTop = '30px';\n"
                + "    alert(myDiv.style.marginTop);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv'/>\n"
                + "</body></html>";
        final String[] messages = {"2px", "30px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void marginAllvsMarginSingle() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <style>\n"
                        + "      #m1 { margin: 3px; }\n"
                        + "      #m2 { margin-left: 3px; margin: 5px; }\n"
                        + "      #m3 { margin: 2px; margin-left: 7px; }\n"
                        + "    </style>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        alertComputedMargins('m1');\n"
                        + "        alertComputedMargins('m2');\n"
                        + "        alertComputedMargins('m3');\n"
                        + "        alertNonComputedMargins('m4');\n"
                        + "        alertNonComputedMargins('m5');\n"
                        + "        alertNonComputedMargins('m6');\n"
                        + "      }\n"
                        + "      function alertComputedMargins(id) {\n"
                        + "        var e = document.getElementById(id);\n"
                        + "        var s = getComputedStyle(e, null);\n"
                        + "        alert('L:' + s.marginLeft + ',R:' + s.marginRight +\n"
                        + "          ',T:' + s.marginTop + ',B:' + s.marginBottom);\n"
                        + "      }\n"
                        + "      function alertNonComputedMargins(id) {\n"
                        + "        var e = document.getElementById(id);\n"
                        + "        var s = e.style;\n"
                        + "        alert('L:' + s.marginLeft + ',R:' + s.marginRight +\n"
                        + "          ',T:' + s.marginTop + ',B:' + s.marginBottom);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <div id='m1'>m1</div>\n"
                        + "    <div id='m2'>m2</div>\n"
                        + "    <div id='m3'>m3</div>\n"
                        + "    <div id='m4' style='margin: 3px;'>m4</div>\n"
                        + "    <div id='m5' style='margin-left: 3px; margin: 5px;'>m5</div>\n"
                        + "    <div id='m6' style='margin: 2px; margin-left: 7px;'>m6</div>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"L:3px,R:3px,T:3px,B:3px", "L:5px,R:5px,T:5px,B:5px", "L:7px,R:2px,T:2px,B:2px",
                "L:3px,R:3px,T:3px,B:3px", "L:5px,R:5px,T:5px,B:5px", "L:7px,R:2px,T:2px,B:2px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void paddingAllvsPaddingSingle() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <style>\n"
                        + "      #m1 { padding: 3px; }\n"
                        + "      #m2 { padding-left: 3px; padding: 5px; }\n"
                        + "      #m3 { padding: 2px; padding-left: 7px; }\n"
                        + "    </style>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        alertComputedPaddings('m1');\n"
                        + "        alertComputedPaddings('m2');\n"
                        + "        alertComputedPaddings('m3');\n"
                        + "        alertNonComputedPaddings('m4');\n"
                        + "        alertNonComputedPaddings('m5');\n"
                        + "        alertNonComputedPaddings('m6');\n"
                        + "      }\n"
                        + "      function alertComputedPaddings(id) {\n"
                        + "        var e = document.getElementById(id);\n"
                        + "        var s = e.currentStyle ? e.currentStyle : getComputedStyle(e, null);\n"
                        + "        alert('L:' + s.paddingLeft + ',R:' + s.paddingRight +\n"
                        + "          ',T:' + s.paddingTop + ',B:' + s.paddingBottom);\n"
                        + "      }\n"
                        + "      function alertNonComputedPaddings(id) {\n"
                        + "        var e = document.getElementById(id);\n"
                        + "        var s = e.style;\n"
                        + "        alert('L:' + s.paddingLeft + ',R:' + s.paddingRight +\n"
                        + "          ',T:' + s.paddingTop + ',B:' + s.paddingBottom);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <div id='m1'>m1</div>\n"
                        + "    <div id='m2'>m2</div>\n"
                        + "    <div id='m3'>m3</div>\n"
                        + "    <div id='m4' style='padding: 3px;'>m4</div>\n"
                        + "    <div id='m5' style='padding-left: 3px; padding: 5px;'>m5</div>\n"
                        + "    <div id='m6' style='padding: 2px; padding-left: 7px;'>m6</div>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"L:3px,R:3px,T:3px,B:3px", "L:5px,R:5px,T:5px,B:5px", "L:7px,R:2px,T:2px,B:2px",
                "L:3px,R:3px,T:3px,B:3px", "L:5px,R:5px,T:5px,B:5px", "L:7px,R:2px,T:2px,B:2px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void styleShorthand() {
        styleShorthand("margin: 10px", "marginTop", "10px");
        styleShorthand("margin: 10px", "marginLeft", "10px");
        styleShorthand("margin: 10px", "marginRight", "10px");
        styleShorthand("margin: 10px", "marginBottom", "10px");

        styleShorthand("margin: 10px 20px", "marginTop", "10px");
        styleShorthand("margin: 10px 20px", "marginLeft", "20px");
        styleShorthand("margin: 10px 20px", "marginRight", "20px");
        styleShorthand("margin: 10px 20px", "marginBottom", "10px");

        styleShorthand("margin: 10px 20px 30px", "marginTop", "10px");
        styleShorthand("margin: 10px 20px 30px", "marginLeft", "20px");
        styleShorthand("margin: 10px 20px 30px", "marginRight", "20px");
        styleShorthand("margin: 10px 20px 30px", "marginBottom", "30px");

        styleShorthand("margin: 10px 20px 30px 40px", "marginTop", "10px");
        styleShorthand("margin: 10px 20px 30px 40px", "marginLeft", "40px");
        styleShorthand("margin: 10px 20px 30px 40px", "marginRight", "20px");
        styleShorthand("margin: 10px 20px 30px 40px", "marginBottom", "30px");

        styleShorthand("padding: 10px", "paddingTop", "10px");
        styleShorthand("padding: 10px", "paddingLeft", "10px");
        styleShorthand("padding: 10px", "paddingRight", "10px");
        styleShorthand("padding: 10px", "paddingBottom", "10px");

        styleShorthand("padding: 10px 20px", "paddingTop", "10px");
        styleShorthand("padding: 10px 20px", "paddingLeft", "20px");
        styleShorthand("padding: 10px 20px", "paddingRight", "20px");
        styleShorthand("padding: 10px 20px", "paddingBottom", "10px");

        styleShorthand("padding: 10px 20px 30px", "paddingTop", "10px");
        styleShorthand("padding: 10px 20px 30px", "paddingLeft", "20px");
        styleShorthand("padding: 10px 20px 30px", "paddingRight", "20px");
        styleShorthand("padding: 10px 20px 30px", "paddingBottom", "30px");

        styleShorthand("padding: 10px 20px 30px 40px", "paddingTop", "10px");
        styleShorthand("padding: 10px 20px 30px 40px", "paddingLeft", "40px");
        styleShorthand("padding: 10px 20px 30px 40px", "paddingRight", "20px");
        styleShorthand("padding: 10px 20px 30px 40px", "paddingBottom", "30px");
    }

    private void styleShorthand(final String style, final String attribute, final String expectedValue) {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function alert(msg) {\n"
                + "    var ta = document.getElementById('myTextArea');\n"
                + "    ta.value += msg + '; ';\n"
                + "  }\n"
                + "function test() {\n"
                + "  var style = document.getElementById('d').style;\n"
                + "  alert(style." + attribute + ");\n"
                + "}\n</script>\n"
                + "</head>\n"
                + "<body onload='test()'><div id='d' style='" + style + "'>foo</div>\n"
                + "  <textarea id='myTextArea' cols='80' rows='30'></textarea>\n"
                + "</body></html>";

        final String[] messages = {expectedValue};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getAttribute() {
        getAttribute("\"font\"");
    }

    @Test
    public void getAttributeFont() {
        getAttribute("'font'");
    }

    @Test
    public void getAttributeColor() {
        getAttribute("'color'");
    }

    @Test
    public void getAttributeColorCase() {
        getAttribute("'ColoR'");
    }

    @Test
    public void getAttributeFont0() {
        getAttribute("'font', 0");
    }

    @Test
    public void getAttributeColor0() {
        getAttribute("'color', 0");
    }

    @Test
    public void getAttributeColorCase0() {
        getAttribute("'coLOr', 0");
    }

    @Test
    public void getAttributeFont1() {
        getAttribute("'font', 1");
    }

    @Test
    public void getAttributeColor1() {
        getAttribute("'color', 1");
    }

    @Test
    public void getAttributeColorCase1() {
        getAttribute("'ColOR', 1");
    }

    private void getAttribute(final String params) {
        final String html =
                "<html><head><script>\n"
                        + "function test() {\n"
                        + "  if (document.all['a'].style.getAttribute) {\n"
                        + "    alert(document.all['a'].style.getAttribute(" + params + "));\n"
                        + "  }\n"
                        + "}\n"
                        + "</script></head>\n"
                        + "<body onload='test()'>\n"
                        + "<a id='a' href='#' style='color:green'>go</a></body></html>";
        checkHtmlAlert(html, null);
    }

    @Test
    public void setAttributeFont() {
        setAttribute("'font', 'blah'");
    }


    @Test
    public void setAttributeColor() {
        setAttribute("'color', 'red'");
    }


    @Test
    public void setAttributeColorCase() {
        setAttribute("'ColoR', 'red'");
    }

    @Test
    public void setAttributeFont0() {
        setAttribute("'font', 'blah', 0");
    }


    @Test
    public void setAttributeColor0() {
        setAttribute("'color', 'red', 0");
    }


    @Test
    public void setAttributeColorCase0() {
        setAttribute("'ColoR', 'red', 0");
    }


    @Test
    public void setAttributeFont1() {
        setAttribute("'font', 'blah', 1");
    }


    @Test
    public void setAttributeColor1() {
        setAttribute("'color', 'red', 1");
    }


    @Test
    public void setAttributeColorCase1() {
        setAttribute("'ColoR', 'red', 1");
    }

    private void setAttribute(final String params) {
        final String html =
                "<html><body onload='test()'>\n"
                        + "<a id='a' href='#' style='color:green'>go</a>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    if (document.all['a'].style.getAttribute) {\n"
                        + "      alert(\"" + params + "\");\n"
                        + "      alert(document.all['a'].style.getAttribute('color'));\n"
                        + "      document.all['a'].style.setAttribute(" + params + ");\n"
                        + "      alert(document.all['a'].style.getAttribute('color'));\n"
                        + "    }\n"
                        + "    else {\n"
                        + "      alert('not supported');\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body></html>";
        final String[] messages = {"not supported"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void setProperty() {
        final String[] expected = {"red ", "black ", "blue important", "gray ", "green "};
        setPropertyBackgroundColor("'background-color', 'red', ''", expected[0]);
        setPropertyBackgroundColor("'background-ColoR', 'black', ''", expected[1]);
        setPropertyBackgroundColor("'background-color', 'blue', 'important'", expected[2]);
        setPropertyBackgroundColor("'background-color', 'gray', null", expected[3]);
        setPropertyBackgroundColor("'background-color', 'green', undefined", expected[4]);
    }

    @Test
    public void setPropertyImportant() {
        final String[] expected = {"green ", "black important", "green "};
        setPropertyBackgroundColor("'background-color', 'white', 'crucial'", expected[0]);
        setPropertyBackgroundColor("'background-color', 'black', 'imPORTant'", expected[1]);
        setPropertyBackgroundColor("'background-color', 'blue', 'important '", expected[2]);
    }

    private void setPropertyBackgroundColor(final String params, final String message) {
        final String html =
                "<html><body onload='test()'>\n"
                        + "<a id='a' href='#' style='background-color:green'>go</a>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var node = document.getElementById('a');\n"
                        + "    try {\n"
                        + "      node.style.setProperty(" + params + ");\n"
                        + "      alert(node.style.backgroundColor + ' ' + node.style.getPropertyPriority('background-color'));\n"
                        + "    } catch(e) { alert(e); }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body></html>";

        final String[] messages = {message};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void setWidthProperty() {
        setLengthProperty("width", "width");
    }

    @Test
    public void setWidth() {
        setLength("width", "width");
    }

    @Test
    public void setHeightProperty() {
        setLengthProperty("height", "height");
    }


    @Test
    public void setHeight() {
        setLength("height", "height");
    }


    @Test
    public void setTopProperty() {
        setLengthProperty("top", "top");
    }

    @Test
    public void setTop() {
        setLength("top", "top");
    }


    @Test
    public void setLeftProperty() {
        setLengthProperty("left", "left");
    }

    @Test
    public void setLeft() {
        setLength("left", "left");
    }

    @Test
    public void setBottomProperty() {
        setLengthProperty("bottom", "bottom");
    }

    @Test
    public void setBottom() {
        setLength("bottom", "bottom");
    }

    @Test
    public void setRightProperty() {
        setLengthProperty("right", "right");
    }

    @Test
    public void setRight() {
        setLength("right", "right");
    }

    @Test
    public void setMarginTopProperty() {
        setLengthProperty("margin-top", "marginTop");
    }

    @Test
    public void setMarginTop() {
        setLength("margin-top", "marginTop");
    }

    @Test
    public void setMarginLeftProperty() {
        setLengthProperty("margin-left", "marginLeft");
    }

    @Test
    public void setMarginLeft() {
        setLength("margin-left", "marginLeft");
    }

    @Test
    public void setMarginBottomProperty() {
        setLengthProperty("margin-bottom", "marginBottom");
    }

    @Test
    public void setMarginBottom() {
        setLength("margin-bottom", "marginBottom");
    }

    @Test
    public void setMarginRightProperty() {
        setLengthProperty("margin-right", "marginRight");
    }

    @Test
    public void setMarginRight() {
        setLength("margin-right", "marginRight");
    }

    @Test
    public void setPaddingTopProperty() {
        setLengthProperty("padding-top", "paddingTop");
    }

    @Test
    public void setPaddingTop() {
        setLength("padding-top", "paddingTop");
    }

    @Test
    public void setPaddingLeftProperty() {
        setLengthProperty("padding-left", "paddingLeft");
    }

    @Test
    public void setPaddingLeft() {
        setLength("padding-left", "paddingLeft");
    }

    @Test
    public void setPaddingBottomProperty() {
        setLengthProperty("padding-bottom", "paddingBottom");
    }

    @Test
    public void setPaddingBottom() {
        setLength("padding-bottom", "paddingBottom");
    }

    @Test
    public void setPaddingRightProperty() {
        setLengthProperty("padding-right", "paddingRight");
    }

    @Test
    public void setPaddingRight() {
        setLength("padding-right", "paddingRight");
    }

    @Test
    public void setBorderTopWidthProperty() {
        setLengthProperty("border-top-width", "borderTopWidth");
    }

    @Test
    public void setBorderTopWidth() {
        setLength("border-top-width", "borderTopWidth");
    }

    @Test
    public void setBorderLeftWidthProperty() {
        setLengthProperty("border-left-width", "borderLeftWidth");
    }

    @Test
    public void setBorderLeftWidth() {
        setLength("border-left-width", "borderLeftWidth");
    }

    @Test
    public void setBorderBottomWidthProperty() {
        setLengthProperty("border-bottom-width", "borderBottomWidth");
    }

    @Test
    public void setBorderBottomWidth() {
        setLength("border-bottom-width", "borderBottomWidth");
    }

    @Test
    public void setBorderRightWidthProperty() {
        setLengthProperty("border-right-width", "borderRightWidth");
    }

    @Test
    public void setBorderRightWidth() {
        setLength("border-right-width", "borderRightWidth");
    }

    @Test
    public void setMaxWidthProperty() {
        setLengthProperty("max-width", "maxWidth");
    }

    @Test
    public void setMaxWidth() {
        setLength("max-width", "maxWidth");
    }

    @Test
    public void setMinWidthProperty() {
        setLengthProperty("min-width", "minWidth");
    }

    @Test
    public void setMinWidth() {
        setLength("min-width", "minWidth");
    }

    @Test
    public void setMaxHeightProperty() {
        setLengthProperty("max-height", "maxHeight");
    }

    @Test
    public void setMaxHeight() {
        setLength("max-height", "maxHeight");
    }

    @Test
    public void setMinHeightProperty() {
        setLengthProperty("min-height", "minHeight");
    }

    @Test
    public void setMinHeight() {
        setLength("min-height", "minHeight");
    }

    @Test
    public void setTextIndentProperty() {
        setLengthProperty("text-indent", "textIndent");
    }

    @Test
    public void setTextIndent() {
        setLength("text-indent", "textIndent");
    }

    @Test
    public void setFontSizeProperty() {
        setLengthProperty("font-size", "fontSize");
    }

    @Test
    public void setFontSize() {
        setLength("font-size", "fontSize");
    }

    @Test
    public void setWordSpacingProperty() {
        setLengthProperty("word-spacing", "wordSpacing");
    }

    @Test
    public void setWordSpacing() {
        setLength("word-spacing", "wordSpacing");
    }

    @Test
    public void setLetterSpacingProperty() {
        setLengthProperty("letter-spacing", "letterSpacing");
    }

    @Test
    public void setLetterSpacing() {
        setLength("letter-spacing", "letterSpacing");
    }

    @Test
    public void setVerticalAlignProperty() {
        setLengthProperty("vertical-align", "verticalAlign");
    }

    @Test
    public void setVerticalAlign() {
        setLength("vertical-align", "verticalAlign");
    }

    @Test
    public void setOutlineWidthProperty() {
        setLengthProperty("outline-width", "outlineWidth");
    }

    @Test
    public void setOutlineWidth() {
        setLength("outline-width", "outlineWidth");
    }

    private void setLengthProperty(final String cssProp, final String prop) {
        final String[] expected = {"4px", "5px", "6em", "auto", "7%", "initial", "inherit",
                "17px", "17px", "17px", null, "17px", "17px", "17px", "17px"};
        setLengthProperty(cssProp, prop, "'4', ''", expected[0]);
        setLengthProperty(cssProp, prop, "'5px', ''", expected[1]);
        setLengthProperty(cssProp, prop, "'6em', ''", expected[2]);
        setLengthProperty(cssProp, prop, "'auto', ''", expected[3]);
        setLengthProperty(cssProp, prop, "'7%', ''", expected[4]);
        setLengthProperty(cssProp, prop, "'initial', ''", expected[5]);
        setLengthProperty(cssProp, prop, "'inherit', ''", expected[6]);
        setLengthProperty(cssProp, prop, "'ellen', ''", expected[7]);
        setLengthProperty(cssProp, prop, "undefined, ''", expected[8]);
        setLengthProperty(cssProp, prop, "'undefined', ''", expected[9]);
        setLengthProperty(cssProp, prop, "'', null", expected[10]);
        setLengthProperty(cssProp, prop, "NaN, ''", expected[11]);
        setLengthProperty(cssProp, prop, "'NaNpx', ''", expected[12]);
        setLengthProperty(cssProp, prop, "true, ''", expected[13]);
        setLengthProperty(cssProp, prop, "Infinity, ''", expected[14]);
    }

    private void setLength(final String cssProp, final String prop) {
        final String[] expected = {"4px", "5px", "6em", "auto", "70%", "initial", "inherit",
                "17px", "17px", "17px", null, "17px", "17px", "17px", "17px"};
        setLength(cssProp, prop, "4", expected[0]);
        setLength(cssProp, prop, "'5px'", expected[1]);
        setLength(cssProp, prop, "'6em'", expected[2]);
        setLength(cssProp, prop, "'auto'", expected[3]);
        setLength(cssProp, prop, "'70%'", expected[4]);
        setLength(cssProp, prop, "'initial'", expected[5]);
        setLength(cssProp, prop, "'inherit'", expected[6]);
        setLength(cssProp, prop, "'ellen'", expected[7]);
        setLength(cssProp, prop, "undefined", expected[8]);
        setLength(cssProp, prop, "'undefined'", expected[9]);
        setLength(cssProp, prop, "''", expected[10]);
        setLength(cssProp, prop, "NaN", expected[11]);
        setLength(cssProp, prop, "'NaNpx'", expected[12]);
        setLength(cssProp, prop, "true", expected[13]);
        setLength(cssProp, prop, "Infinity", expected[14]);
    }

    private void setLengthProperty(final String cssProp, final String prop,
                                   final String params, final String expected) {
        final String html =
                "<html><body onload='test()'>\n"
                        + "<a id='a' href='#' style='" + cssProp + ":17px'>go</a>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var node = document.getElementById('a');\n"
                        + "    try {\n"
                        + "      node.style.setProperty('" + cssProp + "', " + params + ");\n"
                        + "      alert(node.style." + prop + ");\n"
                        + "    } catch(e) { alert(e); }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body></html>";

        final String[] messages = {expected};
        checkHtmlAlert(html, messages);
    }

    private void setLength(final String cssProp, final String prop,
                           final String params, final String expected) {
        final String html =
                "<html><body onload='test()'>\n"
                        + "<a id='a' href='#' style='" + cssProp + ":17px'>go</a>\n"
                        + "<script>\n"

                        + "  function test() {\n"
                        + "    var node = document.getElementById('a');\n"
                        + "    try {\n"
                        + "      node.style." + prop + " = " + params + ";\n"
                        + "      alert(node.style." + prop + ");\n"
                        + "    } catch(e) { alert(e); }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body></html>";

        final String[] messages = {expected};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getPropertyPriority() {
        final String html =
                "<html><body onload='test()'>\n"
                        + "<a id='a1' href='#' style='color:green'>go</a>\n"
                        + "<a id='a2' href='#' style='color:blue !important'>go</a>\n"
                        + "<a id='a3' href='#' style='background-color:green'>go</a>\n"
                        + "<a id='a4' href='#' style='background-color:blue !important'>go</a>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var node = document.getElementById('a1');\n"
                        + "    if (node.style.getPropertyPriority) {\n"
                        + "      alert(node.style.getPropertyPriority('color'));\n"
                        + "      node = document.getElementById('a2');\n"
                        + "      alert(node.style.getPropertyPriority('color'));\n"
                        + "      node = document.getElementById('a3');\n"
                        + "      alert(node.style.getPropertyPriority('background-color'));\n"
                        + "      node = document.getElementById('a4');\n"
                        + "      alert(node.style.getPropertyPriority('background-color'));\n"
                        + "    } else {\n"
                        + "      alert('not supported');\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body></html>";
        final String[] messages = {"", "important", "", "important"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void caseInsensitive() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var node = document.getElementById('div1');\n"
                + "  var style = node.style;\n"
                + "  alert(style.color);\n"
                + "  style.color = 'pink';\n"
                + "  alert(style.color);\n"
                + "  alert(node.getAttribute('style'));\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'><div id='div1' style='COLOR: BLACK'>foo</div></body></html>";

        final String[] messages = {"black", "pink", "color: pink;"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void pixelLeft() {
        final String html = "<html><body>\n"
                + "<div id='a' style='left: 5px; border: 1px solid black;'>a</div>\n"
                + "<div id='b' style='left: 1em; border: 1px solid black;'>b</div>\n"
                + "<script>\n"
                + "  var a = document.getElementById('a');\n"
                + "  var b = document.getElementById('b');\n"
                + "  alert(a.style.left);\n"
                + "  alert(a.style.pixelLeft);\n"
                + "  alert(b.style.left);\n"
                + "  alert(b.style.pixelLeft);\n"
                + "  if(a.style.pixelLeft) {\n"
                + "    a.style.pixelLeft = 30;\n"
                + "    alert(a.style.left);\n"
                + "    alert(a.style.pixelLeft);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"5px", "undefined", "1em", "undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void pixelRight() {
        final String html = "<html><body>\n"
                + "<div id='a' style='right: 5px; border: 1px solid black;'>a</div>\n"
                + "<div id='b' style='right: 1em; border: 1px solid black;'>b</div>\n"
                + "<script>\n"
                + "  var a = document.getElementById('a');\n"
                + "  var b = document.getElementById('b');\n"
                + "  alert(a.style.right);\n"
                + "  alert(a.style.pixelRight);\n"
                + "  alert(b.style.right);\n"
                + "  alert(b.style.pixelRight);\n"
                + "  if(a.style.pixelRight) {\n"
                + "    a.style.pixelRight = 30;\n"
                + "    alert(a.style.right);\n"
                + "    alert(a.style.pixelRight);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"5px", "undefined", "1em", "undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void pixelTop() {
        final String html = "<html><body>\n"
                + "<div id='a' style='top: 5px; border: 1px solid black;'>a</div>\n"
                + "<div id='b' style='top: 1em; border: 1px solid black;'>b</div>\n"
                + "<script>\n"
                + "  var a = document.getElementById('a');\n"
                + "  var b = document.getElementById('b');\n"
                + "  alert(a.style.top);\n"
                + "  alert(a.style.pixelTop);\n"
                + "  alert(b.style.top);\n"
                + "  alert(b.style.pixelTop);\n"
                + "  if(a.style.pixelTop) {\n"
                + "    a.style.pixelTop = 30;\n"
                + "    alert(a.style.top);\n"
                + "    alert(a.style.pixelTop);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"5px", "undefined", "1em", "undefined"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void pixelBottom() {
        final String html = "<html><body>\n"
                + "<div id='a' style='bottom: 5px; border: 1px solid black;'>a</div>\n"
                + "<div id='b' style='bottom: 1em; border: 1px solid black;'>b</div>\n"
                + "<script>\n"
                + "  var a = document.getElementById('a');\n"
                + "  var b = document.getElementById('b');\n"
                + "  alert(a.style.bottom);\n"
                + "  alert(a.style.pixelBottom);\n"
                + "  alert(b.style.bottom);\n"
                + "  alert(b.style.pixelBottom);\n"
                + "  if(a.style.pixelBottom) {\n"
                + "    a.style.pixelBottom = 30;\n"
                + "    alert(a.style.bottom);\n"
                + "    alert(a.style.pixelBottom);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"5px", "undefined", "1em", "undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setToNull() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var div1 = document.getElementById('div1');\n"
                + "  alert(div1.style.border);\n"
                + "  try {\n"
                + "    div1.style.border = null;\n"
                + "  } catch (e) {\n"
                + "    alert('error');\n"
                + "  }\n"
                + "  alert(div1.style.border);\n"
                + "  alert(div1.style.display);\n"
                + "  try {\n"
                + "    div1.style.display = null;\n"
                + "  } catch (e) {\n"
                + "    alert('error');\n"
                + "  }\n"
                + "  alert(div1.style.display);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<div id='div1'>foo</div></body></html>";
        final String[] messages = {null, null, null, null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void length() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var a = document.createElement('div');\n"
                + "    a.style.cssText = 'width: 100%';\n"
                + "    alert(a.style.length);\n"
                + "    alert(a.style[0]);\n"
                + "    alert(a.style[1]);\n"
                + "    alert(a.style[-1]);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"1", "width", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void boxSizing() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var style = document.getElementById('test').style;\n"
                + "    alert(style.boxSizing === '');\n"
                + "    style = document.createElement('div').style;\n"
                + "    alert(style.boxSizing === '');\n"
                + "    style.boxSizing = 'border-box';\n"
                + "    alert(style.boxSizing);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='test'></div>\n"
                + "</body></html>";
        final String[] messages = {"true", "true", "border-box"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void widows() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('mydiv');\n"
                + "    debug(div);\n"
                + "    div.style.widows = 0;\n"
                + "    debug(div);\n"
                + "    div.style.widows = 5;\n"
                + "    debug(div);\n"
                + "    div.style.widows = 0;\n"
                + "    debug(div);\n"
                + "  }\n"
                + "  function debug(div) {\n"
                + "    alert(div.style.widows);\n"
                + "    alert(window.getComputedStyle(div, null).widows);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='mydiv'></div>\n"
                + "</body></html>";
        final String[] messages = {null, "2", null, "2", "5", "5", "5", "5"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void orphans() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('mydiv');\n"
                + "    debug(div);\n"
                + "    div.style.orphans = 0;\n"
                + "    debug(div);\n"
                + "    div.style.orphans = 5;\n"
                + "    debug(div);\n"
                + "    div.style.orphans = 0;\n"
                + "    debug(div);\n"
                + "  }\n"
                + "  function debug(div) {\n"
                + "    alert(div.style.orphans);\n"
                + "    alert(window.getComputedStyle(div, null).orphans);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='mydiv'></div>\n"
                + "</body></html>";
        final String[] messages = {null, "2", null, "2", "5", "5", "5", "5"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void position() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('mydiv');\n"
                + "    debug(div);\n"
                + "    div.style.position = 'fake';\n"
                + "    debug(div);\n"
                + "    div.style.position = ' ';\n"
                + "    debug(div);\n"
                + "    div.style.position = 'AbSoLuTe';\n"
                + "    debug(div);\n"
                + "    div.style.position = '';\n"
                + "    debug(div);\n"
                + "  }\n"
                + "  function debug(div) {\n"
                + "    alert(div.style.position);\n"
                + "    alert(window.getComputedStyle(div, null).position);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='mydiv'></div>\n"
                + "</body></html>";
        final String[] messages = {null, "static", null, "static", null, "static", "absolute", "absolute", null, "static"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setStyle() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('mydiv');\n"
                + "    alert(div.style);\n"
                + "    div.style = 'color: green; font-family: abc';\n"
                + "    alert(div.style);\n"
                + "    alert(div.style.color);\n"
                + "    alert(div.style.fontFamily);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='mydiv'></div>\n"
                + "</body></html>";
        final String[] messages = {"[object CSSStyleDeclaration]", "[object CSSStyleDeclaration]", "green", "abc"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void in() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var node = document.getElementById('div1');\n"
                + "    var style = node.style;\n"
                + "    alert(style.length);\n"
                + "    alert(-1 in style);\n"
                + "    alert(0 in style);\n"
                + "    alert(1 in style);\n"
                + "    alert(42 in style);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1' style='color: black'>foo</div>\n"
                + "</body></html>";
        final String[] messages = {"1", "false", "true", "false", "false"};
        checkHtmlAlert(html, messages);
    }
}
