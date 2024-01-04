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

package org.loboevolution.pdfview.pattern;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.pdfview.BaseWatchable;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFPaint;
import org.loboevolution.pdfview.PDFParseException;
import org.loboevolution.pdfview.colorspace.PDFColorSpace;
import org.loboevolution.pdfview.function.PDFFunction;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.IOException;

/**
 * A shader that performs radial shader based on a function.
 */
@Getter
@Setter
public class ShaderType3 extends PDFShader {
    /**
     * the center of the first circle
     */
    private Point2D center1;

    /**
     * the center of the second circle
     */
    private Point2D center2;

    /**
     * the radius of the first circle
     */
    private float radius1;

    /**
     * the radius of the second circle
     */
    private float radius2;

    /**
     * the domain minimum
     */
    private float minT = 0f;

    /**
     * the domain maximum
     */
    private float maxT = 1f;

    /**
     * whether to extend the start of the axis
     */
    private boolean extendStart = false;

    /**
     * whether to extend the end of the axis
     */
    private boolean extendEnd = false;

    /**
     * functions, as an array of either 1 or n functions
     */
    private PDFFunction[] functions;

    /**
     * Creates a new instance of ShaderType2
     */
    public ShaderType3() {
        super(3);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Parse the shader-specific data
     */
    @Override
    public void parse(final PDFObject shaderObj) throws IOException {
        // read the axis coordinates (required)
        final PDFObject coordsObj = shaderObj.getDictRef("Coords");
        if (coordsObj == null) {
            throw new PDFParseException("No coordinates found!");
        }
        final PDFObject[] coords = coordsObj.getArray();
        center1 = new Point2D.Float(coords[0].getFloatValue(),
                coords[1].getFloatValue());
        center2 = new Point2D.Float(coords[3].getFloatValue(),
                coords[4].getFloatValue());
        radius1 = coords[2].getFloatValue();
        radius2 = coords[5].getFloatValue();

        // read the domain (optional)
        final PDFObject domainObj = shaderObj.getDictRef("Domain");
        if (domainObj != null) {
            final PDFObject[] domain = domainObj.getArray();
            setMinT(domain[0].getFloatValue());
            setMaxT(domain[1].getFloatValue());
        }

        // read the functions (required)
        final PDFObject functionObj = shaderObj.getDictRef("Function");
        if (functionObj == null) {
            throw new PDFParseException("No function defined for shader!");
        }
        final PDFObject[] functionArray = functionObj.getArray();
        final PDFFunction[] functions = new PDFFunction[functionArray.length];
        for (int i = 0; i < functions.length; i++) {
            functions[i] = PDFFunction.getFunction(functionArray[i]);
        }
        setFunctions(functions);

        // read the extend array (optional)
        final PDFObject extendObj = shaderObj.getDictRef("Extend");
        if (extendObj != null) {
            final PDFObject[] extendArray = extendObj.getArray();
            setExtendStart(extendArray[0].getBooleanValue());
            setExtendEnd(extendArray[1].getBooleanValue());
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Create a paint that paints this pattern
     */
    @Override
    public PDFPaint getPaint() {
        return PDFPaint.getPaint(new Type3Paint());
    }

    /**
     * A subclass of paint that uses this shader to generate a paint
     */
    class Type3Paint implements Paint {
        public Type3Paint() {
        }

        /**
         * create a paint context
         */
        @Override
        public PaintContext createContext(final ColorModel cm,
                                          final Rectangle deviceBounds,
                                          final Rectangle2D userBounds,
                                          final AffineTransform xform,
                                          final RenderingHints hints) {
            final ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
            final ColorModel model = new ComponentColorModel(cs,
                    true,
                    false,
                    Transparency.TRANSLUCENT,
                    DataBuffer.TYPE_BYTE);

            return new Type3PaintContext(model, xform);
        }

        @Override
        public int getTransparency() {
            return Transparency.TRANSLUCENT;
        }
    }

    /**
     * A simple paint context that uses an existing raster in device
     * space to generate pixels
     */
    class Type3PaintContext implements PaintContext {
        private final double dx1x0, dy1y0, dr1r0, sqr0, denom;
        /**
         * the color model
         */
        private ColorModel colorModel;
        /**
         * the transformation
         */
        private AffineTransform invXform;

        /**
         * Create a paint context
         */
        Type3PaintContext(final ColorModel colorModel, final AffineTransform xform) {
            this.colorModel = colorModel;

            //Precalculate some often needed values; 
            dx1x0 = center2.getX() - center1.getX();
            dy1y0 = center2.getY() - center1.getY();
            dr1r0 = radius2 - radius1;
            sqr0 = radius1 * radius1;
            denom = dx1x0 * dx1x0 + dy1y0 * dy1y0 - dr1r0 * dr1r0;

            try {
                this.invXform = xform.createInverse();
            } catch (final NoninvertibleTransformException e) {
                BaseWatchable.getErrorHandler().publishException(e);
            }
        }

        @Override
        public void dispose() {
            this.colorModel = null;
        }

        @Override
        public ColorModel getColorModel() {
            return this.colorModel;
        }

        @Override
        public Raster getRaster(final int x, final int y, final int w, final int h) {
            final ColorSpace cs = getColorModel().getColorSpace();
            final PDFColorSpace shadeCSpace = getColorSpace();

            final PDFFunction[] functions = getFunctions();

            final int numComponents = cs.getNumComponents();

            final float[] c1 = new float[2];

            final float[] inputs = new float[1];
            final float[] outputs = new float[shadeCSpace.getNumComponents()];
            float[] outputRBG = new float[numComponents];

            // all the data, plus alpha channel
            final int[] data = new int[w * h * (numComponents + 1)];
            float lastInput = Float.POSITIVE_INFINITY;
            final float tol = TOLERANCE * (getMaxT() - getMinT());

            final int advance = 1;
            // for each device coordinate
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i += advance) {
                    //Get point in user space
                    invXform.transform(new float[]{x + i, y + j}, 0, c1, 0, 1);
                    boolean render = true;
                    final float[] s = calculateInputValues(c1[0], c1[1]);
                    //s[0] <= s[1] holds
                    //if (s[0] >= 0 && s[1] <= 1) s[1] = s[1];
                    if (s[1] >= 0 && s[1] <= 1) s[1] = s[1];
                    else if (extendEnd && s[1] >= 0 && radius1 + s[1] * dr1r0 >= 0) {
                        s[1] = s[1];
                    } else if (s[0] >= 0 && s[0] <= 1) s[1] = s[0];
                    else if (extendStart && s[1] <= 0 && radius1 + s[1] * dr1r0 >= 0) {
                        s[1] = s[1];
                    } else if (extendStart && s[0] <= 1 && radius1 + s[0] * dr1r0 >= 0) {
                        s[1] = s[0];
                    } else render = false;

                    if (render) {
                        final float t = (getMinT() + s[1] * (getMaxT() - getMinT()));
                        // calculate the pixel values at t
                        inputs[0] = t;
                        if (Math.abs(lastInput - t) > tol) {

                            if (functions.length == 1) {
                                functions[0].calculate(inputs, 0, outputs, 0);
                            } else {
                                for (int c = 0; c < functions.length; c++) {
                                    functions[c].calculate(inputs, 0, outputs, c);
                                }
                            }

                            if (!shadeCSpace.getColorSpace().isCS_sRGB()) {
                                //Can be quite slow
                                outputRBG = shadeCSpace.getColorSpace().toRGB(outputs);
                            } else outputRBG = outputs;

                            lastInput = t;
                        }
                        final int base = (j * w + i) * (numComponents + 1);
                        for (int c = 0; c < numComponents; c++) {
                            data[base + c] = (int) (outputRBG[c] * 255);
                        }
                        data[base + numComponents] = 255;
                    }
                }
            }

            final WritableRaster raster =
                    getColorModel().createCompatibleWritableRaster(w, h);
            raster.setPixels(0, 0, w, h, data);

            final Raster child = raster.createTranslatedChild(x, y);
            return child;
        }

        /**
         * From Adobe Technical Note #5600:
         * <p>
         * Given a geometric coordinate position (x, y) in or along the gradient gradient fill,
         * the corresponding value of s can be determined by solving the quadratic
         * constraint equation:
         * <p>
         * [x - xc(s)]2 + [y - yc(s)]2 = [r(s)]2
         * <p>
         * The following code calculates the 2 possible values of s.
         *
         * @return Two possible values of s with s[0] <= s[1]
         */
        private float[] calculateInputValues(final float x, final float y) {
            final double p = -(x - center1.getX()) * dx1x0 - (y - center1.getY()) * dy1y0 - radius1 * dr1r0;
            final double q = (Math.pow(x - center1.getX(), 2) + Math.pow(y - center1.getY(), 2) - sqr0);
            final double root = Math.sqrt(p * p - denom * q);
            final float root1 = (float) ((-p + root) / denom);
            final float root2 = (float) ((-p - root) / denom);
            if (denom < 0) return new float[]{root1, root2};
            else return new float[]{root2, root1};
        }
    }
}
