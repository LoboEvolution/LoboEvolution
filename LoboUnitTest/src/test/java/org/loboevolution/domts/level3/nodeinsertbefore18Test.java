/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

