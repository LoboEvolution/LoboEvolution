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
 * Check implementation of DOMStringList.item by accessing items 0 and length-1 and expecting
 * a string and accessing items out of range and expecting null.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-item">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-item</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-parameterNames">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-parameterNames</a>
 */
public class domstringlistitem01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMStringList paramList;
        DOMConfiguration domConfig;
        boolean contains;
        int length;
        int index;
        String parameter;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        paramList = domConfig.getParameterNames();
        length = paramList.getLength();
        parameter = paramList.item(0);
        assertNotNull("item0NotNull", parameter);
        /*int */
        parameter = paramList.item(length);
        assertNull("itemLengthNull", parameter);
        length -= 1;
        /*int */
        parameter = paramList.item(length);
        assertNotNull("itemLengthMinus1NotNull", parameter);
    }
}

