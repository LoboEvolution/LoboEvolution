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


import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.fail;


/**
 * Using insertBefore on this Document node attempt to insert a new Element node before
 * another Element node and verify a DOMException with a
 * HIERARCHY_REQUEST_ERR, NOT_FOUND_ERR or NOT_SUPPORTED_ERR is raised.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=415">http://www.w3.org/Bugs/Public/show_bug.cgi?id=415</a>
 */
public class nodeinsertbefore10Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element elem;
        Element newElem;
        Node inserted;
        Element docElem;
        String rootNS;
        String rootTagname;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootTagname = docElem.getTagName();
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(1);
        newElem = doc.createElementNS(rootNS, rootTagname);

        try {
            inserted = doc.insertBefore(newElem, elem);
            fail("throw_DOMException");

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 3:
                case 8:
                case 9:
                    break;
                default:
                    throw ex;
            }
        }
    }
}

