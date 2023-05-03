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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;

/**
 * The method replaceChild replaces the child node oldChild with newChild in the list of
 * children, and returns the oldChild node.
 * Using replaceChild on an Element node attempt to replace an Element node with another
 * Element from another document and verify if a WRONG_DOCUMENT_ERR gets thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild27Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document doc2;
        HTMLCollection childList;
        HTMLCollection childList2;
        Element elem2;
        Element elem;
        Node firstChild;
        String nodeName;
        Node replaced;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagNameNS("*", "p");
        elem = (Element) childList.item(0);
        firstChild = elem.getFirstChild();
        doc2 = sampleXmlFile("hc_staff.xml");
        childList2 = doc2.getElementsByTagNameNS("*", "p");
        elem2 = (Element) childList2.item(0);

        {
            boolean success = false;
            try {
                replaced = elem.replaceChild(elem2, firstChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.WRONG_DOCUMENT_ERR);
            }
            assertTrue("WRONG_DOCUMENT_ERR_nodereplacechild27", success);
        }
    }
}