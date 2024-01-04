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
package org.loboevolution.pdfview;

import java.io.IOException;

/**
 * A PDF name tree consists of three kinds of nodes:
 * <ul>
 * <li> The root node contains only a kids entry, pointing to many
 *      other objects
 * <li> An intermediate node contains the limits of all the children in
 *      its subtree, and a kids entry for each child
 * <li> A leaf node contains a set of name-to-object mappings in a dictionary,
 *      as well as the limits of the data contained in that child.
 * </ul>
 * A PDF name tree is sorted in accordance with the String.compareTo() method.
 */
public class NameTree {

    /**
     * the root object
     */
    private final PDFObject root;

    /**
     * Creates a new instance of NameTree
     *
     * @param root a {@link org.loboevolution.pdfview.PDFObject} object.
     */
    public NameTree(final PDFObject root) {
        this.root = root;
    }

    /**
     * Find the PDF object corresponding to the given String in a name tree
     *
     * @param key the key we are looking for in the name tree
     * @return the object associated with str,  if found, or null if not
     * @throws java.io.IOException if any.
     */
    public PDFObject find(final String key) throws IOException {
        return find(root, key);
    }

    /**
     * Recursively walk the name tree looking for a given value
     */
    private PDFObject find(final PDFObject root, final String key)
            throws IOException {
        // first, look for a Names entry, meaning this is a leaf
        final PDFObject names = root.getDictRef("Names");
        if (names != null) {
            return findInArray(names.getArray(), key);
        }

        // no names given, look for kids
        final PDFObject kidsObj = root.getDictRef("Kids");
        if (kidsObj != null) {
            final PDFObject[] kids = kidsObj.getArray();
            for (final PDFObject pdfObject : kids) {
                // find the limits of this kid
                final PDFObject limitsObj = pdfObject.getDictRef("Limits");
                if (limitsObj != null) {
                    final String lowerLimit = limitsObj.getAt(0).getStringValue();
                    final String upperLimit = limitsObj.getAt(1).getStringValue();
                    // are we in range?
                    if ((key.compareTo(lowerLimit) >= 0) && (key.compareTo(upperLimit) <= 0)) {
                        // we are, so find in this child
                        return find(pdfObject, key);
                    }
                }
            }
        }

        // no luck
        return null;
    }

    /**
     * Find an object in a (key,value) array.  Do this by splitting in half
     * repeatedly.
     */
    private PDFObject findInArray(final PDFObject[] array, final String key)
            throws IOException {
        int start = 0;
        int end = array.length / 2;

        while (end >= start && start >= 0 && end < array.length) {
            // find the key at the midpoint
            final int pos = start + ((end - start) / 2);
            final String posKey = array[pos * 2].getStringValue();

            // compare the key to the key we are looking for
            final int comp = key.compareTo(posKey);
            if (comp == 0) {
                // they match.  Return the value
                final int tmp = (pos * 2) + 1;
                if (array.length > tmp) {
                    return array[tmp];
                } else {
                    return null;
                }
            } else if (comp > 0) {
                // too big, search the top half of the tree
                start = pos + 1;
            } else if (comp < 0) {
                // too small, search the bottom half of the tree
                end = pos - 1;
            }
        }

        // not found
        return null;
    }
}
