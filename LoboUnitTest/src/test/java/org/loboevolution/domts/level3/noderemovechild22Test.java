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


import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Comment;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Using removeChild on a new Element node attempt to remove a new Comment child
 * and verify the name of the rturned node that was removed.  Now to remove the child
 * using removeChild on the parent and verify if a NOT_FOUND_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 */
public class noderemovechild22Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element parent;
        Comment child;
        Comment removed;
        String removedName;
        Node removedNode;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        parent = doc.createElementNS("http://www.w3.org/1999/xhtml", "dom3:p");
        child = doc.createComment("DATA");
        appendedChild = parent.appendChild(child);
        removed = (Comment) parent.removeChild(child);
        removedName = removed.getNodeValue();
        assertEquals("noderemovechild22", "DATA", removedName);

        {
            boolean success = false;
            try {
                removedNode = parent.removeChild(child);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_FOUND_ERR);
            }
            assertTrue("throw_NOT_FOUND_ERR", success);
        }
    }
}

