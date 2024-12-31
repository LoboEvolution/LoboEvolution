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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.DOMConfiguration;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The canSetParameter method checks if setting a parameter to a specific value is supported.
 * <p>
 * The parameter cdata-section is turned on by default.  Check to see if this feature can be set
 * to false by invoking canSetParameter method.  Also check that this method does not change the
 * value of parameter by checking if the two cdata-section nodes still exist in the document.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration</a>
 */
public class Domconfigurationcansetparameter02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DOMConfiguration domConfig;
        final HTMLCollection strongList;
        final NodeList childList;
        final Node strongElem;
        final Node cdata1;
        final Node cdata2;
        int nodeType;
        final boolean canSet;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("cdata-sections", Boolean.FALSE);
        assertTrue(canSet, "Domconfigurationcansetparameter02Assert3");
        doc.normalizeDocument();
        strongList = doc.getElementsByTagNameNS("*", "strong");
        strongElem = strongList.item(1);
        childList = strongElem.getChildNodes();
        cdata1 = childList.item(1);
        nodeType = cdata1.getNodeType();
        assertEquals(4, nodeType, "Domconfigurationcansetparameter02Assert4");
        cdata2 = childList.item(3);
        nodeType = cdata2.getNodeType();
        assertEquals(4, nodeType, "Domconfigurationcansetparameter02Assert5");
    }
}

