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
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Normalize a document using Node.normalize and check that
 * the value of the 'cdata-sections' parameter is ignored.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-normalize">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-normalize</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-cdata-sections">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-cdata-sections</a>
 */
public class cdatasections01Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        Element elem;
        CDATASection newCdata;
        CDATASection cdata;
        String nodeName;
        DOMConfiguration domConfig;
        HTMLCollection pList;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        doc = sampleXmlFile("barfoo.xml");
        pList = doc.getElementsByTagName("p");
        elem = (Element) pList.item(0);
        newCdata = doc.createCDATASection("CDATA");
        elem.appendChild(newCdata);
        domConfig = doc.getDomConfig();
        domConfig.setParameter("cdata-sections", Boolean.FALSE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        doc.normalize();
        assertTrue("normalizationError", errorMonitor.assertLowerSeverity(2));
        pList = doc.getElementsByTagName("p");
        elem = (Element) pList.item(0);
        cdata = (CDATASection) elem.getLastChild();
        nodeName = cdata.getNodeName();
        assertEquals("documentnormalizedocument03_true", "#cdata-section", nodeName);
    }
}

