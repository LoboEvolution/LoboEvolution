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
 * Tests for {@link CSSStyleDeclaration} background, font and border shorthand.
 */
@ExtendWith(AlertsExtension.class)
public class CSSStyleDeclaration3Test extends LoboUnitTest {


    @Test
    @Alerts({"null", "null", "null", "null", "null"})
    public void backgroundEmpty() {
        background("");
    }

    @Test
    @Alerts({"red", "initial", "initial", "initial", "initial"})
    public void backgroundColorRed() {
        background("rEd");
    }

    @Test
    @Alerts({"rgb(255, 204, 221)", "initial", "initial", "initial", "initial"})
    public void backgroundColorHex() {
        background("#fFccdd");
    }

    @Test
    @Alerts({"rgb(255, 204, 221)", "initial", "initial", "initial", "initial"})
    public void backgroundColorHexShort() {
        background("#fCd");
    }

    @Test
    @Alerts({"rgb(20, 40, 60)", "initial", "initial", "initial", "initial"})
    public void backgroundColorRgb() {
        background("rGb(20, 40, 60)");
    }

    @Test
    @Alerts({"initial", "url(\"myImage.png\")", "initial", "initial", "initial"})
    public void backgroundImage() {
        background("uRl(myImage.png)");
    }

    @Test
    @Alerts({"initial", "initial", "repeat-x", "initial", "initial"})
    public void backgroundRepeat() {
        background("repeat-x");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "20px 100%", "initial"})
    public void backgroundPosition() {
        background("20px 100%");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "right bottom", "initial"})
    public void backgroundPosition2() {
        background("bottom right");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "10em bottom", "initial"})
    public void backgroundPosition3() {
        background("10em bottom");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "10em center", "initial"})
    public void backgroundPosition4() {
        background("10em center");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "fixed"})
    public void backgroundAttachment() {
        background("fixed");
    }

    @Test
    @Alerts({"red", "url(\"myImage.png\")", "initial", "initial", "initial"})
    public void backgroundMixed() {
        background("red url(\"myImage.png\")");
    }

    @Test
    @Alerts({"rgb(255, 255, 255)", "initial", "no-repeat", "20px 100px", "initial"})
    public void backgroundMixed2() {
        background("#fff no-repeat 20px 100px");
    }

    private void background(final String backgroundStyle) {
        final String html =
                "<html>\n"
                        + "<body>\n"
                        + "  <div id='tester' style='background: " + backgroundStyle + "' >hello</div>\n"
                        + "  <script>\n"
                        + "   var myStyle = document.getElementById('tester').style;\n"
                        + "   alert(myStyle.backgroundColor);\n"
                        + "   alert(myStyle.backgroundImage);\n"
                        + "   alert(myStyle.backgroundRepeat);\n"
                        + "   alert(myStyle.backgroundPosition);\n"
                        + "   alert(myStyle.backgroundAttachment);\n"
                        + "  </script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null", "null", "null", "null"})
    public void backgroundCssEmpty() {
        backgroundCss("");
    }

    @Test
    @Alerts({"red", "initial", "initial", "initial", "initial"})
    public void backgroundCssColorRed() {
        backgroundCss("rEd");
    }

    @Test
    @Alerts({"rgb(255, 204, 221)", "initial", "initial", "initial", "initial"})
    public void backgroundCssColorHex() {
        backgroundCss("#fFccdd");
    }

    @Test
    @Alerts({"rgb(255, 204, 221)", "initial", "initial", "initial", "initial"})
    public void backgroundCssColorHexShort() {
        backgroundCss("#fCd");
    }

    @Test
    @Alerts({"rgb(20, 40, 60)", "initial", "initial", "initial", "initial"})
    public void backgroundCssColorRgb() {
        backgroundCss("rGb(20, 40, 60)");
    }

    @Test
    @Alerts({"initial", "url(\"myImage.png\")", "initial", "initial", "initial"})
    public void backgroundCssImage() {
        backgroundCss("uRl(myImage.png)");
    }

    @Test
    @Alerts({"initial", "initial", "repeat-x", "initial", "initial"})
    public void backgroundCssRepeat() {
        backgroundCss("repeat-x");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "20px 100%", "initial"})
    public void backgroundCssPosition() {
        backgroundCss("20px 100%");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "right bottom", "initial"})
    public void backgroundCssPosition2() {
        backgroundCss("bottom right");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "left bottom", "initial"})
    public void backgroundCssPosition3() {
        backgroundCss("left bottom");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "center top", "initial"})
    public void backgroundCssPosition4() {
        backgroundCss("top center");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "fixed"})
    public void backgroundCssAttachment() {
        backgroundCss("fixed");
    }

    @Test
    @Alerts({"red", "url(\"myImage.png\")", "initial", "initial", "initial"})
    public void backgroundCssMixed() {
        backgroundCss("red url(\"myImage.png\")");
    }

    @Test
    @Alerts({"rgb(255, 255, 255)", "initial", "initial", "20px 100px", "initial"})
    public void backgroundCssMixed2() {
        backgroundCss("#fff 20px 100px");
    }

    private void backgroundCss(final String backgroundStyle) {
        final String html =
                "<html>\n"
                        + "</head>\n"
                        + "  <style type='text/css'>div { background: " + backgroundStyle + " }</style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "  <div id='tester'>hello</div>\n"
                        + "  <script>\n"
                        + "   var myStyle = document.styleSheets[0].cssRules[0].style;\n"
                        + "   alert(myStyle.backgroundColor);\n"
                        + "   alert(myStyle.backgroundImage);\n"
                        + "   alert(myStyle.backgroundRepeat);\n"
                        + "   alert(myStyle.backgroundPosition);\n"
                        + "   alert(myStyle.backgroundAttachment);\n"
                        + "  </script>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"initial", "null", "null", "null", "null"})
    public void backgroundComputedEmpty() {
        backgroundComputed("");
    }

    @Test
    @Alerts({"rgb(255, 0, 0)", "initial", "initial", "initial", "initial"})
    public void backgroundComputedColorRed() {
        backgroundComputed("rEd");
    }

    @Test
    @Alerts({"rgb(255, 204, 221)", "initial", "initial", "initial", "initial"})
    public void backgroundComputedColorHex() {
        backgroundComputed("#fFccdd");
    }

    @Test
    @Alerts({"rgb(255, 204, 221)", "initial", "initial", "initial", "initial"})
    public void backgroundComputedColorHexShort() {
        backgroundComputed("#fCd");
    }

    @Test
    @Alerts({"rgb(20, 40, 60)", "initial", "initial", "initial", "initial"})
    public void backgroundComputedColorRgb() {
        backgroundComputed("rGb(20, 40, 60)");
    }

    @Test
    @Alerts({"initial", "url(\"myImage.png\")", "initial", "initial", "initial"})
    public void backgroundComputedImage() {
        backgroundComputed("uRl(myImage.png)");
    }

    @Test
    @Alerts({"initial", "initial", "repeat-x", "initial", "initial"})
    public void backgroundComputedRepeat() {
        backgroundComputed("repeat-x");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "20px 100%", "initial"})
    public void backgroundComputedPosition() {
        backgroundComputed("20px 100%");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "100% 100%", "initial"})
    public void backgroundComputedPosition2() {
        backgroundComputed("bottom right");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "0% 100%", "initial"})
    public void backgroundComputedPosition3() {
        backgroundComputed("left bottom");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "50% 0%", "initial"})
    public void backgroundComputedPosition4() {
        backgroundComputed("top center");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "fixed"})
    public void backgroundComputedAttachment() {
        backgroundComputed("fixed");
    }

    @Test
    @Alerts({"rgb(255, 0, 0)", "url(\"myImage.png\")", "initial", "initial", "initial"})
    public void backgroundComputedMixed() {
        backgroundComputed("red url(\"myImage.png\")");
    }

    @Test
    @Alerts({"rgb(255, 255, 255)", "initial", "initial", "20px 100px", "initial"})
    public void backgroundComputedMixed2() {
        backgroundComputed("white 20px 100px");
    }

    private void backgroundComputed(final String backgroundStyle) {
        final String html =
                "<html>\n"
                        + "</head>\n"
                        + "  <style type='text/css'>div { background: " + backgroundStyle + " }</style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "  <div id='tester'>hello</div>\n"
                        + "  <script>\n"
                        + "    var myDiv = document.getElementById('tester');\n"
                        + "    var myStyle = window.getComputedStyle(myDiv, null);\n"
                        + "   alert(myStyle.backgroundColor);\n"
                        + "   alert(myStyle.backgroundImage);\n"
                        + "   alert(myStyle.backgroundRepeat);\n"
                        + "   alert(myStyle.backgroundPosition);\n"
                        + "   alert(myStyle.backgroundAttachment);\n"
                        + "  </script>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "", "", "", "", ""})
    public void fontEmpty() {
        font("");
    }

    @Test
    @Alerts({"", "", "", "", "", ""})
    public void fontSizeOnly() {
        font("14px");
    }

    @Test
    @Alerts({"", "", "", "", "", ""})
    public void fontFamilyOnly() {
        font("sans-serif");
    }

    @Test
    @Alerts({"", "", "", "", "", ""})
    public void fontAllExceptSizeAndFamily() {
        font("italic small-caps bold");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "sans-serif"})
    public void fontSizeAndFamily() {
        font("14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "\"Gill Sans Extrabold\""})
    public void fontFamilyWithSpaces() {
        font("14pX Gill Sans Extrabold");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "\"Gill Sans Extrabold\""})
    public void fontFamilyQuoted() {
        font("14pX \"Gill Sans Extrabold\"");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "\"Gill Sans Extrabold\", serif"})
    public void fontFamilyMultiple() {
        font("14pX \"Gill Sans Extrabold\", serif");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14%", "normal", "sans-serif"})
    public void fontSizePercent() {
        font("14.0% sAns-serif");
    }

    @Test
    @Alerts({"italic", "normal", "normal", "14px", "normal", "sans-serif"})
    public void fontStyle() {
        font("iTalic 14pX sAns-serif");
    }

    @Test
    @Alerts({"oblique 10deg", "normal", "normal", "14px", "normal", "sans-serif"})
    public void fontStyleWithDegree() {
        font("oBlique 10deg 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "small-caps", "normal", "14px", "normal", "sans-serif"})
    public void fontVariant() {
        font("sMall-caps 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "bold", "14px", "normal", "sans-serif"})
    public void fontWeight() {
        font("bOld 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "800", "14px", "normal", "sans-serif"})
    public void fontWeightNumber() {
        font("800 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "18em", "sans-serif"})
    public void fontLineHeight() {
        font("14pX/18eM sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "18%", "sans-serif"})
    public void fontLineHeightPercent() {
        font("14pX/18.0% sAns-serif");
    }

    @Test
    @Alerts({"italic", "small-caps", "bold", "14px", "18em", "sans-serif"})
    public void fontAll() {
        font("iTalic sMall-caps bOld 14pX/18eM sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "sans-serif", "undefined", "normal"})
    public void fontSizeAdjustBefore() {
        font("14pX sAns-serif", "font-size-adjust: 0.5");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "sans-serif", "undefined", "normal"})
    public void fontSizeAdjustAfter() {
        font("14pX sAns-serif; font-size-adjust: 0.5", "");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "sans-serif", "undefined", "normal"})
    public void fontStretchBefore() {
        font("14pX sAns-serif", "font-stretch: expanded");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "sans-serif", "undefined", "expanded"})
    public void fontStretchAfter() {
        font("14pX sAns-serif; font-stretch: expanded", "");
    }

    private void font(final String fontStyle) {
        final String html =
                "<html>\n"
                        + "<body>\n"
                        + "  <div id='tester' style='font: " + fontStyle + "' >hello</div>\n"
                        + "  <script>\n"

                        + "    var myStyle = document.getElementById('tester').style;\n"
                        + "   alert(myStyle.fontStyle);\n"
                        + "   alert(myStyle.fontVariant);\n"
                        + "   alert(myStyle.fontWeight);\n"
                        + "   alert(myStyle.fontSize);\n"
                        + "   alert(myStyle.lineHeight);\n"
                        + "   alert(myStyle.fontFamily);\n"
                        + "  </script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    private void font(final String fontStyle, final String otherStyle) {
        final String html =
                "<html>\n"
                        + "<body>\n"
                        + "  <div id='tester' style='" + otherStyle + "; font: " + fontStyle + "' >hello</div>\n"
                        + "  <script>\n"

                        + "    var myStyle = document.getElementById('tester').style;\n"
                        + "   alert(myStyle.fontStyle);\n"
                        + "   alert(myStyle.fontVariant);\n"
                        + "   alert(myStyle.fontWeight);\n"
                        + "   alert(myStyle.fontSize);\n"
                        + "   alert(myStyle.lineHeight);\n"
                        + "   alert(myStyle.fontFamily);\n"
                        + "   alert(myStyle.fontSizeAdjust);\n"
                        + "   alert(myStyle.fontStretch);\n"
                        + "  </script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "", "", "", "", ""})
    public void fontCssEmpty() {
        fontCss("");
    }

    @Test
    @Alerts({"", "", "", "", "", ""})
    public void fontCssSizeOnly() {
        fontCss("14px");
    }

    @Test
    @Alerts({"", "", "", "", "", ""})
    public void fontCssFamilyOnly() {
        fontCss("sans-serif");
    }

    @Test
    @Alerts({"", "", "", "", "", ""})
    public void fontCssAllExceptSizeAndFamily() {
        fontCss("italic small-caps bold");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "sans-serif"})
    public void fontCssSizeAndFamily() {
        fontCss("14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "\"Gill Sans Extrabold\""})
    public void fontCssFamilyWithSpaces() {
        fontCss("14pX Gill Sans Extrabold");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "\"Gill Sans Extrabold\""})
    public void fontCssFamilyQuoted() {
        fontCss("14pX \"Gill Sans Extrabold\"");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "\"Gill Sans Extrabold\", serif"})
    public void fontCssFamilyMultiple() {
        fontCss("14pX \"Gill Sans Extrabold\", serif");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14%", "normal", "sans-serif"})
    public void fontCssSizePercent() {
        fontCss("14.0% sAns-serif");
    }

    @Test
    @Alerts({"italic", "normal", "normal", "14px", "normal", "sans-serif"})
    public void fontCssStyle() {
        fontCss("iTalic 14pX sAns-serif");
    }

    @Test
    @Alerts({"oblique 10deg", "normal", "normal", "14px", "normal", "sans-serif"})
    public void fontCssStyleWithDegree() {
        fontCss("oBlique 10deg 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "small-caps", "normal", "14px", "normal", "sans-serif"})
    public void fontCssVariant() {
        fontCss("sMall-caps 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "bold", "14px", "normal", "sans-serif"})
    public void fontCssWeight() {
        fontCss("bOld 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "800", "14px", "normal", "sans-serif"})
    public void fontCssWeightNumber() {
        fontCss("800 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "18em", "sans-serif"})
    public void fontCssLineHeight() {
        fontCss("14pX/18eM sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "18%", "sans-serif"})
    public void fontCssLineHeightPercent() {
        fontCss("14pX/18.0% sAns-serif");
    }

    @Test
    @Alerts({"italic", "small-caps", "bold", "14px", "18em", "sans-serif"})
    public void fontCssAll() {
        fontCss("iTalic sMall-caps bOld 14pX/18eM sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "sans-serif", "undefined", "normal"})
    public void fontCssSizeAdjustBefore() {
        fontCss("14pX sAns-serif", "font-size-adjust: 0.5");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "sans-serif", "undefined", "normal"})
    public void fontCssSizeAdjustAfter() {
        fontCss("14pX sAns-serif; font-size-adjust: 0.5", "");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "sans-serif", "undefined", "normal"})
    public void fontCssStretchBefore() {
        fontCss("14pX sAns-serif", "font-stretch: expanded");
    }

    @Test
    @Alerts({"normal", "normal", "normal", "14px", "normal", "sans-serif", "undefined", "expanded"})
    public void fontCssStretchAfter() {
        fontCss("14pX sAns-serif; font-stretch: expanded", "");
    }

    private void fontCss(final String fontStyle) {
        final String html =
                "<html>\n"
                        + "</head>\n"
                        + "  <style type='text/css'>div { font: " + fontStyle + " }</style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "  <div id='tester'>hello</div>\n"
                        + "  <script>\n"
                        + "    var myStyle = document.styleSheets[0].cssRules[0].style;\n"
                        + "   alert(myStyle.fontStyle);\n"
                        + "   alert(myStyle.fontVariant);\n"
                        + "   alert(myStyle.fontWeight);\n"
                        + "   alert(myStyle.fontSize);\n"
                        + "   alert(myStyle.lineHeight);\n"
                        + "   alert(myStyle.fontFamily);\n"
                        + "  </script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    private void fontCss(final String fontStyle, final String otherStyle) {
        final String html =
                "<html>\n"
                        + "</head>\n"
                        + "  <style type='text/css'>div { " + otherStyle + "; font: " + fontStyle + " }</style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "  <div id='tester'>hello</div>\n"
                        + "  <script>\n"
                        + "    var myStyle = document.styleSheets[0].cssRules[0].style;\n"
                        + "   alert(myStyle.fontStyle);\n"
                        + "   alert(myStyle.fontVariant);\n"
                        + "   alert(myStyle.fontWeight);\n"
                        + "   alert(myStyle.fontSize);\n"
                        + "   alert(myStyle.lineHeight);\n"
                        + "   alert(myStyle.fontFamily);\n"
                        + "   alert(myStyle.fontSizeAdjust);\n"
                        + "   alert(myStyle.fontStretch);\n"
                        + "  </script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"normal", "normal", "400", "16px", "normal", "\"Times New Roman\""})
    public void fontComputedEmpty() {
        fontComputed("");
    }

    @Test
    @Alerts({"normal", "normal", "400", "16px", "normal", "\"Times New Roman\""})
    public void fontComputedSizeOnly() {
        fontComputed("14px");
    }

    @Test
    @Alerts({"normal", "normal", "400", "16px", "normal", "\"Times New Roman\""})
    public void fontComputedFamilyOnly() {
        fontComputed("sans-serif");
    }

    @Test
    @Alerts({"normal", "normal", "400", "16px", "normal", "\"Times New Roman\""})
    public void fontComputedAllExceptSizeAndFamily() {
        fontComputed("italic small-caps bold");
    }

    @Test
    @Alerts({"normal", "normal", "400", "14px", "normal", "sans-serif"})
    public void fontComputedSizeAndFamily() {
        fontComputed("14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "400", "14px", "normal", "\"Gill Sans Extrabold\""})
    public void fontComputedFamilyWithSpaces() {
        fontComputed("14pX Gill Sans Extrabold");
    }

    @Test
    @Alerts({"normal", "normal", "400", "14px", "normal", "\"Gill Sans Extrabold\""})
    public void fontComputedFamilyQuoted() {
        fontComputed("14pX \"Gill Sans Extrabold\"");
    }

    @Test
    @Alerts({"normal", "normal", "400", "14px", "normal", "\"Gill Sans Extrabold\", serif"})
    public void fontComputedFamilyMultiple() {
        fontComputed("14pX \"Gill Sans Extrabold\", serif");
    }

    @Test
    @Alerts({"normal", "normal", "400", "6px", "normal", "sans-serif"})
    public void fontComputedSizePercent() {
        fontComputed("14.0% sAns-serif");
    }

    @Test
    @Alerts({"italic", "normal", "400", "14px", "normal", "sans-serif"})
    public void fontComputedStyle() {
        fontComputed("iTalic 14pX sAns-serif");
    }

    @Test
    @Alerts({"oblique 10deg", "normal", "400", "14px", "normal", "sans-serif"})
    public void fontComputedStyleWithDegree() {
        fontComputed("oBlique 10deg 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "small-caps", "400", "14px", "normal", "sans-serif"})
    public void fontComputedVariant() {
        fontComputed("sMall-caps 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "700", "14px", "normal", "sans-serif"})
    public void fontComputedWeight() {
        fontComputed("bOld 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "800", "14px", "normal", "sans-serif"})
    public void fontComputedWeightNumber() {
        fontComputed("800 14pX sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "400", "14px", "252px", "sans-serif"})
    public void fontComputedLineHeight() {
        fontComputed("14pX/18eM sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "400", "14px", "2.52px", "sans-serif"})
    public void fontComputedLineHeightPercent() {
        fontComputed("14pX/18.0% sAns-serif");
    }

    @Test
    @Alerts({"italic", "small-caps", "700", "14px", "252px", "sans-serif"})
    public void fontComputedAll() {
        fontComputed("iTalic sMall-caps bOld 14pX/18eM sAns-serif");
    }

    @Test
    @Alerts({"normal", "normal", "400", "14px", "normal", "sans-serif", "undefined", "100%"})
    public void fontComputedSizeAdjustBefore() {
        fontComputed("14pX sAns-serif", "font-size-adjust: 0.5");
    }

    @Test
    @Alerts({"normal", "normal", "400", "14px", "normal", "sans-serif", "undefined", "100%"})
    public void fontComputedSizeAdjustAfter() {
        fontComputed("14pX sAns-serif; font-size-adjust: 0.5", "");
    }

    @Test
    @Alerts({"normal", "normal", "400", "14px", "normal", "sans-serif", "undefined", "100%"})
    public void fontComputedStretchBefore() {
        fontComputed("14pX sAns-serif", "font-stretch: expanded");
    }

    @Test
    @Alerts({"normal", "normal", "400", "14px", "normal", "sans-serif", "undefined", "125%"})
    public void fontComputedStretchAfter() {
        fontComputed("14pX sAns-serif; font-stretch: expanded", "");
    }

    private void fontComputed(final String fontStyle) {
        final String html =
                "<html>\n"
                        + "</head>\n"
                        + "  <style type='text/css'>div { font: " + fontStyle + " }</style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "  <div id='tester'>hello</div>\n"
                        + "  <script>\n"

                        + "    var myDiv = document.getElementById('tester');\n"
                        + "    var myStyle = window.getComputedStyle(myDiv, null);\n"
                        + "   alert(myStyle.fontStyle);\n"
                        + "   alert(myStyle.fontVariant);\n"
                        + "   alert(myStyle.fontWeight);\n"
                        + "   alert(myStyle.fontSize);\n"
                        + "   alert(myStyle.lineHeight);\n"
                        + "   alert(myStyle.fontFamily);\n"
                        + "  </script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    private void fontComputed(final String fontStyle, final String otherStyle) {
        final String html =
                "<html>\n"
                        + "</head>\n"
                        + "  <style type='text/css'>div { " + otherStyle + "; font: " + fontStyle + " }</style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "  <div id='tester'>hello</div>\n"
                        + "  <script>\n"

                        + "    var myDiv = document.getElementById('tester');\n"
                        + "    var myStyle = window.getComputedStyle(myDiv, null);\n"
                        + "   alert(myStyle.fontStyle);\n"
                        + "   alert(myStyle.fontVariant);\n"
                        + "   alert(myStyle.fontWeight);\n"
                        + "   alert(myStyle.fontSize);\n"
                        + "   alert(myStyle.lineHeight);\n"
                        + "   alert(myStyle.fontFamily);\n"
                        + "   alert(myStyle.fontSizeAdjust);\n"
                        + "   alert(myStyle.fontStretch);\n"
                        + "  </script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"})
    public void borderEmpty() {
        border("");
    }

    @Test
    @Alerts({"thin", "thin", "thin", "thin", "initial", "initial", "initial", "initial",
            "initial", "initial", "initial", "initial"})
    public void borderWidth() {
        border("tHin");
    }

    @Test
    @Alerts({"2px", "2px", "2px", "2px", "initial", "initial", "initial", "initial",
            "initial", "initial", "initial", "initial"})
    public void borderWidthNumber() {
        border("2pX");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "solid", "solid", "solid", "solid",
            "initial", "initial", "initial", "initial"})
    public void borderStyle() {
        border("sOlid");
    }

    @Test
    @Alerts({"thin", "thin", "thin", "thin", "solid", "solid", "solid", "solid",
            "initial", "initial", "initial", "initial"})
    public void borderWidthAndStyle() {
        border("tHin sOlid");
    }

    @Test
    @Alerts({"2px", "2px", "2px", "2px", "solid", "solid", "solid", "solid",
            "initial", "initial", "initial", "initial"})
    public void borderWidthNumberAndStyle() {
        border("2pX sOlid");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "initial", "initial", "initial", "initial",
            "red", "red", "red", "red"})
    public void borderColor() {
        border("rEd");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "initial", "initial", "initial", "initial",
            "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)"})
    public void borderColorHex() {
        border("#fFccdd");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "initial", "initial", "initial", "initial",
            "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)"})
    public void borderColorHexShort() {
        border("#fCd");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "initial", "initial", "initial", "initial",
            "rgb(20, 40, 60)", "rgb(20, 40, 60)", "rgb(20, 40, 60)", "rgb(20, 40, 60)"})
    public void borderColorRgb() {
        border("rGb(20, 40, 60)");
    }

    @Test
    @Alerts({"thin", "thin", "thin", "thin", "solid", "solid", "solid", "solid",
            "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)"})
    public void borderAll() {
        border("tHin sOlid #fFccdd");
    }

    private void border(final String borderStyle) {
        final String html =
                "<html>\n"
                        + "<body>\n"
                        + "  <div id='tester' style='border: " + borderStyle + "' >hello</div>\n"
                        + "  <script>\n"
                        + "    var myStyle = document.getElementById('tester').style;\n"
                        + "   alert(myStyle.borderTopWidth);\n"
                        + "   alert(myStyle.borderRightWidth);\n"
                        + "   alert(myStyle.borderBottomWidth);\n"
                        + "   alert(myStyle.borderLeftWidth);\n"
                        + "   alert(myStyle.borderTopStyle);\n"
                        + "   alert(myStyle.borderRightStyle);\n"
                        + "   alert(myStyle.borderBottomStyle);\n"
                        + "   alert(myStyle.borderLeftStyle);\n"
                        + "   alert(myStyle.borderTopColor);\n"
                        + "   alert(myStyle.borderRightColor);\n"
                        + "   alert(myStyle.borderBottomColor);\n"
                        + "   alert(myStyle.borderLeftColor);\n"
                        + "  </script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"})
    public void borderCssEmpty() {
        borderCss("");
    }

    @Test
    @Alerts({"thin", "thin", "thin", "thin", "initial", "initial", "initial", "initial",
            "initial", "initial", "initial", "initial"})
    public void borderCssWidth() {
        borderCss("tHin");
    }

    @Test
    @Alerts({"2px", "2px", "2px", "2px", "initial", "initial", "initial", "initial",
            "initial", "initial", "initial", "initial"})
    public void borderCssWidthNumber() {
        borderCss("2pX");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "solid", "solid", "solid", "solid",
            "initial", "initial", "initial", "initial"})
    public void borderCssStyle() {
        borderCss("sOlid");
    }

    @Test
    @Alerts({"thin", "thin", "thin", "thin", "solid", "solid", "solid", "solid",
            "initial", "initial", "initial", "initial"})
    public void borderCssWidthAndStyle() {
        borderCss("tHin sOlid");
    }

    @Test
    @Alerts({"2px", "2px", "2px", "2px", "solid", "solid", "solid", "solid",
            "initial", "initial", "initial", "initial"})
    public void borderCssWidthNumberAndStyle() {
        borderCss("2pX sOlid");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "initial", "initial", "initial", "initial",
            "red", "red", "red", "red"})
    public void borderCssColor() {
        borderCss("rEd");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "initial", "initial", "initial", "initial",
            "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)"})
    public void borderCssColorHex() {
        borderCss("#fFccdd");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "initial", "initial", "initial", "initial",
            "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)"})
    public void borderCssColorHexShort() {
        borderCss("#fCd");
    }

    @Test
    @Alerts({"initial", "initial", "initial", "initial", "initial", "initial", "initial", "initial",
            "rgb(20, 40, 60)", "rgb(20, 40, 60)", "rgb(20, 40, 60)", "rgb(20, 40, 60)"})
    public void borderCssColorRgb() {
        borderCss("rGb(20, 40, 60)");
    }

    @Test
    @Alerts({"thin", "thin", "thin", "thin", "solid", "solid", "solid", "solid",
            "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)"})
    public void borderCssAll() {
        borderCss("tHin sOlid #fFccdd");
    }

    private void borderCss(final String borderStyle) {
        final String html =
                "<html>\n"
                        + "</head>\n"
                        + "  <style type='text/css'>div { border: " + borderStyle + " }</style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "  <div id='tester'>hello</div>\n"
                        + "  <script>\n"
                        + "   var myStyle = document.styleSheets[0].cssRules[0].style;\n"
                        + "   alert(myStyle.borderTopWidth);\n"
                        + "   alert(myStyle.borderRightWidth);\n"
                        + "   alert(myStyle.borderBottomWidth);\n"
                        + "   alert(myStyle.borderLeftWidth);\n"
                        + "   alert(myStyle.borderTopStyle);\n"
                        + "   alert(myStyle.borderRightStyle);\n"
                        + "   alert(myStyle.borderBottomStyle);\n"
                        + "   alert(myStyle.borderLeftStyle);\n"
                        + "   alert(myStyle.borderTopColor);\n"
                        + "   alert(myStyle.borderRightColor);\n"
                        + "   alert(myStyle.borderBottomColor);\n"
                        + "   alert(myStyle.borderLeftColor);\n"
                        + "  </script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0px", "0px", "0px", "0px", "initial", "initial", "initial", "initial",
            "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)"})
    public void borderComputedEmpty() {
        borderComputed("");
    }

    @Test
    @Alerts({"0px", "0px", "0px", "0px", "initial", "initial", "initial", "initial",
            "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)"})
    public void borderComputedWidth() {
        borderComputed("tHin");
    }

    @Test
    @Alerts({"0px", "0px", "0px", "0px", "initial", "initial", "initial", "initial",
            "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)"})
    public void borderComputedWidthNumber() {
        borderComputed("2pX");
    }

    @Test
    @Alerts({"3px", "3px", "3px", "3px", "solid", "solid", "solid", "solid",
            "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)"})
    public void borderComputedStyle() {
        borderComputed("sOlid");
    }

    @Test
    @Alerts({"1px", "1px", "1px", "1px", "solid", "solid", "solid", "solid",
            "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)"})
    public void borderComputedWidthAndStyle() {
        borderComputed("tHin sOlid");
    }

    @Test
    @Alerts({"2px", "2px", "2px", "2px", "solid", "solid", "solid", "solid",
            "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)", "rgb(0, 0, 0)"})
    public void borderComputedWidthNumberAndStyle() {
        borderComputed("2pX sOlid");
    }

    @Test
    @Alerts({"0px", "0px", "0px", "0px", "initial", "initial", "initial", "initial",
            "rgb(255, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 0, 0)"})
    public void borderComputedColor() {
        borderComputed("rEd");
    }

    @Test
    @Alerts({"0px", "0px", "0px", "0px", "initial", "initial", "initial", "initial",
            "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)"})
    public void borderComputedColorHex() {
        borderComputed("#fFccdd");
    }

    @Test
    @Alerts({"0px", "0px", "0px", "0px", "initial", "initial", "initial", "initial",
            "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)"})
    public void borderComputedColorHexShort() {
        borderComputed("#fCd");
    }

    @Test
    @Alerts({"0px", "0px", "0px", "0px", "initial", "initial", "initial", "initial",
            "rgb(20, 40, 60)", "rgb(20, 40, 60)", "rgb(20, 40, 60)", "rgb(20, 40, 60)"})
    public void borderComputedColorRgb() {
        borderComputed("rGb(20, 40, 60)");
    }

    @Test
    @Alerts({"1px", "1px", "1px", "1px", "solid", "solid", "solid", "solid",
            "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)", "rgb(255, 204, 221)"})
    public void borderComputedAll() {
        borderComputed("tHin sOlid #fFccdd");
    }

    private void borderComputed(final String borderStyle) {
        final String html =
                "<html>\n"
                        + "</head>\n"
                        + "  <style type='text/css'>div { border: " + borderStyle + " }</style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "  <div id='tester'>hello</div>\n"
                        + "  <script>\n"
                        + "   var myDiv = document.getElementById('tester');\n"
                        + "   var myStyle = window.getComputedStyle(myDiv, null);\n"
                        + "   alert(myStyle.borderTopWidth);\n"
                        + "   alert(myStyle.borderRightWidth);\n"
                        + "   alert(myStyle.borderBottomWidth);\n"
                        + "   alert(myStyle.borderLeftWidth);\n"
                        + "   alert(myStyle.borderTopStyle);\n"
                        + "   alert(myStyle.borderRightStyle);\n"
                        + "   alert(myStyle.borderBottomStyle);\n"
                        + "   alert(myStyle.borderLeftStyle);\n"
                        + "   alert(myStyle.borderTopColor);\n"
                        + "   alert(myStyle.borderRightColor);\n"
                        + "   alert(myStyle.borderBottomColor);\n"
                        + "   alert(myStyle.borderLeftColor);\n"
                        + "  </script>\n"
                        + "</body></html>";

       checkHtmlAlert(html);
    }
}
