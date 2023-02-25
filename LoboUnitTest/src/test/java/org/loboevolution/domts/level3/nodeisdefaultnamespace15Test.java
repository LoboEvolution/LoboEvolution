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
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * Using isDefaultNamespace on a Element's new cloned Comment node, which has a namespace attribute
 * declaration without a namespace prefix in its parent Element node and  verify if the
 * value returned is true.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isDefaultNamespace">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isDefaultNamespace</a>
 */
public class nodeisdefaultnamespace15Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element bodyElem;
        Element elem;
        Comment comment;
        Comment clonedComment;
        boolean isDefault;
        Node appendedChild;
        HTMLCollection bodyList;
        doc = sampleXmlFile("hc_staff.xml");
        bodyList = doc.getElementsByTagName("body");
        bodyElem = (Element) bodyList.item(0);
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "p");
        comment = doc.createComment("Text");
        clonedComment = (Comment) comment.cloneNode(true);
        appendedChild = elem.appendChild(clonedComment);
        appendedChild = bodyElem.appendChild(elem);
        isDefault = clonedComment.isDefaultNamespace("http://www.w3.org/1999/xhtml");
        assertTrue("nodeisdefaultnamespace15", isDefault);
    }
}

