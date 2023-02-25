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

import static org.junit.Assert.*;


/**
 * Append an entity reference and a text node after to the content of the
 * first strong element.  Then call replaceWholeText on initial content
 * of that element.  Since the entity reference does not contain any
 * logically-adjacent text content, only the initial text element should
 * be replaced.
 *
 * @author IBM
 * @author Neil Delima
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=425">http://www.w3.org/Bugs/Public/show_bug.cgi?id=425</a>
 */
public class textreplacewholetext07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection itemList;
        Element elementName;
        Text textNode;
        EntityReference erefNode;
        Text replacedText;
        Node appendedChild;
        Node node;
        String nodeValue;
        int nodeType;
        doc = sampleXmlFile("hc_staff.xml");
        itemList = doc.getElementsByTagName("strong");
        elementName = (Element) itemList.item(0);
        erefNode = doc.createEntityReference("ent4");
        textNode = doc.createTextNode("New Text");
        appendedChild = elementName.appendChild(erefNode);
        appendedChild = elementName.appendChild(textNode);
        textNode = (Text) elementName.getFirstChild();
        replacedText = textNode.replaceWholeText("New Text and Cdata");
        textNode = (Text) elementName.getFirstChild();
        assertSame("retval_same", textNode, replacedText);
        nodeValue = textNode.getNodeValue();
        assertEquals("nodeValueSame", "New Text and Cdata", nodeValue);
        node = textNode.getNextSibling();
        assertNotNull("secondChildNotNull", node);
        nodeType = node.getNodeType();
        assertEquals("secondChildIsEntRef", 5, nodeType);
    }
}

