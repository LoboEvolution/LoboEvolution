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
import static org.junit.Assert.assertTrue;


/**
 * The normalizeDocument method method acts as if the document was going through a save
 * and load cycle, putting the document in a "normal" form.
 * Create an Element and a text node and verify the nodeValue of this text node and append these to
 * this Document.  If supported, invoke the setParameter method on this domconfiguration object to set the
 * "element-content-whitespace"  feature to false.  Invoke the normalizeDocument method and verify if
 * the text node has been discarded.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-element-content-whitespace">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-element-content-whitespace</a>
 */
public class documentnormalizedocument10Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem;
        Text newText;
        Text text;
        String nodeValue;
        boolean canSet;
        DOMConfiguration domConfig;
        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.createElement("newElem");
        newText = doc.createTextNode("Text          Node");
        elem.appendChild(newText);
        doc.appendChild(elem);
        text = (Text) elem.getFirstChild();
        nodeValue = text.getNodeValue();
        assertEquals("documentnormalizedocument10", "Text          Node", nodeValue);
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("element-content-whitespace", Boolean.TRUE);
        assertTrue("canSetElementContentWhitespaceTrue", canSet);
        domConfig.setParameter("element-content-whitespace", Boolean.TRUE);
        doc.normalizeDocument();
        text = (Text) elem.getFirstChild();
        nodeValue = text.getNodeValue();
        assertEquals("documentnormalizedocument10_true1", "Text          Node", nodeValue);
        canSet = domConfig.canSetParameter("element-content-whitespace", Boolean.FALSE);

        if (canSet) {
            domConfig.setParameter("element-content-whitespace", Boolean.FALSE);
            doc.normalizeDocument();
            text = (Text) elem.getFirstChild();
            nodeValue = text.getNodeValue();
            assertEquals("documentnormalizedocument10_true2", "Text Node", nodeValue);
        }
    }
}

