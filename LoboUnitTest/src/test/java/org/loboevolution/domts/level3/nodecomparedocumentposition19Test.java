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
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * The method compareDocumentPosition compares a node with this node with regard to their position in the
 * document and according to the document order.
 * <p>
 * Using compareDocumentPosition check if the document position of the first CDATASection node
 * of the second element whose localName is name compared with the second CDATASection node
 * is PRECEDING and is FOLLOWING vice versa.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition19Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element elemStrong;
        CDATASection cdata1;
        CDATASection cdata2;
        Node aNode;
        int cdata1Position;
        int cdata2Position;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagNameNS("*", "strong");
        elemStrong = (Element) elemList.item(1);
        cdata2 = (CDATASection) elemStrong.getLastChild();
        aNode = cdata2.getPreviousSibling();
        cdata1 = (CDATASection) aNode.getPreviousSibling();
        cdata1Position = cdata1.compareDocumentPosition(cdata2);
        assertEquals("nodecomparedocumentposition19_cdata2Follows", 4, cdata1Position);
        cdata2Position = cdata2.compareDocumentPosition(cdata1);
        assertEquals("nodecomparedocumentposition_cdata1Precedes", 2, cdata2Position);
    }
}

