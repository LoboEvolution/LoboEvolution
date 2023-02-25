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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;

/**
 * The method insertBefore inserts the node newChild before the existing child node refChild.
 * If refChild is null, insert newChild at the end of the list of children.
 * <p>
 * Using insertBefore on an Element node attempt to insert new Comment/PI and CDATA nodes
 * before each other and verify the names of the newly inserted nodes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore18Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element newElem;
        Comment newComment;
        ProcessingInstruction newPI;
        CDATASection newCDATA;
        Comment insertedNode;
        String data;
        String target;
        Node appendedChild;
        Node inserted;
        doc = sampleXmlFile("hc_staff.xml");
        element = doc.createElement("element");
        newElem = doc.createElementNS("http://www.w3.org/DOM", "dom3:elem");
        newComment = doc.createComment("Comment");
        newCDATA = doc.createCDATASection("CDATASection");
        newPI = doc.createProcessingInstruction("target", "data");
        appendedChild = element.appendChild(newElem);
        appendedChild = element.appendChild(newComment);
        appendedChild = element.appendChild(newPI);
        appendedChild = element.appendChild(newCDATA);
        inserted = element.insertBefore(newComment, newElem);
        insertedNode = (Comment) element.getFirstChild();
        data = insertedNode.getData();
        assertEquals("nodeinsertbefore18", "Comment", data);
    }
}

