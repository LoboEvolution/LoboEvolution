
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


/**
 * An attempt to add remove an entity using removeNamedItemNS should result in
 * a NO_MODIFICATION_ERR or a NOT_FOUND_ERR.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1788794630">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1788794630</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-removeNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-removeNamedItemNS</a>
 */
public class hc_entitiesremovenameditemns1Test extends LoboUnitTest {


    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NamedNodeMap entities;
        DocumentType docType;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();

        assertNotNull("docTypeNotNull", docType);
        entities = docType.getEntities();
        assertNotNull("entitiesNotNull", entities);

        try {
            entities.removeNamedItemNS("http://www.w3.org/1999/xhtml", "alpha");
            fail("throw_NO_MOD_OR_NOT_FOUND_ERR");

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 7:
                case 8:
                    break;
                default:
                    throw ex;
            }

        }
    }
}

