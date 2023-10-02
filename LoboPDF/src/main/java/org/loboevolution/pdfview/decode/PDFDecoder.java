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
package org.loboevolution.pdfview.decode;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;
import org.loboevolution.pdfview.decrypt.PDFDecrypterFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A PDF Decoder encapsulates all the methods of decoding a stream of bytes
 * based on all the various encoding methods.
 * <p>
 * You should use the decodeStream() method of this object rather than using
 * any of the decoders directly.
 */
public class PDFDecoder {

    /**
     * Constant <code>DCT_FILTERS</code>
     */
    public static final Set<String> DCT_FILTERS = new HashSet<>(Arrays.asList("DCT", "DCTDecode"));

    /**
     * Creates a new instance of PDFDecoder
     */
    private PDFDecoder() {
    }

    /**
     * <p>isLastFilter.</p>
     *
     * @param dict    a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param filters a {@link java.util.Set} object.
     * @return a boolean.
     * @throws java.io.IOException if any.
     */
    public static boolean isLastFilter(final PDFObject dict, final Set<String> filters) throws IOException {
        final PDFObject filter = dict.getDictRef("Filter");
        if (filter == null) {
            return false;
        } else if (filter.getType() == PDFObject.NAME) {
            return filters.contains(filter.getStringValue());
        } else {
            final PDFObject[] ary = filter.getArray();
            return filters.contains(ary[ary.length - 1].getStringValue());
        }
    }

    /**
     * decode a byte[] stream using the filters specified in the object's
     * dictionary (passed as argument 1).
     *
     * @param dict         the dictionary associated with the stream
     * @param streamBuf    the data in the stream, as a byte buffer
     * @param filterLimits a {@link java.util.Set} object.
     * @return a {@link java.nio.ByteBuffer} object.
     * @throws java.io.IOException if any.
     */
    public static ByteBuffer decodeStream(final PDFObject dict, ByteBuffer streamBuf, final Set<String> filterLimits)
            throws IOException {

        final PDFObject filter = dict.getDictRef("Filter");
        if (filter == null) {
            // just apply default decryption
            return dict.getDecrypter().decryptBuffer(null, dict, streamBuf);
        } else {
            // apply filters
            final FilterSpec spec = new FilterSpec(dict, filter);

            // determine whether default encryption applies or if there's a
            // specific Crypt filter; it must be the first filter according to
            // the errata for PDF1.7
            final boolean specificCryptFilter =
                    spec.ary.length != 0 && spec.ary[0].getStringValue().equals("Crypt");
            if (!specificCryptFilter) {
                // No Crypt filter, so should apply default decryption (if
                // present!)
                streamBuf = dict.getDecrypter().decryptBuffer(
                        null, dict, streamBuf);
            }

            for (int i = 0; i < spec.ary.length; i++) {
                final String enctype = spec.ary[i].getStringValue();
                try {
                    if (filterLimits.contains(enctype)) {
                        break;
                    }
                    if (enctype == null) {
                    } else if (enctype.equals("FlateDecode") || enctype.equals("Fl")) {
                        streamBuf = FlateDecode.decode(dict, streamBuf, spec.params[i]);
                    } else if (enctype.equals("LZWDecode") || enctype.equals("LZW")) {
                        streamBuf = LZWDecode.decode(streamBuf, spec.params[i]);
                    } else if (enctype.equals("ASCII85Decode") || enctype.equals("A85")) {
                        streamBuf = ASCII85Decode.decode(streamBuf, spec.params[i]);
                    } else if (enctype.equals("ASCIIHexDecode") || enctype.equals("AHx")) {
                        streamBuf = ASCIIHexDecode.decode(streamBuf, spec.params[i]);
                    } else if (enctype.equals("RunLengthDecode") || enctype.equals("RL")) {
                        streamBuf = RunLengthDecode.decode(streamBuf, spec.params[i]);
                    } else if (enctype.equals("DCTDecode") || enctype.equals("DCT")) {
                        streamBuf = DCTDecode.decode(dict, streamBuf, spec.params[i]);
                    } else if (enctype.equals("JPXDecode")) {
                        streamBuf = JPXDecode.decode(dict, streamBuf, spec.params[i]);
                    } else if (enctype.equals("CCITTFaxDecode") || enctype.equals("CCF")) {
                        streamBuf = CCITTFaxDecode.decode(dict, streamBuf, spec.params[i]);
                    } else if (enctype.equals("Crypt")) {
                        String cfName = PDFDecrypterFactory.CF_IDENTITY;
                        if (spec.params[i] != null) {
                            final PDFObject nameObj = spec.params[i].getDictRef("Name");
                            if (nameObj != null && nameObj.getType() == PDFObject.NAME) {
                                cfName = nameObj.getStringValue();
                            }
                        }
                        streamBuf = dict.getDecrypter().decryptBuffer(cfName, null, streamBuf);
                    } else if (enctype.equals("JBIG2Decode")) {
                        streamBuf = JBig2Decode.decode(dict, streamBuf, spec.params[i]);
                    } else {
                        throw new PDFParseException("Unknown coding method:" + spec.ary[i].getStringValue());
                    }
                } catch (final Exception e) {
                    throw new PDFParseException("Problem decoding " + enctype + " encoded stream!", e);
                }

            }
        }

        return streamBuf;
    }

    /**
     * The name of the Crypt filter to apply
     *
     * @param param the parameters to the Crypt filter
     * @return the name of the crypt filter to apply
     * @throws IOException if there's a problem reading the objects
     */
    private static String getCryptFilterName(final PDFObject param) throws IOException {
        String cfName = PDFDecrypterFactory.CF_IDENTITY;
        if (param != null) {
            final PDFObject nameObj = param.getDictRef("Name");
            if (nameObj != null && nameObj.getType() == PDFObject.NAME) {
                cfName = nameObj.getStringValue();
            }
        }
        return cfName;
    }

    /**
     * Determines whether a stream is encrypted or not; note that encodings
     * (e.g., Flate, LZW) are not considered encryptions.
     *
     * @param dict the stream dictionary
     * @return whether the stream is encrypted
     * @throws java.io.IOException if any.
     */
    public static boolean isEncrypted(final PDFObject dict)
            throws IOException {

        final PDFObject filter = dict.getDictRef("Filter");
        if (filter == null) {
            // just apply default decryption
            return dict.getDecrypter().isEncryptionPresent();
        } else {

            // apply filters
            final FilterSpec spec = new FilterSpec(dict, filter);

            // determine whether default encryption applies or if there's a
            // specific Crypt filter; it must be the first filter according to
            // the errata for PDF1.7
            final boolean specificCryptFilter = spec.ary.length != 0 &&
                    spec.ary[0].getStringValue().equals("Crypt");
            if (!specificCryptFilter) {
                // No Crypt filter, so we just need to refer to
                // the default decrypter
                return dict.getDecrypter().isEncryptionPresent();
            } else {
                final String cfName = getCryptFilterName(spec.params[0]);
                // see whether the specified crypt filter really decrypts
                return dict.getDecrypter().isEncryptionPresent(cfName);
            }
        }

    }

    /**
     * Utility class for reading and storing the specification of
     * Filters on a stream
     */
    private static class FilterSpec {
        PDFObject[] ary;
        PDFObject[] params;

        private FilterSpec(final PDFObject dict, final PDFObject filter) throws IOException {
            if (filter.getType() == PDFObject.NAME) {
                ary = new PDFObject[1];
                ary[0] = filter;
                params = new PDFObject[1];
                params[0] = dict.getDictRef("DecodeParms");
            } else {
                ary = filter.getArray();
                final PDFObject parmsobj = dict.getDictRef("DecodeParms");
                if (parmsobj != null) {
                    params = parmsobj.getArray();
                } else {
                    params = new PDFObject[ary.length];
                }
            }
        }

    }

}
