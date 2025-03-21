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
import org.loboevolution.html.node.CharacterData;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Using insertBefore on a DocumentFragment node attempt to insert a child nodes before
 * other permissible nodes and verify the contents/name of each inserted node.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class Nodeinsertbefore11Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DocumentFragment docFrag;
        final Element elem;
        final ProcessingInstruction pi;
        final Comment comment;
        final Text txt;
        final CDATASection cdata;
        final EntityReference eRef;
        Node inserted;
        String insertedVal;
        doc = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "body");
        pi = doc.createProcessingInstruction("PITarget", "PIData");
        comment = doc.createComment("Comment");
        txt = doc.createTextNode("Text");
        cdata = doc.createCDATASection("CDATA");
        eRef = doc.createEntityReference("alpha");
        docFrag.appendChild(elem);
        docFrag.appendChild(pi);
        docFrag.appendChild(comment);
        docFrag.appendChild(txt);
        docFrag.appendChild(cdata);
        docFrag.appendChild(eRef);
        inserted = docFrag.insertBefore(comment, pi);
        insertedVal = ((CharacterData) /*Node */inserted).getData();
        assertEquals("Comment", insertedVal, "Nodeinsertbefore11Assert2");
        inserted = docFrag.insertBefore(txt, comment);
        insertedVal = ((CharacterData) /*Node */inserted).getData();
        assertEquals("Text", insertedVal, "Nodeinsertbefore11Assert3");
        inserted = docFrag.insertBefore(cdata, txt);
        insertedVal = ((CharacterData) /*Node */inserted).getData();
        assertEquals("CDATA", insertedVal, "Nodeinsertbefore11Assert4");
        inserted = docFrag.insertBefore(eRef, cdata);
        insertedVal = inserted.getNodeName();
        assertEquals("alpha", insertedVal, "Nodeinsertbefore11Assert5");
    }
}

