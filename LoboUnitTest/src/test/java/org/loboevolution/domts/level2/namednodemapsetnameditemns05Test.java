
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

package org.loboevolution.domts.level2;

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Retreive an entity and notation node and add the first notation to the
 * notation node map and first entity node to the entity map.  Since both these
 * maps are for readonly node, a NO_MODIFICATION_ALLOWED_ERR should be raised.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=407">http://www.w3.org/Bugs/Public/show_bug.cgi?id=407</a>
 * @see <a href="http://lists.w3.org/Archives/Member/w3c-dom-ig/2003Nov/0016.html">http://lists.w3.org/Archives/Member/w3c-dom-ig/2003Nov/0016.html</a>
 */
public class namednodemapsetnameditemns05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entities;
        NamedNodeMap notations;
        EntityReference entity;
        Notation notation;

        doc = sampleXmlFile("staffNS.xml");
        docType = doc.getDoctype();
        entities = docType.getEntities();
        assertNotNull("entitiesNotNull", entities);
        notations = docType.getNotations();
        assertNotNull("notationsNotNull", notations);
        entity = (EntityReference) entities.getNamedItem("ent1");
        notation = (Notation) notations.getNamedItem("notation1");

        boolean success = false;
        try {
            entities.setNamedItemNS(entity);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
        }
        assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_entities", success);

        try {
            notations.setNamedItemNS(notation);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
        }
        assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_notations", success);

    }
}

