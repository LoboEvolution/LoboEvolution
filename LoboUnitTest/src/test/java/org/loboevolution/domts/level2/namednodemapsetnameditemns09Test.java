
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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * The method setNamedItemNS adds a node using its namespaceURI and localName and
 * raises a NO_MODIFICATION_ALLOWED_ERR if this map is readonly.
 * <p>
 * Create a new attribute node and attempt to add it to the nodemap of entities and notations
 * for this documenttype.  This should reaise a NO_MODIFICATION_ALLOWED_ERR.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 */
public class namednodemapsetnameditemns09Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entities;
        NamedNodeMap notations;
        Attr attr;
        doc = sampleXmlFile("staffNS.xml");
        docType = doc.getDoctype();
        entities = docType.getEntities();
        notations = docType.getNotations();
        attr = doc.createAttributeNS("http://www.w3.org/DOM/Test", "test");

        boolean success = false;
        try {
            entities.setNamedItemNS(attr);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
        }
        assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_entities", success);

        success = false;
        try {
            notations.setNamedItemNS(attr);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
        }
        assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_notations", success);
    }
}

