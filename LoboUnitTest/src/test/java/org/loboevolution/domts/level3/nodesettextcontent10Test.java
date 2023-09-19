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
 * The method setTextContent has no effect when the node is defined to be null.
 * <p>
 * Using setTextContent on a new Element node, attempt to set its content to ELEMENT.
 * Retreive the textContent and verify if it is was set to ELEMENT.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent</a>
 */
public class nodesettextcontent10Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem;
        Text txt;
        Comment comment;
        EntityReference entRef;
        CDATASection cdata;
        ProcessingInstruction pi;
        String textContent;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.createElementNS("http://www.w3.org/DOM/Test", "dom3:elem");
        txt = doc.createTextNode("Text ");
        comment = doc.createComment("Comment ");
        entRef = doc.createEntityReference("ent1");
        pi = doc.createProcessingInstruction("PIT", "PIData ");
        cdata = doc.createCDATASection("CData");
        appendedChild = elem.appendChild(txt);
        appendedChild = elem.appendChild(comment);
        appendedChild = elem.appendChild(entRef);
        appendedChild = elem.appendChild(pi);
        appendedChild = elem.appendChild(cdata);
        elem.setTextContent("ELEMENT");
        textContent = elem.getTextContent();
        assertEquals("nodesettextcontent10", "ELEMENT", textContent);
    }
}

