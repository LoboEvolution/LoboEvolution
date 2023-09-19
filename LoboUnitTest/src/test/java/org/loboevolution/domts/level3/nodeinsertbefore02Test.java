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
 * Using insertBefore on a new Document node attempt to insert a new Comment node before
 * this DocumentType node and verify the name of the inserted Comment node.  Now
 * attempt to insert a new Processing Instruction node before the new Comment and
 * verify the target of the inserted ProcessingInstruction.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        DocumentType newDocType;
        Comment newComment;
        Comment insertedComment;
        String data;
        ProcessingInstruction newPI;
        ProcessingInstruction insertedPI;
        String target;
        String nullPubId = null;

        String nullSysId = null;

        String rootNS;
        String rootName;
        Element docElem;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDocType = domImpl.createDocumentType(rootName, nullPubId, nullSysId);
        newDoc = domImpl.createDocument(rootNS, rootName, newDocType);
        newComment = newDoc.createComment("Comment");
        newPI = newDoc.createProcessingInstruction("PITarget", "PIData");
        insertedComment = (Comment) newDoc.insertBefore(newComment, newDocType);
        data = insertedComment.getData();
        assertEquals("nodeinsertbefore02_1", "Comment", data);
        insertedPI = (ProcessingInstruction) newDoc.insertBefore(newPI, newComment);
        target = insertedPI.getTarget();
        assertEquals("nodeinsertbefore02_2", "PITarget", target);
    }
}

