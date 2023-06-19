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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;


/**
 * Using compareDocumentPosition to check if the document position returned by comparing the first name with
 * a first position node of another document reference and adopted by the first as a parameter is FOLLOWING.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition32Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document doc2;
        HTMLCollection nameList;
        HTMLCollection positionList;
        Element strong;
        Element code;
        int documentPosition;
        doc = sampleXmlFile("hc_staff.xml");
        doc2 = doc;
        nameList = doc.getElementsByTagName("strong");
        strong = (Element) nameList.item(0);
        positionList = doc2.getElementsByTagName("code");
        code = (Element) positionList.item(0);
        documentPosition = strong.compareDocumentPosition(code);
        assertEquals("nodecomparedocumentpositionFollowing32", 4, documentPosition);
    }
}
