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
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.assertEquals;


/**
 * Using insertBefore on this Document node attempt to insert a new Comment node before
 * this DocumentElement node and verify the name of the inserted Comment node.  Now
 * attempt to insert a new Processing Instruction node before the new Comment and
 * verify the target of the inserted ProcessingInstruction.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        Comment newComment;
        Comment insertedComment;
        String data;
        ProcessingInstruction newPI;
        ProcessingInstruction insertedPI;
        String target;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        newComment = doc.createComment("Comment");
        newPI = doc.createProcessingInstruction("PITarget", "PIData");
        insertedComment = (Comment) doc.insertBefore(newComment, docElem);
        data = insertedComment.getData();
        assertEquals("nodeinsertbefore01_1", "Comment", data);
        insertedPI = (ProcessingInstruction) doc.insertBefore(newPI, newComment);
        target = insertedPI.getTarget();
        assertEquals("nodeinsertbefore01_2", "PITarget", target);
    }
}

