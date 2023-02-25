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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * Using replaceChild on an Element node attempt to replace an
 * EntityReference or Text child node
 * with an Entity node and with itself and verify if a HIERARCHY_REQUEST_ERR gets thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild25Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entities;
        Node entity;
        HTMLCollection childList;
        Node entRef;
        Element elem;
        Element replaced;
        String nodeName;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        entities = docType.getEntities();
        entity = entities.getNamedItem("alpha");
        childList = doc.getElementsByTagName("acronym");
        elem = (Element) childList.item(1);
        entRef = elem.getFirstChild();

        {
            boolean success = false;
            try {
                replaced = (Element) elem.replaceChild(entity, entRef);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.HIERARCHY_REQUEST_ERR);
            }
            assertTrue("throw_HIERARCHY_REQUEST_ERR_1", success);
        }

        {
            boolean success = false;
            try {
                replaced = (Element) elem.replaceChild(elem, entRef);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.HIERARCHY_REQUEST_ERR);
            }
            assertTrue("throw_HIERARCHY_REQUEST_ERR_2", success);
        }
    }
}

