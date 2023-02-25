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

package org.loboevolution.html.dom.svgimpl;

import java.util.Vector;

/**
 * <p>SVGAnimatedValue class.</p>
 */
public abstract class SVGAnimatedValue {

    public static final short ANIMTYPE_ANGLE = 0;
    public static final short ANIMTYPE_BOOLEAN = 1;
    public static final short ANIMTYPE_ENUMERATION = 2;
    public static final short ANIMTYPE_INTEGER = 3;
    public static final short ANIMTYPE_LENGTH = 4;
    public static final short ANIMTYPE_LENGTHLIST = 5;
    public static final short ANIMTYPE_NUMBER = 6;
    public static final short ANIMTYPE_NUMBERLIST = 7;
    public static final short ANIMTYPE_PRESERVEASPECTRATIO = 8;
    public static final short ANIMTYPE_RECT = 9;
    public static final short ANIMTYPE_STRING = 10;
    public static final short ANIMTYPE_TEXTROTATE = 11;
    public static final short ANIMTYPE_TRANSFORMLIST = 12;

    protected SVGElementImpl owner;
    protected Vector animations;

    public SVGElementImpl getOwner() {
        return owner;
    }

    public void addAnimation(SVGAnimationElementImpl animation) {
        if (animations == null) {
            animations = new Vector();
        }
        animations.addElement(animation);
    }

    public Vector getAnimations() {
        return animations;
    }

}
