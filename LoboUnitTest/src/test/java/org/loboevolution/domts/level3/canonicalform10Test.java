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

import static org.junit.Assert.*;


/**
 * Check elimination of unnecessary namespace prefixes when
 * normalized with canonical-form = true.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form</a>
 */
public class canonicalform10Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        HTMLCollection divList;
        Element div;
        DOMConfiguration domConfig;
        boolean canSet;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        Node node;
        doc = sampleXmlFile("canonicalform03.xml");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("canonical-form", Boolean.TRUE);

        if (canSet) {
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            domConfig.setParameter("canonical-form", Boolean.TRUE);
            doc.normalizeDocument();
            assertTrue("normalizeError", errorMonitor.assertLowerSeverity(2));
            divList = doc.getElementsByTagName("div");
            div = (Element) divList.item(5);
            node = div.getAttributeNode("xmlns");
            assertNotNull("xmlnsPresent", node);
            node = div.getAttributeNode("xmlns:a");
            assertNull("xmlnsANotPresent", node);
        }
    }
}

