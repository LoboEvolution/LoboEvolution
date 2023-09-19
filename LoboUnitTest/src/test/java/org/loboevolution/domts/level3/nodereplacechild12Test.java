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

