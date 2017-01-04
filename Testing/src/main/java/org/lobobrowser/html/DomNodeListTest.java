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
import org.w3c.dom.NodeList;

public class DomNodeListTest extends SimpleLoboTest {

    @Test
    public void getElementsByTagName() throws Exception {
        final String firstContent
            = "<html><head><title>First</title></head>"
            + "<body>"
            + "<form><input type='button' name='button1' value='pushme'></form>"
            + "<div>a</div> <div>b</div> <div>c</div>"
            + "</body></html>";

        HTMLDocumentImpl page = loadPage(firstContent);
        NodeList divs = page.getElementsByTagName("div");
        assertEquals(3, divs.getLength());
    }
}
