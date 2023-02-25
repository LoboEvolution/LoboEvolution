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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * Using replaceChild on a DocumentFragment node attempt to replace a Comment node with
 * a ProcessingInstruction and vice versa verify the data of the replaced nodes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild17Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        ProcessingInstruction pi;
        Comment cmt;
        Comment replacedCmt;
        ProcessingInstruction replacedPi;
        String data;
        String target;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        cmt = doc.createComment("Comment");
        pi = doc.createProcessingInstruction("target", "Comment");
        appendedChild = docFrag.appendChild(pi);
        appendedChild = docFrag.appendChild(cmt);
        replacedCmt = (Comment) docFrag.replaceChild(pi, cmt);
        data = replacedCmt.getData();
        assertEquals("nodereplacechild17_1", "Comment", data);
        replacedPi = (ProcessingInstruction) docFrag.replaceChild(cmt, pi);
        target = replacedPi.getTarget();
        assertEquals("nodereplacechild17_2", "target", target);
    }
}

