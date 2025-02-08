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
import org.w3c.dom.DOMException;

/**
 * <p>SVGTransformImpl class.</p>
 */
@Getter
@Setter
public class SVGTransformImpl extends SVGLocatableImpl implements SVGTransform {

    private short type = SVG_TRANSFORM_UNKNOWN;
    private SVGMatrix matrix;
    private float angle;
    public float[] rotateValues;

    /**
     * <p>Constructor for SVGTransformImpl.</p>
     */
    public SVGTransformImpl() {
        super("");
    }

    @Override
    public void setMatrix(SVGMatrix matrix) throws DOMException {
        this.matrix = matrix;
        type = SVG_TRANSFORM_MATRIX;
        angle = 0;
    }

    @Override
    public void setTranslate(float tx, float ty) throws DOMException {
        this.matrix = new SVGMatrixImpl(1, 0, 0, 1, tx, ty);
        matrix = matrix.translate(tx, ty);
        type = SVG_TRANSFORM_TRANSLATE;
        angle = 0;
    }

    @Override
    public void setScale(float sx, float sy) throws DOMException {
        this.matrix = new SVGMatrixImpl(sx, 0, 0, sy, 0, 0);
        this.matrix = matrix.scaleNonUniform(sx, sy);
        type = SVG_TRANSFORM_SCALE;
        angle = 0;
    }

    @Override
    public void setRotate(float angle, float cx, float cy) throws DOMException {
        this.matrix = new SVGMatrixImpl(1, 0, 0, 1, 0, 0);
        this.matrix = matrix.translate(cx, cy);
        this.matrix = matrix.rotate(angle);
        this.matrix = matrix.translate(-cx, -cy);
        type = SVG_TRANSFORM_ROTATE;
        this.angle = angle;
        rotateValues = new float[] { angle, cx, cy };
    }

    @Override
    public void setSkewX(float angle) throws DOMException {
        this.matrix = new SVGMatrixImpl(1, 0, (float) Math.tan(angle), 1, 0, 0);
        type = SVG_TRANSFORM_SKEWX;
        this.angle = angle;
    }

    @Override
    public void setSkewY(float angle) throws DOMException {
        this.matrix = new SVGMatrixImpl(1, (float) Math.tan(angle), 0, 1, 0, 0);
        type = SVG_TRANSFORM_SKEWY;
        this.angle = angle;
    }
}
