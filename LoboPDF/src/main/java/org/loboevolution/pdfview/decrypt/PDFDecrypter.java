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

package org.loboevolution.pdfview.decrypt;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;
import org.loboevolution.pdfview.PDFStringUtil;

import java.nio.ByteBuffer;

/**
 * A decrypter decrypts streams and strings in a PDF document. {@link
 * #decryptBuffer(String, PDFObject, ByteBuffer)} } should be used for decoding
 * streams, and {@link #decryptString(int, int, String)} for string values in
 * the PDF. It is possible for strings and streams to be encrypted with
 * different mechanisms, so the appropriate method must alwayus be used.
 * <p>
 * see "PDFReference 1.7, Section 3.5 Encryption"
 * Author Luke Kirby
 */
public interface PDFDecrypter {

    /**
     * Decrypt a buffer of data
     *
     * @param cryptFilterName the name of the crypt filter, if V4
     *                        encryption is being used, where individual crypt filters may
     *                        be specified for individual streams. If encryption is not using
     *                        V4 encryption (indicated by V=4 in the Encrypt dictionary) then
     *                        this must be null. Null may also be specified with V4 encryption
     *                        to indicate that the default filter should be used.
     * @param streamObj       the object whose stream is being decrypted. The
     *                        containing object's number and generation contribute to the key used for
     *                        stream encrypted with the document's default encryption, so this is
     *                        typically required. Should be null only if a cryptFilterName is
     *                        specified, as objects with specific stream filters use the general
     *                        document key, rather than a stream-specific key.
     * @param streamBuf       the buffer to decrypt
     * @return a buffer containing the decrypted stream, positioned at its
     * beginning; will only be the same buffer as streamBuf if the identity
     * decrypter is being used
     * @throws org.loboevolution.pdfview.PDFParseException if any.
     */
    ByteBuffer decryptBuffer(
            String cryptFilterName,
            PDFObject streamObj,
            ByteBuffer streamBuf)
            throws PDFParseException;

    /**
     * Decrypt a {@link PDFStringUtil basic string}.
     *
     * @param objNum           the object number of the containing object
     * @param objGen           the generation number of the containing object
     * @param inputBasicString the string to be decrypted
     * @return the decrypted string
     * @throws org.loboevolution.pdfview.PDFParseException if any.
     */
    String decryptString(int objNum, final int objGen, final String inputBasicString)
            throws PDFParseException;

    /**
     * Determine whether the password known by the decrypter indicates that
     * the user is the owner of the document. Can be used, in conjunction
     * with {@link #isEncryptionPresent()} to determine whether any
     * permissions apply.
     *
     * @return whether owner authentication is being used to decrypt the
     * document
     */
    boolean isOwnerAuthorised();

    /**
     * Determine whether this actually applies a decryption other than
     * identity decryption.
     *
     * @return whether encryption is present
     */
    boolean isEncryptionPresent();

    /**
     * Determines whether decryption applies for a given crypt filter name
     *
     * @param cryptFilterName the crypt filter name
     * @return whether the given crypt filter decrypts or not
     */
    boolean isEncryptionPresent(final String cryptFilterName);
}
