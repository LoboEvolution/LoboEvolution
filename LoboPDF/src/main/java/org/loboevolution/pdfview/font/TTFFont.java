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
package org.loboevolution.pdfview.font;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.font.ttf.*;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;

/**
 * A true-type font
 */
public class TTFFont extends OutlineFont {

    /**
     * the truetype font itself
     */
    private TrueTypeFont font;

    /**
     * the number of units per em in the font
     */
    private float unitsPerEm;

    /**
     * <p>Constructor for TTFFont.</p>
     *
     * @param baseFont   a {@link java.lang.String} object.
     * @param fontObj    a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param descriptor a {@link org.loboevolution.pdfview.font.PDFFontDescriptor} object.
     * @throws java.io.IOException if any.
     */
    public TTFFont(final String baseFont, final PDFObject fontObj,
                   final PDFFontDescriptor descriptor) throws IOException {
        this(baseFont, fontObj, descriptor, null);
    }

    /**
     * create a new TrueTypeFont object based on a description of the
     * font from the PDF file.  If the description happens to contain
     * an in-line true-type font file (under key "FontFile2"), use the
     * true type font.  Otherwise, parse the description for key information
     * and use that to generate an appropriate font.
     *
     * @param baseFont   a {@link java.lang.String} object.
     * @param fontObj    a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param descriptor a {@link org.loboevolution.pdfview.font.PDFFontDescriptor} object.
     * @param fontFile   a {@link java.io.File} object.
     * @throws java.io.IOException if any.
     */
    public TTFFont(final String baseFont, final PDFObject fontObj,
                   final PDFFontDescriptor descriptor, final File fontFile)
            throws IOException {
        super(baseFont, fontObj, descriptor);

        final PDFObject ttfObj = descriptor.getFontFile2();

        if (ttfObj != null || fontFile != null) {
            if (ttfObj != null) {
                font = TrueTypeFont.parseFont(ttfObj.getStreamBuffer());
            }
            if (fontFile != null) {
                final RandomAccessFile raFile = new RandomAccessFile(fontFile, "r");
                try (raFile; FileChannel fc = raFile.getChannel()) {
                    MappedByteBuffer mappedFont = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                    font = TrueTypeFont.parseFont(mappedFont);
                    mappedFont = null;
                }
            }
            // read the units per em from the head table
            final HeadTable head = (HeadTable) font.getTable("head");
            unitsPerEm = head.getUnitsPerEm();
        } else {
            font = null;
        }
    }

    /**
     * <p>getNames.</p>
     *
     * @return a {@link java.util.Collection} object.
     */
    public Collection<String> getNames() {
        return font.getNames();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the outline of a character given the character code
     */
    @Override
    protected synchronized GeneralPath getOutline(final char src, final float width) {
        // find the cmaps
        final CmapTable cmap = (CmapTable) this.font.getTable("cmap");

        // if there are no cmaps, this is (hopefully) a cid-mapped font,
        // so just trust the value we were given for src
        if (cmap == null) {
            return getOutline((int) src, width);
        }

        final CMap[] maps = cmap.getCMaps();

        // try the maps in order
        for (final CMap cMap2 : maps) {
            final int idx = cMap2.map(src);
            if (idx != 0) {
                return getOutline(idx, width);
            }
        }

        // windows symbol font CMap may use one of the following code ranges
        if (src >= 0 && src <= 0xFF) {
            final int[] symbolPages = new int[]{0xF000, 0xF100, 0xF200};
            for (final int codePage : symbolPages) {
                for (final CMap cMap2 : maps) {
                    final int idx = cMap2.map((char) (src | codePage));
                    if (idx != 0) {
                        return getOutline(idx, width);
                    }
                }
            }
        }

        // not found, return the empty glyph
        return getOutline(0, width);
    }

    /**
     * lookup the outline using the (3, 1) cmap, as specified in 32000-1:2008,
     * 9.6.6.4, when an Encoding is specified.
     *
     * @param val   a char.
     * @param width a float.
     * @return GeneralPath
     */
    protected synchronized GeneralPath getOutlineFrom31CMap(final char val,
                                                            final float width) {
        // find the cmaps
        final CmapTable cmap = (CmapTable) this.font.getTable("cmap");

        if (cmap == null) {
            return null;
        }

        // find the (3, 1) cmap subtable (Microsoft Unicode)
        final CMap map = cmap.getCMap((short) 3, (short) 1);
        if (map == null) {
            return null;
        }
        final int idx = map.map(val);
        if (idx != 0) {
            return getOutline(idx, width);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the outline of a character given the character name
     */
    @Override
    protected synchronized GeneralPath getOutline(final String name, final float width) {
        int idx;
        final PostTable post = (PostTable) this.font.getTable("post");
        if (post != null) {
            idx = post.getGlyphNameIndex(name);
            if (idx != 0) {
                return getOutline(idx, width);
            }
        }

        final Integer res = AdobeGlyphList.getGlyphNameIndex(name);
        if (res != null) {
            idx = res;
            return getOutlineFrom31CMap((char) idx, width);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the outline of a character given the glyph id
     */
    protected synchronized GeneralPath getOutline(final int glyphId, final float width) {
        // find the glyph itself
        final GlyfTable glyf = (GlyfTable) this.font.getTable("glyf");
        final Glyf g = glyf.getGlyph(glyphId);

        GeneralPath gp;
        if (g instanceof GlyfSimple) {
            gp = renderSimpleGlyph((GlyfSimple) g);
        } else if (g instanceof GlyfCompound) {
            gp = renderCompoundGlyph(glyf, (GlyfCompound) g);
        } else {
            gp = new GeneralPath();
        }

        // calculate the advance
        final HmtxTable hmtx = (HmtxTable) this.font.getTable("hmtx");
        final float advance = hmtx.getAdvance(glyphId) / this.unitsPerEm;

        // scale the glyph to match the desired advance
        final float widthfactor = width / advance;

        // the base transform scales the glyph to 1x1
        final AffineTransform at = AffineTransform.getScaleInstance(1 / this.unitsPerEm, 1 / this.unitsPerEm);
        if (advance != 0) {
            at.concatenate(AffineTransform.getScaleInstance(widthfactor, 1));
        }

        gp.transform(at);

        return gp;
    }

    /**
     * Render a simple glyf
     *
     * @param g a {@link org.loboevolution.pdfview.font.ttf.GlyfSimple} object.
     * @return a {@link java.awt.geom.GeneralPath} object.
     */
    protected GeneralPath renderSimpleGlyph(final GlyfSimple g) {
        // the current contour
        int curContour = 0;

        // the render state
        final RenderState rs = new RenderState();
        rs.gp = new GeneralPath();

        for (int i = 0; i < g.getNumPoints(); i++) {
            final PointRec rec = new PointRec(g, i);

            if (rec.onCurve) {
                addOnCurvePoint(rec, rs);
            } else {
                addOffCurvePoint(rec, rs);
            }

            // see if we just ended a contour
            if (i == g.getContourEndPoint(curContour)) {
                curContour++;

                if (rs.firstOff != null) {
                    addOffCurvePoint(rs.firstOff, rs);
                }

                if (rs.firstOn != null) {
                    addOnCurvePoint(rs.firstOn, rs);
                }

                rs.firstOn = null;
                rs.firstOff = null;
                rs.prevOff = null;
            }
        }

        return rs.gp;
    }

    /**
     * Render a compound glyf
     *
     * @param glyf a {@link org.loboevolution.pdfview.font.ttf.GlyfTable} object.
     * @param g    a {@link org.loboevolution.pdfview.font.ttf.GlyfCompound} object.
     * @return a {@link java.awt.geom.GeneralPath} object.
     */
    protected GeneralPath renderCompoundGlyph(final GlyfTable glyf, final GlyfCompound g) {
        final GeneralPath gp = new GeneralPath();

        for (int i = 0; i < g.getNumComponents(); i++) {
            // find and render the component glyf
            final Glyf gl = glyf.getGlyph(g.getGlyphIndex(i));
            GeneralPath path;
            if (gl instanceof GlyfSimple) {
                path = renderSimpleGlyph((GlyfSimple) gl);
            } else if (gl instanceof GlyfCompound) {
                path = renderCompoundGlyph(glyf, (GlyfCompound) gl);
            } else {
                throw new RuntimeException(
                        "Unsupported glyph type " + gl.getClass().getCanonicalName());
            }

            // multiply the translations by units per em
            final double[] matrix = g.getTransform(i);

            // transform the path
            path.transform(new AffineTransform(matrix));

            // add it to the global path
            gp.append(path, false);
        }

        return gp;
    }

    /**
     * add a point on the curve
     */
    private void addOnCurvePoint(final PointRec rec, final RenderState rs) {
        // if the point is on the curve, either move to it,
        // or draw a line from the previous point
        if (rs.firstOn == null) {
            rs.firstOn = rec;
            rs.gp.moveTo(rec.x, rec.y);
        } else if (rs.prevOff != null) {
            rs.gp.quadTo(rs.prevOff.x, rs.prevOff.y, rec.x, rec.y);
            rs.prevOff = null;
        } else {
            rs.gp.lineTo(rec.x, rec.y);
        }
    }

    /**
     * add a point off the curve
     */
    private void addOffCurvePoint(final PointRec rec, final RenderState rs) {
        if (rs.prevOff != null) {
            final PointRec oc = new PointRec((rec.x + rs.prevOff.x) / 2,
                    (rec.y + rs.prevOff.y) / 2,
                    true);
            addOnCurvePoint(oc, rs);
        } else if (rs.firstOn == null) {
            rs.firstOff = rec;
        }
        rs.prevOff = rec;
    }

    static class RenderState {
        // the shape itself

        GeneralPath gp;
        // the first off and on-curve points in the current segment

        PointRec firstOn;

        PointRec firstOff;
        // the previous off and on-curve points in the current segment

        PointRec prevOff;

    }

    /**
     * a point on the stack of points
     */
    static class PointRec {

        final int x;

        final int y;

        final boolean onCurve;

        public PointRec(final int x, final int y, final boolean onCurve) {
            this.x = x;
            this.y = y;
            this.onCurve = onCurve;
        }

        public PointRec(final GlyfSimple g, final int idx) {
            this.x = g.getXCoord(idx);
            this.y = g.getYCoord(idx);
            this.onCurve = g.onCurve(idx);
        }
    }
}
