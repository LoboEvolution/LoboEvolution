
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level2;

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The method setNamedItemNS adds a node using its namespaceURI and localName and
 * raises a HIERARCHY_REQUEST_ERR if an attempt is made to add a node doesn't belong
 * in this NamedNodeMap.
 * Attempt to add an entity to a NamedNodeMap of attribute nodes,
 * Since nodes of this type cannot be added to the attribute node map a HIERARCHY_REQUEST_ERR
 * should be raised.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class Namednodemapsetnameditemns10Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final DocumentType docType;
        final NamedNodeMap entities;
        final NamedNodeMap attributes;
        final EntityReference entity;
        final Element element;
        final HTMLCollection elementList;
        doc = sampleXmlFile("staffNS.xml");
        docType = doc.getDoctype();
        entities = docType.getEntities();
        assertNotNull(entities);
        entity = (EntityReference) entities.getNamedItem("ent1");
        elementList = doc.getElementsByTagName("address");
        element = (Element) elementList.item(0);
        attributes = element.getAttributes();

        {
            boolean success = false;
            try {
                attributes.setNamedItemNS(entity);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.HIERARCHY_REQUEST_ERR);
            }
            assertTrue(success);
        }
    }
}

