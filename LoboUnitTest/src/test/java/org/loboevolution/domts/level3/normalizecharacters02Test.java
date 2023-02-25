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
 * Normalize document with normalize-characters set to true, check that
 * characters are normalized.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-normalize-characters">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-normalize-characters</a>
 * @see <a href="http://www.w3.org/TR/2003/WD-charmod-20030822/">http://www.w3.org/TR/2003/WD-charmod-20030822/</a>
 */
public class normalizecharacters02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        DOMConfiguration domConfig;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        HTMLCollection pList;
        Element pElem;
        Text text;
        String textValue;
        Node retval;
        boolean canSet;
        doc = sampleXmlFile("barfoo.xml");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("normalize-characters", Boolean.TRUE);

        if (canSet) {
            domConfig.setParameter("normalize-characters", Boolean.TRUE);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            pList = doc.getElementsByTagName("p");
            pElem = (Element) pList.item(0);
            text = doc.createTextNode("suçon");
            retval = pElem.appendChild(text);
            doc.normalizeDocument();
            assertTrue("normalizeError", errorMonitor.assertLowerSeverity(2));
            pList = doc.getElementsByTagName("p");
            pElem = (Element) pList.item(0);
            text = (Text) pElem.getFirstChild();
            textValue = text.getNodeValue();
            assertEquals("charNormalized", "barsuçon", textValue);
        }
    }
}

