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
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Using replaceChild on a DocumentFragment node attempt to replace a CDATASection node with
 * a EntityReference and vice versa verify the data of the replaced nodes.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class Nodereplacechild18Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DocumentFragment docFrag;
        final EntityReference entRef;
        final CDATASection cdata;
        final CDATASection replacedCData;
        final EntityReference replacedEref;
        final String cdataName;
        final String erefName;
        doc = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        cdata = doc.createCDATASection("CDATASection");
        entRef = doc.createEntityReference("alpha");
        docFrag.appendChild(entRef);
        docFrag.appendChild(cdata);
        replacedCData = (CDATASection) docFrag.replaceChild(entRef, cdata);
        cdataName = replacedCData.getNodeValue();
        assertEquals("CDATASection", cdataName, "Nodereplacechild18Assert2");
        replacedEref = (EntityReference) docFrag.replaceChild(cdata, entRef);
        erefName = replacedEref.getNodeName();
        assertEquals("alpha", erefName, "Nodereplacechild18Assert3");
    }
}

