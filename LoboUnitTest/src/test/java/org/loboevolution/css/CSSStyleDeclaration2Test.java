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
package org.loboevolution.css;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link CSSStyleDeclaration}.
 */
@ExtendWith(AlertsExtension.class)
public class CSSStyleDeclaration2Test extends LoboUnitTest {


    @Test
    @Alerts({"success", "success", "success", "success"})
    public void widthLikeProperties() {
        widthLikeProperties("bottom", "left", "right", "top");
    }

    @Test
    @Alerts({"borderBottomWidth 42% - 42em", "borderLeftWidth 42% - 42em",
            "borderRightWidth 42% - 42em", "borderTopWidth 42% - 42em"})
    public void widthLikePropertiesBorder() {
        widthLikeProperties("borderBottomWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth");
    }

    @Test
    @Alerts({"success", "success", "success", "success"})
    public void widthLikePropertiesMargin() {
        widthLikeProperties("marginBottom", "marginLeft", "marginRight", "marginTop");
    }

    @Test
    @Alerts({"success", "success", "success", "success"})
    public void widthLikePropertiesPadding() {
        widthLikeProperties("paddingBottom", "paddingLeft", "paddingRight", "paddingTop");
    }

    @Test
    @Alerts({"success", "success", "success", "success", "success", "success"})
    public void widthLikePropertiesHeightWidth() {
        widthLikeProperties("height", "width", "maxHeight", "maxWidth", "minHeight", "minWidth");
    }

    @Test
    @Alerts({"success", "letterSpacing 42% - 42em",
            "outlineWidth 42.0 - ; 42.7 - ; 42 - ; 42% - 42em",
            "success",
            "success",
            "wordSpacing 42% - 42em"})
    public void widthLikePropertiesTont() {
        widthLikeProperties("fontSize", "letterSpacing", "outlineWidth", "textIndent",
                "verticalAlign", "wordSpacing");
    }

    private void widthLikeProperties(final String... properties) {
        final String props = "'" + String.join("', '", properties) + "'";
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var properties = [" + props + "];\n"
                + "\n"
                + "  for (var prop in properties) {\n"
                + "    prop = properties[prop];\n"
                + "    var result = '';\n"
                + "    var node = document.createElement('div');\n"
                + "    if (node.style[prop] != '') {\n"
                + "      if (result == '') { result += prop } else { result += '; ' }\n"
                + "      result += ' initial ' + node.style[prop];\n"
                + "    }\n"
                + "    node.style[prop] = '42.0';\n"
                + "    if (node.style[prop] != '42px') {\n"
                + "      if (result == '') { result += prop } else { result += ';' }\n"
                + "      result += ' 42.0 - ' + node.style[prop];\n"
                + "    }\n"
                + "    node.style[prop] = '42.7';\n"
                + "    var expected = document.all ? '42px' : '42.7px';\n"
                + "    if (node.style[prop] != expected) {\n"
                + "      if (result == '') { result += prop } else { result += ';' }\n"
                + "      result += ' 42.7 - ' + node.style[prop];\n"
                + "    }\n"
                + "    node.style[prop] = '42';\n"
                + "    if (node.style[prop] != '42px') {\n"
                + "      if (result == '') { result += prop } else { result += ';' }\n"
                + "      result += ' 42 - ' + node.style[prop];\n"
                + "    }\n"
                + "    node.style[prop] = '42px';\n"
                + "    if (node.style[prop] != '42px') {\n"
                + "      if (result == '') { result += prop } else { result += ';' }\n"
                + "      result += ' 42px - ' + node.style[prop];\n"
                + "    }\n"
                + "    node.style[prop] = '42mm';\n"
                + "    if (node.style[prop] != '42mm') {\n"
                + "      if (result == '') { result += prop } else { result += ';' }\n"
                + "      result += ' 42mm - ' + node.style[prop];\n"
                + "    }\n"
                + "    node.style[prop] = '42em';\n"
                + "    if (node.style[prop] != '42em') {\n"
                + "      if (result == '') { result += prop } else { result += ';' }\n"
                + "      result += ' 42em - ' + node.style[prop];\n"
                + "    }\n"
                + "    node.style[prop] = '42%';\n"
                + "    if (node.style[prop] != '42%') {\n"
                + "      if (result == '') { result += prop } else { result += ';' }\n"
                + "      result += ' 42% - ' + node.style[prop];\n"
                + "    }\n"
                + "   alert(result == '' ? 'success' : result);\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "baseline", "sub", "super", "text-top",
            "text-bottom", "middle", "top", "bottom",
            "1.7em", "4px", "32%",
            "inherit", "initial", "revert", "unset",
            "unset", "unset", "unset"})
    public void verticalAlign() {
        checkPropertyValues("vertical-align",
                "baseline", "sub", "super", "text-top", "text-bottom", "middle", "top", "bottom",
                "1.7em", "4px", "32%",
                "inherit", "initial", "revert", "unset",
                "1 px", "7mond", "not-supported");
        checkPropertyValuesDirect("verticalAlign",
                "baseline", "sub", "super", "text-top", "text-bottom", "middle", "top", "bottom",
                "1.7em", "4px", "32%",
                "inherit", "initial", "revert", "unset",
                "1 px", "7mond", "not-supported");
    }

    private void checkPropertyValuesDirect(final String property, final String... propertyValues) {
        final String propValues = "'" + String.join("', '", propertyValues) + "'";
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var propValues = [" + propValues + "];\n"
                + "\n"
                + "  var node = document.createElement('div');\n"
                + "  var styleVal = node.style." + property + ";\n"
                + " alert(styleVal);\n"
                + "  propValues.forEach(propValue => {\n"
                + "    node.style." + property + " = propValue;\n"
                + "    styleVal = node.style." + property + ";\n"
                + "   alert(styleVal);\n"
                + "  });\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    private void checkPropertyValues(final String property, final String... propertyValues) {
        final String propValues = "'" + String.join("', '", propertyValues) + "'";
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var propValues = [" + propValues + "];\n"
                + "\n"
                + "  var node = document.createElement('div');\n"
                + "  var styleVal = node.style['" + property + "'];\n"
                + " alert(styleVal);\n"
                + "  propValues.forEach(propValue => {\n"
                + "    node.style['" + property + "'] = propValue;\n"
                + "    styleVal = node.style['" + property + "'];\n"
                + "   alert(styleVal);\n"
                + "  });\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0"})
    public void setLength() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var style = document.body.style;\n"
                + "  try {\n"
                + "   alert(style.length);\n"
                + "    style.length = 100;\n"
                + "   alert(style.length);\n"
                + "  } catch(e) {alert(e); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "Type error"})
    public void setLengthStrictMode() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  'use strict';\n"
                + "  var style = document.body.style;\n"
                + "  try {\n"
                + "   alert(style.length);\n"
                + "    style.length = 100;\n"
                + "   alert(style.length);\n"
                + "  } catch(e) {alert('Type error'); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"function values() { [native code] }", "no for..of", "display"})
    public void iterator() {
        final String html = "<html><head>\n"
                + "</head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var style = document.body.style;\n"
                + "    if (typeof Symbol != 'undefined') {\n"
                + "     alert(style[Symbol.iterator]);\n"
                + "    }\n"
                + "    if (!style.forEach) {\n"
                + "     alert('no for..of');\n"
                + "    }\n"
                + "    if (typeof Symbol === 'undefined') {\n"
                + "      return;\n"
                + "    }\n"
                + "    for (var i of style) {\n"
                + "     alert(i);\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()' style='display: inline'>\n"
                + "  <div></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
