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
 * The method replaceWholeText substitutes the a specified text for the text of
 * the current node and all logically-adjacent text nodes.  This method raises
 * a NO_MODIFICATION_ALLOWED_ERR if one of the Text nodes being replaced is readonly.
 * <p>
 * Invoke replaceWholeText on an existing text node with newly created text and Entityreference
 * nodes (whose replacement text is a character entity reference) appended as children of its parent element node.
 * Where the nodes to be removed are read-only descendants of an EntityReference, the EntityReference
 * must be removed instead of the read-only nodes. Only if any EntityReference to be removed has
 * descendants that are not EntityReference, Text, or CDATASection nodes, the replaceWholeText
 * method must fail, raising a NO_MODIFICATION_ALLOWED_ERR. Verify that the method does not raise
 * an exception and verify the content of the returned text node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText</a>
 */
public class textreplacewholetext06Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection itemList;
        Element elementStrong;
        Text textNode;
        EntityReference erefNode;
        Text replacedText;
        Node appendedChild;
        String nodeValue;
        doc = sampleXmlFile("hc_staff.xml");
        itemList = doc.getElementsByTagName("strong");
        elementStrong = (Element) itemList.item(0);
        textNode = doc.createTextNode("New Text");
        erefNode = doc.createEntityReference("beta");
        appendedChild = elementStrong.appendChild(textNode);
        appendedChild = elementStrong.appendChild(erefNode);
        textNode = (Text) elementStrong.getFirstChild();
        replacedText = textNode.replaceWholeText("New Text and Cdata");
        nodeValue = textNode.getNodeValue();
        assertEquals("textreplacewholetext06", "New Text and Cdata", nodeValue);
    }
}

