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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.DOMConfiguration;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.domimpl.DOMErrorMonitor;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Normalize document using Node.normalize with element-content-whitespace set to false and validation set to true, check that
 * whitespace in element content is preserved.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-normalize">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-normalize</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-element-content-whitespace">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-element-content-whitespace</a>
 */
public class Elementcontentwhitespace03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection bodyList;
        final Element body;
        final DOMConfiguration domConfig;
        final boolean canSet;
        final DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        Node child;
        String childName;
        doc = sampleXmlFile("barfoo.xml");
        domConfig = doc.getDomConfig();

        canSet = domConfig.canSetParameter("element-content-whitespace", Boolean.FALSE);

        if (canSet) {
            domConfig.setParameter("element-content-whitespace", Boolean.FALSE);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            doc.normalize();
            assertTrue(errorMonitor.assertLowerSeverity(2), "Elementcontentwhitespace03Assert1");
            bodyList = doc.getElementsByTagName("body");
            body = (Element) bodyList.item(0);
            child = body.getFirstChild();
            assertNotNull(child, "Elementcontentwhitespace03Assert2");
            childName = child.getNodeName();
            assertEquals("#text", childName, "Elementcontentwhitespace03Assert3");
            child = child.getNextSibling();
            assertNotNull(child, "Elementcontentwhitespace03Assert4");
            childName = child.getNodeName();
            assertEquals("P", childName, "Elementcontentwhitespace03Assert5");
        }
    }
}

