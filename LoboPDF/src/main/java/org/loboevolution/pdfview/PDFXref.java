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

import java.lang.ref.SoftReference;

/**
 * a cross reference representing a line in the PDF cross referencing
 * table.
 * <p>
 * There are two forms of the PDFXref, destinguished by absolutely nothing.
 * The first type of PDFXref is used as indirect references in a PDFObject.
 * In this type, the id is an index number into the object cross reference
 * table.  The id will range from 0 to the size of the cross reference
 * table.
 * <p>
 * The second form is used in the Java representation of the cross reference
 * table.  In this form, the id is the file position of the start of the
 * object in the PDF file.  See the use of both of these in the
 * PDFFile.dereference() method, which takes a PDFXref of the first form,
 * and uses (internally) a PDFXref of the second form.
 * <p>
 * This is an unhappy state of affairs, and should be fixed.  Fortunatly,
 * the two uses have already been factored out as two different methods.
 * <p>
 * Author Mike Wessler
 */
public class PDFXref {

    private final int id;
    private final int generation;
    private final boolean compressed;

    // this field is only used in PDFFile.objIdx
    private SoftReference<PDFObject> reference = null;

    /**
     * create a new PDFXref, given a parsed id and generation.
     *
     * @param id  a int.
     * @param gen a int.
     */
    public PDFXref(final int id, final int gen) {
        this.id = id;
        this.generation = gen;
        this.compressed = false;
    }

    /**
     * create a new PDFXref, given a parsed id, compressedObjId and index
     *
     * @param id         a int.
     * @param gen        a int.
     * @param compressed a boolean.
     */
    public PDFXref(final int id, final int gen, final boolean compressed) {
        this.id = id;
        this.generation = gen;
        this.compressed = compressed;
    }

    /**
     * create a new PDFXref, given a sequence of bytes representing the
     * fixed-width cross reference table line
     *
     * @param line an array of {@link byte} objects.
     */
    public PDFXref(final byte[] line) {
        if (line == null) {
            this.id = -1;
            this.generation = -1;
        } else {
            this.id = Integer.parseInt(new String(line, 0, 10).trim());
            this.generation = Integer.parseInt(new String(line, 11, 5).trim());
        }
        this.compressed = false;
    }

    /**
     * get the character index into the file of the start of this object
     *
     * @return a int.
     */
    public int getFilePos() {
        return this.id;
    }

    /**
     * get the generation of this object
     *
     * @return a int.
     */
    public int getGeneration() {
        return this.generation;
    }

    /**
     * get the generation of this object
     *
     * @return a int.
     */
    public int getIndex() {
        return this.generation;
    }

    /**
     * get the object number of this object
     *
     * @return a int.
     */
    public int getID() {
        return this.id;
    }

    /**
     * get compressed flag of this object
     *
     * @return a boolean.
     */
    public boolean getCompressed() {
        return this.compressed;
    }


    /**
     * Get the object this reference refers to, or null if it hasn't been
     * set.
     *
     * @return the object if it exists, or null if not
     */
    public PDFObject getObject() {
        if (this.reference != null) {
            return this.reference.get();
        }

        return null;
    }

    /**
     * Set the object this reference refers to.
     *
     * @param obj a {@link org.loboevolution.pdfview.PDFObject} object.
     */
    public void setObject(final PDFObject obj) {
        this.reference = new SoftReference<>(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        return (obj instanceof PDFXref) &&
                ((PDFXref) obj).id == id &&
                ((PDFXref) obj).generation == generation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id ^ (generation << 8);
    }
}
