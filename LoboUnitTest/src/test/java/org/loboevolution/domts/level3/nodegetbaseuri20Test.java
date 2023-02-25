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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertNotNull;


/**
 * baseURI for an element from an entity reference should be the URI of the
 * external entity if there is now xml:base attribute.
 *
 * @author Curt Arnold
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=419">http://www.w3.org/Bugs/Public/show_bug.cgi?id=419</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2EntityReference">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2EntityReference</a>
 */
public class nodegetbaseuri20Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        String baseURI;
        HTMLCollection pList;
        Element pElem;
        doc = sampleXmlFile("external_barfoo.xml");
        pList = doc.getElementsByTagName("p");
        pElem = (Element) pList.item(2);
        assertNotNull("pElemNotNull", pElem);
        baseURI = pElem.getBaseURI();
        assertURIEquals("equalsExternalBarFoo", null, null, null, null, "external_widget", null, null, Boolean.TRUE, baseURI);
    }
}

