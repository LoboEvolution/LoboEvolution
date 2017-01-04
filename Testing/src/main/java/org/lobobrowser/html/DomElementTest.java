/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;

public final class DomElementTest extends SimpleLoboTest {

    @Test
    public void getElementsByTagName() throws Exception {
        String htmlSource = "<html><head><script>"
                + "function test() {"
                + "    alert(document.f1.getElementsByTagName('input').length);"
                + "    alert(document.f1.getElementsByTagName('INPUT').length);"
                + "}"
                + "</script></head>"
                + "<body >"
                + "  <form name='f1'>"
                + "    <input>"
                + "    <INPUT>"
                + "  </form>"
                + "</body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);
        assertEquals(2, page.getElementsByTagName("input").getLength());
        assertEquals(2, page.getElementsByTagName("INPUT").getLength());
    }
}
