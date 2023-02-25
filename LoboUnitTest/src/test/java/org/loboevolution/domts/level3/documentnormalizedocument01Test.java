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
import org.loboevolution.html.dom.nodeimpl.DOMErrorMonitor;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Invoke the normalizeDocument method on this document.  Retreive the documentElement node
 * and check the nodeName of this node to make sure it has not changed.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 */
public class documentnormalizedocument01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        String docElemNodeName;
        String origDocElemNodeName;
        DOMConfiguration domConfig;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        origDocElemNodeName = docElem.getNodeName();
        domConfig = doc.getDomConfig();
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        doc.normalizeDocument();
        assertTrue("normalizeError", errorMonitor.assertLowerSeverity(2));
        docElem = doc.getDocumentElement();
        docElemNodeName = docElem.getNodeName();
        assertEquals("documentnormalizedocument01", origDocElemNodeName, docElemNodeName);
    }
}

