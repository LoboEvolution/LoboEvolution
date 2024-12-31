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

package org.loboevolution.pdfview.pattern;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFPaint;
import org.loboevolution.pdfview.PDFParseException;
import org.loboevolution.pdfview.colorspace.PDFColorSpace;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Map;

/**
 * <p>A PDFShader fills a given region with a shading, such as a gradient.</p>
 * <p>
 * Shading Dictionaries (section 4.6)<br>
 * A shading dictionary specifies details of a particular gradient fill,
 * including the type of shading to be used, the geometry of the area to
 * be shaded, and the geometry of the gradient fill. Various shading types
 * are available, depending on the value of the dictionary’s ShadingType entry:
 *
 * <p>Function-based shadings (type 1) define the color of every point in the
 * domain using a mathematical function (not necessarily smooth or continuous).</p>
 *
 * <p>Axial shadings (type 2) define a color blend along a pne between two
 * points, optionally extended beyond the boundary points by continuing
 * the boundary colors.</p>
 *
 * <p>Radial shadings (type 3) define a blend between two circles,
 * optionally extended beyond the boundary circles by continuing the
 * boundary colors. This type of shading is commonly used to represent
 * three-dimensional spheres and cones.</p>
 *
 * <p>Free-form Gouraud-shaded triangle meshes (type 4) define a
 * common construct used by many three-dimensional apppcations to
 * represent complex colored and shaded shapes. Vertices are specified
 * in free-form geometry.</p>
 *
 * <p>Lattice-form Gouraud-shaded triangle meshes (type 5) are based on
 * the same geometrical construct as type 4 but with vertices specified
 * as a pseudorectangular lattice.</p>
 *
 * <p>Coons patch meshes (type 6) construct a shading from one or more
 * color patches, each bounded by four cubic Bézier curves.</p>
 *
 * <p>Tensor-product patch meshes (type 7) are similar to type 6 but
 * with additional control points in each patch, affording greater
 * control over color mapping.</p>
 * <p>
 * Table 4.28 shows the entries that all shading dictionaries share
 * in common; entries specific to particular shading types are
 * described in the relevant sections below.
 */
@Getter
@Setter
public abstract class PDFShader {

    /**
     * Constant <code>FUNCTION_SHADING=1</code>
     */
    public static final int FUNCTION_SHADING = 1;
    /**
     * Constant <code>AXIAL_SHADING=2</code>
     */
    public static final int AXIAL_SHADING = 2;
    /**
     * Constant <code>RADIAL_SHADING=3</code>
     */
    public static final int RADIAL_SHADING = 3;
    /**
     * Constant <code>FREE_FORM_SHADING=4</code>
     */
    public static final int FREE_FORM_SHADING = 4;
    /**
     * Constant <code>LATTICE_SHADING=5</code>
     */
    public static final int LATTICE_SHADING = 5;
    /**
     * Constant <code>COONS_PATCH_MESH_SHADING=6</code>
     */
    public static final int COONS_PATCH_MESH_SHADING = 6;
    /**
     * Constant <code>TENSOR_PRODUCTS_MESH_SHADING=7</code>
     */
    public static final int TENSOR_PRODUCTS_MESH_SHADING = 7;

    /**
     * The tolerance for reevaluating the shading function again
     */
    public static final float TOLERANCE = 1e-4f;

    /**
     * the type of the shading (1 through 7)
     */
    private final int type;

    /**
     * the colorspace
     */
    private PDFColorSpace colorSpace;

    /**
     * the background color
     */
    private PDFPaint background;

    /**
     * the bounding box of the pattern
     */
    private Rectangle2D bbox;

    /**
     * Creates a new instance of PDFShader
     *
     * @param type a {@link java.lang.Integer} object.
     */
    protected PDFShader(final int type) {
        this.type = type;
    }


    /**
     * Parse a pdf shader into a shader object
     *
     * @param shaderObj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param resources a {@link java.util.Map} object.
     * @return a {@link org.loboevolution.pdfview.pattern.PDFShader} object.
     * @throws java.io.IOException if any.
     */
    public static PDFShader getShader(final PDFObject shaderObj, final Map resources)
            throws IOException {
        // first see if the shader is already cached
        PDFShader shader = (PDFShader) shaderObj.getCache();
        if (shader != null) {
            return shader;
        }

        // read the type (required)
        final PDFObject typeObj = shaderObj.getDictRef("ShadingType");
        if (typeObj == null) {
            throw new PDFParseException("No shader type defined!");
        }
        final int type = typeObj.getIntValue();

        // create the shader
        shader = switch (type) {
            case AXIAL_SHADING -> new ShaderType2();
            case RADIAL_SHADING -> new ShaderType3();
            default -> new DummyShader(type);
        };

        // read the color space (required)
        final PDFObject csObj = shaderObj.getDictRef("ColorSpace");
        if (csObj == null) {
            throw new PDFParseException("No colorspace defined!");
        }
        final PDFColorSpace cs = PDFColorSpace.getColorSpace(csObj, resources);
        shader.setColorSpace(cs);

        // read the background color (optional)
        final PDFObject bgObj = shaderObj.getDictRef("Background");
        if (bgObj != null) {
            final PDFObject[] bgObjs = bgObj.getArray();
            final float[] bgArray = new float[bgObjs.length];
            for (int i = 0; i < bgArray.length; i++) {
                bgArray[i] = bgObjs[i].getFloatValue();
            }
            final PDFPaint paint = cs.getPaint(bgArray);
            shader.setBackground(paint);
        }

        // read the bounding box (optional)
        final PDFObject bboxObj = shaderObj.getDictRef("BBox");
        if (bboxObj != null) {
            final PDFObject[] rectObj = bboxObj.getArray();
            final float minX = rectObj[0].getFloatValue();
            final float minY = rectObj[1].getFloatValue();
            final float maxX = rectObj[2].getFloatValue();
            final float maxY = rectObj[3].getFloatValue();

            final Rectangle2D bbox =
                    new Rectangle2D.Float(minX, minY, maxX - minX, maxY - minY);
            shader.setBbox(bbox);
        }

        // parse the shader-specific attributes
        shader.parse(shaderObj);

        // set the cache
        shaderObj.setCache(shader);

        return shader;
    }

    /**
     * Parse the shader-specific data
     *
     * @param shareObj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public abstract void parse(PDFObject shareObj) throws IOException;

    /**
     * Returns paint that represents the selected shader
     *
     * @return a {@link org.loboevolution.pdfview.PDFPaint} object.
     */
    public abstract PDFPaint getPaint();
}
