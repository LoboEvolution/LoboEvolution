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
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Invoke the adoptNode method on this document using a new PI node created in a new doc
 * as the source.  Verify if the node has been adopted correctly by checking the nodeValue
 * of the adopted node.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class Documentadoptnode36Test extends LoboUnitTest {


    @Test
    public void runTest() {
        final Document doc;
        final DOMImplementation domImpl;
        final Document newDoc;
        final ProcessingInstruction newPI1;
        final ProcessingInstruction newPI2;
        final ProcessingInstruction adoptedPI1;
        final ProcessingInstruction adoptedPI2;
        String piTarget;
        String piData;
        final Element docElem;
        final String rootNS;
        final String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, null);
        newPI1 = newDoc.createProcessingInstruction("PITarget", "PIData");
        newPI2 = doc.createProcessingInstruction("PITarget", "PIData");
        adoptedPI1 = (ProcessingInstruction) newDoc.adoptNode(newPI1);

        if ((adoptedPI1 != null)) {
            adoptedPI2 = (ProcessingInstruction) newDoc.adoptNode(newPI2);

            if ((adoptedPI2 != null)) {
                piTarget = adoptedPI1.getTarget();
                piData = adoptedPI1.getData();
                assertEquals("PITarget", piTarget, "Documentadoptnode36Assert2");
                assertEquals("PIData", piData, "Documentadoptnode36Assert3");
                piTarget = adoptedPI2.getTarget();
                piData = adoptedPI2.getData();
                assertEquals("PITarget", piTarget, "Documentadoptnode36Assert4");
                assertEquals("PIData", piData, "Documentadoptnode36Assert5");
            }
        }
    }
}
