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
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.*;


/**
 * Normalize document based on section 3.1 with canonical-form set to true and check normalized document.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form</a>
 */
public class canonicalform08Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        DOMConfiguration domConfig;
        boolean canSet;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        Node node;
        String nodeValue;
        int nodeType;
        int length;
        doc = sampleXmlFile("canonicalform01.xml");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("canonical-form", Boolean.TRUE);

        if (canSet) {
            domConfig.setParameter("canonical-form", Boolean.TRUE);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            doc.normalizeDocument();
            assertTrue("normalizeError", errorMonitor.assertLowerSeverity(2));
            node = doc.getFirstChild();
            nodeType = node.getNodeType();
            assertEquals("PIisFirstChild", 7, nodeType);
            nodeValue = ((ProcessingInstruction) /*Node */node).getData();
            length = nodeValue.length();
            assertEquals("piDataLength", 36, length);
            node = node.getNextSibling();
            nodeType = node.getNodeType();
            assertEquals("TextisSecondChild", 3, nodeType);
            nodeValue = node.getNodeValue();
            length = nodeValue.length();
            assertEquals("secondChildLength", 1, length);
            node = node.getNextSibling();
            nodeType = node.getNodeType();
            assertEquals("ElementisThirdChild", 1, nodeType);
            node = node.getNextSibling();
            nodeType = node.getNodeType();
            assertEquals("TextisFourthChild", 3, nodeType);
            nodeValue = node.getNodeValue();
            length = nodeValue.length();
            assertEquals("fourthChildLength", 1, length);
            node = node.getNextSibling();
            nodeType = node.getNodeType();
            assertEquals("PIisFifthChild", 7, nodeType);
            nodeValue = ((ProcessingInstruction) /*Node */node).getData();
            assertEquals("trailingPIData", "", nodeValue);
            node = node.getNextSibling();
            nodeType = node.getNodeType();
            assertEquals("TextisSixthChild", 3, nodeType);
            nodeValue = node.getNodeValue();
            length = nodeValue.length();
            assertEquals("sixthChildLength", 1, length);
            node = node.getNextSibling();
            nodeType = node.getNodeType();
            assertEquals("CommentisSeventhChild", 8, nodeType);
            node = node.getNextSibling();
            nodeType = node.getNodeType();
            assertEquals("TextisEighthChild", 3, nodeType);
            nodeValue = node.getNodeValue();
            length = nodeValue.length();
            assertEquals("eighthChildLength", 1, length);
            node = node.getNextSibling();
            nodeType = node.getNodeType();
            assertEquals("CommentisNinthChild", 8, nodeType);
            node = node.getNextSibling();
            assertNull("TenthIsNull", node);
        }
    }
}

