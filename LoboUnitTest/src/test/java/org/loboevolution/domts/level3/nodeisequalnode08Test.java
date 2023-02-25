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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertTrue;

/**
 * Retreive an element node of this Document having nodeName as employeeId and
 * namespaceURI as http://www.nist.gov.  Create a new Element node having the same attributes
 * in this Document and using isEqualNode check if 2 Element nodes are equal.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 */
public class nodeisequalnode08Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem1;
        Element elem2;
        HTMLCollection employeeList;
        Text text;
        boolean isEqual;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        employeeList = doc.getElementsByTagName("em");
        elem1 = (Element) employeeList.item(0);
        elem2 = doc.createElementNS("http://www.w3.org/1999/xhtml", "em");
        text = doc.createTextNode("EMP0001");
        appendedChild = elem2.appendChild(text);
        isEqual = elem1.isEqualNode(elem2);
        assertTrue("nodeisequalnode08", isEqual);
    }
}

