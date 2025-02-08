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

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;


/**
 * <p>SVGMatrixImpl class.</p>
 */
@Getter
@Setter
public class SVGMatrixImpl implements SVGMatrix {

    private AffineTransform affineTransform;
    private float a;
    private float b;
    private float c;
    private float d;
    private float e;
    private float f;

    /**
     * Constructs a new SVGMatrixImpl
     */
    public SVGMatrixImpl(SVGMatrixImpl matrix) {
        affineTransform = matrix != null ? matrix.affineTransform : new AffineTransform();
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
    }

    /**
     * Constructs a new SVGMatrixImpl
     */
    public SVGMatrixImpl(float a, float b, float c, float d, float e, float f) {
        this.affineTransform = new AffineTransform(a, b, c, d, e, f);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    @Override
    public SVGMatrix multiply(SVGMatrix secondMatrix) {
        affineTransform.concatenate(secondMatrix.getAffineTransform());
        return this;
    }

    @Override
    public SVGMatrix inverse() throws SVGException {
        AffineTransform inverse;
        try {
            inverse = this.affineTransform.createInverse();
        } catch (NoninvertibleTransformException e) {
            throw new SVGExceptionImpl(SVGException.SVG_MATRIX_NOT_INVERTABLE, "Matrix is not invertable");
        }

        this.affineTransform = new AffineTransform(inverse);
        return this;
    }

    @Override
    public SVGMatrix translate(float x, float y) {
        this.affineTransform.translate(x, y);
        return this;
    }

    @Override
    public SVGMatrix scale(float scaleFactor) {
        return scaleNonUniform(scaleFactor, scaleFactor);
    }

    @Override
    public SVGMatrix scaleNonUniform(float scaleFactorX, float scaleFactorY) {
        this.affineTransform.scale(scaleFactorX, scaleFactorY);
        return this;
    }

    @Override
    public SVGMatrix rotate(float angle) {
        this.affineTransform.rotate(Math.toRadians(angle));
        return this;
    }

    @Override
    public SVGMatrix rotateFromVector(float x, float y) throws SVGException {
        double angle = Math.atan(y / x);
        this.affineTransform.rotate(angle);
        return this;
    }

    @Override
    public SVGMatrix flipX() {
        this.affineTransform.concatenate(new AffineTransform(-1, 0, 0, 1, 0, 0));
        return this;
    }

    @Override
    public SVGMatrix flipY() {
        this.affineTransform.concatenate(new AffineTransform(1, 0, 0, -1, 0, 0));
        return this;
    }

    @Override
    public SVGMatrix skewX(float angle) {
        this.affineTransform.concatenate(new AffineTransform(1, 0, Math.tan(Math.toRadians(angle)), 1, 0, 0));
        return this;
    }

    @Override
    public SVGMatrix skewY(float angle) {
        this.affineTransform.concatenate(new AffineTransform(1, Math.tan(Math.toRadians(angle)), 0, 1, 0, 0));
        return this;
    }
}