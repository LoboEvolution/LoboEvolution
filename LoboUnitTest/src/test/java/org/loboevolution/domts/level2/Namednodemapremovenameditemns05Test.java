
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Retreive an entity and notation node and remove the first notation from the
 * entity node map and first entity node from the notation map.  Since both these
 * maps are readonly, a NO_MODIFICATION_ALLOWED_ERR should be raised.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=407">http://www.w3.org/Bugs/Public/show_bug.cgi?id=407</a>
 * @see <a href="http://lists.w3.org/Archives/Member/w3c-dom-ig/2003Nov/0016.html">http://lists.w3.org/Archives/Member/w3c-dom-ig/2003Nov/0016.html</a>
 */
public class Namednodemapremovenameditemns05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final DocumentType docType;
        final NamedNodeMap entities;
        final NamedNodeMap notations;
        doc = sampleXmlFile("staffNS.xml");
        docType = doc.getDoctype();
        entities = docType.getEntities();
        assertNotNull(entities);
        notations = docType.getNotations();
        assertNotNull(notations);

        try {
            entities.removeNamedItemNS(null, "ent1");
            fail("entity_throw_DOMException");

        } catch (final DOMException ex) {
            switch (ex.getCode()) {
                case 8, 7:
                    break;
                default:
                    throw ex;
            }
        }

        try {
            notations.removeNamedItemNS(null, "notation1");
            fail("notation_throw_DOMException");

        } catch (final DOMException ex) {
            switch (ex.getCode()) {
                case 8:
                case 7:
                    break;
                default:
                    throw ex;
            }
        }
    }
}

