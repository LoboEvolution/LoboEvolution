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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The item method of the DOMStringList Returns the indexth item in the collection.
 * If index is greater than or equal to the number of DOMStrings in the list, this returns null.
 * <p>
 * Invoke the first item on the list of parameters returned by the DOMConfiguration object and
 * make sure it is not null.  Then invoke the 100th item and verify that null is returned.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-item">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-item</a>
 */
public class domstringlistitem02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMStringList paramList;
        DOMConfiguration domConfig;
        int listSize;
        String retStr;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        paramList = domConfig.getParameterNames();
        retStr = paramList.item(0);
        assertNotNull("domstringlistitem02_notNull", retStr);
        retStr = paramList.item(100);
        assertNull("domstringlistitem02_null", retStr);
    }
}

