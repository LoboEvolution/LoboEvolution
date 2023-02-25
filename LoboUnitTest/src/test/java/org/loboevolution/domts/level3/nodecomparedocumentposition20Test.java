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
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertEquals;


/**
 * Using compareDocumentPosition check if the document position of the first Text node
 * of the second element whose localName is name compared with the next CDATASection node
 * is PRECEDING and FOLLOWING vice versa.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition20Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element elemName;
        CDATASection cdata;
        Text txt;
        int txtPosition;
        int cdataPosition;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("strong");
        elemName = (Element) elemList.item(1);
        txt = (Text) elemName.getFirstChild();
        cdata = (CDATASection) elemName.getLastChild();
        txtPosition = txt.compareDocumentPosition(cdata);
        assertEquals("nodecomparedocumentpositionFollowingg20", 4, txtPosition);
        cdataPosition = cdata.compareDocumentPosition(txt);
        assertEquals("nodecomparedocumentpositionPRECEDING20", 2, cdataPosition);
    }
}

