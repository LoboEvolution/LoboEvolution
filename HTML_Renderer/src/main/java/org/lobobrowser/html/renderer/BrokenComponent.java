/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * The Class BrokenComponent.
 */
public class BrokenComponent extends Component {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     * @see java.awt.Component#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10, 10);
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#update(java.awt.Graphics)
     */
    @Override
    public void update(Graphics g) {
        this.paint(g);
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        Dimension size = this.getSize();
        g.drawRect(0, 0, size.width, size.height);
        g.drawLine(0, 0, size.width - 1, size.height - 1);
        g.drawLine(size.width - 1, 0, 0, size.height - 1);
    }
}
