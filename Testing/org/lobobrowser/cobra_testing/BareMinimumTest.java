/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project This
 * library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.cobra_testing;

import javax.swing.*;

import org.lobobrowser.html.gui.*;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.test.*;

/**
 * The Class BareMinimumTest.
 */
public class BareMinimumTest {
    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame();
        HtmlPanel panel = new HtmlPanel();

        panel.setDefaultOverflowX(RenderState.OVERFLOW_HIDDEN);
        panel.setDefaultOverflowY(RenderState.OVERFLOW_HIDDEN);

        window.getContentPane().add(panel);
        window.setSize(400, 400);
        window.setVisible(true);
        new SimpleHtmlRendererContext(panel, new SimpleUserAgentContext())
                .navigate("http://lobobrowser.org/browser/home.jsp");
    }
}
