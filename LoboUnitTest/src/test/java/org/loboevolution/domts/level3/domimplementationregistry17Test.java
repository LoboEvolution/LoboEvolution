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


import lombok.SneakyThrows;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.nodeimpl.bootstrap.DOMImplementationRegistry;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.DOMImplementationList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * If the implementation supports "XML", DOMImplementationRegistry.getDOMImplementationList("xMl 3.0 cOrE") should
 * return a list of DOMImplementation where hasFeature("XML", "3.0"), and hasFeature("Core", null) returns true.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls</a>
 */
public class domimplementationregistry17Test extends LoboUnitTest {
    @Test
    @SneakyThrows
    public void runTest() throws Exception {
       
        DOMImplementation domImpl;
        boolean hasFeature;
        String nullVersion = null;

        DOMImplementationList domImplList;
        int length;
         DOMImplementationRegistry domImplRegistry = DOMImplementationRegistry.newInstance();
        assertNotNull("domImplRegistryNotNull", domImplRegistry);
        domImplList = domImplRegistry.getDOMImplementationList("xMl 3.0 cOrE");
        length = domImplList.getLength();
        assertTrue("atLeastOne", (length > 0));
        for (int indexN1005A = 0; indexN1005A < domImplList.getLength(); indexN1005A++) {
            domImpl = (DOMImplementation) domImplList.item(indexN1005A);
            hasFeature = domImpl.hasFeature("XML", "3.0");
            assertTrue("hasXML3", hasFeature);
            hasFeature = domImpl.hasFeature("Core", nullVersion);
            assertTrue("hasCore", hasFeature);
        }
    }
}

