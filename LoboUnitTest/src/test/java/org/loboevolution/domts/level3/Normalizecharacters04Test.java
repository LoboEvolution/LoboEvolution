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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Normalize an element with normalize-characters set to true, check that
 * characters are normalized.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-normalize">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-normalize</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-normalize-characters">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-normalize-characters</a>
 * @see <a href="http://www.w3.org/TR/2003/WD-charmod-20030822/">http://www.w3.org/TR/2003/WD-charmod-20030822/</a>
 */
public class Normalizecharacters04Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DOMConfiguration domConfig;
        final DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        HTMLCollection pList;
        Element pElem;
        Text text;
        final String textValue;
        final boolean canSet;
        doc = sampleXmlFile("barfoo.xml");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("normalize-characters", Boolean.TRUE);

        if (canSet) {
            domConfig.setParameter("normalize-characters", Boolean.TRUE);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            pList = doc.getElementsByTagName("p");
            pElem = (Element) pList.item(0);
            text = doc.createTextNode("suçon");
            pElem.appendChild(text);
            pElem.normalize();
            assertTrue(errorMonitor.assertLowerSeverity(2), "Normalizecharacters04Assert3");
            pList = doc.getElementsByTagName("p");
            pElem = (Element) pList.item(0);
            text = (Text) pElem.getFirstChild();
            textValue = text.getNodeValue();
            assertEquals("barsuçon", textValue, "Normalizecharacters04Assert4");
        }
    }
}

