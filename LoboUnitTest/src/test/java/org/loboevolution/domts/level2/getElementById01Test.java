
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;


/**
 * The "getElementById(elementId)" method for a
 * Document should return an element whose ID matches elementId.
 * <p>
 * Invoke method getElementById(elementId) on this document
 * with elementId equals "CANADA".  Method should return an element
 * whose tag name is "emp:address".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-104682815">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-104682815</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=383">http://www.w3.org/Bugs/Public/show_bug.cgi?id=383</a>
 */
public class getElementById01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        String tagname;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.getElementById("CANADA");
        tagname = element.getTagName();
        assertEquals("throw_Equals", "emp:address", tagname);
    }
}

