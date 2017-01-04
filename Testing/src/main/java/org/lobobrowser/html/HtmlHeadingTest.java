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

public class HtmlHeadingTest extends SimpleLoboTest {

    @Test
    public void asText() throws Exception {
        String htmlSource = "<html><head>"
            + "</head><body>"
            + "begin"
            + "<h1>in h1</h1>after h1"
            + "<h2>in h2</h2>after h2"
            + "<h3>in h3</h3>after h3"
            + "<h4>in h4</h4>after h4"
            + "<h5>in h5</h5>after h5"
            + "<h6>in h6</h6>after h6"
            + "</body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);
        final String expectedText = "begin" + SEPARATOR_LINE
            + "in h1" + SEPARATOR_LINE
            + "after h1" + SEPARATOR_LINE
            + "in h2" + SEPARATOR_LINE
            + "after h2" + SEPARATOR_LINE
            + "in h3" + SEPARATOR_LINE
            + "after h3" + SEPARATOR_LINE
            + "in h4" + SEPARATOR_LINE
            + "after h4" + SEPARATOR_LINE
            + "in h5" + SEPARATOR_LINE
            + "after h5" + SEPARATOR_LINE
            + "in h6" + SEPARATOR_LINE
            + "after h6";

        assertEquals(expectedText, page.getTextContent());
    }
}
