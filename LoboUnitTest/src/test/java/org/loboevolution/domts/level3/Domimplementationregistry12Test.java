/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level3;


import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.html.dom.domimpl.DOMImplementationImpl;
import org.loboevolution.html.dom.nodeimpl.bootstrap.DOMImplementationRegistry;
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.http.UserAgentContext;

import static org.junit.jupiter.api.Assertions.*;


/**
 * DOMImplementationRegistry.getDOMImplementation("cOrE 3.0 xMl 3.0 eVeNts 2.0 lS") should return null
 * or a DOMImplementation that implements the specified features.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpl">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpl</a>
 */
public class Domimplementationregistry12Test extends LoboUnitTest {
    @Test
    @SneakyThrows
    public void runTest() {

        final DOMImplementation domImpl;
        final boolean hasCore;
        final boolean hasXML;
        final boolean hasEvents;
        final boolean hasLS;
        final DOMImplementation baseImpl;
        final DOMImplementationRegistry domImplRegistry = DOMImplementationRegistry.newInstance();
        assertNotNull(domImplRegistry, "Domimplementationregistry12Assert1");
        domImpl = domImplRegistry.getDOMImplementation("cOrE 3.0 xMl 3.0 eVeNts 2.0 lS");

        if ((domImpl == null)) {
            baseImpl = new DOMImplementationImpl(new UserAgentContext(new LocalHtmlRendererConfig(), true));
            hasCore = baseImpl.hasFeature("Core", "3.0");
            hasXML = baseImpl.hasFeature("XML", "3.0");
            hasEvents = baseImpl.hasFeature("Events", "2.0");
            hasLS = baseImpl.hasFeature("LS", null);
            assertFalse((hasCore && hasXML && hasEvents && hasLS), "Domimplementationregistry12Assert2");
        } else {
            hasCore = domImpl.hasFeature("Core", "3.0");
            assertTrue(hasCore, "Domimplementationregistry12Assert3");
            hasXML = domImpl.hasFeature("XML", "3.0");
            assertTrue(hasXML, "Domimplementationregistry12Assert4");
            hasEvents = domImpl.hasFeature("Events", "2.0");
            assertTrue(hasEvents, "Domimplementationregistry12Assert5");
            hasLS = domImpl.hasFeature("LS", null);
            assertTrue(hasLS, "Domimplementationregistry12Assert6");
        }

    }
}

