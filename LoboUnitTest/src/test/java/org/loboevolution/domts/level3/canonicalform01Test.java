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

import static org.junit.Assert.*;


/**
 * Normalize document with 'canonical-form' set to true, check that
 * entity references are expanded and unused entity declaration are maintained.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form</a>
 */
public class canonicalform01Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        HTMLCollection pList;
        Element pElem;
        DOMConfiguration domConfig;
        boolean canSet;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        Node child;
        String childName;
        EntityReference entRef;
        String childValue;
        doc = sampleXmlFile("barfoo.xml");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("canonical-form", Boolean.TRUE);

        if (canSet) {
            domConfig.setParameter("canonical-form", Boolean.TRUE);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            pList = doc.getElementsByTagName("p");
            pElem = (Element) pList.item(0);
            entRef = doc.createEntityReference("ent1");
            pElem.appendChild(entRef);
            doc.normalizeDocument();
            assertTrue("normalizeError", errorMonitor.assertLowerSeverity(2));
            pList = doc.getElementsByTagName("p");
            pElem = (Element) pList.item(0);
            child = pElem.getLastChild();
            assertNotNull("lastChildNotNull", child);
            childName = child.getNodeName();
            assertEquals("firstChildName", "#text", childName);
            childValue = child.getNodeValue();
            assertEquals("firstChildValue", "barfoo", childValue);
        }
    }
}

