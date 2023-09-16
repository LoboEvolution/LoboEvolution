/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

