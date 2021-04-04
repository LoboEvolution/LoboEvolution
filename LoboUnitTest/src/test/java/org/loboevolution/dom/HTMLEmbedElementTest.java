/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.dom;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;


/**
 * Tests for {@link org.loboevolution.html.dom.HTMLEmbedElement}.
 */
public class HTMLEmbedElementTest extends LoboUnitTest {

    /**
     * <p>getAlign.</p>
     */
    @Test
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <embed id='e1' align='left' ></embed>\n"
                + "  <embed id='e2' align='right' ></embed>\n"
                + "  <embed id='e3' align='bottom' ></embed>\n"
                + "  <embed id='e4' align='middle' ></embed>\n"
                + "  <embed id='e5' align='top' ></embed>\n"
                + "  <embed id='e6' align='absbottom' ></embed>\n"
                + "  <embed id='e7' align='absmiddle' ></embed>\n"
                + "  <embed id='e8' align='baseline' ></embed>\n"
                + "  <embed id='e9' align='texttop' ></embed>\n"
                + "  <embed id='e10' align='wrong' ></embed>\n"
                + "  <embed id='e11' ></embed>\n"

                + "<script>\n"
                + "  for (var i = 1; i <= 11; i++) {\n"
                + "    alert(document.getElementById('e' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"left", "right", "bottom", "middle", "top",
                "absbottom", "absmiddle", "baseline", "texttop", "wrong", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAlign.</p>
     */
    @Test
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <embed id='e1' align='left' ></embed>\n"

                + "<script>\n"
                + "  function setAlign(elem, value) {\n"
                + "    try {\n"
                + "      elem.align = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.align);\n"
                + "  }\n"

                + "  var elem = document.getElementById('e1');\n"
                + "  setAlign(elem, 'CenTer');\n"

                + "  setAlign(elem, '8');\n"
                + "  setAlign(elem, 'foo');\n"

                + "  setAlign(elem, 'left');\n"
                + "  setAlign(elem, 'right');\n"
                + "  setAlign(elem, 'bottom');\n"
                + "  setAlign(elem, 'middle');\n"
                + "  setAlign(elem, 'top');\n"
                + "  setAlign(elem, 'absbottom');\n"
                + "  setAlign(elem, 'absmiddle');\n"
                + "  setAlign(elem, 'baseline');\n"
                + "  setAlign(elem, 'texttop');\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"CenTer", "8", "foo", "left", "right", "bottom", "middle", "top",
                "absbottom", "absmiddle", "baseline", "texttop"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getHeight.</p>
     */
    @Test
    public void getHeight() {
        final String html
                = "<html><body>\n"
                + "  <embed id='e1' height='10px' ></embed>\n"
                + "  <embed id='e2' height='20em' ></embed>\n"
                + "  <embed id='e3' height='80%' ></embed>\n"
                + "  <embed id='e4' height='40' ></embed>\n"
                + "  <embed id='e5' height='wrong' ></embed>\n"
                + "  <embed id='e6' ></embed>\n"

                + "<script>\n"
                + "  for (var i = 1; i <= 6; i++) {\n"
                + "    alert(document.getElementById('e' + i).height);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages =  {"10px", "20em", "80%", "40", "wrong", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setHeight.</p>
     */
    @Test
    public void setHeight() {
        final String html
                = "<html><body>\n"
                + "  <embed id='e1' height='10px' ></embed>\n"

                + "<script>\n"
                + "  function setHeight(elem, value) {\n"
                + "    try {\n"
                + "      elem.height = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.height);\n"
                + "  }\n"

                + "  var elem = document.getElementById('e1');\n"
                + "  setHeight(elem, '20px');\n"

                + "  setHeight(elem, '8');\n"
                + "  setHeight(elem, 'foo');\n"

                + "</script>\n"
                + "</body></html>";
        final String[] messages =  {"20px", "8", "foo"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getWidth.</p>
     */
    @Test
    public void getWidth() {
        final String html
                = "<html><body>\n"
                + "  <embed id='e1' width='10px' ></embed>\n"
                + "  <embed id='e2' width='20em' ></embed>\n"
                + "  <embed id='e3' width='80%' ></embed>\n"
                + "  <embed id='e4' width='40' ></embed>\n"
                + "  <embed id='e5' width='wrong' ></embed>\n"
                + "  <embed id='e6' ></embed>\n"

                + "<script>\n"
                + "  for (var i = 1; i <= 6; i++) {\n"
                + "    alert(document.getElementById('e' + i).width);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"10px", "20em", "80%", "40", "wrong", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setWidth.</p>
     */
    @Test
    public void setWidth() {
        final String html
                = "<html><body>\n"
                + "  <embed id='e1' width='10px' ></embed>\n"

                + "<script>\n"
                + "  function setWidth(elem, value) {\n"
                + "    try {\n"
                + "      elem.width = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.width);\n"
                + "  }\n"

                + "  var elem = document.getElementById('e1');\n"
                + "  setWidth(elem, '20px');\n"

                + "  setWidth(elem, '8');\n"
                + "  setWidth(elem, 'foo');\n"

                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"20px", "8", "foo"};
        checkHtmlAlert(html, messages);
    }
}
