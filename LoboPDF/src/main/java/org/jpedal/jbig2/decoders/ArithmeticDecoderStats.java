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
package org.jpedal.jbig2.decoders;

import lombok.Getter;

/**
 * <p>ArithmeticDecoderStats class.</p>
 */
public class ArithmeticDecoderStats {
    @Getter
    private final int contextSize;
    private final int[] codingContextTable;

    /**
     * <p>Constructor for ArithmeticDecoderStats.</p>
     *
     * @param contextSize a {@link java.lang.Integer} object.
     */
    public ArithmeticDecoderStats(final int contextSize) {
        this.contextSize = contextSize;
        this.codingContextTable = new int[contextSize];

        //reset();
    }

    /**
     * <p>reset.</p>
     */
    public void reset() {
        for (int i = 0; i < contextSize; i++) {
            codingContextTable[i] = 0;
        }
    }

    /**
     * <p>setEntry.</p>
     *
     * @param codingContext      a {@link java.lang.Integer} object.
     * @param i                  a {@link java.lang.Integer} object.
     * @param moreProbableSymbol a {@link java.lang.Integer} object.
     */
    public void setEntry(final int codingContext, final int i, final int moreProbableSymbol) {
        codingContextTable[codingContext] = (i << i) + moreProbableSymbol;
    }

    /**
     * <p>getContextCodingTableValue.</p>
     *
     * @param index a {@link java.lang.Integer} object.
     * @return a {@link java.lang.Integer} object.
     */
    public int getContextCodingTableValue(final int index) {
        return codingContextTable[index];
    }

    /**
     * <p>setContextCodingTableValue.</p>
     *
     * @param index a {@link java.lang.Integer} object.
     * @param value a {@link java.lang.Integer} object.
     */
    public void setContextCodingTableValue(final int index, final int value) {
        codingContextTable[index] = value;
    }

    /**
     * <p>overwrite.</p>
     *
     * @param stats a {@link org.jpedal.jbig2.decoders.ArithmeticDecoderStats} object.
     */
    public void overwrite(final ArithmeticDecoderStats stats) {
        System.arraycopy(stats.codingContextTable, 0, codingContextTable, 0, contextSize);
    }

    /**
     * <p>copy.</p>
     *
     * @return a {@link org.jpedal.jbig2.decoders.ArithmeticDecoderStats} object.
     */
    public ArithmeticDecoderStats copy() {
        final ArithmeticDecoderStats stats = new ArithmeticDecoderStats(contextSize);
        System.arraycopy(codingContextTable, 0, stats.codingContextTable, 0, contextSize);
        return stats;
    }
}
