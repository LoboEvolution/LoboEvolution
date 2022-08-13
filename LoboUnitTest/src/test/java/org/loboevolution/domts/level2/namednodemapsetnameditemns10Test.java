
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.domts.level2;

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.Entity;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * The method setNamedItemNS adds a node using its namespaceURI and localName and
 * raises a HIERARCHY_REQUEST_ERR if an attempt is made to add a node doesn't belong
 * in this NamedNodeMap.
 * Attempt to add an entity to a NamedNodeMap of attribute nodes,
 * Since nodes of this type cannot be added to the attribute node map a HIERARCHY_REQUEST_ERR
 * should be raised.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class namednodemapsetnameditemns10Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entities;
        NamedNodeMap attributes;
        Entity entity;
        Notation notation;
        Element element;
        HTMLCollection elementList;
        Node newNode;
        doc = sampleXmlFile("staffNS.xml");
        docType = doc.getDoctype();
        entities = docType.getEntities();
        assertNotNull("entitiesNotNull", entities);
        entity = (Entity) entities.getNamedItem("ent1");
        elementList = doc.getElementsByTagNameNS("http://www.nist.gov", "address");
        element = (Element) elementList.item(0);
        attributes = element.getAttributes();

        {
            boolean success = false;
            try {
                newNode = attributes.setNamedItemNS((Attr)entity);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.HIERARCHY_REQUEST_ERR);
            }
            assertTrue("throw_HIERARCHY_REQUEST_ERR", success);
        }
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/namednodemapsetnameditemns10";
    }
}

