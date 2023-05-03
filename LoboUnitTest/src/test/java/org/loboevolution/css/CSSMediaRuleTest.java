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

import org.htmlunit.cssparser.dom.CSSMediaRuleImpl;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link CSSMediaRuleImpl}.
 */

public class CSSMediaRuleTest extends LoboUnitTest {

    @Test
    public void simple() {
        final String html
            = "<html><body>\n"
            + "<style>\n"
            + "  @media screen, print { p { background-color:#FFFFFF; }};\n"
            + "</style>\n"
            + "<script>\n"
            + "  var styleSheet = document.styleSheets[0];\n"
            + "  if (styleSheet.cssRules) {\n"
            + "    var rule = styleSheet.cssRules[0];\n"
            + "    var mediaList = rule.media;\n"
            + "    alert(mediaList.length);\n"
            + "    for (var i = 0; i < mediaList.length; i++) {\n"
            + "      alert(mediaList.item(i));\n"
            + "    }\n"
            + "    alert(mediaList.mediaText);\n"
            + "  } else {// Internet Explorer\n"
            + "    alert('Your browser does not support this example!');\n"
            + "  }\n"
            + "</script>\n"
            + "</body></html>";

        final String[] messages = {"2", "screen", "print", "screen, print"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void mediaQuery() {
        final String html
            = "<html><body>\n"
            + "<style>\n"
            + "  @media only screen and (color), print { p { background-color:#FFFFFF; }};\n"
            + "</style>\n"
            + "<script>\n"
            + "  var styleSheet = document.styleSheets[0];\n"
            + "  if (styleSheet.cssRules) {\n"
            + "    var rule = styleSheet.cssRules[0];\n"
            + "    var mediaList = rule.media;\n"
            + "    alert(mediaList.length);\n"
            + "    for (var i = 0; i < mediaList.length; i++) {\n"
            + "      alert(mediaList.item(i));\n"
            + "    }\n"
            + "    alert(mediaList.mediaText);\n"
            + "  } else {// Internet Explorer\n"
            + "    alert('Your browser does not support this example!');\n"
            + "  }\n"
            + "</script>\n"
            + "</body></html>";
        final String[] messages = {"2", "only screen and (color)", "print", "only screen and (color), print"};
        checkHtmlAlert(html, messages);
    }
}
