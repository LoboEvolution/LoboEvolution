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
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Adopt the class attribute node of the fourth acronym element.  Check if this attribute has been adopted successfully by verifying the
 * nodeName, nodeType, nodeValue, specified and ownerElement attributes of the adopted node.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class Documentadoptnode01Test extends LoboUnitTest {


    @Test
    public void runTest() {
        final Document doc;
        final Element attrOwnerElem;
        final Element element;
        final Attr attr;
        final HTMLCollection childList;
        final Node adoptedclass;
        final String nodeName;
        final int nodeType;
        final String nodeValue;
        final Text firstChild;
        final String firstChildValue;
        final EntityReference secondChild;
        final int secondChildType;
        final String secondChildName;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("acronym");
        element = (Element) childList.item(3);
        attr = element.getAttributeNode("class");
        adoptedclass = doc.adoptNode(attr);

        if ((adoptedclass != null)) {
            nodeName = adoptedclass.getNodeName();
            nodeValue = adoptedclass.getNodeValue();
            nodeType = adoptedclass.getNodeType();
            attrOwnerElem = (Element) ((Attr) adoptedclass).getOwnerElement();
            assertEquals("class", nodeName, "Documentadoptnode01Assert1");
            assertEquals(2, nodeType, "Documentadoptnode01Assert2");
            assertNotNull(attrOwnerElem, "Documentadoptnode01Assert3");
            firstChild = (Text) adoptedclass.getFirstChild();
            assertNotNull(firstChild, "Documentadoptnode01Assert4");
            firstChildValue = firstChild.getNodeValue();

            if ("Y".equals(firstChildValue)) {
                secondChild = (EntityReference) firstChild.getNextSibling();
                assertNotNull(secondChild, "Documentadoptnode01Assert5");
                secondChildType = secondChild.getNodeType();
                assertEquals(5, secondChildType, "Documentadoptnode01Assert6");
                secondChildName = secondChild.getNodeName();
                assertEquals("alpha", secondChildName, "Documentadoptnode01Assert7");
            } else {
                assertEquals("Yα", nodeValue, "Documentadoptnode01Assert8");
            }

        }
    }
}

