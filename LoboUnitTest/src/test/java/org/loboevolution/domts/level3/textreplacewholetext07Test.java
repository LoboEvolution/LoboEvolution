/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Append an entity reference and a text node after to the content of the
 * first strong element.  Then call replaceWholeText on initial content
 * of that element.  Since the entity reference does not contain any
 * logically-adjacent text content, only the initial text element should
 * be replaced.
 *
 * @author IBM
 * @author Neil Delima
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=425">http://www.w3.org/Bugs/Public/show_bug.cgi?id=425</a>
 */
public class textreplacewholetext07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection itemList;
        final Element elementName;
        Text textNode;
        final EntityReference erefNode;
        final Text replacedText;
        Node appendedChild;
        final Node node;
        final String nodeValue;
        final int nodeType;
        doc = sampleXmlFile("hc_staff.xml");
        itemList = doc.getElementsByTagName("strong");
        elementName = (Element) itemList.item(0);
        erefNode = doc.createEntityReference("ent4");
        textNode = doc.createTextNode("New Text");
        appendedChild = elementName.appendChild(erefNode);
        appendedChild = elementName.appendChild(textNode);
        textNode = (Text) elementName.getFirstChild();
        replacedText = textNode.replaceWholeText("New Text and Cdata");
        textNode = (Text) elementName.getFirstChild();
        assertSame(textNode, replacedText);
        nodeValue = textNode.getNodeValue();
        assertEquals("New Text and Cdata", nodeValue);
        node = textNode.getNextSibling();
        assertNotNull(node);
        nodeType = node.getNodeType();
        assertEquals(5, nodeType);
    }
}

