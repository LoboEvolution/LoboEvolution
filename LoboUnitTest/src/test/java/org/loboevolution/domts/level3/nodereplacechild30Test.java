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
 * Using replaceChild on an Element node attempt to replace a new Element child node with
 * new child nodes and vice versa and in each case verify the name of the replaced node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild30Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element parent;
        Element oldChild;
        Element newElement;
        Text newText;
        Comment newComment;
        ProcessingInstruction newPI;
        CDATASection newCdata;
        EntityReference newERef;
        Node replaced;
        String nodeName;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        parent = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:html");
        oldChild = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:head");
        newElement = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:body");
        appendedChild = parent.appendChild(oldChild);
        appendedChild = parent.appendChild(newElement);
        newText = doc.createTextNode("Text");
        appendedChild = parent.appendChild(newText);
        newComment = doc.createComment("Comment");
        appendedChild = parent.appendChild(newComment);
        newPI = doc.createProcessingInstruction("target", "data");
        appendedChild = parent.appendChild(newPI);
        newCdata = doc.createCDATASection("Cdata");
        appendedChild = parent.appendChild(newCdata);
        newERef = doc.createEntityReference("delta");
        appendedChild = parent.appendChild(newERef);
        replaced = parent.replaceChild(newElement, oldChild);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild30_1", "xhtml:head", nodeName);
        replaced = parent.replaceChild(oldChild, newElement);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild30_2", "xhtml:body", nodeName);
        replaced = parent.replaceChild(newText, oldChild);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild30_3", "xhtml:head", nodeName);
        replaced = parent.replaceChild(oldChild, newText);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild30_4", "#text", nodeName);
        replaced = parent.replaceChild(newComment, oldChild);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild30_5", "xhtml:head", nodeName);
        replaced = parent.replaceChild(oldChild, newComment);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild30_6", "#comment", nodeName);
        replaced = parent.replaceChild(oldChild, newPI);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild30_7", "target", nodeName);
        replaced = parent.replaceChild(oldChild, newCdata);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild30_8", "#cdata-section", nodeName);
        replaced = parent.replaceChild(oldChild, newERef);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild30_9", "delta", nodeName);
    }
}

