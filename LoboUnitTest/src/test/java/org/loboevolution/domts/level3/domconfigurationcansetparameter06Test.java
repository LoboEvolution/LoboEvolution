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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertTrue;


/**
 * The canSetParameter method checks if setting a parameter to a specific value is supported.
 * <p>
 * The parameter element-content-whitespace is turned on by default. Set this parameter to false will
 * discard all Text nodes that contain whitespaces in element content, as described in [element content whitespace].
 * Check to see if this feature can be set to false by invoking canSetParameter method.  Verify that the text node
 * still exist after invoking canSetParameter.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration</a>
 */
public class domconfigurationcansetparameter06Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMConfiguration domConfig;
        HTMLCollection itemList;
        Element elementStrong;
        Text textNode;
        boolean canSet;
        boolean hasWhitespace;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("element-content-whitespace", Boolean.TRUE);
        assertTrue("domconfigurationcansetparameter06_1", canSet);
        itemList = doc.getElementsByTagNameNS("*", "strong");
        elementStrong = (Element) itemList.item(0);
        textNode = (Text) elementStrong.getFirstChild();
        textNode.setTextContent("                                                ");
        hasWhitespace = textNode.isElementContentWhitespace();
        assertTrue("domconfigurationsetparameter06_2", hasWhitespace);
        doc.normalizeDocument();
        itemList = doc.getElementsByTagNameNS("*", "strong");
        elementStrong = (Element) itemList.item(0);
        textNode = (Text) elementStrong.getFirstChild();
        hasWhitespace = textNode.isElementContentWhitespace();
        assertTrue("domconfigurationsetparameter06_3", hasWhitespace);
    }
}

