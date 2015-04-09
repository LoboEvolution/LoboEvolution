/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.control;

import java.awt.Graphics;

import org.lobobrowser.html.domimpl.DomCanvasImpl;
import org.lobobrowser.html.domimpl.HTMLCanvasElementImpl;
import org.lobobrowser.html.w3c.CanvasRenderingContext;
import org.lobobrowser.html.w3c.CanvasRenderingContext2D;

/**
 * The Class CanvasControl.
 */
public class CanvasControl extends BaseControl{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The width. */
    private int width;

    /** The height. */
    private int height;

    private DomCanvasImpl context;

    public CanvasControl(HTMLCanvasElementImpl modelNode) {
        super(modelNode);
        width = modelNode.getWidth();
        height = modelNode.getHeight();
        
        System.out.println(width);
        System.out.println(height);
        
        System.out.println("controll");
        //context = (DomCanvasImpl)modelNode.getContext("2d");
        
        //System.out.println("context: " + context);
        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(0, 0, new Integer(width), new Integer(height));
        
        System.out.println("paint");
        
        //context.fillRect(0, 0, 0, 0);
        
        //System.out.println("method: " + context.getMethod());     
    }
}
