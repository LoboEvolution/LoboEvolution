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
 * The method getTextContent returns the text content of this node and its descendants.
 * <p>
 * Invoke the method getTextContent on a new DocumentFragment node with new Text, EntityReferences
 * CDATASection, PI and Comment nodes and check if the value returned is a single
 * concatenated String with its content.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent</a>
 */
public class nodegettextcontent16Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        Element elem;
        Element elemChild;
        Text txt;
        Comment comment;
        EntityReference entRef;
        CDATASection cdata;
        ProcessingInstruction pi;
        String textContent;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        elem = doc.createElementNS("http://www.w3.org/DOM/Test", "dom3:elem");
        txt = doc.createTextNode("Text ");
        comment = doc.createComment("Comment ");
        entRef = doc.createEntityReference("beta");
        pi = doc.createProcessingInstruction("PIT", "PIData ");
        cdata = doc.createCDATASection("CData");
        appendedChild = elem.appendChild(txt);
        appendedChild = elem.appendChild(comment);
        appendedChild = elem.appendChild(entRef);
        appendedChild = elem.appendChild(pi);
        appendedChild = elem.appendChild(cdata);
        appendedChild = docFrag.appendChild(elem);
        doc.normalizeDocument();
        textContent = docFrag.getTextContent();
        assertEquals("nodegettextcontent16", "Text βCData", textContent);
    }
}

