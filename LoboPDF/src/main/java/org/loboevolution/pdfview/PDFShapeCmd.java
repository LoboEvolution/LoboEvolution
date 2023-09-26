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
package org.loboevolution.pdfview;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;

/**
 * Encapsulates a path. Also contains extra fields and logic to check
 * for consecutive abutting anti-aliased regions. We stroke the shared
 * line between these regions again with a 1-pixel wide line so that
 * the background doesn't show through between them.
 *
 * Author Mike Wessler
  *
 */
public class PDFShapeCmd extends PDFCmd {
    /** stroke the outline of the path with the stroke paint */
    public static final int STROKE = 1;
    /** fill the path with the fill paint */
    public static final int FILL = 2;
    /** perform both stroke and fill */
    public static final int BOTH = 3;
    /** set the clip region to the path */
    public static final int CLIP = 4;
    /** base path */
    private final GeneralPath gp;
    /** the style */
    private final int style;
    /** the bounding box of the path */
    // private Rectangle2D bounds;
    /** the stroke style for the anti-antialias stroke */
    final BasicStroke againstroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
    private boolean autoAdjustStroke = false;

    /**
     * create a new PDFShapeCmd and check it against the previous one
     * to find any shared edges.
     *
     * @param gp
     * the path
     * @param style
     * the style: an OR of STROKE, FILL, or CLIP. As a
     * convenience, BOTH = STROKE | FILL.
     * @param autoAdjustStroke a boolean.
     */
    public PDFShapeCmd(final GeneralPath gp, final int style, final boolean autoAdjustStroke) {
        this.gp = gp;
        this.style = style;
        this.autoAdjustStroke = autoAdjustStroke;
    }

    /**
     * {@inheritDoc}
     *
     * perform the stroke and record the dirty region
     */
    @Override
    public Rectangle2D execute(final PDFRenderer state) {
        Rectangle2D rect = null;
        if ((this.style & FILL) != 0) {
            rect = state.fill(this.gp);
            final GeneralPath strokeagain = checkOverlap(state);
            if (strokeagain != null) {
                state.draw(strokeagain, this.againstroke);
            }
            if (this.gp != null) {
                state.setLastShape(this.gp);
                state.rememberTransformation();
            }
        }
        if ((this.style & STROKE) != 0) {
            final Rectangle2D strokeRect = state.stroke(this.gp, autoAdjustStroke);
            if (rect == null) {
                rect = strokeRect;
            } else {
                rect = rect.createUnion(strokeRect);
            }
        }
        if ((this.style & CLIP) != 0) {
            state.clip(this.gp);
        }
        return rect;
    }

    /**
    * Check for overlap with the previous shape to make anti-aliased shapes
    * that are near each other look good
    */
    private GeneralPath checkOverlap(final PDFRenderer state) {
        if (this.style == FILL && this.gp != null && state.getLastShape() != null) {
            float[] mypoints = new float[16];
            final float[] prevpoints = new float[16];
            final int mycount = getPoints(this.gp, mypoints, state.getTransform());
            final int prevcount = getPoints(state.getLastShape(), prevpoints, state.getLastTransform());
            // now check mypoints against prevpoints for opposite pairs:
            if (mypoints != null && prevpoints != null) {
                for (int i = 0; i < prevcount; i += 4) {
                    for (int j = 0; j < mycount; j += 4) {
                        if ((Math.abs(mypoints[j + 2] - prevpoints[i]) < 0.01 && Math.abs(mypoints[j + 3] - prevpoints[i + 1]) < 0.01 && Math.abs(mypoints[j] - prevpoints[i + 2]) < 0.01 && Math
                                .abs(mypoints[j + 1] - prevpoints[i + 3]) < 0.01)) {
                            // it seems that need to use the original points location (without the Affine Transform)
                            mypoints = new float[16];
                            getPoints(this.gp, mypoints, null);// without AffineTransform

                            final GeneralPath strokeagain = new GeneralPath();
                            strokeagain.moveTo(mypoints[j], mypoints[j + 1]);
                            strokeagain.lineTo(mypoints[j + 2], mypoints[j + 3]);
                            return strokeagain;
                        }
                    }
                }
            }
        }
        // no issues
        return null;
    }

    /**
    * Get an array of 16 points from a path
    *
    * @return the number of points we actually got
    */
    private int getPoints(final GeneralPath path, float[] mypoints, final AffineTransform at) {
        int count = 0;
        float x = 0;
        float y = 0;
        float startx = 0;
        float starty = 0;
        final float[] coords = new float[6];
        final PathIterator pi = path.getPathIterator(at);
        while (!pi.isDone()) {
            if (count >= mypoints.length) {
                mypoints = null;
                break;
            }
            final int pathtype = pi.currentSegment(coords);
            switch (pathtype) {
            case PathIterator.SEG_MOVETO:
                startx = x = coords[0];
                starty = y = coords[1];
                break;
            case PathIterator.SEG_LINETO:
                mypoints[count++] = x;
                mypoints[count++] = y;
                x = mypoints[count++] = coords[0];
                y = mypoints[count++] = coords[1];
                break;
            case PathIterator.SEG_QUADTO:
                x = coords[2];
                y = coords[3];
                break;
            case PathIterator.SEG_CUBICTO:
                x = mypoints[4];
                y = mypoints[5];
                break;
            case PathIterator.SEG_CLOSE:
                mypoints[count++] = x;
                mypoints[count++] = y;
                x = mypoints[count++] = startx;
                y = mypoints[count++] = starty;
                break;
            default:
    			break;
            }
            pi.next();
        }
        return count;
    }

    /**
     * {@inheritDoc}
     *
     * Get detailed information about this shape
     */
    @Override
    public String getDetails() {
        final StringBuilder sb = new StringBuilder();
        final Rectangle2D b = this.gp.getBounds2D();
        sb.append("ShapeCommand at: ").append(b.getX()).append(", ").append(b.getY()).append("\n");
        sb.append("Size: ").append(b.getWidth()).append(" x ").append(b.getHeight()).append("\n");
        sb.append("Mode: ");
        if ((this.style & FILL) != 0) {
            sb.append("FILL ");
        }
        if ((this.style & STROKE) != 0) {
            sb.append("STROKE ");
        }
        if ((this.style & CLIP) != 0) {
            sb.append("CLIP");
        }
        return sb.toString();
    }
}
