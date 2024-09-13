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
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * Tests for {@link HTMLElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLElement2Test extends LoboUnitTest {

    @Test
    @Alerts({"undefined", "undefined"})
    public void scopeName() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.body.scopeName);\n"
                + "    alert(document.body.tagUrn);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "http://www.meh.com/meh"})
    public void scopeName2() {
        final String html = "<html xmlns:blah='http://www.blah.com/blah'><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var x = document.getElementById('x');\n"
                + "    alert(x.scopeName);\n"
                + "    alert(x.tagUrn);\n"
                + "    try {\n"
                + "      x.tagUrn = 'http://www.meh.com/meh';\n"
                + "      alert(x.scopeName);\n"
                + "      alert(x.tagUrn);\n"
                + "    } catch(e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'><blah:abc id='x'></blah:abc></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"number", "number", "number", "number", "number", "number", "number", "number"})
    public void offsets() {
        final String html = "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "</div></body>\n"
                + "<div id='div1'>foo</div>\n"
                + "<script>\n"
                + "function alertOffsets(_oElt) {\n"
                + "  alert(typeof _oElt.offsetHeight);\n"
                + "  alert(typeof _oElt.offsetWidth);\n"
                + "  alert(typeof _oElt.offsetLeft);\n"
                + "  alert(typeof _oElt.offsetTop);\n"
                + "}\n"
                + "alertOffsets(document.body);\n"
                + "alertOffsets(document.getElementById('div1'));\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("attachEvent not available")
    public void offsetWidthWithEvent() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        
                        + "  function test() {\n"
                        + "    var myDiv2 = document.getElementById('myDiv2');\n"
                        + "    if(!document.attachEvent) { alert('attachEvent not available'); return }\n"
                        + "    myDiv2.attachEvent('ondataavailable', handler);\n"
                        + "    document.attachEvent('ondataavailable', handler);\n"
                        + "    var m = document.createEventObject();\n"
                        + "    m.eventType = 'ondataavailable';\n"
                        + "    myDiv2.fireEvent(m.eventType, m);\n"
                        + "    document.fireEvent(m.eventType, m);\n"
                        + "  }\n"
                        + "  function handler() {\n"
                        + "    var e = document.getElementById('myDiv');\n"
                        + "    e.style.width = 30;\n"
                        + "    alert(e.offsetWidth);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='myDiv'></div>\n"
                        + "  <div id='myDiv2'></div>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void offsetWidthspanWithDifferentFontSize() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        
                        + "  function test() {\n"
                        + "    var myDefault = document.getElementById('myDefault');\n"
                        + "    var myLarge = document.getElementById('myLarge');\n"
                        + "    alert(myDefault.offsetWidth > 20);\n"
                        + "    alert(myLarge.offsetWidth > myDefault.offsetWidth);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <span id='myDefault'>1234567890</span>\n"
                        + "  <span id='myLarge' style='font-size: 10em'>1234567890</span>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void offsetWidthSpanWithDifferentFonts() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        
                        + "  function test() {\n"
                        + "    var mySerif = document.getElementById('mySerif');\n"
                        + "    var mySans = document.getElementById('mySans');\n"
                        + "    alert(mySerif.offsetWidth > 20);\n"
                        + "    alert(mySans.offsetWidth > mySerif.offsetWidth);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <span id='mySerif' style='font-family: serif'>1234567890</span>\n"
                        + "  <span id='mySans' style='font-family: sans-serif'>1234567890</span>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"15", "15"})
    public void offsetTopAndLeftPadding() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var e = document.getElementById('d');\n"
                        + "        alert(e.offsetTop);\n"
                        + "        alert(e.offsetLeft);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()' style='padding: 3px; margin: 0px; border: 0px solid green;'>\n"
                        + "    <div style='padding: 5px; margin: 0px; border: 0px solid blue;'>\n"
                        + "      <div style='padding: 7px; margin: 0px; border: 0px solid red;'>\n"
                        + "        <div id='d' style='padding: 13px; margin: 0px; border: 0px solid black;'>d</div>\n"
                        + "      </div>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"13", "28"})
    public void offsetTopAndLeftMargins() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var e = document.getElementById('d');\n"
                        + "        alert(e.offsetTop);\n"
                        + "        alert(e.offsetLeft);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()' style='padding: 0px; margin: 3px; border: 0px solid green;'>\n"
                        + "    <div style='padding: 0px; margin: 5px; border: 0px solid blue;'>\n"
                        + "      <div style='padding: 0px; margin: 7px; border: 0px solid red;'>\n"
                        + "        <div id='d' style='padding: 0px; margin: 13px; border: 0px solid black;'>d</div>\n"
                        + "      </div>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"15", "15"})
    public void offsetTopAndLeftBorders() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var e = document.getElementById('d');\n"
                        + "        alert(e.offsetTop);\n"
                        + "        alert(e.offsetLeft);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()' style='padding: 0px; margin: 0px; border: 3px solid green;'>\n"
                        + "    <div style='padding: 0px; margin: 0px; border: 5px solid blue;'>\n"
                        + "      <div style='padding: 0px; margin: 0px; border: 7px solid red;'>\n"
                        + "        <div id='d' style='padding: 0px; margin: 0px; border: 13px solid black;'>d</div>\n"
                        + "      </div>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0"})
    public void offsetTopAndLeft_Nothing() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var e = document.getElementById('d');\n"
                        + "        alert(e.offsetTop);\n"
                        + "        alert(e.offsetLeft);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()' style='padding: 0px; margin: 0px; border: 0px solid green;'>\n"
                        + "    <div style='padding: 0px; margin: 0px; border: 0px solid blue;'>\n"
                        + "      <div style='padding: 0px; margin: 0px; border: 0px solid red;'>\n"
                        + "        <div id='d' style='padding: 0px; margin: 0px; border: 0px solid black;'>d</div>\n"
                        + "      </div>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"50", "50"})
    public void offsetTopAndLeft_AbsolutelyPositioned() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var e = document.getElementById('d');\n"
                        + "        alert(e.offsetTop);\n"
                        + "        alert(e.offsetLeft);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <div>\n"
                        + "      <div>\n"
                        + "        <div id='d' style='position:absolute; top:50; left:50;'>d</div>\n"
                        + "      </div>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1 absolute_auto 0", "2 absolute_length 50", "3 absolute_inherit 10", "4 fixed_auto 10",
            "5 fixed_length 50", "6 fixed_inherit 10", "7 relative_auto 0", "8 relative_length 50",
            "9 relative_inherit 10", "10 static_auto 0", "11 static_length 0", "12 static_inherit 0",
            "13 inherit_auto 0", "14 inherit_length 50", "15 inherit_inherit 10"})
    public void offsetLeft_PositionLeft_DifferentCombinations() {
        final String html = "<html><body onload='test()'><script language='javascript'>\n"
                + "String.prototype.trim = function() {\n"
                + "  return this.replace(/^\\s+|\\s+$/g, '');\n"
                + "}\n"
                + "function test() {\n"
                + "  var output = document.getElementById('output');\n"
                + "  output.value = '';\n"
                + "  var children = document.getElementById('container').childNodes;\n"
                + "  for(var i = 0; i < children.length; i++) {\n"
                + "    var c = children[i];\n"
                + "    if(c.tagName) output.value += (c.innerHTML + ' ' + c.id + ' ' + c.offsetLeft + '\\n');\n"
                + "  }\n"
                + "  var alerts = output.value.split('\\n');\n"
                + "  for(var i = 0; i < alerts.length; i++) {\n"
                + "    var s = alerts[i].trim();\n"
                + "    if(s) alert(s);\n"
                + "  }\n"
                + "}\n"
                + "</script>\n"
                + "<textarea id='output' cols='40' rows='20'></textarea>\n"
                + "<div id='container' style='position: absolute; left: 10px;'>\n"
                + "  <div id='absolute_auto' style='position: absolute; left: auto;'>1</div>\n"
                + "  <div id='absolute_length' style='position: absolute; left: 50px;'>2</div>\n"
                + "  <div id='absolute_inherit' style='position: absolute; left: inherit;'>3</div>\n"
                + "  <div id='fixed_auto' style='position: fixed; left: auto;'>4</div>\n"
                + "  <div id='fixed_length' style='position: fixed; left: 50px;'>5</div>\n"
                + "  <div id='fixed_inherit' style='position: fixed; left: inherit;'>6</div>\n"
                + "  <div id='relative_auto' style='position: relative; left: auto;'>7</div>\n"
                + "  <div id='relative_length' style='position: relative; left: 50px;'>8</div>\n"
                + "  <div id='relative_inherit' style='position: relative; left: inherit;'>9</div>\n"
                + "  <div id='static_auto' style='position: static; left: auto;'>10</div>\n"
                + "  <div id='static_length' style='position: static; left: 50px;'>11</div>\n"
                + "  <div id='static_inherit' style='position: static; left: inherit;'>12</div>\n"
                + "  <div id='inherit_auto' style='position: inherit; left: auto;'>13</div>\n"
                + "  <div id='inherit_length' style='position: inherit; left: 50px;'>14</div>v>\n"
                + "  <div id='inherit_inherit' style='position: inherit; left: inherit;'>15</div>\n"
                + "</div>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"40", "10"})
    public void offsetTopAndLeftParentAbsolute() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        
                        + "  function test() {\n"
                        + "    var e = document.getElementById('innerDiv');\n"
                        + "    alert(e.offsetLeft);\n"
                        + "    alert(e.offsetTop);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<div id='styleTest' style='position: absolute; left: 400px; top: 50px; padding: 10px 20px 30px 40px;'>\n"
                        + "<div id='innerDiv'></div>TEST</div>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"400", "50"})
    public void offsetTopAndLeftFixed() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        
                        + "  function test() {\n"
                        + "    var e = document.getElementById('innerDiv');\n"
                        + "    alert(e.offsetLeft);\n"
                        + "    alert(e.offsetTop);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<div id='innerDiv' style='position: fixed; left: 400px; top: 50px;'></div>TEST</div>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    /**
     * Minimal flow/layouting test: verifies that the <tt>offsetTop</tt> property changes depending
     * on previous siblings. In the example below, the second div is below the first one, so its
     * offsetTop must be greater than zero. This sort of test is part of the Dojo unit tests, so
     * this needs to pass for the Dojo unit tests to pass.
     */
    @Test
    @Alerts({"true", "true", "2", "3", "4", "5", "6", "7", "8", "9", "99", "199", "5999"})
    public void offsetTopWithPreviousSiblings() {
        StringBuilder html =
                new StringBuilder("<html><head>\n"
                        + "<script>\n"                        
                        + "  function test() {\n"
                        + "    alert(document.getElementById('d1').offsetTop == 0);\n"
                        + "    var d2OffsetTop = document.getElementById('d2').offsetTop;\n"
                        + "    alert(d2OffsetTop > 0);\n"
                        + "    alert(document.getElementById('d3').offsetTop/d2OffsetTop);\n"
                        + "    alert(document.getElementById('d4').offsetTop/d2OffsetTop);\n"
                        + "    alert(document.getElementById('d5').offsetTop/d2OffsetTop);\n"
                        + "    alert(document.getElementById('d6').offsetTop/d2OffsetTop);\n"
                        + "    alert(document.getElementById('d7').offsetTop/d2OffsetTop);\n"
                        + "    alert(document.getElementById('d8').offsetTop/d2OffsetTop);\n"
                        + "    alert(document.getElementById('d9').offsetTop/d2OffsetTop);\n"
                        + "    alert(document.getElementById('d10').offsetTop/d2OffsetTop);\n"
                        + "    alert(document.getElementById('d100').offsetTop/d2OffsetTop);\n"
                        + "    alert(document.getElementById('d200').offsetTop/d2OffsetTop);\n"
                        + "    alert(document.getElementById('d6000').offsetTop/d2OffsetTop);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body style='padding: 0px; margin: 0px;' onload='test()'>\n");
        for (int i = 1; i <= 6000; i++) {
            html.append("  <div id='d").append(i).append("'>bar</div>\n");
        }
        html.append("</body>\n").append("</html>");
        checkHtmlAlert(html.toString());
    }

    @Test
    @Alerts({"8", "8"})
    public void offsetTopAndLeftWhenParentIsBody() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var d = document.getElementById('d');\n"
                + "    alert(d.offsetLeft);\n"
                + "    alert(d.offsetTop);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "  <body onload='test()'>\n"
                + "    <div id='d'>foo</div>\n"
                + "  </body>\n"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"23", "19"})
    public void offsetTopAndLeftWithRelativePosition() {
        final String html
                = "<html><body onload='test()'><script language='javascript'>\n"
                + "  function test() {\n"
                + "    var inner = document.createElement('div');\n"
                + "    var outer = document.createElement('div');\n"
                + "    \n"
                + "    document.body.appendChild(outer);\n"
                + "    outer.appendChild(inner);\n"
                + "    \n"
                + "    outer.style.position = 'absolute';\n"
                + "    inner.style.position = 'relative';\n"
                + "    inner.style.left = '19.0px';\n"
                + "    inner.style.top = '23.0px';\n"
                + "    \n"
                + "    alert(inner.offsetTop);\n"
                + "    alert(inner.offsetLeft);\n"
                + "  }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"30px", "46", "55px", "71", "71", "0", "0", "0", "0"})
    public void offsetWidthAndHeight() {
        final String html =
                "<html><head>\n"
                        + "<style>\n"
                        + ".dontDisplay { display: none }\n"
                        + ".hideMe { visibility: hidden }\n"
                        + "</style>\n"
                        + "<script>\n"                        
                        + "  function test() {\n"
                        + "    var e = document.getElementById('myDiv');\n"
                        + "    e.style.width = 30;\n"
                        + "    alert(e.style.width);\n"
                        + "    alert(e.offsetWidth);\n"
                        + "    e.style.height = 55;\n"
                        + "    alert(e.style.height);\n"
                        + "    alert(e.offsetHeight);\n"
                        + "    e.className = 'hideMe';\n"
                        + "    alert(e.offsetHeight);\n"
                        + "    e.className = 'dontDisplay';\n"
                        + "    alert(e.offsetHeight);\n"
                        + "    alert(e.offsetWidth);\n"
                        + "    var nested = document.getElementById('nested');\n"
                        + "    alert(nested.offsetHeight);\n"
                        + "    alert(nested.offsetWidth);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='myDiv' style='border: 3px solid #fff; padding: 5px;'><div id='nested'>hello</div></div>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0"})
    public void offsetWidthAndHeightDisplayNoneAndChildren() {
        final String html
                = "<html><body>\n"
                + "<div id='div' style='display: none;'><div style='width: 20px; height: 30px;'></div></div>\n"
                + "<script>\n"
                + "alert(document.getElementById('div').offsetWidth);</script>\n"
                + "<script>alert(document.getElementById('div').offsetHeight);</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "18"})
    public void offsetHeightExplicitHeightZero() {
        final String html
                = "<html><body>\n"
                + "<div id='d1' style='height: 0px;'><div id='d2'>x</div></div>\n"
                + "<script>\n"
                + "alert(document.getElementById('d1').offsetHeight);</script>\n"
                + "<script>alert(document.getElementById('d2').offsetHeight);</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"75", "2", "5", "20", "50", "50", "18"})
    public void offsetHeightCalculatedBasedOnChildren() {
        final String html
                = "<html>\n"
                + "  <body onload='h(\"d1\"); h(\"d2\"); h(\"d3\"); h(\"d4\"); h(\"d5\"); h(\"d6\"); h(\"d7\");'>\n"
                + "    <div id='d1'>\n"
                + "      <div id='d2' style='height:2px;'>x</div>\n"
                + "      <div id='d3' style='height:5px;'><div id='d4' style='height:20px;'>x</div></div>\n"
                + "      <div id='d5'><div id='d6' style='height:50px;'>x</div></div>\n"
                + "      <div id='d7'>x</div>\n"
                + "    </div>\n"
                + "    <script>\n"
                + "function h(id) { alert(document.getElementById(id).offsetHeight); }</script>\n"
                + "  </body>\n"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void offsetHeightTakeFontSizeIntoAccount() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('myTestDiv');\n"
                + "    var initial = elem.offsetHeight;\n"
                + "    alert(initial > 10);\n"
                + "    elem.style.fontSize = '42px';\n"
                + "    alert(elem.offsetHeight > initial);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myTestDiv'>something</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void offsetWidthCalculatedBasedOnPage() {
        final String html
                = "<html><body>\n"
                + "<div id='d1' style='width: 20%'>hello</div>\n"
                + "<div><div id='d2' style='width: 20%'>hello</div></div>\n"
                + "<script>\n"
                + "alert(document.getElementById('d1').offsetWidth > 0);\n"
                + "alert(document.getElementById('d2').offsetWidth > 0);\n"
                + "</script></body>\n"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("30")
    public void offsetWidthParentWidthConstrainsChildWidth() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <style>#a { width: 30px; }</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<div id='a'><div id='b'>foo</div></div>\n"
                + "<script>\n"
                + "alert(document.getElementById('b').offsetWidth);</script>\n"
                + "</body>\n"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("30")
    public void offsetWidthParentWidthConstrainsChildWidth2() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <style>#a{width:30px;} #b{border:2px;padding:3px;}</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<div id='a'><div id='b'>foo</div></div>\n"
                + "<script>\n"
                + "alert(document.getElementById('b').offsetWidth);</script>\n"
                + "</body>\n"
                + "</html>";
        checkHtmlAlert(html);
    }

    /**
     * When CSS float is set to "right" or "left", the width of an element is related to
     * its content and it doesn't takes the full available width.
     */
    @Test
    @Alerts({"1", "0.5", "true"})
    public void offsetWidthCssFloatRightOrLeft() {
        final String html = "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<div id='withoutFloat1'>hello</div><div>hellohello</div>\n"
                + "<div id='withFloat1' style='float: left'>hello</div><div style='float: left'>hellohello</div>\n"
                + "<script>\n"
                + "var eltWithoutFloat1 = document.getElementById('withoutFloat1');\n"
                + "alert(eltWithoutFloat1.offsetWidth / eltWithoutFloat1.nextSibling.offsetWidth);\n"
                + "var eltWithFloat1 = document.getElementById('withFloat1');\n"
                + "alert(eltWithFloat1.offsetWidth / eltWithFloat1.nextSibling.offsetWidth);\n"
                // we don't make any strong assumption on the screen size here,
                // but expect it to be big enough to show 10 times "hello" on one line
                + "alert(eltWithoutFloat1.offsetWidth > 10 * eltWithFloat1.offsetWidth);\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true"})
    public void offsetWidthTakeContentIntoAccount() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elem1 = document.getElementById('myTest1');\n"
                + "    var elem2 = document.getElementById('myTest2');\n"
                + "    var elem3 = document.getElementById('myTest3');\n"
                + "    alert(elem1.offsetWidth == 0);\n"
                + "    alert(elem1.offsetWidth < elem2.offsetWidth);\n"
                + "    alert(elem2.offsetWidth < elem3.offsetWidth);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <span id='myTest1'></span>\n"
                + "  <span id='myTest2'>short</span>\n"
                + "  <span id='myTest3'>loooooooooong</span>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void offsetWidthTakeFontSizeIntoAccount() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('myTestDiv');\n"
                + "    var initial = elem.offsetWidth;\n"
                + "    alert(initial > 10);\n"
                + "    elem.style.fontSize = '42px';\n"
                + "    alert(elem.offsetWidth > initial);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <span id='myTestDiv'>something</span>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"something", "0"})
    public void textContentNull() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    checkChildren();\n"
                + "    myTestDiv.textContent = null;\n"
                + "    checkChildren();\n"
                + "  }\n"
                + "  function checkChildren() {\n"
                + "    if (myTestDiv.childNodes.length == 0)\n"
                + "      alert('0');\n"
                + "    else\n"
                + "      alert(myTestDiv.childNodes.item(0).data);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myTestDiv'>something</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"something", "0"})
    public void textContentEmptyString() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    checkChildren();\n"
                + "    myTestDiv.textContent = '';\n"
                + "    checkChildren();\n"
                + "  }\n"
                + "  function checkChildren() {\n"
                + "    if (myTestDiv.childNodes.length == 0)\n"
                + "      alert('0');\n"
                + "    else\n"
                + "      alert(myTestDiv.childNodes.item(0).data);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myTestDiv'>something</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"something", "Hello World"})
    public void innerText() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    checkChildren();\n"
                + "    myTestDiv.innerText = 'Hello World';\n"
                + "    checkChildren();\n"
                + "  }\n"
                + "  function checkChildren() {\n"
                + "    if (myTestDiv.childNodes.length == 0)\n"
                + "      alert('0');\n"
                + "    else\n"
                + "      alert(myTestDiv.childNodes.item(0).data);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myTestDiv'>something</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"something", "3", "Hello", "[object HTMLBRElement]", "World"})
    public void innerTextLineBreak() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(myTestDiv.childNodes.item(0).data);\n"

                + "    myTestDiv.innerText = 'Hello\\nWorld';\n"
                + "    alert(myTestDiv.childNodes.length);\n"
                + "    alert(myTestDiv.childNodes.item(0).data);\n"
                + "    alert(myTestDiv.childNodes.item(1));\n"
                + "    alert(myTestDiv.childNodes.item(2).data);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myTestDiv'>something</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "1", " ", "0", "1", "undefined", "1", "[object Object]"})
    public void innerTextEmpty() {
        final String html = "<html><head>\n"
                + "<script>"
                +  " function test() {\n"
                + "    myTestDiv0.innerText = '';\n"
                + "    alert(myTestDiv0.childNodes.length);\n"

                + "    myTestDiv1.innerText = ' ';\n"
                + "    alert(myTestDiv1.childNodes.length);\n"
                + "    alert(myTestDiv1.childNodes.item(0).data);\n"

                + "    myTestDiv2.innerText = null;\n"
                + "    alert(myTestDiv2.childNodes.length);\n"

                + "    myTestDiv3.innerText = undefined;\n"
                + "    alert(myTestDiv3.childNodes.length);\n"
                + "    alert(myTestDiv3.childNodes.item(0).data);\n"

                + "    myTestDiv4.innerText = { a: 'b'};\n"
                + "    alert(myTestDiv4.childNodes.length);\n"
                + "    alert(myTestDiv4.childNodes.item(0).data);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myTestDiv0'>something</div>\n"
                + "  <div id='myTestDiv1'>something</div>\n"
                + "  <div id='myTestDiv2'>something</div>\n"
                + "  <div id='myTestDiv3'>something</div>\n"
                + "  <div id='myTestDiv4'>something</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"something", "0"})
    public void innerTextnull() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    checkChildren();\n"
                + "    myTestDiv.innerText = null;\n"
                + "    checkChildren();\n"
                + "  }\n"
                + "  function checkChildren() {\n"
                + "    if (myTestDiv.childNodes.length == 0)\n"
                + "      alert('0');\n"
                + "    else\n"
                + "      alert(myTestDiv.childNodes.item(0).data);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myTestDiv'>something</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"before\\nsvg-text\\nafter", "before\\nsvg-text\\nafter"})
    public void innerTextSVG() {
        final String html = "<html><head>\n"
                + "<script>"
                +  " function test() {\n"
                + "    alert(myTestDiv.innerText);\n"
                + "    alert(myTestDiv.outerText);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>abc"
                + "<div id='myTestDiv'>before<svg><title>svg-title</title><text>svg-text</text></svg>after</div>def"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"MyTitlevar i;", "MyTitlevar i;"})
    public void innerTextHead() {
        final String html = "<html><head>"
                + "<title>MyTitle</title>"
                + "<script>var i;</script>"
                + "</head>"
                + "<body onload='test()'>\n"
                + "<script>"
                +  " function test() {\n"
                + "    alert(document.head.innerText);\n"
                + "    alert(document.head.outerText);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"something", "0"})
    public void innerTextEmptyString() {
        final String html = "<html><head>\n"
                + "<script>"
                +  " function test() {\n"
                + "    checkChildren();\n"
                + "    myTestDiv.innerText = '';\n"
                + "    checkChildren();\n"
                + "  }\n"
                + "  function checkChildren() {\n"
                + "    if (myTestDiv.childNodes.length == 0)\n"
                + "      alert('0');\n"
                + "    else\n"
                + "      alert(myTestDiv.childNodes.item(0).data);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myTestDiv'>something</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"input handler", "blur input"})
    public void eventHandlerBubbleBlur() {
        events("blur");
    }

    @Test
    @Alerts({"input handler", "focus input"})
    public void eventHandlerBubbleFocus() {
        events("focus");
    }

    @Test
    @Alerts({"input handler", "click input", "div handler", "click div"})
    public void eventHandlerBubbleClick() {
        events("click");
    }

    private void events(final String type) {
        final String html = "<html><head>\n"
                + "</head>\n"
                + "<body>\n"
                + "<div id='div' on" + type + "='alert(\"div handler\")'>\n"
                + "<input id='input' on" + type + "='alert(\"input handler\")'>\n"
                + "</div>\n"
                + "<textarea id='log'></textarea>\n"
                + "<script>\n" + "function alert(x) {\n"
                + "  document.getElementById('log').value += x + '\\n';\n"
                + "}\n"
                + "function addListener(id, event) {\n"
                + "  var handler = function(e) { alert(event + ' ' + id) };\n"
                + "  var e = document.getElementById(id);\n"
                + "  e.addEventListener(event, handler, false);\n"
                + "}\n"
                + "var eventType = '" + type + "';\n"
                + "addListener('div', eventType);\n"
                + "addListener('input', eventType);\n"
                + "</script>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("input");
        elem.getOnclick();
    }

    @Test
    @Alerts({"null", "klazz"})
    public void setAttributeNodeUnknown() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var attribute = document.createAttribute('unknown');\n"
                + "    attribute.nodeValue = 'klazz';\n"
                + "    alert(document.body.setAttributeNode(attribute));\n"
                + "    alert(document.body.getAttributeNode('unknown').nodeValue);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "klazz"})
    public void setAttributeNodeUnknown2() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var attribute = document.createAttribute('unknown');\n"
                + "    alert(document.body.setAttributeNode(attribute));\n"
                + "    attribute.nodeValue = 'klazz';\n"
                + "    alert(document.body.getAttributeNode('unknown').nodeValue);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "klazz"})
    public void setAttributeNodeClass() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var attribute = document.createAttribute('class');\n"
                + "    attribute.nodeValue = 'klazz';\n"
                + "    alert(document.body.setAttributeNode(attribute));\n"
                + "    alert(document.body.getAttributeNode('class').nodeValue);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "klazz"})
    public void setAttributeNodeClass2() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var attribute = document.createAttribute('class');\n"
                + "    alert(document.body.setAttributeNode(attribute));\n"
                + "    attribute.nodeValue = 'klazz';\n"
                + "    alert(document.body.getAttributeNode('class').nodeValue);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "center", "true", "center", "false"})
    public void removeAttributeNode() {
        final String html
                = "<html>\n"
                + "<html><head><script>\n"

                + "  function test() {\n"
                + "    var e = document.getElementById('foo');\n"
                + "    alert(e.removeAttributeNode != null);\n"
                + "    alert(e.getAttribute('align'));\n"
                + "    alert(e.hasAttribute('align'));\n"
                + "    var attr = e.getAttributeNode('align');\n"
                + "    alert(attr.value);\n"
                + "    e.removeAttributeNode(attr);\n"
                + "    alert(e.hasAttribute('align'));\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='foo' align='center' />\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "div1"})
    public void querySelectorAll() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + "  .red   {color:#FF0000;}\n"
                + "  .green {color:#00FF00;}\n"
                + "  .blue  {color:#0000FF;}\n"
                + "</style>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var redTags = document.body.querySelectorAll('.green,.red');\n"
                + "  alert(redTags.length);\n"
                + "  alert(redTags.item(0).id);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1' class='red'>First</div>\n"
                + "  <div id='div2' class='red'>Second</div>\n"
                + "  <div id='div3' class='green'>Third</div>\n"
                + "  <div id='div4' class='blue'>Fourth</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "p1"})
    public void querySelectorAllOnDisconnectedElement() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var myDiv = document.createElement('div');\n"
                + "  myDiv.innerHTML = '<p id=\"p1\" class=\"TEST\"></p>';\n"
                + "  var found = myDiv.querySelectorAll('.TEST');\n"
                + "  alert(found.length);\n"
                + "  alert(found.item(0).id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void querySelectorAllBadSelector() {
        for (final String selector : HTMLDocumentTest.JQUERY_CUSTOM_SELECTORS) {
            doTestQuerySelectorAllBadSelector(selector);
        }

        // some other bad selectors tested in jQuery 1.8.2 tests
        final String[] otherBadSelectors = {":nth-child(2n+-0)", ":nth-child(2+0)",
                ":nth-child(- 1n)", ":nth-child(-1 n)"};
        for (final String selector : otherBadSelectors) {
            doTestQuerySelectorAllBadSelector(selector);
        }
    }

    private void doTestQuerySelectorAllBadSelector(final String selector) {
        final String html = "<html><body><div id='it'></div><script>\n"

                + "try {\n"
                + "  document.getElementById('it').querySelectorAll('" + selector + "');\n"
                + "  alert('working: " + selector + "');\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void querySelectorBadSelector() {
        for (final String selector : HTMLDocumentTest.JQUERY_CUSTOM_SELECTORS) {
            doTestQuerySelectorBadSelector(selector);
        }
    }

    private void doTestQuerySelectorBadSelector(final String selector) {
        final String html = "<html><body><div id='it'></div><script>\n"

                + "try {\n"
                + "  document.getElementById('it').querySelector('" + selector + "');\n"
                + "  alert('working " + selector + "');\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void querySelectorAllNoDuplication() {
        final String html = "<html><body>\n"
                + "<div><span>First</span></div>\n"
                + "<script>\n"
                + "  var tags = document.body.querySelectorAll('span, div > span');\n"
                + "  alert(tags.length);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"Old = <B>Old innerHTML</B><!-- old comment -->",
            "New = <B><I id=\"newElt\">New cell value</I></B>",
            "I"})
    public void getSetInnerHTMLComplex() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "  function alert(x) {\n"
                + "    document.getElementById('log').value += x + '\\n';\n"
                + "  }\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    myNode.innerHTML = ' <b><i id=\"newElt\">New cell value</i></b>';\n"
                + "    alert('New = ' + myNode.innerHTML);\n"
                + "    alert(document.getElementById('newElt').tagName);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <p id='myNode'><b>Old innerHTML</b><!-- old comment --></p>\n"
                + "  <textarea id='log'></textarea>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"Old = <B id=\"innerNode\">Old outerHTML</B>",
            "New = <B><I id=\"newElt\">New cell value</I></B",
            "I"})
    public void getSetOuterHTMLComplex() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    var innerNode = document.getElementById('innerNode');\n"
                + "    alert('Old = ' + innerNode.outerHTML);\n"
                + "    innerNode.outerHTML = ' <b><i id=\"newElt\">New cell value</i></b>';\n"
                + "    alert('New = ' + myNode.innerHTML);\n"
                + "    alert(document.getElementById('newElt').tagName);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<p id='myNode'><b id='innerNode'>Old outerHTML</b></p>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "true"})
    public void dispatchEvent2() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function simulateClick() {\n"
                + "    var evt = document.createEvent('MouseEvents');\n"
                + "    evt.initMouseEvent('click', true, true, window, 0, 0, 0, 0, 0,"
                + " false, false, false, false, 0, null);\n"
                + "    var cb = document.getElementById('checkbox');\n"
                + "    cb.dispatchEvent(evt);\n"
                + "  }\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('checkbox').checked);\n"
                + "    simulateClick();\n"
                + "    alert(document.getElementById('checkbox').checked);\n"
                + "  }\n"
                + "</script>\n"
                + "<body onload='test()'>\n"
                + "  <input type='checkbox' id='checkbox'/><label for='checkbox'>Checkbox</label>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void offsetLeft_PositionFixed() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"

                + "  </script>\n"
                + "  <style>\n"
                + "    body {\n"
                + "      padding: 0; margin:0;\n"
                + "    }\n"
                + "    #container {\n"
                + "      width: 200px; position: fixed; right: 0px;\n"
                + "    }\n"
                + "  </style>\n"
                + "</head>\n"
                + "<body onload=\"alert(document.getElementById('container').offsetLeft > 0)\">\n"
                + "  <div id=\"container\">\n"
                + "    <ul>\n"
                + "      <li><span>1st</span> List Item.</li>\n"
                + "      <li><span>Another</span> List Item.</li>\n"
                + "    </ul>\n"
                + "  </div>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"clicked", "fireEvent not available"})
    public void fireEventWithoutTemplate() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "    function doTest() {\n"
                        + "      var elem = document.getElementById('a');\n"
                        + "      if (!elem.fireEvent) { alert('fireEvent not available'); return }\n"
                        + "      elem.fireEvent('onclick');\n"
                        + "    }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "<body>\n"
                        + "  <div id='a' onclick='alert(\"clicked\")'>foo</div>\n"
                        + "  <div id='b' onmouseover='doTest()'>bar</div>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("a");
        elem.getOnclick();

        elem = (HTMLElementImpl) document.getElementById("b");
        elem.getOnmouseover();
    }

    @Test
    @Alerts({"DIV,DIV,http://www.w3.org/1999/xhtml,null,div", "svg,svg,http://www.w3.org/2000/svg,null,svg",
            "g,g,http://www.w3.org/2000/svg,null,g", "svg,svg,http://www.w3.org/2000/svg,null,svg"})
    public void variousNames() {
        final String html =
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" "
                        + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
                        + "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        
                        + "  function test() {\n"
                        + "    debug(document.getElementById('myDiv'));\n"
                        + "    debug(document.getElementById('mySVG'));\n"
                        + "    debug(document.getElementById('myG'));\n"
                        + "    debug(document.getElementById('mySVGWithNS'));\n"
                        + "  }\n"
                        + "  function debug(e) {\n"
                        + "    alert(e.nodeName + ',' + e.tagName + ',' + e.namespaceURI + ',' + e.prefix + ',' + e.localName);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id=myDiv>test</div>\n"
                        + "  <svg id='mySVG'>\n"
                        + "    <G id='myG'></G>\n"
                        + "  </svg>\n"
                        + "  <svg id='mySVGWithNS' xmlns='http://www.w3.org/2017/svg'>\n"
                        + "  </svg>\n"
                        + "</body></html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("success")
    public void setOnclick() {
        eventHandlerSetterGetterTest("onclick");
    }

    @Test
    @Alerts("success")
    public void setOndblclick() {
        eventHandlerSetterGetterTest("ondblclick");
    }

    @Test
    @Alerts("success")
    public void setOnblur() {
        eventHandlerSetterGetterTest("onblur");
    }

    @Test
    @Alerts("success")
    public void setOnfocus() {
        eventHandlerSetterGetterTest("onfocus");
    }

    @Test
    @Alerts("success")
    public void setOnkeydown() {
        eventHandlerSetterGetterTest("onkeydown");
    }

    @Test
    @Alerts("success")
    public void setOnkeypress() {
        eventHandlerSetterGetterTest("onkeypress");
    }

    @Test
    @Alerts("success")
    public void setOnkeyup() {
        eventHandlerSetterGetterTest("onkeyup");
    }

    @Test
    @Alerts("success")
    public void setOnmousedown() {
        eventHandlerSetterGetterTest("onmousedown");
    }

    @Test
    @Alerts("success")
    public void setOnmouseup() {
        eventHandlerSetterGetterTest("onmouseup");
    }

    @Test
    @Alerts("success")
    public void setOnmouseover() {
        eventHandlerSetterGetterTest("onmouseover");
    }

    @Test
    @Alerts("success")
    public void setOnmouseout() {
        eventHandlerSetterGetterTest("onmouseout");
    }

    @Test
    @Alerts("success")
    public void setOnmousemove() {
        eventHandlerSetterGetterTest("onmousemove");
    }

    @Test
    @Alerts("success")
    public void setOnresize() {
        eventHandlerSetterGetterTest("onresize");
    }

    @Test
    @Alerts("success")
    public void setOnerror() {
        eventHandlerSetterGetterTest("onerror");
    }

    /**
     * @param eventName the name of the
     */
    private void eventHandlerSetterGetterTest(final String eventName) {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n" + "function handler(event) {}\n"
                + "function test() {\n"
                + "  var oDiv = document.getElementById('myDiv');\n"
                + "  oDiv." + eventName + " = handler;\n"
                + "  if (oDiv." + eventName + " == handler) {\n"
                + "    alert('success');\n"
                + "  } else {\n"
                + "    alert('fail');\n"
                + "  }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<div id='myDiv'><br/><div><span>test</span></div></div>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }
}
