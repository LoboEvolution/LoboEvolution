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

import org.htmlunit.cssparser.dom.CSSFontFaceRuleImpl;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link CSSFontFaceRuleImpl}.
 */

public class CSSFontFaceRuleTest extends LoboUnitTest {

    @Test
    public void simple() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @font-face { font-family: Delicious; src: url('Delicious-Bold.otf'); }\n"
                + "  h3 { font-family: Delicious;  }\n"
                + "</style>\n"
                + "<script>\n"
                + "try {\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule);\n"
                + "  alert(rule.type);\n"
                + "  alert(rule.cssText);\n"
                + "}\n"
                + "catch (e) { alert('exception'); }\n"
                + "</script></body></html>";
        final String[] messages = {"[object CSSFontFaceRule]", "5",
                "@font-face { font-family: Delicious; src: url(\"Delicious-Bold.otf\"); }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void urlSlashSlashColon() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @font-face { font-family: Delicious; src: url(//:); }\n"
                + "  h3 { font-family: Delicious;  }\n"
                + "</style>\n"
                + "<script>\n"
                + "try {\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule.cssText);\n"
                + "}\n"
                + "catch (e) { alert('exception'); }\n"
                + "</script></body></html>";
        final String[] messages = {"@font-face { font-family: Delicious; src: url(\"//:\"); }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void urlSlashColon() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @font-face { font-family: Delicious; src: url(/:); }\n"
                + "  h3 { font-family: Delicious;  }\n"
                + "</style>\n"
                + "<script>\n"
                + "try {\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule.cssText);\n"
                + "}\n"
                + "catch (e) { alert('exception'); }\n"
                + "</script></body></html>";
        final String[] messages = {"@font-face { font-family: Delicious; src: url(\"/:\"); }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void urlSlashSlash() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @font-face { font-family: Delicious; src: url(//); }\n"
                + "  h3 { font-family: Delicious;  }\n"
                + "</style>\n"
                + "<script>\n"
                + "try {\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule.cssText);\n"
                + "}\n"
                + "catch (e) { alert('exception'); }\n"
                + "</script></body></html>";
        final String[] messages = {"@font-face { font-family: Delicious; src: url(\"//\"); }"};
        checkHtmlAlert(html, messages);
    }
}
