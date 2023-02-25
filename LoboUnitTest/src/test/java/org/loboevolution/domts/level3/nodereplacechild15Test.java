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
 * Using replaceChild on a DocumentFragment node attempt to replace an Element node with
 * another Element and the replaced element.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild15Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        Element elem;
        Element elem2;
        Element replaced;
        DOMImplementation domImpl;
        String title;
        Node appendedChild;
        Element docElem;
        String rootName;
        String rootNS;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootName = docElem.getTagName();
        rootNS = docElem.getNamespaceURI();
        elem = doc.createElementNS(rootNS, rootName);
        domImpl = doc.getImplementation();
        docFrag = doc.createDocumentFragment();
        elem2 = doc.createElementNS(rootNS, rootName);
        elem2.setAttribute("title", "new element");
        appendedChild = docFrag.appendChild(elem2);
        replaced = (Element) docFrag.replaceChild(elem, elem2);
        title = replaced.getAttribute("title");
        assertEquals("nodereplacechild15", "new element", title);
    }
}

