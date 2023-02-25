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
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.DOMStringList;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The length attribute of the DOMStringList returns the number of DOMStrings in the list.
 * The range of valid child node indices is 0 to length-1 inclusive.
 * Invoke the length on the list of parameters returned by the DOMConfiguration object.
 * Verify that the list is not null and length is not 0.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-length">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-length</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-parameterNames">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-parameterNames</a>
 */
public class domstringlistgetlength01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMStringList paramList;
        DOMConfiguration domConfig;
        int listSize;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        paramList = domConfig.getParameterNames();
        assertNotNull("domstringlistgetlength01_notNull", paramList);
        listSize = paramList.getLength();
        assertNotEquals("domstringlistgetlength01_notZero", 0, listSize);
    }
}

