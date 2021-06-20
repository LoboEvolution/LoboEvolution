/*
 * Copyright (c) 2002-2021 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.loboevolution.css;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for ComputedFontImpl
 */

public class ComputedFontTest extends LoboUnitTest {

    @Test
    public void fontSizeEm() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('mydiv');\n"
                + "    var style = window.getComputedStyle(div, null);\n"
                + "    alert(div.style.fontSize);\n"
                + "    alert(style.fontSize);\n"
                + "    div.style.fontSize = '2em';\n"
                + "    alert(div.style.fontSize);\n"
                + "    alert(style.fontSize);\n"
                + "    div.style.fontSize = '150%';\n"
                + "    alert(div.style.fontSize);\n"
                + "    alert(style.fontSize);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='mydiv'></div>\n"
                + "</body></html>";
        final String[] messages = {"", "16px", "2em", "32px", "150%", "24px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void fontInitial() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    debug(div);\n"
                + "    document.body.appendChild(div);\n"
                + "    debug(div);\n"
                + "  }\n"
                + "  function debug(div) {\n"
                + "    var style = window.getComputedStyle(div, null);\n"
                + "    alert(div.style.font);\n"
                + "    alert(style.font);\n"
                + "    alert(div.style.fontStyle);\n"
                + "    alert(style.fontStyle);\n"
                + "    alert(div.style.fontVariant);\n"
                + "    alert(style.fontVariant);\n"
                + "    alert(div.style.fontWeight);\n"
                + "    alert(style.fontWeight);\n"
                + "    alert(div.style.fontSize);\n"
                + "    alert(style.fontSize);\n"
                + "    alert(div.style.lineHeight);\n"
                + "    alert(style.lineHeight);\n"
                + "    alert(div.style.fontFamily);\n"
                + "    alert(style.fontFamily);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "16px \"Times New Roman\"",
                "", "normal", "", "normal", "", "400", "", "16px", "", "normal", "", "\"Times New Roman\""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void fontStyle() {
        final String[] messages = {"15px arial, sans-serif", "15px arial, sans-serif",
                "normal", "normal",
                "oblique 15px arial, sans-serif", "italic 15px arial, sans-serif",
                "oblique", "italic"};
        font("15px arial, sans-serif", "fontStyle", "oblique", messages);
    }

    @Test
    public void wrongFontFamily() {
        final String[] messages = {"", "16px \"Times New Roman\"", "", "\"Times New Roman\""};
        font("xyz", "fontFamily", null, messages);
    }

    @Test
    public void minimalFontFamily() {
        final String[] messages = {"1px xyz", "1px xyz", "xyz", "xyz", "1px abc", "1px abc", "abc", "abc"};
        font("1px xyz", "fontFamily", "abc", messages);
    }


    @Test
    public void minimalFontFamilyReversed() {
        final String[] messages = {"", "16px \"Times New Roman\"", "", "\"Times New Roman\"", "", "16px abc", "abc", "abc"};
        font("xyz 1px", "fontFamily", "abc", messages);
    }

    @Test
    public void minimalLineHeight() {
        final String[] messages = {"1px / 2px xyz", "1px / 2px xyz", "2px", "2px", "1px xyz", "1px xyz", "normal", "normal"};
        font("1px/2px xyz", "lineHeight", "normal", messages);
    }

    @Test
    public void minimalLineHeightSpace() {
        final String[] messages = {"1px / 2px xyz", "1px / 2px xyz", "2px", "2px", "1px xyz", "1px xyz", "normal", "normal"};
        font("1px / 2px xyz", "lineHeight", "normal", messages);
    }


    @Test
    public void minimalLineHeightSpace2() {
        final String[] messages = {"1px / 2px xyz", "1px / 2px xyz", "2px", "2px", "1px xyz", "1px xyz", "normal", "normal"};
        font("1px/ 2px xyz", "lineHeight", "normal", messages);
    }

    @Test
    public void minimalLineHeightSpace3() {
        final String[] messages = {"1px / 2px xyz", "1px / 2px xyz", "2px", "2px", "1px xyz", "1px xyz", "normal", "normal"};
        font("1px /2px xyz", "lineHeight", "normal", messages);
    }

    @Test
    public void minimalLineHeightSpace4() {
        final String[] messages = {"1px / 2px xyz", "1px / 2px xyz", "2px", "2px", "1px xyz", "1px xyz", "normal", "normal"};
        font("1px  /2px xyz", "lineHeight", "normal", messages);
    }

    private void font(final String fontToSet, final String property, final String value, final String[] messages) {
        String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('mydiv');\n"
                + "    div.style.font = '" + fontToSet + "';\n"
                + "    debug(div);\n";

        if (value != null) {
            html += "    div.style." + property + " = '" + value + "';\n"
                    + "    debug(div);\n";
        }

        html += "  }\n"
                + "  function debug(div) {\n"
                + "    var style = window.getComputedStyle(div, null);\n"
                + "    alert(div.style.font);\n"
                + "    alert(style.font);\n"
                + "    alert(div.style." + property + ");\n"
                + "    alert(style." + property + ");\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='mydiv'></div>\n"
                + "</body></html>";
        checkHtmlAlert(html, messages);
    }
}
