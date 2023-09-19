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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.nodeimpl.DOMErrorMonitor;
import org.loboevolution.html.node.*;

import static org.junit.Assert.*;


/**
 * Normalize document with infoset set to true, check that
 * entity references are expanded and unused entity declaration are maintained.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-infoset">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-infoset</a>
 */
public class infoset01Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        HTMLCollection pList;
        Element pElem;
        DOMConfiguration domConfig;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        Node child;
        String childName;
        EntityReference entRef;
        String childValue;
        NamedNodeMap entities;
        Node ent2;
        DocumentType doctype;
        doc = sampleXmlFile("barfoo.xml");
        domConfig = doc.getDomConfig();
        domConfig.setParameter("infoset", Boolean.TRUE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        pList = doc.getElementsByTagName("p");
        pElem = (Element) pList.item(0);
        entRef = doc.createEntityReference("ent1");
        child = pElem.appendChild(entRef);
        doc.normalizeDocument();
        assertTrue("normalizeError", errorMonitor.assertLowerSeverity(2));
        pList = doc.getElementsByTagName("p");
        pElem = (Element) pList.item(0);
        child = pElem.getLastChild();
        assertNotNull("lastChildNotNull", child);
        childName = child.getNodeName();
        assertEquals("firstChildName", "#text", childName);
        childValue = child.getNodeValue();
        assertEquals("firstChildValue", "barfoo", childValue);
        doctype = doc.getDoctype();
        entities = doctype.getEntities();
        ent2 = entities.getNamedItem("ent2");
        assertNotNull("ent2NotNull", ent2);
    }
}

