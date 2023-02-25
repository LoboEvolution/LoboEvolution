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
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.*;


/**
 * DOMImplementationRegistry.getDOMImplementation("cOrE 3.0 xMl 3.0 eVeNts 2.0 lS") should return null
 * or a DOMImplementation that implements the specified features.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpl">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpl</a>
 */
public class domimplementationregistry12Test extends LoboUnitTest {
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

         DOMImplementationRegistry domImplRegistry = DOMImplementationRegistry.newInstance();
        assertNotNull("domImplRegistryNotNull", domImplRegistry);
        domImpl = domImplRegistry.getDOMImplementation("cOrE 3.0 xMl 3.0 eVeNts 2.0 lS");

        if ((domImpl == null)) {
            baseImpl = new DOMImplementationImpl(new UserAgentContext(true));
            hasCore = baseImpl.hasFeature("Core", "3.0");
            hasXML = baseImpl.hasFeature("XML", "3.0");
            hasEvents = baseImpl.hasFeature("Events", "2.0");
            hasLS = baseImpl.hasFeature("LS", nullVersion);
            assertFalse("baseImplFeatures",
                    (hasCore & hasXML & hasEvents & hasLS)
            );
        } else {
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

