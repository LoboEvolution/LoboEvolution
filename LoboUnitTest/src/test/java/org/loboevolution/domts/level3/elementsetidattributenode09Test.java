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

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;

/**
 * This method declares the attribute specified by node to be of type ID. If the value of the specified attribute
 * is unique then this element node can later be retrieved using getElementById on Document. Note, however,
 * that this simply affects this node and does not change any grammar that may be in use.
 * <p>
 * Invoke setIdAttributeNode with the xmlns attribute of ent4.  Verify that NO_MODIFICATION_ALLOWED_ERR is raised.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode</a>
 */
public class elementsetidattributenode09Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element varElem;
        EntityReference entRef;
        Element entElement;
        NamedNodeMap attributesMap;
        Attr attr;
        DOMConfiguration domConfig;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        domConfig.setParameter("entities", Boolean.TRUE);
        doc.normalizeDocument();
        elemList = doc.getElementsByTagNameNS("*", "var");
        varElem = (Element) elemList.item(2);
        entRef = (EntityReference) varElem.getFirstChild();
        entElement = (Element) entRef.getFirstChild();
        attributesMap = entElement.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xmlns");

        {
            boolean success = false;
            try {
                entElement.setIdAttributeNode(attr, true);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR", success);
        }
    }
}

