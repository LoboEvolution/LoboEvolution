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
 * Using replaceChild on a DocumentFragment node attempt to replace a CDATASection node with
 * a EntityReference and vice versa verify the data of the replaced nodes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild18Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        EntityReference entRef;
        CDATASection cdata;
        CDATASection replacedCData;
        EntityReference replacedEref;
        String cdataName;
        String erefName;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        cdata = doc.createCDATASection("CDATASection");
        entRef = doc.createEntityReference("alpha");
        appendedChild = docFrag.appendChild(entRef);
        appendedChild = docFrag.appendChild(cdata);
        replacedCData = (CDATASection) docFrag.replaceChild(entRef, cdata);
        cdataName = replacedCData.getNodeValue();
        assertEquals("nodereplacechild18_1", "CDATASection", cdataName);
        replacedEref = (EntityReference) docFrag.replaceChild(cdata, entRef);
        erefName = replacedEref.getNodeName();
        assertEquals("nodereplacechild18_2", "alpha", erefName);
    }
}

