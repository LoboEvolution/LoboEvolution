/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.html.dom.DOMErrorHandler;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.http.UserAgentContext;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Calls DOMConfiguration.setParameter("error-handler", null).  Spec
 * does not explicitly address the case.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-error-handler">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-error-handler</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter</a>
 */
public class Domconfigerrorhandler2Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final DOMImplementation domImpl;
        final Document doc;
        final DOMConfiguration domConfig;
        final DocumentType nullDocType = null;

        final boolean canSet;
        final DOMErrorHandler errorHandler = null;

        final String parameter = "error-handler";
        final DOMErrorHandler state;
        domImpl = new DOMImplementationImpl(new UserAgentContext(new LocalHtmlRendererConfig(), true));
        doc = domImpl.createDocument("http://www.w3.org/1999/xhtml", "html", nullDocType);
        domConfig = doc.getDomConfig();
        /*DOMErrorHandler */
        canSet = domConfig.canSetParameter(parameter, errorHandler);
        assertTrue(canSet, "Domconfigerrorhandler2Assert3");
        /*DOMErrorHandler */
        domConfig.setParameter(parameter, errorHandler);
        state = (DOMErrorHandler) domConfig.getParameter(parameter);
        assertNull(state, "Domconfigerrorhandler2Assert4");
    }
}

