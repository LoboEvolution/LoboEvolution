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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * The contains method of the DOMStringList tests if a string is part of this DOMStringList.
 * <p>
 * Invoke the contains method on the list searching for several of the parameters recognized by the
 * DOMConfiguration object.
 * Verify that the list contains features that are required and supported by this DOMConfiguration object.
 * Verify that the contains method returns false for a string that is not contained in this DOMStringList.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-contains">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-contains</a>
 */
public class domstringlistcontains02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMStringList paramList;
        DOMConfiguration domConfig;
        boolean contain;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        paramList = domConfig.getParameterNames();
        contain = paramList.contains("comments");
        assertTrue("domstringlistcontains02_1", contain);
        contain = paramList.contains("cdata-sections");
        assertTrue("domstringlistcontains02_2", contain);
        contain = paramList.contains("entities");
        assertTrue("domstringlistcontains02_3", contain);
        contain = paramList.contains("error-handler");
        assertTrue("domstringlistcontains02_4", contain);
        contain = paramList.contains("infoset");
        assertTrue("domstringlistcontains02_5", contain);
        contain = paramList.contains("namespace-declarations");
        assertTrue("domstringlistcontains02_6", contain);
        contain = paramList.contains("element-content-whitespace");
        assertTrue("domstringlistcontains02_7", contain);
        contain = paramList.contains("test");
        assertFalse("domstringlistcontains02_8", contain);
    }
}

