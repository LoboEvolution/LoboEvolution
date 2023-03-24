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
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.assertTrue;


/**
 * DOMImplementation.hasFeature("XML", "3.0") should return true.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-5CED94D7">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-5CED94D7</a>
 */
public class hasFeature03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        DOMImplementation impl;
        boolean state;
        impl = new DOMImplementationImpl(new UserAgentContext(new LocalHtmlRendererConfig(), true));
        state = impl.hasFeature("+cOrE", "3.0");
        assertTrue("hasPlusCore30", state);
    }
}

