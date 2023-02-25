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
 * Check implementation of DOMStringList.contains by searching DOMConfig parameter
 * names for "comments" and "".
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-contains">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-contains</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-parameterNames">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-parameterNames</a>
 */
public class domstringlistcontains01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMStringList paramList;
        DOMConfiguration domConfig;
        boolean contains;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        paramList = domConfig.getParameterNames();
        contains = paramList.contains("comments");
        assertTrue("paramsContainComments", contains);
        contains = paramList.contains("");
        assertFalse("paramsDoesntContainEmpty", contains);
    }
}

