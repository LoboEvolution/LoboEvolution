package org.loboevolution.wpt.borders;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

@ExtendWith(AlertsExtension.class)
public class Css2BordersWptPortTest extends LoboUnitTest {

    /**
     * The border shorthand property properly accepts and sets border-width.
     * Test passes if there is a hollow black square
     * @author <a href='href='http://www.microsoft.com/'>Microsoft<a/>
     * @author Gérard Talbot See <a href='http://www.gtalbot.org/BrowserBugsSection/css21testsuite/'>css21testsuite<a/>
     * @see <a href='http://www.w3.org/TR/CSS21/box.html#propdef-border'>propdef-border<a/>
     * @see <a href='http://www.w3.org/TR/CSS21/box.html#border-shorthand-properties'>border-shorthand-properties<a/>
     * @see <a href='http://www.w3.org/TR/css3-background/#borders'>borders<a/>
     * @see <a href='http://www.w3.org/TR/css3-background/#the-border-shorthands'>the-border-shorthands<a/>
     * @see <a href='https://github.com/web-platform-tests/wpt/blob/b953dd19a65e5cf42efd6c375b60593be7565c22/css/CSS2/borders/border-001-ref.xht'>border-001-ref.xht<a/>
     */
    @Test
    @Alerts({"25px","normal", "Ahem", "rgb(0, 0, 0)", "25px", "150px"})
    public void border001() {
        final String html =
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>"
                + "<html xmlns='http://www.w3.org/1999/xhtml'>"
                + " <head>"
                + "  <title>CSS Reftest Reference</title>"
                + "  <link rel='author' title='Gérard Talbot' href='http://www.gtalbot.org/BrowserBugsSection/css21testsuite/'/>"
                + "  <style type='text/css'><![CDATA["
                + "  div{"
                + "  font: 25px/1 Ahem;"
                + "  width: 6em;"
                + "  word-spacing: 3em;"
                + "  }"
                + "  div.top-and-bottom-stripes{"
                + "  background-color: black;"
                + "  height: 1em;"
                + "  }"
                + "  ]]></style>"
                + " </head>"
                + " <body>"
                + "  <p>Test passes if there is a hollow black square.</p>"
                + "  <div id='div' class='top-and-bottom-stripes'></div>"
                + "  <div>1 2 3 4 5 6 7 8</div>"
                + "  <div class='top-and-bottom-stripes'></div>"
                + " <script>"
                + "  var el = document.getElementById('div');"
                + "  var cs = getComputedStyle(el);"
                + "  alert(cs.fontSize);"
                + "  alert(cs.fontStyle);"
                + "  alert(cs.fontFamily);"
                + "  alert(cs.backgroundColor);"
                + "  alert(cs.height);"
                + "  alert(cs.width);"
                + "  </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    /**
     * The border shorthand property properly accepts and sets border-width.
     * Test passes if there is a hollow black square
     * @author <a href='href='http://www.microsoft.com/'>Microsoft<a/>
     * @author Gérard Talbot See <a href='http://www.gtalbot.org/BrowserBugsSection/css21testsuite/'>css21testsuite<a/>
     * @see <a href='http://www.w3.org/TR/CSS21/box.html#propdef-border'>propdef-border<a/>
     * @see <a href='http://www.w3.org/TR/CSS21/box.html#border-shorthand-properties'>border-shorthand-properties<a/>
     * @see <a href='http://www.w3.org/TR/css3-background/#borders'>borders<a/>
     * @see <a href='http://www.w3.org/TR/css3-background/#the-border-shorthands'>the-border-shorthands<a/>
     * @see <a href='https://github.com/web-platform-tests/wpt/blob/master/css/css-backgrounds/border-radius-001.xht>border-radius-001.xht<a/>
     */
    @Test
    @Alerts({"25px", "solid", "rgb(0, 0, 0)"})
    public void borderRadius001() {
        final String html =
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>"
                + "<html xmlns='http://www.w3.org/1999/xhtml'>"
                + "    <head>"
                + "        <title>CSS Test: Border set using border-width</title>"
                + "        <meta name='assert' content=''/>"
                + "        <style type='text/css'>"
                + "            div"
                + "            {"
                + "                border: 25px;"
                + "                border-style: solid;"
                + "                height: 100px;"
                + "                width: 100px;"
                + "            }"
                + "        </style>"
                + "    </head>"
                + "    <body>"
                + "        <div id='div'></div>"
                + "    <script>"
                + "  var el = document.getElementById('div');"
                + "  var cs = getComputedStyle(el);"
                + "  alert(cs.borderTopWidth);"
                + "  alert(cs.borderTopStyle);"
                + "  alert(cs.borderTopColor);"
                + "</script>"
                + "</body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    /**
     * The border shorthand property properly accepts and sets border-style.
     * Test passes if there is a box with a dashed border below.
     * @author <a href="http://www.microsoft.com/">Microsoft</a>
     * @see <a href="http://www.w3.org/TR/CSS21/box.html#propdef-border">propdef-border</a>
     * @see <a href="http://www.w3.org/TR/CSS21/box.html#border-shorthand-properties">border-shorthand-properties</a>
     */
    @Test
    @Alerts({"5px", "dashed", "rgb(0, 0, 0)"})
    public void border002() {
        final String html =
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
                + "<html xmlns='http://www.w3.org/1999/xhtml'>"
                + "    <head>"
                + "        <title>CSS Test: Border set using border-style</title>"
                + "        <meta name='assert' content='The border shorthand property properly accepts and sets border-style.'/>"
                + "        <style type='text/css'>"
                + "            div {"
                + "                border: dashed;"
                + "                border-width: 5px;"
                + "                height: 1in;"
                + "                width: 1in;"
                + "            }"
                + "        </style>"
                + "    </head>"
                + "    <body>"
                + "        <div id='div'></div>"
                + "    <script>"
                + "      var el = document.getElementById('div');"
                + "      var cs = getComputedStyle(el);"
                + "      alert(cs.borderTopWidth);"
                + "      alert(cs.borderTopStyle);"
                + "      alert(cs.borderTopColor);"
                + "    </script>"
                + "    </body>"
                + "</html>";
        checkHtmlAlert(html);
    }


    /**
     * Test passes if text should have a green border.
     * @author <a href='https://dbaron.org/'>L. David Baron<a/>
     * @author Gérard Talbot See <a href='http://www.gtalbot.org/BrowserBugsSection/css21testsuite/'>css21testsuite<a/>
     * @see <a href='http://www.w3.org/TR/CSS21/about.html#shorthand'>shorthand<a/>
     * @see <a href='https://github.com/web-platform-tests/wpt/blob/master/css/CSS2/borders/shand-border-000.xht>shand-border-000-ref.xht<a/>
     */
    @Test
    @Alerts({"rgb(0, 128, 0)"})
    public void wpt_borders_shand_border000() {
        final String html =
                "<!DOCTYPE html PUBLIC -//W3C//DTD XHTML 1.1//EN http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd>"
                + "<html xmlns='http://www.w3.org/1999/xhtml'>"
                + " <head>"
                + "  <title>CSS Test: Shorthand Properties (border)</title>"
                + "  <style type='text/css'>"
                + "  body { background: white; }"
                + "  span { color: black; }"
                + "  p { color: red; }"
                + "  p { border-color: red; }"
                + "  p { border: medium solid; }"
                + "  p { color: green; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + " <p id='test'><span>This text should have a green border.</span></p>"
                + " <script>"
                + "  var el = document.getElementById('test');"
                + "  var cs = getComputedStyle(el);"
                + "  alert(cs.borderColor);"
                + "</script>"
                + "</body>"
                + "</html>";
        checkHtmlAlert(html);
    }
}
