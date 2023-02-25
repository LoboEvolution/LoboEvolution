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
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertFalse;


/**
 * Invoke setUserData on a CDATASection and EntityReference node to set their
 * UserData to this Document and DocumentElement node.  Verify if the UserData
 * object that was set for both nodes is different.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData</a>
 */
public class nodesetuserdata10Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        Node entRef;
        CDATASection cData;
        HTMLCollection varList;
        Element varElem;
        Object userData;
        Object returned1;
        Object returned2;
        boolean success;
        Object retUserData;

        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        varList = doc.getElementsByTagName("var");
        varElem = (Element) varList.item(2);
        entRef = varElem.getFirstChild();
        cData = doc.createCDATASection("CDATASection");
        /*Node */
        retUserData = entRef.setUserData("Key1", doc, null);
        /*Node */
        retUserData = cData.setUserData("Key2", docElem, null);
        returned1 = entRef.getUserData("Key1");
        returned2 = cData.getUserData("Key2");
        success = ((Node) /*DOMUserData */returned1).isEqualNode(((Node) /*DOMUserData */returned2));
        assertFalse("nodesetuserdata08", success);
    }
}

