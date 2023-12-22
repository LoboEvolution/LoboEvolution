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

package org.loboevolution.pdfview.pattern;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFPaint;
import org.loboevolution.pdfview.PDFParseException;
import org.loboevolution.pdfview.colorspace.PDFColorSpace;
import org.loboevolution.pdfview.function.PDFFunction;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.IOException;

/**
 * A shader that performs axial shader based on a function.
 */
@Getter
@Setter
public class ShaderType2 extends PDFShader {
    /**
     * the start of the axis
     */
    private Point2D axisStart;

    /**
     * the end of the axis
     */
    private Point2D axisEnd;

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
    public ShaderType2() {
        super(2);
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
        final Point2D start = new Point2D.Float(coords[0].getFloatValue(),
                coords[1].getFloatValue());
        final Point2D end = new Point2D.Float(coords[2].getFloatValue(),
                coords[3].getFloatValue());
        setAxisStart(start);
        setAxisEnd(end);

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
        return PDFPaint.getPaint(new Type2Paint());
    }

    /**
     * Set the functions associated with this shader
     *
     * @param functions an array of {@link org.loboevolution.pdfview.function.PDFFunction} objects.
     */
    protected void setFunctions(final PDFFunction[] functions) {
        this.functions = functions;
    }

    /**
     * A subclass of paint that uses this shader to generate a paint
     */
    class Type2Paint implements Paint {
        public Type2Paint() {
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

            final Point2D devStart = xform.transform(getAxisStart(), null);
            final Point2D devEnd = xform.transform(getAxisEnd(), null);

            return new Type2PaintContext(model, devStart, devEnd);
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
    class Type2PaintContext implements PaintContext {
        /**
         * the start of the axis
         */
        private final Point2D start;
        /**
         * the end of the axis
         */
        private final Point2D end;
        private final float dt1t0;
        private final double dx1x0;
        private final double dy1y0;
        private final double sqdx1x0psqdy1y0;
        /**
         * the color model
         */
        private ColorModel colorModel;

        /**
         * Create a paint context
         */
        Type2PaintContext(final ColorModel colorModel, final Point2D start, final Point2D end) {
            this.colorModel = colorModel;
            this.start = start;
            this.end = end;

            //pre calculate some often used values
            dt1t0 = getMaxT() - getMinT();
            dx1x0 = end.getX() - start.getX();
            dy1y0 = end.getY() - start.getY();
            sqdx1x0psqdy1y0 = dx1x0 * dx1x0 + dy1y0 * dy1y0;
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

            final float x0 = (float) this.start.getX();
            final float y0 = (float) this.start.getY();

            final float[] inputs = new float[1];
            final float[] outputs = new float[shadeCSpace.getNumComponents()];
            float[] outputRBG = new float[numComponents];

            // all the data, plus alpha channel
            final int[] data = new int[w * h * (numComponents + 1)];

            // for each device coordinate
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i += 1) {
                    boolean render = true;
                    // find t for that user coordinate
                    final float xp = getXPrime(i + x, j + y, x0, y0);
                    float t = 0;
                    if (xp >= 0 && xp <= 1) t = getMinT() + (dt1t0 * xp);
                    else if (xp < 0 && extendStart) t = getMinT();
                    else if (xp > 1 && extendEnd) t = getMaxT();
                    else render = false;

                    if (render) {
                        // calculate the pixel values at t
                        inputs[0] = t;
                        if (functions.length == 1) {
                            functions[0].calculate(inputs, 0, outputs, 0);
                        } else {
                            for (int c = 0; c < functions.length; c++) {
                                functions[c].calculate(inputs, 0, outputs, c);
                            }
                        }
                        if (functions[0].getNumOutputs() != numComponents) {
                            //CMYK
                            outputRBG = shadeCSpace.getColorSpace().toRGB(outputs);
                        } else outputRBG = outputs;

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
         * x' = (x1 - x0) * (x - x0) + (y1 - y0) * (y - y0)
         * -------------------------------------------
         * (x1 - x0)^2 + (y1 - y0)^2
         */
        private float getXPrime(final float x, final float y, final float x0, final float y0) {

            final double tp = ((dx1x0 * (x - x0)) + (dy1y0 * (y - y0))) / sqdx1x0psqdy1y0;

            return (float) tp;
        }

        /**
         * t = t0 + (t1 - t0) x x'
         */
        private float getT(final float xp) {

            if (xp < 0) {
                return getMinT();
            } else if (xp > 1) {
                return getMaxT();
            } else {
                return getMinT() + (dt1t0 * xp);
            }
        }
    }
}
