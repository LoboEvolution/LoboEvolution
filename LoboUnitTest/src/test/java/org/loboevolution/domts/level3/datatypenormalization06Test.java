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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Normalize document with datatype-normalization set to true.
 * Check if time values were normalized.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-datatype-normalization">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-datatype-normalization</a>
 */
public class datatypenormalization06Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element element;
        DOMConfiguration domConfig;
        String str;
        boolean canSetNormalization;
        boolean canSetValidate;
        boolean canSetXMLSchema;
        String xsdNS = "http://www.w3.org/2001/XMLSchema";
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        doc = sampleXmlFile("datatype_normalization.xml");
        domConfig = doc.getDomConfig();
        canSetNormalization = domConfig.canSetParameter("datatype-normalization", Boolean.TRUE);
        canSetValidate = domConfig.canSetParameter("validate", Boolean.TRUE);
        /*DOMString */
        canSetXMLSchema = domConfig.canSetParameter("schema-type", xsdNS);

        if (canSetNormalization & canSetValidate & canSetXMLSchema) {
            domConfig.setParameter("datatype-normalization", Boolean.TRUE);
            domConfig.setParameter("validate", Boolean.TRUE);
            /*DOMString */
            domConfig.setParameter("schema-type", xsdNS);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            doc.normalizeDocument();
            assertTrue("normalizeError", errorMonitor.assertLowerSeverity(2));
            elemList = doc.getElementsByTagNameNS("http://www.w3.org/2001/DOM-Test-Suite/Level-3/datatype_normalization", "time");
            element = (Element) elemList.item(0);
            str = element.getAttribute("value");
            assertEquals("firstValue", "15:30:00-05:00", str);
            str = element.getAttribute("union");
            assertEquals("firstUnion", "15:30:00-05:00", str);
            str = element.getTextContent();
            assertEquals("firstList", "15:30:00", str);
            element = (Element) elemList.item(1);
            str = element.getAttribute("value");
            assertEquals("secondValue", "15:30:00.0000-05:00", str);
            str = element.getAttribute("union");
            assertEquals("secondUnion", "15:30:00.0000-05:00", str);
            str = element.getTextContent();
            assertEquals("secondList", "15:30:00.0000", str);
            element = (Element) elemList.item(2);
            str = element.getAttribute("value");
            assertEquals("thirdValue", "15:30:00.0001-05:00", str);
            str = element.getAttribute("union");
            assertEquals("thirdUnion", "15:30:00.0001-05:00", str);
            str = element.getTextContent();
            assertEquals("thirdList", "15:30:00.0001", str);
        }
    }
}

