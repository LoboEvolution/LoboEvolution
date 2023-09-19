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

import java.nio.ByteBuffer;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

/**
 * Performs identity decryption; that is, inputs aren't encrypted and
 * are returned right back.
 *
 * Author Luke Kirby
  *
  *
 */
public class IdentityDecrypter implements PDFDecrypter {

    private static final IdentityDecrypter INSTANCE = new IdentityDecrypter();

	/** {@inheritDoc} */
    @Override
	public ByteBuffer decryptBuffer(String cryptFilterName,
            PDFObject streamObj, ByteBuffer streamBuf)
            throws PDFParseException {

        if (cryptFilterName != null) {
            throw new PDFParseException("This Encryption version does not support Crypt filters");
        }

        return streamBuf;
    }

	/** {@inheritDoc} */
    @Override
	public String decryptString(int objNum, int objGen, String inputBasicString) {
        return inputBasicString;
    }

    /**
     * <p>getInstance.</p>
     *
     * @return a {@link org.loboevolution.pdfview.decrypt.IdentityDecrypter} object.
     */
    public static IdentityDecrypter getInstance() {
        return INSTANCE;
    }

	/** {@inheritDoc} */
    @Override
	public boolean isEncryptionPresent() {
        return false;
    }

	/** {@inheritDoc} */
    @Override
	public boolean isEncryptionPresent(String cryptFilterName) {
        return false;
    }

	/** {@inheritDoc} */
    @Override
	public boolean isOwnerAuthorised() {
        return false;
    }
}
