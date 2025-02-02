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

package org.loboevolution.svg;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>SVGTransformImpl class.</p>
 */
@Getter
@Setter
public class SVGTransformImpl extends SVGLocatableImpl implements SVGTransform {

    protected float angle;
    private short type;
    private SVGMatrix matrix;

    /**
     * <p>Constructor for SVGTransformImpl.</p>
     */
    public SVGTransformImpl() {
        super("");
        type = SVG_TRANSFORM_MATRIX;
        matrix = new SVGMatrixImpl();
        angle = 0;
    }

    /**
     * <p>Constructor for SVGTransformImpl.</p>
     *
     * @param transformType a short.
     */
    public SVGTransformImpl(final short transformType) {
        super("");
        this.type = transformType;
    }

    @Override
    public void setMatrix(SVGMatrix matrix) {
        type = SVG_TRANSFORM_MATRIX;
        this.matrix = matrix;
        angle = 0;
    }


    @Override
    public void setTranslate(float tx, float ty) {
        type = SVG_TRANSFORM_TRANSLATE;
        matrix = new SVGMatrixImpl();
        matrix = matrix.translate(tx, ty);
        angle = 0;
    }

    @Override
    public void setScale(float sx, float sy) {
        type = SVG_TRANSFORM_SCALE;
        matrix = new SVGMatrixImpl();
        matrix = matrix.scaleNonUniform(sx, sy);
        angle = 0;
    }

    @Override
    public void setRotate(float angle, float cx, float cy) {
        type = SVG_TRANSFORM_ROTATE;
        matrix = new SVGMatrixImpl();
        matrix = matrix.translate(cx, cy);
        matrix = matrix.rotate(angle);
        matrix = matrix.translate(-cx, -cy);
        this.angle = angle;
    }


    @Override
    public void setSkewX(float angle) {
        type = SVG_TRANSFORM_SKEWX;
        matrix = new SVGMatrixImpl();
        matrix = matrix.skewX(angle);
        this.angle = angle;
    }

    @Override
    public void setSkewY(float angle) {
        type = SVG_TRANSFORM_SKEWY;
        matrix = new SVGMatrixImpl();
        matrix = matrix.skewY(angle);
        this.angle = angle;
    }
}
