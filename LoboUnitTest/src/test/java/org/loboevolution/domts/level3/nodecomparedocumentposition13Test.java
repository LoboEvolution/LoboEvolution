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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Comment;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;

/**
 * Using compareDocumentPosition check if the Document node contains and precedes the new Comment node,
 * and if the Comment node is contained and follows the Document node.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition13Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        Comment comment;
        Element elem;
        HTMLCollection elemList;
        int documentPosition;
        int commentPosition;
        doc = sampleXmlFile("hc_staff.xml");
        comment = doc.createComment("Another Comment");
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(3);
        elem.appendChild(comment);
        documentPosition = doc.compareDocumentPosition(comment);
        assertEquals("nodecomparedocumentpositionIsContainedFollowing13", 20, documentPosition);
        commentPosition = comment.compareDocumentPosition(doc);
        assertEquals("nodecomparedocumentpositionContainsPRECEDING13", 10, commentPosition);
    }
}