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
import org.loboevolution.html.node.Document;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The method getParameter returns the value of a parameter if known.
 * <p>
 * Get the DOMConfiguration object of a document and verify that the default required features are set
 * to true.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter</a>
 */
public class domconfigurationgetparameter01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMConfiguration domConfig;
        Object param;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        param = domConfig.getParameter("comments");
        assertNotNull("domconfigurationgetparameter01_1", param);
        param = domConfig.getParameter("cdata-sections");
        assertNotNull("domconfigurationgetparameter01_2", param);
        param = domConfig.getParameter("entities");
        assertNotNull("domconfigurationgetparameter01_3", param);
        param = domConfig.getParameter("namespace-declarations");
        assertNotNull("domconfigurationgetparameter01_4", param);
        param = domConfig.getParameter("infoset");
        assertNull("domconfigurationgetparameter01_5", param);
    }
}

