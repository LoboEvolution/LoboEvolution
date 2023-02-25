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
 * Normalize document with element-content-whitespace set to true and validation set to true, check that
 * whitespace in element content is preserved.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-element-content-whitespace">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-element-content-whitespace</a>
 */
public class elementcontentwhitespace01Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        HTMLCollection bodyList;
        Element body;
        DOMConfiguration domConfig;
        boolean canSet;
        boolean canSetValidate;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        Node child;
        String childName;
        Text text;
        doc = sampleXmlFile("barfoo.xml");
        domConfig = doc.getDomConfig();
        domConfig.setParameter("element-content-whitespace", Boolean.TRUE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);


        doc.normalizeDocument();
        assertTrue("normalizeError", errorMonitor.assertLowerSeverity(2));
        bodyList = doc.getElementsByTagName("body");
        body = (Element) bodyList.item(0);
        child = body.getFirstChild();
        assertNotNull("firstChildNotNull", child);
        childName = child.getNodeName();
        assertEquals("firstChild", "#text", childName);
        child = child.getNextSibling();
        assertNotNull("secondChildNotNull", child);
        childName = child.getNodeName();
        assertEquals("secondChild", "p", childName);
    }
}

