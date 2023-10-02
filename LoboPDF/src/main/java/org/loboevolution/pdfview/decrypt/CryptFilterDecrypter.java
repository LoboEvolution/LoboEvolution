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

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * Implements Version 4 standard decryption, whereby the Encrypt dictionary
 * contains a list of named 'crypt filters', each of which is the equivalent
 * of a {@link org.loboevolution.pdfview.decrypt.PDFDecrypter}. In addition to this list of crypt filters,
 * the name of the filter to use for streams and the default filter to use
 * for strings is specified. Requests to decode a stream with a named
 * decrypter (typically Identity) instead of the default decrypter
 * are honoured.
 * <p>
 * Author Luke Kirby
 */
public class CryptFilterDecrypter implements PDFDecrypter {

    /**
     * Maps from crypt filter names to their corresponding decrypters
     */
    private final Map<String, PDFDecrypter> decrypters;
    /**
     * The default decrypter for stream content
     */
    private final PDFDecrypter defaultStreamDecrypter;
    /**
     * The default decrypter for string content
     */
    private final PDFDecrypter defaultStringDecrypter;

    /**
     * Class constructor
     *
     * @param decrypters             a map of crypt filter names to their corresponding
     *                               decrypters. Must already contain the Identity filter.
     * @param defaultStreamCryptName the crypt filter name of the default
     *                               stream decrypter
     * @param defaultStringCryptName the crypt filter name of the default
     *                               string decrypter
     * @throws org.loboevolution.pdfview.PDFParseException if any.
     */
    public CryptFilterDecrypter(
            final Map<String, PDFDecrypter> decrypters,
            final String defaultStreamCryptName,
            final String defaultStringCryptName)
            throws PDFParseException {

        this.decrypters = decrypters;
        assert this.decrypters.containsKey("Identity") :
                "Crypt Filter map does not contain required Identity filter";
        defaultStreamDecrypter = this.decrypters.get(defaultStreamCryptName);
        if (defaultStreamDecrypter == null) {
            throw new PDFParseException(
                    "Unknown crypt filter specified as default for streams: " +
                            defaultStreamCryptName);
        }
        defaultStringDecrypter = this.decrypters.get(defaultStringCryptName);
        if (defaultStringDecrypter == null) {
            throw new PDFParseException(
                    "Unknown crypt filter specified as default for strings: " +
                            defaultStringCryptName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteBuffer decryptBuffer(
            final String cryptFilterName, final PDFObject streamObj, final ByteBuffer streamBuf)
            throws PDFParseException {
        final PDFDecrypter decrypter;
        if (cryptFilterName == null) {
            decrypter = defaultStreamDecrypter;
        } else {
            decrypter = decrypters.get(cryptFilterName);
            if (decrypter == null) {
                throw new PDFParseException("Unknown CryptFilter: " +
                        cryptFilterName);
            }
        }
        return decrypter.decryptBuffer(
                // elide the filter name to prevent V2 decrypters from
                // complaining about a crypt filter name
                null,
                // if there's a specific crypt filter being used then objNum
                // and objGen shouldn't contribute to the key, so we
                // should make sure that no streamObj makes its way through
                cryptFilterName != null ? null : streamObj,
                streamBuf);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String decryptString(final int objNum, final int objGen, final String inputBasicString)
            throws PDFParseException {
        return defaultStringDecrypter.decryptString(objNum, objGen, inputBasicString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEncryptionPresent() {
        for (final PDFDecrypter decrypter : decrypters.values()) {
            if (decrypter.isEncryptionPresent()) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEncryptionPresent(final String cryptFilterName) {
        final PDFDecrypter decrypter = decrypters.get(cryptFilterName);
        return decrypter != null && decrypter.isEncryptionPresent(cryptFilterName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOwnerAuthorised() {
        for (final PDFDecrypter decrypter : decrypters.values()) {
            if (decrypter.isOwnerAuthorised()) {
                return true;
            }
        }
        return false;
    }

}
