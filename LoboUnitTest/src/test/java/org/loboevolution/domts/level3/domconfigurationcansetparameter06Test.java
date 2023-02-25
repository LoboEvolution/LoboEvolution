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
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertTrue;


/**
 * The canSetParameter method checks if setting a parameter to a specific value is supported.
 * <p>
 * The parameter element-content-whitespace is turned on by default. Set this parameter to false will
 * discard all Text nodes that contain whitespaces in element content, as described in [element content whitespace].
 * Check to see if this feature can be set to false by invoking canSetParameter method.  Verify that the text node
 * still exist after invoking canSetParameter.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration</a>
 */
public class domconfigurationcansetparameter06Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMConfiguration domConfig;
        HTMLCollection itemList;
        Element elementStrong;
        Text textNode;
        boolean canSet;
        boolean hasWhitespace;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("element-content-whitespace", Boolean.TRUE);
        assertTrue("domconfigurationcansetparameter06_1", canSet);
        itemList = doc.getElementsByTagNameNS("*", "strong");
        elementStrong = (Element) itemList.item(0);
        textNode = (Text) elementStrong.getFirstChild();
        textNode.setTextContent("                                                ");
        hasWhitespace = textNode.isElementContentWhitespace();
        assertTrue("domconfigurationsetparameter06_2", hasWhitespace);
        doc.normalizeDocument();
        itemList = doc.getElementsByTagNameNS("*", "strong");
        elementStrong = (Element) itemList.item(0);
        textNode = (Text) elementStrong.getFirstChild();
        hasWhitespace = textNode.isElementContentWhitespace();
        assertTrue("domconfigurationsetparameter06_3", hasWhitespace);
    }
}

