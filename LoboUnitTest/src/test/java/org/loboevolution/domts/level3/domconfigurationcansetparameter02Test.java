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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * The canSetParameter method checks if setting a parameter to a specific value is supported.
 * <p>
 * The parameter cdata-section is turned on by default.  Check to see if this feature can be set
 * to false by invoking canSetParameter method.  Also check that this method does not change the
 * value of parameter by checking if the two cdata-section nodes still exist in the document.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration</a>
 */
public class domconfigurationcansetparameter02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMConfiguration domConfig;
        HTMLCollection strongList;
        NodeList childList;
        Node strongElem;
        Node cdata1;
        Node cdata2;
        int nodeType;
        boolean canSet;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("cdata-sections", Boolean.FALSE);
        assertTrue("domconfigurationcansetparameter02_1", canSet);
        doc.normalizeDocument();
        strongList = doc.getElementsByTagNameNS("*", "strong");
        strongElem = strongList.item(1);
        childList = strongElem.getChildNodes();
        cdata1 = childList.item(1);
        nodeType = cdata1.getNodeType();
        assertEquals("domconfigurationcansetparameter02_2", 4, nodeType);
        cdata2 = childList.item(3);
        nodeType = cdata2.getNodeType();
        assertEquals("domconfigurationcansetparameter02_3", 4, nodeType);
    }
}

