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
import org.loboevolution.html.dom.DOMError;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.nodeimpl.DOMErrorMonitor;
import org.loboevolution.html.node.*;

import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * Add a CDATASection containing "]]>" and call Node.normalize which should not
 * split or raise warning.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-normalize">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-normalize</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-split-cdata-sections">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-split-cdata-sections</a>
 */
public class splitcdatasections01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem;
        DOMConfiguration domConfig;
        HTMLCollection elemList;
        CDATASection newChild;
        Node oldChild;
        Node retval;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        List<DOMError> errors;

        doc = sampleXmlFile("barfoo.xml");
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(0);
        oldChild = elem.getFirstChild();
        newChild = doc.createCDATASection("this is not ]]> good");
        retval = elem.replaceChild(newChild, oldChild);
        domConfig = doc.getDomConfig();
        domConfig.setParameter("split-cdata-sections", Boolean.FALSE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        doc.normalize();
        assertTrue("noErrors", errorMonitor.assertLowerSeverity(2));
    }
}

