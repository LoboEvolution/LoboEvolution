/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.html.dom;

/**
 * The Interface DOMMatrixReadOnly.
 */
public interface DOMMatrixReadOnly {

    Double getA();

    Double getB();

    Double getC();

    Double getD();

    Double getE();

    Double getF();

    Boolean getIs2D();

    Boolean getIsIdentity();

    Double getM11();

    Double getM12();

    Double getM13();

    Double getM14();

    Double getM21();

    Double getM22();

    Double getM23();

    Double getM24();

    Double getM31();

    Double getM32();

    Double getM33();

    Double getM34();

    Double getM41();

    Double getM42();

    Double getM43();

    Double getM44();

    DOMMatrix flipX();

    DOMMatrix flipY();

    DOMMatrix inverse();

    DOMMatrix multiply();

    DOMMatrix multiply(DOMMatrixInit other);

    DOMMatrix rotate();

    DOMMatrix rotate(Double rotX);

    DOMMatrix rotate(Double rotX, Double rotY);

    DOMMatrix rotate(Double rotX, Double rotY, Double rotZ);

    DOMMatrix rotateAxisAngle();

    DOMMatrix rotateAxisAngle(Double x);

    DOMMatrix rotateAxisAngle(Double x, Double y);

    DOMMatrix rotateAxisAngle(Double x, Double y, Double z);

    DOMMatrix rotateAxisAngle(Double x, Double y, Double z, Double angle);

    DOMMatrix rotateFromVector();

    DOMMatrix rotateFromVector(Double x);

    DOMMatrix rotateFromVector(Double x, Double y);

    DOMMatrix scale();

    DOMMatrix scale(Double scaleX);

    DOMMatrix scale(Double scaleX, Double scaleY);

    DOMMatrix scale(Double scaleX, Double scaleY, Double scaleZ);

    DOMMatrix scale(Double scaleX, Double scaleY, Double scaleZ, Double originX);

    DOMMatrix scale(Double scaleX, Double scaleY, Double scaleZ, Double originX, Double originY);

    DOMMatrix scale(Double scaleX, Double scaleY, Double scaleZ, Double originX, Double originY, Double originZ);

    DOMMatrix scale3d();

    DOMMatrix scale3d(Double scale);

    DOMMatrix scale3d(Double scale, Double originX);

    DOMMatrix scale3d(Double scale, Double originX, Double originY);

    DOMMatrix scale3d(Double scale, Double originX, Double originY, Double originZ);

    DOMMatrix skewX();

    DOMMatrix skewX(Double sx);


    DOMMatrix skewY(Double sy);


    Object toJSON();

    DOMPoint transformPoint();

    DOMPoint transformPoint(DOMPointInit point);

    DOMMatrix translate();

    DOMMatrix translate(Double tx);

    DOMMatrix translate(Double tx, Double ty);

    DOMMatrix translate(Double tx, Double ty, Double tz);
}
