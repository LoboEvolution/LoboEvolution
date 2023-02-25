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
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Checks baseURI for a text node is null.
 *
 * @author Curt Arnold
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=419">http://www.w3.org/Bugs/Public/show_bug.cgi?id=419</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2DocumentType">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2DocumentType</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2EntityReference">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2EntityReference</a>
 */
public class nodegetbaseuri19Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        String baseURI;
        String entBaseURI;
        EntityReference entRef;
        HTMLCollection pList;
        Element pElem;
        Text textNode;
        doc = sampleXmlFile("external_barfoo.xml");
        pList = doc.getElementsByTagName("p");
        pElem = (Element) pList.item(0);
        assertNotNull("pElemNotNull", pElem);
        entRef = (EntityReference) pElem.getLastChild();
        assertNotNull("entRefNotNull", entRef);
        textNode = (Text) entRef.getFirstChild();
        assertNotNull("entRefTextNotNull", textNode);

        baseURI = textNode.getBaseURI();
        assertNull("baseURI", baseURI);
    }
}

