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
package org.jpedal.jbig2.decoders;

import org.jpedal.jbig2.io.StreamReader;
import org.jpedal.jbig2.util.BinaryOperation;

import java.io.IOException;

/**
 * <p>ArithmeticDecoder class.</p>
 */
public class ArithmeticDecoder {

    public final ArithmeticDecoderStats iadhStats;
    public final ArithmeticDecoderStats iadwStats;
    public final ArithmeticDecoderStats iaexStats;
    public final ArithmeticDecoderStats iaaiStats;
    public final ArithmeticDecoderStats iadtStats;
    public final ArithmeticDecoderStats iaitStats;
    public final ArithmeticDecoderStats iafsStats;
    public final ArithmeticDecoderStats iadsStats;
    public final ArithmeticDecoderStats iardxStats;
    public final ArithmeticDecoderStats iardyStats;
    public final ArithmeticDecoderStats iardwStats;
    public final ArithmeticDecoderStats iardhStats;
    public final ArithmeticDecoderStats iariStats;
    final int[] contextSize = {16, 13, 10, 10};
    final int[] referredToContextSize = {13, 10};
    final int[] qeTable = {0x56010000, 0x34010000, 0x18010000, 0x0AC10000, 0x05210000, 0x02210000, 0x56010000, 0x54010000, 0x48010000, 0x38010000, 0x30010000, 0x24010000, 0x1C010000, 0x16010000, 0x56010000, 0x54010000, 0x51010000, 0x48010000, 0x38010000, 0x34010000, 0x30010000, 0x28010000, 0x24010000, 0x22010000, 0x1C010000, 0x18010000, 0x16010000, 0x14010000, 0x12010000, 0x11010000, 0x0AC10000, 0x09C10000, 0x08A10000, 0x05210000, 0x04410000, 0x02A10000, 0x02210000, 0x01410000, 0x01110000, 0x00850000, 0x00490000, 0x00250000, 0x00150000, 0x00090000, 0x00050000, 0x00010000,
            0x56010000};
    final int[] nmpsTable = {1, 2, 3, 4, 5, 38, 7, 8, 9, 10, 11, 12, 13, 29, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 45, 46};
    final int[] nlpsTable = {1, 6, 9, 12, 29, 33, 6, 14, 14, 14, 17, 18, 20, 21, 14, 14, 15, 16, 17, 18, 19, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 46};
    final int[] switchTable = {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private final StreamReader reader;
    public ArithmeticDecoderStats genericRegionStats, refinementRegionStats;
    public ArithmeticDecoderStats iaidStats;
    long buffer0, buffer1;
    long c, a;
    long previous;
    int counter;

    /**
     * <p>Constructor for ArithmeticDecoder.</p>
     *
     * @param reader a {@link org.jpedal.jbig2.io.StreamReader} object.
     */
    public ArithmeticDecoder(final StreamReader reader) {
        this.reader = reader;

        genericRegionStats = new ArithmeticDecoderStats(1 << 1);
        refinementRegionStats = new ArithmeticDecoderStats(1 << 1);

        iadhStats = new ArithmeticDecoderStats(1 << 9);
        iadwStats = new ArithmeticDecoderStats(1 << 9);
        iaexStats = new ArithmeticDecoderStats(1 << 9);
        iaaiStats = new ArithmeticDecoderStats(1 << 9);
        iadtStats = new ArithmeticDecoderStats(1 << 9);
        iaitStats = new ArithmeticDecoderStats(1 << 9);
        iafsStats = new ArithmeticDecoderStats(1 << 9);
        iadsStats = new ArithmeticDecoderStats(1 << 9);
        iardxStats = new ArithmeticDecoderStats(1 << 9);
        iardyStats = new ArithmeticDecoderStats(1 << 9);
        iardwStats = new ArithmeticDecoderStats(1 << 9);
        iardhStats = new ArithmeticDecoderStats(1 << 9);
        iariStats = new ArithmeticDecoderStats(1 << 9);
        iaidStats = new ArithmeticDecoderStats(1 << 1);
    }

    /**
     * <p>resetIntStats.</p>
     *
     * @param symbolCodeLength a {@link java.lang.Integer} object.
     */
    public void resetIntStats(final int symbolCodeLength) {
        iadhStats.reset();
        iadwStats.reset();
        iaexStats.reset();
        iaaiStats.reset();
        iadtStats.reset();
        iaitStats.reset();
        iafsStats.reset();
        iadsStats.reset();
        iardxStats.reset();
        iardyStats.reset();
        iardwStats.reset();
        iardhStats.reset();
        iariStats.reset();

        if (iaidStats.getContextSize() == 1 << (symbolCodeLength + 1)) {
            iaidStats.reset();
        } else {
            iaidStats = new ArithmeticDecoderStats(1 << (symbolCodeLength + 1));
        }
    }

    /**
     * <p>resetGenericStats.</p>
     *
     * @param template      a {@link java.lang.Integer} object.
     * @param previousStats a {@link org.jpedal.jbig2.decoders.ArithmeticDecoderStats} object.
     */
    public void resetGenericStats(final int template, final ArithmeticDecoderStats previousStats) {
        final int size = contextSize[template];

        if (previousStats != null && previousStats.getContextSize() == size) {
            if (genericRegionStats.getContextSize() == size) {
                genericRegionStats.overwrite(previousStats);
            } else {
                genericRegionStats = previousStats.copy();
            }
        } else {
            if (genericRegionStats.getContextSize() == size) {
                genericRegionStats.reset();
            } else {
                genericRegionStats = new ArithmeticDecoderStats(1 << size);
            }
        }
    }

    /**
     * <p>resetRefinementStats.</p>
     *
     * @param template      a {@link java.lang.Integer} object.
     * @param previousStats a {@link org.jpedal.jbig2.decoders.ArithmeticDecoderStats} object.
     */
    public void resetRefinementStats(final int template, final ArithmeticDecoderStats previousStats) {
        final int size = referredToContextSize[template];
        if (previousStats != null && previousStats.getContextSize() == size) {
            if (refinementRegionStats.getContextSize() == size) {
                refinementRegionStats.overwrite(previousStats);
            } else {
                refinementRegionStats = previousStats.copy();
            }
        } else {
            if (refinementRegionStats.getContextSize() == size) {
                refinementRegionStats.reset();
            } else {
                refinementRegionStats = new ArithmeticDecoderStats(1 << size);
            }
        }
    }

    /**
     * <p>start.</p>
     *
     * @throws java.io.IOException if any.
     */
    public void start() throws IOException {
        buffer0 = reader.readByte();
        buffer1 = reader.readByte();

        c = BinaryOperation.bit32ShiftL((buffer0 ^ 0xff), 16);
        readByte();
        c = BinaryOperation.bit32ShiftL(c, 7);
        counter -= 7;
        a = 0x80000000L;
    }

    /**
     * <p>decodeInt.</p>
     *
     * @param stats a {@link org.jpedal.jbig2.decoders.ArithmeticDecoderStats} object.
     * @return a {@link org.jpedal.jbig2.decoders.DecodeIntResult} object.
     * @throws java.io.IOException if any.
     */
    public DecodeIntResult decodeInt(final ArithmeticDecoderStats stats) throws IOException {
        long value;

        previous = 1;
        final int s = decodeIntBit(stats);
        if (decodeIntBit(stats) != 0) {
            if (decodeIntBit(stats) != 0) {
                if (decodeIntBit(stats) != 0) {
                    if (decodeIntBit(stats) != 0) {
                        if (decodeIntBit(stats) != 0) {
                            value = 0;
                            for (int i = 0; i < 32; i++) {
                                value = BinaryOperation.bit32ShiftL(value, 1) | decodeIntBit(stats);
                            }
                            value += 4436;
                        } else {
                            value = 0;
                            for (int i = 0; i < 12; i++) {
                                value = BinaryOperation.bit32ShiftL(value, 1) | decodeIntBit(stats);
                            }
                            value += 340;
                        }
                    } else {
                        value = 0;
                        for (int i = 0; i < 8; i++) {
                            value = BinaryOperation.bit32ShiftL(value, 1) | decodeIntBit(stats);
                        }
                        value += 84;
                    }
                } else {
                    value = 0;
                    for (int i = 0; i < 6; i++) {
                        value = BinaryOperation.bit32ShiftL(value, 1) | decodeIntBit(stats);
                    }
                    value += 20;
                }
            } else {
                value = decodeIntBit(stats);
                value = BinaryOperation.bit32ShiftL(value, 1) | decodeIntBit(stats);
                value = BinaryOperation.bit32ShiftL(value, 1) | decodeIntBit(stats);
                value = BinaryOperation.bit32ShiftL(value, 1) | decodeIntBit(stats);
                value += 4;
            }
        } else {
            value = decodeIntBit(stats);
            value = BinaryOperation.bit32ShiftL(value, 1) | decodeIntBit(stats);
        }

        final int decodedInt;
        if (s != 0) {
            if (value == 0) {
                return new DecodeIntResult((int) value, false);
            }
            decodedInt = (int) -value;
        } else {
            decodedInt = (int) value;
        }

        return new DecodeIntResult(decodedInt, true);
    }

    /**
     * <p>decodeIAID.</p>
     *
     * @param codeLen a long.
     * @param stats   a {@link org.jpedal.jbig2.decoders.ArithmeticDecoderStats} object.
     * @return a long.
     * @throws java.io.IOException if any.
     */
    public long decodeIAID(final long codeLen, final ArithmeticDecoderStats stats) throws IOException {
        previous = 1;
        for (long i = 0; i < codeLen; i++) {
            final int bit = decodeBit(previous, stats);
            previous = BinaryOperation.bit32ShiftL(previous, 1) | bit;
        }

        return previous - (1L << codeLen);
    }

    /**
     * <p>decodeBit.</p>
     *
     * @param context a long.
     * @param stats   a {@link org.jpedal.jbig2.decoders.ArithmeticDecoderStats} object.
     * @return a {@link java.lang.Integer} object.
     * @throws java.io.IOException if any.
     */
    public int decodeBit(final long context, final ArithmeticDecoderStats stats) throws IOException {
        final int iCX = BinaryOperation.bit8Shift(stats.getContextCodingTableValue((int) context), 1, BinaryOperation.RIGHT_SHIFT);
        final int mpsCX = stats.getContextCodingTableValue((int) context) & 1;
        final int qe = qeTable[iCX];

        a -= qe;

        final int bit;
        if (c < a) {
            if ((a & 0x80000000L) != 0) {
                bit = mpsCX;
            } else {
                if (a < qe) {
                    bit = 1 - mpsCX;
                    if (switchTable[iCX] != 0) {
                        stats.setContextCodingTableValue((int) context, (nlpsTable[iCX] << 1) | (1 - mpsCX));
                    } else {
                        stats.setContextCodingTableValue((int) context, (nlpsTable[iCX] << 1) | mpsCX);
                    }
                } else {
                    bit = mpsCX;
                    stats.setContextCodingTableValue((int) context, (nmpsTable[iCX] << 1) | mpsCX);
                }
                do {
                    if (counter == 0) {
                        readByte();
                    }

                    a = BinaryOperation.bit32ShiftL(a, 1);
                    c = BinaryOperation.bit32ShiftL(c, 1);

                    counter--;
                } while ((a & 0x80000000L) == 0);
            }
        } else {
            c -= a;

            if (a < qe) {
                bit = mpsCX;
                stats.setContextCodingTableValue((int) context, (nmpsTable[iCX] << 1) | mpsCX);
            } else {
                bit = 1 - mpsCX;
                if (switchTable[iCX] != 0) {
                    stats.setContextCodingTableValue((int) context, (nlpsTable[iCX] << 1) | (1 - mpsCX));
                } else {
                    stats.setContextCodingTableValue((int) context, (nlpsTable[iCX] << 1) | mpsCX);
                }
            }
            a = qe;

            do {
                if (counter == 0) {
                    readByte();
                }

                a = BinaryOperation.bit32ShiftL(a, 1);
                c = BinaryOperation.bit32ShiftL(c, 1);

                counter--;
            } while ((a & 0x80000000L) == 0);
        }
        return bit;
    }

    private void readByte() throws IOException {
        if (buffer0 == 0xff) {
            if (buffer1 > 0x8f) {
                counter = 8;
            } else {
                buffer0 = buffer1;
                buffer1 = reader.readByte();
                c = c + 0xfe00 - (BinaryOperation.bit32ShiftL(buffer0, 9));
                counter = 7;
            }
        } else {
            buffer0 = buffer1;
            buffer1 = reader.readByte();
            c = c + 0xff00 - (BinaryOperation.bit32ShiftL(buffer0, 8));
            counter = 8;
        }
    }

    private int decodeIntBit(final ArithmeticDecoderStats stats) throws IOException {
        final int bit;

        bit = decodeBit(previous, stats);
        if (previous < 0x100) {
            previous = BinaryOperation.bit32ShiftL(previous, 1) | bit;
        } else {
            previous = (((BinaryOperation.bit32ShiftL(previous, 1)) | bit) & 0x1ff) | 0x100;
        }
        return bit;
    }
}
