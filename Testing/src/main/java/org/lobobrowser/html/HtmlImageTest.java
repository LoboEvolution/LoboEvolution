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

public class HtmlImageTest extends SimpleLoboTest {
	
	@Test
	public void basicTest() throws Exception {
        String htmlSource
            = "<html><head><title>foo</title></head><body>"
            + "<img id='myId' src='foo.png' usemap='#map1'>"
            + "<map name='map1'>"
            + "<area href='a.html' shape='rect' coords='5,5,20,20'>"
            + "<area href='b.html' shape='circle' coords='25,10,10%'>"
            + "</map>"
            + "</body></html>";
        
        HTMLDocumentImpl doc = loadPage(htmlSource);
        assertTrue(IMG.equals(doc.getElementById("myId").getNodeName()));
    }
}
