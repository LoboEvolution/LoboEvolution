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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * Invoke replaceWholeText on an existing text node with newly created text and CDATASection
 * nodes appended as children of its parent element node.  Verify repalceWholeText by
 * verifying the values returned by wholeText.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText</a>
 */
public class textreplacewholetext05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection itemList;
        Element elementName;
        Text textNode;
        CDATASection cdataNode;
        Text replacedText;
        String wholeText;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        itemList = doc.getElementsByTagName("strong");
        elementName = (Element) itemList.item(0);
        textNode = doc.createTextNode("New Text");
        cdataNode = doc.createCDATASection("New CDATA");
        appendedChild = elementName.appendChild(textNode);
        appendedChild = elementName.appendChild(cdataNode);
        textNode = (Text) elementName.getFirstChild();
        replacedText = textNode.replaceWholeText("New Text and Cdata");
        wholeText = replacedText.getWholeText();
        assertEquals("textreplacewholetext05", "New Text and Cdata", wholeText);
    }
}

