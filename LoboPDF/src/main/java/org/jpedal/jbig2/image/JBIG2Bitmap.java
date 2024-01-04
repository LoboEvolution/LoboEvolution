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
package org.jpedal.jbig2.image;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.JBIG2Exception;
import org.jpedal.jbig2.decoders.*;
import org.jpedal.jbig2.util.BinaryOperation;

import java.awt.image.*;
import java.io.IOException;

/**
 * <p>JBIG2Bitmap class.</p>
 */
@Slf4j
@Data
public final class JBIG2Bitmap {
    private final int width;
    private final int line;
    private final ArithmeticDecoder arithmeticDecoder;
    private final HuffmanDecoder huffmanDecoder;
    private final MMRDecoder mmrDecoder;

    //private BitSet data;

    //private static int counter = 0;
    public FastBitSet data;
    private int height;
    private int bitmapNumber;

    /**
     * <p>Constructor for JBIG2Bitmap.</p>
     *
     * @param width             a {@link java.lang.Integer} object.
     * @param height            a {@link java.lang.Integer} object.
     * @param arithmeticDecoder a {@link org.jpedal.jbig2.decoders.ArithmeticDecoder} object.
     * @param huffmanDecoder    a {@link org.jpedal.jbig2.decoders.HuffmanDecoder} object.
     * @param mmrDecoder        a {@link org.jpedal.jbig2.decoders.MMRDecoder} object.
     */
    public JBIG2Bitmap(final int width, final int height, final ArithmeticDecoder arithmeticDecoder, final HuffmanDecoder huffmanDecoder, final MMRDecoder mmrDecoder) {
        this.width = width;
        this.height = height;
        this.arithmeticDecoder = arithmeticDecoder;
        this.huffmanDecoder = huffmanDecoder;
        this.mmrDecoder = mmrDecoder;

        this.line = (width + 7) >> 3;

        this.data = new FastBitSet(width * height);
    }

    /**
     * <p>readBitmap.</p>
     *
     * @param useMMR                             a boolean.
     * @param template                           a {@link java.lang.Integer} object.
     * @param typicalPredictionGenericDecodingOn a boolean.
     * @param useSkip                            a boolean.
     * @param skipBitmap                         a {@link org.jpedal.jbig2.image.JBIG2Bitmap} object.
     * @param adaptiveTemplateX                  an array of {@link short} objects.
     * @param adaptiveTemplateY                  an array of {@link short} objects.
     * @param mmrDataLength                      a {@link java.lang.Integer} object.
     * @throws java.io.IOException             if any.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void readBitmap(final boolean useMMR, final int template, final boolean typicalPredictionGenericDecodingOn, final boolean useSkip, final JBIG2Bitmap skipBitmap, final short[] adaptiveTemplateX, final short[] adaptiveTemplateY, final int mmrDataLength) throws IOException, JBIG2Exception {

        if (useMMR) {

            //MMRDecoder mmrDecoder = MMRDecoder.getInstance();
            mmrDecoder.reset();

            final int[] referenceLine = new int[width + 2];
            final int[] codingLine = new int[width + 2];
            codingLine[0] = codingLine[1] = width;

            for (int row = 0; row < height; row++) {

                int i = 0;
                for (; codingLine[i] < width; i++) {
                    referenceLine[i] = codingLine[i];
                }
                referenceLine[i] = referenceLine[i + 1] = width;

                int referenceI = 0;
                int codingI = 0;
                int a0 = 0;

                do {
                    int code1 = mmrDecoder.get2DCode(), code2, code3;

                    switch (code1) {
                        case MMRDecoder.twoDimensionalPass:
                            if (referenceLine[referenceI] < width) {
                                a0 = referenceLine[referenceI + 1];
                                referenceI += 2;
                            }
                            break;
                        case MMRDecoder.twoDimensionalHorizontal:
                            if ((codingI & 1) != 0) {
                                code1 = 0;
                                do {
                                    code1 += code3 = mmrDecoder.getBlackCode();
                                } while (code3 >= 64);

                                code2 = 0;
                                do {
                                    code2 += code3 = mmrDecoder.getWhiteCode();
                                } while (code3 >= 64);
                            } else {
                                code1 = 0;
                                do {
                                    code1 += code3 = mmrDecoder.getWhiteCode();
                                } while (code3 >= 64);

                                code2 = 0;
                                do {
                                    code2 += code3 = mmrDecoder.getBlackCode();
                                } while (code3 >= 64);

                            }
                            if (code1 > 0 || code2 > 0) {
                                a0 = codingLine[codingI++] = a0 + code1;
                                a0 = codingLine[codingI++] = a0 + code2;

                                while (referenceLine[referenceI] <= a0 && referenceLine[referenceI] < width) {
                                    referenceI += 2;
                                }
                            }
                            break;
                        case MMRDecoder.twoDimensionalVertical0:
                            a0 = codingLine[codingI++] = referenceLine[referenceI];
                            if (referenceLine[referenceI] < width) {
                                referenceI++;
                            }

                            break;
                        case MMRDecoder.twoDimensionalVerticalR1:
                            a0 = codingLine[codingI++] = referenceLine[referenceI] + 1;
                            if (referenceLine[referenceI] < width) {
                                referenceI++;
                                while (referenceLine[referenceI] <= a0 && referenceLine[referenceI] < width) {
                                    referenceI += 2;
                                }
                            }

                            break;
                        case MMRDecoder.twoDimensionalVerticalR2:
                            a0 = codingLine[codingI++] = referenceLine[referenceI] + 2;
                            if (referenceLine[referenceI] < width) {
                                referenceI++;
                                while (referenceLine[referenceI] <= a0 && referenceLine[referenceI] < width) {
                                    referenceI += 2;
                                }
                            }

                            break;
                        case MMRDecoder.twoDimensionalVerticalR3:
                            a0 = codingLine[codingI++] = referenceLine[referenceI] + 3;
                            if (referenceLine[referenceI] < width) {
                                referenceI++;
                                while (referenceLine[referenceI] <= a0 && referenceLine[referenceI] < width) {
                                    referenceI += 2;
                                }
                            }

                            break;
                        case MMRDecoder.twoDimensionalVerticalL1:
                            a0 = codingLine[codingI++] = referenceLine[referenceI] - 1;
                            if (referenceI > 0) {
                                referenceI--;
                            } else {
                                referenceI++;
                            }

                            while (referenceLine[referenceI] <= a0 && referenceLine[referenceI] < width) {
                                referenceI += 2;
                            }

                            break;
                        case MMRDecoder.twoDimensionalVerticalL2:
                            a0 = codingLine[codingI++] = referenceLine[referenceI] - 2;
                            if (referenceI > 0) {
                                referenceI--;
                            } else {
                                referenceI++;
                            }

                            while (referenceLine[referenceI] <= a0 && referenceLine[referenceI] < width) {
                                referenceI += 2;
                            }

                            break;
                        case MMRDecoder.twoDimensionalVerticalL3:
                            a0 = codingLine[codingI++] = referenceLine[referenceI] - 3;
                            if (referenceI > 0) {
                                referenceI--;
                            } else {
                                referenceI++;
                            }

                            while (referenceLine[referenceI] <= a0 && referenceLine[referenceI] < width) {
                                referenceI += 2;
                            }

                            break;
                        default:
                            if (JBIG2StreamDecoder.debug)
                                log.info("Illegal code in JBIG2 MMR bitmap data");

                            break;
                    }
                } while (a0 < width);

                codingLine[codingI++] = width;

                for (int j = 0; codingLine[j] < width; j += 2) {
                    for (int col = codingLine[j]; col < codingLine[j + 1]; col++) {
                        setPixel(col, row, 1);
                    }
                }
            }

            if (mmrDataLength >= 0) {
                mmrDecoder.skipTo(mmrDataLength);
            } else {
                if (mmrDecoder.get24Bits() != 0x001001) {
                    if (JBIG2StreamDecoder.debug)
                        log.info("Missing EOFB in JBIG2 MMR bitmap data");
                }
            }

        } else {

            //ArithmeticDecoder arithmeticDecoder = ArithmeticDecoder.getInstance();

            final BitmapPointer cxPtr0 = new BitmapPointer(this);
            final BitmapPointer cxPtr1 = new BitmapPointer(this);
            final BitmapPointer atPtr0 = new BitmapPointer(this);
            final BitmapPointer atPtr1 = new BitmapPointer(this);
            final BitmapPointer atPtr2 = new BitmapPointer(this);
            final BitmapPointer atPtr3 = new BitmapPointer(this);

            long ltpCX = 0;
            if (typicalPredictionGenericDecodingOn) {
                switch (template) {
                    case 0:
                        ltpCX = 0x3953;
                        break;
                    case 1:
                        ltpCX = 0x079a;
                        break;
                    case 2:
                        ltpCX = 0x0e3;
                        break;
                    case 3:
                        ltpCX = 0x18a;
                        break;
                    default:
                        break;
                }
            }

            boolean ltp = false;
            long cx, cx0, cx1, cx2;

            for (int row = 0; row < height; row++) {
                if (typicalPredictionGenericDecodingOn) {
                    final int bit = arithmeticDecoder.decodeBit(ltpCX, arithmeticDecoder.genericRegionStats);
                    if (bit != 0) {
                        ltp = !ltp;
                    }

                    if (ltp) {
                        duplicateRow(row, row - 1);
                        continue;
                    }
                }

                int pixel;

                switch (template) {
                    case 0:

                        cxPtr0.setPointer(0, row - 2);
                        cx0 = (cxPtr0.nextPixel() << 1);
                        cx0 |= cxPtr0.nextPixel();
                        //cx0 = (BinaryOperation.bit32ShiftL(cx0, 1)) | cxPtr0.nextPixel();

                        cxPtr1.setPointer(0, row - 1);
                        cx1 = (cxPtr1.nextPixel() << 2);
                        cx1 |= (cxPtr1.nextPixel() << 1);
                        cx1 |= (cxPtr1.nextPixel());

                        //cx1 = (BinaryOperation.bit32ShiftL(cx1, 1)) | cxPtr1.nextPixel();
                        //cx1 = (BinaryOperation.bit32ShiftL(cx1, 1)) | cxPtr1.nextPixel();

                        cx2 = 0;

                        atPtr0.setPointer(adaptiveTemplateX[0], row + adaptiveTemplateY[0]);
                        atPtr1.setPointer(adaptiveTemplateX[1], row + adaptiveTemplateY[1]);
                        atPtr2.setPointer(adaptiveTemplateX[2], row + adaptiveTemplateY[2]);
                        atPtr3.setPointer(adaptiveTemplateX[3], row + adaptiveTemplateY[3]);

                        for (int col = 0; col < width; col++) {

                            cx = (BinaryOperation.bit32ShiftL(cx0, 13)) | (BinaryOperation.bit32ShiftL(cx1, 8)) | (BinaryOperation.bit32ShiftL(cx2, 4)) | (atPtr0.nextPixel() << 3) | (atPtr1.nextPixel() << 2) | (atPtr2.nextPixel() << 1) | atPtr3.nextPixel();

                            if (useSkip && skipBitmap.getPixel(col, row) != 0) {
                                pixel = 0;
                            } else {
                                pixel = arithmeticDecoder.decodeBit(cx, arithmeticDecoder.genericRegionStats);
                                if (pixel != 0) {
                                    data.set(row * width + col);
                                }
                            }

                            cx0 = ((BinaryOperation.bit32ShiftL(cx0, 1)) | cxPtr0.nextPixel()) & 0x07;
                            cx1 = ((BinaryOperation.bit32ShiftL(cx1, 1)) | cxPtr1.nextPixel()) & 0x1f;
                            cx2 = ((BinaryOperation.bit32ShiftL(cx2, 1)) | pixel) & 0x0f;
                        }
                        break;

                    case 1:

                        cxPtr0.setPointer(0, row - 2);
                        cx0 = (cxPtr0.nextPixel() << 2);
                        cx0 |= (cxPtr0.nextPixel() << 1);
                        cx0 |= (cxPtr0.nextPixel());
					/*cx0 = cxPtr0.nextPixel();
					cx0 = (BinaryOperation.bit32ShiftL(cx0, 1)) | cxPtr0.nextPixel();
					cx0 = (BinaryOperation.bit32ShiftL(cx0, 1)) | cxPtr0.nextPixel();*/

                        cxPtr1.setPointer(0, row - 1);
                        cx1 = (cxPtr1.nextPixel() << 2);
                        cx1 |= (cxPtr1.nextPixel() << 1);
                        cx1 |= (cxPtr1.nextPixel());
					/*cx1 = cxPtr1.nextPixel();
					cx1 = (BinaryOperation.bit32ShiftL(cx1, 1)) | cxPtr1.nextPixel();
					cx1 = (BinaryOperation.bit32ShiftL(cx1, 1)) | cxPtr1.nextPixel();*/

                        cx2 = 0;

                        atPtr0.setPointer(adaptiveTemplateX[0], row + adaptiveTemplateY[0]);

                        for (int col = 0; col < width; col++) {

                            cx = (BinaryOperation.bit32ShiftL(cx0, 9)) | (BinaryOperation.bit32ShiftL(cx1, 4)) | (BinaryOperation.bit32ShiftL(cx2, 1)) | atPtr0.nextPixel();

                            if (useSkip && skipBitmap.getPixel(col, row) != 0) {
                                pixel = 0;
                            } else {
                                pixel = arithmeticDecoder.decodeBit(cx, arithmeticDecoder.genericRegionStats);
                                if (pixel != 0) {
                                    data.set(row * width + col);
                                }
                            }

                            cx0 = ((BinaryOperation.bit32ShiftL(cx0, 1)) | cxPtr0.nextPixel()) & 0x0f;
                            cx1 = ((BinaryOperation.bit32ShiftL(cx1, 1)) | cxPtr1.nextPixel()) & 0x1f;
                            cx2 = ((BinaryOperation.bit32ShiftL(cx2, 1)) | pixel) & 0x07;
                        }
                        break;

                    case 2:

                        cxPtr0.setPointer(0, row - 2);
                        cx0 = (cxPtr0.nextPixel() << 1);
                        cx0 |= (cxPtr0.nextPixel());
					/*cx0 = cxPtr0.nextPixel();
					cx0 = (BinaryOperation.bit32ShiftL(cx0, 1)) | cxPtr0.nextPixel();
					*/

                        cxPtr1.setPointer(0, row - 1);
                        cx1 = (cxPtr1.nextPixel() << 1);
                        cx1 |= (cxPtr1.nextPixel());
					/*cx1 = cxPtr1.nextPixel();
					cx1 = (BinaryOperation.bit32ShiftL(cx1, 1)) | cxPtr1.nextPixel();*/

                        cx2 = 0;

                        atPtr0.setPointer(adaptiveTemplateX[0], row + adaptiveTemplateY[0]);

                        for (int col = 0; col < width; col++) {

                            cx = (BinaryOperation.bit32ShiftL(cx0, 7)) | (BinaryOperation.bit32ShiftL(cx1, 3)) | (BinaryOperation.bit32ShiftL(cx2, 1)) | atPtr0.nextPixel();

                            if (useSkip && skipBitmap.getPixel(col, row) != 0) {
                                pixel = 0;
                            } else {
                                pixel = arithmeticDecoder.decodeBit(cx, arithmeticDecoder.genericRegionStats);
                                if (pixel != 0) {
                                    data.set(row * width + col);
                                }
                            }

                            cx0 = ((BinaryOperation.bit32ShiftL(cx0, 1)) | cxPtr0.nextPixel()) & 0x07;
                            cx1 = ((BinaryOperation.bit32ShiftL(cx1, 1)) | cxPtr1.nextPixel()) & 0x0f;
                            cx2 = ((BinaryOperation.bit32ShiftL(cx2, 1)) | pixel) & 0x03;
                        }
                        break;

                    case 3:

                        cxPtr1.setPointer(0, row - 1);
                        cx1 = (cxPtr1.nextPixel() << 1);
                        cx1 |= (cxPtr1.nextPixel());
					/*cx1 = cxPtr1.nextPixel();
					cx1 = (BinaryOperation.bit32ShiftL(cx1, 1)) | cxPtr1.nextPixel();
*/
                        cx2 = 0;

                        atPtr0.setPointer(adaptiveTemplateX[0], row + adaptiveTemplateY[0]);

                        for (int col = 0; col < width; col++) {

                            cx = (BinaryOperation.bit32ShiftL(cx1, 5)) | (BinaryOperation.bit32ShiftL(cx2, 1)) | atPtr0.nextPixel();

                            if (useSkip && skipBitmap.getPixel(col, row) != 0) {
                                pixel = 0;

                            } else {
                                pixel = arithmeticDecoder.decodeBit(cx, arithmeticDecoder.genericRegionStats);
                                if (pixel != 0) {
                                    data.set(row * width + col);
                                }
                            }

                            cx1 = ((BinaryOperation.bit32ShiftL(cx1, 1)) | cxPtr1.nextPixel()) & 0x1f;
                            cx2 = ((BinaryOperation.bit32ShiftL(cx2, 1)) | pixel) & 0x0f;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * <p>readGenericRefinementRegion.</p>
     *
     * @param template                             a {@link java.lang.Integer} object.
     * @param typicalPredictionGenericRefinementOn a boolean.
     * @param referredToBitmap                     a {@link org.jpedal.jbig2.image.JBIG2Bitmap} object.
     * @param referenceDX                          a {@link java.lang.Integer} object.
     * @param referenceDY                          a {@link java.lang.Integer} object.
     * @param adaptiveTemplateX                    an array of {@link short} objects.
     * @param adaptiveTemplateY                    an array of {@link short} objects.
     * @throws java.io.IOException             if any.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void readGenericRefinementRegion(final int template, final boolean typicalPredictionGenericRefinementOn, final JBIG2Bitmap referredToBitmap, final int referenceDX, final int referenceDY, final short[] adaptiveTemplateX, final short[] adaptiveTemplateY) throws IOException, JBIG2Exception {

        //ArithmeticDecoder arithmeticDecoder = ArithmeticDecoder.getInstance();

        final BitmapPointer cxPtr0;
        final BitmapPointer cxPtr1;
        final BitmapPointer cxPtr2;
        final BitmapPointer cxPtr3;
        final BitmapPointer cxPtr4;
        final BitmapPointer cxPtr5;
        final BitmapPointer cxPtr6;
        final BitmapPointer typicalPredictionGenericRefinementCXPtr0;
        final BitmapPointer typicalPredictionGenericRefinementCXPtr1;
        final BitmapPointer typicalPredictionGenericRefinementCXPtr2;

        final long ltpCX;
        if (template != 0) {
            ltpCX = 0x008;

            cxPtr0 = new BitmapPointer(this);
            cxPtr1 = new BitmapPointer(this);
            cxPtr2 = new BitmapPointer(referredToBitmap);
            cxPtr3 = new BitmapPointer(referredToBitmap);
            cxPtr4 = new BitmapPointer(referredToBitmap);
            cxPtr5 = new BitmapPointer(this);
            cxPtr6 = new BitmapPointer(this);
            typicalPredictionGenericRefinementCXPtr0 = new BitmapPointer(referredToBitmap);
            typicalPredictionGenericRefinementCXPtr1 = new BitmapPointer(referredToBitmap);
            typicalPredictionGenericRefinementCXPtr2 = new BitmapPointer(referredToBitmap);
        } else {
            ltpCX = 0x0010;

            cxPtr0 = new BitmapPointer(this);
            cxPtr1 = new BitmapPointer(this);
            cxPtr2 = new BitmapPointer(referredToBitmap);
            cxPtr3 = new BitmapPointer(referredToBitmap);
            cxPtr4 = new BitmapPointer(referredToBitmap);
            cxPtr5 = new BitmapPointer(this);
            cxPtr6 = new BitmapPointer(referredToBitmap);
            typicalPredictionGenericRefinementCXPtr0 = new BitmapPointer(referredToBitmap);
            typicalPredictionGenericRefinementCXPtr1 = new BitmapPointer(referredToBitmap);
            typicalPredictionGenericRefinementCXPtr2 = new BitmapPointer(referredToBitmap);
        }

        long cx, cx0, cx2, cx3, cx4;
        long typicalPredictionGenericRefinementCX0, typicalPredictionGenericRefinementCX1, typicalPredictionGenericRefinementCX2;
        boolean ltp = false;

        for (int row = 0; row < height; row++) {

            if (template != 0) {

                cxPtr0.setPointer(0, row - 1);
                cx0 = cxPtr0.nextPixel();

                cxPtr1.setPointer(-1, row);

                cxPtr2.setPointer(-referenceDX, row - 1 - referenceDY);

                cxPtr3.setPointer(-1 - referenceDX, row - referenceDY);
                cx3 = cxPtr3.nextPixel();
                cx3 = (BinaryOperation.bit32ShiftL(cx3, 1)) | cxPtr3.nextPixel();

                cxPtr4.setPointer(-referenceDX, row + 1 - referenceDY);
                cx4 = cxPtr4.nextPixel();

                typicalPredictionGenericRefinementCX0 = typicalPredictionGenericRefinementCX1 = typicalPredictionGenericRefinementCX2 = 0;

                if (typicalPredictionGenericRefinementOn) {
                    typicalPredictionGenericRefinementCXPtr0.setPointer(-1 - referenceDX, row - 1 - referenceDY);
                    typicalPredictionGenericRefinementCX0 = typicalPredictionGenericRefinementCXPtr0.nextPixel();
                    typicalPredictionGenericRefinementCX0 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX0, 1)) | typicalPredictionGenericRefinementCXPtr0.nextPixel();
                    typicalPredictionGenericRefinementCX0 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX0, 1)) | typicalPredictionGenericRefinementCXPtr0.nextPixel();

                    typicalPredictionGenericRefinementCXPtr1.setPointer(-1 - referenceDX, row - referenceDY);
                    typicalPredictionGenericRefinementCX1 = typicalPredictionGenericRefinementCXPtr1.nextPixel();
                    typicalPredictionGenericRefinementCX1 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX1, 1)) | typicalPredictionGenericRefinementCXPtr1.nextPixel();
                    typicalPredictionGenericRefinementCX1 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX1, 1)) | typicalPredictionGenericRefinementCXPtr1.nextPixel();

                    typicalPredictionGenericRefinementCXPtr2.setPointer(-1 - referenceDX, row + 1 - referenceDY);
                    typicalPredictionGenericRefinementCX2 = typicalPredictionGenericRefinementCXPtr2.nextPixel();
                    typicalPredictionGenericRefinementCX2 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX2, 1)) | typicalPredictionGenericRefinementCXPtr2.nextPixel();
                    typicalPredictionGenericRefinementCX2 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX2, 1)) | typicalPredictionGenericRefinementCXPtr2.nextPixel();
                }

                for (int col = 0; col < width; col++) {

                    cx0 = ((BinaryOperation.bit32ShiftL(cx0, 1)) | cxPtr0.nextPixel()) & 7;
                    cx3 = ((BinaryOperation.bit32ShiftL(cx3, 1)) | cxPtr3.nextPixel()) & 7;
                    cx4 = ((BinaryOperation.bit32ShiftL(cx4, 1)) | cxPtr4.nextPixel()) & 3;

                    if (typicalPredictionGenericRefinementOn) {
                        typicalPredictionGenericRefinementCX0 = ((BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX0, 1)) | typicalPredictionGenericRefinementCXPtr0.nextPixel()) & 7;
                        typicalPredictionGenericRefinementCX1 = ((BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX1, 1)) | typicalPredictionGenericRefinementCXPtr1.nextPixel()) & 7;
                        typicalPredictionGenericRefinementCX2 = ((BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX2, 1)) | typicalPredictionGenericRefinementCXPtr2.nextPixel()) & 7;

                        final int decodeBit = arithmeticDecoder.decodeBit(ltpCX, arithmeticDecoder.refinementRegionStats);
                        if (decodeBit != 0) {
                            ltp = !ltp;
                        }
                        if (typicalPredictionGenericRefinementCX0 == 0 && typicalPredictionGenericRefinementCX1 == 0 && typicalPredictionGenericRefinementCX2 == 0) {
                            setPixel(col, row, 0);
                            continue;
                        } else if (typicalPredictionGenericRefinementCX0 == 7 && typicalPredictionGenericRefinementCX1 == 7 && typicalPredictionGenericRefinementCX2 == 7) {
                            setPixel(col, row, 1);
                            continue;
                        }
                    }

                    cx = (BinaryOperation.bit32ShiftL(cx0, 7)) | (cxPtr1.nextPixel() << 6) | (cxPtr2.nextPixel() << 5) | (BinaryOperation.bit32ShiftL(cx3, 2)) | cx4;

                    final int pixel = arithmeticDecoder.decodeBit(cx, arithmeticDecoder.refinementRegionStats);
                    if (pixel == 1) {
                        data.set(row * width + col);
                    }
                }

            } else {

                cxPtr0.setPointer(0, row - 1);
                cx0 = cxPtr0.nextPixel();

                cxPtr1.setPointer(-1, row);

                cxPtr2.setPointer(-referenceDX, row - 1 - referenceDY);
                cx2 = cxPtr2.nextPixel();

                cxPtr3.setPointer(-1 - referenceDX, row - referenceDY);
                cx3 = cxPtr3.nextPixel();
                cx3 = (BinaryOperation.bit32ShiftL(cx3, 1)) | cxPtr3.nextPixel();

                cxPtr4.setPointer(-1 - referenceDX, row + 1 - referenceDY);
                cx4 = cxPtr4.nextPixel();
                cx4 = (BinaryOperation.bit32ShiftL(cx4, 1)) | cxPtr4.nextPixel();

                cxPtr5.setPointer(adaptiveTemplateX[0], row + adaptiveTemplateY[0]);

                cxPtr6.setPointer(adaptiveTemplateX[1] - referenceDX, row + adaptiveTemplateY[1] - referenceDY);

                typicalPredictionGenericRefinementCX0 = typicalPredictionGenericRefinementCX1 = typicalPredictionGenericRefinementCX2 = 0;
                if (typicalPredictionGenericRefinementOn) {
                    typicalPredictionGenericRefinementCXPtr0.setPointer(-1 - referenceDX, row - 1 - referenceDY);
                    typicalPredictionGenericRefinementCX0 = typicalPredictionGenericRefinementCXPtr0.nextPixel();
                    typicalPredictionGenericRefinementCX0 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX0, 1)) | typicalPredictionGenericRefinementCXPtr0.nextPixel();
                    typicalPredictionGenericRefinementCX0 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX0, 1)) | typicalPredictionGenericRefinementCXPtr0.nextPixel();

                    typicalPredictionGenericRefinementCXPtr1.setPointer(-1 - referenceDX, row - referenceDY);
                    typicalPredictionGenericRefinementCX1 = typicalPredictionGenericRefinementCXPtr1.nextPixel();
                    typicalPredictionGenericRefinementCX1 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX1, 1)) | typicalPredictionGenericRefinementCXPtr1.nextPixel();
                    typicalPredictionGenericRefinementCX1 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX1, 1)) | typicalPredictionGenericRefinementCXPtr1.nextPixel();

                    typicalPredictionGenericRefinementCXPtr2.setPointer(-1 - referenceDX, row + 1 - referenceDY);
                    typicalPredictionGenericRefinementCX2 = typicalPredictionGenericRefinementCXPtr2.nextPixel();
                    typicalPredictionGenericRefinementCX2 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX2, 1)) | typicalPredictionGenericRefinementCXPtr2.nextPixel();
                    typicalPredictionGenericRefinementCX2 = (BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX2, 1)) | typicalPredictionGenericRefinementCXPtr2.nextPixel();
                }

                for (int col = 0; col < width; col++) {

                    cx0 = ((BinaryOperation.bit32ShiftL(cx0, 1)) | cxPtr0.nextPixel()) & 3;
                    cx2 = ((BinaryOperation.bit32ShiftL(cx2, 1)) | cxPtr2.nextPixel()) & 3;
                    cx3 = ((BinaryOperation.bit32ShiftL(cx3, 1)) | cxPtr3.nextPixel()) & 7;
                    cx4 = ((BinaryOperation.bit32ShiftL(cx4, 1)) | cxPtr4.nextPixel()) & 7;

                    if (typicalPredictionGenericRefinementOn) {
                        typicalPredictionGenericRefinementCX0 = ((BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX0, 1)) | typicalPredictionGenericRefinementCXPtr0.nextPixel()) & 7;
                        typicalPredictionGenericRefinementCX1 = ((BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX1, 1)) | typicalPredictionGenericRefinementCXPtr1.nextPixel()) & 7;
                        typicalPredictionGenericRefinementCX2 = ((BinaryOperation.bit32ShiftL(typicalPredictionGenericRefinementCX2, 1)) | typicalPredictionGenericRefinementCXPtr2.nextPixel()) & 7;

                        final int decodeBit = arithmeticDecoder.decodeBit(ltpCX, arithmeticDecoder.refinementRegionStats);
                        if (decodeBit == 1) {
                            ltp = !ltp;
                        }
                        if (typicalPredictionGenericRefinementCX0 == 0 && typicalPredictionGenericRefinementCX1 == 0 && typicalPredictionGenericRefinementCX2 == 0) {
                            setPixel(col, row, 0);
                            continue;
                        } else if (typicalPredictionGenericRefinementCX0 == 7 && typicalPredictionGenericRefinementCX1 == 7 && typicalPredictionGenericRefinementCX2 == 7) {
                            setPixel(col, row, 1);
                            continue;
                        }
                    }

                    cx = (BinaryOperation.bit32ShiftL(cx0, 11)) | (cxPtr1.nextPixel() << 10) | (BinaryOperation.bit32ShiftL(cx2, 8)) | (BinaryOperation.bit32ShiftL(cx3, 5)) | (BinaryOperation.bit32ShiftL(cx4, 2)) | (cxPtr5.nextPixel() << 1) | cxPtr6.nextPixel();

                    final int pixel = arithmeticDecoder.decodeBit(cx, arithmeticDecoder.refinementRegionStats);
                    if (pixel == 1) {
                        setPixel(col, row, 1);
                    }
                }
            }
        }
    }

    /**
     * <p>readTextRegion.</p>
     *
     * @param huffman                       a boolean.
     * @param symbolRefine                  a boolean.
     * @param noOfSymbolInstances           a {@link java.lang.Integer} object.
     * @param logStrips                     a {@link java.lang.Integer} object.
     * @param noOfSymbols                   a {@link java.lang.Integer} object.
     * @param symbolCodeTable               an array of {@link int} objects.
     * @param symbolCodeLength              a {@link java.lang.Integer} object.
     * @param symbols                       an array of {@link org.jpedal.jbig2.image.JBIG2Bitmap} objects.
     * @param defaultPixel                  a {@link java.lang.Integer} object.
     * @param combinationOperator           a {@link java.lang.Integer} object.
     * @param transposed                    a boolean.
     * @param referenceCorner               a {@link java.lang.Integer} object.
     * @param sOffset                       a {@link java.lang.Integer} object.
     * @param huffmanFSTable                an array of {@link int} objects.
     * @param huffmanDSTable                an array of {@link int} objects.
     * @param huffmanDTTable                an array of {@link int} objects.
     * @param huffmanRDWTable               an array of {@link int} objects.
     * @param huffmanRDHTable               an array of {@link int} objects.
     * @param huffmanRDXTable               an array of {@link int} objects.
     * @param huffmanRDYTable               an array of {@link int} objects.
     * @param huffmanRSizeTable             an array of {@link int} objects.
     * @param template                      a {@link java.lang.Integer} object.
     * @param symbolRegionAdaptiveTemplateX an array of {@link short} objects.
     * @param symbolRegionAdaptiveTemplateY an array of {@link short} objects.
     * @param decoder                       a {@link org.jpedal.jbig2.decoders.JBIG2StreamDecoder} object.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     * @throws java.io.IOException             if any.
     */
    public void readTextRegion(final boolean huffman, final boolean symbolRefine, final int noOfSymbolInstances, final int logStrips, final int noOfSymbols, final int[][] symbolCodeTable, final int symbolCodeLength, final JBIG2Bitmap[] symbols, final int defaultPixel, final int combinationOperator, final boolean transposed, final int referenceCorner, final int sOffset, final int[][] huffmanFSTable, final int[][] huffmanDSTable, final int[][] huffmanDTTable, final int[][] huffmanRDWTable, final int[][] huffmanRDHTable, final int[][] huffmanRDXTable, final int[][] huffmanRDYTable, final int[][] huffmanRSizeTable, final int template, final short[] symbolRegionAdaptiveTemplateX,
                               final short[] symbolRegionAdaptiveTemplateY, final JBIG2StreamDecoder decoder) throws JBIG2Exception, IOException {

        JBIG2Bitmap symbolBitmap;

        final int strips = 1 << logStrips;

        clear(defaultPixel);

        //HuffmanDecoder huffDecoder = HuffmanDecoder.getInstance();
        //ArithmeticDecoder arithmeticDecoder = ArithmeticDecoder.getInstance();

        int t;
        if (huffman) {
            t = huffmanDecoder.decodeInt(huffmanDTTable).intResult();
        } else {
            t = arithmeticDecoder.decodeInt(arithmeticDecoder.iadtStats).intResult();
        }
        t *= -strips;

        int currentInstance = 0;
        int firstS = 0;
        int dt, tt, ds, s;
        while (currentInstance < noOfSymbolInstances) {

            if (huffman) {
                dt = huffmanDecoder.decodeInt(huffmanDTTable).intResult();
            } else {
                dt = arithmeticDecoder.decodeInt(arithmeticDecoder.iadtStats).intResult();
            }
            t += dt * strips;

            if (huffman) {
                ds = huffmanDecoder.decodeInt(huffmanFSTable).intResult();
            } else {
                ds = arithmeticDecoder.decodeInt(arithmeticDecoder.iafsStats).intResult();
            }
            firstS += ds;
            s = firstS;

            while (true) {

                if (strips == 1) {
                    dt = 0;
                } else if (huffman) {
                    dt = decoder.readBits(logStrips);
                } else {
                    dt = arithmeticDecoder.decodeInt(arithmeticDecoder.iaitStats).intResult();
                }
                tt = t + dt;

                final long symbolID;
                if (huffman) {
                    if (symbolCodeTable != null) {
                        symbolID = huffmanDecoder.decodeInt(symbolCodeTable).intResult();
                    } else {
                        symbolID = decoder.readBits(symbolCodeLength);
                    }
                } else {
                    symbolID = arithmeticDecoder.decodeIAID(symbolCodeLength, arithmeticDecoder.iaidStats);
                }

                if (symbolID >= noOfSymbols) {
                    if (JBIG2StreamDecoder.debug)
                        log.info("Invalid symbol number in JBIG2 text region");
                } else {
                    symbolBitmap = null;

                    final int ri;
                    if (symbolRefine) {
                        if (huffman) {
                            ri = decoder.readBit();
                        } else {
                            ri = arithmeticDecoder.decodeInt(arithmeticDecoder.iariStats).intResult();
                        }
                    } else {
                        ri = 0;
                    }
                    if (ri != 0) {

                        final int refinementDeltaWidth;
                        final int refinementDeltaHeight;
                        int refinementDeltaX;
                        int refinementDeltaY;

                        if (huffman) {
                            refinementDeltaWidth = huffmanDecoder.decodeInt(huffmanRDWTable).intResult();
                            refinementDeltaHeight = huffmanDecoder.decodeInt(huffmanRDHTable).intResult();
                            refinementDeltaX = huffmanDecoder.decodeInt(huffmanRDXTable).intResult();
                            refinementDeltaY = huffmanDecoder.decodeInt(huffmanRDYTable).intResult();

                            decoder.consumeRemainingBits();
                            arithmeticDecoder.start();
                        } else {
                            refinementDeltaWidth = arithmeticDecoder.decodeInt(arithmeticDecoder.iardwStats).intResult();
                            refinementDeltaHeight = arithmeticDecoder.decodeInt(arithmeticDecoder.iardhStats).intResult();
                            refinementDeltaX = arithmeticDecoder.decodeInt(arithmeticDecoder.iardxStats).intResult();
                            refinementDeltaY = arithmeticDecoder.decodeInt(arithmeticDecoder.iardyStats).intResult();
                        }
                        refinementDeltaX = ((refinementDeltaWidth >= 0) ? refinementDeltaWidth : refinementDeltaWidth - 1) / 2 + refinementDeltaX;
                        refinementDeltaY = ((refinementDeltaHeight >= 0) ? refinementDeltaHeight : refinementDeltaHeight - 1) / 2 + refinementDeltaY;

                        symbolBitmap = new JBIG2Bitmap(refinementDeltaWidth + symbols[(int) symbolID].width, refinementDeltaHeight + symbols[(int) symbolID].height, arithmeticDecoder, huffmanDecoder, mmrDecoder);

                        symbolBitmap.readGenericRefinementRegion(template, false, symbols[(int) symbolID], refinementDeltaX, refinementDeltaY, symbolRegionAdaptiveTemplateX, symbolRegionAdaptiveTemplateY);

                    } else {
                        symbolBitmap = symbols[(int) symbolID];
                    }

                    final int bitmapWidth = symbolBitmap.width - 1;
                    final int bitmapHeight = symbolBitmap.height - 1;
                    if (transposed) {
                        switch (referenceCorner) {
                            case 0: // bottom left
                            case 1: // top left
                                combine(symbolBitmap, tt, s, combinationOperator);
                                break;
                            case 2: // bottom right
                            case 3: // top right
                                combine(symbolBitmap, (tt - bitmapWidth), s, combinationOperator);
                                break;
                            default:
                                break;
                        }
                        s += bitmapHeight;
                    } else {
                        switch (referenceCorner) {
                            case 0: // bottom left
                            case 2: // bottom right
                                combine(symbolBitmap, s, (tt - bitmapHeight), combinationOperator);
                                break;
                            case 1: // top left
                            case 3: // top right
                                combine(symbolBitmap, s, tt, combinationOperator);
                                break;
                            default:
                                break;
                        }
                        s += bitmapWidth;
                    }
                }

                currentInstance++;

                final DecodeIntResult decodeIntResult;

                if (huffman) {
                    decodeIntResult = huffmanDecoder.decodeInt(huffmanDSTable);
                } else {
                    decodeIntResult = arithmeticDecoder.decodeInt(arithmeticDecoder.iadsStats);
                }

                if (!decodeIntResult.booleanResult()) {
                    break;
                }

                ds = decodeIntResult.intResult();

                s += sOffset + ds;
            }
        }
    }

    /**
     * <p>clear.</p>
     *
     * @param defPixel a {@link java.lang.Integer} object.
     */
    public void clear(final int defPixel) {
        data.setAll(defPixel == 1);
        //data.set(0, data.size(), defPixel == 1);
    }

    /**
     * <p>combine.</p>
     *
     * @param bitmap a {@link org.jpedal.jbig2.image.JBIG2Bitmap} object.
     * @param x      a {@link java.lang.Integer} object.
     * @param y      a {@link java.lang.Integer} object.
     * @param combOp a long.
     */
    public void combine(final JBIG2Bitmap bitmap, final int x, final int y, final long combOp) {
        final int srcWidth = bitmap.width;
        int srcHeight = bitmap.height;

//		int maxRow = y + srcHeight;
//		int maxCol = x + srcWidth;
//
//		for (int row = y; row < maxRow; row++) {
//			for (int col = x; col < maxCol; srcCol += 8, col += 8) {
//
//				byte srcPixelByte = bitmap.getPixelByte(srcCol, srcRow);
//				byte dstPixelByte = getPixelByte(col, row);
//				byte endPixelByte;
//
//				switch ((int) combOp) {
//				case 0: // or
//					endPixelByte = (byte) (dstPixelByte | srcPixelByte);
//					break;
//				case 1: // and
//					endPixelByte = (byte) (dstPixelByte & srcPixelByte);
//					break;
//				case 2: // xor
//					endPixelByte = (byte) (dstPixelByte ^ srcPixelByte);
//					break;
//				case 3: // xnor
//					endPixelByte = (byte) ~(dstPixelByte ^ srcPixelByte);
//					break;
//				case 4: // replace
//				default:
//					endPixelByte = srcPixelByte;
//					break;
//				}
//				int used = maxCol - col;
//				if (used < 8) {
//					// mask bits
//					endPixelByte = (byte) ((endPixelByte & (0xFF >> (8 - used))) | (dstPixelByte & (0xFF << (used))));
//				}
//				setPixelByte(col, row, endPixelByte);
//			}
//
//			srcCol = 0;
//			srcRow++;
        int minWidth = srcWidth;
        if (x + srcWidth > width) {
            //Should not happen but occurs sometimes because there is something wrong with halftone pics
            minWidth = width - x;
        }
        if (y + srcHeight > height) {
            //Should not happen but occurs sometimes because there is something wrong with halftone pics
            srcHeight = height - y;
        }

        int srcIndx = 0;
        int indx = y * width + x;
        if (combOp == 0) {
            if (x == 0 && y == 0 && srcHeight == height && srcWidth == width) {
                for (int i = 0; i < data.w.length; i++) {
                    data.w[i] |= bitmap.data.w[i];
                }
            }
            for (int row = y; row < y + srcHeight; row++) {
                indx = row * width + x;
                data.or(indx, bitmap.data, srcIndx, minWidth);
				/*for (int col = 0; col < minWidth; col++) {
					if (bitmap.data.get(srcIndx + col)) data.set(indx);
					//data.set(indx, bitmap.data.get(srcIndx + col) || data.get(indx));
					indx++;
				}*/
                srcIndx += srcWidth;
            }
        } else if (combOp == 1) {
            if (x == 0 && y == 0 && srcHeight == height && srcWidth == width) {
                for (int i = 0; i < data.w.length; i++) {
                    data.w[i] &= bitmap.data.w[i];
                }
            }
            for (int row = y; row < y + srcHeight; row++) {
                indx = row * width + x;
                for (int col = 0; col < minWidth; col++) {
                    data.set(indx, bitmap.data.get(srcIndx + col) && data.get(indx));
                    indx++;
                }
                srcIndx += srcWidth;
            }
        } else if (combOp == 2) {
            if (x == 0 && y == 0 && srcHeight == height && srcWidth == width) {
                for (int i = 0; i < data.w.length; i++) {
                    data.w[i] ^= bitmap.data.w[i];
                }
            } else {
                for (int row = y; row < y + srcHeight; row++) {
                    indx = row * width + x;
                    for (int col = 0; col < minWidth; col++) {
                        data.set(indx, bitmap.data.get(srcIndx + col) ^ data.get(indx));
                        indx++;
                    }
                    srcIndx += srcWidth;
                }
            }
        } else if (combOp == 3) {
            for (int row = y; row < y + srcHeight; row++) {
                indx = row * width + x;
                for (int col = 0; col < minWidth; col++) {
                    final boolean srcPixel = bitmap.data.get(srcIndx + col);
                    final boolean pixel = data.get(indx);
                    data.set(indx, pixel == srcPixel);
                    indx++;
                }
                srcIndx += srcWidth;
            }
        } else if (combOp == 4) {
            if (x == 0 && y == 0 && srcHeight == height && srcWidth == width) {
                System.arraycopy(bitmap.data.w, 0, data.w, 0, data.w.length);
            } else {
                for (int row = y; row < y + srcHeight; row++) {
                    indx = row * width + x;
                    for (int col = 0; col < minWidth; col++) {
                        data.set(indx, bitmap.data.get(srcIndx + col));
                        srcIndx++;
                        indx++;
                    }
                    srcIndx += srcWidth;
                }
            }
        }
    }
    private void duplicateRow(final int yDest, final int ySrc) {
        for (int i = 0; i < width; i++) {
            setPixel(i, yDest, getPixel(i, ySrc));
        }
    }

    /**
     * <p>Getter for the field <code>data</code>.</p>
     *
     * @param switchPixelColor a boolean.
     * @return an array of {@link byte} objects.
     */
    public byte[] getData(final boolean switchPixelColor) {
        final byte[] bytes = new byte[height * line];

        int count = 0, offset = 0;
        long k = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if ((count & FastBitSet.mask) == 0) {
                    k = data.w[count >>> FastBitSet.pot];
                }
                //if ((k & (1L << count)) != 0) {
                final int bit = 7 - (offset & 0x7);
                bytes[offset >> 3] |= (byte) (((k >>> count) & 1) << bit);
                //}
                count++;
                offset++;
            }

            offset = (line * 8 * (row + 1));
        }

        if (switchPixelColor) {
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] ^= (byte) 0xff;
            }
        }

        return bytes;
    }

    /**
     * <p>getSlice.</p>
     *
     * @param x      a {@link java.lang.Integer} object.
     * @param y      a {@link java.lang.Integer} object.
     * @param width  a {@link java.lang.Integer} object.
     * @param height a {@link java.lang.Integer} object.
     * @return a {@link org.jpedal.jbig2.image.JBIG2Bitmap} object.
     */
    public JBIG2Bitmap getSlice(final int x, final int y, final int width, final int height) {

        final JBIG2Bitmap slice = new JBIG2Bitmap(width, height, arithmeticDecoder, huffmanDecoder, mmrDecoder);

        int sliceIndx = 0;
        for (int row = y; row < height; row++) {
            int indx = row * this.width + x;
            for (int col = x; col < x + width; col++) {
                if (data.get(indx)) slice.data.set(sliceIndx);
                sliceIndx++;
                indx++;
            }
        }

        return slice;
    }

    private void setPixel(final int col, final int row, final FastBitSet data, final int value) {
        final int index = (row * width) + col;

        data.set(index, value == 1);
    }

    /**
     * <p>setPixel.</p>
     *
     * @param col   a {@link java.lang.Integer} object.
     * @param row   a {@link java.lang.Integer} object.
     * @param value a {@link java.lang.Integer} object.
     */
    public void setPixel(final int col, final int row, final int value) {
        setPixel(col, row, data, value);
    }

    /**
     * <p>getPixel.</p>
     *
     * @param col a {@link java.lang.Integer} object.
     * @param row a {@link java.lang.Integer} object.
     * @return a {@link java.lang.Integer} object.
     */
    public int getPixel(final int col, final int row) {
        return data.get((row * width) + col) ? 1 : 0;
    }

    /**
     * <p>expand.</p>
     *
     * @param newHeight    a {@link java.lang.Integer} object.
     * @param defaultPixel a {@link java.lang.Integer} object.
     */
    public void expand(final int newHeight, final int defaultPixel) {
        final FastBitSet newData = new FastBitSet(newHeight * width);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                setPixel(col, row, newData, getPixel(col, row));
            }
        }

        this.height = newHeight;
        this.data = newData;
    }

    /**
     * <p>getBufferedImage.</p>
     *
     * @return a {@link java.awt.image.BufferedImage} object.
     */
    public BufferedImage getBufferedImage() {
        final byte[] bytes = getData(true);

        if (bytes == null)
            return null;

        // make a a DEEP copy so we can't alter
        final int len = bytes.length;
        final byte[] copy = new byte[len];
        System.arraycopy(bytes, 0, copy, 0, len);

        // create an image from the raw data
        final DataBuffer db = new DataBufferByte(copy, copy.length);

        final WritableRaster raster = Raster.createPackedRaster(db, width, height, 1, null);

        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        image.setData(raster);

        return image;
    }

    /**
     * Faster BitSet implementation. Does not perfom any bound checks.
     * <p>
     * Author Boris von Loesch
     */
    static final class FastBitSet {
        static final int pot = 6;
        static final int mask = (int) ((-1L) >>> (Long.SIZE - pot));
        final long[] w;
        final int length;


        public FastBitSet(final int length) {
            this.length = length;
            int wcount = length / Long.SIZE;
            if (length % Long.SIZE != 0) wcount++;
            w = new long[wcount];
        }

        public int size() {
            return length;
        }

        public void setAll(final boolean value) {
            if (value)
                for (int i = 0; i < w.length; i++) {
                    w[i] = -1L;
                }
            else
                for (int i = 0; i < w.length; i++) {
                    w[i] = 0;
                }

        }

        public void set(final int start, final int end, final boolean value) {
            if (value) {
                for (int i = start; i < end; i++) {
                    set(i);
                }
            } else {
                for (int i = start; i < end; i++) {
                    clear(i);
                }
            }
        }

        public void or(int startindex, final FastBitSet set, final int startIndex, final int length) {
            int setStartIndex = startIndex;
            final int shift = startindex - setStartIndex;
            long k = set.w[setStartIndex >> pot];
            //Cyclic shift
            k = (k << shift) | (k >>> (Long.SIZE - shift));
            if ((setStartIndex & mask) + length <= Long.SIZE) {
                setStartIndex += shift;
                for (int i = 0; i < length; i++) {
                    w[(startindex) >>> pot] |= k & (1L << setStartIndex);
                    setStartIndex++;
                    startindex++;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    if ((setStartIndex & mask) == 0) {
                        k = set.w[(setStartIndex) >> pot];
                        k = (k << shift) | (k >>> (Long.SIZE - shift));
                    }
                    w[(startindex) >>> pot] |= k & (1L << (setStartIndex + shift));
                    setStartIndex++;
                    startindex++;
                }
            }
        }

        public void set(final int index, final boolean value) {
            if (value) set(index);
            else clear(index);
        }

        public void set(final int index) {
            final int windex = index >>> pot;
            w[windex] |= (1L << index);
        }

        public void clear(final int index) {
            final int windex = index >>> pot;
            w[windex] &= ~(1L << index);
        }

        public boolean get(final int index) {
            final int windex = index >>> pot;
            return ((w[windex] & (1L << index)) != 0);
        }
    }
}
