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

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class SVGRectElementImpl extends SVGTransformableImpl implements SVGRectElement {

    /**
     * <p>Constructor for SVGRectElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public SVGRectElementImpl(String name) {
        super(name);
    }

    @Override
    public void draw(Graphics2D graphics) {
        final Shape shape = createShape();
        drawable(graphics, shape);
    }

    @Override
    public Shape createShape() {

        AffineTransform inverseTransform;
        try {
            final SVGMatrix ctm = getCTM();
            inverseTransform = ctm.getAffineTransform().createInverse();
        } catch (Exception e) {
            inverseTransform = null;
        }
        float x = ((SVGLengthImpl) getX().getAnimVal()).getTransformedLength(inverseTransform);
        float y = ((SVGLengthImpl) getY().getAnimVal()).getTransformedLength(inverseTransform);
        float rx = ((SVGLengthImpl) getRx().getAnimVal()).getTransformedLength(inverseTransform);
        float ry = ((SVGLengthImpl) getRy().getAnimVal()).getTransformedLength(inverseTransform);
        float width = ((SVGLengthImpl) getWidth().getAnimVal()).getTransformedLength(inverseTransform);
        float height = ((SVGLengthImpl) getHeight().getAnimVal()).getTransformedLength(inverseTransform);

        Shape rect;
        if (rx > 0 || ry > 0) {
            if (rx > 0 && ry == 0) {
                ry = rx;
            } else if (rx == 0 && ry > 0) {
                rx = ry;
            }
            rect = new RoundRectangle2D.Float(x, y, width, height, rx * 2, ry * 2);
        } else {
            rect = new Rectangle2D.Float(x, y, width, height);
        }
        return rect;
    }

    @Override
    public SVGAnimatedLength getX() {
        return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("x")));
    }

    @Override
    public SVGAnimatedLength getY() {
        return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("y")));
    }

    @Override
    public SVGAnimatedLength getWidth() {
        return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("width")));
    }

    @Override
    public SVGAnimatedLength getHeight() {
        return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("height")));
    }

    @Override
    public SVGAnimatedLength getRx() {
        return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("rx")));
    }

    @Override
    public SVGAnimatedLength getRy() {
        return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("ry")));
    }
}
