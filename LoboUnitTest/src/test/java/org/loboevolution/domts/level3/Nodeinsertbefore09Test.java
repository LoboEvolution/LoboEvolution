/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Comment;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The method insertBefore inserts the node newChild before the existing child node refChild.
 * If refChild is null, insert newChild at the end of the list of children.
 * If newChild is a DocumentFragment object, all of its children are inserted, in the same
 * order, before refChild.
 * Using insertBefore on this Document node attempt to insert a new DocumentFragment node
 * before a Comment node and verify the contents of the Comment node that is a child
 * of the DocumentFragment.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class Nodeinsertbefore09Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DocumentFragment docFrag;
        final Comment newComment;
        final Comment insertComment;
        final Comment comment;
        final String data;
        doc = sampleXmlFile("hc_staff.xml");
        newComment = doc.createComment("Comment");
        doc.appendChild(newComment);
        docFrag = doc.createDocumentFragment();
        insertComment = doc.createComment("insertComment");
        docFrag.appendChild(insertComment);
        doc.insertBefore(docFrag, newComment);
        comment = (Comment) newComment.getPreviousSibling();
        data = comment.getData();
        assertEquals("insertComment", data, "Nodeinsertbefore09Assert2");
    }

}

