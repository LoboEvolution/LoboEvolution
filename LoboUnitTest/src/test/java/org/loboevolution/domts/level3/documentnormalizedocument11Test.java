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
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The normalizeDocument method acts as if the document was going through a save
 * and load cycle, putting the document in a "normal" form.
 * The feature namespace-declarations when set to false, discards all namespace declaration attributes,
 * although namespace prefixes are still retained.
 * <p>
 * Set the normalization feature "namespace-declarations" to false, invoke normalizeDocument and verify
 * the nodeName of element acquired by tagname.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespace-declarations">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespace-declarations</a>
 */
public class documentnormalizedocument11Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element elemName;
        String nodeName;
        boolean canSet;
        DOMConfiguration domConfig;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        domConfig.setParameter("namespace-declarations", Boolean.TRUE);
        doc.normalizeDocument();
        elemList = doc.getElementsByTagNameNS("*", "acronym");
        elemName = (Element) elemList.item(1);
        assertNotNull("documentnormalizedocument11_NotNullElem", elemName);
        canSet = domConfig.canSetParameter("namespace-declarations", Boolean.FALSE);

        if (canSet) {
            domConfig.setParameter("namespace-declarations", Boolean.FALSE);
            doc.normalizeDocument();
            elemList = doc.getElementsByTagNameNS("*", "acronym");
            elemName = (Element) elemList.item(1);
            nodeName = elemName.getNodeName();
            assertEquals("documentnormalizedocument11_namespaceDeclarations", "address", nodeName);
        }
    }
}

