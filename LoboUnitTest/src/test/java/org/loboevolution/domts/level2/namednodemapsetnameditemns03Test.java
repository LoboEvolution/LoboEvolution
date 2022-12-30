
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * The method setNamedItemNS adds a node using its namespaceURI and localName and
 * raises a WRONG_DOCUMENT_ERR if arg was created from a different document than the
 * one that created this map.
 * <p>
 * Retreieve the second element whose local name is address and its attribute into a named node map.
 * Do the same for another document and retreive its street attribute.  Call the setNamedItemNS
 * using the first namedNodeMap and the retreive street attribute of the second.  This should
 * raise a WRONG_DOCUMENT_ERR.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=408">http://www.w3.org/Bugs/Public/show_bug.cgi?id=408</a>
 */
public class namednodemapsetnameditemns03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        Document doc;
        Document docAlt;
        NamedNodeMap attributes;
        NamedNodeMap attributesAlt;
        HTMLCollection elementList;
        HTMLCollection elementListAlt;
        Element element;
        Element elementAlt;
        Attr attr;
        String nullNS = null;

        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagNameNS("*", "address");
        element = (Element) elementList.item(1);
        attributes = element.getAttributes();
        docAlt = sampleXmlFile("staffNS.xml");
        elementListAlt = docAlt.getElementsByTagNameNS("*", "address");
        elementAlt = (Element) elementListAlt.item(1);
        attributesAlt = elementAlt.getAttributes();
        attr = (Attr) attributesAlt.getNamedItemNS(nullNS, "street");
        boolean success = false;

        try {
            attributesAlt.removeNamedItemNS(nullNS, "street");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_FOUND);
        }

        try {
            attributes.setNamedItemNS(attr);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.WRONG_DOCUMENT_ERR);
        }
        assertTrue("throw_WRONG_DOCUMENT_ERR", success);

    }
}

