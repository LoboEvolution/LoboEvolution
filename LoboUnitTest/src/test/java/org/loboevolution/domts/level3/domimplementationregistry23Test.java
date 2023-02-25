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
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.dom.nodeimpl.bootstrap.DOMImplementationRegistry;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.DOMImplementationList;
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.*;


/**
 * DOMImplementationRegistry.getDOMImplementationList("cOrE 3.0 xMl 3.0 eVeNts 2.0 lS")
 * should return an empty list or a list of DOMImplementation that implements the specified features.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls</a>
 */
public class domimplementationregistry23Test extends LoboUnitTest {
    @Test
    @SneakyThrows
    public void runTest() {
       
        DOMImplementation domImpl;
        boolean hasCore;
        boolean hasXML;
        boolean hasEvents;
        boolean hasLS;
        DOMImplementation baseImpl;
        String nullVersion = null;

        DOMImplementationList domImplList;
        int length;
         DOMImplementationRegistry domImplRegistry = DOMImplementationRegistry.newInstance();
        assertNotNull("domImplRegistryNotNull", domImplRegistry);
        domImplList = domImplRegistry.getDOMImplementationList("cOrE 3.0 xMl 3.0 eVeNts 2.0 lS");
        length = (int) domImplList.getLength();

        if (length == 0) {
            baseImpl = new DOMImplementationImpl(new UserAgentContext(true));
            hasCore = baseImpl.hasFeature("Core", "3.0");
            hasXML = baseImpl.hasFeature("XML", "3.0");
            hasEvents = baseImpl.hasFeature("Events", "2.0");
            hasLS = baseImpl.hasFeature("LS", nullVersion);
            assertFalse("baseImplFeatures",
                    (hasCore & hasXML & hasEvents & hasLS)
            );
        } else {
            for (int indexN10096 = 0; indexN10096 < domImplList.getLength(); indexN10096++) {
                domImpl = (DOMImplementation) domImplList.item(indexN10096);
                hasCore = domImpl.hasFeature("Core", "3.0");
                assertTrue("hasCore", hasCore);
                hasXML = domImpl.hasFeature("XML", "3.0");
                assertTrue("hasXML", hasXML);
                hasEvents = domImpl.hasFeature("Events", "2.0");
                assertTrue("hasEvents", hasEvents);
                hasLS = domImpl.hasFeature("LS", nullVersion);
                assertTrue("hasLS", hasLS);
            }
        }

    }
}

