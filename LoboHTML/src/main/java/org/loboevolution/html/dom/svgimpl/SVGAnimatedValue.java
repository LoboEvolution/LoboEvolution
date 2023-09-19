/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
