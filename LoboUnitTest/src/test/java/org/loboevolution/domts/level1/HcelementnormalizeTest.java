
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

package org.loboevolution.domts.level1;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.Text;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Append a couple of text nodes to the first sup element, normalize the
 * document element and check that the element has been normalized.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-162CF083">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-162CF083</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=546">http://www.w3.org/Bugs/Public/show_bug.cgi?id=546</a>
 */
public class HcelementnormalizeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Element root;
        HTMLCollection elementList;
        Element testName;
        final Node firstChild;
        final String childValue;
        Text textNode;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("sup");
        testName = (Element) elementList.item(0);
        textNode = doc.createTextNode("");
        testName.appendChild(textNode);
        textNode = doc.createTextNode(",000");
        testName.appendChild(textNode);
        root = doc.getDocumentElement();
        root.normalize();
        elementList = doc.getElementsByTagName("sup");
        testName = (Element) elementList.item(0);
        firstChild = testName.getFirstChild();
        childValue = firstChild.getNodeValue();
        assertEquals("56,000,000", childValue, "HcelementnormalizeAssert1");
    }
}

