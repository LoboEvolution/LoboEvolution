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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * The method replaceWholeText substitutes the a specified text for the text of
 * the current node and all logically-adjacent text nodes.  This method raises
 * a NO_MODIFICATION_ALLOWED_ERR if one of the Text nodes being replaced is readonly.
 * <p>
 * Invoke replaceWholeText on an existing text node with newly created text and Entityreference
 * nodes (whose replacement text is a character entity reference) appended as children of its parent element node.
 * Where the nodes to be removed are read-only descendants of an EntityReference, the EntityReference
 * must be removed instead of the read-only nodes. Only if any EntityReference to be removed has
 * descendants that are not EntityReference, Text, or CDATASection nodes, the replaceWholeText
 * method must fail, raising a NO_MODIFICATION_ALLOWED_ERR. Verify that the method does not raise
 * an exception and verify the content of the returned text node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText</a>
 */
public class textreplacewholetext06Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection itemList;
        Element elementStrong;
        Text textNode;
        EntityReference erefNode;
        Text replacedText;
        Node appendedChild;
        String nodeValue;
        doc = sampleXmlFile("hc_staff.xml");
        itemList = doc.getElementsByTagName("strong");
        elementStrong = (Element) itemList.item(0);
        textNode = doc.createTextNode("New Text");
        erefNode = doc.createEntityReference("beta");
        appendedChild = elementStrong.appendChild(textNode);
        appendedChild = elementStrong.appendChild(erefNode);
        textNode = (Text) elementStrong.getFirstChild();
        replacedText = textNode.replaceWholeText("New Text and Cdata");
        nodeValue = textNode.getNodeValue();
        assertEquals("textreplacewholetext06", "New Text and Cdata", nodeValue);
    }
}

