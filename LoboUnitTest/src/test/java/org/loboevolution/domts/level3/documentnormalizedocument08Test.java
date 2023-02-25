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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Add two CDATASections containing "]]>" perform normalization with split-cdata-sections=true.
 * Should result in two warnings and at least 4 nodes.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-split-cdata-sections">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-split-cdata-sections</a>
 */
public class documentnormalizedocument08Test extends LoboUnitTest {
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

        DOMError error;
        int length;
        NodeList childNodes;
        String type;
        int splittedCount = 0;
        int severity;
        doc = sampleXmlFile("barfoo.xml");
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(0);
        newChild = doc.createCDATASection("this is not ]]> good");
        oldChild = elem.getFirstChild();
        retval = elem.replaceChild(newChild, oldChild);
        newChild = doc.createCDATASection("this is not ]]> good");
        retval = elem.appendChild(newChild);
        domConfig = doc.getDomConfig();
        domConfig.setParameter("split-cdata-sections", Boolean.TRUE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        doc.normalizeDocument();
        errors = errorMonitor.getErrors();
        for (int indexN100A3 = 0; indexN100A3 < errors.size(); indexN100A3++) {
            error = errors.get(indexN100A3);
            type = error.getType();
            severity = error.getSeverity();

            if ("cdata-sections-splitted".equals(type)) {
                splittedCount += 1;
            } else {
                assertEquals("anyOthersShouldBeWarnings", 1, severity);
            }

        }
        assertEquals("twoSplittedWarning", 2, splittedCount);
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(0);
        childNodes = elem.getChildNodes();
        length = childNodes.getLength();
        assertTrue("atLeast4ChildNodes", (length > 3));
    }
}

