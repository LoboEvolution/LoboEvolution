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

package org.loboevolution.pdfview.action;

import org.loboevolution.pdfview.PDFDestination;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import java.io.IOException;

/**
 * **************************************************************************
 * Utility class for parsing values from a PDFObject
 * <p>
 * Author  Katja Sondermann
 *
 * @since 08.07.2009
 * **************************************************************************
 */
public class PdfObjectParseUtil {

    /**
     * **********************************************************************
     * Parse a String value with the given key from parent object. If it's mandatory
     * and not available, an exception will be thrown.
     *
     * @param key       a {@link java.lang.String} object.
     * @param parent    a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param mandatory a boolean.
     * @return String - can be <code>null</code> if not mandatory
     * @throws java.io.IOException if any.
     */
    public static String parseStringFromDict(final String key, final PDFObject parent, final boolean mandatory) throws IOException {
        PDFObject val = parent;
        while (val.getType() == PDFObject.DICTIONARY) {
            val = val.getDictRef(key);
            if (val == null) {
                if (mandatory) {
                    throw new PDFParseException(key + "value could not be parsed : " + parent);
                }
                return null;
            }
        }
        return val.getStringValue();
    }

    /**
     * **********************************************************************
     * Parse a Boolean value with the given key from parent object. If it's mandatory
     * and not available, an exception will be thrown.
     *
     * @param key       a {@link java.lang.String} object.
     * @param parent    a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param mandatory a boolean.
     * @return boolean - <code>false</code> if not available and not mandatory
     * @throws java.io.IOException if any.
     */
    public static boolean parseBooleanFromDict(final String key, final PDFObject parent, final boolean mandatory) throws IOException {
        final PDFObject val = parent.getDictRef(key);
        if (val == null) {
            if (mandatory) {
                throw new PDFParseException(key + "value could not be parsed : " + parent);
            }
            return false;
        }
        return val.getBooleanValue();
    }

    /**
     * **********************************************************************
     * Parse a integer value with the given key from parent object. If it's mandatory
     * and not available, an exception will be thrown.
     *
     * @param key       a {@link java.lang.String} object.
     * @param parent    a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param mandatory a boolean.
     * @return int - returns "0" in case the value is not a number
     * @throws java.io.IOException if any.
     */
    public static int parseIntegerFromDict(final String key, final PDFObject parent, final boolean mandatory) throws IOException {
        final PDFObject val = parent.getDictRef(key);
        if (val == null) {
            if (mandatory) {
                throw new PDFParseException(key + "value could not be parsed : " + parent);
            }
            return 0;
        }
        return val.getIntValue();
    }

    /**
     * **********************************************************************
     * Parse a destination object
     *
     * @param key       a {@link java.lang.String} object.
     * @param parent    a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param root      a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param mandatory a boolean.
     * @return PDFDestination  - can be <code>null</code> if not mandatory
     * @throws java.io.IOException if any.
     */
    public static PDFDestination parseDestination(final String key, final PDFObject parent, final PDFObject root, final boolean mandatory) throws IOException {
        final PDFObject destObj = parent.getDictRef(key);
        if (destObj == null) {
            if (mandatory) {
                throw new PDFParseException("Error parsing destination " + parent);
            }
            return null;
        }
        return PDFDestination.getDestination(destObj, root);

    }
}
