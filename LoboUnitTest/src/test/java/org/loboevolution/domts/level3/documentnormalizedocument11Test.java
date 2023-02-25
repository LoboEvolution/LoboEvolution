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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The normalizeDocument method method acts as if the document was going through a save
 * and load cycle, putting the document in a "normal" form.
 * The feature namespace-declarations when set to false, discards all namespace declaration attributes,
 * although namespace prefixes are still retained.
 * <p>
 * Set the normalization feature "namespace-declarations" to false, invoke normalizeDocument and verify
 * the nodeName of element acquired by tagname.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespace-declarations">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespace-declarations</a>
 */
public class documentnormalizedocument11Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element elemName;
        String nodeName;
        boolean canSet;
        DOMConfiguration domConfig;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        domConfig.setParameter("namespace-declarations", Boolean.TRUE);
        doc.normalizeDocument();
        elemList = doc.getElementsByTagNameNS("*", "acronym");
        elemName = (Element) elemList.item(1);
        assertNotNull("documentnormalizedocument11_NotNullElem", elemName);
        canSet = domConfig.canSetParameter("namespace-declarations", Boolean.FALSE);

        if (canSet) {
            domConfig.setParameter("namespace-declarations", Boolean.FALSE);
            doc.normalizeDocument();
            elemList = doc.getElementsByTagNameNS("*", "acronym");
            elemName = (Element) elemList.item(1);
            nodeName = elemName.getNodeName();
            assertEquals("documentnormalizedocument11_namespaceDeclarations", "address", nodeName);
        }
    }
}

