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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Normalize a document with a created CDATA section with the
 * 'cdata-sections' parameter set to true then to false and check if
 * the CDATASection has been preserved and then coalesced.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=416">http://www.w3.org/Bugs/Public/show_bug.cgi?id=416</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-cdata-sections">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-cdata-sections</a>
 */
public class documentnormalizedocument03Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        Element elem;
        CDATASection newCdata;
        CDATASection cdata;
        Node text;
        String nodeName;
        String nodeValue;
        DOMConfiguration domConfig;
        HTMLCollection pList;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        doc = sampleXmlFile("barfoo.xml");
        pList = doc.getElementsByTagName("p");
        elem = (Element) pList.item(0);
        newCdata = doc.createCDATASection("CDATA");
        elem.appendChild(newCdata);
        domConfig = doc.getDomConfig();
        domConfig.setParameter("cdata-sections", Boolean.TRUE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        doc.normalizeDocument();
        assertTrue("normalizationError", errorMonitor.assertLowerSeverity(2));
        pList = doc.getElementsByTagName("p");
        elem = (Element) pList.item(0);
        cdata = (CDATASection) elem.getLastChild();
        nodeName = cdata.getNodeName();
        assertEquals("documentnormalizedocument03_true", "#cdata-section", nodeName);
        domConfig.setParameter("cdata-sections", Boolean.FALSE);
        doc.normalizeDocument();
        assertTrue("normalization2Error", errorMonitor.assertLowerSeverity(2));
        pList = doc.getElementsByTagName("p");
        elem = (Element) pList.item(0);
        text = elem.getLastChild();
        nodeName = text.getNodeName();
        assertEquals("documentnormalizedocument03_false", "#text", nodeName);
        nodeValue = text.getNodeValue();
        assertEquals("normalizedValue", "barCDATA", nodeValue);
    }
}