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
 * DOMImplementationRegistry.getDOMImplementation("SVG") should return null or a DOMImplementation
 * where hasFeature("SVG", null) returns true.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpl">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpl</a>
 */
public class domimplementationregistry08Test extends LoboUnitTest {
    @SneakyThrows
    @Test
    public void runTest() {
        DOMImplementation domImpl;
        boolean hasFeature;
        String nullVersion = null;

        DOMImplementationRegistry domImplRegistry = DOMImplementationRegistry.newInstance();
        assertNotNull("domImplRegistryNotNull", domImplRegistry);
        domImpl = domImplRegistry.getDOMImplementation("SVG");

        if ((domImpl == null)) {
            DOMImplementationImpl baseImpl = new DOMImplementationImpl(new UserAgentContext(true));
            hasFeature = baseImpl.hasFeature("SVG", null);
            assertFalse("baseImplSupportsSVG", hasFeature);
        } else {
            hasFeature = domImpl.hasFeature("SVG", nullVersion);
            assertTrue("hasCore", hasFeature);
        }

    }
}

