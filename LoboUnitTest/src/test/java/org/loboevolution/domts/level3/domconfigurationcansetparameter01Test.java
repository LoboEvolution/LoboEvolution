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
 * The parameter commments is turned on by default.  Check to see if this feature can be set
 * to false by invoking canSetParameter method.  Also check that this method does not change the
 * value of parameter.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-canSetParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-canSetParameter</a>
 */
public class domconfigurationcansetparameter01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMConfiguration domConfig;
        boolean canSet;
        Comment newCommentNode;
        Element docElem;
        Node appendedChild;
        Node lastChild;
        String commentValue;
        doc = sampleXmlFile("hc_staff.xml");
        newCommentNode = doc.createComment("This is a new Comment node");
        docElem = doc.getDocumentElement();
        appendedChild = docElem.appendChild(newCommentNode);
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("comments", Boolean.FALSE);
        assertTrue("domconfigurationcansetparameter01", canSet);
        doc.normalizeDocument();
        lastChild = docElem.getLastChild();
        commentValue = lastChild.getNodeValue();
        assertEquals("domconfigurationsetparameter02_2", "This is a new Comment node", commentValue);
    }
}

