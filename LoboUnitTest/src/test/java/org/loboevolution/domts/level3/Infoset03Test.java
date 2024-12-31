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
import org.loboevolution.html.dom.domimpl.DOMErrorMonitor;
import org.loboevolution.html.dom.DOMConfiguration;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Normalize document with infoset set to true,
 * check if string values were not normalized.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-infoset">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-infoset</a>
 */
public class Infoset03Test extends LoboUnitTest {


    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elemList;
        Element element;
        final DOMConfiguration domConfig;
        final boolean canSetValidate;
        final boolean canSetXMLSchema;
        final String xsdNS = "http://www.w3.org/2001/XMLSchema";
        final DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        Node childNode;
        String childValue;
        final int childLength;
        doc = sampleXmlFile("datatype_normalization2.xml");
        domConfig = doc.getDomConfig();
        canSetValidate = domConfig.canSetParameter("validate", Boolean.TRUE);
        /*DOMString */
        canSetXMLSchema = domConfig.canSetParameter("schema-type", xsdNS);

        if (canSetValidate & canSetXMLSchema) {
            domConfig.setParameter("infoset", Boolean.TRUE);
            domConfig.setParameter("validate", Boolean.TRUE);
            /*DOMString */
            domConfig.setParameter("schema-type", xsdNS);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            doc.normalizeDocument();
            assertTrue(errorMonitor.assertLowerSeverity(2), "Infoset03Assert3");
            elemList = doc.getElementsByTagNameNS("http://www.w3.org/1999/xhtml", "code");
            element = (Element) elemList.item(0);
            childNode = element.getFirstChild();
            childValue = childNode.getNodeValue();
            childLength = childValue.length();
            assertEquals(18, childLength, "Infoset03Assert4");
            element = (Element) elemList.item(1);
            childNode = element.getFirstChild();
            childValue = childNode.getNodeValue();
            assertEquals("EMP  0001", childValue, "Infoset03Assert5");
            element = (Element) elemList.item(2);
            childNode = element.getFirstChild();
            childValue = childNode.getNodeValue();
            assertEquals("EMP 0001", childValue, "Infoset03Assert6");
        }
    }
}
