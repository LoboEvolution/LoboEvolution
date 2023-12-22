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

import lombok.Data;

import java.io.IOException;

/**
 * Represents a destination in a PDF file. Destinations take 3 forms:
 * <ul>
 * <li>An explicit destination, which contains a reference to a page as well as
 * some stuff about how to fit it into the window.
 * <li>A named destination, which uses the PDF file's Dests entry in the
 * document catalog to map a name to an explicit destination
 * <li>A string destintation, which uses the PDF file's Dests entry. in the name
 * directory to map a string to an explicit destination.
 * </ul>
 * <p>
 * All three of these cases are handled by the getDestination() method.
 */
@Data
public class PDFDestination {

    /**
     * The known types of destination
     */
    public static final int XYZ = 0;
    /**
     * Constant <code>FIT=1</code>
     */
    public static final int FIT = 1;
    /**
     * Constant <code>FITH=2</code>
     */
    public static final int FITH = 2;
    /**
     * Constant <code>FITV=3</code>
     */
    public static final int FITV = 3;
    /**
     * Constant <code>FITR=4</code>
     */
    public static final int FITR = 4;
    /**
     * Constant <code>FITB=5</code>
     */
    public static final int FITB = 5;
    /**
     * Constant <code>FITBH=6</code>
     */
    public static final int FITBH = 6;
    /**
     * Constant <code>FITBV=7</code>
     */
    public static final int FITBV = 7;
    /**
     * the type of this destination (from the list above)
     */
    private final int type;
    /**
     * the page we refer to
     */
    private final PDFObject pageObj;
    /**
     * the left coordinate of the fit area, if applicable
     */
    private float left;
    /**
     * the right coordinate of the fit area, if applicable
     */
    private float right;
    /**
     * the top coordinate of the fit area, if applicable
     */
    private float top;
    /**
     * the bottom coordinate of the fit area, if applicable
     */
    private float bottom;
    /**
     * the zoom, if applicable
     */
    private float zoom;

    /**
     * Creates a new instance of PDFDestination
     *
     * @param pageObj the page object this destination refers to
     * @param type    the type of page this object refers to
     */
    protected PDFDestination(final PDFObject pageObj, final int type) {
        this.pageObj = pageObj;
        this.type = type;
    }

    /**
     * Get a destination from either an array (explicit destination), a name
     * (named destination) or a string (name tree destination).
     *
     * @param pdfObj  the PDFObject representing this destination
     * @param root the root of the PDF object tree
     * @return a {@link org.loboevolution.pdfview.PDFDestination} object.
     * @throws java.io.IOException if any.
     */
    public static PDFDestination getDestination(final PDFObject pdfObj, final PDFObject root) throws IOException {
        PDFObject obj = pdfObj;
        // resolve string and name issues
        if (obj.getType() == PDFObject.NAME) {
            obj = getDestFromName(obj, root);
        } else if (obj.getType() == PDFObject.STRING) {
            obj = getDestFromString(obj, root);
        }

        // make sure we have the right kind of object
        if (obj == null || obj.getType() != PDFObject.ARRAY) {
            throw new PDFParseException("Can't create destination from: " + obj);
        }

        // the array is in the form [page type args ... ]
        final PDFObject[] destArray = obj.getArray();

        // create the destination based on the type
        PDFDestination dest;
        final String type = destArray[1].getStringValue();
        switch (type) {
            case "XYZ":
                dest = new PDFDestination(destArray[0], XYZ);
                break;
            case "Fit":
                dest = new PDFDestination(destArray[0], FIT);
                break;
            case "FitH":
                dest = new PDFDestination(destArray[0], FITH);
                break;
            case "FitV":
                dest = new PDFDestination(destArray[0], FITV);
                break;
            case "FitR":
                dest = new PDFDestination(destArray[0], FITR);
                break;
            case "FitB":
                dest = new PDFDestination(destArray[0], FITB);
                break;
            case "FitBH":
                dest = new PDFDestination(destArray[0], FITBH);
                break;
            case "FitBV":
                dest = new PDFDestination(destArray[0], FITBV);
                break;
            default:
                throw new PDFParseException("Unknown destination type: " + type);
        }

        // now fill in the arguments based on the type
        switch (dest.getType()) {
            case XYZ:
                dest.setLeft(destArray[2].getFloatValue());
                dest.setTop(destArray[3].getFloatValue());
                dest.setZoom(destArray[4].getFloatValue());
                break;
            case FITH:
            case FITBV:
            case FITBH:
            case FITV:
                if (destArray.length > 2) {
                    dest.setTop(destArray[2].getFloatValue());
                } else {
                    dest.setTop(0.0F);
                }
                break;
            case FITR:
                dest.setLeft(destArray[2].getFloatValue());
                dest.setBottom(destArray[3].getFloatValue());
                dest.setRight(destArray[4].getFloatValue());
                dest.setTop(destArray[5].getFloatValue());
                break;
            default:
                break;
        }

        return dest;
    }

    /**
     * Get a destination, given a name. This means the destination is in the
     * root node's dests dictionary.
     */
    private static PDFObject getDestFromName(final PDFObject name, final PDFObject root) throws IOException {
        // find the dests object in the root node
        final PDFObject dests = root.getDictRef("Dests");
        if (dests != null) {
            // find this name in the dests dictionary
            return dests.getDictRef(name.getStringValue());
        }

        // not found
        return null;
    }

    /**
     * Get a destination, given a string. This means the destination is in the
     * root node's names dictionary.
     */
    private static PDFObject getDestFromString(final PDFObject str, final PDFObject root) throws IOException {
        // find the names object in the root node
        final PDFObject names = root.getDictRef("Names");
        if (names != null) {
            // find the dests entry in the names dictionary
            final PDFObject dests = names.getDictRef("Dests");
            if (dests != null) {
                // create a name tree object
                final NameTree tree = new NameTree(dests);

                // find the value we're looking for
                PDFObject obj = tree.find(str.getStringValue());

                // if we get back a dictionary, look for the /D value
                if (obj != null && obj.getType() == PDFObject.DICTIONARY) {
                    obj = obj.getDictRef("D");
                }

                // found it
                return obj;
            }
        }

        // not found
        return null;
    }
}
