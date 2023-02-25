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
import org.loboevolution.html.node.Comment;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Using replaceChild on this Document node, attempt to replace a new ProcessingInstruction
 * node with new Comment node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=416">http://www.w3.org/Bugs/Public/show_bug.cgi?id=416</a>
 */
public class nodereplacechild12Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        ProcessingInstruction pi;
        Node replaced;
        Comment comment;
        Node lastChild;
        String nodeName;
        Node replacedNode;
        Node appendedChild;
        doc = sampleXmlFile("barfoo.xml");
        comment = doc.createComment("dom3:doc");
        pi = doc.createProcessingInstruction("PITarget", "PIData");
        appendedChild = doc.appendChild(comment);
        appendedChild = doc.appendChild(pi);
        replacedNode = doc.replaceChild(comment, pi);
        assertNotNull("returnValueNotNull", replacedNode);
        nodeName = replacedNode.getNodeName();
        assertEquals("returnValueIsPI", "PITarget", nodeName);
        lastChild = doc.getLastChild();
        assertNotNull("lastChildNotNull", lastChild);
        nodeName = lastChild.getNodeName();
        assertEquals("lastChildIsComment", "#comment", nodeName);
    }
}

