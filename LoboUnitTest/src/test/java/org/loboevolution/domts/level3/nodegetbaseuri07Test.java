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

import static org.junit.Assert.assertEquals;


/**
 * Append a created element to a document and check that its baseURI
 * is inherited from its parent.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=419">http://www.w3.org/Bugs/Public/show_bug.cgi?id=419</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2Element">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2Element</a>
 */
public class nodegetbaseuri07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element newElement;
        String baseURI;
        HTMLCollection bodyList;
        Element bodyElem;
        String htmlNS = "http://www.w3.org/1999/xhtml";
        doc = sampleXmlFile("barfoo_base.xml");
        bodyList = doc.getElementsByTagName("body");
        bodyElem = (Element) bodyList.item(0);
        newElement = doc.createElementNS(htmlNS, "meta");
        newElement.setAttribute("content", "text/xml");
        bodyElem.appendChild(newElement);
        baseURI = newElement.getBaseURI();
        assertEquals("nodegetbaseuri07", "http://www.w3.org/DOM/EmployeeID", baseURI);
    }
}

