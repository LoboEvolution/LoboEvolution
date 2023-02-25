/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.nodeimpl.DOMErrorMonitor;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Normalize document with infoset set to true,
 * check if string values were not normalized.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-infoset">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-infoset</a>
 */
public class infoset03Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element element;
        DOMConfiguration domConfig;
        boolean canSetValidate;
        boolean canSetXMLSchema;
        String xsdNS = "http://www.w3.org/2001/XMLSchema";
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        Node childNode;
        String childValue;
        int childLength;
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
            assertTrue("normalizeError", errorMonitor.assertLowerSeverity(2));
            elemList = doc.getElementsByTagNameNS("http://www.w3.org/1999/xhtml", "code");
            element = (Element) elemList.item(0);
            childNode = element.getFirstChild();
            childValue = childNode.getNodeValue();
            childLength = childValue.length();
            assertEquals("content1", 18, childLength);
            element = (Element) elemList.item(1);
            childNode = element.getFirstChild();
            childValue = childNode.getNodeValue();
            assertEquals("content2", "EMP  0001", childValue);
            element = (Element) elemList.item(2);
            childNode = element.getFirstChild();
            childValue = childNode.getNodeValue();
            assertEquals("content3", "EMP 0001", childValue);
        }
    }
}