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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Using insertBefore on an Element node attempt to insert the parent Element node, before
 * an EntityReference or Text child and verify if a HIERARCHY_REQUEST_ERR is raised.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class Nodeinsertbefore21Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Element element;
        final Node refNode;
        final Element newNode;
        final HTMLCollection childList;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("var");
        element = (Element) childList.item(2);
        refNode = element.getFirstChild();
        newNode = (Element) element.getParentNode();

        {
            boolean success = false;
            try {
                element.insertBefore(newNode, refNode);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.HIERARCHY_REQUEST_ERR);
            }
            assertTrue(success, "Nodeinsertbefore21Assert2");
        }
    }
}

