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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Attempt to replace a text node with a text node from an
 * entity reference. Since the replacing text node should be removed
 * from its current location first, a NO_MODIFICATION_ALLOWED_ERR should
 * be thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild28Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection childList;
        Element acronym;
        EntityReference betaRef;
        Text dallas;
        Node betaText;
        Node appendedChild;
        Node replacedChild;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("acronym");
        acronym = (Element) childList.item(1);
        betaRef = (EntityReference) acronym.getFirstChild();
        assertNotNull("betaRefNotNull", betaRef);
        betaText = betaRef.getFirstChild();
        assertNotNull("betaTextNotNull", betaText);
        dallas = (Text) betaRef.getNextSibling();
        assertNotNull("dallasNotNull", dallas);

        {
            boolean success = false;
            try {
                replacedChild = acronym.replaceChild(betaText, dallas);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR", success);
        }
    }
}

