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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertEquals;


/**
 * Using compareDocumentPosition check if the document position of the first new Text node compared to the
 * second text node is PRECEDING and is FOLLOWING vice versa.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition18Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        Text txt1;
        Text txt2;
        int txt1Position;
        int txt2Position;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        txt1 = doc.createTextNode("T1");
        txt2 = doc.createTextNode("T2");
        docElem.appendChild(txt1);
        docElem.appendChild(txt2);
        txt1Position = txt1.compareDocumentPosition(txt2);
        assertEquals("nodecomparedocumentpositionFollowing18", 4, txt1Position);
        txt2Position = txt2.compareDocumentPosition(txt1);
        assertEquals("nodecomparedocumentpositionPRECEDING18", 2, txt2Position);
    }
}

