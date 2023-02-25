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
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * The method insertBefore inserts the node newChild before the existing child node refChild.
 * If refChild is null, insert newChild at the end of the list of children.
 * If newChild is a DocumentFragment object, all of its children are inserted, in the same
 * order, before refChild.
 * Using insertBefore on this Document node attempt to insert a new DocumentFragment node
 * before a Comment node and verify the contents of the Comment node that is a child
 * of the DocumentFragment.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore09Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        Comment newComment;
        Comment insertComment;
        Comment comment;
        DocumentFragment inserted;
        String data;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        newComment = doc.createComment("Comment");
        appendedChild = doc.appendChild(newComment);
        docFrag = doc.createDocumentFragment();
        insertComment = doc.createComment("insertComment");
        appendedChild = docFrag.appendChild(insertComment);
        inserted = (DocumentFragment) doc.insertBefore(docFrag, newComment);
        comment = (Comment) newComment.getPreviousSibling();
        data = comment.getData();
        assertEquals("nodeinsertbefore09", "insertComment", data);
    }
}

