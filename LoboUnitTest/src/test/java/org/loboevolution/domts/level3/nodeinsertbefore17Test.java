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
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertEquals;


/**
 * The method insertBefore inserts the node newChild before the existing child node refChild.
 * If refChild is null, insert newChild at the end of the list of children.
 * <p>
 * Using insertBefore on an Element node attempt to insert a text node before its
 * first element child and verify the name of the new first child node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore17Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Text newText;
        Node refNode;
        Node firstChild;
        Text insertedText;
        HTMLCollection childList;
        String nodeName;
        Node inserted;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagNameNS("*", "p");
        element = (Element) childList.item(1);
        refNode = element.getFirstChild();
        newText = doc.createTextNode("newText");
        inserted = element.insertBefore(newText, refNode);
        insertedText = (Text) element.getFirstChild();
        nodeName = insertedText.getNodeName();
        assertEquals("nodeinsertbefore17", "#text", nodeName);
    }
}

