/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
 * The DOMMatrix interface represents 4&times;4 matrices, suitable for 2D and 3D operations including rotation and translation. It is a mutable version of the DOMMatrixReadOnly interface.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMMatrix">DOMMatrix - MDN</a>
 * @see <a href="https://drafts.fxtf.org/geometry/#DOMMatrix"># DOMMatrix</a>
 */
public interface DOMMatrix extends DOMMatrixReadOnly {

    void setA(Double val);

    void setB(Double val);

    void setC(Double val);

    void setD(Double val);

    void setE(Double val);

    void setF(Double val);

    Double getM11();

    void setM11(Double val);

    Double getM12();

    void setM12(Double val);

    Double getM21();

    void setM21(Double val);

    Double getM22();

    void setM22(Double val);

    Double getM41();

    void setM41(Double val);

    Double getM42();

    void setM42(Double val);

    DOMMatrix invertSelf();

    DOMMatrix multiplySelf();

    DOMMatrix multiplySelf(DOMMatrixInit other);

    DOMMatrix preMultiplySelf();

    DOMMatrix preMultiplySelf(DOMMatrixInit other);

    DOMMatrix rotateAxisAngleSelf();

    DOMMatrix rotateAxisAngleSelf(Double x);

    DOMMatrix rotateAxisAngleSelf(Double x, Double y);

    DOMMatrix rotateAxisAngleSelf(Double x, Double y, Double z);

    DOMMatrix rotateAxisAngleSelf(Double x, Double y, Double z, Double angle);

    DOMMatrix rotateFromVectorSelf();

    DOMMatrix rotateFromVectorSelf(Double x);

    DOMMatrix rotateFromVectorSelf(Double x, Double y);

    DOMMatrix rotateSelf();

    DOMMatrix rotateSelf(Double rotX);

    DOMMatrix rotateSelf(Double rotX, Double rotY);

    DOMMatrix rotateSelf(Double rotX, Double rotY, Double rotZ);

    DOMMatrix scale3dSelf();

    DOMMatrix scale3dSelf(Double scale);

    DOMMatrix scale3dSelf(Double scale, Double originX);

    DOMMatrix scale3dSelf(Double scale, Double originX, Double originY);

    DOMMatrix scale3dSelf(Double scale, Double originX, Double originY, Double originZ);

    DOMMatrix scaleSelf();

    DOMMatrix scaleSelf(Double scaleX);

    DOMMatrix scaleSelf(Double scaleX, Double scaleY);

    DOMMatrix scaleSelf(Double scaleX, Double scaleY, Double scaleZ);

    DOMMatrix scaleSelf(Double scaleX, Double scaleY, Double scaleZ, Double originX);

    DOMMatrix scaleSelf(Double scaleX, Double scaleY, Double scaleZ, Double originX, Double originY);

    DOMMatrix scaleSelf(Double scaleX, Double scaleY, Double scaleZ, Double originX, Double originY, Double originZ);

    DOMMatrix setMatrixValue(String transformList);

    DOMMatrix skewXSelf();

    DOMMatrix skewXSelf(Double sx);

    DOMMatrix skewYSelf();

    DOMMatrix skewYSelf(Double sy);

    DOMMatrix translateSelf();

    DOMMatrix translateSelf(Double tx);

    DOMMatrix translateSelf(Double tx, Double ty);

    DOMMatrix translateSelf(Double tx, Double ty, Double tz);
}
