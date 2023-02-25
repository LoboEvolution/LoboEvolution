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
import org.loboevolution.html.dom.DOMLocator;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.nodeimpl.DOMErrorMonitor;
import org.loboevolution.html.node.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


/**
 * Normalize document with check-character-normalization set to true, check that
 * non-normalized characters are signaled.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-check-character-normalization">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-check-character-normalization</a>
 * @see <a href="http://www.w3.org/TR/2003/WD-charmod-20030822/">http://www.w3.org/TR/2003/WD-charmod-20030822/</a>
 */
public class checkcharacternormalization02Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        DOMConfiguration domConfig;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        HTMLCollection pList;
        Element pElem;
        Text text;
        String textValue;
        Node retval;
        boolean canSet;
        List<DOMError> errors;

        DOMError error;
        int severity;
        DOMLocator locator;
        Node relatedNode;
        int errorCount = 0;
        String errorType;
        doc = sampleXmlFile("barfoo.xml");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("check-character-normalization", Boolean.TRUE);

        if (canSet) {
            domConfig.setParameter("check-character-normalization", Boolean.TRUE);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            pList = doc.getElementsByTagName("p");
            pElem = (Element) pList.item(0);
            text = doc.createTextNode("suçon");
            retval = pElem.appendChild(text);
            doc.normalizeDocument();
            errors = errorMonitor.getErrors();
            for (int indexN100AA = 0; indexN100AA < errors.size(); indexN100AA++) {
                error = (DOMError) errors.get(indexN100AA);
                severity = error.getSeverity();

                if (severity == 2) {
                    errorCount += 1;
                    errorType = error.getType();
                    assertEquals("errorType", "check-character-normalization-failure", errorType);
                    locator = error.getLocation();
                    relatedNode = locator.getRelatedNode();
                    assertSame("relatedNodeSame", text, relatedNode);
                }
            }
            assertEquals("oneError", 1, errorCount);
        }
    }
}

