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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * Using setTextContent on a new DocumentFragment node Element child, attempt to set its content to
 * DOCUMENTFRAGMENT.  Retreive the textContent and verify if it is was set to DOCUMENTFRAGMENT
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent</a>
 */
public class nodesettextcontent11Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        Element elem;
        Element elemChild;
        Text txt;
        Comment comment;
        EntityReference entRef;
        CDATASection cdata;
        ProcessingInstruction pi;
        String textContent;
        doc = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:p");
        txt = doc.createTextNode("Text ");
        comment = doc.createComment("Comment ");
        entRef = doc.createEntityReference("alpha");
        pi = doc.createProcessingInstruction("PIT", "PIData ");
        cdata = doc.createCDATASection("CData");
        elem.appendChild(txt);
        elem.appendChild(comment);
        elem.appendChild(entRef);
        elem.appendChild(pi);
        elem.appendChild(cdata);
        docFrag.appendChild(elem);
        elem.setTextContent("DOCUMENTFRAGMENT");
        elemChild = (Element) docFrag.getLastChild();
        textContent = elemChild.getTextContent();
        assertEquals("nodegettextcontent11", "DOCUMENTFRAGMENT", textContent);
    }
}

