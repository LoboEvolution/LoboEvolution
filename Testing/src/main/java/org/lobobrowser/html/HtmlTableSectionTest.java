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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;

public class HtmlTableSectionTest extends SimpleLoboTest {

    @Test
    public void asText() throws Exception {
        String htmlSource = "<html>"
            + "<body>"
            + "  <table>"
            + "    <tfoot><td>Five</td></tfoot>"
            + "    <tbody><td>Two</td></tbody>"
            + "    <thead><td>One</td></thead>"
            + "    <thead><td>Three</td></thead>"
            + "    <tfoot><td>Four</td></tfoot>"
            + "  </table>"
            + "</body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);
        assertEquals("One" + SEPARATOR_LINE + "Two" + SEPARATOR_LINE + "Three" + SEPARATOR_LINE
                + "Four" + SEPARATOR_LINE + "Five", page.getTextContent());
    }
}
