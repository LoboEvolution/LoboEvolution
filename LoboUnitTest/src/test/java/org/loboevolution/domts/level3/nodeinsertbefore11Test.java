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
import org.loboevolution.html.node.CharacterData;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * Using insertBefore on a DocumentFragment node attempt to insert a child nodes before
 * other permissible nodes and verify the contents/name of each inserted node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore11Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        Element elem;
        ProcessingInstruction pi;
        Comment comment;
        Text txt;
        CDATASection cdata;
        EntityReference eRef;
        Node inserted;
        String insertedVal;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "body");
        pi = doc.createProcessingInstruction("PITarget", "PIData");
        comment = doc.createComment("Comment");
        txt = doc.createTextNode("Text");
        cdata = doc.createCDATASection("CDATA");
        eRef = doc.createEntityReference("alpha");
        appendedChild = docFrag.appendChild(elem);
        appendedChild = docFrag.appendChild(pi);
        appendedChild = docFrag.appendChild(comment);
        appendedChild = docFrag.appendChild(txt);
        appendedChild = docFrag.appendChild(cdata);
        appendedChild = docFrag.appendChild(eRef);
        inserted = docFrag.insertBefore(comment, pi);
        insertedVal = ((CharacterData) /*Node */inserted).getData();
        assertEquals("nodeinsertbefore11_Comment", "Comment", insertedVal);
        inserted = docFrag.insertBefore(txt, comment);
        insertedVal = ((CharacterData) /*Node */inserted).getData();
        assertEquals("nodeinsertbefore11_Text", "Text", insertedVal);
        inserted = docFrag.insertBefore(cdata, txt);
        insertedVal = ((CharacterData) /*Node */inserted).getData();
        assertEquals("nodeinsertbefore11_CDATA", "CDATA", insertedVal);
        inserted = docFrag.insertBefore(eRef, cdata);
        insertedVal = inserted.getNodeName();
        assertEquals("nodeinsertbefore11_Ent1", "alpha", insertedVal);
    }
}

