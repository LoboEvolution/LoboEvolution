
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Comment;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.*;


/**
 * The "createComment(data)" method creates a new Comment
 * node given the specified string.
 * Retrieve the entire DOM document and invoke its
 * "createComment(data)" method.  It should create a new
 * Comment node whose "data" is the specified string.
 * The content, name and type are retrieved and output.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1334481328">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1334481328</a>
 */
public class hc_documentcreatecommentTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Comment newCommentNode;
        String newCommentValue;
        String newCommentName;
        int newCommentType;
        doc = sampleXmlFile("hc_staff.xml");
        newCommentNode = doc.createComment("This is a new Comment node");
        newCommentValue = newCommentNode.getNodeValue();
        assertEquals("value", "This is a new Comment node", newCommentValue);
        newCommentName = newCommentNode.getNodeName();
        assertEquals("STRONG", "#comment", newCommentName);
        newCommentType = newCommentNode.getNodeType();
        assertEquals("type", 8, newCommentType);
    }
}

