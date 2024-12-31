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
 * The Interface DOMMatrixInit.
 */
public interface DOMMatrixInit extends DOMMatrix2DInit {

    Boolean getIs2D();

    void setIs2D(Boolean val);

    Double getM13();

    void setM13(Double val);

    Double getM14();

    void setM14(Double val);

    Double getM23();

    void setM23(Double val);

    Double getM24();

    void setM24(Double val);

    Double getM31();

    void setM31(Double val);

    Double getM32();

    void setM32(Double val);

    Double getM33();

    void setM33(Double val);

    Double getM34();

    void setM34(Double val);

    Double getM43();

    void setM43(Double val);

    Double getM44();

    void setM44(Double val);

}
