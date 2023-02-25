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
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertTrue;

/**
 * Get the newline between the "body" and "p" element.  Since node is both in element content
 * and whitespace, isElementContentWhitespace should return true.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-isElementContentWhitespace">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-isElementContentWhitespace</a>
 */
public class textiselementcontentwhitespace03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection pList;
        Element pElem;
        Text textNode;
        boolean isElemContentWhitespace;
        doc = sampleXmlFile("barfoo.xml");
        pList = doc.getElementsByTagName("p");
        pElem = (Element) pList.item(0);
        textNode = (Text) pElem.getPreviousSibling();
        isElemContentWhitespace = textNode.isElementContentWhitespace();
        assertTrue("isElementContentWhitespace", isElemContentWhitespace);
    }
}