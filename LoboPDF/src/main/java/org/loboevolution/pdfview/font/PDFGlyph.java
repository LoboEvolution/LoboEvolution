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

import lombok.Data;
import org.loboevolution.pdfview.PDFPage;
import org.loboevolution.pdfview.PDFShapeCmd;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * A single glyph in a stream of PDF text, which knows how to write itself
 * onto a PDF command stream
 */
@Data
public class PDFGlyph {
    /**
     * the character code of this glyph
     */
    private final char src;
    /**
     * the name of this glyph
     */
    private final String name;
    /**
     * the advance from this glyph
     */
    private final Point2D advance;
    /**
     * the shape represented by this glyph (for all fonts but type 3)
     */
    private GeneralPath shape;
    /**
     * the PDFPage storing this glyph's commands (for type 3 fonts)
     */
    private PDFPage page;

    /**
     * Creates a new instance of PDFGlyph based on a shape
     *
     * @param src     a char.
     * @param name    a {@link java.lang.String} object.
     * @param shape   a {@link java.awt.geom.GeneralPath} object.
     * @param advance a {@link java.awt.geom.Point2D.Float} object.
     */
    public PDFGlyph(final char src, final String name, final GeneralPath shape, final Point2D.Float advance) {
        this.shape = shape;
        this.advance = advance;
        this.src = src;
        this.name = name;
    }

    /**
     * Creates a new instance of PDFGlyph based on a page
     *
     * @param src     a char.
     * @param name    a {@link java.lang.String} object.
     * @param page    a {@link org.loboevolution.pdfview.PDFPage} object.
     * @param advance a {@link java.awt.geom.Point2D} object.
     */
    public PDFGlyph(final char src, final String name, final PDFPage page, final Point2D advance) {
        this.page = page;
        this.advance = advance;
        this.src = src;
        this.name = name;
    }

    /**
     * Get the character code of this glyph
     *
     * @return a char.
     */
    public char getChar() {
        return this.src;
    }

    /**
     * Add commands for this glyph to a page
     *
     * @param cmds      a {@link org.loboevolution.pdfview.PDFPage} object.
     * @param transform a {@link java.awt.geom.AffineTransform} object.
     * @param mode      a {@link java.lang.Integer} object.
     * @return a {@link java.awt.geom.Point2D} object.
     */
    public Point2D addCommands(final PDFPage cmds, final AffineTransform transform, final int mode) {
        if (this.shape != null) {
            final GeneralPath outline = (GeneralPath) this.shape.createTransformedShape(transform);
            cmds.addCommand(new PDFShapeCmd(outline, mode, false));
        } else if (this.page != null) {
            cmds.addCommands(this.page, transform);
        }
        return this.advance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.name;
    }
}
